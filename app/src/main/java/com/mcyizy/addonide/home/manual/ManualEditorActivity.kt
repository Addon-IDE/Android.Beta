package com.mcyizy.addonide.home.manual

//默认库
import com.mcyizy.addonide.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
//
import com.mcyizy.android.widget.ToolbarView
import io.github.rosemoe.sora.widget.CodeEditor
//App
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.editor.editor.Theme
import com.mcyizy.editor.editor.Typeface
import com.mcyizy.addonide.home.manual.MainMenu
import com.mcyizy.android.tool2.FileOperation

public class ManualEditorActivity : AppCompatActivity() {

    //上下文
    private lateinit var mCodeEditor : CodeEditor
    private val mFileOperation = FileOperation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_home_manual_editor)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //窗口动画
        overridePendingTransition(R.anim.list_anim_homehelp, R.anim.list_anim_homehelp)
        //代码编辑器赋值
        mCodeEditor = findViewById<CodeEditor>(R.id.code_editor)
        //加载文件
        val file_path = intent.getStringExtra("FilePath").toString()
        LoadFile(file_path)
        //设置工具栏
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("手册编辑")
        toolbar_view.setMenu(true)
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
                val mMainMenu = MainMenu(this@ManualEditorActivity)
                mMainMenu.setActivity(this@ManualEditorActivity)
                mMainMenu.setCode(mCodeEditor)
                mMainMenu.menu(view)
                mMainMenu.setPath(file_path)
            }
        })
        //加载主题
        val mTheme : Theme = Theme(mCodeEditor)
        mTheme.setContext(this)
        mTheme.Default()
        //设置字体
        val mTypeface : Typeface = Typeface(this)
        mTypeface.setCodeEditor(mCodeEditor)
        mTypeface.Default()
        //加载方法
    }
    
    //加载文件
    private fun LoadFile(path : String) {
        val path_text: String = mFileOperation.ReadFile(path)
        mCodeEditor.setText(path_text)
    }
    
    

}