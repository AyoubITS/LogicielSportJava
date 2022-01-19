package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Classe de contrôle des amis
 * 
 * @see Friend
 */
public class FriendConditions {

	/**
	 * Vérifie si le premier utilisateur est amis avec le second
	 * 
	 * @param userSes  Nom de l'utilisateur 1
	 * @param userSes2 Nom de l'utilisateur 2
	 * @return true s'ils sont amis, sinon false
	 */
	static Boolean dejaAmis(String userSes, String userSes2) {
		try {
			Connection connection1 = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

			String query1 = "SELECT * FROM `Friend` WHERE (UserName1 = '" + userSes + "' AND UserName2= '" + userSes2
					+ "'  AND state='accept') OR (Username1 = '" + userSes2 + "' AND UserName2= '" + userSes
					+ "' AND state='accept')";
			Statement sta1 = connection1.createStatement();
			ResultSet x1 = sta1.executeQuery(query1);
			if (x1.next()) {
				System.out.println("Vous etes deja amis!");
				return true;
			}
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		System.out.println(" pas amis ou demande en cours... ");
		return false;
	}

	/**
	 * Vérifie si le premier utilisateur n'est pas ami avec le second
	 * 
	 * @param userSes  Nom de l'utilisateur 1
	 * @param userSes2 Nom de l'utilisateur 2
	 * @return true s'il ne sont pas amis, sinon false
	 */
	static Boolean pasAmis(String userSes, String userSes2) {
		try {
			Connection connection1 = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

			String query1 = "SELECT * FROM `Friend` WHERE (UserName1 = '" + userSes + "' AND UserName2= '" + userSes2
					+ "') OR (Username1 = '" + userSes2 + "' AND UserName2= '" + userSes + "')";
			Statement sta1 = connection1.createStatement();
			ResultSet x1 = sta1.executeQuery(query1);
			if (x1.next()) {
				System.out.println("Vous etes deja amis ou demande en cours ");
				return false;
			}
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		System.out.println("Pas amis, demander? ");
		return true;
	}

	/**
	 * Vérifie si le premier utilisateur a déja demande en ami le second
	 * 
	 * @param userSes  Nom de l'utilisateur 1
	 * @param userSes2 Nom de l'utilisateur 2
	 * @return true si la demande a déja été faite, sinon false
	 */
	static Boolean dejaDemande(String userSes, String userSes2) {
		try {
			Connection connection1 = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

			String query1 = "SELECT * FROM `Friend` WHERE (UserName1 = '" + userSes + "' AND UserName2= '" + userSes2
					+ "' AND state='waiting')";
			Statement sta1 = connection1.createStatement();
			ResultSet x1 = sta1.executeQuery(query1);
			if (x1.next()) {
				System.out.println(" demande en cours... ");
				return true;
			}
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		System.out.println("Pas amis ou accepter demande? ");
		return false;
	}

	/**
	 * Vérifie si le premier utilisateur a déja reçu une demande d'ami du second
	 * 
	 * @param userSes  Nom de l'utilisateur 1
	 * @param userSes2 Nom de l'utilisateur 2
	 * @return true s'il a deja reçu la demande, sinon false
	 */
	static Boolean demandeRecue(String userSes, String userSes2) {
		try {
			Connection connection1 = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");

			String query1 = "SELECT * FROM `Friend` WHERE (UserName1 = '" + userSes2 + "' AND UserName2= '" + userSes
					+ "' AND state='waiting')";
			Statement sta1 = connection1.createStatement();
			ResultSet x1 = sta1.executeQuery(query1);
			if (x1.next()) {
				System.out.println(" cette personne vous a demandé en ami! ");
				return true;
			}
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		System.out.println("Pas amis ou en attente ");
		return false;
	}

	/**
	 * Fonction débogueur pour les liste d'amis
	 * 
	 * @see FriendConditions#dejaAmis(String, String)
	 * @see FriendConditions#pasAmis(String, String)
	 * @see FriendConditions#dejaDemande(String, String)
	 * @see FriendConditions#demandeRecue(String, String)
	 */
	public static void main(String[] args) {
		System.out.println("methode deja amis :");
		Boolean ok = dejaAmis("aaa", "S");
		System.out.println(ok + "\n");

		System.out.println("methode pas amis :");
		Boolean ok1 = pasAmis("aaa", "S");
		System.out.println(ok1 + "\n");

		System.out.println("methode deja demandé :");
		Boolean ok2 = dejaDemande("aaa", "S");
		System.out.println(ok2 + "\n");

		System.out.println("methode demande recue :");
		Boolean ok3 = demandeRecue("aaa", "S");
		System.out.println(ok3 + "\n");
	}
}
