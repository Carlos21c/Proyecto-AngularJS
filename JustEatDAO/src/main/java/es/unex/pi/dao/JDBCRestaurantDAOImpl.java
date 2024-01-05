package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Restaurant;

public class JDBCRestaurantDAOImpl implements RestaurantDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCRestaurantDAOImpl.class.getName());
	
	@Override
	public Restaurant get(long id) {
		if (conn == null) return null;
		
		Restaurant restaurant = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurants WHERE id ="+id);			 
			if (!rs.next()) return null; 
			restaurant  = new Restaurant();	 
			restaurant.setId(rs.getInt("id"));
			restaurant.setName(rs.getString("name"));
			restaurant.setAddress(rs.getString("address"));
			restaurant.setDescription(rs.getString("description"));
			restaurant.setTelephone(rs.getString("telephone"));
			restaurant.setCity(rs.getString("city"));
			restaurant.setIdu(rs.getInt("idu"));
			restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
			restaurant.setMinPrice(rs.getFloat("minPrice"));
			restaurant.setContactEmail(rs.getString("contactemail"));
			restaurant.setMaxPrice(rs.getFloat("maxPrice"));
			restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
			restaurant.setAvailable(rs.getInt("available"));
			restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
			restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
			restaurant.setImage(rs.getString("image"));

			logger.info("fetching restaurant: "+restaurant.getId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurant;
	}
	
	public List<Restaurant> getAll() {

		if (conn == null) return null;
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM restaurants");
			while ( rs.next() ) {
				Restaurant restaurant = new Restaurant();
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setDescription(rs.getString("description"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
				restaurant.setMinPrice(rs.getFloat("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getFloat("maxPrice"));
				restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
				restaurant.setAvailable(rs.getInt("available"));
				restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
				restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
				restaurant.setImage(rs.getString("image"));
				
				restaurants.add(restaurant);
				logger.info("fetching restaurant: "+restaurant.getId());							
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurants;
	}
	
	public List<Restaurant> getAllBySearchName(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurants WHERE UPPER(name) LIKE '%" + search + "%'");

			while (rs.next()) {
				Restaurant restaurant = new Restaurant();
				
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setDescription(rs.getString("description"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
				restaurant.setMinPrice(rs.getFloat("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getFloat("maxPrice"));
				restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
				restaurant.setAvailable(rs.getInt("available"));
				restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
				restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
				restaurant.setImage(rs.getString("image"));
				
				restaurants.add(restaurant);
				
				logger.info("fetching restaurant: "+restaurant.getId());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurants;
	}
	
	public List<Restaurant> getFavorites(long idu){
		
		if (conn == null) return null;
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM restaurants r INNER JOIN favorites f ON (r.id=f.idr) WHERE f.idu="+idu);
			while ( rs.next() ) {
				Restaurant restaurant = new Restaurant();
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setDescription(rs.getString("description"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
				restaurant.setMinPrice(rs.getFloat("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getFloat("maxPrice"));
				restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
				restaurant.setAvailable(rs.getInt("available"));
				restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
				restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
				restaurant.setImage(rs.getString("image"));
				
				restaurants.add(restaurant);
				logger.info("fetching restaurant: "+restaurant.getId());							
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurants;
	}
	
	public List<Restaurant> getAllBySearchFilter(String search, String order, String available, String category) {
		if (conn == null)
			return null;

		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			//Show all as default, where is added for fit the rest of concatenations with AND
			String query = "SELECT * FROM restaurants WHERE 1=1";
			if(search!=null) {
				logger.info("buscando: "+search);
				search = search.toUpperCase();
				query = query+" AND (UPPER(name) LIKE '%"+search+"%' OR UPPER(description) LIKE '%"+search+"%' OR UPPER(city) LIKE '%"+search+"%') ";
			}
			if(available!=null&&!available.equals("all")) {
				query = query+" AND available = "+available+" ";
			}
			if(category!=null&&!category.equals("all")) {
				query = query+" AND  id IN  (SELECT idr "
						+ "FROM restaurantCategories WHERE idct = (SELECT id FROM categories WHERE name = '"+category+"'))";
			}
			if(order!=null) {
				query = query + " ORDER BY "+order;
				if(order.equals("gradesAverage"))
					query = query + " DESC";
			}
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			logger.info("Applying filter... ");

			while (rs.next()) {
				Restaurant restaurant = new Restaurant();
				
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setDescription(rs.getString("description"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
				restaurant.setMinPrice(rs.getFloat("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getFloat("maxPrice"));
				restaurant.setBikeFriendly(rs.getInt("bikeFriendly"));
				restaurant.setAvailable(rs.getInt("available"));
				restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
				restaurant.setDeliveryPrice(rs.getFloat("deliveryPrice"));
				restaurant.setImage(rs.getString("image"));
				
				restaurants.add(restaurant);
				
				logger.info("fetching restaurant: "+restaurant.getId());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurants;
	}
	
	public List<Restaurant> getAllByUser(long idu) {
		
		if (conn == null)
			return null;

		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurants WHERE idu = "+ idu);

			while (rs.next()) {
				Restaurant restaurant = new Restaurant();
				restaurant  = new Restaurant();	 
				restaurant.setId(rs.getInt("id"));
				restaurant.setName(rs.getString("name"));
				restaurant.setDescription(rs.getString("description"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setTelephone(rs.getString("telephone"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setIdu(rs.getInt("idu"));
				restaurant.setGradesAverage(rs.getFloat("gradesAverage"));
				restaurant.setMinPrice(rs.getFloat("minPrice"));
				restaurant.setContactEmail(rs.getString("contactemail"));
				restaurant.setMaxPrice(rs.getFloat("maxPrice"));
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
	public long add(Restaurant restaurant) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			Statement stmt;
			
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='restaurants'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getInt("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO restaurants (name,description,address,telephone,idu,gradesAverage,city,minPrice,contactemail,maxPrice,bikeFriendly,available, deliveryTime, deliveryPrice, image) VALUES('"
									+restaurant.getName()+"','"+restaurant.getDescription()+"','" +restaurant.getAddress()+"','" + restaurant.getTelephone() + "'," 
									+ restaurant.getIdu() + "," + restaurant.getGradesAverage()+",'"+ restaurant.getCity() +"',"+ restaurant.getMinPrice() +",'" + restaurant.getContactEmail() + "'," 
									+ restaurant.getMaxPrice() + "," + restaurant.getBikeFriendly() +"," + restaurant.getAvailable()+"," + restaurant.getDeliveryTime()+"," + restaurant.getDeliveryPrice()+",'" + restaurant.getImage() + "')");
				
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='restaurants'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) return -1;
											
				logger.info("CREATING restaurant("+id+"): "+restaurant.getName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return id;
	}

	@Override
	public boolean update(Restaurant restaurant) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE restaurants SET name='"+restaurant.getName()
				+"', description='"+restaurant.getDescription()
				+"', address='"+restaurant.getAddress()
				+"', telephone='"+restaurant.getTelephone()
				+"', idu="+restaurant.getIdu()
				+", gradesAverage="+restaurant.getGradesAverage()
				+", city='"+restaurant.getCity()
				+"', minPrice="+restaurant.getMinPrice()
				+", contactemail='"+restaurant.getContactEmail()				
				+"', maxPrice="+restaurant.getMaxPrice()
				+", bikeFriendly="+restaurant.getBikeFriendly()
				+", available="+restaurant.getAvailable()
				+", deliveryTime="+restaurant.getDeliveryTime()
				+", deliveryPrice="+restaurant.getDeliveryPrice()
				+", image='"+restaurant.getImage()
				+"' WHERE id = "+restaurant.getId());
				logger.info("updating restaurant: "+restaurant.getId());
						
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;

	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM restaurants WHERE id ="+id);
				logger.info("deleting restaurant: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
