package com.bugrui.smartswiperefreshlayout

import android.app.Application
import com.bugrui.refreshapplication.api.APIService
import com.bugrui.refreshapplication.api.LiveDataCallAdapterFactory
import com.bugrui.request.APIRequest
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        sApp = this
        APIRequest.init(this, APIService.BASE_URL) {
            okHttp {
                cache(Cache(sApp.cacheDir, 10 * 1024 * 1024L))
                //日志
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            retrofit {
                addConverterFactory(GsonConverterFactory.create(Gson()))
                addCallAdapterFactory(LiveDataCallAdapterFactory())
            }
        }
    }

    companion object {
        @JvmStatic
        lateinit var sApp: App
            private set
    }
}