package br.com.digitalhouse.exercicioapi0705.data.network;

import br.com.digitalhouse.exercicioapi0705.model.PessoasResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("api/?results=10")
    Single<PessoasResponse> getListaPessoas();

    @GET("api/?seed={seed}")
    Single<PessoasResponse> getPessoa();

}
