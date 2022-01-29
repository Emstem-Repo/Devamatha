<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function reActivate(){
	document.location.href = "examRevaluationFee.do?method=reactivateRevaluationFee";
}
 function cancelAction(){
	 document.location.href = "examRevaluationFee.do?method=initExamRevaluationFee";
 }
function add(){
	document.location.href = "examRevaluationFee.do?method=addRevaluationFee";
}
 function update(){
	 document.location.href = "examRevaluationFee.do?method=updateRevaluationFee";
 }
 <%--function resetPayScale(){
	 var destination = document.getElementById("payScale");
	 destination.selected = false;
	 var destination1 = document.getElementById("scale");
	 destination1.selected = false;
	 resetErrMsgs();
 }--%>
 function resetRevaluation(){
	 document.getElementById("programtype").value="";
	 document.getElementById("type").value="-Select-";
	 document.getElementById("amount").value="";
	 if(document.getElementById("method").value == "updateRevaluationFee"){
		 document.getElementById("programtype").value=document.getElementById("origProgramType").value;
		 document.getElementById("type").value=document.getElementById("origType").value;
		 document.getElementById("amount").value=document.getElementById("origAmount").value;
	 }
	 resetErrMsgs();
 }
 function editRevaluationFee(id){
	 document.location.href = "examRevaluationFee.do?method=editRevaluationFee&id="+id;
 }
 function deleteRevaluationFee(id){
	 document.location.href = "examRevaluationFee.do?method=deleteRevaluationFee&id="+id;
 }
</script>
</head>
<body>
<html:form action="/examRevaluationFee">
<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateRevaluationFee" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addRevaluationFee" />
		</c:otherwise>
	</c:choose>
<html:hidden property="formName" value="examRevaluationFeeForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="addRevaluationFee" />
	<html:hidden property="origProgramType"	styleId="origProgramType" name="examRevaluationFeeForm"/>
	<html:hidden property="origType" styleId="origType" name="examRevaluationFeeForm"/>
	<html:hidden property="origAmount" styleId="origAmount" name="examRevaluationFeeForm"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Exam 
			<span class="Bredcrumbs">&gt;&gt;Exam Revaluation Fee &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Exam Revaluation Fee</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'>mandatory fields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
            <tr>
            	<td   class="row-odd" width="25%"><div align="right">&nbsp;<span class="Mandatory">*</span>Program Type:</div></td>
                <td  height="25" class="row-even" width="25%">
                	<html:select property="programTypeId"  styleId="programtype" styleClass="combo" name="examRevaluationFeeForm">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="examRevaluationFeeForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
			    <td  class="row-odd" width="25%"><div align="right">&nbsp;<span class="Mandatory">*</span>Type:</div></td>
                <td class="row-even" width="25%">
                	<html:select property="type"  styleId="type" styleClass="combo" name="examRevaluationFeeForm">
                	     
                	     <html:option value="Revaluation">Revaluation</html:option>
                	     <html:option value="Re-totaling">Re-totaling</html:option>
                	</html:select>
                </td>
			</tr>
			<tr>
			    <td class="row-odd" width="25%">
			       <div align="right">&nbsp;<span class="Mandatory">*</span>Amount:</div>
			    </td>
			    <td class="row-even" width="25%">
                     <html:text property="amount" name="examRevaluationFeeForm" styleId="amount"></html:text>			    
			    </td>
			    <td class="row-odd" width="25%"></td>
			    <td class="row-even" width="25%"></td>
			</tr>
           
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="2"></td></tr>
						<tr>
            <td width="49%" height="35" align="right">
            <c:choose>
					<c:when test="${operation == 'edit'}">
						<html:submit  value="Update" styleClass="formbutton" ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit  value="Submit" styleClass="formbutton" ></html:submit>&nbsp;&nbsp;</c:otherwise>
				</c:choose></td>
            
            <td width="49%" height="35" align="left">
            <c:choose>
						<c:when test="${operation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetRevaluation()"></html:button>&nbsp;
						</c:otherwise>
					</c:choose>
            <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
            </td>
          </tr>						
          <tr><td style="height: 10px" align="center" colspan="2"></td></tr>
					</table>
					</div>
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
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="20%" height="25" class="row-odd">
											<div align="center">slno</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Program Type</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Type</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Amount</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Edit</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Delete</div>
											</td>
										</tr>
										<logic:notEmpty name="examRevaluationFeeForm" property="revaluationFeeToList">
									<logic:iterate id="revaluation" name="examRevaluationFeeForm"
										property="revaluationFeeToList" indexId="count">
										<tr>
										<td width="20%" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="revaluation" property="programType" /></div> </td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="revaluation" property="type" /></div> </td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="revaluation" property="amount" /></div> </td>
										<td width="20%" height="25" class="row-even">
										<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editRevaluationFee('<nested:write name="revaluation" property="id" />')" /> </div>

										</td>
										<td width="20%" height="25" class="row-even"> 
										<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteRevaluationFee('<nested:write name="revaluation" property="id" />')" /></div> 
										</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							
						</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</html:form>
</body>
</html>