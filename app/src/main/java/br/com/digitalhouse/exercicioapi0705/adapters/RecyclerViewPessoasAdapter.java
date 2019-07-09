package br.com.digitalhouse.exercicioapi0705.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.digitalhouse.exercicioapi0705.R;
import br.com.digitalhouse.exercicioapi0705.interfaces.RecyclerViewOnItemClickListener;
import br.com.digitalhouse.exercicioapi0705.model.Pessoa;

public class RecyclerViewPessoasAdapter extends RecyclerView.Adapter<RecyclerViewPessoasAdapter.ViewHolder> {

    private List<Pessoa> pessoas;
    private RecyclerViewOnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_pessoa_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Pessoa pessoa = pessoas.get(position);
        viewHolder.setConteudoNaTela(pessoa);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(pessoa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pessoas.size();
    }

    public void update(List<Pessoa> pessoasList) {
        this.pessoas = pessoasList;
        notifyDataSetChanged();
    }

    public RecyclerViewPessoasAdapter(List<Pessoa> pessoas, RecyclerViewOnItemClickListener listener) {
        this.pessoas = pessoas;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewLogin;
        private TextView textViewEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLogin = itemView.findViewById(R.id.textViewLogin);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
        }

        public void setConteudoNaTela(Pessoa pessoa) {
            textViewName.setText(pessoa.getName().getTitle() +
                    pessoa.getName().getFirst() +
                    pessoa.getName().getLast());

            textViewLogin.setText(pessoa.getLogin().getUsername());
            textViewEmail.setText(pessoa.getEmail());
        }
    }
}