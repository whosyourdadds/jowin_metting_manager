package cn.jowin.util;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.jowin.util.task.FindMeetingTask;



public class FindMeetingTimer implements ServletContextListener {

	private Timer timer = null;
	
	
	public void contextDestroyed(ServletContextEvent event) {
		  
		event.getServletContext().log(""+timer);  
		if(null != timer){
			timer.cancel(); 
			event.getServletContext().log("��ʱ������");  
		}
		
	}
	
	
	public void contextInitialized(ServletContextEvent event) {
		
		
		//�������ʼ������������tomcat������ʱ�����������������������ʵ�ֶ�ʱ������ 
		  timer = new Timer(true); 
		  event.getServletContext().log("��ʱ��������");//�����־������tomcat��־�в鿴�� 
		  //����exportHistoryBean��0��ʾ�������ӳ٣�5*1000��ʾÿ��5��ִ������60*60*1000��ʾһ��Сʱ��
		  timer.schedule(new FindMeetingTask(),0,24*60*60*1000); 
		  
		  
		  
	}
	
	
	

}
