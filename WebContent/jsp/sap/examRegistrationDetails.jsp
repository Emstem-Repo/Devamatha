<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
    	
	<!-- CSS files -->
	<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
	<link rel="stylesheet" type="text/css" href="js/jquery.selectBoxIt/src/stylesheets/jquery.selectBoxIt.css">
	<link rel="stylesheet" type="text/css" href="css/custom-button.css">
	<!-- Js Files -->
	<script type="text/javascript" src="js/jquery.selectBoxIt/libs/jquery/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.selectBoxIt/libs/jqueryUI/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.selectBoxIt/src/javascripts/jquery.selectBoxIt.min.js"></script>  
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function getWorkLocation(workLocId) {
		document.location.href = "examRegistrationDetails.do?method=getDisplayDateAndSession&workLocationId="+workLocId;
	}
	function selectDateSession(id){
		document.getElementById("examScheduleDateId").value=id;
		document.getElementById("method").value = "selectDateSession";
		document.examRegDetailsForm.submit();
	}
	$(document).ready(function() {
		$("#cd-dropdown").selectBoxIt();
	});
	
	</script>
	
<html:form action="/examRegistrationDetails" method="post" >
<html:hidden property="method" styleId="method" value=" " />
<html:hidden property="formName" value="examRegDetailsForm"/>
<html:hidden property="examScheduleDateId" styleId="examScheduleDateId" name="examRegDetailsForm"/>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="bgbody">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SAP Exam Registration</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="25%">
					<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
					<td valign="top" height="10%" > 
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="15%">
					<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
					<td valign="top" height="10%" > 
					<logic:notEmpty property="message" name="examRegDetailsForm">
            			<div id="errorMessage" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<span id="err"><html:errors/></span>
							</p>
							</div>
							</div>
            			</logic:notEmpty>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr  >
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="studentrow-odd">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
            			<td valign="top" class="studentrow-odd">
						<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
						
            		  <tr >
						<td align="right" style="font-size:15px;color: #003399;pointer-events: none; cursor: default;" width="30%" >
						<strong>Select Campus :</strong></td>
						<td align="left"  width="50%" style="color:black;">&nbsp;&nbsp;&nbsp;&nbsp;
						<html:select property="workLocationId"  styleId="cd-dropdown" styleClass="selectboxit-container" onchange="getWorkLocation(this.value)" >
							<html:option value="">
							<bean:message key="knowledgepro.select" />-</html:option>
							<html:optionsCollection name="examRegDetailsForm" property="workLocationMap" label="value" value="key" />
						</html:select>
						</td>
						</tr>
           				 </table>
           				 </td>
         			 </tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="12">
				<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
					<td valign="top" height="10%" > 
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<c:if test="${examRegDetailsForm.dateSessionMap!=null}">
				<tr height="30" >
				<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
					<td valign="top" height="10%" > 
					<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
	            				       <tr >
	            				       <td width="60%"></td>
	            				      <td class="myButton1" style="pointer-events: none; cursor: default; padding:10px 17px; " width="5%" height="25" align="right"></td>
	            				      <td width="20%" align="left"><label class="heading">&nbsp;&nbsp;&nbsp;Seats are not available</label></td>
	            				      <td class="myButton" style="pointer-events: none; cursor: default; padding:10px 17px; " width="5%" height="25" align=right></td>
	            				      <td width="20%" align="left"><label class="heading">&nbsp;&nbsp;&nbsp;Seats are available</label></td>
	            				       </tr>
									</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="10">
				<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
					<td valign="top" height="10%" align="left"> 
					<table>
					<tr  height="5" >
					<td class="heading" width="20%"> 1. Kindly Register your name for SAP Exam. </td>
					</tr><tr  height="5"></tr>
					<tr  height="5">
					<td class="heading" width="20%">2. Payment of Rs.&nbsp;<STRONG><FONT color="red"><bean:write name="examRegDetailsForm" property="feeAmount"/></FONT> </STRONG>/- can be done online using your Smart Card.</td>
					</tr><tr  height="5"></tr>
					<tr  height="5">
					<td class="heading" width="20%">3. No Refund will be given in case of cancellation/absent for exam.</td>
					</tr ><tr  height="5"></tr>
					<tr  height="5">
					<td class="heading" width="20%">4. Hall Ticket will be generated on Successful payment.</td>
					</tr ><tr  height="5"></tr>
					<tr  height="5">
					<td class="heading" width="20%">5. You are free to choose date and time according to your convenience from the available slots. </td>
					</tr >
					<tr  height="5"></tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
				<td >
				 <fieldset style=" border: 2px solid #298CB5;">
				<table><tr><td  class="heading"> Please click on the Time to Select and proceed</td></tr></table>
				<table width="75%" align="center" >
				 <tr> 
		                	<c:forEach items="${examRegDetailsForm.dateSessionMap}" var="map" >
		                	<% int count=0; %>
		                		<tr style="height:30px;"></tr>
			                	<tr align="left">
									<c:forEach items="${map.value}" var="toList" >
										<c:if test="${toList.isHideSession=='true'}">
											<td width="20%" class="myButton1"  
												style="pointer-events: none; cursor: default;font-size:12px; width: 140px; height: 60px;"
												align="center" onclick="selectDateSession('<bean:write name="toList" property="sessionId"/>') ">
												<strong><c:out value="${map.key}" ></c:out></strong>
												<br></br>
												<strong ><c:out value="${toList.sessionName}"></c:out></strong>
												<% count++; %>
											</td>
											<td style="width: 20px;"></td>
										</c:if>
										<c:if test="${toList.isHideSession=='false'}">
											<td width="20%" align="center" class="myButton" style="cursor:pointer;font-size:12px;width: 140px; height: 60px;"
											onclick="selectDateSession('<bean:write name="toList" property="sessionId"/>')">
											<strong><c:out value="${map.key}"></c:out></strong>
											<br></br>
											<strong ><c:out value="${toList.sessionName}"></c:out></strong>
											<% count++; %>
											</td>
											<td style="width: 20px;"></td>
										</c:if>
									</c:forEach>
									<% if(count==2){ %>
									<td width="20%"></td><td width="20%"></td>
									<%} %>
								</tr>
		                	</c:forEach>
		                	
                 </tr>
                 <tr style="height:30px;"></tr>
						</table>
						</fieldset>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					</c:if>
							<tr height="30">
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%" > 
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
							<tr>
							<td height="19" valign="top" background="images/Tright_03_03.gif" ></td>
                   			<td ><div align="center" >
								<html:button property="" styleClass="buttom" value="Close" onclick="cancelAction()"></html:button>
					</div></td>
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
