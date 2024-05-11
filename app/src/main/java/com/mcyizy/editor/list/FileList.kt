package com.mcyizy.editor.list;

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import android.widget.ListView
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//Editor Text
import android.text.Editable
import android.text.TextWatcher
//列表适配器
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.AbsListView
//
import java.io.File
//App
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.PictureOperation

public class FileList(
                 private val mContext: Context,
                 private val mFileListView: ListView) {
        
    //监听器声明
    private var mListeners: OnListener? = null
    //列表
    private lateinit var mAdapter: ArrayAdapter<String>
    private lateinit var mItems: Array<String>  
    //App
    private var mFileOperation : FileOperation = FileOperation()  
    private var mPictureOperation : PictureOperation? = null
    //
    private var temp_path : String = ""
    private var default_path : String = ""        
                 
    //初始化
    init {
        //图片操作初始化
        mPictureOperation = PictureOperation(mContext)
    }
    
    //获取路径
    public fun getPath() : String {
        return temp_path
    }
    
    //设置路径
    public fun setPath(path_value: String) {
        default_path = path_value
        temp_path = path_value
        mItems = mFileOperation.FetchSubfileCollection(path_value)
    }
    
    //列表
    public fun List() {
        FileList()
    }
    
    //刷新
    public fun SwipeRefresh(mRefreshLayout: SwipeRefreshLayout) {
        mRefreshLayout.setOnRefreshListener {
            RefreshList()
            mRefreshLayout.isRefreshing = false
        }
    }
    
    //返回上一级
    public fun ReturnSuperior() {
        if (default_path == temp_path) {
            
        } else {
            val file = File(temp_path)
            val parentPath = file.parent
            if (parentPath != null) {
                temp_path = parentPath
            }
            RefreshList()
        }
    }
    
    //搜索
    public fun Search(mEditor : EditText) {
        mEditor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本发生变化之前执行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 当文本发生变化时执行的操作
                Search2(mEditor.getText().toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本发生变化之后执行的操作
            }
        })
    }
    
    //刷新列表
    public fun RefreshList() {
        //刷新数组
        val subfiles = mFileOperation.FetchSubfileCollection(temp_path)
        mItems = subfiles
        //刷新
        FileList()
    }
    
    //后台刷新
    private fun Search2(mText : String) {
        //缓存列表
        val list = ArrayList<String>()
        list.clear()
        RefreshList()
        //
        var i = 0
        while (i < mItems.size) {
            if (mItems[i].contains(mText)) {
                list.add(mItems[i])
            }
            i++
        }
        mItems = list.toTypedArray()
        FileList()
    }
    
    //文件
    private fun FileList() {
        //创建适配器
        mAdapter = ArrayAdapter(mContext, R.layout.list_file_list, mItems)
        //设置适配器
        mFileListView.adapter = mAdapter
        //刷新适配器
        mAdapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_file_list, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_file_list, parent, false)
                }
                var file_path = mItems[position]
                //图标
                val imageView: ImageView = view?.findViewById(R.id.file_icon) as ImageView
                if (mFileOperation.isDirectory(file_path)) {
                    SetPicture(imageView,"file.png")
                } else {
                    SetPictureType(imageView,file_path)
                }
                //名称
                val textView: TextView = view!!.findViewById(R.id.file_name)
                textView.text = mFileOperation.getFileNmae(file_path)
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(mContext, mItems)
        mFileListView.adapter = customAdapter
        //列表被点击
        mFileListView.setOnItemClickListener { _, _, position, _ ->
            //路径
            var Path : String = mItems[position]
            //打开
            if (mFileOperation.Exists(Path)) { //判断文件是否存在
                if (mFileOperation.isDirectory(Path)) {
                    //打开文件夹
                    temp_path = Path
                    RefreshList()
                } else {
                    //打开文件
                    mListeners?.onClick(Path)
                }
            }
        }
        //列表被长按
        mFileListView.setOnItemLongClickListener { _, view, position, _ ->
            //路径
            var Path : String = mItems[position]
            //加载监听器
            mListeners?.onLongClick(Path,view)
            true
        }
    }
    
    //设置图标
    private fun SetPicture(mImageView : ImageView,Name : String) {
        val pictureOperation = mPictureOperation
        if (pictureOperation != null) {
            pictureOperation.setAssetsImage(mImageView,"image/file_type/${Name}")
        }
    }
    
    //设置图标类型
    private fun SetPictureType(mImageView : ImageView,path : String) {
        var SuffixName : String = mFileOperation.getFileSuffixName(path)
        var SuffixNameS : String = SuffixName.lowercase()
        if (SuffixNameS == "json") {
            SetPicture(mImageView,"json.png")
        } else if (SuffixNameS == "js") {
            SetPicture(mImageView,"js.png")
        } else if (SuffixNameS == "java") {
            SetPicture(mImageView,"java.png")
        } else {
            SetPicture(mImageView,"no.png")
        }
    }
    
    fun setOnListener(listener: OnListener) {
        mListeners = listener
    }    
    
    interface OnListener {
        fun onClick(Path: String)
        fun onLongClick(Path: String,mView : View)
    }
                 
}

