package stu.cn.ua.androidlab4.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {CountryDbEntity.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CountryDAO getCountryDAO();
}
