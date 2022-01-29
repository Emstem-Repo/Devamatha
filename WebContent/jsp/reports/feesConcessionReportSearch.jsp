<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<script type="text/javascript">
	function markAsPaid(){
		if(validateCheckBox()) {
		document.getElementById("method").value = "markAsPaid";
		document.feePaidForm.submit();
		}
	}
	function enableClass()
	{
		document.getElementById("classDisplay").style.display="block";
		document.getElementById("dateDisplay").style.display="none";
	}
	function enableDate()
	{
		document.getElementById("dateDisplay").style.display="block";
		document.getElementById("classDisplay").style.display="none";
		
	}
	function submitPage(){
		document.getElementById("divisionName").value = document.getElementById("divId").options[document
										.getElementById("divId").selectedIndex].text;
		document.feesConcessionReportForm.submit();
	}
	function getClasses(ProgramTypeId) {
		getClassesByProgramType("classMap", ProgramTypeId, "class",
				updateClass);
		resetOption("class");		
	}
	function updateClass(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}

</script>

<html:form action="/FeeConcessionReport">

<html:hidden property="method" styleId="method" value="displayConcessionDetails"/>
<html:hidden property="formName" value="feesConcessionReportForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden name="feesConcessionReportForm" property="divisionName" styleId="divisionName"/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt;Fees Concession &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Fees Concession</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
		                         <tr >
							<td width="22%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type"/></div>
							</td>
							<td width="24%" height="25" class="row-even" align="left"><html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getClasses(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 			<c:if test="${programTypeList != null && programTypeList != ''}">
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
				    		</c:if>	
	     					</html:select> 
 							<span class="star"></span></td>

								<td width="25%" height="25" class="row-odd"><div align="right">
								<bean:message key="knowledgepro.fee.division"/><span class="star"></span>:</div></td>
                              <td width="25%" height="25" class="row-even" align="left"><span
												class="star">
                                <html:select property="divId"
												styleClass="combo" styleId="divId">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <html:optionsCollection name="feeDivList" label="name"
													value="id" />
                                </html:select>
                              </span></td>
							</tr>
 							<tr >
								<td width="25%" height="25" class="row-odd"><div align="right">
								Account<span class="star"></span>:</div></td>
                              <td width="25%" height="25" class="row-even" align="left"><span
												class="star">
                                <html:select property="accId"
												styleClass="combo" styleId="accId">
									<html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <html:optionsCollection name="accountList" label="name" 
													value="id" />
                                </html:select>
                              </span></td>							<td>
                      			<html:radio property="classOrDate" styleId="classOrDate1" value="true" onclick="enableClass()" >Classwise</html:radio>
								<html:radio property="classOrDate" styleId="classOrDate2" value="false" onclick="enableDate()">Datewise</html:radio></span>
							</td>
							<td>&nbsp;</td>
							
							</tr>
							<tr >
								<td colspan="4" class="row-even">
								 <table width="100%" cellspacing="1" cellpadding="2" id="classDisplay">
		                         <tr>
					

							<td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.attendanceentry.class" /></div></td>
				                <td height="25"  class="row-even" align="left" width="25%">
								<html:select name="feesConcessionReportForm" styleId="class" property="classId"  styleClass="combo">
 								<html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
									<logic:notEmpty name="classMap" >
										<html:optionsCollection name="classMap" label="value" value="key" />
									</logic:notEmpty>
								</html:select>
			                </td>
							<td width="25%" class="row-even">&nbsp;</td>
							<td width="25%" class="row-even">&nbsp;</td>
							
								</tr></table></td>

	
							</tr>
								<tr >
											<td colspan="4" class="row-even">
								 <table width="100%" cellspacing="1" cellpadding="2" id="dateDisplay">
		                         <tr>


		                           <td valign="top" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
		                           <td class="row-even" align="left" width="25%">
		                           		<html:text styleId="startDate" property="startDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'feesConcessionReportForm',
													// input name
													'controlname' :'startDate'
												});
											</script>
		                           </td>
		                           <td height="25" valign="top" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
		                           <td height="25" class="row-even" align="left" width="25%">
		                            		<html:text styleId="endDate" property="endDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'feesConcessionReportForm',
													// input name
													'controlname' :'endDate'
												});
											</script>
                                   <br></td>

									</tr></table></td>
		                         </tr>
	                       </table>
	                       </td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="right">
	              	 		<html:button styleClass="formbutton" value="Search" property="" onclick="submitPage()" ></html:button>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
                   </table>
                   </td>
                </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>
<script type="text/javascript">
	var classOrDate = "<c:out value='${feesConcessionReportForm.classOrDate}'/>";
	if(classOrDate.length != 0 && classOrDate == 'true') {
		document.getElementById("classDisplay").style.display="block";
		document.getElementById("dateDisplay").style.display="none";
	}
	else
	{
		document.getElementById("classDisplay").style.display="none";
		document.getElementById("dateDisplay").style.display="block";		
	}
	var print = "<c:out value='${feesConcessionReportForm.print}'/>";
	if(print.length != 0 && print == "true") {
		var url ="FeeConcessionReport.do?method=displayPage";
		myRef = window.open(url,"fee_concession_report","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>


