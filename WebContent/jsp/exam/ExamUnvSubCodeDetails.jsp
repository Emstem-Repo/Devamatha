
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<head>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
	function getAllValues(){
		
		var listSize=document.getElementById("unvSubCodeListSize").value;
		var subjectCode;
		var subjectId;
		var subjectValue="";
		for(var i=0; i<listSize ;i++){
			subjectCode=document.getElementById("subjectCode_"+i).value;
			subjectId=document.getElementById("hidden_"+i).value;
			subjectValue=subjectValue+subjectId+"_"+subjectCode+",";
		
			
		}
		document.getElementById("universitySubjectValue").value=subjectValue;
}

</script>
</head>
<html:form action="ExamUnvSubCodeDetails.do" method="POST">

	<html:hidden property="method" styleId="method"
		value="updateExamUnvSubCodeDetails" />
	<html:hidden property="formName" value="ExamUNVSubCodeForm" />
	<html:hidden property="pageType" value="2" />

	<html:hidden property="universitySubjectValue"
		styleId="universitySubjectValue" />
	<html:hidden property="unvSubCodeListSize" styleId="unvSubCodeListSize" />
    <html:hidden property="academicYear_value" styleId="academicYear_value" />
	<html:hidden property="course" styleId="course"  />
	<html:hidden property="scheme" styleId="scheme"  />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.UnvSubCode" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">

			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.UnvSubCode" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							</td>
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
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td class="row-even" width="25%"><bean:write
										name="ExamUNVSubCodeForm" property="academicYear" /></td>

									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										name="ExamUNVSubCodeForm" property="courseName" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.scheme.col" /></div>
									</td>
									<td width="25%" class="row-even" colspan="3"><bean:write
										name="ExamUNVSubCodeForm" property="schemeName" /></td>



									

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
				<tr height="10"></tr>
				<tr>
					<td height="97" valign="top" background="images/Tright_03_03.gif"></td>
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
							<table width="100%" height="86" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="163" class="bodytext">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="bodytext"><bean:message
										key="knowledgepro.exam.UnvSubCode.subjectName" /></td>

									<td align="center" width="180" height="25" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.UnvSubCode.subjectCode" /></div>
									</td>
									<td align="center" width="220" height="25" class="bodytext">
									<div align="center"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.UnvSubCode.unvSubjectCode" /></div>
									</td>

								</tr>
								<logic:iterate name="ExamUNVSubCodeForm"
									property="unvSubCodeList" id="uSCList" indexId="count"
									type="com.kp.cms.to.exam.ExamUnvSubCodeTO">
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
									<td width="25%" align="center" class="bodytext"><bean:write
										name="uSCList" property="subjectCode" /></td>
									<td width="25%" align="center" class="bodytext"><bean:write
										name="uSCList" property="subjectName" /></td>
									<td align="center" class="bodytext"><logic:empty
										name="uSCList" property="universitySubjectCode">
										<input type="hidden" name="id"
											id="hidden_<c:out value="${count}" />"
											value='<bean:write name="uSCList" property="id" />' />
										<input type="text" name="universitySubjectCode" 
											id="subjectCode_<c:out value="${count}" />"
												value='<bean:write name="uSCList" property="universitySubjectCode" />' 
											size="20" maxlength="50" />
									</logic:empty> <logic:notEmpty name="uSCList"
										property="universitySubjectCode">
										<input type="hidden" name="id"
											id="hidden_<c:out value="${count}" />"
											value='<bean:write name="uSCList" property="id" />' />
										<input type="text" name="universitySubjectCode"
											id="subjectCode_<c:out value="${count}" />"
											value='<bean:write name="uSCList" property="universitySubjectCode" />'
											size="20" maxlength="50"/>

									</logic:notEmpty></td>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>



				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right">
							<html:submit
								styleClass="formbutton" value="Submit"
								onclick="getAllValues();" /> 
							</div>
							</td>
							<td width="2%"></td>
							<td width="40%"><html:reset property=""
								styleClass="formbutton" value="Reset">
								<bean:message key="knowledgepro.cancel" />
							</html:reset></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
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

