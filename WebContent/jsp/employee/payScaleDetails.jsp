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
	document.location.href = "payScaleDetails.do?method=reactivatePayScale";
}
 function cancelAction(){
	 document.location.href = "payScaleDetails.do?method=initPayScaleDetails";
 }
function add(){
	document.location.href = "payScaleDetails.do?method=addPayScale";
}
 function update(){
	 document.location.href = "payScaleDetails.do?method=updatePayScale";
 }
 <%--function resetPayScale(){
	 var destination = document.getElementById("payScale");
	 destination.selected = false;
	 var destination1 = document.getElementById("scale");
	 destination1.selected = false;
	 resetErrMsgs();
 }--%>
 function resetPayScale(){
	 document.getElementById("payScale").value="";
	 document.getElementById("scale").value="";
	 if(document.getElementById("method").value == "updatePayScale"){
		 document.getElementById("payScale").value=document.getElementById("origPayscale").value;
		 document.getElementById("scale").value=document.getElementById("origScale").value;
		 var Value=document.getElementById("origteachingStaff").value;
		 if(Value!=null)
		 	{
		 	document.getElementById("teachingStaff").value=document.getElementById("origteachingStaff").value;
		 	}
		 
	 }
	 resetErrMsgs();
 }
 function editPayScale(id){
	 document.location.href = "payScaleDetails.do?method=editPayScale&id="+id;
 }
 function deletePayScale(id){
	 document.location.href = "payScaleDetails.do?method=deletePayScale&id="+id;
 }
</script>
</head>
<body>
<html:form action="/payScaleDetails">
<c:choose>
		<c:when test="${payScaleOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updatePayScale" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addPayScale" />
		</c:otherwise>
	</c:choose>
<html:hidden property="formName" value="payScaleDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="addPayScale" />
	<html:hidden property="origPayscale"	styleId="origPayscale" name="payScaleDetailsForm"/>
	<html:hidden property="origScale" styleId="origScale" name="payScaleDetailsForm"/>
	<html:hidden property="origteachingStaff" styleId="origteachingStaff" name="payScaleDetailsForm"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Employee 
			<span class="Bredcrumbs">&gt;&gt;Payscale Details &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Payscale Details</strong></div>
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
							<div align="right"><span class='MandatoryMark'>mandatoryfields</span></div>
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
              		 <td class="row-odd" width="50%">
								    <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.employee.isTeachingStaff"/>
								    </div>
					</td>
					<td  class="row-even" width="50%" colspan="3">
									 <html:radio property="teachingStaff" value="1"/>Teaching&nbsp; 
									<html:radio property="teachingStaff" value="0"/>Non-Teaching&nbsp;
					</td>
           </tr>
            <tr>
            	<td   class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>Grade:</div></td>
                <td  height="25" class="row-even" width="30%">
                	<table width="189" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td width="60">
                      			<html:text property="payScale" name="payScaleDetailsForm" maxlength="50" styleId="payScale" size="40"></html:text> 
                      		</td>
                      		
                    	</tr>
                	</table>
                </td>
			    <td  class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>Scale:</div></td>
                <td class="row-even" width="30%">
                	<table width="188" border="0" cellspacing="0" cellpadding="0">
                  		<tr>
                    		<td>
                    		<html:text property="scale" name="payScaleDetailsForm" maxlength="50" styleId="scale" size="40"></html:text>  </td>
                  		</tr>
                	</table>
                </td>
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
						<tr>
            <td width="49%" height="35" align="right">
            <c:choose>
					<c:when test="${payScaleOperation == 'edit'}">
						<html:submit  value="Update" styleClass="formbutton" ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit  value="Submit" styleClass="formbutton" ></html:submit></c:otherwise>
				</c:choose></td>
            
            <td width="49%" height="35" align="left">
            <c:choose>
						<c:when test="${payScaleOperation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetPayScale()"></html:button>
						</c:otherwise>
					</c:choose>
            
            &nbsp;
            
            <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
            </td>
          </tr>

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
											<div align="center">Grade</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Scale</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Teaching/Non-Teaching</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Edit</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Delete</div>
											</td>
										</tr>
										<logic:notEmpty name="payScaleDetailsForm" property="payScaleToList">
									<logic:iterate id="payscale" name="payScaleDetailsForm"
										property="payScaleToList" indexId="count">
										<tr>
										<td width="20%" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="payscale" property="payScale" /></div> </td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="payscale" property="scale" /></div> </td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="payscale" property="teachingStaff" /></div> </td>
										<td width="20%" height="25" class="row-even">
										<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editPayScale('<nested:write name="payscale" property="id" />')" /> </div>

										</td>
										<td width="20%" height="25" class="row-even"> 
										<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deletePayScale('<nested:write name="payscale" property="id" />')" /></div> 
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