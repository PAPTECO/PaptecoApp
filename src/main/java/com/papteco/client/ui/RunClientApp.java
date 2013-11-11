package com.papteco.client.ui;

import java.awt.AWTException;
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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;

import com.papteco.client.action.JPromptWindow;
import com.papteco.client.action.PathCacheUtils;
import com.papteco.client.bqueue.QueueBuilder;
import com.papteco.client.netty.ObjectEchoBuilder;
import com.papteco.client.netty.OpenFileServerBuilder;
import com.papteco.client.netty.ReleaseFileServerBuilder;

public class RunClientApp extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = -2435953743688848219L;

	private static RunClientApp frame;

	private TrayIcon trayIcon = null;

	private static final int winWidth = 400;
	private static final int winHeight = 200;
	private static int pointX;
	private static int pointY;
	private JLabel lclPath_l;
	private JTextField lclPath;
	private JCheckBox lclPath_chk;
	private JButton lclPath_btn;
	private JFileChooser fw;
	private JLabel prjCde_l;
	private JTextField prjCde;
	private JButton submitBtn;

	public RunClientApp() throws Exception {
		try {
			QueueBuilder.submitMultipleConsumers(10);
			new ObjectEchoBuilder().runInitinal();
			new Thread(new OpenFileServerBuilder(8082)).start();
			new Thread(new ReleaseFileServerBuilder(8083)).start();

			JPromptWindow.frame = frame;
			// TODO Auto-generated constructor stub
			Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
			Insets si = Toolkit.getDefaultToolkit().getScreenInsets(
					this.getGraphicsConfiguration());
			pointX = sd.width - winWidth - 3;
			pointY = sd.height - si.bottom - winHeight - 3;

			JPanel panel = new JPanel(new GridLayout(5, 1));
			panel.setBounds(0, 0, 100, 50);
			this.setAlwaysOnTop(true);
			lclPath_l = new JLabel("Path of Project Folder:");
			JPanel lclpanel = new JPanel(new GridLayout(1, 2));
			lclPath = new JTextField(40);
			lclPath.setEditable(false);
			lclPath.setText(PathCacheUtils.readFile());
			EnvConstant.LCL_STORING_PATH = lclPath.getText();
			lclPath_chk = new JCheckBox("Ensure");
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
							PathCacheUtils.writeFile(lclPath.getText());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			lclpanel.add(lclPath);
			lclPath.setBounds(0, 0, 300, 20);
			// lclpanel.add(new JLabel());
			lclpanel.add(lclPath_btn);
			prjCde_l = new JLabel("Please Input Project Code(e.g. 1000-1301-001):");
			prjCde = new JTextField(15);
			submitBtn = new JButton("Submit");
			panel.add(lclPath_l);
			panel.add(lclpanel);
			panel.add(prjCde_l);
			panel.add(prjCde);
			panel.add(submitBtn);

			/*
			 * lclPath_chk.addItemListener(new ItemListener() { public void
			 * itemStateChanged(ItemEvent e) { if (lclPath_chk.isSelected()) {
			 * if (StringUtils.isNotEmpty(lclPath.getText())) {
			 * EnvConstant.LCL_STORING_PATH = lclPath.getText(); File f = new
			 * File(EnvConstant.LCL_STORING_PATH); if (!f.exists()) {
			 * f.mkdirs(); f.setExecutable(true, false); f.setReadable(true,
			 * false); f.setWritable(true, false);
			 * System.out.println("Folder \"" + f.getName() + "\" created!"); }
			 * else { System.out.println("Folder \"" + f.getName() +
			 * "\" existing already!"); } lclPath.setEnabled(false); } else {
			 * JOptionPane.showMessageDialog(frame,
			 * "Project Storing Path cannot be empty!", "Warning",
			 * JOptionPane.PLAIN_MESSAGE); } } else { lclPath.setEnabled(true);
			 * } } });
			 */

			submitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						if (StringUtils.isNotEmpty(lclPath.getText())
								&& StringUtils.isNotEmpty(prjCde.getText()
										.trim())) {
							new Thread(new Runnable(){
								public void run(){
									try {
										new ObjectEchoBuilder(prjCde.getText())
										.runSelProjectEcho();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							
						} else {
							JPromptWindow.showWarnMsg("Please input your project storing path and input a project code!");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			getContentPane().add(panel, SwingConstants.CENTER);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {

					// TODO Auto-generated method stub
					if (SystemTray.isSupported()) {
						SystemTray systemTray = SystemTray.getSystemTray();
						if (trayIcon != null) {
							systemTray.remove(trayIcon);
							System.out.println("trayIcon removed");
						}
						URL resource = this.getClass().getResource("/icon.jpg");
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

						trayIcon = new TrayIcon(imageScaled, "Papteco",
								popupMenu);
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
						JOptionPane.showMessageDialog(null, "Papteco",
								"Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
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
