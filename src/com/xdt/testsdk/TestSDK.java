package com.xdt.testsdk;
public class TestSDK {

	public static void main(String[] args) {
		String content="";
		String phones="";
		String userid="";
		String pwd="";
		if(args.length==4){
			userid=args[0];
			pwd=args[1];
			content=args[2];
			phones=args[3];			
		}else {
			/*Scanner input = new Scanner(System.in);
			System.out.println("请输入用户名：");
			userid= input.next();
			System.out.println("请输入密码：");
			pwd= input.next();
			System.out.println("请输入发送内容：例如： 测试【信达通】");
			content= input.next();
			System.out.println("请输入手机号：例如15210661281,13718123594");
			phones= input.next();
			input.close();*/
		}
		System.out.println("发送短信内容："+content);
		String url="http://api.sms.hsyid.com:8088/SendMes.ashx";
        userid="sjsjxw";
        pwd = "sjsjxw123";
        content="测试【石景山区经信委】";
        phones="15810807296";

		/*XdtSms xdtapi=new XdtSms(userid, pwd, url);
		ResponseModel responseModel=xdtapi.SendMessage(content, phones);
		if(responseModel.Result==0){
			System.out.println("提交成功，序列号="+responseModel.SeqId+",余额="+responseModel.Balance);
		}else{
			System.out.println("提交失败，原因="+responseModel.Desc);
		}*/

	}

}
