package com.mcyizy.addonide.home.manual

import android.view.View
import android.content.Context
import com.mcyizy.android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import io.github.rosemoe.sora.widget.CodeEditor
import com.mcyizy.editor.dialog.FindReplaceDialog
import com.mcyizy.editor.menu.MainEditorMenu
import com.mcyizy.android.tool.SystemOperation
import com.mcyizy.editor.module.SaveFileAs
import com.mcyizy.android.tool.ShareOperation
import com.mcyizy.android.tool2.FileOperation

public class MainMenu(private val mContext: Context) {

    private lateinit var mAppCompatActivity : AppCompatActivity
    private lateinit var mCodeEditor : CodeEditor
    private var mPath : String = ""
    val mFileOperation = FileOperation()
    
    public fun setActivity(activity : AppCompatActivity) {
        mAppCompatActivity = activity
    }
    
    public fun setCode(codeEditor:CodeEditor) {
        mCodeEditor = codeEditor
    }

    public fun menu(view: View) {
        val popupMenu = PopupWindow(mContext, view)
        popupMenu.add("保存")
        popupMenu.add("撤销")
        popupMenu.add("重做")
        popupMenu.add("查找")
        popupMenu.add("编辑")
        popupMenu.add("发出")
        popupMenu.add("保护")
        popupMenu.add("加密")
        popupMenu.add("关闭")
        popupMenu.show()
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
                when (index) {
                    0 -> {
                       mFileOperation.WriteFile(mPath,mCodeEditor.getText().toString()) 
                    }
                    1 -> mCodeEditor.undo()
                    2 -> mCodeEditor.redo()
                    3 -> {
                        val mFindReplaceDialog = FindReplaceDialog()
                        mFindReplaceDialog.Dialog(mContext,mCodeEditor)
                    }
                    4 -> {
                        val mMainEditorMenu = MainEditorMenu(mContext)
                        mMainEditorMenu.setCodeEditor(mCodeEditor) 
                        mMainEditorMenu.Menu(view)
                    }
                    5 -> SendOut(view)
                    6 -> {}
                    7 -> {}
                    8 -> {}
                    9 -> {}
                    else -> {}
                }
            }
        })
    }
    
    public fun setPath(path : String) {
        mPath = path
    }
    
    private fun SendOut(view: View) {
        val popupMenu = PopupWindow(mContext, view)
        popupMenu.add("另存为")
        popupMenu.add("复制")
        popupMenu.add("分享")
        popupMenu.show()
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
                when (index) {
                    0 -> {
                        var mSaveFileAs = SaveFileAs()
                        mSaveFileAs.Open(mAppCompatActivity,mPath)
                    }
                    1 -> {
                        SystemOperation.setClipboardText(mContext,mCodeEditor.getText().toString())
                    }
                    2 -> {
                        val mShareOperation = ShareOperation()
                        mShareOperation.ShareFile(mContext,mPath,null)
                    }
                    else -> {}
                }
            }
        })
    }

}
