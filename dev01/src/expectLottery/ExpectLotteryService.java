package expectLottery;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import base.Base;

public class ExpectLotteryService {

	private ExpectLotteryBean mBean;
	private SecureRandom mSecRand;

	private int mShares; //口数

	public ExpectLotteryService(ExpectLotteryBean bean) throws NoSuchAlgorithmException {
		mBean = bean;
		mShares = mBean.getCount();
		mSecRand = SecureRandom.getInstanceStrong();
		//throw new NoSuchAlgorithmException("for SecureRandom"); //◆for Debug
	}

	//Loto
	protected boolean expectLoto() {
		Base.bPutLog("");

		//◆商品情報を保持 (商品コード, 上限値)
		String[] divStr = Base.pElDiv.split(",");
		List<int[]> items = Arrays.asList(
				new int[]{ mBean.loto5, Base.bParseInt(divStr[0], 0) },
				new int[]{ mBean.loto6, Base.bParseInt(divStr[1], 0) },
				new int[]{ mBean.loto7, Base.bParseInt(divStr[2], 0) } );
				//new int[]{ mBean.loto5, Base.bParseInt(divStr[0], mBean.loto5) } ); //for Debug

		for(int i=0; i<items.size(); i++) { //商品毎に予想を取得
			int item = items.get(i)[0], bound = items.get(i)[1]; //商品、上限値
			List<TreeSet<Integer>> sharesSet = new ArrayList<>(); //ｎ口分の予想を保持
			while(sharesSet.size() < mShares) { //口数分の予想を取得
				TreeSet<Integer> oneTS = new TreeSet<>(); //１口分の予想を保持
				while(oneTS.size() < item) { oneTS.add( getRandNum(bound)+1 ); } //１口分の予想を取得(1～bound)、重複除去、ソート
				if(hasDupli(sharesSet, oneTS)) continue; //口単位の重複チェック
				sharesSet.add(oneTS); //保持
			}
			mBean.setLotoShares(sharesSet, item);
		}
		//Base.bPutDebug("\n"); Base.bSleep();
		return true;
	}

	//Toto
	protected boolean expectToto() {
		Base.bPutLog("");
		List<Integer[]> sharesSet = new ArrayList<>(); //ｎ口分の予想を保持
		while(sharesSet.size() < mShares) { //口数分の予想を取得
			Integer[] one = new Integer[Base.pElMatches]; //１口分の予想を保持
			for(int i=0; i<Base.pElMatches; i++) { one[i] = getRandNum(3); } //１口分の予想を取得(0～2)
			if(hasDupli(sharesSet, one)) continue; //口単位の重複チェック
			sharesSet.add(one); //保持
		}
		mBean.setSharesSet(sharesSet);
		//Base.bPutDebug("\n"); Base.bSleep();
		return true;
	}

	//◆口単位の重複チェック(Toto)
	private <T> boolean hasDupli(List<T[]> sharesSet, T[] one) {
		if(sharesSet.isEmpty()) return false;
		for(int i=0; i<sharesSet.size(); i++) {
			if(isSame(sharesSet.get(i), one)) return true; //１口でも重複があればTRUE
		}
		return false; //全口チェック後、重複なしの場合、FALSE
	}
	//◆口単位の重複チェック(Loto)
	private <T> boolean hasDupli(List<TreeSet<T>> sharesSet, Set<T> oneTreeSet) {
		if(sharesSet.isEmpty()) return false;
		Object[] oneTS = oneTreeSet.toArray();
		for(int i=0; i<sharesSet.size(); i++) {
			if(isSame(sharesSet.get(i).toArray(), oneTS)) return true;  //１口でも重複があればTRUE
		}
		return false; //全口チェック後、重複なしの場合、FALSE
	}
	//◆１口ずつ重複チェック
	private <T> boolean isSame(Object[] oneSS, Object[] one) {
		for(int j=0; j<oneSS.length; j++) {
			if((int)oneSS[j] != (int)one[j]) return false; //１つでも異なればFALSE
		}
		Base.bPutDebug("  重複\n");
		return true; //すべて同じ場合、重複としてTRUE
	}

	//◆乱数取得
	private int getRandNum(int bound) {
		int randNum = mSecRand.nextInt(bound); //0～bound-1
		//Base.bPutDebug(randNum + ",");
		return randNum;
	}
}
