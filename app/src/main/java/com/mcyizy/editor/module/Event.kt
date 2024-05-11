package com.mcyizy.editor.module

import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.subscribeEvent
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.EditorKeyEvent
import io.github.rosemoe.sora.event.KeyBindingEvent
import io.github.rosemoe.sora.event.PublishSearchResultEvent
import io.github.rosemoe.sora.event.SelectionChangeEvent
import io.github.rosemoe.sora.event.SideIconClickEvent

public class Event(private val mCodeEditor: CodeEditor) {

    //声明监听器
    private var mListeners: OnEventListener? = null

    init {
        //监听器
        mCodeEditor.apply {
            //选择更改事件
            subscribeEvent<SelectionChangeEvent> { _, _ ->
                mListeners?.onSelectionChangeEvent()
            }
            //发布搜索结果事件
            subscribeEvent<PublishSearchResultEvent> { _, _ -> 
                mListeners?.onPublishSearchResultEvent()
            }
            //内容更改事件
            subscribeEvent<ContentChangeEvent> { _, _ ->
                mListeners?.onContentChangeEvent()
            }
            //键绑定事件
            subscribeEvent<KeyBindingEvent> { _, _ ->
                
            }
            //编辑器键事件
            subscribeEvent<EditorKeyEvent> { _, _ ->
                
            }
            //侧面图标单击事件
            subscribeEvent<SideIconClickEvent> { _, _ ->
                
            }
        }
    }
    
    fun setOnEventListener(listener: OnEventListener) {
        mListeners = listener
    }    
    
    interface OnEventListener {
        fun onSelectionChangeEvent()
        fun onPublishSearchResultEvent()
        fun onContentChangeEvent()
    }
    
}
