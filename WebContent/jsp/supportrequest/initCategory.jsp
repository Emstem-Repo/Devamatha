<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
	function editCategory(id){
		document.location.href = "category.do?method=editCategory&id="+ id;
	}
	function deleteDetails(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "category.do?method=deleteCategory&id="+ id;
		}
	}
	function reActivate() {
		var id = document.getElementById("reactivateid").value;
		document.location.href = "fineCategory.do?method=activateFineCategory&reactivateid="+ id ;
	}
	function resetValue() {
		 resetFieldAndErrMsgs();
	}
	function resetEdit(){
		document.getElementById("departmentId").value=document.getElementById("tempDeptId").value;
		document.getElementById("name").value=document.getElementById("tempName").value;
		var categoryFor = document.getElementById("tempcategoryFor").value;
		if(categoryFor=="U"){
			document.getElementById("Staff").checked = true;		
		}else if(categoryFor=="S"){
			document.getElementById("Student").checked = true;
		}else if(categoryFor=="B"){
			document.getElementById("Both").checked = true;
		}
		resetErrMsgs();
	}	
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelHome(){
		document.location.href = "category.do?method=initCategory";
	}
			
</script>

<html:form action="/category" focus="name">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateCategory" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addCategory" />
		</c:otherwise>
	</c:choose>

	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="categoryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Support Request <span class="Bredcrumbs">&gt;&gt;
			Category &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Category</strong></td>

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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="aknowledgepro.hostel.fineCategory.name"/>:</div>
									</td>
									<td width="30%" class="row-even"> 
										<input type="hidden" name="categoryForm"	id="tempName" value="<bean:write name='categoryForm' property='name'/>" />
                    						<html:text property="name" styleId="name" maxlength="100" size="50"></html:text>
                					</td>
                    				<td class="row-odd" width="20%">
										<div align="right"><span class="Mandatory">*</span>
											Department:
										</div>
									</td>
									<td class="row-even" width="30%"> 
									<input type="hidden" name="categoryForm"	id="tempDeptId" value="<bean:write name='categoryForm' property='departmentId'/>" />
                    						<html:select property="departmentId" styleId="departmentId">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="departmentMap" name="categoryForm">
						   							<html:optionsCollection property="departmentMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
                					</td>
								</tr>
								<tr>
									<td class="row-odd" width="20%" >
										<div align="right"><span class="Mandatory">*</span>
											Category For:
										</div>
									</td>
                					<td class="row-even" align="left" width="30%" >
                							<input type="hidden" name="categoryForm"	id="tempcategoryFor" value="<bean:write name='categoryForm' property='categoryFor'/>" />
											<input type="radio" name="categoryFor" value="U" id="Staff"></input>Staff<input type="radio" name="categoryFor" value="S" id="Student"></input>Student<input type="radio" name="categoryFor" value="B" id="Both"></input>Both
											<script type="text/javascript">
												var catFor = document.getElementById("tempcategoryFor").value;
												if(catFor=="U"){
													document.getElementById("Staff").checked = true;		
												}else if(catFor=="S"){
													document.getElementById("Student").checked = true;
												}else if(catFor=="B"){
													document.getElementById("Both").checked = true;
												}
											</script>
									</td>
									<td class="row-odd" width="20%" align="right"><bean:message key="knowledgepro.supportrequest.mail.to"/>:
									</td>
									<td class="row-even" width="30%" >
										<html:text property="email" styleId="email" maxlength="50" size="50"></html:text>
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

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="center">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="5%" height="35" align="left">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:button property="" value="Reset" styleId="editReset" styleClass="formbutton" onclick="resetEdit()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" value="Reset" styleClass="formbutton" onclick="resetValue()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="50%" height="35" align="left">
							<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:button property="" value="Cancel" styleId="editReset" styleClass="formbutton" onclick="cancelHome()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
								<tr >
                    				<td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="aknowledgepro.hostel.fineCategory.name"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Department</td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Category For</td>
                    				<td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				</tr>
                 				<logic:notEmpty  name="categoryForm" property="categoryList">
								<logic:iterate id="CME" name="categoryForm" property="categoryList" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   				<tr>
                   					<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="name"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="department"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="categoryFor"/></td>
                   					<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 					height="18" style="cursor:pointer" onclick="editCategory('<bean:write name="CME" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   				</tr>
                    				<c:set var="temp" value="1"/>
                   						</c:when>
                    				<c:otherwise>
		            			<tr>
               						<td  height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               						<td  height="25" class="row-white" align="center"><bean:write name="CME" property="name"/></td>
               						<td  height="25" class="row-white" align="center"><bean:write name="CME" property="department"/></td>
               						<td  height="25" class="row-white" align="center"><bean:write name="CME" property="categoryFor"/></td>
               						<td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 				height="18" style="cursor:pointer" onclick="editCategory('<bean:write name="CME" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-white" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
               					</tr>
                    					<c:set var="temp" value="0"/>
				  					</c:otherwise>
                  				</c:choose>
                				</logic:iterate>
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
