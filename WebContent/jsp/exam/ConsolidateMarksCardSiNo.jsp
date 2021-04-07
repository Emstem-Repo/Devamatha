<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function resetFormFields(){	
	document.getElementById("startNo").value=null;
}


</script>
<html:form action="/consolidatemarksCardSINO">	
	<html:hidden property="formName" value="consolidateMarksCardSiNoForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="save"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			Consolidate Marks Card No Entry
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Consolidate Marks Card No Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
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
							<td width="16%" class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div>
							</td>
							<td width="16%" class="row-even" valign="top"><input type="hidden" id="tempyear"
										name="tempyear"
										value="<bean:write name="consolidateMarksCardSiNoForm" property="year"/>" /><html:select
										name="consolidateMarksCardSiNoForm" property="year" styleId="year"
										styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderYear></cms:renderYear>
							</html:select></td>
							
							<td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                			<td width="16%" height="25" class="row-even" >
                    			<html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="consolidateMarksCardSiNoForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     						</html:select> 
               			 	</td>
               			 	
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.marks.si.no" /></div>
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="startNo" styleId="startNo" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span>
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
							<td width="45%" height="35">
							<div align="right">
								<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center">Program Type</div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center">Applied Year</div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.exam.marks.si.no.start" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.exam.marks.si.no.current" /></div>
											</td>
											
										</tr>
											<logic:notEmpty name="consolidateMarksCardSiNoForm" property="toList">
												<logic:iterate id="list" name="consolidateMarksCardSiNoForm" property="toList">
														<tr class="row-even">
														<td height="25">
														<div align="center"><c:out value="${1}" /></div>
														</td>
														<td align="center"><bean:write name="list"
															property="programTypeName" /></td>
														<td align="center"><bean:write name="list"
															property="academicYear" /></td>
														<td align="center"><bean:write name="list"
															property="startNo" /></td>
														<td align="center"><bean:write name="list"
															property="currentNo" /></td>
															
														</tr>
												</logic:iterate>
											</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
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
