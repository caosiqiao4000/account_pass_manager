package com.sh.account.respondbean;


/**
 * @createTime: 2011-12-11 下午10:32:15
 * @version: 1.0
 * @desc :处理结果基类.
 */
public class BaseResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// -- 返回代码定义， 按项目的规则进行定义。
	public static final int SUCCESS = 1;// 处理成功

	public static final int FAIL = 0;// 处理失败

	public static final int UNDEAL = -1;// 未处理

	public static final int PARAMETER_ERROR = 101;// 参数异常

	public static final int SYSTEM_ERROR = 500;// 系统异常

	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

	public int errorCode = UNDEAL;

	public String errorMessage;

	public BaseResult() {

	}

	public BaseResult(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 设置返回结果.
	 */
	public void setResult(int resultCode, String resultMessage) {
		this.errorCode = resultCode;
		this.errorMessage = resultMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}