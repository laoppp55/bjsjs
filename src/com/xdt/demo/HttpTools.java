package com.xdt.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



public class HttpTools {

	private static Log log = LogFactory.getLog(HttpTools.class); 

	/** 
	 * 执行一个HTTP GET请求，返回请求响应的HTML 
	 * 
	 * @param url                 请求的URL地址 
	 * @param queryString 请求的查询参数,可以为null 
	 * @param charset         字符集 
	 * @param pretty            是否美化 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doGet(String url, String queryString) { 
		String response = ""; 
		HttpGet httpGet=new HttpGet(url+"?"+queryString);
		try {          
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {    
				HttpEntity httpEntity = httpResponse.getEntity();  
				response = EntityUtils.toString(httpEntity);                    
			}  
		}catch (IOException e) { 
			log.error("执行HTTP Get请求" + url + "时，发生异常！", e); 
		} finally { 
			if(httpGet!=null)
				httpGet.releaseConnection();
		}            
		return response; 
	} 
	public static String convertStreamToString(InputStream is) {      
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
		StringBuilder sb = new StringBuilder();      

		String line = null;      
		try {      
			while ((line = reader.readLine()) != null) {  
				sb.append(line + "\n");      
			}      
		} catch (IOException e) {      
			e.printStackTrace();      
		} finally {      
			try {      
				is.close();      
			} catch (IOException e) {      
				e.printStackTrace();      
			}      
		}      
		return sb.toString();      
	}  
	/** 
	 * 执行一个HTTP POST请求，返回请求响应的HTML 
	 * 
	 * @param url         请求的URL地址 
	 * @param params    请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @param pretty    是否美化 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doPost(String url, List<NameValuePair> params) { 
		String response = "";
		HttpPost httpPost = new HttpPost(url);            
		try { 
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);  
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
			{  
				HttpEntity httpEntity = httpResponse.getEntity();  
				response = EntityUtils.toString(httpEntity);
			}  
		} catch (IOException e) { 
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e); 
		} finally { 
			httpPost.releaseConnection(); 
		} 
		return response.toString(); 
	}
}
