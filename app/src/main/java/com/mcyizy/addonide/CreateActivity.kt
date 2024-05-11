package com.mcyizy.addonide

//基础库
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
//widget
import android.widget.Toast
import android.view.View
import com.mcyizy.android.widget.ToolbarView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
//drawable
import androidx.annotation.DrawableRes
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
//Text
import android.text.TextWatcher
import android.text.Editable
//Json
import org.json.JSONObject
import org.json.JSONArray
//App
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.CompressionOperation
import com.mcyizy.addonide.module.Dialog as AppDialog
//活动
import android.app.Activity
import android.app.ActivityOptions
import com.mcyizy.addonide.CreateTypeActivity

public class CreateActivity : AppCompatActivity() {

    //App
    private var mPath : Path? = null
    private var mFileOperation : FileOperation = FileOperation()
    //控件声明
    private lateinit var editor_name : TextInputEditText
    private lateinit var editor_author : TextInputEditText
    private lateinit var editor_edition : TextInputEditText
    private lateinit var editor_describe : TextInputEditText
    private lateinit var select_template : MaterialAutoCompleteTextView
    
    //默认属性
    var Type_Type : String = "default"
    var Type_Url : String = "default_type.zip"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_create)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("创建项目")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //
        Init()
        Listener()
        InputLayoutInspect()
    }
    
    private fun Init() {
        //获取路径初始化
        mPath = Path(this)
        //控件属性
        editor_name = findViewById<TextInputEditText>(R.id.editor_name)
        editor_author = findViewById<TextInputEditText>(R.id.editor_author)
        editor_edition = findViewById<TextInputEditText>(R.id.editor_edition)
        editor_describe = findViewById<TextInputEditText>(R.id.editor_describe)
        select_template = findViewById<MaterialAutoCompleteTextView>(R.id.select_template)
        //处理编辑框左侧图标
        setDrawableStartWithTint(editor_name, R.drawable.ic_minecraft, R.color.colorDefaultIcon)
        setDrawableStartWithTint(editor_author, R.drawable.ic_image, R.color.colorDefaultIcon)
        setDrawableStartWithTint(editor_edition, R.drawable.ic_v_circle, R.color.colorDefaultIcon)
        setDrawableStartWithTint(editor_describe, R.drawable.ic_book, R.color.colorDefaultIcon)
        setDrawableStartWithTint2(select_template, R.drawable.ic_book, R.color.colorDefaultIcon)
    }
    
    //监听器
    private fun Listener() {
        //退出
        val button_return = findViewById<MaterialButton>(R.id.button_return)
        button_return.setOnClickListener {
            finishAfterTransition() 
        }
        //创建项目
        val button_create = findViewById<MaterialButton>(R.id.button_create)
        button_create.setOnClickListener {
             Inspect()
        }
        //选择模板
        select_template.setOnClickListener {
            val intent = Intent(this, CreateTypeActivity::class.java)
            val transitionName: String? = "transitions_create_type"
            val sharedElement: View = select_template
            val options = ActivityOptions.makeSceneTransitionAnimation(this as Activity, sharedElement, transitionName)
            startActivityForResult(intent, 1000, options.toBundle())
        }    
    }
    
    //输入布局检查
    private fun InputLayoutInspect() {
        //输入布局初始化
        var editor_name_layout : TextInputLayout = findViewById<TextInputLayout>(R.id.editor_name_layout)
        var editor_author_layout : TextInputLayout = findViewById<TextInputLayout>(R.id.editor_author_layout)
        var editor_edition_layout : TextInputLayout = findViewById<TextInputLayout>(R.id.editor_edition_layout)
        var editor_describe_layout : TextInputLayout = findViewById<TextInputLayout>(R.id.editor_describe_layout)
        var select_template_layout : TextInputLayout = findViewById<TextInputLayout>(R.id.select_template_layout)
        //editor_name_layout
        editor_name_layout.setErrorEnabled(true)
        editor_name_layout.setCounterEnabled(true)
        editor_name_layout.setCounterMaxLength(20)
        //editor_author_layout
        editor_author_layout.setErrorEnabled(true)
        editor_author_layout.setCounterEnabled(true)
        editor_author_layout.setCounterMaxLength(20)
        //editor_edition_layout
        editor_edition_layout.setErrorEnabled(true)
        editor_edition_layout.setCounterEnabled(true)
        editor_edition_layout.setCounterMaxLength(10)
        //editor_describe_layout
        editor_describe_layout.setErrorEnabled(true)
        editor_describe_layout.setCounterEnabled(true)
        editor_describe_layout.setCounterMaxLength(5000)
    }
    
    //检查
    private fun Inspect() {
        //转化为字符串
        var editor_name_str : String = editor_name.getText().toString().replace(" ","")
        var editor_author_str : String = editor_author.getText().toString().replace(" ","")
        var editor_edition_str : String = editor_edition.getText().toString().replace(" ","")
        var editor_describe_str : String = editor_describe.getText().toString().replace(" ","")
        var select_template_str : String = select_template.getText().toString().replace(" ","")
        //判断长度是否符合
        val inputs = mapOf(
            "项目名称" to Pair(editor_name_str, Pair(1, 20)),
            "项目作者" to Pair(editor_author_str, Pair(2, 20)),
            "项目版本" to Pair(editor_edition_str, Pair(1, 10)),
            "项目描述" to Pair(editor_describe_str, Pair(1, 5000)),
            "未选择模板" to Pair(select_template_str, Pair(1, 500))
        )

        var allInputsValid = true

        for ((inputName, inputValueAndLimits) in inputs) {
            val inputValue = inputValueAndLimits.first
            val limits = inputValueAndLimits.second
            val minLength = limits.first
            val maxLength = limits.second
            
            if (inputValue.length < minLength || inputValue.length > maxLength) {
                Toast.makeText(this, "$inputName 应该介于 $minLength 和 $maxLength 字数", Toast.LENGTH_SHORT).show()
                allInputsValid = false
                break
            }
        }
        
        if (allInputsValid) {
            // 执行方法
            StartCreate()
        }
    }
    
    //创建
    private fun StartCreate() {
        //获取控件属性
        var editor_name_str : String = editor_name.getText().toString()
        //变量
        var project_path = mPath!!.getProjectPath()
        var project_path1 = project_path + editor_name_str
        var project_path2 = project_path + editor_name_str + "/Test.zip"
        //初始化对话框
        val mAppDialog = AppDialog(this)
        Thread {
            runOnUiThread {
                //显示对话框
                mAppDialog.LoadDialog("正在创建中")
            }
            //清单文件创建
            StartCreateJson()
            //写出文件
            mFileOperation.writeAssetToFile(this,"project/project_type/${Type_Url}",project_path2)
            //解压文件
            CompressionOperation.ZIPDecompression(project_path2,project_path1)
            //清理缓存
            mFileOperation.deleteFile(project_path2)
            //生成一个文件用于刷新列表
            GenerateBufferFile()
            
            //执行完毕
            runOnUiThread {
                //关闭对话框
                mAppDialog.LoadDialogOff()
                //退出
                finishAfterTransition() 
            }
        }.start()   
    }
    
     //创建Json
    private fun StartCreateJson() {
        //获取控件属性
        var editor_name_str : String = editor_name.getText().toString()
        var editor_author_str : String = editor_author.getText().toString()
        var editor_edition_str : String = editor_edition.getText().toString()
        var editor_describe_str : String = editor_describe.getText().toString()
        //Json
        val rootObject = JSONObject()
        rootObject.put("name", editor_name_str)
        rootObject.put("author", editor_author_str)
        rootObject.put("version", editor_edition_str)
        rootObject.put("type", Type_Type)
        rootObject.put("describe", editor_describe_str)
        //写出路径
        var project_path = mPath!!.getProjectPath()
        var project_path1 = project_path + editor_name_str
        var project_path2 = project_path + editor_name_str + "/manifest.json"
        //创建目录
        mFileOperation.CreateDirectory(project_path1)
        //写出清单文件
        mFileOperation.WriteFile(project_path2,rootObject.toString(4))
    }
    
    //生成一个缓冲文件，用于回到主界面刷新列表
    private fun GenerateBufferFile() {
        val cacheDir = applicationContext.externalCacheDir?.absolutePath
        //创建目录
        mFileOperation.CreateDirectory(cacheDir.toString())
        //写出清单文件
        mFileOperation.WriteFile(cacheDir + "/RefreshProjectList","")
    }
    
    //动态设置 drawableStart 的 tint 颜色
    private fun setDrawableStartWithTint(textView: TextInputEditText, @DrawableRes drawableResId: Int, @ColorRes tintColorResId: Int) {
        val drawable = AppCompatResources.getDrawable(textView.context, drawableResId)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(textView.context, tintColorResId))
        textView.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null)
    }
    private fun setDrawableStartWithTint2(textView: MaterialAutoCompleteTextView, @DrawableRes drawableResId: Int, @ColorRes tintColorResId: Int) {
        val drawable = AppCompatResources.getDrawable(textView.context, drawableResId)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(textView.context, tintColorResId))
        textView.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null)
    }
    
    //获得返回数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                val returnedText = data?.getStringExtra("type_name")
                val returnedText2 = data?.getStringExtra("type_type")
                val returnedText3 = data?.getStringExtra("type_url")
                Type_Type = returnedText2.toString()
                Type_Url = returnedText3.toString()
                select_template.setText(returnedText.toString())
            }
        }
    }
    
}