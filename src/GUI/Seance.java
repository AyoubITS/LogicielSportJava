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
import java.sql.Statement;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Graphics.StatsSport;
import Graphics.StringSportValues;

/**
 * Fenêtre de gestion des séances
 *
 */
public class Seance extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton addSeances;
	private JButton backHome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seance frame = new Seance();
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

	public Seance() {

	}

	/**
	 * Crée la frame du panneau de contrôle des séances
	 * 
	 * @param userSes Nom de l'utilisateur
	 */
	public Seance(String userSes) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ajouter/voir les séances de " + "Test"/* userSes */);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 365, 58);
		contentPane.add(lblNewLabel);

		addSeances = new JButton("Ajouter Seance");
		addSeances.setFont(new Font("Tahoma", Font.PLAIN, 26));
		addSeances.setBounds(10, 484, 233, 73);
		contentPane.add(addSeances);
		addSeances.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChooseSport av = new ChooseSport(userSes);
				av.setTitle("Ajouter Seance");
				av.setVisible(true);
				dispose();
			}
		});

		backHome = new JButton("Accueil");
		backHome.setFont(new Font("Tahoma", Font.PLAIN, 26));
		backHome.setBounds(824, 484, 146, 73);
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

		//génération liste déroulante de la durée
		JComboBox ChooseDuration = new JComboBox();
		ChooseDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ChooseDuration.setModel(new DefaultComboBoxModel(new String[] { "cette semaine", "ce mois", "cette annee" }));
		ChooseDuration.setMaximumRowCount(3);
		ChooseDuration.setBounds(10, 105, 146, 22);
		contentPane.add(ChooseDuration);

		JLabel lblVoirLesStatistiques = new JLabel("Voir les statistiques d\u00E9taill\u00E9es de :");
		lblVoirLesStatistiques.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVoirLesStatistiques.setHorizontalAlignment(SwingConstants.LEFT);
		lblVoirLesStatistiques.setBounds(10, 80, 233, 22);
		contentPane.add(lblVoirLesStatistiques);

		JLabel lblPourLeSport = new JLabel("Pour le sport : ");
		lblPourLeSport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPourLeSport.setBounds(10, 138, 151, 22);
		contentPane.add(lblPourLeSport);

		//génération des boutons radios pour le choix du sport
		JRadioButton ArcButton = new JRadioButton(StringSportValues.Name_SPORT_1);
		ArcButton.setSelected(true);
		ArcButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ArcButton.setBounds(20, 167, 192, 23);
		contentPane.add(ArcButton);

		JRadioButton BoxeButton = new JRadioButton(StringSportValues.Name_SPORT_2);
		BoxeButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		BoxeButton.setBounds(20, 193, 192, 23);
		contentPane.add(BoxeButton);

		JRadioButton NatationButton = new JRadioButton(StringSportValues.Name_SPORT_3);
		NatationButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		NatationButton.setBounds(20, 219, 192, 23);
		contentPane.add(NatationButton);

		JRadioButton SkiButton = new JRadioButton(StringSportValues.Name_SPORT_4);
		SkiButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SkiButton.setBounds(20, 245, 192, 23);
		contentPane.add(SkiButton);

		JRadioButton TennisButton = new JRadioButton(StringSportValues.Name_SPORT_5);
		TennisButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TennisButton.setBounds(20, 271, 192, 23);
		contentPane.add(TennisButton);

		ButtonGroup ChooseSportButton = new ButtonGroup();
		ChooseSportButton.add(ArcButton);
		ChooseSportButton.add(BoxeButton);
		ChooseSportButton.add(NatationButton);
		ChooseSportButton.add(SkiButton);
		ChooseSportButton.add(TennisButton);

		//bouton pour afficher le graphique précédemment paramétrer
		JButton btnVoir = new JButton("Voir");
		btnVoir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Enumeration<AbstractButton> buttons = ChooseSportButton.getElements(); buttons
						.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected()) {
						String durationSelected = (String) ChooseDuration.getSelectedItem();

						String sportChoosen = button.getText();
						String duration = "";
						switch (durationSelected) {
						case "cette semaine":
							duration = StringSportValues.WEEK;
							break;
						case "ce mois":
							duration = StringSportValues.MONTH;
							break;
						case "cette annee":
							duration = StringSportValues.YEAR;
							break;
						}

						StatsSport ss = new StatsSport(userSes, duration, sportChoosen);
						ss.setTitle("Résume de pratique de sports");
						ss.setVisible(true);
					}
				}
			}
		});
		btnVoir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoir.setBounds(10, 328, 146, 23);
		contentPane.add(btnVoir);

		JLabel lblModif = new JLabel("Liste des 3 derni\u00E8res s\u00E9ances :");
		lblModif.setHorizontalAlignment(SwingConstants.CENTER);
		lblModif.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModif.setBounds(253, 80, 745, 22);
		contentPane.add(lblModif);

		try {
			//connexion pour afficher les 3 dernières séances
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
			String query = "(";
			String ListSport[] = { "Arc", "Boxe", "Natation", "Ski", "Tennis" };
			for (int indexSport = 0; indexSport < ListSport.length; indexSport++) {
				query += "SELECT ALL date,sport_id,'" + ListSport[indexSport] + "' as sport FROM `"
						+ ListSport[indexSport] + "` WHERE (user_name = '" + userSes + "') ";
				if (indexSport != ListSport.length - 1) {
					query += "UNION" + " ";
				}
			}
			query += ") ORDER BY date DESC LIMIT 3";
			System.out.println(query);
			Statement sta = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultquery = sta.executeQuery(query);

			resultquery.next();
			JLabel SportFirstLast = new JLabel(resultquery.getString(3));
			SportFirstLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			SportFirstLast.setBounds(707, 193, 192, 22);
			contentPane.add(SportFirstLast);

			JLabel DateFirstLast = new JLabel(resultquery.getString(1));
			DateFirstLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DateFirstLast.setBounds(505, 193, 192, 22);
			contentPane.add(DateFirstLast);

			resultquery.next();
			JLabel DateSecondLast = new JLabel(resultquery.getString(1));
			DateSecondLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DateSecondLast.setBounds(505, 226, 192, 22);
			contentPane.add(DateSecondLast);

			JLabel SportSecondLast = new JLabel(resultquery.getString(3));
			SportSecondLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			SportSecondLast.setBounds(707, 226, 192, 22);
			contentPane.add(SportSecondLast);

			resultquery.next();
			JLabel DateThirdLast = new JLabel(resultquery.getString(1));
			DateThirdLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DateThirdLast.setBounds(505, 259, 192, 22);
			contentPane.add(DateThirdLast);

			JLabel SportThirdLast = new JLabel(resultquery.getString(3));
			SportThirdLast.setFont(new Font("Tahoma", Font.PLAIN, 14));
			SportThirdLast.setBounds(707, 259, 192, 22);
			contentPane.add(SportThirdLast);

			JButton DeleteFirstLastSeance = new JButton("Supprimer");
			DeleteFirstLastSeance.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DeleteFirstLastSeance.setBounds(852, 193, 146, 22);
			DeleteFirstLastSeance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteSeance(userSes, SportFirstLast.getText(), DateFirstLast.getText());
					JOptionPane.showMessageDialog(DeleteFirstLastSeance, "Seance supprimee");
					Seance s = new Seance(userSes);
					s.setVisible(true);
					dispose();

				}
			});

			contentPane.add(DeleteFirstLastSeance);

			JButton DeleteSecondLastSeance = new JButton("Supprimer");
			DeleteSecondLastSeance.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DeleteSecondLastSeance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteSeance(userSes, SportSecondLast.getText(), DateSecondLast.getText());
					JOptionPane.showMessageDialog(DeleteSecondLastSeance, "Seance supprimee");
					Seance s = new Seance(userSes);
					s.setVisible(true);
					dispose();
				}
			});
			DeleteSecondLastSeance.setBounds(852, 226, 146, 22);
			contentPane.add(DeleteSecondLastSeance);

			JButton DeleteThirdLastSeance = new JButton("Supprimer");
			DeleteThirdLastSeance.setFont(new Font("Tahoma", Font.PLAIN, 14));
			DeleteThirdLastSeance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteSeance(userSes, SportThirdLast.getText(), DateThirdLast.getText());
					JOptionPane.showMessageDialog(DeleteThirdLastSeance, "Seance supprimee");
					Seance s = new Seance(userSes);
					s.setVisible(true);
					dispose();
				}
			});
			DeleteThirdLastSeance.setBounds(852, 259, 146, 23);
			contentPane.add(DeleteThirdLastSeance);

			connection.close();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	/**
	 * Supprime la séance sélectionnée par l'utilisateur
	 * 
	 * @param userSes Nom de l'utilisateur
	 * @param sport   Le sport de la séance
	 * @param date    La date de la séance
	 */
	protected void deleteSeance(String userSes, String sport, String date) {
		try {
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
			String query;
			query = "DELETE FROM `" + sport + "` WHERE ((user_name = '" + userSes + "') AND (date ='" + date + "'))";
			PreparedStatement st = connection.prepareStatement(query);
			st.executeUpdate();
			connection.close();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}
}
