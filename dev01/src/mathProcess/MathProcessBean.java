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

	private boolean prime; //true:素数
	public boolean getPrime() { return prime; }

	private List<Long> primes; //素数のリスト
	public List<Long> getPrimes() { return primes; }

	private List<Long> divisors; //約数のリスト
	public List<Long> getDivisors() { return divisors; }
	protected void setDivisors(List<Long> pDivisors) { //結果のセット(Single)
		divisors = pDivisors;
		prime = pDivisors.isEmpty(); //約数がなければTRUE(=素数)
		step = getStepVal(pDivisors);
	}

	private long rangeEnd = 0;
	public long getRangeEnd() { return rangeEnd; }

	private int step = 23;
	public int getStep() { return step; }

	protected void getParamValues(HttpServletRequest req) { //画面入力値の取得
		numToCalc = Long.parseLong( Base.bGetParam(Const.prmNumToCalc, req) ); //入力値
		itemRD = Base.bGetParam(Const.prmItemRD, req); //Single or Range;
		primes = null; divisors = null; //ついでに素数リスト、約数リストを初期化
	}

	protected void setValues(List<Long> pPrimes, long pRangeEnd) { //結果のセット(Range)
		primes = pPrimes;  rangeEnd = pRangeEnd; step = getStepVal(pPrimes);
	}

	//桁数からStep数を算出
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
