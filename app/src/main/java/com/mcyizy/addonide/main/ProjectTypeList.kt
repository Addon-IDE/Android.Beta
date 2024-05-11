package com.mcyizy.addonide.main

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import android.content.Intent
//视图
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.GridView
import android.view.ViewGroup
import android.widget.LinearLayout
import android.view.ViewGroup.LayoutParams
//列表适配器
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.AbsListView
//json
import org.json.JSONArray
import org.json.JSONObject
//App
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.PictureOperation
//活动 动画
import android.app.Activity
import android.app.ActivityOptions
import com.mcyizy.addonide.TestActivity

//项目列表
public class ProjectTypeList(private val mContext: Context,private val mGridView: GridView) {
    
    //App
    private var mFileOperation : FileOperation = FileOperation()
    private var mPictureOperation : PictureOperation? = null
    
    //列表
    private lateinit var Adapter: ArrayAdapter<String>
    private lateinit var Items: Array<String>
    
    //初始化监听器
    private var mListeners: OnTypeListener? = null
    
    //
    public fun Init() {
        //赋值
        mPictureOperation = PictureOperation(mContext)
        //转换
        Conversion()
        //加载列表
        List()
    }
    
    //转换
    private fun Conversion() {
        //初始化
        val list = ArrayList<String>()
        list.clear()
        //读取 循环
        var json_content : String = mFileOperation.ReadResourceFile(mContext,"data/project_type.json")
        val jsonArray = JSONArray(json_content)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(jsonObject.toString())
        }
        //转换
        Items = list.toTypedArray()
    }

    //加载列表
    private fun List() {
        //创建适配器
        Adapter = ArrayAdapter(mContext, R.layout.list_project_type_list, Items)
        //设置适配器
        mGridView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_project_type_list, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_project_type_list, parent, false)
                }
                //获取内容
                var text_string = Items[position]
                var jsonObject = JSONObject(text_string)
                var title_icon = jsonObject.getString("icon")
                var title_name = jsonObject.getString("name")
                //设置图标
                val imageView: ImageView = view?.findViewById(R.id.icon) as ImageView
                SetListPicture(imageView,title_icon)
                //设置名称
                val textView = view!!.findViewById<TextView>(R.id.name)
                textView.text = title_name
                //组件初始化
                val listComponent = view!!.findViewById<LinearLayout>(R.id.button_layout)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(mContext,listComponent)
                //列表点击监听事件
                mGridView.setOnItemClickListener { _, _, position, _ ->
                    var text_string2 : String = Items[position]
                    val jsonObject2 = JSONObject(text_string2)
                    // 获取当前活动的文本
                    mListeners?.onType(jsonObject2.getString("name"),jsonObject2.getString("type"),jsonObject2.getString("url"))
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(mContext, Items)
        mGridView.adapter = customAdapter
    }
    
    //设置图标
    private fun SetListPicture(mImageView : ImageView,Name : String) {
        val pictureOperation = mPictureOperation
        if (pictureOperation != null) {
            if (Name == "") {
            } else {
                pictureOperation.setAssetsImage(mImageView,"project/project_type_icon/${Name}")
            }
        }
    }
    
    fun setOnTypeListener(listener: OnTypeListener) {
        mListeners = listener
    }    
    
    interface OnTypeListener {
        fun onType(Name: String,Type: String,Uri: String)
    }
    

}
