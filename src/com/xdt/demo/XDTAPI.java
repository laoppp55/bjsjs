package com.xdt.demo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class XDTAPI {
	private String _user;
	private String _pwd;
	private String _url;
	public XDTAPI(String user,String pwd,String url){
		_user=user;
		_pwd=pwd;
		_url=url;
				
	}
	public ResponseModel SendMessage(String content,String phones) throws UnsupportedEncodingException {
        try {
            List<NameValuePair> pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("user", _user));
            pairs.add(new BasicNameValuePair("pwd", _pwd));
            pairs.add(new BasicNameValuePair("mobiles", phones));
            pairs.add(new BasicNameValuePair("contents", URLEncoder.encode(content, "UTF-8")));
            String resString=HttpTools.doPost(_url, pairs);
            ResponseModel responseModel=parseResponseXML(resString);
            return responseModel;
        }catch (Exception e){
            System.out.println(e);
        }catch (Throwable te){
            System.out.println(te);
        }
        return null;

	}
	private ResponseModel parseResponseXML(String xmlString){
		ResponseModel responseModel=new ResponseModel();
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
            document = saxReader.read(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
			Element root = document.getRootElement();
			responseModel.Result=Integer.parseInt(root.elementTextTrim("result"));
			responseModel.Desc=root.elementTextTrim("desc");
			Element dataElement = root.element("data");
			responseModel.SeqId=dataElement.elementTextTrim("seqid");
			responseModel.Balance=dataElement.elementTextTrim("balance");
		} catch (Exception e) {			
			e.printStackTrace();
			responseModel.Result=-1;
			responseModel.Desc="系统异常:"+e.toString();
			responseModel.SeqId="-1";
			responseModel.Balance="-1";
		}
		return responseModel;

	}
}
