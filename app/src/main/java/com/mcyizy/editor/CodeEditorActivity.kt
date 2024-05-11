package com.mcyizy.editor

//基础库
import com.mcyizy.addonide.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
//widget
import android.view.View
import android.widget.Toast
import android.view.Gravity
import android.widget.EditText
import android.widget.ListView
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.mcyizy.editor.ToolbarView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.drawerlayout.widget.DrawerLayout
//弹窗
import com.mcyizy.addonide.module.Dialog
//App
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.main.DataStorage
//CodeEditor
import io.github.rosemoe.sora.widget.CodeEditor
//Module
import com.mcyizy.editor.module.Event
import com.mcyizy.editor.module.Toolbar
import com.mcyizy.editor.module.File
import com.mcyizy.editor.module.SaveFileAs
//Editor
import com.mcyizy.editor.list.FileList
import com.mcyizy.editor.list.FileShortcutList
import com.mcyizy.editor.list.SymbolsList
//Menu
import com.mcyizy.editor.menu.CodeMain
import com.mcyizy.editor.menu.CreateFileMenu
import com.mcyizy.editor.menu.OperateFileMenu

import com.mcyizy.editor.ProjectType as ProjectTypeEditor

public class CodeEditorActivity : AppCompatActivity() {
    
    //控件初始化
    private lateinit var mToolbarView : ToolbarView
    private lateinit var mCodeEditor : CodeEditor
    //编辑器模块
    private var mEvent : Event? = null
    private var mToolbar : Toolbar? = null
    private var mFile : File? = null
    private var mSaveFileAs : SaveFileAs = SaveFileAs()
    //编辑器List模块
    private var mFileList : FileList? = null
    private var mFileShortcutList : FileShortcutList? = null
    private var mSymbolsList : SymbolsList? = null
    //菜单
    private var mCodeMain : CodeMain? = null
    private var mCreateFileMenu : CreateFileMenu? = null
    private var mOperateFileMenu : OperateFileMenu? = null
    //配置
    private var temp_path_file : String = ""
    private var isReadEditor : Boolean = false
    private var tab_save_locked : Boolean = false
    //设定
    private var tab_save_before_switching : Boolean = true //tab切换前保存
    private var file_save_before_switching : Boolean = true //列表切换前保存

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_editor_code)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //初始化
        Init()
    }
    
    //初始化
    private fun Init() {
        //控件初始化
        mToolbarView = findViewById(R.id.toolbar_view)
        mCodeEditor = findViewById(R.id.code_editor_view)
        //模块初始化
        mToolbar = Toolbar(mCodeEditor)
        mEvent = Event(mCodeEditor)
        mFile = File()
        //菜单初始化
        mCodeMain = CodeMain(this)
        mCreateFileMenu = CreateFileMenu(this)
        mOperateFileMenu = OperateFileMenu(this)
        //加载方法
        Function()
        Listener()
        FileList()
        FileShortcutList()
        SymbolsList()
        //进行基础配置
        UnopenedFilePrompt(true)
        
        //编辑器
        val mProjectTypeEditor = ProjectTypeEditor(this)
        mProjectTypeEditor.setCode(mCodeEditor)
        mProjectTypeEditor.Project(intent.getStringExtra("ProjectPath").toString())
    }
    
    //功能
    private fun Function() {
        //File初始化
        val file = mFile
        if (file != null) {
            file.setCode(mCodeEditor)
            file.setContext(this@CodeEditorActivity)
        }
        //文件列表返回上一级
        val up_button = findViewById<LinearLayout>(R.id.up_button)
        up_button.setOnClickListener {
            val fileList = mFileList
            fileList!!.ReturnSuperior()
        }
        //创建菜单
        val add_menu_button = findViewById<LinearLayout>(R.id.add_menu_button)
        add_menu_button.setOnClickListener {
            val createFileMenu = mCreateFileMenu
            val fileList = mFileList
            createFileMenu!!.setPath(fileList!!.getPath())
            createFileMenu!!.setPathDefault(intent.getStringExtra("ProjectPath").toString())
            createFileMenu!!.setList(fileList)
            createFileMenu!!.CreateMenu(add_menu_button)
        }
    }
    
    //监听器
    private fun Listener() {
        //工具栏监听器
        mToolbarView.setOnToolbarListener(object : ToolbarView.OnToolbarListener {
            //打开文件侧滑
            override fun onButtonFile() {
                FileDrawer()
            }
            //可读与编辑切换
            override fun onButton1() {
                if (isReadEditor) {
                    isReadEditor = false
                    mCodeEditor.setEditable(!isReadEditor)
                    mToolbarView.Button1_Icon(true)
                } else {
                    isReadEditor = true
                    mCodeEditor.setEditable(!isReadEditor)
                    mToolbarView.Button1_Icon(false)
                }
            }
            //运行
            override fun onButton2() {
            }
            //撤销
            override fun onButton3() {
                mToolbar!!.undo()
            }
            //重做
            override fun onButton4() {
                mToolbar!!.redo()
            }
            //菜单
            override fun onMenuButton(mView : View) {
                val codeMain = mCodeMain
                codeMain!!.setActivity(this@CodeEditorActivity)
                codeMain!!.setPath(temp_path_file)
                codeMain!!.setPathDefault(intent.getStringExtra("ProjectPath").toString())
                codeMain!!.setCodeEditor(mCodeEditor)
                codeMain!!.Menu(mView)
            }
        })
        //代码编辑器事件
        val event = mEvent
        if (event != null) {
           event.setOnEventListener(object : Event.OnEventListener {
                //选择更改事件
                override fun onSelectionChangeEvent() {
                    PositionDisplay()
                }
                //发布搜索结果事件
                override fun onPublishSearchResultEvent() {
                    PositionDisplay()
                }
                //内容更改事件
                override fun onContentChangeEvent() {
                    
                }
            })
        }
        //
    }
    
    //显示光标位置信息
    private fun PositionDisplay() {
        val cursor = mCodeEditor.cursor
        var text = (1 + cursor.leftLine).toString() + ":" + cursor.leftColumn.toString()
        mToolbarView.setPositionDisplay(text)
    }
    
    //文件列表加载
    private fun FileList() {
        val receivedString = intent.getStringExtra("ProjectPath").toString()
       //加载列表
        val file_list = findViewById<ListView>(R.id.file_list)
        val file_list_refresh = findViewById<SwipeRefreshLayout>(R.id.file_list_refresh)
        val search_editor = findViewById<EditText>(R.id.search_editor)
        mFileList = FileList(this,file_list)
        val fileList = mFileList
        if (fileList != null) {
           fileList.setPath(receivedString)
           fileList.List()
           fileList.SwipeRefresh(file_list_refresh)
           fileList.Search(search_editor)
        }
        //监听器
        fileList!!.setOnListener(object : FileList.OnListener {
            var mcontext = this
            //项目选中时
            override fun onClick(Path: String) {
                //保存文件
                val mFileOperation = FileOperation()
                if (file_save_before_switching && mFileOperation.Exists(temp_path_file)) {
                    mFileOperation.WriteFile(temp_path_file,mCodeEditor.getText().toString())
                }
                //
                tab_save_locked = false
                temp_path_file = Path
                //加入快捷栏
                val fileShortcutList = mFileShortcutList
                fileShortcutList!!.addItem(Path)
                //打开文件 初始化
                val file = mFile
                file!!.setPath(Path)
                //打开文件
                FileDrawer()
                file!!.Open()
                //关闭"未打开任何文件"面板
                UnopenedFilePrompt(false)
                //
                tab_save_locked = true
                //存储于本地，方便后面调用
                val mDataStorage = DataStorage(this@CodeEditorActivity)
                mDataStorage.Write("Editor_File_Type",mFileOperation.getFileSuffixName(Path))
            }
            //项目长按时
            override fun onLongClick(Path: String,mView : View) {
                val operateFileMenu = mOperateFileMenu
                operateFileMenu!!.setPath(Path)
                operateFileMenu!!.setList(fileList)
                operateFileMenu!!.OperateMenu(mView)
            }
        })
    }
    //文件快捷栏加载
    private fun FileShortcutList() {
        val file_shortcut_list = findViewById<TabLayout>(R.id.file_shortcut_list)
        mFileShortcutList = FileShortcutList(this,file_shortcut_list)
        val fileShortcutList = mFileShortcutList
        //监听器
        fileShortcutList!!.setOnListener(object : FileShortcutList.OnListener {
            //项目为空时
            override fun onEmpty() {
                UnopenedFilePrompt(true)
                temp_path_file = ""
            }
            //项目被选中时
            override fun onSelect(index: Int) {
                
            }
            override fun onSelect2(Text: String) {
                //保存文件
                val mFileOperation = FileOperation()
                if (tab_save_locked && tab_save_before_switching && mFileOperation.Exists(temp_path_file)) {
                    mFileOperation.WriteFile(temp_path_file,mCodeEditor.getText().toString())
                }
                //设置
                temp_path_file = Text
                //打开文件
                val file = mFile
                file!!.setPath(Text)
                file!!.Open()
                //存储于本地，方便后面调用
                val mDataStorage = DataStorage(this@CodeEditorActivity)
                mDataStorage.Write("Editor_File_Type",mFileOperation.getFileSuffixName(Text))
                //Toast.makeText(this@CodeEditorActivity, "cyvycvycg", Toast.LENGTH_SHORT).show()
            }
        })
    } 
    //符号栏列表加载
    private fun SymbolsList() {
        val symbols_list = findViewById<LinearLayout>(R.id.symbols_list)
        mSymbolsList = SymbolsList(this,symbols_list,mCodeEditor)
        val symbolsList = mSymbolsList
        if (symbolsList != null) {
            
        }  
    }
    //显示没有打开任何文件面板
    private fun UnopenedFilePrompt(value: Boolean) {
        val unopened_file_prompt = findViewById<LinearLayout>(R.id.unopened_file_prompt)
        if (value) {
            unopened_file_prompt.visibility = View.VISIBLE
            //隐藏代码编辑器和清空代码
            mCodeEditor.setText("")
            mCodeEditor.visibility = View.GONE
            //关闭编辑器语言
            mCodeEditor.setEditorLanguage(null)
            //重置显示列与行显示器
            mToolbarView.setPositionDisplay("-:-")
        } else {
            unopened_file_prompt.visibility = View.GONE
            //重新显示
            mCodeEditor.visibility = View.VISIBLE
        }
    }
    //侧滑栏功能
    private fun FileDrawer() {
        val mDrawerLayout = findViewById<DrawerLayout>(R.id.file_drawer_layout)
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT)
        }
    }
    
    //<------继承Activity------>
    //返回结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //另存为返回
        if (requestCode == 1001 && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null && data.data != null) {
                // 在这里处理返回的数据
                mSaveFileAs.Save(this,data,mCodeEditor)
            }
        }
    }
    
    
}