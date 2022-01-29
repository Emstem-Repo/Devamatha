<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
	function resetValue() {
		 resetFieldAndErrMsgs();
	}
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function examMap(year){
		getExamByYear("examMap", year, "examId", updateExams);
		}	
	function updateExams(req){
		updateOptionsFromMap(req,"examId","- Select -");
		}	
	function departmentMap(streamId){
		getDeptByStream("deptMap", streamId, "deptId", updateDepartment);
		}
	function updateDepartment(req){
		updateOptionsFromMap(req,"deptId","- Select -");
		}	
</script>

<html:form action="/examInvigilationDutyExemption">
<html:hidden property="formName" value="examInvigilatorDutyExemptionForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method"	value="getInvigilatorsList" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.invigilator.exam.exemption" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.invigilator.exam.exemption" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"> *</span><bean:message key="knowledgepro.exam.room.availability.campus" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="locationId" styleId="locationId" name="examInvigilatorDutyExemptionForm">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="workLocationMap" name="examInvigilatorDutyExemptionForm">
						   							<html:optionsCollection property="workLocationMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.allotment.invigilator.exam.duty.exemption" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="exemptionId" styleId="exemptionId" name="examInvigilatorDutyExemptionForm">
                    						<html:option value="">--Select--</html:option>
	                    							<logic:notEmpty property="exemptionMap" name="examInvigilatorDutyExemptionForm">
							   							<html:optionsCollection property="exemptionMap" label="value" value="key"/>
							   						</logic:notEmpty>
						   					</html:select>
									</td>
								</tr>
								<tr>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span><bean:message key="knowledgepro.exam.allotment.invigilator.deanary" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="deanaryId" styleId="deanaryId" name="examInvigilatorDutyExemptionForm" onchange="departmentMap(this.value)">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="deanaryMap" name="examInvigilatorDutyExemptionForm">
						   							<html:optionsCollection property="deanaryMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span><bean:message key="knowledgepro.admin.department.report" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="deptId" styleId="deptId" name="examInvigilatorDutyExemptionForm">
                    						<html:option value="">--Select--</html:option>
                    						<c:choose>
				             					<c:when test="${deptMap != null}">
				             					<html:optionsCollection name="deptMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
	                    							<logic:notEmpty property="deptMap" name="examInvigilatorDutyExemptionForm">
							   							<html:optionsCollection property="deptMap" label="value" value="key"/>
							   						</logic:notEmpty>
						   						</c:otherwise>
						   					</c:choose>
						   					</html:select>
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
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="center">
								<html:submit property="" styleClass="formbutton" value="Submit"	styleId="submitbutton">
								</html:submit>
							</td>
							<td width="1%" height="35" align="center">
							</td>
							<td width="5%" height="35" align="left">
								<html:button property="" value="Reset" styleClass="formbutton" onclick="resetValue()"></html:button>
							</td>
							<td width="50%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
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
