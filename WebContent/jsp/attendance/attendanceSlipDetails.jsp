<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.List"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.GOTO"%><script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<!-- attendanceSlipDetails.jsp -->
<script type="text/javascript">

function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}

function searchAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value = "cancelAttendanceSearch";
	document.attendanceReentryForm.submit();
}

function selectAll(obj) {
	value = obj.checked;
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		inputObj.checked = value;
		}
    }
}

function unCheckSelectAll() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox' && inputObj.id != "checkAll") {
	   		checkBoxOthersCount++;
	   		if(inputObj.checked) {
	   			checkBoxOthersSelectedCount++;
	   		}	
		}
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
    	document.getElementById("checkAll").checked = false;
    } else {
    	document.getElementById("checkAll").checked = true;
    }        
}

function cancelAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value ="cancelSelectedAttendance";
	document.attendanceReentryForm.submit();
}

function getAttendanceClasses(classes){
	classes =  document.getElementById("classes");
	if(classes.selectedIndex != -1) {
		var selectedClasses = new Array();
		var count = 0;
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	selectedClasses[count] = classes.options[i].value;
		      count++;
		    }
		 }	
		if(teachers.selectedIndex != -1) {
			getClassesByYearInMuliSelect("classMap", year, "classes", updateClasses);
		} 
	}
}
function printICard(){
		var url ="attendanceSlipDetails.do?method=printAttendanceSlipDetails";
		myRef = window.open(url,"attendanceSlipDetails","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
   }
function cancelSlipPrint(){
	document.getElementById("method").value = "initAttendanceSlipDetails";
	document.attendanceEntryForm.submit();
}
</script>

<html:form action="/attendanceSlipDetails" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value="getAttendanceSlipDetails"/>
<html:hidden property="pageType" value="3"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendanceentry.attendanceslipdetails"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendanceentry.attendanceslipdetails"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
               	    <div id="errorMessage">
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
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
					        	<tr>
			                       <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceEntryForm" property="year"/>"/>
				                           <html:select property="year" styleId="academicYear" name="attendanceEntryForm" styleClass="combo" onchange="getClasses(this.value)">
		                       	   				<!--  <html:option value=""><bean:write name="attendanceEntryForm" property="year"/></html:option> -->
		                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
		                       			   </html:select>
				        			</td>
				                  <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select name="attendanceEntryForm" styleId="classes" property="classes">
					                  <html:option value="">Select</html:option>
					                 	    <c:if test="${classMap != null}">
					       		    		<html:optionsCollection property="classMap" label="value" value="key"/>
					       		    		</c:if>
					                  </html:select>
				                  </td>
				                </tr>
				                <tr>
				                	<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.fromdate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="attendanceEntryForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'attendanceEntryForm',
								// input name
								'controlname' :'fromDate'
							});
						</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text property="toDate" styleId="toDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'attendanceEntryForm',
											// input name
											'controlname' :'toDate'
										});
									</script>
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
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="center">
                       <html:submit styleClass="formbutton" value="Search"></html:submit>
                       </div></td>
                     </tr>
                   </table>
                   </td>
                </tr>
                <logic:notEmpty name="PeriodList">
                <tr>
                    <td  colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
					        	<tr>
			                       <td class="row-odd" width="25%" ><div align="center"><h3><bean:message key="knowledgepro.attendanceentry.attendanceslipdetails"/>&nbsp;&nbsp;&nbsp;
			                       	Class Name:<bean:write name="attendanceEntryForm" property="className"/>&nbsp;&nbsp;
			                       (<bean:write name="attendanceEntryForm" property="fromDate"/>&nbsp; to &nbsp;<bean:write name="attendanceEntryForm" property="toDate"/>)</h3>
			                       </div></td>
			                    </tr>
			               </table>
			                 <table>
								<!-- AttedanceSlipDetails.Periods -->
								<tr>	
								<td class="row-odd" align="center" width="10%"></td>
								<logic:iterate id="periodList" name="PeriodList" >			                  
								  <td class="row-odd" align="center" width="10%">
								  		<h3><bean:write name="periodList"/></h3>
				                  </td>
				                </logic:iterate>
				       			</tr>
				        <logic:notEmpty name="SlipDetails">  
				        <%
				          String date=null;
				        %> 
				          <logic:iterate name="SlipDetails" id="attendanceDate" indexId="counter">
				                <tr>
				                <td class="row-even" width="10%"><div align="left">
				                &nbsp;&nbsp;<bean:write name="attendanceDate" property="key"/><br></br>
				                <% date="date_"+counter;%>
								<input type="hidden" id='<%=date%>' name="weekday" value='<bean:write name="attendanceDate" property="key"/>'/>
								<script language="javascript">
              						 // Name of the days as Array
              						appDate=document.getElementById("date_<c:out value='${counter}'/>").value;
               						var dayNameArr = new Array ("Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday");
               						var dateArr = appDate.split("/"); // split the date into array using the date seperator
               						var day = eval(dateArr[0]);
               						var month = eval(dateArr[1]);
               						var year = eval(dateArr[2]);
              						// Calculate the total number of days, with taking care of leapyear 
              						var totalDays = day + (2*month) + parseInt(3*(month+1)/5) + year + parseInt(year/4) - parseInt(year/100) + parseInt(year/400) + 2;
              						// Mod of the total number of days with 7 gives us the day number
              						var dayNo = (totalDays%7);
              						// if the resultant mod of 7 is 0 then its Saturday so assign the dayNo to 7
              						if(dayNo == 0){
                   							dayNo = 7;
              						}
              						document.write("&nbsp;&nbsp;"+dayNameArr[dayNo-1]);
								</script>
				                </div></td>
				                <logic:iterate id="periodMap" name="attendanceDate" property="value" >
				                <td class="row-even" width="10%"><div align="left">
				                 <logic:iterate id="list" name="periodMap" property="value">
				                	&nbsp;&nbsp;<bean:write name="list" property="subject"/> - 
				                	<bean:write name="list" property="teachers"/><br></br>
				                 </logic:iterate>
				                </div>
				                </td>
				                </logic:iterate>
				                </tr>
				         </logic:iterate>
				        </logic:notEmpty> 	
	                    </table>
	                   </td>
	                     <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
	                     <tr>
					        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					     </tr>
                     <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </td>
                 </tr>
               
               
               
               <logic:notEmpty name="attendanceEntryForm" property="subMap"> 
               <tr>
                    <td  colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table>
	                         <logic:iterate id="subjectMap" name="attendanceEntryForm" property="subMap" indexId="count">
	                         <c:if test="${count%3==0}">
	                         <tr>
	                         </c:if>
								<td class="row-even" width="33%"><div align="left">
									&nbsp;&nbsp;<bean:write name="subjectMap" property="key"/>=<bean:write name="subjectMap" property="value"/> 	
									</div>
	                     	   </td>
	                     </logic:iterate>
	                     </table>
	                     </td>
	                     <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
	                     <tr>
					        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					     </tr>
                     <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" /></td>
                     </tr>
                     </table>
                   </td>
                 </tr>
	             </logic:notEmpty>
	             </logic:notEmpty>
               </table> 
                
<!-- List of Slip Details -->
	          </td>
	          <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	         
  </tr>
  <tr>
  	 <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
        <logic:notEmpty name="PeriodList">
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                     <tr>
                       <td width="45%"><div align="center">
                       <html:button property="" styleClass="formbutton" value="Print" onclick="printICard()"></html:button>
					    <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelSlipPrint()"></html:button>
					   </div></td>
                     </tr>
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>                     
         </logic:notEmpty>
                   </table>
        
  		 </td>
	 <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
  </tr>
   
  <tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
</table></td></tr>
</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
	document.getElementById("academicYear").value = year;
}
</script>
