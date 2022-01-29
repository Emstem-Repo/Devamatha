<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
function goToFirstPage(method1) {
	document.location.href = "serviceLearningMarksEntry.do?method="+method1;
}

function printServiceLearning(studentId) {
	var url = "serviceLearningMarksEntry.do?method=printServiceLearningActivityMarksEntry&studentId="
		+ studentId;
   myRef = window
  .open(url, "MarksCard",
	"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}
function myFunction(msg) {
    alert(msg);
}

</script>

<html:form action="/serviceLearningMarksEntry">
	<html:hidden property="formName" value="serviceLearningMarksEntryForm" />
	
	
		<html:hidden property="method" styleId="method" value="saveServiceLearningActivity" />
		
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Service Learning	>> Service Learning Marks Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Service Learning Marks Entry</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td width="15%" height="25" class="row-odd">
									<div align="right">Programme Name :</div>
									</td>

									<td width="45%" height="25" class="row-even" ><bean:write
										property="programName" name="serviceLearningMarksEntryForm"></bean:write></td>
									
									<td width="15%" class="row-odd">
									<div align="right">Date :</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										property="startDate" name="serviceLearningMarksEntryForm"></bean:write></td>
										
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="right">Organized By :</div>
									</td>
									<td  height="25" class="row-even"><bean:write
										property="departmentName" name="serviceLearningMarksEntryForm"></bean:write></td>
									<td  class="row-odd">
									<div align="right">Incharge :</div>
									</td>
									<td  class="row-even"><bean:write
										property="inchargeName" name="serviceLearningMarksEntryForm"></bean:write></td>

								</tr>
							
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
            <logic:notEmpty property="serviceLearningActivityToList" name="serviceLearningMarksEntryForm">
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
							<td valign="top" height="25">
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr height="25">

									<td class="row-odd" height="25">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd" height="25">Name</td>
									<td  class="row-odd" height="25">Register No</td>
									<td class="row-odd" height="25">Class</td>
									<td class="row-odd" height="25">Hours</td>
									<td class="row-odd" height="25">Learning And Outcome</td>
									<td class="row-odd" height="25">Mark</td>
									<td class="row-odd" height="25">Remark</td>
									
								</tr>
								
								
								<nested:iterate id="resultlist" property="serviceLearningActivityToList" name="serviceLearningMarksEntryForm" indexId="count">
								
								<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
							<td width="13%" height="39" >&nbsp;<bean:write name="resultlist" property="studentName" /></td>
                            <td width="19%" >&nbsp;<bean:write property="registerNo" name="resultlist"/></td>
                            <td width="20%" >&nbsp;<bean:write property="classname" name="resultlist"/></td>
                            <td width="8%">&nbsp;<bean:write property="attendedHours"  name="resultlist" /></td>
                             <td width="12%">&nbsp;<img src="images/detail_icon.jpg"
													width="70" height="25"
													onclick="myFunction('<bean:write property="learningAndActivity"  name="resultlist" />')"/> </td>
                          
                        <%--     <td width="12%">&nbsp;  <a onclick="alert('<bean:write property="learningAndActivity"  name="resultlist" />')">click me</a></td>--%>
                            <td> <nested:text property="mark"  styleClass="TextBox" ></nested:text></td>
							 <td> <nested:text property="remark"  styleClass="TextBox" ></nested:text></td>
								
											</tr>
											
											
							</nested:iterate>
					
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
	</logic:notEmpty>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit value="Submit" styleClass="formbutton"></html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
							
										<input type="button" class="formbutton"
													value="Cancel" onclick="goToFirstPage('initServiceLearningMarksEntry')" />	
								</td>
							<td width="2%"></td>
							<td width="45%" height="35">
							<div align="left">
							<input type="button" class="formbutton" value="Print"
										 onclick="printServiceLearning('<bean:write name="serviceLearningMarksEntryForm" property="studentId" />')"/ >
							</div>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
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
