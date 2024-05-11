package com.mcyizy.addonide

//基础库
import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.app.Activity
//widget
import com.mcyizy.android.widget.ToolbarView
import android.widget.GridView
import com.google.android.material.button.MaterialButton
//App
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.addonide.main.ProjectTypeList

public class CreateTypeActivity : AppCompatActivity() {

    //
    private var mProjectTypeList : ProjectTypeList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_create_type)
        //播放动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //退出
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("选择类型")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //
        //加载项目列表
        val list_view: GridView = findViewById<GridView>(R.id.template_list)
        mProjectTypeList = ProjectTypeList(this,list_view)
        mProjectTypeList!!.Init()
        //
        Listener()
    }  
    
    //监听器
    private fun Listener() {
        //列表选择监听器
        mProjectTypeList!!.setOnTypeListener(object : ProjectTypeList.OnTypeListener {
            override fun onType(Name: String,Type: String,Uri: String) {
                val returnIntent = Intent()
                returnIntent.putExtra("type_name", Name)
                returnIntent.putExtra("type_type", Type)
                returnIntent.putExtra("type_url", Uri)
                setResult(Activity.RESULT_OK, returnIntent)
                finishAfterTransition()
            }
        })
        //退出
        val button_return = findViewById<MaterialButton>(R.id.button_return)
        button_return.setOnClickListener {
            finishAfterTransition() 
        }
    }  
    
}