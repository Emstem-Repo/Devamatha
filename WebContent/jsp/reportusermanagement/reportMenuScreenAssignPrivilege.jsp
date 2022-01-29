<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script src="js/reportsMenuScreen.js" type="text/javascript"></script>
<style type="text/css">
.highlight{background: #6495ED;}   
</style>
<html:form action="/reportMenuScreen">
	<html:hidden property="method" styleId="method" value="addReportsMenuAssignAggrement"/>
	<html:hidden property="formName" value="reportsScreenMasterForm" />
	<html:hidden property="pageType" value="2" />
	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.usermanagement.reportmenuScreenMaster" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="954" background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.usermanagement.reportmenuScreenMaster" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
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
						</tr>
						<tr>
						<td height="15" bgcolor="#E6E6FA">Reports Menu Name :- <bean:write name="reportsScreenMasterForm" property="menuName"/>
						</td>
						</tr>
					<tr>
					<td>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.sec.Roles" />
									</td>
									<td  class="row-odd" align="center">
											 SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
									</td>
									</tr>
								<logic:notEmpty name="reportsScreenMasterForm" property="privilegeList">
								<tr>
									<nested:iterate id="prev" name="reportsScreenMasterForm" property="privilegeList" indexId="count">
									<%
                						String styleId="check_"+count;
										String flag="flag_"+count;
                					%>
										<c:choose>
										 <c:when test="${count%2 == 0}">
											<tr class="row-even">
										    	</c:when>
													<c:otherwise>
														<tr class="row-white">
														</c:otherwise>
					 					  </c:choose>
					 					  <td width="6%" height="25">
													<div align="center"><c:out value="${count + 1}" /></div>
										 </td>
										  <td width="76%" align="center"><nested:write
														name="prev" property="roleName" />
											</td>
												<td align="center" >
												<nested:hidden property="tempChecked" styleId="<%=flag %>" name="prev"></nested:hidden>
				                   					<nested:checkbox property="checked" styleId="<%=styleId%>" onclick="unCheckSelectAll()"> </nested:checkbox>
				                   					<script type="text/javascript">
				                   							var id=<c:out value='${count}'/>;
				                   							var flag=document.getElementById("flag_"+id).value;
				                   							if(flag=="on"){
				                   								document.getElementById("check_"+id).checked = true;
				                       							}
				                   					</script>
										   </td>
									</nested:iterate>
									</tr>
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
				</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
                    <td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="50" align="center" colspan="2"> 
                	<html:submit property="" styleClass="formbutton" value="Submit"></html:submit>&nbsp;&nbsp;
					<html:button property="" styleClass="formbutton" value="Back" onclick="backMenuScreen()"></html:button>	
					</td>
					<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                </tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
changeColor();
</script>