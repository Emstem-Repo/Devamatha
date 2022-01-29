<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function resetData() {
		resetFieldAndErrMsgs();
	}
	function saveDetails(){
		document.getElementById("method").value="saveStudent";
		document.documentVerificationEntryForm.submit();
	}
	function clearData(){
		document.location.href="DocumentsEntry.do?method=initPage";
	}
</script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/DocumentsEntry" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="getDocumentsForStudent"/>
<html:hidden property="formName" value="documentVerificationEntryForm"/>
<html:hidden property="pageType" value="2"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; Documents Entry&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Documents Entry</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
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
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                           <tr>
                             <td height="25" class="row-odd" width="45%"><div id ="reFrom" align="right"><span class="Mandatory">*</span>Register Number: </div></td>
                             <td height="25" class="row-even" align="left" width="50%">
                             <span class="star">
                               <html:text property="registerNo" styleId="registerNo" name="documentVerificationEntryForm" />
                             </span></td>
                           </tr>
                           <tr height="10px">
                           		<td></td>
                           </tr>
                           <tr>
                           		<td colspan="2" align="center">
                           			<html:submit value="Search" styleClass="formbutton"></html:submit>
                           		</td>
                           </tr>
                           <logic:notEmpty property="docList" name="documentVerificationEntryForm">
                           <tr height="30px">
                           		<td align="center" class="row-odd" colspan="2"><span class="heading">&nbsp;<bean:message
								key="knowledgepro.admission.documents" /></span></td>
                           </tr>
                           <tr>
                           		<td colspan="2">
                           		
                           			<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td>
												<table width="100%">
													<tr>
														<td height="25" class="row-odd" width="20%">
														<div align="center"><bean:message key="admissionForm.edit.hardcopy.label"/></div>
														</td>
														<td height="25" class="row-odd" width="20%">
														<div align="center"><bean:message key="admissionForm.edit.na.label"/> </div>
														</td>
														<td height="25" class="row-odd" width="55%" align="center"><bean:message
															key="knowledgepro.admission.documents" /></td>
													</tr>
												</table>
											</td>
										
										</tr>
										
										<c:set var="temp" value="0" />
										<% String sty=""; %>
										<nested:iterate name="documentVerificationEntryForm"
											property="docList" indexId="count1" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
													<%sty="row-even"; %>
													<c:set var="temp" value="1" />
												</c:when>	
												<c:otherwise>
													<%sty="row-white"; %>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>	
													<tr>
													  <td>
													  	<table width="100%">
													  		<tr>
													  			<td width="20%" height="25" class='<%=sty %>'>
																<div align="center">
																 <input type="hidden" id="selected_<c:out value='${count1}'/>" name="selected_<c:out value='${count1}'/>" value="<nested:write name='docList' property='temphardSubmitted'/>"/>
																<input type="checkbox" id="selected1_<c:out value='${count1}'/>" name="docList[<c:out value='${count1}'/>].hardSubmitted" id="select_<c:out value='${count1}'/>" onclick="unselectApplicable('<c:out value="${count1}"/>')"/>
																<script type="text/javascript">
																	var selectedId = document.getElementById("selected_<c:out value='${count1}'/>").value;
																	if(selectedId == "true") {
																			document.getElementById("selected1_<c:out value='${count1}'/>").checked = true;
																	}		
																</script>
																</div>
																</td>
																	<td width="20%" height="25" class='<%=sty %>'>
																<div align="center">
																<%String met="unselectHardSubmit("+count1+")"; %>
																<c:if test="${docList.needToProduceSemWiseMC=='true'}">
																	<%met=met+",disableChekcBox("+count1+")"; %>
																</c:if>
																 <input type="hidden" id="selected_not_applicable_<c:out value='${count1}'/>" name="selected_<c:out value='${count1}'/>" value="<nested:write name='docList' property='tempNotApplicable'/>"/>
																<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
																<input type="checkbox" id="selected1_not_applicable_<c:out value='${count1}'/>" name="docList[<c:out value='${count1}'/>].notApplicable" onclick="<%=met %>"/>
																<script type="text/javascript">
																	var selected_not_applicable_Id = document.getElementById("selected_not_applicable_<c:out value='${count1}'/>").value;
																	if(selected_not_applicable_Id == "true") {
																			document.getElementById("selected1_not_applicable_<c:out value='${count1}'/>").checked = true;
																	}		
																</script>
																</div>
																</td>
																<td width="55%" height="25" class='<%=sty %>' align="center"><nested:write
																	property="printName" /></td>
													  		</tr>
													  		<c:if test="${docList.needToProduceSemWiseMC=='true'}">
													  		<%
													  			String semId="semisterNo_"+count1;
													  			String semMethod="checkTheFields("+count1+")";
													  			String semTypeID="semType_"+count1;
													  		%>
														  		 <tr height="25" class="row-odd">
														  		    <td width="20%">
														  		    	Sem No:<nested:select property="semisterNo" styleClass="combo"  styleId='<%=semId %>' onchange='<%=semMethod %>'>
																			<option value=""><bean:message key="knowledgepro.admin.select"/></option>
																			<html:option value="1">1</html:option>
																			<html:option value="2">2</html:option>
																			<html:option value="3">3</html:option>
																			<html:option value="4">4</html:option>
																			<html:option value="5">5</html:option>
																			<html:option value="6">6</html:option>
																			<html:option value="7">7</html:option>
																			<html:option value="8">8</html:option>
																			<html:option value="9">9</html:option>
																			<html:option value="10">10</html:option>
																			<html:option value="11">11</html:option>
																			<html:option value="12">12</html:option>
																		</nested:select>
														  		    </td>
														  		    <td width="20%"> 
														  		    	Type:<nested:select property="semType" styleClass="combo" styleId='<%=semTypeID %>' >
																			<html:option value="sem">sem</html:option>
																			<html:option value="year">year</html:option>
																		</nested:select>
														  		    </td>
														  		    <td width="55%">
														  		    	<table>
														  		    	<tr>
														  		    		<logic:notEmpty name="docList" property="docDetailsList"> 
														  		    		<nested:iterate id="doc" property="docDetailsList" indexId="count2">
														  		    			<td>
														  		    				<div align="center">
														  		    					<%
													  										String checkId="check_"+count1+"_"+count2;
														  		    						String checkMethod="checkBoxField("+count1+","+count2+")";
													  										%>
														  		    					<bean:write name="doc" property="semNo"/> 
														  		    					
														  		    					<nested:checkbox property="checked" styleId='<%=checkId %>' onclick='<%=checkMethod %>'></nested:checkbox>
														  		    					
																						<script type="text/javascript">
																							var check = "<bean:write name='doc' property='checked'/>";
																							if(check == "yes") {
																			                        document.getElementById("check_<c:out value='${count1}'/>_<c:out value='${count2}'/>").checked = true;
																							}	
																							if(check == "no") {
																		                        document.getElementById("check_<c:out value='${count1}'/>_<c:out value='${count2}'/>").checked = false;
																						}
														  		    					</script>
														  		    				
														  		    				
																					</div>
														  		    			</td>
														  		    		</nested:iterate>
														  		   			</logic:notEmpty>
														  		    	</tr>
														  		    </table>
														  		    
														  		    </td>
														  		 </tr>   
														  		</c:if>
													  	</table>
													  </td>
													</tr>
										</nested:iterate>
									</table>
                           		
                           		</td>
                           </tr>
                           <tr height="30px">
                           <td>&nbsp;</td>
                           </tr>
                           <tr>
							  <td align="center" colspan="2">
							  	 <input type="submit" value="Submit" class="formbutton" onclick="saveDetails()"/> &nbsp;&nbsp;
								 <input type="button" value="Cancel" class="formbutton" onclick="clearData()"/>
							  </td>
							</tr>							
						</logic:notEmpty>
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



function unselectApplicable(count) {
	document.getElementById("selected1_not_applicable_"+count).checked = false;
}	

function unselectHardSubmit(count){
	document.getElementById("selected1_"+count).checked = false;
}	
</script>