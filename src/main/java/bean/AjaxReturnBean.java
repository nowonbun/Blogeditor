package bean;

public class AjaxReturnBean {
	
	public static final String WARMING = "W";
	public static final String INFO = "I";
	public static final String ERROR = "E";
	public static final String SUCCESS = "S";
	
	private String type;
	private String message;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
