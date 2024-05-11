package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.View
//widget
import android.widget.EditText
import android.widget.GridView
import com.mcyizy.android.widget.ToolbarView
//
import android.text.TextWatcher
import android.text.Editable
//App
import com.mcyizy.addonide.main.ProjectList
import com.mcyizy.android.foundation.ApplicationWindow

public class HomeSearchActivity : AppCompatActivity() {

    //App
    private var mProjectList : ProjectList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_home_search)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("搜索项目")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //加载项目列表
        val list_view: GridView = findViewById<GridView>(R.id.list_view)
        mProjectList = ProjectList(this,list_view)
        mProjectList!!.Init()
        //加载搜索编辑框监听器
        EditListener()
    }  
    
    //编辑框监听器
    private fun EditListener() {
        val search_editor = findViewById<EditText>(R.id.search_editor)
        search_editor.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本变化之前执行的操作
            }
        
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 当文本发生变化时执行的操作
                mProjectList!!.Search(search_editor.getText().toString())
            }
        
            override fun afterTextChanged(s: Editable?) {
                // 在文本变化之后执行的操作
            }
        })
    }
    
}