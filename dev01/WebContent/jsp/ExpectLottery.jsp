<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "ExpectLottery");</script>

<div class="expCond">
  <div>
    ◆予想対象
    <label><input type="radio" name="${C.prmItemRD}" value="${C.rdToto}" />${C.rdToto}</label>
    <label><input type="radio" name="${C.prmItemRD}" value="${C.rdLoto}" />${C.rdLoto}</label><br>
    ◆予想口数<input type="text" name="${C.prmCount}" maxlength="2" size="1" />
    <p><a href="javascript: bExec('${acro}', '${C.cntxtEL}', 'POST', 'Expect');">Get Expect</a></p>
  </div>
</div>

<hr id="hr"><%-- <jsp:forward page="/jsp/Dummy.jsp" /> ◆ for Debug --%>

<table id="resultToto"><!-- ◆ Toto ◆ -->
  <c:forEach begin="0" end="${oBeanEL.sharesSet.size()}" step="10" varStatus="loopO"><tr>
    <c:forEach begin="${loopO.index}" end="${loopO.index+9}" items="${oBeanEL.sharesSet}" var="one" varStatus="loopM"><td>
      <table><!-- ◆ １口分 ◆ -->
        <tr><th colspan="3">S${loopM.index + 1 }</th></tr>
        <c:forEach items="${one}" varStatus="loopIn">
        <tr>
          <td><c:if test="${one[loopIn.index] == C.match1Win}"  >${one[loopIn.index]}</c:if></td>
          <td><c:if test="${one[loopIn.index] == C.match0Other}">${one[loopIn.index]}</c:if></td>
          <td><c:if test="${one[loopIn.index] == C.match2Lose}" >${one[loopIn.index]}</c:if></td>
        </tr>
        </c:forEach>
      </table>
    </td></c:forEach>
  </tr></c:forEach>
</table>

<div id="resultLoto">
  <table id="resultMiniLoto"><!-- ◆ MiniLoto ◆ -->
    <tr><td colspan="6">Mini Loto</td></tr>
    <c:forEach begin="0" end="${oBeanEL.sharesMini.size()}" items="${oBeanEL.sharesMini}" var="one" varStatus="loop5">
    <tr>
      <th>S${loop5.index + 1}</th>
      <td>${one[0]}</td><td>${one[1]}</td><td>${one[2]}</td><td>${one[3]}</td><td>${one[4]}</td>
    </tr>
    </c:forEach>
  </table>
  <table id="resultLoto6"><!-- ◆ Loto6 ◆ -->
    <tr><td colspan="7">Loto6</td></tr>
    <c:forEach begin="0" end="${oBeanEL.sharesSix.size()}" items="${oBeanEL.sharesSix}" var="one" varStatus="loop6">
    <tr>
      <th>S${loop6.index + 1}</th>
      <td>${one[0]}</td><td>${one[1]}</td><td>${one[2]}</td><td>${one[3]}</td><td>${one[4]}</td><td>${one[5]}</td>
    </tr>
    </c:forEach>
  </table>
  <table id="resultLoto7"><!-- ◆ Loto7 ◆ -->
    <tr><td colspan="8">Loto7</td></tr>
    <c:forEach begin="0" end="${oBeanEL.sharesSeven.size()}" items="${oBeanEL.sharesSeven}" var="one" varStatus="loop7">
    <tr>
      <th>S${loop7.index + 1}</th>
      <td>${one[0]}</td><td>${one[1]}</td><td>${one[2]}</td><td>${one[3]}</td><td>${one[4]}</td><td>${one[5]}</td><td>${one[6]}</td>
    </tr>
    </c:forEach>
  </table>
</div>

<script type="text/javascript">
//初期設定
function initExp(){
	bSetProperVal("${oBeanEL.count}", 99, bGetObjByNm("${C.prmCount}")[0]);
	bGetFormElem("${acro}", "${C.prmItemRD}").value = "${oBeanEL.itemRD}";

	//結果エリアの表示設定
	if(0 < "${oBeanEL.sharesSet.size()}"){ bSetDisplayAreas(new Array("hr,block", "resultToto,block")); }//Toto
	var areas2 = new Array(); //Loto
	if(0 < "${oBeanEL.sharesMini.size()}" ){ areas2.push("resultMiniLoto,inline"); }
	if(0 < "${oBeanEL.sharesSix.size()}"  ){ areas2.push("resultLoto6,inline"); }
	if(0 < "${oBeanEL.sharesSeven.size()}"){ areas2.push("resultLoto7,inline"); }
	if(0 < areas2.length){
		areas2.push("resultLoto,block", "hr,block");
		bSetDisplayAreas(areas2);
	}
}

initExp(); //初期設定
</script>
<!-- ◆ ${srvltPath} END ◆ -->