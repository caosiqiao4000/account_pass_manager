package mobile.http;

/**
 * 请求状态记录 为使用我们备份域名作准备
 * 
 * @author Administrator
 * 
 */
public class ResponseStateRecore {
	// 请求结果状态码,当为4XX 或者5XX时,使用备用域名
	private int responseResultStateCode;
	private String responseStr;
	private String md5;

	public ResponseStateRecore() {
	};

	public ResponseStateRecore(int requestResultStateCode, String responseStr) {
		this.responseResultStateCode = requestResultStateCode;
		this.responseStr = responseStr;
	}

	public int getRequestResultStateCode() {
		return responseResultStateCode;
	}

	public void setRequestResultStateCode(int requestResultStateCode) {
		this.responseResultStateCode = requestResultStateCode;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
