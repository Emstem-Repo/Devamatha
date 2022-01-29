<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.List"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.GOTO"%><script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<!-- attendanceSlipDetails.jsp -->
<script type="text/javascript">


</script>

<html:form action="/TcDetailsOldStudents" method="post">

<html:hidden property="formName" value="tCDetailsOldStudentsForm" />
<html:hidden property="method" styleId="method" value="printTcOldStudents"/>
<html:hidden property="button" styleId="button" name="tcDetailsOldStudentsForm"/>
<html:hidden property="pageType" value="4"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admisssion.tcprintoldstudents"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admisssion.tcprintoldstudents"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					<div align="right" style="color: red"><span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<bean:write name="msg"/>
						<br/>
					</html:messages> </FONT></div>
					</td>
				</tr>
                 <tr>
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
					        	<tr>
			                       <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.regno"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                          <html:text name="tcDetailsOldStudentsForm" property="registerNo" styleId="registerNo" size="15" maxlength="15"/>
				        			</td>
				                </tr>
	                       </table>
	                       </td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="center">
                       <html:submit styleClass="formbutton" value="Print"></html:submit>
                       </div></td>
                     </tr>
                   </table>
                   </td>
                </tr>
                
               </table> 
                
<!-- List of Slip Details -->
	          </td>
	          <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	         
  </tr>
  
   
  <tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
</table></td></tr>
</table>
</html:form>

<script>
	val=document.getElementById("button").value;
	if(val=="true"){
		var url ="TcDetailsOldStudents.do?method=print";
		myRef = window.open(url,"TcDetailsOldStudents","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>