
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<title>:: CMS ::</title>
<script>
function goToFirstPage() {
		document.location.href = "uncheckGenSmartCard.do?method=initUncheckGeneratedSmartCard";
	}


function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
            }
    }
}

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                  }     
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }        
}
</script>
<html:form action="/uncheckGenSmartCard">

	<html:hidden property="method" styleId="method"	value="uncheckGeneratedFlag" />
	<html:hidden property="formName" value="uncheckGenSmartCardForm" />
	<html:hidden property="pageType" value="2" />


<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.uncheckSmartCardData" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admission.uncheckSmartCardData" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        </td>
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
				 <td width="15%" height="30" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.reJoin.joiningBatch" /> :</div></td>
                  <td width="15%" height="30" class="row-even" ><bean:write name="uncheckGenSmartCardForm" property="academicYear" /></td>              
                  <td width="15%" height="26" class="row-odd"><div align="right" ><bean:message	key="knowledgepro.admission.programtype" /> :</div></td>
                <td width="20%" class="row-even" ><bean:write name="uncheckGenSmartCardForm" property="programType" /></td>
                 <td width="15%" height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.admission.program" /> </div></td>
                <td width="20%" class="row-even"  ><bean:write name="uncheckGenSmartCardForm" property="program" /></td>
                </tr>
                <tr>
                  <td height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.admission.course" /> </div></td>
                <td class="row-even"  ><bean:write name="uncheckGenSmartCardForm" property="course" /></td>
                 <td height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.registernofrom" /> </div></td>
                <td class="row-even"  ><bean:write name="uncheckGenSmartCardForm" property="regNoFrom" /></td>
                 <td height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.registernoto" /> </div></td>
                <td class="row-even"  ><bean:write name="uncheckGenSmartCardForm" property="regNoTo" /></td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="20%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.attendance.classname" /></div></td>
											<td width="20%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.registerNo" /></td>
											<td width="40%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /> </td>
											<td width="20%" height="25" align="left" class="row-odd">
											<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> <bean:message
												key="knowledgepro.admission.selectStudents" /></td>
										</tr>
										
										
										
										<c:set var="temp" value="0" />

										<nested:iterate name="uncheckGenSmartCardForm"
											property="generatedStudentList" id="generatedStudentList"
											type="com.kp.cms.to.admin.StudentTO">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td  height="25" class="row-even"
															align=left><bean:write name="generatedStudentList"
															property="className" /></td>
														<td height="25" class="row-even"
															align="left"><bean:write name="generatedStudentList"
															property="registerNo" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="generatedStudentList"
															property="studentName" /></td>
														<td  height="25" class="row-even"
															align="center">													
														<nested:checkbox property="checked1" onclick="unCheckSelectAll()"></nested:checkbox></td>
													
													<c:set var="temp" value="1" /></tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td  height="25" class="row-white"
															align="left"><bean:write name="generatedStudentList"
															property="className" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="generatedStudentList"
															property="registerNo" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="generatedStudentList"
															property="studentName" /></td>
														<td  height="25" class="row-white"
															align="center">
															<nested:checkbox property="checked1" onclick="unCheckSelectAll()"></nested:checkbox></td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>


									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="47%" height="35"><div align="right">
              <html:submit value="Submit" styleClass="formbutton"></html:submit>
            </div></td>
            <td width="1%"></td>
          
            <td width="1%"></td>
            <td width="46%">
            
            <input type="Reset"
								class="formbutton" value="Cancel" onclick="goToFirstPage()"/>
           </td>
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

