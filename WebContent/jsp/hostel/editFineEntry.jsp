<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript"><!--

	<%--function reActivate() {
		var id = document.getElementById("reactivateid").value;
		document.location.href = "fineEntry.do?method=activateFineCategory&reactivateid="+ id ;
	}
	function resetValues() {
		document.getElementById("name").value = "";
		if (document.getElementById("method").value == "updateSingleFieldMaster") {
			document.getElementById("name").value = document
					.getElementById("originalValue").value;
		}
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}--%>
	function updateFineEntry() {
		document.getElementById("method").value = "updateFineEntry";
		document.fineEntryForm.submit();
	}
	function getStudentDetails(){
		document.getElementById("notValid").innerHTML = null;
		document.getElementById("errorMessage").innerHTML = null;
		document.getElementById("studentName").innerHTML = null;
		document.getElementById("studentRoomNo").innerHTML =null;
		document.getElementById("studentBedNo").innerHTML = null;
		document.getElementById("studentBlock").innerHTML = null;
		document.getElementById("studentUnit").innerHTML = null;
		document.getElementById("studentCourse").innerHTML = null;
		document.getElementById("studentHostel").innerHTML = null;
		var academicYear = document.getElementById("academicYr").value;
		var registerNo = document.getElementById("regNo").value;
		getStudentDetailsForVisitors(academicYear,registerNo,updateStudentDetails);
		}
	function updateStudentDetails(req){
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		var isMsg = false;
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
			if (value[I].firstChild != null) {
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("notValid").innerHTML = temp;
				enableFields(true);
				isMsg = true;
			}
			}
		}
		if(isMsg=true){
		var items = responseObj.getElementsByTagName("studentDetails");
		for ( var I = 0; I < items.length; I++) {
			if(items[I]!=null){
				var studentName = items[I].getElementsByTagName("studentName")[0].firstChild.nodeValue;
				var studentRoom= items[I].getElementsByTagName("studentRoom")[0].firstChild.nodeValue;
				var studentBed = items[I].getElementsByTagName("studentBed")[0].firstChild.nodeValue;
				var studentBlock = items[I].getElementsByTagName("studentBlock")[0].firstChild.nodeValue;
				var studentUnit = items[I].getElementsByTagName("studentUnit")[0].firstChild.nodeValue;
				var studentCourse = items[I].getElementsByTagName("studentCourse")[0].firstChild.nodeValue;
				var studentHostel = items[I].getElementsByTagName("studentHostel")[0].firstChild.nodeValue;
				document.getElementById("studentName").innerHTML = studentName;
				document.getElementById("studentRoomNo").innerHTML = studentRoom;
				document.getElementById("studentBedNo").innerHTML = studentBed;
				document.getElementById("studentBlock").innerHTML = studentBlock;
				document.getElementById("studentUnit").innerHTML = studentUnit;
				document.getElementById("studentCourse").innerHTML = studentCourse;
				document.getElementById("studentHostel").innerHTML = studentHostel;
			}
		}
		}
		}
	function getAmount(id){
		getAmountByCategory(id,updateAmount);
		}
	function updateAmount(req) {
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("option");
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("amount").value = temp;
				}
			}
			
		}
	}
	<%--function cancelAction(){
		document.location.href = "HostelTransaction.do?method=transactionImages";
	}--%>
	function cancelHome(){
		document.location.href = "fineEntry.do?method=initEditFineEntry";
	}
	
	<%--function resetMessages() {
		 resetFieldAndErrMsgs();
		
	}	--%>
	function resetField(){
		document.getElementById("amount").value = document.getElementById("tempAmount").value ;
		document.getElementById("remarks").value =document.getElementById("tempRemarks").value;
		document.getElementById("categoryId").value =document.getElementById("tempCategoryId").value;
		resetErrMsgs();
		}
	
</script>

<html:form action="/fineEntry" method="post">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateFineEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addFineEntry" />
		</c:otherwise>
	</c:choose>

	<html:hidden property="id" styleId="id" />
	<html:hidden property="reactivateid" styleId="reactivateid" />
	<html:hidden property="originalValue" styleId="originalValue" />
	<html:hidden property="originalValue1" styleId="originalValue1" />
	<html:hidden property="formName" value="fineEntryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="aknowledgepro.hostel.fineEntry.display" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="aknowledgepro.hostel.fineEntry.display" /></strong></td>

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
					<div id="notValid" style="color: red"><FONT color="red"></FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							<% boolean disable=false;%>
								<logic:equal value="true" name="fineEntryForm" property="flag">
							<% disable=true;%>
							</logic:equal>
								<tr >
				  					<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year"/>:</div>
									</td>
									<td class="row-even"  width="25%">
										<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="fineEntryForm" property="academicYear"/>"/>
											<html:select property="academicYear"  styleClass="combo" styleId="academicYr" disabled='<%=disable%>'>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select></td>	
									<td class="row-odd"  width="25%"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  					<td class="row-even"  width="25%">
                  					<html:text property="regNo"	styleId="regNo" maxlength="12" size="12" disabled='<%=disable%>' onchange="getStudentDetails()" onblur="getStudentDetails()"/>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.name"/>:</td>
				  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentName"/>
				  						
				  					</td>	
				  					<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.course"/>:</td>
                  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentCourse"/>
                  					</td>	
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel"/>:</td>
                  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentHostel"/>
									</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.blocks"/>:</td>
                  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentBlock"/>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.units"/>:</td>
				  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentUnit"/>
				  					</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.roomno"/>:</td>
                  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentRoom"/>
                  					</td>	
	                  			</tr>
								<tr>
									<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.bedno"/>:</td>
                  					<td class="row-even" height="25">
												<bean:write name="fineEntryForm" property="studentBed"/>
                  					</td>
									<td class="row-odd" width="10%" >
									<div align="right"><span class="Mandatory">*</span> <bean:message key="aknowledgepro.hostel.fineCategory.name"/>:</div>
									</td>
									<td class="row-even" width="20%"> 
									<input type="hidden" name="fineEntryForm"	id="tempCategoryId" value="<bean:write name='fineEntryForm' property='categoryId'/>" />
                    						<html:select property="categoryId" styleId="categoryId" onchange="getAmount(this.value)">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="categoryMap" name="fineEntryForm">
						   							<html:optionsCollection property="categoryMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
                					</td>
								</tr>
								<tr>
									<td class="row-odd" width="10%">
										<div align="right"><span class="Mandatory">*</span>
											<bean:message key="aknowledgepro.hostel.fineCategory.amount"/>
										</div>
									</td>
									<td  class="row-even" width="20%">
									<input type="hidden" name="fineEntryForm"	id="tempAmount" value="<bean:write name='fineEntryForm' property='amount'/>" />
										<html:text property="amount" styleId="amount" maxlength="5" onkeypress="return isNumberKey(event)" size="5"></html:text>
									</td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>Remarks:</div>
									</td>
									<td width="25%" class="row-even"> 
										<input type="hidden" name="fineEntryForm"	id="tempRemarks" value="<bean:write name='fineEntryForm' property='remarks'/>" />
                    						<html:textarea property="remarks" styleId="remarks" rows="3"></html:textarea>
                					</td>
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
				<tr>

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td  height="35" align="right" width="50%">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update & Print"
										styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Save & Print"
										styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="2%"></td>
							<td  height="35" align="left" width="5%">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetField()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="2%"></td>
							<td  height="35" align="left" width="40%">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelHome()"/>
								</c:when>
								<c:otherwise>
									<input name="Submit" type="reset" class="formbutton" value="Close" onclick="cancelAction()"/>
								</c:otherwise>
							</c:choose>
							 	
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

