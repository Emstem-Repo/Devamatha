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
	function printResult(){
		var url = "studentCollectionReport.do?method=printStudentReport";
		myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
		}
    function cancelAction() {
    	document.location.href = "studentCollectionReport.do?method=initStudentReport";
    }
</SCRIPT>
<body>

<html:form action="studentCollectionReport" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.petticash.StudentCollectionReport"/><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.petticash.StudentCollectionReport"/></strong></div></td>
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
             <div style="text-align: center;">
    		        <logic:notEmpty name="studentCollectionReportForm" property="startDate">
    				<logic:notEmpty name="studentCollectionReportForm" property="endDate">
						
							   <bean:write name="studentCollectionReportForm" property="startDate"/> -- <bean:write name="studentCollectionReportForm" property="endDate"/>
						
    		    	</logic:notEmpty>	
       		    </logic:notEmpty>
			 </div>
      <td valign="top">       
<div style="overflow: auto; width: 730px; ">
	<c:set var="temp" value="0" />  
		 
		<display:table export="true" uid="attendanceid" name="sessionScope.studentCollectionReport" requestURI="" defaultorder="ascending" pagesize="10">
			<display:setProperty name="export.excel.filename" value="StudentCollections.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="StudentCollections.csv"/>
		<c:choose>
			<c:when test="${temp == 0}">	
				<display:column style=" padding-right: 180px;" property="date" sortable="true" title="Date" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 180px;" property="time" sortable="true" title="Time" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 180px;" property="recNumber" sortable="true" title="Receipt Number" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 150px;" property="accCode" sortable="true" title="Account Code" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 150px;" property="accName" sortable="true" title="Account Name" class="row-even" headerClass="row-odd"/>			
				<display:column style=" padding-right: 150px;" property="amount" sortable="true" title="Amount" class="row-even" headerClass="row-odd"/>	
	<c:set var="temp" value="1" />
			</c:when>
			<c:otherwise>
				<display:column style=" padding-right: 180px;" property="date" sortable="true" title="Date" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 180px;" property="time" sortable="true" title="Time" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 180px;" property="recNumber" sortable="true" title="Receipt Number" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 150px;" property="accCode" sortable="true" title="Account Code" class="row-even" headerClass="row-odd"/>
				<display:column style=" padding-right: 150px;" property="accName" sortable="true" title="Account Name" class="row-even" headerClass="row-odd"/>			
				<display:column style=" padding-right: 150px;" property="amount" sortable="true" title="Amount" class="row-even" headerClass="row-odd"/>
						
	<c:set var="temp" value="0" />
			</c:otherwise>
		</c:choose>										
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
                  <td width="46%" height="35">
					<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printResult()"></html:button></div>
					</td>
					<td width="1%"></td>
					<td width="52%" align="left">
					<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()">
					</html:button> 
				</td>
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
