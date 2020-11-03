<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "MathProcess");</script>

<div class="mpCond">
    ◆判定数字<input type="text" name="${C.prmNumToCalc}" maxlength="10" size="10" /><br>
    ◆判定要領
    <label><input type="radio" name="${C.prmItemRD}" value="${C.rdSingle}" />単独</label>
    <label><input type="radio" name="${C.prmItemRD}" value="${C.rdRange}"  />範囲</label>　　<br>
    <p><a href="javascript: mpExec();">Calculate</a>　　</p>
  <div id="mpHint"></div>
</div>

<hr id="hr"><%-- <jsp:forward page="/jsp/Dummy.jsp" /> ◆ for Debug --%>

<div id="mpIsPrime"></div>

<div id="mpDivisors">
約数は
<table class="divisors"><!-- ◆ 約数リスト ◆ -->
  <c:forEach begin="0" end="${oBeanMP.divisors.size()}" step="${oBeanMP.step}" varStatus="loopO">
  <tr>
    <c:forEach begin="${loopO.index}" end="${loopO.index + loopO.step -1}" items="${oBeanMP.divisors}" var="one" varStatus="loopI">
    <td>${one}</td>
    </c:forEach>
  </tr></c:forEach>
</table>
</div>

<div id="mpPrimes">
<b>' ${oBeanMP.numToCalc} ～ ${oBeanMP.rangeEnd}'</b> の素数は・・・
<table class="primes"><!-- ◆ 素数リスト ◆ -->
  <c:forEach begin="0" end="${oBeanMP.primes.size()}" step="${oBeanMP.step}" varStatus="loopO">
  <tr>
    <c:forEach begin="${loopO.index}" end="${loopO.index + loopO.step -1}" items="${oBeanMP.primes}" var="one" varStatus="loopI">
    <td>${one}</td>
    </c:forEach>
  </tr>
  </c:forEach>
</table>
</div>

<script type="text/javascript">
//メンバー定数
const limit = ${oBeanMP.maxVal}; //long_max は 9223372036854775807
const mpHint = limit + " 以下の正の整数を指定してね";

//初期設定
function initMP(){
	bGetObjByNm("${C.prmNumToCalc}")[0].value = "${oBeanMP.numToCalc}";
	bGetFormElem("${acro}", "${C.prmItemRD}").value      = "${oBeanMP.itemRD}";
	bSetTextContent("mpHint", mpHint);

	//エリア設定
	var areas = new Array();
	if(0 < "${oBeanMP.numToCalc}" && "${oBeanMP.itemRD}" == "${C.rdSingle}"){
		bSetTextContent("mpIsPrime", "' ${oBeanMP.numToCalc} ' は素数" +  ( "${oBeanMP.prime}" == "true" ? "" : "に非ず"));
		areas.push("hr,block", "mpIsPrime,block");
		if(0 < "${oBeanMP.divisors.size()}"){ areas.push("mpDivisors,block"); }
	}
	if(0 < "${oBeanMP.primes.size()}") { areas.push("hr,block", "mpPrimes,block"); }
	bSetDisplayAreas(areas);
}

initMP(); //初期設定

//入力チェック、FORM実行
function mpExec(){
	//入力値が範囲内であること
	if(!bIsValidInputMP( bGetObjByNm("${C.prmNumToCalc}")[0], 1, limit, mpHint )){ return; }
	bExec("${acro}", "${C.cntxtMP}", "POST", "Calc");
}

</script>
<!-- ◆ ${srvltPath} END ◆ -->