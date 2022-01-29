<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function setAppId(transId,appId,isStaff){
	document.getElementById("transactionId").value = transId;
	document.getElementById("appNo").value = appId;
	document.getElementById("isStaff").value=isStaff;
}
</script>
<body>

<html:form action="/hostelCheckin" method="post" >
	<html:hidden property="formName" value="hostelCheckinForm" />
	<html:hidden property="method" styleId="method" value="getHostelCheckinDetails" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="transactionId" styleId="transactionId" value=""></html:hidden>
	<html:hidden property="appNo" styleId="appNo" value=""></html:hidden>
	<html:hidden property="isStaff" styleId="isStaff" value=""/>
	
	
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading">Hostel<span class="Bredcrumbs">&gt;&gt; Check-In &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Check-In</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="43" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
	           			<td class="row-odd">
	           				
	           			</td>
	           			<td class="row-odd">
	           				Hostel Name
	           			</td>
	           			<td class="row-odd">
	           				Room Type
	           			</td>
	           			<td class="row-odd">
	           				Floor No
	           			</td>
	           			<td class="row-odd">
	           				Room No
	           			</td>
	           			<td class="row-odd">
	           				Bed No
	           			</td>
	           		</tr>
	            	<nested:iterate property="allotedList" name="hostelCheckinForm" id="hostelList" indexId="index">
	            		<bean:define id="transId" property="id" name="hostelList"></bean:define>
	            		<bean:define id="appId" property="applicationId" name="hostelList"></bean:define>
	            		<bean:define id="staff" property="isStaff" name="hostelList"></bean:define>
	            		<c:choose>
			            	<c:when test="${temp == 0}">
	            				<tr>
									
									<td class="row-even">
										<input  name="id" type="radio" id="appId" onclick="setAppId('<%=transId %>','<%=appId%>','<%=staff%>')">		
									</td>
									<td class="row-even">
										<nested:write property="hostelName"/>
									</td>
									<td class="row-even">
										<nested:write property="roomType"/>
									</td>
									<td class="row-even">
										<nested:write property="floorNo"/>
									</td>
									<td class="row-even">
										<nested:write property="roomName"/>
									</td>
									<td class="row-even">
										<nested:write property="bedNo"/>
									</td>            				
	            				</tr>
	            				<c:set var="temp" value="1"/>
	            			</c:when>
	            			<c:otherwise>
	            				<tr>
	            					<td class="row-white">
										<input  name="id" type="radio" id="appId" onclick="setAppId('<%=transId %>','<%=appId%>','<%=staff%>')">		
									</td>
									<td class="row-white">
										<nested:write property="hostelName"/>
									</td>
									<td class="row-white">
										<nested:write property="roomType"/>
									</td>
									<td class="row-white">
										<nested:write property="floorNo"/>
									</td>
									<td class="row-white">
										<nested:write property="roomName"/>
									</td> 
									<td class="row-white">
										<nested:write property="bedNo"/>
									</td>           
	            				</tr>	
	            				<c:set var="temp" value="0"/>
	            			</c:otherwise>
	            		</c:choose>		
	            	</nested:iterate>
	            </table>
			</td>
            <td width="5" height="62"  background="images/right.gif"></td>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38%" height="35">&nbsp;</td>
            <td width="10%">
	        	<html:submit styleClass="formbutton" ></html:submit>
			</td>
            <td width="8%">
            	<html:cancel styleClass="formbutton" />
			</td>
            <td width="44%">&nbsp;</td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
	            			