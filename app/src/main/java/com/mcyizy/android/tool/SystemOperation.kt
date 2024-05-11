package com.mcyizy.android.tool

import android.content.Context

import android.content.ClipData
import android.content.ClipboardManager

//系统操作
public class SystemOperation {

    companion object {
        //置剪切板文本
        @JvmStatic
        fun setClipboardText(mContext : Context,mTextContent : String) {
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("label", mTextContent))
        }
        //取剪切板文本
        @JvmStatic
        fun getClipboardText(mContext : Context) : String {
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (clipboard.hasPrimaryClip()) {
                val clipText = clipboard.primaryClip?.getItemAt(0)?.text.toString()
                return clipText
            }
            return ""
        }
    }
    
    
}
