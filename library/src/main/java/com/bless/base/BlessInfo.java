package com.bless.base;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlessInfo {

	private BlessInfo() {}
	
	public static String getToken(Context paramContext) {
		String key = "bless_key" + getBuildKey() + getAndroidId(paramContext);
		String token = hashKey(key);
		return ((String)(String) (token = token.toUpperCase()));
	}

	/**
	 * 得到传入的Key的MD5值
	 *
	 * @param key
	 * @return
	 */
	private static String hashKey(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
	/**
	 * <pre>
	 * 	
	 * 	Build.BOARD // 主板  
	 * 	Build.BRAND // android系统定制商  
	 * 	Build.CPU_ABI // cpu指令集  
	 * 	Build.DEVICE // 设备参数  
	 * 	Build.DISPLAY // 显示屏参数  
	 * 	Build.FINGERPRINT // 硬件名称  
	 * 	Build.HOST  
	 * 	Build.ID // 修订版本列表  
	 * 	Build.MANUFACTURER // 硬件制造商  
	 * 	Build.MODEL // 版本  
	 * 	Build.PRODUCT // 手机制造商  
	 * 	Build.TAGS // 描述build的标签  
	 * 	Build.TIME  
	 * 	Build.TYPE // builder类型  
	 * 	Build.USER  
	 * </pre>
	 * @return
	 */
	private static String getBuildKey() {
		String key = "";
		key = key + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10)
				+ (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10)
				+ (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10)
				+ (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10)
				+ (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10)
				+ (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10)
				+ (Build.USER.length() % 10);
		return key;
	}
	
	private static String getAndroidId(Context paramContext) {
		return Settings.Secure.getString(paramContext.getContentResolver(), Settings.System.ANDROID_ID);
	}
}
