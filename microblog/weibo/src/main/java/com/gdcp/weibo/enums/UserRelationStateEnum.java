package com.gdcp.weibo.enums;

public enum UserRelationStateEnum {
	RELATION(0, "审核中"), OFFLINE_USER(-1, "非法用户"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"),
	FORWARD_RELATION(3,"顺向关注"),BACKWARD_RELATION(4,"逆向关注"),MUTUAL_RELATION(5,"互相关注"),
	INNER_ERROR(-1001, "内部系统错误"),NULL_USERRELATIONID(-1002,"userRelationId为空"),
	NULL_USERRELATION(-1003,"用户关系为空"),EMPTY(-1004,"传入参数为空"),REPEAT_ERROR(-1005,"重复添加关注");
    private int state;
    private String stateInfo;
    
    private UserRelationStateEnum(int state,String stateInfo) {
    	this.state = state;
    	this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static UserRelationStateEnum stateOf(int state) {
    	for(UserRelationStateEnum stateEnum :values()) {
    		if (stateEnum.getState()==state) {
				return stateEnum;
			}
    	}
    	return null;
    }
    /**
     * 依据传入的state返回相应的stateInfo值
     */
    public static String stateInfoOf(int state) {
    	for(UserRelationStateEnum stateEnum :values()) {
    		if (stateEnum.getState()==state) {
				return stateEnum.getStateInfo();
			}
    	}
    	return null;
    }
	public int getState() {
		return state;
	}
	
	public String getStateInfo() {
		return stateInfo;
	}
}
