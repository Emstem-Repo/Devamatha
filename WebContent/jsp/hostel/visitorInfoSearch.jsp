<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function resetMessages() {
	resetFieldAndErrMsgs();
	}
function checkRadio(field){
	alert(field);
	alert(field.value);
	if(field.checked){
		field.value="true";
		alert(field.value);
	}
}
function submitForm(){
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
		   		if(checkBoxselectedCount == 0){
	   			checkBoxselectedCount++;
		   		}
		   		else{
			   		alert("you can select only one");
			   		inputObj.checked=false;
			   	}
		   	}
		}
    }
    if(checkBoxselectedCount == 0) {
        document.getElementById("errorMessage").innerHTML = "Please select at least one record.";
    }    
    else { 
	document.location.href = "hostelVisitorInfo.do?method=getSelectedMember";
    }
}
function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
		   		if(checkBoxselectedCount == 0){
	   			checkBoxselectedCount++;
		   		}
		   		else{
			   			alert("you can select only one");
			   		inputObj.checked=false;
			   	}
		   	}
		}
    }
}
function cancelAction(){
	document.location.href = "hostelVisitorInfo.do?method=initVisitorInfo";
}
</script>
<html:form action="/hostelVisitorInfo">
	<html:hidden property="method" styleId="method" value="getSelectedMember" />	
	<html:hidden property="formName" value="visitorInfoForm" />
	<table width="98%" border="0">
	  <tr>
	    <td><span class="heading"><a href="#" class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.visitor.display"/>&gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	     <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" ></td>
	        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left" class="heading_white"><bean:message key="knowledgepro.hostel.visitor.display"/></div></td>
	        <td width="11" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="10" class="heading">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
			<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news">
			<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
			<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
			</html:messages> </FONT></div>
			</td>
			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		</tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="10" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5"></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5"></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td width="22%" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.student.name"/></td>
	                  <td width="22%" class="row-odd" align="center"><bean:message key="knowledgepro.hostel.name"/></td>
	                  <td width="22%"class="row-odd" align="center"><bean:message key="knowledgepro.hostel.floorno"/> </td>
	                  <td width="25%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hostel.roomno"/></td>
	                  <td width="9%" class="row-odd" align="center"><bean:message key="knowledgepro.hostel.visitor"/></td>
	                  </tr>
	                <nested:notEmpty name="visitorInfoForm" property="list">
	               <nested:iterate id="visitor" name="visitorInfoForm" property="list" indexId="count">
	                <tr class="row-even">
	                  <td align="center" class="bodytext">
	                  <bean:write name="visitor" property="studentName"/>
	                  </td>
	                  <td align="center" class="bodytext"><bean:write name="visitor" property="hostelType"/></td>
	                  <td align="center"><bean:write name="visitor" property="floorNo"/></td>
	                  <td height="25" align="center" ><bean:write name="visitor" property="roomNo"/></td>
	                  <td align="center" >
	                  <input type="checkbox" id="<c:out value='${count}'/>" name="list[<c:out value='${count}'/>].selected" onclick="validateCheckBox()"/>
	                  </td>
	                  </tr>
	                  </nested:iterate>
	                  </nested:notEmpty>
	                 </table>
	             </td>
	            <td  background="images/right.gif" width="5" height="54"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" ></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="10" class="heading">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
	        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="48%" height="35"><div align="right">
	                 <html:submit styleClass="formbutton" onclick="submitForm()">
				<bean:message key="knowledgepro.submit" />
			</html:submit>
	              </div></td>
	              <td width="2%"></td>
	              <td width="50%"><input name="Reset" type="reset" class="formbutton" value="Reset"/> &nbsp; <html:button property="" value="Cancel" onclick="cancelAction()" styleClass="formbutton"></html:button> </td>
	            </tr>
	        </table>
	         
	        </td>
	        <td width="11" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" ></td>
	        <td width="100%"  background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>