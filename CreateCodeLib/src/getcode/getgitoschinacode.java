package getcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getgitoschinacode extends Thread{

      private HashSet<String> projectnamelist = new HashSet<String>();
	 
    //  private HashSet<String> codeurl = new HashSet<String>();
	
	  int startnum = 0;
	  int endnum = 0;
	  public int num = 0;
	  int count = 0;
	  String cmdhead = "git clone ";
	  String project = null;
	  String starturl = null;
	  String projectname = null;
	  String libpath="";
	  int fcount=0;
	  
	  public getgitoschinacode(int startnum,int endnum,String starturl,int fcount,String libpath) {
		// TODO Auto-generated constructor stub
		  this.startnum = startnum;
		  this.endnum = endnum;
		  this.starturl = starturl;	
		  this.fcount=fcount;
		  this.libpath=libpath;
	}
	  
	  public getgitoschinacode () {
		
	}
	 
	  public void run() {
		  try {
				getproject(starturl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		  		  
	}
	  
	  
	  //閼惧嘲绶遍幀濠氥�閺侊拷
	  public void getindexnum(String url){
		  
		  boolean flag = true;
		  
		  do{
			Document doc = null;  
			  try {
				doc = Jsoup.connect(url).timeout(5000).get();
				flag =true;
			} catch (IOException e) {
				// TODO: handle exception
				try {
					printstatement("网站链接超时\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				flag = false;
			}
			  
			  if(doc != null){
				  Element es = doc.select("li.page").last();
				if(es != null){
					String txt = es.text();
					num = Integer.parseInt(txt);
					try {
						printstatement("共计"+num+"页\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}  
				  				  
			  }
			  						  
			  
		  }while(flag==false);
		  		  
	  }
	  
	  //鐠佸墽鐤嗙挧宄邦潗妞わ拷
	  public void setstartnum(int n){
		  this.startnum = n;
	  }
	
	  //鐠佸墽鐤嗛張顐︺�
	  public void setendnum(int m) {
		this.endnum = m;
	}
	
	  //濞撳懐鈹杊ash鐞涳拷
	  public void clearprojectname()throws IOException{
		  projectnamelist.clear();
		  
	  }
	  	  
	  public void getproject(String url)throws IOException
	  {
		  printstatement("开始解析第"+startnum+"页到第"+endnum+"页\n");
		  
		//  getindexnum(url);
		  
		//  System.out.println(this.num);
		//  if(endnum > num)
		//  {
		//	  endnum = num;			  
		//  }
	    // if(startnum > num)
		//  {
		//	  startnum = num;			  
		//  }
		  
		  for(int i =startnum;i<=endnum;i++){
			  
			  String strurl = url.substring(0,url.length()-1);
			  strurl=strurl+i;
			  
			  boolean flag = true;
			  
			  Document doc = null;
			  
			  do{
				  try {
					  System.out.println(strurl);
					  doc = Jsoup.connect(strurl).timeout(5000).get();
					  flag=true;
				} catch (IOException e) {
					// TODO: handle exception
					  printstatement("解析超时\n");
					flag=false;
				}
				  
				  if(doc != null)
				  {
					  Elements es = doc.getElementsByTag("h3");
					  if(es != null){
						  for(int m=0;m<es.size();m++){							 													
								String txtString = es.get(m).getElementsByTag("a").attr("href");
							
								projectnamelist.add(txtString);	 												  
					  }					  					  
				  }
				  }
					
				  Iterator<String> iter = projectnamelist.iterator();
				  while(iter.hasNext()){
					  String name = iter.next();
					  String[] as = name.split("/");
					  projectname = as[2];
					  String tempurl = "http://git.oschina.net" + name;
					  getcmd(tempurl);
				  }
				  projectnamelist.clear();
					  					  
			  }while(flag == false);
			  
			  }  
			  
		  
	  }
	
	  public void getcmd(String surl)throws IOException{
		 
		  boolean flag = true;
		  
		  Document doc = null;
		  int count = 0;
		do{
			if(count<30){
				
				count++;
				System.out.println("Get CMD");
				try {
					doc = Jsoup.connect(surl).timeout(5000).get();
				} catch (IOException e) {
					// TODO: handle exception
					  printstatement("解析超时\n");
					flag = false;
				}
		if(doc != null){
			Element es = doc.getElementById("project_clone");
			if(es!=null){
			String detailurl = doc.getElementById("project_clone").attr("value");
			detailurl = cmdhead +detailurl;
			
			String pathtemp =libpath+ "/getgitoschinacode-" + fcount + ".bat";
			File csv = new File(pathtemp);
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true));
			  printstatement("获取地址："+detailurl+"\n");
			bw.write(detailurl + " "+libpath+"/"+projectname + "\n");
			bw.close();
			}	
		}		
				
			}			
			else{
				count = 0;
				break;				
			}
			
			
		}while(flag == false);
		  		  
	  }
	  
	  
	  
	  public void printstatement(String str) throws IOException {
			String pathtemp = libpath + "/statement.txt";
			File csv = new File(pathtemp);
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
			bw.write(str + "\n");
			bw.close();

		}
		
	  
	  
	  	  
	  public  void getoschinacode(String starturl, String libpath, int threadnum)throws IOException
	  {
		  //String strstart = "http://git.oschina.net/language/Java?page=2";
		  String strstart = starturl;
		 
		  getgitoschinacode gp = new getgitoschinacode();
	      gp.getindexnum(strstart);
		  int num = gp.num/threadnum;
		  for(int i=0;i<=threadnum;i++){
	            if(((i+1)*num)<=gp.num)
	            {	  
				getgitoschinacode gpnthread=new getgitoschinacode(i*num+1,(i+1)*num,strstart,i,libpath);
			    gpnthread.start();
			    }
	            else{
	            getgitoschinacode gpnthread1 = new getgitoschinacode(i*num+1,gp.num,strstart,i,libpath);
	            gpnthread1.start();
	            }
			
		  }
		  
	  }
	  
}
