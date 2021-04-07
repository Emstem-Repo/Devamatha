<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  $('#Submit').click(function(){
       var poolName = $('#poolName').val();
       if(poolName== ''){
    	   $('#errorMessage').slideDown().html("<span>Pool Name is Required</span>");
          return false;
        }
       else{
    	   document.getElementById("method").value = "addExamPoolName";
   		   document.examRoomAllotmentPoolForm.submit();
       }
      });
  $('#Update').click(function(){
      var poolName = $('#poolName').val();
      if(poolName== ''){
   	   $('#errorMessage').slideDown().html("<span>Pool Name is Required</span>");
         return false;
       }
      else{
   	       document.getElementById("method").value = "updatePoolDetails";
   	       resetErrMsgs();
  		   document.examRoomAllotmentPoolForm.submit();
      }
     });
});	
	function editPool(id) {
		document.location.href = "examRoomAllotmentPool.do?method=editPoolDetails&id="+id;
	}
	function deletePool(id) {
		 $.confirm({
				'message'	: 'Are you sure you want to delete this entry?',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.location.href = "examRoomAllotmentPool.do?method=deletePoolDetails&id="+id;
						}
					},
 	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
	}
	function resetPool() {
		document.getElementById("poolName").value = "";
		document.getElementById("errorMessage").value = "";
		if(document.getElementById("method").value=="updatePoolDetails"){
			 document.getElementById("poolName").value=document.getElementById("origPoolName").value;
		}
		resetErrMsgs();

//	 resetFieldAndErrMsgs();
	}
	function reActivate() {
		document.location.href = "examRoomAllotmentPool.do?method=reactivatePoolDetails";
	}
</script>

<html:form action="/examRoomAllotmentPool" method="post">
	<html:hidden property="formName" value="examRoomAllotmentPoolForm" />
	<html:hidden property="pageType" value="1"/>
    <input type="hidden" id="origPoolName" value="<bean:write property="poolName" name="examRoomAllotmentPoolForm"/>"/>	
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updatePoolDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addExamPoolName" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.pool.creation" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.pool.creation" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
							</td>
						</tr>
						<tr>
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
                            <td width="50%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.allotment.pool.creation.name" />:</div>
							</td>
							<td width="50%" height="25" class="row-even">
                            <div align="left">
                            <html:text property="poolName" name="examRoomAllotmentPoolForm" styleClass="TextBox" styleId="poolName" maxlength="50"></html:text>
                            </div>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update"  styleId="Update"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit"  styleId="Submit"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetPool()" styleId="reset"></html:button>
										</td>
								</tr>
							</table>
							</td>
						</tr>
					<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="10%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="70%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.exam.allotment.pool.creation.name" /></td>
									<td width="10%" class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<c:set var="slnocount" value="0" />
								<logic:notEmpty name="examRoomAllotmentPoolForm" property="allotmentPoolToList">
								<logic:iterate name="examRoomAllotmentPoolForm" property="allotmentPoolToList" id="poolAllot" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td width="10%" height="25" align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="70%" height="25" align="center"><bean:write
										name="poolAllot" property="poolName"/> </td>
									<td width="10%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editPool('<bean:write name="poolAllot" property="id"/>')">
									</div>
									</td>
									<td width="10%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deletePool('<bean:write name="poolAllot" property="id"/>')">
									</div>
									</td>
								</logic:iterate>
								</logic:notEmpty>
							</table>
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
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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