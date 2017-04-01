package cn.jowin.util.task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import java.io.File;  
import org.apache.commons.mail.EmailAttachment;  
import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.HtmlEmail;  
import org.apache.commons.mail.SimpleEmail;  
import org.apache.commons.mail.MultiPartEmail;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import cn.jowin.entity.Meeting;
import cn.jowin.entity.MeetingRelation;
import cn.jowin.service.MeetingService;
import cn.jowin.service.imp.MeetingServiceImp;

//@Service
public class SendMailTask extends TimerTask {  
  
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
   
	private static final String START = "start";
	private static final String END = "end";

	
	private List<MeetingRelation> mrList;
	private String msg;
	
	private Timer timer;
	
	private String meetingId;
	private Long  dateTime;
	private String  str;
	private ApplicationContext ctx =new ClassPathXmlApplicationContext(
			"spring-mybatis.xml","spring-service.xml");
	
	

	



	




	public SendMailTask(List<MeetingRelation> mrList, String msg, Timer timer, String meetingId, Long dateTime,
			String str) {
		super();
		this.mrList = mrList;
		this.msg = msg;
		this.timer = timer;
		this.meetingId = meetingId;
		this.dateTime = dateTime;
		this.str = str;
		
	}


	/** 
     * ���� Apache Commons Email �����commons-email-1.1�������ʼ� 
     * @param args 
     */  
    public static void main(String[] args) {  
          
          
//      SimpleEmail email = new SimpleEmail();//���������ͨ���ʼ���ʹ�������Ϳ�����  
//        MultiPartEmail email = new MultiPartEmail();//���Ҫ���ʹ��������ʼ�����ʹ�������  
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
          
        	  email.setFrom("18702104117@163.com");//���÷�����  
              email.addTo("875234583@qq.com");//�����ռ���  
              email.setSubject("���Թ�˾����");//��������  
              email.setMsg("С��������Կ���");//�����ʼ�����  
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
        } catch (EmailException e) {  
        	System.out.println("����ʧ��");
            e.printStackTrace();  
        }  
  
    }  
  
    
    public boolean sendMailHTML(String addTo,String massge){
    	
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
          
        	  email.setFrom("18702104117@163.com");//���÷�����  
              email.addTo(addTo);//�����ռ���  
              email.setSubject("��������");//��������  
              email.setMsg(massge);//�����ʼ�����  
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
            System.out.println("--------------"+massge);
            System.out.println("���ͳɹ�"+addTo);  
            return true;
            	
        } catch (EmailException e) {  
        	System.out.println("����ʧ��"+addTo);
//            e.printStackTrace();  
        	//TODO ��¼����ʧ�ܵ��û����浽MR����
        	//TODO ������
        }
        return false;
    }


	@Override
	public void run() {
//		System.out.println("--------------"+str);
		MeetingService meetingService = 
				ctx.getBean("meetingService", MeetingService.class);
		
		Meeting meeting = meetingService.findMeetingById(meetingId);
		boolean flag = true;
		boolean flag2 = false;
		if(!(meeting==null||meeting.getRecord()==3)){
			if(END.equals(str)){
				flag=dateTime.longValue()==meeting.getEndTime().longValue();
			}
			if(START.equals(str)){
				flag2 = dateTime.longValue()==meeting.getStartTime().longValue();
			}
			if(flag||flag2){
				for(MeetingRelation mr :mrList){
					
					sendMailHTML(mr.getUserEmail(),msg);
					try {
	//					//TO0p();���3-7S
						Thread.sleep((long)(Math.random()*2000+5000));
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					
				}
				if(START.equals(str)){
					meeting.setIsEmail("1");
					meetingService.updateIsEmailMeeting(meeting);
				}
				if(END.equals(str)){
					meeting.setIsEmail("2");
					meetingService.updateIsEmailMeeting(meeting);
				}
				
			}
		
		}
		if(timer!=null){
			timer.cancel();
		}
		
		
		
		
		
	}
}  