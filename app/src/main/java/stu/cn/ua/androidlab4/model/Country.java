package stu.cn.ua.androidlab4.model;

import stu.cn.ua.androidlab4.model.db.CountryDbEntity;

public class Country {

    private final String id;
    private final String name;
    private final String capitalCity;
    private final String iso2code;
    private final String incomeLevel;

    public Country(String id, String name, String capitalCity, String iso2code, String incomeLevel){
        this.id = id;
        this.name = name;
        this.capitalCity = capitalCity;
        this.iso2code = iso2code;
        this.incomeLevel = incomeLevel;
    }

    public Country(CountryDbEntity entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getCapitalCity(),
                entity.getIso2Code(),
                entity.getIncomeLevel());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIso2code() {
        return iso2code;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public String getIncomeLevel() {
        return incomeLevel;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "\n"
                + "capital city: " + this.capitalCity + "\n"
                + "iso2code: " + this.iso2code + "\n"
                + "income level: " + this.incomeLevel;
    }
}
