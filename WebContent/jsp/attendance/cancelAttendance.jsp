<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
function searchAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value = "cancelAttendanceSearch";
	document.attendanceEntryForm.submit();
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
	document.attendanceEntryForm.submit();
}

</script>

<html:form action="/AttendanceEntry" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="classSelectedIndex" styleId="classSelectedIndex"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.cancelattendance"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.cancelattendance"/></strong></div></td>
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
				                  <td height="25" class="row-odd" width="120">
				                  <div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
				                  </td>
				                  <td class="row-even" width="130">
				                  <html:select property="attendanceTypeId" styleId="attendanceTypeId" name="attendanceEntryForm" styleClass="combo">
				                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				                  	<html:optionsCollection name="attendanceEntryForm" property="attendanceTypes" label="value" value="key"/>
				                  </html:select>
				                  </td>
				                  <td class="row-odd" width="90"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
				                  <td class="row-even" width="150">
				                  <table width="82" border="0" cellspacing="0" cellpadding="0">
				                    <tr>
				                      <td width="60">
				                      <html:text name="attendanceEntryForm" styleId="attendancedate" property="attendancedate" styleClass="TextBox"/>
				                      </td>
				                      <td width="40"><script language="JavaScript">
											new tcal ({
												// form name
												'formname': 'attendanceEntryForm',
												// input name
												'controlname': 'attendancedate'
											});</script></td>
				                    </tr>
				                  </table></td>
				                  <td class="row-odd" width="90">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
					               </td>
					               <td class="row-even" >
					                  <html:select name="attendanceEntryForm" styleId="classes" property="classes" size="5" style="width:200px" multiple="multiple">
					       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
					                  </html:select>
				                  </td>
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
	              	 		<html:button property="" styleClass="formbutton" value="Search" onclick="searchAttendance()"></html:button>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
                   </table>
                   </td>
                </tr>
                <tr>
                   <td height="35" colspan="6" >
                    <logic:notEmpty name="attendanceEntryForm" property="attendanceList">
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
	                       <tr >
	                           <td width="5%" class="row-odd"><div align="center"><input type="checkbox" id="checkAll" onclick="selectAll(this)">Select</div></td>
		                       <td width="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendanceentry.attendance"/></div></td>
		                       <td width="10%" class="row-odd" colspan="2"><div align="center">Teachers</div></td>
		                       <td width="10%" class="row-odd" colspan="2"><div align="center"><bean:message key="knowledgepro.attendanceentry.subject"/></div></td>
		                       <td width="10%" class="row-odd" style="display: none;"><div align="center"><bean:message key="knowledgepro.attendanceentry.activity"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.cancelattendance.periods"/></div></td>
		                       <td width="10%" class="row-odd" style="display: none;"><div align="center"><bean:message key="knowledgepro.cancelattendance.batches"/></div></td>
		                   </tr>
	                       <c:set var="temp" value="0"/>
	                       <nested:iterate id="attendance" name="attendanceEntryForm" property="attendanceList" type="com.kp.cms.to.attendance.AttendanceTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr><nested:hidden property="id"></nested:hidden>
									   <td height="25" class="row-even" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="checked" onclick="unCheckSelectAll()"/></div></td>
				                       <td class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-even" ><div align="center"><nested:write property="type"/></div></td>
				                       <td class="row-even" colspan="2"><div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div></td>
				                       <td class="row-even" colspan="2"><div align="center"><nested:write property="subject"/></div></td>
				                       <td class="row-even" style="display: none;"><div align="center"><nested:write property="activity"/></div></td>
				                       <td class="row-even" ><div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td>
				                       <td class="row-even" style="display: none;"><div align="center"><nested:write property="batch"/></div></td>
				                   </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                    <tr><nested:hidden property="id"></nested:hidden> 
			             			   <td height="25" class="row-white" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="checked" onclick="unCheckSelectAll()"/></div></td>
			             			   <td class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
			             			   <td class="row-white" ><div align="center"><nested:write property="type"/></div></td>
				                       <td class="row-white" colspan="2"><div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div></td>
				                       <td class="row-white" colspan="2"><div align="center"><nested:write property="subject"/></div></td>
				                       <td class="row-white" style="display: none;"><div align="center"><nested:write property="activity"/></div></td>
				                       <td class="row-white" ><div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td>
				                       <td class="row-white" style="display: none;"><div align="center"><nested:write property="batch"/></div></td>
	                           </tr>
	                    		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </nested:iterate>
	                      <tr >
            			<td style="height: 10px;" align="center" colspan="8">
            
           		
            			</td>
          				</tr>
	                     <tr>
						   <td height="25" colspan="8" class="row-white" ><div align="center">
						   <html:button property="" styleClass="formbutton" value="Cancel Attendance"  onclick="cancelAttendance()"></html:button>
						   </div></td>
	                      
	                     </tr>
	                   </table>
	                  
                   	 </td>
                       <td width="5"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                    </logic:notEmpty>
                   </td>
                 </tr>
               
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
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