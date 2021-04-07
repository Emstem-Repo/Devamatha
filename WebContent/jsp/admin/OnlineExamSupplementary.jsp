<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type="text/javascript">

function submitData(method) {
	{
	document.getElementById("method").value=method;	
	document.onlineExamSuppApplicationForm.submit();
	}
}
function cancelAction() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

function PrintReceipt()
{
	var url = "onlineExamSuppApplication.do?method=initOnlineReciepts&count="+1;
	var browserName=navigator.appName; 
		 myRef = window.open(url,"Receipt","left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}


</script>
<html:form action="/onlineExamSuppApplication" >
	<html:hidden property="formName" value="onlineExamSuppApplicationForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1"/>
	
	
	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Holistic/Indian Constitution Repeat Exam Application</strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							
								
						<td align="left" colspan="3">
	               	    		<div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	   				<div id="errorMessage" style="color:red;font-family:arial;font-size:11px;">
	                       				<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										<span id="err"><html:errors/></span> 
										</p>
	                       					<FONT color="green"><B>
											<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
											</html:messages></B>
						 				 </FONT>
						  			</div>
							</td>
						</tr>
				<logic:equal property="printReceipt" value="true" name="onlineExamSuppApplicationForm">
				<tr>
				<td  colspan="4"><div style='color:red' align="center"><B><a href="#" class="navmenu" onclick="PrintReceipt()"><div style='color:red' align="center"><U>Click here to Print Hallticket</U></div></a></B></div>
				</td>
				</tr>
				</logic:equal>
				<logic:equal property="alreadyApplied" value="false" name="onlineExamSuppApplicationForm">
						<tr>
							<!-- <td width="5" background="images/st_left.gif"></td>-->
							<td width="100%" valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2" >
							
						<tr height="25px">
						<td  align="left" class="heading" colspan="3"> 
						<b> HED/EVS Repeat examination September 2013 (for the students failed or absent)
						</b></td></tr>

						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>Pay the examination  fee of Rs 200 online using your smart card and print your online receipt without fail.
						</b></td></tr>

						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>Produce the online receipt cum hall ticket at the venue of the examination, failing which you will not be permitted to take up the exam.
						</b></td></tr>
						
						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>Be present at the venue of the examination on the date and time given in the receipt cum hall ticket.
						</b></td></tr>
						
						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>It is mandatory to pass this examination, failing which your hall ticket for the end semester examinations will be with held.
						</b></td></tr>
										
				<tr></tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
			
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
				</logic:equal>
				<logic:equal property="alreadyApplied" value="true" name="onlineExamSuppApplicationForm">
					<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>You have applied for this Exam.
						</b>
						</td></tr>
					<tr>
					<td height="20"></td>
					</tr>	
					<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>Produce the online receipt cum hall ticket at the venue of the examination, failing which you will not be permitted to take up the exam.
						</b></td></tr>
						
						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>Be present at the venue of the examination on the date and time given in the receipt cum hall ticket.
						</b></td></tr>
						
						<tr height="25px"><td  align="left" class="heading" colspan="3"> 
						<b>It is mandatory to pass this examination, failing which your hall ticket for the end semester examinations will be with held.
						</b></td></tr>
										
				</logic:equal>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td height="25"></td>
						</tr>
						<tr>
                   			<td colspan="4"><div align="center">
                <logic:equal property="alreadyApplied" value="false" name="onlineExamSuppApplicationForm">
								<html:button property="" styleClass="btnbg" value="Proceed with Smart Card Payment" onclick="submitData('verifyStudentSmartCardForStudentLogin')"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp; 
								<html:button property="" styleClass="btnbg" value="Close" onclick="cancelAction()"></html:button>
			   </logic:equal>	
							<!--<logic:equal property="printReceipt" value="true" name="onlineExamSuppApplicationForm">
								<html:button property="" styleClass="btnbg" value="Print Receipt" onclick="PrintReceipt()"></html:button>
							</logic:equal>-->
					</div></td>
                 </tr>
                 <tr><td>&nbsp;</td></tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
