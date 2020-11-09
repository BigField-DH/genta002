package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Base extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected HttpServletRequest bReq;
	private   HttpServletResponse bRes;
	protected HttpSession bSession;
	private   String bSrvlt;
	private   String bAcro;
	protected String bAttrBean;
	protected String bAction;

	public Base() {}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		setInit(req, res, false); //初期設定
		bPutError("◆ " + bSrvlt + " ◆");
		if(cSrvltGettable.contains(bSrvlt)) {
			doProcess(); //個別処理
			bPutLog(bSrvlt + " : committed " + bRes.isCommitted() + "\n");
			bReq.setAttribute(cAttrBaseMsg, "Gettable");
			bReq.setAttribute(cAttrSrvlt, bSrvlt); //遷移先
			bReq.setAttribute(cAttrAcro, bAcro); //略称
			bReq.getServletContext().getRequestDispatcher(cJspBase).forward(bReq, bRes);
		} else {
			bPutError(Const.cntxtJspIndex +" : isCommitted " + bRes.isCommitted() + "\n");
			bSession.setAttribute(cAttrBaseMsg, "Ungettable");
			bRes.sendRedirect(Const.cntxtJspIndex);
		}
		//putError("◆ " + bSrvlt + " ◆ END\n");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String srvlt = srvltTM;
		try {
			setInit(req, res, true); //初期設定
			bPutLog("◆ " + bSrvlt + " ◆");

			if( !cActMap.get(bSrvlt).contains(bAction) ) { //許容Actionチェック
				bPutError("Invalid Action '" + bAction + "'");
				//bReq.setAttribute(cAttrBaseMsg, bSrvlt + " : Invalid Action '" + bAction + "'");
				bSetBaseMsg(bReq, bSrvlt + " : Invalid Action '" + bAction + "'");
				return;
			}
			doProcess(); //個別処理
			srvlt = bSrvlt; //遷移先

		} catch (RuntimeException e) {
			bPutError(e + "\n"); e.printStackTrace();
			//bReq.setAttribute(cAttrBaseMsg, bSrvlt + " : " + bGetMessage(e));
			bSetBaseMsg(bReq, bSrvlt + " : " + bGetMessage(e));
		} finally {
			bPutLog(srvlt + " : committed " + bRes.isCommitted() + "\n");
			bReq.setAttribute(cAttrAcro, srvlt); //略称
			bReq.setAttribute(cAttrSrvlt, srvlt); //遷移先
			bReq.getServletContext().getRequestDispatcher(cJspBase).forward(bReq, bRes);
			//putLog("◆ " + bSrvlt + " ◆ END\n");
		}
	}

	//◆初期設定
	private void setInit(HttpServletRequest req, HttpServletResponse res, boolean flg) throws UnsupportedEncodingException {
		bReq = req;
		bRes = res;

		if(flg) bReq.setCharacterEncoding(StandardCharsets.UTF_8.toString());

		bSrvlt = bReq.getServletPath();
		bAcro = cAcroMap.get(bSrvlt);
		bSession = bReq.getSession(true);

		if(flg) {
			bAttrBean = "oBean" + bAcro;
			bAction = bGetParam(Const.prmAction, bReq);
		}
		//throw new UnsupportedEncodingException("for Debug");
	}

	//◆ Base Method to be overridden in sub Classes ◆
	protected void doProcess() throws RuntimeException {
		bPutError("to be Overridden \n"); throw new RuntimeException("to be Overridden");
	}

	//◆ 初期表示時処理
	protected boolean bIsShow() {
		if(!Const.actShow.equals(bAction)) return false;
		//bReq.setAttribute(cAttrBaseMsg, Const.actShow + bAcro);
		bSetBaseMsg(bReq, Const.actShow + bAcro);
		return true;
	}

	//◆ Constants ◆//

	private static final String cJspBase   = "/jsp/Base.jsp"; //◆Common Page

	private static final String cAttrBaseMsg = "baseMsg";   //◆Attributes
	private static final String cAttrSrvlt   = "srvltPath";
	private static final String cAttrAcro    = "acro";

	protected static final String srvltTM  = "/TopMenu"; //◆Servlet Paths
	public    static final String srvltCB  = "/CheckBox";
	public    static final String srvltSC  = "/StringConvert";
	public    static final String srvltEL  = "/ExpectLottery";
	public    static final String srvltMP  = "/MathProcess";
	public    static final String srvltDBO = "/DbOperation";
	protected static final String srvltDmy = "/Dummy";

	private static final Map<String, List<String>> cActMap = new HashMap<>() { //◆Servlet - Action Mappings
		private static final long serialVersionUID = 1L;
	{
		put(srvltTM,  Arrays.asList(""));
		put(srvltCB,  Arrays.asList(Const.actShow, Const.actCreate, Const.actClear));
		put(srvltSC,  Arrays.asList(Const.actShow, Const.actConvert));
		put(srvltEL,  Arrays.asList(Const.actShow, Const.actExpect));
		put(srvltMP,  Arrays.asList(Const.actShow, Const.actCalc));
		put(srvltDBO, Arrays.asList(Const.actShow, Const.actSearch, Const.actInsert, Const.actUpdate, Const.actDelete));
		put(srvltDmy, Arrays.asList(""));
	}};

	private static final Map<String, String> cAcroMap = new HashMap<>() {  //◆Servlet Acronims
		private static final long serialVersionUID = 1L;
	{
		put(srvltTM, "TM"); put(srvltCB, "CB"); put(srvltSC,  "SC");
		put(srvltEL, "EL"); put(srvltMP, "MP"); put(srvltDBO, "DBO"); put(srvltDmy, "Dmy");
	}};

	private static final List<String> cSrvltGettable = Arrays.asList(srvltTM, srvltDmy); //◆Gettable Servlets


	//◆ Utility ◆//

	//◆String判定
	public static boolean bIsNone(String str) {
		return str == null || str.isEmpty();
	}

	//◆リクエストパラメータ取得
	public static String bGetParam(String name, HttpServletRequest req) {
		try {
			String param = req.getParameter(name);
			if(param == null) { bPutError(name + " : NULL"); return ""; }
			return param;
		} catch (Throwable e) {
			bPutError(name + " : " + e); return "";
		}
	}

	//◆リクエストパラメータMap取得
	public static String[] bGetParamMap(String name, HttpServletRequest req) {
		try {
			return req.getParameterMap().get(name);
		} catch (Throwable e) {
			bPutError(name + " : " + e); return null;
		}
	}

	//◆通常ログ出力
	public static <T> void bPutLog(T msg) {
		System.out.println("  " + bGetInfo() + " : " + msg); bSleep();
	}

	//◆エラーログ出力
	public static <T> void bPutError(T msg) {
		System.err.println("  " + bGetInfo() + " : " + msg); bSleep();
	}

	//◆Debugログ出力
	public static <T> void bPutDebug(T msg) {
		if(pBsDebug) { System.err.print(msg); }
	}

	//◆Sleep
	public static void bSleep() {
		try { Thread.sleep(3); } catch (Throwable e) { bPutError(e); }
	}

	//◆スタックトーレスからメソッド名、クラス名を取得
	private static String bGetInfo() {
		try {
			String[] info = Thread.currentThread().getStackTrace()[3].toString().split("[.(]");
			return info[1] + "." + info[2] + "()";
		} catch (Throwable e) {
			bPutError(e); return "";
		}
	}

	//◆ParseInt
	public static Integer bParseInt(String param, Integer defVal) {
		try { return Integer.parseInt(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}
	//◆ParseLong
	public static Long bParseLong(String param, Long defVal) {
		try { return Long.parseLong(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}
	//◆ParseDouble
	public static Double bParseDouble(String param, Double defVal) {
		try { return Double.parseDouble(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}

	//◆配列 → CSV
	public static <T> String bMakeCsv(T[] arr, boolean flg) {
		try {
			return bMakeCsv(Arrays.asList(arr), flg);
		} catch (Throwable e) {
			bPutError(e); return "";
		}
	}
	public static <T> String bMakeCsv(List<T> list, boolean flg) {
		try {
			StringBuffer bf = new StringBuffer();
			for(int i=0; i<list.size(); i++){ bf.append( (flg ? "[ " : "") + list.get(i) + (flg ? " ]," : ",") ); }
			return (1 <= bf.length()) ? bf.substring(0, bf.length() - 1).toString() : "";
		} catch (Throwable e) {
			bPutError(e); return "";
		}
	}

	//◆Exception から メッセージを生成
	public static String bGetMessage(Throwable e) {
		try {
			String[] eArr = e.toString().split("[\\.:]");
			StringBuilder msg = new StringBuilder();
			for(int i=0; i<eArr.length; i++) {
				if(eArr[i].endsWith("Exception")) { msg.append(eArr[i] + ": "); break; }
			}
			return msg.append(e.getMessage()).toString();
		} catch (Throwable ex) {
			bPutError(ex); return "";
		}
	}

	//◆Timestamp型へ変換
	public static Timestamp bConvDateTime(String inDateTime, String defDateTime) {
		try {
			return Timestamp.valueOf(inDateTime);
		} catch (Throwable e) {
			bPutError(inDateTime + " : " + e);
			try {
				return (defDateTime != null) ? Timestamp.valueOf(defDateTime) : null;
			} catch (Throwable e2) {
				bPutError(e2); return (defDateTime != null) ? Timestamp.valueOf(LocalDateTime.now()) : null;
			}
		}
	}

	//◆プロパティ値取得
	private static final String cPropDir  = "C:\\dev\\Git\\git\\genta\\dev01\\WebContent\\properties\\";
	private static final String cPropFile = "DefaultValue.properties";
/*	private static Properties bProp;
	public static String bGetPropValue(String key) {
		try {
			if(bProp == null) bProp = new Properties();
			bProp.load(new FileInputStream(cPropDir + cPropFile));
			String value = bProp.getProperty(key);
			if(value == null) { bPutError(key + " : NULL"); return ""; }
			bPutLog(key + " : " + value);
			return value;
		} catch (Throwable e) {
			bPutError(key + " : " + e); return "";
		}
	}*/

	//◆プロパティ値取得(一括)
	private static boolean pBsDebug = false;  //Debugモード
	public  static int     pCbCount = 0;      //リスト件数
	public  static int     pElCount = 0;      //口数
	public  static int     pElBound = 0;      //乱数上限値
	public  static String  pElDiv = null;     //除数
	public  static int     pElMatches = 0;    //試合数
	public  static long    pMpMaxValue = 0L;  //算出最大値
	public  static double  pMpLimit = 0;      //範囲上限値
	public  static String  pDboConURL = null; //DB接続URL
	private static Map<String, String> pMap = null;
	private static final String[] cKeys = new String[]{
			"BS_DebugMode","CB_ListCount","EL_ShareCount","EL_Bound","EL_Divisors","EL_Matches","MP_MaxValue","MP_Limit","DBO_ConnectionURL"};
	public static void bGetPropMap() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(cPropDir + cPropFile));

			pMap = new HashMap<>();
			for(int i=0; i<cKeys.length; i++) {
				String value = null;
				try {
					value = prop.getProperty(cKeys[i]);
				} catch (Throwable e) {
					bPutError(cKeys[i] + " : " + e);
				}
				pMap.put(cKeys[i], value);
			}
			bPutLog(pMap);

			pBsDebug    = pMap.get(cKeys[0]).equals("1"); //取得したプロパティを展開
			pCbCount    = Base.bParseInt( pMap.get(cKeys[1]), 0 );
			pElCount    = Base.bParseInt( pMap.get(cKeys[2]), 0 );
			pElBound    = Base.bParseInt( pMap.get(cKeys[3]), 0 );
			pElDiv      = pMap.get(cKeys[4]);
			pElMatches  = Base.bParseInt( pMap.get(cKeys[5]), 0 );
			pMpMaxValue = bParseLong(pMap.get(cKeys[6]),0L);
			pMpLimit    = bParseDouble(pMap.get(cKeys[7]),0.0);
			pDboConURL  = pMap.get(cKeys[8]);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	//◆BaseMsgの追記型設定
	public static <T> void bSetBaseMsg(HttpServletRequest req, T msg) {
		Object curMsg = req.getAttribute(cAttrBaseMsg);
		req.setAttribute(cAttrBaseMsg, (curMsg != null) ? curMsg + " / " + msg : msg);
	}
}