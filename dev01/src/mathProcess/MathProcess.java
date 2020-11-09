package mathProcess;

import javax.servlet.annotation.WebServlet;
import base.Base;

@WebServlet(Base.srvltMP)
public class MathProcess extends Base {
	private static final long serialVersionUID = 1L;

	public MathProcess() {}

    @Override
	protected void doProcess() {
		Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new MathProcessBean()); return; }

		MathProcessBean bean = (MathProcessBean)bSession.getAttribute(bAttrBean);
		if(bean == null) {
			Base.bPutError("セッション無効");
			Base.bSetBaseMsg(bReq, "セッション無効");
			bSession.setAttribute(bAttrBean, new MathProcessBean()); return;
		}

		bean.getParamValues(bReq);
		MathProcessService svc = new MathProcessService(bean);

		if(bean.isRange()) {
			svc.calcRange();  //範囲判定
		} else {
			svc.calcSingle(); //単独判定
		}
		bSession.setAttribute(bAttrBean, bean); //保存
	}
}
