<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}

	function searchYearMonthWise(count){
		var examMonth = document.getElementById("examMonth_"+count).value;
		if(count == 0){
			getYearByMonthWise(examMonth, updateYearMap);
		}
		if(count == 1){
			getYearByMonthWise(examMonth, updateYearMap1);
		}
	}
	function updateYearMap(req){
		
		if(document.getElementById("examYear_0") != null){
			updateOptionsFromMap(req,"examYear_0","-Select-");
		}
	}

	function updateYearMap1(req){
		if(document.getElementById("examYear_1") != null){
			updateOptionsFromMap(req,"examYear_1","-Select-");
		}
	}
</script>
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

<body>
<html:form action="/admissionFormSubmit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="8"/>
<html:hidden property="formName" value="admissionFormForm"/>
<html:hidden property="onlineApply" value="true"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>  &gt;&gt; <bean:message key="knowledgepro.applicationform.prereq.label"/></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.applicationform.prereq.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div><div id="errorMessage"><html:errors/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
<bean:define id="requisites" property="coursePrerequisites" name="admissionFormForm" type="java.util.ArrayList"></bean:define>
<nested:iterate property="coursePrerequisites" name="admissionFormForm" indexId="count" id="prereq">
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>

        <td  valign="top" class="news">

		<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td  background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
		
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" height="53" border="0" cellpadding="0" cellspacing="1" align="center">
                <tr class="row-white">
                  <td  height="25" class="row-odd"><div align="right"><span class="row-odd"><span class='Mandatory'>*</span><nested:write property="prerequisiteTO.name"/>:</span></div></td>
                  <td class="row-odd">
                    <div align="left">
						<nested:text property="userMark" styleClass="textboxMediam" maxlength="6" onblur="checkForEmpty(this);isValidNumber(this)" onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)"></nested:text>
                    </div></td>
				
					<td class="row-odd"><div align="right"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.roll.label"/> </div></td>
					<td class="row-odd"><div align="left">
					<nested:text property="rollNo" styleClass="textboxMediam" maxlength="20"></nested:text>
					</div></td>
                  </tr>
                <tr class="row-white">
                  <td height="25" class="row-even" align="right"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.passmonth.label"/></td>
                  <td class="row-even"><div align="left">
                  <%String monthId="examMonth_"+count; %>
                  <%String method = "searchYearMonthWise("+count+")"; %>
                  <nested:select property="examMonth" styleId='<%=monthId %>' styleClass="comboMedium" onchange="<%=method %>">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="monthMap" name="admissionFormForm">
						<html:optionsCollection property="monthMap" label="value" value="key"/>
					 </logic:notEmpty>
				</nested:select>
				</div></td>
			<td height="25" class="row-even" align="right"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.passyear.label"/></td>
            <td class="row-even"><div align="left">
			<%String tempyearId="tempyear_"+count; %>
			<%String examYearId="examYear_"+count; %>
	        <input type="hidden" id='<%=tempyearId %>' name='<%=tempyearId %>' value="<nested:write property="examYear" name="prereq" />" />
				<nested:select property="examYear" styleId='<%=examYearId %>' styleClass="comboMedium" >
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="yearMap" name="admissionFormForm">
						<html:optionsCollection property="yearMap" label="value" value="value"/>
					 </logic:notEmpty>
				</nested:select>
				<script type="text/javascript">
					var d = new Date();
					var currentYear = d.getFullYear();
					document.getElementById("year_<c:out value='${count}'/>").value = currentYear;
					var year = document.getElementById("tempyear_<c:out value='${count}'/>").value;
					if (year.length != 0) {
						document.getElementById("year_<c:out value='${count}'/>").value = year;
					}
				</script>
				  </div></td>
				<td height="25" class="row-even"></td>
				<td height="25" class="row-even"></td>
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
<%
	List prerequisites=(ArrayList)requisites;
	if(prerequisites.size()>1 && count!=(prerequisites.size()-1)){
%>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="25" class="news"><div align="center" class="heading">OR</div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
<%
	}
%>
</nested:iterate>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
                <html:button property="" onclick="submitAdmissionForm('submitPreRequisiteApply')" styleClass="formbutton" value="Continue"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="submitAdmissionForm('initPrerequisiteApply')"></html:button></div></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>

</html:html>