<!-- ◆ Index START ◆ -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html" charset="UTF-8">
<link rel="icon" href="${C.cntxtFavicon}" />
<link rel="stylesheet" href="${C.cntxtCssBase}" type="text/css">
</head>

<script type="text/javascript" src="${C.cntxtJsBase}" charset="utf-8"></script>
<script type="text/javascript">
if("<%= request.getRequestURI() %>" != "${C.cntxtJspIndex}"){ location.replace("${C.cntxtJspIndex}"); }
<% response.setCharacterEncoding("UTF-8"); %>
</script>

<body class="indexJsp">

<div class="ttl">
  <B id="pageTtl">◆</B>
  <a href="${C.cntxtTM}">Top Menu</a>
</div>

<hr>

<div>
  <div id="tRow1"></div><div id="tRow2"></div><div id="tRow3"></div><div id="tRow4"></div><div id="tRow5"></div>
</div>

<hr>

<% Throwable e = (Throwable)request.getAttribute("javax.servlet.error.exception"); %>
<div>
  <%= (e != null) ? "StackTrace :<br>" : "" %>
  <div id="stBody">
    <% if(e != null) {
      StackTraceElement[] st = e.getStackTrace();
      for(int i=0; i<st.length; i++){ out.print(e.getStackTrace()[i] + "\n"); }
    } %>
  </div>
</div>

</body>

<script type="text/javascript">
//初期設定
function initIndex(){
	<% Object objMsg = (String)request.getAttribute("javax.servlet.error.message"); %>
	bInitIndex(
		 "<%= (objMsg != null) ? objMsg.toString().split("\\r\\n")[0] : "" %>" //System Message
		,"<%= request.getAttribute("javax.servlet.error.exception_type") %>" //Exception Type
		,"<%= request.getRequestURI() %>"
		,"${baseMsg}"
		,<%= response.getStatus() %>
	);

	//to set StackTrace
	if(<%= (e != null) %>){ bSetDisplay("stBody", "block"); }

	<% session.invalidate(); %> //セッション破棄
}

initIndex(); //初期設定
</script>

</html>
<!-- ◆ Index END ◆ -->