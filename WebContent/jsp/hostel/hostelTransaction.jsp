<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function Cancel(){
	document.location.href = "HostelTransaction.do?method=initHostelTransaction";
}
function Transaction(count){
	var reg=document.getElementById("registerNo_"+count).value;
	document.location.href = "HostelTransaction.do?method=transactionImages&studentRegNo="+reg;
}

</script>
<html:form action="/HostelTransaction" method="post">
<html:hidden property="formName" value="hostelTransactionForm" />
<html:hidden property="method" styleId="method" value="getStudentDetails" />
<html:hidden property="pageType" value="1"/>
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;Hostel Transaction &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> Hostel Transaction</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
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
									<table width="100%" cellspacing="1" cellpadding="2" >
									<tr>
											 <td width="16%" height="25" class="row-odd"  >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
								            </td>
									         <td width="17%" height="25" class="row-even" >
											        <input type="hidden" id="tempyear" name="appliedYear" value="<bean:write name="hostelTransactionForm" property="year"/>" />
												    
											       	<html:select property="year" styleId="year" styleClass="combo" >
														<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
														<cms:renderAcademicYear></cms:renderAcademicYear>
													</html:select>
											</td>
											<td width="16%" height="25" class="row-odd" ><div align="right">
                                     	<bean:message key="knowledgepro.exam.blockUnblock.regNo"/></div>
                                     	</td>
                                     	<td width="16%" height="25" class="row-even" >
                                     		<span class="star">
                                      			<html:text property="regno" styleId="regNo" size="27" />
                                     	 	</span>
                                     	</td>
									</tr>
                                  	<tr>
                                     	
                                        <td width="16%" height="25" class="row-odd"><div align="right">
                                        <bean:message key="knowledgepro.fee.studentname"/>:</div>
                                        </td>
                                        <td width="18%" class="row-even"><div align="left"> <span class="star">
                                                <html:text property="studentName" styleId="studentName" size="30" />
                                                </span></div>
                                        </td>
                                        <td width="16%" height="25" class="row-odd">
												<div align="right">
												<bean:message key="knowledgepro.hostel.hostel.entry.name" /></div>
											</td>
											<td width="16%" height="25" class="row-even" ><span class="star"> 
											<html:select property="hostelName" styleClass="comboLarge" styleId="hostelName" >
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty property="hostelList" name="hostelTransactionForm">
													<html:optionsCollection property="hostelList" name="hostelTransactionForm" label="name" value="id"/>
												</logic:notEmpty>
											</html:select></span></td>
                                    </tr>
									</table>
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
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" colspan="6" align="center">
									<div align="center">
											<html:submit styleClass="formbutton" value="search"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<html:button property="" styleClass="formbutton" value="Reset" onclick="Cancel()" />
									</div>
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
				<logic:notEmpty property="studentList" name="hostelTransactionForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          				 <tr>
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
										<table width="100%" cellspacing="1" cellpadding="0">
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student RegisterNumber</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student Name</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Course</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Room No</div> 
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction</div> 
												</td>
											</tr>
												<nested:iterate id="to" property="studentList" name="hostelTransactionForm" indexId="count">
												<% String TransactionMethod="Transaction("+count+")"; %>
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="25%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													 <input type="hidden" id="registerNo_<c:out value='${count}'/>"   value="<bean:write name="to" property="registerNo"/>" />
													<td align="center" width="20%" height="25"><nested:write  name="to"
														 property="registerNo" /></td>
													<td align="center" width="30%" height="25"><nested:write  name="to"  
														 property="studentName" /></td>
													<td align="center" width="30%" height="25"><nested:write  name="to"  
														 property="courseName" /></td>
													<td align="center" width="30%" height="25"><nested:write  name="to"  
														 property="roomNo" /></td>
													<td align="center" width="20%" height="25">
													<html:button property="" styleClass="formbutton" 
													value="Transaction" onclick="<%=TransactionMethod%>" /></td>
													</nested:iterate>
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
									<td width="49%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Close" onclick="Cancel()"></html:button></td>
								</tr>
							</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			</logic:notEmpty>
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
<script>
var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>
