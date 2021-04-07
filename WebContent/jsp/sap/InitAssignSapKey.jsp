<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<html:html>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function cancelToHome(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function resetMessages() {
	 resetFieldAndErrMsgs();
}	
</script>
<html:form action="/assignSapKey" method="POST">
<html:hidden property="formName" value="assignSapKeyForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="method" value="registeredData"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs">SAP<span class="Bredcrumbs">&gt;&gt;Assign SAP Keys&gt;&gt;</span></span></td>
  </tr>
  <tr>
  	<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="30"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Assign SAP Keys</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
    			<tr>
   				 	<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
			        <td valign="top" class="news">
					<div id="errorMessage"> 
					<html:errors/><FONT color="green">
						<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
						  </FONT>
						</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
     			</tr>
      			<tr>
       	 			<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
			        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			            <tr>
				            <td ><img src="images/01.gif" width="5" height="5" /></td>
				            <td width="914" background="images/02.gif"></td>
				            <td><img src="images/03.gif" width="5" height="5" /></td>
			            </tr>
			            <tr>
			            	<td width="5"  background="images/left.gif"></td>
				            <td valign="top">
				            	<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
					                <tr>
						                <td class="row-odd" width="15%"> 
											<div align="right"><span class="Mandatory"></span>
											Start Date:</div>
										</td>
										<td   class="row-even" width="35%" align="left">
											<html:text name="assignSapKeyForm" property="startDate" styleId="startDate" size="10" maxlength="16"/>
												<script language="JavaScript">
													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#startDate").datepicker(pickerOpts);
														});
												</script>
										</td> 
										<td class="row-odd" width="15%"> 
													<div align="right"><span class="Mandatory"></span>
													End Date:</div> 
										</td>
										<td   class="row-even" width="35%" align="left">
												<html:text name="assignSapKeyForm" property="endDate" styleId="endDate" size="10" maxlength="16"/>
													<script language="JavaScript">
														$(function(){
										 					var pickerOpts = {
										        			dateFormat:"dd/mm/yy"
										       				};  
										  					$.datepicker.setDefaults(
										   					$.extend($.datepicker.regional[""])
										  					);
										  				$("#endDate").datepicker(pickerOpts);
															});
													</script>
										</td> 
									</tr>
									<tr>
										 <td class="row-odd" width="15%" align="right">Status:</td>
										 <td   class="row-even" width="35%" align="left">
										 	<html:select property="status" styleId="status" name="assignSapKeyForm">
											 	<html:option value="Pending" styleClass="comboSmall" ></html:option>
												<html:option value="Assigned" styleClass="comboSmall"></html:option>
											</html:select>
										 </td>
										 <td class="row-odd" width="15%" align="right">Class</td>
										 <td   class="row-even" width="35%" align="left">
										 	<html:select property="classId" styleId="classId">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="classMap" name="assignSapKeyForm">
						   							<html:optionsCollection property="classMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
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
         			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      		</tr>
		    <tr>
	            <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
	            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
		           <tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td width="44%"></td>
						            <td width="5%" height="35"align="right">
										<html:submit property="" styleClass="formbutton" value="Submit"></html:submit>
									</td>
									<td width="1%" align="left"></td>
									<td width="5%" align="left">
										<html:button property="" styleClass="formbutton" value="Reset"	onclick="resetMessages()"></html:button>
									</td>
									<td width="1%" align="left"></td>
									<td width="44%" align="left">
										<html:button property="" styleClass="formbutton" value="Cancel"	onclick="cancelToHome()"></html:button>
									</td>
							</tr>
						</table>
					</td>
			        </tr>
		          </table>
		        </td>
		        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
		      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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
</html:html>
                  

