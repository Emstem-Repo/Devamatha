<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.itextpdf.text.log.SysoLogger"%><link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
    <link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui-supportRequest.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
	<script>
	$(document).ready(function() {
	 $("#openDialog").hide();
	});
	function cancelAction() {
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function remarkFun(id,count){
		var status = document.getElementById("status_"+count).value;
		document.getElementById("remarks").value=null;
		 $("#openDialog").dialog({
 	        resizable: false,
 	        modal: true,
 	        height: 200,
 	        title: "Enter Remarks",
 	        width: 260,
 	        close: function() {
			document.getElementById("status_"+count).value="";
 	    	$("#openDialog").dialog("destroy");
 	    	$("#openDialog").hide();
          },
          buttons: {
        	  Submit : function() {
            	var remarks = document.getElementById("remarks").value;
            	if(remarks.trim()!=''){
                	if(remarks.length>500){
                		 $.confirm({
              				'message'	: 'Remarks length is too large',
              				'buttons'	: {
              					'Ok'	: {
              						'class'	: 'blue',
              						'action': function(){
              							$.confirm.hide();
              						}
              					}
              				}
              			});
                	}else{
                		hook=false;
                		document.location.href = "studentSupportRequest.do?method=updateStatusAndRemarks&id="+id+"&status="+status+"&remarks="+remarks;
                   	 $("#openDialog").dialog("close");
                     $("#openDialog").hide();
                    	}
                	
            	}else{
            		 $.confirm({
         				'message'	: 'Please Enter Remarks',
         				'buttons'	: {
         					'Ok'	: {
         						'class'	: 'blue',
         						'action': function(){
         							$.confirm.hide();
         						}
         					}
         				}
         			});
                	}
            },
           Cancel : function() {
  			   document.getElementById("status_"+count).value="";
                $("#openDialog").dialog("close");
                $("#openDialog").hide();
             }
 	               
          }
 	    });
		}
	function categoryValue(id,count){
		var categoryId = document.getElementById("catgryId_"+count).value;
		var hidecategoryId = document.getElementById("hidcatgryId_"+count).value;
		 $.confirm({
				'message'	: 'This action will change the category to the selected one. Are you sure to change it?',
				'buttons'	: {
					'Yes'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.location.href = "studentSupportRequest.do?method=updateCategory&id="+id+"&categoryId="+categoryId;
						}
					},
 	       'No'	:  {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.getElementById("catgryId_"+count).value=hidecategoryId;
						}
					}
				}
			});
	}
	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function winOpen(id,reqId) {
		var url = "studentSupportRequest.do?method=getStudentOrUserDetails&regOrUserId="+id+"&deptId="+reqId;
		myRef = window.open(url, "studentOrUserDetails",
						"left=20,top=20,width=1000,height=400,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
	<style type="text/css">
	 body { font-size: 62.5%; }
</style>
<html:form action="/studentSupportRequest" >
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="studentSupportRequestForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Support Request Status<span class="Bredcrumbs">&gt;&gt;
			Support Request Status &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Support Request Status</strong></td>

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
							<td valign="top">
							<logic:notEmpty name="studentSupportRequestForm" property="list">
											<table width="100%" border="0" align="center" cellpadding="0"cellspacing="0">
												<tr>
													<td valign="top">
													<table width="100%" cellspacing="1" cellpadding="2">
														<tr >
										       				<td width="5%"  height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
										       				<td width="5%" height="25" class="row-odd"><div align="center">Request ID</div></td>
										       				<td width="5%" height="25" class="row-odd"><div align="center">Date</div></td>
										       				<td width="5%" height="25" class="row-odd"><div align="center">Issue Raised By</div></td>
										       				<td width="10%" height="25" class="row-odd"><div align="center">Campus</div></td>
										       				<td width="35%" height="25" class="row-odd"><div align="center">Issue</div></td>
										       				<td width="20%" class="row-odd"><div align="center">Remarks</div></td>
										       				<td width="width="10%" height="25" class="row-odd"><div align="center">Change Category</div></td>
										       				<td width="5%" class="row-odd"><div align="center">Status</div></td>
										             	</tr>
														<nested:iterate id="CME" name="studentSupportRequestForm" property="list" indexId="count" type="com.kp.cms.to.admin.StudentSupportRequestTo">
														<% 
															String method = "categoryValue("+CME.getId()+","+count+")";
															String styleId = "catgryId_"+count;
															String hideStyleId = "hidcatgryId_"+count;
															String method1="remarkFun("+CME.getId()+","+count+")";
															String styleId1 = "status_"+count;
														%>
										  						<c:choose>
																	<c:when test="${count%2 == 0}">
																		<tr class="row-even">
																	</c:when>
																	<c:otherwise>
																		<tr class="row-white">
																	</c:otherwise>
																</c:choose>
										           					<td  height="25" ><div align="center"><c:out value="${count + 1}"/></div></td>
										           					<td  height="25" ><div align="center"><bean:write name="CME" property="requestId"/></div></td>
										           					<td  height="25" ><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
										           					<td  height="25" >
										           						<a href="javascript:winOpen('<bean:write name="CME" property="issueRaisedBy"/>','<bean:write name="CME" property="requestId"/>')"><bean:write name="CME" property="issueRaisedBy"/></a>
										           					</td>
										           					<td  height="25" ><div align="center"><bean:write name="CME" property="campus"/></div></td>
										           					<td  height="25" ><div align="left"><bean:write name="CME" property="description"/></div></td>
										           					<td  height="25" ><div align="left"><bean:write name="CME" property="remarks"/></div></td>
										           					<td  height="25" width="10%" align="left">
										           					<nested:hidden property="id" styleId="catagoriId" ></nested:hidden>
										           						<nested:hidden property="catgryId" styleId="<%=hideStyleId %>" name="CME"></nested:hidden>
										           						<nested:select property="catgryId" styleId="<%=styleId %>" name="CME" styleClass="comboMedium" onchange="<%=method %>">
								                    						<html:option value="">--Select--</html:option>
								                    						<nested:notEmpty property="categoryMap" name="CME">
													   							<nested:optionsCollection property="categoryMap" label="value" value="key"/>
													   						</nested:notEmpty>
													   					</nested:select>
										           					</td>
										           					<td  height="25" align="center">
										           						<nested:select property="status" styleId="<%=styleId1 %>" name="CME" onchange="<%=method1 %>">
								                    						<html:option value="">--Select--</html:option>
								                    						<nested:notEmpty property="statusMap" name="CME">
													   							<nested:optionsCollection property="statusMap" label="value" value="key"/>
													   						</nested:notEmpty>
													   					</nested:select>
										           					</td>
										    				</nested:iterate>
													</table>
													</td>
													</tr>
												</table>
							</logic:notEmpty>
							</td>
						</tr>
						<tr>
							<td height="10">
								<div id="openDialog">
										<div align="center">
											<textarea name="remarks" style="width: 240px; padding: 3px;" id="remarks" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></textarea>
											<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; color: red">/500 Characters 
										</div>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center"><html:button property="" value="Cancel" styleClass="formbutton" onclick="cancelAction()"></html:button></td>
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