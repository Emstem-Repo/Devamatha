<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
   <script type="text/javascript">
   function resetValues() {
		//document.getElementById("certificateCourseName").value = "";
		
		document.getElementById("subjectCode").value = "";
		document.getElementById("subjectName").value = "";
		document.getElementById("subjectGroupName").value = "";
		document.getElementById("certificateCourse").selectedIndex = 0;
		document.getElementById("certificateCourse").label = "-Select-";
		resetErrMsgs();
	}
   function initSubjectGroupAssignment(value) {
		document.getElementById("semType").value = value;
		document.getElementById("method").value = "initSubjectGroupAssignment";
		document.studentCertificateCourseForm.submit();
	}
   function getSubjectCodeName(key) {
		document.getElementById("certificateCourse").value = key;
		document.getElementById("method").value = "getSubjectCodeName";
		document.studentCertificateCourseForm.submit();
	}

	
    </script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentCertificateCourse">
	<html:hidden property="method" styleId="method" value="SubmitSubjectCodeName" />
	<html:hidden property="formName" value="studentCertificateCourseForm" />
	<html:hidden property="pageType" value="2" />
		

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			Certificate Course Subject group Name &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Certificate Course </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
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
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Academic Year</div>
									</td>
									<td width="16%" class="row-even">
										
                 						<html:text property="academicYear" styleId="academicYear" styleClass="TextBox"
											size="25" maxlength="100"  readonly="true"/>	
									</td>
									<td  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.sem.type" />:</div>
									</td>
									<td  class="row-even">
									<!-- <input type="hidden" id="tempsemType" name="tempsemType" value="<bean:write name="studentCertificateCourseForm" property="semType"/>" />-->
									<html:select property="semType" styleId="semType" styleClass="combo" onchange="initSubjectGroupAssignment(this.value)">
										<html:option value="ODD">ODD</html:option>
										<html:option value="EVEN">EVEN</html:option>
									</html:select>
									 </td>
							</tr>
							<tr>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Certificate Course</div>
									</td>
									<td height="25" class="row-even" colspan="3"><label>
									<logic:notEmpty  name="studentCertificateCourseForm" property="courseMap">
	                 				 	<html:select  property="certificateCourse" name="studentCertificateCourseForm" styleId="certificateCourse" styleClass="comboLarge" onchange="getSubjectCodeName(this.value)">
	                  					<html:option value="">--Select--</html:option>
	                  					<html:optionsCollection property="courseMap" name="studentCertificateCourseForm" label="value" value="key"/>
	                  					</html:select>
	                  				</logic:notEmpty></label></td>
								</tr>
								<tr>								
								<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject Code</div>
								</td>
								<td width="16%" class="row-even">
										<html:text property="subjectCode" styleId="subjectCode" styleClass="TextBox"
											size="25" maxlength="100" readonly="true"/> <span class="star"></span>
								</td>
								<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject Name</div>
								</td>
								<td width="16%" class="row-even">
										<html:text property="subjectName" styleId="subjectName" styleClass="TextBox"
											size="25" maxlength="100" readonly="true"/> <span class="star"></span>
								</td>
								</tr>
								<tr>								
								<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject Group Name</div>
								</td>
								<td width="16%" class="row-even" colspan="3">
										<html:text property="subjectGroupName" styleId="subjectGroupName" styleClass="TextBox"
											size="25" maxlength="100" /> <span class="star"></span>
								</td>
								</tr>
							
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							
							<div align="right">
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton" ></html:submit>
										</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetValues()"></html:button>
										</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<!-- <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>

							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>

							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>-->
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
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
