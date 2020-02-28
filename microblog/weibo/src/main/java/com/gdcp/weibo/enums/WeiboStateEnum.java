package com.gdcp.weibo.enums;

public enum WeiboStateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法用户"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"),
	INNER_ERROR(-1001, "内部系统错误"),NULL_WEIBOID(-1002,"WeiboId为空"),
	NULL_WEIBO(-1003,"微博信息为空"),EMPTY(-1002,"传入参数为空");
    private int state;
    private String stateInfo;
    
    private WeiboStateEnum(int state,String stateInfo) {
    	this.state = state;
    	this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static WeiboStateEnum stateOf(int state) {
    	for(WeiboStateEnum stateEnum :values()) {
    		if (stateEnum.getState()==state) {
				return stateEnum;
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
