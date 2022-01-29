<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script language='JavaScript'>

	function viewDetails() {
		var url = "html/about.html";
		window.open(url,'aboutus','left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1');
	}
	function viewHelp() {
		var url = "jsp/helpFiles/IndexHelp.jsp";
		window.open(url,'Help','left=20,top=20,width=900,height=500,toolbar=1,resizable=0,scrollbars=1');
	
	}
	function viewContactUsDetails() {
		var url = "html/contact_us.html";
		window.open(url,'contactus','left=20,top=20,width=325,height=175,toolbar=0,resizable=0,scrollbars=0');
	}
</script>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td height="87" colspan="3" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="20%" height="25" rowspan="2" class="row-white" ><div align="left"><img src='images/header-logo.png' alt="Logo not available"  height="100"></div></td>
          <td width="80%"><div align="right"><img src="images/OnlineHeaderFinal.jpg" width="772" height="100"></div></td>
       </tr>
      </table>
      </td>
    </tr>
    <tr>
     
      <td width="100%" background="images/Orang_CurveBG.gif"><div align="left">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<c:choose> 
			<c:when test="${onlineuser == 1}">
				<tr><td width="15%" class="heading_white">
					
					</td></tr>
			</c:when>
			<c:otherwise>
			<tr>
				<td width="53%" height="19" class="heading_white"><a href="LoginAction.do?method=loginAction"  class="heading_white">Home</a>|<a  href="javascript:void(0)"  onclick="viewDetails()" class="heading_white">About us</a>|<a  href="javascript:void(0)"  onclick="viewContactUsDetails()" class="heading_white"> Contact Us</a></td>
	            <td width="15%" class="heading_white">Welcome:&nbsp;<%=session.getAttribute("empname")%></td>
	            <td width="12%" class="heading_white"><a href="ChangePassword.do?method=initChangePassword"  class="heading_white">Change Password</a></td>
	            <td width="3%" class="heading_white"><a href="javascript:void(0)"  onclick="viewHelp()" class="heading_white">Help</a></td>
	            <td width="3%" class="heading_white"><div align="center"><a href="LogoutAction.do?method=logout"><img src="images/signout_icon.png" width="16" height="16" border="0"></a></div></td>
	            <td width="6%" class="heading_white"><a href="LogoutAction.do?method=logout"  class="heading_white">Sign Out</a></td>
			</tr>
			</c:otherwise>
			</c:choose>
        </table>
      </div></td>
      
    </tr>
  </tbody>
</table>
