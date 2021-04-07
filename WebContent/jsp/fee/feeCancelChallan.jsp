	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
		<script type="text/javascript">
		function cancelAction() {
			document.location.href = "FeePaid.do?method=initChallanCancel";
		}
		function markAsPaid(){
			if(validateCheckBox()) {
			document.getElementById("method").value = "markAsCancel";
			document.feePaidForm.submit();
			}
		}
		function validateCheckBox() {
			var inputs = document.getElementsByTagName("input");
		    var inputObj;
		    var checkBoxselectedCount = 0;
		    for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
			   		if(inputObj.checked){
			   			checkBoxselectedCount++;
				   	}
				}
		    }
	
		    if(checkBoxselectedCount == 0) {
		        document.getElementById("err").innerHTML = "Please select at least one record.";
		        document.getElementById("errorMessage").innerHTML = "";
		    	return false;
		    }    
		    else { 
		        return true;
		    }    
		            
		}
		function imposeMaxLength(evt, Object) {
			var keynum = (evt.which) ? evt.which : event.keyCode;
			if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
				return true;
			}
			var MaxLen = 100;
			return (Object.value.length < MaxLen);
		} 
	
		function selectAll(obj) {
			value = obj.checked;
			var inputs = document.getElementsByTagName("input");
		    var inputObj;
		    var checkBoxselectedCount = 0;
		    for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
			   		inputObj.checked = value;
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
			   		}	
				}
		    }
		    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
		    	document.getElementById("checkAll").checked = false;
		    } else {
		    	document.getElementById("checkAll").checked = true;
		    }        
		}
	</script>
	<html:form action="/FeePaid">	
			<html:hidden property="method" styleId="method" value="markAsCancel" />
			<html:hidden property="formName" value="feePaidForm"/>
			<!-- <html:hidden property="pageType" value="2" /> -->
		<table width="100%" border="0">
			<tr>
				<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/>
				<span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.fee.canlelchallan"/>
				 &gt;&gt;</span></span></td>
			</tr>
			<tr>
				<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/Tcenter.gif" class="body"><strong
							class="boxheader"> <bean:message key="knowledgepro.fee.canlelchallan"/> Entry</strong></td>
	
						<td width="10"><img src="images/Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>
					<tr>
						<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
	
						<td valign="top" class="news">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								 <tr>
	                   <td height="35" colspan="6" >
	                    <logic:notEmpty name="feePaidForm" property="feePaymentList">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">	                  
	                     <tr>	            
	                       <td valign="top">	                       
		                   <table width="100%" cellspacing="1" cellpadding="2">
		                       <tr >
		                           <td width="5%" height="25" class="row-odd"><div align="center"><input type="checkbox" id="checkAll" onclick="selectAll(this)"> Cancel</div></td>
			                       <td width="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
			                       <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.appregno"/></div></td>
			                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.billno"/></div></td>
			                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.totalamount"/></div></td>
			                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.feepays.challandate"/></div></td>
			                      <td width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.feepays.Reason"/></div></td> 
		                       </tr>
		                       <c:set var="temp" value="0"/>
		                       <nested:iterate name="feePaidForm" property="feePaymentList" type="com.kp.cms.to.fee.FeePaymentTO" indexId="count">
			                       <c:choose>
		                           	 <c:when test="${temp == 0}">
		                           		<tr><nested:hidden property="id"></nested:hidden>
										   <td height="25" class="row-even" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="isChallanCanceled" onclick="unCheckSelectAll()"/></div></td>
					                       <td class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="registrationNo"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="billNo"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="totalFeePaid"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="challenPrintedDate"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:textarea property="cancelReason" cols="18" rows="2" styleId="cancelReason" onkeypress="return imposeMaxLength(event,this)"></nested:textarea></div></td>
		                               </tr>
		                      		   <c:set var="temp" value="1"/>
		                   		 	</c:when>
		                    	    <c:otherwise>
				                    <tr><nested:hidden property="id"></nested:hidden> 
				             			   <td height="25" class="row-white" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="isChallanCanceled" onclick="unCheckSelectAll()"/></div></td>
				             			   <td class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="registrationNo"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="billNo"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="totalFeePaid"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="challenPrintedDate"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:textarea property="cancelReason" cols="18" rows="2" styleId="cancelReason" onkeypress="return imposeMaxLength(event,this)"></nested:textarea></div></td>
		                           </tr>
		                           <c:set var="temp" value="0"/>
		                    	  </c:otherwise>
		                        </c:choose>
		                      </nested:iterate>
		                      <tr>
							   <td height="2" class="row-even" ><div align="center"></div></td>
		                       <td class="row-even"><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                     </tr>
		                     <tr>
							   <td height="25" colspan="6" class="row-white" ><div align="center">
							   <html:button property="" styleClass="formbutton" value="Submit"  onclick="markAsPaid()"></html:button>
							   </div></td>
							   <td width="52%" align="left"><html:button property=""
								styleClass="formbutton" value="Cancel" onclick="cancelAction()">
								
								</html:button> 
							</td>
		                        </tr>
		                   </table>
		                  
	                      </td>
 					      </tr>
	                                        </table>
	                    </logic:notEmpty>
	                   </td>
	                 </tr>
							</table>
								</td>
								<td width="5" height="29" background="images/right.gif"></td>
							</tr>
	
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
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
						<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/TcenterD.gif"></td>
						<td><img src="images/Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
				</td>
	
			</tr>
		</table>
	</html:form>
						