package com.mcyizy.editor.menu

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import android.view.View
//PopupMenu
import com.mcyizy.android.widget.PopupWindow
//App
import com.mcyizy.android.tool2.CompressionOperation
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.tool.ShareOperation
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//function

public class MainProjectMenu(private val mContext: Context) {

    //Lateinit
    
    //变量
    private val mFileOperation = FileOperation()
    private val mShareOperation = ShareOperation()
    private val mPath = Path(mContext)
    private var mPath_Default : String = ""
    
    //配置
    companion object {
        private const val MENU_1 = "备份项目"
        private const val MENU_2 = "项目编辑"
        private const val MENU_3 = "分享项目"
        private const val MENU_4 = "清理缓存"
    }
    
    //设置路径
    public fun setPathDefault(path : String) {
        mPath_Default = path
    }
    
    //创建菜单
    public fun Menu(mView : View) {
        //
        val popupMenu = PopupWindow(mContext, mView)
        popupMenu.add(MENU_1)
        popupMenu.add(MENU_2)
        popupMenu.add(MENU_3)
        popupMenu.add(MENU_4)
        popupMenu.show()
        //
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
               when (index) {
                //备份项目
                0 -> {
                    val output = mPath.getBackupPath() + "/" + mFileOperation.getFilePrefixName(mPath_Default) + ".zip"
                    val consequence : Boolean = CompressionOperation.ZIPCompression(mPath_Default,output)
                    if (consequence) {
                        Toasty.normal(mContext, "项目备份成功！").show()
                    } else {
                        Toasty.error(mContext, "项目备份失败！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //项目编辑
                1 -> {
                    
                }
                //分享项目
                2 -> {
                    val output = mPath.getCachePath() + "/" + mFileOperation.getFilePrefixName(mPath_Default) + "_share.zip"
                    val consequence : Boolean = CompressionOperation.ZIPCompression(mPath_Default,output)
                    if (consequence) {
                        Toasty.normal(mContext, "项目开始分享！").show()
                        mShareOperation.ShareFile(mContext,output,null)
                    } else {
                        Toasty.error(mContext, "项目分享失败！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //清理缓存
                3 -> {
                    
                }
                //无
                else -> {
                
                }
                
              }  
            }
        })
    }
    
    
}