package com.mcyizy.addonide.main

//导入库
import android.content.Context
import com.mcyizy.addonide.main.Path
import com.mcyizy.addonide.setting.SettingOperate
import com.mcyizy.android.tool2.FileOperation

//初始化
public class Init {

    //路径
    private var path : String = ""
    //文件操作
    private var mPath : Path? = null
    private var mAppFileOperation : FileOperation = FileOperation()
    
    //初始化
    public fun init(mContext: Context) {
        //变量赋值
        mPath = Path(mContext)
        path = mPath!!.getMainPath()
        //创建相应文件
        create_file()
        summon_file(mContext)
    }
    
    //创建文件
    public fun create_file() {
        //创建目录
        mAppFileOperation.CreateDirectory(path + "project")
        mAppFileOperation.CreateDirectory(path + "backup")
        mAppFileOperation.CreateDirectory(path + "plugin")
        mAppFileOperation.CreateDirectory(path + "cache")
        mAppFileOperation.CreateDirectory(path + "logs")
        mAppFileOperation.CreateDirectory(path + "theme")
    }
    
    //生成配置文件
    public fun summon_file(mContext: Context) {
        //变量
        val setting_path = path + "setting.json"
        
        //自动删除设置文件，以后会移除，主要用于测试
        //mAppFileOperation.deleteFile(setting_path)
        
        //判断设置文件是否存在
        if (mAppFileOperation.Exists(setting_path)) {
            val file_str = mAppFileOperation.ReadFile(setting_path)
            if (SettingOperate.isJsonValid(file_str)) {
            } else {
                mAppFileOperation.writeAssetToFile(mContext,"data/setting.json",setting_path)
            }
        } else {
            mAppFileOperation.writeAssetToFile(mContext,"data/setting.json",setting_path)
        }
    }
    
}
