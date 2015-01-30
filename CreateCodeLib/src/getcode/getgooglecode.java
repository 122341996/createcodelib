package getcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getgooglecode extends Thread {
	private HashSet<String> projectnamelist = new HashSet<String>();

	int startnum = 0;
	int endnum = 0;
	int filecount = 0;

	int num = 0;
	int count = 0;
	String starturl = "";
	String libpath = "";
	int threadnum = 1;

	getgooglecode() {
		/*
		 * String pathtemp="projectname.txt"; File csv = new File(pathtemp);
		 * BufferedWriter bw; try { bw = new BufferedWriter(new
		 * FileWriter(csv)); bw.close(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}

	public void run() {
		try {
			getproject(starturl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public getgooglecode(int startnum, int endnum, String starturl,
			String libpath, int threadnum, int filecount) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.starturl = starturl;
		this.libpath = libpath;
		this.threadnum = threadnum;
		this.filecount = filecount;

	}

	public void combine() throws IOException {

		String filePath = "projectnameall.txt";
		String pathtemp = "getcodeall.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(csv, true));
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					/*
					 * String cmdstr="svn checkout http://";
					 * cmdstr=cmdstr+lineTxt
					 * +".googlecode.com/svn/trunk/ /googlecode/";
					 * cmdstr=cmdstr+lineTxt+"\n";
					 */
					String cmdstr = lineTxt + "\n";
					bw.write(cmdstr);
					System.out.println(cmdstr);

				}

				read.close();
				System.out.println("Get  trainresult Set Finished!\n");
				bw.close();

			} else {
				System.out.println("ï¿½Ò²ï¿½ï¿½ï¿½Ö¸ï¿½ï¿½ï¿½ï¿½ï¿½Ä¼ï¿½");
			}
		} catch (Exception e) {
			System.out.println("ï¿½ï¿½È¡ï¿½Ä¼ï¿½ï¿½ï¿½ï¿½Ý³ï¿½ï¿½ï¿½");
			e.printStackTrace();
		}

	}

	public void getindexnum(String url) {

		boolean flag = true;

		do {
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(5000).get();
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("»ñÈ¡Ò³Êý³¬Ê±");
				flag = false;
			}

			if (doc != null) {
				Elements es = doc
						.select("td[style=padding:3px 1em; font-weight:normal; white-space:nowrap;]");

				if (es != null) {
					String result = es.text();
					// System.out.println("result:"+result);

					if (result != null && !result.trim().equals("")) {

						for (int i = 1; i < 5; i++) {
							String pnum = result.substring(result.length() - i,
									result.length());
							if (pnum.startsWith(" ")) {
								num = Integer.parseInt(pnum.trim()) / 10;
								break;
							}

						}
						if (num == 0)
							num = 100;

						System.out.println("pnum:" + num);
					}
				}

			}
		} while (flag == false);

	}

	public void clearprojectname() throws IOException {
		projectnamelist.clear();
	}

	public void printcmd(String str, String projectname) throws IOException {
		System.out.println(str);
		String[] as = str.split(" ");
		String dirurl = "";
		if (str.trim().startsWith("svn")) {

			dirurl += as[0];
			dirurl += " " + as[1];
			dirurl += " " + as[2];
			dirurl += " " + libpath + "/" + as[3];

		} else if (str.trim().startsWith("git")) {

			dirurl += as[0];
			dirurl += " " + as[1];
			dirurl += " " + as[2];
			dirurl += " " + libpath + "/" + projectname;

		} else if (str.trim().startsWith("hg")) {

			dirurl += as[0];
			dirurl += " " + as[1];
			dirurl += " " + as[2];
			dirurl += " " + libpath + "/" + projectname;

		}

		String pathtemp = libpath + "/getcodecmd-" + filecount + ".bat";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(dirurl + "\n");
		bw.close();
		System.err.println(count);

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

	public void printstatement(String str) throws IOException {
		String pathtemp = libpath + "/statement.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(str + "\n");
		bw.close();
		System.err.println(count);

	}

	public void getproject(String url) throws IOException {

		printstatement("\n¿ªÊ¼½âÎöµÚ" + startnum + "Ò³µ½µÚ" + endnum + "Ò³\n");

		getindexnum(url);

		if (endnum > num) {
			endnum = num;
		}
		if (startnum > num) {
			startnum = num;
		}

		for (int i = startnum; i < endnum; i++) {
			if (i % 10 == 0) {
				printprojectname();
				clearprojectname();
			}
			String starturl = url.substring(0, url.length() - 2);
			starturl = starturl + (i * 10);

			boolean flag = true;

			Document doc = null;
			do {
				System.out.println(starturl);
				try {
					doc = Jsoup.connect(starturl).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Time out!!");
					flag = false;
				}
				if (doc != null) {
					Elements es = doc.getElementsByTag("table");
					if (es != null) {
						Elements tempE = es
								.select("a[onmousedown^=return clk]");
						// System.out.println(tempE);
						// Elements tempE = es.select("a[href^=/p/]");
						if (tempE != null) {
							for (Element pnlink : tempE) {
								count++;
								String projectName = pnlink.attr("href");
								String newstartuml = "http://code.google.com"
										+ projectName + "source/checkout";
								getcmd(newstartuml, projectName);
								projectName = projectName.substring(3,
										projectName.length() - 1);
								projectnamelist.add(projectName);
								// System.out.println(projectName);
							}
						}
					}
				}
			} while (flag == false);

			if (i == endnum - 1) {
				printstatement("\n½âÎöµÚ" + startnum + "Ò³µ½µÚ" + endnum + "Ò³Íê³É\n");
			}

		}

	}

	public void getcmd(String starturl, String projectname) throws IOException {

		boolean flag = true;

		Document doc = null;
		int count = 0;
		do {
			if (count < 30) {
				count++;
				System.out.println(starturl);
				try {
					doc = Jsoup.connect(starturl).timeout(5000).get();
					flag = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					printstatement("Á¬½ÓÍøÕ¾³¬Ê±£¡\n");
					flag = false;
				}

				if (doc != null) {
					Elements es = doc.select("tt[id=checkoutcmd]");

					if (es != null) {
						String result = es.text();
						if (result != null) {
							// String pnum=result.substring(19,
							// result.length());
							// num=Integer.parseInt(pnum)/10;
							printstatement("»ñÈ¡µØÖ·£º" + result + "\n");
							printcmd(result, projectname);
						}
					}
				}

			} else {
				count = 0;
				break;
			}
		} while (flag == false);
	}

	public void getgooglecodecode(String starturl, String libpath, int threadnum)
			throws IOException {
		// TODO Auto-generated method stub
		// String
		// starturl="https://code.google.com/hosting/search?q=label%3AJava&filter=0&mode=&start=00";

		getgooglecode gpn = new getgooglecode();
		gpn.getindexnum(starturl);
		int pagenum = gpn.num;

		if (threadnum > 1) {

			int pagesize = pagenum / (threadnum);
			for (int i = 0; i < (threadnum ); i++) {
				getgooglecode gpn2 = new getgooglecode(i * pagesize + 1,
						(i + 1) * pagesize + 1, starturl, libpath, threadnum, i);

				gpn2.start();
				// gpn2.printprojectname();
				// gpn2.combine();
			}

			

		} else {
			getgooglecode gpn3 = new getgooglecode(1, gpn.num, starturl,
					libpath, 1, 0);

			gpn3.start();

		}

		String cmdStr = "";// "cmd.exe /k "+cmdStr)

		// Runtime.getRuntime().exec("cmd.exe /C start f:/getcodecmd.bat");

	}

}
