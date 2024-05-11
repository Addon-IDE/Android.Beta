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

//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty

//App
import com.mcyizy.android.tool.PixelOperation
import com.mcyizy.editor2.project.ProjectList
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.ViewComponent

public class CreateMenu(private val mContext: Context) {

    private lateinit var dialog : AlertDialog
    private lateinit var builder : Builder
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
        builder.setTitle("创建文件")
        builder.setView(getView())
        builder.setNeutralButton("取消") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("文件夹") { dialog, _ ->
            var file_name : String = mEditText.getText().toString()
            var path_output : String = mPath + "/" + file_name
            //判断当前文件夹下是否有同名文件
            if (mFileOperation.Exists(path_output) && file_name.replace(" ","") != "") {
                //警告当前下面有同名文件无法创建
                Toasty.error(mContext, "警告当前下面有同名文件无法创建！", Toast.LENGTH_SHORT, true).show()
            } else {
                //没有
                var boolean : Boolean = mFileOperation.CreateDirectory(path_output)
                if (boolean) {
                   mFileList.RefreshList()
                   dialog.dismiss()
                }
            }
        }
        builder.setPositiveButton("文件") { dialog, _ ->
            var file_name : String = mEditText.getText().toString()
            //创建
            if (file_name.replace(" ","") == "") {
            
            } else {
                mFileOperation.WriteFile(mPath + "/" + file_name + ".md","")
                mFileList.RefreshList()
                dialog.dismiss()
            }
        }
        dialog = builder.create()
        dialog.show()
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
