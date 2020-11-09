package expectLottery;

import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class ExpectLotteryBean {

	//LotoItem Variation
	protected final int loto5 = 5;
	protected final int loto6 = 6;
	protected final int loto7 = 7;

	public ExpectLotteryBean() {}
	public ExpectLotteryBean(HttpServletRequest req) {
		count = Base.bParseInt( Base.bGetParam(Const.prmCount, req), Base.pElCount );
		itemRD = Base.bGetParam(Const.prmItemRD, req); //Toto or Loto
	}

	private int count = Base.pElCount;
	public int getCount() { return count; }

	private List<Integer[]> sharesSet;
	public List<Integer[]>getSharesSet(){ return sharesSet; }
	protected void setSharesSet(List<Integer[]> pSharesSet) { sharesSet = pSharesSet; }

	private String itemRD = Const.rdToto;
	public String getItemRD() { return itemRD; }
	protected boolean isToto() { return Const.rdToto.equals(itemRD); }

	private List<TreeSet<Integer>> sharesMini;
	private List<TreeSet<Integer>> sharesSix;
	private List<TreeSet<Integer>> sharesSeven;
	public  List<TreeSet<Integer>> getSharesMini() { return sharesMini; }
	public  List<TreeSet<Integer>> getSharesSix()  { return sharesSix; }
	public  List<TreeSet<Integer>> getSharesSeven(){ return sharesSeven; }
	protected void setLotoShares(List<TreeSet<Integer>> pSharesSet, int item) {
		if(item == loto5){
			sharesMini = pSharesSet;
		} else if(item == loto7) {
			sharesSeven = pSharesSet;
		} else {
			sharesSix = pSharesSet;
		}
	}
}
