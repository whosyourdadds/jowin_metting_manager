package cn.jowin.test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jowin.dao.MeetingDao;
import cn.jowin.dao.MeetingRelationDao;
import cn.jowin.dao.RoomDao;
import cn.jowin.entity.Company;
import cn.jowin.service.CompanyService;
import cn.jowin.util.Md5;
import cn.jowin.util.task.FindMeetingTask;
public class TestCase {
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx = 
		new ClassPathXmlApplicationContext(
			"spring-mybatis.xml",
			"spring-service.xml");
	}
	@Test
	public void testMD5(){
		//测试Task
//		MeetingServiceImp meetingService=new MeetingServiceImp();
////		@Autowired
//		MeetingRelationServiceImp meetingRelationService=new MeetingRelationServiceImp();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  ");
//		Date date = new Date();
//		String str = sdf1.format(date)+"00:00:00";
//		try {
//			date = sdf.parse(str);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Long endTime = date.getTime()+86400000;
//		List<Meeting> meetingList = meetingService.findMeetingByTime(date.getTime(), endTime, "start");
//		List<Meeting> meetingList2 = meetingService.findMeetingByTime(date.getTime(), endTime, "end");
//		System.out.println(meetingList);
//		System.out.println(meetingList2);
//		System.out.println(date.getTime());
//		System.out.println(endTime);
		
//		FindMeetingTask f = new FindMeetingTask();
//		f.run();
//		Date d = new Date(new Long("1487355000000"));
//		Date e = new Date(new Long("1487365800000"));
//		System.out.println(sdf.format(d));
//		System.out.println(sdf.format(e));
		//电子邮件    
//		 String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    
//		 Pattern regex = Pattern.compile(check);    
//		 Matcher matcher = regex.matcher("875234583@qq2.com");    
//		 boolean isMatched = matcher.matches();    
//		 System.out.println(isMatched);  
		
//		long l = (long) (Math.random()*2000+5000);
//		System.out.println(l);
//		
		
//		List<MeetingRelation> mrList = new ArrayList<MeetingRelation>();
//		MeetingRelation mr = new MeetingRelation(null,null,null,null,null,null,null,"875234583@qq.com",null);
//		String[] str = new String[]{"875234583@qq.com","chenl@jowin.cn","1037690127@qq.com","3199686389@qq.com","624315113@qq.com","247536842@qq.com","784838898@qq.com","2506065431@qq.com","1378318714@qq.com","115699955@qq.com","1156422336@qq.com"};
//		System.out.println(mrList.size());
//		System.out.println(mrList);
		
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  ");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//		
//		Date date = new Date();
//		System.out.println(date);
//		String str = sdf1.format(date)+"00:00:00";
//		System.out.println(str);
//		try {
//			date = sdf.parse(str);
//			System.out.println(date);
//			str = sdf.format(date);
//			System.out.println(str);
//		} catch (ParseException e) {
//			
//			e.printStackTrace();
//		}
		
//		SendMail s = new SendMail();
//		boolean j = s.sendMailHTML("18702104117@163.com", "875234583@qq.com", "小王明天测试开会");
//		System.out.println(j);
		String a="123456";
		System.out.println(Md5.saltMd5(a));
//		UserDao dao=ctx.getBean("userDao",UserDao.class);
//		List<User> list = dao.findAll();
//		System.out.println(list);
//		MeetingDao dao = ctx.getBean("meetingDao",MeetingDao.class);
//		Meeting meeting = dao.findMeetingById("eda2cb7f-8ab7-4a35-9850-d33ca50be68b");
//		System.out.println(meeting);
//		System.out.println(Long.SIZE);
//		System.out.println(Long.MAX_VALUE);
//		Long a=1479873900000L;
//		long c = (long)1479873900000;
//		Long b = new Long(1482465900000L);
//		Meeting meeting2 = dao.findMeetingByTime(1479873900000L,1482465900000L); 
//		System.out.println(meeting2);
		
//		System.out.println(UUID.randomUUID().toString());
//		System.out.println(UUID.randomUUID().toString());
//		System.out.println(UUID.randomUUID().toString());
	}
	
	
	@Test
	public void testSave() throws Throwable{
		MeetingDao dao=ctx.getBean("meetingDao",MeetingDao.class);
		MeetingRelationDao mrDao = ctx.getBean("meetingRelationDao",MeetingRelationDao.class);
		String id=UUID.randomUUID().toString();
		System.out.println(id);
		
		String a = "2016-10-16  13:45";
		String b = "2016-11-11  01:00";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		Long sDate = sdf1.parse(a).getTime();
		Long eDate = sdf1.parse(b).getTime();
		System.out.println(sDate+"|"+eDate);
//		dao.saveMeeting(new Meeting(id, "b6961f2f-d889-4217-92e8-da7261f44436", "fe505529-50af-4144-9734-3d03737457af",
//				sDate, eDate, "fe505529-50af-4144-9734-3d03737457af", "王正香3,王正香2", 
//				"wangzx@Jowin.cn,wangzx@Jowin.cn", "18501685488,18501685488", "王正香3", 
//				"abcd", "aaaaaaaa", "+8", sDate+(1000*60*30), 
//				sDate, null, null, "是", 
//				sDate+(1000*60*30), null, eDate+(1000*60*30)));
		
//		mrDao.saveMeetingRelation(new MeetingRelation("512db9f0-d96f-4eb6-9dfa-7e2a2521ca88",id,null));
//		mrDao.saveMeetingRelation(new MeetingRelation("5bb2c93c-88d6-4419-a13b-0e3665fcb13d",id,null));
//		User user = new User(
//				id,"chenLi","chenl@Jowin.cn","12345678910","021-12345678","123456"
//				,null,null,null,null,null);
//		dao.saveUser(user);
	}
	
	@Test
	public void testCompany(){
		CompanyService cs=ctx.getBean("companyService",CompanyService.class);
		Company c= cs.registCompany("九闻通信", "斜土路", "12345678", "123@jowin.cn", "12345678", "www.jowin.cn");
		System.out.println(c);
	}
	
	@Test
	public void testRoom(){
		RoomDao dao = ctx.getBean("roomDao",RoomDao.class);
		for(int i=0 ;10>i;i++){
//			Room room = new Room(UUID.randomUUID()ID().toString(),null,"2e09784d-68fd-4c46-aeef-66dd614b0f5d","f49dd7a1-be12-465a-bd2e-59fe775d6fa3",null,"00"+i,"12345678",null,"空闲","00"+i,"123456","00"+i,null,null,null,null);
//			dao.saveRoom(room);
			System.out.println(i);
		}
	}
	
}
