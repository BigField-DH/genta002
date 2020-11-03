package expectLottery;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import base.Base;

public class ExpectLotteryService {

	private ExpectLotteryBean mBean;
	private SecureRandom mSecRand;

	private int mShares; //����
	private int mBound;  //��������l

	private final String mKeyBnd  = "EL_Bound";
	private final String mKeyDiv  = "EL_Divisors";
	private final String mKeyMtch = "EL_Matches";

	public ExpectLotteryService(ExpectLotteryBean bean) throws NoSuchAlgorithmException {
		mBean = bean;
		mShares = mBean.getCount();
		mBound = Integer.parseInt( Base.bGetPropValue(mKeyBnd) );
		mSecRand = SecureRandom.getInstanceStrong();
		//throw new NoSuchAlgorithmException("for SecureRandom"); //��for Debug
	}

	//Loto
	protected boolean expectLoto() {
		Base.bPutLog("");

		//�����i����ێ� (���i�R�[�h, ����)
		String[] divStr = Base.bGetPropValue(mKeyDiv).split(",");
		List<int[]> items = Arrays.asList(
				new int[]{ mBean.loto5, Integer.parseInt(divStr[0]) },
				new int[]{ mBean.loto6, Integer.parseInt(divStr[1]) },
				new int[]{ mBean.loto7, Integer.parseInt(divStr[2]) } );
		
		//�I�����ꂽ���i�̗\�z���擾
		for(int i=0; i<items.size(); i++) {

			int item = items.get(i)[0], divisor = items.get(i)[1]; //���i�A����
			HashSet<Integer[]> sharesSet = new HashSet<>(); //�������̗\�z��ێ�

			//�������̗\�z���擾
			while(sharesSet.size() < mShares) {
				TreeSet<Integer> oneTS = new TreeSet<>(); //�P�����̗\�z��ێ�

				//�����擾(�P�`bound)�A��]�Z�o�A�d�������A�\�[�g
				while(oneTS.size() < item) { oneTS.add( ( (mSecRand.nextInt(mBound)+1) % divisor )+1 ); }

				Integer[] one = new Integer[oneTS.size()];
				Iterator<Integer> oneIt = oneTS.iterator();
				for(int j=0; j<one.length; j++) { one[j] = oneIt.next(); } //TreeSet �� Integer[]

				sharesSet.add(one); //�ێ�(���P�ʂŏd��������)
			}
			mBean.setLotoShares(sharesSet, item);
		}
		return true;
	}

	//Toto
	protected boolean expectToto() {
		Base.bPutLog("");

		int MATCHES = Integer.parseInt( Base.bGetPropValue(mKeyMtch) ); //�P�����̎�����
		HashSet<Integer[]> sharesSet = new HashSet<>(); //�������̗\�z��ێ�

		//�������̗\�z���擾
		while(sharesSet.size() < mShares) {

			Integer[] one = new Integer[MATCHES]; //�P�����̗\�z��ێ�

			//�����擾(�P�`bound)�A��]�Z�o�A�R�[�h�֕ϊ�
			for(int i=0; i<MATCHES; i++) { one[i] = (mSecRand.nextInt(mBound)+1) % 3; }
			sharesSet.add(one); //�ێ�(���P�ʂŏd��������)
		}
		mBean.setSharesSet(sharesSet);
		return true;
	}
}
