<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<html:html>
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

<body>
<html:form action="interviewprocess" method="post">
<logic:notEmpty name="interviewProcessForm" property="studentList">
<nested:iterate  name="interviewProcessForm" id="interviewCardTO" property="studentList" indexId="count">
<br>
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images/01.gif" width="5" height="5"></td>
    <td width="100%" background="images/02.gif"></td>
    <td width="11" ><img src="images/03.gif" width="5" height="5"></td>
  </tr>
  <tr>
    <td width="10" height="0"  background="images/left.gif"></td>
    <td height="206" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
      <tr >
        <td width="20%" height="25" rowspan="2" class="row-white" ><div align="center"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" width="98" height="103"></div></td>
        <td height="62" class="row-white" ><div align="center"><span class="style1">
               <logic:notEmpty name="interviewCardTO" property="collegeName">    
        	<bean:write name="interviewCardTO" property="collegeName"/>
        </logic:notEmpty>
		</span><br>
        <logic:notEmpty name="interviewCardTO" property="collegeAddress">    
        	<bean:write name="interviewCardTO" property="collegeAddress"/>
        </logic:notEmpty><br>
        <logic:notEmpty name="interviewCardTO" property="collegeAddress1">    
        	<bean:write name="interviewCardTO" property="collegeAddress1"/>
        </logic:notEmpty><br>
        <logic:notEmpty name="interviewCardTO" property="collegeAddress2">    
        	<bean:write name="interviewCardTO" property="collegeAddress2"/>
        </logic:notEmpty>
		</div></td>
        <td width="20%" height="25" class="row-white" ><div align="center"><img src='<%=request.getContextPath()%>/TopBarServlet' alt="Logo not available" width="98" height="103"></div></td>
      </tr>
      <tr >
        <td height="25" class="row-white" ><div align="center" class="heading"><bean:message key="knowledgepro.interview.interviewcard"/></div></td>
        <td width="20%" valign="middle" class="row-white" ><div align="center" class="heading"></div></td>
      </tr>
      <tr >
        <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.interview.ApplicationNo"/></div></td>
        <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.interview.InterviewTypenocol"/></div></td>
        <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.interview.CandidateName"/></div></td>
      </tr>
      <tr >
        <td height="25" class="row-even" ><div align="center">    
        <logic:notEmpty name="interviewCardTO" property="admApplnTO.applnNo">    
        	<bean:write name="interviewCardTO" property="admApplnTO.applnNo"/>
        </logic:notEmpty>
        </div></td>
        <td height="25" class="row-even" ><div align="center">
			<logic:notEmpty name="interviewCardTO" property="interviewType">    
				<bean:write name="interviewCardTO" property="interviewType"/>
			</logic:notEmpty>
		</div></td>
        <td class="row-even" ><div align="center">
        <logic:notEmpty name="interviewCardTO" property="admApplnTO.personalData.firstName">
        	<bean:write name="interviewCardTO" property="admApplnTO.personalData.firstName"/>&nbsp;
			<logic:notEmpty name="interviewCardTO" property="admApplnTO.personalData.middleName">
			<bean:write name="interviewCardTO" property="admApplnTO.personalData.middleName"/>&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="interviewCardTO" property="admApplnTO.personalData.lastName">
			<bean:write name="interviewCardTO" property="admApplnTO.personalData.lastName"/>
			</logic:notEmpty>
        </logic:notEmpty>	
        </div></td>
      </tr>
      <tr >
        <td height="25" class="row-even" ><div align="center"><strong><bean:message key="knowledgepro.interview.coursecombination"/></strong></div></td>
        <td height="25" class="row-even" ><div align="center"><strong><bean:message key="knowledgepro.interview.Date"/></strong></div></td>
        <td height="25" class="row-even" ><div align="center"><strong><bean:message key="knowledgepro.interview.Time"/></strong></div></td>
      </tr>

      <tr >
        <td height="25" class="row-even" ><div align="center">
          <logic:notEmpty name="interviewCardTO" property="admApplnTO.course.name">
        	<bean:write name="interviewCardTO" property="admApplnTO.course.name"/>
          </logic:notEmpty>	
          	
        </div></td>
        <td height="40" class="row-even" >
           <logic:notEmpty name="interviewCardTO" property="interview.date">
        	<div align="center"><bean:write name="interviewCardTO" property="interview.date"/></div>
           </logic:notEmpty>	
        </td>
        <td class="row-even" >
          <logic:notEmpty name="interviewCardTO" property="time">
        	<div align="center"><bean:write name="interviewCardTO" property="time"/></div>
          </logic:notEmpty>	
        </td>
      </tr>
      <tr >
        <td height="40" colspan="3" valign="top" class="row-even" ><table width="100%" border="0" align="center" cellpadding="3" cellspacing="2">
          <tr>
            <td class="body">
				<logic:notEmpty name="interviewCardTO" property="intCardContent">
            		<bean:write name="interviewCardTO" property="intCardContent"/>
				</logic:notEmpty>
			</td>
          </tr>
        </table></td>
        </tr>
      <tr >
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
      </tr>
      <tr >
        <td height="30" valign="top" class="row-even" ><bean:message key="knowledgepro.interview.issuedDate"/></td>
        <td height="30" valign="top" class="row-even" >
         <logic:notEmpty name="interviewCardTO" property="issueDate">
        	<div align="center"><bean:write name="interviewCardTO" property="issueDate"/></div>
          </logic:notEmpty>	

		</td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
      </tr>
      <tr >
        <td height="30" valign="top" class="row-even" ><bean:message key="knowledgepro.interview.issuedby"/></td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
 
		
        <td height="30" valign="top" class="row-even" ><bean:message key="knowledgepro.interview.principal"/></td>
      </tr>
      <tr >
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
        <td height="30" valign="top" class="row-even" >&nbsp;</td>
      </tr>
      
    </table>      
   </td>
    <td  background="images/right.gif" width="11" height="3"></td>
  </tr>
  <tr>
    <td height="5"><img src="images/04.gif" width="5" height="5"></td>
    <td background="images/05.gif"></td>
    <td><img src="images/06.gif" ></td>
  </tr>
</table>
 			</nested:iterate> 
            </logic:notEmpty>

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
