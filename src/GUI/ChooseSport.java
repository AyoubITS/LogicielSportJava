package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Graphics.StringSportValues;

import javax.swing.SwingConstants;

/**
 * Fenêtre pour choisir le sport dont on veut ajouter une séance
 *
 */
public class ChooseSport extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String test = "Username";
					ChooseSport frame = new ChooseSport(test);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Créer la frame pour choisir le sport dont on veut ajouter une séance
	 * 
	 * @param userSes Nom de l'utilisateur
	 */
	public ChooseSport(String userSes) {
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ajouter des valeurs pour quel sport ?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 988, 202);
		contentPane.add(lblNewLabel);

		btnNewButton_1 = new JButton(StringSportValues.Name_SPORT_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sport = StringSportValues.Name_SPORT_1;
				String nameFirstValue = StringSportValues.Name_Value1_SPORT1;
				String nameSecondValue = StringSportValues.Name_Value2_SPORT1;
				AddValues ad = new AddValues(userSes, sport, nameFirstValue, nameSecondValue);
				ad.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(77, 224, 140, 70);
		contentPane.add(btnNewButton_1);

		btnNewButton_2 = new JButton(StringSportValues.Name_SPORT_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sport = StringSportValues.Name_SPORT_2;
				String nameFirstValue = StringSportValues.Name_Value1_SPORT2;
				String nameSecondValue = StringSportValues.Name_Value2_SPORT2;
				AddValues ad = new AddValues(userSes, sport, nameFirstValue, nameSecondValue);
				ad.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(227, 224, 140, 70);
		contentPane.add(btnNewButton_2);

		btnNewButton_3 = new JButton(StringSportValues.Name_SPORT_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sport = StringSportValues.Name_SPORT_3;
				String nameFirstValue = StringSportValues.Name_Value1_SPORT3;
				String nameSecondValue = StringSportValues.Name_Value2_SPORT3;
				AddValues ad = new AddValues(userSes, sport, nameFirstValue, nameSecondValue);
				ad.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(77, 318, 140, 70);
		contentPane.add(btnNewButton_3);

		btnNewButton_4 = new JButton(StringSportValues.Name_SPORT_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sport = StringSportValues.Name_SPORT_4;
				String nameFirstValue = StringSportValues.Name_Value1_SPORT4;
				String nameSecondValue = StringSportValues.Name_Value2_SPORT4;
				AddValues ad = new AddValues(userSes, sport, nameFirstValue, nameSecondValue);
				ad.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(377, 224, 140, 70);
		contentPane.add(btnNewButton_4);

		btnNewButton_5 = new JButton(StringSportValues.Name_SPORT_5);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sport = StringSportValues.Name_SPORT_5;
				String nameFirstValue = StringSportValues.Name_Value1_SPORT5;
				String nameSecondValue = StringSportValues.Name_Value2_SPORT5;
				AddValues ad = new AddValues(userSes, sport, nameFirstValue, nameSecondValue);
				ad.setVisible(true);
			}
		});
		btnNewButton_5.setBounds(227, 318, 140, 70);
		contentPane.add(btnNewButton_5);

		btnNewButton_6 = new JButton("Retour Accueil");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserHome uh = new UserHome(userSes);
				uh.setVisible(true);
				dispose();
			}
		});
		btnNewButton_6.setBounds(679, 330, 153, 46);
		contentPane.add(btnNewButton_6);
	}

}
