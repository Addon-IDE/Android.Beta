package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
//widget

//
import com.mcyizy.android.foundation.ApplicationWindow


public class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_test)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //
        
        
    }    
    
}