package expectLottery;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class ExpectLotteryBean {

	private final String mKeyCnt = "EL_ShareCount";

	//LotoItem Variation
	protected final int loto5 = 5;
	protected final int loto6 = 6;
	protected final int loto7 = 7;

	public ExpectLotteryBean() { count = Base.bPrseInt(Base.bGetPropValue(mKeyCnt), 0); }
	public ExpectLotteryBean(HttpServletRequest req) {
		count = Base.bGetPrmCount(req, mKeyCnt); //口数を初期化 (画面設定値 > プロパティ値 > 0)
		itemRD = Base.bGetParam(Const.prmItemRD, req); //Toto or Loto
		sharesSet = new HashSet<>(); sharesMini = new HashSet<>(); sharesSix = new HashSet<>(); sharesSeven = new HashSet<>();
	}

	private int count = 0;
	public int getCount() { return count; }

	private HashSet<Integer[]> sharesSet;
	public HashSet<Integer[]>getSharesSet(){ return sharesSet; }
	protected void setSharesSet(HashSet<Integer[]> pSharesSet) { sharesSet = pSharesSet; }

	private String itemRD = Const.rdToto;
	public String getItemRD() { return itemRD; }
	protected boolean isToto() { return Const.rdToto.equals(itemRD); }

	private HashSet<Integer[]> sharesMini;
	private HashSet<Integer[]> sharesSix;
	private HashSet<Integer[]> sharesSeven;
	public  HashSet<Integer[]> getSharesMini() { return sharesMini; }
	public  HashSet<Integer[]> getSharesSix()  { return sharesSix; }
	public  HashSet<Integer[]> getSharesSeven(){ return sharesSeven; }
	protected void setLotoShares(HashSet<Integer[]> pSharesSet, int item) {
		if(item == loto5){
			sharesMini = pSharesSet;
		} else if(item == loto7) {
			sharesSeven = pSharesSet;
		} else {
			sharesSix = pSharesSet;
		}
	}
}
