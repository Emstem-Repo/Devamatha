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
function Cancel()
{
	document.location.href = "LoginAction.do?method=loginAction";
}
</SCRIPT>
<body>

<html:form action="/ViewInternalMarks" method="post">	
<html:hidden property="method" styleId="method"	value="" />
<html:hidden property="formName" value="ViewInternalMarksForm" />
<html:hidden property="pageType" value="1" />
<table width="100%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.view.internal.marks"/><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
   <tr>
   
   </tr>
  <tr>
  
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
    
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.view.internal.marks"/></strong></div></td>
       
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
        
      </tr>
    
      <tr>
       
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        
        <td height="8" valign="top"></td>
        
         
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
        
      </tr>
      
           
      <tr>
     
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        
        
        <td height="8" valign="top">
        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          
             <tr>
              <td width="5"  background="images/left.gif"></td>
              <div style="font-family: verdana;font-size: 13px;text-align: center; ">
              	Subject Name:-<bean:write name="ViewInternalMarksForm" property="subjectName"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    
   		 		Class Name:-<bean:write name="ViewInternalMarksForm" property="className"/>
   		 	</div>
         
       
            
      <td valign="top">  
      
      <table width="100%" cellspacing="1" cellpadding="2" border="1">
      
		<tr>
		
		<td width="50" height="20" align="center" class="row-white">Register No</td>
		<td width="50" height="20" class="row-white"  align="center">Name</td>
		
		<logic:notEmpty name="ViewInternalMarksForm" property="examNames">
			<logic:iterate id="markHead" name="ViewInternalMarksForm"
				property="examNames" indexId="count">
				<td width="50" height="20" class="row-white"  align="center">
					<c:out value="${markHead}"></c:out>	
				</td>
				</logic:iterate></logic:notEmpty>
			</tr>
			<logic:iterate id="details" name="ViewInternalMarksForm"
				property="listCourseDetails" indexId="count">
				
			<tr class="row-white">
					
				<td class="row-white" align="center" ><bean:write name="details" property="regNo"/> </td>
				<td class="row-white" align="center"><bean:write name="details" property="studentName"/></td>
				
				<logic:notEmpty name="details"
					property="tos">
					<logic:iterate id="mark" name="details"
						property="tos">
					<td height="25" class="row-white">
						<table width="100%">
							<tr>
								<td width="50%" align="left" class="row-white">
								<logic:notEmpty name="mark" property="theoryMarks" >
								<bean:write
									name="mark" property="theoryMarks" />
								</logic:notEmpty>
									
									</td>
								<td width="50%" align="right">
								<logic:notEmpty name="mark" property="practicalMarks" >
								<bean:write
									name="mark" property="practicalMarks" />
								</logic:notEmpty>
							</tr>
						 </table>
					  </td>
					</logic:iterate>
				</logic:notEmpty>
			</logic:iterate>
	</table>

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
					<html:button property="cancel" onclick="window.close()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
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
<script type="text/javascript">
	window.print();
</script>
</html:html>
