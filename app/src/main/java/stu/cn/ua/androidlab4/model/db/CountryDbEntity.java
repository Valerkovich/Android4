package stu.cn.ua.androidlab4.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import stu.cn.ua.androidlab4.model.network.CountryNetworkEntity;

@Entity(tableName = "countries")
public class CountryDbEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "capitalCity")
    private String capitalCity;

    @ColumnInfo(name = "iso2Code")
    private String iso2Code;

    @ColumnInfo(name = "incomeLevel")
    private String incomeLevel;

    public CountryDbEntity() {
    }

    public CountryDbEntity(CountryNetworkEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.capitalCity = entity.getCapitalCity();
        this.iso2Code = entity.getIso2code();
        this.incomeLevel = entity.getIncomeLevel().getValue();
    }

    public void setIncomeLevel(String incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public String getIncomeLevel() {
        return incomeLevel;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public String getIso2Code() {
        return iso2Code;
    }
}
