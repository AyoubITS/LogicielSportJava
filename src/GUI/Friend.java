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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Graphics.SportMostPracticed;

/**
 * Fenêtre pour afficher la liste d'amis, demander des personnes en amis et
 * afficher leurs statistique
 * 
 * @see FriendConditions
 *
 */
public class Friend extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton backHome;
	private JButton voir;
	private JButton demande;
	private JButton recu;
	private JLabel lab1;

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
	 * Crée la frame de la liste d'amis
	 */

	public Friend() {

	}

	public Friend(String userSes) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ajout/voir amis de " + userSes);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(423, 13, 400, 200);
		contentPane.add(lblNewLabel);
		lab1 = new JLabel("Product List");

		// Liste déroulante des pseudos existants

		JComboBox<String> liste = new JComboBox<String>();
		liste.setBounds(317, 205, 380, 103);
		contentPane.add(liste);
		try {

			// connexion a la bdd et recuperation des pseudos existants
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

			PreparedStatement statement = connection
					.prepareStatement("SELECT user_name FROM account WHERE user_name !='" + userSes + "'");
			try (ResultSet Rs = statement.executeQuery()) {

				// Pour affecter une valeur de base de données à un Combobox
				while (Rs.next()) {
					liste.addItem(Rs.getString("user_name"));
				}
			}

		} catch (Exception e) {
			System.out.print("impossible de se connecter à la base");
			e.printStackTrace();

		}

		ActionListener cbActionListener = new ActionListener() {// add actionlistner to listen for change
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {

				String user2 = (String) liste.getSelectedItem();// get the selected item
				FriendConditions fc = new FriendConditions();

				contentPane.revalidate();
				contentPane.repaint();

				// condition si user deja ami avec pseudo choisi
				if (fc.dejaAmis(userSes, user2)) {
					JOptionPane.showMessageDialog(liste, "Vous etes amis!");
					System.out.println("NN");
					voir = new JButton("Voir Stats");
					voir.setFont(new Font("Tahoma", Font.PLAIN, 26));
					voir.setBounds(382, 400, 229, 56);
					contentPane.add(voir);
					voir.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent f) {
							contentPane.remove(voir);
							contentPane.revalidate();
							contentPane.repaint();
							// voir graphique de l'ami
							SportMostPracticed smp = new SportMostPracticed(user2);
							smp.setTitle("Sport les plus pratiqués");
							smp.setVisible(true);

						}
					});

				}
				// condition si user pas ami avec pseudo choisi
				else if (fc.pasAmis(userSes, user2)) {
					JOptionPane.showMessageDialog(liste, "Vous etes pas amis!");

					demande = new JButton("Demander Amis");
					demande.setFont(new Font("Tahoma", Font.PLAIN, 26));
					demande.setBounds(382, 400, 229, 56);
					contentPane.add(demande);
					demande.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent f) {
							contentPane.remove(demande);
							contentPane.revalidate();
							contentPane.repaint();

							try {
								// ajouter a la bdd que l'user a demandé en ami le pseudo choisi
								Connection connection2 = DriverManager.getConnection(
										"jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife",
										"Ucp2020=");

								String query2 = "INSERT INTO Friend (UserName1, UserName2, state) values('" + userSes
										+ "','" + user2 + "',  'waiting' )";

								Statement sta = connection2.createStatement();
								int x = sta.executeUpdate(query2);
								if (x == 0) {
									JOptionPane.showMessageDialog(liste, "This is alredy exist");
								} else {
									JOptionPane.showMessageDialog(liste, "demande bien faite");
								}
								connection2.close();
							} catch (Exception exception) {
								exception.printStackTrace();
							}

						}
					});
				}
				// condition si user deja demandé ami pseudo choisi
				else if (fc.dejaDemande(userSes, user2)) {
					JOptionPane.showMessageDialog(liste, "Demande en attente!");

				}
				// condition si user a recu demande ami avec pseudo choisi
				else if (fc.demandeRecue(userSes, user2)) {
					JOptionPane.showMessageDialog(liste, "Vous avez recu une demande de sa part!");

					recu = new JButton("Accepter Demande");
					recu.setFont(new Font("Tahoma", Font.PLAIN, 26));
					recu.setBounds(382, 400, 229, 56);
					contentPane.add(recu);
					recu.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent f) {
							contentPane.remove(recu);
							contentPane.revalidate();
							contentPane.repaint();

							try {
								// mettre a jour la BDD avec etat "accepté"
								Connection connection3 = DriverManager.getConnection(
										"jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife",
										"Ucp2020=");

								String query3 = "UPDATE Friend SET state = 'accept' WHERE UserName1= '" + user2
										+ "' AND UserName2= '" + userSes + "' ";

								Statement sta = connection3.createStatement();
								int x = sta.executeUpdate(query3);
								if (x == 0) {
									JOptionPane.showMessageDialog(liste, "This is alredy exist");
								} else {
									JOptionPane.showMessageDialog(liste, "demande bien acceptée");
								}
								connection3.close();
							} catch (Exception exception) {
								exception.printStackTrace();
							}

						}
					});
				}

			}
		};

		liste.addActionListener(cbActionListener);

		// ajouter bouton accueil
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