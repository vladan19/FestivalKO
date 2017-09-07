

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.etfbl.ip.festivalko.dao.UserDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;
import net.etfbl.ip.festivalko.model.User;
import net.etfbl.ip.festivalko.model.UserType;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDAO dao = DAOFactory.getInstance().getUserDAO();
		User u = new User();
		u.setFirstName("Vladan");
		u.setLastName("Stojnic");
		u.setJmbg("1906994100001");
		u.setEmail("vladanstojnic@gmail.com");
		u.setType(UserType.ADMINISTRATOR);
		u.setPictureUri("testPic");
		u.setUsername("admin");
		u.setPassword("pass");
		u = dao.save(u);
		User u1 = dao.findById(u.getPrimaryKey());
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<html><body>");
		if(u1.isActivated()==false) {
			if(u.getId() == u1.getId()) {
				pw.println("ok");
			}
			else {
				pw.println("id fail");
			}
		}
		else {
			pw.println("activation fail");
		}
		pw.println("</body></html>");
	}

}
