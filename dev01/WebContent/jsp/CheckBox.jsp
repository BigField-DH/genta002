<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "CheckBox"); </script>

<div class="cbCond">
  <table>
    <tr><td>◆${C.prmCount}</td><td><input type="text" value="" maxlength="2"                name="${C.prmCount}" size="13" /> (半角数字)</td></tr>
    <tr><td>◆Selected</td>     <td><input type="text" value="${oBeanCB.inSelected}" maxlength="15" name="Selected" size="13" /> (半角数字、CSV)</td></tr>
    <tr><td>◆notSIM</td>       <td><input type="text" value="${oBeanCB.inNotSim}"   maxlength="15" name="notSIM"   size="13" /> (半角数字、CSV)　　</td></tr>
    <tr><td></td><td class="btn"><a href="javascript: cbExec('${C.actCreate}');">${C.actCreate}</a></td></tr>
  </table>
  <div id="cbArea">
    ◆クリア対象 (いずれか必須)<br>
    <label><input type="checkbox" name="${C.prmItemCB}" value="${C.cbSim}"   />${C.cbSim}</label>
    <label><input type="checkbox" name="${C.prmItemCB}" value="${C.cbCheck}" />${C.cbCheck}</label>
    <a href="javascript: cbExec('${C.actClear}');">${C.actClear}</a>
  </div>
</div>

<hr id="hr">
<table id="cbTable">
  <tr>
    <th>SIM</th>
    <th class="col">LPID</th>
    <td><a href="javascript: setDisplayRows('table-row', -1);">＋</a></td>
    <td><a href="javascript: setDisplayRows('none',      -1);">－</a></td>
  </tr>

  <!-- ◆ 蛇腹の見出し ◆ -->
  <c:forEach begin="0" end="${oBeanCB.subList.size()}" step="10" items="${oBeanCB.subList}" varStatus="loopOut">
  <tr id="cbTtl${loopOut.index + 1}">
    <td class="col bgc" colspan="2">${loopOut.index + 101} ～
      <c:if test="${loopOut.index + 10 < oBeanCB.subList.size()}">${loopOut.index + 110}</c:if>
      <c:if test="${oBeanCB.subList.size() <= loopOut.index + 10}">${oBeanCB.subList.size() + 100}</c:if>
    </td>
    <td><a href="javascript: setDisplayRows('table-row', ${loopOut.index + 1});">＋</a></td>
  </tr>

  <!-- ◆ テーブル本体 ◆ -->
  <c:forEach begin="${loopOut.index}" end="${loopOut.index + 9}" items="${oBeanCB.subList}" var="one" varStatus="loop">
  <tr id="cbTr${loop.index + 1}">
    <th>${one[1]}<input type="hidden" name="sim" value="${one[1]}" /></th>
    <td class="col"><label>
      <input style="padding-right:5px;" type="checkbox" name="${C.prmCbox}" value="${one[0]}" onchange="checkCB(${loop.index})"
        <c:if test="${one[2]}">checked</c:if>
        <c:if test="${one[1] == '●'}">disabled</c:if> />${one[0]}
    </label></td>
    <td><c:if test="${loop.index % 10 == 0}"><a href="javascript: setDisplayRows('none', ${loopOut.index + 1});">－</a></c:if></td>
  </tr>
  </c:forEach>
  </c:forEach>
</table>

<script type="text/javascript">
//メンバー変数、定数
var mCheckedLpIds = (!bIsEmpty("${oBeanCB.lpIdsCsv}")) ? "${oBeanCB.lpIdsCsv}".split(",") : new Array(); //チェックされたLPIDのリスト
const mListSize = "${oBeanCB.subList.size()}"; //テーブルの明細件数

//初期設定
function initCB(){
	bSetProperVal("${oBeanCB.count}", 99, bGetObjByNm("${C.prmCount}")[0]);
	bInitCB(mListSize, new Array("cbTable,inherit", "hr,inherit", "cbArea,table-cell"),
			bGetObjByNm("${C.prmCbox}"), bGetObjByNm("sim"), mCheckedLpIds);
}

initCB(); //初期設定

//CheckBox変更時の設定
function checkCB(idx){ mCheckedLpIds = bCheckCB(mCheckedLpIds, bGetObjByNm("${C.prmCbox}"), idx, bGetObjByNm("sim")); }

//行の表示設定
function setDisplayRows(status, rowNum){ bSetDisplayRows(mListSize, status, rowNum, "cbTr", "cbTtl"); }

//入力チェック、FORM実行
function cbExec(action){
	if(action == "${C.actCreate}"){
		if( !bIsValidInputCreate( new Array("${C.prmCount}", "Selected", "notSIM") ) ){ return; }
	} else {
		if( !bIsValidInputClear( bGetObjByNm("${C.prmItemCB}"), bGetObjById("cbArea") ) ){ return; }
	}
	bExec("${acro}", "${C.cntxtCB}","POST",action);
}
</script>
<!-- ◆ ${srvltPath} END ◆ -->