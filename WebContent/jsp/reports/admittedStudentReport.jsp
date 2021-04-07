<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">

<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "admittedStudentReport.do?method=initAdmittedStudentsReport";
}
</SCRIPT>
<html:form action="admittedStudentReport" method="post">
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="formName" value="admittedStudentsForm" />
<html:hidden property="pageType" value="1" />
<div style="overflow: auto; width: 800px;">
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.reports"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.admittedstudentreport" />&gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.admittedstudentreport" /></strong></div></td>
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
      		  <td align="center" class="heading"><bean:write property="organizationName" name="admittedStudentsForm"/> </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
			<tr>
              <td width="5"  background="images/left.gif"></td>
      		  <td align="center"> <span class="heading">
             Admitted Students of  <bean:write property="tempYear" name="admittedStudentsForm"/></span> </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>

            <tr>
              <td width="5"  background="images/left.gif"></td>
             
      		  <td valign="top">
					<logic:notEmpty name = "admittedStudentsForm" property="classWithStudents">
						<logic:iterate id="classWithStudents" name = "admittedStudentsForm" property="classWithStudents">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="5"  background="images/left.gif"></td>
								<td align="center" class="heading">
									<bean:write property="className" name="classWithStudents"/>
								</td>
							<td width="5" height="30"  background="images/right.gif"></td>
						</tr>
						<tr>
						<td width="5"  background="images/left.gif"></td>
						<td>      

							<div style="overflow: auto; width: 914px; ">
							<bean:define id="studList" property="students" name="classWithStudents"></bean:define>
							 <display:table export="false" uid="studid"   name="<%=studList %>"  requestURI=""
								defaultorder="ascending" pagesize="10">
								<display:column media="html" style="padding-right: 80px;" sortable="true" title="Sl No." property="slNo" class="row-even" headerClass="row-odd"></display:column>
								<display:column  media="html" style=" padding-right: 80px; " sortable="true"
									title="Application Number"  property = "applicationNo" class="row-even"
									headerClass="row-odd" />
								<display:column  media="html" style=" padding-right: 80px; " sortable="true"
									title="Name" property = "applicantName" class="row-even"
									headerClass="row-odd" />
								<display:column  media="html" style=" padding-right: 80px; " sortable="true"
									title="Gender"  property = "gender" class="row-even"
									headerClass="row-odd" />
								<display:column  media="html" style=" padding-right: 80px; " sortable="true"
									title="% of mark for previous exam" property="prerequisitePercentage" class="row-even"
									headerClass="row-odd"  />
								
							</display:table>
							</div>
						</td>
							<td width="5" height="30"  background="images/right.gif"></td>
						</tr>
					</table>
					</logic:iterate>
				</logic:notEmpty>
			</td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
			<tr>
              <td width="5"  background="images/left.gif"></td>
      		  <td valign="top">
				<div style="overflow: auto; width: 914px; ">
						<display:table export="true" uid="totalid" name="sessionScope.admittedStudentReport"  requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
							<display:setProperty name="export.excel.filename" value="admittedStudents.xls" />
							<display:setProperty name="export.csv.filename" value="admittedStudents.csv" />
								<display:column></display:column>
								<display:column media="excel csv" style="padding-right: 80px;" sortable="true" title="Sl No." property="slNo" class="row-even" headerClass="row-odd"></display:column>
								<display:column  media="excel csv" style=" padding-right: 80px; " sortable="true"
									title="Application Number"  property = "applicationNo" class="row-even"
									headerClass="row-odd" />
								<display:column  media="excel csv" style=" padding-right: 80px; " sortable="true"
									title="Name" property = "applicantName" class="row-even"
									headerClass="row-odd" />
								<display:column  media="excel csv" style=" padding-right: 80px; " sortable="true"
									title="Gender"  property = "gender" class="row-even"
									headerClass="row-odd" />
								<display:column  media="excel csv" style=" padding-right: 80px; " sortable="true"
									title="% of mark for previous exam" property="prerequisitePercentage" class="row-even"
									headerClass="row-odd"  />
						</display:table>
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
</table></div>
</html:form>
