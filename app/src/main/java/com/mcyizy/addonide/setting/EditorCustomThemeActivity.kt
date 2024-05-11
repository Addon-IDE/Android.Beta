package com.mcyizy.addonide.setting

//基础库
import com.mcyizy.addonide.R
import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.Context
import android.graphics.Color
//
import com.mcyizy.android.widget.ToolbarView
//视图
import android.view.View
import android.widget.TextView
import android.widget.ListView
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.view.ViewGroup.LayoutParams
//对话框
import androidx.appcompat.app.AlertDialog
//列表适配器
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.AbsListView
//Json
import org.json.JSONObject
import org.json.JSONArray
//App
import com.mcyizy.addonide.main.Path
import com.mcyizy.android.ViewComponent
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.android.tool.ColorOperation
import com.mcyizy.android.utils.FileHelper
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.setting.SettingOperate
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//ColorPicker
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder

public class EditorCustomThemeActivity : AppCompatActivity() {

    //列表
    private lateinit var mListView: ListView
    private lateinit var Adapter: ArrayAdapter<String>
    private lateinit var Items: Array<String>
    private lateinit var Items2: Array<String>
    //
    val mFileOperation: FileOperation = FileOperation()
    //
    var CustomThemeIndexString = ""
    var CustomThemeNameString = ""
    var CustomThemePathString = ""
    var night_switch_text = false
    var font_file_path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_setting_editor_customtheme)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //窗口动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        //工具栏
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("自定义主题")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finish() 
            }
            override fun onMenu(view : View) {
            }
        })
        //加载监听器
        ButtonListener()
        //初始化
        mListView = findViewById<ListView>(R.id.list_view)
        RPath()
        if (inspect()) {
            Conversion("theme")
            List()
        }
    }
    
    //按钮监听器
    private fun ButtonListener() {
        //切换
        val switch_button = findViewById<LinearLayout>(R.id.switch_button)
        val switch_text = findViewById<TextView>(R.id.switch_text)
        switch_button.setOnClickListener {
            if (night_switch_text) {
                night_switch_text = false
                switch_text.setText("夜间模式")
                if (inspect()) {
                    Conversion("theme")
                    List()
                }
            } else {
                night_switch_text = true
                switch_text.setText("日间模式")
                if (inspect()) {
                    Conversion("theme_night")
                    List()
                }
            }
        }
        //创建键
        val switch_add = findViewById<LinearLayout>(R.id.switch_add)
        switch_add.setOnClickListener {
            AddKeyDialog()
        }
        //字体
        val switch_font = findViewById<LinearLayout>(R.id.switch_font)
        switch_font.setOnClickListener {
            //路径初始化
            val mPath = Path(this)
            val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
            val path_list_path_manifest = path_list_path + "manifest.json"
            //弹窗
            val builder_font = AlertDialog.Builder(this)
            builder_font.setTitle("字体")
            builder_font.setMessage("①不使用字体：编辑器将不会采用任何字体\n②选择字体：编辑器将会使用你选择的字体，如果当前主题已存在字体则进行替换")
            .setNeutralButton("不使用字体") { dialog, _ ->
                //json
                val json_string : String = mFileOperation.ReadFile(path_list_path_manifest)
                val json_string2 : JSONObject = JSONObject(json_string)
                //删除对应文件
                val Font_Ttf_File : String = path_list_path + json_string2.getString("font")
                mFileOperation.deleteFile(Font_Ttf_File)
                //删除键
                if (json_string2.has("font")) {
                    json_string2.remove("font")
                    mFileOperation.WriteFile(path_list_path_manifest,json_string2.toString(4))
                }
                Toasty.normal(this, "字体删除完毕，已保存！").show()
            }
            .setNegativeButton("选择字体") { dialog, _ ->
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*" // 无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 1)
            }
            .setPositiveButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder_font.create()
            dialog.show()    
        }
    }
    
    //读取
    private fun RPath() {
        //获取路径
        CustomThemeIndexString = intent.getStringExtra("CustomThemeIndex").toString()
        CustomThemeNameString = intent.getStringExtra("CustomThemeName").toString()
    }
    
    //刷新列表
    private fun Refresh() {
        if (!night_switch_text) {
            if (inspect()) {
                Conversion("theme")
                List()
            }
        } else {
            if (inspect()) {
                Conversion("theme_night")
                List()
            }
        }
    }
    
    //转换
    private fun Conversion(thName : String) {
        //处理路径
        val mPath = Path(this)
        val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
        val path_list_path_manifest = path_list_path + "manifest.json"
        //读取文件
        val jsonpath = path_list_path + ReadManifest(path_list_path_manifest,thName)
        CustomThemePathString = jsonpath
        val jsonString = mFileOperation.ReadFile(jsonpath)
        val jsonObject = JSONObject(jsonString)
        //开始转换
        val list = ArrayList<String>()
        val list2 = ArrayList<String>()
        //批量处理key与list
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next() // 获取键名
            val value = jsonObject.getString(key) // 获取对应的值
            
            list.add(key)
            list2.add(value)
        }
        Items = list.toTypedArray()
        Items2 = list2.toTypedArray()
    }
    
    //解析清单文件
    private fun ReadManifest(mpath : String,name : String) : String {
        val jsonObject = JSONObject(mFileOperation.ReadFile(mpath))
        val name2 = jsonObject.getString(name)
        return name2
    }
    
    //检查
    private fun inspect() : Boolean {
        //
        val mPath = Path(this)
        val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
        val path_list_path_manifest = path_list_path + "manifest.json"
        var output = false
        //检查文件是否存在
        if (mFileOperation.Exists(path_list_path_manifest)) {
            if (SettingOperate.isJsonValid(mFileOperation.ReadFile(path_list_path_manifest))) {
                val jsonObject = JSONObject(mFileOperation.ReadFile(path_list_path_manifest))
                if (jsonObject.has("theme")) {
                    if (jsonObject.has("theme_night")) {
                        val path_list_path_theme = path_list_path + jsonObject.getString("theme")
                        val path_list_path_theme_night = path_list_path + jsonObject.getString("theme_night")
                        if (mFileOperation.Exists(path_list_path_theme)) {
                            if (mFileOperation.Exists(path_list_path_theme_night)) {
                                output = true
                            } else {
                                output = false
                                Toasty.error(this, "夜间主题虽然定义了，但是没有找到相应文件！", Toast.LENGTH_SHORT, true).show()
                            }
                        } else {
                            output = false
                            Toasty.error(this, "日间主题虽然定义了，但是没有找到相应文件！", Toast.LENGTH_SHORT, true).show()
                        }
                    } else {
                        output = false
                        Toasty.error(this, "在清单文件中未找到夜间主题的定义！", Toast.LENGTH_SHORT, true).show()
                    }
                } else {
                    output = false
                    Toasty.error(this, "在清单文件中未找到日间主题的定义！", Toast.LENGTH_SHORT, true).show()
                }
                //判断字体
                if (jsonObject.has("font")) {
                    val path_list_path_theme_night = path_list_path + jsonObject.getString("theme_night")
                    if (mFileOperation.Exists(path_list_path_theme_night)) {
                    
                    } else {
                        Toasty.warning(this, "清单文件中虽然定义了字体，但没有找到字体文件，编辑器将加载字体失败！", Toast.LENGTH_SHORT, true).show()
                    }
                } else {
                    
                }
            } else {
                output = false
                Toasty.error(this, "清单文件中JSON不合法异常！", Toast.LENGTH_SHORT, true).show()
            }
        } else {
            output = false
            Toasty.error(this, "清单文件不存在加载失败！", Toast.LENGTH_SHORT, true).show()
        }
        //
        if (!output) {
            
        }
        
        return output
    }
    
    //加载列表
    private fun List() {
        //创建适配器
        Adapter = ArrayAdapter(this, R.layout.list_editor_theme_color, Items)
        //设置适配器
        mListView.adapter = Adapter
        //刷新适配器
        Adapter.notifyDataSetChanged()
        //自定义适配器
        class CustomAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.list_editor_theme_color, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_editor_theme_color, parent, false)
                }
                //设置名称
                val textView = view!!.findViewById<TextView>(R.id.name_view)
                textView.setHorizontallyScrolling(true)  // 启用水平滚动
                textView.isSelected = true
                textView.setText(getZhLanguage(Items[position].toString()))
                //设置颜色
                val color_view = view!!.findViewById<LinearLayout>(R.id.color_view)
                val color_int = Color.parseColor(Items2[position])
                color_view.setBackgroundColor(color_int)
                val listComponent = view!!.findViewById<RelativeLayout>(R.id.list_component)
                //设置水波纹
                val mViewComponent = ViewComponent()
                mViewComponent.WaterRippleEffect(this@EditorCustomThemeActivity,listComponent)
                //列表点击监听事件
                listComponent.setOnClickListener {
                    //打开对话框
                    ColorPickerDialog(Items[position],color_view)
                }
                //列表长按监听事件
                listComponent.setOnLongClickListener {
                    //打开对话框
                    DeleteDialog(Items[position])
                    true
                }
                return view
            }
        }
        //使用自定义适配器
        val customAdapter = CustomAdapter(this, Items)
        mListView.adapter = customAdapter
    }
    
    //创建键
    private fun AddKeyDialog() {
        //初始化
        val builder_key = AlertDialog.Builder(this)
        var key_list : Array<String> = arrayOf()
        var key_list2 : Array<String> = arrayOf()
        val key_list_temp = ArrayList<String>()
        val key_list_temp2 = ArrayList<String>()
        var json_array = JSONArray(mFileOperation.ReadResourceFile(this,"editor/Theme.json"))
        //开始处理
        var mi = 0
        while (mi < json_array.length()) {
            key_list_temp.add(json_array.optString(mi))
            key_list_temp2.add(getZhLanguage(json_array.optString(mi)))
            mi++
        }
        key_list = key_list_temp.toTypedArray()
        key_list2 = key_list_temp2.toTypedArray()
        //
        builder_key.setTitle("创建键")
        .setItems(key_list2) { dialog, which ->
            //添加
            val json_string : String = mFileOperation.ReadFile(CustomThemePathString)
            val json_string2 : JSONObject = JSONObject(json_string)
            val key_list_string : String = key_list[which]
            if (json_string2.has(key_list_string)) {
                AddDialog(key_list_string)
            } else {
                json_string2.put(key_list_string,"#FFFFFF")
                mFileOperation.WriteFile(CustomThemePathString,json_string2.toString(4))
                //提示
                Toasty.normal(this, "创建完毕，已保存！").show()
                //刷新列表
                Refresh()
            }
        }
        .setPositiveButton("取消") { dialog, _ ->
            //
            dialog.dismiss()
            
        }
        val dialog = builder_key.create()
        dialog.show()
    }
    
    //颜色编辑
    private fun ColorPickerDialog(mName : String,mLinearLayout : LinearLayout) {
        val json_string : String = mFileOperation.ReadFile(CustomThemePathString)
        val json_string2 : JSONObject = JSONObject(json_string)
        ColorPickerDialogBuilder
            .with(this)
            .setTitle("编辑颜色")
            .initialColor(-1)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton("确定") { dialog, selectedColor, allColors ->
                //设置
                val selectedColor_str = ColorOperation.getColorString(selectedColor)
                json_string2.put(mName,selectedColor_str)
                mFileOperation.WriteFile(CustomThemePathString,json_string2.toString(4))
                //给列表设置背景颜色
                mLinearLayout.setBackgroundColor(selectedColor)
                //提示
                Toasty.normal(this, "编辑完毕，已保存！").show()
            }
            .setNegativeButton("取消") { dialog, which -> 
            }
            .build()
            .show()
    }
    
    //删除
    private fun DeleteDialog(mName : String) {
        val builder = AlertDialog.Builder(this)
        val json_string : String = mFileOperation.ReadFile(CustomThemePathString)
        val json_string2 : JSONObject = JSONObject(json_string)
        builder.setTitle("是否删除？")
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, _ ->
                //删除
                json_string2.remove(mName)
                mFileOperation.WriteFile(CustomThemePathString,json_string2.toString(4))
                //提示
                Toasty.normal(this, "删除完毕，已保存！").show()
                //刷新列表
                Refresh()
            }
        val dialog = builder.create()
        dialog.show()    
    }
    
    //如果有相同的键就会弹出
    private fun AddDialog(mName : String) {
        val builder = AlertDialog.Builder(this)
        val json_string : String = mFileOperation.ReadFile(CustomThemePathString)
        val json_string2 : JSONObject = JSONObject(json_string)
        builder.setTitle("提示")
        builder.setMessage("当前项目中有相同的键，${mName}键存在是否覆盖？")
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("覆盖") { dialog, _ ->
                json_string2.put(mName,"#FFFFFF")
                mFileOperation.WriteFile(CustomThemePathString,json_string2.toString(4))
                //提示
                Toasty.normal(this, "覆盖完毕，已保存！").show()
                //刷新列表
                Refresh()
            }
        val dialog = builder.create()
        dialog.show()    
    }
    
    //根据键名来获取他的中文
    private fun getZhLanguage(key_name : String) : String {
        val json_string : String = mFileOperation.ReadResourceFile(this,"editor/Theme_Zh.json")
        val json_string2 : JSONObject = JSONObject(json_string)
        if (json_string2.has(key_name)) {
            return json_string2.getString(key_name)
        }
        return key_name
    }
    
    //软件回调事件
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            if (uri != null) {
                //字体
                font_file_path = FileHelper.getFileAbsolutePath(this, uri)
                if (font_file_path != null) {
                    //变量
                    val mPath = Path(this)
                    val path_list_path = mPath.getThemePath() + CustomThemeNameString + "/"
                    val path_list_path_manifest = path_list_path + "manifest.json"
                    //json
                    val json_string : String = mFileOperation.ReadFile(path_list_path_manifest)
                    val json_string2 : JSONObject = JSONObject(json_string)
                    json_string2.put("font","font.ttf")
                    //开始复制文件
                    if (json_string2.has("font")) {
                        val path_list_path_font_file = path_list_path + json_string2.getString("font")
                        val output_boolean = mFileOperation.CopyFile(font_file_path.toString(),path_list_path_font_file)
                        if (output_boolean) {
                            Toasty.normal(this, "字体已选择并保存成功！").show()
                        } else {
                            Toasty.error(this, "字体选择复制时出现了错误！", Toast.LENGTH_SHORT, true).show()
                        }
                    }
                    //文件
                    mFileOperation.WriteFile(path_list_path_manifest,json_string2.toString(4))
                }
            }
        }
    }
      
}
