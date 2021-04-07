<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<html:form action="HostelAttendance" method="post">
	<html:hidden property="method" styleId="method"	value="saveAttendance" />
	<html:hidden property="formName" value="attendanceForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="id" styleId="id"/>
	<table width="99%" border="0">
	<tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key = "hostel.attendance.entry"/> &gt;&gt;</span></span></td>	  
	</tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key = "hostel.attendance.entry"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
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
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	              <tr >
	                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.name.col"/> </div></td>
	
	                <td width="21%" height="25" class="row-even" ><bean:write name = "attendanceForm" property="hostelName"/> </td>
	                <td width="21%" class="row-odd" ><div align="right"><bean:message key="hostel.attendance.group.name"/></div></td>
	                <td width="21%" class="row-even" ><bean:write name = "attendanceForm" property="groupName"/></td>
	              </tr>
	            </table></td>
	            <td width="5" background="images/right.gif"></td>
	          </tr>
	
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="25" class="heading"><strong><bean:message key="hostel.attendance.mark.for.absentees"/></strong></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="66" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
					<nested:iterate name="attendanceForm" id = "student" property="hostelGroupStudentList" indexId="count">
	                  <td width="6%" height="25" class="row-odd" >
						<input type="hidden" name="hostelGroupStudentList[<c:out value='${count}'/>].dummySelected" id="studenthidden_<c:out value='${count}'/>"
												value="<nested:write name='student' property='dummySelected'/>" />	
						<input type="checkbox" name="hostelGroupStudentList[<c:out value='${count}'/>].selected" id="<c:out value='${count}'/>" >
						<script type="text/javascript">
							var student1 = document.getElementById("studenthidden_<c:out value='${count}'/>").value;
							if(student1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
						</td>
	                  <td width="50%" height="25" class="row-even" ><bean:write name = "student"  property="name"/></td>

						<c:if test="${(count + 1) % 2 == 0}">
							<tr>
						</c:if>
						</nested:iterate>
	                  </tr>
	              </table></td>
	              <td width="5" height="55"  background="images/right.gif"></td>
	            </tr>
	
	            <tr>
	              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	              <td background="images/05.gif"></td>
	              <td><img src="images/06.gif" /></td>
	            </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="35" align="center" class="heading" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="49%" height="35" align="right"><div>
	                <html:submit property="" styleClass="formbutton" value="Submit"
							styleId="submitbutton"/>
	            </div></td>
	            <td width="4%" align="center">&nbsp;</td>
	            <td width="49%" align="left"><div>
	              <html:button property=""
					styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"/>
	            </div></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	
	            <td align="center">&nbsp;</td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	
	      </tr>
	    </table>
	</td>
	</tr>
</table>
</html:form>
