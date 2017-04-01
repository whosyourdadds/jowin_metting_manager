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
     * ���� Apache Commons Email �����commons-email-1.1�������ʼ� 
     * @param args 
     */  
    public static void main(String[] args) {  
          
          
    	SimpleEmail email2 = new SimpleEmail();//���������ͨ���ʼ���ʹ�������Ϳ�����  
        MultiPartEmail email = new MultiPartEmail();//���Ҫ���ʹ��������ʼ�����ʹ�������  
//        HtmlEmail email = new HtmlEmail();//���Է���html���͵��ʼ�  
        
        email.setHostName("smtp.163.com");//ָ��Ҫʹ�õ��ʼ�������  
        email.setAuthentication("18702104117", "b123456");//ʹ��163���ʼ����������ṩ��163��ע����û�������Ȩ�룬       ����  
        email.setCharset("UTF-8");  
      //  email.setSmtpPort(25);
        
        //b123456
        try {  
        	//m18702104117_1@163.com
        	//18702104117@163.com
        	//18702104117
        	//m18702104117_1
          
        	  email.setFrom("18702104117@163.com");//���÷�����  
              email.addTo("875234583@qq.com");//�����ռ���  
              email.setSubject("���Թ�˾����");//��������  
              email.setMsg("С��������Կ���");//�����ʼ�����  
              email.setDebug(false);
              /**
               * ���ڸ���
               */
            File file = new File("C:\\testEmail.txt");//Ҫ���͵ĸ���  
//              
            EmailAttachment attachment = new EmailAttachment();  
            attachment.setPath(file.getPath());  
            attachment.setName(file.getName());  
            attachment.setDescription("��������");  
            attachment.setDisposition(EmailAttachment.ATTACHMENT);//����������  
            email.attach(attachment);  
              
            email.send();  
            System.out.println("���ͳɹ�");  
        } catch (EmailException e) {  
        	System.out.println("����ʧ��");
            e.printStackTrace();  
        }  
  
    }  
  
    
    public boolean sendMailHTML(String from,String addTo,String msg){
    	
    	HtmlEmail email = new HtmlEmail();//���Է���html���͵��ʼ�  
        
        email.setHostName("smtp.163.com");//ָ��Ҫʹ�õ��ʼ�������  
        email.setAuthentication("18702104117", "b123456");//ʹ��163���ʼ����������ṩ��163��ע����û�������Ȩ�룬       ����  
        email.setCharset("UTF-8");  
      //  email.setSmtpPort(25);
        
        //b123456
        try {  
        	//m18702104117_1@163.com
        	//18702104117@163.com
        	//18702104117
        	//m18702104117_1
          
        	  email.setFrom(from);//���÷�����  
              email.addTo(addTo);//�����ռ���  
              email.setSubject("��������");//��������  
              email.setMsg(msg);//�����ʼ�����  
              email.setDebug(false);
              /**
               * ���ڸ���
               */
//            File file = new File("C:\\testEmail.txt");//Ҫ���͵ĸ���  
//              
//            EmailAttachment attachment = new EmailAttachment();  
//            attachment.setPath(file.getPath());  
//            attachment.setName(file.getName());  
//            attachment.setDescription("��������");  
//            attachment.setDisposition(EmailAttachment.ATTACHMENT);//����������  
//            email.attach(attachment);  
              
            email.send();  
            System.out.println("���ͳɹ�");  
            return true;
            	
        } catch (EmailException e) {  
        	System.out.println("����ʧ��");
            e.printStackTrace();  
        }
        return false;
    }


	
}  