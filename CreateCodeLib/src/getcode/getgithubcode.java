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

public class getgithubcode extends Thread{

	private HashSet<String> projectnamelist=new HashSet<String> ();
	
	private HashSet<String> codeurl=new HashSet<String> ();
	
	
	
	int startnum=0;
	int endnum=0;
	public int num=0;
	int count=0;
	String projectname=null;
	String author=null;
	String version=null;
	String starturl=null;
	String libpath="";
	int fcount=0;
	public getgithubcode (int startnum, int endnum,String url,int fcount,String libpath){
		this.startnum=startnum;
		this.endnum=endnum;
		this.starturl=url;
		this.libpath=libpath;
		this.fcount=fcount;
		
		
	}
	public getgithubcode (){}
	
	
	public void run(){
		try {
			getproject(starturl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public void getindexnum(String url)  {//ȡ����ҳ��

        boolean flag=true;
		
		
		do{
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
			flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				printstatement("链接网站超时\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			flag=false;
		}
		
		if(doc!=null)
		{
			//Elements es = doc.select("td[style=padding:3px 1em; font-weight:normal; white-space:nowrap;]");
//			Elements es = doc.select("p[id=result_count]");
//		if(es!=null){
//			String result=es.text();
//	        if(result!=null){
//	        	String[] as=result.split(" of ");
//	        	//System.out.println(as[0]);
//	        	String pnum=as[1].substring(0, as[1].length()-1);
//	            num=Integer.parseInt(pnum);
//	    		System.out.println("pnum:"+num); 
//
//	        }
//		}
			Elements es = doc.getElementsByTag("a");
    		if(es!=null)
    		{
    			for(int kk=0;kk<es.size()-1;kk++){ 
    				String purl=es.get(kk).attr("href");
    				String kind=es.get(kk+1).attr("class");
    				if(purl.startsWith("/search")&&kind.equals("next_page"))
    				{
    					String pnum = es.get(kk).text();
    					num=Integer.parseInt(pnum);
    		    		System.out.println("pnum:"+num);
    		    		
    		    		try {
    						printstatement("共计"+num+"页\n");
    					} catch (IOException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    		    		
    		    		
//    						String pnum=purl.substring("/project_open.php?tech=Java&app=&p=".length());
//        					num=Integer.parseInt(pnum);
//        		    		System.out.println("pnum:"+num); 
    					
    				}
    				
    			}
    		}
		}
		}while(flag==false);
		
		
	}
	
	
	
	public void setstartnum(int n)  {
		this.startnum=n;
		
	}
	public void setendnum(int n)  {
		this.endnum=n;
		
	}
	
	public void clearprojectname() throws IOException {
		projectnamelist.clear();	
	}
	
	public void printcmd(String str) throws IOException {
		String pathtemp="getcodecmd.bat";
		File csv = new File(pathtemp); 
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
	    bw.write(str+"\n");
		bw.close();
		System.err.println(count); 
		
	}
	
	
	public void printprojectname() throws IOException {
		String pathtemp="projectname.txt";
		  File csv = new File(pathtemp); 
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
        
		Iterator<String> iterator=projectnamelist.iterator();
		while(iterator.hasNext()){
			 String name=iterator.next();
	          bw.write(name+"\n");
			  System.out.println(name);
		}
		bw.close();
		System.err.println(count); 
		
	}
	
	
	
	public void getproject(String url) throws IOException  {//�Ƿ����ÿ�����̣�
		
		
		//System.out.println(startnum+"::"+endnum);
		
		try {
			printstatement("开始分析第"+startnum+"页到第"+endnum+"页\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		getindexnum(url);
		
		if(endnum>num)
		{
			endnum=num;
		}
		if(startnum>num)
		{
			startnum=num;
		}
		
		for(int i=startnum;i<=endnum;i++)
		{

			String starturl=url.substring(0, "https://github.com/search?l=Java&p=".length());
			starturl=starturl+i+"&q=java&type=Repositories&utf8=%E2%9C%93";
		    
		    boolean flag=true;
		    
            Document doc=null;
            do{
            	System.out.println(starturl);
            	try {
    				doc = Jsoup.connect(starturl).timeout(5000).get();
    				flag=true;
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				System.err.println("Time out!!");
    				try {
    					printstatement("链接网站超时\n");
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				flag=false;
    			}
            	if(doc!=null)
            	{
            		Elements es = doc.getElementsByTag("canvas");
            		if(es!=null)
            		{
            			for(int kk=0;kk<es.size();kk++){ 
            				String purl=es.get(kk).attr("data-source");
            				String kind=es.get(kk).attr("class");
            				
            				if(kind.equals("js-participation-graph bars"))
            				{
            					purl = purl.substring(0, purl.length()-"/graphs/owner_participation".length());
            					//System.out.println(purl);
            					projectnamelist.add(purl);
            				}
            				
            			}
            		}	
            	}
            	
            	
            	
            	Iterator<String> iterator=projectnamelist.iterator();
            	int ssss=0;
        		while(iterator.hasNext()){
        			System.err.println("page:"+i+"psiz:"+projectnamelist.size()+":"+(ssss++));
        			 
        			try {
        				printstatement("正在分析第"+i+"页第"+ssss+"个项目\n");
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			
        			
        			String name=iterator.next();
//        			  projectname=name.substring(1);
        			 String[] as=name.split("/");
        			 author = as[1];
       			     projectname=as[2];
        			  String pcontenturl="https://github.com"+name;
        			  //System.out.println(pcontenturl);
        			  
        			  getcmdfin(pcontenturl);
        			  
        		}
        		projectnamelist.clear();
            	
            	
            	
            	
            	
            }while(flag==false);

		}
		
	}
	public void getversion(String starturl) throws IOException{//��ȡ�汾��
		boolean flag=true;
	    
        Document doc=null;
        int count=0;
        starturl = starturl +"/releases";
        do{
        	if(count<30)
        	{
        		count++;
        	//System.out.println("in project page"+starturl);
        	
              	
        	try {
				doc = Jsoup.connect(starturl).timeout(5000).get();
				flag=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Time out!!");
				try {
					printstatement("链接网站超时\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				flag=false;
				}
        		if(doc!=null){
        			String tag = null;//������href�п��ٶ�λ�汾�ŵı���
        			version= null;
        			tag = starturl.substring("https://github.com".length()) + "/tag/";
	        		Elements es = doc.getElementsByTag("a");
	        		if(es!=null)
	        		{
	        			for(int kk=0;kk<es.size();kk++){ 
	        				String purl=es.get(kk).attr("href"); 
	        				if(purl.startsWith(tag)){
	        					version = purl.substring(tag.length());
	        					break;
	        				}
	        			}
	        			
	        		}
        		}
        	}
        	else
        	{
        		count=0;
        		break;
        	}
        }while(flag==false);
	}
	
	
	public void getcmd(String starturl) throws IOException  {//���code�������ӵ�ҳ���ַ
		    
		    boolean flag=true;
		    
            Document doc=null;
            int count=0;
            do{
            	if(count<30)
            	{
            		count++;
            	//System.out.println("in project page"+starturl);
            	
                  	
            	try {
    				doc = Jsoup.connect(starturl).timeout(5000).get();
    				flag=true;
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				System.err.println("Time out!!");
    				try {
    					printstatement("链接网站超时\n");
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				flag=false;
    			}
            	if(doc!=null)
            	{
            		Elements es = doc.getElementsByTag("a");
            		if(es!=null)
            		{
            			for(int kk=0;kk<es.size();kk++){ 
            				String purl=es.get(kk).attr("href");
            				
            				if(purl.startsWith("/p/")&&purl.contains("/code/"))
            				{
            					//System.out.println(purl);
            					//projectnamelist.add(purl);
            					String tempurl="http://sourceforge.net"+purl;
            					codeurl.add(tempurl);
            				}
            				
            			}
            		}	
            	}
            	
            	
            	
            	Iterator<String> iterator=codeurl.iterator();
        		while(iterator.hasNext()){
        			 String name=iterator.next();
        			  
        			  
        			  System.out.println(name);
        			  getcmdfin(name);
        			  
        		}
        		codeurl.clear();
            	
            	
            	
            	
            	
            	}
            	else
            	{
            		count=0;
            		break;
            	}
            }while(flag==false);	
	}
	
	
	public void getcmdfin(String starturl) throws IOException  {
	    
	    boolean flag=true;
	    
        Document doc=null;
        int count=0;
        do{
        	if(count<30)
        	{
        		count++;
        	System.out.println("try to get cmd");
        	
              	
        	try {
				doc = Jsoup.connect(starturl).timeout(5000).get();
				flag=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Time out!!");
				try {
					printstatement("链接网站超时\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				flag=false;
			}
        	if(doc!=null)
        	{
        		Elements es = doc.getElementsByTag("input");
        		if(es!=null)
        		{
        			for(int kk=0;kk<es.size();kk++){ 
        				String purl=es.get(kk).attr("value");
        				String kind=es.get(kk).attr("class");
        				
        				if(kind.equals("input-mini input-monospace js-url-field js-zeroclipboard-target"))
        				{
        					System.err.println("final:"+purl);
        					try {
            					printstatement("获取地址："+purl+"\n");
            				} catch (IOException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}
        					
        					String pathtemp=libpath+"/getgithubcode-"+fcount+".bat";
        					  File csv = new File  (pathtemp); 
        			        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
        			        bw.write("git clone "+purl+" "+libpath+"/"+author+"-"+projectname+"\n");
        			        //bw.write("git clone "+purl+"\n");
        					bw.close();
        					
        					break;
        					
        					//projectnamelist.add(purl);
        					
        				}
        				
        			}
        		}	
        	}
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	}
        	else
        	{
        		count=0;
        		break;
        	}
        }while(flag==false);	
}
	
	
	
	public void getghcode(String starturl, String libpath, int threadnum)
			throws IOException {
		// TODO Auto-generated method stub

		getgithubcode gpn = new getgithubcode(0, 1, starturl, 0,
				libpath);
		gpn.getindexnum(starturl);

		if (threadnum > 1) {

			int num = gpn.num / (threadnum - 1);
			for (int i = 0; i < (threadnum - 1); i++) {
				getgithubcode gpn2 = new getgithubcode(i * num + 1,
						(i + 1) * num + 1, starturl, i, libpath);
				gpn2.start();
			}
			getgithubcode gpn3 = new getgithubcode((threadnum - 1)
					* num, gpn.num, starturl, threadnum - 1, libpath);
			gpn3.start();
		} else {
			getgithubcode gpn2 = new getgithubcode(1, gpn.num,
					starturl, 0, libpath);
			gpn2.start();
		}

	}

	public void printstatement(String str) throws IOException {
		String pathtemp = libpath + "/statement.txt";
		File csv = new File(pathtemp);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		bw.write(str + "\n");
		bw.close();

	}
	
	
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String starturl="https://github.com/search?p=8&q=java&type=Repositories&utf8=%E2%9C%93";
		String starturl="https://github.com/search?l=Java&p=3&q=java&type=Repositories&utf8=%E2%9C%93";
		String libpath="d:/ct";
		
		
		getgithubcode gpn=new getgithubcode();
	   gpn.getghcode(starturl, libpath, 5);
		
		//gpn.getcmdfin("http://sourceforge.net/p/weka/code/?source=navbar");
		//gpn.getindexnum(starturl);
		//gpn.getproject(starturl);
		//gpn.printprojectname();
		//gpn.combine();
		//String cmdStr="";//"cmd.exe /k "+cmdStr)
		
		
		//Runtime.getRuntime().exec("cmd.exe /C start f:/getcodecmd.bat");  
		
		
		
		

		

	}

}
