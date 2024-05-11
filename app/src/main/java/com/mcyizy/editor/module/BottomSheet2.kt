package com.mcyizy.editor.module

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//控件
import android.view.View
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButtonToggleGroup
//文件管理
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.editor.list.FileList
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty

public class BottomSheet2(private val mContext: Context) {

     //全局变量
     private var mPath : String = ""
     
     private lateinit var mFileList : FileList
    
    //设置路径
    public fun setPath(mpath : String) {
        mPath = mpath
    }
    //设置列表
    public fun setList(mlist : FileList) {
        mFileList = mlist
    }

    //文件夹
    public fun FileRename() {
        //弹窗
        val bottomSheet = BottomSheetDialog(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.sheet_dialog_filerename, null)
        bottomSheet.setContentView(view)
        bottomSheet.show()
        //加载监听器
        FileRenameListener(view,bottomSheet)
    }
    
    private fun FileRenameListener(view : View,mBottomSheetDialog : BottomSheetDialog) {
        var mFileOperation = FileOperation()
        //获取控件属性
        val createfile_name = view.findViewById<TextInputEditText>(R.id.createfile_name)
        //设置基础配置
        createfile_name.setText(mFileOperation.getFileNmae(mPath))
        createfile_name.setSelection(mFileOperation.getFileNmae(mPath).length)
        //创建文件夹
        val button_create = view.findViewById<MaterialButton>(R.id.button_create)
        button_create.setOnClickListener {
            //获取
            var file_name : String = createfile_name.getText().toString()
            //转换
            var parent_directory_path : String = mFileOperation.getParentDirectoryPath(mPath)
            //开始处理
            var boolean : Boolean = mFileOperation.renameFile(mPath,parent_directory_path + "/" + file_name)
            if (boolean && file_name.replace(" ","") != "") {
                mBottomSheetDialog.dismiss()
                Toasty.success(mContext, "重命名成功", Toast.LENGTH_SHORT, true).show()
                //刷新列表
                mFileList.RefreshList()
            } else {
                Toasty.error(mContext, "重命名失败！", Toast.LENGTH_SHORT, true).show()
            }
        }
        //取消
        val button_no = view.findViewById<MaterialButton>(R.id.button_no)
        button_no.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
    }
    
}
