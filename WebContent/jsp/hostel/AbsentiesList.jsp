<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
	function resetMessages() {
		 resetFieldAndErrMsgs();
		 document.location.href = "absentiesList.do?method=initAbsentiesList";
	}	
	function cancelAction(){
		resetFieldAndErrMsgs();
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function getBlock(hostelId){
		document.getElementById("unitId").value="";
		getBlockByHostel("blockMap",hostelId,"blockId",updateBlock);
		}
	function updateBlock(req) {
		updateOptionsFromMap(req,"blockId","- Select -");
	}
	function getUnit(blockId){
		getUnitByBlock("unitMap",blockId,"unitId",updateUnit);
		}
	function updateUnit(req) {
		updateOptionsFromMap(req,"unitId","- Select -");
	}
</script>
<html:form action="/absentiesList" method="post" >
	<html:hidden property="formName" value="absentiesListForm" />
	<html:hidden property="method" styleId="method" value="getAbsentiesList"/>
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.absenties.list" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.absenties.list" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
								<div id="notValid"><FONT color="red"></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
							
						</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
                  							<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory"></span>
												<bean:message key="knowledgepro.hostel" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
                    							<html:select property="hostelId" styleId="hostelId" onchange="getBlock(this.value)">
                    							<html:option value="">--Select--</html:option>
                    								<logic:notEmpty property="hostelMap" name="absentiesListForm">
						   								<html:optionsCollection property="hostelMap" label="value" value="key"/>
						   							</logic:notEmpty>
						   						</html:select>
											</td> 
											<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory"></span>
												<bean:message key="knowledgepro.block" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
												<html:select property="blockId" styleId="blockId"  styleClass="combo" onchange="getUnit(this.value)">
													<html:option value="">--Select--</html:option>
						 								<c:choose>
             			 									<c:when test="${blockMap != null}">
             			 										<html:optionsCollection name="blockMap" label="value" value="key" />
															</c:when>
															<c:otherwise>
															<logic:notEmpty property="blockMap" name="absentiesListForm">
						   									<html:optionsCollection property="blockMap" label="value" value="key"/>
						   									</logic:notEmpty>
														</c:otherwise>
							 							</c:choose>
			  									</html:select>
											</td>
									</tr>
									<tr>
										<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory"></span>
												<bean:message key="knowledgepro.unit" />:</div>
										</td>
										<td   class="row-even" width="35%" align="left">
											<html:select property="unitId" styleId="unitId"  styleClass="combo">
												<html:option value="">--Select--</html:option>
						 							<c:choose>
             			 								<c:when test="${unitMap != null}">
             			 									<html:optionsCollection name="unitMap" label="value" value="key" />
														</c:when>
														<c:otherwise>
															<logic:notEmpty property="unitMap" name="absentiesListForm">
						   									<html:optionsCollection property="unitMap" label="value" value="key"/>
						   									</logic:notEmpty>
														</c:otherwise>
							 						</c:choose>
			  								</html:select>
										</td>  
										<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory">*</span>
												Date:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
												<input type="hidden" name="absentiesListForm"	id="tempHolidaysFrom" value="<bean:write name='absentiesListForm' property='holidaysFrom'/>" />
												<html:text name="absentiesListForm" property="holidaysFrom" styleId="holidaysFrom" size="10" maxlength="16"/>
												<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#holidaysFrom").datepicker(pickerOpts);
														});
                                     				</script>
											<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Morning" name="absentiesListForm">Morning</nested:radio>
											<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Evening" >Evening</nested:radio>
										</td> 
									</tr>
	                  					<!--<tr>
                  							<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory">*</span>
												<bean:message key="knowledgepro.holidays.from" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
												<input type="hidden" name="absentiesListForm"	id="tempHolidaysFrom" value="<bean:write name='absentiesListForm' property='holidaysFrom'/>" />
												<html:text name="absentiesListForm" property="holidaysFrom" styleId="holidaysFrom" size="10" maxlength="16"/>
												<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#holidaysFrom").datepicker(pickerOpts);
														});
                                     				</script>
											<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Morning" name="absentiesListForm">Morning</nested:radio>
											<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Evening" >Evening</nested:radio>
										</td> 
						 				<td class="row-odd" width="15%"> 
											<div align="right"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.holidays.to" />:</div> 
										</td>
										<td   class="row-even" width="35%" align="left" colspan="2">
											<input type="hidden" name="absentiesListForm"	id="tempHolidaysTo" value="<bean:write name='absentiesListForm' property='holidaysTo'/>" />
											<html:text name="absentiesListForm" property="holidaysTo" styleId="holidaysTo" size="10" maxlength="16"/>
											<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#holidaysTo").datepicker(pickerOpts);
														});
                                     				</script>
											<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="Morning" name="absentiesListForm">Morning</nested:radio>
											<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="Evening" >Evening</nested:radio>
										</td> 
									</tr>
									--></table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
            						<td width="43%" height="35">&nbsp;</td>
            						<td width="7%"><html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.submit" />
											</html:submit>
									</td>
            						<td width="6%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button>
									</td>
            						<td width="44%" ><html:button property="" styleClass="formbutton" onclick="cancelAction()">
												<bean:message key="knowledgepro.cancel"/>
											</html:button>
									</td>
          						</tr>
							</table>
							</td>
						</tr>
						</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
