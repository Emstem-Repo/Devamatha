<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript"><!--
	function addTcDetails() {
		document.getElementById("method").value = "initAddTcDetails";
		document.tcDetailsOldStudentsForm.submit();
	}

	function editTcDetails(id) {
		document.location.href = "TcDetailsOldStudents.do?method=editTcDetails&id="+id;
	}

	function deleteTc(id){
		document.location.href = "TcDetailsOldStudents.do?method=deleteTc&id="+id;
	}

	function getTcDetailsByYear(year) {
	    document.getElementById("year").value = year; 
		document.getElementById("method").value = "setOldStudentsData";
		document.tcDetailsOldStudentsForm.submit();
	}

	function resetFields() {
		document.getElementById("method").value = "initTcDetails";
		document.tcDetailsOldStudentsForm.submit();
	}
</script>
<html:form action="/TcDetailsOldStudents" method="post">

	<html:hidden property="formName" value="tcDetailsOldStudentsForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="button" styleId="button" name="tcDetailsOldStudentsForm"/>

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tcdetailsoldstudents" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.admission.tcdetailsoldstudents" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					<div align="right" style="color: red"><span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
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
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear" />:</div>
									</td>
									<td class="row-even" align="left">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="tcDetailsOldStudentsForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo" onchange="getTcDetailsByYear(this.value)">
										<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select>
									
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Add"
										onclick="addTcDetails()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>
							</div>
							</td>
						</tr>
					</table>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.admission.tcno" /></td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.certificate.course.regno" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.fee.studentname" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="tcDetailsOldStudentsForm" property="tcDetailsOldStudentsToList">
									<logic:iterate id="tcDetailsOldStudentsTo" name="tcDetailsOldStudentsForm"
										property="tcDetailsOldStudentsToList" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="18%" height="25"><bean:write
											name="tcDetailsOldStudentsTo" property="tcNo" /></td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="tcDetailsOldStudentsTo"
											property="registerNo" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="tcDetailsOldStudentsTo"
											property="name" /></div>
										</td>
										<td width="7%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editTcDetails('<bean:write name="tcDetailsOldStudentsTo" property="id" />')" /></div>
										</td>
										<td width="10%" height="25" align="center">
										<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteTc('<bean:write name="tcDetailsOldStudentsTo" property="id"/>')">
									</div>
									</td>
										
										</tr>
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
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
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
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>
<script>
	val=document.getElementById("button").value;
	if(val=="true"){
		var url ="TcDetailsOldStudents.do?method=print";
		myRef = window.open(url,"TcDetailsOldStudents","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>
