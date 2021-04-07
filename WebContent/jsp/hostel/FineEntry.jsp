<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	<%--function editFineEntry(id){
		document.getElementById("academicYr").disabled=true;
		document.getElementById("regNo").disabled=true;
		document.location.href = "fineEntry.do?method=editFineEntry&id="+ id;
	}
	function deleteDetails(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "fineEntry.do?method=deleteFineEntry&id="+ id;
		}
	}
	function reActivate() {
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
	function cancelAction(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancel1(){
		document.location.href = "HostelTransaction.do?method=transactionImages";
	}
	<%--function cancelHome(){
		document.location.href = "fineEntry.do?method=initFineEntry";
	}--%>
	function savePaid(paid,id){
		document.location.href = "fineEntry.do?method=updateWhenPaidTheFine&id="+ id+"&paid="+paid;
		}
	function resetMessages() {
		 resetFieldAndErrMsgs();
		
	}	
	function resetMessages1() {
		document.location.href ="fineEntry.do?method=initFineEntry";
	}
	<%--function resetField(){
		document.getElementById("amount").value = document.getElementById("tempAmount").value ;
		document.getElementById("remarks").value =document.getElementById("tempRemarks").value;
		document.getElementById("categoryId").value =document.getElementById("tempCategoryId").value;
		resetErrMsgs();
		}--%>
		function viewDetails(id) {
			document.location.href = "fineEntry.do?method=editFineEntry&id="+id+"&propertyName=view";
	}
</script>

<html:form action="/fineEntry" focus="name">
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
				  					<td class="row-odd" width="25%" height="25">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year"/>:</div>
									</td>
									<td class="row-even"  width="25%" height="25">
										<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="fineEntryForm" property="academicYear"/>"/>
											<html:select property="academicYear"  styleClass="combo" styleId="academicYr" disabled='<%=disable%>'>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select></td>	
									<td class="row-odd"  width="25%" height="25"><div align="right" ><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  					<td class="row-even"  width="25%" height="25">
                  					<html:text property="regNo"	styleId="regNo" maxlength="12" size="12" disabled='<%=disable%>' onchange="getStudentDetails()" onblur="getStudentDetails()"/>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.name"/>:</td>
				  					<td class="row-even" height="25">
				  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentName"/>
											</c:when>
											<c:otherwise>
											<div id="studentName" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
				  						
				  					</td>	
				  					<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.course"/>:</td>
                  					<td class="row-even" height="25">
                  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentCourse"/>
											</c:when>
											<c:otherwise>
											<div id="studentCourse" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
                  					</td>	
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel"/>:</td>
                  					<td class="row-even" height="25">
                  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentHostel"/>
											</c:when>
											<c:otherwise>
											<div id="studentHostel" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.blocks"/>:</td>
                  					<td class="row-even" height="25">
                  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentBlock"/>
											</c:when>
											<c:otherwise>
											<div id="studentBlock" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.units"/>:</td>
				  					<td class="row-even" height="25">
				  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentUnit"/>
											</c:when>
											<c:otherwise>
											<div id="studentUnit" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
				  					</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.roomno"/>:</td>
                  					<td class="row-even" height="25">
                  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentRoom"/>
											</c:when>
											<c:otherwise>
											<div id="studentRoomNo" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
                  					</td>	
	                  			</tr>
								<tr>
									<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.bedno"/>:</td>
                  					<td class="row-even" height="25" >
                  						<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<bean:write name="fineEntryForm" property="studentBed"/>
											</c:when>
											<c:otherwise>
											<div id="studentBedNo" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
                  					</td>
									<td class="row-odd" width="10%">
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
							<td   align="left" width="4%">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetField()"></html:button>
								</c:when>
								<c:otherwise>
										<c:choose>
												<c:when test="${fineEntryForm.isHlTransaction !=null && fineEntryForm.isHlTransaction !='' && fineEntryForm.isHlTransaction == 'true'}">
													<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages1()"></html:button>
												</c:when>
												<c:otherwise>
													<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
												</c:otherwise>
											</c:choose>
								</c:otherwise>
							</c:choose>
							</td>
							<td  height="35" align="left" width="40%">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelHome()"/>
								</c:when>
								<c:otherwise>
								<c:choose>
										<c:when test="${fineEntryForm.isHlTransaction !=null && fineEntryForm.isHlTransaction !='' && fineEntryForm.isHlTransaction == 'true'}">
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancel1()"> </html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction()"> </html:button>
										</c:otherwise>
									</c:choose>
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
								<tr >
                    				<td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    				<td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="aknowledgepro.hostel.fineCategory.name"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.interview.Date"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="aknowledgepro.hostel.fineCategory.amount"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Paid</td>
                    				<!--<td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				--><td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.view"/></div></td></tr>
                 				<logic:iterate id="list" name="fineEntryForm" property="fineEntryToList" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   				<tr>
                   					<td  height="25" class="row-white" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="registerNo"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="category"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="date"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="amount"/></td>
                   					<td  height="25" class="row-white" align="center">
                   							<input type="hidden" name="fineEntryToList[<c:out value='${count}'/>].paid1"	id="hidden_<c:out value='${count}'/>"
												value="<bean:write name='list' property='paid'/>" />
											<input type="checkbox" name="fineEntryToList[<c:out value='${count}'/>].paid" value="fineEntryToList<c:out value='${count}'/>" id="fineEntryToList<c:out value='${count}'/>" onclick="savePaid(this.value,'<bean:write name="list" property="id"/>')" />
												<script type="text/javascript">
													var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
														if(studentId == "true") {
															document.getElementById("fineEntryToList<c:out value='${count}'/>").checked = true;
															document.getElementById("fineEntryToList<c:out value='${count}'/>").value = true;
														}else{
															document.getElementById("fineEntryToList<c:out value='${count}'/>").checked = false;
															document.getElementById("fineEntryToList<c:out value='${count}'/>").value = false;
															}		
												</script>
                   						</td>
                   					<!--<td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 					height="18" style="cursor:pointer" onclick="editFineEntry('<bean:write name="list" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-white" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="list" property="id"/>')"></div></td>
                   				--><td  height="25" class="row-white" ><div align="center">
                   					<img src="images/View_icon.gif" width="16" height="16" style="cursor:pointer" onclick="viewDetails('<bean:write name="list" property="id"/>')"></div></td>
                   				</tr>
                    				<c:set var="temp" value="1"/>
                   						</c:when>
                    				<c:otherwise>
		            			<tr>
               						<td  height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
               						<td  height="25" class="row-even" align="center"><bean:write name="list" property="registerNo"/></td>
               						<td  height="25" class="row-even" align="center"><bean:write name="list" property="category"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="list" property="date"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="list" property="amount"/></td>
                   					<td  height="25" class="row-even" align="center">
                   							<input type="hidden" name="fineEntryToList[<c:out value='${count}'/>].paid1"	id="hidden_<c:out value='${count}'/>"
												value="<bean:write name='list' property='paid'/>" />
											<input type="checkbox" name="fineEntryToList[<c:out value='${count}'/>].paid" value="fineEntryToList<c:out value='${count}'/>" id="fineEntryToList<c:out value='${count}'/>" onclick="savePaid(this.value,'<bean:write name="list" property="id"/>')"/>
												<script type="text/javascript">
													var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
													if(studentId == "true") {
														document.getElementById("fineEntryToList<c:out value='${count}'/>").checked = true;
														document.getElementById("fineEntryToList<c:out value='${count}'/>").value = true;
													}else{
														document.getElementById("fineEntryToList<c:out value='${count}'/>").checked = false;
														document.getElementById("fineEntryToList<c:out value='${count}'/>").value = false;
														}		
												</script>
                   					</td>
               						<!--<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 				height="18" style="cursor:pointer" onclick="editFineEntry('<bean:write name="list" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="list" property="id"/>')"></div></td>
               					--><td  height="25" class="row-even" ><div align="center">
                   						<img src="images/View_icon.gif" width="16" height="16" style="cursor:pointer" onclick="viewDetails('<bean:write name="list" property="id"/>')"></div></td>
               					</tr>
                    					<c:set var="temp" value="0"/>
				  					</c:otherwise>
                  				</c:choose>
                				</logic:iterate>
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
var print = "<c:out value='${fineEntryForm.printFineEntry}'/>";
if(print.length != 0 && print == "true"){
	var url = "fineEntry.do?method=printFineEntry";
		myRef = window.open(url, "printHallTicket",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}
</script>
