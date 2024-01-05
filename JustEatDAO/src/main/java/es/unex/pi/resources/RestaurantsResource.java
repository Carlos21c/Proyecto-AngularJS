package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.FavoritesDAO;
import es.unex.pi.dao.JDBCFavoritesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/restaurants")
public class RestaurantsResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> searchRestaurant(@QueryParam("search") String search, @QueryParam("order") String order, 
            @QueryParam("available") String available, @QueryParam("category") String category, @Context HttpServletRequest request) {
		
		List<Restaurant> restaurants = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			
			restaurants = restaurantDAO.getAllBySearchFilter(search, order, available, category);
		}
		return restaurants;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> restaurantsByUser(@Context HttpServletRequest request){
		
		List<Restaurant> restaurants = null;
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			User user = (User) request.getSession().getAttribute("user");
			// 3. If the user is a Manager, return all the orders.
			// otherwise, return the orders of the current user
			restaurants = restaurantDAO.getAllByUser(user.getId());
		}
		return restaurants;
	}
	
	@GET
	@Path("/favorites")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant>getFavorites(@Context HttpServletRequest request){
		
		List<Restaurant> restaurants = null;
		Connection conn =(Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantsDAO = new JDBCRestaurantDAOImpl();
			restaurantsDAO.setConnection(conn);
			User user = (User)request.getSession().getAttribute("user");
			restaurants = restaurantsDAO.getFavorites(user.getId());
		}
		return restaurants;
	}
	
	@GET
	@Path("/isFavorite/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isFavorite(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		boolean fav = false;
		
		if(conn!=null) {
			FavoritesDAO favDAO = new JDBCFavoritesDAOImpl();
			favDAO.setConnection(conn);
			User user = (User)request.getSession().getAttribute("user");
			fav = favDAO.check(idr, user.getId());
		}
		return fav;
	}
	
	@GET
	@Path("/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurantJSON(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		
		Restaurant restaurant = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			User user = (User) request.getSession().getAttribute("user");
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			restaurant = restaurantDAO.get(idr);
			if(restaurant == null)
				throw new CustomNotFoundException("Restaurant (" + idr + ") is not found");
			if(restaurant.getIdu()!=user.getId())
				throw new CustomBadRequestException("The user is not the restaurant owner");
		}
		return restaurant;
	}
	
	@GET
	@Path("/public/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurantPublicJSON(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		
		Restaurant restaurant = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			User user = (User) request.getSession().getAttribute("user");
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			restaurant = restaurantDAO.get(idr);
			if(restaurant == null)
				throw new CustomNotFoundException("Restaurant (" + idr + ") is not found");
		}
		return restaurant;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postRestaurant(Restaurant newRestaurant, @Context HttpServletRequest request) throws Exception{
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(conn!=null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
			restaurantCategoriesDAO.setConnection(conn);
			
			newRestaurant.setIdu(user.getId());
			long id = restaurantDAO.add(newRestaurant);
			
			restaurantCategoriesDAO.addCategoriesRestaurant(newRestaurant.getList(), id);
			
			Response res = Response
					.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
			return res;
		}
		else 
			return Response.serverError().build();
	}
	
	
	
	@PUT
	@Path("/{idr: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putRestaurant(Restaurant newRestaurant, @PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
			restaurantCategoriesDAO.setConnection(conn);
			User user = (User)request.getSession().getAttribute("user");
			
			Restaurant restaurant = restaurantDAO.get(newRestaurant.getId());
			
			if(restaurant != null) {
				if(restaurant.getIdu() == user.getId()) {
					restaurantCategoriesDAO.updateCategoriesRestaurant(newRestaurant.getList(), restaurant.getId());
					if(restaurantDAO.update(newRestaurant)) {
							return Response.noContent().build();
					}
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The user is not the restaurant owner");
			}
			else
				throw new CustomNotFoundException("Restaurant (" + newRestaurant.getId() + ") is not found");
		}else
			return Response.serverError().build();
	}
	
	@DELETE
	@Path("/{idr: [0-9]+}")
	public Response deleteRestaurant(@PathParam("idr") long idr, @Context HttpServletRequest request){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			User user = (User)request.getSession().getAttribute("user");
			Restaurant rest = restaurantDAO.get(idr);
			if(rest!=null) {
				if(rest.getIdu() == user.getId()) {
					if(restaurantDAO.delete(idr))
						return Response.noContent().build();
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The user is not the restaurant's owner");
			}
			else 
				throw new CustomNotFoundException("Restaurant (" + idr + ") is not found");
		}
		else
			return Response.serverError().build();
	}
	
	@POST
	@Path("/addToFavorites/{idr: [0-9]+}")
	public Response addToFavorites(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if (conn != null) {
			FavoritesDAO favDAO = new JDBCFavoritesDAOImpl();
			favDAO.setConnection(conn);
			
			User user = (User)request.getSession().getAttribute("user");
			
			favDAO.add(idr, user.getId());
			
			return Response.ok().build();
		}
		return Response.serverError().build();
	}
	
	@DELETE
	@Path("/deleteFromFavorites/{idr: [0-9]+}")
	public Response deleteFromFavorites(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if (conn != null) {
			FavoritesDAO favDAO = new JDBCFavoritesDAOImpl();
			favDAO.setConnection(conn);
			
			User user = (User)request.getSession().getAttribute("user");
			
			favDAO.delete(idr, user.getId());
			
			return Response.ok().build();
		}
		return Response.serverError().build();
	}
}