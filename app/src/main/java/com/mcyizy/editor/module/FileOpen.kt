package com.mcyizy.editor.module

//主
import android.content.Context
//代码编辑器
import io.github.rosemoe.sora.widget.CodeEditor
//文件模块
import com.mcyizy.android.tool2.FileOperation
//打开模块
import com.mcyizy.editor.open.TextCodeOpen

public class FileOpen {

    //文件模块
    private var mFileOperation: FileOperation = FileOperation()
    //全局
    private lateinit var mContext: Context
    private lateinit var mCodeEditor: CodeEditor
    //路径
    private var mPath: String = ""
    //打开模块
    private var mTextCodeOpen :TextCodeOpen = TextCodeOpen()
    
    //Context
    public fun setContext(context_value: Context) {
        mContext = context_value
        
        //
        mTextCodeOpen.setContext(mContext)
    }
    
    //设置路径
    public fun setPath(path_value: String) {
        mPath = path_value
    }
    
    //设置编辑框
    public fun setCode(code_editor: CodeEditor) {
        mCodeEditor = code_editor
        
        //
        mTextCodeOpen.setCode(mCodeEditor)
    }
    
    //打开
    public fun Open() {
        //文件格式转换
        var format: String = mFileOperation.getFileSuffixName(mPath)
        var format2: String = format.lowercase()
        //判断
        if (format2 == "jpg" || format2 == "png") {
            
        } else if (format2 == "mp3" || format2 == "ogg") {
            
        } else {
            TextOpen()
        }
    }
    
    //打开文本文件
    private fun TextOpen() {
        //文件格式转换
        var format: String = mFileOperation.getFileSuffixName(mPath)
        var format2: String = format.lowercase()
        //判断
        if (format2 == "java") {
            mTextCodeOpen.Open(mPath)
            mTextCodeOpen.Type(1)
        } else if (format2 == "json") {
            mTextCodeOpen.Open(mPath)
            mTextCodeOpen.Type(2)
        } else {
            mCodeEditor.setEditorLanguage(null)
            mTextCodeOpen.Open(mPath)
            mTextCodeOpen.Type(0)
        }
    }
    
}
