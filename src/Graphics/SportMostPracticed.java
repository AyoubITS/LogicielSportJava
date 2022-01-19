package Graphics;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class SportMostPracticed extends JFrame {
	/**
	 * Diagramme de type circulaire qui affiche tous les sports fait par
	 * l'utilisateur et le temps de pratique
	 * 
	 * @param userSes Nom de l'utilisateur
	 */
	public SportMostPracticed(String userSes) {
		JPanel pnl;
		pnl = new JPanel(new BorderLayout());
		setContentPane(pnl);
		setSize(400, 250);

		HashMap<String, Integer> ResultHashMap = new HashMap<String, Integer>();
		ResultHashMap = SearchHourPractice(userSes);
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		Set set = ResultHashMap.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			// METTRE DANS LE GRAPH
			pieDataset.setValue(me.getKey().toString() + " : " + me.getValue() + " min", ((int) me.getValue()));
		}
		JFreeChart pieChart = ChartFactory.createPieChart("Sport les plus pratiques", pieDataset, true, true, true);
		ChartPanel cPanel = new ChartPanel(pieChart);
		pnl.add(cPanel);

	}

	/**
	 * Cherche dans la base de données toutes les séances d'un utilisateur pour
	 * calculer le temps de pratique total pour chaque Sport
	 * 
	 * @param userSesNom de l'utilisateur
	 * @return Un dictionnaire avec pour clé le nom d'un sport et valeur le temps de
	 *         pratique de celui ci
	 * @see SportMostPracticed#ListDateLastWeek(String[])
	 * @see SportMostPracticed#generatequery(String[], String)
	 */
	public HashMap<String, Integer> SearchHourPractice(String userSes) {
		try {
			HashMap<String, Integer> ResultHashMap = new HashMap<String, Integer>();
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
			String datelastweek[] = new String[7];
			datelastweek = ListDateLastWeek(datelastweek);
			String query;
			query = generatequery(datelastweek, userSes);
			Statement sta = connection.createStatement();
			ResultSet result = sta.executeQuery(query);

			while (result.next()) {
				// si le sport est nouveau, on l'ajoute dans le dictionnaire, sinon on
				// additionne le temps à la valeur totale précédente
				if (!ResultHashMap.containsKey(result.getString(3))) {
					ResultHashMap.put(result.getString(3), result.getInt(2));
				} else {
					ResultHashMap.put(result.getString(3), result.getInt(2) + ResultHashMap.get(result.getString(3)));
				}
			}
			connection.close();
			return ResultHashMap;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Génère la requête SQL qui va être utiliser pour rechercher toutes les séances
	 * de la semaine en cours d'un utilisateur
	 * 
	 * @param datelastweek tableau contenant les dates des 7 derniers jours
	 * @param userSes      Nom de l'utilisateur dont on doit récupérer les données
	 * @return La requête SQL à utiliser
	 * @see SportMostPracticed#SearchHourPractice(String)
	 */
	private String generatequery(String[] datelastweek, String userSes) {
		String query = "";
		String ListSport[] = { "Arc", "Boxe", "Natation", "Ski", "Tennis" };
		// parcours la liste de tous les sports, on les ajoute dans la requête SQL
		for (int indexSport = 0; indexSport < ListSport.length; indexSport++) {
			query += "SELECT ALL sport_id,time,'" + ListSport[indexSport] + "' as sport FROM `" + ListSport[indexSport]
					+ "` WHERE (user_name = '" + userSes + "')";
			if (indexSport != ListSport.length - 1) {
				query += "UNION" + " ";
			}
		}
		return query;
	}

	/**
	 * Génère un tableau contenant les 7 dernières dates dont aujourd'hui
	 * 
	 * @param tabDates un tableau vide à compléter
	 * @return le tableau remplit
	 * @see SportMostPracticed#SearchHourPractice(String)
	 */
	public String[] ListDateLastWeek(String[] tabDates) {
		Calendar calendar = Calendar.getInstance();
		// On utilise le même format que dans la base de données
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
		for (int index = 0; index < 7; index++) {
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -index);
			tabDates[index] = dateFormat.format(calendar.getTime());
		}
		return tabDates;
	}
}
