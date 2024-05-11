package com.mcyizy.addonide.main

import android.content.Context
import com.mcyizy.android.tool2.FileOperation

public class DataStorage(private val mContext: Context) {

    //声明
    val mFileOperation = FileOperation()
    val mPath = "/data/user/0/com.mcyizy.addonide/files/data/"
    
    //初始化
    init {
        if (!mFileOperation.Exists(mPath)) {
            mFileOperation.CreateDirectory(mPath)
        }
    }
    
    //读取数据
    public fun Read(Name : String) : String {
        val path = mPath + Name + "d"
        if (mFileOperation.Exists(path)) {
            return mFileOperation.ReadFile(path)
        }
        return ""
    }
    
    //写入数据
    public fun Write(Name : String,Value : String) {
        val path = mPath + Name + "d"
        mFileOperation.WriteFile(path,Value)
    }
    
    //删除数据
    public fun Delete(Name : String) : Boolean {
        val path = mPath + Name + "d"
        return mFileOperation.deleteFile(path)
    }
    
    //数据是否存在
    public fun Exists(Name : String) : Boolean {
        val path = mPath + Name + "d"
        return mFileOperation.Exists(path)
    }
    
}
