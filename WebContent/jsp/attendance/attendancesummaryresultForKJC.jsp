<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
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
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "attendSummaryReport.do?method=initAttendSummaryReport&KJC=true";
    }
</SCRIPT>
<body>

<html:form action="attendSummaryReport" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendance.summaryreport" /><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendance.summaryreport" /></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
             <div class="row-white" style="text-align: center; padding-bottom: 7px;">
		        <logic:notEmpty name="attendanceSummaryReportForm" property="startDate">
    				<logic:notEmpty name="attendanceSummaryReportForm" property="endDate">
						<logic:notEmpty name="attendanceSummaryReportForm" property="courseName">
							<bean:message key="knowledgepro.attendance.summaryreportof" /><logic:notEqual name="attendanceSummaryReportForm" property="courseName" value="- Select -"> <bean:write name="attendanceSummaryReportForm" property="courseName"/></logic:notEqual> <bean:message key="knowledgepro.attendance.summaryreportfrom" /> <bean:write name="attendanceSummaryReportForm" property="startDate"/> <bean:message key="knowledgepro.attendance.summaryreportto" /> <bean:write name="attendanceSummaryReportForm" property="endDate"/>
						</logic:notEmpty>  
    		    	</logic:notEmpty>	
       		    </logic:notEmpty>
			 </div>
      <td valign="top">       
<div style="overflow: auto; width: 730px; ">
	<c:set var="temp" value="0" />  
		 <logic:iterate id="mapId" name="attendanceSummaryReportForm"
											property="attendanceSummarySizeList" indexId="count">
		
		<%String attendanceList ="sessionScope.attendanceSummaryReport"+(count+1); %>
		 <%String attendanceuid = "attendanceid"+(count+1); %>
		<display:table export="true" uid="<%=attendanceuid %>" name="<%=attendanceList %>" requestURI="" defaultorder="ascending" pagesize="10">
			<display:setProperty name="export.excel.filename" value="Attendance.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="Attendance.csv"/>
			<display:caption class="heading" media="html csv excel"><bean:write name="attendanceSummaryReportForm" property="organisationName"/>ATTENDANCE REPORT OF <bean:write name="<%=attendanceuid %>" property="className"/> STUDENTS FROM <bean:write name="attendanceSummaryReportForm" property="startDate"/> TO <bean:write name="attendanceSummaryReportForm" property="endDate"/></display:caption>
		
				<display:column style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="Roll No" class="row-even" headerClass="row-odd"><bean:write name="<%=attendanceuid %>" property="rollNo"/></display:column>
				<display:column style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="Registration Number" class="row-even" headerClass="row-odd"><bean:write name="<%=attendanceuid %>" property="regNo"/></display:column>
				<display:column style=" padding-left: 40px; padding-right: 40px; " property="studentName" sortable="true" title="Student Name" class="row-even" headerClass="row-odd"/>
				<logic:notEmpty name="<%=attendanceuid %>" property="secondLanguage">
				<display:column style=" padding-left: 40px; padding-right: 40px; " property="secondLanguage" sortable="true" title="Second Language Code" class="row-even" headerClass="row-odd"/>
				</logic:notEmpty>
				<logic:notEmpty name="<%=attendanceuid %>" property="subjectSummaryList">
			<logic:iterate id="index" name="<%=attendanceuid %>"
											property="subjectSummaryList"
											type="com.kp.cms.to.attendance.SubjectSummaryTO">
											<%String titleHtml = index.getSubjectName()+"<br>"+"Hld &nbsp;&nbsp;Att &nbsp;&nbsp;%"; %>
												<%String title = index.getSubjectName()+"\n"+"Hld Att %"; %>
											<display:column style=" padding-left: 0px; padding-right: 25px; " media="html" title="<%=titleHtml %>" headerClass="row-odd" class="row-even" sortable="true">
											<pre class="row-even"> 	<bean:write name="index" property="classesHeld" /> <logic:notEmpty name="index" property="classesHeld"><logic:notEqual name="index" property="classesHeld"  value="-">|</logic:notEqual></logic:notEmpty> <bean:write name="index" property="classesAttended" /> <logic:notEmpty name="index" property="classesAttended"><logic:notEqual name="index" property="classesAttended"  value="-">|</logic:notEqual></logic:notEmpty> <bean:write name="index" property="percentage" /> </pre>
											</display:column>
											<display:column style=" padding-left: 0px; padding-right: 25px; " media="csv excel" title="<%=title %>" headerClass="row-odd" class="row-even" sortable="true">
												<bean:write name="index" property="classesHeld" /> <logic:notEmpty name="index" property="classesHeld"><logic:notEqual name="index" property="classesHeld"  value="-">|</logic:notEqual></logic:notEmpty> <bean:write name="index" property="classesAttended" /> <logic:notEmpty name="index" property="classesAttended"><logic:notEqual name="index" property="classesAttended"  value="-">|</logic:notEqual></logic:notEmpty> <bean:write name="index" property="percentage" />  
											</display:column>
											
										</logic:iterate>
			</logic:notEmpty>
			<display:column style=" padding-left: 40px; padding-right: 40px; " property="aggrigatePercentage" sortable="true" title="Aggregate" class="row-even" headerClass="row-odd"/>
				<logic:notEmpty name="<%=attendanceuid %>" property="activitySummaryTOlist">
				<display:column media="html" style=" padding-left: 90px; padding-right: 90px; " sortable="true" title="Activity" class="row-even" headerClass="row-odd">
				<logic:iterate id="index1" name="<%=attendanceuid %>" property="activitySummaryTOlist">
				<pre class="row-even"> <bean:write name="index1" property="activityName" /> </pre>  </logic:iterate> </display:column>

				<display:column media="csv excel" style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="Activity" class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index1" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <bean:write name="index1" property="activityName" /> </logic:iterate> </display:column>
			

				<display:column media="html" style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="ActivityHeld" class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index2" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <pre class="row-even"> <bean:write name="index2" property="activityHeld" /> </pre> </logic:iterate> </display:column>

				<display:column media="csv excel" style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="ActivityHeld" class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index2" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <bean:write name="index2" property="activityHeld" /> </logic:iterate> </display:column>

				<display:column media="html" style=" padding-left: 50px; padding-right: 50px; " sortable="true" title="ActivityAttd." class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index3" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <pre class="row-even"> <bean:write name="index3" property="activityAttended" /> </pre> </logic:iterate> </display:column>

				<display:column media="csv excel" style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="ActivityAttd." class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index3" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <bean:write name="index3" property="activityAttended" /> </logic:iterate> </display:column>
			 
				<display:column media="html" style=" padding-left: 50px; padding-right: 50px; " sortable="true" title="ActivityPert." class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index4" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <pre class="row-even"> <bean:write name="index4" property="activityPercentage" /> </pre> </logic:iterate> </display:column>

				<display:column media="csv excel" style=" padding-left: 40px; padding-right: 40px; " sortable="true" title="ActivityPert." class="row-even" headerClass="row-odd">
				
				<logic:iterate id="index4" name="<%=attendanceuid %>" property="activitySummaryTOlist"> <bean:write name="index4" property="activityPercentage" /> </logic:iterate> </display:column>
				</logic:notEmpty>
    <display:footer>
      <tr>
        <td colspan="4" >* - Maximum attendance percentage with leaves</td>
      </tr>
    </display:footer>

		</display:table>
</logic:iterate>
	</div>	
		</td>
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
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
          
              <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="25"><div align="center">                  
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
                </tr>
              </table>
            
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
