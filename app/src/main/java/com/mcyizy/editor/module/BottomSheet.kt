package com.mcyizy.editor.module

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//graphics
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
//控件
import android.view.View
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButtonToggleGroup
//文件管理
import com.mcyizy.android.tool2.FileOperation
//App
import com.mcyizy.editor.list.FileList
import com.mcyizy.editor.ProjectType

public class BottomSheet(private val mContext: Context) {

     //全局变量
     private var mPath : String = ""
     private var mPath_Default : String = ""
     private var type : Int = 0
     private var mformat_type : Int = 0
     private var mformat : Int = 0
     //
     private lateinit var mFileList : FileList
     private val mProjectType = ProjectType(mContext)
     
     //初始化
     init {
     
     }
    
    //设置路径
    public fun setPath(mpath : String) {
        mPath = mpath
    }
     //设置路径
    public fun setPathDefault(mpath : String) {
        mPath_Default = mpath
    }
    //设置列表
    public fun setList(mlist : FileList) {
        mFileList = mlist
    }

    //文件夹
    public fun CreateFolder() {
        //弹窗
        val bottomSheet = BottomSheetDialog(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.sheet_dialog_createfolder, null)
        bottomSheet.setContentView(view)
        bottomSheet.show()
        //设置背景颜色
        bottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //加载监听器
        CreateFolderListener(view,bottomSheet)
    }
    
    //文件
    public fun CreateFile() {
        //弹窗
        val bottomSheet = BottomSheetDialog(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.sheet_dialog_createfile, null)
        bottomSheet.setContentView(view)
        bottomSheet.show()
        //设置背景颜色
        bottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //加载监听器
        CreateFileListener(view,bottomSheet)
        //类型重置
        mformat = 0
    }
    
    //创建文件夹监听器
    private fun CreateFolderListener(view : View,mBottomSheetDialog : BottomSheetDialog) {
        var mFileOperation = FileOperation()
        //创建文件夹
        val button_create = view.findViewById<MaterialButton>(R.id.button_create)
        button_create.setOnClickListener {
            //获取
            val createfile_name = view.findViewById<TextInputEditText>(R.id.createfile_name)
            var file_name : String = createfile_name.getText().toString()
            var path_output : String = mPath + "/" + file_name
            //判断当前文件夹下是否有同名文件
            if (mFileOperation.Exists(path_output) && file_name.replace(" ","") != "") {
                //警告当前下面有同名文件无法创建
                
            } else {
                //没有
                var boolean : Boolean = mFileOperation.CreateDirectory(path_output)
                if (boolean) {
                   mBottomSheetDialog.dismiss()
                   //刷新列表
                   mFileList.RefreshList()
                }
            }
        }
        //取消
        val button_no = view.findViewById<MaterialButton>(R.id.button_no)
        button_no.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
    }
    
     //创建文件监听器
    private fun CreateFileListener(view : View,mBottomSheetDialog : BottomSheetDialog) {
        var mFileOperation = FileOperation()
        //获取view
        val tab_type = view.findViewById<MaterialButtonToggleGroup>(R.id.tab_type)
        val tab_group = view.findViewById<MaterialButtonToggleGroup>(R.id.tab_group)
        //隐藏
        tab_type.visibility = View.GONE
        tab_group.visibility = View.GONE
        //判断类型
        mProjectType.Project(mPath_Default,true)
        val type_project = mProjectType.getType()
        when (type_project) {
            "default" -> tab_type.visibility = View.VISIBLE
            "minecraft_addon" -> tab_group.visibility = View.VISIBLE
        }
        //默认重置
        
        //类型切换
        tab_type.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val checkedButtonIndex = tab_type.indexOfChild(view.findViewById(checkedId)) // 获取被选中按钮的索引
                type = 1
                mformat_type = checkedButtonIndex
                
            }
        }
        //类型切换2
        tab_group.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val checkedButtonIndex = tab_group.indexOfChild(view.findViewById(checkedId)) // 获取被选中按钮的索引
                type = 2
                mformat = checkedButtonIndex
            }
        }
        //创建文件
        val button_create = view.findViewById<MaterialButton>(R.id.button_create)
        button_create.setOnClickListener {
            val createfile_name = view.findViewById<TextInputEditText>(R.id.createfile_name)
            var file_name : String = createfile_name.getText().toString()
            //类型
            var path_output : String = when (type) {
                0 -> mPath + "/" + file_name
                1 -> mPath + "/" + file_name + IndextoFormat()
                2 -> mPath + "/" + file_name + IndextoFormat2()
                else -> mPath + "/" + file_name
            }
            //创建
            if (file_name.replace(" ","") == "") {
            
            } else {
                mFileOperation.WriteFile(path_output,"")
                mBottomSheetDialog.dismiss() 
                //刷新列表
                mFileList.RefreshList()
            }
        }
        //取消
        val button_no = view.findViewById<MaterialButton>(R.id.button_no)
        button_no.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
    }
    
    //索引转换格式
    private fun IndextoFormat() : String {
        return when(mformat_type) {
            0 -> ""
            1 -> ".addm"
            2 -> ".3"
            3 -> ".4"
            else -> ""
        }
    }
    private fun IndextoFormat2() : String {
        return when(mformat) {
            0 -> ""
            1 -> ".json"
            2 -> ".js"
            3 -> ".mcfunction"
            else -> ""
        }
    }
    
    
}
