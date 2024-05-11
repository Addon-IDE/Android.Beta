package com.mcyizy.android.tool;

import android.content.Context;
import java.io.File;
import android.content.Intent;
import android.net.Uri;

public class ShareOperation {
    
    //分享文件
    public void ShareFile(Context mContext,String file_path,String app_name) {
        File file = new File(file_path);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		android.os.StrictMode.VmPolicy.Builder builder = new android.os.StrictMode.VmPolicy.Builder();
        android.os.StrictMode.setVmPolicy(builder.build());
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.setType("*/*");
		if(app_name != null && !(app_name.length()==0) && !app_name.isEmpty() && !"".equals(app_name)) intent.setPackage(app_name);
		mContext.startActivity(Intent.createChooser(intent, "分享"));
    }
    
}
