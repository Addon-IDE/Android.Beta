package com.mcyizy.editor.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import java.io.OutputStream
import com.mcyizy.android.tool2.FileOperation
import io.github.rosemoe.sora.widget.CodeEditor
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty

public class SaveFileAs {

    //打开保存文件窗口
    public fun Open(activity : AppCompatActivity,path : String) {
        //获取名称
        val mFileOperation = FileOperation()
        val mName = mFileOperation.getFileNmae(path)
        //跳转
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TITLE, mName)
        activity.startActivityForResult(intent, 1001)
    } 
    
    //保存
    public fun Save(context : Context,data : Intent,codeeditor : CodeEditor) {
        try {
            val uri: Uri? = data.data
            val outputStream: OutputStream? = context.contentResolver.openOutputStream(uri!!)
            if (outputStream != null) {
                // 将内容写入到文件中
                val content = codeeditor.getText().toString()
                outputStream.write(content.toByteArray())
                outputStream.close()
                // 文件保存成功
                Toasty.success(context, "文件保存成功！", Toast.LENGTH_SHORT, true).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // 文件保存失败
            Toasty.error(context, "文件保存失败！", Toast.LENGTH_SHORT, true).show()
        }
    }
    
    
}
