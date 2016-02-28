package com.game.common.utility.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

import com.game.common.utility.os.OSUtils;

public class Im4ImageUtils {

	/**
	 * ImageMagick的路径
	 */
	public static String imageMagickPath = "D:\\Program Files\\ImageMagick-6.9.0-Q16";

	/**
	 * 
	 * 根据坐标裁剪图片
	 * 
	 * @param srcPath   要裁剪图片的路径
	 * @param newPath   裁剪图片后的路径
	 * @param x         起始横坐标
	 * @param y         起始纵坐标
	 * @param x1        结束横坐标
	 * @param y1        结束纵坐标
	 */

	public static void cutImage(String srcPath, String newPath, int x, int y, int x1, int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		/**
		 * width：  裁剪的宽度
		 * height： 裁剪的高度
		 * x：       裁剪的横坐标
		 * y：       裁剪的挫坐标
		 */
		op.crop(width, height, x, y);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 根据宽度缩放图片
	 * 
	 * @param width            缩放后的图片宽度
	 * @param srcPath          源图片路径
	 * @param newPath          缩放后图片的路径
	 */
	public static void cutImage(int width, String srcPath, String newPath)	throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(width, null);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 图片裁剪或缩放
	 * @param inStream 
	 * @param outStream
	 * @param width 宽度
	 * @param height 高度
	 * @param isResize：true (允许拉伸 宽度为width 高度为 height), false (等比例裁剪, 尺寸范围不超过width或height)
	 */
	public static void cutImage(InputStream inStream, OutputStream outStream, int width, int height, boolean isResize) {
		if (inStream == null)
			return;
		try {
			Pipe inputProvider = new Pipe(inStream, null);
			Pipe outputConsumer = new Pipe(null, outStream);
			IMOperation op = new IMOperation();
			op.addImage("-"); // read from stream
			if (isResize)
				op.resize(width, height, '!');
			else
				op.resize(width, height);
			op.addImage("jpg:-"); //
			ConvertCmd convert = new ConvertCmd();
			convert.setInputProvider(inputProvider);
			convert.setOutputConsumer(outputConsumer);
			// linux下不要设置此值，不然会报错
			if ("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
			convert.run(op, new Object[]{});
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 根据尺寸缩放图片
	 * @param width             缩放后的图片宽度
	 * @param height            缩放后的图片高度
	 * @param srcPath           源图片路径
	 * @param newPath           缩放后图片的路径
	 */
	public static void cutImage(int width, int height, String srcPath,	String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(width, height);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}
	/**
	 * 先将图片进行缩放，然后切割中间部分
	 * @param srcPath
	 * @param desPath
	 * @param width
	 * @param height
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public static void cutImageCenter(String srcPath, String desPath, int width, int height) throws IOException, InterruptedException, IM4JavaException{  
        IMOperation op = new IMOperation();  
        op.addImage(srcPath);  
        op.resize(width, height, '^').gravity("center").extent(width, height);
        //op.resize(width, height);
        op.addImage(desPath);  
  
        ConvertCmd convert = new ConvertCmd();
        if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
        //convert.createScript("e:\\test\\myscript.sh",op);  
        convert.run(op);  
    }
	
	/**
	 * 先将图片进行缩放，然后切割中间部分
	 * @param inStream
	 * @param outStream
	 * @param width
	 * @param height
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public static void cutImageCenter(InputStream inStream, OutputStream outStream, int width, int height) throws IOException, InterruptedException, IM4JavaException{  
		Pipe inputProvider = new Pipe(inStream, null);
		Pipe outputConsumer = new Pipe(null, outStream);
		IMOperation op = new IMOperation();
		op.addImage("-"); // read from stream
        op.resize(width, height, '^').gravity("center").extent(width, height);
        op.addImage("jpg:-"); //
        ConvertCmd convert = new ConvertCmd();
        convert.setInputProvider(inputProvider);
        convert.setOutputConsumer(outputConsumer);
        if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
        //convert.createScript("e:\\test\\myscript.sh",op);  
		convert.run(op);
		inStream.close();
		outStream.close();
    }  
	
	/**
	 * 给图片加水印
	 * @param srcPath            源图片路径
	 */
	public static void addImgText(String srcPath) throws Exception {
		IMOperation op = new IMOperation();
		op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("text 5,5 juziku.com");
		op.addImage();
		op.addImage();
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		if("Windows".equals(OSUtils.getName().name())) convert.setSearchPath(imageMagickPath);
		convert.run(op, srcPath, srcPath);
	}
	
	

	public static void main(String[] args) throws Exception {
		cutImageCenter("E:/photo/111.jpg", "E://photo/222.jpg", 500,500);
		//cutImage(200,200, "E:/photo/111.jpg", "E://photo/222.jpg");
	}
}