package com.mcyizy.editor.component

import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.event.ContentChangeEvent

public class CodeAutoCompletionListener {

    //声明监听器
    private var mListeners: OnAutoListener? = null

   //
   init {
        
   }
   
   fun setCode(mCodeEditor: CodeEditor) {
    //监听器
        mCodeEditor.apply {
          //选择更改事件
          subscribeEvent(ContentChangeEvent::class.java) { event, unsubscribe ->
                val action = event.getAction()
                val changedText = event.getChangedText()
                when (action) {
                    0 -> {}
                    1 -> {}
                    2 -> mListeners?.onInsert(changedText.toString())
                    3 -> mListeners?.onDelete(changedText.toString())
                    else -> {}
                }
          }
       }     
   }
    
    fun setOnAutoListener(listener: OnAutoListener) {
        mListeners = listener
    }    
    
    interface OnAutoListener {
        fun onInsert(content : String)
        fun onDelete(content : String)
    }

}
