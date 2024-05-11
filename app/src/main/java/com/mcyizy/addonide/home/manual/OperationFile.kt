package com.mcyizy.addonide.home.manual

import com.mcyizy.addonide.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder

import android.view.View
import android.view.LayoutInflater
import android.widget.LinearLayout

import android.widget.EditText
import android.widget.Button

//App
import com.mcyizy.addonide.main.Path as MainPath
import com.mcyizy.android.tool.PixelOperation
import com.mcyizy.addonide.home.manual.ManualList
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.CompressionOperation
import com.mcyizy.android.tool.ShareOperation

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
    private lateinit var mFileList : ManualList
    val mFileOperation = FileOperation()
    
    var mPath = ""
    
    public fun setPath(path : String) {
        mPath = path
    }
    
     //设置列表
    public fun setList(mlist : ManualList) {
        mFileList = mlist
    }
    
    public fun Start() {
        builder = AlertDialog.Builder(mContext)
        builder.setTitle("操作")
        builder.setView(getView())
        //默认值
        mEditText.setText(mFileOperation.getFilePrefixName(mPath))
        builder.setNeutralButton("打包发出") { dialog, _ ->
            if (mFileOperation.isDirectory(mPath)) {
                val mMainPath = MainPath(mContext)
                val output = mMainPath.getBackupPath() + "home/manual/" + mFileOperation.getFilePrefixName(mPath) + "_pack.zip"
                val output2 = mMainPath.getBackupPath() + "home/manual/"
                if (!mFileOperation.Exists(output2)) {
                    mFileOperation.CreateDirectory(output2)
                }
                if (CompressionOperation.ZIPCompression(mPath,output)) {
                    val mShareOperation = ShareOperation()
                    mShareOperation.ShareFile(mContext,output,null)
                } else {
                    Toasty.error(mContext, "打包失败！", Toast.LENGTH_SHORT, true).show()
                }
            }
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
        
        linearLayout.addView(mEditText)
        
        return linearLayout
    }

}