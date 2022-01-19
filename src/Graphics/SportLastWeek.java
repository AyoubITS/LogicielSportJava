package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import GUI.UserHome;

public class SportLastWeek extends JFrame {
	/**
	 * Graphique de type Diagramme en b�tons qui affiche les s�ances de sports au
	 * cours des 7 derniers jours
	 * 
	 * @param userSes Nom de l'utilisateur
	 */
	public SportLastWeek(String userSes) {
		JPanel pnl;
		pnl = new JPanel(new BorderLayout());
		setContentPane(pnl);
		setSize(800, 500);

		CategoryDataset dataset = createDataset(userSes);
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		pnl.add(chartPanel);
	}

	/**
	 * G�n�re le Diagramme en b�tons : cr�ation de l'objet JFreeChart � afficher et
	 * de ses param�tres (couleurs, nom)
	 * 
	 * @param dataset Les donn�es � ins�rer dans le graphique
	 * @return L'objet diagramme en b�tons
	 */
	private JFreeChart createChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createStackedAreaChart("Sports pratiqu�s ces 7 derniers jours", "",
				"Temps (min)", dataset, PlotOrientation.VERTICAL, true, true, false);
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		CategoryPlot plot = chart.getCategoryPlot();

		// donne une couleur � chaque �l�ments pour les diff�rencier
		renderer.setSeriesPaint(0, new Color(51, 255, 51));
		renderer.setSeriesPaint(1, new Color(255, 51, 51));
		renderer.setSeriesPaint(2, new Color(51, 51, 255));
		renderer.setSeriesPaint(3, new Color(128, 128, 128));
		renderer.setSeriesPaint(4, new Color(255, 255, 0));
		plot.setRenderer(renderer);
		return chart;
	}

	/**
	 * G�n�re les donn�es � utiliser dans le futur Graphique de type Diagramme en
	 * b�tons, effectue une connexion � la base de donn�es avec une requ�te d�ja
	 * construite puis stock les r�sultat dans une variable de type
	 * DefaultCategoryDataset
	 * 
	 * @param userSes L'identifiant de la personne dont on doit r�colter ses
	 *                statistiques
	 * @return Un objet de type DefaultCategoryDataset contenant les donn�ees �
	 *         ins�rer dans le graphique
	 * @see SportLastWeek#generatequery(String[], String)
	 * @see SportLastWeek#createChart(CategoryDataset)
	 * @see SportLastWeek#ListDateLastWeek(String[])
	 */
	public CategoryDataset createDataset(String userSes) {
		try {
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
			String datelastweek[] = new String[7];
			datelastweek = ListDateLastWeek(datelastweek);
			String query;
			query = generatequery(datelastweek, userSes);
			Statement sta = connection.createStatement();
			ResultSet resultquery = sta.executeQuery(query);

			DefaultCategoryDataset result = new DefaultCategoryDataset();
			for (int index = datelastweek.length - 1; index >= 0; index--) {
				// on ajoute chaque Sport � chaque date avec une valeur de 0 pour qu'il soit
				// obligatoirement r�f�renc� dans le graphique
				result.addValue(0, StringSportValues.Name_SPORT_1, datelastweek[index].substring(0, 11));
				result.addValue(0, StringSportValues.Name_SPORT_2, datelastweek[index].substring(0, 11));
				result.addValue(0, StringSportValues.Name_SPORT_3, datelastweek[index].substring(0, 11));
				result.addValue(0, StringSportValues.Name_SPORT_4, datelastweek[index].substring(0, 11));
				result.addValue(0, StringSportValues.Name_SPORT_5, datelastweek[index].substring(0, 11));
			}
			while (resultquery.next()) {
				// on ajoute chaque r�sultat de la requ�te SQL dans la bonne colonne
				result.addValue(resultquery.getInt("time"), resultquery.getString("sport"),
						resultquery.getString("date").substring(0, 11));
			}
			connection.close();
			return result;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * G�n�re la requ�te SQL qui va �tre utiliser pour rechercher toutes les s�ances
	 * de la semaine en cours d'un utilisateur
	 * 
	 * @param datelastweek tableau contenant les dates des 7 derniers jours
	 * @param userSes      Nom de l'utilisateur dont on doit r�cup�rer les donn�es
	 * @return La requ�te SQL � utiliser
	 * @see SportLastWeek#createDataset(String)
	 */
	private String generatequery(String[] datelastweek, String userSes) {
		String query = "";
		String ListSport[] = { "Arc", "Boxe", "Natation", "Ski", "Tennis" };
		String whereClauseDate = "";

		// parcours toutes les dates et on les inclus dans la requ�te
		for (int indexDate = 0; indexDate < datelastweek.length; indexDate++) {
			whereClauseDate += "date LIKE ('" + datelastweek[indexDate] + "%')";
			if (indexDate < 6) {
				whereClauseDate += " OR ";
			}
		}
		// parcours tous les sports et les inclus dans la requ�te
		for (int indexSport = 0; indexSport < ListSport.length; indexSport++) {
			query += "SELECT ALL date,sport_id,time,'" + ListSport[indexSport] + "' as sport FROM `"
					+ ListSport[indexSport] + "` WHERE ((user_name = '" + userSes + "') AND (" + whereClauseDate
					+ ")) ";
			if (indexSport != ListSport.length - 1) {
				query += "UNION" + " ";
			}
		}
		query += "ORDER BY date";
		return query;
	}

	/**
	 * G�n�re un tableau contenant les 7 derni�res dates dont aujourd'hui
	 * 
	 * @param tabDates un tableau vide � compl�ter
	 * @return le tableau remplit
	 * @see SportLastWeek#createDataset(String)
	 */
	public String[] ListDateLastWeek(String[] tabDates) {
		Calendar calendar = Calendar.getInstance();
		// utilisation du m�me format que dans la Base de Donn�es
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
		for (int index = 0; index < 7; index++) {
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -index);
			tabDates[index] = dateFormat.format(calendar.getTime());
		}
		return tabDates;
	}

}
