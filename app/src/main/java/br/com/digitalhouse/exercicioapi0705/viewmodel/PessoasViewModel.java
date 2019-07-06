package br.com.digitalhouse.exercicioapi0705.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.digitalhouse.exercicioapi0705.model.Pessoa;
import br.com.digitalhouse.exercicioapi0705.model.PessoasResponse;
import br.com.digitalhouse.exercicioapi0705.repository.PessoasRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PessoasViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pessoa>> pessoasLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();
    private PessoasRepository repository = new PessoasRepository();

    public PessoasViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pessoa>> getPessoasLiveData() {
        return pessoasLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public void buscarPessoas() {

        /*if (AppUtil.isNetworkConnected(getApplication().getApplicationContext())){

        }*/

        disposable.add(

                Single.concat(
                        repository.obterListaPessoasDoArquivo(getApplication().getApplicationContext()),
                        repository.obterListaPessoasInternet())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loadingLiveData.setValue(true))
                        .doAfterTerminate(() -> loadingLiveData.setValue(false))
                        .subscribe(new Consumer<PessoasResponse>() {
                            @Override
                            public void accept(PessoasResponse pessoasResponse) throws Exception {
                                pessoasLiveData.setValue(pessoasResponse.getResults());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                errorLiveData.setValue(throwable);
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
