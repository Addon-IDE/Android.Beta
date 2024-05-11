package com.mcyizy.android.foundation

import android.os.Build
import android.graphics.Color
import android.content.Context
import android.view.Window
import android.app.Activity
import android.view.View
import android.content.res.Configuration

object ApplicationWindow {

    //设置状态栏颜色
    fun StatusBar(mContext: Context, type: Int) {
        val currentTheme = mContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            // 当前是非夜间模式，将夜间模式主题应用到整个应用程序
            if (type == 0) {
                StatusBarColor(mContext,"#FFFFFF")
                StatusBarFontBlack(mContext,true)
            } else {
                StatusBarColor(mContext,"#FAFAFA")
                StatusBarFontBlack(mContext,true)
            }
        } else {
            // 当前是夜间模式，保持夜间模式主题应用到整个应用程序
            if (type == 0) {
                StatusBarColor(mContext,"#333333")
                StatusBarFontBlack(mContext,false)
            } else {
                StatusBarColor(mContext,"#222222")
                StatusBarFontBlack(mContext,false)
            }
        }
    }

    //设置状态栏颜色
    fun StatusBarColor(mContext: Context, color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = (mContext as Activity).window
            window.statusBarColor = Color.parseColor(color)
        }
    }
    
    //设置状态栏字体颜色是否为黑色
    fun StatusBarFontBlack(mContext: Context, isDarkColor: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = (mContext as Activity).window
            if (isDarkColor) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }
}