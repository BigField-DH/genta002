package dbOperation;

import java.sql.Connection;
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

	protected HttpServletRequest req;
	private StringBuilder sb;
	private String action;
	protected boolean isInsert, isDelete, isSearch;

	protected void setReqInfo(HttpServletRequest pReq, String pAction) {
		req = pReq;
		action = pAction;
		isInsert = Const.actInsert.equals(action);
		isDelete = Const.actDelete.equals(action);
		isSearch = !(isInsert || isDelete || Const.actUpdate.equals(action));
		sb = new StringBuilder(action + " ");
	}
	protected <T> void appendSb(T msg) { sb.append(pk + ":" + msg + " "); }
	protected String getSb() { return sb.toString(); }

	protected Connection con;

	private List<Object[]> subList; //����
	public List<Object[]> getSubList() { return subList; }
	protected void setSubList(ResultSet rsSel) throws SQLException {
		subList = new ArrayList<>();
		while(rsSel.next()) {
			String col3 = rsSel.getString("col3");
			subList.add( new Object[] {
				rsSel.getString("col1"), rsSel.getInt("col2"), (col3 != null) ? col3.substring(0, 19) : null} );
		}
	}

	//��������(��ʗp)
	private String  condCol1,  condCol3f ,condCol3t;
	private Integer condCol2f, condCol2t;

	public String  getCondCol1()  { return condCol1;  }
	public Integer getCondCol2f() { return condCol2f; }
	public Integer getCondCol2t() { return condCol2t; }
	public String  getCondCol3f() { return condCol3f; }
	public String  getCondCol3t() { return condCol3t; }

	//��������(����PG�p)
	private String    condCol1Pg;
	private Integer   condCol2fPg, condCol2tPg;
	private Timestamp condCol3fPg, condCol3tPg;

	private void setCondCols(String pCol1, Integer pCol2f, Integer pCol2t, String pCol3f, String pCol3t) {
		condCol1 = pCol1; condCol2f = pCol2f; condCol2t = pCol2t; condCol3f = pCol3f; condCol3t = pCol3t;
		condCol1Pg  = "%" + condCol1 + "%"; //���O����v�A�����v�A�B��
		condCol2fPg = condCol2f != null ? condCol2f : -99999999;
		condCol2tPg = condCol2t != null ? condCol2t :  99999999;
		condCol3fPg = Base.bConvDateTime(condCol3f, "1900-01-01 00:00:00");
		condCol3tPg = Base.bConvDateTime(condCol3t, "2199-12-31 23:59:59");
		//���������o�^��̌��������́A�v����
	}

	//col3�̂����ꂩ�ɒl�������TRUE
	protected boolean hasCondCol3() { return !Base.bIsNone(condCol3f) || !Base.bIsNone(condCol3t); }

	//�\�[�g����
	private static final String cDefSort  = "1", cDefOrder = "ASC";

	private String sortCol = cDefSort;
	private String order   = cDefOrder;

	public String getSortCol() { return sortCol; }
	public String getOrder()   { return order;   }

	protected String sortOrder;
	private void setSortOrder(String pSort, String pOrder) { //SQL�p�ɐ��`
		sortCol = !Base.bIsNone(pSort)  ? pSort  : cDefSort;
		order   = !Base.bIsNone(pOrder) ? pOrder : cDefOrder;
		sortOrder = sortCol + " " + order;
	}

/*	private void setCondColsPg() {
		condCol1Pg  = "%" + condCol1 + "%";
		condCol2fPg = condCol2f != null ? condCol2f : -99999999;
		condCol2tPg = condCol2t != null ? condCol2t :  99999999;
		condCol3fPg = Base.bConvDate(condCol3f, "1900-01-01", " 00:00:00.000");
		condCol3tPg = Base.bConvDate(condCol3t, "2199-12-31", " 23:59:59.999");
	}*/

	//���������ݒ�(��ʓ��� / �o�^�㌟��)
	protected void setSearchCond() {

		if(isInsert) { //�������͗v����
			setCondCols(insCol1.substring(0, 1), -Math.abs(insCol2), Math.abs(insCol2), "", "");
			setSortOrder(cDefSort, cDefOrder);

		} else {
			setCondCols(
				Base.bGetParam(Const.prmCondCol1, req),
				Base.bParseInt(Base.bGetParam(Const.prmCondCol2f, req), null), 
				Base.bParseInt(Base.bGetParam(Const.prmCondCol2t, req), null),
				Base.bGetParam(Const.prmCondCol3f, req),
				Base.bGetParam(Const.prmCondCol3t, req) );
			setSortOrder(Base.bGetParam(Const.prmSortRD1, req), Base.bGetParam(Const.prmSortRD2, req));
		}
	}

	//����������Statement�֐ݒ�
	protected String setSqlCondParams(PreparedStatement pStmtSel) throws SQLException {
		pStmtSel.setString(   1, condCol1Pg);
		pStmtSel.setInt(      2, condCol2fPg);
		pStmtSel.setInt(      3, condCol2tPg);
		pStmtSel.setTimestamp(4, condCol3fPg);
		pStmtSel.setTimestamp(5, condCol3tPg);
		return Base.bMakeCsv( //���O���`
				new Object[] { condCol1Pg, condCol2fPg, condCol2tPg, condCol3fPg, condCol3tPg}, true);
	}

	//�o�^����
	protected String insCol1;
	public String getInsCol1() { return insCol1; }

	private Integer insCol2;
	public Integer getInsCol2() { return insCol2; }

	private Timestamp insCol3;
	public String getInsCol3() { return (insCol3 != null) ? insCol3.toString().substring(0, 19) : null; }

/*	private String insCols; //���O�p
	private void setInsCols() { insCols = Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true); } //���O���`
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

/*	//�o�^������Statement�֐ݒ�
	protected void setSqlInsParams(PreparedStatement pStmt) throws SQLException {
		pStmt.setInt(      1, insCol2);
		pStmt.setTimestamp(2, insCol3);
		pStmt.setString(   3, insCol1);
		Base.bPutLog(Base.bMakeCsv(new Object[] { insCol2, insCol3, insCol1 }, true)); //���O���`
	}*/

	//�o�^����(������)
	protected List<Object[]> insList; //�o�^����
	public List<Object[]> getInsList() { return insList; }
	protected void setInsList() {

		String[] pCbox = isInsert ? null : Base.bGetParamMap(Const.prmCbox, req);
		String[] pCol1s = Base.bGetParamMap(isInsert ? Const.prmInsCol1 : Const.prmUpdCol1, req);
		String[] pCol2s = isDelete ? null : Base.bGetParamMap(isInsert ? Const.prmInsCol2 : Const.prmUpdCol2, req);
		String[] pCol3s = isDelete ? null : Base.bGetParamMap(isInsert ? Const.prmInsCol3 : Const.prmUpdCol3, req);

		insList = new ArrayList<>();
		for(int i=0; isInsert && i<pCol1s.length; i++) {
			if(Base.bIsNone(pCol1s[i])) continue;
			insList.add( new Object[] {
				pCol1s[i], Integer.parseInt(pCol2s[i]), Base.bConvDateTime(pCol3s[i], null) } );
		}
		for(int i=0; !isInsert && i<pCbox.length; i++) {
			int cb = Integer.parseInt(pCbox[i]);
			insList.add( isDelete
				? new Object[] { pCol1s[cb] }
				: new Object[] { pCol1s[cb], Integer.parseInt(pCol2s[cb]), Base.bConvDateTime(pCol3s[cb], null) } );
		}
	}

	protected Object pk; //PK
	protected void setPk(int i) { pk = insList.get(i)[0]; }

	//�o�^������Statement�֐ݒ�(������)
	protected String setSqlInsParamsAll(PreparedStatement pStmt, int i) throws SQLException {
		Object[] insObj = insList.get(i);
		pStmt.setInt(      1, (int)insObj[1]      );
		pStmt.setTimestamp(2, (Timestamp)insObj[2]);
		pStmt.setString(   3, (String)insObj[0]   );
		return Base.bMakeCsv(insObj, true); //���O���`
	}
	
}
