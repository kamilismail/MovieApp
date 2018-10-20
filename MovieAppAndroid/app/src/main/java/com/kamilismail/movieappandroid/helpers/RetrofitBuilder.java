package com.kamilismail.movieappandroid.helpers;

import android.content.Context;

import com.kamilismail.movieappandroid.dictionery.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static Retrofit createRetrofit(Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(SelfSigningClientBuilder.createClient(context))
                .build();

        return retrofit;
    }
}
