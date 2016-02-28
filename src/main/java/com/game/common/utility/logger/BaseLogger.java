package com.game.common.utility.logger;

import java.util.UUID;

import org.apache.log4j.Logger;

public abstract class BaseLogger extends Logger {
	protected String append = "#";

	protected BaseLogger(String name) {
		super(name);
	}

	public abstract String create();

	public void execute() {
		Log4jManager.getLog(this.name).info(create().replaceAll("\n",""));
	}
	
	public String baseParameter(){
		StringBuilder builder = new StringBuilder();
		builder.append(ip).append(append);
		builder.append(os).append(append);
		builder.append(osVersion).append(append);
		builder.append(deviceName).append(append);
		builder.append(deviceId);
		return builder.toString();
	}
	
	private String deviceId;// （android：imei, ios：唯一标识）
	private String deviceName;// 手机型号
	//private String gameId;// 游戏命名编号
	private String os;// (android、ios) 操作系统
	private String osVersion;// 操作系统版本
	//private String gameVersion;// (游戏升级用)
	//private String sdkVersion;// （当前SDK的版本号）
	//private String screenResolution;// 屏幕分辨率
	private String logtime;// （后续安全验证）
	private String ip;//
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

//	public String getGameId() {
//		return gameId;
//	}
//
//	public void setGameId(String gameId) {
//		this.gameId = gameId;
//	}

	public String getLogId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

//	public String getGameVersion() {
//		return gameVersion;
//	}
//
//	public void setGameVersion(String gameVersion) {
//		this.gameVersion = gameVersion;
//	}
//
//	public String getSdkVersion() {
//		return sdkVersion;
//	}
//
//	public void setSdkVersion(String sdkVersion) {
//		this.sdkVersion = sdkVersion;
//	}
//
//	public String getScreenResolution() {
//		return screenResolution;
//	}
//
//	public void setScreenResolution(String screenResolution) {
//		this.screenResolution = screenResolution;
//	}


	public String getTimestamp() {
		return this.logtime;
	}


	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

}
