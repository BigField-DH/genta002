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
		setInit(req, res, false); //ϊέθ
		bPutError(" " + bSrvlt + " ");
		if(cSrvltGettable.contains(bSrvlt)) {
			doProcess(); //ΒΚ
			bPutLog(bSrvlt + " : committed " + bRes.isCommitted() + "\n");
			bReq.setAttribute(cAttrBaseMsg, "Gettable");
			bReq.setAttribute(cAttrSrvlt, bSrvlt); //JΪζ
			bReq.setAttribute(cAttrAcro, bAcro); //ͺΜ
			bReq.getServletContext().getRequestDispatcher(cJspBase).forward(bReq, bRes);
		} else {
			bPutError(Const.cntxtJspIndex +" : isCommitted " + bRes.isCommitted() + "\n");
			bSession.setAttribute(cAttrBaseMsg, "Ungettable");
			bRes.sendRedirect(Const.cntxtJspIndex);
		}
		//putError(" " + bSrvlt + "  END\n");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String srvlt = srvltTM;
		try {
			setInit(req, res, true); //ϊέθ
			bPutLog(" " + bSrvlt + " ");

			if( !cActMap.get(bSrvlt).contains(bAction) ) { //eAction`FbN
				bPutError("Invalid Action '" + bAction + "'");
				bSetBaseMsg(bReq, bSrvlt + " : Invalid Action '" + bAction + "'");
				return;
			}
			doProcess(); //ΒΚ
			srvlt = bSrvlt; //JΪζ

		} catch (RuntimeException e) {
			bPutError(e + "\n"); e.printStackTrace();
			bSetBaseMsg(bReq, bSrvlt + " : " + bGetMessage(e));
		} finally {
			bPutLog(srvlt + " : committed " + bRes.isCommitted() + "\n");
			bReq.setAttribute(cAttrAcro, bAcro);  //ͺΜ
			bReq.setAttribute(cAttrSrvlt, srvlt); //JΪζ
			bReq.getServletContext().getRequestDispatcher(cJspBase).forward(bReq, bRes);
			//putLog(" " + bSrvlt + "  END\n");
		}
	}

	//ϊέθ
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

	// Base Method to be overridden in sub Classes 
	protected void doProcess() throws RuntimeException {
		bPutError("to be Overridden \n"); throw new RuntimeException("to be Overridden");
	}

	// ϊ\¦
	protected boolean bIsShow() {
		if(!Const.actShow.equals(bAction)) return false;
		//bReq.setAttribute(cAttrBaseMsg, Const.actShow + bAcro);
		bSetBaseMsg(bReq, Const.actShow + bAcro);
		return true;
	}

	// Constants //

	private static final String cJspBase   = "/jsp/Base.jsp"; //Common Page

	private static final String cAttrBaseMsg = "baseMsg";   //Attributes
	private static final String cAttrSrvlt   = "srvltPath";
	private static final String cAttrAcro    = "acro";

	protected static final String srvltTM  = "/TopMenu"; //Servlet Paths
	public    static final String srvltCB  = "/CheckBox";
	public    static final String srvltSC  = "/StringConvert";
	public    static final String srvltEL  = "/ExpectLottery";
	public    static final String srvltMP  = "/MathProcess";
	public    static final String srvltDBO = "/DbOperation";
	public    static final String srvltAM  = "/AtndMgt";
	protected static final String srvltDmy = "/Dummy";

	private static Map<String, List<String>> cActMap;
	private static void setActMap(String[] pActions) {
		cActMap = new HashMap<>();
		for(int i=0; i<pActions.length; i++) {
			String[] one = pActions[i].split("_");
			cActMap.put(one[0], Arrays.asList(one[1].split("@")));
		}
	}

	private static Map<String, String> cAcroMap;
	private static void setAcroMap(String[] pAcros) {
		cAcroMap = new HashMap<>();
		for(int i=0; i<pAcros.length; i++) {
			String[] one = pAcros[i].split("_");
			cAcroMap.put(one[0], one[1]);
		}
	}

	private static final List<String> cSrvltGettable = Arrays.asList(srvltTM, srvltDmy); //Gettable Servlets

	// Utility //

	//String»θ
	public static boolean bIsNone(String str) {
		return str == null || str.isEmpty();
	}

	//NGXgp[^ζΎ
	public static String bGetParam(String name, HttpServletRequest req) {
		try {
			String param = req.getParameter(name);
			if(param == null) { bPutError(name + " : NULL"); return ""; }
			return param;
		} catch (Throwable e) {
			bPutError(name + " : " + e); return "";
		}
	}

	//NGXgp[^MapζΎ
	public static String[] bGetParamMap(String name, HttpServletRequest req) {
		try {
			return req.getParameterMap().get(name);
		} catch (Throwable e) {
			bPutError(name + " : " + e); return null;
		}
	}

	//ΚνOoΝ
	public static <T> void bPutLog(T msg) {
		System.out.println("  " + bGetInfo() + " : " + msg); bSleep();
	}

	//G[OoΝ
	public static <T> void bPutError(T msg) {
		System.err.println("  " + bGetInfo() + " : " + msg); bSleep();
	}

	//DebugOoΝ
	public static <T> void bPutDebug(T msg) {
		if(pBsDebug) { System.err.print(msg); }
	}

	//Sleep
	public static void bSleep() {
		try { Thread.sleep(3); } catch (Throwable e) { bPutError(e); }
	}

	//X^bNg[X©η\bhΌANXΌπζΎ
	private static String bGetInfo() {
		try {
			String[] info = Thread.currentThread().getStackTrace()[3].toString().split("[.(]");
			return info[1] + "." + info[2] + "()";
		} catch (Throwable e) {
			bPutError(e); return "";
		}
	}

	//ParseInt
	public static Integer bParseInt(String param, Integer defVal) {
		try { return Integer.parseInt(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}
	//ParseLong
	public static Long bParseLong(String param, Long defVal) {
		try { return Long.parseLong(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}
	//ParseDouble
	public static Double bParseDouble(String param, Double defVal) {
		try { return Double.parseDouble(param); } catch (Throwable e) { bPutError(e); return defVal; }
	}

	//zρ ¨ CSV
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

	//Exception ©η bZ[WπΆ¬
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

	//Timestamp^ΦΟ·
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

	//BaseMsgΜΗL^έθ
	public static <T> void bSetBaseMsg(HttpServletRequest req, T msg) {
		Object curMsg = req.getAttribute(cAttrBaseMsg);
		req.setAttribute(cAttrBaseMsg, (curMsg != null) ? curMsg + " / " + msg : msg);
	}

	//vpeBlζΎ
	private static boolean  pBsDebug    = false; //Debug[h
	public  static int      pCbCount    = 0;     //Xg
	public  static int      pElCount    = 0;     //ϋ
	public  static int      pElBound    = 0;     //γΐl
	public  static String   pElDiv      = null;  //
	public  static int      pElMatches  = 0;     //
	public  static long     pMpMaxValue = 0L;    //ZoΕεl
	public  static double   pMpLimit    = 0;     //ΝΝγΐl
	public  static String   pDboConURL  = null;  //DBΪ±URL

	private static final String cPropDir  = "C:\\dev\\Git\\git\\genta\\dev01\\WebContent\\properties\\";
	private static final String cPropFile = "DefaultValue.properties";
	private static final String[] cKeysBS  = new String[]{"BS_DebugMode", "BS_Actions", "BS_Acros"};
	private static final String[] cKeysCB  = new String[]{"CB_ListCount"};
	private static final String[] cKeysEL  = new String[]{"EL_ShareCount", "EL_Bound", "EL_Divisors","EL_Matches"};
	private static final String[] cKeysMP  = new String[]{"MP_MaxValue", "MP_Limit"};
	private static final String[] cKeysDBO = new String[]{"DBO_ConnectionURL"}; //
	
	public static void bGetPropMap() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(cPropDir + cPropFile));

			Map<String, String> pMap = new HashMap<>();
			List<String[]> keylist = Arrays.asList(cKeysBS, cKeysCB, cKeysEL, cKeysMP, cKeysDBO);

			for(int i=0; i<keylist.size(); i++) {

				String[] keys = keylist.get(i);
				Map<String, String> logMap = new HashMap<>();

				for(int j=0; j<keys.length; j++) {
					String value = null;
					try {
						value = prop.getProperty(keys[j]);
					} catch (Throwable e) {
						bPutError(keys[j] + " : " + e);
					}
					pMap.put(keys[j], value);
					logMap.put(keys[j], value);
				}
				bPutLog(logMap);
			}
			pBsDebug    = pMap.get(cKeysBS[0]).equals("1"); //ζΎ΅½vpeBπWJ
			setActMap(pMap.get(cKeysBS[1]).split(","));  //eAction
			setAcroMap(pMap.get(cKeysBS[2]).split(",")); //ͺΜ
			pCbCount    = Base.bParseInt( pMap.get(cKeysCB[0]), 0 );
			pElCount    = Base.bParseInt( pMap.get(cKeysEL[0]), 0 );
			pElBound    = Base.bParseInt( pMap.get(cKeysEL[1]), 0 );
			pElDiv      = pMap.get(cKeysEL[2]);
			pElMatches  = Base.bParseInt( pMap.get(cKeysEL[3]), 0 );
			pMpMaxValue = bParseLong(pMap.get(cKeysMP[0]),0L);
			pMpLimit    = bParseDouble(pMap.get(cKeysMP[1]),0.0);
			pDboConURL  = pMap.get(cKeysDBO[0]);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}