<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
function cancelAction(){
	document.location.href="LoginAction.do?method=cancelLoginAction";
}
function increaseTime(){
	var destination5=document.getElementById("additionalPeriodIds");
	var hours=1;
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		if (destination5.options[x1].selected) {
	    	hours= hours+1;
	    }
	}	
	document.getElementById("addHours").innerHTML=hours;
	document.getElementById("hoursHeld").value=hours;
}
var hook = true;
$(function() {
	 $("a,.formbutton,img,input:checkbox").each(function(){
		var method = $(this).attr("onclick"); 
		if(method != undefined){
			method ="appendMethodOnBrowserClose(),"+method;
		}else{
			method ="appendMethodOnBrowserClose()";
		}
			
		$(this).attr("onclick",method);
	 });
	});

function appendMethodOnBrowserClose(){
	hook = false;
}
$(function() {
	 $("a,.formbutton").click(function(){
		hook =false;
	  });
});
$(document).keydown(function(event) {
    var keycode = event.keyCode;
    if(keycode == '116') {
       return false;
    }
});
window.onbeforeunload = confirmExit;
  function confirmExit()
  {
	if(hook){
			var deleteConfirm = confirm("You have attempted to leave this page.  If you continue, the current session will be inactive?");
			if (deleteConfirm == true) {
				document.location.href = "LogoutAction.do?method=logout";
				return true;	
			}else{
				return false;
			}
		}else{
			hook =true;
		}
  }

</script>
<html:form action="/AttendanceEntry" method="post">

	<html:hidden property="formName" value="attendanceEntryForm" />
	<html:hidden property="method" styleId="method" value="initAttendanceEntrySecondPageByTimeTable"/>
	<html:hidden property="pageType" value="1"/>
	<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendanceentry.entry"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendanceentry.entry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td >
	       <div align="right" style="color:red" class="heading"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
	       <div id="errorMessage" style="font-family: verdana;font-size: 10px">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	   </div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" >
        
        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <logic:equal value="false" name="attendanceEntryForm" property="timeTableAvailable">
            	<table width="100%" cellspacing="1" cellpadding="2">
            		<tr>
            			<td>
            				Time Table is Not Available (OR) Permitted Time For Attendance Entry is Over
            			</td>
            		</tr>
            	</table>
            </logic:equal>
            <logic:equal value="true" name="attendanceEntryForm" property="timeTableAvailable">
            <table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" >
                  <div align="right"><bean:message key="knowledgepro.attendanceentry.type"/>:</div></td>
                  <td class="row-even" align="left">
                  	<bean:write name="attendanceEntryForm" property="attenType"/>
                  </td>
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
                  <td class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="row-even" align="left">
                      	<bean:write name="attendanceEntryForm" property="attendancedate"/>
                      </td>
                    </tr>
                  </table></td>
                  
                </tr>
                <tr >
                <td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		          <td class="row-even" align="left">
		                    <bean:write name="attendanceEntryForm" property="acaYear"/>       
		        	</td>
                  <td class="row-odd" >
                	 	 <div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
                  </td>
                  <td class="row-even" >
                  	 
                  	 <c:out value="${attendanceEntryForm.attendanceClass}"/> 
                  </td>
                </tr>
                <tr>
                	<td width="18%" height="25" class="row-odd" >
                    	<div id="subjectdiv" align="right"><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                  </td>
                  <td width="19%" class="row-even" >
                    	<bean:write name="attendanceEntryForm" property="attendanceSubject"/> 
                  </td>
                  <td width="13%" class="row-odd" >
                  		<div id="teacherdiv" align="right"><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  </td>
                  <td width="18%" class="row-even" >
						<bean:write name="attendanceEntryForm" property="teacherName"/>
                  </td>
                </tr>
                <tr >
                  <td width="14%" class="row-odd" >
                  	    <div id="perioddiv" align="right"><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
                  </td>
                  <td width="18%" class="row-even" >
						<bean:write name="attendanceEntryForm" property="attendancePeriod"/>
                  </td>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></td>
                  <td class="row-even" >
                  	<html:hidden name="attendanceEntryForm" property="hoursHeld" styleId="hoursHeld"/>
                  	<div id="addHours">
                 	<bean:write name="attendanceEntryForm" property="hoursHeld"/></div>
                  </td>
                </tr>
                <tr>
                <td class="row-odd" >
                    	<div id="batchdiv" align="right"><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
                  </td>
                  <td class="row-even" >
						<bean:write name="attendanceEntryForm" property="attendanceBatchName"/>	
                  </td>
                <td class="row-odd" >
                    	<div id="activitydiv" align="right"><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
                  </td>
                  <td class="row-even" >
                  	<bean:write name="attendanceEntryForm" property="attendanceActivity"/>
                  </td>
                </tr>
                <tr>
                <td class="row-odd" >
                    	<div id="batchdiv" align="right">Additional Teacher:</div>
                  </td>
                  <td class="row-even" >
						<html:select property="additionalUserIds" styleId="additionalUserIds" styleClass="combo" multiple="multiple" style="width:200px;height:80px">
							<logic:notEmpty name="attendanceEntryForm" property="additionalUserMap">
								<html:optionsCollection name="attendanceEntryForm" property="additionalUserMap" label="value" value="key" />
							</logic:notEmpty>
						</html:select>		
                  </td>
                  <td class="row-odd" align="right">
                    	Additional Periods:
                  </td>
                  <td class="row-even" >
	                  <html:select property="additionalPeriodIds" styleId="additionalPeriodIds" styleClass="combo" multiple="multiple" style="width:200px;height:80px" onchange="increaseTime()">
							<logic:notEmpty name="attendanceEntryForm" property="additionalPeriods">
								<html:optionsCollection name="attendanceEntryForm" property="additionalPeriods" label="value" value="key" />
							</logic:notEmpty>
						</html:select>
                  </td>
                </tr>
              </table>
              </logic:equal>
              </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
          <tr>
            <td height="35" align="center">
            <logic:equal value="true" name="attendanceEntryForm" property="timeTableAvailable">
           		<html:submit value="             Next           " styleClass="formbutton" property=""></html:submit>
        	</logic:equal>
           		<html:button value="             Cancel           " styleClass="formbutton" property="" onclick="cancelAction()"></html:button>
            </td>
          </tr>
        <tr><td style="height: 10px" align="center" colspan="1"></td></tr>          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table></td>
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