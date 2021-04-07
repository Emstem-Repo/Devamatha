<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
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
function cancel(){
	document.location.href = "extendSupplyImprApplDate.do?method=initExtendSuppluImprApplDate";
	
}
function unCheckSelectAll() {
			 var inputs = document.getElementsByTagName("input"); 
			 var inputObj;
			 var checkBoxOthersSelectedCount = 0;
			 var checkBoxOthersCount = 0;
			 for(var count1 = 0;count1<inputs.length;count1++) {
			          inputObj = inputs[count1];
			          var type = inputObj.getAttribute("type");
			            if (type == 'checkbox' && inputObj.id != "checkAll") {
			                  checkBoxOthersCount++;
			                  if(inputObj.checked) {
			                        checkBoxOthersSelectedCount++;
			                        inputObj.value="on";
			                  }else{
			                	  inputObj.value="off";	
			                      }   
			            }
			    }
			    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
			      document.getElementById("checkAll").checked = false;
			    } else {
			      document.getElementById("checkAll").checked = true;
			    }
	}
function selectAll(obj) {
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
	}
</script>

<html:form action="/extendSupplyImprApplDate" focus="name">
<html:hidden property="formName" value="extendSupplyImprovApplDateForm" />
<html:hidden property="method" styleId="method" value="updatetheExtendedDate"/>
<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Exam<span class="Bredcrumbs">&gt;&gt;
			Extend Supplementary Improvement Application Date &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Extend Supplementary Improvement Application Date</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td colspan="12">
										<table width="100%">
											<tr>
												<td width="25"  height="25" class="row-odd">
													<div align="center"><span class="Mandatory">*</span>Extended End Date</div>
												</td>
												<td class="row-even" >
													<html:text name="extendSupplyImprovApplDateForm" property="extendedEndDate" styleId="extendedEndDate" size="10" maxlength="16"/>
										             <script language="JavaScript">
														$(function(){
															 var pickerOpts = {
																	 	            dateFormat:"dd/mm/yy"
																	         };  
															  $.datepicker.setDefaults(
															    $.extend($.datepicker.regional[""])
															  );
															  $("#extendedEndDate").datepicker(pickerOpts);
															});
					                                  </script>
												</td>
												
												
												<!--Ashwini-->
												<td width="25%"  height="25" class="row-odd">
													<div align="center">Fine Start Date</div>
												</td>
												<td class="row-even" >
													<html:text name="extendSupplyImprovApplDateForm" property="extendedFineStartDate" styleId="extendedFineStartDate" size="10" maxlength="16"/>
										             <script language="JavaScript">
														$(function(){
															 var pickerOpts = {
																	 	            dateFormat:"dd/mm/yy"
																	         };  
															  $.datepicker.setDefaults(
															    $.extend($.datepicker.regional[""])
															  );
															  $("#extendedFineStartDate").datepicker(pickerOpts);
															});
					                                  </script>
												</td>
												
												<td width="25%"  height="25" class="row-odd">
													<div align="center">Fine End Date</div>
												</td>
												<td class="row-even" >
													<html:text name="extendSupplyImprovApplDateForm" property="extendedFineEndDate" styleId="extendedFineEndDate" size="10" maxlength="16"/>
										             <script language="JavaScript">
														$(function(){
															 var pickerOpts = {
																	 	            dateFormat:"dd/mm/yy"
																	         };  
															  $.datepicker.setDefaults(
															    $.extend($.datepicker.regional[""])
															  );
															  $("#extendedFineEndDate").datepicker(pickerOpts);
															});
					                                  </script>
												</td>
												<!--<td  width="25%"  height="25" class="row-odd"></td>
												<td class="row-even" width="25%"></td>
												
												--><td width="25%"  height="25" class="row-odd">
											<div align="right">Fine Amount: </div>
											</td>
							                <td class="row-even"><html:text
												name="extendSupplyImprovApplDateForm" property="fineAmount"
												styleId="fineAmount" size="10" styleClass="TextBox"></html:text></td>
												</tr>
											<tr>
												<td width="25%"  height="25" class="row-odd">
													<div align="center">Super Fine Start Date</div>
												</td>
												<td class="row-even" >
													<html:text name="extendSupplyImprovApplDateForm" property="extendedSuperFineStartDate" styleId="extendedSuperFineStartDate" size="10" maxlength="16"/>
										             <script language="JavaScript">
														$(function(){
															 var pickerOpts = {
																	 	            dateFormat:"dd/mm/yy"
																	         };  
															  $.datepicker.setDefaults(
															    $.extend($.datepicker.regional[""])
															  );
															  $("#extendedSuperFineStartDate").datepicker(pickerOpts);
															});
					                                  </script>
												</td>
												<td  width="25%"  height="25" class="row-odd">
													<div align="center">Super Fine End Date</div>
												</td>
												<td class="row-even" >
													<html:text name="extendSupplyImprovApplDateForm" property="extendedSuperFineEndDate" styleId="extendedSuperFineEndDate" size="10" maxlength="16"/>
										             <script language="JavaScript">
														$(function(){
															 var pickerOpts = {
																	 	            dateFormat:"dd/mm/yy"
																	         };  
															  $.datepicker.setDefaults(
															    $.extend($.datepicker.regional[""])
															  );
															  $("#extendedSuperFineEndDate").datepicker(pickerOpts);
															});
					                                  </script>
												</td>
												<!--<td  width="25%"  height="25" class="row-odd"></td>
												<td class="row-even" width="25%"></td>
												
												
												--><td width="25%"  height="25" class="row-odd">
											<div align="right">Super Fine Amount: </div>
											</td>	
							<td class="row-even"><html:text
												name="extendSupplyImprovApplDateForm" property="superFineAmount"
												styleId="superFineAmount" size="10" styleClass="TextBox"></html:text></td>
												<td width="25%"  height="25" class="row-odd">
												</td>
												<td class="row-even">
												</td>
											</tr>
										</table>
									</td>
                 				</tr>
								<tr>
									<td  width="4%"  height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="2%"  height="5%" class="row-odd" ><div align="center">
										All<input type="checkbox" id="checkAll" onclick="selectAll(this)"/></div>
									</td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Class</div></td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Start Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">End Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Extended End Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Fine Start Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Fine End Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Fine Amount</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Super Fine Start Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Super Fine End Date</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Super Fine Amount</div></td>
                    				
                 				</tr>
                 				<logic:notEmpty name="extendSupplyImprovApplDateForm" property="toList">
								<nested:iterate id="CME" name="extendSupplyImprovApplDateForm" property="toList" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   						
                   				<tr>
                   					<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
                   					<td  height="25" class="row-even" align="center">
                   					<nested:checkbox property="checked" styleId="checkId" onclick="unCheckSelectAll()"> </nested:checkbox>
                   					</td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="className"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="startDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="endDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="extendedDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="extendedFineStartDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="extendedFineDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="fineAmount"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="extendedSuperFineStartDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="extendedSuperFineDate"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="superFineAmount"/></td>
                   					
                   				</tr>
                    				<c:set var="temp" value="1"/>
                   						</c:when>
                    				<c:otherwise>
		            			<tr>
		            			
		            				<td  height="25" class="row-white" align="center"><c:out value="${count + 1}" /></td>
		            				<td  height="25" class="row-white" align="center"><nested:checkbox property="checked" styleId="checkId" onclick="unCheckSelectAll()"> </nested:checkbox>
		            				</td>
		            				<td  height="25" class="row-white" align="center"><nested:write name="CME" property="className"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="startDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="endDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="extendedDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="extendedFineStartDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="extendedFineDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="fineAmount"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="extendedSuperFineStartDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="extendedSuperFineDate"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="superFineAmount"/></td>
               					</tr>
                    					<c:set var="temp" value="0"/>
				  					</c:otherwise>
                  				</c:choose>
                				</nested:iterate>
								</logic:notEmpty>
								
								
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="right">
								<html:submit property="" value="Update" styleId="update" styleClass="formbutton"></html:submit>
							</td>
							<td width="1%" height="35" align="right"></td>
							<td width="55%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>

