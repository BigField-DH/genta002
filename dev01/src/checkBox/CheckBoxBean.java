package checkBox;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;

public class CheckBoxBean {

	private final String mKey  = "CB_ListCount";

	public CheckBoxBean(){ count = Base.bPrseInt(Base.bGetPropValue(mKey), 0); }
	public CheckBoxBean(HttpServletRequest req){
		count = Base.bGetPrmCount(req, mKey); //������������ (��ʐݒ�l > �v���p�e�B�l > 0)
		subList = new ArrayList<>();
	}

	private List<Object[]> subList;
	public List<Object[]> getSubList() { return subList; }
	protected <T> void setSubList(T lpId, boolean notSim, boolean checked) {
		subList.add( new Object[] {lpId, (notSim) ? "��" : "", checked} );
	}

	private String lpIdsCsv; //��ʐ���p��LPID�̃��X�g(CSV�`��)
	public String getLpIdsCsv(){ return lpIdsCsv; }

	protected void setCsvForJsp(List<String> pLpIds){
		lpIdsCsv = Base.bMakeCsv(pLpIds, false);
		inSelected = lpIdsCsv;
	}

	private int count;
	public int getCount() { return count; }

	private String inSelected = "106,108"; //��ʕ\���p��LPID�̃��X�g(CSV�`��)
	public String getInSelected() { return inSelected; }

	private String inNotSim = "102,109"; 
	public String getInNotSim() { return inNotSim; }
	protected void setInNotSim(List<String> pNotSims) { inNotSim = Base.bMakeCsv(pNotSims, false); }
	protected void setInNotSim(boolean clrSim) { if(clrSim) inNotSim = ""; }
}
