
package br.com.digitalhouse.exercicioapi0705.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PessoasResponse {

    @Expose
    private Info info;

    @Expose
    @SerializedName("results")
    private List<Pessoa> pessoas;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Pessoa> getResults() {
        return pessoas;
    }

    public void setResults(List<Pessoa> results) {
        this.pessoas = results;
    }

}
