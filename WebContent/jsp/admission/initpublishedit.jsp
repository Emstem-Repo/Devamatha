<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">
<script type="text/javascript">
var status="";
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
		var  year1 = document.getElementById("academicYear3").value;
		var  className = document.getElementById("classId").value;
		var ischecked=document.getElementById("isRollNo_1").value;
		var startDate=document.getElementById("downLoadStartDate").value;
		var endDate=document.getElementById("downLoadEndDate").value;
        if(status=="")
        {
        	document.location.href = "PublishStudentEdit.do?method=saveMethod";
        }else if(status=="regNo")
        { 
        	document.location.href = "PublishStudentEdit.do?method=saveMethod&regNoFrom=" +regfrom + "&regNoTo=" + regto + "&editStartDate=" + startDate + "&editEndDate= " + endDate;
        }else if(status=="class"){
        	   document.getElementById("method").value="saveMethod";
        }else if(status=="program"){
        	document.location.href = "PublishStudentEdit.do?method=saveMethod&isWholeStudent=" +ischecked + "&editStartDate=" + startDate + "&editEndDate= " + endDate;
        }
	}
	function getClassesByAcademicYear(year) {
		getClassesByYear("classesMap", year, "classId", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMapForMultiSelect(req, "classId");
	}
	function disableClassProgramm() {
		status = "regNo";
		var regFrom=document.getElementById('regNoFrom').value;
		var regTo=document.getElementById('regNoTo').value;
		if((regFrom!=null && regFrom!="")||(regTo!=null && regTo!=""))
		{
		document.getElementById('academicYear3').disabled = true;
		document.getElementById('classId').disabled = true;
		document.getElementById('regNoFrom').disabled = false;
		document.getElementById('regNoTo').disabled = false;
		document.getElementById("isRollNo_1").disabled=true;
		document.getElementById("downLoadStartDate").disabled=true;
		document.getElementById("downLoadEndDate").disabled=true;
		}else{
			enableFields();
		}
	}
	function disableRegisterNoClass() {
		status = "program";
		var progr=document.getElementById('isRollNo_1').value;
		if((progr!=null && progr!="")||(sem!=null && sem!=""))
		{
		document.getElementById('regNoFrom').disabled = true;
		document.getElementById('regNoTo').disabled = true;
		document.getElementById('classId').disabled = true;
		document.getElementById('academicYear3').disabled = true;
		document.getElementById("downLoadStartDate").disabled=true;
		document.getElementById("downLoadEndDate").disabled=true;
		}else{
			enableFields();
		}
	}
	function disableProgramRegisterNo() {
		status = "class";
		document.getElementById('regNoFrom').disabled = true;
		document.getElementById('regNoTo').disabled = true;
		document.getElementById("isRollNo_1").disabled=true;
		document.getElementById("downLoadStartDate").disabled=true;
		document.getElementById("downLoadEndDate").disabled=true;
	}
	function enableFields() {
		status = "";
		document.getElementById('academicYear3').disabled = false;
		document.getElementById('classId').disabled = false;
		document.getElementById('regNoFrom').disabled = false;
		document.getElementById('regNoTo').disabled = false;
		document.getElementById("displayingErrorMessage").innerHTML="";
	}
	function deleteDetails(id) 
	{
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) 
		{
			 document.location.href="PublishStudentEdit.do?method=deleteDetails&id=" + id;
		}
	}
	function editDetails(id){
		document.getElementById("print").value="Update";
		document.location.href="PublishStudentEdit.do?method=editDetails&id=" + id;
	}
	function reActivate()
	{
		var dupId = document.getElementById("dupId").value;
		document.location.href = "PublishStudentEdit.do?method=reActivateClass&dupId=" + dupId;
	}
	function UpdateDetails()
	{
		document.location.href = "PublishStudentEdit.do?method=updateDetails";
	}
	function clearAll() {
		document.location.href = "PublishStudentEdit.do?method=initPublishMethod";
		enableFields();
	}
</script>

<html:form action="/PublishStudentEdit">
<html:hidden property="formName" value="publishStudentEditForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="dupId" styleId="dupId"/>
<c:choose>
		<c:when test="${publish != null && publish == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveMethod" />
		</c:otherwise>
	</c:choose>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; Publish Student Edit&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Publish Student Edit</strong></div></td>
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
                             <td width="15%" height="25" class="row-odd" ><div id = "reFrom" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.regno.from.col" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="regNoFrom" styleId="regNoFrom" name="publishStudentEditForm" onchange="disableClassProgramm()"/>
                             </span></td>
							<td width="15%" height="25" class="row-odd" ><div id = "reTo" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.regno.to.col" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="regNoTo" styleId="regNoTo" name="publishStudentEditForm" onchange="disableClassProgramm()"/>
                             </span></td>
                           </tr>
                          
                            <tr><td align="center" colspan="4">OR</td> </tr>
                            <tr >
                             <td width="15%" height="25" class="row-odd" ><div id = "academicYear2" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:select
										property="academicYear" styleClass="combo"
										styleId="academicYear3" name="publishStudentEditForm" onchange="getClassesByAcademicYear(this.value),disableProgramRegisterNo()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select>
                             </span></td>
                             <td width="15%" height="25" class="row-odd" ><div id ="classes1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left" colspan="3">
							  <html:select property="classIds" styleId="classId" multiple="multiple" style="width:350px" size="8" onblur="disableProgramRegisterNo()">
								<logic:notEmpty name="publishStudentEditForm" property="classMap">
									<html:optionsCollection name="publishStudentEditForm" property="classMap" label="value" value="key" />
								</logic:notEmpty>
							</html:select>
							  
						  	</td>
                           </tr>
                           
<!--                           <tr><td align="center" colspan="4">OR</td> </tr>-->
                           
<!--                           <tr>-->
<!--							<td width="13%" height="25" class="row-odd">-->
<!--							<div align="right"><input type="radio" name="isWholeStudent" id="isRollNo_1" value="true"  onclick="disableRegisterNoClass()"/></div>-->
<!--							</td>-->
<!--							<td width="5%" height="25" class="row-even" >-->
<!--							<b>Publish For Whole Students</b>-->
<!--                   			-->
<!--							</td>-->
<!--							<td width="15%" class="row-even">-->
<!--							<div align="right"></div>-->
<!--							</td>-->
<!--							<td width="5%" height="25" class="row-even">-->
<!--                   			</td>-->
<!--						</tr>-->
						
						 <tr height="20"><td align="center" colspan="4"></td> </tr>
						
						<tr>
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.TimeTable.startDate" />:
									</div>
									</td>
									<td width="5%" height="25" class="row-even" >
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="publishStudentEditForm"
												property="editStartDate" styleId="downLoadStartDate"
												maxlength="10" styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'publishStudentEditForm',
													// input name
													'controlname' :'editStartDate'
												});
											</script></td>
										</tr>
									</table>
									</td>
									<td width="13%" height="25" class="row-odd">
									<div align="right">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.TimeTable.endDate" />:</div>
									</div>
									</td>
									<td width="5%" height="25" class="row-even" >
									<table  border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="publishStudentEditForm" property="editEndDate"
												styleId="downLoadEndDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'publishStudentEditForm',
													// input name
													'controlname' :'editEndDate'
												});
											</script></td>
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
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
								<c:choose>
										<c:when test="${publish != null && publish == 'edit'}">
											<html:submit property="" styleId="print" styleClass="formbutton" value="Update"  onclick="UpdateDetails()"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleId="print" styleClass="formbutton" value="Submit"  onclick="printPass()"></html:submit>
										</c:otherwise>
								</c:choose>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="clearAll();"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                     
                     
             <logic:notEmpty name="publishStudentEditForm" property="publishStudentEditTOs">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td width="15" height="30%" class="row-odd" align="center" >Class Name</td>
                    <td width="15" height="30%" class="row-odd" align="center" >Academic year</td>
                    <td width="15" height="30%" class="row-odd" align="center" >Start Date</td>
                    <td width="15" height="30%" class="row-odd" align="center" >End Date</td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="to" name="publishStudentEditForm" property="publishStudentEditTOs" indexId="count">
                		<c:choose>
							<c:when test="${count%2 == 0}">
									<tr class="row-even">
							</c:when>
							<c:otherwise>
									<tr class="row-even">
							</c:otherwise>
						</c:choose>
                		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="className"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="academicYear"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="startDate"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="endDate"/></td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/edit_icon.gif" width="16" height="16" style="cursor:pointer" onclick="editDetails('<bean:write name="to" property="id"/>')"></div></td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="to" property="id"/>')"></div></td>
                   
                </logic:iterate>
                
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
          </logic:notEmpty>
                     
                     
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
