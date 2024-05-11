package com.mcyizy.editor.editor

//android
import android.content.Context
//Json
import org.json.JSONObject
import org.json.JSONArray
//code
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
//app
import com.mcyizy.addonide.main.Path
import com.mcyizy.addonide.setting.SettingOperate
import com.mcyizy.android.tool.ColorOperation
import com.mcyizy.android.tool2.FileOperation

public class ColorScheme(val mContext : Context) {
    
    val mFileOperation : FileOperation = FileOperation()
    
    //CodeEditor
    private lateinit var mCodeEditor: CodeEditor
    
    //设置CodeEditor
    public fun setCodeEditor(codeeditor : CodeEditor) {
        mCodeEditor = codeeditor
    }
    
    //设置主题
    public fun setScheme() {
        //待会需要调用的
        val mPath = Path(mContext)
        val CustomThemeNameString: String = SettingOperate.getValue(mContext,"CustomThemeEditorName")
        val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
        val path_list_path_manifest = mPath.getThemePath() + CustomThemeNameString + "/manifest.json"
        //加载主题清单
        val json_string : String = mFileOperation.ReadFile(path_list_path_manifest)
        val json_string2 : JSONObject = JSONObject(json_string)
        //判断是否为夜间模式
        if (SettingOperate.getValue(mContext,"NightMode")) {
            //夜间主题
            if (json_string2.has("theme_night")) {
                val path_list_path2 = path_list_path + json_string2.getString("theme_night")
                setColorScheme(path_list_path2)
            }
        } else {
            //日间主题
            if (json_string2.has("theme")) {
                val path_list_path2 = path_list_path + json_string2.getString("theme")
                setColorScheme(path_list_path2)
            }
        }
    }
    
    //
    private fun setColorScheme(path_list_path : String) {
        //判断文件是否存在
        if (mFileOperation.Exists(path_list_path)) {
            //读取
            val jsonString = mFileOperation.ReadFile(path_list_path)
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()
            //批量设置颜色
            val mEditorColorScheme : EditorColorScheme = EditorColorScheme()
            while (keys.hasNext()) {
                val key = keys.next() // 获取键名
                val value = jsonObject.getString(key) // 获取对应的值
                val color_value = ColorOperation.getColorInt(value)
                //colorScheme应用到CodeEditor
                mEditorColorScheme.setColor(getType(key),color_value)
            }
            mCodeEditor.colorScheme = mEditorColorScheme
        }
    }
    
     //获取颜色类型
     private fun getType(name : String) : Int {
        val jsonString = mFileOperation.ReadResourceFile(mContext,"editor/Theme_Type.json")
        val jsonObject = JSONObject(jsonString)
        val value = jsonObject.getString(name)
        return value.toInt()
     }
}
