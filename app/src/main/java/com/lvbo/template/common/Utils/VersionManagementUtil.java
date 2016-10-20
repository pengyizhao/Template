package com.lvbo.template.common.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * ==================================================================
 * Copyright (C) 2016 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 16/9/29 12:05
 * @description ${todo}
 * <p>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 16/9/29 12:05  Drew.Chiang       v1.0.0          create
 * <p>
 * ==================================================================
 */

public class VersionManagementUtil {

    public static String getVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /**
     * @param versionServer
     * @param versionLocal
     * @return if versionServer > versionLocal, return 1, if equal, return 0, else return
     * -1
     *//*
    public static int versionCompare(String versionServer, String versionLocal) {
        String version1 = versionServer;
        String version2 = versionLocal;

        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0) {
            throw new IllegalArgumentException("Invalid parameter!");
        }

        int index1 = 0;
        int index2 = 0;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);

            if (number1[0] < number2[0]) {
                return -1;
            } else if (number1[0] > number2[0]) {
                return 1;
            } else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if (index1 == version1.length() && index2 == version2.length()) {
            return 0;
        }
        if (index1 < version1.length()) {
            return 1;
        } else {
            return -1;
        }
    }*/

    /**
     * @param version
     * @param index   the starting point
     * @return the number between two dots, and the index of the dot
     */
    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }


    /**
     * @param versionServer
     * @param versionLocal
     * @return if versionServer > versionLocal, return 1, if equal, return 0, else return
     * -1
     */
    public static int versionCompare(String versionServer, String versionLocal) {
        String version1 = versionServer;
        String version2 = versionLocal;

        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0) {
            throw new IllegalArgumentException("Invalid parameter!");
        }

        int index = 0;

        String[] versionNumbers1 = version1.split("\\.");
        String[] versionNumbers2 = version2.split("\\.");

        int version1Length = versionNumbers1.length;
        int version2Length = versionNumbers2.length;

        while (index < version1Length && index < version2Length) {
            if(Integer.parseInt(versionNumbers1[index]) > Integer.parseInt(versionNumbers2[index])){
                return 1;
            }
            index ++;
        }

        if(version1Length > version2Length){
            return 1;
        }

        if(version1Length == version2Length){
            return Integer.parseInt(versionNumbers1[index - 1]) == Integer.parseInt(versionNumbers2[index - 1])? 0:-1;
        }

        return -1;
    }
}
