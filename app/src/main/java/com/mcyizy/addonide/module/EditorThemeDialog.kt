package com.mcyizy.addonide.module

import com.mcyizy.addonide.R
import android.content.Context
import android.content.Intent
import com.mcyizy.addonide.main.Path
import androidx.appcompat.app.AlertDialog
import java.io.File
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.android.tool2.CompressionOperation
import com.mcyizy.addonide.setting.SettingOperate
//Toasty
import android.widget.Toast
import es.dmoral.toasty.Toasty
//设置活动
import com.mcyizy.addonide.setting.EditorCustomThemeActivity

public class EditorThemeDialog(private val mContext: Context) {

    // 创建AlertDialog.Builder对象
    val builder = AlertDialog.Builder(mContext)
    val builder2 = AlertDialog.Builder(mContext)
    val builder3 = AlertDialog.Builder(mContext)
    
    //
    val mFileOperation : FileOperation = FileOperation()
    //列表
    var array_list : Array<String> = arrayOf()
    var array_list2 : Array<String> = arrayOf()
    var array_list3 : Array<String> = arrayOf("不使用字体","JetBrainsMono-Regular","consolas","Roboto-Regular")
    var selectedOption = 0
    var selectedOption2 = 0
    var selectedOption3 = 0
    var isNight = false
    
    
    //选项
    public fun Option() {
        //自定义主题
        selectedOption2 = SettingOperate.getValue(mContext,"CustomThemeEditorId")
        //字体
        selectedOption3 = SettingOperate.getValue(mContext,"FontEditor")
    }
    
    //判断
    public fun Switch(boolean : Boolean) {
        isNight = !boolean
        if (boolean) {
            //日间主题
            array_list = arrayOf("Eclipse","GitHub","NotepadXX","QuietLight")
            selectedOption = SettingOperate.getValue(mContext,"DayEditor")
        } else {
            //夜间主题
            array_list = arrayOf("Darcula","VS2019","AtomOneDark")
            selectedOption = SettingOperate.getValue(mContext,"NightEditor")
        }
    }
    
    //加载对话框
    public fun Dialog() {
        //标题
        builder.setTitle("编辑器主题")
            .setSingleChoiceItems(array_list, selectedOption) { dialog, which ->
                selectedOption = which // 更新当前选中的选项索引
            }
            .setNeutralButton("字体") { dialog, id ->
                // 新建按钮被点击
                FontDialog()
            }
            .setNegativeButton("自定义") { dialog, id ->
                // 自定义按钮被点击
                val mPath = Path(mContext)
                val str_path = mPath.getThemePath()
                //文件转换为列表数组
                val list = ArrayList<String>()
                val ff = File(str_path).listFiles()
                if (ff != null) {
                    for (file in ff) {
                       if (mFileOperation.isDirectory(file.absolutePath)) {
                           list.add(mFileOperation.getFileNmae(file.absolutePath)) 
                       }
                    }
                }
                array_list2 = list.toTypedArray()
                //加载对话框
                CustomizeDialog()
            }
            .setPositiveButton("确定") { dialog, id ->
                // 确定按钮被点击
                SettingOperate.setValue(mContext,"CustomThemeEditor",false)
                SettingOperate.setValue(mContext,"CustomThemeEditorId", -1)
                if (isNight) {
                    SettingOperate.setValue(mContext,"NightEditor",selectedOption)
                } else {
                    SettingOperate.setValue(mContext,"DayEditor",selectedOption)
                }
            }
        val dialog = builder.create()
        dialog.show()
    }
    
    //加载自定义对话框
    public fun CustomizeDialog() {
        //标题
        builder2.setTitle("主题自定义")
            .setSingleChoiceItems(array_list2, selectedOption2) { dialog, which ->
                selectedOption2 = which // 更新当前选中的选项索引
            }
            .setNeutralButton("新建") { dialog, id ->
                val mPath = Path(mContext)
                val path_theme_path = mPath.getThemePath()
                if (mFileOperation.writeAssetToFile(mContext,"editor/CustomThemes.zip",path_theme_path + "CustomThemes.zip")) {
                    var size_list = mFileOperation.FetchSubfileCollection(path_theme_path).size
                    if (CompressionOperation.ZIPDecompression(path_theme_path + "CustomThemes.zip",path_theme_path + "新主题(${size_list.toString()})")) {
                        Toasty.normal(mContext, "创建完毕！").show()
                        mFileOperation.deleteFile(path_theme_path + "CustomThemes.zip")
                        dialog.dismiss()
                    } else {
                        Toasty.error(mContext, "创建失败，解压文件失败！", Toast.LENGTH_SHORT, true).show()
                    }
                } else {
                    Toasty.error(mContext, "创建失败，在写出资源文件时出现了异常！", Toast.LENGTH_SHORT, true).show()
                }
                
            }
            .setNegativeButton("编辑") { dialog, id ->
                // 编辑按钮被点击
                val mPath = Path(mContext)
                val path_list_path = mPath.getThemePath() + if (selectedOption2 >= 0) array_list2[selectedOption2] else "null"
                if (mFileOperation.Exists(path_list_path)) {
                    val intent = Intent(mContext, EditorCustomThemeActivity::class.java)
                    intent.putExtra("CustomThemeIndex", selectedOption2.toString())
                    intent.putExtra("CustomThemeName", array_list2[selectedOption2].toString())
                    mContext.startActivity(intent)
                }
            }
            .setPositiveButton("确定") { dialog, id ->
                // 确定按钮被点击
                SettingOperate.setValue(mContext,"DayEditor",-1)
                SettingOperate.setValue(mContext,"NightEditor", -1)
                SettingOperate.setValue(mContext,"CustomThemeEditor",true)
                SettingOperate.setValue(mContext,"CustomThemeEditorId",selectedOption2)
                SettingOperate.setValue(mContext,"CustomThemeEditorName",array_list2[selectedOption2].toString())
            }
        val dialog = builder2.create()
        dialog.show()
    }
    
    //加载字体对话框
    public fun FontDialog() {
        //标题
        builder3.setTitle("编辑器字体")
            .setSingleChoiceItems(array_list3, selectedOption3) { dialog, which ->
                selectedOption3 = which // 更新当前选中的选项索引
            }
            .setNegativeButton("取消") { dialog, id ->
                // 取消按钮被点击
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, id ->
                // 确定按钮被点击
                SettingOperate.setValue(mContext,"FontEditor",selectedOption3)
            }
        val dialog = builder3.create()
        dialog.show()
    }
    
}
