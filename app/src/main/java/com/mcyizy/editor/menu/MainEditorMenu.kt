package com.mcyizy.editor.menu

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import android.view.View
//
import io.github.rosemoe.sora.widget.CodeEditor
//PopupMenu
import com.mcyizy.android.widget.PopupWindow
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//function
import com.mcyizy.android.tool.SystemOperation

public class MainEditorMenu(private val mContext: Context) {

    //Lateinit
    private lateinit var mCodeEditor : CodeEditor
    
    //配置
    companion object {
        private const val MENU_1 = "复制行"
        private const val MENU_2 = "剪切行"
        private const val MENU_3 = "删除行"
        private const val MENU_4 = "清空行"
        private const val MENU_5 = "替换行"
        private const val MENU_6 = "重复行"
        private const val MENU_7 = "转为大写"
        private const val MENU_8 = "转为小写"
        private const val MENU_9 = "增加缩进"
        private const val MENU_10 = "对齐文本"
        
    }
    
    //设置编辑器
    public fun setCodeEditor(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    
    //创建菜单
    public fun Menu(mView : View) {
        //初始化
        
        //菜单
        val popupMenu = PopupWindow(mContext, mView)
        popupMenu.add(MENU_1)
        popupMenu.add(MENU_2)
        popupMenu.add(MENU_3)
        popupMenu.add(MENU_4)
        popupMenu.add(MENU_5)
        popupMenu.add(MENU_6)
        popupMenu.add(MENU_7)
        popupMenu.add(MENU_8)
        popupMenu.add(MENU_9)
        popupMenu.add(MENU_10)
        //选中
        popupMenu.OnItemListener(object : PopupWindow.OnItemListener {
            override fun onItem(index : Int) {
            when (index) {
                //复制当前行
                0 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var content = text.getLineString(line_position) //获取当前行的内容
                   SystemOperation.setClipboardText(mContext,content)
                }
                //复制当前行
                1 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   var content = text.getLineString(line_position) //获取当前行的内容
                   SystemOperation.setClipboardText(mContext,content)
                   text.delete(line_position,0,line_position,line_string_size)
                }
                //删除当前行
                2 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   if (line_position-1 >= 0 ) {
                        var Str = ""
                        Str = text.getLineString(line_position-1)
                        text.delete(line_position-1,0,line_position,line_string_size)
                        text.insert(line_position-1,0,Str)
                   } else {
                        text.delete(line_position,0,line_position,line_string_size)
                   }
                }
                //清空当前行
                3 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   text.delete(line_position,0,line_position,line_string_size)
                }
                //替换当前行
                4 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   text.delete(line_position,0,line_position,line_string_size)
                   text.insert(line_position,0,SystemOperation.getClipboardText(mContext))
                }
                //重复当前行
                5 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var content = text.getLineString(line_position) //获取当前行的内容
                   text.insert(line_position,0,content + "\n")
                }
                //转换为大写
                6 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   var content = text.getLineString(line_position) //获取当前行的内容
                   text.delete(line_position,0,line_position,line_string_size)
                   text.insert(line_position,0,content.uppercase())
                }
                //转换为小写
                7 -> {
                   val cursor = mCodeEditor.getCursor()
                   val text = mCodeEditor.getText()
                   var line_position = cursor.getRightLine() //获取当前行的位置
                   var line_string_size = text.getColumnCount(line_position) //获取当前行的文本数量
                   var content = text.getLineString(line_position) //获取当前行的内容
                   text.delete(line_position,0,line_position,line_string_size)
                   text.insert(line_position,0,content.lowercase())
                }
                //增加缩进
                8 -> {
                   val Str = "    "
                   mCodeEditor.insertText(Str,Str.length)
                }
                //对齐文本
                9 -> {
                   var str_list : Array<String> = mCodeEditor.getText().toString().split("\n").toTypedArray()
                   var str_out : String = ""
                   for (item in str_list) {
                        var Text = item.toString().trim()
                        str_out = str_out + Text + "\n"
                   }
                   val text = mCodeEditor.getText()
                   text.delete(0,mCodeEditor.getText().toString().length)
                   text.insert(0,0,str_out)
                } 
             }
            }  
        })
        //显示
        popupMenu.show()           
    }
    
}
