package com.mcyizy.android.tool2

import android.content.Context
import android.widget.ImageView
import java.io.IOException
import android.graphics.drawable.Drawable

//图片操作
public class PictureOperation(private val mContext: Context) {

    //读取assets设置到图片框中
    public fun setAssetsImage(mImageView : ImageView,Name : String) {
        try {
            val inputStream = mContext.assets.open(Name) // 替换为你的图片文件名
            val drawable = Drawable.createFromStream(inputStream, null)
            mImageView.setImageDrawable(drawable)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    
}
