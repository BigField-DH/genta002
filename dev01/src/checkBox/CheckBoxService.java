package checkBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class CheckBoxService {

	private HttpServletRequest mReq;

	public CheckBoxService(HttpServletRequest pReq) {
		mReq = pReq;
	}

	protected boolean createTable(CheckBoxBean bean) {
		Base.bPutLog("");

		int count = bean.getCount();

		List<String> lpIds   = Arrays.asList(Base.bGetParam("Selected", mReq).split(",")); //List of LPIDs checked
		List<String> notSims = Arrays.asList(Base.bGetParam("notSIM",   mReq).split(",")); //List of LPIDs not Simulated

		List<String> lpIdsForJsp = new ArrayList<>();
		List<String> notSimsForJsp = new ArrayList<>();

		for(int i=0; i<count; i++){
			String num = String.valueOf(i + 101); //LPID for List
			
			boolean notSim = false;
			if(notSims.contains(num)) {
				notSim = true;   //true when not Simulated
				notSimsForJsp.add(num);
			}

			boolean checked = false;
			if(!notSim && lpIds.contains(num)) {
				checked = true;      //true when Simulated & Checked
				lpIdsForJsp.add(num); //to add LPID for JSP
			}
			bean.setSubList(num, notSim, checked);
		}
		bean.setCsvForJsp(lpIdsForJsp);
		bean.setInNotSim(notSimsForJsp);
		return true;
	}

	protected boolean clear(CheckBoxBean bean) {
		Base.bPutLog("");

		List<String> cbClrList = Arrays.asList(Base.bGetParamMap(Const.prmItemCB, mReq)); //クリア対象(SIM,CHECK)
		boolean clrSim = cbClrList.contains(Const.cbSim);     //SIM選択時 TRUE
		boolean clrCheck = cbClrList.contains(Const.cbCheck); //CHECK選択時 TRUE

		//List of LPIDs checked
		List<String> checkedLpIds = (!clrCheck) ? Arrays.asList(Base.bGetParamMap(Const.prmCbox, mReq)) : null;

		List<Object[]> subList = bean.getSubList(); //画面の明細を取得
		for(int i=0; i<subList.size(); i++){
			Object[] oneRow = subList.get(i);
			//oneRow[0] は lpId
			if(clrSim) oneRow[1] = ""; //SIM再評価
			oneRow[2] = clrCheck ? false : checkedLpIds.contains(oneRow[0]); //CHECK再評価
			subList.set( i, oneRow );
		}
		bean.setCsvForJsp(checkedLpIds);
		bean.setInNotSim(clrSim);
		return true;
	}
}
