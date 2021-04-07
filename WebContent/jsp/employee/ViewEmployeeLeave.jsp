<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">
function getEmployeeLeavDetails(){
	var year=document.getElementById("academicYear").value;
	var employee=document.getElementById("employeeName").value;
	var eCodeName;
	if(document.getElementById("eCodeName_1").checked){
		 eCodeName = document.getElementById("eCodeName_1").value
	}else
	{
		eCodeName = document.getElementById("eCodeName_2").value
	}
	document.location.href = "viewEmployeeLeave.do?method=viewEmployeeLeaveDetails&academicYear="+year+"&employeeName="+employee+"&eCodeName="+eCodeName;
	
}
function getECodeName(eCodeName){

	getEmployeeCodeName("listOfEmployee",eCodeName,"employeeName",updateToEmployee);
	
}

function updateToEmployee(req){
		updateOptionsFromMap(req, "employeeName", "- Select -");
}
</script>
<html:form action="/viewEmployeeLeave" method="post">
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="ViewEmployeeLeaveForm" />

<table width="100%" border="0">
  <tr>
    <td><span class="heading">View Employee  Leave</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">View Employee  Leave</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even">
            <table width="100%" cellspacing="1" cellpadding="2">
            <tr>

									<td height="25" colspan="2" class="row-even">
									<div align="center">
									<input type="radio" name="eCodeName" id="eCodeName_1" value="eCode" checked="checked" onclick="getECodeName(this.value)" />
									
									Employee Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="eCodeName" id="eCodeName_2" value="eName" onclick="getECodeName(this.value)"/> 
										Employee
									Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
									</td>


								</tr>
              <tr>
              	<td width="20%" class="row-odd"><div align="right">Employee:</div></td>
                <td width="25%" class="row-even">
                   <html:select  property="employeeName" styleId="employeeName" styleClass="combo" style="width:200px"> 
                     	<html:option value=""> 	<bean:message key="knowledgepro.admin.select" />
						</html:option>
						<logic:notEmpty name="ViewEmployeeLeaveForm" property="listOfEmployee">
							<html:optionsCollection property="listOfEmployee"
								name="ViewEmployeeLeaveForm" label="value"
								value="key" />
						</logic:notEmpty>
					</html:select>
                   </td>
                <td width="20%"  class="row-odd" ><div align="right"> Year:</div></td>
                <td width="20%" class="row-even">
                     <html:select  property="academicYear" styleId="academicYear" styleClass="combo" > 
                     	<cms:renderYear></cms:renderYear>
					</html:select>
					&nbsp;&nbsp;<img src="images/view_o.gif" border="0" onclick="getEmployeeLeavDetails()" style="cursor: pointer"/>
					</td>
              </tr>
            </table></td>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>