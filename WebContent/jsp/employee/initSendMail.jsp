<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>

</head>
<script type="text/javascript">
function resetFields(){
	resetFieldAndErrMsgs();
}

function cancelPage() {
	document.location.href = "DownLoadEmployeeResume.do?method=initDownloadEmployeeResume";
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
	<%--var count = document.getElementById("selectColumn"); 
	var cont = document.getElementById("selectColumn").selectedIndex;
	if(cont == -1 && count.length == 0){
		document.getElementById("selectedIndex").value = -1;
	}else{
		document.getElementById("selectedIndex").value = 1;
	}--%>
		document.getElementById("method").value = "sendMail";	
		document.downloadEmployeeResumeForm.submit();		
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
	  var array = new Array();
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			array[j]=tmp1;
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
			y.value = tmp1;
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


<html:form action="/DownLoadEmployeeResume" method="post">
<html:hidden property="method" styleId="method" value=""/>	
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="downloadEmployeeResumeForm" />
<html:hidden property="employeeId" styleId="employeeId" name="downloadEmployeeResumeForm" />
<input type="hidden"  id="isDisplay"  value="<bean:write name="downloadEmployeeResumeForm" property="clear"/>"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Download Employee  Resume &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Download Employee Resume</strong></td>
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
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
            <tr>
            <td colspan="3" class="heading">Select the faculties to forward the application.</td>
            </tr>
			<tr>
		 		<%--<td height="25" class="row-odd" width="25%"><div align="right">Enter MailId:</div></td>
		        <td height="25" class="row-even">
		        	 <label>
						<html:textarea name="downloadEmployeeResumeForm" property="emailIds"  style="width: 400px; height: 70px"/>
					</label>
					</td>	--%>
					<td width="20%" class="row-even" align="right">
	                    <html:select property="userIdsArray" name="downloadEmployeeResumeForm"  styleId="unselectColumn"  multiple="multiple" size="10" style="width:350px;" styleClass="body">
							<html:optionsCollection name="downloadEmployeeResumeForm" property="usersMap" label="value" value="key" styleClass="comboBig"/>
						</html:select>
					</td>
                	<td width="5%" class="row-even" align="center">	
						<input type="button" align="right" value="&gt&gt;" id="moveOut" onclick="moveinid()"/> 
						<input type="button" align="right" value="&lt&lt;" id="moveIn" onclick="moveoutid()"/>
					</td>
					<td width="25%" class="row-even">
	         			<html:select property="selectedUserIdsArray" name="downloadEmployeeResumeForm" styleId="selectColumn"  multiple="multiple" size="10" style="width:350px" styleClass="body">
							<c:if test="${optionMap != null}">
					            <html:optionsCollection name="optionMap" label="value" value="key" styleClass="comboBig" />
					            </c:if>	
						</html:select>
					</td>					
	       </tr>
	       <% String message="Dear Sir, \n\n<br><br>Please download the applications forms from the link Employee->Download Applications from christuniversity.in/kp \n\n<br><br>Regards,\n<br>Office of Personnel Relations, \n\n<br>Ph: 40129087."; %>
	       <tr>
		 		<td height="25" class="row-odd" width="20%"><div align="right">Enter Body:</div></td>
		        <td height="25" class="row-even" colspan="2">
		        	<label>
						<html:textarea name="downloadEmployeeResumeForm" property="mailBody"  style="width: 500px; height: 140px" value='<%=message %>'/>
					</label></td>						
	       </tr>
            </table>
            </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"/></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Forward" onclick="updateValues()"></html:button>
										
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:button property="" styleClass="formbutton" onclick="cancelPage()" value="Cancel"></html:button>
							</div>
							</td>
						</tr>
			</table>
		</td>
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

</html:html>
<script type="text/javascript">
var value = document.getElementById("isDisplay").value;
if(value == "false"){
var sda2 = document.getElementById("unselectColumn");
	for(var j=0;j<sda2.length;j++) {
		sda2[j].selected = false;
	}
}
</script>