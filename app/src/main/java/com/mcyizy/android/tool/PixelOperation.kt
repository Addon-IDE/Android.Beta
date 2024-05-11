package com.mcyizy.android.tool

import android.content.Context
import android.util.TypedValue

//像素操作
public class PixelOperation {

    //DP到PX
    public fun DPtoPX(mContext : Context,value : Int) : Int {
       val scale = mContext.resources.displayMetrics.density
       return (value * scale + 0.5f).toInt() 
    }
    
    //PX到DP
	public fun PXtoDP(mContext : Context, value : Int) : Int {
		val scale = mContext.resources.displayMetrics.density
        return (value / scale + 0.5f).toInt()
	}
	
    //SP到PX
	public fun SPtoPX(mContext : Context, value : Float) : Int {
		val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        mContext.resources.displayMetrics
        )
        return px.toInt()
	}
	
    //PX到SP
	public fun PXtoSP(mContext : Context, value : Float) : Int {
		val scaledDensity = mContext.resources.displayMetrics.scaledDensity
        val sp = value / scaledDensity
        return sp.toInt()
    }

}
