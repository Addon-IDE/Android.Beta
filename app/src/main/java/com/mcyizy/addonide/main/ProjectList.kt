package com.mcyizy.addonide.main

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import android.content.Intent
import org.json.JSONObject
//视图
import android.view.View
import android.widget.TextView
import android.widget.GridView
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.view.ViewGroup.LayoutParams
//列表适配器
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.AbsListView
//App
import com.mcyizy.addonide.main.Path
import com.mcyizy.addonide.main.Project
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.setting.SettingOperate
import com.mcyizy.addonide.main.DataStorage
//活动 动画
import android.app.Activity
import android.app.ActivityOptions
import com.mcyizy.editor.CodeEditorActivity
import com.mcyizy.editor2.MainActivity as EditorMainActivity
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty

//项目列表
public class ProjectList(private val mContext: Context,private val mGridView: GridView) {
    
    //App
    private var mPath : Path? = null
    private var mAppProject : Project = Project()
    private var mFileOperation : FileOperation = FileOperation()
    
    //列表
    private lateinit var Adapter: ArrayAdapter<String>
    private lateinit var Items: Array<String>
    
    //
    public fun Init() {
        //获取路径初始化
        mPath = Path(mContext)
        //初始化路径
        var project_path = mPath!!.getProjectPath()
        //路径转数组
        Items = mFileOperation.FetchSubfileCollection(project_path)
        //加载列表
        List()
    }
    
    //搜索
    public fun Search(text_str: String) {
        //
        val search_list = ArrayList<String>()
        search_list.clear()
        var project_path = mPath!!.getProjectPath()
        var Items_search: Array<String> = mFileOperation.FetchSubfileCollection(project_path)
        //过滤
        for (item in Items_search) {
            if (mFileOperation.getFileNmae(item).contains(text_str)) {
                search_list.add(item)
            }
        }
        //转换
        Items = search_list.toTypedArray()
        List()
    }
    
    //刷新
    public fun Refresh() {
        var project_path = mPath!!.getProjectPath()
        Items = mFileOperation.FetchSubfileCollection(project_path)
        List()
    }

    //加载列表
    private fun List() {
        //创建适配器
        Adapter = ArrayAdapter(mContext, R.layout.list_project_list, Items)
        //设置适配器
        mGridView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_project_list, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_project_list, parent, false)
                }
                //设置名称
                val textView = view!!.findViewById<TextView>(R.id.name)
                var File_Path : String = Items[position]
                textView.text = mFileOperation.getFileNmae(File_Path)
                //设置修改时间
                val timeView = view!!.findViewById<TextView>(R.id.time)
                timeView.text = mAppProject.getFormattedTimeSinceLastModification(File_Path)
                //
                val listComponent = view!!.findViewById<RelativeLayout>(R.id.list_component)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(mContext,listComponent)
                //列表点击监听事件
                listComponent.setOnClickListener {
                    val type = ManifestFile(File_Path)
                    //存储于本地，方便后面调用
                    val mDataStorage = DataStorage(mContext)
                    mDataStorage.Write("Project_Type",type)
                    when (type) {
                        "default" -> {
                            val intent = Intent(mContext, EditorMainActivity::class.java)
                            intent.putExtra("ProjectPath", File_Path.toString())
                            mContext.startActivity(intent)   
                        }
                        else -> {
                             //切换窗口
                            val intent = Intent(mContext, CodeEditorActivity::class.java)
                            val transitionName: String? = "transitions_main"
                            val sharedElement: View = listComponent
                            val options = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, sharedElement, transitionName)
                            //传入参数
                            intent.putExtra("ProjectPath", File_Path.toString())
                            //切换
                            mContext.startActivity(intent, options.toBundle())   
                        }
                    }
                    
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(mContext, Items)
        mGridView.adapter = customAdapter
    }
    
    //读取清单
    private fun ManifestFile(path : String) : String {
        val Manifest_Path = path + "/manifest.json"
        if (mFileOperation.Exists(Manifest_Path)) {
            val Manifest_Content = mFileOperation.ReadFile(Manifest_Path)
            if (SettingOperate.isJsonValid(Manifest_Content)) {
                val jsonObject = JSONObject(Manifest_Content)
                if (jsonObject.has("type")) {
                    val type = jsonObject.getString("type")
                    return type
                } else {
                    Toasty.error(mContext, "在清单文件中未找到类型定义！", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.error(mContext, "清单文件有异常！", Toast.LENGTH_SHORT, true).show()
            }
        } else {
            Toasty.error(mContext, "清单文件不存在！", Toast.LENGTH_SHORT, true).show()
        }
        return ""
    }
    
    

}
