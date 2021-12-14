package com.sohu.mavenpublishlibrary.utils;


import android.os.Environment;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 读取工程模式配置，用于测试使用
 *
 * @author liaoweiwei
 */
public class ConfigUtil {

    private static ConfigUtil mConfigUtil = null;

    private JSONObject mConfig = null;

    private ConfigUtil() {
        try {
            mConfig = new JSONObject(readFileSdcardFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ROOT_PATH" + "/" + "config/config.txt"));
        } catch (Exception e) {
            mConfig = new JSONObject();
            e.printStackTrace();
        }
    }

    public static ConfigUtil getInstance() {
        if (mConfigUtil == null) {
            synchronized (ConfigUtil.class) {
                if (mConfigUtil == null) {
                    mConfigUtil = new ConfigUtil();
                }
            }
        }
        return mConfigUtil;
    }

    public String getBaseUrl(String defaultUrl, boolean debug) {
        if (debug) {
            try {
                String connectLiveUrl = mConfig.optString("baseUrlV4");
                return TextUtils.isEmpty(connectLiveUrl) ? defaultUrl : connectLiveUrl;
            } catch (Exception e) {
                e.printStackTrace();
                return defaultUrl;
            }
        } else {
            return defaultUrl;
        }
    }

    /**
     * 读取手写开关，用于qa自动化测试
     *
     * @return
     */
    public boolean getWriteOption() {

        try {
            String chooseMap = mConfig.optString("choose_map");
            JSONArray array = new JSONArray(chooseMap);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                if ("firstScreenWrite".equals(object.optString("key"))) {
                    return object.optBoolean("value", false);
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读SD中的文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFileSdcardFile(String fileName) throws IOException {
        String res = "";
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(fileName);

            int length = fin.available();

            byte[] buffer = new byte[length];
            int var = fin.read(buffer);
            res = new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
        return res;
    }

}