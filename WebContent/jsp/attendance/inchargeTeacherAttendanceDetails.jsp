<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>

<SCRIPT type="text/javascript">
   
    function winOpen(userId, classId, subjectId, batch ) {
    											
		var url = "attendTeacherReport.do?method=getPeriodDetails&userId="+userId+"&classId="+classId+"&subjectId="+subjectId+"&batchName="+batch;
		myRef = window
				.open(url, "viewPrivileges",
						"left=20,top=20,width=400,height=400,toolbar=1,resizable=0,scrollbars=1");
	}
	function cancelAction() {
    	document.location.href = "LoginAction.do?method=loginAction";
    }
    function printAreport(){
    	var url ="attendTeacherReport.do?method=printTeacherReport";
    	myRef = window.open(url,"AttendTeacherReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
        }
</SCRIPT>

<html:form action="attendTeacherReport" method="post">
	<%--<html:hidden property="method" styleId="method" value="initSalvageReport" />--%>
		<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendance.inchargeteacher.report"/><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendance.inchargeteacher.report"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<div style="text-align: center; ">
		                     <logic:notEmpty name="attendanceTeacherReportForm" property="startDate">
    				         <logic:notEmpty name="attendanceTeacherReportForm" property="endDate">
						
							   <bean:write name="attendanceTeacherReportForm" property="startDate"/> -- <bean:write name="attendanceTeacherReportForm" property="endDate"/>
						
    		    	          </logic:notEmpty>	
       		                  </logic:notEmpty>
			               </div>
							<td valign="top">
							<div style="overflow: auto; width: 914px;">
							<display:table export="true" uid="studentAttendance" id="teacherList" name="sessionScope.attendanceTeacherReport" requestURI="" defaultorder="ascending" pagesize="8" style="width:100%" sort="page">
								<display:setProperty name="export.excel.filename" value="teacherclasses.xls" />
								<display:setProperty name="export.xml" value="false" />
								<display:setProperty name="export.csv.filename" value="teacherclasses.csv" />
									<display:column style="  width: 200px;height: 40px " title="Department" property="department" headerClass="row-odd" class="row-even" sortable="true" />
								<display:column style="  width: 200px;height: 40px " title="Teacher Name" property="teacherName" headerClass="row-odd" class="row-even" sortable="true" />
								<display:column style=" width: 130px; " title="Class Name" property="className" headerClass="row-odd" class="row-even" sortable="true" />
								<display:column style=" width: 130px;" property="batch" sortable="true" title="Batch Name" class="row-even" headerClass="row-odd"/>
								<display:column style=" width: 130px; " title="Hours Taken"  headerClass="row-odd" class="row-even" sortable="true" media="html">
								<A HREF="javascript:winOpen('<bean:write name="teacherList" property="userId" />','<bean:write name="teacherList" property="classId" />','<bean:write name="teacherList" property="subjectId"/>' ,'<bean:write name="teacherList" property="batch" />' );"><bean:write name="teacherList" property="hourseTaken" /></A>
								</display:column> 
								<display:column title="Hours Taken" style=" width: 130px;;" class="row-even" sortable="true" media="excel , csv" headerClass="row-odd">
			  						 <bean:write name="teacherList" property="hourseTaken" /></display:column>
								<display:column style=" width: 130px; " title="Subject Code" property="subjectCode" headerClass="row-odd" class="row-even" sortable="true"/>
								<display:column style=" width: 330px; " title="Subject Name" property="subjectName" headerClass="row-odd" class="row-even" sortable="true"/>	
							</display:table>
							</div>
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
					<td height="61" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">

					<table width="100%" height="48" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
						<td height="25">
				<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printAreport()"></html:button></div>
				</td>
							<td height="25"><div align="left">                  
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>