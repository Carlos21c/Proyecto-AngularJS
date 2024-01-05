 package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Dish;
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

@Path("/dishes")
public class DishesResources {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dish> getDishesBy(@QueryParam("idr") Long idr, @QueryParam("ido") Long ido) {
		List<Dish> dishes = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		dishDAO.setConnection(conn);
		if (conn!=null) {
			if (idr != null && ido == null) {
				dishes = dishDAO.getAllByIdr(idr);
			} else if (ido != null && idr == null) {
				dishes = dishDAO.getAllByIdo(ido);
			}
			return dishes;
		}
		else return null;
	}
	
	@GET
	@Path("/byRestaurant/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dish> getDishesByRestaurant(@PathParam("idr") long idr, @Context HttpServletRequest request) {
		List<Dish> dishes = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		
		if (conn!=null) {
			dishDAO.setConnection(conn);
			User user = (User) request.getSession().getAttribute("user");
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			Restaurant res = restaurantDAO.get(idr);
			if(res != null) {
				if(user.getId()==res.getIdu()) {
					 return dishDAO.getAllByIdr(idr);
				}
				else throw new CustomBadRequestException("The user is not the dish's owner");
			}
			else
				throw new CustomNotFoundException("Restaurant (" + idr + ") is not found");
			
		}
		return dishes;
	}
	@GET
	@Path("/{idd: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Dish getDish(@PathParam("idd") long idd, @Context HttpServletRequest request) {
		Dish dish = null;
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		
		if(conn!=null){
			
			User user = (User) request.getSession().getAttribute("user");
			restaurantDAO.setConnection(conn);
			dishDAO.setConnection(conn);
			dish = dishDAO.get(idd);
			if(dish!=null) {
				Restaurant res = restaurantDAO.get(dish.getIdr());
				if(res.getIdu()== user.getId()) {
					return dish;
				}
				else
					throw new CustomNotFoundException("Restaurant (" + res.getId() + ") is not found");
			}
			else
				throw new CustomNotFoundException("Dish (" + idd + ") is not found");
		}
		else
			return null;
	}
	
	@POST
	@Path("/{idr: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postDish(Dish newDish, @PathParam("idr") long idr, @Context HttpServletRequest request) {
		
		Connection conn = (Connection)sc.getAttribute("dbConn");
		if(conn != null) {
			DishDAO dishDAO = new JDBCDishDAOImpl();
			dishDAO.setConnection(conn);
			
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			Restaurant restaurant = restaurantDAO.get(idr);
			
			User user = (User)request.getSession().getAttribute("user");
			
			if(restaurant!=null) {
				if(restaurant.getIdu()==user.getId()) {
					newDish.setIdr(idr);
						
					long id = dishDAO.add(newDish);
					
					Response res = Response
							.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
							.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
					return res;
				}else throw new CustomBadRequestException("The user is not the restaurant's owner");
			}else throw new CustomNotFoundException("The restaurant ("+idr+") does not exist");
		}
		else
			return Response.serverError().build();
	}
	
	@PUT
	@Path("/{idd: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putDish(Dish newDish, @PathParam("idd") long idd, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(conn != null) {
			DishDAO dishDAO = new JDBCDishDAOImpl();
			dishDAO.setConnection(conn);
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			
			Dish dish = dishDAO.get(newDish.getId());
			if(dish!=null) {
				Restaurant res = restaurantDAO.get(dish.getIdr());
				if(user.getId()==res.getIdu()) {
					if(dishDAO.update(newDish))
						return Response.noContent().build();
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The user is not the dish's owner");
			}
			else
				throw new CustomNotFoundException("Dish (" + idd + ") is not found");
		}
		else
			return Response.serverError().build();
	}
	
	@DELETE
	@Path("/{idd: [0-9]+}")
	public Response deleteOrder(@PathParam("idd") long idd, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		if(conn != null) {
			DishDAO dishDAO = new JDBCDishDAOImpl();
			dishDAO.setConnection(conn);
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			User user = (User)request.getSession().getAttribute("user");
			Dish dish = dishDAO.get(idd);
			if(dish != null) {
				Restaurant res = restaurantDAO.get(dish.getIdr());
				if(user.getId()==res.getIdu()) {
					if(dishDAO.delete(idd))
						return Response.noContent().build();
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The user is not the dish's owner");
			}
			else
				throw new CustomNotFoundException("Dish (" + idd + ") is not found");
		}
		else
			return Response.serverError().build();
	}
}