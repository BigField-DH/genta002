<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.amCond { margin:0px 0px 0px 10px; display:table; }
.amCond td { border:solid 1px; padding:0px 4px; }
</style>
<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "AtndMgt");</script>

<table class="amCond">
  <tr>
    <td>年</td><td>月</td><td>勤怠情報</td><td>契約情報</td><td>休憩情報</td><td>休日情報</td>
  </tr>
  <tr>
    <td><input type="text" name="${C.prmCondYear}"  maxlength="4"  size="2" /></td>
    <td><input type="text" name="${C.prmCondMonth}" maxlength="2"  size="1" /></td>
    <td><input type="radio" name="${C.prmItemRD}" value="${C.rd0Atnd}"    /></td>
    <td><input type="radio" name="${C.prmItemRD}" value="${C.rd1Contr}"   /></td>
    <td><input type="radio" name="${C.prmItemRD}" value="${C.rd2Recess}"  /></td>
    <td><input type="radio" name="${C.prmItemRD}" value="${C.rd3Holiday}" /></td>
    <td style="border:solid 0px;">　<input type="button" onClick="bExec('${acro}', '${C.cntxtAM}',  'POST', '${C.actSearch}')" value="${C.actSearch}" /></td>
  </tr>
</table>


<hr>

<hr id="hr">

<div id="amContr">
  ${oBeanAM.condYear}年契約情報
  <table>
    <tr><td>月</td><td>単価</td><td>上限</td><td>下限</td><td>超過</td><td>控除</td><td>売上</td><td>日時</td></tr>
    <c:forEach begin="0" end="${oBeanAM.contrList.size()}" items="${oBeanAM.contrList}" var="one" varStatus="loop">
    <tr>
      <td>${one[1]}月</td>
      <td><input type="text" name="${C.prmCbox}"    value="${one[2]}" /></td>
      <td><input type="text" name="${C.prmUpdCol1}" value="${one[3]}" /></td>
      <td><input type="text" name="${C.prmUpdCol2}" value="${one[4]}" /></td>
      <td><input type="text" name="${C.prmUpdCol3}" value="${one[5]}" /></td>
      <td><input type="text" name="${C.prmUpdCol3}" value="${one[6]}" /></td>
      <td><input type="text" name="${C.prmUpdCol3}" value="${one[7]}" /></td>
      <td><input type="text" name="${C.prmUpdCol3}" value="${one[8]}" /></td>
    </tr>
    </c:forEach>
  </table>
</div>

<script type="text/javascript">
function initDBO(){ //◆初期設定
	//検索条件欄
	bGetFormElem("${acro}", "${C.prmItemRD}").value = "${oBeanAM.rdPurpose}";
	bGetObjByNm("${C.prmCondYear}")[0].value  = "${oBeanAM.condYear}";
	bGetObjByNm("${C.prmCondMonth}")[0].value = "${oBeanAM.condMonth}";


	//登録条件欄
	//エリア表示
}
initDBO(); //◆初期設定 実行


</script>
<!-- ◆ ${srvltPath} END ◆ -->