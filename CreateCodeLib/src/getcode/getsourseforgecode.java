package getcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getsourseforgecode extends Thread {

	private HashSet<String> projectnamelist = new HashSet<String>();

	private HashSet<String> codeurl = new HashSet<String>();

	int startnum = 0;
	int endnum = 0;
	public int num = 0;
	int count = 0;
	String projectname = null;
	String starturl = null;
	int filecount = 0;
	String libpath = null;
	String statement = "";
	boolean stopflag = false;

	public getsourseforgecode() {

	}

	public getsourseforgecode(int startnum, int endnum, String url, int n,
			String libpath) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.starturl = url;
		this.filecount = n;
		this.libpath = libpath;
	}

	public void run() {
		try {
			getproject(starturl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getindexnum(String url) {

		boolean flag = true;
		int count = 0;
		do {

			if (count < 10) {
				count++;

				Document doc = null;
				try {
					doc = Jsoup.connect(url).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						printstatement("网站链接超时\n");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					flag = false;
				}

				if (doc != null) {
					// Elements es =
					// doc.select("td[style=padding:3px 1em; font-weight:normal; white-space:nowrap;]");
					Elements es = doc.select("p[id=result_count]");
					if (es != null) {
						String result = es.text();
						if (result != null) {
							String[] as = result.split(" of ");
							// System.out.println(as[0]);
							String pnum = as[1]
									.substring(0, as[1].length() - 1);
							num = Integer.parseInt(pnum);
							try {
								printstatement("共获取" + num + "页\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}

				}

			} else {
				count = 0;
				break;
			}

		} while (flag == false);

	}

	public void setstartnum(int n) {
		this.startnum = n;

	}

	public void setendnum(int n) {
		this.endnum = n;

	}

	public void setstopflag(boolean stopflag) {
		this.stopflag = stopflag;

	}

	public void setfilecount(int n) {
		this.filecount = n;

	}

	public void setlibpath(String libpath) {
		this.libpath = libpath;

	}

	public String getstatement() {
		return this.statement;
	}

	public void clearprojectname() throws IOException {
		projectnamelist.clear();
	}

	public void printcmd(String str) throws IOException {
		String pathtemp = libpath + "/getcodecmd" + filecount + ".bat";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(str + "\n");
		bw.close();
		System.err.println(count);

	}

	public void printstatement(String str) throws IOException {
		String pathtemp = libpath + "/statement.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(str + "\n");
		bw.close();

	}

	public void printprojectname() throws IOException {
		String pathtemp = "projectname.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

		Iterator<String> iterator = projectnamelist.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			bw.write(name + "\n");
			System.out.println(name);
		}
		bw.close();
		System.err.println(count);

	}

	public void getproject(String url) throws IOException {

		printstatement("\n开始解析第" + startnum + "页到第" + endnum + "页\n");

		getindexnum(url);

		if (endnum > num) {
			endnum = num;
		}
		if (startnum > num) {
			startnum = num;
		}

		for (int i = startnum; i < endnum; i++) {

			String starturl = url.substring(0, url.length() - 1);
			starturl = starturl + i;

			boolean flag = true;
			int count = 0;
			Document doc = null;
			do {
				System.out.println(starturl);

				if (count < 10) {
					count++;
					try {
						doc = Jsoup.connect(starturl).timeout(5000).get();
						flag = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						printstatement("第" + i + "页解析超时\n");
						flag = false;
					}
					if (doc != null) {
						Elements es = doc.getElementsByTag("a");
						if (es != null) {
							for (int kk = 0; kk < es.size(); kk++) {
								String purl = es.get(kk).attr("href");
								String kind = es.get(kk).attr("class");

								if (purl.startsWith("/projects/")
										&& kind.equals("project-icon")) {
									// System.out.println(purl);
									projectnamelist.add(purl);
								}

							}
						}
					}

					Iterator<String> iterator = projectnamelist.iterator();
					int ssss = 0;
					while (iterator.hasNext()) {

						printstatement("解析第" + i + "页共计"
								+ projectnamelist.size() + "个项目中的第" + ssss
								+ "个项目");
						ssss++;
						String name = iterator.next();
						String[] as = name.split("/");
						projectname = as[2];
						String pcontenturl = "http://sourceforge.net" + name;
						// System.out.println(pcontenturl);
						getcmd(pcontenturl, name);

					}
					projectnamelist.clear();

				} else {
					count = 0;
					break;
				}

			} while (flag == false);

			if (i == endnum - 1) {
				printstatement("\n解析第" + startnum + "页到第" + endnum + "页完成\n");
			}

		}

	}

	public void getcmd(String starturl, String proname) throws IOException {

		boolean flag = true;

		Document doc = null;
		int count = 0;
		do {
			if (count < 10) {
				count++;
				// System.out.println("in project page"+starturl);

				try {
					doc = Jsoup.connect(starturl).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					printstatement("解析" + proname + "超时\n");
					flag = false;
				}
				if (doc != null) {
					Elements es = doc.getElementsByTag("a");
					if (es != null) {
						for (int kk = 0; kk < es.size(); kk++) {
							String purl = es.get(kk).attr("href");

							if (purl.startsWith("/p/")
									&& purl.contains("/code/")) {
								// System.out.println(purl);
								// projectnamelist.add(purl);
								String tempurl = "http://sourceforge.net"
										+ purl;
								codeurl.add(tempurl);
							}

						}
					}
				}

				Iterator<String> iterator = codeurl.iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();

					System.out.println(name);
					getcmdfin(name);

				}
				codeurl.clear();

			} else {
				count = 0;
				break;
			}
		} while (flag == false);
	}

	public void getcmdfin(String starturl) throws IOException {

		boolean flag = true;

		Document doc = null;
		int count = 0;
		do {
			if (count < 10) {
				count++;
				System.out.println("try to get cmd");

				try {
					doc = Jsoup.connect(starturl).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Time out!!");
					flag = false;
				}
				if (doc != null) {
					Elements es = doc.getElementsByTag("a");
					if (es != null) {
						for (int kk = 0; kk < es.size(); kk++) {
							String purl = es.get(kk).attr("data-url");
							String kind = es.get(kk).attr("title");

							if (kind.equals("HTTP")) {
								System.err.println(purl);
								printstatement("获取项目地址" + purl + "\n");
								String pathtemp = libpath
										+ "/getsoruseforgecode-" + filecount
										+ ".bat";
								File csv = new File(pathtemp);
								BufferedWriter bw = new BufferedWriter(
										new FileWriter(csv, true));
								String newpurl = changeurl(purl);
								bw.write(newpurl + "\n");
								bw.close();

								// projectnamelist.add(purl);

							}

						}
					}
				}

			} else {
				count = 0;
				break;
			}
		} while (flag == false);
	}

	public String changeurl(String purl) {
		String dirurl = "";
		String[] as = purl.split(" ");

		dirurl += as[0];
		dirurl += " " + as[1];
		dirurl += " " + as[2];
		dirurl += " " + libpath + "/" + as[3];

		return dirurl;
	}

	public void getsfcode(String starturl, String libpath, int threadnum)
			throws IOException {
		// TODO Auto-generated method stub

		getsourseforgecode gpn = new getsourseforgecode(0, 1, starturl, 0,
				libpath);
		gpn.getindexnum(starturl);

		if (threadnum > 1) {

			int num = gpn.num / (threadnum - 1);
			for (int i = 0; i < (threadnum - 1); i++) {
				getsourseforgecode gpn2 = new getsourseforgecode(i * num + 1,
						(i + 1) * num + 1, starturl, i, libpath);
				gpn2.start();
			}
			getsourseforgecode gpn3 = new getsourseforgecode((threadnum - 1)
					* num, gpn.num, starturl, threadnum - 1, libpath);
			gpn3.start();
		} else {
			getsourseforgecode gpn2 = new getsourseforgecode(1, gpn.num,
					starturl, 0, libpath);
			gpn2.start();
		}

	}

}
