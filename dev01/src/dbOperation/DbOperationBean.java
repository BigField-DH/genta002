package dbOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class DbOperationBean {

	public DbOperationBean() {}

	private List<Object[]> subList; //明細
	public List<Object[]> getSubList() { return subList; }
	protected void setSubList(ResultSet rsSel) throws SQLException {
		subList = new ArrayList<>();
		while(rsSel.next()) {
			String col3 = rsSel.getString("col3");
			subList.add( new Object[] {
				rsSel.getString("col1"), rsSel.getInt("col2"), (col3 != null) ? col3.substring(0, 19) : null} );
		}
	}

	//検索条件(画面用)
	private String condCol1;
	public String getCondCol1() { return condCol1; }

	private Integer condCol2f;
	public Integer getCondCol2f() { return condCol2f; }

	private Integer condCol2t;
	public Integer getCondCol2t() { return condCol2t; }

	private String condCol3f;
	public String getCondCol3f() { return condCol3f; }

	private String condCol3t;
	public String getCondCol3t() { return condCol3t; }

	private void setCondCols(String pCol1, Integer pCol2f, Integer pCol2t, String pCol3f, String pCol3t) {
		condCol1 = pCol1; condCol2f = pCol2f; condCol2t = pCol2t; condCol3f = pCol3f; condCol3t = pCol3t;
	}
	protected boolean hasCondCol3() {
		return !Base.bIsNone(condCol3f) || !Base.bIsNone(condCol3t);
	}

	private static final String cDefSort  = "1";
	private static final String cDefOrder = "ASC";

	//ソート条件
	private String sortCol = cDefSort;
	public String getSortCol() { return sortCol; }

	private String order = cDefOrder;
	public String getOrder() { return order; }

	private String sortOrder;
	private void setSortOrder(String pSort, String pOrder) { //SQL用に整形
		sortCol = !Base.bIsNone(pSort)  ? pSort  : cDefSort;
		order   = !Base.bIsNone(pOrder) ? pOrder : cDefOrder;
		sortOrder = sortCol + " " + order;
	}
	protected String getSortOrder() { return sortOrder; }

	//検索条件(内部PG用)
	private String    condCol1Pg;
	private Integer   condCol2fPg;
	private Integer   condCol2tPg;
	private Timestamp condCol3fPg;
	private Timestamp condCol3tPg;
//	private String    condColsPg; //ログ用
	private void setCondColsPg() {
		condCol1Pg  = "%" + condCol1 + "%";
		condCol2fPg = condCol2f != null ? condCol2f : -99999999;
		condCol2tPg = condCol2t != null ? condCol2t :  99999999;
		condCol3fPg = Base.bConvDate(condCol3f, "1900-01-01", " 00:00:00.000");
		condCol3tPg = Base.bConvDate(condCol3t, "2199-12-31", " 23:59:59.999");
//		condColsPg  = Base.bMakeCsv( //ログ整形
//				new Object[] { condCol1Pg, condCol2fPg, condCol2tPg, condCol3fPg, condCol3tPg}, true);
	}
//	protected String getCondParams() { return condColsPg; }

	//検索条件設定(画面入力値)
	protected void setSearchCond(HttpServletRequest req) {
		setCondCols(
			Base.bGetParam(Const.prmCondCol1, req),
			Base.bPrseInt(Base.bGetParam(Const.prmCondCol2f, req), null), 
			Base.bPrseInt(Base.bGetParam(Const.prmCondCol2t, req), null),
			Base.bGetParam(Const.prmCondCol3f, req),
			Base.bGetParam(Const.prmCondCol3t, req) );
		setSortOrder(Base.bGetParam(Const.prmSortRD1, req), Base.bGetParam(Const.prmSortRD2, req));
		setCondColsPg();
	}

	//検索条件設定(登録後検索用)
	protected void setSearchCond() {
		setCondCols(insCol1.substring(0, 1), -Math.abs(insCol2), Math.abs(insCol2), "", "");
		setSortOrder(cDefSort, cDefOrder);
		setCondColsPg();
	}

	//検索条件をStatementへ設定
	protected void setSqlCondParams(PreparedStatement pStmtSel) throws SQLException {
		pStmtSel.setString(   1, condCol1Pg);
		pStmtSel.setInt(      2, condCol2fPg);
		pStmtSel.setInt(      3, condCol2tPg);
		pStmtSel.setTimestamp(4, condCol3fPg);
		pStmtSel.setTimestamp(5, condCol3tPg);
		Base.bPutLog(Base.bMakeCsv( //ログ整形
				new Object[] { condCol1Pg, condCol2fPg, condCol2tPg, condCol3fPg, condCol3tPg}, true));
	}

	//登録条件
	private String insCol1;
	public String getInsCol1() { return insCol1; }

	private Integer insCol2;
	public Integer getInsCol2() { return insCol2; }

	private Timestamp insCol3;
	public String getInsCol3() { return (insCol3 != null) ? insCol3.toString().substring(0, 19) : null; }

//	private String insCols; //ログ用
//	private void setInsCols() { insCols = Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true); } //ログ整形
//	protected String getInsParams() { return insCols; }

	protected void setInsCols(HttpServletRequest req) { //INSERT
		insCol1 = Base.bGetParam(Const.prmInsCol1, req);
		insCol2 = Integer.parseInt(Base.bGetParam(Const.prmInsCol2, req));
		insCol3 = Base.bConvDate(Base.bGetParam(Const.prmInsCol3, req), null, "");
		//setInsCols();
	}
	protected void setInsCols(HttpServletRequest req, Integer radio) { //UPDATE,DELETE
		insCol1 = Base.bGetParams(Const.prmUpdCol1, req)[radio];
		insCol2 = Integer.parseInt(Base.bGetParams(Const.prmUpdCol2, req)[radio]);
		insCol3 = Base.bConvDate(Base.bGetParams(Const.prmUpdCol3, req)[radio], null, "");
		//setInsCols();
	}

	//登録条件をStatementへ設定
	protected void setSqlInsParams(PreparedStatement pStmt) throws SQLException {
		pStmt.setInt(      1, insCol2);
		pStmt.setTimestamp(2, insCol3);
		pStmt.setString(   3, insCol1);
		Base.bPutLog(Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true)); //ログ整形
	}
}
