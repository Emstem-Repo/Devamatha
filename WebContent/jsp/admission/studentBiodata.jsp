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
<script language="JavaScript" src="js/admission/admissionBiodata.js"></script>
<script language="JavaScript" src="js/admission/studentBiodata.js"></script>
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
<html:form action="/studentBiodata" enctype="multipart/form-data" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="13"/>
<html:hidden property="formName" value="StudentBiodataForm"/>
<%
	String submitjsmethod=null;
%>
<logic:notEmpty name="transactionstatus" scope="request">
	<%
		submitjsmethod="#";
	%>
</logic:notEmpty>
<logic:empty name="transactionstatus" scope="request">
	<%
		submitjsmethod="submitMe('initStudentCreationDetail')";
	%>
</logic:empty>
	<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="admissionForm.studentadd.main.label" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="954" background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="admissionForm.studentadd.main.label" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		
		
        

      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
<div id="errorMessage">
      						<html:errors/><FONT color="green">
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
                    <td width="10%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                    <td width="20%" height="25" class="row-even"> <input type="hidden" id="programType" name="programType" value='<bean:write name="StudentBiodataForm" property="programTypeId"/>'/>
                <html:select styleId="programTypeId"  property="programTypeId" styleClass="combo" onchange="getPrograms(this.value,'progPref1')">
									<html:option value="">- Select -</html:option>
									<cms:renderProgramTypes></cms:renderProgramTypes>
				</html:select></td>
                    <td width="10%" class="row-odd"><div align="right" class="row-odd"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
                    <td width="20%" class="row-even"><html:select property="programId" styleClass="combo" styleId="progPref1" onchange="getCourse(this.value,'coursePref1')">
									<html:option value="">- Select -</html:option>
									<c:if test="${StudentBiodataForm.programTypeId != null && StudentBiodataForm.programTypeId != ''}">
			                           					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
		                            		    	 	<c:if test="${programMap != null}">
		                            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select></td>
                    <td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course"/>:</div></td>
                    <td width="30%" class="row-even"><html:select property="courseId" styleClass="comboLarge" styleId="coursePref1">
									<html:option value="">- Select -</html:option>
									<c:if test="${StudentBiodataForm.programId != null && StudentBiodataForm.programId != ''}">
                         						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
                         		    			<c:if test="${courseMap != null}">
                         		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
                         		    			</c:if>	 
		                           </c:if>
							</html:select></td>
                  </tr>
                   <tr class="row-white">
                    <td width="10%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/>:</div></td>
                    <td width="20%" class="row-even"> 
                    <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="StudentBiodataForm" property="academicYear"/>"/>
                <html:select styleId="academicYear"  property="academicYear" name="StudentBiodataForm" styleClass="combo">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
				</html:select></td>
                    <td width="10%" class="row-odd"></td>
                    <td width="20%" class="row-even"></td>
                    <td width="10%" class="row-odd"></td>
                    <td width="20%" class="row-even"></td>
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
            <td width="49%" height="35"><div align="right">
               <html:button property="" onclick='<%=submitjsmethod %>' styleClass="formbutton" value="Continue"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></div></td>
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
<script type="text/javascript">

var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("academicYear").value = year;
}
</script>