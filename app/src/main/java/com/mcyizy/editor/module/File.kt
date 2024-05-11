package com.mcyizy.editor.module

//主
import android.content.Context
//App
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.module.Dialog
//打开
import io.github.rosemoe.sora.widget.CodeEditor
//模块
import com.mcyizy.editor.module.FileOpen
import com.mcyizy.editor.list.FileList
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//OS
import android.os.Handler
import android.os.Looper

public class File {

    //FileOpen
    var mFileOpen: FileOpen = FileOpen()
    var mFileOperation : FileOperation = FileOperation()
    private var mDialog : Dialog? = null
    private lateinit var mFileList : FileList
    //全局
    private lateinit var mContext: Context
    private lateinit var mCodeEditor: CodeEditor
    //
    private var mPath: String = ""
    
    //Context
    public fun setContext(context_value: Context) {
        mContext = context_value
        
        mDialog = Dialog(context_value)
    }
    //设置路径
    public fun setPath(path_value: String) {
        mPath = path_value
    }
    //设置列表
    public fun setList(mlist : FileList) {
        mFileList = mlist
    }
    //设置编辑框
    public fun setCode(code_editor: CodeEditor) {
        mCodeEditor = code_editor
    }
    
    //打开
    public fun Open() {
        //
        mFileOpen.setContext(mContext)
        mFileOpen.setPath(mPath)
        mFileOpen.setCode(mCodeEditor)
        mFileOpen.Open()
    }
    
    //删除文件
    public fun DeleteFile() {
        var dialog = mDialog
        val handler = Handler(Looper.getMainLooper())
        Thread {
            handler.post {
                //显示对话框
                dialog!!.LoadDialog("正在删除...")
            }
            val boolean: Boolean = mFileOperation.deleteFile(mPath)
            handler.post {
                if (boolean) {
                    Toasty.success(mContext, "删除文件成功！", Toast.LENGTH_SHORT, true).show()
                    //刷新列表
                    mFileList.RefreshList()
                } else {
                    Toasty.error(mContext, "删除文件失败！", Toast.LENGTH_SHORT, true).show()
                }
                //处理完毕
                dialog!!.LoadDialogOff()
            }
        }.start()
        
    }

}
