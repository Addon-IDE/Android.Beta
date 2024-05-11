package com.mcyizy.android.tool

import android.graphics.Color

//颜色操作
public class ColorOperation {

    companion object {
        //取颜色红色值
        @JvmStatic
        fun getRed(color : Int) : Int {
           return Color.red(color) 
        }
        
        //取颜色绿色值
        @JvmStatic
        fun getGreen(color : Int) : Int {
           return Color.green(color) 
        }
        
        //取颜色蓝色值
        @JvmStatic
        fun getBlue(color : Int) : Int {
           return Color.blue(color) 
        }
        
        //取颜色透明度
        @JvmStatic
        fun getAlpha(color : Int) : Int {
           return Color.alpha(color) 
        }
        
        //合成颜色值
        @JvmStatic
        fun getARGB(alpha : Int,red : Int,green : Int,blue : Int) : Int {
           return Color.argb(alpha,red,green,blue) 
        }
        
        //文本到颜色值
        @JvmStatic
        fun getColorInt(color : String) : Int {
           return Color.parseColor(color) 
        }
        
        //颜色值到文本
        @JvmStatic
        fun getColorString(color: Int, supportAlpha: Boolean = true, upperCase: Boolean = true) : String {
            var newColor = color
            if (!supportAlpha) {
                newColor = color and 0x00ffffff
            }
            var colorStr = Integer.toHexString(newColor)
            if (upperCase) {
                colorStr = colorStr.uppercase()
            } else {
                colorStr = colorStr.lowercase()
            }
            while (colorStr.length < 6) {
                colorStr = "0$colorStr"
            }
            if (supportAlpha) {
                while (colorStr.length < 8) {
                    colorStr = if (upperCase) "F$colorStr" else "f$colorStr"
                }
            }
            return "#$colorStr"
        }
        
        
    }
    
    
}
