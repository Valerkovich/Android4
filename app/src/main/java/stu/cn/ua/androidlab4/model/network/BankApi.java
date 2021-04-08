package stu.cn.ua.androidlab4.model.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BankApi {

    @GET("country")
    Call<ResponseBody> getCountries(@Query("format") String format, @Query("per_page") int per_page);
}
