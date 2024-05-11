package com.mcyizy.editor.menu

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import io.github.rosemoe.sora.widget.CodeEditor
//PopupMenu
import com.mcyizy.android.widget.PopupWindow
//App
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.editor.dialog.FindReplaceDialog
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//菜单
import com.mcyizy.editor.menu.MainFileMenu
import com.mcyizy.editor.menu.MainEditorMenu
import com.mcyizy.editor.menu.MainProjectMenu

public class CodeMain(private val mContext: Context) {

    //lateinit
    private lateinit var mAppCompatActivity : AppCompatActivity
    private lateinit var mCodeEditor : CodeEditor
    //菜单
    private var mMainFileMenu : MainFileMenu? = null
    private var mMainEditorMenu : MainEditorMenu? = null
    private var mMainProjectMenu : MainProjectMenu? = null
    //变量
    private var path_file : String = ""
    private var mPath_Default : String = ""
    private var path_file_icon = "image/menu/"

    //配置
    companion object {
        private const val MENU_1 = "保存"
        private const val MENU_2 = "文件"
        private const val MENU_3 = "编辑"
        private const val MENU_4 = "查找"
        private const val MENU_5 = "项目"
        private const val MENU_6 = "辅助"
        private const val MENU_7 = "工具"
    }
    
    //初始化
    init {
        mMainFileMenu = MainFileMenu(mContext)
        mMainEditorMenu = MainEditorMenu(mContext)
        mMainProjectMenu = MainProjectMenu(mContext)
    }
    //设置活动
    public fun setActivity(activity : AppCompatActivity) {
        mAppCompatActivity = activity
    }
    //设置路径
    public fun setPath(path : String) {
        path_file = path
    }
    //设置路径
    public fun setPathDefault(mpath : String) {
        mPath_Default = mpath
    }
    //设置编辑器
    public fun setCodeEditor(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    //创建菜单
    public fun Menu(mView : View) {
        //初始化
        val mainFileMenu = mMainFileMenu
        val mainEditorMenu = mMainEditorMenu
        val mainProjectMenu = mMainProjectMenu
        //菜单
        val popupMenu = PopupWindow(mContext, mView)
        popupMenu.add(getIcon("save.png"),MENU_1)
        popupMenu.add(MENU_2)
        popupMenu.add(MENU_3)
        popupMenu.add(MENU_4)
        popupMenu.add(MENU_5)
        popupMenu.add(MENU_6)
        popupMenu.add(MENU_7)
        //选中
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
               when (index) {
                //保存
                0 -> {
                    val mFileOperation = FileOperation()
                    if (mFileOperation.Exists(path_file)) {
                        mFileOperation.WriteFile(path_file,mCodeEditor.getText().toString())
                        Toasty.success(mContext, "保存完毕！", Toast.LENGTH_SHORT, true).show()
                    } else {
                        Toasty.error(mContext, "保存失败！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //文件
                1 -> {
                   mainFileMenu!!.setActivity(mAppCompatActivity) 
                   mainFileMenu!!.setPath(path_file) 
                   mainFileMenu!!.setCodeEditor(mCodeEditor) 
                   mainFileMenu!!.Menu(mView)
                }
                //编辑
                2 -> {
                   mainEditorMenu!!.setCodeEditor(mCodeEditor) 
                   mainEditorMenu!!.Menu(mView)
                }
                //查找和替换
                3 -> {
                    val mFindReplaceDialog = FindReplaceDialog()
                    mFindReplaceDialog.Dialog(mContext,mCodeEditor)
                }
                //项目
                4 -> {
                    mainProjectMenu!!.setPathDefault(mPath_Default)
                    mainProjectMenu!!.Menu(mView)
                }
              }
            }
        })
        //显示
        popupMenu.show()           
    }
    
    //
    private fun getIcon(name : String) : String {
        return path_file_icon + name
    }
    
    
}
