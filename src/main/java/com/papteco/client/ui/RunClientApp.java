package com.papteco.client.ui;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.papteco.client.action.EnvConfiguration;
import com.papteco.client.action.JPromptWindow;
import com.papteco.client.action.PathCacheUtils;
import com.papteco.client.action.QuartzJob;
import com.papteco.client.bqueue.QueueBuilder;
import com.papteco.client.netty.LoginClientBuilder;
import com.papteco.client.netty.ObjectEchoBuilder;
import com.papteco.client.netty.OpenFileServerBuilder;
import com.papteco.client.netty.ReleaseFileServerBuilder;

public class RunClientApp extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = -2435953743688848219L;
	protected Properties envsetting = EnvConfiguration.getEnvSetting();
	private static RunClientApp frame;

	private TrayIcon trayIcon = null;

	private static final int winWidth = 400;
	private static final int winHeight = 200;
	private static int pointX;
	private static int pointY;
	private JLabel lclPath_l;
	private JTextField lclPath;
	private JButton lclPath_btn;
	private JLabel lclMailPath_l;
	private JTextField lclMailPath;
	private JButton lclMailPath_btn;
	private JFileChooser fw;
	private JLabel prjCde_l;
	private JTextField prjCde;
	private JButton submitBtn;
	private JTextField username;
	private JPasswordField password;
	private JPanel loginpanel;
	private JPanel mainpanel;
	private JPanel basicpanel;
	private CardLayout card;

	protected void Quartz() {
		try {
			SchedulerFactory scheduler = new StdSchedulerFactory();
			Scheduler schedu = scheduler.getScheduler();
			JobDetail job = new JobDetail("job1", "group1",
					(new QuartzJob(null)).getClass());
			CronTrigger con = new CronTrigger("trigger", "group1", "job1",
					"group1", envsetting.getProperty("cron_setting"));
			// CronTrigger con = new
			// CronTrigger("trigger","group1","job1","group1","1 1 7 * * *");
			schedu.addJob(job, true);
			schedu.scheduleJob(con);

			schedu.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void drawMainPanel() throws IOException {
		// ===============main panel=================
		JPromptWindow.frame = frame;
		mainpanel = new JPanel(new GridLayout(7, 1));
		mainpanel.setBounds(0, 0, 100, 50);
		lclPath_l = new JLabel("Path of Project Folder:");
		JPanel lclpanel = new JPanel(new GridLayout(1, 2));
		lclPath = new JTextField(40);
		lclPath.setEditable(false);
		lclPath.setText(PathCacheUtils.readPathCache(PathCacheUtils.PRJ_PATH));
		EnvConstant.LCL_STORING_PATH = lclPath.getText();
		lclPath_btn = new JButton("Set Path");
		lclPath_btn.setPreferredSize(new Dimension(100, 50));
		lclPath_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fw = new JFileChooser();
				fw.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fw.showOpenDialog(null);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					lclPath.setText(fw.getSelectedFile().getPath());
					EnvConstant.LCL_STORING_PATH = lclPath.getText();
					try {
						PathCacheUtils.writePathCache(PathCacheUtils.PRJ_PATH,
								lclPath.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		lclpanel.add(lclPath);
		lclPath.setBounds(0, 0, 300, 20);
		lclpanel.add(lclPath_btn);

		lclMailPath_l = new JLabel("Path of Mail-File Folder:");
		JPanel lclmailpanel = new JPanel(new GridLayout(1, 2));
		lclMailPath = new JTextField(40);
		lclMailPath.setEditable(false);
		lclMailPath.setText(PathCacheUtils
				.readPathCache(PathCacheUtils.MAIL_PATH));
		EnvConstant.LCL_MAILFILE_PATH = lclMailPath.getText();
		lclMailPath_btn = new JButton("Set Path");
		lclMailPath_btn.setPreferredSize(new Dimension(100, 50));
		lclMailPath_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fw = new JFileChooser();
				fw.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fw.showOpenDialog(null);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					lclMailPath.setText(fw.getSelectedFile().getPath());
					EnvConstant.LCL_MAILFILE_PATH = lclMailPath.getText();
					try {
						PathCacheUtils.writePathCache(PathCacheUtils.MAIL_PATH,
								lclMailPath.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		lclmailpanel.add(lclMailPath);
		lclMailPath.setBounds(0, 0, 300, 20);
		lclmailpanel.add(lclMailPath_btn);

		prjCde_l = new JLabel("Please Input Project Code(e.g. 1000-1301-001):");
		prjCde = new JTextField(15);
		submitBtn = new JButton("Submit");
		mainpanel.add(lclPath_l);
		mainpanel.add(lclpanel);
		mainpanel.add(lclMailPath_l);
		mainpanel.add(lclmailpanel);
		mainpanel.add(prjCde_l);
		mainpanel.add(prjCde);
		mainpanel.add(submitBtn);
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (StringUtils.isNotEmpty(lclPath.getText())
							&& StringUtils.isNotEmpty(prjCde.getText().trim())) {
						prjCde.setText(prjCde.getText().trim());
						new Thread(new Runnable() {
							public void run() {
								try {
									new ObjectEchoBuilder(prjCde.getText()
											.trim()).runSelProjectEcho();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();

					} else {
						JPromptWindow
								.showWarnMsg("Please input your project storing path and input a project code!");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		basicpanel.add(mainpanel, "mainpanel");
	}

	public RunClientApp() throws Exception {
		card = new CardLayout(5, 5);
		basicpanel = new JPanel(card);
		this.setAlwaysOnTop(true);
		Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
		Insets si = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		pointX = sd.width - winWidth - 3;
		pointY = sd.height - si.bottom - winHeight - 3;

		// ==========login panel=============
		loginpanel = new JPanel(new GridLayout(5, 1));
		loginpanel.setBounds(0, 0, 100, 50);
		username = new JTextField(40);
		password = new JPasswordField(40);
		JButton submit_btn = new JButton("Submit");
		loginpanel.add(new JLabel("Username: "));
		loginpanel.add(username);
		loginpanel.add(new JLabel("Password: "));
		loginpanel.add(password);
		loginpanel.add(submit_btn);
		submit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (StringUtils.isEmpty(username.getText())) {
					JPromptWindow.showWarnMsg("Please input username!");
				} else if (StringUtils.isEmpty(String.valueOf(password
						.getPassword()))) {
					JPromptWindow.showWarnMsg("Please input password!");
				} else {
					try {
						LoginStatusUtil.loginStatus = "";
						new LoginClientBuilder(username.getText(), String
								.valueOf(password.getPassword()))
								.validateUser();
						System.out.println(LoginStatusUtil.loginStatus);
						if (LoginStatusUtil.loginStatus.equals("NOUSER")) {
							JPromptWindow.showWarnMsg("No this user!");
						} else if (LoginStatusUtil.loginStatus.equals("PWDINC")) {
							JPromptWindow.showWarnMsg("Incorrect password!");
						} else {
							EnvConstant.LOGIN_USER = username.getText();
							Quartz();
							drawMainPanel();
							showMainPanel(username.getText());
							card.show(basicpanel, "mainpanel");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		basicpanel.add(loginpanel, "loginpanel");

		// ===========system tray===============
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				// TODO Auto-generated method stub
				if (SystemTray.isSupported()) {
					SystemTray systemTray = SystemTray.getSystemTray();
					if (trayIcon != null) {
						systemTray.remove(trayIcon);
						System.out.println("trayIcon removed");
					}
					URL resource = this.getClass().getResource("/logo.png");
					BufferedImage imageScaled = null;
					BufferedImage in;
					try {
						in = ImageIO.read(resource);
						// imageScaled = ImageScale.scale(in, 0.05, 0.05,
						// 1);
						imageScaled = in;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PopupMenu popupMenu = new PopupMenu();
					MenuItem item = new MenuItem("Exit");
					MenuItem item2 = new MenuItem("Open Window");
					popupMenu.add(item);
					popupMenu.add(item2);
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							QueueBuilder.closeQueueService();
							System.exit(0);
						}
					});
					item2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							frame.setVisible(true);
						}
					});
					trayIcon = new TrayIcon(imageScaled, "Papteco", popupMenu);
					trayIcon.setImageAutoSize(true);
					trayIcon.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							frame.setVisible(true);
						}
					});
					try {
						systemTray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Papteco", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		getContentPane().add(basicpanel, SwingConstants.CENTER);
	}

	private void showMainPanel(String username) {
		try {
			QueueBuilder.submitMultipleConsumers(10);
			new ObjectEchoBuilder(username, "INITIAL").runInitinal();
			new Thread(new OpenFileServerBuilder()).start();
			new Thread(new ReleaseFileServerBuilder()).start();

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame,
					"Cannot connect to PIMS Server.", "Warning",
					JOptionPane.PLAIN_MESSAGE);
			QueueBuilder.closeQueueService();
			System.exit(0);
		}
	}

	public static void makeSingle(String singleId) {
		RandomAccessFile raf = null;
		FileChannel channel = null;
		FileLock lock = null;

		try {

			File sf = new File(System.getProperty("java.io.tmpdir") + singleId
					+ ".single");
			sf.deleteOnExit();
			sf.createNewFile();

			raf = new RandomAccessFile(sf, "rw");
			channel = raf.getChannel();
			lock = channel.tryLock();

			if (lock == null) {
				// Error is for a automatically stop of the program, while
				// for Exception, you have to handle it in the catch clause.
				JOptionPane.showMessageDialog(null,
						"An instance of the application is running.");
				throw new Error("An instance of the application is running.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.exit(0);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		RunClientApp.makeSingle("single.test");
		frame = new RunClientApp();
		frame.setTitle("Papteco Client Application");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(winWidth, winHeight);
		frame.setLocation(pointX, pointY);
		/*
		 * System.out.println(frame.getExtendedState());
		 * frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		 */
	}

}
