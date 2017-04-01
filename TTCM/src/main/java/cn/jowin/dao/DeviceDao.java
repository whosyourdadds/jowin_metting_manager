package cn.jowin.dao;

import cn.jowin.entity.Device;

public interface DeviceDao {
	void saveDevice(Device device);
	Device findDeviceById(String deviceId);
	Device findDeviceByName(String deviceName);
}
