package GUI;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Graphics.StringSportValues;

import java.util.Date;
import javax.swing.SwingConstants;

/**
 * Fenêtre de formulaire d'ajout de séance
 *
 */
public class AddValues extends JFrame {

	private JPanel contentPane;
	private JTextField firstValueField;
	private JTextField secondValueField;
	private JButton sendValuesButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					String username = "UsernameTest";
					String sport = StringSportValues.Name_SPORT_3;
					String nameFirstValue = "valeur un";
					String nameSecondValue = "valeur deux";
					AddValues frame = new AddValues(username, sport, nameFirstValue, nameSecondValue);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Génère le formulaire pour entrer les donner d'une nouvelle séance
	 * 
	 * @param userSes         Nom de l'utilisateur
	 * @param SportChoosen    Sport choisit par l'utilisateur
	 * @param nameFirstValue  Le nom de la première statistique du Sport choisi
	 * @param nameSecondValue Le nom de la deuxième statistique du Sport choisi
	 */
	public AddValues(String userSes, String SportChoosen, String nameFirstValue, String nameSecondValue) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 337, 420);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewValues = new JLabel("Nouvelles valeurs pour : " + SportChoosen);
		lblNewValues.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewValues.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewValues.setBounds(0, 10, 331, 50);
		contentPane.add(lblNewValues);

		firstValueField = new JTextField();
		firstValueField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		firstValueField.setBounds(0, 164, 331, 36);
		contentPane.add(firstValueField);
		firstValueField.setColumns(10);

		secondValueField = new JTextField();
		secondValueField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		secondValueField.setBounds(0, 254, 331, 36);
		contentPane.add(secondValueField);

		JLabel lblUsername = new JLabel(nameFirstValue);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBackground(Color.BLACK);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(0, 120, 331, 52);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel(nameSecondValue);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setBackground(Color.BLACK);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(0, 205, 331, 52);
		contentPane.add(lblPassword);

		sendValuesButton = new JButton("Ajouter");
		sendValuesButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		sendValuesButton.setBounds(10, 307, 146, 73);
		sendValuesButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String username = userSes;
				String sportName = SportChoosen;
				String firstValue = firstValueField.getText();
				String secondValue = secondValueField.getText();

				if ((firstValue.length() == 0) || (secondValue.length() == 0)) {
					JOptionPane.showMessageDialog(sendValuesButton, "Merci de saisir tous les champs !");
				} else {
					if (checkValues(SportChoosen, firstValue, secondValue)) {
						// on regarde le sport pour sauvegarder dans la bonne table
						switch (sportName) {
						case StringSportValues.Name_SPORT_1:
							SaveValuesDB(StringSportValues.Name_SPORT_1, username, firstValue, secondValue);
							break;
						case StringSportValues.Name_SPORT_2:
							SaveValuesDB(StringSportValues.Name_SPORT_2, username, firstValue, secondValue);
							break;
						case StringSportValues.Name_SPORT_3:
							SaveValuesDB(StringSportValues.Name_SPORT_3, username, firstValue, secondValue);
							break;
						case StringSportValues.Name_SPORT_4:
							SaveValuesDB(StringSportValues.Name_SPORT_4, username, firstValue, secondValue);
							break;
						case StringSportValues.Name_SPORT_5:
							SaveValuesDB(StringSportValues.Name_SPORT_5, username, firstValue, secondValue);
							break;
						}
						dispose();
					} else {
						JOptionPane.showMessageDialog(sendValuesButton, "Donnees non valide !");
					}
				}
			}

			/**
			 * Sauvegarde dans la base de données une Seance de Sport
			 * 
			 * @param nameSport   Nom du Sport
			 * @param username    Nom de l'utilisateur
			 * @param firstValue  Valeur de la premiere Statistique
			 * @param secondValue Valeur de la seconde Statistique
			 */
			public void SaveValuesDB(String nameSport, String username, String firstValue, String secondValue) {
				try {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = new Date();
					String dateDB = dateFormat.format(date);
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
					String query = "SELECT COUNT(*) FROM `" + nameSport + "`";
					Statement sta = connection.createStatement();
					ResultSet result = sta.executeQuery(query);
					result.next();
					int numberRow = result.getInt(1);
					// génération de la requête
					switch (nameSport) {
					case StringSportValues.Name_SPORT_1:
						query = "INSERT INTO " + nameSport + " (sport_id,date,user_name,precision_rate,time) values('"
								+ numberRow + "','" + dateDB + "','" + username + "','" + firstValue + "','"
								+ secondValue + "')";
						break;
					case StringSportValues.Name_SPORT_2:
						query = "INSERT INTO " + nameSport + " (sport_id,date,user_name,intensité,time) values('"
								+ numberRow + "','" + dateDB + "','" + username + "','" + firstValue + "','"
								+ secondValue + "')";
						break;
					case StringSportValues.Name_SPORT_3:
						query = "INSERT INTO " + nameSport + " (sport_id,date,user_name,time,nb_longueur) values('"
								+ numberRow + "','" + dateDB + "','" + username + "','" + firstValue + "','"
								+ secondValue + "')";
						break;
					case StringSportValues.Name_SPORT_4:
						query = "INSERT INTO " + nameSport + " (sport_id,date,user_name,time,distance) values('"
								+ numberRow + "','" + dateDB + "','" + username + "','" + firstValue + "','"
								+ secondValue + "')";
						break;
					case StringSportValues.Name_SPORT_5:
						query = "INSERT INTO " + nameSport + " (sport_id,date,user_name,nombre_jeu,time) values('"
								+ numberRow + "','" + dateDB + "','" + username + "','" + firstValue + "','"
								+ secondValue + "')";
						break;
					}
					sta = connection.createStatement();
					sta.executeUpdate(query);
					connection.close();
					JOptionPane.showMessageDialog(sendValuesButton, "Valeurs enregistrées !");
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}

			/**
			 * Vérifie les données saisie par l'utilisateur, pour pouvoir les insérer dans la base de données après
			 * @param SportChoosen Sport choisie
			 * @param firstValue La première statistique
			 * @param secondValue La seconde statistique
			 * @return true si les données sont valide, sinon false
			 */
			public boolean checkValues(String SportChoosen, String firstValue, String secondValue) {
				// insert traitments of Values ( verification )
				try {
					Integer.parseInt(firstValue);
					Integer.parseInt(secondValue);
				} catch (NumberFormatException e) {
					return false;
				} catch (NullPointerException e) {
					return false;
				}
				// only got here if we didn't return false
				return true;
			}

		});

		contentPane.add(sendValuesButton);

		JButton CancelButton = new JButton("Annuler");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		CancelButton.setBounds(166, 307, 146, 73);
		contentPane.add(CancelButton);
		CancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
}
