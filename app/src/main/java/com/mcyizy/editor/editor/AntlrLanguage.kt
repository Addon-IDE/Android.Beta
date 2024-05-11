package com.mcyizy.editor.editor

//主
import io.github.rosemoe.sora.widget.CodeEditor

//json
import com.zouyi.xinying.language.json.impl.JsonLanguage

public class AntlrLanguage(private val mCodeEditor: CodeEditor) {

    //设置Json
    public fun setJsonLanguage() {
        val jsonLanguage = JsonLanguage()
        mCodeEditor.setEditorLanguage(jsonLanguage)
    }

}