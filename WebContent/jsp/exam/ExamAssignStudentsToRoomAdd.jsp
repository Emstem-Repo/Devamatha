<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript" language="javascript">
function fun()
{
	document.location.href="ExamAssignStudentsToRoom.do?method=initExamAssignStudentsToRoom";
}
// Functions for AJAX 
function x(){
		var id = document.getElementById("className");
		
		var selectedArray = new Array();
		var count = 0;
		
		for ( var i = 0; i < id.options.length; i++) {
			if (id.options[i].selected) {
			selectedArray[count] = id.options[i].value;
				count++;
			}
		}
		
		return selectedArray.toString();

	}
function getSubjectName() {
	
	var classId=x();
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var examNameId=document.getElementById("examNameId").value;
	var time=hr.concat(":").concat(min);
	
		getSubjectNameByClassIds("mapSubjects", classId,date,time,examNameId, "subjectName",	updateSubjects);

	}
function updateSubjects(req) {
	updateOptionsFromMapForMultiSelect(req, "subjectName", "- Select -");
}
function deleteExaminars(id,name){
	var examType=document.getElementById("examType").value;
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var roomId=document.getElementById("roomId").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;
	deleteConfirm =confirm("Are you sure to delete "+ name +" entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamAssignStudentsToRoom.do?method=deleteExaminars&id="+id+"&examType="+examType+"&examNameId="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&roomId="+roomId+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder;
	}
}



function deleteStudents(id,name){
	var examType=document.getElementById("examType").value;
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var roomId=document.getElementById("roomId").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;

	var alloted=document.getElementById("alloted").value;
	
	
	deleteConfirm =confirm("Are you sure to delete "+ name +" entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamAssignStudentsToRoom.do?method=deleteStudents&id="+id+"&examType="+examType+"&examNameId="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&roomId="+roomId+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder+"&alloted="+alloted;
	}
}

function addExaminars(){
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var examTypeId=document.getElementById("examType").value;
	var roomId=document.getElementById("roomId").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;
	
	document.location.href = "AssignExaminer.do?method=assignExaminer&examName="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&actionType=studentsToRoom&examType="+examTypeId+"&roomId="+roomId+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder;
	
}

function addStudents(){
	var classId=x();
	var subjectId=getsubjectId();
	var examNameId=document.getElementById("examNameId").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;
	var examType=document.getElementById("examType").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var roomId=document.getElementById("roomId").value;
	
	document.location.href = "ExamAssignStudentsToRoom.do?method=addStudents&examNameId="+examNameId+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder+"&classId="+classId+"&subjectId="+subjectId+"&examType="+examType+"&date="+date+"&hr="+hr+"&min="+min+"&roomId="+roomId;
}
function getsubjectId(){
	var id = document.getElementById("subjectName");
	
	var selectedArray = new Array();
	var count = 0;
	
	for ( var i = 0; i < id.options.length; i++) {
		if (id.options[i].selected) {
		selectedArray[count] = id.options[i].value;
			count++;
		}
	}
	
	return selectedArray.toString();

}
</script>
<html:form action="/ExamAssignStudentsToRoom" method="post">

	<html:hidden property="formName" value="ExamAssignStudentsToRoomForm" />
	<html:hidden property="pageType" value="3" />

	<html:hidden property="examType" styleId="examType" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="date" styleId="date" />
	<html:hidden property="hr" styleId="hr" />
	<html:hidden property="min" styleId="min" />
	<html:hidden property="roomId" styleId="roomId" />
	<html:hidden property="alloted" styleId="alloted" />
	<html:hidden property="roomCapacity" styleId="roomCapacity" />

	<html:hidden property="nonEligible" styleId="nonEligible" />
	<html:hidden property="displayOrder" styleId="displayOrder" />

	<html:hidden property="listExaminarSize" styleId="listExaminarSize" />
	<html:hidden property="listStudentsSize" styleId="listStudentsSize" />
	
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.assignStudentsToRoom" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><p><strong class="boxheader"> <bean:message key="knowledgepro.exam.assignStudentsToRoom" /></strong></p>          </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
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
                <td height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.examDefinition.examName" /> :</div></td>
                <td width="25%" class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="examName"/></td>
                <td width="20%" height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.date" /> :</div></td>
                <td width="27%" class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="date"/></td>
                </tr>
                <tr>
                <td height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.time" /> :</div></td>
                <td class="row-even" ><bean:write name="ExamAssignStudentsToRoomForm" property="time"/></td>
                <td class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.roomCapacity" /> :</div></td>
                <td class="row-even"><span class="star"><bean:write name="ExamAssignStudentsToRoomForm" property="roomCapacity"/>
                  
                </span></td>
                </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.alloted" /> :</div></td>
                <td class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="alloted"/></td>
                <td class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.available" /> :</div></td>
                <td class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="available"/></td>
                </tr>
              <tr >
               <td width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.roomNo" />.:</div></td>
                <td class="row-even" ><bean:write name="ExamAssignStudentsToRoomForm" property="roomNo"/></td>
              <td class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.assignStudentsToRoom.comments" />:</div></td>
              <td class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="comments"/></td>
             
  
                </tr>
                <tr >
               <td height="26" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.classname" /> :</div></td>
                <td class="row-even"> <nested:select property="className" styleClass="body"	multiple="multiple" size="8" styleId="className" style="width:250px" onchange="getSubjectName()">
										
										<nested:optionsCollection name="ExamAssignStudentsToRoomForm"
											property="listClassNameMap" label="value" value="key" />
									</nested:select></td>
                <td height="26" class="row-odd"><div align="right" ><span class="Mandatory">*</span>Subject :</div></td>
                <td class="row-even"><nested:select
												property="subjectName" styleId="subjectName" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${ExamAssignStudentsToRoomForm.className!=null && ExamAssignStudentsToRoomForm.className !=''}">
													<c:set var="mapSubjects"
														value="${baseActionForm.collectionMap['mapSubjects']}" />
													<c:if test="${mapSubjects != null}">
														<nested:optionsCollection property="mapSubjects"
															label="value" value="key" />
													</c:if>
												</c:if>
											</nested:select></td>
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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
            <td valign="top"><table width="100%" cellspacing="4" cellpadding="2">
              <tr><td style="height: 10px" align="center" colspan=""></td></tr>
              <tr >
                <td height="25" colspan="7" class="row-white" ><input name="button" type="button" class="formbutton" value="Add Examiners" onClick="addExaminars()" /></td>
                </tr>
			<c:if test="${ExamAssignStudentsToRoomForm.listExaminarSize != 0}" >
              <tr >
                <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno" /> </div></td>
                <td height="25" class="row-odd" ><bean:message key="employee.info.firstName" /></td>
                <td class="row-odd" ><bean:message key="employee.info.lastName" /></td>
                <td class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.remove" /></div></td>
              </tr>
             
             <logic:iterate name="ExamAssignStudentsToRoomForm"
									property="listExaminars" id="listExaminars" indexId="count"
									type="com.kp.cms.to.exam.ExamAssignStudentsToRoomTO">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
						<td class="bodytext"> <div align="center"><c:out value="${count + 1}" /></div> </td>
						<td align="left" class="bodytext"><bean:write name="listExaminars" property="firstName" /></td>
						<td align="left" class="bodytext"><bean:write name="listExaminars" property="lastName" /></td>
						<td align="center" class="bodytext"><div align="center"><img src="images/delete_icon.gif"	height="18" style="cursor: pointer" onclick="deleteExaminars('<bean:write name="listExaminars" property="id" />','<bean:write name="listExaminars" property="firstName" />')"></div></td>
				   </tr>
				</logic:iterate>

	</c:if>

             <tr><td style="height: 10px" align="center" colspan=""></td></tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
                  <td height="25"  class="row-white" ><input name="button" type="button" class="formbutton" value="Add Students" onClick="addStudents()" /></td>
                  <td class="row-white"></td>
                   <td class="row-white"></td>
                   <td class="row-white"></td>
                   <td class="row-white"></td>
                    <td class="row-white"></td>
                     <td class="row-white"></td>
                </tr>
		<c:if test="${ExamAssignStudentsToRoomForm.listStudentsSize!=0}" >
                <tr >
                  <td height="25" width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno" /> </div></td>
                  <td  class="row-odd" >Class</td>
                  <td  class="row-odd" >Subject</td>
                  <td  class="row-odd" >Register No.</td>
                  <td  class="row-odd" >Roll No</td>
                  <td  class="row-odd" >Name</td>
                  <td class="row-odd"><div align="center">Remove</div></td>
                </tr>
               
 				<logic:iterate name="ExamAssignStudentsToRoomForm"
									property="listStudents" id="listStudents" indexId="count"
									type="com.kp.cms.to.exam.ExamAssignStudentsToRoomTO">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
						<td class="bodytext"> <div align="center"><c:out value="${count + 1}" /></div> </td>
						<td align="left" class="bodytext"><bean:write name="listStudents" property="className" /></td>
						<td align="left" class="bodytext"><bean:write name="listStudents" property="subject" /></td>
						<td align="left" class="bodytext"><bean:write name="listStudents" property="registerNo" /></td>
						<td align="left" class="bodytext"><bean:write name="listStudents" property="rollNo" /></td>
						<td align="left" class="bodytext"><bean:write name="listStudents" property="name" /></td>
						<td align="center" class="bodytext"><div align="center"><img src="images/delete_icon.gif"	height="18" style="cursor: pointer" onclick="deleteStudents('<bean:write name="listStudents" property="id" />','<bean:write name="listStudents" property="name" />')"></div></td>
				   </tr>
				</logic:iterate>


		</c:if>


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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
          <tr>
            <td width="45%" height="35" align="right"><input name="Submit2" type="submit" class="formbutton" value="Print Invigilator Dairy" /></td>
            <td width="2%" height="35" align="center"><input name="Submit" type="reset" class="formbutton" value="Submit" onclick="fun();"/></td>
            <td width="45%" align="left"><input name="Submit3" type="reset" class="formbutton" value="Cancel" onclick="fun();"/></td>
          </tr>
          <tr><td style="height: 10px" align="center" colspan="3"></td></tr>          
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


