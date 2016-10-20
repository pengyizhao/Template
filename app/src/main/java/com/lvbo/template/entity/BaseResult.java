package com.lvbo.template.entity;

/**
 * Created by lvbo on 16/8/16.
 */
public class BaseResult {
    private int ReturnCode;
    private String ReturnText;

    public int getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        ReturnCode = returnCode;
    }

    public String getReturnText() {
        return ReturnText;
    }

    public void setReturnText(String returnText) {
        ReturnText = returnText;
    }
}
