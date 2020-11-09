package base;

public final class Const {

	public Const() {}

	//◆ Constants ◆//

	private static final String cntxt = "/dev01"; //◆ContextRoot

	protected static final String cntxtJspIndex = cntxt + "/jsp/Index.jsp"; //◆Common File

	public static final String prmAction    = "action";  //◆Request Parameters
	public static final String prmCount     = "Count";
	public static final String prmItemRD    = "itemRD";
	public static final String prmSortRD1   = "sortRD1";
	public static final String prmSortRD2   = "sortRD2";
	public static final String prmItemCB    = "itemCB";
	public static final String prmCbox      = "cbox";
	public static final String prmInStr     = "inStr";
	public static final String prmNumToCalc = "numToCalc";
	public static final String prmCondCol1  = "condCol1";
	public static final String prmCondCol2f = "condCol2f";
	public static final String prmCondCol2t = "condCol2t";
	public static final String prmCondCol3f = "condCol3f";
	public static final String prmCondCol3t = "condCol3t";
	public static final String prmInsCol1   = "insCol1";
	public static final String prmInsCol2   = "insCol2";
	public static final String prmInsCol3   = "insCol3";
	public static final String prmUpdCol1   = "updCol1";
	public static final String prmUpdCol2   = "updCol2";
	public static final String prmUpdCol3   = "updCol3";

	public static final String actShow    = "show";   //◆Actions
	public static final String actCreate  = "Create";
	public static final String actClear   = "Clear";
	public static final String actConvert = "Convert";
	public static final String actExpect  = "Expect";
	public static final String actCalc    = "Calc";
	public static final String actSearch  = "Search";
	public static final String actInsert  = "Insert";
	public static final String actUpdate  = "Update";
	public static final String actDelete  = "Delete";

	public static final int rd0Mrs      = 0;  //◆prmItemRD Variation
	public static final int rd1Rvrs     = 1;
	public static final int rd2MrsToStr = 2;
	public static final int rd3StrToMrs = 3;
	public static final String rdSingle = "single";
	public static final String rdRange  = "range";
	public static final String rdToto   = "Toto";
	public static final String rdLoto   = "Loto";

	public static final String cbSim   = "SIM";   //◆prmItemCB Variation
	public static final String cbCheck = "CHECK";

	public static final int match1Win   = 1; //◆Match Result
	public static final int match2Lose  = 2;
	public static final int match0Other = 0;

	//◆ Getters (used in JSP as Constant) ◆//
	public String getCntxtTM()  { return cntxt + Base.srvltTM; }
	public String getCntxtCB()  { return cntxt + Base.srvltCB; }
	public String getCntxtSC()  { return cntxt + Base.srvltSC; }
	public String getCntxtEL()  { return cntxt + Base.srvltEL; }
	public String getCntxtMP()  { return cntxt + Base.srvltMP; }
	public String getCntxtDBO() { return cntxt + Base.srvltDBO; }
	public String getCntxtDmy() { return cntxt + Base.srvltDmy; }

	public String getCntxtJsBase()   { return cntxt + "/js/Base.js"; }
	public String getCntxtFavicon()  { return cntxt + "/icon/favicon.ico"; }
	public String getCntxtCssBase()  { return cntxt + "/css/Base.css"; }
	public String getCntxtJspIndex() { return cntxtJspIndex; }

	public String getPrmAction()    { return prmAction; }
	public String getPrmCount()     { return prmCount; }
	public String getPrmItemRD()    { return prmItemRD; }
	public String getPrmSortRD1()   { return prmSortRD1; }
	public String getPrmSortRD2()   { return prmSortRD2; }
	public String getPrmItemCB()    { return prmItemCB; }
	public String getPrmCbox()      { return prmCbox; }
	public String getPrmInStr()     { return prmInStr; }
	public String getPrmNumToCalc() { return prmNumToCalc; }
	public String getPrmCondCol1()  { return prmCondCol1; }
	public String getPrmCondCol2f() { return prmCondCol2f; }
	public String getPrmCondCol2t() { return prmCondCol2t; }
	public String getPrmCondCol3f() { return prmCondCol3f; }
	public String getPrmCondCol3t() { return prmCondCol3t; }
	public String getPrmInsCol1()   { return prmInsCol1; }
	public String getPrmInsCol2()   { return prmInsCol2; }
	public String getPrmInsCol3()   { return prmInsCol3; }
	public String getPrmUpdCol1()   { return prmUpdCol1; }
	public String getPrmUpdCol2()   { return prmUpdCol2; }
	public String getPrmUpdCol3()   { return prmUpdCol3; }

	public String getActShow()    { return actShow; }
	public String getActCreate()  { return actCreate; }
	public String getActClear()   { return actClear; }
	public String getActConvert() { return actConvert; }
	public String getActSearch()  { return actSearch; }
	public String getActInsert()  { return actInsert; }
	public String getActUpdate()  { return actUpdate; }
	public String getActDelete()  { return actDelete; }

	public int getRd0Mrs()      { return rd0Mrs; }
	public int getRd1Rvrs()     { return rd1Rvrs; }
	public int getRd2MrsToStr() { return rd2MrsToStr; }
	public int getRd3StrToMrs() { return rd3StrToMrs; }
	public String getRdSingle() { return rdSingle; }
	public String getRdRange()  { return rdRange; }
	public String getRdToto()   { return rdToto; }
	public String getRdLoto()   { return rdLoto; }

	public String getCbSim()   { return cbSim; }
	public String getCbCheck() { return cbCheck; }

	public int getMatch1Win()   { return match1Win; }
	public int getMatch2Lose()  { return match2Lose; }
	public int getMatch0Other() { return match0Other; }
}
