package com.mcyizy.editor.editor

//主
import io.github.rosemoe.sora.widget.CodeEditor

//AntlrLanguage
import com.mcyizy.editor.editor.AntlrLanguage
//java
import io.github.rosemoe.sora.langs.java.JavaLanguage

public class Language(private val mCodeEditor: CodeEditor) {

    //设置语言
    public fun Set(lang : Int) {
        var mAntlrLanguage : AntlrLanguage = AntlrLanguage(mCodeEditor)
        when (lang) {
            0 -> mCodeEditor.setEditorLanguage(null)
            1 -> mCodeEditor.setEditorLanguage(JavaLanguage())
            2 -> mAntlrLanguage.setJsonLanguage()
            else -> mCodeEditor.setEditorLanguage(null)
        }
    }

}
