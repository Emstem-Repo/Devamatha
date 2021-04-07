<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%> 
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>

<script>
	function hideButtons() {
		document.getElementById("submitBtn").style.display = "none";
		document.getElementById("lgoutBtn").style.display = "none";
	}
	function hideCourseList() {
		document.getElementById("coursesList").style.display="none";
		document.getElementById("prefContent").style.display="block"; 
		}
</script>
<style>
.hdbtn{
			text-decoration: none;
			border: 1 px solid blue;
			background: #33BBFF;
			padding: 8px 25px 8px 25px;
			color: white;
			border-radius: 10px;
			text-align: center;
			
		}
		.coursesList{
			 border: 1px solid #CCE5FF;
			 width: 60%;
			 margin-left: 25%;
			 box-shadow:10px 10px 10px #000;
  			-webkit-box-shadow:-20px 20px 10px gray;
  			-moz-box-shadow: 10px 10px 10px #000;
  			 margin-top: 16px;
			 margin-bottom: 16px;
			 background-color: rgba(153, 204, 255, 0.4);
		}
	table tr{
			vertical-align: top;
		}
</style>
<html:hidden property="onlineApply" value="true" name="onlineApplicationForm"/>
<html:hidden property="serverDownMessage" styleId="serverDownMessage"  name="onlineApplicationForm"/>

<input type="hidden" name="<%= Constants.TOKEN_KEY %>" value="<%= session.getAttribute(Globals.TRANSACTION_TOKEN_KEY) %>" > 
<html:hidden property="pageType" value="" />
 
  <table width="80%" style="background-color: #F0F8FF" align="center">

    <%-- 
	<tr>
    <td >
	<table width="100%" align="center" border="0">
	<tr>
	<td align="center">
	<div id="nav-menu">
	<ul>
	<li class="current">Terms & Conditions</li>
	<li class="">Payment</li>
     <li class="">Personal Details</li>
     <li class="">Course Options</li>
     <li class="">Education Dtails</li>
	 <li  class="">Upload Photo</li>
   </ul>
   </div>
   </td></tr>
    </table></td>
  </tr>
  --%>
  
   <tr ><td height="10px"></td></tr>
   
<!-- errors display -->
  <tr >
	<td  align="center">
							<div id="errorMessage" align="center">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"	property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages>
							</FONT>
							</div>
							<div id="errorMessage1" style="font-size: 11px; color: red"></div>
	</td>
	</tr>
	<tr><td>
		<div id="coursesList" class="coursesList">
 <table class="courses" align="center">
 <tr>
 	<td style="color: red;padding-right: 20px"><b>Aided Programmes</b></td>
 	<td style="color: red;padding-left: 20px"><b>Self Financing Programmes</b></td>
 </tr>
 <logic:notEqual value="null" name="onlineApplicationForm" property="coursesList">
 	<tr>
 		<td style="border-right: 1px solid gray; padding-right: 20px">
 			<table>
 			<logic:iterate id="courses1" name="onlineApplicationForm" property="coursesList">
 			<logic:equal value="true" name="courses1" property="aidedOrNot">
 				<tr>
 					<td>
							<bean:write name="courses1" property="course"></bean:write>
 					</td>
 				</tr>
 				</logic:equal>
 				</logic:iterate>
 			</table>
 		</td>
 		<td style="padding-left: 20px">
 			<table>
 			<logic:iterate id="courses1" name="onlineApplicationForm" property="coursesList">
 			<logic:equal value="false" name="courses1" property="aidedOrNot">
 				<tr>
 					<td>
						<bean:write name="courses1" property="course"></bean:write>
 					</td>
 				</tr>
 				</logic:equal>
 				</logic:iterate>
 			</table>
 		</td>
 	</tr>
 	</logic:notEqual>
	<tr>
	<td colspan="2" align="center" style="padding: 15px;"><a href="#" class="hdbtn" onclick="hideCourseList()">Ok</a> </td>
	</tr>
</table>
</div>
	</td></tr>
	
	<tr><td>
	<div id="prefContent" class="prefContent" style="display: none">
	<table style="width: 100%;">
  <tr>
    <td width="100%"><table align="center" width="100%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="23" align="center" class="subheading">TERMS & CONDITIONS</td>
      </tr>
    </table></td>
  </tr>
   
   <tr ><td height="10px"></td></tr>
   
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class="normal inst" >
         <tr ><td height="20px"></td></tr>
          <tr>
            <%--  <td width="20%">&nbsp; Condition 1:</td>--%>
            <td> 1.	I shall remit the application fees through online payment/challan.</td>
            </tr>
            <tr>
            <%-- <td width="20%">&nbsp; Condition 2:</td>--%>
            <td> 2.	Failure to remit the fees on time shall result in the rejection of my application.</td>
		 </tr>
		  <tr ><td height="20px"></td></tr>
        </table>
        </td>
      </tr>
   
  	 <tr ><td height="20%"></td></tr>
  
   
      
 <%-- <tr style="font-size:18px; font-weight:bold;font-style:oblique">
   <td  align="center">
     I Accept all the above listed terms and conditions 
   </td>
 </tr>--%>
  
  <tr ><td height="20"></td></tr>
  
   <tr>
  <td  width="100%" align="center">
  
   <html:button styleId="submitBtn" property="" onclick="hideButtons();submitAdmissionForm('submitBasicInfo')" styleClass="cntbtn" value="Click to Accept"></html:button>
    <%-- &nbsp; <html:button property="" onclick="cancel()" styleClass="btn btn2" value="Click To Decline"></html:button>
   --%> &nbsp; <html:button styleId="lgoutBtn" property="" onclick="cancel2()" styleClass="cancelbtn" value="Logout"></html:button>
              
   </td>
  </tr>
  </table>
  </div>
  </td></tr>
 
     <tr ><td height="40px"></td></tr>
     
</table>

	
	
	