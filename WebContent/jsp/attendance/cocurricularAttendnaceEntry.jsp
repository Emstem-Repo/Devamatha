<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}

function searchAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value = "cancelAttendanceSearch";
	document.attendanceReentryForm.submit();
}

function selectAll(obj) {
	value = obj.checked;
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		inputObj.checked = value;
		}
    }
}

function unCheckSelectAll() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox' && inputObj.id != "checkAll") {
	   		checkBoxOthersCount++;
	   		if(inputObj.checked) {
	   			checkBoxOthersSelectedCount++;
	   		}	
		}
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
    	document.getElementById("checkAll").checked = false;
    } else {
    	document.getElementById("checkAll").checked = true;
    }        
}

function cancelAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value ="cancelSelectedAttendance";
	document.attendanceReentryForm.submit();
}


</script>

<html:form action="/cocurricularAttendnaceEntry" method="post">

<html:hidden property="formName" value="cocurricularAttendnaceEntryForm" />
<html:hidden property="method" styleId="method" value="initCocurricularAttendnaceEntrySecondPage"/>
<html:hidden property="pageType" value="1"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendance.cocurricular.label"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendance.cocurricular.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
	                       
	                             <tr>
	                             <td class="row-even" colspan="4" align="center">
				                	<div align="center">
					                <html:radio name="cocurricularAttendnaceEntryForm" property="oprationMode" styleId="optional_2" value="edit" /> Edit Application &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                <html:radio name="cocurricularAttendnaceEntryForm" property="oprationMode" styleId="optional_3" value="approve" /> Approve Application
				                	</div>
				                	</td>
				                	
	                             </tr>
					        	<tr>
			                       <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="cocurricularAttendnaceEntryForm" property="year"/>"/>
				                           <html:select property="year" styleId="academicYear" name="cocurricularAttendnaceEntryForm" styleClass="combo" onchange="getClasses(this.value)">
		                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
		                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
		                       			   </html:select>
				        			</td>
				                  <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select name="cocurricularAttendnaceEntryForm" styleId="classes" property="classes">
					                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					                 	    <c:if test="${classMap != null}">
					       		    		<html:optionsCollection property="classMap" label="value" value="key"/>
					       		    		</c:if>
					                  </html:select>
				                  </td>
				                </tr>
				                <tr>
				                	<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.fromdate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="cocurricularAttendnaceEntryForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'cocurricularAttendnaceEntryForm',
								// input name
								'controlname' :'fromDate'
							});
						</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text property="toDate" styleId="toDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'cocurricularAttendnaceEntryForm',
											// input name
											'controlname' :'toDate'
										});
									</script>
									</td>
				                </tr>
				                <tr>
<!--				                	<td class="row-even" colspan="2">-->
<!--				                	<html:radio name="cocurricularAttendnaceEntryForm" property="appNo" styleId="optional_1" value="1"/> App. No-->
<!--					                <html:radio name="cocurricularAttendnaceEntryForm" property="appNo" styleId="optional_2" value="2"/> Roll No-->
<!--					                <html:radio name="cocurricularAttendnaceEntryForm" property="appNo" styleId="optional_3" value="3"/> Reg. No-->
<!--				                	</td>-->
				                	<td class="row-odd"><div align="right" ><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.pettycash.rollNo" />:</div></td>
				                	<td class="row-even"><html:text property="regNo" size="15" maxlength="15"></html:text> </td>
				                </tr>
				               
	                       </table>
	                       </td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="right">
                       <html:submit styleClass="formbutton" value="Search"></html:submit>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
                   </table>
                   </td>
                </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>