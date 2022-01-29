<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript">
function editOrganization(id) {
	document.location.href = "BiometricLogSetup.do?method=editBiometricLogSetup&id="
			+ id;
}
</script>

<html:form action="/BiometricLogSetup" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="addBiometricLog" />
	<html:hidden property="formName" value="BiometricLogSetupForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	    <td height="15"><span class="heading"><span class="Bredcrumbs"></span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Biometric Log Setup</strong></td>
	
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	     <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td  class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
		  <tr>
	       <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="heading">Log Field</td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2">
					<tr >
						<td width="50%" height="25" class="row-odd" align="center"  > Field Name	</td>
						<td width="50%" height="25" class="row-odd" align="center"  >Field Order </td>
	                </tr>
	                

	                <tr >
						<td  height="25" class="row-even" align="center"  > Terminal ID	</td>
						<td  height="25" class="row-even" align="center"  > 
						
						<html:hidden property="dummyTerminalId" styleId="terminalId" name="BiometricLogSetupForm"/>
						
						<html:select  property="terminalId" styleId="terminalId1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						
						<script type="text/javascript">
						  var tey = document.getElementById("terminalId").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("terminalId1").value = tey;
							}
						</script>
 						</td>
	                </tr>
	                <tr >
						<td  height="25" class="row-white" align="center"  > Finger Print ID	</td>
						<td  height="25" class="row-white" align="center"  >  
						
						<html:hidden property="dummyFingerPrintId" styleId="fingerPrintId" name="BiometricLogSetupForm"/>
						<html:select  property="fingerPrintId" styleId="fingerPrintId1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						<script type="text/javascript">
						  var tey = document.getElementById("fingerPrintId").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("fingerPrintId1").value = tey;
							}
						</script>	</td>
	                </tr>
	                
	                <tr >
						<td  height="25" class="row-even" align="center"  > Employee Code	</td>
						<td  height="25" class="row-even" align="center"  > 
						
						<html:hidden property="dummyEmployeeCode" styleId="ec" name="BiometricLogSetupForm"/> 
						<html:select  property="employeeCode" styleId="ec1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						<script type="text/javascript">
						  var tey = document.getElementById("ec").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("ec1").value = tey;
							}
						</script>	</td>
	                </tr>
	                <tr >
						<td  height="25" class="row-white" align="center"  > Function key	</td>
						<td  height="25" class="row-white" align="center"  > 
						
						<html:hidden property="dummyFunctionkey" styleId="fk" name="BiometricLogSetupForm"/> 
						 <html:select  property="functionkey" styleId="fk1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select>
						<script type="text/javascript">
						  var tey = document.getElementById("fk").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("fk1").value = tey;
							}
						</script> 	</td>
	                </tr>
	                
	                <tr >
						<td  height="25" class="row-even" align="center"  > Employee Name	</td>
						<td  height="25" class="row-even" align="center"  > 
						
						<html:hidden property="dummyEmployeeName" styleId="en" name="BiometricLogSetupForm"/> 
						 <html:select  property="employeeName" styleId="en1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select>
						<script type="text/javascript">
						  var tey = document.getElementById("en").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("en1").value = tey;
							}
						</script> 	</td>
	                </tr>
	                <tr >
						<td  height="25" class="row-white" align="center"  > DateTime</td>
						<td  height="25" class="row-white" align="center"  >
						
						<html:hidden property="dummyDatetime" styleId="dt" name="BiometricLogSetupForm"/>
						  <html:select  property="datetime" styleId="dt1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						<script type="text/javascript">
						  var tey = document.getElementById("dt").value;
							if (tey != null && tey.length != 0) {
								document.getElementById("dt1").value = tey;
							}
						</script>	</td>
	                </tr>
	                <tr >
						<td  height="25" class="row-even" align="center"  > Status	</td>
						<td  height="25" class="row-even" align="center"  > 
						
						<html:hidden property="dummyStatus" styleId="stu" name="BiometricLogSetupForm"/>
						<html:select  property="status" styleId="stu1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						<script type="text/javascript">
						  var tey = document.getElementById("stu").value;
						 
							if (tey != null && tey.length != 0) {
								document.getElementById("stu1").value = tey;
							}
						</script>	</td>
	                </tr>
	                 <tr >
						<td  height="25" class="row-even" align="center"  > Test Code  </td>
						<td  height="25" class="row-even" align="center"  > 
						
						<html:hidden property="dummyTestCode" styleId="co" name="BiometricLogSetupForm"/>
						<html:select  property="testCode" styleId="co1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="">Select</option>
							<cms:experienceMonth expLimit="8" year="true"></cms:experienceMonth>
						</html:select> 
						<script type="text/javascript">
						  var tey = document.getElementById("co").value;
						 
							if (tey != null && tey.length != 0) {
								document.getElementById("co1").value = tey;
							}
						</script>	</td>
	                </tr>
	               
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	       <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"> </td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2">
					 <tr >
						<td  width="50%" height="25" class="row-even" align="center"  > Delimited With	</td>
						<td  width="50%" height="25" class="row-even" align="center"  >
						<html:hidden property="dummyDelimitedWith" styleId="dw" name="BiometricLogSetupForm"/>
						 <html:select  property="delimitedWith" styleId="dw1" name="BiometricLogSetupForm" styleClass="combo">
							<option value=",">Comma [,]</option>
							<option value=";"> Semi Colan[;]</option>
						</html:select>
						<script type="text/javascript">
						  var tey = document.getElementById("dw").value;
						  
							if (tey != null && tey.length != 0) {
								document.getElementById("dw1").value = tey;
							}
						</script> 	</td>
	                </tr>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	       <tr>
	       <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"> </td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2">
					 
	                
	                <tr >
						<td width="50%" height="25" class="row-even" align="center"  > Date Format	</td>
						<td  width="50%" height="25" class="row-even" align="center"  >
						
						<html:hidden property="dummyDateFormat" styleId="df" name="BiometricLogSetupForm"/>
						<html:select  property="dateFormat" styleId="df1" name="BiometricLogSetupForm" styleClass="combo">
							<option value="dd/MMM/yyyy">dd/MMM/yyyy</option>
							<option value="dd-mm-yyyy">dd-mm-yyyy</option>
							<option value=" mm-dd-yyyy">mm-dd-yyyy</option>
							<option value="yyyy/mm/dd">yyyy-mm-dd</option>
							<option value="yyyy-mm-dd">dd/mm/yyyy</option>
							<option value="mm/dd/yyyy">mm/dd/yyyy</option>
							<option value="yyyy/mm/dd">yyyy/mm/dd</option>
						</html:select>
						<script type="text/javascript">
						  var tey = document.getElementById("df").value;
						 
							if (tey != null && tey.length != 0) {
								document.getElementById("df1").value = tey;
							}
						</script> </td>
	                </tr>
	               
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"> </td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	              <tr>
	              <td width="50%" height="25" class="row-even" align="center"><FONT color="red"> <span class='MandatoryMark'>*</span></FONT></div>Text file path </td>
	                <td width="50%" height="25" class="row-even" align="center"> <html:textarea property="textFilePath" name="BiometricLogSetupForm" />		</td>
	                </tr>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
 			<tr>
		        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
		        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
		          <tr>
		
		            <td width="45%" height="35"><div align="right">
		              <html:submit styleClass="formbutton" value="Submit"/>
		            </div></td>
		            <td width="2%"></td>
		            <td width="53%"><html:button property="" styleClass="formbutton"  value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
		          </tr>
		        </table> </td>
		        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		
		      </tr>
		       <tr>
	        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">  
			<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="row-odd">Terminal ID</td>
									<td align="center" class="row-odd">Finger Print ID </td>
									<td align="center" class="row-odd">Employee Code</td>
									<td align="center" class="row-odd">Employee Name </td>
									<td align="center" class="row-odd">DateTime</td>
									<td align="center" class="row-odd">Function Key</td>
									<td align="center" class="row-odd">Status</td>
									<td align="center" class="row-odd">Test Code</td>
									<td align="center" class="row-odd">Delimited With </td>
									<td align="center" class="row-odd">Date Format</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.edit" /></div>
									</td>
								</tr>
								<logic:notEmpty name="BiometricLogSetupForm"
									property="list">
									<nested:iterate id="org" name="BiometricLogSetupForm"
										property="list" indexId="count">
												<tr>
													<td width="5%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="20%" height="25" class="row-even"><nested:write
														name="org" property="terminalId" /></td>
													<td align="center" width="21%" class="row-even"><nested:write
														name="org" property="fingerPrintId" />
													</td>
													<td align="center" width="25%" height="25" class="row-even">
													<div align="center"></div>
													<nested:write name="org" property="employeeCode" />
													</td>
													<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="employeeName" />
													</td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="datetime" /></td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="functionkey" /></td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="status" /></td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="testCode" /></td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="delimitedWith" /></td>
													<td align="center" width="22%" class="row-even"><nested:write
													name="org" property="dateFormat" /></td>
													<td width="7%" height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="editOrganization('<bean:write name="org" property="id"/>')"></div>
													</td>
												</tr>
									</nested:iterate>
								</logic:notEmpty>
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
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	
	  </tr>
	</table>
</html:form>
