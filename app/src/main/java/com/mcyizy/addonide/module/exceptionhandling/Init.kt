package com.mcyizy.addonide.module.exceptionhandling

import android.content.Context
import java.lang.Thread.UncaughtExceptionHandler

import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

import android.content.Intent
import com.mcyizy.addonide.module.exceptionhandling.CrashActivity

public class Init : Thread.UncaughtExceptionHandler {
    
    //
    private lateinit var mContext : Context
    private lateinit var mDefaultHandler : UncaughtExceptionHandler
    
    //初始化
    public fun init(context : Context) {
        mContext = context
        //获取系统的默认UnCaughtException处理程序
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //将crashhandler设置为程序的默认处理程序
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
    
    //未捕获的异常
    override fun uncaughtException(thread: Thread, ex: Throwable) {
       if (!handleException(ex) && mDefaultHandler != null) {
            // If the user does not deal with the system is the default exception handler to handle
            mDefaultHandler.uncaughtException(thread, ex)
       } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
            // Log.e(TAG, "error : ", e)
            }
            // exit app
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
       }
    }
    
    private fun handleException(ex : Throwable) : Boolean {
        if (ex == null) {
            return false
        }
        //切换到崩溃窗口
        val intent = Intent(mContext, CrashActivity::class.java)
        intent.putExtra("message",getStackTraceString(ex).toString())
        intent.putExtra("stackTrace",ex.stackTrace.toString())
        mContext.startActivity(intent) // 启动目标窗口
        
        
        return false
    }
   
   //获取错误堆栈信息
   private fun getStackTraceString(tr: Throwable?): String {
        try {
            if (tr == null) {
                return ""
            }
            var t: Throwable? = tr
            while (t != null) {
                if (t is UnknownHostException) {
                    return ""
                }
                t = t.cause
            }
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            tr.printStackTrace(pw)
            return sw.toString()
        } catch (e: Exception) {
            return ""
        }
    }

}
