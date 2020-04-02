package com.bizwink.cms.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HttpRequestEmsTest {
    public static String postWithParamsForString(String url, List<NameValuePair> params) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost =   new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } else {
            System.out.println("url = [" + url + "], params = [" + params + "], statusCode = [" + statusCode + "]");
            return null;
        }
    }
    public static void main(String[] args) {
        String url = "http://61.49.1.174/es/es/PortalNoLogined/PagePermit.do?pageNo=1&key=b1d0c19ee42af86539059f57d29e8f79";
        try {
            List<NameValuePair> param = new ArrayList<>();
            String s = postWithParamsForString(url, param);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

