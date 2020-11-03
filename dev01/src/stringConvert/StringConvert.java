package stringConvert;

import javax.servlet.annotation.WebServlet;
import base.Base;

@WebServlet(Base.srvltSC)
public class StringConvert extends Base {
	private static final long serialVersionUID = 1L;

	public StringConvert() {}

	@Override
	protected void doProcess() {
		Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new StringConvertBean()); return; }

		StringConvertBean bean = new StringConvertBean(bReq);
		StringConvertService svc = new StringConvertService(bean, bReq);

		if(bean.isMorse()) {
			svc.morseConvert(); //2:Morse to String / 3:String to Morse
		} else {
			svc.reverse(); //1:Reverse
		}
		bSession.setAttribute(bAttrBean, bean); //•Û‘¶
	}
}
