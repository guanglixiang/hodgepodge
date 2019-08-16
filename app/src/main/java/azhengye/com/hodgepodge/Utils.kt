package azhengye.com.hodgepodge

import android.app.Activity
import android.util.DisplayMetrics

/**
 * Created by azhengye on 2019-08-19.
 */
object Utils {

    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels;
    }
}