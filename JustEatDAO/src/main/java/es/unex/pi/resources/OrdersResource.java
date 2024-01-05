package es.unex.pi.resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.JDBCOrderDishesDAOImpl;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.OrderDishesDAO;
import es.unex.pi.model.Cart;
import es.unex.pi.model.CartLine;
import es.unex.pi.model.Order;
import es.unex.pi.model.OrderDishes;
import es.unex.pi.model.User;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/orders")
public class OrdersResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrders(@Context HttpServletRequest request){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			OrderDAO orderDAO = new JDBCOrderDAOImpl();
			orderDAO.setConnection(conn);
			
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			List<Order> orders = orderDAO.getAllByIdu(user.getId());
			
			return orders;
		}
		else return null;
	}
}
