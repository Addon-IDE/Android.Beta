package com.mcyizy.editor2

//基础库
import com.mcyizy.addonide.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.res.Configuration
//widget
import android.widget.EditText
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mcyizy.android.view.pager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
//Addon
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.ViewComponent
//活动
import android.content.Intent
import com.mcyizy.editor2.ProjectActivity

public class MainActivity : AppCompatActivity() {

    //App
    private val mViewComponent = ViewComponent()
    
    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_editor2_main)
        //设置状态栏
        ApplicationWindow.StatusBar(this,1)
        
        //加载方法
        MianBox()
        Tabs()
        Pager()
    }
    
    //主页工具栏
    private fun MianBox() {
        //帮助
        val main_help = findViewById<LinearLayout>(R.id.main_help)
        main_help.setOnClickListener {
        
        }
        //退出
        val main_signout = findViewById<LinearLayout>(R.id.main_signout)
        main_signout.setOnClickListener {
        
        }
    }
    
    // 标签栏
    private fun Tabs() {
        tabLayout = findViewById<TabLayout>(R.id.shortcut_list)
        val list : Array<String> = arrayOf("基础信息","创建项目","选项构建")
        for (item in list) {
            val tab = tabLayout.newTab()
            tab.text = item.toString()
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
        
            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 处理标签取消选定事件
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                
            }
        })
    }
    
    // 加载分页
    private fun Pager() {
        // 初始化
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        val inflate1 = LayoutInflater.from(this).inflate(R.layout.activity_editor2_main_a, null)
        val inflate2 = LayoutInflater.from(this).inflate(R.layout.activity_editor2_main_b, null)
        val inflate3 = LayoutInflater.from(this).inflate(R.layout.activity_editor2_main_c, null)
        
        
        // 加入列表
        val viewList: MutableList<View> = ArrayList()
        viewList.add(inflate1)
        viewList.add(inflate2)
        viewList.add(inflate3)
        
        // 添加到适配器中
        val adapter = ViewPagerAdapter(viewList) // ViewPagerAdapter 类需要创建
        viewPager.adapter = adapter
        
        // 获取要修改的布局
        val layout1 = viewList[0]
        val layout2 = viewList[1]
        ALayout(layout1)
        BButton(layout2)
        
        // 在 ViewPager 页面切换时更新 TabLayout
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // 什么都不做
            }
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
            override fun onPageScrollStateChanged(state: Int) {
                // 什么都不做
            }
        })
    }
    
    //基础信息
    private fun ALayout(view : View) {
        //给编辑框设置边框
        val editor_name = view.findViewById<EditText>(R.id.editor_name)
        val editor_author = view.findViewById<EditText>(R.id.editor_author)
        val editor_version = view.findViewById<EditText>(R.id.editor_version)
        val editor_describe = view.findViewById<EditText>(R.id.editor_describe)
        val editor_other = view.findViewById<EditText>(R.id.editor_other)
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            mViewComponent.setRoundedBackgroundWithBorder(editor_name,-523,-2174036,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_author,-523,-2174036,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_version,-523,-2174036,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_describe,-523,-2174036,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_other,-523,-2174036,5,3f,3f,3f,3f)
        } else {
            mViewComponent.setRoundedBackgroundWithBorder(editor_name,-11316397,-9013387,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_author,-11316397,-9013387,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_version,-11316397,-9013387,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_describe,-11316397,-9013387,5,3f,3f,3f,3f)
            mViewComponent.setRoundedBackgroundWithBorder(editor_other,-11316397,-9013387,5,3f,3f,3f,3f)
        }
        
        // 背景   边框
        
    }
    
    //主页功能按钮监听
    private fun BButton(view : View) {
        //项目
        val project_button = view.findViewById<LinearLayout>(R.id.project_button)
        project_button.setOnClickListener{
            val intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent) // 启动目标窗口
        }
    }
    
    
}