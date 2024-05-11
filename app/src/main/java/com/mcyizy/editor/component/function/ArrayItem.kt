package com.mcyizy.editor.component.function

import android.content.Context
import com.mcyizy.android.tool2.FileOperation

import org.json.JSONArray
import org.json.JSONObject

import com.mcyizy.editor.component.function.Util

public class ArrayItem(private val mContext: Context) {

    //App
    val mFileOperation : FileOperation = FileOperation()
    val mUtil : Util = Util()

    //获取Addons中的mjson方法
    public fun getAddonsMjsonFun(mPath : String) : ArrayList<String> {
        val array_string : String = mFileOperation.ReadResourceFile(mContext,mPath) //读取文件
        val array_string2 : String = mUtil.InterceptingText(array_string,"{","}",false)
        val lines = array_string2.lines() //转换为行
        val array = ArrayList<String>() //声明数组
        array.clear()
        //循环递归
        for (line in lines) {
            if (line.isNotBlank()) {
                if (line.contains("fun")) {
                    val functionName = line.trim().substringAfter("fun ").substringBefore("[")
                    array.add(functionName)
                }
            }
        }
        
        return array
    }
    
    //获取Addons中的清单
    public fun getAddonsManifest(mName : String) : ArrayList<String> {
        val path = "data/minecraft/addon_manifest.json"
        val array_string : String = mFileOperation.ReadResourceFile(mContext,path) //读取文件
        val array_string_json : JSONArray = JSONArray(array_string) //转换为json
        val array = ArrayList<String>() //声明数组
        array.clear()
        //循环
        var i = 0
        while (i < array_string_json.length()) {
            val json_object = array_string_json.getJSONObject(i)
            if (json_object.getString("name") == mName) {
                array.add(json_object.getString("path"))
            }
            i++
        }
        return array
    }
    
    //获取Addons中的清单中的名称
    public fun getAddonsManifestName() : ArrayList<String> {
        val path = "data/minecraft/addon_manifest.json"
        val array_string : String = mFileOperation.ReadResourceFile(mContext,path) //读取文件
        val array_string_json : JSONArray = JSONArray(array_string) //转换为json
        val array = ArrayList<String>() //声明数组
        array.clear()
        //循环
        var i = 0
        while (i < array_string_json.length()) {
            val json_object = array_string_json.getJSONObject(i)
            array.add(json_object.getString("name"))
            i++
        }
        return array
    }
    
    //获取当前文件是否声明，并且转换为数组
    public fun getAddonsFileDeclare(content : String) : ArrayList<String> {
        val array = ArrayList<String>() //声明数组
        array.clear() //清空
        val lines = content.split("\n")
        for (line in lines) {
            if (line.contains("let")) {
                array.add(line)
            }
        }
        return array
    }
    
    
    
}
