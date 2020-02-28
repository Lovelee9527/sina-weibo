package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdcp.weibo.dao.CollectionDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dto.CollectionExecution;
import com.gdcp.weibo.entity.Collection;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CollectionStateEnum;
import com.gdcp.weibo.exceptions.CollectionOperationExeption;
import com.gdcp.weibo.service.CollectionService;
import com.gdcp.weibo.service.WeiboService;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionDao collectionDao;
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private WeiboDao weiboDao;

	@Override
	public CollectionExecution checkCollection(Collection collection) {
		if (collection == null) {
			return new CollectionExecution(CollectionStateEnum.EMPTY);
		} else {
			try {
				int effectedNum = -1;
				CollectionExecution ce = getCollectionList(collection);
				if (ce.getState() == CollectionStateEnum.SUCCESS.getState() && ce.getCount() > 0) {
					effectedNum = deleteCollection(collection);
				} else {
//					Weibo weibo = weiboDao.queryWeiboById(collection.getWeboId());
//					if (weibo.getAuthor().getUserId() != collection.getUserId()) {
						effectedNum = addCollection(collection);
//					}
				}
				if (effectedNum > 0) {
					return new CollectionExecution(CollectionStateEnum.SUCCESS);
				} else {
					return new CollectionExecution(CollectionStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new CollectionOperationExeption("checkCollection error: " + e.toString());
			}
		}
	}

	private int addCollection(Collection collection) {
		collection.setState(1);
		collection.setCreateTime(new Date());
		collection.setLastEditTime(new Date());
		int effectedNum = collectionDao.insertCollection(collection);
		return effectedNum;
	}

	private int deleteCollection(Collection collection) {
		collection.setState(1);
		int effectedNum = collectionDao.deleteCollection(collection);
		return effectedNum;
	}

	@Override
	public CollectionExecution getCollectionList(Collection collectionCondition) {
		if (collectionCondition == null || collectionCondition.getUserId() <= 0) {
			return new CollectionExecution(CollectionStateEnum.EMPTY);
		} else {
			try {
				List<Weibo> weiboList = new ArrayList<Weibo>();
				collectionCondition.setState(1);
				int count = collectionDao.queryCollectionCount(collectionCondition);
				List<Collection> collectionList = collectionDao.queryCollectionList(collectionCondition);
				for (Collection collection : collectionList) {
					Weibo weibo = weiboService.getWeiboByWeiboId(collection.getWeboId());
					weiboList.add(weibo);
				}
				return new CollectionExecution(CollectionStateEnum.SUCCESS, collectionList, count, weiboList);
			} catch (Exception e) {
				throw new CollectionOperationExeption("deleteCollection error: " + e.toString());
			}
		}
	}

}
