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
		mNumToCalc = mBean.getNumToCalc(); //��ʓ��͒l
	}

	//�͈�
	protected boolean calcRange() {
		Base.bPutLog("");

		long rangeEnd = //�͈͂̏��
				mNumToCalc + (long) Math.min(mLimit, Math.ceil(mBean.getMaxVal() / Math.ceil(mNumToCalc / 50)));
		List<Long> primes = new ArrayList<>(); //�f�����X�g
		for(long i=mNumToCalc; i<=rangeEnd; i++) {
			boolean flg = true;
			for(int j=2; flg && j<=i/2; j++) {
				if(i % j == 0) { flg = false; break; } //����؂ꂽ���_�ŁAfalse�ŏI��
			}
			if(flg) primes.add(i); //�f��(true)�Ɣ��肳�ꂽ������ێ�
		}
		mBean.setValues(primes, rangeEnd);
		return true;
	}

	//�P��
	protected boolean calcSingle() {
		Base.bPutLog("");

		List<Long> divisors = new ArrayList<>(); //��
		for(long i=2; i<=mNumToCalc/2; i++) {
			if(mNumToCalc % i == 0) divisors.add(i); //�񐔂�ێ�
		}
		mBean.setDivisors(divisors);
		return true;
	}
}
