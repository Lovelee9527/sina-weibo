package com.gdcp.weibo.enums;

public enum CommentStateEnum {
	
	SUCCESS(0, "操作成功"), ERROR(-1001, "操作失败"), EMPTY(-1002,"数值为空"),
	INNER_ERROR(-1003, "内部系统错误"),NULL_COMMENTID(-1004,"CommentId为空");
	
	private int state;
    private String stateInfo;
    
    private CommentStateEnum(int state,String stateInfo) {
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
    public static CommentStateEnum stateOf(int state) {
    	for(CommentStateEnum stateEnum :values()) {
    		if (stateEnum.getState()==state) {
				return stateEnum;
			}
    	}
    	return null;
    }
}
