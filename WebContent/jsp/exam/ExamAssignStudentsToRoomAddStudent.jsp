<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
	

	function selectAll() {
		
		
		var totalCount=document.getElementById("listStudentsSize").value;
		var examinersSelected = "";
		var selectedcount = 0;
		for ( var i = 0; i < totalCount; i++) {
			if (document.getElementById("selectall").checked == true) {
				document.getElementById("check_" + i).checked = true;
				examinersSelected = examinersSelected
						+ document.getElementById("check_" + i).value + ",";
			} else if (document.getElementById("selectall").checked == false) {
				document.getElementById("check_" + i).checked = false;
			}
			selectedcount++;

		}
		
		document.getElementById("examiner").value = examinersSelected;
	}

	function setChkBoxValues(value) {
		var examinersSelected = "";
		examinersSelected = examinersSelected + value + ",";
		document.getElementById("studentId").value = examinersSelected;

	}
	function fun()
	{
		document.location.href="ExamAssignStudentsToRoom.do?method=initExamAssignStudentsToRoom";
	}
</script>


<html:form action="/ExamAssignStudentsToRoom" method="post">
	<html:hidden property="studentId" styleId="studentId" />

<html:hidden property="listStudentsSize" styleId="listStudentsSize" />


	<html:hidden property="formName" value="ExamAssignStudentsToRoomForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="examType" styleId="examType" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="date" styleId="date" />
	<html:hidden property="hr" styleId="hr" />
	<html:hidden property="min" styleId="min" />
	<html:hidden property="roomId" styleId="roomId" />

	<html:hidden property="nonEligible" styleId="nonEligible" />
	<html:hidden property="displayOrder" styleId="displayOrder" />

	<html:hidden property="classId" styleId="classId" />
	<html:hidden property="subjectId" styleId="subjectId" />
    
	<html:hidden property="available" styleId="available" />

	<c:choose>
		<c:when
			test="${examAssignOperation != null && examAssignOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="addStudentsToRoom" />
			</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="getStudents" />
		</c:otherwise>
	</c:choose>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">Exam >> Student Marks Edit&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Student Marks Edit  </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="23%" height="25" class="row-odd" ><div align="right">Exam Name:</div></td>
                  <td width="23%" height="25" class="row-even" ><bean:write name="ExamAssignStudentsToRoomForm" property="examName"/></td>
                  <td width="28%" class="row-odd"><div align="right">Date &amp; Time:</div></td>
                  <td width="26%" class="row-even"><bean:write name="ExamAssignStudentsToRoomForm" property="date"/> <bean:write name="ExamAssignStudentsToRoomForm" property="time"/></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">Available :</div></td>
                  <td height="25" class="row-even" ><bean:write name="ExamAssignStudentsToRoomForm" property="available"/></td>
                  <td class="row-odd"><div align="right"><span class="Mandatory">*</span> No. of Students:</div></td>
                  <td class="row-even"><html:text property="noOfStudents" styleId="noOfStudents" maxlength="3" styleClass="TextBox" size="20" onkeypress="return isNumberKey(event)" /></td>
                </tr>
                <tr >
                  <td height="25" colspan="4" class="row-white" >Note : All unassigned eligible students to write exams for that date and time will be displayed.</td>
                  </tr>
            </table></td>
            <td width="5" background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr><td style="height: 10px" align="center" colspan="6"></td></tr>
          <tr>

		<c:choose>
		<c:when
			test="${examAssignOperation != null && examAssignOperation == 'edit'}">
			 <td width="49%" height="35" align="right"><input  type="submit" class="formbutton" value="Search" disabled="disabled" /></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><input type="reset" class="formbutton" value="Reset" disabled="disabled"/></td>
        </c:when>
		<c:otherwise>
			 <td width="49%" height="35" align="right"><input  type="submit" class="formbutton" value="Search" /></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><input type="reset" class="formbutton" value="Reset" onclick="fun();"/></td>
        
		</c:otherwise>
	</c:choose>

             </tr>         
             <tr><td style="height: 10px" align="center" colspan="6"></td></tr>             
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
	<c:if test="${ExamAssignStudentsToRoomForm.listStudentsSize != 0}" >
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                
                  <td height="25" class="row-odd" ><div align="center">Select all 
                      <br>
                      <input type="checkbox"
										name='selectcheckall' id="selectall"
										onclick="selectAll()">
                  </div></td>
                  <td height="25" class="row-odd" >Class</td>
                  <td class="row-odd" >Subject</td>
                  <td class="row-odd" >Register No.</td>
                  <td class="row-odd" >Roll No</td>
                  <td class="row-odd" >Student Name</td>
                  </tr>
               <nested:iterate 	property="listStudents"  indexId="count">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
						<td class="bodytext"> <div align="center">
						<%
						  String s2 = "hidden_" + count;
						  String s1 = "check_" + count;
						 
						 
						%> 
						<nested:hidden styleId='<%=s2%>' property="isCheckedDummy" name="listStudents" /> 

						<nested:checkbox property="isChecked" styleId='<%=s1%>' />
						<script type="text/javascript">
							var v = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if (v=='true') {
													document.getElementById("check_<c:out value='${count}'/>").checked = true;
								}
							
						</script></div> </td>
						<td align="left" class="bodytext"><nested:hidden property="classId" /><nested:write  property="className" /></td>
						
						<td align="left" class="bodytext"><nested:write  property="subject" />
						<nested:hidden property="subjectId" /></td>
						<td align="left" class="bodytext"><nested:write  property="registerNo" /></td>
						<td align="left" class="bodytext"><nested:write  property="rollNo" /></td>
						<td align="left" class="bodytext"><nested:write property="name" /></td>
						
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">
			<input name="Submit" type="submit" class="formbutton" value="Add"  /></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left">
			<input name="Submit3" type="reset" class="formbutton" value="Cancel" onclick="fun()"/></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
	</c:if>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
	
    </table></td>
  </tr>
</table>



</html:form>


