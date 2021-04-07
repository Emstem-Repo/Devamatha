<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

	<script type="text/javascript">
	function deleteFineEntry(id){
		
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "fineEntry.do?method=deleteFineEntry&id="+id;
		}
	}
	function editFineEntry(id) {
		document.location.href = "fineEntry.do?method=editFineEntry&id="+id;
	}
	
	function searchFineEntry(){
		document.getElementById("method").value = "searchFineEntry";
		document.fineEntryForm.submit();
	}
	function resetMessages() {
		document.location.href = "fineEntry.do?method=initEditFineEntry";
	}
</script>
<html:form action="/fineEntry" method="post">
<html:hidden property="formName" value="fineEntryForm" />
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="id" styleId="id" />
<html:hidden property="pageType" value="2" />
	<!--<c:choose>
		<c:when test="${disciplinaryOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editFineEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addFineEntry" />
		</c:otherwise>
	</c:choose>
	--><table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			Edit Fine Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
							Edit Fine Entry
						</td>
						<td width="10">
							<img src="images/Tright_1_01.gif" width="9" height="29">
						</td>
					</tr>				
					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right">
								<FONT color="red">
									<span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span>
								</FONT>
							</div>
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<div id="notValid"><FONT color="red"></FONT></div>
								<FONT color="green">
									<html:messages id="msg"	property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
								</FONT>
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>					

					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
											<td width="25%" height="25" class="row-odd"><div align="right">
												<bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
											<td width="25%" class="row-even">
												<input type="hidden" id="yr" name="yr" value="<bean:write name="fineEntryForm" property="academicYear"/>" />
												<html:select property="academicYear" styleId="year" styleClass="combo" name="fineEntryForm">
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
												</html:select>
											</td>
											<td  width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												<bean:message key="knowledgepro.hostel.reservation.registerNo" /></div></td>
											<td  width="25%" class="row-even">
												<html:text name="fineEntryForm" property="regNo" styleId="registerNo" size="15" maxlength="10"  />
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
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									
									<td width="2%"></td>
									<td width="45%" height="35">						
										<div align="right">
											<html:button property="" styleClass="formbutton" onclick="searchFineEntry()" value="Search">&nbsp;&nbsp;	
											</html:button>
										</div>									
									</td>
									<td  height="35" >									
										<div>&nbsp;&nbsp;&nbsp;&nbsp;
											<html:button property="" styleClass="formbutton" onclick="resetMessages()">
											<bean:message key="knowledgepro.reset" /></html:button>
										</div>									
									</td>
								</tr>
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<logic:notEmpty name="fineEntryForm" property="fineEntryToList">
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
                    				<!--<td width="15" height="30%" class="row-odd" align="center" >Paid</td>
                    				--><td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				</tr>
                 				
                 				<logic:iterate id="list" name="fineEntryForm" property="fineEntryToList" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   				<tr>
                   					<td  height="25" class="row-white" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="registerNo"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="category"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="date"/></td>
                   					<td  height="25" class="row-white" align="center"><bean:write name="list" property="amount"/></td>
                   					<!--<td  height="25" class="row-white" align="center">
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
                   					--><td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 					height="18" style="cursor:pointer" onclick="editFineEntry('<bean:write name="list" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-white" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFineEntry('<bean:write name="list" property="id"/>')"></div></td>
                   					
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
                   					<!--<td  height="25" class="row-even" align="center">
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
               						--><td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 				height="18" style="cursor:pointer" onclick="editFineEntry('<bean:write name="list" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFineEntry('<bean:write name="list" property="id"/>')"></div></td>
               						
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
				</logic:notEmpty>
					<tr>
						<td height="3" valign="top" background="images/Tright_03_03.gif"></td>
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
