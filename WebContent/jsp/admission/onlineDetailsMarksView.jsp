<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<head>
<script src="js/admission/confirmDetailMark.js" type="text/javascript"></script>
</head>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<script type="text/javascript">

var hook = true;


function appendMethodOnBrowserClose(){
	hook = false;
}
$(function() {
	 $("a,.formbutton").click(function(){
		hook =false;
	  });
});

window.onbeforeunload = confirmExit;
  function confirmExit()
  {
	  if(hook){
		  hook =false;
		}else{
			hook =true;
		}
  }

$(document).ready(function() {	
	
	var ctrlKeyDown = false;

	$(document).on("keydown", keydown);
    $(document).on("keyup", keyup);
   

    function keydown(e) { 
        if ((e.which || e.keyCode) == 116 || ((e.which || e.keyCode) == 82 && ctrlKeyDown)) {
            // Pressing F5 or Ctrl+R
            e.preventDefault();
        } else if ((e.which || e.keyCode) == 17) {
            // Pressing  only Ctrl
            ctrlKeyDown = true;
        }
    };

    function click(e){
    	e.preventDefault();
    };

    function keyup(e){
        // Key up Ctrl
        if ((e.which || e.keyCode) == 17) 
            ctrlKeyDown = false;
    };
    
    });
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

<table width="80%" style="background-color: #F0F8FF" align="center">

  <tr>
    <td> </td>
  </tr>
  <tr><td  align="left"><div id="errorMessage"><html:errors/></div></td></tr>
  <tr>
    <td>     <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" >
                   
                   <table width="100%" cellspacing="1" cellpadding="2">
                    
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22">
                      
                       
                       </td>
                     </tr>
                  <tr>
						<td class="row-odd" width="10%"><bean:message key="admissionForm.detailmark.slno.label"/></td>
						
						<td class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admin.subject.subject.name.disp"/> </div></td>
 						<td height="25" class="row-odd" width="22%"><div align="center"><bean:message key="admissionForm.detailmark.obtain.label"/></div></td>
						<td height="25" class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admission.maxMark"/></div></td>
 						
					</tr>
					<%for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SUBJECTS;i++) {
						String propertyName="detailMark.subject"+i;
						String dynaMandatory="detailMark.subject"+i+"Mandatory";
						String propertyDetailedName="detailMark.detailedSubjects"+i+".id";
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
						<logic:notEmpty property="<%=propertyName %>" name="onlineApplicationForm">
								<logic:equal value="true" property="<%=dynaMandatory %>" name="onlineApplicationForm">
							 	<span class="Mandatory">*</span><bean:write property="<%=propertyName %>" name="onlineApplicationForm" ></bean:write>
								</logic:equal>
								<logic:equal value="false" property="<%=dynaMandatory %>" name="onlineApplicationForm">
									<html:text readonly="true" property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</logic:equal>
							</logic:notEmpty>
							<logic:empty property="<%=propertyName %>" name="onlineApplicationForm">
							 	<html:text readonly="true" property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
							</logic:empty>
						</td>
						<td class="row-even" ><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						 <td class="row-even" ><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
					</tr>
					<%} %>
					
					<tr>
					   
						  
						<td class="row-odd" colspan="2" ><div align="right">TOTAL MARKS / TOTAL GRADEPOINTS:&nbsp;</div></td>
						
						<td class="row-even" align="center"><html:text property="detailMark.totalObtainedMarks" size="7" maxlength="7"  styleId="ObtainedMark" readonly="true"></html:text>
						</td>
						<td class="row-even" align="center"><html:text property="detailMark.totalMarks" styleId="totalMark" size="7" maxlength="7" readonly="true"></html:text>
						</td>
						
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
                        
                    </div></td>
                    <td width="2%"></td>
                    <td width="53%"><html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitConfirmCancelButtonView()'></html:button></td>
                  </tr>
                </table>
                            </td>
                 </tr>
                 
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
           </td>
  </tr>
</table>
</body>
</html:html>
