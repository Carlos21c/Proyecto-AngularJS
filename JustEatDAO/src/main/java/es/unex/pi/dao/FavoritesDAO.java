package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Restaurant;



public interface FavoritesDAO {
	
	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Adds restaurantId and userId to the database.
	 * 
	 * @param restId, userId
	 *            restId of favorite restaurant and userId of user that liked.
	 * 
	 * @return Restaurant identifier or -1 in case the operation failed.
	 */
	
	public void add(long restId, long userId);
	
	/**
	 * Delete the restaurant of the database.
	 * 
	 * @param restId, userId
	 *            restId of favorite restaurant and userId of user that liked.
	 * 
	 * @return Restaurant identifier or -1 in case the operation failed.
	 */
	
	public boolean delete(long restId, long userId);
	
	
	/**
	 * Get the favourites restaurants of the user.
	 * 
	 * @param userId
	 *            userId of user
	 * 
	 * @return A list with all the restaurants that are the favs of the user.
	 */
	
	public List<Restaurant> getFavs(long userId);
	
	/**
	 * Check if a restaurant is added to fav of a user.
	 * 
	 * @param restId, userId
	 *            restId of favorite restaurant and userId of user that liked.
	 * 
	 * @return True if exists, false in other case.
	 */
	
	public boolean check(long restId, long userId);
	
	
}
