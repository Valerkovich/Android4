package stu.cn.ua.androidlab4.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CountryDAO {

    @Query("SELECT * FROM countries ORDER BY id COLLATE NOCASE")
    List<CountryDbEntity> getShips() ;

    @Query("SELECT * FROM countries WHERE name COLLATE UTF8_GENERAL_CI LIKE :name")
    List<CountryDbEntity> getByName(String name);

    @Query("SELECT * FROM countries WHERE id = :id")
    CountryDbEntity getById(String id);

    @Insert
    void insertShips(List<CountryDbEntity> repositories);

    @Query("DELETE FROM countries")
    void deleteShips();

    @Transaction
    default void updateShips(List<CountryDbEntity> entities){
        deleteShips();
        insertShips(entities);
    }
}
