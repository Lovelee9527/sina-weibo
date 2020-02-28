package com.gdcp.weibo.enums;

public enum PraiseStateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法用户"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"),
	INNER_ERROR(-1001, "内部系统错误"),NULL_PRAISEID(-1002,"praiseId为空"),
	NULL_Praise(-1003,"通知信息为空"),EMPTY(-1002,"传入参数为空"),ERROR_WEIBO(-1003,"无效微博");
    private int state;
    private String stateInfo;
    
    private PraiseStateEnum(int state,String stateInfo) {
    	this.state = state;
    	this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static PraiseStateEnum stateOf(int state) {
    	for(PraiseStateEnum stateEnum :values()) {
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
    	for(PraiseStateEnum stateEnum :values()) {
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
