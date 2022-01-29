<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
	function backFrontScreen() 
	{
	 document.location.href="StudentLoginAction.do?method=studentLoginAction";
  	}
	function addMenuAssign() {
		document.getElementById("method").value = "SavePrintApplication";
		document.loginform.submit();
	}

	function DownloadApplicationForm(){
		document.getElementById("method").value = "RepeatExamApplicationDownload";
		document.loginform.submit();
	}

	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	
/*	function selectAll(obj) {
		var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
	}
	
	function unCheckSelectAll() {
		 var inputs = document.getElementsByTagName("input");
		 var inputObj;
		 var checkBoxOthersSelectedCount = 0;
		 var checkBoxOthersCount = 0;
		 for(var count1 = 0;count1<inputs.length;count1++) {
		          inputObj = inputs[count1];
		          var type = inputObj.getAttribute("type");
		            if (type == 'checkbox' && inputObj.id != "checkAll") {
		                  checkBoxOthersCount++;
		                  if(inputObj.checked) {
		                        checkBoxOthersSelectedCount++;
		                        inputObj.value="on";
		                  }else{
		                	  inputObj.value="off";	
		                      }   
		            }
		    }
		    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
		      document.getElementById("checkAll").checked = false;
		    } else {
		      document.getElementById("checkAll").checked = true;
		    } 
	} */
	
</script>
<html:form action="/MidSemRepeatExamApplication">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="SavePrintApplication"/>
	
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Application For Mid Semester Exam</strong></div>
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
				<logic:equal property="attemtsCompleted" value="true" name="loginform">
				<tr>
				<td colspan="4" align="center" style="color: red; font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 14px;">Your chances to appear for Mid semester repeat exam is already over. Please contact Controller of Examinations for any clarification</td>
				</tr>
				</logic:equal>
				<logic:equal property="attemtsCompleted" value="false" name="loginform">
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
							<tr>
							<td align="left" colspan="5"><font size="2">
							Steps to be followed for mid semester repeat application </font></td>
							</tr><tr>
							<td align="left" colspan="5"><font size="2" >
                             Step 1 - Select the subjects which you wish to apply for mid semester repeat exam. Once the application is submitted, no editing is possible.<br></font></td>
                             </tr><tr>
							<td align="left" colspan="5"><font size="2">
                             Step 2 - Print the application and submit the same to HOD with relevant documents.<br></font></td>
                             </tr><tr>
							<td align="left" colspan="5"><font size="2">
                             Step 3 - With HOD's approval, submit the form personally to Controller of Examinations.<br></font></td>
                             </tr>
                           <logic:equal property="feesExemption" name="loginform" value="true">
                           <tr>
                           	<td align="left" colspan="5"><font size="2">
                             Step 4 - After the approval from Controller of Examinations, Print the Hallticket and produce it on all the days of examination.<br></font></td>
                             </tr>
                           </logic:equal>
                           <logic:equal property="feesExemption" name="loginform" value="false">
							<tr>
							<td align="left" colspan="5"><font size="2">
                             Step 4 - After the approval from Controller of Examinations, fee payment can be done online.<br></font></td>
                             </tr><tr>
							<td align="left" colspan="5"><font size="2">
                             Step 5 - Print the Hallticket and produce it on all the days of examination.<br></font></td>
							</tr>
						</logic:equal>
							<tr>
							<td height="20" class="studentrow-white" colspan="6">&nbsp; </td>
						   </tr>
						<tr bgcolor="#CEECF5">
						   <td height="30" align="center" colspan="2"><font size="2" color="#07190B">Register No : <bean:write name="loginform" property="midSemRepeatRegNo"></bean:write></font></td>
						   <td height="30" align="center" colspan="1"><font size="2" color="#07190B">Name : <bean:write name="loginform" property="midSemStudentName"></bean:write></font></td>
						   <td height="30" align="center" colspan="2"><font size="2" color="#07190B">Class : <bean:write name="loginform" property="midSemClassName"></bean:write></font></td>
						</tr>
								<tr>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.code" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.fee" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.exam.revaluation.offline.applyFor" /></div></td>
								</tr>
								<logic:notEmpty name="loginform" property="midSemRepeatList">
								<tr>
									<nested:iterate id="prev" name="loginform" property="midSemRepeatList" indexId="count">
									<%
                						String styleId="check_"+count;
										String flag="flag_"+count;
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
										  <td width="15%" align="center"><nested:write name="prev" property="amount" /></td>
										  <td width="15%"align="center" >
												<nested:hidden property="tempChecked" styleId="<%=flag %>" name="prev"></nested:hidden>
										 <c:choose>
					 					  <c:when test="${loginform.isDownloaded!=null && loginform.isDownloaded==false}">
					 					  <nested:checkbox property="checked" styleId="<%=styleId%>"> </nested:checkbox>
					 					  </c:when>
					 					    <c:otherwise>
					 					    <nested:checkbox property="checked" styleId="<%=styleId%>" disabled="true"> </nested:checkbox>
					 					    </c:otherwise>
					 					  </c:choose>
				                   					
				                   					<script type="text/javascript">
				                   							var id=<c:out value='${count}'/>;
				                   							var flag=document.getElementById("flag_"+id).value;
				                   							if(flag=="on"){
				                   								document.getElementById("check_"+id).checked = true;
				                       							}
				                   					</script>
										   </td>
										   
										   
										   
									</nested:iterate>
									
									</tr>
									<tr class="studentrow-even">
										<td colspan="2" height="25"><span class="Mandatory">*</span>Reason for not attending regular mid semester examination:- </td>
										<td colspan="3">
														
										<c:choose>
					 					  <c:when test="${loginform.isDownloaded!=null && loginform.isDownloaded==false}">
					 					  <html:textarea property="midSemRepeatReason" cols="100" rows="2" onkeypress="return imposeMaxLength1(this, 490);" onchange="return imposeMaxLength1(this, 490);"></html:textarea>
					 					  </c:when>
					 					    <c:otherwise>
					 					   <html:textarea property="midSemRepeatReason" cols="100" rows="2" disabled="true"></html:textarea>
					 					    </c:otherwise>
					 					  </c:choose>
					 					  </td>
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
					 	<c:when test="${loginform.isDownloaded!=null && loginform.isDownloaded==false}">
					 	 <html:submit property="" styleClass="btnbg" value="Save & Download"></html:submit>&nbsp;&nbsp;
					 	</c:when>
					 	<c:otherwise>
					 	<html:button property="" styleClass="btnbg" value="Download Application Form" onclick="DownloadApplicationForm()"></html:button>	
					 	</c:otherwise>
					 	</c:choose>
					<html:button property="" styleClass="btnbg" value="Close" onclick="backFrontScreen()"></html:button>	
					</td>
					<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                </tr>
                </logic:equal>
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
	var url = "MidSemRepeatExamApplication.do?method=RepeatExamApplicationPrint";
	myRef = window .open(url, "Repeat Mid Semester Exam Application Print", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>