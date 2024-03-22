package test;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthenticationService authenticationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            String driver = config.getInitParameter("DB_driver");
            Class.forName(driver);
            String url = config.getInitParameter("DB_url");
            String DBusername = config.getInitParameter("DB_username");
            String DBpassword = config.getInitParameter("DB_password");

            // Initialize AuthenticationService here
            authenticationService = new AuthenticationService(url, DBusername, DBpassword);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to load JDBC driver", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = authenticationService.authenticate(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error_3.jsp");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect("noLoginCredentials.jsp");
        } catch (AuthenticationService.AuthenticationException e) {
            if (e.getMessage().equals("Invalid username")) {
                response.sendRedirect("error_1.jsp");
            } else if (e.getMessage().equals("Invalid password")) {
                response.sendRedirect("error_2.jsp");
            } else {
                response.sendRedirect("error_3.jsp");
            }
        } catch (IOException e) {
            response.sendRedirect("error404.jsp");
        }
    }
}
