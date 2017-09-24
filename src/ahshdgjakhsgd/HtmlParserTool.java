//package ahshdgjakhsgd;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.NodeClassFilter;
//import org.htmlparser.filters.OrFilter;
//import org.htmlparser.nodes.TagNode;
//import org.htmlparser.nodes.TextNode;
//import org.htmlparser.tags.Div;
//import org.htmlparser.tags.Html;
//import org.htmlparser.tags.ImageTag;
//import org.htmlparser.tags.LinkTag;
//import org.htmlparser.tags.ParagraphTag;
//import org.htmlparser.tags.ScriptTag;
//import org.htmlparser.tags.SelectTag;
//import org.htmlparser.tags.Span;
//import org.htmlparser.tags.StyleTag;
//import org.htmlparser.tags.TableHeader;
//import org.htmlparser.tags.TableTag;
//import org.htmlparser.util.NodeIterator;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.htmlparser.visitors.HtmlPage;
//
//import Webspader.DownloadPage;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableRow;
//import pigisliuchunqing.ExtractContext;
//import API.API;
//import Entity.Page;
//import Entity.PageContext;
//import Entity.TableColumnValid;
//import Entity.TableImfor;
//import Entity.TableValid;
//
//public class HtmlParserTool {
//	
//	String metakeywords = "<META content={0} name=keywords>";
//	String metatitle = "<TITLE>{0}</TITLE>";
//	String metadesc = "<META content={0} name=description>";
//	String netshap = "<p> 正文快照: 时间{0}</p> ";
//
//	String tempLeate = "<LI class=active><A href=\"{0}\" target=_blank>{1}</A></LI>";
//	String crop = "<p><A href=\"{0}\" target=_blank>{1}</A></p> ";
//
//	protected static final String lineSign = System
//			.getProperty("line.separator");
//	protected static final int lineSign_size = lineSign.length();
//	
//	private static DownloadPage downloadPage =new DownloadPage();
//
//
//	//获取网站上的一个连接
//	public static Set<String> extracLinks(String url,String code,LinkFilter filter){
//		//存放从网页上获取的url
//		Set<String> links=new HashSet<String>();
//		//调用页面分析方法
//		ExtractContext extractContext=new ExtractContext();
//		try{
//			Parser parser =new Parser(url);
//			if(code==null||code.equals(""))
//				parser.setEncoding(parser.getEncoding());
//			else
//				parser.setEncoding(code);
//			NodeFilter farmeFilter=new NodeFilter(){
//				public boolean accept(Node node){
//					if(node.getText().startsWith("frame src=")){
//						return true;
//					}
//					else
//						return false;
//				}
//			};
//			
//			//过滤<a>标签和<frame>标签
//			OrFilter orfilter=new OrFilter(new NodeClassFilter(
//					LinkTag.class),farmeFilter);
//			//获取所有的过滤的标签
//			NodeList list =parser.extractAllNodesThatMatch(orfilter);
//			for(int i=0;i<list.size();i++){
//				Node tag=list.elementAt(i);
//				//<a>
//				if(tag instanceof LinkTag){
//					LinkTag link=(LinkTag)tag;
//					String linkUrl=link.getLink();
//					if(filter.accpect(linkUrl))
//						links.add(linkUrl);
//				}else{
//					//获取<farme>
//					String frame=tag.getText();
//					int start =frame.indexOf("src=");
//					int end =frame.indexOf(" ");
//					if(end==-1)
//						end=frame.indexOf(">");
//					String frameurl=frame.substring(start+5,end-1);
//					if(filter.accpect(frameurl))
//						links.add(frameurl);				
//				}
//			}
//			
//		}catch(Exception e){
//			
//		}
//		return links;
//	}
//	
//	//分析页面信息
//	public static List analysisPage(String url){
//		List list=new ArrayList();
//		PageContext page=new PageContext();
//		try {
//			Parser parser =new Parser(url);
//			 for (NodeIterator nodelist = parser.elements (); nodelist.hasMoreNodes(); )
//			 {
//				 Node tag=nodelist.nextNode();
//				 String tagstr=tag.getText();
//					ArrayList htmllist= extractHtml(tag,page,url);
//					if(htmllist!=null)
//						list.addAll(htmllist);
//			 }
//			 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	
//	/**
//	 * 递归钻取正文信息
//	 * 
//	 * @param nodeP
//	 * @return
//	 */
//	protected static ArrayList extractHtml(Node nodeP, PageContext context, String siteUrl)
//			throws Exception {
//		NodeList nodeList = nodeP.getChildren();
//		boolean bl = false;
//		if ((nodeList == null) || (nodeList.size() == 0)) {
//			if (nodeP instanceof ParagraphTag) {
//				ArrayList tableList = new ArrayList();
//				StringBuffer temp = new StringBuffer();
//				temp.append("<p style=\"TEXT-INDENT: 2em\">");
//				tableList.add(temp);
//				temp = new StringBuffer();
//				temp.append("</p>").append(lineSign);
//				temp.append("</p>");
//				tableList.add(temp);
//				return tableList;
//			}
//			return null;
//		}
//		if ((nodeP instanceof TableTag) || (nodeP instanceof Div)) {
//			bl = true;
//		}
//		if (nodeP instanceof ParagraphTag) {
//			ArrayList tableList = new ArrayList();
//			StringBuffer temp = new StringBuffer();
//			temp.append("<p style=\"TEXT-INDENT: 2em\">");
//			tableList.add(temp);
//			extractParagraph(nodeP, siteUrl, tableList);
//			temp = new StringBuffer();
//			temp.append("</p>").append(lineSign);
//			temp.append("</p>");
//			tableList.add(temp);
//			return tableList;
//		}
//		ArrayList<Object> tableList = new ArrayList();
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				if (node instanceof LinkTag) {
//					tableList.add(node);
//					setLinkImg(node, siteUrl);
//				} else if (node instanceof ImageTag) {
//					ImageTag img = (ImageTag) node;
//					if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
//						img.setImageURL(siteUrl + img.getImageURL());
//					} else {
//						img.setImageURL(img.getImageURL());
//					}
//					tableList.add(node);
//				} else if (node instanceof ScriptTag
//						|| node instanceof StyleTag
//						|| node instanceof SelectTag) {
//				} else if (node instanceof TextNode) {
//					if (node.getText().length() > 0) {
//						StringBuffer temp = new StringBuffer();
////						String text = collapse(node.getText().replaceAll(
////								"&nbsp;", "").replaceAll("　", ""));
//       					String text = collapse(node.getText());
//						temp.append(text.trim());
//						tableList.add(temp);
//					}
//				} else {
//					if (node instanceof TableTag || node instanceof Div) {
//						TableValid tableValid = new TableValid();
//						isValidTable(node, tableValid);
//						if (tableValid.getTrnum() > 2) {
//							tableList.add(node);
//							continue;
//						}
//					}
//					List tempList = extractHtml(node, context, siteUrl);
//					if ((tempList != null) && (tempList.size() > 0)) {
//						Iterator ti = tempList.iterator();
//						while (ti.hasNext()) {
//							tableList.add(ti.next());
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		if ((tableList != null) && (tableList.size() > 0)) {
//			if (bl) {
//				StringBuffer temp = new StringBuffer();
//				Iterator ti = tableList.iterator();
//				int wordSize = 0;
//				StringBuffer node;
//				int status = 0;
//				StringBuffer lineStart = new StringBuffer(
//						"<p style=\"TEXT-INDENT: 2em\">");
//				StringBuffer lineEnd = new StringBuffer("</p>" + lineSign);
//				//StringBuffer lineEnd = new StringBuffer("</p>");
//				while (ti.hasNext()) {
//					Object k = ti.next();
//					if (k instanceof LinkTag) {
//						if (status == 0) {
//							temp.append(lineStart);
//							status = 1;
//						}
//						node = new StringBuffer(((LinkTag) k).toHtml());
//						temp.append(node);
//					} else if (k instanceof ImageTag) {
//						if (status == 0) {
//							temp.append(lineStart);
//							status = 1;
//						}
//						node = new StringBuffer(((ImageTag) k).toHtml());
//						temp.append(node);
//					} else if (k instanceof TableTag) {
//						if (status == 0) {
//							temp.append(lineStart);
//							status = 1;
//						}
//
//						node = new StringBuffer(((TableTag) k).toHtml());
//						temp.append(node);
//					} else if (k instanceof Div) {
//						if (status == 0) {
//							temp.append(lineStart);
//							status = 1;
//						}
//						node = new StringBuffer(((Div) k).toHtml());
//						temp.append(node);
//					} else {
//						node = (StringBuffer) k;
//						if (status == 0) {
//							if (node.indexOf("<p") < 0) {
//								temp.append(lineStart);
//								temp.append(node);
//								wordSize = wordSize + node.length();
//								status = 1;
//							} else {
//								temp.append(node);
//								status = 1;
//							}
//						} else if (status == 1) {
//							if (node.indexOf("</p") < 0) {
//								if (node.indexOf("<p") < 0) {
//									temp.append(node);
//									wordSize = wordSize + node.length();
//								} else {
//									temp.append(lineEnd);
//									temp.append(node);
//									status = 1;
//								}
//							} else {
//								temp.append(node);
//								status = 0;
//							}
//						}
//					}
//				}
//				if (status == 1) {
//					temp.append(lineEnd);
//				}
//
//				if (wordSize > context.getNumber()) {
//					context.setNumber(wordSize);
//					context.setTextBuffer(temp);
//				}
//				return null;
//			} else {
//				return tableList;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 设置图象连接
//	 * 
//	 * @param nodeP
//	 * @param siteUrl
//	 */
//	private static void setLinkImg(Node nodeP, String siteUrl) {
//		NodeList nodeList = nodeP.getChildren();
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				if (node instanceof ImageTag) {
//					ImageTag img = (ImageTag) node;
//					if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
//						img.setImageURL(siteUrl + img.getImageURL());
//					} else {
//						img.setImageURL(img.getImageURL());
//					}
//					downloadPage.downLoad(img.getImageURL());
//				}
//				
//			}
//		} catch (Exception e) {
//			return;
//		}
//		return;
//	}
//
//	/**
//	 * 钻取段落中的内容
//	 * 
//	 * @param nodeP
//	 * @param siteUrl
//	 * @param tableList
//	 * @return
//	 */
//	private static List extractParagraph(Node nodeP, String siteUrl, List tableList) {
//		NodeList nodeList = nodeP.getChildren();
//		if ((nodeList == null) || (nodeList.size() == 0)) {
//			if (nodeP instanceof ParagraphTag) {
//				StringBuffer temp = new StringBuffer();
//				temp.append("<p style=\"TEXT-INDENT: 2em\">");
//				tableList.add(temp);
//				temp = new StringBuffer();
//				temp.append("</p>").append(lineSign);
//				//temp.append("</p>");
//				tableList.add(temp);
//				return tableList;
//			}
//			return null;
//		}
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				if (node instanceof ScriptTag || node instanceof StyleTag
//						|| node instanceof SelectTag) {
//				} else if (node instanceof LinkTag) {
//					tableList.add(node);
//					setLinkImg(node, siteUrl);
//				} else if (node instanceof ImageTag) {
//					ImageTag img = (ImageTag) node;
//					if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
//						img.setImageURL(siteUrl + img.getImageURL());
//					} else {
//						img.setImageURL(img.getImageURL());
//					}
//					tableList.add(node);
//				} else if (node instanceof TextNode) {
//					if (node.getText().trim().length() > 0) {
//						String text = collapse(node.getText().replaceAll(
//								"&nbsp;", "").replaceAll("　", ""));
//					//	String text = collapse(node.getText());
//						StringBuffer temp = new StringBuffer();
//						temp.append(text);
//						tableList.add(temp);
//					}
//				} else if (node instanceof Span) {
//					StringBuffer spanWord = new StringBuffer();
//					getSpanWord(node, spanWord);
//					if ((spanWord != null) && (spanWord.length() > 0)) {
////						String text = collapse(spanWord.toString().replaceAll(
////								"&nbsp;", "").replaceAll("　", ""));
//						String text = collapse(node.getText());
//						StringBuffer temp = new StringBuffer();
//						temp.append(text);
//						tableList.add(temp);
//					}
//				} else if (node instanceof TagNode) {
//					String tag = node.toHtml();
//					if (tag.length() <= 10) {
//						tag = tag.toLowerCase();
//						if ((tag.indexOf("strong") >= 0)
//								|| (tag.indexOf("b") >= 0)) {
//							StringBuffer temp = new StringBuffer();
//							temp.append(tag);
//							tableList.add(temp);
//						}
//					} else {
//						if (node instanceof TableTag || node instanceof Div) {
//							TableValid tableValid = new TableValid();
//							isValidTable(node, tableValid);
//							if (tableValid.getTrnum() > 2) {
//								tableList.add(node);
//								continue;
//							}
//						}
//						extractParagraph(node, siteUrl, tableList);
//					}
//				}
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		return tableList;
//	}
//
//	protected static void getSpanWord(Node nodeP, StringBuffer spanWord) {
//		NodeList nodeList = nodeP.getChildren();
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				if (node instanceof ScriptTag || node instanceof StyleTag
//						|| node instanceof SelectTag) {
//				} 
//				if (node instanceof TextNode) {
//					spanWord.append(node.getText());
//				} 
//				if (node instanceof Span) {
//					getSpanWord(node, spanWord);
//				} 
//				if (node instanceof ParagraphTag) {
//					getSpanWord(node, spanWord);
//				} 
//				if (node instanceof TagNode) {
//					String tag = node.toHtml().toLowerCase();
//					if (tag.length() <= 10) {
//						if ((tag.indexOf("strong") >= 0)
//								|| (tag.indexOf("b") >= 0)) {
//							spanWord.append(tag);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
//		return;
//	}
//
//	/**
//	 * 判断TABLE是否是表单
//	 * 
//	 * @param nodeP
//	 * @return
//	 */
//	private static void isValidTable(Node nodeP, TableValid tableValid) {
//		NodeList nodeList = nodeP.getChildren();
//		/** 如果该表单没有子节点则返回* */
//		if ((nodeList == null) || (nodeList.size() == 0)) {
//			return;
//		}
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				/** 如果子节点本身也是表单则返回* */
//				if (node instanceof TableTag || node instanceof Div) {
//					return;
//				} else if (node instanceof ScriptTag
//						|| node instanceof StyleTag
//						|| node instanceof SelectTag) {
//					return;
//				} else if (node instanceof TableColumn) {
//					return;
//				} else if (node instanceof TableRow) {
//					TableColumnValid tcValid = new TableColumnValid();
//					tcValid.setValid(true);
//					findTD(node, tcValid);
//					if (tcValid.isValid()) {
//						if (tcValid.getTdNum() < 2) {
//							if (tableValid.getTdnum() > 0) {
//								return;
//							} else {
//								continue;
//							}
//						} else {
//							if (tableValid.getTdnum() == 0) {
//								tableValid.setTdnum(tcValid.getTdNum());
//								tableValid.setTrnum(tableValid.getTrnum() + 1);
//							} else {
//								if (tableValid.getTdnum() == tcValid.getTdNum()) {
//									tableValid
//											.setTrnum(tableValid.getTrnum() + 1);
//								} else {
//									return;
//								}
//							}
//						}
//					}
//				} else {
//					isValidTable(node, tableValid);
//				}
//			}
//		} catch (Exception e) {
//			return;
//		}
//		return;
//	}
//
//	/**
//	 * 判断是否有效TR
//	 * 
//	 * @param nodeP
//	 * @param TcValid
//	 * @return
//	 */
//	private static void findTD(Node nodeP, TableColumnValid tcValid) {
//		NodeList nodeList = nodeP.getChildren();
//		/** 如果该表单没有子节点则返回* */
//		if ((nodeList == null) || (nodeList.size() == 0)) {
//			return;
//		}
//		try {
//			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
//				Node node = (Node) e.nextNode();
//				/** 如果有嵌套表单* */
//				if (node instanceof TableTag || node instanceof Div
//						|| node instanceof TableRow
//						|| node instanceof TableHeader) {
//					tcValid.setValid(false);
//					return;
//				} else if (node instanceof ScriptTag
//						|| node instanceof StyleTag
//						|| node instanceof SelectTag) {
//					tcValid.setValid(false);
//					return;
//				} else if (node instanceof TableColumn) {
//					tcValid.setTdNum(tcValid.getTdNum() + 1);
//				} else {
//					findTD(node, tcValid);
//				}
//			}
//		} catch (Exception e) {
//			tcValid.setValid(false);
//			return;
//		}
//		return;
//	}
//
//	protected static String collapse(String string) {
//		int chars;
//		int length;
//		int state;
//		char character;
//		StringBuffer buffer = new StringBuffer();
//		chars = string.length();
//		if (0 != chars) {
//			length = buffer.length();
//			state = ((0 == length) || (buffer.charAt(length - 1) == ' ') || ((lineSign_size <= length) && buffer
//					.substring(length - lineSign_size, length).equals(lineSign))) ? 0
//					: 1;
//			for (int i = 0; i < chars; i++) {
//				character = string.charAt(i);
//				switch (character) {
//				case '\u0020':
//				case '\u0009':
//				case '\u000C':
//				case '\u200B':
//				case '\u00a0':
//				case '\r':
//				case '\n':
//					if (0 != state) {
//						state = 1;
//					}
//					break;
//				default:
//					if (1 == state) {
//						buffer.append(' ');
//					}
//					state = 2;
//					buffer.append(character);
//				}
//			}
//		}
//		return buffer.toString();
//	}
//}
