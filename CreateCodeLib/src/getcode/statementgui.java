package getcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class statementgui extends JFrame {

	private JPanel contentPane;
	java.util.Timer timer;
	java.util.Timer timercmd;
	String oldstr = "";
	JTextArea textAreastate = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					statementgui frame = new statementgui(
							"http://sourceforge.net/directory/?q=&page=5",
							"d:/ct", 4, "SourceForge.net");
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public statementgui(String starturl, final String libpath, int threadnum,
			String selectweb) {

		textAreastate.setText("start spy program!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 424, 25);
		contentPane.add(panel);

		JLabel label = new JLabel("\u7A0B\u5E8F\u8FD0\u884C\u72B6\u6001");
		panel.add(label);

		JPanel panelcenter = new JPanel();
		panelcenter.setBounds(5, 30, 424, 217);
		contentPane.add(panelcenter);
		panelcenter.setLayout(null);
		textAreastate.setBounds(20, 180, 116, 27);
		panelcenter.add(textAreastate);

		JScrollPane scrollPane = new JScrollPane(textAreastate);
		scrollPane.setBounds(10, 0, 404, 184);
		panelcenter.add(scrollPane);

		final String anlysercmd = textAreastate.getText();

		// JScrollPane scrollPane_1 = new JScrollPane(textAreastate);
		// textAreastate.setLineWrap(true);
		// scrollPane_1.setBounds(13, 283, 558, 86);
		//
		// scrollPane_1.setHorizontalScrollBarPolicy(
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scrollPane_1.setVerticalScrollBarPolicy(
		// JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//
		//
		// panelcenter.add(scrollPane_1);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(5, 247, 424, 10);
		contentPane.add(panel_4);

		try {

			timer = new java.util.Timer(true);

			timer.schedule(new java.util.TimerTask() {
				public void run() {

					String newstr = anlysercmd;

					try {
						// String encoding="GBK";
						File file = new File(libpath + "/statement.txt");
						if (file.isFile() && file.exists()) { // 判断文件是否存在
							InputStreamReader read = new InputStreamReader(
									new FileInputStream(file));// 考虑到编码格式
							BufferedReader bufferedReader = new BufferedReader(
									read);
							String lineTxt = null;
							while ((lineTxt = bufferedReader.readLine()) != null) {
								newstr += lineTxt + "\n";
							}
							read.close();
						} else {
							System.out.println("找不到指定的文件");
						}
					} catch (Exception e) {
						System.out.println("读取文件内容出错");
						e.printStackTrace();
					}

					// System.out.println(newstr);

					textAreastate.setText(newstr);

					textAreastate.selectAll();
					textAreastate.setCaretPosition(textAreastate
							.getSelectedText().length());
					textAreastate.requestFocus();

				}
			}, 0, 1000);

			if (selectweb.equals("SourceForge.net")) {
				getsourseforgecode gpn = new getsourseforgecode();

				try {
					gpn.getsfcode(starturl, libpath, threadnum);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				getsourseforgegit gpn2 = new getsourseforgegit();

				try {
					gpn2.getsfcode(starturl, libpath, threadnum);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				getsourseforgesvn gpn3 = new getsourseforgesvn();

				try {
					gpn3.getsfcode(starturl, libpath, threadnum);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				

			} else if (selectweb.equals("Google code")) {
				getgooglecode gpn = new getgooglecode();
				gpn.getgooglecodecode(starturl, libpath, threadnum);

			}
			else if (selectweb.equals("SVNchina")) {
				 getsvnchinacode gpn=new  getsvnchinacode();
				 try {
					gpn.getsvnchinacode(starturl, libpath, threadnum);
					} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			else if (selectweb.equals("OSchina")) {
				getgitoschinacode gpn=new getgitoschinacode();
				gpn.getoschinacode(starturl, libpath, threadnum);

			}
			
			
			else if (selectweb.equals("Github")) {
				getgithubcode gpn=new getgithubcode();
				gpn.getghcode(starturl, libpath, threadnum);

			}
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		timercmd = new java.util.Timer(true);

		timercmd.schedule(new java.util.TimerTask() {
			public void run() {

				String newstr = "";
				try {
					// String encoding="GBK";
					File file = new File(libpath + "/statement.txt");
					if (file.isFile() && file.exists()) { // 判断文件是否存在
						InputStreamReader read = new InputStreamReader(
								new FileInputStream(file));// 考虑到编码格式
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						while ((lineTxt = bufferedReader.readLine()) != null) {
							newstr += lineTxt + "\n";
						}
						read.close();
					} else {
						System.out.println("找不到指定的文件");
					}
				} catch (Exception e) {
					System.out.println("读取文件内容出错");
					e.printStackTrace();
				}

				// System.out.println(newstr);

				if (oldstr.equals(newstr)) {
					String pathtemp = libpath + "/statement.txt";
					File csv = new File(pathtemp);

					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(
								csv, true));
						bw.write("分析网页结束\n");
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					cancel();
				} else {
					System.err.println("exchange");
					oldstr = newstr;
				}

			}
		}, 0, 30 * 1000);

	}

}
