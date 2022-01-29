<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getFaculties(department) {
		getEmployeesByDepartment("employeeMap", department, "employee", updateFaculties);
	}
	function updateFaculties(req) {
		updateOptionsFromMap(req, "employee", "- Select -");
	}
	function submitPrincipalAppraisal(){
		document.getElementById("method").value = "submitPrincipalAppraisal";
		document.principalAppraisalForm.submit();
	}
	function resetMessages() {
		document.getElementById("method").value = "initPrincipalAppraisal";
		document.principalAppraisalForm.submit();
	}	
</script>
<html:form action="/principalAppraisal" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="principalAppraisalForm" />
<html:hidden property="pageType" value="1" />
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/><span class="Bredcrumbs">&gt;&gt;
    <bean:message key="knowledgepro.employee.principal.appraisal" /></span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.principal.appraisal" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
        <div align="right" class="mandatoryfield"><bean:message
		key="knowledgepro.mandatoryfields" /></div></td>
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
                <td width="50%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.employee.department.col"/>  </div></td>
                <td width="50%" class="row-even">
                <html:select property="departmentId" styleId="departmentId" styleClass="comboLarge" onchange="getFaculties(this.value)">
                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                    <logic:notEmpty name="principalAppraisalForm" property="departmentList">
					<html:optionsCollection property="departmentList" name="principalAppraisalForm" label="name" value="id" />
					</logic:notEmpty>
                  </html:select>
      			</td>
              </tr>
              <tr >
                <td width="50%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.employee.faculty.col"/></div></td>
                <td width="50%" class="row-even"> 
                <input type="hidden"
				name="emp" id="emp"
				value='<bean:write name="principalAppraisalForm" property="employeeId"/>' />              
                <html:select
					property="employeeId" styleClass="comboLarge" styleId="employee">
					<html:option value="">
					<bean:message key="knowledgepro.admin.select" /></html:option>
					<c:choose>
					<c:when test="${employeeMap != null}">
					<html:optionsCollection name="employeeMap" label="value"
					value="key" />
					</c:when>	
					<c:otherwise>
					<c:if
					test="${principalAppraisalForm.departmentId != null && principalAppraisalForm.departmentId != ''}">
					<c:set var="employeeMap"
						value="${baseActionForm.collectionMap['employeeMap']}" />
					<c:if test="${employeeMap != null}">
					<html:optionsCollection name="employeeMap" label="value"
					value="key" />
					</c:if>
				</c:if>
				</c:otherwise>				
					</c:choose>
					</html:select>               
                </td>
              </tr>
              <tr>
              <td class="row-odd" height="25"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.employee.attribute"/></div></td>
              <td class="row-odd"><span class="Mandatory">*</span> <bean:message key="knowledgepro.employee.assign.the.value"/></td>
              </tr>             
              <logic:notEmpty name="principalAppraisalForm" property="attriButeList">
              <nested:iterate id="att" name="principalAppraisalForm" property="attriButeList">
              <tr>
              <td class="row-even" height="25"><div align="right"><nested:write name="att" property="name"/> </div></td>
              <td class="row-even">
              <nested:radio property="value" value="1"></nested:radio>
				<bean:message key="knowledgepro.scheme.minrange"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				<nested:radio property="value" value="2"></nested:radio>
				<bean:message key="knowledgepro.employee.two"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<nested:radio property="value" value="3"></nested:radio>
				<bean:message key="knowledgepro.admission.maxcandidateworkexp"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<nested:radio property="value" value="4"></nested:radio>
				<bean:message key="knowledgepro.employee.four"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<nested:radio property="value" value="5"></nested:radio>
				<bean:message key="knowledgepro.employee.five"/>
				</td>
              </tr>
              </nested:iterate>
              </logic:notEmpty>      
              <tr>
                <td width="48%"  class="row-odd" ><div align="right"> <bean:message key="knowledgepro.employee.recommendation.col"/> </div></td>
                <td class="row-even" colspan="5"><span class="row-white">
                <html:textarea property="recommendation" cols="75" rows="8" styleId="recommendation"></html:textarea>
                </span></td>
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
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" value="Submit" styleClass="formbutton" onclick="submitPrincipalAppraisal()"></html:button></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:button property="" value="Reset" styleClass="formbutton" onclick="resetMessages()"></html:button></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
<script type="text/javascript">
	var empId = document.getElementById("emp").value;
	if (empId != null && empId.length != 0) {
		document.getElementById("employee").value = empId;
	}
</script>