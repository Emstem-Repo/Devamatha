<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<script type="text/javascript">

function moveinid()
{
	var sda = document.getElementById('selectColumn');
	var sda1 = document.getElementById('unselectColumn');
	var len = sda1.length;
	
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			len--;
			j--;
			if(j<0){
				document.getElementById("moveOut").disabled = true;
				document.getElementById("moveIn").disabled = false;
			}
			if(sda1.length != 0) {
				document.getElementById("moveIn").disabled = false;
				document.getElementById("moveOut").disabled = false;
			}
			else
				document.getElementById("moveIn").disabled = false;
			var y=document.createElement('option');
			y.setAttribute("class","comboBig");
			y.text=tmp;
			try
			{
			sda.add(y,null);
			}
			catch(ex){
			sda.add(y);	
			}
		}
	}	
}

function moveoutid()
{
	var sda = document.getElementById('selectColumn');
	var len = sda.length;
	var sda1 = document.getElementById('unselectColumn');
	if(sda1.length == 0) {
		document.getElementById("moveOut").disabled = false;
	}
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			len--;
			j--;
			if(j<0){
				document.getElementById("moveIn").disabled = true;
				document.getElementById("moveOut").disabled = false;
			}
			if(sda.length <= 0)
				document.getElementById("moveIn").disabled = true;
			else
				document.getElementById("moveIn").disabled = false;
			var y=document.createElement('option');
			y.text=tmp;
			y.value = tmp1;
			y.setAttribute("class","comboBig");
			try
			{
				sda1.add(y,null);
			}
			catch(ex)
			{
				sda1.add(y);
			}
		}
	}
}

function resetMessages() {
	resetFieldAndErrMsgs();
}

function updateValues() {
	var sda1 = document.getElementById("selectColumn");
	for(var i=0;i<sda1.length;i++) {
		sda1[i].selected = true;
	}	
	var sda2 = document.getElementById("unselectColumn");
	for(var j=0;j<sda2.length;j++) {
		sda2[j].selected = true;
	}
	var count = document.getElementById("selectColumn"); 
	var cont = document.getElementById("selectColumn").selectedIndex;
	if(cont == -1 && count.length == 0){
		document.getElementById("selectedIndex").value = -1;
	}else{
		document.getElementById("selectedIndex").value = 1;
	}

	document.getElementById("method").value = "exportToExcel";
}

</script>

<html:form action="/studentDetailsReport" method="POST">
<html:hidden property="selectedIndex" styleId="selectedIndex"/>
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="studentDetailsReportForm" />
<html:hidden property="pageType" value="3" />

<table width="100%" border="0">
  		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.reports.student.details.report" />&gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Report Column Config</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
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
                  <tr>
                    <td height="25" valign="top" width="50%" class="row-odd" ><div align="center"><b><bean:message key="knowledgepro.admission.report.unselectedcolumns"/></b></div></td>
                    <td height="25" valign="top" width="50%" class="row-odd" ><div align="center"><span class="Mandatory">*</span><b><bean:message key="knowledgepro.admission.report.selectedcolumns"/></b></div></td>
                   </tr>
                   <tr> 
                    <td height="25" class="row-even" width="50%" colspan="3">
                    <table>
                    <tr>
                    <td width="10%">	
					</td>
                    <td width="30%">
	                    <html:select property="unselectedColumnsArray" styleId="unselectColumn" styleClass="body" multiple="multiple" size="20" style="width:300px;">
						<html:option value="Date Of Birth" styleClass="comboBig"></html:option>
						<html:option value="Gender" styleClass="comboBig"></html:option>
						<html:option value="Bank Account No" styleClass="comboBig"></html:option>
						<html:option value="Smart Card No" styleClass="comboBig"></html:option>
						<html:option value="Mobile No" styleClass="comboBig"></html:option>
						<html:option value="E-Mail" styleClass="comboBig"></html:option>
						<html:option value="University E-Mail" styleClass="comboBig"></html:option>
						<html:option value="Religion" styleClass="comboBig"></html:option>
						<html:option value="Mother Tongue" styleClass="comboBig"></html:option>
						<html:option value="Birth Place" styleClass="comboBig"></html:option>
						<html:option value="Blood Group" styleClass="comboBig"></html:option>
						<html:option value="Passport No" styleClass="comboBig"></html:option>
						<html:option value="Passport Validity" styleClass="comboBig"></html:option>
						<html:option value="Nationality" styleClass="comboBig"></html:option>
						<html:option value="Caste" styleClass="comboBig"></html:option>
						<html:option value="Current Address Line1" styleClass="comboBig"></html:option>
						<html:option value="Current Address Line2" styleClass="comboBig"></html:option>
						<html:option value="Current City" styleClass="comboBig"></html:option>
						<html:option value="Current State" styleClass="comboBig"></html:option>
						<html:option value="Current Country" styleClass="comboBig"></html:option>
						<html:option value="Current PinCode" styleClass="comboBig"></html:option>
						<html:option value="Permanent Address Line1" styleClass="comboBig"></html:option>
						<html:option value="Permanent Address Line2" styleClass="comboBig"></html:option>
						<html:option value="Permanent City" styleClass="comboBig"></html:option>
						<html:option value="Permanent State" styleClass="comboBig"></html:option>
						<html:option value="Permanent Country" styleClass="comboBig"></html:option>
						<html:option value="Permanent PinCode" styleClass="comboBig"></html:option>
						<html:option value="Father Name" styleClass="comboBig"></html:option>
						<html:option value="Mother Name" styleClass="comboBig"></html:option>
						<html:option value="Guardian Address Line1" styleClass="comboBig"></html:option>
						<html:option value="Guardian Address Line2" styleClass="comboBig"></html:option>
						<html:option value="Guardian City" styleClass="comboBig"></html:option>
						<html:option value="Guardian State" styleClass="comboBig"></html:option>
						<html:option value="Guardian Country" styleClass="comboBig"></html:option>
						<html:option value="Guardian PinCode" styleClass="comboBig"></html:option>
						<html:option value="Parent Mobile No" styleClass="comboBig"></html:option>
						<html:option value="Second Language" styleClass="comboBig"></html:option>
						<html:option value="Course" styleClass="comboBig"></html:option>
						<html:option value="Handicapped" styleClass="comboBig"></html:option>
						<html:option value="Handicapped Description" styleClass="comboBig"></html:option>
						</html:select>
					</td>
					<td width="5%">	
					</td>
                	<td width="15%">	
						<input type="button" align="right" value="====&gt;" id="moveOut" onclick="moveinid()"/> 
						<input type="button" align="right" value="&lt;====" id="moveIn" onclick="moveoutid()"/>
					</td>
					<td width="5%">	
					</td>
					<td width="30%">
	         			<html:select property="selectedColumnsArray" styleId="selectColumn" styleClass="body" multiple="multiple" size="20" style="width:300px">
						<html:option value="Register No" styleClass="comboBig"></html:option>
						<html:option value="Student Name" styleClass="comboBig"></html:option>
						<html:option value="Class Name" styleClass="comboBig"></html:option>
						<html:option value="Application No" styleClass="comboBig"></html:option>
						</html:select>
					</td>
					<td width="10%">	
					</td>
		            </tr>
					</table>	
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
              <td width="49%" height="35"><div align="right">
                 <html:submit property="" styleClass="formbutton" onclick="updateValues()"><bean:message key="knowledgepro.submit"/></html:submit>
              </div></td>
              <td width="2%"></td>
              <td width="49%">
             	<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
            </td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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