package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Cart;
import es.unex.pi.model.User;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet.do")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Handling GET (SignUpServlet.do)");
		
		//Show Index.jsp
		HttpSession session = request.getSession();
		
		session.setAttribute("errorSignUp", false);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SignUp.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Handling POST (SignUpServlet.do)");
		boolean abort = false;
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		if (conn != null) {
			HttpSession session = request.getSession();
		
			UserDAO users = new JDBCUserDAOImpl();
			users.setConnection(conn);
			
			if(users.validateEmail(request.getParameter("email"))) {
				User user = new User();
				user.setName(request.getParameter("name"));
				user.setSurname(request.getParameter("surname"));
				user.setEmail(request.getParameter("email"));
				user.setPassword(request.getParameter("password"));
				
				if(user.validatePassword()) {
					long id = users.add(user);
					
					user.setId(id);
					session.setAttribute("user", user);
					
					Cart cart = new Cart();
					session.setAttribute("cart", cart);
					
					response.sendRedirect("pages/index.html");
				}else {
					abort = true;
					request.setAttribute("message", "Password must be at least 8 digits including one number, one uppercase, one lowercase, and one alphanumeric character");
				}
			}
			else {
				abort = true;
				request.setAttribute("message", "The email is already registered");
			}
			if(abort==true) {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SignUp.jsp");
				view.forward(request, response);
			}
		}
	}

}
