package com.bizwink.cms.util;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-15
 * To change this template use File | Settings | File Templates.
 */
public  class SourceXML {
    public static Document getDocument(String url){
        Document doc = null;
        HttpURLConnection conn = null;
        InputStream ins = null;
        SAXReader reader = null;
        try{
            //HttpTimeoutHandler hth = new HttpTimeoutHandler(600000);
            URL conURL = new URL(null,url);
            conn = (HttpURLConnection)conURL.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            ins = conn.getInputStream();
            reader =new SAXReader();
            doc= reader.read(ins);
            ins.close();
            conn.disconnect();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (ins != null) {
                    ins.close();
                    ins = null;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return doc;
    }
}
