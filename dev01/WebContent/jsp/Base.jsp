<!-- ◆ Base START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html" charset="UTF-8">
<link rel="icon" href="${C.cntxtFavicon}" />
<link rel="stylesheet" href="${C.cntxtCssBase}" type="text/css">
</head>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">
//遷移時点でExceptionならエラーページへ
<% Throwable e = (Throwable)request.getAttribute("javax.servlet.error.exception"); %>
if(<%= (e != null) %>){ alert("Throwable"); location.replace("${C.cntxtJspIndex}"); }

bCheckSrvltPath("${srvltPath}", ""); //サーブレットパスのチェック
</script>

<body class="baseStyle">
<!-- ◆ Header START ◆ -->
<div id="header"><b id="hdrTtl">◆</b><span id="hdrMsg">◆</span></div>
<!-- ◆ Header END ◆ -->

<!-- ◆ Main START ◆ -->
<%-- <form id="main"><input type="hidden" name="${C.prmAction}" value="" /> --%>
<form id="${acro}"><input type="hidden" name="${C.prmAction}" value="" />
<hr>
  <c:if test="${srvltPath != null && srvltPath != ''}">
    <jsp:include page="${srvltPath.substring(1)}.jsp" flush="false" />
  </c:if>
<%-- <%@ include file="TopMenu.jsp" %> ◆ for Debug --%>
<hr>
</form>
<!-- ◆ Main END ◆ -->

<!-- ◆ Footer START ◆ -->
<div id="footer">
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtTM}',  'POST',             '')" name="ftrBtn" value="" />
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtCB}',  'POST', '${C.actShow}')" name="ftrBtn" value="" />
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtSC}',  'POST', '${C.actShow}')" name="ftrBtn" value="" />
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtEL}',  'POST', '${C.actShow}')" name="ftrBtn" value="" />
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtMP}',  'POST', '${C.actShow}')" name="ftrBtn" value="" />
  　<input type="button" onClick="bExec('${acro}', '${C.cntxtDBO}', 'POST', '${C.actShow}')" name="ftrBtn" value="" />
</div>
<!-- ◆ Footer END ◆ -->
</body>

<script type="text/javascript">
//Baseページの初期設定
function initBase(){
	bInitBase("${srvltPath}", "${baseMsg.replaceAll('\"', '\'')}", "hdrTtl", "ftrBtn");

	<% session.removeAttribute("baseMsg"); %>
	<% if("GET".equals(request.getMethod())) session.invalidate(); %>
}

initBase(); //Baseページの初期設定
document.body.style.display = "block";
</script>

</html>
<!-- ◆ Base END ◆ -->