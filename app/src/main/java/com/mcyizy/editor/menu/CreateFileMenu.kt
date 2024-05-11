package com.mcyizy.editor.menu

//基础库
import com.mcyizy.addonide.R
import android.content.Context
//PopupMenu
import androidx.appcompat.widget.PopupMenu
//widget
import android.widget.LinearLayout
//
import com.mcyizy.editor.list.FileList
import com.mcyizy.editor.module.BottomSheet

public class CreateFileMenu(private val mContext: Context) {

    //
    private lateinit var mFileList : FileList
    private var mBottomSheet : BottomSheet? = null

    //配置
    companion object {
        private const val MENU_1 = "新建文件夹"
        private const val MENU_2 = "新建文件"
        private const val MENU_3 = "导入文件"
    }
    
    //初始化
    init {
        mBottomSheet = BottomSheet(mContext)
    }
    
    //全局变量
     private var mPath : String = ""
     private var mPath_Default : String = ""
    
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

    //创建菜单
    public fun CreateMenu(mLinearLayout : LinearLayout) {
        //初始化
        var bottomSheet = mBottomSheet
        //菜单
        val popupMenu = PopupMenu(mContext, mLinearLayout)
        popupMenu.menu.add(MENU_1)
        popupMenu.menu.add(MENU_2)
        popupMenu.menu.add(MENU_3)
        popupMenu.setOnMenuItemClickListener {
            bottomSheet!!.setList(mFileList)
            when (it.title) {
                MENU_1 -> {
                    bottomSheet!!.setPath(mPath)
                    bottomSheet!!.setPathDefault(mPath_Default)
                    bottomSheet!!.CreateFolder()
                }
                MENU_2 -> {
                    bottomSheet!!.setPath(mPath)
                    bottomSheet!!.setPathDefault(mPath_Default)
                    bottomSheet!!.CreateFile()
                }
                MENU_3 -> {}
            }
            true
        } 
        popupMenu.show()           
    }

}
