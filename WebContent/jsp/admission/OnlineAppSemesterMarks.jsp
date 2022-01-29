<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<script type="text/javascript">



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

function checkAlertMarksObtainedBySemisterWise(count){
	$.confirm({
 		'message'	: 'Please enter the obtained marks.In case if the marks are in credit or CGPA, convert it into percentage and enter',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					document.getElementById("marksObtained"+count).focus();
					$.confirm.hide();
				}
			}
		}
 	});
 	
}

function checkAlertMaxMarksSemisterWise(count){
	$.confirm({
 		'message'	: 'The Max marks of all subjects should be entered here. Eg: If there are 5 subjects and each subject is out of 100, then enter 500.',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					document.getElementById("maxMarks"+count).focus();
					$.confirm.hide();
				}
			}
		}
 	});
 	
}


function checkAlertMarksObtainedBySemisterWiseWithLanguage(count){
	$.confirm({
 		'message'	: 'Please enter the obtained marks.In case if the marks are in credit or CGPA, convert it into percentage and enter',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					document.getElementById("marksObtained_languagewise"+count).focus();
					$.confirm.hide();
				}
			}
		}
 	});
 	
}

function checkAlertMaxMarksSemisterWiseWithLanguage(count){
	$.confirm({
 		'message'	: 'The Max marks of all subjects should be entered here. Eg: If there are 5 subjects and each subject is out of 100, then enter 500.',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					document.getElementById("maxMarks_languagewise"+count).focus();
					$.confirm.hide();
				}
			}
		}
 	});
 	
}
</script>
<body>
<html:hidden property="pageType" value="11"/>
<table width="98%" border="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.semestermark.main.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%"  border="0" cellpadding="0" cellspacing="0">
             <c:if test="${onlineApplicationForm.isLanguageWiseMarks == 'false'}">
			 	<tr>
			 		<td colspan="4">
			 			<font color="red">Note &nbsp;&nbsp;:&nbsp;&nbsp;Languages not to be considered</font>
			 		</td>
			 	</tr>
			 </c:if>
             <tr >
	            <td height="25" class="row-odds" ><div align="center"><bean:message key="admissionForm.edit.semestermarkedit.semno.label"/> </div></td>
	            <td height="25" class="row-odds" ><bean:message key="knowledgepro.admission.semestername"/></td>
	            <c:if test="${onlineApplicationForm.isLanguageWiseMarks == 'true'}">
					<td height="25" class="row-odds" ><div align="left"><bean:message key="admissionForm.semmark.withlangobtain.label"/></div></td>
					<td height="25" class="row-odds" ><div align="left"><bean:message key="admissionForm.semmark.withlangtotal.label"/></div></td>
 					
				</c:if>
				 <c:choose>
				 	<c:when test="${onlineApplicationForm.isLanguageWiseMarks == 'true'}">
				 		<td height="25" class="row-odds" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangobtain.label"/></div></td>
						<td height="25" class="row-odds" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangtotal.label"/></div></td>
				 	</c:when>
				 	<c:otherwise>
				 		<td height="25" class="row-odds" ><div align="left"><bean:message key="admissionForm.semmark.obtain.label"/></div></td>
					<td height="25" class="row-odds" ><div align="left"><b><bean:message key="admissionForm.semmark.total.label"/></b></div></td>
				 	</c:otherwise>
				 </c:choose>
            </tr>
              <nested:iterate property="semesterList" id="semId"  type="com.kp.cms.to.admin.ApplicantMarkDetailsTO" name="onlineApplicationForm" indexId="count">
				<%
					String dynaStyle="";
					if(count%2==0)
						dynaStyle="row-even1";
					else
						dynaStyle="row-white";
					String obtainedMarkWithLanguageStyleID = "marksObtained_languagewise" + count.toString();
					String totalMarkWithLanguageStyleID = "maxMarks_languagewise" + count.toString();
					String obtainedMarkWithOutLanguageStyleID = "marksObtained" + count.toString();
					String totalMarkWithOutLanguageStyleID = "maxMarks" + count.toString();
					String calc= "calctotalobtainedMarkWithLanguage(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalMarkWithLanguage = "calctotaltotalMarkWithLanguage(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalobtainedMarkWithoutLan = "calctotalobtainedMarkWithoutLan(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalMarkWithoutLan = "calctotalMarkWithoutLan(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String checkAlertMarksObtainedBySemisterWise="checkAlertMarksObtainedBySemisterWise("+count+")";
					String checkAlertMaxMarksSemisterWise="checkAlertMaxMarksSemisterWise("+count+")";
					
					String checkAlertMarksObtainedBySemisterWiseWithLanguage="checkAlertMarksObtainedBySemisterWiseWithLanguage("+count+")";
					String checkAlertMaxMarksSemisterWiseWithLanguage="checkAlertMaxMarksSemisterWiseWithLanguage("+count+")";
				
				%>
			
			 <tr >
	            <td class="<%=dynaStyle %>"><div align="center"><nested:write property="semesterNo" ></nested:write> </div></td>
	            <td class="<%=dynaStyle %>" ><div align="left"><nested:text property="semesterName" size="10" maxlength="50" ></nested:text></div> </td>
	            <c:if test="${onlineApplicationForm.isLanguageWiseMarks == 'true'}">
	            	<td class="<%=dynaStyle %>"><div align="left"><nested:text property="marksObtained_languagewise" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=obtainedMarkWithLanguageStyleID%>' onblur='<%=calc%>' onfocus="<%=checkAlertMarksObtainedBySemisterWiseWithLanguage%>" onclick="<%=checkAlertMarksObtainedBySemisterWiseWithLanguage%>"  ></nested:text> </div></td>
					<td class="<%=dynaStyle %>"><div align="left"><nested:text property="maxMarks_languagewise" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=totalMarkWithLanguageStyleID%>' onblur='<%=calctotalMarkWithLanguage%>' onfocus="<%=checkAlertMaxMarksSemisterWiseWithLanguage%>" onclick="<%=checkAlertMaxMarksSemisterWiseWithLanguage%>" ></nested:text> </div></td>
					
				</c:if>
				<td class="<%=dynaStyle %>" ><div align="left"><nested:text property="marksObtained" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=obtainedMarkWithOutLanguageStyleID%>' onblur='<%=calctotalobtainedMarkWithoutLan%>' onfocus="<%=checkAlertMarksObtainedBySemisterWise%>" onclick="<%=checkAlertMarksObtainedBySemisterWise%>" ></nested:text> </div></td>
				<td class="<%=dynaStyle %>" ><div align="left"><nested:text property="maxMarks" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=totalMarkWithOutLanguageStyleID%>' onblur="<%=calctotalMarkWithoutLan%>" onfocus="<%=checkAlertMaxMarksSemisterWise%>" onclick="<%=checkAlertMaxMarksSemisterWise%>" ></nested:text> </div></td>
	            
            </tr>
				
			</nested:iterate>
				<tr>
						<td class="row-odd"><div align="center"><b><bean:message key="knowledgepro.admin.subject.total.marks.disp"/></b></div></td>
						<td class="row-even1"><div align="left">&nbsp;</div></td>
						<c:if test="${onlineApplicationForm.isLanguageWiseMarks == 'true'}">
							<td class="row-even1"><div align="left"><b><html:text property="totalobtainedMarkWithLanguage" styleId="totalobtainedMarkWithLanguage" size="7" maxlength="7" readonly="true"></html:text></b></div></td>
							<td class="row-even1"><div align="left"><b><html:text property="totalMarkWithLanguage" styleId="totalMarkWithLanguage" size="7" maxlength="7" readonly="true"></html:text></b></div></td>
						</c:if>
						<td class="row-even1"><div align="left"><b><html:text property="totalobtainedMarkWithoutLan" size="7" maxlength="7"  styleId="totalobtainedMarkWithoutLan" readonly="true"></html:text></b></div></td>
						<td class="row-even1"><div align="left"><b><html:text property="totalMarkWithoutLan" styleId="totalMarkWithoutLan" size="7" maxlength="7" readonly="true"></html:text></b></div></td>
						
					</tr>
		         <tr>
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
			%>
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitAdmissionForm('submitSemesterConfirmMark')" styleClass="buttons" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><div align="center"><html:button property=""  styleClass="buttons" value="Reset" onclick='resetFieldAndErrMsgs()'></html:button></div></td>
                    <td width="53%"><div align="left"><html:button property=""  styleClass="buttons" value="Cancel" onclick='submitConfirmCancelButton()'></html:button></div></td>
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
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
    </td>
    </tr>
</table>
</body>