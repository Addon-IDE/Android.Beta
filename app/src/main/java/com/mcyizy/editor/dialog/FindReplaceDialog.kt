package com.mcyizy.editor.dialog

//基础库
import com.mcyizy.addonide.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
//
import android.view.View
import android.view.LayoutInflater
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog.Builder
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.EditorSearcher.SearchOptions

import java.util.regex.PatternSyntaxException

public class FindReplaceDialog {

    private lateinit var builder : Builder
    private lateinit var dialog : AlertDialog
    private var searchOptions = SearchOptions(false, false)
    
    var type = SearchOptions.TYPE_NORMAL
    var ignoreCase = false

    public fun Dialog(mContext : Context,mCodeEditor : CodeEditor) {
        // 创建对话框构建器
        builder = AlertDialog.Builder(mContext)
        // 自定义布局
        val inflater = LayoutInflater.from(mContext)
        val dialogView = inflater.inflate(R.layout.dialog_editor_find_replace, null)
        builder.setView(dialogView)
        // 创建并显示对话框
        dialog = builder.create()
        dialog.show()
        //
        incident(dialogView,mCodeEditor)
    }
    
    private fun incident(mView : View,mCodeEditor : CodeEditor) {
        //获取编辑框
        val find_edit = mView.findViewById<EditText>(R.id.find_edit)
        val replace_edit = mView.findViewById<EditText>(R.id.replace_edit)
        //编辑框转换为文本
        val find_edit_text : String = find_edit.getText().toString()
        //获取控件
        val botton_up = mView.findViewById<LinearLayout>(R.id.botton_up)
        val botton_down = mView.findViewById<LinearLayout>(R.id.botton_down)
        val botton_replace = mView.findViewById<LinearLayout>(R.id.botton_replace)
        val botton_replaceall = mView.findViewById<LinearLayout>(R.id.botton_replaceall)
        val botton_off = mView.findViewById<LinearLayout>(R.id.botton_off)
        //获取CheckBox
        val regular_expression = mView.findViewById<CheckBox>(R.id.regular_expression)
        val whole_word = mView.findViewById<CheckBox>(R.id.whole_word)
        val ignore_case = mView.findViewById<CheckBox>(R.id.ignore_case)
        //
        find_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //初始化搜索配置
                type = SearchOptions.TYPE_NORMAL
                if (regular_expression.isChecked()) {
                    type = SearchOptions.TYPE_REGULAR_EXPRESSION
                }
                if (whole_word.isChecked()) {
                    type = SearchOptions.TYPE_WHOLE_WORD
                }
                ignoreCase = ignore_case.isChecked()
                searchOptions = SearchOptions(type, ignoreCase)
                //
                if (editable.isNotEmpty()) {
                    try {
                        mCodeEditor.searcher.search(
                            editable.toString(),
                            searchOptions,
                        )
                    } catch (e: PatternSyntaxException) {
                        // Regex error
                    }
                } else {
                    mCodeEditor.searcher.stopSearch()
                }
            }
        })
        //查找上一个
        botton_up.setOnClickListener {
            mCodeEditor.searcher.gotoNext()
        }
        //查找下一个
        botton_down.setOnClickListener {
            mCodeEditor.searcher.gotoPrevious()
        }
        //替换
        botton_replace.setOnClickListener {
            val replace_edit_text : String = replace_edit.getText().toString()
            mCodeEditor.searcher.replaceThis(replace_edit_text)
        }
        //全部替换
        botton_replaceall.setOnClickListener {
            val replace_edit_text : String = replace_edit.getText().toString()
            mCodeEditor.searcher.replaceAll(replace_edit_text)
        }
        //关闭
        botton_off.setOnClickListener {
            mCodeEditor.searcher.stopSearch()
            dialog.dismiss()
        }
    }

}
