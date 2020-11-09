package checkBox;

import javax.servlet.annotation.WebServlet;
import base.Base;
import base.Const;

@WebServlet(Base.srvltCB)
public class CheckBox extends Base {
	private static final long serialVersionUID = 1L;

    public CheckBox() {}

	@Override
	protected void doProcess() {
    	Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new CheckBoxBean()); return; }

		CheckBoxBean bean;
		CheckBoxService svc = new CheckBoxService(bReq);

		if(Const.actClear.equals(bAction)) { //クリア
			bean = (CheckBoxBean)bSession.getAttribute(bAttrBean);
			if(bean != null) {
				svc.clear(bean);
			} else {
				Base.bPutError("セッション無効");
				Base.bSetBaseMsg(bReq, "セッション無効");
				bean = new CheckBoxBean();
			}

		} else { //if(Const.actCreate.equals(bAction)) { //一覧表作成
			bean = new CheckBoxBean(bReq);
			svc.createTable(bean);
		}
		bSession.setAttribute(bAttrBean, bean);
	}
}
