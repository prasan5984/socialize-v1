package src.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import src.helpers.SocializeHelper;

/**
 * Application Lifecycle Listener implementation class ShutdownListener
 *
 */
@WebListener
public class ShutdownListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ShutdownListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        SocializeHelper.shutdownSerializor();
    }
	
}
