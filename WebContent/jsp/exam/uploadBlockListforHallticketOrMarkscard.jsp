<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>

<script type="text/javascript" language="javascript">
	// Functions for AJAX 
	
	function resetField(){
		document.location.href="UploadBlockListForHallticketOrMarkscard.do?method=initUploadBlockListForHallticketOrMarkscard";
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/UploadBlockListForHallticketOrMarkscard" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="UploadBlockListForHallticketOrMarkscardForm" />
	<html:hidden property="method" styleId="method" value="uploadExcelSheet" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.blockUnblock" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.blockUnblock" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
				<tr>
					<div id="errorMessage">
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT><FONT color="red"><html:errors /></FONT> </div>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading" >
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" ></td>
				</tr>
				<!--  <tr>
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.unMatchRegNum != null && UploadBlockListForHallticketOrMarkscardForm.unMatchRegNum  != ''}">
						<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="unMatchRegNum"/></c:if>
					</tr>-->
					<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag3">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.unMatchRegNum != null && UploadBlockListForHallticketOrMarkscardForm.unMatchRegNum  != ''}">
					<FONT color="red"><bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="unMatchRegNumMsg"/><br/>
					<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="unMatchRegNum"/></FONT><br/></c:if>
					</logic:equal>
				</tr>
				<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.dupRegNum != null && UploadBlockListForHallticketOrMarkscardForm.dupRegNum  != ''}">
					<FONT color="red"><bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="dupRegNumMsg"/><br/>
					<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="dupRegNum"/></FONT><br/></c:if>
					</logic:equal>
				</tr>
				<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag1">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.classNotFound != null && UploadBlockListForHallticketOrMarkscardForm.classNotFound  != ''}">
					<FONT color="red"><bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="classNotFound"/><br/>
					</FONT></c:if>
					</logic:equal>
				</tr>
				<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag2">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.duplireasonNum != null && UploadBlockListForHallticketOrMarkscardForm.duplireasonNum != ''}">
					<FONT color="red"><bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="duplireason"/><br/>
					<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="duplireasonNum"/></FONT><br/></c:if>
					</logic:equal>
				</tr>
				<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag4">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.unMatchClasses != null && UploadBlockListForHallticketOrMarkscardForm.unMatchClasses  != ''}">
					<FONT color="red">
					<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="unMatchClasses"/></FONT><br/></c:if>
					</logic:equal>
				</tr>
				<tr><logic:equal value="true" name="UploadBlockListForHallticketOrMarkscardForm" property="flag5">
					 <c:if test="${UploadBlockListForHallticketOrMarkscardForm.unMatchRegByClass != null && UploadBlockListForHallticketOrMarkscardForm.unMatchRegByClass  != ''}">
					<FONT color="red">
					<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="unMatchRegByClass"/></FONT><br/></c:if>
					</logic:equal>
				</tr>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="UploadBlockListForHallticketOrMarkscardForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" colspan="3" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="UploadBlockListForHallticketOrMarkscardForm">
											<html:optionsCollection property="examTypeList"
												name="UploadBlockListForHallticketOrMarkscardForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									</tr>
								<tr>
									<td width="24%" height="128" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.blockUnblock.examName" /> :</DIV>
									</div>
									</td>
									<td width="21%" height="128"  class="row-even"><html:select
										property="examName" styleClass="combo" styleId="examName"
										name="UploadBlockListForHallticketOrMarkscardForm" style="width:200px">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
										<logic:notEmpty name="UploadBlockListForHallticketOrMarkscardForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="UploadBlockListForHallticketOrMarkscardForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd" height="28">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.blockUnblock.type" /> :</div>
									</td>
									<td class="row-even"><html:select
										property="typeId" styleId="typeId">
										<html:option value="">Select</html:option>
										<html:option value="1">Hall Ticket</html:option>
										<html:option value="2">Marks Card</html:option>
									</html:select></td>
		    						<td class="row-even">&nbsp;
		    						</td>
								</tr>
								<tr>
								<td height="25" class="row-odd" width="25%">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.file" />:</div></td>
								<td height="25" class="row-even">
								<html:file property="thefile" name="UploadBlockListForHallticketOrMarkscardForm"/></td>
								</tr>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px"  align="center" colspan="1"></td></tr>
						<tr>
							<td width="45%" height="35" ><div align="center">
							<input type="submit" class="formbutton" value="Upload" />&nbsp;
							 <html:button property="" value="Reset" onclick="resetField()" styleClass="formbutton"></html:button></div>
								</td>
						</tr>						
						<tr><td style="height: 10px"  align="center" colspan="1"></td></tr>						
					</table>
					</td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>