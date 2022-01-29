<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
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
<html:form action="/studentEdit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="6"/>
<html:hidden property="formName" value="studentEditForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Admission <span class="Bredcrumbs">&gt;&gt; Detailed Marks Edit &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.detailmark.main.label"/></strong></div></td>
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
                      
                       
                       </td>
                     </tr>
                     <tr>
						<td class="row-odd" width="10%">Sl No</td>
						
						<td class="row-odd" width="22%"><div align="center">Subject Name:</div></td>
 						<td height="25" class="row-odd" width="22%"><div align="center">Obtained Marks:</div></td>
						<td height="25" class="row-odd" width="22%"><div align="center">Total Marks:</div></td>
 						
					</tr>
					<%for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SUBJECTS;i++) {
						String propertyName="detailMark.subject"+i;
						String propertyDetailedName="detailMark.detailedSubjects"+i+".id";
						String dynaMandatory="detailMark.subject"+i+"Mandatory";
						String propertyDetailedId="detailMark.detailedSubjects"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						String dynajs="updatetotalMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						String dynajs2="updateObtainMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
						String method = "putSubjectName('"+i+"',this.value)";
					%>
                     <tr>
                         <td  class="row-even" > <%=i %>
                         </td>
                   
						 <td  class="row-even" >
							 
						 	<logic:notEmpty property="<%=propertyName %>" name="studentEditForm">
							 	<logic:equal value="true" property="<%=dynaMandatory %>" name="studentEditForm">
							 	<span class="Mandatory">*</span><bean:write property="<%=propertyName %>" name="studentEditForm" ></bean:write>
								</logic:equal>
								<logic:equal value="false" property="<%=dynaMandatory %>" name="studentEditForm">
									<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="studentEditForm" size="25" maxlength="100"></html:text>
								</logic:equal>
							</logic:notEmpty>
							<logic:empty property="<%=propertyName %>" name="studentEditForm">
							 	<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="studentEditForm" size="25" maxlength="100"></html:text>
							</logic:empty>
						</td>
						<td class="row-even" ><html:text property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						 <td class="row-even" ><html:text property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
					</tr>
					<%} %>
					
					<tr>
					 
						<td class="row-odd" >Total Obtained Mark:</td>
						<td class="row-even" ><html:text property="detailMark.totalObtainedMarks" size="7" maxlength="7"  styleId="ObtainedMark" readonly="true"></html:text></td>
						
						<td class="row-odd" >Total Mark:</td>
						<td class="row-even" ><html:text property="detailMark.totalMarks" styleId="totalMark" size="7" maxlength="7" readonly="true"></html:text></td>
						
					</tr>
                   </table></td>
                 </tr>
                 
                 <tr>
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
			%>
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitMe('submitDetailMarkEdit')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><html:button property=""  styleClass="formbutton" value="Reset" onclick='<%=resetmethod %>'></html:button></td>
                    <td width="53%"><html:button property=""  styleClass="formbutton" value="Cancel" onclick="cancelMarkWindow()"></html:button></td>
                  </tr>
                </table>
                            </td>
                 </tr>
                 
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