<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script type="text/javascript">
	function editDetails(id){
		document.location.href = "ConvocationSession.do?method=editDetails&id="+id;
	}
	function addDetails(id){
		document.getElementById("method").value="addSessionDetails";
	}
	function updateDetails(id){
		document.getElementById("method").value="updateSessionDetails";
	}
	function deleteDetails(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "ConvocationSession.do?method=deleteDetails&id="+id;
		}
	}
	function resetMsgs(){
		document.location.href = "honoursCourseEntry.do?method=editDetails";
	}
	function resetErrorMsgs() {
		resetErrMsgs();
	}
</script>
</head>
<html:form action="/ConvocationSession" method="POST">
<html:hidden property="formName" value="convocationSessionForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" styleId="id"/>
<html:hidden property="method" styleId="method" value="addSessionDetails" />

<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs">Admin <span class="Bredcrumbs">&gt;&gt; Convocation Session</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Convocation Session  </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
	            <tr>
	            	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Date:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:text property="date" styleId="date" size="11" maxlength="11"></html:text>
										<script language="JavaScript">
											new tcal( {
												// form name
												'formname' :'convocationSessionForm',
												// input name
												'controlname' :'date'
											});
										</script>
	              	</td>
	              	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Session:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:radio property="amOrpm" value="AM">&nbsp;AM </html:radio>
                 		<html:radio property="amOrpm" value="PM">&nbsp;PM </html:radio>
	              	</td>
	            </tr>
	            <tr>
	            	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Max Guest Allowed:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:text property="maxGuest" styleId="maxGuest" onkeypress="return isNumberKey(event)"></html:text>
	              	</td>
	              	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Pass Amount:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:text property="passAmount" styleId="passAmount" onkeypress="return isNumberKey(event)"></html:text>
	              	</td>
	            </tr>
            	 <tr>
	            	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Courses:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:select property="courseIds"  styleId="courseId" styleClass="body" multiple="multiple" size="10" style="width:350px" >
						  <html:optionsCollection name="convocationSessionForm" property="courseMap" label="value" value="key" />
					  </html:select>
	              	</td>
	              	<td height="25" class="row-odd"></td>
	              	<td align="left" height="25" class="row-even">
	              	</td>
	            </tr>
             </table></td>
             <td width="5" height="30"  background="images/right.gif"></td>
             </tr>
             <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
            </tr>
            </table></td>
            <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
             <tr>
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
           <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
				<tr> 
	            <td width="45%" height="35"><div align="right">
	            <html:submit property="" styleClass="formbutton" value="Submit" onclick="addDetails()"></html:submit>
				</div>
				</td>
				<td width="2%"></td>
					<td width="53%">
					<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetErrorMsgs()"></html:button>
				</td>
			</tr>				
			<tr><td style="height: 10px" align="center" colspan="3"></td></tr>			
				</table>
			</td>
	          </tr>
          <logic:notEmpty name="convocationSessionForm" property="convocationDetails">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td width="15" height="30%" class="row-odd" align="center" >Date</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Session</td>
                    <td width="15" height="30%" class="row-odd" align="center" >Max Guest Allowed</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Pass Amount</td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="to" name="convocationSessionForm" property="convocationDetails" indexId="count">
                		<c:choose>
							<c:when test="${count%2 == 0}">
									<tr class="row-even">
							</c:when>
							<c:otherwise>
									<tr class="row-white">
							</c:otherwise>
						</c:choose>
                		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="date"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="amOrpm"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="maxGuest"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="passAmount"/></td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="to" property="id"/>')"></div></td>
                   
                </logic:iterate>
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          </logic:notEmpty>
          
          
          </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var sessionId = document.getElementById("tempSession").value;
	if (sessionId != null && sessionId.length != 0) {
		document.getElementById("sessionId").value = sessionId;
	}
</script>