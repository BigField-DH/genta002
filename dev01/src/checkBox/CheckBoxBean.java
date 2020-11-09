package checkBox;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class CheckBoxBean {

	public CheckBoxBean(){}
	public CheckBoxBean(HttpServletRequest req){
		count = Base.bParseInt( Base.bGetParam(Const.prmCount, req), Base.pCbCount );
		subList = new ArrayList<>();
	}

	private List<Object[]> subList;
	public List<Object[]> getSubList() { return subList; }
	protected <T> void setSubList(T lpId, boolean notSim, boolean checked) {
		subList.add( new Object[] {lpId, (notSim) ? "●" : "", checked} );
	}

	private String lpIdsCsv; //画面制御用のLPIDのリスト(CSV形式)
	public String getLpIdsCsv(){ return lpIdsCsv; }

	protected void setCsvForJsp(List<String> pLpIds){
		lpIdsCsv = Base.bMakeCsv(pLpIds, false);
		inSelected = lpIdsCsv;
	}

	private int count = Base.pCbCount;
	public int getCount() { return count; }

	private String inSelected = "106,108"; //画面表示用のLPIDのリスト(CSV形式)
	public String getInSelected() { return inSelected; }

	private String inNotSim = "102,109"; 
	public String getInNotSim() { return inNotSim; }
	protected void setInNotSim(List<String> pNotSims) { inNotSim = Base.bMakeCsv(pNotSims, false); }
	protected void setInNotSim(boolean clrSim) { if(clrSim) inNotSim = ""; }
}
