<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"      
 "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">
<mapper namespace="cn.jowin.dao.DeviceDao">
	<insert id="saveDevice" 
	parameterType="cn.jowin.entity.Device">
		insert into user 
		( user_id, name,password,email,mobile,phone, 
		 company_id,update_time,is_Adminstrator,meeting_Id,power_Id) 
		values 
			 (#{id}, #{name}, #{password},
			 #{password}, #{email}, #{mobile},#{phone},
			 #{companyId},#{updateTime},#{isAdminstrator},#{meetingId},#{powerId})
	</insert>

	<select id="findDeviceById"
		parameterType="string"
		resultType="cn.jowin.entity.Device">
		select 
			user_id as id,
			name,
			password,
			email,
			mobile,phone, 
		 	company_id,update_time,is_Adminstrator,meeting_Id,power_Id
		from
			user
		where	
			user_id=#{id}
	</select>
	
	<select id="findDeviceByName"
		parameterType="string"
		resultType="cn.jowin.entity.Device">
		select 
			user_id as id,
			name,
			password,
			email,
			mobile,phone, 
		 	company_id,update_time,is_Adminstrator,meeting_Id,power_Id
		from
			user
		where  name=#{name}
	</select>	
	
</mapper>







