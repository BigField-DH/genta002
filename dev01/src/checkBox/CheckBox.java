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

		if(Const.actClear.equals(bAction)) { //�N���A
			bean = (CheckBoxBean)bSession.getAttribute(bAttrBean);
			if(bean != null) {
				svc.clear(bean);
			} else {
				Base.bPutError("�Z�b�V��������");
				Base.bSetBaseMsg(bReq, "�Z�b�V��������");
				bean = new CheckBoxBean();
			}

		} else { //if(Const.actCreate.equals(bAction)) { //�ꗗ�\�쐬
			bean = new CheckBoxBean(bReq);
			svc.createTable(bean);
		}
		bSession.setAttribute(bAttrBean, bean);
	}
}
