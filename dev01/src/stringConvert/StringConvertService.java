package stringConvert;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import base.Base;
import base.Const;

public class StringConvertService {

	private StringConvertBean mBean;
	private HttpServletRequest mReq;
	private String mInStrPg;

	public StringConvertService(StringConvertBean bean, HttpServletRequest req) {
		mBean = bean; mReq = req;
		mInStrPg = mBean.getInStrPg(); //for Process below
	}

	//Reverse
	protected boolean reverse() {
		Base.bPutLog("");

		char[] inStrPgChar = mInStrPg.toCharArray();
		StringBuilder sbOut = new StringBuilder();

		for(int i=inStrPgChar.length-1; 0<=i; i--) { //to read characters from end of String
			if (inStrPgChar[i] != ' ') sbOut.append(inStrPgChar[i]).append(' ');
		}
		mBean.setOutStr(sbOut);
		return true;
	}

	//Morse Convert (pps 2:Morse to String / 3:String to Morse)
	protected boolean morseConvert() {
		Base.bPutLog("");

		final String[] strArr = //Map Strings
			{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			 "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			 "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			 " ", " "}; //©intentionally

		final String[] mrsArr = //Map Morse Codes`
			{"!|", "|!!!", "|!|!", "|!!", "!", "!!|!", "||!", "!!!!", "!!", "!|||",
			"|!|", "!|!!", "||", "|!", "|||", "!||!", "||!|", "!|!", "!!!", "|",
			"!!|", "!!!|", "!||", "|!!|", "|!||", "||!!",
			"|||||", "!||||", "!!|||", "!!!||", "!!!!|", "!!!!!", "|!!!!", "||!!!", "|||!!", "||||!",
			"", " "};

		if(strArr.length != mrsArr.length) {
			//mReq.setAttribute(Base.cAttrBaseMsg, "Invalid Map Info");
			Base.bSetBaseMsg(mReq, "Invalid Map Info");
			return false;
		}

		String[] keyArr, valArr; //Key,Value for Map
		char inChar[] = null; String inArr[] = null;
		int itemRD = mBean.getItemRD();

		//Assignment of Keys & Values 
		if(itemRD == Const.rd2MrsToStr) {
			keyArr = mrsArr; valArr = strArr;
			inArr = mInStrPg.split(" ");
		} else {
			keyArr = strArr; valArr = mrsArr;
			inChar = mInStrPg.toLowerCase().toCharArray();
		}

		//to create Map Info
		Map<String, String> map = new HashMap<>();
		for(int i=0; i<keyArr.length; i++) { map.put(keyArr[i], valArr[i]); }

		//for Output
		StringBuilder sbOut = new StringBuilder();

		//String to Morse
		for(int i=0; inChar != null && i<inChar.length; i++) {
			sbOut.append(map.get(String.valueOf(inChar[i])) + " ");
		}

		//Morse to String
		for(int i=0; inArr != null && i<inArr.length; i++) {
			String val = map.get(inArr[i]);
			sbOut.append(val == null ? "?" : val);
		}

		//to create Space-String (digits of pps-1) for Shaping
		StringBuilder sp = new StringBuilder();
		for(int i=0; i<itemRD-1; i++) { sp.append(" "); }

		//Shaping for Output
		mBean.setOutStr(sbOut.toString().replaceAll("[ ]{" + itemRD + ",}", sp.toString()));
		return true;
	}
}
