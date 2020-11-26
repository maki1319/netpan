package view;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Mail;
import model.MailUtil;
import model.UserDAO;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.awt.event.ActionEvent;

public class updatePassword extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updatePassword frame = new updatePassword();
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
	public static String getCode(int n) {
		String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
		char[] ch = new char[n]; 
		for (int i = 0; i < n; i++) {
			Random random = new Random();
			int index = random.nextInt(string.length());
			ch[i] = string.charAt(index);
		}
		
		String result = String.valueOf(ch);
		return result;

	}
	public updatePassword() {
	    String s2=getCode(4);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("\u786E\u5B9A");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			     String emailString=textField_3.getText().trim();
				String string1=textField.getText().trim();
				String passString=textField_1.getText().trim();
				String passString2=textField_2.getText().trim();
				
				boolean flag=s2.equals(string1)&&passString.equals(passString2);
				if(flag) {
				     UserDAO userDAO=new UserDAO();
				   userDAO.update(emailString, passString);
					dispose();
					JOptionPane.showMessageDialog(null, "修改成功","jpan",JOptionPane.INFORMATION_MESSAGE);
					LoginFrame loginFrame=new LoginFrame();
					loginFrame.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "请确认您的信息","jpan",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("\u8FD4\u56DE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginFrame login=new LoginFrame();
				login.setVisible(true);
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u4F60\u7684\u9A8C\u8BC1\u7801");
		lblNewLabel.setEnabled(false);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BF7\u8F93\u5165\u4F60\u7684\u65B0\u5BC6\u7801");
		lblNewLabel_1.setEnabled(false);
		
		JLabel lblNewLabel_2 = new JLabel("\u8BF7\u518D\u6B21\u786E\u5B9A\u5BC6\u7801");
		lblNewLabel_2.setEnabled(false);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\u8BF7\u8F93\u5165\u4F60\u7684\u90AE\u7BB1");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("\u70B9\u51FB\u53D1\u9001\u9A8C\u8BC1\u7801");
		btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 String emailString=textField_3.getText().trim();
					Mail mail = new Mail();  
			         mail.setHost("smtp.qq.com"); 			//设置邮件服务器,如果不用QQ邮箱的,自己找找看相关的  
			         mail.setPortNumber("465");   			//设置邮件服务器端口号,默认25
			         mail.setSender(".....@qq.com");   			//发送人
			         mail.setName("***");   					//发送人昵称
			         mail.setReceiver(emailString); 			//接收人  
			         mail.setUsername("......."); 			//登录账号,一般都是和邮箱名一样
			         mail.setPassword("密码");  //QQ邮箱登录第三方客户端时,密码框请输入“授权码”进行验证。其他的密码具体查看邮件服务器的说明
			         mail.setSubject("标题");  
			         mail.setMessage("<h1 style=color:red >你的验证码为:"+s2+",有效期为5分钟</font></h1>");  
			         if (new MailUtil().send(mail)) {
			        	JOptionPane.showInternalMessageDialog(null,"发送成功", "pan", JOptionPane.INFORMATION_MESSAGE);
			       } else {
						JOptionPane.showInternalMessageDialog(null,"发送失败", "pan", JOptionPane.ERROR_MESSAGE);
					} 
				}
			
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(76)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1))
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
								.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
								.addComponent(textField_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(119)
							.addComponent(btnNewButton_1)
							.addGap(69)
							.addComponent(btnNewButton_2)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(28, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2))
					.addGap(49))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
