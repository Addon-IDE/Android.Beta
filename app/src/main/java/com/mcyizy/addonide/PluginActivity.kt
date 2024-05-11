package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
//widget
import com.mcyizy.android.widget.ToolbarView
//视图
import android.view.View
import android.widget.TextView
import android.widget.ListView
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
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.setting.SettingOperate
//
import com.mcyizy.addonide.plugin.Main as PluginMain

public class PluginActivity : AppCompatActivity() {

    //列表
    private lateinit var Adapter: ArrayAdapter<String>
    private lateinit var Items: Array<String>
    private lateinit var mListView: ListView
    //模块
    val mFileOperation : FileOperation = FileOperation()
    val mPluginMain : PluginMain = PluginMain()
    //变量
    var try_string : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_plugin)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //设置工具栏
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("插件")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //加载列表
        val mPath = Path(this)
        mListView = findViewById<ListView>(R.id.plugin_list)
        Items = mFileOperation.FetchSubfileCollection(mPath.getPluginPath())
        List()
    } 
    
     //加载列表
    private fun List() {
        //创建适配器
        Adapter = ArrayAdapter(this, R.layout.list_plugin_list, Items)
        //设置适配器
        mListView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_plugin_list, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_plugin_list, parent, false)
                }
                //初始化
                val mPath = Path(this@PluginActivity)
                var item_path : String = Items[position]
                val plugin_path : String = item_path + "/"
                //控件声明
                val title_name = view!!.findViewById<TextView>(R.id.title_name)
                val title_describe = view!!.findViewById<TextView>(R.id.title_describe)
                val title_version = view!!.findViewById<TextView>(R.id.title_version)
                val title_author = view!!.findViewById<TextView>(R.id.title_author)
                //加载json
                if (isManifest(plugin_path)) {
                    //设置属性
                    title_name.text = ReadManifest(plugin_path,"name")
                    title_describe.text = ReadManifest(plugin_path,"author")
                    title_version.text = ReadManifest(plugin_path,"version_name")
                    title_author.text = ReadManifest(plugin_path,"describe")
                } else {
                    title_name.text = "清单文件损坏"
                    title_describe.text = "Failed to load！"
                    title_version.text = "-.-.-"
                    title_author.text = "Failed to load！"
                }
                //面板
                val listComponent = view!!.findViewById<LinearLayout>(R.id.list_component)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(this@PluginActivity,listComponent)
                //列表点击监听事件
                listComponent.setOnClickListener {
                    var item_path : String = Items[position]
                    mPluginMain.setContext(this@PluginActivity)
                    mPluginMain.PluginPath(item_path)
                    mPluginMain.Run(0)
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(this, Items)
        mListView.adapter = customAdapter
    } 
    
    //判断清单文件是否存在或者异常
    private fun isManifest(path : String) : Boolean {
        var output : Boolean = false
        val path2 : String = path + "manifest.json"
        if (mFileOperation.Exists(path2)) {
            val manifest_text = mFileOperation.ReadFile(path2)
            if (SettingOperate.isJsonValid(manifest_text)) {
                output = true
            } else {
                output = false
                try_string = "清单文件内json不合法！"
            }
        } else {
           output = false   
           try_string = "清单文件不存在！"
        }
        return output
    }
    
    //读取清单内的键
    private fun ReadManifest(path : String,name : String) : String {
        var output = ""
        val manifest_text = mFileOperation.ReadFile(path + "manifest.json")
        val mJSONObject = JSONObject(manifest_text)
        if (mJSONObject.has(name)) {
            output = mJSONObject.getString(name)
        } else {
            output = "失败，未找到相应的键"
        }
        return output
    }

}
