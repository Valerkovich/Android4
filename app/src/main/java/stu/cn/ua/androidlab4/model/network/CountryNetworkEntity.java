package stu.cn.ua.androidlab4.model.network;

import com.google.gson.annotations.SerializedName;

    public class CountryNetworkEntity {
        private String id;
        private String name;
        private String iso2code;
        private String capitalCity;
        private IncomeLevel incomeLevel;

        public IncomeLevel getIncomeLevel() {
            return incomeLevel;
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
    }
