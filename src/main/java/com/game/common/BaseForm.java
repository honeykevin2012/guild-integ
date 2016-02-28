package com.game.common;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.basics.validate.Signature;

@Signature(deviceId = "deviceId", deviceName = "deviceName", gameId = "gameId", os = "os", osVersion = "osVersion", gameVersion = "gameVersion", sdkVersion = "sdkVersion", screenResolution = "screenResolution", timestamp = "timestamp", data = "data", nuid="nuid", signature = "signature")
public class BaseForm {
	@NotEmpty(message = "error#设备唯一标识不能为空.")
	private String deviceId;// （android：imei, ios：唯一标识）

	private String deviceName;// 手机型号

	@NotEmpty(message = "error#游戏编号不能为空.")
	private String gameId;// 游戏命名编号

	@NotEmpty(message = "error#操作系统不能为空.")
	private String os;// (android、ios) 操作系统

	@NotEmpty(message = "error#操作系统版本不能为空.")
	private String osVersion;// 操作系统版本

	@NotEmpty(message = "error#游戏版本不能为空.")
	private String gameVersion;// (游戏升级用)

	@NotEmpty(message = "error#SDK版本不能为空.")
	private String sdkVersion;// （当前SDK的版本号）

	@NotEmpty(message = "error#屏幕分辨率不能为空.")
	private String screenResolution;// 屏幕分辨率

	@NotEmpty(message = "error#时间戳不能为空")
	private String timestamp;// （后续安全验证）

	@NotEmpty(message = "error#签名不能为空.")
	private String signature;// MD5(key+mac+gameId)md5小写
	
	private String ip;

	private String data;// 请求参数RSA加密密文
	private String token;// 请求参数3des加密密文
	private Integer nuid;//服务器校验用户ID参数名(固定约定)

	private Integer pn;// 当前页
	private Integer ps;// 每页记录数

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getNuid() {
		return nuid;
	}

	public void setNuid(Integer nuid) {
		this.nuid = nuid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
