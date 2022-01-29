<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<title>Knowledge Pro</title>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
	<link rel="stylesheet" type="text/css" href="css/calstyle.css">
	<link rel="stylesheet" type="text/css" href="css/displaytag.css">
	<link rel="stylesheet" href="css/calendar.css">
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
    <script type="text/javascript" src="js/basiccalendar.js"></script>
    <script type="text/javascript" src="js/clockh.js"></script>
 	<script type="text/javascript" src="js/clockp.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script language="JavaScript" src="js/calendar_us.js"></script>
</head>
<script type="text/javascript">
function submitData() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    var selectedStudents="";
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   		if(document.getElementById("studentName_"+inputObj.id)!=null){
		   			selectedStudents=selectedStudents+document.getElementById("studentName_"+inputObj.id).value+" \n";
			   		}
		   	}
		}
    }
   var  submitConfirm = confirm("Absentees:"+checkBoxselectedCount+" \n"+selectedStudents);
	if (submitConfirm) {
	document.attendanceEntryForm.submit();
	}
}
function backPage() {
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntrySecondPage";
}

function backToFirstPage() {
	document.location.href="AttendanceEntry.do?method=initAttendanceEntryByTimeTableForEmployeeLogin";
}
function validateCheckBox(countvalue) {
	var isCheck = document.getElementById(countvalue).checked;
	if(isCheck){
		document.getElementById("student_"+countvalue).style.background="red";
	}else{
		document.getElementById("student_"+countvalue).style.background="white";
	}
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
<html:hidden property="method" styleId="method" value="saveAttendanceByTimeTable"/>
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
					<div id="err" style="font-family: verdana;font-size: 14px"></div>
			<div id="errorMessage" style="font-family: verdana;font-size: 14px"><FONT color="red"><html:errors /></FONT>
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
                  <td class="row-odd" >
                  		<div id="teacherdiv" align="right"><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  </td>
                  <td class="row-even" >
						<bean:write name="attendanceEntryForm" property="teacherName"/>
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
            <td><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
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
                        <td width="40" height="25" class="atten-odd" align="center"><bean:message key="knowledgepro.attendance.markabsent"/></td>
                        <td width="160" class="atten-odd" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		Reg No
                        	</c:when>
                        	<c:otherwise>
                        		Roll No
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="135" class="atten-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        
                      </tr>
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count">
                      
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="atten-even">
								</c:when>
									<c:otherwise>
									<tr class="atten-white">
								</c:otherwise>
					 </c:choose>
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
                        	<span id="student_<c:out value='${count}'/>">
                        	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox(<c:out value='${count}'/>)"/>
							</span>
                        </c:otherwise>
                        </c:choose>
						<script type="text/javascript">
							var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
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
                        
                        <td width="212" align="center">
                        <input type="hidden" name="studentName_<c:out value='${count}'/>" id="studentName_<c:out value='${count}'/>" value='<nested:write name="student" property="registerNo"/> ( <nested:write name="student" property="studentName"/> )' />
                        <nested:write name="student" property="studentName"/></td>
                      </tr>
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
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
          <tr>
            <td height="35" align="center">
            <html:button property="" styleClass="formbutton" value="                    Submit                   " onclick="submitData()"></html:button>
            &nbsp;&nbsp;&nbsp;
            <c:choose>
            <c:when test="${attendanceEntryForm.isSecondPage == true}">
            	<html:button property="" styleClass="formbutton" value="                   Cancel                 " onclick="backPage()"></html:button>
            </c:when>
            <c:otherwise>
            	<html:button property="" styleClass="formbutton" value="                   Cancel                  " onclick="backToFirstPage()"></html:button>
            </c:otherwise>
            </c:choose>
            </td>			
          </tr>
        <tr><td style="height: 10px" align="center" colspan="1"></td></tr>          
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