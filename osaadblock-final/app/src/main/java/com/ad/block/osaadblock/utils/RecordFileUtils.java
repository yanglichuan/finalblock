package com.ad.block.osaadblock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author tom
 */
public class RecordFileUtils {
	private static RecordFileUtils sp_util;
	private static final String SP_FILE_NAME = "oasadblock.ini";

	private RecordFileUtils() {
	}

	private Context context;
	public static RecordFileUtils getInstance(Context ct) {
		if (sp_util == null) {
			synchronized (RecordFileUtils.class) {
				if (sp_util == null) {
					sp_util = new RecordFileUtils(ct);
					return sp_util;
				}
			}
		}
		return sp_util;
	}

	private RecordFileUtils(Context ct) {
		this.context = ct;
	}

	public int getIntData(String key) {
		SharedPreferences sp = getSP();
		return sp.getInt(key,0);
	}

	public int getIntData(String key,int defaltValue) {
		SharedPreferences sp = getSP();
		return sp.getInt(key,defaltValue);
	}

	public boolean setIntData(String key, int value) {
		SharedPreferences sp = getSP();
		if (sp == null) {
			return false;
		}
		Editor editor = sp.edit();
		if (editor == null) {
			return false;
		}
		editor.putInt(key,value);
		editor.commit();
		return true;
	}


	public boolean getBooleanData(String key) {
		SharedPreferences sp = getSP();
		return sp.getBoolean(key, false);
	}
	public boolean getBooleanData(String key,boolean bInit) {
		SharedPreferences sp = getSP();
		return sp.getBoolean(key, bInit);
	}

	public String getStringData(String key) {
		SharedPreferences sp = getSP();
		return sp.getString(key, "");
	}

	public boolean setStringData(String key, String value) {
		SharedPreferences sp = getSP();
		if (sp == null) {
			return false;
		}
		Editor editor = sp.edit();
		if (editor == null) {
			return false;
		}
		editor.putString(key, value);
		editor.commit();
		return true;
	}

	public boolean setBooleanData(String key, boolean value) {
		SharedPreferences sp = getSP();
		if (sp == null) {
			return false;
		}
		Editor editor = sp.edit();
		if (editor == null) {
			return false;
		}
		editor.putBoolean(key, value);
		editor.commit();
		return true;
	}

	private SharedPreferences getSP() {
		SharedPreferences sp = context.getSharedPreferences(
				SP_FILE_NAME, Context.MODE_PRIVATE);
		return sp;
	}




	public long getLongData(String key) {
		SharedPreferences sp = getSP();
		return sp.getInt(key,0);
	}

	public long getLongData(String key,long defaltValue) {
		SharedPreferences sp = getSP();
		return sp.getLong(key,defaltValue);
	}

	public boolean setLongData(String key,long value) {
		SharedPreferences sp = getSP();
		if (sp == null) {
			return false;
		}
		Editor editor = sp.edit();
		if (editor == null) {
			return false;
		}
		editor.putLong(key,value);
		editor.commit();
		return true;
	}




	public float getFloatData(String key) {
		SharedPreferences sp = getSP();
		return sp.getFloat(key, 0.0f);
	}

	public float getFloatData(String key, float defaltValue) {
		SharedPreferences sp = getSP();
		return sp.getFloat(key, defaltValue);
	}

	public boolean setFloatData(String key,float value) {
		SharedPreferences sp = getSP();
		if (sp == null) {
			return false;
		}
		Editor editor = sp.edit();
		if (editor == null) {
			return false;
		}
		editor.putFloat(key, value);
		editor.commit();
		return true;
	}
}
