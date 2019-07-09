package br.com.digitalhouse.exercicioapi0705.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.digitalhouse.exercicioapi0705.model.Pessoa;
import io.reactivex.Flowable;

@Dao
public interface PessoasDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pessoa pessoa);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Pessoa> pessoas);

    @Update
    void update(Pessoa pessoa);

    @Delete
    void delete(Pessoa pessoa);

    @Query("SELECT * FROM tab_pessoas limit 30")
    List<Pessoa> getAll();

    @Query("SELECT * FROM tab_pessoas limit 30")
    Flowable<List<Pessoa>> getAllRxJava();

    //@Query("SELECT * FROM tab_pessoas WHERE idBanco = :id")
    //Pessoa getPessoaById(long id);

    //@Query("SELECT * FROM tab_pessoas WHERE idBanco = :id")
    //Flowable<Pessoa> getPessoaByIdRxJava(long id);

}
