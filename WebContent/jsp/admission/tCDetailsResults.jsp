<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function cancelAction() {
		document.location.href = "tcDetails.do?method=initTCDetails";
	}
	function editDetails(studentId) {
		document.location.href = "tcDetails.do?method=getStudentDetails&studentId="+studentId;
	}
	function printTC(studentId) {
		var url="";
		url = "tcDetails.do?method=printTC&studentId="+studentId;
		myRef = window.open(url,"TC","left=20,top=20,width=600,height=500,toolbar=10,resizable=0,scrollbars=1");
	}
</script>
<html:form action="/tcDetails" method="post">
	<html:hidden property="method" styleId="method" value="getStudentDetails" />
	<html:hidden property="formName" value="tCDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tc.details.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.tc.details.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><bean:write name="tCDetailsForm" property="academicYear"/></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even"><bean:write name="tCDetailsForm" property="className"/></td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.fee.rollno" /></div>
									</td>
									<td   class="row-even">
									<bean:write name="tCDetailsForm" property="rollNo"/>
									</td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
								<tr class="row-odd">
								<td>SI.No </td>
								<td>Student Name </td>
								<td>Roll No</td>
								<td>Edit</td>
								<td>Print</td>
								</tr>
								<nested:notEmpty name="tCDetailsForm" property="boardList">
								<nested:iterate id="bdForm" name="tCDetailsForm" property="boardList" indexId="count">
									<tr class="row-even"> 
										<td><c:out value="${count+1}"></c:out></td>
										<td><bean:write name="bdForm" property="studentName"/>  </td>
										<td><bean:write name="bdForm" property="rollNo"/>  </td>
										<td>
										<img src="images/edit_icon.gif"
													width="16" height="18"
													style="cursor: pointer;"
													onclick="editDetails('<bean:write name="bdForm" property="studentId" />')" />
										 </td>
										 
										 <td>
										 <nested:equal value="true" name="bdForm" property="showPrint">
										<img src="images/print-icon.png"
													width="16" height="18"
													style="cursor: pointer;"
													onclick="printTC('<bean:write name="bdForm" property="studentId" />')" />
													</nested:equal>
										 </td>
									</tr>
								</nested:iterate>
								</nested:notEmpty>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							&nbsp;&nbsp;
							&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
