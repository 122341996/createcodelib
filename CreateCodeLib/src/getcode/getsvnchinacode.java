package getcode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.io.FileWriter;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getsvnchinacode extends Thread {

	private HashSet<String> projectnamelist = new HashSet<String>();
	int startnum = 0;
	int endnum = 0;
	static String username = "waitler2002";
	static String passwd = "sj199182";
	private int num = 0;
	String cmdheadString = "svn checkout ";
	String project = null;
	String starturl = null;
	String projectname = null;
	String urllogin = null;
	String libpath="";
	int fcount=0;

	// String sessionid = "73om8ats2btjqckll38t80euu3";

	public getsvnchinacode() {

	}

	public void run() {
		try {
			getproject(urllogin, starturl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public getsvnchinacode(int startnum, int endnum, String urllogin, String url,int fcount, String libpath) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.urllogin = urllogin;
		this.starturl = url;
		this.fcount=fcount;
		this.libpath=libpath;
	}

	public void getindexnum(String url) {

		boolean flag = true;
		Document doc = null;
		do {
			try {
				doc = Jsoup.connect(url).timeout(5000).get();
				flag = true;
			} catch (Exception e) {
				try {
					printstatement("网站连接超时！\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				flag = false;
			}
			if (doc != null) {
				Elements es = doc.getElementsByClass("nextLast");
				if (es != null) {
					String namehref = es.first().attr("href");
					String[] name = namehref.split("=");
					String numString = name[name.length - 1];
					num = Integer.parseInt(numString);
				}
			}
		} while (flag == false);
	}

	public void getproject(String urllogin, String url) throws IOException {

		printstatement("开始解析第"+startnum + "页到第" + endnum+"页\n");

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", passwd);
		for (int i = startnum; i <= endnum; i++) {
			String starturl = url.substring(0, url.length() - 1);
			starturl = starturl + i;
			boolean flag = true;
			do {
				Document doc = null;
				try {
					Response response = Jsoup
							.connect(urllogin)
							.header("Accept-Language", "zh-CN,zh;q=0.8")
							.header("Accept-Encoding", "gzip,deflate")
							.header("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
							.header("Connection", "keep-alive")
							.header("User-Agent",
									"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36")
							.header("Host", "www.svnchina.com")
							.header("Referer", "http://www.svnchina.com/")
							.data(map).method(Method.POST).timeout(20000)
							.execute();
					String sessionid = response.cookie("PHPSESSID");
					doc = Jsoup.connect(starturl)
							.cookie("PHPSESSID", sessionid).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					printstatement("登录网站超时!!!\n");
					flag = false;
				}
				if (doc != null) {
					Elements es = doc.getElementsByClass("tips");
					if (es != null) {
						for (int k = 0; k < es.size(); k++) {
							String projecthref = es.attr("href") + "@"
									+ es.get(k).text();
							if (projecthref.split("@").length != 1) { // 鏉╁洦鎶ら幒澶愩�閻╊喖鎮曟稉铏光敄閻拷
								projectnamelist.add(projecthref);
							}
						}
					}
				}
				Iterator<String> iterator = projectnamelist.iterator();
				while (iterator.hasNext()) {
					String urlString = iterator.next();
					String[] ss = urlString.split("@");
					String urlString2 = "http://www.svnchina.com/" + ss[0];
					if (ss.length == 2) {
						projectname = ss[1];
					} else {
						System.out.println(urlString);
					}
					getcmd(urllogin, urlString2);
				}
				projectnamelist.clear();
			} while (flag == false);
		}

	}

	public void getcmd(String urllogin, String url) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", passwd);
		boolean flag = true;
		Document doc = null;
		int count = 0;
		do {
			if (count < 30) {
				count++;
				System.out.println("GET CMD");
				try {
					Response response = Jsoup
							.connect(urllogin)
							.header("Accept-Language", "zh-CN,zh;q=0.8")
							.header("Accept-Encoding", "gzip,deflate")
							.header("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
							.header("Connection", "keep-alive")
							.header("User-Agent",
									"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36")
							.header("Host", "www.svnchina.com")
							.header("Referer", "http://www.svnchina.com/")
							.data(map).method(Method.POST).timeout(20000)
							.execute();
					String sessionid = response.cookie("PHPSESSID");
					doc = Jsoup.connect(url).cookie("PHPSESSID", sessionid)
							.timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					printstatement("解析网页超时\n");
					flag = false;
				}
				if (doc != null) {
					Elements es = doc.getElementsByClass("svnUrl");
					if (es != null) {
						String svnString = es.first().attr("href");
						svnString = cmdheadString + svnString;
						String pathtemp = libpath+"/getsvnchinacode-" + fcount + ".bat";
						File csv = new File(pathtemp);
						BufferedWriter bw = new BufferedWriter(new FileWriter(
								csv, true));
						printstatement("获取地址："+svnString+"\n");
						bw.write(svnString + " " +libpath+"/"+ projectname + "\n");
						bw.close();
					}
				}
			} else {
				count = 0;
				break;
			}
		} while (flag == false);

	}
	
	public void printstatement(String str) throws IOException {
		String pathtemp = libpath + "/statement.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(str + "\n");
		bw.close();

	}
	
	
	
	

	public  void getsvnchinacode(String starturl, String libpath, int threadnum) throws Exception {
		String urllogin = "http://www.svnchina.com/login.php";
		//String url = "http://www.svnchina.com/project_open.php?tech=Java&app=&p=2";
		
		String url=starturl;
		
		// String username = "waitler2002";
		// String passwd = "sj199182";

		// // String url1=
		// "http://www.svnchina.com/project.php?id=46465&sign=dab00100a08f2d65bcafa2920ab5bb54";

		// Map<String, String > map = new HashMap<String, String>();
		// map.put("username", username);
		// map.put("password", passwd);
		//
		//
		// String test =
		// "http://www.svnchina.com/project_open.php?tech=Java&app=&p=14";
		// Response response = Jsoup.connect(urllogin).header("Accept-Language",
		// "zh-CN,zh;q=0.8").header("Accept-Encoding",
		// "gzip,deflate").header("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").header("Connection",
		// "keep-alive").header("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36").header("Host",
		// "www.svnchina.com").header("Referer",
		// "http://www.svnchina.com/").data(map).method(Method.POST).timeout(20000).execute();
		// String sessionid = response.cookie("PHPSESSID");
		// Document doc=Jsoup.connect(test).cookie("PHPSESSID",
		// sessionid).timeout(5000).get();
		//
		// Elements es = doc.getElementsByClass("tips");
		// String projecthref = es.attr("href")+"@"+es.get(0).text() ;
		//
		// System.out.println(projecthref);

		getsvnchinacode gp = new getsvnchinacode();
		gp.getindexnum(url);
		int num = gp.num / threadnum;

		for (int i = 0; i <= threadnum; i++) {
			if (((i + 1) * num) <= gp.num) {
				getsvnchinacode gpnthread = new getsvnchinacode(i * num + 1,
						(i + 1) * num, urllogin, url,i,libpath);
				gpnthread.start();
			} else {
				getsvnchinacode gpnthread1 = new getsvnchinacode(i * num + 1,
						gp.num, urllogin, url,i,libpath);
				gpnthread1.start();
			}

		}

	}

}
