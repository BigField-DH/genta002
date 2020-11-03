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

		System.out.println("\n�R�������������������@�͂��߂܂������@������������������\n");

		Base.bPutLog("");

		//application�X�R�[�v�ɁA�T�[�o�Ɠ����萔��ݒ�
		ev.getServletContext().setAttribute("C", new Const());

		System.out.print("\n");
	}

	public void contextDestroyed(ServletContextEvent ev) {
		System.out.println("\n�R�������������������@�X�A���炪��@������������������\n");
	}
}
