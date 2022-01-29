<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
	<script>
	function cancelAction() {
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function addAdmin(){
		var categoryId=document.getElementById("categoryId").value;
		var desc=document.getElementById("description").value;
		document.location.href = "studentSupportRequest.do?method=addAdminSupportRequest&categoryId="+categoryId+"&description="+desc;
		}
	</script>
<html:form action="/studentSupportRequest" >
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="studentSupportRequestForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Support Request <span class="Bredcrumbs">&gt;&gt;
			Support Request &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Support Request</strong></td>

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
					<FONT color="green" size="2"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="60%" border="0" align="center" cellpadding="0"
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
										<tr class="row-white">
						                   <td width="39%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="aknowledgepro.hostel.fineCategory.name"/></div></td>
						                   <td width="61%" colspan="2" class="row-even">
											<html:select property="categoryId" styleId="categoryId" style="width:250px;">
						   						<html:option value="">--Select--</html:option>
						       						<logic:notEmpty property="categoryMap" name="studentSupportRequestForm">
														<html:optionsCollection property="categoryMap" label="value" value="key"/>
													</logic:notEmpty>
						   					</html:select>
										   </td>
			            				</tr>
			            				<tr class="row-white">
						                   <td width="39%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.holidays.description"/></div></td>
											<td width="61%" class="row-even">
												<table>
													<tr>
														<td width="100%">
															<html:textarea property="description" style="width: 100%" styleId="description" cols="30" rows="5" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
														</td>
													</tr>
													<tr>
														<td width="100%">
															<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; color: red">/500 Characters
														</td>
													</tr>
												</table>
											</td>
               							</tr>
               							 <tr class="row-white">
						                   <td colspan="2"><div align="center">
											<html:button property="" value="Submit" styleClass="formbutton" onclick="addAdmin()"></html:button>	&nbsp; <html:button property="" value="Cancel" styleClass="formbutton" onclick="cancelAction()"></html:button>
											</div></td>
						                 </tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
				<tr><td height="19"></td></tr>
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
							<logic:notEmpty name="studentSupportRequestForm" property="list">
											<table width="100%" border="0" align="center" cellpadding="0"cellspacing="0">
												<tr>
													<td valign="top">
													<table width="100%" cellspacing="1" cellpadding="2">
														<tr >
										       				<td width="5%"  height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
										       				<td width="5%" height="25" class="row-odd"><div align="center">Request ID</div></td>
										       				<td width="5%" height="25" class="row-odd"><div align="center">Date Of Submission</div></td>
										       				<td width="15%" height="25" class="row-odd"><div align="center"><bean:message key="aknowledgepro.hostel.fineCategory.name"/></div></td>
										       				<td width="35%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.employee.holidays.description"/></div></td>
										       				<td width="5%" class="row-odd"><div align="center">Status</div></td>
										       				<td width="10%" class="row-odd"><div align="center">Remarks</div></td>
										             	</tr>
														<logic:iterate id="CME" name="studentSupportRequestForm" property="list" indexId="count">
										  					<c:choose>
										     					<c:when test="${temp == 0}">
										           				<tr>
										           					<td  height="25" class="row-white" ><div align="center"><c:out value="${count + 1}"/></div></td>
										           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="requestId"/></div></td>
										           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
										           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="categoryName"/></div></td>
										           					<td  height="25" class="row-white"><bean:write name="CME" property="description"/></td>
										           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="status"/></div></td>
										           					<td  height="25" class="row-white"><div align="left"><bean:write name="CME" property="remarks"/></div></td>
										           				</tr>
										           				<c:set var="temp" value="1"/>
										          						</c:when>
										           				<c:otherwise>
										          				<tr>
										      						<td  height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
										      						<td  height="25" class="row-even"><div align="center"><bean:write name="CME" property="requestId"/></div></td>
										      						<td  height="25" class="row-even"><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
										      						<td  height="25" class="row-even"><div align="center"><bean:write name="CME" property="categoryName"/></div></td>
										      						<td  height="25" class="row-even"><bean:write name="CME" property="description"/></td>
										      						<td  height="25" class="row-even"><div align="center"><bean:write name="CME" property="status"/></div></td>
										      						<td  height="25" class="row-even"><div align="left"><bean:write name="CME" property="remarks"/></div></td>
										           				</tr>
										                		<c:set var="temp" value="0"/>
										  					</c:otherwise>
										      				</c:choose>
										    				</logic:iterate>
													</table>
													</td>
													</tr>
												</table>
							</logic:notEmpty>
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