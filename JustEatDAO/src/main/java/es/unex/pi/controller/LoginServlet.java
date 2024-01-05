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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Handling GET (LoginServlet.do)");
		
		//Show Index.jsp
		HttpSession session = request.getSession();
		
		session.setAttribute("errorLogin", false);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Handling POST (LoginServlet.do)");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		if (conn != null) {
			HttpSession session = request.getSession();
		
			UserDAO users = new JDBCUserDAOImpl();
			users.setConnection(conn);
			
			User user = users.validateLogin(request.getParameter("email"),request.getParameter("password"));
			
			if(user!=null) {
				session.setAttribute("user", user);
				Cart cart = new Cart();
				session.setAttribute("cart", cart);
				response.sendRedirect("pages/index.html");
				
			}
			else {
				session.setAttribute("errorLogin", true);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
				view.forward(request, response);
			}
		}
	}

}
