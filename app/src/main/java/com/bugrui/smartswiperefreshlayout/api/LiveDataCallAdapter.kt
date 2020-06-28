package com.bugrui.refreshapplication.api

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.JsonParseException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :

    CallAdapter<ApiResponse<R>, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<ApiResponse<R>>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<ApiResponse<R>> {

                        override fun onResponse(
                            call: Call<ApiResponse<R>>,
                            response: Response<ApiResponse<R>>
                        ) {
                            Log.e("bugrui", response.body().toString())
                            if (response.isSuccessful) {
                                if (response.body()!!.error) {
                                    postValue(
                                        ApiResponse.error(
                                            msg = response.message(),
                                            data =response.body()!!.results
                                        )
                                    )
                                }else{
                                    postValue(
                                        ApiResponse.success(
                                            msg = response.message(),
                                            data =response.body()!!.results
                                        )
                                    )
                                }


                            } else {
                                postValue(
                                    ApiResponse.error(
                                        msg = response.message(),
                                        data = null
                                    )
                                )
                            }

                        }

                        override fun onFailure(call: Call<ApiResponse<R>>, throwable: Throwable) {
                            throwable.printStackTrace()
                            postValue(
                                ApiResponse.error(
                                    msg = when (throwable) {
                                        is JsonParseException -> "服务器数据异常，请重试！"
                                        is ConnectException -> "连接不到服务器，请稍后再试！"
                                        is TimeoutException -> "您的手机网络不太畅哦！"
                                        is SocketTimeoutException -> "您的手机网络不太畅哦！"
                                        is UnknownHostException -> "无网络~"
                                        is IOException -> "连接不到服务器，请稍后再试！"
                                        else -> "请求连接失败，请重试！"
                                    }
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}

