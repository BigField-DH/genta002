package AtndMgt;

import javax.servlet.annotation.WebServlet;
import base.Base;
import base.Const;

@WebServlet(Base.srvltAM)
public class AtndMgt extends Base {
	private static final long serialVersionUID = 1L;

	public AtndMgt() {}

    @Override
	protected void doProcess() {
		Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new AtndMgtBean()); return; }

		AtndMgtBean bean = (AtndMgtBean)bSession.getAttribute(bAttrBean);

		if(bean == null) {
			Base.bPutError("セッション無効");
			Base.bSetBaseMsg(bReq, "セッション無効");
			bean = new AtndMgtBean();

			//if(Const.actUpdate.equals(bAction)) { bSession.setAttribute(bAttrBean, bean); return;
		}

		bean.setAction(bAction);
		AtndMgtService svc = new AtndMgtService();

		if(bean.isSearch()) {
			//int purpose = Integer.parseInt( Base.bGetParam(Const.prmItemRD, bReq) );
			bean.setRdPurpose( Integer.parseInt( Base.bGetParam(Const.prmItemRD, bReq) ) );
			if(bean.isContract()) {
				svc.procContr(bean, bReq);
			}
		}

		//bean.setReqInfo(bReq, bAction);
		//svc.execSQL(bean);
		bSession.setAttribute(bAttrBean, bean); //SESSIONに保存
    }
}
