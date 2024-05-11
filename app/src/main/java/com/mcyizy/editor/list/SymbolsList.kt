package com.mcyizy.editor.list

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//view widget
import android.view.Gravity
import android.widget.TextView
import android.widget.LinearLayout
//android
import android.graphics.Typeface
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.content.ContextCompat
//code editor
import io.github.rosemoe.sora.widget.CodeEditor
//android app
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.tool.PixelOperation

public class SymbolsList(private val mContext: Context,
                         private val mLinearLayout: LinearLayout,
                         private val mCodeEditor: CodeEditor) {
    
    //基础配置
    private var symbols_list = ArrayList<String>()
    private var mViewComponent = ViewComponent()
    private val mPixelOperation = PixelOperation()
                      
    //初始化
    init {
        //测试
        symbols_list = arrayListOf("{","}","(",")",";","=","\"","|","&","!","[","]","<",">","+","-","/")
        //加载列表
        List()
    }
      
    //列表
    private fun List() {
        //清空
        mLinearLayout.removeAllViews()
        //循环
        for (item in symbols_list) {
            //声明
            val mTextView = TextView(mContext)
            //设置文本
            mTextView.setText(item.toString())
            //设置为居中
            mTextView.gravity = Gravity.CENTER
            //设置颜色
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorDefaultFont))
            //设置字体大小
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            //单行显示
            mTextView.setSingleLine(true)
            //背景颜色
            mTextView.setBackgroundColor(0)
            //设置宽度和高度
            val layoutParams = ViewGroup.LayoutParams(
                mPixelOperation.DPtoPX(mContext,40), // 设置宽度为40dp
                mPixelOperation.DPtoPX(mContext,40)  // 设置高度为40dp
            )
            mTextView.layoutParams = layoutParams
            //设置水波纹
            mViewComponent.WaterRippleEffect(mContext,mTextView)
            //添加
            mLinearLayout.addView(mTextView)
            //设置监听器
            Listener(mTextView)
        }
    }
    
    //监听器
    private fun Listener(mTextView: TextView) {
        mTextView.setOnClickListener {
            var str_text = mTextView.getText().toString()
            insertText(str_text,str_text.length)
        }
    }
    
    //插入文本
    private fun insertText(content: String, selectionOffset: Int = 0) {
        mCodeEditor.insertText(content,selectionOffset)
    }
        
                         
}
