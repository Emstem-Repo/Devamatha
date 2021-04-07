$(document).ready(function() {
	  $("#div_1").show();
	  $("#div_2").hide();
	  $("#div_3").hide();
	  $("#div_4").hide();
	  $("#div_5").hide();
	  $("#div_6").hide();
	  $("#btnMoveRightTab").hide();
	  finalMethod();
});

function finalMethod(){
	var tabNum = $("#tabNo").val();
	var oldTabNo = $("#oldTabNo").val();
	$("#li_1").removeClass("active-tab");
	$("#li_1").addClass("inactive-tab");
	$("#div_1").hide();
	$("#li_"+tabNum).addClass("inactive-tab");
	$("#li_"+oldTabNo).addClass("inactive-tab");
	$("#div_"+oldTabNo).hide();
	$("#div_"+tabNum).hide();
	$("#li_"+tabNum).removeClass("inactive-tab");
	$("#li_"+tabNum).addClass("active-tab");
	$("#div_"+tabNum).show();
	var mode = $("#mode").val();
	if (mode != null && mode != "") {
		$("#update").show();
		$("#save").hide();
	} else {
		$("#save").show();
		$("#update").hide();
	}
}
function nextBtn(){
	var totTab = $("#totalTabs").val();
	var tabNum = $("#tabNo").val();
	document.getElementById("oldTabNo").value = tabNum;
	$("#div_"+tabNum).hide();
	$("#li_"+tabNum).removeClass("active-tab");
	$("#li_"+tabNum).addClass("inactive-tab");
	if (totTab==5 && tabNum ==4) {
		tabNum = parseInt(tabNum)+2;
	} else {
		tabNum = parseInt(tabNum)+1;
	}
	$("#div_"+tabNum).show();
	$("#li_"+tabNum).addClass("active-tab");
	if (tabNum == 6) {
		$("#btnMoveRightTab").hide();
		var mode = $("#mode").val();
		if (mode != null && mode != "") {
			$("#update").show();
			$("#save").hide();
		} else {
			$("#save").show();
			$("#update").hide();
		}
		$("#close").show();
	}
	document.getElementById("tabNo").value = tabNum;
}
function showAndHide(val){
	document.getElementById("tempSelectChoice").value = val;
	if (val == "NI") {
		$("#NIO").show();
		$("#OTHER").hide();
		$("#btnMoveRightTab").hide();
		var mode = $("#mode").val();
		if (mode != null && mode != "") {
			$("#update").show();
			$("#save").hide();
		} else {
			$("#save").show();
			$("#update").hide();
		}
		$("#close").show();
	} else {
		$("#NIO").hide();
		$("#OTHER").show();
		$("#save").hide();
		$("#update").hide();
		$("#close").hide();
		$("#btnMoveRightTab").show();
	}
}
function goToFirstPage() {
	document.getElementById("method").value = "studentRegistrationFirstPage";
	document.onlineApplicationForm.submit();
}
function changeEditors(id){
	var tabNum = $("#tabNo").val();
	$("#div_"+tabNum).hide();
	$("#li_"+tabNum).removeClass("active-tab");
	$("#li_"+tabNum).addClass("inactive-tab");
	if(id == 1){
		$("#div_"+id).show();
		$("#li_"+id).addClass("active-tab");
		$("#btnMoveRightTab").show();
		$("#save").hide();
		$("#update").hide();
		$("#close").hide();
		}else if(id == 6){
			$("#div_"+id).show();
			$("#li_"+id).addClass("active-tab");
			$("#btnMoveRightTab").hide();
			var mode = $("#mode").val();
			if (mode != null && mode != "") {
				$("#update").show();
				$("#save").hide();
			} else {
				$("#save").show();
				$("#update").hide();
			}
			$("#close").show();
		}else{
			$("#div_"+id).show();
			$("#li_"+id).addClass("active-tab");
			$("#btnMoveRightTab").show();
			$("#save").hide();
			$("#update").hide();
			$("#close").hide();
		}
		document.getElementById("tabNo").value = id;
	document.getElementById("oldTabNo").value = id;
}