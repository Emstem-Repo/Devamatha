<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<script>


function searchStreamWise(streamId){
		getDepartmentByStreamWise(streamId,updateDepartmentMap);
}
function updateDepartmentMap(req){
	updateOptionsFromMap(req,"tempDepartmentId","-Select-");
}


function cancel() {
		document.location.href = "LoginAction.do?method=loginAction";
}



function imposeMaxLengthName(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 100;
	return (Object.value.length < MaxLen);
}
function imposeMaxLengthUID(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 10;
	return (Object.value.length < MaxLen);
}
function imposeMaxLengthUIDD(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 15;
	return (Object.value.length < MaxLen);
}
function imposeMaxLengthFingerPrintId(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 30;
	return (Object.value.length < MaxLen);
}
</script>

<body>
<html:form action="/EmployeeInfoViewDisplay" enctype="multipart/form-data" method="POST">
<html:hidden property="method" value="getSearchedEmployeeGen" styleId="method"/>
<html:hidden property="pageType" value="13"/>
<html:hidden property="formName" value="EmployeeInfoViewForm"/>
	<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.employee.View" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="954" background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.View" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
     <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
<div id="errorMessage">
      			<html:errors/><FONT color="red">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                    <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.employeeId"/>:</div></td>
                    <td width="25%" class="row-even"><html:text property="tempFingerPrintId" styleId="tempFingerPrintId" onkeypress="return imposeMaxLengthFingerPrintId(event,this)"/></td>
                    
                    <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
                    <td width="30%" class="row-even"><html:text property="tempName" styleId="tempName" onkeypress="return imposeMaxLengthName(event,this)"/></td>
                    
             </tr>
             
              <tr class="row-white">
                    <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.code"/>:</div></td>
                    <td width="25%" class="row-even"><html:text property="tempCode" styleId="tempCode" onkeypress="return imposeMaxLengthUID(event,this)"/></td>
                    
                     <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.uId"/>:</div></td>
                    <td width="30%" class="row-even"><html:text property="tempUid" styleId="tempUid" onkeypress="return imposeMaxLengthUIDD(event,this)"/></td>
                    
             </tr>
              <tr class="row-white">
              	 <td width="15%" class="row-odd"><div align="right" ><bean:message key="knowledgepro.employee.streamDetails"/></div></td>
                <td width="25%" height="25" class="row-even" >
                 <html:select property="tempStreamId" styleId="tempStreamId" onchange="searchStreamWise(this.value)">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="tempStreamMap" name="EmployeeInfoViewForm">
						<html:optionsCollection property="tempStreamMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
            </td>
             <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.Department"/>:</div></td>
                    <td width="30%" class="row-even">
                    <html:select property="tempDepartmentId" styleId="tempDepartmentId">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="tempDepartmentMap" name="EmployeeInfoViewForm">
						<html:optionsCollection property="tempDepartmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				    </html:select>
				   </td>
                    </tr>
              <tr>
                <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.designation"/>:</div></td>
                 <td width="25%" class="row-even"><html:select property="tempDesignationPfId" styleId="tempDesignationPfId">
				   	<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="tempDesignationMap" name="EmployeeInfoViewForm">
						<html:optionsCollection property="tempDesignationMap" label="value" value="key"/>
						</logic:notEmpty>
					</html:select>
				</td>
				 <td width="15%" height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.employee.empType"/></div></td>
                <td width="30%" class="row-even">
                 <html:select property="tempEmptypeId" styleId="tempEmptypeId">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="tempEmpTypeMap" name="EmployeeInfoViewForm">
						<html:optionsCollection property="tempEmpTypeMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                              </td>
             </tr>
             <tr>
								    <td class="row-odd" width="15%">
								    <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.employee.isTeachingStaff"/>
								    </div>
								    </td>
									<td  class="row-even" width="25%">
									 <html:radio property="tempTeachingStaff" value="1"/>Teaching&nbsp; 
									<html:radio property="tempTeachingStaff" value="0"/>Non-Teaching&nbsp;
									<html:radio property="tempTeachingStaff" value="2"/>All
									</td>
								  		<td  width="15%" height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.employee.active"/> </div></td>
               						 <td class="row-even"  width="30%">
                	 					<html:radio property="tempActive" value="1"/>Yes&nbsp; 
										 <html:radio property="tempActive" value="0"/>No
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
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35"><div align="center">
               <html:submit property="" styleClass="formbutton" value="Continue"></html:submit>
               <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
            </div></td>
            
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html:html>
