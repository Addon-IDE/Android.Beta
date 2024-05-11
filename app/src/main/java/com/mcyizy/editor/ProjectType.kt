package com.mcyizy.editor

import android.content.Context
import org.json.JSONObject
import org.json.JSONTokener
import io.github.rosemoe.sora.widget.CodeEditor
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//App
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.editor.component.CodeEditor as AppCodeEditor

public class ProjectType(private val mContext: Context) {

    //lateinit
    private lateinit var mCodeEditor : CodeEditor
    //App
    private val mFileOperation = FileOperation()
    //变量
    private var mType = ""
    
    //设置代码
    public fun setCode(code : CodeEditor) {
        mCodeEditor = code
    }
    
    //处理
    public fun Project(project_path : String,get_type : Boolean = false) {
        val manifest = project_path + "/manifest.json"
        if (mFileOperation.Exists(manifest)) {
            val json_string = mFileOperation.ReadFile(manifest)
            if (isJsonValid(json_string)) {
                val json = JSONObject(json_string)
                if (json.has("type")) {
                    val type_name = json.getString("type")
                    mType = type_name
                    if (!get_type) {
                       Project_Type(type_name)                  
                    }
                } else {
                    Toasty.error(mContext, "模块加载失败，在清单文件中未找到type！", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.error(mContext, "模块加载失败，清单文件异常！", Toast.LENGTH_SHORT, true).show()
            }
        } else {
            Toasty.error(mContext, "模块加载失败，清单文件不存在！", Toast.LENGTH_SHORT, true).show()
        }
    }
    public fun getType() : String {
        return mType
    }
    private fun Project_Type(type : String) {
        when (type) {
            "default" -> Default()
            else -> {}
        }
    }
    
    //默认加载
    private fun Default() {
        //编辑器
        val mAppCodeEditor = AppCodeEditor(mContext)
        mAppCodeEditor.setCode(mCodeEditor)
        mAppCodeEditor.Editor()
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
