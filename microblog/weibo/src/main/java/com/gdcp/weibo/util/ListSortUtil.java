package com.gdcp.weibo.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.entity.Weibo;

/**
 * List的排序
 * @author 刚刚好
 *
 */
public class ListSortUtil{
	
	/**
	 * 用户关系列表按发布时间降序排序
	 * @param list
	 * @return
	 */
	public static List<UserRelation> ListSortOfUserRelation(List<UserRelation> relationlist) {
        Collections.sort(relationlist, new Comparator<UserRelation>() {
            @Override
            public int compare(UserRelation o1, UserRelation o2) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = o1.getLastEditTime();
                    Date dt2 = o2.getLastEditTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
		return relationlist;
    }
	
	/**
	 * 微博按发布时间降序排序
	 * @param list
	 * @return
	 */
	public static List<Weibo> ListSort(List<Weibo> list) {
        Collections.sort(list, new Comparator<Weibo>() {
            @Override
            public int compare(Weibo o1, Weibo o2) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = o1.getPublishTime();
                    Date dt2 = o2.getPublishTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
		return list;
    }
	
	public static List<Comment> ListSortOfComments(List<Comment> list) {
        Collections.sort(list, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = o1.getTime();
                    Date dt2 = o2.getTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
		return list;
    }




}
