<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
function backFrontScreen() {
	document.location.href="StudentLoginAction.do?method=studentLoginAction";
  }
function printHallTicket() {
	var url = "MidSemRepeatExamApplication.do?method=RepeatExamHallTicketPrint";
	myRef = window .open(url, "HallTicket For Repeat Mid Semester Exam", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
  }
</script>
<html:form action="/MidSemRepeatExamApplication">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="repeatExamFeePaymentProcess"/>
	
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Fee Payment For Mid Semester Repeat Exam</strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
					<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					 <td>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#CEECF5">
						   <td height="30" align="center" colspan="2"><font size="2" color="#07190B">Register No : <bean:write name="loginform" property="midSemRepeatRegNo"></bean:write></font></td>
						   <td height="30" align="center" colspan="1"><font size="2" color="#07190B">Name : <bean:write name="loginform" property="midSemStudentName"></bean:write></font></td>
						   <td height="30" align="center" colspan="4"><font size="2" color="#07190B">Class : <bean:write name="loginform" property="midSemClassName"></bean:write></font></td>
						</tr>
								<tr>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.code" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.fee" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.midsem.Repeat.exam.Applied.for" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.midsem.Repeat.exam.Approved" /></div></td>
										<td height="25" class="studentrow-odd"><div align="center">Rejected</div></td>
								</tr>
								<logic:notEmpty name="loginform" property="midSemRepeatList">
								<tr>
									<nested:iterate id="prev" name="loginform" property="midSemRepeatList" indexId="count">
									<%
                						String styleId="check_"+count;
										String flag="flag_"+count;
										String approveStyleId="checkApprove_"+count;
										String approveflag="flagApprove_"+count;
										String reject="reject_"+count;
										String rejectflag="flagReject_"+count;
                					%>
										<c:choose>
										 <c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										    	</c:when>
													<c:otherwise>
														<tr class="studentrow-white">
														</c:otherwise>
					 					  </c:choose>
					 					  
					 					  <td width="10%" height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
										  <td width="20%" align="center"><nested:write name="prev" property="subjectCode" /></td>
										  <td width="40%" align="left"><nested:write name="prev" property="subject" /></td>
										  <td width="10%" align="center"><nested:write name="prev" property="amount" /></td>
										  
										  <td width="10%"align="center" >
										  <nested:hidden property="tempChecked" styleId="<%=flag %>" name="prev"></nested:hidden>
					 					    <nested:checkbox property="checked" styleId="<%=styleId%>" disabled="true"> </nested:checkbox>
				                   			<script type="text/javascript">
				                   			  var id=<c:out value='${count}'/>;
				                   			  var flag=document.getElementById("flag_"+id).value;
				                   			  if(flag=="on"){
				                   			 document.getElementById("check_"+id).checked = true;
				                       		 }
				                   			 </script>
										   </td>
										   
										   <td width="10%"align="center" >
										  <nested:hidden property="tempCheckedApprove" styleId="<%=approveflag%>" name="prev"></nested:hidden>
					 					    <nested:checkbox property="checkedApprove" styleId="<%=approveStyleId%>" disabled="true"> </nested:checkbox>
				                   			<script type="text/javascript">
				                   			  var id=<c:out value='${count}'/>;
				                   			  var flag=document.getElementById("flagApprove_"+id).value;
				                   			  if(flag=="on"){
				                   			 document.getElementById("checkApprove_"+id).checked = true;
				                       		 }
				                   			 </script>
										   </td>
										   <td width="15%"align="center" >
											  <nested:hidden property="tempCheckedReject" styleId="<%=rejectflag%>" name="prev"></nested:hidden>
						 					  <nested:checkbox property="checkedReject" styleId="<%=reject%>" disabled="true"> </nested:checkbox>
					                   		  <script type="text/javascript">
					                   		  var id=<c:out value='${count}'/>;
					                   		  var flagReject=document.getElementById("flagReject_"+id).value;
					                   		  if(flagReject=="on"){
					                   		 	 document.getElementById("reject_"+id).checked = true;
					                       	  }
				                   		 </script>
										 </td>
											 
										   
									</nested:iterate>
									</tr>
								</logic:notEmpty>
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
				</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
                    <td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="50" align="center" colspan="2"> 
					   <c:choose>
            		     <c:when test="${loginform.isFeesPaid =='true'}">
              	   		<html:button property="" styleClass="btnbg" value="Print HallTicket" onclick="printHallTicket()"></html:button>	
              		     </c:when>
              		     <c:otherwise>
                		<html:submit property="" styleClass="btnbg" value="Proceed with Smart Card Payment"></html:submit>&nbsp;&nbsp;
              		    </c:otherwise>
              	      </c:choose>
					<html:button property="" styleClass="btnbg" value="Close" onclick="backFrontScreen()"></html:button>	
					</td>
					<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                </tr>
                <c:choose>
            		  <c:when test="${loginform.isFeesPaid =='false'}">
                <tr class="studentrow-white">
                <td valign="top" background="images/Tright_03_03.gif"></td>
                <td height="40" align="Left" colspan="2" style="color: black; font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 14px;">**Download your hall ticket after successful completion of fee payment
                </td>
                <td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                </tr>
                 </c:when>
                </c:choose>
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
var print = "<c:out value='${loginform.midSemPrint}'/>";
if(print.length != 0 && print == "true"){
	var url = "MidSemRepeatExamApplication.do?method=RepeatExamHallTicketPrint";
	myRef = window .open(url, "HallTicket For Repeat Mid Semester Exam", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>