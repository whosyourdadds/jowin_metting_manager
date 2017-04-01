package cn.jowin.util.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jowin.entity.Meeting;
import cn.jowin.entity.MeetingRelation;
import cn.jowin.service.MeetingRelationService;
import cn.jowin.service.MeetingService;
import cn.jowin.service.imp.MeetingRelationServiceImp;
import cn.jowin.service.imp.MeetingServiceImp;

public class FindMeetingTask extends TimerTask {
	
	private ApplicationContext ctx =new ClassPathXmlApplicationContext(
			"spring-mybatis.xml","spring-service.xml");
	
	private MeetingService meetingService = 
			ctx.getBean("meetingService", MeetingService.class);
	
	private MeetingRelationService meetingRelationService = 
			ctx.getBean("meetingRelationService", MeetingRelationService.class);
	
	private static final String START = "start";
	private static final String END = "end";
	@Override
	public void run() {
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  ");
		Date date = new Date();
		String str = sdf1.format(date)+"00:00:00";
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long endTime = date.getTime()+86400000;
		
		List<Meeting> meetingList = meetingService.findMeetingByTime(date.getTime(), endTime, "start");
		List<Meeting> meetingList2 = meetingService.findMeetingByTime(date.getTime(), endTime, "end");
		
//		System.out.println(meetingList);
//		System.out.println(date.getTime());
//		System.out.println(sdf.format(date));
//		System.out.println(sdf.format(new Date(endTime)));
		
		String msg ;
		//�����ѯ���������ڿգ��Ǿͱ���
		if(!(meetingList.isEmpty()||meetingList==null)){
			for(Meeting meeting:meetingList){
				List<MeetingRelation> mrList = meetingRelationService.findMeetingByMeetingId(meeting.getMeetingId());
				msg ="����һ�����鼴����ʼ!<br>����:"+meeting.getSubject()+"  ��ʼʱ��:"+sdf.format(new Date(meeting.getStartTime()));
				//��ѯ��������ػ����û������ʼ�
				Timer timer = new Timer();
				Long startDate = meeting.getStartTime()-1800000;
				timer.schedule(new SendMailTask(mrList,msg,timer,meeting.getMeetingId(), meeting.getStartTime(),START), new Date(startDate));
				//				event.getServletContext().log("��ʱ��������");
				
			}
		}
		
		if(!(meetingList2.isEmpty()||meetingList2==null)){
			for(Meeting meeting:meetingList2){
				List<MeetingRelation> mrList = meetingRelationService.findMeetingByMeetingId(meeting.getMeetingId());
				msg ="���Ļ��鼴������!<br>�����Ҫ�ӳ�";
				//��ѯ��������ػ����û������ʼ�
				Timer timer = new Timer();
				Long endDate = meeting.getEndTime()-1800000;
				timer.schedule(new SendMailTask(mrList,msg,timer,meeting.getMeetingId(), meeting.getEndTime(),END), new Date(endDate));
				//				event.getServletContext().log("��ʱ��������");
			
			}
		}
		
		
		//���Դ���
//		List<MeetingRelation> mrList = new ArrayList<MeetingRelation>();
//		String[] string = new String[]{"875234583@qq.com","chenl@jowin.cn","1037690127@qq.com","3199686389@qq.com","624315113@qq.com","247536842@qq.com","784838898@qq.com","2506065431@qq.com","1378318714@qq.com","115699955@qq.com","1156422336@qq.com"};
//		for(int i = 0;i<string.length;i++){
//			MeetingRelation mr = new MeetingRelation(null,null,null,null,null,null,null,string[i],null);
//			mrList.add(mr);
//		}
//		for(int i = 0;i<4;i++){
//			msg ="���Ļ��鼴������!<br>�����Ҫ�ӳ�";
//			Timer timer = new Timer();
//			Long endDate = System.currentTimeMillis()+(5000*i);
//			timer.schedule(new SendMailTask(mrList,msg,timer,null, null,"end"+i), new Date(endDate));
//		}
//		
//		
//		for(int i = 0;i<4;i++){
//			msg ="����һ�����鼴����ʼ!<br>����:�²�����˭��  ��ʼʱ��:"+sdf.format(new Date(System.currentTimeMillis()));
//			//��ѯ��������ػ����û������ʼ�
//			Timer timer = new Timer();
//			Long startDate = System.currentTimeMillis()+(5000*i);
//			timer.schedule(new SendMailTask(mrList,msg,timer,null, null,"Start"+i), new Date(startDate));
//		}
		
		
	}
	
	
	public static void stopTime(Timer timer) {  
        timer.cancel();  
    }  

	
}
