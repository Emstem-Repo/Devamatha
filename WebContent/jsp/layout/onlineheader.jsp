<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td height="87" colspan="3" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="20%" height="25" rowspan="2" class="row-white" ><div align="left"><img src='images/header-logo.png' alt="Logo not available" width="500" height="100"></div></td>
          <td width="80%"><div align="right"><img src="images/OnlineHeaderFinal.jpg"  height="100"></div></td>
        </tr>
      </table>
      </td>
    </tr>
    <tr>
     
      <td width="100%" background="images/Orang_CurveBG.gif"><div align="left">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
			
				<tr><td width="15%" height="15" class="heading_white"></td></tr>
			
        </table>
      </div></td>
      
    </tr>
     <%if(session.getAttribute("serverDownMessage")!=null && !session.getAttribute("serverDownMessage").toString().isEmpty()){ %>
        <tr>
        <td width="100%"> <font color="#864b4f" size="3" FACE="BRITANNIC BOLD"><marquee scrollamount="3">
       <%=session.getAttribute("serverDownMessage") %>
        </marquee></font> </td>
        </tr>
        <%} %>
  </tbody>
</table>
