package cn.jowin.dao;

import cn.jowin.entity.Power;

public interface PowerDao {
	void savePower(Power power);
	Power findPowerById(String powerId);
}
