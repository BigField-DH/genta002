package stringConvert;

import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class StringConvertBean {

	public StringConvertBean() {}
	public StringConvertBean(HttpServletRequest req) {
		String inStrPrm = Base.bGetParam(Const.prmInStr, req); //Input String
		inStr = inStrPrm.replaceAll("\r\n", "\\\\r\\\\n"); //to display
		inStrPg = inStrPrm.replaceAll("[\\s\\h]", " "); //for Process below
		itemRD = Base.bPrseInt(Base.bGetParam(Const.prmItemRD, req), Const.rd1Rvrs); //Reverse or Morse(2,3)
	}

	private String outStr;
	public String getOutStr() { return outStr; }
	protected void setOutStr(StringBuilder pOutStr) { outStr = pOutStr.toString().trim(); }
	protected void setOutStr(String pOutStr) { outStr = pOutStr.trim(); }

	private String inStr;
	public String getInStr() { return inStr; }
	private String inStrPg;
	protected String getInStrPg() { return inStrPg; }
	

	private int itemRD = Const.rd1Rvrs;
	public int getItemRD() { return itemRD; }
	protected boolean isMorse() { return itemRD == Const.rd2MrsToStr || itemRD == Const.rd3StrToMrs; }
}
