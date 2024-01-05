package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.model.Category;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
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
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/categories")
public class CategoriesResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			return categoryDAO.getAll();
		}
		else return null;
	}
	
	@GET
	@Path("/restaurant/{idr: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategoriesByRestaurant(@PathParam("idr") long idr){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			return categoryDAO.getAllByRestaurant(idr);
		}
		else return null;
	}
	
	@GET
	@Path("/{idc: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategory(@PathParam("idc") long idc) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			
			Category cat = categoryDAO.get(idc);

			return cat;
		}
		else {
			return null;
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCategory(Category newCategory, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			
			long id = categoryDAO.add(newCategory);
			
			Response res = Response
					.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
			return res;
		}
		else
			return Response.serverError().build();
	}
	
	@PUT
	@Path("/{idc: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putCategory(Category newCategory, @PathParam("idc") long idc, @Context HttpServletRequest request) throws Exception {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			
			Category category = categoryDAO.get(newCategory.getId());
			
			if(category != null) {
				if(categoryDAO.update(newCategory))
					return Response.noContent().build();
				else
					return Response.serverError().build();
			}
			else
				throw new CustomNotFoundException("Category (" + newCategory.getId() + ") is not found");
		}
		else
			return Response.serverError().build();
	}
	
	@DELETE
	@Path("/{idc: [0-9]+}")
	public Response deleteCategory(@PathParam("idc") long idc, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		if(conn != null) {
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			Category category = categoryDAO.get(idc);
			if(category != null) {
				if(!categoryDAO.isUsed(idc)) {
					if(categoryDAO.delete(idc))
						return Response.noContent().build();
					else
						return Response.serverError().build();
				}
				else
					throw new CustomBadRequestException("The category is in use by a restaurant");
			}
			else
				throw new CustomNotFoundException("Dish (" + idc + ") is not found");
		}
		else
			return Response.serverError().build();
	}
}
