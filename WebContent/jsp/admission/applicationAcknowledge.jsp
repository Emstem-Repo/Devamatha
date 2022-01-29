<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link href="styles.css" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function getName(appNo) { 
	//getNameByAppNo(appNo,"studentName",updateName);
	var received = document.getElementById("receivedThrough").value;
	var year=document.getElementById("year").value;
	document.location.href = "appAcknowledgement.do?method=getDetails&appNo="+appNo+"&receivedThrough="+received+"&year="+year;	
	
}

function checkAvailability(receivedThrough){
	checkReceivedThroughAvailability(receivedThrough,"errorMessage",displayMessage);
}
function displayMessage(req) {
	updateAvailableValue(req,"errorMessage"); 
}
function reActivate(){
	document.location.href = "receivedThrough.do?method=reactivateReceivedThrough";
}
 function cancelAction(){
	 document.location.href = "receivedThrough.do?method=initReceivedThrough";
 }
 function resetReceivedThrough(){
	 if(document.getElementById("method").value == "updateReceivedThrough"){
		 document.getElementById("receivedThrough").value=document.getElementById("origReceivedThrough").value;
		 document.getElementById("slipRequired").value=document.getElementById("origSlipRequired").value;
	 }
	 resetErrMsgs();
 }
 function resets(){
	 document.getElementById("receivedThrough").value="";
	  document.getElementById("slip1").checked = false;
	  document.getElementById("slip2").checked = false;
	 resetErrMsgs();
 }
 function saveAcknowledgement() {
		document.getElementById("method").value = "saveApplnAcknowledgement";	
		document.getElementById("flagId").value = "false";
		document.applicationAcknowledgeForm.submit();	
 }
 function saveAndPrintAcknowledgement() {
		document.getElementById("method").value = "saveApplnAcknowledgement";	
		document.applicationAcknowledgeForm.submit();	
 }
 function showButton(){
	 var tdl = document.getElementById("save&print");
 	tdl.style.display = 'block';
 }
 function hideButton(){
	 var tdl = document.getElementById("save&print");
 	tdl.style.display = 'none';
 }

 function getCourses(applnNo){
	 var year=document.getElementById("year").value;
	 getCoursesByApplnNo("coursesMap", applnNo,year,"course", updateCourses);
 }
 function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}

</script>
</head>
<body>
<html:form action="/appAcknowledgement">
<html:hidden property="flag" styleId="flagId" value="true"/>
<html:hidden property="formName" value="applicationAcknowledgeForm" />
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="method" styleId="method" value="addReceivedThrough" />
	<html:hidden property="slipRequred" styleId="slipRequred"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admission.applicationAcknowledge"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.applicationAcknowledge"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'>mandatoryfields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
           <tr>
           	 		<td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year"/>:
				    </div>
					</td>
					<td  class="row-even" width="25%">
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="applicationAcknowledgeForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo">
									<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
					</td>
                     <td class="row-odd" width="25%">
                     <div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.applicationno"/>: </div>
                     </td>
                     <td class="row-even" width="25%">
                           <html:text property="appNo" name="applicationAcknowledgeForm" maxlength="20" styleId="appNo" size="20" onblur="getName(this.value);"></html:text> 
                     </td>
              	
           </tr>
           <tr>
           	  <td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.course"/>:</div></td>
              <td  height="25" class="row-even" >
              <html:select property="courseId" styleClass="combo" styleId="course">
			   <html:option value=""> <bean:message key="knowledgepro.admin.select" /> </html:option>
                <logic:notEmpty property="coursesMap" name="applicationAcknowledgeForm">
	                   <html:optionsCollection name="applicationAcknowledgeForm" property="coursesMap" label="value" value="key"/>
					</logic:notEmpty>
		     </html:select>
              </td>   
                <td class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
                <td class="row-even" width="25%"><html:text property="name" name="applicationAcknowledgeForm" maxlength="50" styleId="studentName" size="40" ></html:text></td>
           </tr>
           <tr>
           			 <td class="row-odd" width="25%">
                     <div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.dateofbirth"/>:</div>
                     </td>
                     <td class="row-even" width="25%">
                        <table width="100" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td>
                      			<html:text name="applicationAcknowledgeForm" property="dob" styleId="dob" size="10" maxlength="16"/> 
                      		</td >
                      		<td width="2"><div align="left">
                      			<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'applicationAcknowledgeForm',
										// input name
										'controlname': 'dob'
									});
								</script></div>
							</td>
                    	</tr>
                	</table>  
                     </td>
           
           
                  <td  height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admission.applicationAcknowledge.date"/>:
	               </div>
	              </td>
                  <td  height="25" class="row-even" >
                	<table width="100" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td>
                      			<html:text name="applicationAcknowledgeForm" property="receivedDate" styleId="receivedDate" size="10" maxlength="16"/> 
                      		</td >
                      		<td width="2"><div align="left">
                      			<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'applicationAcknowledgeForm',
										// input name
										'controlname': 'receivedDate'
									});
								</script></div>
							</td>
                    	</tr>
                	</table>
                </td>
              
           </tr>
           <tr>
           <td class="row-odd" width="25%"> <div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.receivedThrough"/>:
                                    </div>
                    </td>
           <td  class="row-even" width="25%"> <html:text property="receivedThrough" name="applicationAcknowledgeForm" maxlength="50" styleId="receivedThrough" size="20" onchange="checkAvailability(this.value);" ></html:text>
           <div id="messageBox"></div>
            </td>
           <td class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admission.acknowledge.tracking.no"/>
                                   :</div></td>
           <td class="row-even" width="25%">
           <html:text property="trackingNo" name="applicationAcknowledgeForm" maxlength="50" styleId="trackingNo" size="20" ></html:text>
           </td>
           </tr>
           <tr>
                <td class="row-odd" width="25%">
                     <div align="right">
								      <bean:message key="knowledgepro.hostel.adminmessage.remarks"/>:
								    </div>
                     </td>
                     <td class="row-even" width="25%">
                           <html:textarea property="remarks" name="applicationAcknowledgeForm" styleId="remarks" ></html:textarea> 
                     </td>
            <td class="row-odd" width="25%"><span class="Mandatory">*</span> <bean:message key="admissionForm.mob.no.label"/>(Enter 10 digit Mobile Number excluding country code)</td>
            <td class="row-even" width="25%"><html:text property="mobileNo" name="applicationAcknowledgeForm" maxlength="50" styleId="mobileNo" size="20" ></html:text></td>
           </tr>
            <logic:equal value="true" name="applicationAcknowledgeForm" property="isPhotoRequired">
           <tr>
                    <td class="row-odd" width="25%" ><div align="right"><bean:message key="knowledgepro.application.acknoledge.photo.uploaded"/></div></td>
                    <td width="25%" height="25" class="row-even" >
                    <logic:equal value="true" name="applicationAcknowledgeForm" property="isPhotoUpload">
                   <B><bean:message key="knowledgepro.yes"/></B>
					</logic:equal>
					<logic:equal value="false" name="applicationAcknowledgeForm" property="isPhotoUpload">
                    <B><bean:message key="knowledgepro.no"/></B>
					</logic:equal>
					</td>
					<td class="row-odd" width="25%" ></td>
					<td class="row-even" width="25%" /></td>
					
          </tr>
          </logic:equal>
		</table>
	</td>
	<td width="5" height="30" background="images/right.gif"></td>
</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				     <tr>
				     <c:choose>
				     <c:when test="${not empty applicationAcknowledgeForm.applnTo}">
                        <td width="49%" height="35" align="right" ><html:button property="" styleClass="formbutton" value="Update" onclick="saveAcknowledgement()"/>
                        </td></c:when>
                        <c:otherwise>
                        <td width="49%" height="35" align="right" ><html:button property="" styleClass="formbutton" value="Save" onclick="saveAcknowledgement()"/>
                        </td>
                        </c:otherwise>
                        </c:choose>
                        
                        <td width="2%" ></td>
                        <c:choose>
                        <c:when test="${not empty applicationAcknowledgeForm.applnTo}">
						<td width="49%" height="35" align="left" ><html:button property="" styleClass="formbutton" value="Update & Print" onclick="saveAndPrintAcknowledgement()" styleId="save&print"/>
                        </td></c:when>
                        <c:otherwise>
                        <td width="49%" height="35" align="left" ><html:button property="" styleClass="formbutton" value="Save & Print" onclick="saveAndPrintAcknowledgement()" styleId="save&print"/>
                        </c:otherwise>
                        </c:choose>
                        <td width="2%" height="35" align="center"> </td>
                        <td width="49%" height="35" align="left">&nbsp;</td>
                     </tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					
					
					<td valign="top" class="news"><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
         
          <logic:notEmpty property="applnTo" name="applicationAcknowledgeForm"> <tr>
          
            <td ><img src="images/01.gif"  height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif"  height="5" /></td>
          </tr>
          <tr>
            <td   background="images/left.gif"></td>
            <td width="100%" valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.interview.Name"/> </div></td>
                    <td width="25%" height="25" class="row-even"><bean:write name="applicationAcknowledgeForm" property="applnTo.name" /></td>
                  </tr>
                  <tr>
                    <td height="25" class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.admission.receivedThrough"/></div></td>
                    <td width="25%" height="25" class="row-even" ><bean:write name="applicationAcknowledgeForm" property="applnTo.receivedThrough"/></td>
                  </tr>
                  <tr>
                    <td class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.admission.applicationAcknowledge.date"/> </div></td>
                     <td width="25%" height="25" class="row-even" ><bean:write name="applicationAcknowledgeForm" property="applnTo.receivedDate"/></td>
                    </tr>
                  <tr>
                    <td class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.hostel.reservation.remarks"/></div></td>
                    <td width="25%" height="25" class="row-even" ><bean:write name="applicationAcknowledgeForm" property="applnTo.remarks" /></td>
                  </tr>
              </table>
            </td>
             <td   background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif"  height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr></logic:notEmpty>
        </table></td>
        
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="10"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</html:form>
	<script type="text/javascript">
var print = "<c:out value='${applicationAcknowledgeForm.printReceipt}'/>";
if(print.length != 0 && print == "true") {
	var url ="appAcknowledgement.do?method=printReceiptAfterSave";
	myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	
}
var to = "<c:out value='${applicationAcknowledgeForm.applnTo}'/>";
if(to.length==0){
document.getElementById("receivedDate").value = "";

	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();
	if(day<10){
		day = "0"+day;
	}
	if(month<10){
		month = "0"+month;
	}
	if (document.getElementById('receivedDate').value == ''){
	document.getElementById('receivedDate').value = day + '/' + month + '/' + year;
	}
}
 		var slipRequired = "<c:out value='${applicationAcknowledgeForm.slipRequred}'/>";
 		if(slipRequired.length != 0 && slipRequired == "true"){
 	 		showButton();
 		}else{
 	 		hideButton();
 		}


 		var year = document.getElementById("tempyear").value;
 		if (year.length != 0) {
 			document.getElementById("year").value = year;
 		}
  if(document.getElementById("slipRequred").value == "true"){	
 	    	var tdl = document.getElementById("save&print");
 	    	var td2= document.getElementById("trackingNo");
 	    	tdl.style.display = 'block';
 	    	td2.disabled=true;
 	 }
 	 else{
 	    	var td1 = document.getElementById("save&print");
 	    	var td2= document.getElementById("trackingNo");
 	    	td1.style.display = 'none';
 	    	td2.disabled=false;
 	    }
   var setFocus = "<c:out value='${applicationAcknowledgeForm.setFocus}'/>";
   if(setFocus.length !=0 && setFocus == "true"){
	  
	 document.getElementById('course').focus();
   }
</script>
</body>
</html>