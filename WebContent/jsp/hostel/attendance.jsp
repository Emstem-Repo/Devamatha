<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
	function getGroups(hostelId) {
		getGroupsByHostel("groupMap", hostelId, "hlGroupId", updateGroups);
	}
	
	function updateGroups(req) {
		updateOptionsFromMap(req, "hlGroupId", "- Select -");
	}
	function addHostelAtt() {
		document.getElementById("hostelName").value = document
				.getElementById("hostelId").options[document
				.getElementById("hostelId").selectedIndex].text;
		document.getElementById("groupName").value = document
				.getElementById("hlGroupId").options[document
				.getElementById("hlGroupId").selectedIndex].text;
		document.attendanceForm.submit();
	}
	Date.prototype.toShort = function() { 
		function f(n) { 
	        return n < 10 ? "0" + n : n; 
	    } 
	    return f(this.getDate() ) + "/" + f(this.getMonth() + 1) + "/" +  this.getFullYear(); 
	}
	  	
	function resetValues(){
		resetFieldAndErrMsgs();
		document.getElementById("attendanceDate").value = (new Date()).toShort();
	}		
</script>
<html:form action="HostelAttendance" method="post">
	<html:hidden property="formName" value="attendanceForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="hostelName" styleId="hostelName"/>
	<html:hidden property="groupName" styleId="groupName"/>
	<html:hidden property="method" styleId="method"	value="loadStudent" />
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
	                <td width="17%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.name.col"/> </div></td>
	
	                <td width="17%" height="25" class="row-even" >
	                <html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" 
	                	onchange="getGroups(this.value)">
							<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="hostelList"
									label="name" value="id" />
							</html:select></td>
	                <td width="17%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="hostel.attendance.group.name"/> </div></td>
	                <td width="17%" class="row-even" >
	              		  <html:select property="hlGroupId" styleClass="combo"
								styleId="hlGroupId">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<c:choose>
									<c:when test="${operation == 'edit'}">
									<c:if test="${groupMap != null}">
										<html:optionsCollection name="groupMap" label="value"
											value="key" />
									</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${attendanceForm.hostelId != null && attendanceForm.hostelId != ''}">
											<c:set var="groupMap"
												value="${baseActionForm.collectionMap['groupMap']}" />
											<c:if test="${groupMap != null}">
												<html:optionsCollection name="groupMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select></td>
	                <td width="17%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="hostel.attendance.date.provided"/> </div></td>
	                <td width="17%" class="row-even" ><table width="22%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	
	                      <td width="27%"><html:text property="attendanceDate" styleId="attendanceDate" size="11" maxlength="11"></html:text></td>
								<td width="73%"><script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'attendanceForm',
										// input name
										'controlname' :'attendanceDate'
									});
								</script>
							</td>
	                    </tr>
	                </table></td>
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
	
	        <td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="49%" height="35" align="right"><div>
				<html:button property=""styleClass="formbutton" value="Continue"
					onclick="addHostelAtt()"></html:button>
	            </div></td>
	            <td width="4%" align="center">&nbsp;</td>
	            <td width="49%" align="left"><div>
	                <html:button property="" styleClass="formbutton"
							value="Reset" onclick="resetValues()"></html:button>
	
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