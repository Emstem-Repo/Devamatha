<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">

<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function init() {
		document.location.href = "ExamFooterAgreement.do?method=initExamFooterAgreement";

	}

	function editTemplate(id) {
		document.getElementById("id").value = id;
		document.getElementById("method").value = "editFooterAgreement";
		document.ExamFooterAgreementForm.submit();
	}

	function updateTemplate() {
		document.getElementById("method").value = "updateFooterAgreement";
		document.ExamFooterAgreementForm.submit();
	}

	function addTemplate() {
		document.getElementById("method").value = "createFooterAgreement";
		document.ExamFooterAgreementForm.submit();
	}

	function deleteTemplate(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ExamFooterAgreement.do?method=deleteFooterAgreement&id="
					+ id;
		}
	}
</script>
<html:form action="/ExamFooterAgreement" method="post">
	<html:hidden property="method" styleId="method"
		value="createFooterAgreement" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="ExamFooterAgreementForm" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.exam.footerAggreement" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.exam.footerAggreement" /> </strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="50" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
						<tr>
							<td height="49" colspan="6" class="body">
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
											<td width="20%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.footerAggreement.name" />:</div>
											</td>
											<td height="25" class="row-even">
											<div align="left"><html:text property="name"
												name="ExamFooterAgreementForm" maxlength="50"></html:text></div>
											</td>
											<td width="13%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.footerAggreement.type" />:</div>
											</td>
											<td class="row-even" height="25">
											<div align="left"><html:select property="typeId"
												name="ExamFooterAgreementForm" styleClass="combo"
												styleId="type">
												<html:option value=" ">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<html:option value="A">
													<bean:message
														key="knowledgepro.exam.footerAggreement.agreement" />
												</html:option>
												<html:option value="F">
													<bean:message
														key="knowledgepro.exam.footerAggreement.footer" />
												</html:option>


											</html:select></div>
											</td>


										</tr>
										<tr>


											<td width="22%" height="15" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admin.programtype" />:</div>
											</td>
											<td width="20%" height="15" class="row-even">
											<div align="left"><html:select
												property="selectedProgramType" styleClass="body"
												styleId="programType">
												<html:option value="">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<html:optionsCollection name="ExamFooterAgreementForm"
													property="programTypeList" label="display" value="id" />

											</html:select></div>
											</td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.exam.footerAggreement.date" /> :</div>
											</td>
											<td class="row-even">
											<div align="left"><html:text
												name="ExamFooterAgreementForm" property="date"
												styleId="dateId" size="10" maxlength="16" /> <script
												language="JavaScript">
	new tcal( {
		// form name
		'formname' :'ExamFooterAgreementForm',
		// input name
		'controlname' :'date'
	});
</script></div>
											</td>


										</tr>
										<tr>
											<td class="row-odd" height="28">
											<div align="right"><span class="Mandatory">*</span>Hall Ticket/Marks Card :</div>
											</td>
											<td class="row-even"><html:select
												property="halTcktOrMarksCard" styleId="halTcktOrMarksCard">
												<html:option value="">Select</html:option>
												<html:option value="1">Hall Ticket</html:option>
												<html:option value="2">Marks Card</html:option>
												<html:option value="3">Application</html:option>
											</html:select></td>
											<td class="row-odd" align="right">Batch:</td>
											<td class="row-even"> 
												 <input type="hidden" id="tempYear" name="tempYear" value="<bean:write name="ExamFooterAgreementForm" property="academicYear"/>">
						                            <html:select property="academicYear" styleId="year" styleClass="combo">
					                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					                     	   					 <cms:renderYear></cms:renderYear>
			                     			   		</html:select>
											</td>
											
										</tr>
										
										<tr>
											<td width="50%" height="25" class="row-odd" colspan="6">
											<FCK:editor instanceName="EditorDefault" toolbarSet="Default">
												<jsp:attribute name="value" trim="true">
										<c:out value="${ExamFooterAgreementForm.templateDescription}"
														escapeXml="false"></c:out>
									</jsp:attribute>
											</FCK:editor></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="20" colspan="6" valign="top" class="body">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr></tr>
								<tr>
									<td height="20" colspan="2">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="20">
											<div align="center"><c:choose>
												<c:when test="${operation == 'edit'}">
													<html:button property="" styleClass="formbutton"
														value="Update" onclick="updateTemplate()"></html:button>
												</c:when>
												<c:otherwise>
													<html:button property="" styleClass="formbutton"
														value="Save" onclick="addTemplate()"></html:button>
													<html:button property="" styleClass="formbutton"
														value="Reset" onclick="init()"></html:button>
												</c:otherwise>
											</c:choose></div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10" colspan="6" class="body"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty name="ExamFooterAgreementForm" property="mainList">
					<tr>
						<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
						<div align="center">
						<table width="100%" height="50" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="49" colspan="6" class="body">
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
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.slno" /></div>
												</td>
												<td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.exam.footerAggreement.name" /></div>
												</td>
												<td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.exam.footerAggreement.programType" /></div>
												</td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.exam.footerAggreement.type" /></div>
												</td>
												<td width="15%" height="25" class="row-odd">
												<div align="center">Hall Ticket/Marks Card</div>
												</td>
												<td width="10%" height="25" class="row-odd" align="center">
												<span class="star"> <bean:message
													key="knowledgepro.exam.footerAggreement.date" /> </span></td>
												<td width="10%" height="25" class="row-odd" align="center">
												<span class="star"> Batch </span></td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.edit" /></div>
												</td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.delete" /></div>
												</td>
											</tr>
											<logic:iterate id="template" name="ExamFooterAgreementForm"
												property="mainList" indexId="count">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
												<td height="25">
												<div align="center"><c:out value="${count + 1}" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="name" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="classCode" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="type" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="hallTicketorMarksCard" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="date" /></div>
												</td>
												<td height="25">
												<div align="center"><bean:write name="template"
													property="year" /></div>
												</td>
												<td height="25">
												<div align="center"><img src="images/edit_icon.gif"
													style="cursor: pointer" width="16" height="18"
													onclick="editTemplate('<bean:write name="template" property="id"/>')"></div>
												</td>
												<td height="25">
												<div align="center"><img src="images/delete_icon.gif"
													style="cursor: pointer" width="16" height="16"
													onclick="deleteTemplate('<bean:write name="template" property="id"/>')"></div>
												</td>
												</tr>
											</logic:iterate>
										</table>
										</td>
										<td width="5" height="30" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
										<td background="images/05.gif"></td>
										<td><img src="images/06.gif" /></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td height="10" colspan="6" class="body"></td>
							</tr>
						</table>
						</div>
						</td>
						<td width="13" valign="top" background="images/Tright_3_3.gif"
							class="news"></td>
					</tr>
				</logic:notEmpty>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempYear").value;
	if(year!= 0) {
	 	document.getElementById("year").value=year;
	}else if(year==""){
		document.getElementById("year").value="";
	}
</script>