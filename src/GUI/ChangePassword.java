package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre de changement de mot de passe
 *
 */
public class ChangePassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblEnterNewPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crée la frame de changement de mot de passe
	 */
	public ChangePassword(String name) {
		setBounds(450, 360, 1024, 234);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 34));
		textField.setBounds(373, 35, 609, 67);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Enter");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// change le mot de passe par rapport a l'input de l'user
				String pstr = textField.getText();
				try {
					System.out.println("update password name " + name);
					System.out.println("update password");

					// connexion + mise a jour de la BDD
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

					PreparedStatement st = (PreparedStatement) con
							.prepareStatement("Update account set password=? where user_name=?");

					st.setString(1, pstr);
					st.setString(2, name);
					st.executeUpdate();
					JOptionPane.showMessageDialog(btnSearch, "Password has been successfully changed");

				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
		btnSearch.setBackground(new Color(240, 240, 240));
		btnSearch.setBounds(438, 127, 170, 59);
		contentPane.add(btnSearch);

		lblEnterNewPassword = new JLabel("Enter New Password :");
		lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblEnterNewPassword.setBounds(45, 37, 326, 67);
		contentPane.add(lblEnterNewPassword);
	}

}
