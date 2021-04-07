<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
		
</script>
<html:form action="/birtFeeReport" method="post">
<html:hidden property="method" styleId="method" value="initBirtFeeReport" />
<html:hidden property="formName" value="birtFeeReportForm" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"></span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/st_Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/st_Tcenter.gif" class="heading_white" >DOWNLOAD MARKS CARD</td>
        <td width="10" ><img src="images/st_Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/st_Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/st_Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/st_Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          		<IFRAME name="reportName" id="reportName" SRC="<%= request.getContextPath( ) + 
          		"/run?__format=PDF&__report=reports/"+request.getAttribute("reportName")+".rptdesign&sample=my+parameter&ExamName="
          				+session.getAttribute("examIDForMCard")
          				+ "&Exam Class=" + session.getAttribute("marksCardClassId") + 
          				"&Semester=" + session.getAttribute("semesterYearNo")+
          				"&Start RegisterNo=" + session.getAttribute("stuRegNo")+ 
          				"&End RegisterNo=" + session.getAttribute("stuRegNo")%>" WIDTH=100% HEIGHT="600px;">

</IFRAME>
         
        </table>


</td>
        <td width="10" valign="top" background="images/st_Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/st_Tright_03_03.gif"></td>
                      </tr>
                      <tr>
                        <td height="99" valign="top" background="images/st_Tright_03_03.gif"></td>
     				   <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
                      
                      <tr>
						        	
						         	<td ><div align="center">
						            	<html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button>
									</div></td>
							</tr></table></td>
                       <td height="26" valign="top" background="images/st_Tright_03_03.gif"></td>
                      </tr>
      <tr>
        <td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/st_TcenterD.gif"></td>
        <td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>