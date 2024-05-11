package com.mcyizy.addonide.setting

import android.view.View
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ImageView
import android.view.LayoutInflater
import com.suke.widget.SwitchButton
import com.mcyizy.addonide.R
import android.app.Activity

class SettingListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    //加载监听器
    private var mListeners: OnListener? = null
    //初始化控件
    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var component_image: ImageView
    private lateinit var component_button: SwitchButton
    //初始化变量
    var type_value : String = "0"
    var title_value : String = ""
    var subtitle_value : String = ""

    init {
        // 先处理自定义属性
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SettingListView, // 这里是您定义的自定义属性的样式集
            defStyle,
            0
        )
        // 获取值
        type_value = typedArray.getString(R.styleable.SettingListView_type) ?: "0"
        title_value = typedArray.getString(R.styleable.SettingListView_title) ?: "标题"
        subtitle_value = typedArray.getString(R.styleable.SettingListView_subtitle) ?: "副标题"
        // 结束获取值
        typedArray.recycle()
        // 获取 XML 布局文件
        LayoutInflater.from(context).inflate(R.layout.view_setting_list, this, true)
        // 获取 XML 布局文件中的控件，并在此处对其进行操作
        title = findViewById(R.id.title_text)
        subtitle = findViewById(R.id.subtitle_text)
        component_image = findViewById(R.id.component_image)
        component_button = findViewById(R.id.component_button)
        //加载方法
        RefreshView()
        //加载监听器
        Listener()
    }
    
    //开关状态
    public fun isSwitch() : SwitchButton {
        return component_button
    }
    public fun getSwitch() : Boolean {
        return component_button.isChecked
    }
    public fun setSwitch(select : Boolean) {
        component_button.isChecked = select
    }
   
    //刷新控件
    private fun RefreshView() {
        //标题
        title.setText(title_value)
        //副标题
        subtitle.setText(subtitle_value)
        //是否显示布局
        when (type_value) {
            "0" -> {
                component_image.visibility = View.VISIBLE
                component_button.visibility = View.GONE
            }
            "1" -> {
                component_image.visibility = View.GONE
                component_button.visibility = View.VISIBLE
            }
            else -> {
                component_image.visibility = View.VISIBLE
                component_button.visibility = View.GONE
            }
        }
    }
    
    //监听器
    private fun Listener() {
        //被点击
        val setting_button = findViewById<RelativeLayout>(R.id.setting_button)
        setting_button.setOnClickListener {
            mListeners?.onClick()
        }
    }
    
    fun setOnListener(listener: OnListener) {
        mListeners = listener
    }    
    
    interface OnListener {
        fun onClick()
    }
    
    fun setOnClickListener(listener: () -> Unit) {
        val setting_button = findViewById<RelativeLayout>(R.id.setting_button)
        setting_button.setOnClickListener { listener() }
    }
    
}