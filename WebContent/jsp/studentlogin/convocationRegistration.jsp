<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function guestRelationShip(value) {
		var passAvailable=document.getElementById("passAvailable").value;
		var onepassAvailbale=document.getElementById("onepassAvailbale").value;
		if(value=='true' && onepassAvailbale=='true'){
			document.getElementById("relationShip").style.display = "block";
			document.getElementById("relationShip").innerHTML = "<table width='100%'> <tr height='25'> <td class='studentrow-odd' align='right' width='50%'>select the Pass:</td> <td class='studentrow-odd' align='left'> <input type='radio' value='1' name='passes'> 1 Pass</td> </tr></table>";
			document.getElementById("buttonId").value="proceed With Online Payment";
		}else if(value=='true' && passAvailable=='true'){
			document.getElementById("relationShip").style.display = "block";
			document.getElementById("buttonId").value="proceed With Online Payment";
		}else if(value == 'true' && passAvailable=='false'){
			document.getElementById("buttonId").value="Submit";
			document.getElementById("relationShip").style.display = "block";
			document.getElementById("relationShip").innerHTML=" <table width='100%'> <tr tr height='25'><td width='100%' colspan='2' class='studentrow-odd' align='center'>Passes are not available</td> </tr></table>";
			document.getElementById("pass2").checked = true;
		}else{
			document.getElementById("relationShip").style.display = "none";
			document.getElementById("buttonId").value="Submit";
		}
	}
	</script>
<html:form action="/onlineExamSuppApplication" >
	<html:hidden property="method" styleId="method" value="getOnlinePaymentPage" />
	<html:hidden property="formName" value="onlineExamSuppApplicationForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="convocationRelation" styleId="convocationRelation"/>
	<html:hidden property="passAvailable" styleId="passAvailable" name="onlineExamSuppApplicationForm"/>
	<html:hidden property="onepassAvailbale" styleId="onepassAvailbale" name="onlineExamSuppApplicationForm"/>
	
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">CONVOCATION REGISTRATION</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr><td colspan="2">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
             </td>
						</tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<logic:equal value="true" property="recordExist" name="onlineExamSuppApplicationForm">
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="100%" class="boxheader" align="center">
									<FONT color="red">
										You have Already Registered.</FONT>
									</td>
								</tr>
								</table>								
							</logic:equal>
							<logic:equal value="false" property="recordExist" name="onlineExamSuppApplicationForm">
							
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="100%" colspan="4">
									<table>
										<tr class="row-white">
						                   <td width="100%" class="heading" colspan="4"><div align="center">
						                   	<FONT size="3px">
												<bean:write name="onlineExamSuppApplicationForm" property="convocationDate"/></FONT><BR/><BR/>
											</div></td>
					                 	</tr>
										
										<tr> 
										<td width="10%"></td>
										<td width="90%" class="heading">
										Instructions:- <BR/><BR/>
										
										1. All eligible candidates  are expected to  confirm their participation online through their Knowledge Pro login. <BR/>
										   Registration is mandatory and Last date for registration is 19th March 2013<BR/><BR/>
										   
								        2. This information is sought to make arrangements of the Convocation. The Controller of Examination of <BR/>
								           Christ University is the final authority to decide your eligibility to attend the Convocation.<BR/><BR/>
								           
										3. No substitute is allowed to receive your degree. Failure to attend the convocation will entail a penalty of Rs 1000.<BR/><BR/>

										4. Each student is entitled to a maximum of two passes for the guests. Each pass costing Rs 200 can be booked through <BR/>
										   online using the smart card and it will be available only on first cum first served basis. Students who are successful <BR/>
										   in booking the passes can collect the passes from Office of Examination, Block I, on or before 19 th March. Please note <BR/>
										   that  the seats in the auditorium will be available only on first cum first served basis.<BR/><BR/>
										</td></tr>
										
										<tr> 
										<td width="10%"></td>
										<td width="90%" class="heading">
											FAQs <BR/><BR/>
										
										1. Where do I report on the Convocation day?<BR/>
										 &nbsp;&nbsp;&nbsp;   Ans: Contact your HOD<BR/><BR/>
										
										2. Am I eligible for more than 2 passes?<BR/>
										   &nbsp;&nbsp;&nbsp; Ans: No, each student can get maximum of 2 passes.<BR/><BR/>
										
										3. If my smart card is lost/not working how do I proceed?<BR/>
										  &nbsp;&nbsp;&nbsp;  Ans: Please approach the Office of Examination, Block I, and apply either with your friends smart card or <BR/>
										  &nbsp;&nbsp;&nbsp;  by paying the required amount through the Bank in the prescribed challan available in the Office of Examination.<BR/><BR/>
										
										4. When can I collect the passes from the Office of Examinations?<BR/>
										  &nbsp;&nbsp;&nbsp;  Ans: The Convocation passes can be collected from the Office of Examinations in both the campuses <BR/>
										  &nbsp;&nbsp;&nbsp;  from 9:00 AM to 5:00 PM (08th March 2013, 12:00 noon onwards)<BR/><BR/>
										  
								    </td></tr>
									</table>
									</td>
								</tr>
								
								 <tr class="row-white">
                 <td width="50%" class="studentrow-odd"><div align="right">I <bean:write name="onlineExamSuppApplicationForm" property="studentName"/> confirm my participation in the convocation :</div></td>
                <td height="25" width="50%" class="studentrow-even"><html:checkbox property="participation" name="onlineExamSuppApplicationForm"></html:checkbox> </td>
                 </tr>
                  <tr class="row-white">
                 <td width="50%" class="studentrow-odd"><div align="right">Whether Guest Pass is required:</div></td>
                 <td height="25" width="50%" class="studentrow-even">
                 	<html:radio property="guestPassRequired" styleId="pass1" value="true" onchange="guestRelationShip(this.value)">Yes </html:radio>
                 	<html:radio property="guestPassRequired" styleId="pass2" value="false" onchange="guestRelationShip(this.value)">No</html:radio>
                 </td>
                 </tr>
                 <tr>
                 <td colspan="4">
									<div id="relationShip">
									<table width="100%">
									<tr>
						                 <td width="50%" class="studentrow-odd" align="right">select the number of passes:</td>
						                 <td height="25" width="50%" class="studentrow-even">
											<html:radio property="passes" value="1">1 Pass </html:radio>
                 							<html:radio property="passes" value="2">2 Passes </html:radio>			 	
										</td>
					                </tr></table></div></td> </tr>
                  <tr class="row-white">
                   <td colspan="2"><div align="center">
					<html:submit value="Submit" styleClass="btnbg" styleId="buttonId"></html:submit>	&nbsp; <html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
                 </table>
                 </logic:equal>
						</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>

							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
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
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
</html:form>
<script type="text/javascript">
	document.getElementById("relationShip").style.display = "none";
	var pass=document.getElementById("pass1").value;
	var passAvailable=document.getElementById("passAvailable").value;
	var onepassAvailbale=document.getElementById("onepassAvailbale").value;
	if(pass=='true' && document.getElementById("pass1").checked && onepassAvailbale=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML = "<table width='100%'> <tr height='25'> <td class='studentrow-odd' align='right' width='50%'>select the Pass:</td> <td class='studentrow-odd' align='left'> <input type='radio' value='1' name='passes'> 1 Pass</td> </tr></table>";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='false'){
		document.getElementById("buttonId").value="Submit";
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML=" <table width='100%'> <tr tr height='25'><td width='100%' colspan='2' class='studentrow-odd' align='center'>Passes are not available</td> </tr></table>";
		document.getElementById("pass2").checked = true;
	}else{
		document.getElementById("relationShip").style.display = "none";
		document.getElementById("buttonId").value="Submit";
	}
</script>