
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<SCRIPT type="text/javascript">
	function editFeeAccount(id) {
		document.location.href = "feeaccountEntry.do?method=editFeeaccount&id=" + id;

	}
	function deleteFeeAccount(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "feeaccountEntry.do?method=deleteFeeAccount&id="
					+ id;
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "feeaccountEntry.do?method=reactivateFeeAccount&id="
				+ id;
	}

	function viewFeeAccount(id) {
		var url = "feeaccountEntry.do?method=viewFeeAccount&id=" + id;
		myRef = window.open(url, "viewContent",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}

	function downloadFile(id) {
		document.location.href = "feeAccountDownload.do?id="+ id;
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 500;
		return (Object.value.length < MaxLen);
	}
</SCRIPT>
<html:form action="/feeaccountEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="feeAccountForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="id" styleId="id" />
<html:hidden property="originalcode" styleId="originalcode" />
<c:choose>
<c:when test="${Update!=null}">
<html:hidden property="method" styleId="method" value="updateFeeAccount" />
</c:when>
<c:otherwise>
<html:hidden property="method" styleId="method" value="addFeeAccount" />
</c:otherwise>
</c:choose>
	
	
    <table width="100%" border="0">
      <tr>
        <td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feeaccountentry"/> &gt;&gt;</span></span></td>
      </tr>
		<tr>
        <td valign="top" class="news"> 
		
		</td>
       </tr>
      <tr>
        <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29" /></td>
              <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feeaccountentry"/></strong></div></td>
              <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29" /></td>
            </tr>
            <tr>
              <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
              <td width="100%" valign="top" class="news"><table width="100%" height="211" border="0" cellpadding="0"
						cellspacing="0">
                <tr>
                  <td height="25" colspan="6" class="mandatoryfield"></td>
                </tr>
				<tr><td></td><td>
						<div align="right"><FONT color="red"> <span class='MandatoryMark'>* Mandatory fields</span></FONT></div>
						<div id="errorMessage">
						<FONT color="red"><html:errors /></FONT>
						<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
						</html:messages> </FONT>
						</div>
					</td></tr>
                <tr>
                  <td height="35" colspan="6" valign="top" class="body"><table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
					
                      <tr>
                        <td><img src="images/01.gif" width="5" height="5" /></td>
					                       <td width="914" background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5" /></td>
                      </tr>
                      <tr>
                        <td width="5" background="images/left.gif"></td>
                        <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                            <tr>
                            	<td width="13%" height="25" class="row-odd"><div align="right"><span
												class="Mandatory">*</span><bean:message key="knowledgepro.adimin.feeaccount.code"/><span class="star"></span>:</div></td>
                              	<td width="19%" height="25" class="row-even">
                              		<span class="star">
									<html:text property="code" styleClass="TextBox"
												styleId="code" size="16" maxlength="30" />
                              		</span>
                           		</td>
                              <td colspan="2" width="19%" class="row-odd"><div align="right">
								<span class="Mandatory">*</span><bean:message key="knowledgepro.adimin.feeaccount.name"/><span class="star"></span>:</div>
							  </td>
                              <td width="24%" class="row-even"><span class="star">
                                <html:text property="name" maxlength="30" size="16" styleId="name" styleClass="TextBox"/>
                              </span></td>
                              
                            </tr>
							<tr>
								<td width="19%" height="25" class="row-odd"><div align="right"><span
												class="Mandatory">*</span><bean:message key="knowledgepro.fee.feeaccount.logo.label"/><span class="star"></span>:</div></td>
                               <td width="25%" height="25" class="row-even">
                               
                               <c:choose>
									<c:when test="${feeAccountOperation == 'edit'}">
										<html:file property="formFile" styleId="file"/>
										<a href="#"
											onclick="downloadFile('<bean:write name="feeAccountForm" property="id"/>')">
											<bean:message key="knowledgepro.guidelines.view"/> </a>
									</c:when>
									<c:otherwise>
										<html:file property="formFile" styleId="file"/>
									</c:otherwise>
							  </c:choose>
                               </td>
							  <td colspan="2" width="19%" class="row-odd"><div align="right">
								<span class="Mandatory">*</span><bean:message key="knowledgepro.fee.feeaccount.printaccountname.label"/><span class="star"></span>:</div>
							  </td>
                              <td width="24%" class="row-even"><span class="star">
                                <html:text property="printAccName" maxlength="70" size="16" styleId="printAccName" styleClass="TextBox"/>
                              </span></td>
							</tr>
							
		                    <tr>
		                     	<td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.feeaccount.bankinfo.label"/><span class="star"></span>:</div></td>
		                        <td class="row-even"><html:textarea property="bankInfo" styleId="bankinfo" cols="30" rows="5"/></td>
		                        
		                        <td colspan="2" class="row-odd"><div align="right"><span
												class="Mandatory">*</span><bean:message key="knowledgepro.fee.feeaccount.printposition.label"/><span class="star"></span>:</div></td>
                               <td width="24%" width="19%" class="row-even"><span class="star">
                                <html:select property="position"  styleId="positionId" styleClass="combo">
                                <html:option value="">- Select -</html:option>
                                <html:option value="1">1</html:option>
                                <html:option value="2">2</html:option>
                                <html:option value="3">3</html:option>
                                </html:select>
                              </span></td>
						  </tr>
                        </table></td>
                        <td width="5" height="30" background="images/right.gif"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" /></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="35" colspan="6" valign="top" class="body"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                      <tr>
                        <td width="47%" height="35"><div align="right">
							<c:choose>
							<c:when test="${Update!=null}">
							<html:submit styleClass="formbutton"
										styleId="button"><bean:message key="knowledgepro.update"/></html:submit>
							</c:when>
							<c:otherwise>
							<html:submit styleClass="formbutton"
										styleId="button"><bean:message key="knowledgepro.admin.add"/></html:submit>
							</c:otherwise>
							</c:choose>
                            
                        </div></td>
                        <td width="1%"></td>
                        <td width="52%"><html:cancel styleClass="formbutton"
										styleId="Reset"><bean:message key="knowledgepro.admin.reset"/></html:cancel></td>
                      </tr>                      
                      <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                      
                  </table></td>
                </tr>
                <tr>
                  <td height="35" colspan="6" valign="top" class="body"><table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
                      <tr>
                        <td><img src="images/01.gif" width="5" height="5" /></td>
                        <td width="100%" background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5" /></td>
                      </tr>
                      <tr>
                        <td width="5" background="images/left.gif"></td>
                        <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                            <tr>
                              <td height="25" class="row-odd" width="7%"><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
			      			  <td class="row-odd" width="15%" align="center"><bean:message key="knowledgepro.adimin.feeaccount.code"/></td>
                              <td class="row-odd" width="15%" align="center"><bean:message key="knowledgepro.adimin.feeaccount.name"/></td>
                              <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.view" /></div></td> 
                              <td class="row-odd" width="7%"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                              <td class="row-odd" width="6%"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                            </tr>
                            <logic:iterate name="feeAccountForm" property="feeAccountList"
											id="feeList" indexId="count" type="com.kp.cms.to.fee.FeeAccountTO">
                              <c:choose>
                                <c:when test="${temp == 0}">
                                  <tr>
                                    <td width="7%" height="25" class="row-even"><div align="center">
                                        <c:out value="${count+1}" />
                                    </div></td>
                                    
									<td width="20%" class="row-even" align="center"><bean:write
														name="feeList" property="code" /></td>
                                    <td width="20%" class="row-even" align="center"><bean:write
														name="feeList" property="name" /></td>
									<td width="6%" height="25" class="row-even"><div align="center"><img src="images/View_icon.gif" style="cursor:pointer"
														width="16" height="18" onclick="viewFeeAccount('<bean:write	name="feeList" property="id" />')"></div></td>					
                                    <td width="6%" height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
															width="16" height="18" onclick="editFeeAccount('<bean:write	name="feeList" property="id" />')"/></div></td>
                                    <td width="6%" height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" style="cursor:pointer" 
                                    						width="16" height="16" onclick="deleteFeeAccount('<bean:write	name="feeList" property="id" />')"/></div></td>
                                  </tr>
                                  <c:set var="temp" value="1" />
                                </c:when>
                                <c:otherwise>
                                  <tr>
                                    <td height="25" class="row-white"><div align="center">
                                        <c:out value="${count+1}" />
                                    </div></td>
                                    
									<td width="20%" class="row-white" align="center"><bean:write
														name="feeList" property="code" /></td>
                                    <td width="20%"class="row-white" align="center"><bean:write
														name="feeList" property="name" /></td>
									<td height="25" class="row-white"><div align="center"><img src="images/View_icon.gif" style="cursor:pointer"
														width="16" height="18" onclick="viewFeeAccount('<bean:write	name="feeList" property="id" />')"></div></td>
                                    <td height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
															width="16" height="18" onclick="editFeeAccount('<bean:write	name="feeList" property="id" />')"/></div></td>
                                    <td height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="16"
                                    						style="cursor:pointer" onclick="deleteFeeAccount('<bean:write	name="feeList" property="id" />')"/></div></td>
                                  </tr>
                                  <c:set var="temp" value="0" />
                                </c:otherwise>
                              </c:choose>
                            </logic:iterate>
                        </table></td>
                        <td width="5" height="30" background="images/right.gif"></td>
                      </tr>
                  </table></td>
                  
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                  <td width="100%" background="images/05.gif"></td>
                  <td><img src="images/06.gif" /></td>
                </tr>
              </table></td>
              <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
            <tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
            <tr>
              <td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
              <td width="100%" background="images/TcenterD.gif"></td>
              <td><img src="images/Tright_02.gif" width="9" height="29" /></td>
            </tr>
        </table></td>
      </tr>
    </table>
</html:form>
