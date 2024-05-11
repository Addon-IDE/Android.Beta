package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.content.Context
import android.view.View
//widget
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.mcyizy.android.widget.ToolbarView
import com.mcyizy.addonide.setting.SettingListView 
import com.mahfa.dnswitch.DayNightSwitch
import com.mahfa.dnswitch.DayNightSwitchListener
//
import android.content.res.Configuration
import android.content.Intent
import android.app.Activity
//App
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.addonide.setting.SettingOperate
//对话框
import com.mcyizy.addonide.module.EditorThemeDialog
//活动
import com.mcyizy.addonide.setting.SettingEditorActivity

public class SettingActivity : AppCompatActivity() {

    //
    var IsNight : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_setting)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //退出
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("设置")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //夜间模式
        DayNightListener()
        //版本面板边框
        setRoundedBgWithBorder()
        //
        ButtonListener()
    }  
    
    //夜间模式切换
    private fun DayNightListener() {
        //控件初始化
        val day_night_switch = findViewById<DayNightSwitch>(R.id.day_night_switch)
        //判断是否为夜间模式
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            day_night_switch.setIsNight(false,false)
            IsNight = false
            SettingOperate.setValue(this,"NightMode",false)
        } else {
            day_night_switch.setIsNight(true,false)
            IsNight = true
            SettingOperate.setValue(this,"NightMode",true)
        }
        //切换监听器
        day_night_switch.setListener(object : DayNightSwitchListener {
            override fun onSwitch(isNight: Boolean) {
                if (isNight) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                // 准备启动主活动的意图
                val intent = Intent(this@SettingActivity, MainActivity::class.java)
                // 清除之前的实例和其他活动
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                // 启动主活动
                startActivity(intent)
                // (可选) 加上淡入淡出的转场效果，使其看起来更平滑
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                // 结束当前的活动（可选，因为FLAG_ACTIVITY_CLEAR_TASK会清除它）
                finish()
              
            }
        })
    }
    
    //版本面板边框
    private fun setRoundedBgWithBorder() {
        val versions_panel = findViewById<LinearLayout>(R.id.versions_panel)
        val mViewComponent = ViewComponent()
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            mViewComponent.setRoundedBackgroundWithBorder(versions_panel,-1,-1513240,2,20f,20f,20f,20f)
        } else {
            mViewComponent.setRoundedBackgroundWithBorder(versions_panel,-13421773,-14935012,2,20f,20f,20f,20f)
        }
    }
    
    //按钮监听
    private fun ButtonListener() {
        //获取控件对象
        val setting_button1 = findViewById<SettingListView>(R.id.setting_button1)
        val setting_button2 = findViewById<SettingListView>(R.id.setting_button2)
        //编辑器主题
        setting_button1.setOnClickListener {
            val mEditorThemeDialog = EditorThemeDialog(this)
            mEditorThemeDialog.Option()
            mEditorThemeDialog.Switch(!IsNight)
            mEditorThemeDialog.Dialog()
        }
        //编辑器设置
        setting_button2.setOnClickListener {
            val intent = Intent(this, SettingEditorActivity::class.java)
            startActivity(intent) // 启动目标窗口
        }
    }
    
}