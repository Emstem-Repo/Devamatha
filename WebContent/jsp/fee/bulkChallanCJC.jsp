<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function resetMessages() {	
		resetErrMsgs();
	}	
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function checkData() {
		
		
	}
	function checkReprintBulkChallan(){
		document.getElementById("method").value="getReprintBulkChallanData";
		document.feePaymentForm.submit();
	}
	function checkprintBulkChallan(){
		document.getElementById("method").value="checkprintBulkChallan";
		document.feePaymentForm.submit();
	}
	
	
	function getClasses(year) {
		getClassesByYear("classMap", year, "classId", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classId", "- Select -");
	}
</script>
<html:form action="/bulkChallanPrint" method="post">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="feePaymentForm" />
	<html:hidden property="pageType" value="6" />
	<html:hidden property="feeGroupSelectedIndex" styleId="feeGroupSelectedIndex"/>
	<html:hidden property="feeGroupName" styleId="feeGroupName"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading">
				<bean:message key="knowledgepro.fee" />
			<span class="Bredcrumbs">&gt;&gt; Bulk Fee Payment &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Bulk Fee Payment</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="44" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempAcademicyear" name="academicYearClass"
										value="<bean:write name="feePaymentForm" property="academicYearForClass"/>" />
									<html:select 
										property="academicYearForClass" name="feePaymentForm" styleId="academicYearForClass"
										styleClass="combo" onchange="getClasses(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.bulk.fee.payment.next.class"/> :</div>
									</td>
									<td width="32%" height="25" class="row-even">
										<html:select name="feePaymentForm" property="classId" styleClass="combo" styleId="classId" >
											<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="feePaymentForm" property="classMap">
												<html:optionsCollection property="classMap" name="feePaymentForm" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</td>
								</tr>
								<tr>	
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.feeGroupName"/> :</div>
									</td>
									<td width="35%" class="row-even">
									<div align="left"><span class="star">
									<html:select property="selectedfeeGroup" styleClass="combo" styleId="feeGroupId">
										<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
										<html:optionsCollection property="feeGroupList" name="feePaymentForm" label="name" value="id"/>
									</html:select>
									</span>
									</div>
									</td>
									<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Financial Year:</div></td>
                             		<td width="22%" height="25" class="row-even">
                             		<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="feePaymentForm" property="year"/>"/>
                             		<html:select property="financialYearId" styleClass="combo" styleId="year">
									<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
									<cms:renderFinancialYear></cms:renderFinancialYear>
									</html:select>  
                             	</td>
								</tr>
								<tr>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.bulk.fee.payment.from.reg"/> :</div>
									</td>
									<td width="32%" height="25" class="row-even">
										<html:text property="fromReg" styleId="fromReg"></html:text>
									</td>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.bulk.fee.payment.to.reg"/> :</div>
									</td>
									<td width="32%" height="25" class="row-even">
										<html:text property="toReg" styleId="toReg"></html:text>
									</td>
								</tr>
								<tr>
									 <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Date :</div></td>
	                            	<td width="25%" height="25" class="row-even" align="left"><nested:text name="feePaymentForm" property="feeDate" styleId="feeDate"/>
			                            <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'feePaymentForm',
													// input name
													'controlname' :'feeDate'
												});
											</script>
	                                </td>
									<td width="16%" height="25" class="row-odd">
										&nbsp;
									</td>
									<td width="32%" height="25" class="row-even">
										&nbsp;
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
										<html:button property="" styleClass="formbutton" value="Submit" onclick="checkprintBulkChallan()">
											<bean:message key="knowledgepro.submit" />
										</html:button>

									</div>
									</td>
									<td width="2%"></td>
									<td align="left">
									
										<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()">
											<bean:message key="knowledgepro.cancel" />
										</html:button>
										<html:button property="" styleClass="formbutton" value="Reprint" onclick="checkReprintBulkChallan()">
											<bean:message key="knowledgepro.reprint" />
										</html:button>
										
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
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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
		<script type="text/javascript">
		var print = "<c:out value='${feePaymentForm.printChallan}'/>";
		if(print.length != 0 && print == "true") 
		{
	 		var obj1= document.getElementById("feeGroupId").selectedIndex;
			document.getElementById("feeGroupSelectedIndex").value=obj1;
			document.getElementById("feeGroupName").value = document.getElementById("feeGroupId").options[document.getElementById("feeGroupId").selectedIndex].text;
			var url ="bulkChallanPrint.do?method=printBulkChallan&feeGroupName="+document.getElementById("feeGroupName").value+
			"&feeGroupSelectedIndex="+document.getElementById("feeGroupSelectedIndex").value+
			"&classId="+document.getElementById("classId").value+
			"&selectedfeeGroup="+document.getElementById("feeGroupId").value+
			"&financialYearId="+document.getElementById("year").value+
			"&fromReg="+document.getElementById("fromReg").value+
			"&toReg="+document.getElementById("toReg").value+
			"&feeDate="+document.getElementById("feeDate").value;
			document.getElementById("feeGroupId").value="";
			document.getElementById("classId").value="";
			document.getElementById("year").value="";
			document.getElementById("fromReg").value="";
			document.getElementById("toReg").value="";
			myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
		}
		else 
		{
			var year = document.getElementById("tempyear").value;
			if(year.length != 0) {
		 	document.getElementById("year").value=year;
			} else if(year.length ==0 && error == "true"){
			document.getElementById("year").selectedIndex = 0;

		}	
	}
			var academicYear = document.getElementById("tempAcademicyear").value;
			if(academicYear.length != 0) {
		 	document.getElementById("academicYearForClass").value=academicYear;
			} 
	</script>
</html:form>
