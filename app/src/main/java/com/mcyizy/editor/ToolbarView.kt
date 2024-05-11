package com.mcyizy.editor

import android.content.Context
import android.view.View
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.ImageView
import android.view.LayoutInflater
import com.mcyizy.addonide.R
import android.app.Activity

class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var mListeners: OnToolbarListener? = null //监听器
    
    //private lateinit var title: TextView

    init {
    
        LayoutInflater.from(context).inflate(R.layout.view_toolbar_editor, this, true)
        // 获取 XML 布局文件中的控件，并在此处对其进行操作
       /* title = findViewById(R.id.title)
        */
        //加载监听器
        ButtonListener()
    }
    
    //标题
    public fun setTitle(txt: String) {
        var title: TextView = findViewById(R.id.title)
        title.setText(txt)
    }
    
    //位置显示
    public fun setPositionDisplay(txt: String) {
        var position_display: TextView = findViewById(R.id.position_display)
        position_display.setText(txt)
    }
    
    //按钮监控器
    private fun ButtonListener() {
        //控件属性
        var file_menu: ImageView = findViewById(R.id.file_menu)
        var toolbar_button1: LinearLayout = findViewById(R.id.toolbar_button1)
        var toolbar_button2: LinearLayout = findViewById(R.id.toolbar_button2)
        var toolbar_button3: LinearLayout = findViewById(R.id.toolbar_button3)
        var toolbar_button4: LinearLayout = findViewById(R.id.toolbar_button4)
        var toolbar_menu: ImageView = findViewById(R.id.toolbar_menu)
        //按钮1
        file_menu.setOnClickListener {
            mListeners?.onButtonFile()
        }
        //按钮2
        toolbar_button1.setOnClickListener {
            mListeners?.onButton1()
        }
        //按钮3
        toolbar_button2.setOnClickListener {
            mListeners?.onButton2()
        }
        //按钮4
        toolbar_button3.setOnClickListener {
            mListeners?.onButton3()
        }
        //按钮5
        toolbar_button4.setOnClickListener {
            mListeners?.onButton4()
        }
        //按钮6
        toolbar_menu.setOnClickListener {
            mListeners?.onMenuButton(toolbar_menu)
        }
    }
    
    //切换图标
    public fun Button1_Icon(value: Boolean) {
        var toolbar_button1_icon: ImageView = findViewById(R.id.toolbar_button1_icon)
        if (value) {
            toolbar_button1_icon.setImageResource(R.drawable.ic_write)
        } else {
            toolbar_button1_icon.setImageResource(R.drawable.ic_read)
        }
    }
    
    fun setOnToolbarListener(listener: OnToolbarListener) {
        mListeners = listener
    }    
    
    interface OnToolbarListener {
        fun onButtonFile()
        fun onButton1()
        fun onButton2()
        fun onButton3()
        fun onButton4()
        fun onMenuButton(mView : View)
    }
    
    
}