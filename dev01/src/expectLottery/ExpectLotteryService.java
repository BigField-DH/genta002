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

	private int mShares; //����

	public ExpectLotteryService(ExpectLotteryBean bean) throws NoSuchAlgorithmException {
		mBean = bean;
		mShares = mBean.getCount();
		mSecRand = SecureRandom.getInstanceStrong();
		//throw new NoSuchAlgorithmException("for SecureRandom"); //��for Debug
	}

	//Loto
	protected boolean expectLoto() {
		Base.bPutLog("");

		//�����i����ێ� (���i�R�[�h, ����l)
		String[] divStr = Base.pElDiv.split(",");
		List<int[]> items = Arrays.asList(
				new int[]{ mBean.loto5, Base.bParseInt(divStr[0], 0) },
				new int[]{ mBean.loto6, Base.bParseInt(divStr[1], 0) },
				new int[]{ mBean.loto7, Base.bParseInt(divStr[2], 0) } );
				//new int[]{ mBean.loto5, Base.bParseInt(divStr[0], mBean.loto5) } ); //for Debug

		for(int i=0; i<items.size(); i++) { //���i���ɗ\�z���擾
			int item = items.get(i)[0], bound = items.get(i)[1]; //���i�A����l
			List<TreeSet<Integer>> sharesSet = new ArrayList<>(); //�������̗\�z��ێ�
			while(sharesSet.size() < mShares) { //�������̗\�z���擾
				TreeSet<Integer> oneTS = new TreeSet<>(); //�P�����̗\�z��ێ�
				while(oneTS.size() < item) { oneTS.add( getRandNum(bound)+1 ); } //�P�����̗\�z���擾(1�`bound)�A�d�������A�\�[�g
				if(hasDupli(sharesSet, oneTS)) continue; //���P�ʂ̏d���`�F�b�N
				sharesSet.add(oneTS); //�ێ�
			}
			mBean.setLotoShares(sharesSet, item);
		}
		//Base.bPutDebug("\n"); Base.bSleep();
		return true;
	}

	//Toto
	protected boolean expectToto() {
		Base.bPutLog("");
		List<Integer[]> sharesSet = new ArrayList<>(); //�������̗\�z��ێ�
		while(sharesSet.size() < mShares) { //�������̗\�z���擾
			Integer[] one = new Integer[Base.pElMatches]; //�P�����̗\�z��ێ�
			for(int i=0; i<Base.pElMatches; i++) { one[i] = getRandNum(3); } //�P�����̗\�z���擾(0�`2)
			if(hasDupli(sharesSet, one)) continue; //���P�ʂ̏d���`�F�b�N
			sharesSet.add(one); //�ێ�
		}
		mBean.setSharesSet(sharesSet);
		//Base.bPutDebug("\n"); Base.bSleep();
		return true;
	}

	//�����P�ʂ̏d���`�F�b�N(Toto)
	private <T> boolean hasDupli(List<T[]> sharesSet, T[] one) {
		if(sharesSet.isEmpty()) return false;
		for(int i=0; i<sharesSet.size(); i++) {
			if(isSame(sharesSet.get(i), one)) return true; //�P���ł��d���������TRUE
		}
		return false; //�S���`�F�b�N��A�d���Ȃ��̏ꍇ�AFALSE
	}
	//�����P�ʂ̏d���`�F�b�N(Loto)
	private <T> boolean hasDupli(List<TreeSet<T>> sharesSet, Set<T> oneTreeSet) {
		if(sharesSet.isEmpty()) return false;
		Object[] oneTS = oneTreeSet.toArray();
		for(int i=0; i<sharesSet.size(); i++) {
			if(isSame(sharesSet.get(i).toArray(), oneTS)) return true;  //�P���ł��d���������TRUE
		}
		return false; //�S���`�F�b�N��A�d���Ȃ��̏ꍇ�AFALSE
	}
	//���P�����d���`�F�b�N
	private <T> boolean isSame(Object[] oneSS, Object[] one) {
		for(int j=0; j<oneSS.length; j++) {
			if((int)oneSS[j] != (int)one[j]) return false; //�P�ł��قȂ��FALSE
		}
		Base.bPutDebug("  �d��\n");
		return true; //���ׂē����ꍇ�A�d���Ƃ���TRUE
	}

	//�������擾
	private int getRandNum(int bound) {
		int randNum = mSecRand.nextInt(bound); //0�`bound-1
		//Base.bPutDebug(randNum + ",");
		return randNum;
	}
}
