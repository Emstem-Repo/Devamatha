<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
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
    	document.location.href = "sectionWiseReport.do?method=initSectionWiseReport&KJC=true";
    }
</SCRIPT>

<html:form action="leaveReport" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.reports.sectionwisereport.label" /><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.reports.sectionwisereport.label" /></strong></div></td>
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
      		  <td align="center" class="heading"><bean:write name="sectionWiseForm" property="organizationName"/> </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <logic:notEmpty name="sectionWiseForm" property="academicYear">
			<tr>
              <td width="5"  background="images/left.gif"></td>
      		  <td align="center"> <span class="heading">
	      		  <bean:message key="knowledgepro.reports.sectionwisereport.message.label" /> 
	      		  <bean:write name="sectionWiseForm" property="academicYear"/></span>
	      	  </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
      		  <td align="center"><span class="heading">
	      		</span>
	      	  </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            </logic:notEmpty>
            <tr>
              <td width="5"  background="images/left.gif"></td>
            <td valign="top">       
<div style="overflow: auto; width: 914px; ">
		<logic:notEmpty name="sectionWiseForm" property="sectionWiseList">	 
		<logic:iterate id="mapId" name="sectionWiseForm" property="sectionWiseList" indexId="count">
		
		<%String sectionList ="sessionScope.sectionWiseReport"+(count); %>
		 <%String sectionuid = "attendanceid"+(count); %>
		<span class="heading"><bean:write name="mapId" /></span>
		
		<display:table export="true" uid="<%=sectionuid %>" name="<%=sectionList %>" requestURI="" defaultorder="ascending" pagesize="10">
			<display:setProperty name="export.excel.filename" value="SectionWiseReport.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="SectionWiseReport.csv"/>
				<display:column style=" padding-right: 40px; padding-left: 40px; width:30px;" sortable="true" title="S.No" property="slno" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 40px; padding-left: 40px; width:100px;" sortable="true" title="RollNo." property="rollNo" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 40px; padding-left: 40px; width:100px;" sortable="true" title="Student Name" property="studentName" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 40px; padding-left: 40px; width:100px;" sortable="true" title="Second Language" property="secondLanguage" class="row-even" headerClass="row-odd"/>
		</display:table>
		
		</logic:iterate>
		</logic:notEmpty>
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