package mathProcess;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class MathProcessBean {

	public MathProcessBean() {}

	private long maxVal = Base.pMpMaxValue;
	public long getMaxVal() { return maxVal; }

	private String itemRD = Const.rdSingle;
	public String getItemRD() { return itemRD; }
	protected boolean isRange() { return Const.rdRange.equals(itemRD); }

	private Long numToCalc = null;
	public Long getNumToCalc() { return numToCalc; }

	private boolean prime; //true:�f��
	public boolean getPrime() { return prime; }

	private List<Long> primes; //�f���̃��X�g
	public List<Long> getPrimes() { return primes; }

	private List<Long> divisors; //�񐔂̃��X�g
	public List<Long> getDivisors() { return divisors; }
	protected void setDivisors(List<Long> pDivisors) { //���ʂ̃Z�b�g(Single)
		divisors = pDivisors;
		prime = pDivisors.isEmpty(); //�񐔂��Ȃ����TRUE(=�f��)
		step = getStepVal(pDivisors);
	}

	private long rangeEnd = 0;
	public long getRangeEnd() { return rangeEnd; }

	private int step = 23;
	public int getStep() { return step; }

	protected void getParamValues(HttpServletRequest req) { //��ʓ��͒l�̎擾
		numToCalc = Long.parseLong( Base.bGetParam(Const.prmNumToCalc, req) ); //���͒l
		itemRD = Base.bGetParam(Const.prmItemRD, req); //Single or Range;
		primes = null; divisors = null; //���łɑf�����X�g�A�񐔃��X�g��������
	}

	protected void setValues(List<Long> pPrimes, long pRangeEnd) { //���ʂ̃Z�b�g(Range)
		primes = pPrimes;  rangeEnd = pRangeEnd; step = getStepVal(pPrimes);
	}

	//��������Step�����Z�o
	private int getStepVal(List<Long> list) {
		int[] col = new int[]{0,0,0,0,18,15,13,11,10,9,8};
		int[] cnt = new int[]{0,0,0,0,0,0,0,0,0,0,0};
		for(int i=0; i<list.size(); i++) { cnt[list.get(i).toString().length()]++; }
		if(col[10]/2   <= cnt[10]) return col[10];
		if(col[9] /2+1 <= cnt[9])  return col[9];
		if(col[8] /2   <= cnt[8])  return col[8];
		if(col[7] /2+1 <= cnt[7])  return col[7];
		if(col[6] /2+1 <= cnt[6])  return col[6];
		if(col[5] /2+1 <= cnt[5])  return col[5];
		if(col[4] /2+1 <= cnt[4])  return col[4];
		return 23;
	}
}
