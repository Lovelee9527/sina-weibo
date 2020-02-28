package com.gdcp.weibo.enums;

public enum ReplyStateEnum {
	SUCCESS(0, "操作成功"), ERROR(-1001, "操作失败"),EMPTY(-1004,"参数为空"),
	INNER_ERROR(-1003, "内部系统错误"),NULL_REPLYID(-1004,"ReplyId为空");
	
	private int state;
    private String stateInfo;
    
    private ReplyStateEnum(int state,String stateInfo) {
    	this.state = state;
    	this.stateInfo = stateInfo;
    }
    
    public int getState() {
		return state;
	}
	
	public String getStateInfo() {
		return stateInfo;
	}

    /**
     * 依据传入的state返回相应的enum值
     */
    public static ReplyStateEnum stateOf(int state) {
    	for(ReplyStateEnum stateEnum :values()) {
    		if (stateEnum.getState()==state) {
				return stateEnum;
			}
    	}
    	return null;
    }
}
