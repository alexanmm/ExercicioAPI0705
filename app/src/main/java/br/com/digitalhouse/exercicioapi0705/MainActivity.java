package br.com.digitalhouse.exercicioapi0705;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.com.digitalhouse.exercicioapi0705.adapters.RecyclerViewPessoasAdapter;
import br.com.digitalhouse.exercicioapi0705.data.database.Database;
import br.com.digitalhouse.exercicioapi0705.data.database.dao.PessoasDAO;
import br.com.digitalhouse.exercicioapi0705.interfaces.RecyclerViewOnItemClickListener;
import br.com.digitalhouse.exercicioapi0705.model.Pessoa;
import br.com.digitalhouse.exercicioapi0705.viewmodel.PessoasViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnItemClickListener {

    private RecyclerView recyclerViewPessoas;
    private RecyclerViewPessoasAdapter adapter;
    private List<Pessoa> pessoasList = new ArrayList<>();
    private PessoasDAO dao;

    private PessoasViewModel viewModel;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPessoas = findViewById(R.id.recyclerViewPessoas);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerViewPessoas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewPessoasAdapter(pessoasList, this);
        recyclerViewPessoas.setHasFixedSize(true);
        recyclerViewPessoas.setAdapter(adapter);

        // Fazer a inicialização do view model
        viewModel = ViewModelProviders.of(this).get(PessoasViewModel.class);

        // Buscar os dados no repository
        viewModel.buscarPessoas();

        // Adicionar os observables
        viewModel.getPessoasLiveData().observe(this, new Observer<List<Pessoa>>() {
            @Override
            public void onChanged(List<Pessoa> pessoas) {
                adapter.update(pessoas);
            }
        });

        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewModel.getErrorLiveData().observe(this, throwable -> {
            Snackbar.make(recyclerViewPessoas, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.buscarPessoas();
        });

///* Banco de Dados
/*

        // Criamos o layoutmanager para o RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //Setamos o layoutManager no recyclerView
        recyclerViewPessoas.setLayoutManager(layoutManager);

        // Inicializamos o adapter passando a lista vazia e o listener de click do item
        adapter = new RecyclerViewPessoasAdapter(pessoasList, this);

        //setamos o adapter no recyclerView
        recyclerViewPessoas.setAdapter(adapter);

        dao = Database.getDatabase(this).pessoasDAO();

        // buscar todos os item salvos na base de dados e carregar no recyclerview
        buscarTodasAsPessoas();

        //Spinner spinner = findViewById(R.id.spinner);
        //String[] listItem = new String[]{"Tairo", "Jessica", "Vinicius"};
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, listItem);
        //spinner.setAdapter(arrayAdapter);
  */
    }

    public void buscarTodasAsPessoas() {

        // Uso de thread
        /*new Thread(() -> {
            List<Contato> contatos = dao.getAll();

            runOnUiThread(() -> {
                adapter.update(contatos);
            });

        }).start();*/

        dao.getAllRxJava()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pessoas -> {
                    adapter.update(pessoas);
                }, throwable -> {
                    Log.i("TAG", "buscarTodasAsPessoas: " + throwable.getMessage());
                });
    }

    @Override
    public void onItemClick(Pessoa pessoa) {
        // ao clicar no item, deletar e remover da lista
        new Thread(() -> {
            dao.delete(pessoa);
            buscarTodasAsPessoas();
        }).start();
    }

}
