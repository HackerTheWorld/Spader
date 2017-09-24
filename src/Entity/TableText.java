package Entity;

import java.util.List;

public class TableText {
	private List linkList;
	private StringBuffer text;
	private int tablerow;
	private int totalrow;
	private String sign;
	
	public List getLinkList() {
		return linkList;
	}
	public void setLinkList(List linkList) {
		this.linkList = linkList;
	}
	public StringBuffer getText() {
		return text;
	}
	public void setText(StringBuffer text) {
		this.text = text;
	}
	public int getTablerow() {
		return tablerow;
	}
	public void setTablerow(int tablerow) {
		this.tablerow = tablerow;
	}
	public int getTotalrow() {
		return totalrow;
	}
	public void setTotalrow(int totalrow) {
		this.totalrow = totalrow;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
