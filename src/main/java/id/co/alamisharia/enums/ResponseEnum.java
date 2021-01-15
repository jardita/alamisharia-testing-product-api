package id.co.alamisharia.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseEnum {
	SUCCESS(200,"Success","", null),
	BAD_REQUEST(400, "Error", "Bad Request", null),
	NOT_FOUND(404, "Error", "Not Found", null),
	INTERNAL_SERVER_ERROR(500,"Error","Internal Server Error", null);

	
	private int code;
	private String status;
	private String message;
	private Object data;

	ResponseEnum(int code, String status, String message, Object data) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResponseEnum getEnum(int value) {
		  for(ResponseEnum e: ResponseEnum.values()) {
		    if(e.code == value) {
		      return e;
		    }
		  }
		  return INTERNAL_SERVER_ERROR;// not found
	}
	

}
