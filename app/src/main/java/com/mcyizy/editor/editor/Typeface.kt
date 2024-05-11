package com.mcyizy.editor.editor

//android
import android.content.Context
import android.graphics.Typeface
import com.mcyizy.addonide.setting.SettingOperate
//Json
import org.json.JSONObject
import org.json.JSONArray
//主
import io.github.rosemoe.sora.widget.CodeEditor
//App
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.tool2.FileOperation

public class Typeface(private val mContext: Context) {

    //CodeEditor
    private lateinit var mCodeEditor: CodeEditor
    
    val mFileOperation : FileOperation = FileOperation()
    
    //设置CodeEditor
    public fun setCodeEditor(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    
    //设置字体
    public fun Default() {
        if (SettingOperate.getValue(mContext,"CustomThemeEditor")) {
            //初始化
            val mPath = Path(mContext)
            val CustomThemeNameString: String = SettingOperate.getValue(mContext,"CustomThemeEditorName")
            val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
            val path_list_path_manifest = mPath.getThemePath() + CustomThemeNameString + "/manifest.json"
            //加载主题清单
            val json_string : String = mFileOperation.ReadFile(path_list_path_manifest)
            val json_string2 : JSONObject = JSONObject(json_string)
            if (json_string2.has("font")) {
                val path_list_path2 = path_list_path + json_string2.getString("font")
                setFont2(path_list_path2)
            }
        } else {
            val mFontEditor : Int = SettingOperate.getValue(mContext,"FontEditor")
            val font_name : String = getStringName(mFontEditor)
            if (font_name != "") {
                setFont(font_name)
            }
        }
    }
    
    //设置字体
    private fun setFont(name : String) {
        val typeface = Typeface.createFromAsset(mContext.assets, name)
        mCodeEditor.setTypefaceText(typeface)
        mCodeEditor.setTypefaceLineNumber(typeface)
    }
    
    //设置字体2
    private fun setFont2(path : String) {
        if (mFileOperation.Exists(path)) {
            val typeface = Typeface.createFromFile(path)
            mCodeEditor.setTypefaceText(typeface)
            mCodeEditor.setTypefaceLineNumber(typeface)
        }
    }
    
    //获取字体路径
    private fun getStringName(index : Int) : String {
        return when(index) {
            0 -> ""
            1 -> "editor/fonts/JetBrainsMono-Regular.ttf"
            2 -> "editor/fonts/Consolas.ttf"
            3 -> "editor/fonts/Roboto-Regular.ttf"
            else -> ""
        }
    }
    
}
