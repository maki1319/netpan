package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Field;

import config.SocketConfig;
import model.Message;
import model.MessageType;
import model.UserDAO;
import model.Users;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLayeredPane layeredPane;
	private JPanel panel_2;
	private JLabel lblNewLabel_1;
	private JLabel label;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JPanel panel_3;

	private Users user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(null);
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
	public MainFrame(Users user) {
		this.user = user;
		setResizable(false);
		setTitle("Java\u7F51\u76D8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 696, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(0, 0, 784, 462);
		layeredPane.add(panel_1);
		panel_1.setLayout(null);

		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBounds(0, 0, 217, 462);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("sources/images/4.png")
				.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		lblNewLabel_1.setBounds(56, 20, 100, 100);
		panel_2.add(lblNewLabel_1);

		label = new JLabel(user.getUsername());
		label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(30, 154, 54, 15);
		panel_2.add(label);

		button = new JButton("�ϴ�");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}

		});
		button.setBounds(63, 193, 93, 23);
		panel_2.add(button);

		button_1 = new JButton("\u6211\u7684\u8D44\u6E90");

		button_1.setBounds(63, 240, 93, 23);
		panel_2.add(button_1);

		JLabel lblNewLabel_4 = new JLabel("\u4F1A\u5458");
		lblNewLabel_4.setBounds(122, 154, 54, 15);
		panel_2.add(lblNewLabel_4);
		if (user.getPermission() == 0) {
			lblNewLabel_4.setText("�ǻ�Ա");
		} else {
			lblNewLabel_4.setText("��Ա");
		}

		button_2 = new JButton("\u5347\u7EA7\u6210\u4F1A\u5458");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getPermission() == 1) {
					JOptionPane.showMessageDialog(null, "���Ѿ��ǻ�Ա��", "jpan", JOptionPane.INFORMATION_MESSAGE);
				} else {
					UserDAO u = new UserDAO();
					u.updatePer(user.getUsername());
					lblNewLabel_4.setText("��Ա");
				}
			}
		});
		button_2.setBounds(63, 291, 93, 23);
		panel_2.add(button_2);

		button_3 = new JButton("\u9000\u51FA\u7F51\u76D8");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame m = new LoginFrame();
				m.setLocationRelativeTo(null);
				m.setVisible(true);
				MainFrame.this.setVisible(false);
			}
		});
		button_3.setBounds(63, 343, 93, 23);
		panel_2.add(button_3);

		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(217, 0, 463, 435);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 784, 462);
		layeredPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("sources/images/login2.png"));
		panel.add(lblNewLabel, BorderLayout.CENTER);

		// ���췽���������ڼ��ع����б�����õķ���
		// ���Զ�ȡ�ļ��б�ķ����Ϳ���������ִ��
		Message getAllFilesMessage = new Message();
		getAllFilesMessage.setFrom(user);
		getAllFilesMessage.setType(MessageType.LISTALLFILES);

		// ��Ϣ��װ��Ͼ���Ҫ����Ϣ���ͳ�ȥ��������Ҫ����socket���󣨱���Ŀ���ö����ӣ�
		try {
			Socket client = new Socket(SocketConfig.ServerIP, SocketConfig.ServerPort);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			out.writeObject(getAllFilesMessage);// ����Ϣ�����͸�������
			out.flush();
			System.out.println("�Ѿ�����Ϣ���͸�������");
			Message allFiles = (Message) in.readObject();
			System.out.println("������Ϣ���");
			int x = 10;
			int y = 10;
			for (File f : allFiles.getFiles()) {
				System.out.println(f.getAbsolutePath());
				JPanel oneFile = new JPanel();
				oneFile.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						if (e.getClickCount() == 1) {
							JPopupMenu menu = new JPopupMenu();
							JMenuItem down = new JMenuItem("����");
							JMenuItem delete = new JMenuItem("ɾ��");
							menu.add(down);
							menu.addSeparator();
							menu.add(delete);
							menu.show(oneFile, e.getX(), e.getY());
							menu.addSeparator();
							down.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									String fileName = ((JLabel) oneFile.getComponents()[1]).getText();// 1.����û����ĸ��ļ����������Ҽ�����ø��ļ���
									System.out.println(fileName);


									Message willDownloadMessage = new Message();
									willDownloadMessage.setFilename(fileName);
									willDownloadMessage.setFrom(user);
									willDownloadMessage.setType(MessageType.DOWNLOAD);

									try {
										Socket client = new Socket(SocketConfig.ServerIP, SocketConfig.ServerPort);
										ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
										ObjectInputStream in = new ObjectInputStream(client.getInputStream());
										out.writeObject(willDownloadMessage);// ����Ϣ�����͸�������
										out.flush();


										JFileChooser fc = new JFileChooser();
										fc.showSaveDialog(MainFrame.this);
										File selectPath = fc.getSelectedFile();
										System.out.println(selectPath.getAbsolutePath());
										if (!selectPath.exists())
											selectPath.mkdirs();


										FileOutputStream fileOut = new FileOutputStream(new File(selectPath, fileName));
										byte[] bs;
										if (user.getPermission() == 1) {
											bs = new byte[1024];
										} else {
											bs = new byte[8];
										}
										int length = -1;
										while ((length = in.read(bs)) != -1) {
											fileOut.write(bs, 0, length);
											fileOut.flush();
										}
										out.close();
										fileOut.close();
										in.close();


										JOptionPane.showMessageDialog(MainFrame.this, "�ļ����سɹ�!", "��ܰ��ʾ",
												JOptionPane.INFORMATION_MESSAGE);
									
									} catch (Exception eee) {
										eee.printStackTrace();
									}

								}

							});
							delete.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									String fileName = ((JLabel) oneFile.getComponents()[1]).getText();
									System.out.println("ɾ��" + fileName);

									Message deleteMessage = new Message();
									deleteMessage.setFilename(fileName);
									deleteMessage.setFrom(user);
									deleteMessage.setType(MessageType.DELETE);

									try {
										Socket client = new Socket(SocketConfig.ServerIP, SocketConfig.ServerPort);
										ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
										ObjectInputStream in = new ObjectInputStream(client.getInputStream());
										out.writeObject(deleteMessage);
										out.flush();

										File file = new File(f.getAbsolutePath());
										if (file.isFile()) { 
											file.delete();

										} else {

											File[] files = file.listFiles(); 

											if (files == null) {

												file.delete();

											}
											file.delete();

										}

										out.close();
										in.close();
										JOptionPane.showMessageDialog(MainFrame.this, "ɾ���ɹ�", "��ܰ��ʾ",
												JOptionPane.INFORMATION_MESSAGE);
										dispose();
										MainFrame main1=new MainFrame(user);
										main1.setVisible(true);
									} catch (Exception eee) {
										eee.printStackTrace();
									}

								}

							});

						}
					}
				});
				oneFile.setLayout(null);
				oneFile.setBounds(x, y, 80, 100);
				oneFile.setBorder(BorderFactory.createLineBorder(Color.black));
				String fileExt = f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length());
				File fileIcon = new File("sources/images/fileicons/" + fileExt + ".gif");
				JLabel fileImage = null;
				if (fileIcon.exists()) {
					if (f.isDirectory()) {
						fileImage = new JLabel(
								new ImageIcon(Toolkit.getDefaultToolkit().getImage("sources/images/fileicons/dir.gif")
										.getScaledInstance(78, 78, Image.SCALE_DEFAULT)));
					} else {
						fileImage = new JLabel(new ImageIcon(
								Toolkit.getDefaultToolkit().getImage("sources/images/fileicons/" + fileExt + ".gif")
										.getScaledInstance(78, 78, Image.SCALE_DEFAULT)));
					}
				} else {
					fileImage = new JLabel(
							new ImageIcon(Toolkit.getDefaultToolkit().getImage("sources/images/fileicons/file.gif")
									.getScaledInstance(78, 78, Image.SCALE_DEFAULT)));
				}
				fileImage.setBounds(1, 1, 78, 78);
				JLabel fileName = new JLabel(f.getName(), SwingConstants.CENTER);
				fileName.setToolTipText(f.getName());
				fileName.setBounds(0, 80, 80, 20);
				oneFile.add(fileImage);
				oneFile.add(fileName);
				panel_3.add(oneFile);
				x += 90;
				if (x >= 480) {
					x = 10;
					y += 120;
				}

			}
			client.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame MAIN=new MainFrame(user);
				MAIN.setVisible(true);
				
				}
		});
	}

	/**
	 * ������������������û��������ļ��б�
	 */
	private void load() {
		// 1.�����ļ�ѡ����ȡ�û�ѡ��Ҫ�ϴ����ļ�
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(MainFrame.this);

		File file = fc.getSelectedFile();// ��ȡ�û���ѡ�����ѡ����ļ�

		if (file == null) {
			return;
		}
		System.out.println(file.getAbsolutePath());
		// 2.����socket����
		ObjectOutputStream out = null;
		Socket client = null;
		try {
			client = new Socket(SocketConfig.ServerIP, SocketConfig.ServerPort);
			out = new ObjectOutputStream(client.getOutputStream());
			System.out.println("���ӷ������ɹ�");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 3.��װһ����Ϣ������Ϣ�����б�Ǻõ�ǰ���������Ҫִ��ʲô����
		Message m = new Message();
		m.setFrom(user);
		m.setFileSize(file.length());
		m.setFilename(file.getName());
		m.setType(MessageType.UPLOAD);

		try {
			out.writeObject(m);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 4.ʹ��socket������ʼ�ϴ�����
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] bs = new byte[1024];
			int length = -1;
			while ((length = in.read(bs)) != -1) {
				out.write(bs, 0, length);
				out.flush();
			}
			out.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 5.�ϴ������ʾ���
		JOptionPane.showMessageDialog(MainFrame.this, "�ļ��ϴ��ɹ�!", "��ܰ��ʾ", JOptionPane.INFORMATION_MESSAGE);
		dispose();
		MainFrame mainFrame=new MainFrame(user);
		mainFrame.setVisible(true);
	}

	private void showAllFiles(Message allFiles) {
		System.out.println("��ʾ�����ļ�");
		panel_3.removeAll();
		int x = 10;
		int y = 10;
		for (File f : allFiles.getFiles()) {

			System.out.println(f.getAbsolutePath());
			// ���´����Ǹ��ݵ�ǰ�ļ����ͼ��ض�Ӧ��ͼ����ʾ�������б���
			JPanel oneFile = new JPanel();
			oneFile.setLayout(null);
			oneFile.setBounds(x, y, 80, 100);
			String fileExt = f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length());
			File fileIcon = new File("sources/images/fileicons/" + fileExt + ".gif");
			JLabel fileImage = null;
			if (fileIcon.exists()) {
				if (f.isDirectory()) {
					fileImage = new JLabel(
							new ImageIcon(Toolkit.getDefaultToolkit().getImage("sources/images/fileicons/dir.gif")
									.getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
				} else {
					fileImage = new JLabel(new ImageIcon(
							Toolkit.getDefaultToolkit().getImage("sources/images/fileicons/" + fileExt + ".gif")
									.getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
				}
			} else {
				fileImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit()
						.getImage("sources/imgs/fileicons/file.gif").getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
			}
			fileImage.setBounds(0, 0, 80, 80);
			JLabel fileName = new JLabel(f.getName(), SwingConstants.CENTER);
			fileName.setToolTipText(f.getName());
			fileName.setBounds(0, 80, 80, 20);
			oneFile.add(fileImage);
			oneFile.add(fileName);
			panel_3.add(oneFile);
			x += 90;
			if (x >= 480) {
				x = 10;
				y += 120;
			}

		}
	}

}
