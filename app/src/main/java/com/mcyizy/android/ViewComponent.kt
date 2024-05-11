package com.mcyizy.android

import android.content.*
import android.content.res.*
import android.view.*
import android.util.TypedValue
import android.graphics.drawable.GradientDrawable

public class ViewComponent {
    
    //水波纹效果
    fun WaterRippleEffect(context : Context,mView : View) {
        val theme: Resources.Theme = context.theme
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        val attribute = intArrayOf(android.R.attr.selectableItemBackground)
        val typedArray: TypedArray = theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        mView.foreground = typedArray.getDrawable(0)
    }
    
    //置圆角背景边框
    fun setRoundedBackgroundWithBorder(
        view: View,
        backgroundColor: Int,
        borderColor: Int,
        borderWidth: Int,
        cornerRadiusTopLeft: Float,
        cornerRadiusTopRight: Float,
        cornerRadiusBottomRight: Float,
        cornerRadiusBottomLeft: Float
    ) {
        val outRadius = floatArrayOf(
            cornerRadiusTopLeft,
            cornerRadiusTopLeft,
            cornerRadiusTopRight,
            cornerRadiusTopRight,
            cornerRadiusBottomRight,
            cornerRadiusBottomRight,
            cornerRadiusBottomLeft,
            cornerRadiusBottomLeft
        )
        
        val drawable = GradientDrawable()
        drawable.cornerRadii = outRadius
        drawable.setStroke(borderWidth, borderColor)
        drawable.setColor(backgroundColor)
        
        view.background = drawable
    }
    
}
