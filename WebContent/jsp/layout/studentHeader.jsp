<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>

  <!-- <tr>
    <td height="87" colspan="3" valign="top">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td height="70" bgcolor="#6ea7d1" colspan="3" valign="top" background="images/studentLogin/bg_img1.gif" ><table width="100%" bgcolor="#6ea7d1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="65%" height="85"><img src="images/OnlineHeaderFinal.jpg" width="572" height="85"></td>
          <td valign="bottom"><img src="images/studentLogin/krpoLogo.png" width="300" height="50" align="right"></td>
        </tr>
      </table></td>
    </tr>-->
    
    
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="87" colspan="3" valign="top">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
      <td height="87" colspan="3" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       <tr>
          <td width="10%" height="25" rowspan="2"><div align="left"><img src="images/Logos/header-logo.png" alt="Logo not available" width="400" height="100"></div></td>
          <td width="80%"><div align="right"><img src="images/studentLogin/OnlineHeaderFinal1.png" width="940" height="100"></div></td>
       </tr>
      </table>
      </td>
    </tr>
    
    
    
     <!-- <tr>
      <td height="87" colspan="3" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="20%" height="25" rowspan="2" class="row-white" ><div align="left"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" width="238" height="100"></div></td>
          <td width="80%" colspan="2"><div align="right"><img src="images/OnlineHeaderFinal.jpg" width="900" height="100"></div></td>
       </tr>
      </table>
      </td>
    </tr>-->
    
     
    
     <!-- <tr>
       	  <td height="87"  bgcolor="#0f1b84" valign="top" width="100%" colspan="3" align="center"><img src="images/studentLogin/StLoginHeader.png"  height="100"></td>
    </tr>-->
      <tr>
        <td>&nbsp;</td>
        <td  align="right" class="news">
        <c:choose>
        <c:when test="${username != null}">
			Welcome&nbsp; <c:out value="${username}"/>,&nbsp;
			<c:if test="${tempPasswordLogin == false}">
	          <c:if test="${ChangePassword == true}"> 
			<a href="ChangePassword.do?method=initChangeStudentPassword"  class="news">Change Password</a> &nbsp; &nbsp; 
			</c:if>
			</c:if>
			<a href="StudentLoginAction.do?method=studentLogoutAction"  class="news">Sign Out</a> 
		</c:when>
		<c:otherwise>
			&nbsp;
		</c:otherwise>
		</c:choose>
		</td>
        </tr>
        <%if(session.getAttribute("serverDownMessage")!=null && !session.getAttribute("serverDownMessage").toString().isEmpty()){ %>
        <tr>
        <td width="100%" colspan="2"><font color="#864b4f" size="3" FACE="BRITANNIC BOLD"><marquee scrollamount="3">
          <%=session.getAttribute("serverDownMessage") %>
        </marquee></font> </td>
        </tr>
        <%} %>
    </table></td>
  </tr>
</table>

