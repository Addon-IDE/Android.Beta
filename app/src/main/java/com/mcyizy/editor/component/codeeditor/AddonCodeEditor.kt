package com.mcyizy.editor.component.codeeditor

//
import kotlin.text.Regex
//App
import android.content.Context
import com.mcyizy.editor.component.CodeAutoCompletion
import io.github.rosemoe.sora.widget.CodeEditor
//CodeEditor
import com.mcyizy.editor.module.Event
//Function
import com.mcyizy.editor.component.function.ArrayItem
import com.mcyizy.editor.component.function.ContrastTable
import com.mcyizy.editor.component.function.Util

import android.widget.Toast


public class AddonCodeEditor(private val mContext: Context) {

    //lateinit
    private lateinit var mCodeEditor: CodeEditor
    private lateinit var mCodeAutoCompletion: CodeAutoCompletion
    //
    private var mEvent : Event? = null
    
    //设置代码编辑框
    public fun setCode(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    //加载编辑器
    public fun Editor() {
        //自动补全
        mCodeAutoCompletion = CodeAutoCompletion(mContext)
        //设置
        mCodeAutoCompletion.setCode(mCodeEditor)
        mCodeAutoCompletion.AutoCompletion()
        //基础设定
        mCodeAutoCompletion.setAllowedInputPoint(true)
        //事件监听
        mEvent = Event(mCodeEditor)
        Listener()
    }
    
    //释放
    public fun release() {
        
    }
    
    //监听器
    private fun Listener() {
        //代码编辑器事件
        val event = mEvent
        if (event != null) {
           event.setOnEventListener(object : Event.OnEventListener {
                //选择更改事件
                override fun onSelectionChangeEvent() {
                    
                }
                //发布搜索结果事件
                override fun onPublishSearchResultEvent() {
                    
                }
                //内容更改事件
                override fun onContentChangeEvent() {
                    //获取光标和文本
                    val cursor = mCodeEditor.getCursor()
                    val text = mCodeEditor.getText()
                    //获取当前行
                    var line_position = cursor.getRightLine() //获取当前行的位置
                    var content = text.getLineString(line_position) //获取当前行的内容
                    
                    val result = content.substringBefore(".")
                    if (content.contains(".")) {
                        MainArrayFunction(result)
                    } else {
                        mCodeAutoCompletion.clearItem()  
                    }
                    
                    //加载一些方法
                    MainArrayKeyword()
                    MainArrayClass()
                }
            })
        }
    }
    
    //关键字
    private fun MainArrayKeyword() {
        val mArry = arrayOf("let")
        for (item in mArry) {
            val icon_path = "image/complement/ic_box_pink.png"
            mCodeAutoCompletion.addItem(icon_path,item.replace(" ",""),"No description","Keyword")
        }
    }
    
    //相关类
    private fun MainArrayClass() {
        //获取光标和文本
        val cursor = mCodeEditor.getCursor()
        val text = mCodeEditor.getText()
        //获取当前行
        var line_position = cursor.getRightLine() //获取当前行的位置
        var content = text.getLineString(line_position) //获取当前行的内容
        //开始处理            
        if (content.contains("let")) {
            if (content.contains("=")) {
                val mArrayItem = ArrayItem(mContext)
                val mArry = mArrayItem.getAddonsManifestName()
                for (item in mArry) {
                    val icon_path = "image/complement/ic_box_light.png"
                    mCodeAutoCompletion.addItem(icon_path,item.replace(" ",""),"No description","Class")
                }
            }
        }
    }
    
    private fun MainArrayFunction(input : String) {
        if (input != "") {
            val mArrayItem = ArrayItem(mContext)
            val mArry = mArrayItem.getAddonsFileDeclare(mCodeEditor.getText().toString())
            for (item in mArry) {
                val val_name : String = getVariableName(item)
                if (val_name != "") {
                    if (input == val_name) {
                        val ClassName = getVariableFun(item)
                        MainArrayHandle2(ClassName)
                    } else {
                        mCodeAutoCompletion.clearItem()  
                    }
                } else {
                    mCodeAutoCompletion.clearItem()  
                }
            } 
        } else {
          mCodeAutoCompletion.clearItem()  
        }
    } 
    
    //addon_manifest.json 相关
    private fun MainArrayHandle2(mName : String) {
        val mArrayItem = ArrayItem(mContext)
        val mArry = mArrayItem.getAddonsManifest(mName)
        for (item in mArry) {
            MainArrayHandle3(item)
        }
    }
    
    //*.mjson 相关
    private fun MainArrayHandle3(mPathName : String) {
        val mArrayItem = ArrayItem(mContext)
        val mArry : ArrayList<String> = mArrayItem.getAddonsMjsonFun(mPathName)
        
        val mContrastTable = ContrastTable(mContext)
        
        mCodeAutoCompletion.clearItem()
        for (item in mArry) {
            val icon_path = "image/complement/ic_box_blue.png"
            
            val describe = mContrastTable.getDescribe(mPathName,item.replace(" ","").replace("(","").replace(")",""))
            mCodeAutoCompletion.addItem(icon_path,item.replace(" ",""),describe,"Function")
        }
        mCodeAutoCompletion.show()
    }
    
    //获取变量的名称
    private fun getVariableName(content : String) : String {
        var resu = ""
        val pattern = "\\blet\\b\\s+(\\w+)\\s*=.*".toRegex()
        val matchResult = pattern.find(content)
        if(matchResult != null) {
            val result = matchResult.groupValues[1]
            resu = result // 输出 "abc"
        }
        return resu
    }
    //获取类名
    private fun getVariableFun(content: String): String {
        val mUtil : Util = Util()
        val text = mUtil.InterceptingText(content,"=","(",false)
        val text2 = text.replace(" ","")
        return text2
    }

}