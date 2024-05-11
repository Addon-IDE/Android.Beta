package com.mcyizy.editor.menu

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//PopupMenu
import androidx.appcompat.widget.PopupMenu
//widget
import android.view.View
//
import com.mcyizy.editor.module.BottomSheet2
import com.mcyizy.editor.module.File as AppFile
import com.mcyizy.editor.list.FileList

public class OperateFileMenu(private val mContext: Context) {

    //
    private var mBottomSheet : BottomSheet2? = null
    private var mAppFile : AppFile = AppFile()
    private lateinit var mFileList : FileList

    //配置
    companion object {
        private const val MENU_1 = "重命名"
        private const val MENU_2 = "删除文件"
    }
    
    //初始化
    init {
       mBottomSheet = BottomSheet2(mContext) 
    }
    
    //全局变量
     private var mPath : String = ""
    
    //设置路径
    public fun setPath(mpath : String) {
        mPath = mpath
    }
    //设置列表
    public fun setList(mlist : FileList) {
        mFileList = mlist
    }

    //创建菜单
    public fun OperateMenu(mView : View) {
        //初始化
        var bottomSheet = mBottomSheet
        //菜单
        val popupMenu = PopupMenu(mContext, mView)
        popupMenu.menu.add(MENU_1)
        popupMenu.menu.add(MENU_2)
        popupMenu.setGravity(5)
        popupMenu.setOnMenuItemClickListener {
            when (it.title) {
                MENU_1 -> {
                    bottomSheet!!.setPath(mPath)
                    bottomSheet!!.setList(mFileList)
                    bottomSheet!!.FileRename()
                }
                MENU_2 -> {
                    mAppFile.setContext(mContext)
                    mAppFile.setPath(mPath)
                    mAppFile.setList(mFileList)
                    mAppFile.DeleteFile()
                }
            }
            true
        } 
        popupMenu.show()           
    }

}