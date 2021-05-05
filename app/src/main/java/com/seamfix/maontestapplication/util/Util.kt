package com.seamfix.maontestapplication.util

import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.FragmentActivity

const val ID = "ID"
const val NAME = "NAME"

/*** Due to screen size, tablets can contain the required 4 rows, while mobile ***/
fun getProperColumnCount(fragmentActivity: FragmentActivity, arrayListSize: Int): Int{
    if(isDeviceTablet(fragmentActivity)){
        return 4
    }

    if(isDeviceSmall(fragmentActivity)){
        return 2 //All really small devices should only show 2 rows.
    }

    //Other devices should use 3 rows at most because of their screen may not contain many columns:
    return when (arrayListSize) {
        0 -> {
            1 //span count should at least be set to 1
        }
        in 1..2 -> {
            arrayListSize
        }
        else -> {
            3 //small devices have a maximum span of 3
        }
    }
}


/*** Return true if the android device is a Tablet ***/
fun isDeviceTablet(fragmentActivity: FragmentActivity): Boolean{
    val metrics = DisplayMetrics()
    fragmentActivity.windowManager.defaultDisplay.getMetrics(metrics)

    val yInches = metrics.heightPixels / metrics.ydpi
    val xInches = metrics.widthPixels / metrics.xdpi
    val diagonalInches =
        Math.sqrt(xInches * xInches + yInches * yInches.toDouble())
    if (diagonalInches >= 6.5) {
        // 6.5inch device or bigger
        Log.e("AppUtils", "Detected... You're using a Tablet")
        return true
    } else {
        // smaller device
        Log.e("AppUtils", "Detected... You're using a Mobile Phone")
    }
    return false
}


/**** Returns true if the android device has a very small screen ie below 5.0 inch ***/
fun isDeviceSmall(fragmentActivity: FragmentActivity): Boolean{
    val metrics = DisplayMetrics()
    fragmentActivity.windowManager.defaultDisplay.getMetrics(metrics)

    val yInches = metrics.heightPixels / metrics.ydpi
    val xInches = metrics.widthPixels / metrics.xdpi
    val diagonalInches =
        Math.sqrt(xInches * xInches + yInches * yInches.toDouble())
    if (diagonalInches < 4.8) {
        //lesser than 4.8inch device
        Log.e("AppUtils", "Detected... You're using a small device")
        return true
    } else {
        // smaller device
        Log.e("AppUtils", "Detected... You're using a Mobile Phone")
    }
    return false
}

enum class UIState{
    LOADING, NETWORK_ERROR, DATA_FOUND
}