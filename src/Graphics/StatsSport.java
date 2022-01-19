package Graphics;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatsSport extends JFrame {

	/**
	 * Génère une fenêtre avec graphique du ratio perf/temps en fonction de la durée
	 * de la séance pour un sport données et un interval de temps donnée
	 * 
	 * @param userSes  Nom de l'utilisateur
	 * @param duration durée de l'intervalle(cette année, ce mois, cette semaine)
	 * @param Sport    Sport dont on doit afficher les performances
	 * @see StatsSport#generateQuery(String, String, String)
	 * @see StatsSport#createDataset(ResultSet, String, String)
	 */
	public StatsSport(String userSes, String duration, String Sport) {
		JPanel pnl;
		pnl = new JPanel(new BorderLayout());
		setContentPane(pnl);
		setSize(800, 500);

		try {
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://mysql-netlife.alwaysdata.net/netlife_sport", "netlife", "Ucp2020=");
			String query = generateQuery(userSes, duration, Sport);
			Statement sta = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultquery = sta.executeQuery(query);
			ResultSetMetaData md = (ResultSetMetaData) resultquery.getMetaData();

			DefaultCategoryDataset dataset = createDataset(resultquery, duration, Sport);
			JFreeChart chart = ChartFactory.createLineChart("Ratio des performances de " + Sport, "Date",
					md.getColumnLabel(3) + " / " + md.getColumnLabel(4), dataset, PlotOrientation.VERTICAL, true, true,
					false);

			ChartPanel chartPanel = new ChartPanel(chart);
			pnl.add(chartPanel);
			connection.close();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	/**
	 * Génère le graphique de type courbe du ratio perf/temps d'un Sport
	 * 
	 * @param resultquery La liste des données compatibles trouvées dans la base de
	 *                    données
	 * @param duration    durée de l'intervalle(cette année, ce mois, cette semaine)
	 * @param Sport       Sport dont on doit afficher les performances
	 * @return Le graphique à afficher
	 * @throws SQLException
	 */
	public DefaultCategoryDataset createDataset(ResultSet resultquery, String duration, String Sport)
			throws SQLException {
		String serie = "Perf/time";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		int lastduration = 0, Occurencenumber = 0, tmpStat = 0;

		String ComparaisonLine = "Best";
		int minimuntime = 100, maximuntime = 0, bestSeance = 0;
		while (resultquery.next()) {
			minimuntime = Math.min(minimuntime, resultquery.getInt(4));
			maximuntime = Math.max(maximuntime, resultquery.getInt(4));
			bestSeance = Math.max(bestSeance, resultquery.getInt(3));
		}
		for (int index = minimuntime; index <= maximuntime; index += 5) {
			dataset.addValue(bestSeance, ComparaisonLine, String.valueOf(index));
			dataset.addValue(0, serie, String.valueOf(index));
		}
		dataset.addValue(bestSeance, ComparaisonLine, String.valueOf(minimuntime));
		dataset.addValue(bestSeance, ComparaisonLine, String.valueOf(maximuntime));

		resultquery.beforeFirst();
		while (resultquery.next()) {

			if (lastduration == resultquery.getInt(4)) {
				Occurencenumber += 1;
				tmpStat += resultquery.getInt(3);
			} else {
				if ((Occurencenumber != 0) && (tmpStat != 0)) {
					dataset.addValue(tmpStat / Occurencenumber, serie, String.valueOf(lastduration));
				}
				lastduration = resultquery.getInt(4);
				Occurencenumber = 1;
				tmpStat = resultquery.getInt(3);
			}
		}
		dataset.addValue(tmpStat / Occurencenumber, serie, String.valueOf(lastduration));
		return dataset;

	}

	/**
	 * Génère la requête SQL qui va être utiliser pour trouver toutes les séances
	 * d'un Sport données dans un intervalle de temps donné
	 * 
	 * @param userSes  Nom de l'utilisateur
	 * @param duration durée de l'intervalle(cette année, ce mois, cette semaine)
	 * @param Sport    Sport dont on doit afficher les performances
	 * @return La requête SQL
	 * 
	 * @see StatsSport#createDataset(ResultSet, String, String)
	 */
	private String generateQuery(String userSes, String duration, String Sport) {
		String query = "";
		// en fonction du sport, on ne choisit par la même donnée dans la même table
		switch (Sport) {
		case StringSportValues.Name_SPORT_1:
			query = "SELECT sport_id,date,precision_rate,time FROM Arc ";
			break;
		case StringSportValues.Name_SPORT_2:
			query = "SELECT sport_id,date,intensité,time FROM Boxe ";
			break;
		case StringSportValues.Name_SPORT_3:
			query = "SELECT sport_id,date,nb_longueur,time FROM Natation ";
			break;
		case StringSportValues.Name_SPORT_4:
			query = "SELECT sport_id,date,distance,time FROM Ski ";
			break;
		case StringSportValues.Name_SPORT_5:
			query = "SELECT sport_id,date,nombre_jeu,time FROM Tennis ";
			break;
		}
		query += "WHERE (user_name = '" + userSes + "')";

		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		String curMonth = new SimpleDateFormat("MM").format(new Date());
		// on cherche les données de cette année, de ce mois ou de cette semaine
		switch (duration) {
		case StringSportValues.YEAR:
			query += "AND (date LIKE ('" + curYear + "%'))";
			break;
		case StringSportValues.MONTH:
			query += "AND (date LIKE ('" + curYear + "-" + curMonth + "%'))";
			break;
		case StringSportValues.WEEK:
			query += "AND (";
			Calendar calendardaythisweek = Calendar.getInstance();
			DateFormat day = new SimpleDateFormat("MM-dd");
			for (int index = Calendar.SUNDAY; index <= Calendar.SATURDAY; index++) {
				calendardaythisweek.set(Calendar.DAY_OF_WEEK, index);
				String curDay = day.format(calendardaythisweek.getTime());
				query += "(date LIKE ('" + curYear + "-" + curDay + "%'))";
				if (index != Calendar.SATURDAY) {
					query += " OR ";
				} else {
					query += ") ";
				}
			}
			break;
		}
		query += "ORDER BY time";

		return query;
	}
}
