<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function editExceptionTypes(id) {
	document.location.href = "ExceptionTypes.do?method=editExceptionTypes&id="
			+ id;
}

function deleteExceptionTypes(id, examName) {
	deleteConfirm = confirm("Are you sure to delete '" + examName
			+ "'  entry?");
	if (deleteConfirm) {
		document.location.href = "ExceptionTypes.do?method=deleteExceptionTypes&id="
				+ id;
	}
}
function reActivate(){
	document.location.href = "ExceptionTypes.do?method=reactivateExceptionType";
}
</script>

<html:form action="/ExceptionTypes">
	
	<html:hidden property="formName" value="ExceptionTypesForm" />
	<html:hidden property="pageType" value="1" />
	
	<c:choose>
		<c:when
			test="${ExceptionTypesOperation != null && ExceptionTypesOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateExceptionTypes" />
			<html:hidden property="id" styleId="id" />
		
		</c:when>
		<c:otherwise>
		    <html:hidden property="method" styleId="method" value="addExceptionTypes" />
			
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
	  <tr>
	    <td height="15"><span class="heading">Exception Types<span class="Bredcrumbs"></span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Exception Types</strong></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
   	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td  class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>

	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
	                <td  height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span>Exception Type:</div></td>
	                <td  height="25" class="row-even" > <html:text property="exceptionType" styleId="exceptionType" size="11" maxlength="50"/></td>
	                <td  height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span>Exception Short Name:</div></td>
	                <td  height="25" class="row-even" > <html:text property="exceptionShortName" styleId="exceptionShortName" size="11" maxlength="3"/></td>
	              </tr>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
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
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right"> 
	            
	            <c:choose>
					<c:when
						test="${ExceptionTypesOperation != null && ExceptionTypesOperation == 'edit'}">
						<html:submit styleClass="formbutton" value="Update"/>
					</c:when>
					<c:otherwise>
					   <html:submit styleClass="formbutton" value="Submit"/>
					</c:otherwise>
				</c:choose>
	            
	            
	            </div></td>
	            <td width="2%"></td>
	            <td width="53%">
	            <c:choose>
					<c:when
						test="${ExceptionTypesOperation != null && ExceptionTypesOperation == 'edit'}">
						<html:cancel styleClass="formbutton" value="Reset"/>
					</c:when>
					<c:otherwise>
					   <html:button property="" styleClass="formbutton"  value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
					</c:otherwise>
				</c:choose>
	            
	            
				</td>
	          </tr>
	        </table> </td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      
	      
	      
	      
	      
	      
	      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td  height="25" class="row-odd"><bean:message
										key="knowledgepro.employee.exceptionTypes.exceptionType" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.employee.exceptionTypes.exceptionShortName" /></td>
									
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>


								<logic:iterate name="ExceptionTypesForm"
									property="listExceptionType" id="listExceptionType" indexId="count"
									type="com.kp.cms.to.employee.ExceptionTypeTO">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td class="bodytext">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td align="center" class="bodytext"><bean:write
										name="listExceptionType" property="exceptionType" /></td>
									<td align="center" class="bodytext"><bean:write
										name="listExceptionType" property="exceptionShortName" /></td>
									
									<td align="center" class="bodytext">
									<div align="center"><img src="images/edit_icon.gif"
										height="18" style="cursor: pointer"
										onclick="editExceptionTypes('<bean:write name="listExceptionType" property="id"/>')"></div>
									</td>

									<td align="center" class="bodytext">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor: pointer"
										onclick="deleteExceptionTypes('<bean:write name="listExceptionType" property="id"/>','<bean:write name="listExceptionType" property="exceptionType" />')"></div>
									</td>

									</tr>
								</logic:iterate>





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
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
	      
	      
	      
	      
	      
	      <tr>
	        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>
