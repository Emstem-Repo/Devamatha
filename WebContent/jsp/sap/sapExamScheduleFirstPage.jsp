<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
		
	function cancelAction(){
		resetFieldAndErrMsgs();
		document.location.href = "examSchedule.do?method=initExamSchedul";
	}
	function getVenueDetails(){
		document.getElementById("method").value="assignVenueAndInvigilator";
		document.examScheduleForm.submit();
	}
	function search(){
		document.getElementById("method").value="searchVenueAndInvigilatorDetails";
		document.examScheduleForm.submit();
	}
	function editExamSchedule(id){
		document.getElementById("examScheduleDateId").value=id;

		document.getElementById("method").value="editExamVenueAndInvigilatorDetails";
		document.examScheduleForm.submit();
	}
	function deleteExamSchedule(id){
		document.getElementById("examScheduleDateId").value=id;

		document.getElementById("method").value="deleteExamVenueAndInvigilatorDetails";
		document.examScheduleForm.submit();
	}
</script>
<html:form action="/examSchedule" method="post" >
	<html:hidden property="formName" value="examScheduleForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1" />
	<html:hidden property="examScheduleDateId" styleId="examScheduleDateId" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Sap <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.sap.examSchedule" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.sap.examSchedule" /></strong></td>
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
                 							
										<td width="20%" height="25" class="row-odd">
										<div align="right"><bean:message key="knowledgepro.admin.academicyear.and.month" /> :</div>
										</td>
										<td width="30%" class="row-even">
										
											<nested:select property="month" styleId="month" styleClass="Timings">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:option value="1">JAN</html:option>
												<html:option value="2">FEB</html:option>
												<html:option value="3">MAR</html:option>
												<html:option value="4">APR</html:option>
												<html:option value="5">MAY</html:option>
												<html:option value="6">JUN</html:option>
												<html:option value="7">JUL</html:option>
												<html:option value="8">AUG</html:option>
								              	<html:option value="9">SEP</html:option>
								              	<html:option value="10">OCT</html:option>
								              	<html:option value="11">NOV</html:option>
												<html:option value="12">DEC</html:option>
											</nested:select>&nbsp;
											
											<html:select property="year"
												styleId="year" styleClass="Timings">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderYear normalYear="true"></cms:renderYear>
											</html:select>
										
										</td>
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
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="43%" height="35">&nbsp;</td>
									<td width="6%"><html:button property="" styleClass="formbutton" onclick="getVenueDetails()">
												<bean:message key="knowledgepro.admin.add"/>
											</html:button>
									</td>
            						<td width="7%"><html:button property="" styleClass="formbutton" onclick="search()">
												<bean:message key="knowledgepro.admin.search" />
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
						
				<logic:notEmpty name="examScheduleForm" property="searchExamSchToList">	
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
												<td height="25" align="center" class="row-odd" width="5%" >
												<bean:message key="knowledgepro.admin.subject.subject.s1no" />
												</td>
												<td height="25" class="row-odd" align="center" width="20%"><bean:message
													key="knowledgepro.admin.template.Date" /></td>
												<td class="row-odd" align="center" width="20%"><bean:message
													key="knowledgepro.admin.session" /></td>
												<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.admin.session.order" /></td>	
												<td class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.edit" /></div>
												</td>
												<td class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.delete" /></div>
												</td>
										</tr>
					                	<nested:iterate id="to" name="examScheduleForm" property="searchExamSchToList" indexId="count">
					                	<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
											<td height="25"  width="5%">
											<div align="center"><c:out value="${count+1}" /></div>
											</td>
											<td height="25"  align="center" width="20%"><bean:write
												name="to" property="examDate" /></td>
											<td  align="center" width="20%"><bean:write
												name="to" property="session" /></td>
											<td  align="center" width="10%"><bean:write
												name="to" property="sessionOrder" /></td>
											<td height="25"  width="15%">
											<div align="center"><img
												src="images/edit_icon.gif" width="10%" height="20"
												style="cursor: pointer"
												onclick="editExamSchedule('<bean:write name="to" property="id" />')" /></div>
											</td>
											<td width="15%" height="25" >
											<div align="center"><img
												src="images/delete_icon.gif" width="12%" height="20"
												style="cursor: pointer"
												onclick="deleteExamSchedule('<bean:write name="to" property="id" />')" /></div>
											</td>
				                 		</nested:iterate>
									
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
				</logic:notEmpty>		
				</table>
				</td>
				<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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

