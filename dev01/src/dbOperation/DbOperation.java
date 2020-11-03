package dbOperation;

import javax.servlet.annotation.WebServlet;
import base.Base;
import base.Const;

@WebServlet(Base.srvltDBO)
public class DbOperation extends Base {
	private static final long serialVersionUID = 1L;

	public DbOperation() {}

    @Override
	protected void doProcess() {
		Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new DbOperationBean()); return; }

		DbOperationBean bean = (DbOperationBean)bSession.getAttribute(bAttrBean);

		if(Const.actUpdate.equals(bAction) && bean == null) {
			Base.bPutError("セッション無効");
			bReq.setAttribute(Base.cAttrBaseMsg, "セッション無効");
			bSession.setAttribute(bAttrBean, new DbOperationBean()); return;
		}
		if(bean == null) bean = new DbOperationBean();

		DbOperationService svc = new DbOperationService(bReq);
		svc.execSQL(bean, bAction);
		bSession.setAttribute(bAttrBean, bean); //SESSIONに保存
    }
}
