package initialize;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import base.Base;
import base.Const;

@WebListener
public final class Initialize implements ServletContextListener {

	public Initialize() {}

	public void contextInitialized(ServletContextEvent ev) {

		System.out.println("\nR@ΝΆίάΑΉ₯@\n");

		Base.bPutLog("");
		Base.bGetPropMap(); //vpeBlΜκζΎ

		//applicationXR[vΙAT[oΖ―Άθπέθ
		ev.getServletContext().setAttribute("C", new Const());

		System.out.print("\n");
	}

	public void contextDestroyed(ServletContextEvent ev) {
		System.out.println("\nR@ΒXAͺηͺη@\n");
	}
}
