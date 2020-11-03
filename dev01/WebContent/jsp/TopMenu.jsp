<!-- ◆ ${srvltPath} START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">bCheckSrvltPath("${srvltPath}", "TopMenu");</script>

<div class="TopMenu">

<b id="srvltTtl1">◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtCB}', 'POST', 'frmC')">Action NG</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtCB}', 'GET',  ''    )">GET</a>
<hr>

<b id="srvltTtl2">◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtSC}', 'POST', 'frmY')">Action NG</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtSC}', 'GET',  ''    )">GET</a>
<hr>

<b id="srvltTtl3">◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtEL}', 'POST', 'frmE2')">Action NG</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtEL}', 'GET',  ''     )">GET</a>
<hr>

<b id="srvltTtl4">◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtMP}', 'POST', 'MPx')">Action NG</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtMP}', 'GET',  ''     )">GET</a>
<hr>

<b id="srvltTtl5">◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtDBO}', 'POST', 'DBO')">Action NG</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtDBO}', 'GET',  ''   )">GET</a>
<hr>

<b id="srvltTtl6">◆ 異常系 ◆</b><br>
　<a href="javascript: bExec('${acro}', '/dev01/Empty',  'POST', '')">Not Found</a>
　<a href="javascript: bExec('${acro}', '${C.cntxtDmy}', 'GET',  '')">RuntimeException</a>
　<a href="/dev01/jsp/TopMenu.jsp"  >TopMenu.jsp</a>
　<a href="/dev01/jsp/Base.jsp"     >Base.jsp</a>
　<a href="/dev01/jsp/CheckBox.jsp" >CheckBox.jsp</a>
<hr>

<b id="srvltTtl6">◆ Dummy ◆</b><br>
　<a href="javascript: bExec('${acro}', '${C.cntxtDmy}', 'POST', '')">Dummy</a>

</div>

<script type="text/javascript">
bInitTopMenu("srvltTtl"); //初期設定
</script>
<!-- ◆ ${srvltPath} END ◆ -->