<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "StringConvert");</script>

<div class="StrConv">
  <div>
    ◆入力文字列<br>
    <textarea name="${C.prmInStr}"  cols="40" rows="3" placeholder="何か書いてね"></textarea>
  </div>
  <div>→<br>→</div>
  <div>
    ◆出力文字列　<a href="javascript: clearOutStr();" id="btnClr">Clear</a><br>
    <textarea name="outStr" cols="40" rows="3" disabled ></textarea>
  </div>
</div>

<div class="StrConv">
  <div>
    ◆変換要領<br>
    <label><input type="radio" value="${C.rd0Mrs}"   name="${C.prmItemRD}" onClick="dispHint(${C.rd0Mrs});" />Morse ⇔ String</label>　<br>
    <label><input type="radio" value="${C.rd1Rvrs}" name="${C.prmItemRD}" onClick="dispHint();"              />Reverse</label>
  </div>
  <div>
    ◆PG<br>
    <label><input type="radio" value="S" name="PG" />サーバ変換</label><br>
    <label><input type="radio" value="C" name="PG" />クライアント変換</label>　
    <a href="javascript: scExec();">${C.actConvert}</a>　　
  </div>
  <p class="none">
    <input type="radio" value="${C.rd2MrsToStr}" name="${C.prmItemRD}" />
    <input type="radio" value="${C.rd3StrToMrs}" name="${C.prmItemRD}" />
  </p>
  <div id="scHint"></div>
</div>

<script type="text/javascript">
//初期設定
function initSC(){
	bGetObjByNm("${C.prmInStr}")[0].value           = "${oBeanSC.inStr}";
	bGetObjByNm("outStr")[0].value                  = "${oBeanSC.outStr}";
	bGetFormElem("${acro}", "${C.prmItemRD}").value = ("${oBeanSC.itemRD}" == "${C.rd1Rvrs}") ? "${C.rd1Rvrs}" : "${C.rd0Mrs}";
	bGetFormElem("${acro}", "PG").value             = (bIsEmpty("${oBeanSC.inStr}")) ? "C" : "S";
	if(!bIsEmpty("${oBeanSC.outStr}")){ bSetDisplay("btnClr", "inline"); }
}

initSC(); //初期設定

const mHint = "Morse → String : 長点「|」短点「!」<br>String → Morse : アルファベットと数字のみ有効";

//ヒント表示
function dispHint(pps){ bSetInnerHTML("scHint", (pps == "${C.rd0Mrs}") ? mHint : ""); }

//入力チェック、変換処理実行
function scExec(){
	const inStrObj = bGetObjByNm("${C.prmInStr}")[0];
	const inStr = inStrObj.value.replaceAll(/[\s\h]/g, " ").trim();
	if(bIsEmpty(inStr)){ bSetErrStyle("だから何か書けよ！", inStrObj); return; }

	var pps = bGetFormElem("${acro}", "${C.prmItemRD}");
	var mrsFlg = false;
	if(pps.value == "${C.rd0Mrs}"){
		if( inStr.match(/^[a-zA-Z0-9 ]+$/) != null ){
			pps.value = "${C.rd3StrToMrs}"
		} else if( inStr.match(/^[!| ]+$/) != null ){
			pps.value = "${C.rd2MrsToStr}"
		} else {
			bSetErrStyle(mHint, inStrObj);
			return;
		}
		mrsFlg = true;
	}

	if(bGetFormElem("${acro}", "PG").value == "S"){
		bExec("${acro}", "${C.cntxtSC}", "POST", "${C.actConvert}"); //サーバで変換

	} else {
		//クライアントで変換 
		const out = ( (mrsFlg) ? bMorseConvertSC(inStr, pps) : bReverseSC(inStr) ).trim();
		if(!bIsEmpty(out)){
			bGetObjByNm("outStr")[0].value = out;
			bSetHdrMsg("クライアントにて変換実施");
			bSetDisplay("btnClr", "inline");
		}
		inStrObj.style = bGetNmlStyle();
	}
}

//出力欄のクリア
function clearOutStr(){
	bGetObjByNm("outStr")[0].value = "";
	bSetDisplay("btnClr", "none");
}
</script>
<!-- ◆ ${srvltPath} END ◆ -->