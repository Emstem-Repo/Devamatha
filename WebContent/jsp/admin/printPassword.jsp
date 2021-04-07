<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
var status="";
	function resetData() {
		resetFieldAndErrMsgs();
		document.getElementById("isRollNo_2").checked = true;
		document.getElementById("isStudent_1").checked = true;
		document.location.href = "PrintPassword.do?method=initPrintPassword";
		enableFields();
	}
	function changeLabel(isRollNo){
		if(isRollNo == "true"){
			document.getElementById("reFrom").innerHTML = "<span class='MandatoryMark'>*</span>Roll No. From:";
			document.getElementById("reTo").innerHTML = "<span class='MandatoryMark'>*</span>Roll No. To:";
		}
		else
		{
			document.getElementById("reFrom").innerHTML = "<span class='MandatoryMark'>*</span>Reg No. From:";
			document.getElementById("reTo").innerHTML = "<span class='MandatoryMark'>*</span>Reg No. To:";
		}		
		
	}
	function printPass(){
		var  regfrom = document.getElementById("regNoFrom").value;
		var  regto = document.getElementById("regNoTo").value;
		var  isRollNo = document.getElementById("isRollNo_1").checked;
		var isStudent = document.getElementById("isStudent_1").checked;
		var  year = document.getElementById("academicYear").value;
		var  year1 = document.getElementById("academicYear3").value;
		var  programm = document.getElementById("programm").value;
		var  semester = document.getElementById("semester").value;
		var  className = document.getElementById("classes").value;
        if(status=="")
        {
        	document.getElementById("displayingErrorMessage").innerHTML="Please Enter Anything"; 
        	
        }else if(status=="regNo")
        {
            if(regfrom==null || regfrom=="")
            {
            	document.getElementById("displayingErrorMessage").innerHTML="Please Enter RegisterFrom"; 
            }
            else if(regto==null || regto==""){
            	document.getElementById("displayingErrorMessage").innerHTML="Please Enter registerNo To"; 
                  
            }else {
            	document.getElementById("displayingErrorMessage").innerHTML="";
            	var url = "PrintPassword.do?method=printPassword&regNoFrom=" +regfrom + "&regNoTo=" + regto + "&isRollNo=" + isRollNo + "&isStudent=" + isStudent;
				myRef = window
						.open(url, "viewPassword",
								"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
            }
        }else if(status=="program"){
                if(programm==null || programm=="")
                {
                	document.getElementById("displayingErrorMessage").innerHTML="Please Select Programm"; 
                }else if(semester==null || semester=="")
                {
                	document.getElementById("displayingErrorMessage").innerHTML="Please Select semester"; 
                }else if(year==null || year==""){
                	document.getElementById("displayingErrorMessage").innerHTML="Please select year"; 
                }else {
                	document.getElementById("displayingErrorMessage").innerHTML="";
                	 var url = "PrintPassword.do?method=printPassword&programm=" +programm + "&semester=" + semester +"&academicYear=" +year+ "&isRollNo=" + isRollNo + "&isStudent=" + isStudent;
               		myRef = window
               				.open(url, "viewPassword",
               						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
                }
        }else if(status=="class"){
               if(year1==null || year1=="")
               {
            	   document.getElementById("displayingErrorMessage").innerHTML="Please select year"; 
               }else if(className==null || className=="")
               {
                   document.getElementById("displayingErrorMessage").innerHTML="Please select class"; 
                   
               }else {
            	   document.getElementById("displayingErrorMessage").innerHTML="";
            	   var url = "PrintPassword.do?method=printPassword&classes=" +className + "&academicYear=" + year1 + "&isRollNo=" + isRollNo + "&isStudent=" + isStudent;
            		myRef = window
            				.open(url, "viewPassword",
            						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
               }
        }
	}
function getClassesByAcademicYear(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}
function disableClassProgramm() {
	status = "regNo";
	var regFrom=document.getElementById('regNoFrom').value;
	var regTo=document.getElementById('regNoTo').value;
	if((regFrom!=null && regFrom!="")||(regTo!=null && regTo!=""))
	{
	document.getElementById('academicYear').disabled = true;
	document.getElementById('programm').disabled = true;
	document.getElementById('semester').disabled = true;
	document.getElementById('academicYear3').disabled = true;
	document.getElementById('classes').disabled = true;
	document.getElementById('regNoFrom').disabled = false;
	document.getElementById('regNoTo').disabled = false;
	}else{
		enableFields();
	}
}
function disableRegisterNoClass() {
	status = "program";
	var progr=document.getElementById('programm').value;
	var sem=document.getElementById('semester').value;
	if((progr!=null && progr!="")||(sem!=null && sem!=""))
	{
	document.getElementById('regNoFrom').disabled = true;
	document.getElementById('regNoTo').disabled = true;
	document.getElementById('classes').disabled = true;
	document.getElementById('academicYear3').disabled = true;
	document.getElementById('academicYear').disabled = false;
	document.getElementById('programm').disabled = false;
	document.getElementById('semester').disabled = false;
	}else{
		enableFields();
	}
}
function disableProgramRegisterNo() {
	status = "class";
	var clas=document.getElementById('classes').value;
	if(clas!=null && clas!="")
	{
	document.getElementById('academicYear').disabled = true;
	document.getElementById('programm').disabled = true;
	document.getElementById('semester').disabled = true;
	document.getElementById('regNoFrom').disabled = true;
	document.getElementById('regNoTo').disabled = true;
	document.getElementById('academicYear3').disabled = false;
	document.getElementById('classes').disabled = false;
    }else{
		enableFields();
	}
}
function enableFields() {
	status = "";
	document.getElementById('academicYear').disabled = false;
	document.getElementById('programm').disabled = false;
	document.getElementById('semester').disabled = false;
	document.getElementById('academicYear3').disabled = false;
	document.getElementById('classes').disabled = false;
	document.getElementById('regNoFrom').disabled = false;
	document.getElementById('regNoTo').disabled = false;
	document.getElementById("displayingErrorMessage").innerHTML="";
}
</script>

<html:form action="/PrintPassword">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="printPasswordForm"/>
<html:hidden property="pageType" value="1"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.print.password"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admin.print.password"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <FONT size="2px" color="red"><div id="displayingErrorMessage"></div></FONT>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.print.roll.no"/></div>
							</td>
							<td width="5%" height="25" class="row-even" >
							<input type="radio" name="isRollNo" id="isRollNo_1" value="true" onclick="changeLabel(this.value)"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isRollNo" id="isRollNo_2" value="false" checked="checked" onclick="changeLabel(this.value)"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var RegistartionNo = "<bean:write name='printPasswordForm' property='isRollNo'/>";
								if(RegistartionNo == "true") {
				                        document.getElementById("isRollNo_2").checked = true;
								}	
							</script>
							</td>
							<td width="15%" class="row-odd">
							<div align="right">Is Student</div>
							</td>
							<td width="5%" height="25" class="row-even">
							<input type="radio" name="isStudent" id="isStudent_1" value="true" checked="checked" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isStudent" id="isStudent_2" value="false"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var isStudent = "<bean:write name='printPasswordForm' property='isStudent'/>";
								if(isStudent == "true") {
				                        document.getElementById("isStudent_2").checked = true;
								}	
							</script></td>
						</tr>
                           <tr>
                             <td width="15%" height="25" class="row-odd" ><div id = "reFrom" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.regno.from.col" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="regNoFrom" styleId="regNoFrom" name="printPasswordForm" onchange="disableClassProgramm()"/>
                             </span></td>
							<td width="15%" height="25" class="row-odd" ><div id = "reTo" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.regno.to.col" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="regNoTo" styleId="regNoTo" name="printPasswordForm" onchange="disableClassProgramm()"/>
                             </span></td>
                           </tr>
                           <tr><td align="center" colspan="4">OR</td></tr>
                            <tr>
                             <td width="15%" height="25" class="row-odd" ><div id = "academicYear1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:select
										property="academicYear" styleClass="combo"
										styleId="academicYear" name="printPasswordForm" onchange="disableRegisterNoClass()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select>
                             </span></td>
                             <td width="15%" height="25" class="row-odd" ><div id = "programm1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                              <html:select
										property="programm" styleClass="combo"
										styleId="programm" name="printPasswordForm" onchange="disableRegisterNoClass()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="printPasswordForm" property="programToList" label="name" value="id"/>
									</html:select></td>
                           </tr>
                           <tr>	<td width="15%" height="25" class="row-odd" ><div id = "semester1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.certificate.course.semester" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left" colspan="3">
                              <html:select
										property="semester" styleClass="combo"
										styleId="semester" name="printPasswordForm" onchange="disableRegisterNoClass()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="1">1</html:option>
										<html:option value="2">2</html:option>
										<html:option value="3">3</html:option>
										<html:option value="4">4</html:option>
										<html:option value="5">5</html:option>
										<html:option value="6">6</html:option>
										<html:option value="7">7</html:option>
										<html:option value="8">8</html:option>
										<html:option value="9">9</html:option>
										<html:option value="10">10</html:option>
									</html:select></td></tr>
                            <tr><td align="center" colspan="4">OR</td> </tr>
                            <tr >
                             <td width="15%" height="25" class="row-odd" ><div id = "academicYear2" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:select
										property="academicYear" styleClass="combo"
										styleId="academicYear3" name="printPasswordForm" onchange="getClassesByAcademicYear(this.value),disableProgramRegisterNo()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select>
                             </span></td>
                             <td width="15%" height="25" class="row-odd" ><div id ="classes1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left" colspan="3">
                              <html:select
										property="classes" styleClass="combo"
										styleId="classes" name="printPasswordForm" onchange="disableProgramRegisterNo()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${printPasswordForm.classes != null && printPasswordForm.classes != ' '}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
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
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
			   				  <html:button property="" styleId="print" styleClass="formbutton" value="Submit" onclick="printPass()"></html:button>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
