<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>:: CMS ::</title>

<script type="text/javascript" language="javascript">

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
		var mode = document.getElementById("mode").value;
		if(mode == "excel") {
			document.getElementById("method").value = "exportToExcel";			
		} else {
			document.getElementById("method").value = "exportToCsv";			
		}	
	}
	
	function resetMessages() {
		resetFieldAndErrMsgs();
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
	
</script>

</head>
<html:form action="/employeeReport" method="POST">
<html:hidden property="selectedIndex" styleId="selectedIndex"/>
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="mode" styleId="mode"/>
<html:hidden property="formName" value="employeeReportForm" />
<html:hidden property="pageType" value="2" />

<table width="100%" border="0">
  <tr>
				
    <td><span class="Bredcrumbs">
    <bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt; 
     <bean:message key="knowledgepro.admission.report.columnconfig"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.admission.report.columnconfig"/> </strong></td>
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
                    <td height="25" class="row-even" width="40%" colspan="3">
                    <table>
                    <tr>
                    <td width="50%">
	                    <html:select property="unselectedColumnsArray" styleId="unselectColumn" styleClass="body" multiple="multiple" size="20" style="width:300px;">
						<html:option value="UserName" styleClass="comboBig"></html:option>
						<html:option value="UID" styleClass="comboBig"></html:option>
						<html:option value="Nationality" styleClass="comboBig"></html:option>
						<html:option value="Gender" styleClass="comboBig"></html:option>
						<html:option value="Marital Status" styleClass="comboBig"></html:option>
						<html:option value="Date of birth" styleClass="comboBig"></html:option>
						<html:option value="Age" styleClass="comboBig"></html:option>
						<html:option value="Blood Group" styleClass="comboBig"></html:option>
						<html:option value="Religion" styleClass="comboBig"></html:option>
						<html:option value="Pan No" styleClass="comboBig"></html:option>
						<html:option value="Official Email Id" styleClass="comboBig"></html:option>
						<html:option value="Personal Email Id" styleClass="comboBig"></html:option>
						<html:option value="Reservation Category" styleClass="comboBig"></html:option>
						<html:option value="MobileNo" styleClass="comboBig"></html:option>
						<html:option value="Bank Account No" styleClass="comboBig"></html:option>
						<html:option value="PF Account No" styleClass="comboBig"></html:option>
						<html:option value="Four Wheeler No" styleClass="comboBig"></html:option>
						<html:option value="Two Wheeler No" styleClass="comboBig"></html:option>
						<html:option value="Smart Card No" styleClass="comboBig"></html:option>
						<html:option value="Home Telephone" styleClass="comboBig"></html:option>
						<html:option value="Work Telephone" styleClass="comboBig"></html:option>
						<html:option value="Current Address Line1" styleClass="comboBig"></html:option>
						<html:option value="Current Address Line2" styleClass="comboBig"></html:option>
						<html:option value="Current City" styleClass="comboBig"></html:option>
						<html:option value="Current PinCode" styleClass="comboBig"></html:option>
						<html:option value="Current Country" styleClass="comboBig"></html:option>
						<html:option value="Current State" styleClass="comboBig"></html:option>
						<html:option value="Permanent Address Line1" styleClass="comboBig"></html:option>
						<html:option value="Permanent Address Line2" styleClass="comboBig"></html:option>
						<html:option value="Permanent City" styleClass="comboBig"></html:option>
						<html:option value="Permanent PinCode" styleClass="comboBig"></html:option>
						<html:option value="Permanent Country" styleClass="comboBig"></html:option>
						<html:option value="Permanent State" styleClass="comboBig"></html:option>
						<html:option value="Photo" styleClass="comboBig"></html:option>
						<html:option value="Employee Type" styleClass="comboBig"></html:option>
						<html:option value="Joined Date" styleClass="comboBig"></html:option>
						<html:option value="Rejoin Date" styleClass="comboBig"></html:option>
						<html:option value="Date of Retirement" styleClass="comboBig"></html:option>
						<html:option value="Appointment Letter Date" styleClass="comboBig"></html:option>
						<html:option value="Reference Number For Appointment" styleClass="comboBig"></html:option>
						<html:option value="Active" styleClass="comboBig"></html:option>
						<html:option value="Stream Details" styleClass="comboBig"></html:option>
						<html:option value="Work Location" styleClass="comboBig"></html:option>
						<html:option value="Deputation To Department" styleClass="comboBig"></html:option>
						<html:option value="Title" styleClass="comboBig"></html:option>
						<html:option value="Report To" styleClass="comboBig"></html:option>
						<html:option value="Total Experience Years" styleClass="comboBig"></html:option>
						<html:option value="Total Experience Months" styleClass="comboBig"></html:option>
						<html:option value="Recognised Experience Years" styleClass="comboBig"></html:option>
						<html:option value="Recognised Experience Months" styleClass="comboBig"></html:option>
						<html:option value="Subject Area" styleClass="comboBig"></html:option>
						<html:option value="Qualification Level" styleClass="comboBig"></html:option>
						<html:option value="Highest Qualification" styleClass="comboBig"></html:option>
						<html:option value="Scale" styleClass="comboBig"></html:option>
						<html:option value="Teaching Staff" styleClass="comboBig"></html:option>
						<html:option value="Emergency Contact Name" styleClass="comboBig"></html:option>
						<html:option value="RelationShip" styleClass="comboBig"></html:option>
						<html:option value="Emergency Mobile" styleClass="comboBig"></html:option>
						<html:option value="Emergency HomeTelephone" styleClass="comboBig"></html:option>
						<html:option value="Emergency WorkTelephone" styleClass="comboBig"></html:option>
						<html:option value="Passport No" styleClass="comboBig"></html:option>
						<html:option value="Passport Status" styleClass="comboBig"></html:option>
						<html:option value="Passport ReviewStatus" styleClass="comboBig"></html:option>
						<html:option value="Passport Issue Date" styleClass="comboBig"></html:option>
						<html:option value="Passport Date_of_expiry" styleClass="comboBig"></html:option>
						<html:option value="Passport Comments" styleClass="comboBig"></html:option>
						<html:option value="Passport Country" styleClass="comboBig"></html:option>
						<html:option value="Visa No" styleClass="comboBig"></html:option>
						<html:option value="Visa Status" styleClass="comboBig"></html:option>
						<html:option value="Visa ReviewStatus" styleClass="comboBig"></html:option>
						<html:option value="Visa Issue_date" styleClass="comboBig"></html:option>
						<html:option value="Visa Date_of_expiry" styleClass="comboBig"></html:option>
						<html:option value="Visa Comments" styleClass="comboBig"></html:option>
						<html:option value="Visa Country" styleClass="comboBig"></html:option>
						<html:option value="Date of Resignation" styleClass="comboBig"></html:option>
						<html:option value="Date of Leaving" styleClass="comboBig"></html:option>
						<html:option value="Reason of Leaving" styleClass="comboBig"></html:option>
						<html:option value="Releaving Order Date" styleClass="comboBig"></html:option>
						<html:option value="Reference Number For Releaving" styleClass="comboBig"></html:option>
						<html:option value="No. of Publications Refereed" styleClass="comboBig"></html:option>
						<html:option value="Publications Non Refereed" styleClass="comboBig"></html:option>
						<html:option value="No. of Books with ISBN" styleClass="comboBig"></html:option>
						<html:option value="Grade" styleClass="comboBig"></html:option>
						<html:option value="No. of Books with ISBN" styleClass="comboBig"></html:option>
						<html:option value="Grade" styleClass="comboBig"></html:option>
						<% if(CMSConstants.LINK_FOR_CJC){ %>
						<html:option value="Experience in CJC" styleClass="comboBig"></html:option>
						<%} else {%>
						<html:option value="Experience in CU" styleClass="comboBig"></html:option>
						<%} %>
						<html:option value="Total Current Experience" styleClass="comboBig"></html:option>
						<html:option value="Extension Number" styleClass="comboBig"></html:option>
						</html:select>
					</td>
                	<td width="15%">	
						<input type="button" align="right" value="====&gt;" id="moveOut" onclick="moveinid()"/> 
						<input type="button" align="right" value="&lt;====" id="moveIn" onclick="moveoutid()"/>
					</td>
					<td width="50%">
	         			<html:select property="selectedColumnsArray" styleId="selectColumn" styleClass="body" multiple="multiple" size="20" style="width:300px">
						<html:option value="EmpId" styleClass="comboBig"></html:option>
						<html:option value="Name" styleClass="comboBig"></html:option>
						<html:option value="Designation" styleClass="comboBig"></html:option>
						<html:option value="Department" styleClass="comboBig"></html:option>
						</html:select>
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
              <td width="45%" height="35"><div align="right">
                 <html:submit property="" styleClass="formbutton" onclick="updateValues()"><bean:message key="knowledgepro.submit"/></html:submit>
              </div></td>
              <td width="2%"></td>
              <td width="53%">
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