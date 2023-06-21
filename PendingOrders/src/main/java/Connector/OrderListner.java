package Connector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class OrderListner
 *
 */
public class OrderListner implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public OrderListner() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	System.out.println("Servlet Destroyed");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	OrderConnector ob = new OrderConnector();
    	ServletContext context = sce.getServletContext();
    	context.setAttribute("DB",ob);
    	System.out.println("Servlet Started");
    }
	
}
