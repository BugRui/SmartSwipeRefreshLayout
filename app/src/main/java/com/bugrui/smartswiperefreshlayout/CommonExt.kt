package com.bugrui.smartswiperefreshlayout

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast

/**
 * 转dp
 */
val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * 获取屏幕宽度PX
 */
val Context.screenWidth: Int
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

/**
 * 吐司
 */
fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

