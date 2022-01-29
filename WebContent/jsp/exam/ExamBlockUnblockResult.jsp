<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>


<script type="text/javascript" language="javascript">
		checked = false;
		var ids="";
		
	function checkedAll () {
	ids="";
	 if (checked == false)
		 {
		 checked = true
		 }
	 else
		 {
		 checked = false
		 }
	 var candidatesSize = document.getElementById("listCandidatesSize").value;
	 
	for (var i = 0; i < candidatesSize; i++) {
		var na="Student"+i;
		document.getElementById(na).checked = checked;
		if( checked ==true){
			 ids=ids+document.getElementById(na).value+",";
			
		}
	}
	 document.getElementById("classId_studentId").value=ids;
	}

	function updateValue(id1,id2, reason)
	{
		alert(id1 + ",         " + reason);
		 ids=ids+id1+"-"+id2+ "-" + reason + ",";
		  document.getElementById("classId_studentId").value=ids;
	}
	function updateUnBlock(id1){
		ids=ids+id1+",";
		 document.getElementById("classId_studentId").value=ids;
	}	
	function resetCall(){
		document.location.href = "ExamBlockUnblock.do?method=initBlockUnblock";
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamBlockUnblock.do" styleId="myform">
	<html:hidden property="formName" value="ExamBlockUnblockForm" />
	<html:hidden property="method" styleId="method"
		value="blockUnBlockExamResult" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="classId_studentId" styleId="classId_studentId" />
	<html:hidden property="examId"/>
	<html:hidden property="typeId"/>
	<html:hidden property="listCandidatesSize" styleId="listCandidatesSize"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.blockUnblock" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<p><strong class="boxheader"><bean:message
						key="knowledgepro.exam.blockUnblock" /> </strong></p>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							</div>
							<div align="right" class="mandatoryfield">
					<bean:message
						key="knowledgepro.mandatoryfields" /></div>
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
									<td width="23%" height="25" class="row-odd">
									<div align="right">
									<div align="right"><bean:message
										key="knowledgepro.exam.blockUnblock.examName" /> :</div>
									</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										name="ExamBlockUnblockForm" property="examName" /></td>
									<td width="23%" rowspan="2" align="center" valign="middle"
										class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.blockUnblock.type" />:</div>
									</td>
									<td width="31%" rowspan="2" class="row-even"><bean:write
										name="ExamBlockUnblockForm" property="type" /></td>
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
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.select" /> <input
										type='checkbox' name='selectcheckall' id="selectcheckall" onClick="checkedAll()" />
									</td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.class" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.regNo" /></td>
									
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.name" /></td>
									<td class="row-odd">Status</td>
										<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'Block'}">
											<td class="row-odd">Reason</td>
										</c:if>
										<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'UnBlock'}">
											<td class="row-odd">Reason</td>
										</c:if>
								</tr>

								<c:set var="temp" value="0" />


								<nested:iterate name="ExamBlockUnblockForm"
									property="listCandidatesName" id="listCandidatesName"
									indexId="count">

									<%
										String dynamicStyle = "";
														if (count % 2 != 0) {
															dynamicStyle = "row-white";

														} else {
															dynamicStyle = "row-even";

														}
														String rowid = "Student" + count;
									%>
									<tr>
										
										
										
										
										<c:choose>
								<c:when
									test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'Block'}">

										<td width="10%" height="25" class='<%=dynamicStyle%>'>
										<div align="center">
										<nested:checkbox property="isSelected" ></nested:checkbox>
										</div>
										</td>
								</c:when>
								<c:otherwise>
										<td width="10%" height="25" class='<%=dynamicStyle%>'>
										<div align="center">
										<nested:checkbox property="isSelected" ></nested:checkbox>
										</div>
										</td>

								</c:otherwise>
							</c:choose>
									
										<td width="10%" height="25" class='<%=dynamicStyle%>'><bean:write
											name="listCandidatesName" property="className" /></td>
										<td width="20%" class='<%=dynamicStyle%>'><bean:write
											name="listCandidatesName" property="regNumber" /></td>
										
										<td width="20%" class='<%=dynamicStyle%>'><bean:write
											name="listCandidatesName" property="name" /></td>
										<td width="20%" class='<%=dynamicStyle%>'><bean:write
											name="listCandidatesName" property="status" /></td>
										<c:if test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'Block'}">
											<td width="40%" class='<%=dynamicStyle%>'>
											<nested:text  property="reason" styleId="reason" maxlength="300"></nested:text>
											</td>
										</c:if>	
										<c:if test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'UnBlock'}">
											<td width="40%" class='<%=dynamicStyle%>'>
											<bean:write	name="listCandidatesName" property="reason" />
											</td>
										</c:if>											
										
										
									</tr>

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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="94%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr><td style="height: 10px" align="center" colspan="6"></td></tr>
						<tr>
							<td width="20%" height="35" align="center">&nbsp;</td>
							<td width="20%" height="35" align="center">&nbsp;</td>
							<td width="20%" align="center"><c:choose>
								<c:when
									test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'Block'}">

									<input name="BlockUnBlock" type="submit" class="formbutton"
										value="Block" />
								</c:when>
								<c:otherwise>
									<input name="BlockUnBlock" type="submit" class="formbutton"
										value="UnBlock" />

								</c:otherwise>
							</c:choose></td>
							<td width="20%" align="center"><input name="Submit"
								type="reset" class="formbutton" value="Cancel" onclick="resetCall()" /></td>
							<td width="20%" align="center">&nbsp;</td>
							<td width="20%" align="center">&nbsp;</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="6"></td></tr>	
						<tr>
							<td height="35" align="center">&nbsp;</td>
							<td height="35" align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
						</tr>
					</table>
					</td>
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