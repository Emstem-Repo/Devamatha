 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.List"%>

<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function putSubjectName(id,value) {
	var propertyName = "detailMark.subject"+id;
	var selectBox = "detailMark.detailedSubjects"+id;
	if(value.length == 0){
		document.getElementById(propertyName).value="";
	}else if(value == '-1') {
		document.getElementById(propertyName).value="";
		document.getElementById(propertyName).readOnly = false;
	} else {
		document.getElementById(propertyName).value=document.getElementById('detailMark.detailedSubjects'+id).options[document.getElementById('detailMark.detailedSubjects'+id).selectedIndex].text;
		document.getElementById(propertyName).readOnly = true;
	}		
}

function savePreference()  {
	
	var count=document.getElementById("preListCount").value;
	var i;
	var sum=0;
	var tot=0;
	var pretot=0;
	for(i=0;i<count;i++){
		var pre=document.getElementById("prefNo"+i).value;
		if(pre!=null && pre!=""){
			tot++;
			sum=parseInt(sum)+parseInt(tot);
			pretot=parseInt(pretot)+parseInt(pre);
		}
		
	}
	if(sum==pretot){
		document.applicationEditForm.submit();
	}else{
		alert('Please Enter Correct Preferences');
		document.getElementById("method").value="getPreferences";
		document.applicationEditForm.submit();
	}
}

</script>
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
<html:form action="/admissionFormSubmit">

<html:hidden property="method" styleId="method"	value="savePreferences" />
<html:hidden property="pageType" value="4"/>
<html:hidden property="formName" value="applicationEditForm"/>
<html:hidden property="courseId" value="applicationEditForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/> <span class="Bredcrumbs">&gt;&gt; Preferences &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> submit your preferences </strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" ><table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22">
                      
                        <h3><font color="red">Enter your preference as 2, 3,4.....<br>Your first preference is <bean:write name="applicationEditForm" property="courseName1"/></br></font></h3>
                       </td>
                     </tr>
                     
                     <tr>
						<td class="row-odd" ><div align="center"><bean:message key="admissionForm.detailmark.slno.label"/></div></td>
						
						<td class="row-odd" ><div align="center">course </div></td>
 						<td height="25" class="row-odd" ><div align="center">Preference No</div></td>
						
					</tr>
									
							
								<%int preList=0; %>
									
									<nested:notEmpty name="applicationEditForm" property="prefcourses">
								<nested:iterate id="preForm" name="applicationEditForm" property="prefcourses" indexId="count">
									
									<% 
									preList++;
									%>
									
										<tr> 
											<td  height="25" class="row-even">
											<div align="center"><c:out value="${count+1}" /></div>
											</td>
												<c:choose>
											
										<c:when test="${applicationEditForm.courseName1==preForm.name}">
										<td class="row-even"><div align="center"><bean:write name="preForm" property="name"/></div> </td>
										<td class="row-even" ><div align="center"><nested:text property="prefNo" value="1" size="6" styleId='<%="prefNo"+count%>' maxlength="6" readonly="true" ></nested:text></div></td>
						 </c:when>
						 <c:otherwise>
						 
						 <td class="row-even"><div align="center"><bean:write name="preForm" property="name"/></div> </td>
										<td class="row-even" ><div align="center"><nested:text property="prefNo" size="6" maxlength="6" styleId='<%="prefNo"+count%>' onkeypress="return isNumberKey(event)"></nested:text></div></td>
						</c:otherwise>
										</c:choose>				
									</tr>
												
										
										
										</nested:iterate>
										</nested:notEmpty>
										
                  <tr>
										
                    <td>
                    <input  type="hidden" value="<%=preList%>" id="preListCount">
                    </td>
                    <td ><div align="center"><input name="button2" type="submit"
								class="formbutton" value="Submit" onclick="savePreference()"/>
                    <html:button property=""  styleClass="formbutton" value="Reset" onclick=''></html:button><html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitConfirmCancelButton()'></html:button></div></td>
                 
                    <td></td></tr>
									
							
                   </td>
                 </tr>
                 
                 </table>		
                 
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>