<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" language="javascript">
function assiginRoom(id){
	var examType=document.getElementById("examType").value;
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;
	
	document.location.href = "ExamAssignStudentsToRoom.do?method=Add&examType="+examType+"&examNameId="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&roomId="+id+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder;
}
function addMarkAttendance(id){
	var examType=document.getElementById("examType").value;
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;
	
	document.location.href = "ExamAssignStudentsToRoom.do?method=addMarkAttendance&examType="+examType+"&examNameId="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&roomId="+id+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder;	
}

function deleteStudents(id,name){
	
	var examType=document.getElementById("examType").value;
	var examNameId=document.getElementById("examNameId").value;
	var date=document.getElementById("date").value;
	var hr=document.getElementById("hr").value;
	var min=document.getElementById("min").value;
	var nonEligible=document.getElementById("nonEligible").value;
	var displayOrder=document.getElementById("displayOrder").value;

	var roomNo=document.getElementById("roomNo").value;
	
	deleteConfirm =confirm("Are you sure to delete "+ name +" entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamAssignStudentsToRoom.do?method=deleteRooms&id="+id+"&examType="+examType+"&examNameId="+examNameId+"&date="+date+"&hr="+hr+"&min="+min+"&roomNo="+id+"&nonEligible="+nonEligible+"&displayOrder="+displayOrder+"&roomNo="+roomNo;
	}
}
</script>

<html:form action="/ExamAssignStudentsToRoom.do" method="post">
	
	<html:hidden property="formName" value="ExamAssignStudentsToRoomForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="examType" styleId="examType" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="date" styleId="date" />
	<html:hidden property="hr" styleId="hr" />
	<html:hidden property="min" styleId="min" />
	<html:hidden property="roomNo" styleId="roomNo" />
	<html:hidden property="roomId" styleId="roomId" />
	<html:hidden property="nonEligible" styleId="nonEligible" />
	<html:hidden property="displayOrder" styleId="displayOrder" />
    

<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.assignStudentsToRoom" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><p><strong class="boxheader"> <bean:message key="knowledgepro.exam.assignStudentsToRoom" /> </strong></p>          </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
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
                <td height="25" align="center" class="row-odd" ><bean:message key="knowledgepro.slno" />.</td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.roomNo" />.</td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.examDefinition.examName" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.totalCapacity" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.alloted" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.date" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.time" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.assign" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.markAttendance" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.assignStudentsToRoom.removeRoom" /></td>
              </tr>
<logic:notEmpty  name="ExamAssignStudentsToRoomForm"
									property="listRoomDetails">
				<logic:iterate name="ExamAssignStudentsToRoomForm"
									property="listRoomDetails" id="listRoomDetails" indexId="count"
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
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="roomNo" /></td>
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="examName" /></td>
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="roomCapacity" /></td>
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="alloted" /></td>
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="date" /></td>
						<td align="center" class="bodytext"><bean:write name="listRoomDetails" property="time" /></td>
						
						<td align="center" class="bodytext"><div align="center"><img src="images/edit_icon.gif"	height="18" style="cursor: pointer" onclick="assiginRoom('<bean:write name="listRoomDetails" property="roomId" />')"></div></td>
						<td align="center" class="bodytext"><div align="center"><img src="images/mark_atten.gif"	height="18" style="cursor: pointer" onclick="addMarkAttendance('<bean:write name="listRoomDetails" property="roomId" />')"></div></td>
						<td align="center" class="bodytext"><div align="center"><img src="images/delete_icon.gif"	height="18" style="cursor: pointer" onclick="deleteStudents('<bean:write name="listRoomDetails" property="roomId" />','<bean:write name="listRoomDetails" property="roomNo" />')"></div></td>
				   </tr>
				</logic:iterate>
</logic:notEmpty>
        
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>


