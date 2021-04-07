<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/AdmissionSelectionProcess.js"></script>
<script type="text/javascript">
function close() 
{
	document.location.href = "LoginAction.do?method=loginAction";
 }
function RunProcess()
{
	document.getElementById("method").value="searchRunProcess";
	document.admSelectionProcessCJCForm.submit();
}

</script>
<html:form action="/admSelectionCJC">
	<html:hidden property="formName" value="admSelectionProcessCJCForm" />
	<html:hidden property="method" styleId="method" value=""/>
	
	<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs">Admission
		<span class="Bredcrumbs">&gt;&gt;Admission Selection Process</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Admission Selection Process</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
							<td class="row-odd" width="25%" height="25">
							<div align="right"><span class="Mandatory">*</span>&nbsp;Applied Year:</div></td>
							<td class="row-even" width="25%" height="25"><div align="left">
							<input type="hidden" value="<bean:write name="admSelectionProcessCJCForm" property="year"/>" id="tempYear">
		                           <html:select property="year" styleId="academicYear" name="admSelectionProcessCJCForm" style="width:130px" onchange="changeYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				 <cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
                       			   </html:select></div>
		        			</td>
		        			<!-- <td class="row-odd" width="20%" height="25"><div align="right">
							<span class="Mandatory">*</span>&nbsp;Gender</div></td>
		        			<td class="row-even"  width="30%" height="25">
		        			<html:radio property="gender" name="admSelectionProcessCJCForm" value="Boy" styleId="gender">Boy</html:radio>
					         <html:radio property="gender" name="admSelectionProcessCJCForm" value="Girl" styleId="gender">Girl</html:radio>
					         </td>-->
							</tr>  
		        			<tr>
		        			<td class="row-odd" width="20%" height="25"><div align="right">
							<span class="Mandatory">*</span>&nbsp;List</div></td>
		        			<td class="row-even"  width="30%" height="25">
		        			<html:select name="admSelectionProcessCJCForm" property="listName" styleId="listName" styleClass="comboLarge">
								<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								   		<html:option value="First">First </html:option>
								   		<html:option value="Second">Second</html:option>
										<html:option value="Third">Third </html:option>
								   		<html:option value="Fourth">Fourth</html:option>
										<html:option value="Fifth">Fifth </html:option>
							    	 </html:select>
							</td>
							<td class="row-odd" width="20%" height="25" align="right">
							<span class="Mandatory">*</span>&nbsp;University</td>
							<td class="row-even"  width="30%" height="25">
							<html:select name="admSelectionProcessCJCForm" property="universityId" styleId="universityId" styleClass="comboLarge">
								<html:option value=""><bean:message key="knowledgepro.admin.select" />
								</html:option>
								<logic:notEmpty property="universityMap" name="admSelectionProcessCJCForm">
								  		<html:optionsCollection property="universityMap" label="value" value="key"/>
								  </logic:notEmpty>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<td width="49%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Run Process"
										onclick="RunProcess()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
									<html:button property="" styleClass="formbutton" value="Close"
										onclick="close()"></html:button>
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
var tempYear=document.getElementById("tempYear").value;
if(tempYear!=null && tempYear!=""){
	document.getElementById("academicYear").value=tempYear;
}
</script>