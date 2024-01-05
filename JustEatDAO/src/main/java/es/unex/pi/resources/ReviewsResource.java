package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
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

@Path("/reviews")
public class ReviewsResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviews(@PathParam("idr") long idr, @Context HttpServletRequest request){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			if(restaurantDAO.get(idr)!=null) {
				ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
				reviewsDAO.setConnection(conn);
				return reviewsDAO.getAllByRestaurant(idr);
			}
		}return null;
	}
	
	@GET
	@Path("/getReview/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Review getReview(@PathParam("idr") long idr, @Context HttpServletRequest request){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
			reviewsDAO.setConnection(conn);
			
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			return reviewsDAO.get(idr, user.getId());
		}else 
			throw new CustomBadRequestException("La conexión con la bd no es correcta");
	}
	
	@POST
	@Path("/{idr: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postReview(Review newReview, @PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
			reviewsDAO.setConnection(conn);
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			
			if(restaurantDAO.get(idr)!=null) {
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				
				newReview.setIdu(user.getId());
				newReview.setIdr(idr);
				
				reviewsDAO.add(newReview);
				
				Response res = Response
						.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(1)).build())
						.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(1)).build()).build();
				return res;
			}
			else throw new CustomBadRequestException("The restaurant ("+idr+") is not found");
			
		}
		else return Response.serverError().build();
	}
	
	@PUT
	@Path("/{idr: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putReview(Review newReview, @PathParam("idr") long idr, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
			reviewsDAO.setConnection(conn);
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			
			if(restaurantDAO.get(idr)!=null) {
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				
				if(newReview.getIdu()==user.getId()) {
					if(reviewsDAO.update(newReview))
						return Response.noContent().build();
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The user is not the review's author");
				
			}else throw new CustomBadRequestException("The restaurant ("+idr+") is not found");
		}
		else return Response.serverError().build();
	}
}
