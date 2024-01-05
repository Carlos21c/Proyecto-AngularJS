package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Restaurant;


public class JDBCFavoritesDAOImpl implements FavoritesDAO{
	
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCRestaurantDAOImpl.class.getName());
	

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
	@Override
	public void add(long restId, long userId) {

		if (conn != null){

			Statement stmt;
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO favorites (idr,idu) VALUES("
									+ restId +","+ userId + ")");				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	@Override
	public boolean delete(long restId, long userId) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM favorites WHERE idr="+restId+" AND idu="+userId);
				logger.info("deleting favorite: "+restId);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}
	
	@Override
	public List<Restaurant> getFavs(long userId) {
		if (conn == null)
			return null;

		ArrayList <Integer> ids = new ArrayList<Integer>(); 
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		int idRest;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rsId = stmt.executeQuery("SELECT idr FROM favorites WHERE idu = "+ userId);

			while (rsId.next()) {
				idRest = rsId.getInt("idr");
				ids.add(idRest); 
			}
			for (int i=0; i < ids.size(); i++) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM restaurants WHERE id = "+ ids.get(i));
				Restaurant restaurant = new Restaurant();
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getInt("gradesAverage"));
				restaurant.setMinPrice(rs.getInt("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getInt("maxPrice"));
				restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
				restaurant.setAvailable(rs.getInt("available"));
				restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
				restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
				restaurant.setImage(rs.getString("image"));
				restaurants.add(restaurant);
		
				logger.info("fetching restaurants: "+restaurant.getId());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurants;
	}

	@Override
	public boolean check(long restId, long userId) {
		boolean exist = false; 
		if (conn != null) {

			Statement stmt;

			try {
				stmt = conn.createStatement();
				ResultSet rsId = stmt.executeQuery(
						"SELECT * FROM favorites WHERE idr="+restId+" AND idu="+ userId);
				if(rsId.next()) {
					exist = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return exist;
	}
	
}
