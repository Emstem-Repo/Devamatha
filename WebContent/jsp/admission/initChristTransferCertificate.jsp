<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}
</script>

<html:form action="/transferCertificate" method="post">
	<html:hidden property="formName" value="transferCertificateForm" />
	<html:hidden property="method" styleId="method" value="printChristTransferCertificate"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="toCollege" styleId="toCollege"/>
	<html:hidden property="reprint" styleId="reprint"/>
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
				                       							<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
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
					                  							<logic:equal value="Christ" property="toCollege" name="transferCertificateForm">
					                  							<td class="row-odd">
					                  								<div id="classsdiv" align="right"><bean:message key="knowledgepro.admission.transferCerficate.duplicate"/>:</div>
					                  							</td>
					                  							<td class="row-even">
					                  								<html:radio property="duplicate" styleId="duplicateYes" value="Yes">Yes</html:radio>
					                  								&nbsp;
					                  								<html:radio property="duplicate" styleId="duplicateNo" value="No">No</html:radio>
					                  							</td>
					                  							</logic:equal>
					                  							
					                						</tr>
					                						<logic:equal value="Cjc" property="toCollege" name="transferCertificateForm">
					                  						<tr>
					                  							<td class="row-odd">
					                  								<div id="classsdiv" align="right"><bean:message key="knowledgepro.admission.tc.for"/>:</div>
					                  							</td>
					                  							<td class="row-even">
					                  								<html:select name="transferCertificateForm" styleId="tcFor" property="tcFor">
					                  									<html:option value="CJC">Christ Junior College</html:option>
					                  									<html:option value="CEC">Christ Evening College</html:option>
					                  								</html:select>
					                  							</td>
					                  							<td class="row-odd">
					                  								<div id="classsdiv" align="right"><bean:message key="knowledgepro.admission.tc.type"/>:</div>
					                  							</td>
					                  							<td class="row-even">
					                  								<html:select name="transferCertificateForm" styleId="tcType" property="tcType">
					                  									<html:option value="Normal">Normal</html:option>
					                  									<html:option value="Discontinued">Discontinued</html:option>
					                  									<html:option value="Duplicate">Duplicate</html:option>
					                  								</html:select>
					                  							</td>
					                  						</tr>
					                  						<tr>
					                  							<td class="row-odd">
					                  								<div id="classsdiv" align="right"><bean:message key="knowledgepro.admission.tc.include.fail"/>:</div>
					                  							</td>
					                  							<td class="row-even">
					                  								<html:radio property="includeFail" styleId="includeFailYes" value="Yes">Yes</html:radio>
					                  								&nbsp;
					                  								<html:radio property="includeFail" styleId="includeFailNo" value="No">No</html:radio>
					                  							</td>
					                  							<td class="row-even" colspan="2">
					                  								&nbsp;
					                  							</td>
					                  						</tr>	
					                  						</logic:equal>
					                						<tr>
																<td class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.regno.from"/>:</div></td>
																<td class="row-even" align="left"><html:text property="fromUsn" styleId="fromUsn"></html:text> </td>
																<td class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.regno.to"/>:</div></td>
																<td class="row-even" align="left"><html:text property="toUsn" styleId="toUsn"></html:text> </td>
																<logic:equal value="Christ" property="toCollege" name="transferCertificateForm">
																<td class="row-odd">
					                  								&nbsp;
					                  							</td>
					                  							<td class="row-even">
					                  								&nbsp;
					                  							</td>
					                  							</logic:equal>					                						
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
	                       							<td width="45%">
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
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>