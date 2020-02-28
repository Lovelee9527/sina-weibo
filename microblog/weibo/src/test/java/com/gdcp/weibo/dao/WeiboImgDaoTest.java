package com.gdcp.weibo.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.entity.WeiboImg;
import com.gdcp.weibo.util.ImageUtil;
import com.gdcp.weibo.util.PathUtil;

public class WeiboImgDaoTest extends BaseTest{
	@Autowired
	private WeiboImgDao weiboImgDao;
	
	@Test
	public void testQueryByWeiboIdANDCount() {
		List<WeiboImg> weiboImgList=weiboImgDao.queryByWeiboId(3);
		int count=weiboImgDao.queryCountByWeiboId(3);
		System.out.println(count);
		System.out.println(weiboImgList.size());
		System.out.println(weiboImgList.get(0).getImgAddr());
		System.out.println(weiboImgList.get(0).getPriority());
		System.out.println(weiboImgList.get(1).getImgAddr());
		System.out.println(weiboImgList.get(1).getPriority());
	}
	
//	@Test
//	public void testinserWeiboImg() throws FileNotFoundException {
//		List<WeiboImg> weiboImgList=new ArrayList<WeiboImg>();
//		File imgFile1=new File("C:/Users/刚刚好/Pictures/微博头像/1.jpg");
//		InputStream is1=new FileInputStream(imgFile1);
//		ImageHolder ih1=new ImageHolder(imgFile1.getName(), is1);
//		File imgFile2=new File("C:/Users/刚刚好/Pictures/微博头像/2.jpg");
//		InputStream is2=new FileInputStream(imgFile2);
//		ImageHolder ih2=new ImageHolder(imgFile2.getName(), is2);
//		File imgFile3=new File("C:/Users/刚刚好/Pictures/微博头像/3.jpg");
//		InputStream is3=new FileInputStream(imgFile3);
//		ImageHolder ih3=new ImageHolder(imgFile3.getName(), is3);
//		File imgFile4=new File("C:/Users/刚刚好/Pictures/微博头像/4.jpg");
//		InputStream is4=new FileInputStream(imgFile4);
//		ImageHolder ih4=new ImageHolder(imgFile4.getName(), is4);
//		File imgFile5=new File("C:/Users/刚刚好/Pictures/微博头像/5.jpg");
//		InputStream is5=new FileInputStream(imgFile5);
//		ImageHolder ih5=new ImageHolder(imgFile5.getName(), is5);
//		File imgFile6=new File("C:/Users/刚刚好/Pictures/微博头像/6.jpg");
//		InputStream is6=new FileInputStream(imgFile6);
//		ImageHolder ih6=new ImageHolder(imgFile6.getName(), is6);
//		File imgFile7=new File("C:/Users/刚刚好/Pictures/微博头像/7.jpg");
//		InputStream is7=new FileInputStream(imgFile7);
//		ImageHolder ih7=new ImageHolder(imgFile7.getName(), is7);
//		File imgFile8=new File("C:/Users/刚刚好/Pictures/微博头像/8.jpg");
//		InputStream is8=new FileInputStream(imgFile8);
//		ImageHolder ih8=new ImageHolder(imgFile8.getName(), is8);
//		File imgFile9=new File("C:/Users/刚刚好/Pictures/微博头像/9.jpg");
//		InputStream is9=new FileInputStream(imgFile9);
//		ImageHolder ih9=new ImageHolder(imgFile9.getName(), is9);
//		File imgFile10=new File("C:/Users/刚刚好/Pictures/微博头像/10.jpg");
//		InputStream is10=new FileInputStream(imgFile10);
//		ImageHolder ih10=new ImageHolder(imgFile10.getName(), is10);
//		WeiboImg img1=new WeiboImg();
//		img1.setImgAddr(imgAddr);
//		WeiboImg img2=new WeiboImg();
//		WeiboImg img3=new WeiboImg();
//		WeiboImg img4=new WeiboImg();
//		WeiboImg img5=new WeiboImg();
//		WeiboImg img6=new WeiboImg();
//		WeiboImg img7=new WeiboImg();
//		WeiboImg img8=new WeiboImg();
//		WeiboImg img9=new WeiboImg();
//		WeiboImg img10=new WeiboImg();
//		weiboImgList.
//		int effectedNum= weiboImgDao.batchInsertWeiboImg(weiboImgList);
//	}
	
	/**
	 * 添加微博图片地址
	 * @param weibo
	 * @param thumbnail
	 */
	private void addWeiboImg(Weibo weibo,ImageHolder thumbnail) {
		//获取shop图片目录的相对值路径
		String dest=PathUtil.getWeiboImagePath(23, 2);
		String headImgAddr= ImageUtil.generateNormalImg(thumbnail,dest);
		
//		weibo.setAddr
	}
	

}
