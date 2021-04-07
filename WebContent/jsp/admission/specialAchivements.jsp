<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
function addAchivements() {
	document.getElementById("method").value = "addStudentAchivements";
}
function resetMessages() {
	resetFieldAndErrMsgs();
}

function deleteAchivement(id)
{
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "specialAchivements.do?method=deleteAchivement&id="+id;	
}

function editAchivement(id)
{
	document.location.href = "specialAchivements.do?method=editAchivement&id="+id;
}

function updateAchivements()
{
	document.getElementById("method").value = "updateAchivement";
}

function reActivate()
{
	document.location.href = "specialAchivements.do?method=reActivateAchivement";
}
function getTermNumber(regNo){
	if(regNo != null){
		getCurrentTermNumber(regNo,updateTermNumberMap);
	}
}
function updateTermNumberMap (req){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("termNumber").value=temp;
			}
		}
	}
}

function resetMethod()
{
	document.location.href = "specialAchivements.do?method=editAchivement";
}
</script>

<html:form action="/specialAchivements" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="specialAchivementsForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						 <tr>
   							 <td colspan="3"><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.label" /> &gt;&gt;</span></span></td>
  						</tr>
						<tr>
							<td width="9"><img src="images/Tright_03_01.gif" width="9"
								height="29"></td>
							<td background="images/Tcenter.gif" class="body">
							<div align="left"><strong class="boxheader">
							<bean:message key="knowledgepro.admission.label" /></strong></div>
							</td>
							<td width="10"><img src="images/Tright_1_01.gif" width="9"
								height="29"></td>
						</tr>
						<tr>
							<td height="122" valign="top"
								background="images/Tright_03_03.gif"></td>
							<td valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr bgcolor="#FFFFFF">
									<td height="20" colspan="4">
									<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
									<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
									<FONT color="green"><html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.regNo" /></div>
									</td>
									<td class="row-even"><html:text
										property="registerNo" styleId="regNo" size="10" maxlength="20" onchange="getTermNumber(this.value)"></html:text>
									</td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Term Number</div>
									</td>
									<td class="row-even">
									<html:select property="termNumber" styleId="termNumber">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="TermNumberMap" label="value" value="key" />
									</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
			                    		<bean:message key="knowledgepro.admission.achivement" /></div></td>
									<td class="row-even" colspan="2"><span class="row-white">
									<FCK:editor instanceName="EditorDefault"  toolbarSet="Default">
									<jsp:attribute name="value">
										<c:out value="${specialAchivementsForm.achivements}" escapeXml="false"></c:out>
									</jsp:attribute>
								</FCK:editor>
									
               									 </span></td><td>&nbsp;</td>
								</tr>
								<tr>
									<td height="45" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="49%" height="35"><div align="right">
								               <c:choose>
            									<c:when test="${operation == 'edit'}">
              	   									<html:submit onclick="updateAchivements()" styleClass="formbutton" value="Update"></html:submit>
              									</c:when>
              									<c:otherwise>
                									<html:submit onclick="addAchivements()" styleClass="formbutton" value="Submit"></html:submit>
              									</c:otherwise>
              									</c:choose>
								               
								            </div></td>
								            <td width="1%"></td>
								            <td width="51%"><div align="left">
								              <c:choose>
            									<c:when test="${operation == 'edit'}">
              	   									 <html:button property=""  styleClass="formbutton" value="Reset" onclick="resetMethod()"></html:button>
              									</c:when>
              									<c:otherwise>
                									 <html:button property=""  styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
              									</c:otherwise>
              									</c:choose>
								           
								            
								            </div></td>
								          </tr>
									</table>
									</td>
								</tr>
								<tr>
            				<td height="45" colspan="4" >
            					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              						<tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.admission.regNo"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.termnumber"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.achivement"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="specialAchivementsForm" property="achivementList">
                <logic:iterate id="CME" name="specialAchivementsForm" property="achivementList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="31%" height="25" class="row-even" align="center"><bean:write name="CME" property="regNo"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="termNumber"/></td>
                   		<bean:define id="tempAch" name="CME" property="achivement"></bean:define>
                   		<td width="41%" class="row-even" align="center"><%=tempAch%></td>
			            <td width="6%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editAchivement('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="6%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteAchivement('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="31%" height="25" class="row-white" align="center"><bean:write name="CME" property="regNo"/></td>
                   		<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="termNumber"/></td>
               			<bean:define id="tempAch" name="CME" property="achivement"></bean:define>
                   		<td width="41%" class="row-white" align="center"><%=tempAch%></td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editAchivement('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteAchivement('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
                </logic:notEmpty>
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
							</table>
							</td>
							<td width="10" valign="top" background="images/Tright_3_3.gif"
								class="news"></td>
						</tr>
						
						
						
						<tr>
							<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
							<td width="949" background="images/TcenterD.gif"></td>
							<td><img src="images/Tright_02.gif" width="9" height="29"></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td valign="top">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
