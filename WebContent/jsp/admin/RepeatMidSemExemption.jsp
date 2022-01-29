<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
	
	function Cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
  	}
    function Save(){
		document.getElementById("method").value = "SaveExemption";
		document.repeatMidSemAppForm.submit();
  	}
	
	function Search(){
		document.getElementById("method").value = "getStudentExamMidsemRepeatData";
		document.repeatMidSemAppForm.submit();
  	}
	
	function changeYear(year){
		document.getElementById("academicYear").value = year;
		getExamByYearOnly("midsemExamList", year, "midSemExamId", updateExams);
	}
	function updateExams(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("midSemExamId");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}
		destination.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("option");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination.options[j+1] = new Option(label,value);
		 }
	}
	
	
</script>
<html:form action="/RepeatExamExemption">
	<html:hidden property="formName" value="repeatMidSemAppForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1" />
	
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Mid Semester Exam Exemption Studentwise</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
					<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					 <td>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td class="row-odd" width="25%" height="25">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
							<td class="row-even" width="25%" height="25"><div align="left">
							<input type="hidden" value="<bean:write name="repeatMidSemAppForm" property="year"/>" id="tempYear">
		                        <html:select property="year" styleId="academicYear" name="repeatMidSemAppForm" style="width:130px" onchange="changeYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				 <cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select></div>
		        			</td>
							<td class="row-odd" width="25%" height="25"><div align="right">
							  <span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.mid.semester.exam"/>:</div></td>
							  <td class="row-even"  width="25%" height="25" colspan="2">
							     <html:select name="repeatMidSemAppForm" property="midSemExamId" styleId="midSemExamId" styleClass="comboLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="midsemExamList"
													name="repeatMidSemAppForm" label="value" value="key" />
											
							   </html:select>
							   </td>
							 </tr>
							 <tr>
							 	<td class="row-odd" width="25%" height="25">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.reservation.registerNo"/>:</div></td>
								<td class="row-even" width="25%" height="25" colspan="4"><div align="left">
			                        <html:text property="registerNo" styleId="registerNo" name="repeatMidSemAppForm" size="10"></html:text></div>
		        				</td>
							 </tr>
							 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
							 <tr>
                    			
								<td height="50" align="center" colspan="6"> 
								 <html:button property="" styleClass="formbutton" value="Search" onclick="Search()"></html:button>&nbsp;&nbsp;
								<html:button property="" styleClass="formbutton" value="Close" onclick="Cancel()"></html:button>	
								</td>
			                </tr>							 
			                <tr><td style="height: 10px" align="center" colspan="1"></td></tr>			                
					<logic:equal property="dataAvailable" name="repeatMidSemAppForm" value="true">
								<tr bgcolor="#CEECF5">
								   <td height="30" align="center" width="30%" class="row-odd"><font size="2" color="#07190B">Register No</font></td>
								   <td height="30" align="center" class="row-odd" colspan="2"><font size="2" color="#07190B">Name</font></td>
								   <td height="30" align="center" class="row-odd" width="30%"><font size="2" color="#07190B">Exam Name</font></td>
								   <td height="30" align="center" class="row-odd"><font size="2" color="#07190B">Exemption Permitted</font></td>
								</tr>
								
								<logic:notEmpty name="repeatMidSemAppForm" property="studentName">
								<tr class="row-even">
										  <td align="center" width="30%"><nested:write name="repeatMidSemAppForm" property="registerNo" /></td>
										  <td align="center" colspan="2"><nested:write name="repeatMidSemAppForm" property="studentName" /></td>
										  <td align="center" width="30%"><nested:write name="repeatMidSemAppForm" property="midsemExamName" /></td>
										  <td align="center" >
												<nested:hidden property="tempChecked" styleId="tempChecked" name="repeatMidSemAppForm"></nested:hidden>
					 					  		<nested:checkbox property="checked" styleId="checked"> </nested:checkbox>
				                   					<script type="text/javascript">
				                   							var flag=document.getElementById("tempChecked").value;
				                   							if(flag=="on"){
				                   								document.getElementById("checked").checked = true;
				                       							}
				                   					</script>
										   </td>
									</tr>
									<tr>
										<td height="50" align="center" colspan="4"> 
										 <html:submit property="" styleClass="formbutton" value="Save" onclick="Save()"></html:submit>&nbsp;&nbsp;
										<html:button property="" styleClass="formbutton" value="Close" onclick="Cancel()"></html:button>	
										</td>
			                	</tr>
								</logic:notEmpty>
								
						</logic:equal>
					</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
				</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
var tempYear=document.getElementById("tempYear").value;
if(tempYear!=null && tempYear!=""){
	document.getElementById("academicYear").value=tempYear;
}
</script>