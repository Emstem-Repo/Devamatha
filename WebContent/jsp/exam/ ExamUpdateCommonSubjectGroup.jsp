
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript" language="javascript"><!--
	function getClasses(academicYear) {
		
		 {
			var args = "method=getClassesByYear&year=" + academicYear;
			var url = "AjaxRequest.do";
		requestOperation(url, args, updateClass);
		}
		//getClassesByYearInMuliSelect("classMap", academicYear,
		//"selectedClasses", updateClass);
	}

	function updateClass(req) {
		updateOptionsFromMapMultiselect(req, "selectedClasses");

	}
	function update() {
		var flag = false;
		//var selectedClasses=document.getElementById("selectedClasses").value;
		var deleteConfirm = confirm("This process updates the common subject groups to all students in the selected class")
		if (deleteConfirm) {
			flag = true;
			//document.location.href = "ExamUpdateCommonSubjectGroup.do?method=getStudents&selectedClasses="
				//	+ selectedClasses;
		}
		
		return flag;
		//return confirm("This process updates the common subject groups to all students in the selected class");

	}
</script>

<html:form action="/ExamUpdateCommonSubjectGroup.do"
	onsubmit="return update()">

	<html:hidden property="method" styleId="method" value="getStudents" />
	<html:hidden property="formName"
		value="ExamUpdateCommonSubjectGroupForm" />
	<html:hidden property="pageType" value="1" />




	<table width="100%" border="0">
		<tr>
		<tr>


			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam" /></a> <span
				class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.updateCommonSubGrp" /> &gt;&gt;</span></span>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">


				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<p><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.updateCommonSubGrp" /></strong></p>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" style="color: red"><span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="message" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages>
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
									<td width="24%" height="128" class="row-odd">
									<div align="right">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.year" /> :</div>
									</div>
									</td>

									<td class="row-even" width="25%"><html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo"  onchange="getClasses(this.value)">
											<cms:renderYear  >  </cms:renderYear>
										
									</html:select></td>

									<td width="22%" align="center" valign="middle" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.class" />:</div>
									</td>


									<td width="33%" class="row-even"><html:select
										name="ExamUpdateCommonSubjectGroupForm"
										property="selectedClasses" styleId="selectedClasses"
										styleClass="body" multiple="multiple" size="5"
										style="width:200px">
										<c:if
											test="${ExamUpdateCommonSubjectGroupForm.academicYear != null && ExamUpdateCommonSubjectGroupForm.academicYear != ''}">
											<c:set var="classMap"
												value="${baseActionForm.collectionMap['classMap']}" />
											<c:if test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									<c:if test="${operation=='val'}">
												<logic:notEmpty name="ExamUpdateCommonSubjectGroupForm" property="selectedClasses">
												<html:optionsCollection property="selectedClasses" label="value"
													value="key" />
													</logic:notEmpty>	
										
									</c:if>
									</html:select></td>
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

					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="51%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton"
								value="Update Common Subject Groups" /></td>
							<td width="1%" height="35" align="center">&nbsp;</td>
							<td width="48%" align="left">&nbsp;</td>
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
		<tr>
			<td>&nbsp;</td>

		</tr>
	</table>



</html:form>

