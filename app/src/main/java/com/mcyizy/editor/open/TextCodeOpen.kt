package com.mcyizy.editor.open

//主
import android.app.Activity
import android.content.Context
//文件模块
import com.mcyizy.android.tool2.FileOperation
//Editor
import com.mcyizy.editor.editor.Theme
import com.mcyizy.editor.editor.Typeface
import com.mcyizy.editor.editor.Language
import com.mcyizy.editor.editor.Setting
//代码编辑器
import io.github.rosemoe.sora.widget.CodeEditor

public class TextCodeOpen {

    //文件模块
    private var mFileOperation: FileOperation = FileOperation()
    //全局
    private lateinit var mContext: Context
    private lateinit var mCodeEditor: CodeEditor
    
    //Context
    public fun setContext(context_value: Context) {
        mContext = context_value
    }
    //设置编辑框
    public fun setCode(code_editor: CodeEditor) {
        mCodeEditor = code_editor
    }
    
    //打开
    public fun Open(Path : String) {
        //打开文件
        val pathText: String = mFileOperation.ReadFile(Path)
        try {
            mCodeEditor.setText(pathText)
        } catch (e: Exception) {
            e.printStackTrace() 
        }
        
    }
    
    //类型
    public fun Type(type : Int) {
        //设置主题
        val mTheme : Theme = Theme(mCodeEditor)
        mTheme.setContext(mContext)
        mTheme.Default()
        //设置字体
        val mTypeface : Typeface = Typeface(mContext)
        mTypeface.setCodeEditor(mCodeEditor)
        mTypeface.Default()
        //打开语言
        val mLanguage : Language = Language(mCodeEditor)
        mLanguage.Set(type)
        //设置
        //设置字体
        val mSetting : Setting = Setting(mContext)
        mSetting.setCodeEditor(mCodeEditor)
        mSetting.RoutineSetting()
    }
    
    
}
