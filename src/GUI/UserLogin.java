package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre de connexion au logiciel
 *
 */
public class UserLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private JLabel label;
	private JPanel contentPane;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * crée la frame de la page de connexion
	 */
	public UserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
		lblNewLabel.setBounds(423, 13, 273, 93);
		contentPane.add(lblNewLabel);

		// recupere les username et password rempli dans les field
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		textField.setBounds(481, 170, 281, 68);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordField.setBounds(481, 286, 281, 68);
		contentPane.add(passwordField);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBackground(Color.BLACK);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblUsername.setBounds(250, 166, 193, 52);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setBackground(Color.CYAN);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblPassword.setBounds(250, 286, 193, 52);
		contentPane.add(lblPassword);

		btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton.setBounds(545, 392, 162, 73);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String userName = textField.getText();
				String password = passwordField.getText();
				try {
					// verifie dans la bdd si pseudo/mdp correspond bien
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

					PreparedStatement st = (PreparedStatement) connection.prepareStatement(
							"Select user_name, password from account where user_name=? and password=?");

					st.setString(1, userName);
					st.setNString(2, password);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						dispose();
						UserHome ah = new UserHome(userName);
						ah.setTitle("Welcome");
						ah.setVisible(true);
						JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
					} else {
						JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
					}
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		});

		contentPane.add(btnNewButton);

		label = new JLabel("");
		label.setBounds(0, 0, 1008, 562);
		contentPane.add(label);

		btnNewButton_1 = new JButton("Register");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton_1.setBounds(545, 482, 162, 73);
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e1) {
				UserRegistration f = new UserRegistration();
				f.setVisible(true);
			}
		});

		contentPane.add(btnNewButton_1);

	}
}
