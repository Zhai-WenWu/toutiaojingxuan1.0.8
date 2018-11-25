package com.deshang.ttjx.framework.manager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class UIManager {

	public static void turnToAct(Context ct, Class<?> cls) {
		Intent it = new Intent(ct, cls);
		ct.startActivity(it);
	}

	public static void turnToAct(Context ct, Class<?> cls, Bundle b) {
		Intent it = new Intent(ct, cls);
		if (b != null) {
			it.putExtras(b);
		}
		ct.startActivity(it);
	}

	public static void turnToActForresult(Activity ct, Class<?> cls, int requestCode, Bundle bundle) {
		Intent it = new Intent(ct, cls);
		if (bundle != null) {
			it.putExtras(bundle);
		}
		ct.startActivityForResult(it, requestCode);
	}

}
