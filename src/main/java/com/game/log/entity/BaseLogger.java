package com.game.log.entity;

import java.util.Date;

import com.game.common.utility.DateUtils;

public abstract class BaseLogger {
	private String deviceId;// （android：imei, ios：唯一标识）
	private String deviceName;// 手机型号
	private String gameId;// 游戏命名编号
	private String os;// (android、ios) 操作系统
	private String osVersion;// 操作系统版本
	private String gameVersion;// (游戏升级用)
	private String sdkVersion;// （当前SDK的版本号）
	private String screenResolution;// 屏幕分辨率
	private String timestamp;// （后续安全验证）
	private String signature;// MD5(key+mac+gameId)md5小写
	private String ip;
	private String nuid;

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

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
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

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public abstract void create();
}
