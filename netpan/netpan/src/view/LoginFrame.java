package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.UserDAO;
import model.Users;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private UserDAO dao;
	private JLayeredPane layeredPane;
	private JPanel panel_1;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					LoginFrame frame = new LoginFrame();
					frame.setLocationRelativeTo(null);
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
	public LoginFrame() {
		dao=new UserDAO();
		setResizable(false);
		setTitle("Java\u7F51\u76D8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(0, 0, 443, 432);
		layeredPane.add(panel_1);
		panel_1.setLayout(null);
		
		label = new JLabel("\u8D26\u6237\uFF1A");
		label.setEnabled(false);
		label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.DARK_GRAY);
		label.setBounds(81, 181, 69, 15);
		panel_1.add(label);
		
		textField = new JTextField("");
		textField.setBounds(202, 178, 171, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setFont(lblNewLabel_1.getFont().deriveFont(lblNewLabel_1.getFont().getStyle() | Font.BOLD));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(81, 233, 69, 15);
		panel_1.add(lblNewLabel_1);
		
		passwordField = new JPasswordField("");
		passwordField.setBounds(202, 230, 171, 21);
		panel_1.add(passwordField);
		
		btnNewButton = new JButton("\u7ACB\u5373\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText().trim();
				String password=passwordField.getText();
                if(username.equals("")||password.equals("")) {
                	JOptionPane.showMessageDialog(null, "«Î ‰»Î√˚◊÷ªÚ√‹¬Î", "Œ¬‹∞Ã· æ", JOptionPane.ERROR_MESSAGE);
                }
				Users u=dao.login(username, password);
				if(u==null) {
					JOptionPane.showMessageDialog(LoginFrame.this, "µ«¬º ß∞‹", "Œ¬‹∞Ã· æ", JOptionPane.ERROR_MESSAGE);
				}else {
					MainFrame  m=new MainFrame(u);
					m.setVisible(true);
					m.setLocationRelativeTo(null);
					LoginFrame.this.dispose();
				}
			}
		});
		btnNewButton.setBounds(108, 319, 93, 23);
		panel_1.add(btnNewButton);
		
		JButton button = new JButton("\u6CE8\u518C\u8D26\u6237");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterFrame m=new RegisterFrame();
				m.setLocationRelativeTo(null);
				m.setVisible(true);
				LoginFrame.this.setVisible(false);
			}
		});
		button.setBounds(303, 319, 93, 23);
		panel_1.add(button);
		
		JButton btnNewButton_1 = new JButton("\u627E\u56DE\u5BC6\u7801");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				updatePassword update=new updatePassword();
				update.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 405, 93, 23);
		panel_1.add(btnNewButton_1);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 469, 448);
		layeredPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 613, 452);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setIcon(new ImageIcon("sources/images/login2.png"));
		panel.add(lblNewLabel);
	}
}
