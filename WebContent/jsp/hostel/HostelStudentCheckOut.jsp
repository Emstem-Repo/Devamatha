<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
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

	function addCheckOut() {
		document.getElementById("method").value = "addCheckOut";
		document.HostelStudentCheckOutForm.submit();
	}

	function goBack(){
		document.location.href = "HostelStudentCheckOut.do?method=initHostelStudentCheckOut";
	}
	function imposeMaxLength(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(249, size);
	    }
	}

	</script>
<html:form action="/HostelStudentCheckOut" method="post">
<html:hidden property="formName" value="HostelStudentCheckOutForm" />
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="getDetails"/>
	<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.hostel.adminmessage.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.student.checkout" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.student.checkout" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
									<td height="1" colspan="2" class="heading" align="left">Hostel Information
									</td>
								</tr>
							<tr>
									<td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
                                    <td width="18%" class="row-even" colspan="3"><b><bean:write name="HostelStudentCheckOutForm" property="studentName" /></b></td>
                                    
							</tr>
								<tr>
                                        <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.name.col"/></div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="hostelName" />
                                        </td>
                                         <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.roomtype"/>:</div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="roomType" /></td>
							     </tr>
								<tr>
                                        <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.auditorium.block.name"/>:</div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="blockName" />
                                        </td>
                                         <td width="15%" height="25" class="row-odd"><div align="right">Unit Name:</div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="unitName" /></td>
							     </tr>
							     <tr>
                                        <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.floorno"/>.:</div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="floorNo" />
                                        </td>
                                         <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.roomno"/>.:</div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="roomNo" /></td>
							     </tr>
							     <tr>
                                        <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.checkin.bedNo"/></div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="bedNo" />
                                        </td>
                                         <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.checkin.checkinDate"/></div></td>
                                        <td width="18%" class="row-even"><bean:write name="HostelStudentCheckOutForm" property="checkInDate" /></td>
							     </tr>
								
							
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
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td height="10" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
					<td valign="top" class="news" height="10">
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
									<td height="1" colspan="2" class="heading" align="left">Facilities Alloted
									</td>
								</tr>
								<tr>
										
										<td width="8%" height="25" class="row-odd">Sl. No.</td>
										<td width="42%" class="row-odd" align="left">Name</td>	
										<td width="50%" height="25" class="row-odd" align="left">Description</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="HostelStudentCheckOutForm" property="facilityList">
									<logic:iterate id="rec" name="HostelStudentCheckOutForm" property="facilityList" type="com.kp.cms.to.hostel.FacilityTO" indexId="count">
											<tr>
											<td width="5%" height="25" class="row-even">
											<div align="left"><c:out value="${count + 1}" /></div>
											</td>
											<td align="left" width="20%" class="row-even"><bean:write
												name="rec" property="name" /></td>
											<td align="left" width="10%" class="row-even"><bean:write
												name="rec" property="description" /></td>
												
										</tr>
										<c:set var="temp" value="1" />
										
									</logic:iterate>
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
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" height="10">
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
									<td height="1" colspan="2" class="heading" align="left">Check-Out Information
									</td>
								</tr>
								
								<tr>
									<td width="50%" height="25" class="row-odd" align="right">
									<span class="Mandatory">*</span>&nbsp;Facilities Verified:</td>
									<td width="50%" class="row-even"><span class="star">
									<c:choose>
            		    <c:when test="${HostelStudentCheckOutForm.editMode == null}">
              	   		<input type="hidden" id="c1" name="c1"
										value='<bean:write name="HostelStudentCheckOutForm" property="checked1"/>' />
						<html:checkbox property="checked1" name="HostelStudentCheckOutForm" styleId="checked1"></html:checkbox>
              		    </c:when>
              		    <c:otherwise>
                		<input type="hidden" id="c1" name="c1"
										value='<bean:write name="HostelStudentCheckOutForm" property="checked1"/>' />
						<html:checkbox property="checked1" name="HostelStudentCheckOutForm" styleId="checked1" disabled="true"></html:checkbox>
              		    </c:otherwise>
              	     </c:choose>
							</span></td>		
							</tr>
								<tr>
									<td width="50%" height="25" class="row-odd" align="right">
									<span class="Mandatory">*</span>&nbsp;Check Out:</td>
									<td width="50%" class="row-even"><span class="star">
									
									<c:choose>
            		    <c:when test="${HostelStudentCheckOutForm.editMode == null}">
              	   		<input type="hidden" id="c2" name="c2"
										value='<bean:write name="HostelStudentCheckOutForm" property="checked2"/>' />
						<html:checkbox property="checked2" name="HostelStudentCheckOutForm" styleId="checked2"></html:checkbox>
              		    </c:when>
              		    <c:otherwise>
                		<input type="hidden" id="c2" name="c2"
										value='<bean:write name="HostelStudentCheckOutForm" property="checked2"/>' />
						<html:checkbox property="checked2" name="HostelStudentCheckOutForm" styleId="checked2" disabled="true"></html:checkbox>
              		    </c:otherwise>
              	     </c:choose>
									</span></td>
																		
							</tr>
								<tr>
								<td width="50%" height="25" class="row-odd" align="right">
								<span class="Mandatory">*</span>&nbsp;Check-Out Date:</td>
								<td   class="row-even" width="25%" align="left">
													<html:text name="HostelStudentCheckOutForm" property="date" styleId="date" size="15" maxlength="16"/>
                                    				<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#date").datepicker(pickerOpts);
														});
                                     				</script>
								</td>
								</tr>
								<tr>
								<td width="50%" height="25" class="row-odd" align="right">Check-Out Remarks:</td>
								<td height="25" class="row-even"><input type="hidden" name="cr" id="cr"
										value='<bean:write name="HostelStudentCheckOutForm" property="checkOutRemarks"/>' />
										
								<html:textarea name="HostelStudentCheckOutForm" property="checkOutRemarks" cols="2" rows="10" style="width: 250px; height: 70px;" 
								styleId="checkOutRemarks" onkeypress="return imposeMaxLength(this, 0);"></html:textarea>
										
								</td>
								</tr>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Check-Out"
										onclick="addCheckOut()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="49%">			
							<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="goBack()"></html:button>
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var check1Id = document.getElementById("c1").value;
	if (check1Id != null && check1Id.length != 0) {
		document.getElementById("checked1").value = check1Id;
	}
	var check2Id = document.getElementById("c2").value;
	if (check2Id != null && check2Id.length != 0) {
		document.getElementById("checked2").value = check2Id;
	}
	var chRem = document.getElementById("cr").value;
	if (chRem != null && chRem.length != 0) {
		document.getElementById("checkOutRemarks").value = chRem;
	}
</script>