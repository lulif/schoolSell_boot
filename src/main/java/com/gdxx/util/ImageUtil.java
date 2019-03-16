package com.gdxx.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Configuration
public class ImageUtil {

	private static  String winPath;

	private static  String linuxPath;

	private static  String shopPath;
	
	@Value("${win.base.path}")
	public  void setWinPath(String winPath) {
		ImageUtil.winPath = winPath;
	}

	@Value("${linux.base.path}")
	public  void setLinuxPath(String linuxPath) {
		ImageUtil.linuxPath = linuxPath;
	}

	@Value("${shop.relevent.path}")
	public  void setShopPath(String shopPath) {
		ImageUtil.shopPath = shopPath;
	}
	

	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr, boolean details) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		String os = System.getProperty("os.name");
		String path = "";
		if (os.toLowerCase().startsWith("win")) {
			path = winPath + "\\watermark";
		} else {
			path = linuxPath + "/watermark";
		}
		makeDirPath(targetAddr);

		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImageBasePath() + relativeAddr);
		try {
			if (!details) {
				Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
						.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(path + "/watermark.png")), 0.25f)
						.outputQuality(0.8f).toFile(dest);
			} else {
				Thumbnails.of(thumbnail.getInputStream()).size(337, 640)
						.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(path + "/watermark.png")), 0.25f)
						.outputQuality(0.8f).toFile(dest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;

	}

	// 创建目标路径所涉及到的目录
	private static void makeDirPath(String targetAddr) {
		String raelFileParentPath = PathUtil.getImageBasePath() + targetAddr;
		File dirPath = new File(raelFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	// 获取文件后缀名
	private static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));

	}

	// 生成随机文件名
	private static String getRandomFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		Random ranDom = new Random();
		int ranNum = ranDom.nextInt(89999) + 10000;
		return sdf.format(new Date()) + ranNum;
	}

	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImageBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				//如果是个目录的话就一个个删
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

}
