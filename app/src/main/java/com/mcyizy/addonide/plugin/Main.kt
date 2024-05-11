package com.mcyizy.addonide.plugin

import android.content.Context
import com.mcyizy.addonide.main.Path
//json
import org.json.JSONArray
import org.json.JSONObject
//plugin
import com.mcyizy.addonide.plugin.LoadActivity
//App
import com.mcyizy.android.tool2.FileOperation
import com.mcyizy.addonide.setting.SettingOperate

public class Main {

    //lateinit
    private lateinit var mContext: Context
    private lateinit var mPath: Path
    //plugin
    val mLoadActivity = LoadActivity()
    //App
    val mFileOperation : FileOperation = FileOperation()
    
    //变量
    var apk_dex_path = ""
    var apk_dex_path_out = ""
    var apk_dex_path_name = ""
    
    //设置上下文
    public fun setContext(context : Context) {
        mContext = context
        mPath = Path(mContext)
    }
    
    //插件路径初始化
    public fun PluginPath(path : String) {
        val path2 : String = path + "/manifest.json"
        if (mFileOperation.Exists(path2)) {
            val manifest_text = mFileOperation.ReadFile(path2)
            if (SettingOperate.isJsonValid(manifest_text)) {
                val mJSONObject = JSONObject(manifest_text)
                if (mJSONObject.has("data")) {
                    val mJSONObject_data = JSONObject(mJSONObject.getString("data"))
                    val temp_pathapk = path + "/" + mJSONObject_data.getString("file")
                    apk_dex_path = temp_pathapk
                    apk_dex_path_out = path
                    apk_dex_path_name = mJSONObject_data.getString("name")
                }
            }
        }
    }
    
    //开始运行
    public fun Run(type : Int) {
        when (type) {
            0 -> {RunDexActivity()}
            else -> {}
        }
    }
    
    //加载app中的活动
    private fun RunDexActivity() {
       mLoadActivity.Init(mContext)
       mLoadActivity.Run(mContext)
    }
    
}
