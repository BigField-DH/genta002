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

		if(bean == null) {
			Base.bPutError("�Z�b�V��������");
			Base.bSetBaseMsg(bReq, "�Z�b�V��������");
			bean = new DbOperationBean();

			if(Const.actUpdate.equals(bAction)) { bSession.setAttribute(bAttrBean, bean); return; } //��Search�ȊO�ɂ�����
		}

		DbOperationService svc = new DbOperationService();
		bean.setReqInfo(bReq, bAction);
		svc.execSQL(bean);
		bSession.setAttribute(bAttrBean, bean); //SESSION�ɕۑ�
    }
}
