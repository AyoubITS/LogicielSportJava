package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * Fenetre des param�tres
 *
 */
public class Settings extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton backHome;
	private JButton button;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Friend frame = new Friend();
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

	public Settings() {

	}

	/**
	 * G�n�re la frame des param�tres
	 * 
	 * @param userSes Nom de l'utilisateur
	 */
	public Settings(String userSes) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// cliquer sur le bouton d�co ouvre une box qui demande de valider notre choix
		// si c'est le cas la page se ferme et on retourne sur login
		btnNewButton = new JButton("Deconnexion");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
				// JOptionPane.setRootFrame(null);
				if (a == JOptionPane.YES_OPTION) {
					dispose();
					UserLogin obj = new UserLogin();
					obj.setTitle("Login");
					obj.setVisible(true);
				}
				dispose();
				UserLogin obj = new UserLogin();

				obj.setTitle("Login");
				obj.setVisible(true);

			}
		});
		btnNewButton.setBounds(344, 137, 288, 93);
		contentPane.add(btnNewButton);

		// lance la classe change password pour modifier le mdp
		button = new JButton("Change-password\r\n");
		button.setBackground(UIManager.getColor("Button.disabledForeground"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePassword bo = new ChangePassword(userSes);
				bo.setTitle("Change Password");
				bo.setVisible(true);

			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(344, 311, 288, 93);
		contentPane.add(button);

		backHome = new JButton("Accueil");
		backHome.setFont(new Font("Tahoma", Font.PLAIN, 26));
		backHome.setBounds(852, 484, 146, 73);
		contentPane.add(backHome);
		backHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserHome ah = new UserHome(userSes);
				ah.setTitle("Welcome");
				ah.setVisible(true);
			}
		});
	}
}