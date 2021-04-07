<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function updateConfigReport(){	
	document.columnForm.method.value = "updateConfigReport";
	document.columnForm.submit();
}
function resetMessages() {
	resetErrMsgs();
}
function initColumnReport() {
	document.location.href = "columnSubmitReport.do?method=initConfigReport";
}	
function disablePosition(count){
	var positionid="position_"+count;
	document.getElementById(positionid).value = "";
	document.getElementById(positionid).disabled = true;
}
function enablePosition(count){
	var positionid="position_"+count;
	document.getElementById(positionid).disabled = false;
}
</script>
<html:form action="/columnSubmitReport" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="columnForm"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports"/> &gt;&gt; <bean:message key="knowledgepro.reports.details"/></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.reports.details"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div><div id="errorMessage"><html:errors/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td  valign="top" class="news">
		<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td  background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>			
			<tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top" class="heading"><div align="left"><nested:write property="reportTO.reportName" name="columnForm"/></div>
           	</td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>			
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" height="53" border="0" cellpadding="0" cellspacing="1" align="center">
            	<tr class="row-odd">
                  <td  height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.reports.columnName"/>
                    </div></td>
                  <td class="row-odd">
                    <div align="left"><bean:message key="knowledgepro.showcolumn"/>
                    </div></td>
				<td class="row-odd"><div align="left"><bean:message key="knowledgepro.usermanagement.module.position"/>
					</div></td>
                  </tr>
                <nested:iterate property="reportTO.reportNameSummaryList" name="columnForm" indexId="count" id="columnId">
                <tr class="row-even">
                  <td  height="25" class="row-even"><div align="left">
						<nested:write property="columnName" name="columnId"/>
                    </div></td>
                  <td class="row-even">
                  <%		String dynaCountId="count_"+count;
							String dynaPositionId="position_"+count;
							String dynaEnableMethod="enablePosition('"+count+"')";
							String dynaDisableMethod="disablePosition('"+count+"')";
						%>
                    <div align="left">
                    	<bean:define id="showcolumnvalue" property="showColumn" name="columnId"></bean:define>
                    	<input type="hidden" name="dynacount" id="count_<c:out value='${count}'/>" value="<%=showcolumnvalue %>" />
                    	<nested:radio property="showColumn"  value="true" styleId="radiotrue" onchange="<%=dynaEnableMethod %>"> <bean:message key="knowledgepro.true"/></nested:radio>
                    	<nested:radio property="showColumn" value="false" styleId="radiofalse" onchange="<%=dynaDisableMethod %>"> <bean:message key="knowledgepro.false"/></nested:radio>
                    </div></td>
				
					<td class="row-even"><div align="left">
						
					
						<nested:text property="position"  size="3" maxlength="3" styleId="<%=dynaPositionId %>"/>
					</div>
					</td>
					<script type="text/javascript">
						var condition = document.getElementById('<%= dynaCountId%>').value;
						if(condition == "false"){
							document.getElementById('<%=dynaPositionId %>').disabled = true;	
							document.getElementById('<%=dynaPositionId %>').value = "";
						}
					</script>
                  </tr>
                </nested:iterate>
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
                <html:button property="" onclick="updateConfigReport()" styleClass="formbutton" value="Submit"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Back" onclick="initColumnReport()"></html:button></div></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>     
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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