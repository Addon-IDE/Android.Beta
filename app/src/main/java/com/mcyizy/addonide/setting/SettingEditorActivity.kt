package com.mcyizy.addonide.setting

//基础库
import android.os.Bundle
import com.mcyizy.addonide.R
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.View
//widget
import com.mcyizy.android.widget.ToolbarView
import com.suke.widget.SwitchButton
//App
import com.mcyizy.android.foundation.ApplicationWindow
import com.mcyizy.addonide.setting.SettingOperate

public class SettingEditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置视图
        setContentView(R.layout.activity_setting_editor)
        //设置状态栏
        ApplicationWindow.StatusBar(this,0)
        //窗口动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        //Toolbar
        val toolbar_view = findViewById<ToolbarView>(R.id.toolbar_view)
        toolbar_view.setTitle("编辑器设置")
        toolbar_view.OnReturnListener(object : ToolbarView.OnReturnListener {
            override fun onReturn() {
               finishAfterTransition() 
            }
            override fun onMenu(view : View) {
            }
        })
        //加载监听器
        ButtonListener()
        SwitchListener()
    }
    
    //按钮监听
    private fun ButtonListener() {
        //获取控件对象
        val setting_button1 = findViewById<SettingListView>(R.id.setting_button1)
        val setting_button2 = findViewById<SettingListView>(R.id.setting_button2)
        val setting_button3 = findViewById<SettingListView>(R.id.setting_button3)
        //字体大小
        setting_button1.setOnClickListener {
            FontSizeDialog()
        }
        //Tab宽度
        setting_button2.setOnClickListener {
            TabDialog()
        }
        //显示不可打印字符
        setting_button3.setOnClickListener {
            FlagDialog()
        }
    }
    
    //开关监听
    private fun SwitchListener() {
        //获取控件对象
        val setting_button4 = findViewById<SettingListView>(R.id.setting_button4)
        val setting_button5 = findViewById<SettingListView>(R.id.setting_button5)
        val setting_button6 = findViewById<SettingListView>(R.id.setting_button6)
        val setting_button7 = findViewById<SettingListView>(R.id.setting_button7)
        val setting_button8 = findViewById<SettingListView>(R.id.setting_button8)
        val setting_button9 = findViewById<SettingListView>(R.id.setting_button9)
        //设置属性
        setting_button4.setSwitch(SettingOperate.getValue(this,"SiameseWordEditor"))
        setting_button5.setSwitch(SettingOperate.getValue(this,"WordWrapEditor"))
        setting_button6.setSwitch(SettingOperate.getValue(this,"MagnifierEditor"))
        setting_button7.setSwitch(SettingOperate.getValue(this,"SafetyInputEditor"))
        setting_button8.setSwitch(SettingOperate.getValue(this,"FixedLineEditor"))
        setting_button9.setSwitch(SettingOperate.getValue(this,"AutocompleteEditor"))
        //连体字
        setting_button4.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
                SettingOperate.setValue(this@SettingEditorActivity,"SiameseWordEditor",isChecked)
            }
        })
        //自动换行
        setting_button5.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
                SettingOperate.setValue(this@SettingEditorActivity,"WordWrapEditor",isChecked)
            }
        })
        //显示放大镜
        setting_button6.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
               SettingOperate.setValue(this@SettingEditorActivity,"MagnifierEditor",isChecked) 
            }
        })
        //安全输入
        setting_button7.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
                SettingOperate.setValue(this@SettingEditorActivity,"SafetyInputEditor",isChecked)
            }
        })
        //固定行数
        setting_button8.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
                SettingOperate.setValue(this@SettingEditorActivity,"FixedLineEditor",isChecked)
            }
        })
        //自动补全
        setting_button9.isSwitch().setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
                SettingOperate.setValue(this@SettingEditorActivity,"AutocompleteEditor",isChecked)
            }
        })
    }
    
    //selectedOption
    var selectedOption = 0
    var selectedOption2 = 0
    
    //字体大小调节
    private fun FontSizeDialog() {
        //配置
        var array_list : Array<String> = arrayOf()
        val list = ArrayList<String>()
        for (i in 6..32) {
            list.add(i.toString())
        }
        //获取设置
        selectedOption = SettingOperate.getValue(this,"FontSizeEditor")
        array_list = list.toTypedArray()
        //弹窗
        val builder = AlertDialog.Builder(this)
        builder.setTitle("字体大小")
            .setSingleChoiceItems(array_list, selectedOption) { dialog, which ->
                selectedOption = which // 更新当前选中的选项索引
            }
            .setNeutralButton("重置") { dialog, id ->
                SettingOperate.setValue(this,"FontSizeEditor",10)
            }
            .setNegativeButton("取消") { dialog, id ->
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, id ->
                SettingOperate.setValue(this,"FontSizeEditor",selectedOption.toInt())
            }
        val dialog = builder.create()
        dialog.show()
    }
    
    //Tab 宽度
    private fun TabDialog() {
        //配置
        var array_list : Array<String> = arrayOf()
        val list = ArrayList<String>()
        list.add("2")
        list.add("4")
        list.add("6")
        list.add("8")
        //获取设置
        selectedOption2 = SettingOperate.getValue(this,"TabWidthEditor")
        array_list = list.toTypedArray()
        //弹窗
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tab 宽度")
            .setSingleChoiceItems(array_list, selectedOption2) { dialog, which ->
                selectedOption2 = which // 更新当前选中的选项索引
            }
            .setNegativeButton("取消") { dialog, id ->
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, id ->
                SettingOperate.setValue(this,"TabWidthEditor",selectedOption2.toInt())
            }
        val dialog = builder.create()
        dialog.show()
    }
    
    //显示不可打印字符
    private fun FlagDialog() {
        //配置
        var array_list : Array<String> = arrayOf()
        val list = ArrayList<String>()
        val list2 = ArrayList<String>()
        list.add("Leading")
        list.add("Triling")
        list.add("Inner")
        list.add("Empty lines")
        list.add("Line breaks")
        //获取设置
        val selectedOption3 : String = SettingOperate.getValue(this,"FlagsDrawEditor")
        array_list = list.toTypedArray()
        //弹窗
        val builder = AlertDialog.Builder(this)
        builder.setTitle("显示不可打印字符")
        builder.setMultiChoiceItems(array_list, booleanArrayOf(
                                    if (selectedOption3.contains("0")) true else false,
                                    if (selectedOption3.contains("1")) true else false,
                                    if (selectedOption3.contains("2")) true else false,
                                    if (selectedOption3.contains("3")) true else false,
                                    if (selectedOption3.contains("4")) true else false)) { dialog, which, isChecked ->
            if (isChecked) {
                list2.add(which.toString())
            } else {
                list2.remove(which.toString())
            }
        }
        builder.setNegativeButton("取消") { dialog, id ->
            dialog.dismiss()
        }
        builder.setPositiveButton("确定") { dialog, id ->
            SettingOperate.setValue(this,"FlagsDrawEditor",list2.toString())
        }
        val dialog = builder.create()
        dialog.show()
    }
    
}
