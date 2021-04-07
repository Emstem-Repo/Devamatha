<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function cancel(){
	document.location.href = "valuationRemuPayment.do?method=initValuationRemuPayment";
	
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
	}
function selectAll(obj) {
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
function getAmountDetails(id){
	var url = "valuationRemuPayment.do?method=displayValuatorDetails&id="+id;
		myRef = window
			.open(url, "Challan",
				"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}
</script>

<html:form action="/valuationRemuPayment" focus="name">
<html:hidden property="formName" value="examValuationRemuPaymentForm" />
<html:hidden property="method" styleId="method" value="updatePaidAndModeOfPayment"/>
<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading">Exam<span class="Bredcrumbs">&gt;&gt;
			Valuation Remuneration Payment &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Valuation Remuneration Payment</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<c:choose>
										<c:when test="${paidStatus != null && paidStatus == 'paid'}">
											<td  width="2%"  height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="6%"  height="5%" class="row-odd" ><div align="center"> Voucher No</div></td>
											<td width="8%"  height="5%" class="row-odd" ><div align="center">Date</div></td>
		                    				<td width="15%"  height="5%" class="row-odd" ><div align="center">Faculty Name</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Department</div></td>
		                    				<td width="5%" class="row-odd"><div align="center">Total Amount</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">PAN No</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Account No</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Bank Name</div></td>
		                    				<td width="8%" class="row-odd"><div align="center">IFSC Code</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Mode Of Payment</div></td>
										</c:when>
										<c:otherwise>
											<td  width="2%"  height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="2%"  height="5%" class="row-odd" ><div align="center">
												Paid<input type="checkbox" id="checkAll" onclick="selectAll(this)"/></div>
											</td>
											<td width="6%"  height="5%" class="row-odd" ><div align="center"> Voucher No</div></td>
											<td width="10%"  height="5%" class="row-odd" ><div align="center">Date</div></td>
		                    				<td width="15%"  height="5%" class="row-odd" ><div align="center">Faculty Name</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Department</div></td>
		                    				<td width="5%" class="row-odd"><div align="center">Total Amount</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">PAN No</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Account No</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Bank Name</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">IFSC Code</div></td>
		                    				<td width="10%" class="row-odd"><div align="center">Mode Of Payment</div></td>
										</c:otherwise>
									</c:choose>
                 				</tr>
                 				<logic:notEmpty name="examValuationRemuPaymentForm" property="list">
								<nested:iterate id="CME" name="examValuationRemuPaymentForm" property="list" indexId="count" type="com.kp.cms.to.exam.ExamValuationRemuPaymentTo">
									<% 
										String modeOfPayment = "modeOfPayment_"+count;
										String method = "getAmountDetails("+CME.getId()+")";
									%>
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   						
	                  				<tr>
		                  				<c:choose>
											<c:when test="${paidStatus != null && paidStatus == 'paid'}">
												<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
			                  					<td  height="25" class="row-even" align="center"><a href="#" onclick="<%=method%>"><nested:write name="CME" property="vocherNo"/></a></td>
			                  					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="date"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="name"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="department"/></td>
			                  					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="totalAmount"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="panNo"/></td>
			                   					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="accountNo"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="bankName"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="ifscCode"/></td>
			                  					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="modeOfPayment"/>  </td> 						
											</c:when>
											<c:otherwise>
												<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
			                  					<td  height="25" class="row-even" align="center">
			                  					<nested:checkbox property="checked" styleId="checkId" onclick="unCheckSelectAll()"> </nested:checkbox>
			                  					</td>
			                  					<td  height="25" class="row-even" align="center"><a href="#" onclick="<%=method%>"><nested:write name="CME" property="vocherNo"/></a></td>
			                  					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="date"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="name"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="department"/></td>
			                  					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="totalAmount"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="panNo"/></td>
			                   					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="accountNo"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="bankName"/></td>
			                  					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="ifscCode"/></td>
			                  					<td  height="25" class="row-even" align="center">
			                  						<nested:select property="modeOfPayment" styleId="<%=modeOfPayment%>"  styleClass="comboMediumSmall" >
			                   						<nested:notEmpty property="modeOfPaymentMap" name="CME">
							   							<nested:optionsCollection property="modeOfPaymentMap" label="value" value="key"/>
							   						</nested:notEmpty>
							   					</nested:select>
			                  					</td>
											</c:otherwise>
										</c:choose>
	                  				</tr>
                				</nested:iterate>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
								<c:choose>
									<c:when test="${paidStatus != null && paidStatus == 'paid'}">
									<td width="5%" height="35" align="right">
									</td>
									</c:when>
									<c:otherwise>
										<td width="5%" height="35" align="right">
											<html:submit property="" value="Update" styleId="update" styleClass="formbutton"></html:submit>
										</td>
									</c:otherwise>
								</c:choose>
							
							<td width="1%" height="35" align="right"></td>
							<td width="55%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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

