package com.game.common.utility.os;

/**
 * 操作系统类： 获取System.getProperty("os.name")对应的操作系统
 * 
 * @author isea533
 */
public class OSUtils {

	private static String OS = System.getProperty("os.name").toLowerCase();

	private static OSUtils _instance = new OSUtils();

	private OSPlatform platform;

	private OSUtils() {
	}

	public static boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public static boolean isMacOS() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
	}

	public static boolean isMacOSX() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
	}

	public static boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

	public static boolean isOS2() {
		return OS.indexOf("os/2") >= 0;
	}

	public static boolean isSolaris() {
		return OS.indexOf("solaris") >= 0;
	}

	public static boolean isSunOS() {
		return OS.indexOf("sunos") >= 0;
	}

	public static boolean isMPEiX() {
		return OS.indexOf("mpe/ix") >= 0;
	}

	public static boolean isHPUX() {
		return OS.indexOf("hp-ux") >= 0;
	}

	public static boolean isAix() {
		return OS.indexOf("aix") >= 0;
	}

	public static boolean isOS390() {
		return OS.indexOf("os/390") >= 0;
	}

	public static boolean isFreeBSD() {
		return OS.indexOf("freebsd") >= 0;
	}

	public static boolean isIrix() {
		return OS.indexOf("irix") >= 0;
	}

	public static boolean isDigitalUnix() {
		return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
	}

	public static boolean isNetWare() {
		return OS.indexOf("netware") >= 0;
	}

	public static boolean isOSF1() {
		return OS.indexOf("osf1") >= 0;
	}

	public static boolean isOpenVMS() {
		return OS.indexOf("openvms") >= 0;
	}

	/**
	 * 获取操作系统名字
	 * 
	 * @return 操作系统名
	 */
	public static OSPlatform getName() {
		if (isAix()) {
			_instance.platform = OSPlatform.AIX;
		} else if (isDigitalUnix()) {
			_instance.platform = OSPlatform.Digital_Unix;
		} else if (isFreeBSD()) {
			_instance.platform = OSPlatform.FreeBSD;
		} else if (isHPUX()) {
			_instance.platform = OSPlatform.HP_UX;
		} else if (isIrix()) {
			_instance.platform = OSPlatform.Irix;
		} else if (isLinux()) {
			_instance.platform = OSPlatform.Linux;
		} else if (isMacOS()) {
			_instance.platform = OSPlatform.Mac_OS;
		} else if (isMacOSX()) {
			_instance.platform = OSPlatform.Mac_OS_X;
		} else if (isMPEiX()) {
			_instance.platform = OSPlatform.MPEiX;
		} else if (isNetWare()) {
			_instance.platform = OSPlatform.NetWare_411;
		} else if (isOpenVMS()) {
			_instance.platform = OSPlatform.OpenVMS;
		} else if (isOS2()) {
			_instance.platform = OSPlatform.OS2;
		} else if (isOS390()) {
			_instance.platform = OSPlatform.OS390;
		} else if (isOSF1()) {
			_instance.platform = OSPlatform.OSF1;
		} else if (isSolaris()) {
			_instance.platform = OSPlatform.Solaris;
		} else if (isSunOS()) {
			_instance.platform = OSPlatform.SunOS;
		} else if (isWindows()) {
			_instance.platform = OSPlatform.Windows;
		} else {
			_instance.platform = OSPlatform.Others;
		}
		return _instance.platform;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(OSUtils.getName());
	}

}