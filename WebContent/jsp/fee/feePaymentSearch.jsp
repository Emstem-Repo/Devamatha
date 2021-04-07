<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
function getClasses(year) {
	getClassesByYearNew("classMap", year, "class", updateClasses);
	//resetOption("subject");
}
function updateClasses(req) {
	updateOptionsFromMap(req, "class", "- Select -");
}
function resetData() {
	document.getElementById("applicationId").value = "";
	document.getElementById("registrationNo").value = "";	
	document.getElementById("rollNumber").value = "";
	document.getElementById("year").value = "";
	document.getElementById("class").value = "";
	document.getElementById("studentName").value = "";
	resetErrMsgs();
}
function searchFeePayment() {
	document.getElementById("method").value = "initNewFeePaymentSecond";
	document.feePaymentForm.submit();
}
function validNumber(field) {
	if(isNaN(field.value)) {
		field.value="";
	}
}
function feePaySecodPage(applnNo){
	document.getElementById("applicationId").value = applnNo;	
	document.getElementById("method").value = "initNewFeePaymentSecond";
	document.feePaymentForm.submit();
}

</script>
<html:form action="/FeePayment">
<html:hidden property="method" styleId="method" value="initNewFeePaymentSecond"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="admittedThroughName" value=""/>
<html:hidden property="financialYearId" styleId="financialYearId"/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feepayment"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feepayment"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="263"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
                   <td height="30" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="100%" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="1">
                           <tr>
							<td width="16%" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
                             <td width="10%" class="row-even" align="left">
                             <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="feePaymentForm" property="year"/>"/>
                             <html:select property="year" styleId="year" styleClass="combo" onchange="getClasses(this.value)">
		                     	 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
		                     	 <cms:renderYear></cms:renderYear>
		                     </html:select>  
                               </td>
                               <td width="30%" class="row-odd"><div align="right"><span class="Mandatory">*</span> Select Amount:</div></td>
		                     	<td width="10%" class="row-even" align="left" >
		                     <html:radio property="isCurrent" value="no" styleId="prevId" >Previous Year Amount &nbsp;&nbsp; </html:radio>
		                     <html:radio property="isCurrent" value="yes" styleId="currId">Current Year Amount &nbsp;&nbsp; </html:radio>
		                     </td>
                           </tr>
                           <tr>	<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="feePaymentForm" property="classId"/>' />
									<html:select property="classId" styleClass="comboLarge" styleId="class" onchange="setClassName">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${feePaymentForm.classId != null && feePaymentForm.classId != ''}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
							<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Financial Year:</div></td>
                            <td width="22%" height="25" class="row-even">
                             	<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="feePaymentForm" property="year"/>"/>
                             	<html:select property="financialYearId" styleClass="combo" styleId="finYear" >
								<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
									<cms:renderFinancialForFeePaymenYear></cms:renderFinancialForFeePaymenYear>
								</html:select>  
                             </td>
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
                 </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="100%" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5" background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="0" cellpadding="1">
                           <tr>
 							<td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.appno"/>:</div></td>
                             <td width="15%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="applicationId" styleId="applicationId" name="feePaymentForm" onkeypress="return isNumberKey(event)" maxlength="9" onblur="validNumber(this)"/>
                             </span></td>							
							<td width="5%" height="25" class="row-even" align="center">OR</td>
                             <td width="15%" height="25" class="row-odd"><div align="right" id="regLabel"><bean:message key="knowledgepro.fee.regestrationno"/>:</div></td>
                             <td width="15%" height="25" class="row-even" align="left"><span class="star">
                               <html:text property="registrationNo" styleId="registrationNo" name="feePaymentForm" maxlength="10"/>
                             </span></td>
							<td width="5%" height="25" class="row-even" align="center">OR</td>
 							<td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.rollno"/>:</div></td>
                             <td width="15%" height="25" class="row-even" align="left"><span class="star">
                               <html:text property="rollNumber" styleId="rollNumber" name="feePaymentForm" maxlength="10"/>
                             </span></td>
                           </tr>
                           <tr>
                           <td width="5%" height="25" class="row-even" align="center">OR</td>
                           <td width="5%" height="25" class="row-odd" align="right">Student Name:</td>
                             <td width="15%" height="25" class="row-even" align="left">
       							<html:text property="studentName" styleId="studentName" name="feePaymentForm" maxlength="50"/>
                             </td>
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
                </tr>
                 <tr>
                   <td height="35" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="37" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
		                  <tr>
		                    <td height="27"><div align="center">
		                      <html:submit styleClass="formbutton" value="Search" ></html:submit> &nbsp;&nbsp;&nbsp;
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
		                    </div></td>
		                    </tr>		                  
		                    <tr><td style="height: 10px" align="center" colspan="1"></td></tr>		                    
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                <logic:notEmpty name="feePaymentForm" property="feePayStudentList">
                 <tr>
                   <td height="30" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="100%" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr>
	                 <td width="15%" height="25" align="center" class="row-odd">Sl No.</td>
	                <td width="15%" height="25" align="center" class="row-odd">Application No.</td>
	                <td width="25%" height="25" align="center" class="row-odd">Student Name</td>
	                <td width="15%" height="25" align="center" class="row-odd">Course</td>
	                <td width="15%" height="25" align="center" class="row-odd">Register No.</td>
	                <td width="15%" height="25" align="center" class="row-odd">Roll No.</td>
	                <td width="15%" height="25" align="center" class="row-odd">&nbsp;</td>
	                </tr>
					<logic:iterate id="feePayStudentList" name="feePaymentForm" property="feePayStudentList"
							indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
							<td width="8%" height="25" align="center">
							<div align="center"><c:out value="${count + 1}" /></div>
							</td>
							<td height="25" align="center"><bean:write
								name="feePayStudentList" property="applnNo" /></td>
	
							<td align="center"><bean:write
								name="feePayStudentList" property="name" /></td>
								
							<td align="center"><bean:write
								name="feePayStudentList" property="course" /></td>
							<td width="15%" align="center"><bean:write
								name="feePayStudentList" property="regNo" /></td>										
								
							<td width="15%" align="center"><bean:write
								name="feePayStudentList" property="rollNo" /></td>		
								
							<td width="10%" height="25" align="center">
							<div align="center"><img src="images/edit_icon.gif"
								width="16" height="18" style="cursor:pointer"
								onclick="feePaySecodPage('<bean:write name="feePayStudentList" property="applnNo"/>')">
							</div>
							</td>
							</tr>
						</logic:iterate>     
						</table></td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table></td>
                 </tr>           
                </logic:notEmpty>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
var print = "<c:out value='${feePaymentForm.printChallan}'/>";
var error = "<c:out value='${feePaymentForm.error}'/>";
//if(print.length != 0 && print == "true") {
 	//document.getElementById("applicationId").value = "";
	//var url ="FeePayment.do?method=dispalyChallanFromFeePayment";
	//myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	
//} else {
	//var year = document.getElementById("tempyear").value;
	//if(year.length != 0) {
	 	//document.getElementById("year").value=year;
	//} else if(year.length ==0 && error == "true"){
		//document.getElementById("year").selectedIndex = 0;
	//}	
//}
var finYear = document.getElementById("financialYearId").value;
document.getElementById("finYear").value = finYear;
</script>