package com.mcyizy.editor.editor

//android
import android.content.Context
//CodeEditor
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.getComponent
import io.github.rosemoe.sora.widget.component.Magnifier
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion
//App
import com.mcyizy.addonide.setting.SettingOperate

public class Setting(val mContext : Context) {


    //CodeEditor
    private lateinit var mCodeEditor: CodeEditor
    
    //设置CodeEditor
    public fun setCodeEditor(codeeditor : CodeEditor) {
        mCodeEditor = codeeditor
    }
    
    //常规设置
    public fun RoutineSetting() {
        //字体大小
        val font_size: Int = SettingOperate.getValue(mContext,"FontSizeEditor")
        val font_size2 = font_size.toFloat() + 6.0f
        mCodeEditor.setTextSize(font_size2)
        //tab制表符
        val tab_width: Int = SettingOperate.getValue(mContext,"TabWidthEditor")
        when (tab_width) {
            0 -> mCodeEditor.setTabWidth(2)
            1 -> mCodeEditor.setTabWidth(4)
            2 -> mCodeEditor.setTabWidth(6)
            3 -> mCodeEditor.setTabWidth(8)
            else -> mCodeEditor.setTabWidth(4)
        }
        //显示不可打印字符
        val flags_draw: String = SettingOperate.getValue(mContext,"FlagsDrawEditor")
        mCodeEditor.nonPrintablePaintingFlags = if (flags_draw.contains("0")) CodeEditor.FLAG_DRAW_WHITESPACE_LEADING else 0 or if (flags_draw.contains("1")) CodeEditor.FLAG_DRAW_WHITESPACE_TRAILING else 0 or if (flags_draw.contains("2")) CodeEditor.FLAG_DRAW_WHITESPACE_INNER else 0 or if (flags_draw.contains("3")) CodeEditor.FLAG_DRAW_WHITESPACE_FOR_EMPTY_LINE else 0 or if (flags_draw.contains("4")) CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION else 0
        //连体字
        val siamese_word: Boolean = SettingOperate.getValue(mContext,"SiameseWordEditor")
        mCodeEditor.setLigatureEnabled(siamese_word)
        //自动换行
        val word_wrap: Boolean = SettingOperate.getValue(mContext,"WordWrapEditor")
        mCodeEditor.setWordwrap(word_wrap)
        //显示放大镜
        val magnifier: Boolean = SettingOperate.getValue(mContext,"MagnifierEditor")
        mCodeEditor.getComponent<Magnifier>()
                    .setEnabled(magnifier)
        //安全输入
        val safety_input: Boolean = SettingOperate.getValue(mContext,"SafetyInputEditor")
        if (safety_input) {
            mCodeEditor.setInputType(129)
        } else {
            mCodeEditor.setInputType(1)
        }
        //固定行数
        val fixed_line: Boolean = SettingOperate.getValue(mContext,"FixedLineEditor")
        mCodeEditor.setPinLineNumber(fixed_line)
        //自动补全
        val autocomplete: Boolean = SettingOperate.getValue(mContext,"AutocompleteEditor")
        mCodeEditor.getComponent<EditorAutoCompletion>()
                    .setEnabled(autocomplete)
    }
        
}
