package es.unex.pi.resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.JDBCOrderDishesDAOImpl;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.OrderDishesDAO;
import es.unex.pi.model.Cart;
import es.unex.pi.model.CartLine;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Order;
import es.unex.pi.model.OrderDishes;
import es.unex.pi.model.User;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/cart")
public class CartResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Cart getCart(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		return cart;
	}
	
	@POST
	public Response sendOrder(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			User user = (User) request.getSession().getAttribute("user");
			OrderDishesDAO orderDishes = new JDBCOrderDishesDAOImpl();
			orderDishes.setConnection(conn);
			
			OrderDAO orders = new JDBCOrderDAOImpl();
			orders.setConnection(conn);
			
			Order order = new Order();
			order.setIdu(user.getId());
			order.setTotalPrice(cart.getPrice());
			
			long ido = orders.add(order);
			
			HashMap<Long, CartLine> lines = cart.getLines();
			
			Iterator<Entry<Long, CartLine>> itLines = lines.entrySet().iterator();
			
			while(itLines.hasNext()) {
				Entry<Long, CartLine> line = itLines.next();
				//while(line.getValue().getAmount()>0) {
					OrderDishes orderdish = new OrderDishes();
					orderdish.setIddi(line.getValue().getDish().getId());
					orderdish.setIdo(ido);
					orderDishes.add(orderdish);
					//line.getValue().decAmount();
				//}
			}
			
			Cart newCart = new Cart();
			
			request.getSession().setAttribute("cart", newCart);
			
			Response res = Response
					.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(ido)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(ido)).build()).build();
			return res;
		}
		else return Response.serverError().build();
	}
	
	@POST
	@Path("/add/{idd: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToCart(@PathParam("idd") long idd, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(conn != null && cart != null) {
			DishDAO dishDAO = new JDBCDishDAOImpl();
			dishDAO.setConnection(conn);
			
			Dish dish = dishDAO.get(idd);
			if (dish != null) {
				cart.addToCart(dish);
				session.setAttribute("cart", cart);
				return Response.ok().build();
			}
			else
				throw new NotFoundException("The dish ("+ idd+") not exist");
		}
		else
			return Response.serverError().build();
	}
	
	@DELETE
	@Path("/delete/{idd: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delteFromCart(@PathParam("idd") long idd, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(conn != null && cart != null) {
			DishDAO dishDAO = new JDBCDishDAOImpl();
			dishDAO.setConnection(conn);
			
			Dish dish = dishDAO.get(idd);
			if (dish != null) {
				cart.deleteFromCart(idd);
				session.setAttribute("cart", cart);
				return Response.ok().build();
			}
			else
				throw new NotFoundException("The dish ("+ idd+") not exist");
		}
		else
			return Response.serverError().build();
	}
}
