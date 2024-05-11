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
import com.mcyizy.editor.module.SaveFileAs
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//function
import com.mcyizy.editor.dialog.EncodedDialog

public class MainFileMenu(private val mContext: Context) {

    //Lateinit
    private lateinit var mAppCompatActivity : AppCompatActivity
    private lateinit var mCodeEditor : CodeEditor
    //变量
    private var path_file : String = ""
    
    //配置
    companion object {
        private const val MENU_1 = "另存为"
        private const val MENU_2 = "重新加载文件"
        private const val MENU_3 = "指定编码重新加载"
        private const val MENU_4 = "指定编码保存文件"
        private const val MENU_5 = "统计"
    }
    
    //设置活动
    public fun setActivity(activity : AppCompatActivity) {
        mAppCompatActivity = activity
    }
    //设置路径
    public fun setPath(path : String) {
        path_file = path
    }
    //设置编辑器
    public fun setCodeEditor(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    
    //创建菜单
    public fun Menu(mView : View) {
        //
        val popupMenu = PopupWindow(mContext, mView)
        popupMenu.add(MENU_1)
        popupMenu.add(MENU_2)
        popupMenu.add(MENU_3)
        popupMenu.add(MENU_4)
        popupMenu.add(MENU_5)
        popupMenu.show()
        //
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
               when (index) {
                //另存为
                0 -> {
                    val mFileOperation = FileOperation()
                    if (mFileOperation.Exists(path_file)) {
                        var mSaveFileAs = SaveFileAs()
                        mSaveFileAs.Open(mAppCompatActivity,path_file)
                    } else {
                        Toasty.error(mContext, "另存失败，你没有打开任何文件！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //重新载入
                1 -> {
                    val mFileOperation = FileOperation()
                    if (mFileOperation.Exists(path_file)) {
                        var code_text : String = mFileOperation.ReadFile(path_file)
                        mCodeEditor.setText(code_text)
                        Toasty.success(mContext, "重新载入成功！", Toast.LENGTH_SHORT, true).show()
                    } else {
                        Toasty.error(mContext, "载入失败，你没有打开任何文件！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //指定编码重新加载
                2 -> {
                    val mFileOperation = FileOperation()
                    if (mFileOperation.Exists(path_file)) {
                        val mEncodedDialog = EncodedDialog()
                        mEncodedDialog.setPath(path_file)
                        mEncodedDialog.Dialog(mContext,mCodeEditor,false)
                    } else {
                        Toasty.error(mContext, "打开选择编码器失败，你没有打开任何文件！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //指定编码保存文件
                3 -> {
                    val mFileOperation = FileOperation()
                    if (mFileOperation.Exists(path_file)) {
                        val mEncodedDialog = EncodedDialog()
                        mEncodedDialog.setPath(path_file)
                        mEncodedDialog.Dialog(mContext,mCodeEditor,true)
                    } else {
                        Toasty.error(mContext, "打开选择编码器失败，你没有打开任何文件！", Toast.LENGTH_SHORT, true).show()
                    }
                }
                //无
                else -> {
                
                }
                
              }  
            }
        })
    }
    
    /*
    //创建菜单
    public fun Menu(mView : View) {
        //初始化
        
        //菜单
        val popupMenu = PopupMenu(mContext, mView)
        popupMenu.setGravity(5)
        popupMenu.menu.add(MENU_1)
        popupMenu.menu.add(MENU_2)
        popupMenu.menu.add(MENU_3)
        popupMenu.menu.add(MENU_4)
        popupMenu.menu.add(MENU_5)
        //选中
        popupMenu.setOnMenuItemClickListener {
            
            }
            true
        }
        //显示
        popupMenu.show()           
    }
    */
    
    
}
