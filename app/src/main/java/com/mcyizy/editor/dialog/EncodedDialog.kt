package com.mcyizy.editor.dialog

//基础库
import android.content.Context
import android.content.DialogInterface
import java.nio.charset.Charset
import androidx.appcompat.app.AlertDialog
//CodeEditor
import io.github.rosemoe.sora.widget.CodeEditor
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//
import com.mcyizy.android.tool2.FileOperation

public class EncodedDialog {

    //文件
    var mFileOperation = FileOperation()
    //创建选项列表
    private var text_temp : String = "" 
    private var path_temp : String = ""
    private var options : Array<String> = arrayOf()
    //设置路径
    public fun setPath(path : String) {
        path_temp = path
        text_temp = mFileOperation.ReadFile(path)
    }
    //弹窗
    public fun Dialog(mContext : Context,mCodeEditor : CodeEditor,mSaveFile : Boolean) {
        //读取文件
        var res_text : String = mFileOperation.ReadResourceFile(mContext,"editor/Encoding.txt")
        if (res_text != "null") {
            options = res_text.split("\n").toTypedArray()
        } else {
            options = arrayOf("加载失败！")
        }
        // 创建对话框构建器
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("选择编码")
        //选择监听器
        .setSingleChoiceItems(options, 0) { dialogInterface: DialogInterface, selectedIndex: Int ->
            // 处理选中项的逻辑
            when (selectedIndex) {
                0 -> mCodeEditor.setText(text_temp)
                1 -> mCodeEditor.setText(text_temp)
                else -> {
                    if (mSaveFile) {
                        //指定编码保存
                        TranscodingSave(selectedIndex,mCodeEditor)
                        Toasty.success(mContext, "新编码保存完毕！", Toast.LENGTH_SHORT, true).show()
                    } else {
                        //指定编码重新加载
                        Transcoding(selectedIndex,mCodeEditor)
                        Toasty.success(mContext, "新编码载入完毕！", Toast.LENGTH_SHORT, true).show()
                    }
                }
            }
            // 关闭菜单
            dialogInterface.dismiss()
            }
         //取消按钮   
        .setPositiveButton("取消") { dialogInterface: DialogInterface, _: Int ->
            // 处理"OK"按钮点击事件的逻辑
            dialogInterface.dismiss()
        }
        // 创建并显示对话框
        val dialog = builder.create()
        dialog.show()
    }
    
    //转换编码
    private fun Transcoding(index : Int,mCodeEditor : CodeEditor) {
        // 原始文本
        val originalText = mCodeEditor.getText().toString()
        // 要转换的编码格式
        val encodingType = if (index == 0) "UTF-8" else options[index].toString()
        // 将文本转换为指定编码格式
        val encodedText = String(originalText.toByteArray(Charset.forName("UTF-8")), Charset.forName(encodingType))
        // 设置文本
        mCodeEditor.setText(encodedText.toString())
    }
    
    //转换编码保存
    private fun TranscodingSave(index : Int,mCodeEditor : CodeEditor) {
        // 原始文本
        val originalText = mCodeEditor.getText().toString()
        // 要转换的编码格式
        val encodingType = if (index == 0) "UTF-8" else options[index].toString()
        // 将文本转换为指定编码格式
        val encodedText = String(originalText.toByteArray(Charset.forName("UTF-8")), Charset.forName(encodingType))
       // 设置文本
        mFileOperation.WriteFile(path_temp,encodedText)
    }
    
}
