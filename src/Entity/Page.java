package Entity;

import java.io.Serializable;



public class Page implements Serializable{
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public StringBuffer getText() {
		return text;
	}
	public void setText(StringBuffer text) {
		this.text = text;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String[] getRefrences() {
		return refrences;
	}
	public void setRefrences(String[] refrences) {
		this.refrences = refrences;
	}
	
	private String oldurl;
	public String getOldurl() {
		return oldurl;
	}
	public void setOldurl(String oldurl) {
		this.oldurl = oldurl;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	private String url;
	private StringBuffer text;
	private String html;
	private int code;
	private int layer;
	private String[] refrences;
	private int num;
	

}
