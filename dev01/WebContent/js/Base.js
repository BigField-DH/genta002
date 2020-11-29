//定数
function bGetIntMin(){ return -2147483648; }
function bGetIntMax(){ return  2147483647; }
function bGetNum8Min(){ return -99999999; }
function bGetNum8Max(){ return  99999999; }
function bGetNmlStyle() { return "border:solid 1px; background:#FFFFFF;"; }
function bGetErrStyle() { return "border:solid 1px #FF0000; background:#FFE0E0;"; }
function bGetMsgRangeInt(){ return "[" + bGetIntMin() +"] から [" + bGetIntMax() +"] が有効"; }
function bGetMsgRangeNum8(){ return "[" + bGetNum8Min() +"] から [" + bGetNum8Max() +"] が有効"; }
function bGetMsgFormatDateTime(){ return "yyyy-mm-dd HH:MM:SS 形式で"; }
function bGetMsgFormatDate(){ return "yyyy-mm-dd 形式で"; }
function bGetMsgRangeDateTime(){ return "[1900-01-01 00:00:00.000] から [2199-12-31 23:59:59.999] が有効"; }
function bGetMsgRangeDate(){ return "[1900-01-01] から [2199-12-31] が有効"; }

//String Utility
function bIsEmpty(str){ return (str == null || typeof str === "undefined" || str.length == 0 || str == "null"); }

//数値範囲チェック
function bIsNumRange(inNum, lmtMin, lmtMax){ return lmtMin <= inNum && inNum <= lmtMax; }
function bIsNum8Range(inNum){ return bGetNum8Min() <= inNum && inNum <= bGetNum8Max(); }

//Integerの範囲内であること
function bIsIntRange(val){ return bIsNumRange(val, bGetIntMin(), bGetIntMax()); }

//数値範囲チェック (範囲外の場合は規定値を返却)
function bSetProperVal(num, bound, obj){
	if(num < 0){
		num = 0;
	} else if(bound < num){
		num = bound;
	}
	obj.value = num;
}

//適正遷移判定
function bCheckSrvltPath(srvltPath, jspName){
	var tmpMsg = "";
	if(bIsEmpty(srvltPath)){
		tmpMsg = "Invalid Access";
	} else if(!bIsEmpty(jspName) && ( jspName != srvltPath.substring(1) )){
		tmpMsg = "Inconsistent Access";
	}
	if(!bIsEmpty(tmpMsg)){
		sessionStorage.setItem("errMsg", tmpMsg);
		location.replace("/dev01/jsp/Index.jsp");
	}
}

//サーブレットパスをページ名 へ変換 (TRUE:◆あり)
function bGetPageName(srvlt, markFlg){
	if(bIsEmpty(srvlt)){ return "Error!"; }
	var ret = "";
	for(b=0; b<srvlt.length; b++){
		const c = srvlt.charAt(b);
		if(c == "/"){ continue; }
		if(/[A-Z]/.test(c)){ ret += " "; }
		ret += c;
	}
	return (markFlg) ? "◆" + ret + " ◆" : ret.trim();
}

//エリアの表示設定(ID指定)
function bSetDisplayAreas(areas){
	for(b=0; b<areas.length; b++){
		const one = areas[b].split(",");
		document.getElementById(one[0]).style.display = one[1];
	}
}
function bSetDisplay(id, status){ document.getElementById(id).style.display = status; }

//Headerメッセージ設定
function bSetHdrMsg(val, flg) {
	document.getElementById("hdrMsg").innerHTML = val;
	//document.getElementById("hdrMsg").textContent = val;
}

//textContent設定(ID指定)
function bSetTextContent(id, val){ document.getElementById(id).textContent = val; }

//innerHTML設定(ID指定)
function bSetInnerHTML(id, val){ document.getElementById(id).innerHTML = val; }

//Object取得(NAME指定)
/*function bGetObjByNms(names){
	var objs = new Array();
	for(var ii=0; ii<names.length; ii++){
		objs.push( document.getElementsByName(names[ii]) );
	}
	return objs;
} 今のところ使わない*/
function bGetObjByNm(name){ return document.getElementsByName(name); }

//Object取得(ID指定)
function bGetObjById(id){ return document.getElementById(id); }

//Form Elements取得
function bGetFormElem(id, name){ return document.forms[id].elements[name]; }

//FORM実行
function bExec(id, action, method, cmd){
	document.body.style.display = "none";
	var frmObj = document.forms[id];
	frmObj.action = action;
	frmObj.method = method;
	frmObj.elements["action"].value = cmd;
	frmObj.submit();
}

//サーブレットパスのリストを返却
function bGetPages(flg){
	var names = new Array(
		"/CheckBox", "/StringConvert", "/ExpectLottery", "/MathProcess", "/DbOperation", "/AtndMgt");
	if(flg){ names.unshift("TopMenu"); }
	return names;
}

//◆一括でNormalStyle設定
function bSetNmlStyleNms(names){ //NameObj[i]の名前リストを渡す
	bSetHdrMsg("");
	for(var i=0; i<names.length; i++){   //名前リストの要素数分ループ
		var obj = bGetObjByNm(names[i]); //NameObj取得
		for(var j=0; j<obj.length; j++){ obj[j].style = bGetNmlStyle(); } //NameObj[j]に設定
	}
}
//◆エラースタイル設定(チェック時)
function bSetErrStyle(msg, obj){ //メッセージと NameObj[i] or IdObj を渡す
	bSetHdrMsg(msg); obj.style = bGetErrStyle(); //NameObj[i] or IdObjに設定
}

//◆日付、時刻のチェック
function bIsValidDateTime(ymdHMSObj, title){
	const ymdHMS = ymdHMSObj.value;
	//形式チェック(yyyy-mm-dd HH:MM:SS)
	if( !ymdHMS.match(/^\d{4}[-]\d{2}[-]\d{2}[ ]\d{2}[:]\d{2}[:]\d{2}$/) ){ //形式チェック
		bSetErrStyle(title + ": " + bGetMsgFormatDateTime(), ymdHMSObj); return false;
	}
	//実在チェック([1900-01-01 00:00:00] ～ [2199-12-31 23:59:59])
	if( !bIsTrueDate(ymdHMS.split(" ")[0]) || !isTrueTime(ymdHMS.split(" ")[1]) ){ //yyyy-mm-dd、HH:MM:SS
		bSetErrStyle(title + ": " + bGetMsgRangeDateTime(), ymdHMSObj); return false;
	}
	return true;

	function isTrueTime(HMS){ //◆HH:MM:SS.fff の 実在チェック
		const inTime = HMS.split(/[:\.]/);
		return bIsNumRange(inTime[0], 00, 23) && //時
			bIsNumRange(inTime[1], 00,  59) && bIsNumRange(inTime[2], 00, 59); //分、秒
	}
}

//◆日付のチェック
function bIsValidDate(ymdObj, title){
	if( !ymdObj.value.match(/^\d{4}[-]\d{2}[-]\d{2}$/) ) { //形式チェック
		bSetErrStyle(title + ": " + bGetMsgFormatDate(), ymdObj); return false;
	}
	if( !bIsTrueDate(ymdObj.value) ){ //実在チェック
		bSetErrStyle(title + ": " + bGetMsgRangeDate(), ymdObj); return false;
	}
	return true;
}

//◆yyyy-mm-dd の 実在チェック
function bIsTrueDate(ymdValue){
	const inDate = ymdValue.split("-");
	const datum = new Date(inDate[0], inDate[1]-1, inDate[2]);
	return bIsNumRange(inDate[0], 1900, 2199) &&
			datum.getFullYear() == inDate[0] && datum.getMonth()+1 == inDate[1] && datum.getDate() == inDate[2];
}

// ◆ BaseJsp ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//Baseページの初期設定
function bInitBase(srvltPath, baseMsg, hdrTtl, ftrBtn){
	const ttl = bGetPageName(srvltPath, true);
	document.title = ttl;         //ページタイトル設定
	bSetTextContent(hdrTtl, ttl); //Headerタイトル設定

	bSetHdrMsg(baseMsg); //Headerメッセージ設定

	//Footer用ボタン名の設定
	const pageFT = bGetPages(true);
	for(i=0; i<pageFT.length; i++){ bGetObjByNm(ftrBtn)[i].value = bGetPageName(pageFT[i], false); }
}

// ◆ TopMenu ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//TopMenu初期設定
function bInitTopMenu(id){
	//srvlt title 設定
	const pageTP = bGetPages();
	for(i=0; i<pageTP.length; i++){ bSetTextContent(id+(i+1), bGetPageName(pageTP[i], true)); }
}

// ◆ CheckBox ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//初期設定
function bInitCB(listSize, areas, cboxObj, simObj, checkedLpIds){
	if(0 < listSize){
		bSetDisplayAreas(areas);
		bSetDisableCB(cboxObj, simObj, checkedLpIds);
	}
}

//チェック数に応じた活性/非活性の設定
function bSetDisableCB(cboxObj, simObj, checkedLpIds){
	for(i=0; i<cboxObj.length; i++){
		cboxObj[i].disabled = (3 <= checkedLpIds.length)
									? !cboxObj[i].checked //チェックなしのLPIDを非活性化
									: simObj[i].value == "●"; //SIMブロック以外のLPIDを活性化
	}
}

//チェック変更時の設定
function bCheckCB(checkedLpIds, cboxObj, idx, simObj){
	if(cboxObj[idx].checked){ //チェックON
		checkedLpIds.push(cboxObj[idx].value); //対象のLPID を checkedLpIds に追加
		checkedLpIds = Array.from(new Set(checkedLpIds)); //重複を削除
	} else { //チェックOFF
		if(checkedLpIds.includes(cboxObj[idx].value)){ //対象のLPIDが checkedLpIds に存在する場合
			checkedLpIds.splice(checkedLpIds.indexOf(cboxObj[idx].value), 1); //checkedLpIds から削除
		}
	}
	bSetDisableCB(cboxObj, simObj, checkedLpIds);
	return checkedLpIds;
}

//行の表示設定
function bSetDisplayRows(listSize, status, rowNum, cbTr, cbTtl){
	const status2 = (status == "none") ? "table-row" : "none";
	if(0 < rowNum){ //個別
		const remain = listSize - (rowNum + 9);
		for(i=0; i<((0 < remain) ? 10 : 10 + remain); i++){
			bSetDisplay(cbTr +(rowNum+i), status);
		}
		    bSetDisplay(cbTtl+rowNum,     status2);
	} else { //一括
		for(i=0; i<listSize; i++){   bSetDisplay(cbTr +(i+1), status); }
		for(i=0; i<listSize; i+=10){ bSetDisplay(cbTtl+(i+1), status2); }
	}
}

//Create時の入力チェック
function bIsValidInputCreate(names){ //Names を渡す
	bSetNmlStyleNms(names); //Styleを元に戻す
	for(var i=0; i<names.length; i++){
		const reg = (i==0) ? /[^0-9]/ : /[^0-9,]/;
		var obj = bGetObjByNm(names[i])[0];
		if(reg.test(obj.value)){ bSetErrStyle(names[i] + ": 入力形式を確認せよ", obj); return false; }
	}
	return true;
}

//Clear時の入力チェック
function bIsValidInputClear(itemCBObj, cbAreaObj){
	if(!itemCBObj[0].checked && !itemCBObj[1].checked) {
		bSetHdrMsg("クリア対象：１つは指定しろよ！");
		cbAreaObj.style = "color:#FF0000; display:table-cell;";
		return false;
	}
	return true;
}

// ◆ StringConvert ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//モールス変換(pps.value 2:Morse to String / 3:String to Morse)
function bMorseConvertSC(inStr, pps){
	const strArr = new Array( //Map Strings
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
		"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		" ", " "); //←これわざと
	const mrsArr = new Array( //Map Morse Codes`
		"!|", "|!!!", "|!|!", "|!!", "!", "!!|!", "||!", "!!!!", "!!", "!|||",
		"|!|", "!|!!", "||", "|!", "|||", "!||!", "||!|", "!|!", "!!!", "|",
		"!!|", "!!!|", "!||", "|!!|", "|!||", "||!!",
		"|||||", "!||||", "!!|||", "!!!||", "!!!!|", "!!!!!", "|!!!!", "||!!!", "|||!!", "||||!",
		"", " ");
	if(strArr.length != mrsArr.length) { bSetHdrMsg("Invalid Map Info"); return ""; }
	var keyArr; var valArr; //Key,Value for Map
	var inArr;  var inChar; var reg
	//Assignment of Keys & Values & Regular Expression
	if(pps.value == 2){ //"${C.rd2MrsToStr}"
		keyArr = mrsArr; valArr = strArr;
		inArr = inStr.split(" ");
		reg = /[ ]{2,}/g;
	} else {
		keyArr = strArr; valArr = mrsArr;
		inChar = inStr.toLowerCase();
		reg = /[ ]{3,}/g;
	}
	//to create Map Info
	const map = new Map();
	for(i=0; i<keyArr.length; i++) { map.set(keyArr[i], valArr[i]); }
	var out = "";
	//String to Morse
	for(i=0; !bIsEmpty(inChar) && i<inChar.length; i++){ out += map.get(inChar.charAt(i)) + " "; }
	//Morse to String
	for(i=0; (typeof inArr !== "undefined") && i<inArr.length; i++) {
		const val = map.get(inArr[i]);
		out += (typeof val === "undefined") ? "?" : val;
	}
	//to create Space-String (digits of pps.value-1) for Shaping
	var sp = "";
	for(i=0; i<pps.value-1; i++) { sp += " "; }
	pps.value = 0; //"${C.rd0Mrs}"
	return out.replaceAll(reg, sp); //Shaping for Output
}

//文字列反転
function bReverseSC(inStr){
	var out = "";
	for(i=inStr.length-1; 0<=i; i--){ //後ろから読み込む
		if(inStr.charAt(i) != " "){ out += inStr.charAt(i) + " "; }
	}
	return out;
}

// ◆ ExpectLottery ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆

// ◆ MathProcess ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//入力チェック
function bIsValidInputMP(numToCalcObj, lmtMin, lmtMax, mpHint){
	//入力値が範囲内であること
	if( !bIsNumRange(numToCalcObj.value, lmtMin, lmtMax) ){ bSetErrStyle(mpHint, numToCalcObj); return false; }
	return true;
}
// ◆ DbOperation ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//◆Insert入力チェック
function bIsValidInsertDBO(col1Obj, col2Obj, col3Obj){
	const col1val = col1Obj.value;
	if(!col1val.match(/^[0-9a-zA-Z]+$/)){
		bSetErrStyle("col1: 半角英数字(最大6桁)", col1Obj); return false;
	}
	if( bIsEmpty(col2Obj.value) || !bIsNum8Range(col2Obj.value) ){
		bSetErrStyle("col2: "+ bGetMsgRangeNum8(), col2Obj); return false;
	}
	if( !bIsEmpty(col3Obj.value) && !bIsValidDateTime(col3Obj, "col3") ){ return false; }
	return true;
}

//◆Update入力チェック
function bIsValidUpdateDBO(col2Obj, col3Obj){
	if( bIsEmpty(col2Obj.value) || !bIsNum8Range(col2Obj.value) ){
		bSetErrStyle("col2: "+ bGetMsgRangeNum8(), col2Obj); return false;
	}
	if( !bIsEmpty(col3Obj.value) && !bIsValidDateTime(col3Obj, "col3") ){ return false; }
	return true;
}

//◆Search入力チェック
function bIsValidSearchDBO(col1Obj, col2fObj, col2tObj, col3fObj, col3tObj){
	if( !bIsEmpty(col1Obj.value) && !col1Obj.value.match(/^[0-9a-zA-Z]+$/) ){
		bSetErrStyle("col1: 半角英数字で", col1Obj); return false;
	}
	if( !bIsEmpty(col2fObj.value) && !bIsNum8Range(col2fObj.value) ){
		bSetErrStyle("col2 from: "+ bGetMsgRangeNum8(), col2fObj); return false;
	}
	if( !bIsEmpty(col2tObj.value) && !bIsNum8Range(col2tObj.value) ){
		bSetErrStyle("col2 to: "+ bGetMsgRangeNum8(), col2tObj); return false;
	}
	if( !bIsEmpty(col3fObj.value) && !bIsValidDate(col3fObj, "col3 from") ){ return false; } 
	if( !bIsEmpty(col3tObj.value) && !bIsValidDate(col3tObj, "col3 to") ){ return false; }
	return true;
}

// ◆ IndexJsp ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆ ◆
//IndexJsp初期設定
function bInitIndex(sysMsg, exceptionType, reqUrl, baseMsg, status ){
	setValueDisplay("System Message :", sysMsg,        2);
	setValueDisplay("Exception Type :", exceptionType, 3);
	setValueDisplay("Request URL :",    reqUrl,        4);

	const sessionMsg= sessionStorage.getItem("errMsg");
	sessionStorage.removeItem("errMsg");
	const errMsg = (!bIsEmpty(baseMsg)      ? baseMsg      + " / " : "")
	              + (!bIsEmpty(sessionMsg  ) ? sessionMsg   + " / " : "");
	setValueDisplay("Error Message :", errMsg, 5);

	const info = ( bIsNumRange(status, 0, 299) && bIsEmpty(errMsg) );
	const pageTtl = "◆ "+ ((info) ? "INDEX" : "ErroInfo") + " ◆";
	document.body.style = (info) ? "color:#600000; background:#FFFBF0;" : "color:#A00000; background:#FFF0E0;";
	document.title = pageTtl;
	bSetTextContent("pageTtl", pageTtl);
	setValueDisplay("Status :", status, 1);

	//メッセージ欄設定
	function setValueDisplay(suffix, val, idx){
		if(!bIsEmpty(val)){
			bSetInnerHTML("tRow"+idx, '<div class="right">' + suffix + '&nbsp;</div><i>' + val + "</i>");
			bSetDisplay("tRow"+idx, "table-row");
		}
	}
}