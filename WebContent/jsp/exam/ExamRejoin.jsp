<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript" language="javascript">
// Functions for AJAX 
function getClasses(joingBatch) {
	getClassesByYearInMuliSelect("classMap", joingBatch, "classes", updateClasses);
   }
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "-Select-");
  }

function setClasses() {
document.getElementById("className").value = document
			.getElementById("classes").options[document
			.getElementById("classes").selectedIndex].text
   }

function resetValues(){
	document.location.href = "ExamRejoin.do?method=initRejoin";
	resetErrMsgs();
}
	
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamRejoin.do">
	<html:hidden property="formName" value="ExamRejoinForm" />
	<html:hidden property="method" styleId="method" value="ExamRejoin" />
	<html:hidden property="className" styleId="className" />
	<html:hidden property="pageType" value="1" />
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message	key="knowledgepro.exam.reJoin.rejoin" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message	key="knowledgepro.exam.reJoin.rejoin" /> </strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div>
        <div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
    
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
                <td width="23%" height="25" class="row-odd" ><div align="right"> <bean:message	key="knowledgepro.exam.reJoin.registerNo" />. :</div></td>
                <td width="20%" class="row-even" ><html:text property="regNumber" styleId="regNumber" maxlength="50" styleClass="TextBox" size="15"/></td>
                <td width="17%" class="row-even" ><strong><bean:message	key="knowledgepro.exam.reJoin.or" /></strong></td>
                <td width="18%" height="25" class="row-odd" ><div align="right"> <bean:message	key="knowledgepro.exam.reJoin.rollNo" />. :</div></td>
                <td width="22%" class="row-even" ><html:text property="rollNumber" styleId="rollNumber" maxlength="50" styleClass="TextBox" size="15"/></td>
                </tr>
                <tr>
                <td width="23%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message	key="knowledgepro.exam.reJoin.newRegNo" />. :</div></td>
                <td class="row-even" ><html:text property="newRegNumber" styleId="newRegNumber" maxlength="50" styleClass="TextBox" size="15"/></td>
                <td class="row-even" ><strong><bean:message	key="knowledgepro.exam.reJoin.or" /></strong></td>
                <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message	key="knowledgepro.exam.reJoin.newRollNo" />. :</div>                  </td>
                <td width="22%" class="row-even" ><html:text property="newRollNumber" styleId="newRollNumber" maxlength="50" styleClass="TextBox" size="15"/></td>
                </tr>
				<tr >
                
                 <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message	key="knowledgepro.exam.reJoin.joiningBatch" /> :</div></td>
                <td height="25" class="row-even" colspan="2" >
                
                
                
                <html:select property="joiningBatch" styleClass="body"
										styleId="joiningBatch" onchange="getClasses(this.value)" >
										
										
										<cms:renderYear></cms:renderYear>
									</html:select>
                
                </td>
                <td height="25"  class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message	key="knowledgepro.exam.reJoin.readmittedClass" /> :</div></td>
                <td height="25" colspan="1"  class="row-even" >
                
                
                <nested:select property="readmittedClass"  styleClass="combo"
										  styleId="classes"
										style="width:150px" onchange="setClasses()">

										<c:if
											test="${ExamRejoinForm.joiningBatch != null && ExamRejoinForm.joiningBatch != ''}">
											<c:set var="classMap"
												value="${baseActionForm.collectionMap['classMap']}" />
											<c:if test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:if>

										</c:if>

									</nested:select>
                
                
                </td>
                </tr>
                <tr>
                <td width="18%" height="25" class="row-odd" ><div align="right"><bean:message	key="knowledgepro.exam.reJoin.date" />. :</div>                  </td>
                <td width="22%" class="row-even" colspan="4"><html:text property="joiningDate" styleId="joiningDate" maxlength="10" styleClass="TextBox" size="15"/>
                
                <script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'ExamRejoinForm',
											// input name
											'controlname' :'joiningDate'
										});
									</script>
                
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><input name="submit" type="submit" class="formbutton" value="Submit" /></td>
            <td width="2%" align="center">&nbsp;</td>
            <td width="49%" align="left"><input type="reset" class="formbutton" value="Reset"  onclick="resetValues()"/> </td>
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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