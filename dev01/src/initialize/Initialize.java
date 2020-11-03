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

		System.out.println("\n３◆◆◆◆◆◆◆◆◆　はじめまっせぇ　◆◆◆◆◆◆◆◆◆\n");

		Base.bPutLog("");

		//applicationスコープに、サーバと同じ定数を設定
		ev.getServletContext().setAttribute("C", new Const());

		System.out.print("\n");
	}

	public void contextDestroyed(ServletContextEvent ev) {
		System.out.println("\n３◆◆◆◆◆◆◆◆◆　閉店、がらがら　◆◆◆◆◆◆◆◆◆\n");
	}
}
