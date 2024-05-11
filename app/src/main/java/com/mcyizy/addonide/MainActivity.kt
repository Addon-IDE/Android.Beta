
package com.mcyizy.addonide

//基础库
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcyizy.addonide.databinding.ActivityMainBinding
//崩溃生成相关
import com.mcyizy.addonide.module.exceptionhandling.Init as InitException

import com.ExceptionCapture.activity.MyUncaughtExceptionHandler
import com.ExceptionCapture.activity.UncaughtExceptionActivity
//App
import com.mcyizy.addonide.setting.SettingOperate

//活动
import android.content.Intent
import com.mcyizy.addonide.HomeActivity

public class MainActivity : AppCompatActivity() {

    //保存与该活动相关联的 ActivityMainBinding 实例
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //内容视图
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //初始化崩溃生成
        //MyUncaughtExceptionHandler.getInstance(this).init();
        val mInitException = InitException()
        mInitException.init(this)
        //切换窗口
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent) // 启动目标窗口
    }
    
}
