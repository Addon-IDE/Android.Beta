package com.mcyizy.addonide.module.exceptionhandling

//基础库
import com.mcyizy.addonide.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.View
//widget
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.mcyizy.android.widget.ToolbarView
//java
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.lang.reflect.Field
//android
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.content.pm.ApplicationInfo
import android.os.Build
import android.net.Uri
import android.content.Intent
//
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool.SystemOperation

public class CrashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_module_crash)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //退出
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("软件崩溃")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               Toast.makeText(this@CrashActivity, "程序已崩溃，无法退出！\n点击下方功能，重启", Toast.LENGTH_SHORT).show()
            }
            override fun onMenu(view : View) {
            }
        })
        //获取属性
        val message_text_1 = findViewById<TextView>(R.id.message_text_1)
        val message_text_2 = findViewById<TextView>(R.id.message_text_2)
        val message_text_3 = findViewById<TextView>(R.id.message_text_3)
        val message_text_4 = findViewById<TextView>(R.id.message_text_4)
        val message_text_5 = findViewById<TextView>(R.id.message_text_5)
        //加载方式
        ApplicationInformation(message_text_1)
        DeviceInformation(message_text_2)
        CrashInformation(message_text_3)
        CrashInformation2(message_text_4)
        ErrorStackInformation(message_text_5)
        ButtonListener()
    }
    
    //返回键逻辑
    override fun onBackPressed() {
        // 添加您的退出逻辑
        Toast.makeText(this, "程序已崩溃，无法退出！\n点击下方功能，重启", Toast.LENGTH_SHORT).show()
    }
    
    //应用信息
    private fun ApplicationInformation(mTextView : TextView) {
    try {
            // 获取PackageManager实例
            val packageManager: PackageManager = this.packageManager
            // 获取PackageInfo实例
            val packageInfo: PackageInfo = packageManager.getPackageInfo(this.packageName, 1)
            // 获取ApplicationInfo实例
            val applicationInfo: ApplicationInfo = packageInfo.applicationInfo
            // 获取应用名称
            val appName: String = packageManager.getApplicationLabel(applicationInfo).toString()
            // 获取应用包名
            val packageName: String = packageInfo.packageName
            // 获取版本名
            val versionName: String = packageInfo.versionName
            // 获取版本号
            val versionCode: Int = packageInfo.versionCode
            // 获取最小SDK版本
            val minSdkVersion: Int = applicationInfo.minSdkVersion
            // 获取目标SDK版本
            val targetSdkVersion: Int = applicationInfo.targetSdkVersion
            //获取崩溃时间
            val format_time = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss.SSS秒", Locale.getDefault()).format(Date())
            // 进行整合
            val TextIntegration: String = "应用名称：${appName}\n" + "应用包名：${packageName}\n" + "版本名称：${versionName}\n" + "版本号：${versionCode}\n" + "最小SDK版本：${minSdkVersion}\n" + "目标SDK版本：${targetSdkVersion}\n" + "崩溃时间：${format_time}"
            mTextView.setText(TextIntegration)
        } catch (e: PackageManager.NameNotFoundException) {
            // 捕获PackageManager.NameNotFoundException异常
            e.printStackTrace()
            mTextView.setText("信息获取失败！ (Information acquisition failed)")
        }
    }
    
    //设备信息
    private fun DeviceInformation(mTextView : TextView) {
        var text = ""
        for (field: Field in Build::class.java.declaredFields) {
            try {
                field.isAccessible = true
                val obj: Any? = field.get(null)
                if (obj != null) {
                    val output = field.name + "：" + obj
                    text = text + output + "\n"
                    mTextView.setText(text)
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                mTextView.setText("信息获取失败！ (Information acquisition failed)")
            }
        }
    }
    
    //崩溃信息
    private fun CrashInformation(mTextView : TextView) {
        try {
            val string_text = intent.getStringExtra("message").toString()
            val string_text2 = getFirstLine(string_text) 
            mTextView.setText(string_text2)
        } catch (e: Exception) {
            e.printStackTrace()
            mTextView.setText("信息获取失败！ (Information acquisition failed)")
        }
    }
    private fun CrashInformation2(mTextView : TextView) {
        try {
            val string_text = intent.getStringExtra("message").toString()
            mTextView.setText(string_text)
        } catch (e: Exception) {
            e.printStackTrace()
            mTextView.setText("信息获取失败！ (Information acquisition failed)")
        }
    }
    
    //错误堆栈
    private fun ErrorStackInformation(mTextView : TextView) {
        try {
            val string_text = intent.getStringExtra("stackTrace").toString()
            mTextView.setText(string_text)
        } catch (e: Exception) {
            e.printStackTrace()
            mTextView.setText("信息获取失败！ (Information acquisition failed)")
        }
    }
    
    //按钮监听器
    private fun ButtonListener() {
        //控件属性获取
        val function_button_1 = findViewById<Button>(R.id.function_button_1)
        val function_button_2 = findViewById<Button>(R.id.function_button_2)
        val function_button_3 = findViewById<Button>(R.id.function_button_3)
        val function_button_4 = findViewById<Button>(R.id.function_button_4)
        //复制
        function_button_1.setOnClickListener {
            try {
                SystemOperation.setClipboardText(this,Merge())
                Toast.makeText(this, "复制完毕", Toast.LENGTH_SHORT).show()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
            }
        }
        //打包成文档
        function_button_2.setOnClickListener {
            try {
                val intents = Intent("android.intent.action.SEND")
                intents.putExtra("android.intent.extra.SUBJECT", "分享到")
                intents.putExtra("android.intent.extra.TEXT", Merge())
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(Intent.createChooser(intents, "分享到"))
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
            }
        }
        //反馈
        function_button_3.setOnClickListener {
            try {
                val qqNumber = "3457982009"  // 替换为你的QQ号码
                val intents = Intent("android.intent.action.VIEW", Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=${qqNumber}&version=1"))
                startActivity(intents)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
            }
        }
        //重启
        function_button_4.setOnClickListener {
            try {
                // 重启应用
                val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

                // 关闭当前应用
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
            }
        }
        function_button_4.setOnLongClickListener {
            finish()
            true
        }
    }
    
    private fun getFirstLine(content: String): String {
        return content.lines().firstOrNull() ?: ""
    }
    
    private fun Merge() : String {
        val message_text_1 = findViewById<TextView>(R.id.message_text_1)
        val message_text_2 = findViewById<TextView>(R.id.message_text_2)
        val message_text_3 = findViewById<TextView>(R.id.message_text_3)
        val message_text_4 = findViewById<TextView>(R.id.message_text_4)
        val message_text_5 = findViewById<TextView>(R.id.message_text_5)
        
        val text = "应用信息：\n${message_text_1.getText().toString()}\n" + "设备信息：${message_text_2.getText().toString()}\n" + "崩溃信息：${message_text_3.getText().toString()}\n" + "崩溃信息详细：${message_text_4.getText().toString()}\n" + "其他：${message_text_5.getText().toString()}"
        return text
    }
    
}