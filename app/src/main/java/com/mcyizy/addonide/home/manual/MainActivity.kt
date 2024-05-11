package com.mcyizy.addonide.home.manual

//默认库
import com.mcyizy.addonide.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//widget
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.LinearLayout
//App
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.addonide.home.manual.ManualList
import com.mcyizy.addonide.home.manual.CreateMenu

public class MainActivity : AppCompatActivity() {

    //上下文
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_home_manual)
        //设置状态栏
        ApplicationWindow.StatusBar(this,1)
        //加载方法
        Start()
    }
    
    override fun onRestart() {
        super.onStart()
        //处理动画
        overridePendingTransition(R.anim.list_anim_homehelp, R.anim.list_anim_homehelp)
    }
    
    public fun Start() {
        //获取对象
        val list = findViewById<ListView>(R.id.listview)
        
        val button_up = findViewById<LinearLayout>(R.id.button_up)
        val button_search = findViewById<LinearLayout>(R.id.button_search)
        val button_add = findViewById<LinearLayout>(R.id.button_add)
        
        val search_editor = findViewById<EditText>(R.id.search_editor)
        
        //初始化
        val mManualList = ManualList(this,list)
        //手册
        mManualList.setEditText(search_editor)
        mManualList.Conversion("")
        mManualList.Button(button_up,button_search)
        mManualList.List()
        
        //监听器
        button_add.setOnClickListener {
            val mCreateMenu = CreateMenu(this)
            mCreateMenu.setPath(mManualList.getFolderPath())
            mCreateMenu.setList(mManualList)
            mCreateMenu.Start()
        }
    }
    
}    