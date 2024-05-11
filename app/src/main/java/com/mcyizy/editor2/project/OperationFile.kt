package com.mcyizy.editor2.project

import com.mcyizy.addonide.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import android.content.res.Configuration

import android.view.View
import android.view.LayoutInflater
import android.widget.LinearLayout

import android.widget.EditText
import android.widget.Button

//App
import com.mcyizy.addonide.main.Path as MainPath
import com.mcyizy.android.tool.PixelOperation
import com.mcyizy.editor2.project.ProjectList
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.CompressionOperation
import com.mcyizy.android.ViewComponent

//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty

public class OperationFile(private val mContext: Context) {

    private lateinit var dialog : AlertDialog
    private lateinit var builder : Builder
    private lateinit var dialogDelete : AlertDialog
    private lateinit var builderDelete : Builder
    private lateinit var mEditText : EditText
    val mPixelOperation = PixelOperation()
    private lateinit var mFileList : ProjectList
    val mFileOperation = FileOperation()
    private val mViewComponent = ViewComponent()
    
    var mPath = ""
    
    public fun setPath(path : String) {
        mPath = path
    }
    
     //设置列表
    public fun setList(mlist : ProjectList) {
        mFileList = mlist
    }
    
    public fun Start() {
        builder = AlertDialog.Builder(mContext)
        builder.setTitle("操作")
        builder.setView(getView())
        //默认值
        mEditText.setText(mFileOperation.getFilePrefixName(mPath))
        builder.setNeutralButton("取消") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("删除") { dialog, _ ->
           
            builderDelete = AlertDialog.Builder(mContext)
            builderDelete.setTitle("提示")
            builderDelete.setMessage("是否删除")
            builderDelete.setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            builderDelete.setPositiveButton("确定") { dialog, _ ->
                Delete()
                dialog.dismiss()
            }
            dialogDelete = builderDelete.create()
            dialogDelete.show()
           
        }
        builder.setPositiveButton("重命名") { dialog, _ ->
            //获取
            var file_name : String = mEditText.getText().toString()
            //转换
            var parent_directory_path : String = mFileOperation.getParentDirectoryPath(mPath)
            //开始处理
            var boolean : Boolean = mFileOperation.renameFile(mPath,parent_directory_path + "/" + file_name + 
                if (mFileOperation.isDirectory(mPath)) {
                    ""
                } else {
                    "." + mFileOperation.getFileSuffixName(mPath)
                }
            )
            if (boolean && file_name.replace(" ","") != "") {
                Toasty.success(mContext, "重命名成功", Toast.LENGTH_SHORT, true).show()
                //刷新列表
                mFileList.RefreshList()
                dialog.dismiss()
            } else {
                Toasty.error(mContext, "重命名失败！", Toast.LENGTH_SHORT, true).show()
            }
        }
        dialog = builder.create()
        dialog.show()
    }
    
    private fun Delete() {
        val boolean: Boolean = mFileOperation.deleteFile(mPath)
        if (boolean) {
            Toasty.success(mContext, "删除文件成功！", Toast.LENGTH_SHORT, true).show()
            mFileList.RefreshList()
        } else {
            Toasty.error(mContext, "删除文件失败！", Toast.LENGTH_SHORT, true).show()
        }
    }

    private fun getView() : View {
        // 创建一个LinearLayout作为根布局
        val linearLayout = LinearLayout(mContext)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,  // 将dp转换为像素
            LinearLayout.LayoutParams.FILL_PARENT   // 将dp转换为像素
        )
        //编辑框1
        mEditText = EditText(mContext)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            mPixelOperation.DPtoPX(mContext,45)
        )
        layoutParams.setMargins(mPixelOperation.DPtoPX(mContext,20), mPixelOperation.DPtoPX(mContext,10), mPixelOperation.DPtoPX(mContext,20), mPixelOperation.DPtoPX(mContext,5)) // 设置左外边距和右外边距的像素值
        mEditText.layoutParams = layoutParams
        mEditText.setText("")
        
        mEditText.setPadding(mPixelOperation.DPtoPX(mContext,5), 0, mPixelOperation.DPtoPX(mContext,5), 0)
        
        val currentTheme = mContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentTheme == Configuration.UI_MODE_NIGHT_NO) {
            mViewComponent.setRoundedBackgroundWithBorder(mEditText,-523,-2174036,5,3f,3f,3f,3f)
        } else {
            mViewComponent.setRoundedBackgroundWithBorder(mEditText,-11316397,-9013387,5,3f,3f,3f,3f)
        }
        
        linearLayout.addView(mEditText)
        
        return linearLayout
    }

}