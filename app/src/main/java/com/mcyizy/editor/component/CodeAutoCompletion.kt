package com.mcyizy.editor.component

//android
import com.mcyizy.addonide.R
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.PopupWindow
import android.view.LayoutInflater
import android.widget.ListView
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.graphics.drawable.GradientDrawable
//json
import org.json.JSONArray
import org.json.JSONObject
//列表适配器
import android.widget.ArrayAdapter
import android.widget.AbsListView
//App
import io.github.rosemoe.sora.widget.CodeEditor
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.tool.PixelOperation
import com.mcyizy.android.tool2.PictureOperation
//组件
import com.mcyizy.editor.component.CodeAutoCompletionListener

public class CodeAutoCompletion(private val mContext: Context) {

    //
    val popupWindow = PopupWindow(mContext)
    private val background = GradientDrawable()
    val mPixelOperation = PixelOperation()
    //lateinit
    private lateinit var mCodeEditor: CodeEditor
    //列表
    private lateinit var Adapter: ArrayAdapter<String>
    private var Items = ArrayList<String>()
    private var item_main = ArrayList<String>()
    private lateinit var mListView : ListView
    private var string_temp : String = ""
    //变量
    var width_value : Int = 300
    var height_value : Int = 0 //220
    var AllowedInputPointS : Boolean = false
    
    //初始化
    init {
        
    }
    
    //设置代码编辑框
    public fun setCode(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    public fun AutoCompletion() {
        Listener()
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
        PopupWindow()
        //popupWindow.showAsDropDown(mCodeEditor)
    }
    
    //关闭
    public fun dismis() {
        // 关闭PopupWindow
        popupWindow.dismiss()
    }
    
    //--------主要方法--------
    //添加项目
    public fun addItem(icon : String = "",
                       name : String = "",
                       describe : String = "",
                       type : String = "") {
        
        val json = JSONObject()
        json.put("icon",icon)
        json.put("name",name)
        json.put("describe",describe)
        json.put("type",type)
        item_main.add(json.toString(0))
    }
    //清空所有项目
    public fun clearItem() {
        item_main.clear()
    }
    //清空输入字符串
    public fun clearStringTemp() {
        string_temp = ""
    }
    //允许输入点
    public fun setAllowedInputPoint(bools : Boolean) {
        AllowedInputPointS = bools
    }
    
    //--------内部方法--------
    
    //设置宽度和高度
    private fun WidthHeight() {
        popupWindow.setWidth(mPixelOperation.DPtoPX(mContext,width_value))
        popupWindow.setHeight(mPixelOperation.DPtoPX(mContext,height_value))
    }
    
    //处理监听器
    private fun Listener() {
        val mCodeAutoCompletionListener = CodeAutoCompletionListener()
        val codeAutoCompletionListener = mCodeAutoCompletionListener
        if (codeAutoCompletionListener != null) {
            codeAutoCompletionListener.setCode(mCodeEditor)
            codeAutoCompletionListener.setOnAutoListener(object : CodeAutoCompletionListener.OnAutoListener {
                //插入
                override fun onInsert(content : String) {
                    string_temp = string_temp + content
                    
                    val bool = ClearStringTemp(content)
                    if (bool) {
                        PopupWindow()
                    }
                }
                //删除
                override fun onDelete(content : String) {
                    if (string_temp.isNotEmpty()) {
                        string_temp = string_temp.substring(0, string_temp.length - 1)
                    }
                    
                    val bool = ClearStringTemp(content)
                    if (bool) {
                        PopupWindow()
                    }
                }
            })
        }
    }
    
    //弹窗显示
    private fun PopupWindow() {
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
        //设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupCodeCompletionAnimation)
        //设置PopupWindow的布局
        popupWindow.contentView = getView()
        //列表
        List()
        //显示
        if (popupWindow.isShowing) {
            popupWindow.dismiss()
        }
        popupWindow.showAtLocation(mCodeEditor, 49, 0, getLocationY().toInt()) 
        popupWindow.showAsDropDown(mCodeEditor)
    }
    
    //列表
    private fun List() {
        Filter()
        CalculateListHeight()
        WidthHeight()
        List2()
    }
    
    //过滤算法
    private fun Filter() {
        var i = 0
        Items.clear()
        while (i < item_main.size) {
            val json_object = JSONObject(item_main[i])
            if (json_object.getString("name").contains(string_temp)) {
                Items.add(item_main[i])
            }
            i++
        }
    }
    
    //计算列表高度
    private fun CalculateListHeight() {
        //计算列表高度
        var item_size = Items.size
        if (item_size <= 5) {
            height_value = item_size * 40
        } else {
            if (isLandscapeOrientation()) {
                //横屏适配
                val displayMetrics = mContext.resources.displayMetrics
                val height = displayMetrics.heightPixels
                height_value = mPixelOperation.PXtoDP(mContext,height) - mPixelOperation.DPtoPX(mContext,25)
            } else {
                height_value = 200
            }
        }
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
        // 添加列表布局
        mListView = ListView(mContext)
        // 隐藏列表分割线
        mListView.setDividerHeight(0)
        // 将ListView添加到LinearLayout中
        linearLayout.addView(mListView)
        
        return linearLayout
    }
    
    //获取位置
    private fun getLocationY() : Float {
        val cursor = mCodeEditor.cursor
        val CursorY : Float = getCursorY()
        val CursorY2 : Float = CursorY - cursor.getRightLine() / 50.0f - mCodeEditor.getOffsetY()
        val CursorYS : Float = CursorY2 + 350.0f //+ (getCurrentLineSize() * 5)
        return CursorYS
    }
    
    //获取光标Y轴
    private fun getCursorY() : Float {
        val cursor = mCodeEditor.cursor
        val line = cursor.getLeftLine()
        val Y = mCodeEditor.getOffset2(line,cursor.getLeftColumn())
        return Y
    }
    
     //列表
    private fun List2() {
        //创建适配器
        Adapter = ArrayAdapter(mContext, R.layout.list_editor_completelist, Items)
        //设置适配器
        mListView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: ArrayList<String>) : ArrayAdapter<String>(context, R.layout.list_editor_completelist, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_editor_completelist, parent, false)
                }
                //获取控件
                val icon_name = view!!.findViewById<ImageView>(R.id.icon_name)
                val title_name = view!!.findViewById<TextView>(R.id.title_name)
                val title_describe = view!!.findViewById<TextView>(R.id.title_describe)
                val title_type = view!!.findViewById<TextView>(R.id.title_type)
                //item + json
                var item_string : String = Items[position]
                val json_object = JSONObject(item_string)
                //设置
                if (json_object.getString("icon").replace(" ","") != "") {
                    val mPictureOperation = PictureOperation(mContext)
                    mPictureOperation.setAssetsImage(icon_name,json_object.getString("icon"))
                }
                title_name.text = json_object.getString("name")
                title_describe.text = json_object.getString("describe")
                title_type.text = json_object.getString("type")
                //列表点击监听事件
                val listComponent = view!!.findViewById<LinearLayout>(R.id.list_component)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(mContext,listComponent)
                listComponent.setOnClickListener {
                    //插入
                    InsertText(json_object.getString("name"))
                    //清除输入缓存
                    string_temp = ""
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
    
    //获取当前行的数量
    private fun getCurrentLineSize() : Int {
       val cursor = mCodeEditor.getCursor()
       val text = mCodeEditor.getText()
       var line_position = cursor.getRightLine() //获取当前行的位置
       var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
       return line_string_size
    }
    
    //换行空格字符清空输入缓存字符串
    private fun ClearStringTemp(str : String) : Boolean {
        var returnvalue = true
        if (str == " ") {
            string_temp = ""
            returnvalue = false
            popupWindow.dismiss()
        }
        if (str == "\n") {
            string_temp = ""
            returnvalue = false
            popupWindow.dismiss()
        }
        if (AllowedInputPointS) {
            if (str == ".") {
                string_temp = ""
                returnvalue = true
            }
        }
        return returnvalue
    }
    
    //插入
    private fun InsertText(content : String) {
        //获取长度
        val content_size : Int = content.length
        val string_temp_size : Int = string_temp.length
        //光标和文本获取
        val cursor = mCodeEditor.getCursor()
        val text = mCodeEditor.getText()
        //获取数值
        val line_position = cursor.getRightLine() //获取当前行的位置
        val cursorIndex = cursor.getRightColumn()
        val cursorIndex2 = text.getCharIndex(line_position,cursorIndex)
        //删除
        text.delete(cursorIndex2-string_temp_size,cursorIndex2)
        //插入
        mCodeEditor.insertText(content,content_size)
        
    }

}
