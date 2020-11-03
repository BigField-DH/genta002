package mathProcess;

import java.util.ArrayList;
import java.util.List;
import base.Base;

public class MathProcessService {

	private MathProcessBean mBean;
	private final long mNumToCalc;
	private final double mLimit = 100000.0;

	public MathProcessService(MathProcessBean bean) {
		mBean = bean;
		mNumToCalc = mBean.getNumToCalc(); //画面入力値
	}

	//範囲
	protected boolean calcRange() {
		Base.bPutLog("");

		long rangeEnd = //範囲の上限
				mNumToCalc + (long) Math.min(mLimit, Math.ceil(mBean.getMaxVal() / Math.ceil(mNumToCalc / 50)));
		List<Long> primes = new ArrayList<>(); //素数リスト
		for(long i=mNumToCalc; i<=rangeEnd; i++) {
			boolean flg = true;
			for(int j=2; flg && j<=i/2; j++) {
				if(i % j == 0) { flg = false; break; } //割り切れた時点で、falseで終了
			}
			if(flg) primes.add(i); //素数(true)と判定された数字を保持
		}
		mBean.setValues(primes, rangeEnd);
		return true;
	}

	//単独
	protected boolean calcSingle() {
		Base.bPutLog("");

		List<Long> divisors = new ArrayList<>(); //約数
		for(long i=2; i<=mNumToCalc/2; i++) {
			if(mNumToCalc % i == 0) divisors.add(i); //約数を保持
		}
		mBean.setDivisors(divisors);
		return true;
	}
}
