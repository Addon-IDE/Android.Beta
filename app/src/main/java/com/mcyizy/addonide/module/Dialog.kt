package com.mcyizy.addonide.module

import com.mcyizy.addonide.R
import android.view.LayoutInflater
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.widget.TextView
import android.graphics.drawable.ColorDrawable
import android.view.Window

public class Dialog(private val mContext: Context) {
    // 创建AlertDialog.Builder对象
    val builder = AlertDialog.Builder(mContext)
    private var dialog: AlertDialog? = null

    // 加载对话框
    public fun LoadDialog(title: String) {
        // 设置布局
        val inflater = LayoutInflater.from(mContext)
        val dialogLayout = inflater.inflate(R.layout.dialog_load_dialog, null)
        builder.setView(dialogLayout)

        // 修改布局
        val TitleText = dialogLayout.findViewById<TextView>(R.id.title)
        TitleText.text = title
        
        //设置不可以取消
        dialog?.setCancelable(false)

        // 创建并显示对话框
        dialog = builder.create()
        dialog?.show()
        
        //设置背景颜色
        val tempDialog = dialog ?: builder.create()
        val window: Window? = tempDialog.window
        window?.setBackgroundDrawable(ColorDrawable(0))
    }

    // 关闭加载对话框
    public fun LoadDialogOff() {
        dialog?.dismiss()
    }
}