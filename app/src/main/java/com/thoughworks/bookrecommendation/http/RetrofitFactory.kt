package com.thoughworks.bookrecommendation.http

import com.thoughworks.bookrecommendation.model.entity.BaseResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    val retrofit: Retrofit

    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }

        fun createService(): ApiService {
            return instance.retrofit.create(ApiService::class.java)
        }

        fun <T> Observable<BaseResponse<T>>.executeResult(observer: Observer<BaseResponse<T>>) {
            this.subscribeOn(Schedulers.io())// 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread())// UI线程处理返回接口
                .subscribe(observer)// 订阅
        }
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiService.API_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

}