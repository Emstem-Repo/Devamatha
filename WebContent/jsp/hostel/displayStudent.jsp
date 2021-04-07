<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" language="javaScript">	
	function cancelAction() {
		document.location.href = "studentInout.do?method=initStudentInout";
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}	
	function checkForEmpty(field) {
		if (field.value.length == 0)
			field.value = "00";
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function clearField(field) {
		if (field.value == "00"){
			field.value = "";
		}
		if(field.value == "0"){
			field.value = "";
		}
	}
</script>

</head>
<body>
<html:form action="/studentInout">
<html:hidden property="formName" value="studentInoutForm" />
<html:hidden property="method" styleId="method" value="submitStudentDetails"/>
<html:hidden property="pageType" value="2" />
<table width="99%" border="0">
 <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.student.in.out"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.student.in.out"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
								<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
								<div id="errorMessage">
									<FONT color="red"><html:errors /></FONT>
									<FONT color="green">
										<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
										</html:messages>
									</FONT>
								</div>
							</td>
	     <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.slno"/></td>
                  <td class="row-odd">Hostel Name</td>
                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.student.name"/> </td>
                  <td colspan="2" align="center" class="row-odd" ><bean:message key="knowledgepro.hostel.activity"/></td>
                  </tr>
                <tr >
                  <td height="25" align="center" class="row-even" >&nbsp;</td>
                  <td class="row-even">&nbsp;</td>
                  <td class="row-even">&nbsp;</td>
                  <td class="row-even"><bean:message key="knowledgepro.hostel.timeout"/></td>
				  <td class="row-even"><bean:message key="knowledgepro.hostel.timein"/></td>
                  
                </tr>
				<c:set var="temp" value="0"/>
				<c:set var="i" value="0"/>
				
				<logic:notEmpty  name="studentInoutForm" property="studentInoutToList" >
                <nested:iterate  id="studIn"  name="studentInoutForm" property="studentInoutToList" indexId="count" type="com.kp.cms.to.hostel.StudentInoutTo">
					<%
						String timeOutDateId = "timeout" + count;
						String timeInDateId = "timein" + count;
					%>
	                <c:choose>
	                   <c:when test="${temp == 0}">
				 <tr>
	            	<td height="25" align="center" class="row-white"><c:out value="${count+1}"/></td>
	              	<td class="row-white"><bean:write name="studIn" property="hostelName" /></td>
	              	<td class="row-white"><bean:write name="studIn" property="firstName" /></td>
					<td class="row-white" >
						<table width="82" border="0" cellspacing="0" cellpadding="0">
	                    	<tr>
	                      		<td width="60"><nested:text property="outTime"  styleId='<%=timeOutDateId %>' size="10" maxlength="10" /></td>
	                      		<td width="40">
									<script language="JavaScript">							
											new tcal ({
												// form name
												'formname': 'studentInoutForm',
												// input name
												'controlname': '<%=timeOutDateId %>'
											});
									</script>
								</td>
	                    	</tr>	                    
	                  	</table>
						<nested:text property="outTimeone" maxlength="2" size="2"  onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>:
						<nested:text property="outTimetwo" maxlength="2" size="2"  onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>
					</td>
					<td class="row-white">
						<table width="82" border="0" cellspacing="0" cellpadding="0">
	                    	<tr>
	                      		<td width="60"><nested:text property="inTime" styleId='<%=timeInDateId %>' size="10" maxlength="10"/></td>
		                      	<td width="40">
									<script language="JavaScript">								
										new tcal ({											
											// form name
											'formname': 'studentInoutForm',
											// input name
											'controlname': '<%=timeInDateId %>'											
										});
									</script>
								</td>
	                    	</tr>
	                  	</table>
						<nested:text property="inTimeone" maxlength="2" size="2"  onkeypress ="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>:
						<nested:text property="inTimetwo" maxlength="2" size="2"  onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>
					</td>
	                
	          	</tr>
				<c:set var="temp" value="1"/>
	            		</c:when>
						<c:otherwise>
				<tr>
	              	<td height="25" align="center" class="row-even" ><c:out value="${count+1}"/></td>
	               	<td class="row-even"><bean:write name="studIn" property="hostelName" /></td>
	               	<td class="row-even"><bean:write name="studIn" property="firstName"/></td>
					<td class="row-even">
						<table width="82" border="0" cellspacing="0" cellpadding="0">
	                    	<tr>
	                      		<td width="60"><nested:text property="outTime" styleId='<%=timeOutDateId %>'  size="10" maxlength="10"/></td>
	                      		<td width="40">
									<script language="JavaScript">							
											new tcal ({
												// form name
												'formname': 'studentInoutForm',
												// input name
												'controlname': '<%=timeOutDateId %>'
											});
									</script>
								</td>
	                    	</tr>
	                  	</table>
						<nested:text property="outTimeone" maxlength="2" size="2" onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>:
						<nested:text property="outTimetwo" maxlength="2" size="2" onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"/>
					</td>
					<td class="row-even">
						<table width="82" border="0" cellspacing="0" cellpadding="0">
	                    	<tr>
	                      		<td width="60"><nested:text property="inTime" styleId='<%=timeInDateId %>' size="10" maxlength="10" /></td>
		                      	<td width="40">
									<script language="JavaScript">									
										new tcal ({									
											// form name
											'formname': 'studentInoutForm',
											// input name
											'controlname': '<%=timeInDateId %>'
										});
									</script>
								</td>
	                    	</tr>
	                  	</table>
						<nested:text property="inTimeone" maxlength="2" size="2"  onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)"  />:
						<nested:text property="inTimetwo" maxlength="2" size="2"  onkeypress="return isNumberKey(event)" onblur="checkForEmpty(this), checkNumber(this)" onfocus="clearField(this)" />
					</td>
	        	</tr>
				<c:set var="temp" value="0"/>
						</c:otherwise>	                    
	              	</c:choose>
                </nested:iterate>
                </logic:notEmpty>


			</table>
		</td>
        <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
		<tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
		<tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" >&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="49%" align="right"><html:submit property="" styleClass="formbutton" value="Submit" /></td>
              <td width="2%" align="center">&nbsp;</td>
              <td width="49%"><input type="button" class="formbutton" value="Cancel" onclick="cancelAction()" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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
</body>
</html>
