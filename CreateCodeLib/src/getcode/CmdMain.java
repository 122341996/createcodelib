package getcode;
import java.io.IOException;

public class CmdMain {
 public static void main(String[] args){
  
//  //执行批处理文件
//  String strcmd="cmd /c start  D:/ct/getcodecmd-0.bat";
//  Runtime rt = Runtime.getRuntime();
//  Process ps = null;
//  try {
//     ps = rt.exec(strcmd);
//  } catch (IOException e1) {
//     e1.printStackTrace();
//  }
//  try {
//   ps.waitFor();
//  } catch (InterruptedException e) {
//   // TODO Auto-generated catch block
//   e.printStackTrace();
//  }
//  int i = ps.exitValue();
//  if (i == 0) {
//    System.out.println("执行完成.") ;
//  } else {
//    System.out.println("执行失败.") ;
//  }
//  ps.destroy();
//  ps = null;
//  
//  //批处理执行完后，根据cmd.exe进程名称 kill掉cmd窗口(这个方法是好不容易才找到了，网上很多介绍的都无效，csdn废我3分才找到这个方法)
//  new CmdMain().killProcess();
	 
	 
	 
//	 getgooglecode gpn = new getgooglecode();
//		try {
//			gpn.getgooglecodecode("https://code.google.com/hosting/search?q=label%3AJava&filter=0&mode=&start=30", "d:/ct",3);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	 
	 
	 getsvnchinacode gpn=new  getsvnchinacode();
	 try {
		gpn.getsvnchinacode("http://www.svnchina.com/project_open.php?tech=Java&app=&p=2", "d:/ct", 4);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
 }
 
 public void killProcess(){
  Runtime rt = Runtime.getRuntime();
  Process p = null;  
  try {
   rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}
