package com.mcyizy.editor.component.function

import android.content.Context
import com.mcyizy.android.tool2.FileOperation

import com.mcyizy.editor.component.function.Util

public class ContrastTable(private val mContext: Context) {

    //App
    val mFileOperation : FileOperation = FileOperation()
    val mUtil : Util = Util()
    
    //获取描述
    public fun getDescribe(mPath : String,mName: String) : String{
        //获取内容
        val array_string : String = mFileOperation.ReadResourceFile(mContext,mPath) //读取文件
        val array_string2 : String = mUtil.InterceptingText(array_string,"[tip]","[endtip]",false)
        
        val lines = array_string2.lines()
        for (line in lines) {
            val parts = line.split("=")
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim()
                if (key == mName) {
                    return value
                }
            }
        }
        return "没有描述"
    }

}
