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

public class XDTDemo {
	public static void main(String[] args) throws UnsupportedEncodingException {
        String content="测试短信发送【石景山区经信委】";
		String phones="15810807296";
		/*if(args.length==2){
			content=args[0];
			phones=args[1];
		}*/
		//sendMessages(content,phones);
		String url="http://api.sms.hsyid.com:8088/SendMes.ashx";
		String userid="sjsjxw";
		String pwd="sjsjxw123";
		XDTAPI xdtapi=new XDTAPI(userid, pwd, url);

        ResponseModel responseModel = xdtapi.SendMessage(content, phones);

		if(responseModel.Result==0){
			System.out.println("提交成功，序列号="+responseModel.SeqId+",余额="+responseModel.Balance);
		}else{
			System.out.println("提交失败，原因="+responseModel.Desc);
		}

	}
	public static void sendMessages(String content,String phones){
		String url="http://**************:8088/SendMes.ashx";
		String userid="***";
		String pwd="****";
		List<NameValuePair> pairs=new ArrayList<>();
		pairs.add(new BasicNameValuePair("user", userid));
		pairs.add(new BasicNameValuePair("pwd", pwd));
		pairs.add(new BasicNameValuePair("mobiles", phones));
		pairs.add(new BasicNameValuePair("contents", URLEncoder.encode(content)));
		String resString=HttpTools.doPost(url, pairs);		
		//System.out.println("responseString="+resString);
		ResponseModel responseModel=parseResponseXML(resString);
		if(responseModel.Result==0){
			System.out.println("提交成功，序列号="+responseModel.SeqId+",余额="+responseModel.Balance);
		}else{
			System.out.println("提交失败，原因="+responseModel.Desc);
		}
	}
	public static ResponseModel parseResponseXML(String xmlString){
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
