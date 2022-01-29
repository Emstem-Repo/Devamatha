<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">

function getSchemeNo(registerNo) {
	document.getElementById("errorMessage").innerHTML = "";
	document.getElementById("submitbutton").style.display="none";
	resetOption("schemeNo");
	var args = "method=getSchemeNoForCertificateCourse&regNo=" + registerNo;
	var destinationOption = document.getElementById("schemeNo");
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "NewStudentCertificateCourse.do";
	// make an request to server passing URL need to be invoked and
	// arguments.
	requestOperationProgram(url, args, updateSchemeNo);
}
function updateSchemeNo(req)
{
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null && value.length>0){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null && value[I].firstChild!='' && value[I].firstChild.length!=0){
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("errorMessage").innerHTML = temp;
				
				document.getElementById("submitbutton").style.display="none";
			}
		}
	}else{
		var destination=document.getElementById("schemeNo");
		if(responseObj.getElementsByTagName("option")!=null){
			var items = responseObj.getElementsByTagName("option");
			var label, value;
			for ( var i = 0; i < items.length; i++) {
				label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
				value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
				destination.options[i] = new Option(label, value);
			}
		}
		var temp = responseObj.getElementsByTagName("schemeId");
		for ( var I = 0; I < temp.length; I++) {
			var select = document.getElementById('schemeNo');
			for ( var i = 0; i < select.options.length; i++ )
			{
			  o = select.options[i];
			  if ( temp[0].firstChild.nodeValue==o.value)
			  {
			    o.selected = true;
			    if(parseInt(temp[0].firstChild.nodeValue)%2==0)
				    document.getElementById("semType").value="EVEN";
			    else
			    	document.getElementById("semType").value="ODD";
			  }
			}
		}
		document.getElementById("submitbutton").style.display="block";
	}
}
function changeSemType(schemeNo){
	if(parseInt(schemeNo)%2==0)
	    document.getElementById("semType").value="EVEN";
    else
    	document.getElementById("semType").value="ODD";
}
function checkForm(){
	var regNo=document.getElementById("registerNo").value;
	if(regNo== null || regNo==''){
			alert("Please Enter Student Reg No");
		}else{
			document.newStudentCertificateCourseForm.submit();
			}
}

</script>
<html:form action="/NewStudentCertificateCourse">	
		<html:hidden property="method" styleId="method" value="getCertificateCourses" />
		<html:hidden property="formName" value="newStudentCertificateCourseForm"/>
		<html:hidden property="pageType" value="1" />
		<html:hidden property="semType" styleId="semType"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.certificate.course"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.certificate.course"/> Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
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
									<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.certificate.course.regno"/>:</div>
									</td>
								<td width="21%" class="row-even" valign="top"><html:text property="regNo" styleId="registerNo"  onchange="getSchemeNo(this.value)"></html:text></td>																	
								
								<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.certificate.course.Semester"/>:</div>
								</td>
									
								<td width="23%" class="row-even" valign="top"> 
							       <html:select property="schemeNo" styleClass="combo" styleId="schemeNo" onchange="changeSemType(this.value)">
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
									</html:select>
								</td>
								</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>

						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<div align="center">
									<html:button property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton" onclick="checkForm()">
									</html:button>
								</div>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>
<script type="text/javascript">

function stopRKey(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}
}

document.onkeypress = stopRKey;

document.getElementById("submitbutton").style.display="none";

var print = "<c:out value='${newStudentCertificateCourseForm.printCourse}'/>";
if(print.length != 0 && print == "true") {
	var url ="StudentCertificateCourse.do?method=showStudentDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>