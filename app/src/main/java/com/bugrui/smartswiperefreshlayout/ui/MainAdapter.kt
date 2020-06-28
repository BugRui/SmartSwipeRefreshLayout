package com.bugrui.smartswiperefreshlayout.ui

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bugrui.refreshapplication.data.Gank
import com.bugrui.smartswiperefreshlayout.R
import com.bugrui.smartswiperefreshlayout.dp
import com.bumptech.glide.Glide

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var mDatas: ArrayList<Gank> = ArrayList()
    private var listener: OnItemClickListener? = null

    fun setDatas(datas: List<Gank>?) {
        mDatas.clear()
        if (datas.isNullOrEmpty()) return
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    fun addDatas(datas: List<Gank>?) {
        if (datas.isNullOrEmpty()) return
        val oldSize = mDatas.size as Int
        mDatas.addAll(datas)
        val newSize = mDatas.size as Int
        notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mDatas.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = mDatas.get(position)

        Glide.with(holder.imageView)
            .load(data.url)
            .override(holder.itemView.context.screenWidth / 3, 200.dp)
            .thumbnail(0.2F)
            .into(holder.imageView)

        with(holder.itemView) {
            setOnClickListener {
                listener?.onItemClick(holder.imageView, position)
            }
        }
    }

    /**
     * 获取屏幕宽度PX
     */
    private val Context.screenWidth: Int
        get() {
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: ImageView, position: Int)
    }

}