<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript"><!--
function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}
function printTc(studentId){
	var	fromReg=document.getElementById("fromUsn").value;
	var	toReg=document.getElementById("toUsn").value;			
	var tcReDate=document.getElementById("tcReDate").value;	
	var classes=document.getElementById("classes").value;
	var year=document.getElementById("academicYear").value;
	var studentName=document.getElementById("studentName").value;
	var url = "transferCertificate.do?method=rePrintTC&fromUsn="+fromReg+"&tcReDate="+tcReDate+"&toUsn="+toReg+"&classes="+classes+"&academicYear="+year+"&studentName="+studentName+"&studentId="+studentId;
	myRef = window.open(url,"TC","left=20,top=20,width=600,height=500,toolbar=2,resizable=0,scrollbars=1");
}
function getCandidatesForTCPrint(rePrint){
	document.getElementById("reprint").value=rePrint;
	var fromReg=document.getElementById("fromUsn").value;
	var tcReDate=document.getElementById("tcReDate").value;
	var toReg=document.getElementById("toUsn").value;
	var classes=document.getElementById("classes").value;
	var year=document.getElementById("academicYear").value;
	var studentName=document.getElementById("studentName").value;
	document.getElementById("method").value = "getCandidatesForTCRePrint";
	document.transferCertificateForm.submit();
	//var url = "transferCertificate.do?method=getCandidatesForTCPrint&fromUsn="+fromReg+"&tcReDate="+tcReDate+"&toUsn="+toReg+"&classes="+classes+"&academicYear="+year+"&studentName="+studentName;
	//myRef = window.open(url,"TC","left=20,top=20,width=600,height=500,toolbar=2,resizable=0,scrollbars=1");
}
</script>

<html:form action="/transferCertificate" method="post">
	<html:hidden property="reprint" styleId="reprint"/>	
	<html:hidden property="formName" value="transferCertificateForm" />
	<html:hidden property="method" styleId="method" value="initReprint"/>
	<html:hidden property="pageType" value="1"/>
	<table width="98%" border="0">
  		<tr>
    		<td><span class="heading"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admission.transferCertificate"/> &gt;&gt;</span></span></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      				<tr>
        				<td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        				<td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.transferCertificate"/></strong></div></td>
        				<td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      				</tr>
      				<tr>
        				<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        				<td valign="top" class="news">
        					<div align="center">
	              				<table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
	                 				<tr>
	               	    				<td height="20" colspan="6" align="left">
	               	    					<div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
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
						        							<tr>
																<td class="row-odd"><div align="right"><bean:message key="knowledgepro.rollnofrom"/>:</div></td>
																<td class="row-even" align="left"><html:text property="fromUsn" styleId="fromUsn"></html:text> </td>
																<td class="row-odd"><div align="right"><bean:message key="knowledgepro.rollnoto"/>:</div></td>
																<td class="row-even" align="left"><html:text property="toUsn" styleId="toUsn"></html:text> </td>
																<td class="row-odd" align="center" style="display: none;"><div align="right"><bean:message key="admissionForm.education.TCDate.label"/>:</div></td>
		                                                        <td class="row-even" style="display: none;"><html:text
												                 name="transferCertificateForm" property="tcReDate"
												                 styleId="tcReDate" size="16" maxlength="16" /> <script
												                 language="JavaScript">
												                 new tcal( {
												            	// form name
													          'formname' :'transferCertificateForm',
													          // input name
													          'controlname' :'tcReDate'
												              });
										             	     </script></td>
					                						</tr>
					                						<tr>
					                							<td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
					        		   							<td class="row-even" align="left">
					                           						<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="transferCertificateForm" property="year"/>"/>
					                           						<html:select property="year" styleId="academicYear" name="transferCertificateForm" styleClass="combo" onchange="getClasses(this.value)">
			                       	   				 					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			                       	   										<cms:renderAcademicYear></cms:renderAcademicYear>
			                       			   							</html:select>
					        									</td>
					        														                  							<td class="row-odd">
				                 		 							<div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
						               							</td>
						               							<td class="row-even">
						                  							<html:select name="transferCertificateForm" styleId="classes" property="classes">
						                  								<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						                 	    							<c:if test="${classMap != null}">
						       		    										<html:optionsCollection property="classMap" label="value" value="key"/>
						       		    									</c:if>
						                  							</html:select>
					                  							</td>
					        									
					                						
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
	                     							<td width="25%"></td>
	                       							<td width="25%">
	                       								<div align="right">	
	                       									<html:button styleClass="formbutton"  property="" onclick="printTc()"><bean:message key="knowledgepro.admission.transferCertificate.print"/></html:button>
	                       								</div>
	                       							</td>
	                       							<td width="2%"></td>
	                       							<td width="53%" height="45" align="left">
	                   	 								<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
	                       							</td>
	                       							
	                     						</tr>
	                   						</table>
	                   					</td>
	                				</tr>
	                 				<tr>	                 				
	                    				<td height="35" colspan="6" class="body" >
				        					<table width="50%" border="0" align="center" cellpadding="2" cellspacing="0">		                     					
		                     					<div align="center" class="heading">OR</div>
		                     					<!--<tr>
		                       						<td ><img src="images/01.gif" width="5" height="5" /></td>
		                       						<td width="914" background="images/02.gif"></td>
		                       						<td><img src="images/03.gif" width="5" height="5" /></td>
		                     					</tr>
		                     					--><tr>
		                     					<td width="50%" height="25" class="row-odd" align="left">Student Name:</td>
                            				 	<td width="50%" height="25" class="row-even" align="left">
       											<html:text property="studentName" styleId="studentName" name="transferCertificateForm" maxlength="50"/>
                            					</td>                            					
		                     					</tr>		                     		
	                     						<!--<tr>
	                       							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                       							<td background="images/05.gif"></td>
	                       							<td><img src="images/06.gif" /></td>
	                     						</tr>
	                     						--><tr><td height="10%">&nbsp;</td></tr>
	                     						<tr>
													<td height="15%">
	                       								<div align="right">	
	                       									<html:button styleClass="formbutton"  property="" onclick="getCandidatesForTCPrint()">Search</html:button>
	                       								</div>
	                       							</td>
												</tr>
												<tr><td width="25%">&nbsp;</td></tr>												
	                   						</table>
	                   					</td>	
	                   					
	                 				</tr>
	                 				
	                 		<nested:notEmpty name="transferCertificateForm" property="allStudentsForTcPrint">
	                 		<tr>
		                       			<td ><img src="images/01.gif" width="5" height="5" /></td>
		                       			<td width="914" background="images/02.gif"></td>
		                       			<td><img src="images/03.gif" width="5" height="5" /></td>
		                     	</tr>
	                 		<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								
								<tr class="row-odd" height="25">
								<td>SI.No </td>
								<td>Student Name </td>
								<td>Roll No</td>
								<td style="display: none;">Student Id</td>
								<td>Course Name</td>
								<td>Print</td>
								</tr>
								
								<nested:iterate id="bdForm" name="transferCertificateForm" property="allStudentsForTcPrint" indexId="count">
									<tr class="row-even"> 
										<td><c:out value="${count+1}"></c:out></td>
										<td><bean:write name="bdForm" property="studentName"/>  </td>
										<td><bean:write name="bdForm" property="rollNo"/></td>
										<td style="display: none;"><bean:write name="bdForm" property="studentId"/></td>
										<td><bean:write name="bdForm" property="course"/></td>
										<td align="center">
										<img src="images/print-icon.png"
													width="16" height="18"
													onclick="printTc(<bean:write name="bdForm" property="studentId"/>)" />
										 </td>
									</tr>
								</nested:iterate>
								
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr><tr>
	                       				<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                       				<td background="images/05.gif"></td>
	                       				<td><img src="images/06.gif" /></td>
	                     		</tr>
	                     		<tr><td>&nbsp;</td></tr>
	                     		</nested:notEmpty>
	                 				
	              				</table>
            				</div>
            			</td>
        				<td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      				</tr>
      				<tr>
        				<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        				<td width="100%" background="images/TcenterD.gif"></td>
        				<td><img src="images/Tright_02.gif" width="9" height="29"></td>
      				</tr>
    			</table>
    		</td>
  		</tr>
	</table>
</html:form>