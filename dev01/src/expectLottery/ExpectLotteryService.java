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

	private int mShares; //口数
	private int mBound;  //乱数上限値

	private final String mKeyBnd  = "EL_Bound";
	private final String mKeyDiv  = "EL_Divisors";
	private final String mKeyMtch = "EL_Matches";

	public ExpectLotteryService(ExpectLotteryBean bean) throws NoSuchAlgorithmException {
		mBean = bean;
		mShares = mBean.getCount();
		mBound = Integer.parseInt( Base.bGetPropValue(mKeyBnd) );
		mSecRand = SecureRandom.getInstanceStrong();
		//throw new NoSuchAlgorithmException("for SecureRandom"); //◆for Debug
	}

	//Loto
	protected boolean expectLoto() {
		Base.bPutLog("");

		//◆商品情報を保持 (商品コード, 除数)
		String[] divStr = Base.bGetPropValue(mKeyDiv).split(",");
		List<int[]> items = Arrays.asList(
				new int[]{ mBean.loto5, Integer.parseInt(divStr[0]) },
				new int[]{ mBean.loto6, Integer.parseInt(divStr[1]) },
				new int[]{ mBean.loto7, Integer.parseInt(divStr[2]) } );
		
		//選択された商品の予想を取得
		for(int i=0; i<items.size(); i++) {

			int item = items.get(i)[0], divisor = items.get(i)[1]; //商品、除数
			HashSet<Integer[]> sharesSet = new HashSet<>(); //ｎ口分の予想を保持

			//口数分の予想を取得
			while(sharesSet.size() < mShares) {
				TreeSet<Integer> oneTS = new TreeSet<>(); //１口分の予想を保持

				//乱数取得(１～bound)、剰余算出、重複除去、ソート
				while(oneTS.size() < item) { oneTS.add( ( (mSecRand.nextInt(mBound)+1) % divisor )+1 ); }

				Integer[] one = new Integer[oneTS.size()];
				Iterator<Integer> oneIt = oneTS.iterator();
				for(int j=0; j<one.length; j++) { one[j] = oneIt.next(); } //TreeSet → Integer[]

				sharesSet.add(one); //保持(口単位で重複を除去)
			}
			mBean.setLotoShares(sharesSet, item);
		}
		return true;
	}

	//Toto
	protected boolean expectToto() {
		Base.bPutLog("");

		int MATCHES = Integer.parseInt( Base.bGetPropValue(mKeyMtch) ); //１口分の試合数
		HashSet<Integer[]> sharesSet = new HashSet<>(); //ｎ口分の予想を保持

		//口数分の予想を取得
		while(sharesSet.size() < mShares) {

			Integer[] one = new Integer[MATCHES]; //１口分の予想を保持

			//乱数取得(１～bound)、剰余算出、コードへ変換
			for(int i=0; i<MATCHES; i++) { one[i] = (mSecRand.nextInt(mBound)+1) % 3; }
			sharesSet.add(one); //保持(口単位で重複を除去)
		}
		mBean.setSharesSet(sharesSet);
		return true;
	}
}
