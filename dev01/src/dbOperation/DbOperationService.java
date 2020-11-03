package dbOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class DbOperationService {

	private static final String mKey = "DBO_ConnectionURL";
	private HttpServletRequest mReq;

	public DbOperationService(HttpServletRequest req) {
		mReq = req;
	}

	protected void execSQL(DbOperationBean bean, String action) {

		Connection con = null;
		try {
			con = DriverManager.getConnection(Base.bGetPropValue(mKey));
			con.setAutoCommit(false);

			if(Const.actInsert.equals(action)) {
				insert(bean, con); //�o�^
			} else if(Const.actUpdate.equals(action) || Const.actDelete.equals(action)) {
				update(bean, con, action); //�X�V�E�폜
			} else {
				search(bean, con); //����
			}

		} catch (Throwable e) {
			e.printStackTrace();
			mReq.setAttribute(Base.cAttrBaseMsg, Base.bGetMessage(e));
			try {
				if(con != null) { Base.bPutError("Connection RollBack"); con.rollback(); }
			} catch (SQLException e1) {
				Base.bPutError("ROLLBACK ���s: " + e1);
			}
		} finally {
			try {
				if(con != null) { Base.bPutLog("Connection Close"); con.close(); }
			} catch (SQLException e) {
				Base.bPutError("CLOSE ���s: " + e);
			}
		}
	}

	//�o�^
	private void insert(DbOperationBean bean, Connection con) throws SQLException {
		Base.bPutLog("");

		bean.setInsCols(mReq); //�o�^�l�擾
		execUpdate(bean, con, "INSERT INTO tbl2 (col2, col3, col1) VALUES (?, ?, ?)"); //���s
		//execUpdate("INSERT INTO tbl2 (col2, col3, colx) VALUES (?, ?, ?)"); //for Debug

		bean.setSearchCond(); //���������ݒ�
		select(bean, con); //�o�^�����f�[�^�̌����A�\��
	}

	//�X�V�E�폜
	private void update(DbOperationBean bean, Connection con, String action) throws SQLException {
		Base.bPutLog("");

		bean.setInsCols( mReq, Integer.parseInt(Base.bGetParam(Const.prmItemRD, mReq)) ); //�X�V�l�擾
		String col1 = bean.getInsCol1();

		//���b�N�`�F�b�N
		String sqlLock = "SELECT col1,col2,col3 FROM tbl2 WITH (XLOCK,ROWLOCK,NOWAIT) WHERE col1 = '" + col1 + "'";
		//String sqlLock = "SELECT col1,col2,col3 FROM tbl2 WITH (XLOCK,ROWLOCK,NOWAIT) WHERE col1 = '@@@'"; //for Debug
		//String sqlLock = "SELECT colx,col2,col3 FROM tbl2 WITH (XLOCK,ROWLOCK,NOWAIT) WHERE col1 = '" + col1 + "'"; //for Debug
		Base.bPutLog(sqlLock);
		ResultSet rsLock = con.prepareStatement(
				sqlLock , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();

		if(rsLock.last() && rsLock.getRow() == 1) {
			StringBuilder sql = new StringBuilder(); //�X�V�pSQL
			if(Const.actDelete.equals(action)) {
				sql.append("DELETE FROM tbl2 WHERE col1 = '" + col1 + "'");
			} else {
				sql.append("UPDATE tbl2 SET col2 = ?, col3 = ? WHERE col1 = ?");
				//sql.append("UPDATE tbl2 SET col2 = ?, col3 = ? WHERE col1 = ? and 'a' = '1'"); //for Debug
				//sql.append("UPDATE tbl2 SET col2 = ?, col3 = ? WHERE colx = ?"); //for Debug
			}

			execUpdate(bean, con, sql.toString()); //���s;

		} else {
			con.rollback();
			Base.bPutError("�Y���f�[�^�Ȃ�(���Ƀ��b�N����Ă邩��): " + col1);
			mReq.setAttribute(Base.cAttrBaseMsg, "�Y���f�[�^�Ȃ�(���Ƀ��b�N����Ă邩��): " + col1);
		}
		select(bean, con); //���߂Ɠ��������Ō���
	}

	//�X�VSQL���s (�o�^�E�X�V�E�폜)
	private void execUpdate(DbOperationBean bean, Connection con, String sql) throws SQLException {
		Base.bPutLog("");

		String cmd = sql.split(" ")[0];

		Base.bPutLog(sql);
		PreparedStatement pStmt = con.prepareStatement(sql);

		if(!Const.actDelete.toUpperCase().equals(cmd)) bean.setSqlInsParams(pStmt);

		if(pStmt.executeUpdate() != 1) {
			con.rollback();
			Base.bPutError(cmd + " ���s(���̑��̗��R)");
			mReq.setAttribute(Base.cAttrBaseMsg, cmd + " ���s(���̑��̗��R)");
			return;
		}
		con.commit();
		mReq.setAttribute(Base.cAttrBaseMsg, cmd + " ����");
	}

	//����
	private void search(DbOperationBean bean, Connection con) throws SQLException {
		Base.bPutLog("");
		bean.setSearchCond(mReq); //��ʓ��͏������擾
		select(bean, con); //����
	}

	//SELECT
	private void select(DbOperationBean bean, Connection con) throws SQLException{

		StringBuilder sqlSel = new StringBuilder("SELECT col1,col2,col3 FROM tbl2 WITH (NOLOCK) ");
		//StringBuilder sqlSel = new StringBuilder("SELECT colx,col2,col3 FROM tbl2 WITH (NOLOCK) "); //for Debug
		sqlSel.append("WHERE col1 LIKE ? AND col2 BETWEEN ? AND ? AND ( ");
		if(!bean.hasCondCol3()) sqlSel.append("col3 IS NULL OR ");
		sqlSel.append("col3 BETWEEN ? AND ? ) ORDER BY " + bean.getSortOrder() );
		Base.bPutLog(sqlSel);

		PreparedStatement pStmtSel = con.prepareStatement(sqlSel.toString());
		bean.setSqlCondParams(pStmtSel);

		bean.setSubList(pStmtSel.executeQuery()); //�������s�A���ׂ֐ݒ�
	}
}
