package com.mcyizy.android.widget

//android
import com.mcyizy.addonide.R
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.LinearLayout.LayoutParams
import android.graphics.drawable.GradientDrawable
//
import androidx.appcompat.app.AppCompatDelegate
//列表适配器
import android.widget.ArrayAdapter
import android.widget.AbsListView
//App
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.tool2.PictureOperation
import com.mcyizy.android.tool.PixelOperation

public class PopupWindow(private val mContext: Context,private val mView : View) {
    
    //PopupWindow
    val popupWindow = PopupWindow(mContext)
    val mPixelOperation = PixelOperation()
    private var mListeners: OnItemListener? = null
    //
    private val background = GradientDrawable()
    //列表
    private lateinit var Adapter: ArrayAdapter<String>
    private lateinit var Items: Array<String>
    private val item_temp = ArrayList<String>()
    private val item_temp_icon = ArrayList<String>()
    private lateinit var mListView : ListView
    //变量
    var width_value : Int = 180
    var height_value : Int = 220
    
    //初始化
    init {
        // 设置PopupWindow的宽度和高度
        WidthHeight()
        // 设置白色背景 + 设置圆角
        val currentTheme = mContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            setBackground(-1)
        } else {
            setBackground(-15658735)
        }
        setRadius(20.0f)
        // 设置阴影
        setElevation(10.0f)
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation)
        //设置点击外部区域是否隐藏
        popupWindow.setOutsideTouchable(true)
        // 设置PopupWindow的布局
        popupWindow.contentView = getView()
    }
    
    //
    public fun add(icon : String,name : String) {
        item_temp_icon.add(icon)
        add(name)
    }
    
    //添加项目
    public fun add(name : String) {
        item_temp.add(name)
        Items = item_temp.toTypedArray()
        //计算列表高度
        var item_size = item_temp.size
        if (item_size <= 6) {
            height_value = item_size * 40
        } else {
            if (isLandscapeOrientation()) {
                //横屏适配
                val displayMetrics = mContext.resources.displayMetrics
                val height = displayMetrics.heightPixels
                height_value = mPixelOperation.PXtoDP(mContext,height) - mPixelOperation.DPtoPX(mContext,25)
            } else {
                height_value = 280
            }
        }
        WidthHeight()
        //加载列表
        List()
    }
    
    //设置背景颜色
    public fun setBackground(color : Int) {
        background.setColor(color)
        popupWindow.setBackgroundDrawable(background)
    }
    
    //设置阴影
    public fun setElevation(value : Float) {
         popupWindow.elevation = value
    }
    
    //设置圆角
    public fun setRadius(value : Float) {
        val cornerRadius = value
        background.cornerRadius = cornerRadius
        popupWindow.setBackgroundDrawable(background)
    }
    
    //显示
    public fun show() {
        // 显示PopupWindow
        popupWindow.showAsDropDown(mView)
    }
    
    //关闭
    public fun dismis() {
        // 关闭PopupWindow
        popupWindow.dismiss()
    }
    
    
    //--------监听器--------
    
    fun OnItemListener(listener: OnItemListener) {
        mListeners = listener
    }    
    interface OnItemListener {
        fun onItem(index : Int)
    }
    
    //--------内部方法--------
    
    //设置宽度和高度
    private fun WidthHeight() {
        popupWindow.setWidth(mPixelOperation.DPtoPX(mContext,width_value))
        popupWindow.setHeight(mPixelOperation.DPtoPX(mContext,height_value))
    }
    
    //布局
    private fun getView() : View {
        // 创建一个LinearLayout作为根布局
        val linearLayout = LinearLayout(mContext)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            mPixelOperation.DPtoPX(mContext,width_value),  // 将dp转换为像素
            mPixelOperation.DPtoPX(mContext,height_value)   // 将dp转换为像素
        )
        //
        
        // 添加列表布局
        mListView = ListView(mContext)
        // 隐藏列表分割线
        mListView.setDividerHeight(0)
        // 将ListView添加到LinearLayout中
        linearLayout.addView(mListView)
        
        return linearLayout
    }
    
    //列表
    private fun List() {
        //创建适配器
        Adapter = ArrayAdapter(mContext, R.layout.android_popwindow_list, Items)
        //设置适配器
        mListView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.android_popwindow_list, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.android_popwindow_list, parent, false)
                }
                //设置图标
                val icon_name = view!!.findViewById<ImageView>(R.id.icon_name)
                val mPictureOperation = PictureOperation(mContext)
                try {
                    mPictureOperation.setAssetsImage(icon_name,item_temp_icon[position])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //设置名称
                val textView = view!!.findViewById<TextView>(R.id.title_name)
                var item_name : String = Items[position]
                textView.text = item_name
                //列表点击监听事件
                val listComponent = view!!.findViewById<LinearLayout>(R.id.list_component)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(mContext,listComponent)
                listComponent.setOnClickListener {
                    //
                    mListeners?.onItem(position)
                    // 关闭PopupWindow
                    popupWindow.dismiss()
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(mContext, Items)
        mListView.adapter = customAdapter
    }
    
    //判断屏幕方向是否为横屏
    private fun isLandscapeOrientation(): Boolean {
        val orientation = mContext.resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }
    
}

