package com.gdcp.weibo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdcp.weibo.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	// 通过线程逆推到类加载得到classPath绝对路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//自定义时间格式
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	
	/**
	 * 将CommonsMultipartFile转换成File类
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile=new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	/**
	 * 处理用户头像，并返回新生成图片的相对值路径
	 * @param targetAddr是图片存储路径
	 * @return
	 */
    public static String generateThumbnail(ImageHolder thumbnail,String targetAddr) {
    	//获取文件的随机名
    	String realFileName= getRandomFileName();
    	//获取文件扩展名
    	String extension=getFileExtension(thumbnail.getImageName());  	
    	//创建文件目录
    	makeDirPath(targetAddr);
    	//相对路径
    	String relativeAddr= targetAddr + realFileName +extension;
    	logger.debug("current relativeAddr addr is:" +relativeAddr);
    	//图片的完整路径
    	File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
    	logger.debug("current complete addr is:" + PathUtil.getImgBasePath()+relativeAddr);
    	//图片处理，调用Thumbnails生成带有水印的图片
    	try {
//			Thumbnails.of(thumbnail.getImage()).size(200, 200)
//			.outputQuality(0.8f).toFile(dest);
		   Thumbnails.of(thumbnail.getImage()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"watermark.jpg")), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
    	return relativeAddr;
    }
    
    /**
     * 校园商铺项目时：处理详情图，并返回新生成图片的相对值路径
     * 处理微博图片，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail,String targetAddr) {
    	//获取文件的随机名
    	String realFileName= getRandomFileName();
    	//获取文件扩展名
    	String extension=getFileExtension(thumbnail.getImageName());  	
    	//创建文件目录
    	makeDirPath(targetAddr);
    	//相对路径
    	String relativeAddr= targetAddr + realFileName +extension;
    	logger.debug("current relativeAddr addr is:" +relativeAddr);
    	//图片的完整路径
    	File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
    	logger.debug("current complete addr is:" + PathUtil.getImgBasePath()+relativeAddr);
    	//图片处理,调用Thumbnails生成带有水印的图片
    	try {
			/*Thumbnails.of(thumbnail.getImage()).size(337, 600)
			.outputQuality(0.9f).toFile(dest);*/
			Thumbnails.of(thumbnail.getImage()).size(400, 400)
			.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
    	return relativeAddr;
    }
    
	/**
	 * 图片处理与存储到目标路径
	 * @param imgs
	 * @param targetAddr
	 * @return
	 */
//	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
//		int count = 0;
//		List<String> relativeAddrList = new ArrayList<String>();
//		if (imgs != null && imgs.size() > 0) {
//			makeDirPath(targetAddr);
//			for (CommonsMultipartFile img : imgs) {
//				String realFileName = getRandomFileName();
//				String extension=getFileExtension(img);
//				String relativeAddr = targetAddr + realFileName + count + extension;
//				File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//				count++;
//				try {
//					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
//				} catch (IOException e) {
//					throw new RuntimeException("创建图片失败：" + e.toString());
//				}
//				relativeAddrList.add(relativeAddr);
//			}
//		}
//		return relativeAddrList;
//	}
	
    
    /**
     * 创建目标路径所涉及到的目录，即E:/Eclipse_project/work/ayou/xxx.jpg,
     * 那么 Eclipse_project work ayou 这三个文件加到得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
    	//获取文件流绝对路径
		String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
     * 获取输入文件流的扩展名(.jpg .png 等)
     * @param cFile
     * @return
     */
    private static String getFileExtension(String  fileName) {
    	//获取输入文件流的原文件名
//		String originalFileName = cFile.getOriginalFilename();
//		String originalFileName = cFile.getName();
		//获取输入文件名"."后面的字符串
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     * @return
     */
	public static String getRandomFileName() {
		//获取随机五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr=sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	/**
	 * storePath是文件的路径还是目录的路径，
	 * 如果storePath是文件的路径则删除该文件，最后目录路径也要删掉
	 * 如果storePath是目录的路径则删除改目录，
	 * @param storePath
	 */
	public static void delectFileOrPath(String storePath) {
		File fileOrPath= new File(PathUtil.getImgBasePath()+storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[]=fileOrPath.listFiles();
				for(int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
			//无论是什么路径最后改路径都删除 
			fileOrPath.delete();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// 给小黄人图片添加水印图片存到小黄人new
		Thumbnails.of(new File("E:/microblog/images/dilireba.jpg")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
				.outputQuality(0.8f).toFile("E:/microblog/images/new.jpg");
	}
}
