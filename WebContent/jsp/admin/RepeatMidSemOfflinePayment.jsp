<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
	
	function Cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
  	}
    function Save(){
		document.getElementById("method").value = "SaveOfflinePayment";
		document.examMidsemRepeatForm.submit();
  	}
	
	function Search(){
		document.getElementById("method").value = "RepeatOfflinePayment";
		document.examMidsemRepeatForm.submit();
  	}
	
	function changeYear(year){
		document.getElementById("academicYear").value = year;
		getExamByYear("examList", year, "examId", updateExams);
	}
	function updateExams(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("examId");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}
		destination.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("option");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination.options[j+1] = new Option(label,value);
		 }
	}
	
	
</script>
<html:form action="/ExamMidsemRepeat">
	<html:hidden property="formName" value="examMidsemRepeatForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="5" />
	
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Mid Semester Exam Offline payment</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
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
						<tr>
							<td class="row-odd" width="25%" height="25">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
							<td class="row-even" width="25%" height="25"><div align="left">
							<input type="hidden" value="<bean:write name="examMidsemRepeatForm" property="year"/>" id="tempYear">
		                        <html:select property="year" styleId="academicYear" name="examMidsemRepeatForm" style="width:130px" onchange="changeYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				 <cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select></div>
		        			</td>
							<td class="row-odd" width="25%" height="25"><div align="right">
							  <span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.mid.semester.exam"/>:</div></td>
							  <td class="row-even"  width="25%" height="25" colspan="2">
							     <html:select name="examMidsemRepeatForm" property="examId" styleId="examId" styleClass="comboLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="examList"
													name="examMidsemRepeatForm" label="value" value="key" />
											
							   </html:select>
							   </td>
							 </tr>
							 <tr>
							 	<td class="row-odd" width="25%" height="25">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.reservation.registerNo"/>:</div></td>
								<td class="row-even" width="25%" height="25" colspan="4"><div align="left">
			                        <html:text property="midSemRepeatRegNo" styleId="registerNo" name="examMidsemRepeatForm" size="10"></html:text></div>
		        				</td>
							 </tr>
							 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
							 <tr>                    			
								<td height="50" align="center" colspan="6"> 
								 <html:button property="" styleClass="formbutton" value="Search" onclick="Search()"></html:button>&nbsp;&nbsp;
								<html:button property="" styleClass="formbutton" value="Close" onclick="Cancel()"></html:button>	
								</td>
			                </tr>							 
			                <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
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
				
				<logic:notEmpty name="examMidsemRepeatForm" property="midSemRepeatList">
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
			             <tr>
						    <td class="row-odd" height="25" colspan="2"><div align="right"><bean:message key="knowledgepro.fee.studentname"/> : </div></td>
							  <td class="row-even" height="25" colspan="1">
						        <span class="star"><bean:write name="examMidsemRepeatForm" property="midSemStudentName"/></span>
							   </td>
							   <td   height="25" class="row-odd" colspan="2"><div align="right"><bean:message key="knowledgepro.attendance.classname"/> : </div></td>
	                            <td class="row-even" colspan="2">
					           <span class="star"> <bean:write name="examMidsemRepeatForm" property="midSemClassName"/></span>
					           </td>
							   </tr>
						  
			             <tr><td height="25" class="row-white" ></td></tr>
			                   <tr>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.code" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.exam.midsem.Repeat.attendance.percentage" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.fee" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.midsem.Repeat.exam.Applied.for" /></div></td>
									<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.approve" /></div></td>
								</tr>
			             	  <tr>
							<nested:iterate id="prev" name="examMidsemRepeatForm" property="midSemRepeatList" indexId="count">
									<%
                						String styleId="check_"+count;
										String flag="flag_"+count;
										String approveStyleId="checkApprove_"+count;
										String approveflag="flagApprove_"+count;
                					%>
										<c:choose>
										 <c:when test="${count%2 == 0}">
											<tr class="row-even">
										    	</c:when>
													<c:otherwise>
														<tr class="row-white">
														</c:otherwise>
					 					  </c:choose>
					 					  
					 					  <td width="5%" height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
										  <td width="15%" align="center"><nested:write name="prev" property="subjectCode" /></td>
										  <td width="30%" align="left"><nested:write name="prev" property="subject" /></td>
										  <td width="10%" align="center"><nested:write name="prev" property="attenPersent" /></td>
										  <td width="10%" align="left"><nested:write name="prev" property="amount" /></td>
										  <td width="15%"align="center" >
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
										 <td width="15%"align="center" >
										  <nested:hidden property="tempCheckedApprove" styleId="<%=approveflag %>" name="prev"></nested:hidden>
					 					  <nested:checkbox property="checkedApprove" styleId="<%=approveStyleId%>" disabled="true"> </nested:checkbox>
				                   		  <script type="text/javascript">
				                   		  var id=<c:out value='${count}'/>;
				                   		  var flags=document.getElementById("flagApprove_"+id).value;
				                   		  if(flags=="on"){
				                   		  document.getElementById("checkApprove_"+id).checked = true;
				                       	  }
				                   		 </script>
										 </td>
									</nested:iterate>
									</tr>
									
								<tr>
			                        <td class="row-odd" width="25%" height="25" colspan="4">
										<div align="right" >&nbsp;Total Amount Payable:</div></td>
										<td class="row-even" width="25%" height="25" colspan="3"><div align="left"> <bean:write name="examMidsemRepeatForm" property="totalFees"/></div> </td>
																				
			                      </tr>
			                      <tr>
			                     	 <td class="row-odd" width="25%" height="25">
										<div align="right" >&nbsp;Fees Paid</div></td>
			                     	 <td  align="center" class="row-even">
										  <html:hidden property="tempOfflineFeesPaid" styleId="tempOfflineFeesPaid" name="examMidsemRepeatForm"></html:hidden>
					 					  <html:checkbox property="offlineFeePaid" styleId="offlineFeePaid"> </html:checkbox>
				                   		  <script type="text/javascript">
				                   		  var flags=document.getElementById("tempOfflineFeesPaid").value;
				                   		  if(flags=="on"){
				                   		  document.getElementById("offlineFeePaid").checked = true;
				                       	  }
				                   		 </script>
										 </td>
										 
										 <td class="row-odd" width="25%" height="25" colspan="2">
										<div align="right"><span class="Mandatory">*</span>&nbsp;Fee Payment Description</div></td>
										<td class="row-even" width="25%" height="25" colspan="3"><div align="left"> <html:text name="examMidsemRepeatForm" property="feePaymentDescription" maxlength="99"/></div> </td>
			                      </tr>
			                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
			                     <tr>
											<td height="50" align="center" colspan="6"> 
											 <html:button property="" styleClass="formbutton" value="Save" onclick="Save()"></html:button>&nbsp;&nbsp;
											<html:button property="" styleClass="formbutton" value="Close" onclick="Cancel()"></html:button>	
											</td>
						          </tr>			                     
						          <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                         </table>
                         </td>
							<td width="5" height="29" background="images/right.gif"></td>
                        </tr>
                        
                        </logic:notEmpty>
				
				
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