<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<script type="text/javascript">
function prePrint()
{
  document.getElementById("printme").style.visibility = "hidden";
}
function postPrint()
{	
  document.getElementById("printme").style.visibility = "visible";
}


function printICard() {	
	prePrint();
	window.print();
	postPrint();
}
function closeICard()
{	
  window.close();
}

</script>
<html:form action="/ScoreSheet" method="post">
<html:hidden  property="formName" value="scoreSheetForm" />
<c:set var="k" value="0" />
<c:set var="group" value="1" />
<logic:notEmpty name="SelectedCandidates" scope="session">
<nested:iterate  name="SelectedCandidates" id="screenId" type="com.kp.cms.to.reports.ScoreSheetTO" scope="session" indexId="count">
<c:set var="k" value="${k+1}" />
<table >
	   	 <tr>
		     <td>
		      <table>
              		<tr>
						<td colspan="2" class="score">
						For Group <c:out value="${group}"/> OF <c:out value="${screenId.date}"/> AT <c:out value="${screenId.time}"/> (Kindly en-circle the Grade)
						</td>
					</tr>
					<tr>			
						<td width="130Px">
						
							<bean:define id="count" name="screenId" property="count" type="java.lang.Integer"></bean:define>										
							<%if(!CMSConstants.LINK_FOR_CJC){ %>
								<img src='images/StudentPhotos/<c:out value="${screenId.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="90Px" width="90Px" alt="Image not found"/>
		                   <%} else{%>
		                   		<img src='<%=request.getContextPath()%>/StudentPhotoServlet?count=<%=count%>'  height="90Px" width="90Px" alt="Image not found"/>
		                   	<%} %>
						</td>
						<td>
							<table  width="100%" height="90px">
								<tr>
									<td style="border:1px solid #000000" class="score">
									Appl No: <c:out value="${screenId.applicationNo}"/>
									</td>
								</tr>
								<tr >
									<td class="score" style="border:1px solid #000000">
									DOB	:   <c:out value="${screenId.dateOfBirth }"></c:out>
									</td>
								</tr>
								<tr >
									<td style="border:1px solid #000000" class="score">
									Name: <c:out value="${screenId.applicantName}"/>
									</td>
								</tr>
								<tr >
									<td style="border:1px solid #000000" class="score">
									Gender: <c:out value="${screenId.gender}"/>
									</td>
								</tr>
							</table>
						</td>
				  </tr>
              </table>
            </td>
            <td>
            <table  height="130px">
            	<tr>
            		<logic:notEmpty name="screenId" property="interviewSubRoundsTOList">
				<logic:iterate id="index" name="screenId" property="interviewSubRoundsTOList" type="com.kp.cms.to.admission.InterviewSubroundsTO">
					<%String title = index.getName(); %>
					<td  width="70" class="score">
					<%=title %>
					<logic:notEmpty name="index" property="gradeList">
					<table >
					<logic:iterate id="gradeIndex" name="index" property="gradeList" type="com.kp.cms.bo.admin.Grade">
					<tr>
						<td style="border:1px solid #000000"  width="70" class="score"><bean:write name="gradeIndex" property="grade"/></td>
					</tr>
					</logic:iterate>
					</table>
					</logic:notEmpty>
					</td>
				</logic:iterate>
				</logic:notEmpty>
	
				<logic:empty name="screenId" property="interviewSubRoundsTOList">
					<td  width="70" class="score">
					<bean:write name="screenId" property="name"/>
					<logic:notEmpty name="screenId" property="gradeList">
						<table>
						<logic:iterate id="gradeIndex" name="screenId" property="gradeList" type="com.kp.cms.bo.admin.Grade">
							<tr>
							<td style="border:1px solid #000000"  width="70" class="score"><bean:write name="gradeIndex" property="grade"/></td>
							</tr>
						</logic:iterate>
						</table>
					</logic:notEmpty>
					</td>
				</logic:empty>
            	</tr>
            </table>
            </td>
        </tr>
      </table>
      <table height="5px">
      <tr></tr>
      </table>
<c:if test="${scoreSheetForm.studentPerGroup==k}">	
<c:set var="k" value="0" />
<c:set var="group" value="${group+1}" />
<p style="page-break-after:always;"> </p>
<p style="page-break-after:always;"> </p>
</c:if>
</nested:iterate>
</logic:notEmpty>
<script type="text/javascript">
	window.print();
</script>
</html:form>