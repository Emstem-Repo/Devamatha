<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	
	<script type="text/javascript">
	function getDetails(){
		document.getElementById("method").value = "ScLostCorrectionSearch";
		document.ScLostCorrectionForm.submit();
	}
	
	function resetMessages(){
		document.location.href = "ScLostCorrection.do?method=initScLostCorrection";
		resetErrMsgs();
	}

	function showStudent(){
		document.getElementById("studentDisplay").style.display = "block";
		document.getElementById("employeeDisplay").style.display = "none";
		resetErrMsgs();
	}

	function showEmployee(){
		document.getElementById("studentDisplay").style.display = "none";
		document.getElementById("employeeDisplay").style.display = "block";
		resetErrMsgs();
	}

	$(document).ready(function() {
		  $('#Submit').click(function(){
		       var regNo = $('#regNo').val();
		       
		       if(regNo==''){
		    	   $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Student Reg. No. OR Employee Id.</span>");
		          return false;
		        }
		        else{
		       		document.studentDetailsReportForm.submit();
		       	}
		      });
		});
	</script>

<html:form action="/ScLostCorrection" method="post">
<html:hidden property="formName" value="ScLostCorrectionForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method" value="ScLostCorrectionSearch"/>
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.smartcard" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.smartcard.lostcorrection" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.smartcard.lostcorrection" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span  class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
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
									<td height="25" colspan="2" class="row-even">
									<div align="Center">
									<html:radio property="isEmployee" styleId="stu" value="Student"
										onclick="showStudent()"></html:radio>
									Student &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="isEmployee" styleId="emp" value="Employee"
										onclick="showEmployee()"></html:radio>
									Employee 
									</div>
									</td>
							</tr>
							<tr>
									<td width="50%" height="25" class="row-odd" >
									<div id="studentDisplay" align="right">
                                    <span class="Mandatory">*</span>&nbsp;
                                    <bean:message key="knowledgepro.exam.blockUnblock.regNo"/>:</div>
                                    <div id="employeeDisplay" align="right">
                                    <span class="Mandatory">*</span>&nbsp;
                                    <bean:message key="knowledgepro.admin.employeeid"/>:</div>
                                    
                                    </td>
                                     
                                     <td width="50%" class="row-even"><div align="left"> <span class="star">
                                               <html:text property="regNo" styleId="regNo" size="20"  />
                                    </span></div></td>									
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
								<html:submit property="" styleClass="formbutton" value="Submit"  styleId="Submit"></html:submit>
									
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button>
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

document.getElementById("studentDisplay").style.display = "block";
document.getElementById("employeeDisplay").style.display = "none";

</script>