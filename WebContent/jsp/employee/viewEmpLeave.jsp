<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
	function resetFields() {
	    document.getElementById("fingerPrint").value="";
	    document.getElementById("empCode").value="";
	}
	function showData(value){
		//getFingerPrintIds("fingerPrintId",value,"mydiv",updateShowData);
		xmlHttp=GetXmlHttpObject();
		var url="AjaxRequest.do?method=getDynamicFingerPrintId&fingerPrintId="+value;
		xmlHttp.onreadystatechange=stateChanged(); 
		xmlHttp.open("GET",url,true);
		xmlHttp.send(null);
	}
	function updateShowData(req){
		updateShowData(req,"mydiv");
	}
	function stateChanged() { 
		if(xmlHttp.readyState==4 || xmlHttp.readyState=="complete"){ 
		    var showdata = xmlHttp.responseText; 
		    document.getElementById("messageBox1").innerHTML= showdata;
		    } 
		}
	function GetXmlHttpObject(){
		var xmlHttp=null;
		try {
		  xmlHttp=new XMLHttpRequest();
		 }
		catch (e) {
		 try  {
		  xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		  }
		 catch (e)  {
		  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		 }
		return xmlHttp;
		}
					
</script>

<html:form action="/employeeApplyLeave">
<html:hidden property="formName" value="employeeApplyLeaveForm" />
<c:choose>
		<c:when test="${Operation == 'viewDepEmpLeaves'}">
			<html:hidden property="method" styleId="method"
				value="getDepartmentEmpLeaves" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="getEmployeeLeaves"/>
		</c:otherwise>
	</c:choose>

<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
  <tr>
    <td class="heading"><html:link href="AdminHome.do" styleClass="Bredcrumbs"><bean:message key="knowledgepro.employee"/></html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.leave.view"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.leave.view"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"> <div id="errorMessage"><div align="right"><span class="Mandatory">*</span><span class='MandatoryMark'>mandatoryfields</span></div>
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
		  </div></td>
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
                <td width="25%" height="25" class="row-odd"><div align="right" >
                <bean:message key="knowledgepro.employee.leave.employeeId"/></div></td>
                <td width="25%" class="row-even">
                  <html:text property="fingerPrintId" styleClass="TextBox" styleId="fingerPrint" size="16" maxlength="20" name="employeeApplyLeaveForm"/>
                  <div id="messageBox1"></div>
                </td>
              <td width="25%" height="25" class="row-odd"><div align="right" >
                <bean:message key="employee.info.code"/></div></td>
              <td width="25%" class="row-even">
                  <html:text property="empCode" styleClass="TextBox" styleId="empCode" size="16" maxlength="20" name="employeeApplyLeaveForm"/>
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
            <td width="45%" height="35"><div align="right">
            <html:submit  styleClass="formbutton" value="Search"></html:submit>
            </div></td>
            <td width="2%"></td>
            <td width="53%">
            <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()"></html:button>
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
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>