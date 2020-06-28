package com.bugrui.refreshapplication.api

import androidx.lifecycle.LiveData
import com.bugrui.refreshapplication.data.Gank
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author:            BugRui
 * @CreateDate:        2020/2/12 15:39
 * @Description:       java类作用描述
 */
interface APIService {
    companion object {
        const val BASE_URL = "http://gank.io/"
    }

    @GET("api/data/福利/40/{page}")
    fun getData(@Path("page") page: Int): LiveData<ApiResponse<List<Gank>?>>
}