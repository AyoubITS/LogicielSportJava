package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Graphics.SportLastWeek;
import Graphics.SportMostPracticed;

/**
 * Fenêtre d'accueil du logiciel
 *
 */
public class UserHome extends JFrame {

	private static final long serialVersionUID = 1;

	private JPanel contentPane;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton StatsSportPracticedButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserHome frame = new UserHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserHome() {

	}

	/**
	 * Crée la frame de la page d'accueil
	 */
	public UserHome(String userSes) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnNewButton_1 = new JButton("Accueil");
		btnNewButton_1.setBounds(115, 500, 113, 46);
		contentPane.add(btnNewButton_1);

		// bouton qui lance la page seance
		btnNewButton_2 = new JButton("Ajouter s\u00E9ance");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Seance s = new Seance(userSes);
				s.setVisible(true);

			}
		});
		btnNewButton_2.setBounds(321, 500, 113, 46);
		contentPane.add(btnNewButton_2);

		// bouton qui lance la page amis
		btnNewButton_3 = new JButton("Amis");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Friend fr = new Friend(userSes);
				fr.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(541, 500, 113, 46);
		contentPane.add(btnNewButton_3);

		// bouton qui lance la page parametre
		btnNewButton_4 = new JButton("Param\u00E8tres");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Settings se = new Settings(userSes);
				se.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(763, 500, 113, 46);
		contentPane.add(btnNewButton_4);

		// bouton qui lance la page sport most practiced
		StatsSportPracticedButton = new JButton("Voir d\u00E9tail");
		StatsSportPracticedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SportMostPracticed smp = new SportMostPracticed(userSes);
				smp.setTitle("Sport les plus pratiqués");
				smp.setVisible(true);
			}
		});
		StatsSportPracticedButton.setBounds(166, 264, 113, 46);
		contentPane.add(StatsSportPracticedButton);

		JLabel lblVosSportLes = new JLabel("Vos sport les plus pratiqu\u00E9s");
		lblVosSportLes.setHorizontalAlignment(SwingConstants.CENTER);
		lblVosSportLes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVosSportLes.setBounds(10, 200, 424, 53);
		contentPane.add(lblVosSportLes);

		JLabel lblSportPratiquesAu = new JLabel("Sport pratiqu\u00E9es au cours des 7 derniers jours");
		lblSportPratiquesAu.setHorizontalAlignment(SwingConstants.CENTER);
		lblSportPratiquesAu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSportPratiquesAu.setBounds(541, 200, 457, 53);
		contentPane.add(lblSportPratiquesAu);

		// bouton qui lance la page sport last week
		JButton SportLastWeekButton = new JButton("Voir d\u00E9tail");
		SportLastWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SportLastWeek slw = new SportLastWeek(userSes);
				slw.setTitle("Sport les plus pratiqués");
				slw.setVisible(true);
			}
		});
		SportLastWeekButton.setBounds(714, 264, 113, 46);
		contentPane.add(SportLastWeekButton);

	}
}
