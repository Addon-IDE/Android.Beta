package com.mcyizy.editor.component

//App
import android.content.Context
import io.github.rosemoe.sora.widget.CodeEditor
import com.mcyizy.addonide.setting.SettingOperate
import com.mcyizy.addonide.main.DataStorage
//Editor
import com.mcyizy.editor.component.codeeditor.AddonCodeEditor

public class CodeEditor(private val mContext: Context) {

    //lateinit
    private lateinit var mCodeEditor: CodeEditor
    
    //设置代码编辑框
    public fun setCode(codeEditor : CodeEditor) {
        mCodeEditor = codeEditor
    }
    //加载编辑器
    public fun Editor() {
        //根据项目类型来加载编辑器
        val mDataStorage = DataStorage(mContext)
        val project_type = mDataStorage.Read("Project_Type")
        when (project_type) {
            "default" -> AddonEditor()
            else -> {}
        }
                
    }
    
    //Addon编辑器
    private fun AddonEditor() {
        //Addon Editor
        val autocomplete: Boolean = SettingOperate.getValue(mContext,"AutocompleteEditor")
        if (autocomplete) {
            //根据文件类型来加载
            val mDataStorage = DataStorage(mContext)
            val file_type = mDataStorage.Read("Editor_File_Type").lowercase()
            //
            //执行
            when (file_type) {
                "addm" -> {
                    lateinit var mAddonCodeEditor: AddonCodeEditor
                    mAddonCodeEditor = AddonCodeEditor(mContext)
                    mAddonCodeEditor.setCode(mCodeEditor)
                    mAddonCodeEditor.Editor()
                }
                else -> {
                    
                }
            }
        }
    }
   

}
