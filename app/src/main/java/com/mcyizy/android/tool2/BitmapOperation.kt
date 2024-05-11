package com.mcyizy.android.tool2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileInputStream

public class BitmapOperation(private val mContext: Context) {
    
    //读取内部存储图片转换为Bitmap
    public fun getBitmapFromInternalStorage(filePath: String): Bitmap {
        val fileInputStream = FileInputStream(filePath) // 打开文件输入流
        val bitmap = BitmapFactory.decodeStream(fileInputStream) // 解码文件流为 Bitmap
        fileInputStream.close() // 关闭文件输入流
        return bitmap // 返回 Bitmap
    }
    
}
