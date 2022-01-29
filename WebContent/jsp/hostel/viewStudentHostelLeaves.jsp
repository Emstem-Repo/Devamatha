<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>

<html:form action="/hostelLeave" >
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="hostelLeaveForm" />
	<html:hidden property="pageType" value="5" />
	
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Hostel Leaves </strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr><td colspan="2">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
             </td>
						</tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
		                  <tr class="studentrow-odd">
		                   <td align="center"><bean:message key="knowledgepro.slno" /></td>
		                   <td align="center">From Date</td>
		                   <td align="center">To Date</td>
		                   <td align="center">Status</td>
		                 </tr>
		                 <logic:notEmpty property="totalLeaves" name="hostelLeaveForm">
		                 	<logic:iterate id="to" property="totalLeaves" name="hostelLeaveForm" indexId="count">
		                 	
		                 		<c:choose>
									<c:when test="${count%2 == 0}">
										<tr class="studentrow-even">
									</c:when>
									<c:otherwise>
										<tr class="studentrow-odd">
									</c:otherwise>
								</c:choose>
				                   <td align="center"><c:out value="${count + 1}" /></td>
				                   <td align="center"><bean:write name="to" property="startDate"/> </td>
				                   <td align="center"><bean:write name="to" property="endDate"/></td>
				                   <td align="center"><bean:write name="to" property="status"/></td>
			                 </logic:iterate>
		                 </logic:notEmpty>
                 </table>
						</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>

							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
</html:form>
<script type="text/javascript">
	document.getElementById("relationShip").style.display = "none";
	var pass=document.getElementById("pass1").value;
	var passAvailable=document.getElementById("passAvailable").value;
	var onepassAvailbale=document.getElementById("onepassAvailbale").value;
	if(pass=='true' && document.getElementById("pass1").checked && onepassAvailbale=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML = "<table width='100%'> <tr height='25'> <td class='studentrow-odd' align='right' width='50%'>select the Pass:</td> <td class='studentrow-odd' align='left'> <input type='radio' value='1' name='passes'> 1 Pass</td> </tr></table>";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='false'){
		document.getElementById("buttonId").value="Submit";
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML=" <table width='100%'> <tr tr height='25'><td width='100%' colspan='2' class='studentrow-odd' align='center'>Passes are not available</td> </tr></table>";
		document.getElementById("pass2").checked = true;
	}else{
		document.getElementById("relationShip").style.display = "none";
		document.getElementById("buttonId").value="Submit";
	}
</script>