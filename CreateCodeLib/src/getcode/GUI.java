package getcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;

import java.awt.Choice;

import javax.swing.JComboBox;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.TextField;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JToolBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldstarturl;
	private JTextField textFieldpath;
	JButton open;
	String libpath = null;
	private JTextField textFieldspynum;
	JTextArea textAreastatement = new JTextArea();
	String starturl = null;
	String spynum = null;
	java.util.Timer timer;
	java.util.Timer timercmd;
	getsourseforgecode gpn;
	int threadnum = 0;
	String oldstr = "";
	String selectweb = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {

		String temp = textAreastatement.getText();
		temp = temp + "特定领域代码库构建工具启动\n";
		textAreastatement.setText(temp);
		textAreastatement.setEditable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel paneltop = new JPanel();
		contentPane.add(paneltop, BorderLayout.NORTH);

		JLabel label = new JLabel("特定领域代码库构建工具");
		label.setFont(new Font("", Font.BOLD, 18));
		paneltop.add(label);

		JPanel panelleft = new JPanel();
		contentPane.add(panelleft, BorderLayout.WEST);
		panelleft.setLayout(null);

		JPanel panelbottom = new JPanel();
		contentPane.add(panelbottom, BorderLayout.SOUTH);

		JPanel panelcenter = new JPanel();
		contentPane.add(panelcenter, BorderLayout.CENTER);
		panelcenter.setLayout(null);

		textFieldstarturl = new JTextField();
		textFieldstarturl.setBounds(13, 110, 568, 21);
		panelcenter.add(textFieldstarturl);
		textFieldstarturl.setColumns(200);

		final Choice choice = new Choice();
		choice.setBounds(13, 55, 568, 21);
		choice.addItem("Google code");
		choice.addItem("Github");
		choice.addItem("SourceForge.net");
		choice.addItem("OSchina");
		choice.addItem("SVNchina");
		choice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				// System.out.println(choice.getSelectedItem());

				String temp = textAreastatement.getText();
				temp = temp + "选择开源代码库:\n" + choice.getSelectedItem() + "\n";
				textAreastatement.setText(temp);

				textAreastatement.selectAll();
				textAreastatement.setCaretPosition(textAreastatement
						.getSelectedText().length());
				textAreastatement.requestFocus();

			}
		});

		panelcenter.add(choice);

		textFieldpath = new JTextField();
		textFieldpath.setBounds(13, 170, 466, 21);
		panelcenter.add(textFieldpath);
		textFieldpath.setColumns(200);

		JButton pathButton = new JButton("选择地址");
		pathButton.setBounds(489, 169, 92, 23);

		pathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					if (file.isDirectory()) {
						System.out.println("文件夹地址：" + file.getAbsolutePath());
						textFieldpath.setText(file.getPath());
						libpath = file.getPath();
						String temp = textAreastatement.getText();
						temp = temp + "选择代码库路径:\n" + libpath + "\n";
						textAreastatement.setText(temp);

						textAreastatement.selectAll();
						textAreastatement.setCaretPosition(textAreastatement
								.getSelectedText().length());
						textAreastatement.requestFocus();

					} else if (file.isFile()) {
						System.out.println("文件地址" + file.getAbsolutePath());
						JOptionPane.showMessageDialog(null, "请选择文件夹");
					}
				}
				// System.out.println(jfc.getSelectedFile().getName());

			}
		});

		panelcenter.add(pathButton);

		JLabel label_2 = new JLabel("起始网址");
		label_2.setBounds(13, 85, 558, 23);
		panelcenter.add(label_2);

		JLabel label_3 = new JLabel("代码库地址");
		label_3.setBounds(13, 141, 558, 19);
		panelcenter.add(label_3);

		JLabel label_1 = new JLabel("开源网站");
		label_1.setBounds(13, 34, 558, 15);
		panelcenter.add(label_1);

		textFieldspynum = new JTextField();
		textFieldspynum.setBounds(71, 212, 66, 21);
		panelcenter.add(textFieldspynum);
		textFieldspynum.setColumns(10);

		JButton buttonstart = new JButton("分析网页");
		buttonstart.setBounds(111, 251, 133, 23);

		buttonstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				starturl = textFieldstarturl.getText();
				spynum = textFieldspynum.getText();

				// System.out.println("起始网址："+starturl);

				if (starturl == null || starturl.trim().equals("")) {

					JOptionPane.showMessageDialog(null, "请输入起始网址");
				} else if (libpath == null || libpath.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入代码库地址");
				} else if (spynum == null || spynum.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入爬虫线程数");
				} else {
					libpath = libpath.replace("\\", "/");
					String temp = textAreastatement.getText();
					temp = temp + "开始获取源代码\n运行信息\n选择开源代码库:"
							+ choice.getSelectedItem() + "\n";
					temp = temp + "起始url：\n" + starturl + "\n";
					temp = temp + "代码库地址：\n" + libpath + "\n";
					temp = temp + "爬虫线程数：\n" + spynum + "\n";
					textAreastatement.setText(temp);

					textAreastatement.selectAll();
					textAreastatement.setCaretPosition(textAreastatement
							.getSelectedText().length());
					textAreastatement.requestFocus();

					threadnum = Integer.parseInt(spynum.trim());
					String codeweb = choice.getSelectedItem();

					selectweb = choice.getSelectedItem();

					String pathtemp = libpath + "/statement.txt";
					File csv = new File(pathtemp);
					BufferedWriter bw;
					try {
						bw = new BufferedWriter(new FileWriter(csv));
						bw.write("\n");
						bw.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					String pathtempurl = libpath + "/url.txt";
					File csvurl = new File(pathtempurl);
					BufferedWriter bwurl;
					try {
						bwurl = new BufferedWriter(new FileWriter(csvurl));
						bwurl.write(starturl);
						bwurl.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					
					
					clearallbatfile(libpath);
					

					if (codeweb.trim().equals("SourceForge.net")) {

						// gpn=new getsourseforgecode();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									statementgui frame2 = new statementgui(
											starturl, libpath, threadnum,
											"SourceForge.net");
									frame2.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					} else if (codeweb.trim().equals("Google code")) {

						// gpn=new getsourseforgecode();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									statementgui frame2 = new statementgui(
											starturl, libpath, threadnum,
											"Google code");
									frame2.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
					
					else if(selectweb.trim().equals("SVNchina"))
					{

						// gpn=new getsourseforgecode();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									statementgui frame2 = new statementgui(
											starturl, libpath, threadnum,
											"SVNchina");
									frame2.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
					else if(selectweb.trim().equals("OSchina"))
					{

						// gpn=new getsourseforgecode();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									statementgui frame2 = new statementgui(
											starturl, libpath, threadnum,
											"OSchina");
									frame2.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
					else if(selectweb.trim().equals("Github"))
					{

						// gpn=new getsourseforgecode();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									statementgui frame2 = new statementgui(
											starturl, libpath, threadnum,
											"Github");
									frame2.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
					
					
					
					
					
					

				}

			}
		});

		panelcenter.add(buttonstart);

		JButton buttonstop = new JButton("构建代码库");
		buttonstop.setBounds(347, 251, 133, 23);
		buttonstop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (selectweb.trim().equals("SourceForge.net")) {
					for (int i = 0; i < threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath
								+ "/getsoruseforgecode-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					for (int i = 0; i < threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath
								+ "/getsoruseforgegit-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

					for (int i = 0; i < threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath
								+ "/getsoruseforgesvn-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				} else if (selectweb.trim().equals("Google code")) {
					for (int i = 0; i < threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath
								+ "/getcodecmd-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
				else if(selectweb.trim().equals("SVNchina"))
				{
					for (int i = 0; i <= threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath+"/getsvnchinacode-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
				}
				else if(selectweb.trim().equals("OSchina"))
				{
					for (int i = 0; i <= threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath+ "/getgitoschinacode-" + i + ".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
				}
				else if(selectweb.trim().equals("Github"))
				{
					for (int i = 0; i < threadnum; i++) {
						String cmd = "cmd.exe /C start " + libpath+"/getgithubcode-"+i+".bat";
						try {
							Process process = Runtime.getRuntime().exec(cmd);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
				}
				
				
				
				
				
				
				
				
				
				
				

			}
		});

		panelcenter.add(buttonstop);

		JLabel label_4 = DefaultComponentFactory.getInstance().createLabel(
				"进程数");
		label_4.setBounds(13, 215, 48, 15);
		panelcenter.add(label_4);

		JScrollPane scrollPane_1 = new JScrollPane(textAreastatement);
		textAreastatement.setLineWrap(true);
		scrollPane_1.setBounds(13, 283, 558, 86);

		scrollPane_1
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelcenter.add(scrollPane_1);

		JPanel panelright = new JPanel();
		contentPane.add(panelright, BorderLayout.EAST);
	}
	
	
	
	public void clearallbatfile(String dirpath)
	{
		File f=new File(dirpath);
		if(f.isDirectory())
		{
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				//System.out.println(files[i].getPath());
				if(files[i].getPath().endsWith(".bat")){
					String pathtempurl = files[i].getPath();
					File csvurl = new File(pathtempurl);
					BufferedWriter bwurl;
					try {
						bwurl = new BufferedWriter(new FileWriter(csvurl));
						bwurl.write("\n");
						bwurl.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				  }
				
			}  
			
		}
		
		
		
		
		String pathtempurl = libpath + "/url.txt";
		File csvurl = new File(pathtempurl);
		BufferedWriter bwurl;
		try {
			bwurl = new BufferedWriter(new FileWriter(csvurl));
			bwurl.write(starturl);
			bwurl.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	
	
	
	
	

	protected void exit() {
		// TODO Auto-generated method stub
		this.exit();

	}
}
