package com.mcyizy.editor.editor

//android
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.mcyizy.addonide.setting.SettingOperate
//主
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import com.mcyizy.editor.editor.ColorScheme
//白天主题
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX
//夜间主题
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019
//
import com.zouyi.xinying.colorScheme.QuietLightColorScheme
import com.zouyi.xinying.colorScheme.AtomOneDarkColorScheme

public class Theme(private val mCodeEditor: CodeEditor) {

    //Context
    private lateinit var mContext: Context
    
    //设置Context
    public fun setContext(context : Context) {
        mContext = context
    }

    //设置主题
    public fun Default() {
        if (SettingOperate.getValue(mContext,"CustomThemeEditor")) {
            val mColorScheme : ColorScheme = ColorScheme(mContext)
            mColorScheme.setCodeEditor(mCodeEditor)
            mColorScheme.setScheme()
        } else {
            if (SettingOperate.getValue(mContext,"NightMode")) {
                SetNight(SettingOperate.getValue(mContext,"NightEditor"))
            } else {
                SetDay(SettingOperate.getValue(mContext,"DayEditor"))
            }
        }
        
    }
    //设置白天模式
    public fun SetDay(value : Int) {
        when (value) {
            0 -> mCodeEditor.colorScheme = SchemeEclipse()
            1 -> mCodeEditor.colorScheme = SchemeGitHub()
            2 -> mCodeEditor.colorScheme = SchemeNotepadXX()
            3 -> mCodeEditor.colorScheme = QuietLightColorScheme()
            else -> mCodeEditor.colorScheme = EditorColorScheme()
        }
    }
    
    //设置夜间模式
    public fun SetNight(value : Int) {
        when (value) {
            //夜间模式
            0 -> mCodeEditor.colorScheme = SchemeDarcula()
            1 -> mCodeEditor.colorScheme = SchemeVS2019()
            2 -> mCodeEditor.colorScheme = AtomOneDarkColorScheme()
            else -> mCodeEditor.colorScheme = EditorColorScheme()
        }
    }

}