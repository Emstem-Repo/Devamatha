<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function submitData() {
	document.attendanceEntryForm.submit();
}
function backPage() {
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntrySecondPage";
}

function backToFirstPage() {
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntryFirstPage";
}
function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Number of Absentees is:"+checkBoxselectedCount;
            
}
</script>

<html:form action="/AttendanceEntry" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value="saveAttendance"/>
<html:hidden property="pageType" value="1"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendanceentry.entry"/></span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendance.attendanceentry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            
            
            <table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                	<td width="13%" class="row-odd" >
                  		<div id="teacherdiv" align="right"><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  </td>
                  <td width="18%" class="row-even" >
						<bean:write name="attendanceEntryForm" property="attendanceTeacher"/>
                  </td>
                     <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
                  <td class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="row-even" align="left">
                      	<bean:write name="attendanceEntryForm" property="attendancedate"/>
                      </td>
                    </tr>
                  </table></td>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		          <td class="row-even" align="left">
		                    <bean:write name="attendanceEntryForm" property="acaYear"/>       
		        	</td>
                </tr>
                <tr >
                  <td class="row-odd" >
                	 	 <div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
                  </td>
                  <td class="row-even" >
                  	 
                  	 <c:out value="${attendanceEntryForm.attendanceClass}"/> 
                  </td>
                  <td width="18%" height="25" class="row-odd" >
                    	<div id="subjectdiv" align="right"><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                  </td>
                  <td width="19%" class="row-even" >
                    	<bean:write name="attendanceEntryForm" property="attendanceSubject"/> 
                  </td>
                  <td height="25" class="row-odd" >
                  <div align="right"><bean:message key="knowledgepro.attendanceentry.type"/>:</div></td>
                  <td class="row-even" align="left">
                  	<bean:write name="attendanceEntryForm" property="attenType"/>
                  </td>
               
                  
                </tr>
                <tr >
                  <td width="14%" class="row-odd" >
                  	    <div id="perioddiv" align="right"><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
                  </td>
                  <td width="18%" class="row-even" >
						<bean:write name="attendanceEntryForm" property="attendancePeriod"/>
                  </td>
                  <td height="25" class="row-odd" colspan="2"><div align="right"><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></td>
                  <td class="row-even" colspan="2">
                 	<bean:write name="attendanceEntryForm" property="hoursHeld"/>
                  </td>
              <!--    <td class="row-odd" >
                    	<div id="batchdiv" align="right"><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
                  </td>
                  <td class="row-even" >
						<bean:write name="attendanceEntryForm" property="batchNameWithCode"/>	
                  </td>-->
                </tr>
                <tr style="display: none;">
                <td class="row-odd" >
                    	<div id="activitydiv" align="right"><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
                  </td>
                  <td class="row-even" >
                  	<bean:write name="attendanceEntryForm" property="attendanceActivity"/>
                  </td>
                   <td class="row-odd" >&nbsp;
                   </td>
                   <td class="row-even" >&nbsp;
                   </td>
                   <td class="row-odd" >&nbsp;
                   </td>
                   <td class="row-even" >&nbsp;
                   </td>
                  
                </tr>
                
              </table>
              
              
              
             </td>
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
                  
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                      <td height="25" class="row-odd" align="center">
						<div align="center"><bean:message key="knowledgepro.slno" />
						</div>
					 </td>
                      
                         <td width="160" class="row-odd" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<font style="font-size: 11px">Reg No</font>
                        	</c:when>
                        	<c:otherwise>
                        		<font style="font-size: 11px">Roll No</font>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.markabsent"/></td>
                       
                      </tr>
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count">
                      
                      <c:if test="${count < attendanceEntryForm.halfLength}">
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					 <td height="25" align="center">
						<div align="center"><c:out value="${count+1}" /></div>
					</td>
												
					         <td width="193" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<nested:write name="student" property="registerNo"/>
                        	</c:when>
                        	<c:otherwise>
                        		<nested:write name="student" property="rollNo"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        
                        <td height="25" align="center" >
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        <c:choose>
                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
                        <input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" disabled="disabled" />
                        </c:when>
                        <c:otherwise>
                        	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
                        </c:otherwise>
                        </c:choose>
						<script type="text/javascript">
							var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                
                      </tr>
                      </c:if>
                      </nested:iterate>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
              
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                      <td height="25" class="row-odd" align="center">
						<div align="center"><bean:message key="knowledgepro.slno" />
						</div>
					 </td>
                        <td width="160" class="row-odd" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<font style="font-size: 11px">Reg No</font>
                        	</c:when>
                        	<c:otherwise>
                        		<font style="font-size: 11px">Roll No</font>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.markabsent"/></td>
                        
                      </tr>
                      <c:set var="c" value="0"/>
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count">
                      <c:set var="c" value="${c + 1}"/>
                      <c:if test="${count >= attendanceEntryForm.halfLength}">
                      <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					 
					 <td width="8%" height="25"  align="center">
					<div align="center"><c:out value="${count+1}" /></div>
					</td>
												
					      <td width="193" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<nested:write name="student" property="registerNo"/>
                        	</c:when>
                        	<c:otherwise>
                        		<nested:write name="student" property="rollNo"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        <td height="25" align="center">
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        
                        <c:choose>
                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
                        <input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" disabled="disabled"/>
                        </c:when>
                        <c:otherwise>
                        	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
                        </c:otherwise>
                        </c:choose>
                        
						<script type="text/javascript">
							var studentId1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                   
                      </tr>
                      </c:if>
                      </nested:iterate>
                      <c:if test="${(c % 2) != 0}" >
                      <tr  class="row-white">
                        <td width="193"  >&nbsp;</td>
                        <td width="212" >&nbsp;</td>
                        <td height="25" align="center" >&nbsp;
                      </td>
                      </tr>
                      </c:if>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr >
            <td style="height: 10px;" align="center" colspan="2">
            
           		
            </td>
          </tr>
          <tr>
          	<td width="42%"><div id="err"></div></td>
            <td height="35" >&nbsp;
            <html:button property="" styleClass="formbutton" value="Submit" onclick="submitData()"></html:button>
            &nbsp;
            <c:choose>
            <c:when test="${attendanceEntryForm.isSecondPage == true}">
            	<html:button property="" styleClass="formbutton" value="Cancel" onclick="backPage()"></html:button>
            </c:when>
            <c:otherwise>
            	<html:button property="" styleClass="formbutton" value="Cancel" onclick="backToFirstPage()"></html:button>
            </c:otherwise>
            </c:choose>
            </td>
			
          </tr>
        </table>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
<script language="JavaScript" >
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Number of Absentees is:"+checkBoxselectedCount;
</script>

