package com.mcyizy.editor2.project

import com.mcyizy.addonide.R
import java.io.File
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.AbsListView
import android.widget.ArrayAdapter
import java.util.ArrayList
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable

import android.content.Intent
import com.mcyizy.addonide.home.manual.ManualEditorActivity

import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.PictureOperation
import com.mcyizy.editor.module.File as DeleteFileDialog
import com.mcyizy.editor2.project.OperationFile
import com.mcyizy.editor2.project.Filtration

public class ProjectList(private val mContext: Context,private val mListView: ListView) {

    //列表
    private lateinit var mAdapter: ArrayAdapter<String>
    private lateinit var mItems: Array<String>
    private lateinit var mEditText: EditText
    
    //模块
    val mFileOperation: FileOperation = FileOperation()
    private var mPictureOperation : PictureOperation? = null
    private val mFiltration = Filtration()
    
    //路径
    private var list_path = ""
    private var default_path : String = ""
    
    //初始化
    init {
        mPictureOperation = PictureOperation(mContext)
        //初始化路径
        var patha = mContext.getExternalFilesDir(null)?.absolutePath
        list_path = patha.toString() + "/data/Manual"
        default_path = list_path
        //生成文件夹
        if (!mFileOperation.Exists(list_path)) {
            mFileOperation.CreateDirectory(list_path)
        }
        //初始化列表
        mItems = arrayOf()
    }
    
    //
    public fun getFolderPath() : String {
        return list_path
    }
    
    public fun getFolderDefaultPath() : String {
        return default_path
    }
    
    //声明搜索编辑框
    public fun setEditText(editText : EditText) {
        mEditText = editText
    }
    
    //转换
    public fun Conversion(name : String) {
        //获取列表数组
        val subfiles = mFileOperation.FetchSubfileCollection(list_path)
        mItems = subfiles
    }
    
    //按钮的点击
    public fun Button(button_up : LinearLayout) {
        //返回上一级
        button_up.setOnClickListener {
            if (default_path == list_path) {
                
            } else {
                val file = java.io.File(list_path)
                val parentPath = file.parent
                if (parentPath != null) {
                    list_path = parentPath
                }
                RefreshList()
            }
        }
        //搜索
        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本变化之前执行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val fileName = mEditText.getText().toString() // 文件名搜索条件
                val matchingFiles = mFiltration.searchFilesByName(default_path, fileName)
                if (fileName == "") {
                    RefreshList()
                } else {
                    mItems = matchingFiles
                    List()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本变化之后执行的操作
            }
        })
    }
    
    //刷新列表
    public fun RefreshList() {
        //刷新数组
        val subfiles = mFileOperation.FetchSubfileCollection(list_path)
        mItems = subfiles
        //刷新
        List()
    }
    
    public fun List() {
        //创建适配器
        mAdapter = ArrayAdapter(mContext, R.layout.list_editor2_project, mItems)
        //设置适配器
        mListView.adapter = mAdapter
        //刷新适配器
        mAdapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_editor2_project, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_editor2_project, parent, false)
                }
                var item_string = mItems[position]
                //设置图标
                val imageView: ImageView = view?.findViewById(R.id.icon) as ImageView
                setFileIcon(imageView,item_string)
                //设置名称
                val textView = view!!.findViewById<TextView>(R.id.title)
                textView.text = mFileOperation.getFileNmae(item_string)
                //设置描述
                val textdescribe = view!!.findViewById<TextView>(R.id.describe)
                if (mFileOperation.isDirectory(item_string)) {
                    textdescribe.text = mFileOperation.GetModifiedTime(item_string)
                } else {
                    val file = File(item_string)
                    textdescribe.text = file.length().toString() + " 字节"
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(mContext, mItems)
        mListView.adapter = customAdapter
        //动画
        val layoutAnimation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.list_anim_editor2)
        mListView.layoutAnimation = layoutAnimation
        mListView.scheduleLayoutAnimation()
        //列表被点击
        mListView.setOnItemClickListener { _, _, position, _ ->
            var Path : String = mItems[position]
            if (mFileOperation.Exists(Path)) { //判断文件是否存在
                if (mFileOperation.isDirectory(Path)) {
                    //打开文件夹
                    list_path = Path
                    RefreshList()
                } else {
                    //打开文件
                    /*
                    val intent = Intent(mContext, ManualEditorActivity::class.java)
                    intent.putExtra("FilePath", Path.toString())
                    mContext.startActivity(intent)
                    */
                }
            }
        }
        //列表被长按
        mListView.setOnItemLongClickListener { _, _, position, _ ->
            var item_string : String = mItems[position]
            
            val mOperationFile = OperationFile(mContext)
            mOperationFile.setPath(item_string)
            mOperationFile.setList(this)
            mOperationFile.Start()
            
            true
        }
    }
    
    //根据文件类型来设置图标
    private fun setFileIcon(imageView : ImageView,path:String) {
        if (mFileOperation.isDirectory(path)) {
            SetPicture(imageView,"folder.png") 
        } else {
            SetPicture(imageView,"document.png") 
        }
    }
    
    //设置图标
    private fun SetPicture(mImageView : ImageView,Name : String) {
        val pictureOperation = mPictureOperation
        if (pictureOperation != null) {
            if (Name == "") {
            } else {
                pictureOperation.setAssetsImage(mImageView,"image/home/manual/${Name}")
            }
        }
    }

}