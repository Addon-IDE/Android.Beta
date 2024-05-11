package com.mcyizy.editor.module

import io.github.rosemoe.sora.widget.CodeEditor

public class Toolbar(private val mCodeEditor: CodeEditor) {

    //初始化
    init {
        
    }
    
    //撤销
    public fun undo() {
        mCodeEditor.undo()
    }
    
    //重做
    public fun redo() {
        mCodeEditor.redo()
    }
    
}