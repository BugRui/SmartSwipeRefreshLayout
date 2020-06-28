package com.bugrui.refreshapplication.data

/**
 * @Author:            BugRui
 * @CreateDate:        2020/2/12 15:40
 * @Description:       java类作用描述
 */
data class Gank(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)