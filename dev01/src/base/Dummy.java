package base;

import javax.servlet.annotation.WebServlet;

@WebServlet(Base.srvltDmy)
public class Dummy extends Base {
	private static final long serialVersionUID = 1L;

	public Dummy() {}

	@Override
	protected void doProcess() throws RuntimeException {
		Base.bPutLog("");

		if("POST".equals(bReq.getMethod())) return;
		//bReq.setAttribute(Base.cAttrBaseMsg, "��_�~�[������");
		Base.bSetBaseMsg(bReq, "��");
		Base.bSetBaseMsg(bReq, "�_�~�[����");
		Base.bSetBaseMsg(bReq, "��");
		throw new RuntimeException("Dummy �� GET �� RuntimeException ����");
	}
}
