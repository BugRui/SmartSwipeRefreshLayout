package com.bugrui.smartswiperefreshlayout.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.bugrui.refresh.listener.OnSmartRefreshLoadMoreListener
import com.bugrui.refreshapplication.api.APIService
import com.bugrui.request.APIRequest
import com.bugrui.smartswiperefreshlayout.R
import com.bugrui.smartswiperefreshlayout.dp
import com.bugrui.smartswiperefreshlayout.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnSmartRefreshLoadMoreListener,
    MainAdapter.OnItemClickListener {

    private val mAdapter = MainAdapter()

    private var mPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(GridItemDecoration(2, 6.dp, true))
            mAdapter.setOnItemClickListener(this@MainActivity)
            val gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
            layoutManager = gridLayoutManager
            adapter = mAdapter

        }

        fab.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        refreshLayout.setPrefetchDistance(18)
        refreshLayout.setOnRefreshLoadMoreListener(recyclerView, this)
        refreshLayout.autoRefreshing()
    }


    override fun onRefreshing() {
        getList(true)
    }

    override fun onLoadMore() {
        getList(false)
    }

    private val apiService = APIRequest.create(APIService::class.java)

    private fun getList(isRefresh: Boolean) {
        apiService.getData(if (isRefresh) 1 else mPage)
            .observe(this, Observer {
                refreshLayout.finishRefreshing()
                refreshLayout.finishLoadingMore()
                if (it.isSuccessful()) {
                    if (isRefresh) {
                        mPage = 2
                        mAdapter.setDatas(it.results)
                        if (mAdapter.mDatas.isNullOrEmpty()) {
                            refreshLayout.setNoLoadingMoreAll()
                        }
                    } else {
                        mPage++
                        mAdapter.addDatas(it.results)
                        if (it.results.isNullOrEmpty()) {
                            refreshLayout.finishLoadingMoreAll()
                        }
                    }
                } else {
                    toast(it.msg ?: "")
                }
            })

    }

    override fun onItemClick(view: ImageView, position: Int) {

    }
}
