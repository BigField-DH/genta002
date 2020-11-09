<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style></style>
<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "DbOperation");</script>

<div class="dboCond">◆検索条件
  <table>
    <tr><td>col1 (%半角英数%)</td><td>col2 (from - to) (最大8桁)</td><td>col3 (from - to) (yyyy-mm-dd)</td></tr>
    <tr>
      <td><input type="text" name="${C.prmCondCol1}"  maxlength="6"  size="9"  />&nbsp;(最大6桁)</td>
      <td><input type="text" name="${C.prmCondCol2f}" maxlength="9"  size="9"  />&nbsp;～
          <input type="text" name="${C.prmCondCol2t}" maxlength="9"  size="9"  /></td>
      <td><input type="text" name="${C.prmCondCol3f}" maxlength="10" size="11" />&nbsp;～
          <input type="text" name="${C.prmCondCol3t}" maxlength="10" size="11" /></td>
    </tr>
  </table>
  <div class="spacer"></div>
  <table>
    <tr>
      <td style="padding:0px; border:none">◆ソート条件&nbsp;</td>
      <td><label><input type="radio" name="${C.prmSortRD1}" value="1" />col1</label></td>
      <td><label><input type="radio" name="${C.prmSortRD1}" value="2" />col2</label></td>
      <td><label><input type="radio" name="${C.prmSortRD1}" value="3" />col3</label></td>
      <td style="padding:0px 5px; border:none"></td>
      <td><label><input type="radio" name="${C.prmSortRD2}" value="ASC"  />ASC</label></td>
      <td><label><input type="radio" name="${C.prmSortRD2}" value="DESC" />DESC</label></td>
      <td style="border:none">　　<a href="javascript: dboExec('${C.actSearch}');">${C.actSearch}</a></td>
    </tr>
  </table>
</div>

<hr>

<div class="dboCond">
  <table>
<%--<tr>
      <td style="padding:0px; border:none">◆登録条件&nbsp;</td>
      <td>col1 <input type="text" name="${C.prmInsCol1}" maxlength="6"  size="9"  /></td>
      <td>col2 <input type="text" name="${C.prmInsCol2}" maxlength="9"  size="9"  /></td>
      <td>col3 <input type="text" name="${C.prmInsCol3}" maxlength="19" size="19" /></td>
      <td style="padding:0px; border:none">　<a href="javascript: dboExec('${C.actInsert}');">${C.actInsert}</a></td>
    </tr> --%>
    <tr>
      <td style="padding:0px; border:none">◆登録条件1&nbsp;</td>
      <td>col1 <input type="text" name="${C.prmInsCol1}" maxlength="6"  size="9"  /></td>
      <td>col2 <input type="text" name="${C.prmInsCol2}" maxlength="9"  size="9"  /></td>
      <td>col3 <input type="text" name="${C.prmInsCol3}" maxlength="19" size="19" /></td>
    </tr>
    <tr>
      <td style="padding:0px; border:none">◆登録条件2&nbsp;</td>
      <td>col1 <input type="text" name="${C.prmInsCol1}" maxlength="6"  size="9"  /></td>
      <td>col2 <input type="text" name="${C.prmInsCol2}" maxlength="9"  size="9"  /></td>
      <td>col3 <input type="text" name="${C.prmInsCol3}" maxlength="19" size="19" /></td>
      <td style="padding:0px; border:none">　<a href="javascript: dboExec('${C.actInsert}');">${C.actInsert}</a></td>
    </tr>
  </table>
</div>

<hr id="hr">

<div id="dboResultArea">
  <div class="dboCount">◆検索結果 ${oBeanDBO.subList.size()}件</div>
  <div id="dboBtn">
    　<a href="javascript: dboExec('${C.actUpdate}');">${C.actUpdate}</a>
    　<a href="javascript: bExec('${acro}', '${C.cntxtDBO}', 'POST', '${C.actDelete}');">${C.actDelete}</a>
    　<a href="javascript: copyValues();">copy</a>
  </div>
  <div class="spacer"></div>
  <table id="dboResult">
    <tr><td>No</td><td></td><td>col1</td><td>col2</td><td>col3</td><td></td></tr>
    <c:forEach begin="0" end="${oBeanDBO.subList.size()}" items="${oBeanDBO.subList}" var="one" varStatus="loop">
    <tr>
      <td class="dboRight">${loop.index + 1}</td>
      <td><input type="checkbox" name="${C.prmCbox}" value="${loop.index}" onClick="getCountChecked();" /></td>
      <td><input type="text" name="${C.prmUpdCol1}" value="${one[0]}" readonly       size="9"  /></td>
      <td><input type="text" name="${C.prmUpdCol2}" value="${one[1]}" maxlength="9"  size="9"  /></td>
      <td><input type="text" name="${C.prmUpdCol3}" value="${one[2]}" maxlength="19" size="19" /></td>
      <td><input type="radio" name="${C.prmItemRD}" value="${loop.index}" onClick="javascript: bSetDisplay('dboBtn', 'table-cell');" /></td>
    </tr>
    </c:forEach>
  </table>
</div>

<script type="text/javascript">
function initDBO(){ //◆初期設定
	//検索条件欄
	bGetObjByNm("${C.prmCondCol1}")[0].value  = "${oBeanDBO.condCol1}";
	bGetObjByNm("${C.prmCondCol2f}")[0].value = "${oBeanDBO.condCol2f}";
	bGetObjByNm("${C.prmCondCol2t}")[0].value = "${oBeanDBO.condCol2t}";
	bGetObjByNm("${C.prmCondCol3f}")[0].value = "${oBeanDBO.condCol3f}";
	bGetObjByNm("${C.prmCondCol3t}")[0].value = "${oBeanDBO.condCol3t}";
	bGetFormElem("${acro}", "${C.prmSortRD1}").value = "${oBeanDBO.sortCol}";
	bGetFormElem("${acro}", "${C.prmSortRD2}").value = "${oBeanDBO.order}";

	const action = "<%= request.getParameter("action") %>";
	//登録条件欄
	if("${C.actInsert}" == action){
		bGetObjByNm("${C.prmInsCol1}")[0].value = "${oBeanDBO.insCol1}";
		bGetObjByNm("${C.prmInsCol2}")[0].value = "${oBeanDBO.insCol2}";
		bGetObjByNm("${C.prmInsCol3}")[0].value = "${oBeanDBO.insCol3}";
	}

	//エリア表示
	if(!bIsEmpty("${oBeanDBO.subList.size()}")){
		var areas = new Array();
		areas.push("dboResultArea,block", "hr,block");
		if(0 < "${oBeanDBO.subList.size()}"){ areas.push("dboResult,block"); }
		bSetDisplayAreas(areas);
	}
}
initDBO(); //◆初期設定 実行

function dboExec(action){ //◆入力チェック、FORM実行
	bSetNmlStyleNms( new Array( //Style一括戻し
		"${C.prmInsCol1}",  "${C.prmInsCol2}",   "${C.prmInsCol3}",   "${C.prmUpdCol2}",   "${C.prmUpdCol3}"
	  , "${C.prmCondCol1}", "${C.prmCondCol2f}", "${C.prmCondCol2t}", "${C.prmCondCol3f}", "${C.prmCondCol3t}" ) );

	if(action == "${C.actInsert}"){ //登録 ★複数件対応
		if( !bIsValidInsertDBO( bGetObjByNm("${C.prmInsCol1}")[0]
							  , bGetObjByNm("${C.prmInsCol2}")[0], bGetObjByNm("${C.prmInsCol3}")[0] ) ){ return; }

	} else if(action == "${C.actUpdate}"){ //更新 ★複数件対応
		const cBoxObj = bGetObjByNm("${C.prmCbox}");
		var indexes = new Array();
		for(i=0; i<cBoxObj.length; i++){ if(cBoxObj[i].checked) indexes.push(cBoxObj[i].value); }
		console.log(indexes);

		for(i=0; i<indexes.length; i++){
			if( !bIsValidUpdateDBO( bGetObjByNm("${C.prmUpdCol2}")[indexes[i]], bGetObjByNm("${C.prmUpdCol3}")[indexes[i]] ) ){ return; }
		}
		//return;
	/*	const idx = bGetFormElem("${acro}", "${C.prmItemRD}").value;
		if( !bIsValidUpdateDBO( bGetObjByNm("${C.prmUpdCol2}")[idx], bGetObjByNm("${C.prmUpdCol3}")[idx] ) ){ return; }*/

	} else { //検索
		if( !bIsValidSearchDBO( bGetObjByNm("${C.prmCondCol1}")[0]
				, bGetObjByNm("${C.prmCondCol2f}")[0], bGetObjByNm("${C.prmCondCol2t}")[0]
				, bGetObjByNm("${C.prmCondCol3f}")[0], bGetObjByNm("${C.prmCondCol3t}")[0] ) ){ return; }
	}
	bExec("${acro}", "${C.cntxtDBO}", "POST", action); //実行
}

function copyValues(){ //◆検索結果欄から登録条件欄へ値をコピー ★複数件対応
	const idx = bGetFormElem("${acro}", "${C.prmItemRD}").value;
	bGetObjByNm("${C.prmInsCol1}")[0].value = bGetObjByNm("${C.prmUpdCol1}")[idx].value;
	bGetObjByNm("${C.prmInsCol2}")[0].value = bGetObjByNm("${C.prmUpdCol2}")[idx].value;
	bGetObjByNm("${C.prmInsCol3}")[0].value = bGetObjByNm("${C.prmUpdCol3}")[idx].value;
}

function getCountChecked(){
	const obj = bGetObjByNm("${C.prmCbox}");
	var cnt = 0; //チェックの個数
	for(i=0; i<obj.length; i++){ if(obj[i].checked) cnt++; } //チェックの個数をカウント
	for(i=0; i<obj.length; i++){ obj[i].disabled = (5<=cnt) ? !obj[i].checked : false; } //チェックなしを非活性に
	bSetDisplay("dboBtn", (1<=cnt) ? "table-cell" : "none"); //チェックあり → ボタン表示
	//console.log(obj.value + " , ");
}
</script>
<!-- ◆ ${srvltPath} END ◆ -->