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
	
	document.getElementById("method").value = "modifyAttendanceSearch";
	document.attendanceEntryForm.submit();
}

function editAttendance(id) {
	document.getElementById("attendanceId").value=id;
	document.getElementById("method").value ="modifyAttendance";
	document.attendanceEntryForm.submit();
}

</script>

<html:form action="/AttendanceEntryForStaff" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value="modifyAttendanceSearch"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="classSelectedIndex" styleId="classSelectedIndex"/>
<html:hidden property="attendanceId" styleId="attendanceId"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendanceentry.modifyattendance"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendanceentry.modifyattendance"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
					                  <html:select name="attendanceEntryForm" styleId="classes" property="classes" size="5" style="width:120px" multiple="multiple">
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
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                     <tr>
                       <td width="45%"><div align="right">
	              	 		<html:button property="" styleClass="formbutton" value="Search" onclick="searchAttendance()"></html:button>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                     
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
		                       <td width="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		                       <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendanceentry.subject"/></div></td>
		                       <td width="12%" class="row-odd" ><div align="center">Teachers</div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.cancelattendance.periods"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.cancelattendance.batches"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center">Edit</div></td>
		                   </tr>
	                       <c:set var="temp" value="0"/>
	                       <logic:iterate id="attendance" name="attendanceEntryForm" property="attendanceList" type="com.kp.cms.to.attendance.AttendanceTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr>
				                       <td class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-even" ><div align="center"><bean:write property="subject" name="attendance"/></div></td>
				                       <td class="row-even" ><div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div></td>
				                       <td class="row-even" ><div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td>
				                       <td class="row-even" ><div align="center"><bean:write property="batch" name="attendance"/></div></td>
				                       <td class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editAttendance('<bean:write name="attendance" property="id"/>')"></div></td>
				                   </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                    <tr>
			             			   <td class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
			             			   <td class="row-white" ><div align="center"><bean:write property="subject" name="attendance"/></div></td>
				                       <td class="row-white" ><div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div></td>
				                       <td class="row-white" ><div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td>
				                       <td class="row-white" ><div align="center"><bean:write property="batch" name="attendance"/></div></td>
				                       <td class="row-white" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editAttendance('<bean:write name="attendance" property="id"/>')"></div></td>
	                           </tr>
	                    		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </logic:iterate>
	                      <tr>
						   <td height="2" colspan="6" class="row-even" ><div align="center"></div></td>
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