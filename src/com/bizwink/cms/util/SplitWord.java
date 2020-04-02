package com.bizwink.cms.util;


import com.bizwink.search.util.CommUtil;

/**
 * Created by kang on 2019/9/24.
 */
public class SplitWord {
    public static void main(String[] args) {
        String splitStr = "北京石景山\"\"()-()";
        //String splitStr = "北京石景山\"北京 石景山\"-(北京 石景山 八宝山)";
        splitStr = CommUtil.processXSS(splitStr);
        String[] splitArr = splitStr.split("[\"(|\"|)|)\\-(]");
        for (int i = 0; i < splitArr.length; i++) {
            if (splitArr[i] != null && splitArr[i].length()>0) {
                System.out.println("第" + i + "个内容=" + splitArr[i]);
            }
        }
    }
}