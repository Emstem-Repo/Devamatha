<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

function cancelAction(){
	document.location.href="StudentLoginAction.do?method=returnHomePage";
}

function printApplication()
{
	var url ="newSupplementaryImpApp.do?method=showPrintDetailsForRegular";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}

function printChallanApplication()
{
	var url ="newSupplementaryImpApp.do?method=showPrintChallanForRegular";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function submitApplication(){
	document.location.href="newSupplementaryImpApp.do?method=submitRegularApplication";
}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="calculateAmount" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Regular Application
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Regular Application</strong></td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20">
					<table width="100%">
					<tr>
							<td align="left">
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="err"><html:errors/></span>
							</p>
							</div>
							</div>

							<div id="messages"><div class="display-info">
							<span id="msg"><html:messages id="message" property="messages" message="true"><c:out value="${message}" escapeXml="false"></c:out><br></html:messages></span>
							</div>
							</div>
							<script type="text/javascript">
								if(document.getElementById("msg")==null ||  document.getElementById("msg").innerHTML==''){
									document.getElementById("messages").style.display="none";
									}
								if(document.getElementById("err").innerHTML==''){
									document.getElementById("errorMessages").style.display="none";
									}
							</script>
							
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
							<td valign="top" align="center">
	
	<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="regularAppAvailable">
	<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="isAttendanceShortage">
							
							
	<table width="100%" cellspacing="1" cellpadding="2">
							
							
							
		<tr>
		
		<td width="70%" align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="110" width="440" ></img>
		</td>
		<td>
		
		<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		 
		 
		 <tr>
		 <td align="center" style="border:none">Details of Fee Remitted
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Rs &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; :..................</div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Challan No:..........</div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Date &nbsp; &nbsp; &nbsp; &nbsp; :................</div>
		 </td>
		 </tr>
		</table>
		
		
		</td>
		</tr>
		
		<tr>
		<td colspan="3"></td>
		</tr>
		
		<tr>
		<td colspan="3" style="text-align: center; font-size: 17px;">
		<b> APPLICATION FOR REGISTRATION OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" />,<bean:write name="newSupplementaryImpApplicationForm" property="month" />-<bean:write name="newSupplementaryImpApplicationForm" property="year" /> </b>
		</td>
		</tr>
		<tr>
		<td colspan="3"></td>
		</tr>
		
		  
		<tr>
		 <td colspan="3">
		 
		 <table width="100%" >
		 
		  <tr>
		 <td style="padding-left: 70px; font-size: 16px;" colspan="2" width="40%"><b>Register Number: &nbsp; &nbsp; <bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></b>
			
		 </td><!--
		 <td style="padding-left: 10px;font-size: 16px;"><b><bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></b></td>
		 
		 --></tr>
		</table>
		 </td>
		 </tr>
		
		
		
		 <tr>
		 <td colspan="3">
		 
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;"><!--
		 <tr>
		 <td align="center" >1</td>
		 <td >Name of the Department</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="courseDep" /></td>
		 </tr>
		 --><tr>
		 <td align="center" >1</td>
		 <td >Name of the Programme</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 </tr>
		 <tr>
		 <td align="center" >2</td>
		 <td >Semester</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="schemeNo" /></td>
		 </tr>
		 <tr>
		 <td align="center" width="5%" >3</td>
		 <td width="35%" >Examination appearing for  (Regular/ Supplementary/ Improvement)</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="examType" /></td>
		 </tr>
		
		 <tr>
		 <td height="25%" align="center" >4</td>
		 <td >Name of the Candidate
																	<br>[as in Xth or SSLC records]
		 </td>
		 <td height="25%" colspan="2" style="text-transform: uppercase;padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		 <tr>
		 <td height="25%" align="center" >5</td>
		 <td height="25%" >Address for Communication with Telephone Number & E-mail id</td>
		 <td height="25%" colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="address" />
		 <bean:write name="newSupplementaryImpApplicationForm" property="mobileNo" /> & 
		 <bean:write name="newSupplementaryImpApplicationForm" property="email" />
		 </td>
		 </tr>
		 <tr>
		 <td align="center" >6</td>
		 <td >Date of Birth</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="originalDob" /></td>
		 </tr>
		 <tr>
		 <td align="center" >7</td>
		 <td >Whether eligible for fee concession, If eligible, state the category</td>
		 <td width="15%" style=" padding-left: 10px;">YES/NO</td>
		 <td  style=" padding-left: 10px;">Category:....................................</td>
		 </tr>
		 
		  
		 <tr>
		 <td align="center" >8</td>
		 <td >Whether the attendance of the Candidate is satisfactory at the time of submitting the application/ shortage will be condoned.</td>
		  <td width="15%" style=" padding-left: 10px;">YES</td>
		 <td  style=" padding-left: 10px;">Applied for condonation<br>
		 (Specify):....................................</br></td>
		 </tr>
		 
		 
		 
		 </table>
		 
		 
		
		 </td>
		 
		 
		 
		 </tr>
		 
		 
		 <tr>
		 <td colspan="3">
		 <p align="center" style="font-size: 14px;"><b>Details of Courses to which Candidate is Appearing</b></p>
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		<tr style="border-collapse: collapse;border: 1px solid balck;">
		 <td width="10%" style="text-align: center;">Type</td>
		 <td width="60%" style="text-align: center;">Title</td>
		 </tr>	
		 
		 <nested:notEmpty property="supSubjectList" name="newSupplementaryImpApplicationForm">
		 <nested:iterate property="supSubjectList" id="testType" indexId="count" name="newSupplementaryImpApplicationForm">
		 <c:if test="${testType.subjectType=='T'}">
		 <tr>
		 <td >Theory</td>
		 <td style=" "><nested:write  property="subjectName" /></td>
		 
		 
		 </tr>
		
		 </c:if>
		 </nested:iterate>
		 
		  <tr style="border-collapse: collapse;border:none;padding: 2px">
		 <td colspan="2" ><br></br></td>
		 </tr>
		 
		
		 
		  <nested:iterate property="supSubjectList" id="testType" indexId="count" name="newSupplementaryImpApplicationForm">
		 <c:if test="${testType.subjectType=='P'}">
		   <tr>
		 <td >Practical</td>
		 <td style=" "><nested:write  property="subjectName" /></td>
		 
		 
		 </tr>
		
		 <tr style="border-collapse: collapse;border:none;">
		 <td colspan="2" align="right"> Attested by Class Teacher </td>
		 
		 </tr>
		                                                                                                     
		 </c:if>
		 </nested:iterate>
		 </nested:notEmpty>
		 
		 
		 </table>
		 <!--  
		 *  T-Theory, P-Practical and V-Viva Voce
		 -->
		 </td>
		 
		 
		 
		 </tr>
		  <tr><td colspan="3">
		  <table width="100%" >
		 
		   <tr>
		 
		 <td  width="35%" style="height: 100px;font-size: 12px;">Place:		<br></br>							
			Date:	 
				
		</td>
		<td width="30%" style="height: 100px;text-align: right;font-size: 12px;"><b>Signature of Student	</b> 	</td>
		 <td width="35%" style="height: 100px;text-align: right;font-size: 12px;"><b>Signature of the H.O.D	</b> </td>
		 
		 
		 
		 </tr>
		
		 
		<!--   
		 <tr>
		 <td width="35%" style="font-size: 12px;">	<b>Signature of Class Teacher/Mentor</b> </td>
		 <td width="30%">	 </td>
		 <td width="35%" style="text-align: right;font-size: 12px;"><b>Seal & Signature of the H.O.D</b>	 </td>
		 </tr>
		 -->
		 
		 <!--  
		 <tr>
		 <td colspan="3">
		 <table width="100%" border="0" style="border-collapse: collapse;font-size: 12px;">
		 <tr style="border-collapse: collapse;">
		 <td >Application Fee - 25</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Practical Paper - 50*1=50</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Marklist Fee - 30</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >CV Fee - 150</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Online Service Charge - 20</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Total=775</td>
		 </tr>
		
		 
		  <tr style="border-collapse: collapse;">
		 
		 <td ><br></br> </td>
		 </tr>
		 
		 <tr style="border-collapse: collapse;">
		 
		 <td >NB:The students who are eligible for fee concession are required to remit R.S 20 only.</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >*  Attach the copy of the challan </td>
		 </tr>
		 </table>
		 		
		 </td>
		 </tr>
		 
		 -->
		 
		 </table>
		</td>
		</tr>
		
					
			</table>
		
		<p style="page-break-after:always;"> </p>
		
	<table width="100%" >
							
							<tr height="10px">
							<td colspan="3" class="row-print-desc">
							<logic:notEmpty name="newSupplementaryImpApplicationForm" property="description">
								<c:out value="${newSupplementaryImpApplicationForm.description}" escapeXml="false"></c:out>
							</logic:notEmpty>
							</td>
							</tr>
	</table>	
	</nested:equal>
		</nested:equal>					
	
		</td>
		
		
							<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="regularAppAvailable">
								Application is not available
							</nested:equal>
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="isAttendanceShortage">
								You are Not eligible for Writing the Examination Registration.Your Attendance is Below 75%.
							</nested:equal>
							
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
							<td width="47%" height="29">&nbsp;</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<nested:equal value="true" property="extended" name="newSupplementaryImpApplicationForm">
						<tr>
						<td width="47%" height="35" colspan="3">
						<div align="center" style="font-weight: bold;font-size: 12px;color: red"><B>Late submission fee: <bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/></B>
						
						</div>
						</td>
						</tr>
					</nested:equal>
						
						<tr>
							<td width="47%" height="35">
							<div align="right">
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="regularAppAvailable">
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="isAttendanceShortage">
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="isSubmitted">
							<html:button property=""
								styleClass="btnbg" value="Submit"
								onclick="submitApplication()"></html:button>
								</nested:equal>
								&nbsp; &nbsp; 
								<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="isSubmitted">
							<html:button property=""
								styleClass="btnbg" value="Print Application"
								onclick="printApplication()"></html:button>
							<html:button property=""
								styleClass="btnbg" value="Print Challan" 
								onclick="printChallanApplication()"></html:button>
								&nbsp; &nbsp; 
							
							
							</nested:equal>
							</nested:equal>
							</nested:equal>
							</div>
							</td>
							<td width="1%"></td>
							<td width="46%">
							
							<html:button property=""
								styleClass="btnbg" value="Close"
								onclick="cancelAction()"></html:button>
							</td>
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
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
var print = "<c:out value='${newSupplementaryImpApplicationForm.printSupplementary}'/>";
if(print.length != 0 && print == "true") {
	var url ="newSupplementaryImpApp.do?method=showPrintDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>
