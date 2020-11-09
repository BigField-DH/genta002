package dbOperation;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import base.Base;

public class DbOperationService {

	public DbOperationService() {}

	protected void execSQL(DbOperationBean bean) {

		try {
			bean.con = DriverManager.getConnection(Base.pDboConURL);
			bean.con.setAutoCommit(false);

			if(bean.isSearch) {
				search(bean); //����
			} else {
				updateAll(bean);  //�o�^�E�X�V�E�폜(�ꊇ)
			}
			Base.bSetBaseMsg(bean.req, bean.getSb());

		} catch (Throwable e) {
			e.printStackTrace();
			Base.bSetBaseMsg(bean.req, Base.bGetMessage(e));
			try {
				if(bean.con != null) { Base.bPutError("RollBack"); bean.con.rollback(); }
			} catch (SQLException e1) {
				Base.bPutError("ROLLBACK ���s: " + e1);
			}
		} finally {
			try {
				if(bean.con != null) { Base.bPutLog("Connection Close"); bean.con.close(); }
			} catch (SQLException e) {
				Base.bPutError("CLOSE ���s: " + e);
			}
		}
	}

	//�X�V�E�폜(�ꊇ)
	private void updateAll(DbOperationBean bean) throws SQLException {
		Base.bPutLog("");

		bean.setInsList(); //�o�^�l / �X�V�l�擾

		for(int i=0; i<bean.insList.size(); i++) {
			bean.setPk(i);

			if(!bean.isInsert) {
				//���b�N�`�F�b�N
				String sqlLock = "SELECT col1,col2,col3 FROM tbl2 WITH (XLOCK,ROWLOCK,NOWAIT) WHERE col1 = '" + bean.pk + "'";
				Base.bPutLog(sqlLock);
				ResultSet rsLock = bean.con.prepareStatement(
						sqlLock , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();

				if( !(rsLock.last() && rsLock.getRow()==1) ) {
					Base.bPutError(bean.pk + ":���b�N�s��");
					bean.appendSb("���b�N�s��");
					continue;//con.rollback();
				}
			}
			execUpdateAll2(bean, i); //���s;
		}
		if(!bean.isInsert) select(bean); //���߂Ɠ��������Ō���
		//�o�^�����f�[�^�̌����A�\����Insert��
	}

	//�X�VSQL���s (�o�^�E�X�V�E�폜) (�ꊇ)
	private void execUpdateAll2(DbOperationBean bean, int i) {
		//Base.bPutLog("");

		String sql = null; //SQL��`
		if(bean.isInsert) {
			sql = "INSERT INTO tbl2 (col2, col3, col1) VALUES (?, ?, ?)";
		} else if(bean.isDelete) {
			sql = "DELETE FROM tbl2 WHERE col1 = '" + bean.pk + "'";
		} else {
			sql = "UPDATE tbl2 SET col2 = ?, col3 = ? WHERE col1 = ?";
		}
		Base.bPutLog(sql);

		try {
			PreparedStatement pStmt = bean.con.prepareStatement(sql);
	
			if(!bean.isDelete) Base.bPutLog( bean.setSqlInsParamsAll(pStmt, i) );
	
			if(pStmt.executeUpdate() != 1) {
				Base.bPutError(bean.pk + ":���s(���̑��̗��R) RollBack");
				bean.appendSb("���s");
				bean.con.rollback(); return;
			}
			bean.con.commit();
			bean.appendSb("����");

		} catch (SQLException e) {
			Base.bPutError(bean.pk + ":���s: " + e);//e.printStackTrace();
			bean.appendSb(Base.bGetMessage(e));
			try {
				if(bean.con != null) { Base.bPutError(bean.pk + ":RollBack"); bean.con.rollback(); }
			} catch (SQLException e1) {
				Base.bPutError(bean.pk + ":RollBack ���s: " + e1);
			}
		}
	}

	//����
	private void search(DbOperationBean bean) throws SQLException {
		Base.bPutLog("");
		bean.setSearchCond(); //��ʓ��͏������擾
		select(bean); //����
	}

	//SELECT
	private void select(DbOperationBean bean) throws SQLException{

		StringBuilder sqlSel = new StringBuilder("SELECT col1,col2,col3 FROM tbl2 WITH (NOLOCK) ");
		//StringBuilder sqlSel = new StringBuilder("SELECT colx,col2,col3 FROM tbl2 WITH (NOLOCK) "); //for Debug
		sqlSel.append("WHERE col1 LIKE ? AND col2 BETWEEN ? AND ? AND ( ");
		if(!bean.hasCondCol3()) sqlSel.append("col3 IS NULL OR ");
		sqlSel.append("col3 BETWEEN ? AND ? ) ORDER BY " + bean.sortOrder );
		Base.bPutLog(sqlSel);

		PreparedStatement pStmtSel = bean.con.prepareStatement(sqlSel.toString());
		Base.bPutLog( bean.setSqlCondParams(pStmtSel) );

		bean.setSubList(pStmtSel.executeQuery()); //�������s�A���ׂ֐ݒ�
	}
}
