package com.shai.manage.respondbean;

/**
 * @createTime: 2011-12-11 下午10:32:15
 * @version: 1.0
 * @desc :处理结果基类.
 */
public class BaseResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// -- 返回代码定义， 按项目的规则进行定义。
	public final int SUCCESS = 1;// 处理成功

	public static final int FAIL = 0;// 处理失败

	public static final int UNDEAL = -1;// 未处理

	public static final int PARAMETER_ERROR = 101;// 参数异常

	public static final int SYSTEM_ERROR = 500;// 系统异常

	public int errCode = UNDEAL;// 返回码给这个参数

	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

//	public String errorMessage;
	private String msg;

	public BaseResult() {

	}

	public BaseResult(int errorCode, String errorMessage) {
		this.errCode = errorCode;
		this.msg = errorMessage;
	}

	/**
	 * 设置返回结果.
	 */
	public void setResult(int resultCode, String resultMessage) {
		this.errCode = resultCode;
		this.msg = resultMessage;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
