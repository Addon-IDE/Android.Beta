package com.mcyizy.addonide.setting

import org.json.JSONObject
import org.json.JSONTokener

import android.content.Context
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.tool2.FileOperation

public object SettingOperate {

    public val mFileOperation : FileOperation = FileOperation()
    
    //设置项目
    public fun <T> setValue(mContext: Context, mName: String, mValue: T) {
        val json_string : String = mFileOperation.ReadFile(SettingPath(mContext))
        val json_string2 : JSONObject = JSONObject(json_string)
        if (isJsonValid(json_string)) {
            when (mValue) {
                is String -> {
                    // 处理字符串类型
                    json_string2.put(mName,mValue.toString())
                }
                is Int -> {
                    // 处理整数类型
                    json_string2.put(mName,mValue.toInt())
                }
                is Boolean -> {
                    // 处理布尔类型
                    json_string2.put(mName, mValue?.toString()?.toBoolean() ?: false)
                }
                else -> {
                    // 其他类型的处理
                    json_string2.put(mName,mValue.toString())
                }
            }
            mFileOperation.WriteFile(SettingPath(mContext),json_string2.toString(4))
        }
    }
    
    //获取设置项目
    inline fun <reified T> getValue(mContext: Context, mName: String): T {
        val json_string: String = mFileOperation.ReadFile(SettingPath(mContext))
        val jsonObject = JSONObject(json_string)
        if (isJsonValid(json_string)) {
            return when (T::class) {
                String::class -> jsonObject.getString(mName) as T
                Int::class -> jsonObject.getInt(mName) as T
                Boolean::class -> jsonObject.getBoolean(mName) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
        throw IllegalArgumentException("JSON is not valid")
    }
    
    
    //获取设置路径
    public fun SettingPath(mContext: Context) : String {
        val mPath = Path(mContext)
        return mPath.getMainPath() + "setting.json"
    }
    
    //是否有语法错误
    public fun isJsonValid(jsonString: String): Boolean {
        return try {
            // 尝试解析 JSON
            JSONTokener(jsonString).nextValue()
            true // JSON 语法正确
        } catch (e: Exception) {
            false // JSON 语法错误
        }
    }

}