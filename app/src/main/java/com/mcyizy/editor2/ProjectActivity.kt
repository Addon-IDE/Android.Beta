package com.mcyizy.editor2

//基础库
import com.mcyizy.addonide.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
//widget
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
//
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.editor2.project.ProjectList
import com.mcyizy.editor2.project.CreateMenu

public class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_editor2_project)
        //设置状态栏
        ApplicationWindow.StatusBar(this,1)
        //加载方法
        Start()
        Tabs()
    } 
    
    public fun Start() {
        //获取对象
        val list = findViewById<ListView>(R.id.list_view)
        
        val button_up = findViewById<LinearLayout>(R.id.up_button)
        
        val button_add = findViewById<LinearLayout>(R.id.add_button)
        
        val search_editor = findViewById<EditText>(R.id.editor_search)
        
        //初始化
        val mProjectList = ProjectList(this,list)
        //手册
        mProjectList.setEditText(search_editor)
        mProjectList.Conversion("")
        mProjectList.Button(button_up)
        mProjectList.List()
        
        //监听器
        button_add.setOnClickListener {
            val mCreateMenu = CreateMenu(this)
            mCreateMenu.setPath(mProjectList.getFolderPath())
            mCreateMenu.setList(mProjectList)
            mCreateMenu.Start()
        }
    } 
    
    // 标签栏
    private fun Tabs() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_list)
        val list : Array<String> = arrayOf("代码","资源")
        for (item in list) {
            val tab = tabLayout.newTab()
            tab.text = item.toString()
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                
            }
        
            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 处理标签取消选定事件
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                
            }
        })
    }  
    
}