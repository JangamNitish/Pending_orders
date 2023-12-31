package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import Connector.OrderConnector;
import Database.PendingOrdersData;
import OrdersPojo.OrdersPojo;

public class PendingOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PendingOrders() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson gson = new Gson();
		JSONObject obj = new JSONObject();
		PendingOrdersData Obj1 = new PendingOrdersData();
		ServletContext context = request.getServletContext();
		OrderConnector ob = (OrderConnector) context.getAttribute("DB");
		try {
			Connection con = ob.getConnection();

			OrdersPojo json = gson.fromJson(request.getReader(), OrdersPojo.class);
			PrintWriter out = response.getWriter();
			String str = Obj1.pendingOrders(json, con);
			response.setContentType("application/JSON");
			obj.put("message", str);
			out.println(obj);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
