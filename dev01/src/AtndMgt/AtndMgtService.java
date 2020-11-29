package AtndMgt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import base.Base;

public class AtndMgtService {

	public AtndMgtService() {}

	//契約情報処理
	protected void procContr(AtndMgtBean bean, HttpServletRequest req) {

		Connection con = null;
		try {
			con = DriverManager.getConnection(Base.pDboConURL); //★
			con.setAutoCommit(false);
			//bean.setConnection(DriverManager.getConnection(Base.pDboConURL));

			if(bean.isSearch()) {
				bean.setSearchCond(req);   //画面入力条件を取得
				searchContract(bean, con); //検索
			} else {
				updateAll(bean);  //登録・更新・削除(一括)
			}
			//Base.bSetBaseMsg(bean.req, bean.getSb());

		} catch (Throwable e) {
			e.printStackTrace();
			Base.bSetBaseMsg(bean.req, Base.bGetMessage(e));
			try {
				if(con != null) { Base.bPutError("RollBack"); con.rollback(); }
			} catch (SQLException e1) {
				Base.bPutError("ROLLBACK 失敗: " + e1);
			}
		} finally {
			try {
				if(con != null) { Base.bPutLog("Connection Close"); con.close(); }
			} catch (SQLException e) {
				Base.bPutError("CLOSE 失敗: " + e);
			}
		}
	}

	//契約情報検索
	private void searchContract(AtndMgtBean bean, Connection con) throws SQLException{

		StringBuilder sqlSel = new StringBuilder();
		sqlSel.append("SELECT ");
		sqlSel.append("YMD,UNIT_PRICE,UPPER_LIMIT,LOWER_LIMIT,SURPLUS,DEDUCTION,ACTUAL_SALES,UPD_DATE ");
		sqlSel.append("FROM CONTRACT WITH (NOLOCK) ");
		sqlSel.append("WHERE YMD BETWEEN ? AND ? ");
		sqlSel.append("ORDER BY YMD ASC ");
		Base.bPutLog(sqlSel);

		PreparedStatement pStmtSel = con.prepareStatement(sqlSel.toString());
		Base.bPutLog( bean.setSelContrParams(pStmtSel) );

		bean.setContrList(pStmtSel.executeQuery()); //検索実行、明細へ設定
	}

	protected void execSQL(AtndMgtBean bean) {

		try {
			bean.setConnection(DriverManager.getConnection(Base.pDboConURL));//★
			bean.getConnection().setAutoCommit(false);

			if(bean.isSearch()) {
				//search(bean); //検索
			} else {
				updateAll(bean);  //登録・更新・削除(一括)
			}
			Base.bSetBaseMsg(bean.req, bean.getSb());

		} catch (Throwable e) {
			e.printStackTrace();
			Base.bSetBaseMsg(bean.req, Base.bGetMessage(e));
			try {
				if(bean.getConnection() != null) { Base.bPutError("RollBack"); bean.getConnection().rollback(); }
			} catch (SQLException e1) {
				Base.bPutError("ROLLBACK 失敗: " + e1);
			}
		} finally {
			try {
				if(bean.getConnection() != null) { Base.bPutLog("Connection Close"); bean.getConnection().close(); }
			} catch (SQLException e) {
				Base.bPutError("CLOSE 失敗: " + e);
			}
		}
	}

	//更新・削除(一括)
	private void updateAll(AtndMgtBean bean) throws SQLException {
		Base.bPutLog("");

		bean.setInsList(); //登録値 / 更新値取得

		for(int i=0; i<bean.insList.size(); i++) {
			bean.setPk(i);

			if(bean.isSearch()) {
				//ロックチェック
				String sqlLock = "SELECT col1,col2,col3 FROM tbl2 WITH (XLOCK,ROWLOCK,NOWAIT) WHERE col1 = '" + bean.pk + "'";
				Base.bPutLog(sqlLock);
				ResultSet rsLock = bean.getConnection().prepareStatement(
						sqlLock , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();

				if( !(rsLock.last() && rsLock.getRow()==1) ) {
					Base.bPutError(bean.pk + ":ロック不可");
					bean.appendSb("ロック不可");
					continue;//con.rollback();
				}
			}
			execUpdateAll2(bean, i); //実行;
		}
		//登録したデータの検索、表示★Insert時
	}

	//更新SQL実行 (登録・更新・削除) (一括)
	private void execUpdateAll2(AtndMgtBean bean, int i) {
		//Base.bPutLog("");

		String sql = null; //SQL定義
		if(bean.isSearch()) {
			sql = "INSERT INTO tbl2 (col2, col3, col1) VALUES (?, ?, ?)";
		} else if(!bean.isSearch()) {
			sql = "DELETE FROM tbl2 WHERE col1 = '" + bean.pk + "'";
		} else {
			sql = "UPDATE tbl2 SET col2 = ?, col3 = ? WHERE col1 = ?";
		}
		Base.bPutLog(sql);

		try {
			PreparedStatement pStmt = bean.getConnection().prepareStatement(sql);
	
			if(bean.isSearch()) Base.bPutLog( bean.setSqlInsParamsAll(pStmt, i) );
	
			if(pStmt.executeUpdate() != 1) {
				Base.bPutError(bean.pk + ":失敗(その他の理由) RollBack");
				bean.appendSb("失敗");
				bean.getConnection().rollback(); return;
			}
			bean.getConnection().commit();
			bean.appendSb("成功");

		} catch (SQLException e) {
			Base.bPutError(bean.pk + ":失敗: " + e);//e.printStackTrace();
			bean.appendSb(Base.bGetMessage(e));
			try {
				if(bean.getConnection() != null) { Base.bPutError(bean.pk + ":RollBack"); bean.getConnection().rollback(); }
			} catch (SQLException e1) {
				Base.bPutError(bean.pk + ":RollBack 失敗: " + e1);
			}
		}
	}

}
