package cn.jowin.util;


import java.io.File;  
import org.apache.commons.mail.EmailAttachment;  
import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.HtmlEmail;  
import org.apache.commons.mail.SimpleEmail;  
import org.apache.commons.mail.MultiPartEmail;  
public class SendMail{  
  
	private static final String SMTP_163 =  "smtp.163.com";
	private static final String SMTP_google = "smtp.gmail.com";
	private static final String SMTP_21cn = "smtp.21cn.com sina.com";
	private static final String SMTP_sina  = "smtp.sina.com.cn" ;
	private static final String SMTP_tom  = "smtp.tom.com" ;
	private static final String SMTP_263  = "smtp.263.net" ;
	private static final String SMTP_x263  = "smtp.x263.net" ;
	private static final String SMTP_elong  = "smtp.elong.com" ;
	private static final String SMTP_china  = "smtp.china.com" ;
	private static final String SMTP_sohu  = "smtp.sohu.com" ;
	private static final String SMTP_etang  = "smtp.etang.com" ;
	private static final String SMTP_yahoo  = "smtp.mail.yahoo.com" ;
   
	
	
	



	/** 
     * 利用 Apache Commons Email 组件（commons-email-1.1）发送邮件 
     * @param args 
     */  
    public static void main(String[] args) {  
          
          
    	SimpleEmail email2 = new SimpleEmail();//如果发送普通的邮件，使用这个类就可以了  
        MultiPartEmail email = new MultiPartEmail();//如果要发送带附件的邮件，需使用这个类  
//        HtmlEmail email = new HtmlEmail();//可以发送html类型的邮件  
        
        email.setHostName("smtp.163.com");//指定要使用的邮件服务器  
        email.setAuthentication("18702104117", "b123456");//使用163的邮件服务器需提供在163已注册的用户名、授权码，       密码  
        email.setCharset("UTF-8");  
      //  email.setSmtpPort(25);
        
        //b123456
        try {  
        	//m18702104117_1@163.com
        	//18702104117@163.com
        	//18702104117
        	//m18702104117_1
          
        	  email.setFrom("18702104117@163.com");//设置发件人  
              email.addTo("875234583@qq.com");//设置收件人  
              email.setSubject("测试公司开会");//设置主题  
              email.setMsg("小王明天测试开会");//设置邮件内容  
              email.setDebug(false);
              /**
               * 关于附件
               */
            File file = new File("C:\\testEmail.txt");//要发送的附件  
//              
            EmailAttachment attachment = new EmailAttachment();  
            attachment.setPath(file.getPath());  
            attachment.setName(file.getName());  
            attachment.setDescription("附件描述");  
            attachment.setDisposition(EmailAttachment.ATTACHMENT);//附件的类型  
            email.attach(attachment);  
              
            email.send();  
            System.out.println("发送成功");  
        } catch (EmailException e) {  
        	System.out.println("发送失败");
            e.printStackTrace();  
        }  
  
    }  
  
    
    public boolean sendMailHTML(String from,String addTo,String msg){
    	
    	HtmlEmail email = new HtmlEmail();//可以发送html类型的邮件  
        
        email.setHostName("smtp.163.com");//指定要使用的邮件服务器  
        email.setAuthentication("18702104117", "b123456");//使用163的邮件服务器需提供在163已注册的用户名、授权码，       密码  
        email.setCharset("UTF-8");  
      //  email.setSmtpPort(25);
        
        //b123456
        try {  
        	//m18702104117_1@163.com
        	//18702104117@163.com
        	//18702104117
        	//m18702104117_1
          
        	  email.setFrom(from);//设置发件人  
              email.addTo(addTo);//设置收件人  
              email.setSubject("会议提醒");//设置主题  
              email.setMsg(msg);//设置邮件内容  
              email.setDebug(false);
              /**
               * 关于附件
               */
//            File file = new File("C:\\testEmail.txt");//要发送的附件  
//              
//            EmailAttachment attachment = new EmailAttachment();  
//            attachment.setPath(file.getPath());  
//            attachment.setName(file.getName());  
//            attachment.setDescription("附件描述");  
//            attachment.setDisposition(EmailAttachment.ATTACHMENT);//附件的类型  
//            email.attach(attachment);  
              
            email.send();  
            System.out.println("发送成功");  
            return true;
            	
        } catch (EmailException e) {  
        	System.out.println("发送失败");
            e.printStackTrace();  
        }
        return false;
    }


	
}  