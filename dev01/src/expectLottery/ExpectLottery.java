package expectLottery;

import java.security.NoSuchAlgorithmException;
import javax.servlet.annotation.WebServlet;
import base.Base;

@WebServlet(Base.srvltEL)
public class ExpectLottery extends Base {
	private static final long serialVersionUID = 1L;

	public ExpectLottery() {}

    @Override
	protected void doProcess() {
		Base.bPutLog("");

		if(bIsShow()) { bSession.setAttribute(bAttrBean, new ExpectLotteryBean()); return; }

		try {
			ExpectLotteryBean bean = new ExpectLotteryBean(bReq);
			ExpectLotteryService svc = new ExpectLotteryService(bean);

			if(bean.isToto()) {
				svc.expectToto(); //Toto �\�z�擾
			} else {
				svc.expectLoto(); //Loto �\�z�擾
			}
			bSession.setAttribute(bAttrBean, bean); //�����������̂�

		} catch (NumberFormatException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			Base.bSetBaseMsg(bReq, Base.bGetMessage(e));
		}
	}
}
