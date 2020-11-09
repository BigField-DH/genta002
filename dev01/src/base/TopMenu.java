package base;

import javax.servlet.annotation.WebServlet;

@WebServlet(Base.srvltTM)
public class TopMenu extends Base {
	private static final long serialVersionUID = 1L;

	public TopMenu() {}

	@Override
	protected void doProcess() throws RuntimeException {
		Base.bPutLog("");
		if("POST".equals(bReq.getMethod())) Base.bGetPropMap(); //プロパティ値の一括取得
	}
}
