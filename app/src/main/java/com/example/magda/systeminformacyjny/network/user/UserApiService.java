package com.example.magda.systeminformacyjny.network.user;

import com.example.magda.systeminformacyjny.network.DefaultResourceWrapper;
import com.example.magda.systeminformacyjny.network.WhereToGoService;

import io.reactivex.MaybeSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class UserApiService {

    private WhereToGoService whereToGoService;

    public UserApiService(Retrofit retrofit) {
        this.whereToGoService = retrofit.create(WhereToGoService.class);
    }

    public MaybeSource<LoginResponse.User> login(String apiKey, LoginRequest loginRequest) {
        return whereToGoService.login(apiKey, new DefaultResourceWrapper(loginRequest))
                .subscribeOn(Schedulers.io())
                .map(response -> response.getUserList().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<ResponseBody> sendNewsletter(String apiKey, NewsletterRequest newsletterRequest) {
        return null;
        //TODO dorobic wysylanie newsletter
    }
}
