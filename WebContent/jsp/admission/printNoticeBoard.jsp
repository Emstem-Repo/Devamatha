<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


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
    <script language='JavaScript'>
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
</head>


<body>
<html:form action="interviewprocess" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5" ><img src="images/01.gif" width="5" height="5" /></td>
        <td width="914" background="images/02.gif"></td>
        <td width="5"><img src="images/03.gif" width="5" height="5" /></td>
      </tr>
      <tr>
        <td  background="images/left.gif"></td>
        <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            <tr >
              <td height="25" class="row-odd" ><bean:message key="knowledgepro.interview.ApplicationNo"/></td>
              <td class="row-odd" > <bean:message key="knowledgepro.interview.CandidateName"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.Date"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.Time"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.Interviewer"/></td>
			  <td class="row-odd" ><bean:message key="knowledgepro.interview.InterviewTypenocol"/></td>
            </tr>
            <logic:notEmpty name="interviewProcessForm" property="studentList">
            <nested:iterate  name="interviewProcessForm" id="interviewCardTO" property="studentList" indexId="count">
			<%
				String dynamicStyle="";				
				if(count%2!=0){
					dynamicStyle="row-white";

				}else{
					dynamicStyle="row-even";

				}
			%>
			
                  
                  <tr >
                    <td width="32%" height="25" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.applnNo"/></td>
                    <td width="30%" class='<%= dynamicStyle %>' >
                    	<bean:write name="interviewCardTO" property="admApplnTO.personalData.firstName"/>
						<logic:notEmpty name="interviewCardTO" property="admApplnTO.personalData.middleName">
						<bean:write name="interviewCardTO" property="admApplnTO.personalData.middleName"/>&nbsp;
						</logic:notEmpty>
						<logic:notEmpty name="interviewCardTO" property="admApplnTO.personalData.lastName">
						<bean:write name="interviewCardTO" property="admApplnTO.personalData.lastName"/>
						</logic:notEmpty>
					</td>
                    <td width="30%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interview.date"/></td>
                    <td width="30%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="time"/></td>
                    <td width="30%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interviewer"/></td>
					<td width="30%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interviewType"/></td>
                  </tr>
 
 			</nested:iterate> 
            </logic:notEmpty>
        </table></td>
        <td height="30"  background="images/right.gif"></td>
      </tr>
      <tr>
        <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
        <td background="images/05.gif"></td>
        <td><img src="images/06.gif" /></td>
      </tr>
    </table></td>
  </tr>
</table>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="45%" height="35"><div align="right">
                      
                  </div></td>
                  <td width="2%" style="padding-right: 5px;"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printICard()"></html:button></td>
                  <td width="8%"><html:button property="" styleId="closeme" styleClass="formbutton" value="Close" onclick="closeICard()"></html:button></td>
                  <td width="44%"></td>
                </tr>
              </table>

</html:form>
</body>
</html:html>
