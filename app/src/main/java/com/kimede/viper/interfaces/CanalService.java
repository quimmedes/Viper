package com.kimede.viper.interfaces;

import com.kimede.viper.Models.Canal;
import com.kimede.viper.Models.Categoria;
import com.kimede.viper.Models.Configure;
import com.kimede.viper.Models.Link;
import com.kimede.viper.Models.Odata;
import com.kimede.viper.Models.Videos;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CanalService {
    @GET("api/categoria")
    Call<List<Categoria>> GetCategoria();

    @GET("api/configures")
    Call<List<Configure>> GetConfigure();

    @GET("api/animes/lancamento")
    Call<List<Canal>> GetLancamento();

    @GET("odata/Animesdb")
    Call<Odata> GetOdataAlphabet(@Query("$filter") String str, @Query("$select") String str2, @Query("$orderby") String str3, @Query("$skip") String str4, @Query("$inlinecount") String str5);

    @GET("odata/Animesdb")
    Call<Odata> GetOdataAnime(@Query("$filter") String str);

    @GET("api/animes/recentes")
    Call<List<Canal>> GetRecentes();

    @GET("api/episodioexes/{letra}")
    Call<ArrayList<Videos>> GetVideos(@Path("letra") String str);

    @GET("api/episodioexes/links")
    Call<ArrayList<Link>> GetLinksEpi(@Query("id") String str);
}
