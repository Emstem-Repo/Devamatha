<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
	function edit(id){
		document.location.href = "openSyllabusEntry.do?method=edit&id="+ id;
	}
	function delet(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "openSyllabusEntry.do?method=delete&id="+ id;
		}
	}
	function resetEdit(){
		document.getElementById("batch").value=document.getElementById("tempBatch").value;
		document.getElementById("startDate").value=document.getElementById("tempStartDate").value;
		document.getElementById("endDate").value=document.getElementById("tempEndDate").value;
		}
	function resetValue() {
		 resetFieldAndErrMsgs();
	}
	
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelHome(){
		document.location.href = "openSyllabusEntry.do?method=initOpenSyllabusEntry";
	}
			
</script>
<html:form action="/openSyllabusEntry" method="post">
<html:hidden property="id" styleId="id" />
<html:hidden property="formName" value="openSyllabusEntryForm" />
<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="update" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="add" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Admin <span class="Bredcrumbs">&gt;&gt;
			Open Syllabus Entry &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Open Syllabus Entry</strong></td>

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
	                       	  	<td width="25%" class="row-odd" ><div align="right">
	                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.cancelattendance.batches"/>:</div>
	                             </td>
	                             <td width="25%" height="25" class="row-odd" >
	                             <html:hidden property="tempBatch" styleId="tempBatch"/>
	                           		<html:select property="batch" styleClass="combo" styleId="batch">
										<html:option value=""> <bean:message key="knowledgepro.admin.select" />	</html:option>
										<cms:renderMinToMaxYearList></cms:renderMinToMaxYearList>
									</html:select>
	                             </td>
	                             <td width="25%" class="row-odd" colspan="2"></td>
	                           </tr>
								<tr>
     								<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
              						<td   class="row-even" width="25%" align="left">
										<html:hidden property="tempStartDate" styleId="tempStartDate"/>
										<html:text  property="startDate" styleId="startDate" size="10" maxlength="16"/>
                               				<script language="JavaScript">
			 									$(function(){
								 					var pickerOpts = {
								        			dateFormat:"dd/mm/yy"
								       				};  
								  					$.datepicker.setDefaults(
								   					$.extend($.datepicker.regional[""])
								  					);
								  				$("#startDate").datepicker(pickerOpts);
													});
                                			</script>
									</td> 
									<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
              						<td   class="row-even" width="25%" align="left">
										<html:hidden property="tempEndDate" styleId="tempEndDate"/>
										<html:text  property="endDate" styleId="endDate" size="10" maxlength="16"/>
                               				<script language="JavaScript">
			 									$(function(){
								 					var pickerOpts = {
								        			dateFormat:"dd/mm/yy"
								       				};  
								  					$.datepicker.setDefaults(
								   					$.extend($.datepicker.regional[""])
								  					);
								  				$("#endDate").datepicker(pickerOpts);
													});
                                			</script>
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
				<logic:notEmpty  name="openSyllabusEntryForm" property="list">
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
                    				<td width="5%"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.cancelattendance.batches"/></td>
                    				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.feepays.startdate"/></td>
                    				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.feepays.enddate"/></td>
                    				<td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				</tr>
								<logic:iterate id="CME" name="openSyllabusEntryForm" property="list" indexId="count">
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   				<tr>
                   					<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="batch"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="startDate"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="endDate"/></td>
                   					<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 					height="18" style="cursor:pointer" onclick="edit('<bean:write name="CME" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="delet('<bean:write name="CME" property="id"/>')"></div></td>
                   				</tr>
                				</logic:iterate>
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
				</logic:notEmpty>
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
	<script type="text/javascript">
document.getElementById("batch").value=document.getElementById("tempBatch").value;
</script>
</html:form>
