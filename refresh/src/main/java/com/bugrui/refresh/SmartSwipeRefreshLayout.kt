package com.bugrui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bugrui.refresh.listener.OnRefreshingListener
import com.bugrui.refresh.listener.OnSmartLoadMoreListener
import com.bugrui.refresh.listener.OnSmartRefreshLoadMoreListener

/**
 * @Author: BugRui
 * @CreateDate: 2020/6/5 9:51
 * @Description: 智能刷新布局
 */
class SmartSwipeRefreshLayout : SwipeRefreshLayout {


    private var mLayoutManager: RecyclerView.LayoutManager? = null


    /**
     * 加载完成全部
     */
    private var mLoadingMoreCompleteAll = false

    /**
     * 是否正在加载
     */
    private var mLoadingMore = false

    /**
     * 表示控件移动的最小距离，手移动的距离大于这个距离才能拖动控件
     */
    private var mScaledTouchSlop: Int = 0

    /**
     * 预取距离,默认为5
     */
    private var prefetchDistance = 5


    /**
     * 下拉刷新监听
     */
    private var refreshingListener: OnRefreshingListener? = null

    /**
     * 智能加载监听,滑动到预取距离时回调
     */
    private var loadMoreListener: OnSmartLoadMoreListener? = null

    /**
     * 下拉刷新和智能加载监听
     */
    private var refreshLoadMoreListener: OnSmartRefreshLoadMoreListener? = null

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setColorSchemeColors(
            getColor(android.R.color.holo_red_light),
            getColor(android.R.color.holo_orange_dark),
            getColor(android.R.color.holo_green_light)
        )
        mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        setOnRefreshListener { onRefreshing() }
    }

    private fun getColor(color: Int): Int = ContextCompat.getColor(context, color)

    private var mDownY: Float = 0F
    private var mMoveY: Float = 0F


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 移动的起点
                mDownY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                mMoveY = ev.y
                // 移动过程中判断时候能下拉加载更多
                if (canLoadMore()) {
                    onLoadingMore()
                }
            }
        }
        return super.dispatchTouchEvent(ev)

    }

    /**
     * 是否是上拉
     */
    private fun isPullUp(): Boolean = (mDownY - mMoveY) >= mScaledTouchSlop

    /**
     * 是否可以加载
     */
    private fun canLoadMore(): Boolean {

        //布局管理器为空时，不能加载
        if (mLayoutManager == null) {
            return false
        }

        //已经加载完成
        if (mLoadingMoreCompleteAll) {
            return false
        }

        //正在刷新和加载中不能进行加载
        if (isRefreshing || mLoadingMore) {
            return false
        }

        if (!isPullUp()) {
            return false
        }

        if (mLayoutManager is LinearLayoutManager) {
            val layoutManager = mLayoutManager as LinearLayoutManager
            if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - prefetchDistance) {
                return true
            }
        }

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val layoutManager = mLayoutManager as StaggeredGridLayoutManager
            val lastPosition =
                findMax(layoutManager.findLastVisibleItemPositions(IntArray(layoutManager.spanCount)))
            if (lastPosition >= layoutManager.itemCount - prefetchDistance) {
                return true
            }
        }

        if (mLayoutManager is GridLayoutManager) {
            val layoutManager = mLayoutManager as GridLayoutManager
            if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - prefetchDistance) {
                return true
            }
        }

        return false
    }

    /**
     * StaggeredGridLayoutManager 获取最后的位置
     *
     * 因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
     * 得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
     *
     */
    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) max = value
        }
        return max
    }


    private fun onRefreshing() {
        mLoadingMore = false
        refreshingListener?.onRefreshing()
        refreshLoadMoreListener?.onRefreshing()
    }


    private fun onLoadingMore() {
        mLoadingMore = true
        mLoadingMore = true
        loadMoreListener?.onLoadMore()
        refreshLoadMoreListener?.onLoadMore()
    }


    /**
     * 自动刷新
     */
    fun autoRefreshing() {
        isRefreshing = true
        onRefreshing()
    }

    /**
     * 加载刷新完成
     */
    fun finishRefreshing() {
        isRefreshing = false
        mLoadingMoreCompleteAll = false
    }

    /**
     * 加载更多完成
     */
    fun finishLoadingMore() {
        mLoadingMore = false
    }

    /**
     * 加载完成全部，不会继续加载,下拉刷新会重置
     */
    fun finishLoadingMoreAll() {
        mLoadingMoreCompleteAll = true
    }

    /**
     * 重置加载完成全部
     */
    fun setNoLoadingMoreAll() {
        mLoadingMoreCompleteAll = false
    }

    /**
     * 设置预取距离
     */
    fun setPrefetchDistance(prefetch: Int) {
        this.prefetchDistance = prefetch
    }

    /**
     * 设置下拉刷新监听
     */
    fun setOnRefreshingListener(listener: OnRefreshingListener) {
        this.refreshingListener = listener
    }

    /**
     * 设置智能加载监听
     */
    fun setOnLoadMoreListener(
        recyclerView: RecyclerView,
        listener: OnSmartLoadMoreListener
    ) {
        this.mLayoutManager = recyclerView.layoutManager
        this.loadMoreListener = listener
    }


    /**
     * 设置下拉刷新和智能加载监听
     */
    fun setOnRefreshLoadMoreListener(
        recyclerView: RecyclerView,
        listener: OnSmartRefreshLoadMoreListener
    ) {
        this.mLayoutManager = recyclerView.layoutManager
        this.refreshLoadMoreListener = listener
    }
}


