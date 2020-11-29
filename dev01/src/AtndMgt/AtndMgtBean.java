package AtndMgt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class AtndMgtBean {

	public AtndMgtBean() {
		String[] ym = String.valueOf(new Date(System.currentTimeMillis())).split("-");
		condYear  = ym[0];
		condMonth = ym[1];
	}

	protected HttpServletRequest req;
	private StringBuilder sb;

	private Connection con;
	protected Connection getConnection() { return con; }
	protected void setConnection(Connection pCon) throws SQLException {
		con = pCon;
		con.setAutoCommit(false);
	}

	private String action; //Action
	public String getAction() { return action; }
	protected void setAction(String pAction) { action = pAction; }
	protected boolean isSearch() { return Const.actSearch.equals(action); }

	//ŒŸõğŒ—“
	private String condYear; //”N
	public String getCondYear() { return condYear; }
	protected void setCondYear(String pYear) { condYear = pYear; }

	private String condMonth; //Œ
	public String getCondMonth() { return condMonth; }
	protected void setCondMonth(String pMonth) { condMonth = pMonth; }

	private int rdPurpose = Const.rd0Atnd; //ŠÇ—‘ÎÛ‘I‘ğ
	public int getRdPurpose() { return rdPurpose; }
	protected void setRdPurpose(int pPurpose) { rdPurpose = pPurpose; }
	protected boolean isContract() { return rdPurpose == Const.rd1Contr; }

	//ŒŸõğŒİ’è(‰æ–Ê“ü—Í’læ“¾)
	protected void setSearchCond(HttpServletRequest req) {
		condYear  = Base.bGetParam(Const.prmCondYear,  req);
		condMonth = Base.bGetParam(Const.prmCondMonth, req);
	}

	//ŒŸõğŒ‚ğStatement‚Öİ’è(Œ_–ñî•ñ)
	protected String setSelContrParams(PreparedStatement pStmtSel) throws SQLException {
		Date start = Date.valueOf(condYear + "-01-01");
		Date end   = Date.valueOf(condYear + "-12-31");
		pStmtSel.setDate(1, start );
		pStmtSel.setDate(2, end   );
		return Base.bMakeCsv( new Object[] { start, end }, true); //ƒƒO®Œ`
	}

	private List<Object[]> contrList; //Œ_–ñî•ñ–¾×
	public List<Object[]> getContrList() { return contrList; }
	protected void setContrList(ResultSet rsSel) throws SQLException {
		contrList = new ArrayList<>();
		while(rsSel.next()) {
			String[] ym = rsSel.getString("YMD").split("-");
			contrList.add( new Object[] {
				ym[0], ym[1], rsSel.getInt("UNIT_PRICE"), rsSel.getDouble("UPPER_LIMIT"),
				rsSel.getDouble("LOWER_LIMIT"), rsSel.getInt("SURPLUS"), rsSel.getInt("DEDUCTION"),
				rsSel.getInt("ACTUAL_SALES"), rsSel.getTimestamp("UPD_DATE"), } );
		}
	}


	
	protected void setReqInfo(HttpServletRequest pReq, String pAction) {
		req = pReq;
		action = pAction;
//		isInsert = Const.actInsert.equals(action);
//		isDelete = Const.actDelete.equals(action);
//		isSearch = !(isInsert || isDelete || Const.actUpdate.equals(action));
		sb = new StringBuilder(action + " ");
	}
	protected <T> void appendSb(T msg) { sb.append(pk + ":" + msg + " "); }
	protected String getSb() { return sb.toString(); }




	//ŒŸõğŒ(‰æ–Ê—p)
	private String  condCol1,  condCol3f ,condCol3t;
	private Integer condCol2f, condCol2t;

	public String  getCondCol1()  { return condCol1;  }
	public Integer getCondCol2f() { return condCol2f; }
	public Integer getCondCol2t() { return condCol2t; }
	public String  getCondCol3f() { return condCol3f; }
	public String  getCondCol3t() { return condCol3t; }

	//ŒŸõğŒ(“à•”PG—p)
	private String    condCol1Pg;
	private Integer   condCol2fPg, condCol2tPg;
	private Timestamp condCol3fPg, condCol3tPg;

	private void setCondCols(String pCol1, Integer pCol2f, Integer pCol2t, String pCol3f, String pCol3t) {
		condCol1 = pCol1; condCol2f = pCol2f; condCol2t = pCol2t; condCol3f = pCol3f; condCol3t = pCol3t;
		condCol1Pg  = "%" + condCol1 + "%"; //š‘O•ûˆê’vAŒã•ûˆê’vAB–†
		condCol2fPg = condCol2f != null ? condCol2f : -99999999;
		condCol2tPg = condCol2t != null ? condCol2t :  99999999;
		condCol3fPg = Base.bConvDateTime(condCol3f, "1900-01-01 00:00:00");
		condCol3tPg = Base.bConvDateTime(condCol3t, "2199-12-31 23:59:59");
		//š•¡”Œ“o˜^Œã‚ÌŒŸõğŒ‚ÍA—vŒŸ“¢
	}

	//col3‚Ì‚¢‚¸‚ê‚©‚É’l‚ª‚ ‚ê‚ÎTRUE
	protected boolean hasCondCol3() { return !Base.bIsNone(condCol3f) || !Base.bIsNone(condCol3t); }

	//ƒ\[ƒgğŒ
	private static final String cDefSort  = "1", cDefOrder = "ASC";

	private String sortCol = cDefSort;
	private String order   = cDefOrder;

	public String getSortCol() { return sortCol; }
	public String getOrder()   { return order;   }

	protected String sortOrder;
	private void setSortOrder(String pSort, String pOrder) { //SQL—p‚É®Œ`
		sortCol = !Base.bIsNone(pSort)  ? pSort  : cDefSort;
		order   = !Base.bIsNone(pOrder) ? pOrder : cDefOrder;
		sortOrder = sortCol + " " + order;
	}



	//“o˜^ğŒ
	protected String insCol1;
	public String getInsCol1() { return insCol1; }

	private Integer insCol2;
	public Integer getInsCol2() { return insCol2; }

	private Timestamp insCol3;
	public String getInsCol3() { return (insCol3 != null) ? insCol3.toString().substring(0, 19) : null; }

/*	private String insCols; //ƒƒO—p
	private void setInsCols() { insCols = Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true); } //ƒƒO®Œ`
	protected String getInsParams() { return insCols; }*/

	protected void setInsCols() { //INSERT
		insCol1 = Base.bGetParam(Const.prmInsCol1, req);
		insCol2 = Integer.parseInt(Base.bGetParam(Const.prmInsCol2, req));
		insCol3 = Base.bConvDateTime(Base.bGetParam(Const.prmInsCol3, req), null);
	}
	protected void setUpdCols() { //UPDATE,DELETE
		int radio = Integer.parseInt( Base.bGetParam(Const.prmItemRD, req) );
		insCol1 = Base.bGetParamMap(Const.prmUpdCol1, req)[radio];
		insCol2 = Integer.parseInt(Base.bGetParamMap(Const.prmUpdCol2, req)[radio]);
		insCol3 = Base.bConvDateTime(Base.bGetParamMap(Const.prmUpdCol3, req)[radio], null);
	}

/*	//“o˜^ğŒ‚ğStatement‚Öİ’è
	protected void setSqlInsParams(PreparedStatement pStmt) throws SQLException {
		pStmt.setInt(      1, insCol2);
		pStmt.setTimestamp(2, insCol3);
		pStmt.setString(   3, insCol1);
		Base.bPutLog(Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true)); //ƒƒO®Œ`
	}*/

	//“o˜^ğŒ(•¡”Œ)
	protected List<Object[]> insList; //“o˜^–¾×
	public List<Object[]> getInsList() { return insList; }
	protected void setInsList() {

	}

	protected Object pk; //PK
	protected void setPk(int i) { pk = insList.get(i)[0]; }

	//“o˜^ğŒ‚ğStatement‚Öİ’è(•¡”Œ)
	protected String setSqlInsParamsAll(PreparedStatement pStmt, int i) throws SQLException {
		Object[] insObj = insList.get(i);
		pStmt.setInt(      1, (int)insObj[1]      );
		pStmt.setTimestamp(2, (Timestamp)insObj[2]);
		pStmt.setString(   3, (String)insObj[0]   );
		return Base.bMakeCsv(insObj, true); //ƒƒO®Œ`
	}
	
}
