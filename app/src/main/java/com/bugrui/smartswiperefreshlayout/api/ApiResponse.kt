package com.bugrui.refreshapplication.api

import java.io.Serializable


/**
 * @Author:            BugRui
 * @CreateDate:        2020/2/17 10:33
 * @Description:       Api资源
 */
data class ApiResponse<T : Any?>(
    val error: Boolean,
    val results: T?,
    val msg: String? = null
) {

    /**
     * 请求成功
     */
    fun isSuccessful(): Boolean {
        return !error
    }

    companion object {

        fun <T> success(msg: String? = null, data: T?): ApiResponse<T> {
            return ApiResponse(error = false, results = data, msg = msg)
        }

        fun <T> error(msg: String?, data: T? = null): ApiResponse<T> {
            return ApiResponse(error = true,results = data, msg = msg)
        }
    }
}


