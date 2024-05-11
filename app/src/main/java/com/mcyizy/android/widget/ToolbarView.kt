package com.mcyizy.android.widget

import com.mcyizy.addonide.R
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.app.Activity
import android.view.View

public class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var mListeners: OnReturnListener? = null
    
    private var title: TextView
    private var return_layout: LinearLayout
    private var menu_button: ImageView
    
    private var isMenu: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_toolbar_main, this, true)
        // 获取 XML 布局文件中的控件，并在此处对其进行操作
        title = findViewById(R.id.title)
        
        //退出
        return_layout = findViewById(R.id.return_layout)
        return_layout.setOnClickListener {
            //(context as Activity).finishAfterTransition() 
            mListeners?.onReturn()
        }
        
        //菜单
        menu_button = findViewById(R.id.menu_button)
        menu_button.setOnClickListener {
            mListeners?.onMenu(menu_button)
        }
        
        setMenu(false)
    }

    //设置标题
    fun setTitle(value: String) {
        title.text = value
    }
    
    //显示菜单
    fun setMenu(value : Boolean) {
        isMenu = value
        if (isMenu) {
            menu_button.visibility = View.VISIBLE
        } else {
            menu_button.visibility = View.GONE
        }
    }
    
    fun OnReturnListener(listener: OnReturnListener) {
        mListeners = listener
    }    
    
    interface OnReturnListener {
        fun onReturn()
        fun onMenu(view : View)
    }
    
    
}