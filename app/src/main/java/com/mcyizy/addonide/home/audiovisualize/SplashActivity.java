package com.mcyizy.addonide.home.audiovisualize;

public class SplashActivity extends android.app.Activity
{
	@Override
	protected void onCreate(android.os.Bundle Bundle)
	{
		super.onCreate(Bundle);
		android.content.Intent Intent = new android.content.Intent(SplashActivity.this, AudioVisualizeActivity.class);
		startActivity(Intent);
		finish();
	}
}
