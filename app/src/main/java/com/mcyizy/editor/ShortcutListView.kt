package com.mcyizy.editor

import android.view.View
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.ImageView
import android.view.LayoutInflater
import com.mcyizy.addonide.R
import android.app.Activity

class ShortcutListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var mListeners: OnListener? = null
    
    private lateinit var title: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_editor_shortcut, this, true)
        // 获取 XML 布局文件中的控件，并在此处对其进行操作
        title = findViewById(R.id.title)
        
        //加载监听器
        Listener()
    }
   
    //设置标题
    fun setTitle(value: String) {
        title.setText(value)
    }
    
    //设置下划线可视
    fun setUnderLine(value: Boolean) {
        val underline = findViewById<LinearLayout>(R.id.underline)
        if (value) {
            underline.visibility = View.VISIBLE
        } else {
            underline.visibility = View.GONE
        }
    }
    
    //监听器
    private fun Listener() {
        //被点击
        val delete = findViewById<ImageView>(R.id.delete)
        delete.setOnClickListener {
            mListeners?.onClick()
        }
    }
    
    fun setOnListener(listener: OnListener) {
        mListeners = listener
    }    
    
    interface OnListener {
        fun onClick()
    }
    
    
}