package es.unex.pi.resources;

import java.sql.Connection;

import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Cart;
import es.unex.pi.model.User;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/users")
public class UsersResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		return user; 
	}
	@GET
	@Path("/{idu: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("idu") long idu) {
		User user = new User();
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn!=null) {
			UserDAO userDAO = new JDBCUserDAOImpl();
			userDAO.setConnection(conn);
			
			User completeUser = userDAO.get(idu);
			if(completeUser != null)
				user.setName(completeUser.getName());
		}
		return user;
	}
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    
	    User user = userDAO.validateLogin(formParams.getFirst("email"), formParams.getFirst("password"));
	    
	    if (user != null) {
	    	request.getSession().setAttribute("user", user);
	    	return Response.ok().build();
	    } else {
	        throw new CustomBadRequestException("The user credentials are not correct");
	    }
	}
	
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {
	    Connection conn = (Connection) sc.getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    
	    if(userDAO.validateEmail(formParams.getFirst("email"))){
	    
		    // Crear un nuevo usuario
		    User user = new User();
		    user.setEmail(formParams.getFirst("email"));
		    user.setPassword(formParams.getFirst("password"));
		    user.setName(formParams.getFirst("name"));
		    user.setSurname(formParams.getFirst("surname"));
		    
		    // Guardar el nuevo usuario
		    long idu = userDAO.add(user);
		    user.setId(idu);
		    request.getSession().setAttribute("user", user);
		    
		    Cart cart = new Cart();
		    request.getSession().setAttribute("cart", cart);
		    
		    Response res =  Response
					.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(idu)).build()).contentLocation(
							uriInfo.getAbsolutePathBuilder().path(Long.toString(idu)).build())
					.build();
		    return res;
	    }
	    else throw new CustomBadRequestException("The email is already registered");
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request) {
		
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null) {
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("cart");
				return Response.ok().build();
		}
		else return Response.serverError().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(User newUser, @Context HttpServletRequest request)
			throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		if(conn != null) {
			userDAO.setConnection(conn);
			
			User user = (User) request.getSession().getAttribute("user");
			String email = newUser.getEmail();
			
			if(userDAO.validateEmail(email)||email.equals(user.getEmail())) {
				request.getSession().setAttribute("user", newUser);
				if(userDAO.update(newUser))
					return Response.noContent().build();
				else
					return Response.serverError().build();
			}
			else
				throw new CustomBadRequestException("The email is not valid");
		}else
			return Response.serverError().build();
	}
	@DELETE
	public Response deleteUser(@Context HttpServletRequest request) {
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		User user = (User) request.getSession().getAttribute("user");
		userDAO.delete(user.getId());
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("cart");
		return Response.noContent().build();
	}
}
