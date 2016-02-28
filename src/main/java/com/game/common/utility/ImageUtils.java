package com.game.common.utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {

	/*
	 * 
	 * force 如果为false是等比例缩放；
	 */
	public static void cutImage(InputStream in, BufferedOutputStream out, int width, int height, boolean force) throws IOException {
		Image imgTemp = null;
		Rectangle area = null;
		BufferedImage image = ImageIO.read(in);
		if (force) {
			area = getMaxCutSize(image.getWidth(), image.getHeight(), width, height);
			ImageFilter filter = new CropImageFilter(area.x, area.y, area.width, area.height);
			ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
			imgTemp = Toolkit.getDefaultToolkit().createImage(producer);
		} else {
			// 计算缩放尺寸
			area = getMaxZoomSize(image.getWidth(), image.getHeight(), width, height);
			imgTemp = image;
		}
		int finalWidth = force ? width : area.width;
		int finalHeight = force ? height : area.height;
		// 绘出缩放后图片
		imgTemp = imgTemp.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
		BufferedImage imgResult = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = imgResult.getGraphics();
		g.drawImage(imgTemp, 0, 0, null);
		g.dispose();

		// 输出文件

		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(imgResult);
		param.setQuality(0.90f, true);
		encoder.encode(imgResult, param);
	}

	//
	public static byte[] cutImage(InputStream in, String resolution, boolean force) throws IOException {
		String imgSize = resolution;
		if (imgSize != null) {
			int width = Integer.parseInt(imgSize.split("_")[0]);
			int height = Integer.parseInt(imgSize.split("_")[1]);
			Image imgTemp = null;
			Rectangle area = null;
			BufferedImage image = ImageIO.read(in);
			if (force) {
				area = getMaxCutSize(image.getWidth(), image.getHeight(), width, height);
				ImageFilter filter = new CropImageFilter(area.x, area.y, area.width, area.height);
				ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
				imgTemp = Toolkit.getDefaultToolkit().createImage(producer);
			} else {
				// 计算缩放尺寸
				area = getMaxZoomSize(image.getWidth(), image.getHeight(), width, height);
				imgTemp = image;
			}
			int finalWidth = force ? width : area.width;
			int finalHeight = force ? height : area.height;
			// 绘出缩放后图片
			imgTemp = imgTemp.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
			BufferedImage imgResult = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = imgResult.getGraphics();
			g.drawImage(imgTemp, 0, 0, null);
			g.dispose();

			// 输出文件
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(imgResult);
			param.setQuality(0.90f, true);
			encoder.encode(imgResult, param);

			byte temp[] = null;
			temp = out.toByteArray();
			out.close();
			return temp;
		} else {
			return null;
		}

	}

	private static Rectangle getMaxCutSize(int w1, int h1, int w2, int h2) {
		int x, y, w3, h3;
		if ((float) w1 / w2 >= (float) h1 / h2) {
			h3 = h1; // 高度设为原高度
			w3 = h1 * w2 / h2; // 宽度按比率计算
		} else {
			w3 = w1; // 宽度设为原宽度
			h3 = w1 * h2 / w2; // 高度按比较计算
		}
		x = (w1 - w3) / 2;
		y = (h1 - h3) / 2;
		return new Rectangle(x > 0 ? x : 0, y > 0 ? y : 0, w3, h3);
	}

	/**
	 * 图片缩放后大小
	 * 
	 * @param w1
	 * @param h1
	 * @param w2
	 * @param h2
	 * @return
	 */
	private static Rectangle getMaxZoomSize(int w1, int h1, int w2, int h2) {
		int w3, h3;
		if (w1 <= w2 && h1 <= h2) {
			w3 = w1;
			h3 = h1;
		} else if ((float) w1 / w2 >= (float) h1 / h2) {
			w3 = w2;
			h3 = w2 * h1 / w1;
		} else {
			h3 = h2;
			w3 = h2 * w1 / h1;
		}
		return new Rectangle(w3, h3);
	}

}
