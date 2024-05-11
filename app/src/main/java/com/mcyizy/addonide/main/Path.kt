package com.mcyizy.addonide.main

import android.content.Context

public class Path(private val mContext: Context) {

    //变量
    var path : String = ""
    
    init {
        path = mContext.getExternalFilesDir(null)!!.absolutePath
    }
    
    //获取路径
    public fun getMainPath() : String {
        return path + "/"
    }
    
    //获取项目路径
    public fun getProjectPath() : String {
        return path + "/project/"
    }
    
    //获取备份路径
    public fun getBackupPath() : String {
        return path + "/backup/"
    }
    
    //获取插件路径
    public fun getPluginPath() : String {
        return path + "/plugin/"
    }
    
    //获取主题路径
    public fun getThemePath() : String {
        return path + "/theme/"
    }
    
    //获取缓存路径
    public fun getCachePath() : String {
        return path + "/cache/"
    }
    
    
}