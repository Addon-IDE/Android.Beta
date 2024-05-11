package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
//部件
import android.view.View
import android.widget.GridView
import android.widget.LinearLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
//活动
import android.app.Activity
import android.app.ActivityOptions
import com.mcyizy.addonide.CreateActivity
import com.mcyizy.addonide.PluginActivity
import com.mcyizy.addonide.SettingActivity
import com.mcyizy.addonide.HomeSearchActivity
import com.mcyizy.addonide.home.manual.MainActivity as ManualMainActivity
import com.mcyizy.addonide.home.audiovisualize.AudioVisualizeActivity
//App
import com.mcyizy.addonide.main.Init
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.main.ProjectList

import com.mcyizy.addonide.home.unity.UnityActivity

public class HomeActivity : AppCompatActivity() {

    //App
    private var mAppInit : Init = Init()
    private var mProjectList : ProjectList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_home)
        //设置状态栏
        ApplicationWindow.StatusBar(this,1)
        //初始化文件
        mAppInit.init(this)
        //加载项目列表
        val list_view: GridView = findViewById<GridView>(R.id.list_view)
        mProjectList = ProjectList(this,list_view)
        mProjectList!!.Init()
        
        //
        Listener()
        BottomAppBar()
        
    } 
    
    //监听器
    private fun Listener() {
        //创建项目
        val bottomfab = findViewById<FloatingActionButton>(R.id.bottomfab)
        bottomfab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            val transitionName: String? = "transitions_create"
            val sharedElement: View = bottomfab
            val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
            startActivity(intent, options.toBundle())
        }
        //搜索项目
        val search_layout = findViewById<LinearLayout>(R.id.search_layout)
        search_layout.setOnClickListener {
            val intent = Intent(this, HomeSearchActivity::class.java)
            val transitionName: String? = "transitions_homesearch"
            val sharedElement: View = search_layout
            val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
            startActivity(intent, options.toBundle())
        }
    }
    
    //下方工具栏
    private fun BottomAppBar() {
        //
        val bottomAppBar: BottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // 主页
                    val intent = Intent(this, UnityActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item2 -> {
                    // 插件
                    val intent = Intent(this, ManualMainActivity::class.java)
                    val transitionName: String? = "transitions_help"
                    val sharedElement: View = bottomAppBar
                    val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
                    startActivity(intent, options.toBundle())
                    true
                }
                R.id.menu_item3 -> {
                    // 插件
                    val intent = Intent(this, PluginActivity::class.java)
                    val transitionName: String? = "transitions_plugin"
                    val sharedElement: View = bottomAppBar
                    val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
                    startActivity(intent, options.toBundle())
                    true
                }
                R.id.menu_item4 -> {
                    // 设置
                    val intent = Intent(this, SettingActivity::class.java)
                    val transitionName: String? = "transitions_setting"
                    val sharedElement: View = bottomAppBar
                    val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
                    startActivity(intent, options.toBundle())
                    true
                }
                // 添加更多菜单项的处理
                else -> false
            }
        }
    }
    
    //被重新启动
    override fun onStart() {
        super.onStart()
        //刷新列表，加一个判断用于防止动画异常
        var mmFileOperation = FileOperation()
        val cacheDir = applicationContext.externalCacheDir?.absolutePath
        if (mmFileOperation.Exists(cacheDir + "/RefreshProjectList")) {
            mProjectList!!.Refresh()
            mmFileOperation.deleteFile(cacheDir + "/RefreshProjectList")
        }
    }
    
    //返回键被按下
    override fun onBackPressed() {
        //该判断用于防止切换主题后，按返回键会再一次回到主活动
        if (isTaskRoot) {
            finishAffinity() // 关闭所有活动，并结束应用程序
        } else {
            finishAffinity() // 否则执行默认的返回逻辑
        }
    }
    
}
