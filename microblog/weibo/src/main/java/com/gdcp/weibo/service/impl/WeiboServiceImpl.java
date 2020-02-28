package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.NotifyDao;
import com.gdcp.weibo.dao.PraiseDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dao.WeiboImgDao;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.entity.WeiboImg;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.PraiseService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.ImageUtil;
import com.gdcp.weibo.util.PageCalculator;
import com.gdcp.weibo.util.PathUtil;


@Service
public class WeiboServiceImpl implements WeiboService{

	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private WeiboImgDao weiboImgDao;	
	@Autowired
	private NotifyDao notifyDao;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private PraiseDao praiseDao;
	
	@Override
	public WeiboExecution getWeiboList(Weibo weiboCondition, int pageIndex, int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Weibo> weiboList=weiboDao.queryWeiboList(weiboCondition, rowIndex, pageSize);
		List<Weibo> retuenWeiboList=new ArrayList<Weibo>();
		//遍历查询到的微博，并通过Id查询到对应图片并set进微博里
		for(Weibo tempWeibo:weiboList) {
				List<WeiboImg> weiboImgList=weiboImgDao.queryByWeiboId(tempWeibo.getWeiboId());
				if (weiboImgList.size()>0) {
					tempWeibo.setWeiboImgList(weiboImgList);
			}
			
			retuenWeiboList.add(tempWeibo);
		}
		int count=weiboDao.queryWeiboCount(weiboCondition);
		WeiboExecution we=new WeiboExecution();
		we.setCount(count);
		we.setWeiboList(retuenWeiboList);
		we.setStateInfo(WeiboStateEnum.SUCCESS.getStateInfo());
		we.setState(WeiboStateEnum.SUCCESS.getState());
		return we;
	}
	
	@Override
	@Transactional
		//1.往tb_weibo写入商品信息，获取weiboId
		//2.结合weiboId批量处理微博图片
		//3.将微博图片列表批量插入tb_weibo_img中
	public WeiboExecution addWeibo(Weibo weibo, List<ImageHolder> weiboImgHolderList) 
			throws WeiboOperationException {
		if (weibo!=null && weibo.getAuthor()!=null && weibo.getAuthor().getUserId()!=null) {
			weibo.setPublishTime(new Date());
			try {
				//新增微博信息
				int effectedNum=weiboDao.insertWeibo(weibo);
				if (effectedNum<=0) {
					throw new WeiboOperationException("微博发布失败");
				}
			} catch (Exception e) {
				throw new WeiboOperationException("微博发布失败"+e.toString());
			}
			//若微博图片不为空则添加
			if(weiboImgHolderList!=null&&weiboImgHolderList.size()>0) {
				addWeiboImgList(weibo,weiboImgHolderList);
			}
			return new WeiboExecution(WeiboStateEnum.SUCCESS,weibo);
		}else {
			//传参为空则返回空值错误信息
			return new WeiboExecution(WeiboStateEnum.EMPTY);
		}
	}

	/**
	 * 批量添加微博图片
	 * @param weibo
	 * @param weiboImgHolderList
	 */
	private void addWeiboImgList(Weibo weibo, List<ImageHolder> weiboImgHolderList) {
		//获取图片存储路径，直接存放到相应用户的微博文件夹底下
		String dest=PathUtil.getWeiboImagePath(
				weibo.getAuthor().getUserId(), weibo.getWeiboId());
		List<WeiboImg> weiboImgList=new ArrayList<WeiboImg>();
		//遍历图片一次去处理，并添加进WeiboImg实体类里面
		for(ImageHolder weiboImgHolder:weiboImgHolderList) {
			//将图片创建放入目录，返回相对途径
			String imgAddr=ImageUtil.generateNormalImg(weiboImgHolder, dest);
			WeiboImg weiboImg=new WeiboImg();
			weiboImg.setImgAddr(imgAddr);
			weiboImg.setWeiboId(weibo.getWeiboId());
			weiboImgList.add(weiboImg);
		}
		//如果确实用图片需要添加的，就执行批量添加操作
		if (weiboImgList.size()>0) {
			try {
				int effectedNum=weiboImgDao.batchInsertWeiboImg(weiboImgList);
				if (effectedNum<=0) {
					throw new WeiboOperationException("创建微博图片失败");
				}
			} catch (Exception e) {
				throw new WeiboOperationException("创建微博图片失败"+e.toString());
			}
		}
		
		
	}

	@Override
	public WeiboExecution deleteWeiboById(long authorId, long weiboId) {
		if (authorId<0||weiboId<0) {
			return new WeiboExecution(WeiboStateEnum.EMPTY);
		}else {
			try {
				int effectedNum=weiboDao.deleteWeiboById(authorId,weiboId);
				if (effectedNum>0) {
					return new WeiboExecution(WeiboStateEnum.SUCCESS);
				}else {
					return new WeiboExecution(WeiboStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new WeiboOperationException("deleteWeibo error :"+e.toString());
			}
			
		}
	}

	@Override
	public Weibo getWeiboByWeiboId(Long weiboId) {
		if (weiboId<=0) {
			return null;
		}else {
			Weibo weibo=weiboDao.queryWeiboById(weiboId);
			List<WeiboImg> weiboImgList=weiboImgDao.queryByWeiboId(weibo.getWeiboId());
			if (weiboImgList.size()>0) {
				weibo.setWeiboImgList(weiboImgList);
		  }
			return weibo;
		}
	}

	@Override
	public WeiboExecution getAtMyWeibosByTargetId(long targetId) {
		if(targetId<=0) {
			return new WeiboExecution(WeiboStateEnum.EMPTY);
		}else {
			try {
				List<Weibo> weiboList=new ArrayList<>();
				Notify notifyCondition=new Notify();
				notifyCondition.setAction(1);
				notifyCondition.setTargetId(targetId);
				NotifyExecution ne=notifyService.getNotifyList(notifyCondition);
				if (ne.getState()==NotifyStateEnum.SUCCESS.getState()&&ne.getCount()>0) {
					System.out.println(ne.getNotifyList().size());
					for(Notify notify : ne.getNotifyList()) {
						if (notify.getWeiboId()>0) {
							Weibo weibo=weiboDao.queryWeiboById(notify.getWeiboId());
							List<WeiboImg> weiboImgList=weiboImgDao.queryByWeiboId(weibo.getWeiboId());
							if (weiboImgList.size()>0) {
								weibo.setWeiboImgList(weiboImgList);
						    }
							if (weibo!=null) {
								weiboList.add(weibo);
							}
						}
					}
					return new WeiboExecution(WeiboStateEnum.SUCCESS,weiboList,weiboList.size());
				}else {
					return new WeiboExecution(WeiboStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new WeiboOperationException("getAtMyWeibosByTargetId error :"+e.toString());
			}
		}
	}

	@Override
	public WeiboExecution getHotWeiboList(int pageIndex, int pageSize) {
			int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<Weibo> weiboList=weiboDao.queryHotWeiboList(new Weibo(), rowIndex, pageSize);
			List<Weibo> retuenWeiboList=new ArrayList<Weibo>();
			//遍历查询到的微博，并通过Id查询到对应图片并set进微博里
			for(Weibo tempWeibo:weiboList) {
					List<WeiboImg> weiboImgList=weiboImgDao.queryByWeiboId(tempWeibo.getWeiboId());
					if (weiboImgList.size()>0) {
						tempWeibo.setWeiboImgList(weiboImgList);
				}
				
				retuenWeiboList.add(tempWeibo);
			}
			int count=weiboDao.queryWeiboCount(new Weibo());
			WeiboExecution we=new WeiboExecution();
			we.setCount(count);
			we.setWeiboList(retuenWeiboList);
			we.setStateInfo(WeiboStateEnum.SUCCESS.getStateInfo());
			we.setState(WeiboStateEnum.SUCCESS.getState());
			return we;
	}

	@Override
	public WeiboExecution updateWeibo(Weibo weibo) {
		if (weibo==null) {
			return new WeiboExecution(WeiboStateEnum.EMPTY);
		}else {
			Praise praiseCondition=new Praise();
			praiseCondition.setWeiboId(weibo.getWeiboId());
			praiseCondition.setState(1);
			int praiseCount=praiseDao.queryPraiseCount(praiseCondition);
			weibo.setPraiseCount(praiseCount);
		    int effectedNum=weiboDao.updateWeibo(weibo);
		    if (effectedNum>0) {
		    	return new WeiboExecution(WeiboStateEnum.SUCCESS);
			}else {
				return new WeiboExecution(WeiboStateEnum.INNER_ERROR);
			}
		}
	}


}
