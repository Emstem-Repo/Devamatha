<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script>
function getSemister(selectedYear){
	getSemistersByYear("semistersMap", selectedYear, "semister", updateSemisters);
}
function updateSemisters(req){
	updateOptionsFromMap(req, "semister", "- Select -");
}
function getClasses(semisterNo){
	var academicYear=document.getElementById("academicYear").value;
	getClassesByYearAndSem("classMap",semisterNo, academicYear, "classes", updateClasses);
	
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", "- Select -");
}
</script>

<html:form action="/ClassWiseAttendanceStatus">
<html:hidden property="formName" value="classWiseAttendanceStatusForm"/>
<html:hidden property="method" styleId="method" value="showClassWiseAttendanceStatus"/>
<html:hidden property="pageType" value="1" />


<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance.classWiseAttendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendance.classWiseAttendance.entry"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendance.classWiseAttendance.entry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td >
	       <div align="right" style="color:red" class="heading"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
	       <div id="errorMessage">
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
            <table width="100%" cellspacing="1" cellpadding="1">
                <tr >
                <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="classWiseAttendanceStatusForm" property="year"/>" />
									<html:select 
										property="year" styleId="academicYear"
										styleClass="combo" onchange="getSemister(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
				            <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.fee.semister" /> :</div></td>
					               <td class="row-even" width="25%">
					                  <html:select name="classWiseAttendanceStatusForm" styleId="semister" property="semister" onchange="getClasses(this.value)">
					                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					       		    		<html:optionsCollection name="classWiseAttendanceStatusForm" property="semisterMap" label="value" value="key"/>
					                  </html:select>
				                  </td>
                
                </tr>
                
                <tr>
                <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select name="classWiseAttendanceStatusForm" styleId="classes" property="classes">
					                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
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
									<html:text name="classWiseAttendanceStatusForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'classWiseAttendanceStatusForm',
								// input name
								'controlname' :'fromDate'
							});
						</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text name="classWiseAttendanceStatusForm" property="toDate" styleId="toDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'classWiseAttendanceStatusForm',
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr >
            <td style="height: 10px;" align="center">
            
           		
            </td>
          </tr>
          <tr>
           
                       <td width="45%"><div align="right">
                       <html:submit styleClass="formbutton" value="Submit"></html:submit>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
          </tr>  
        </table></td>
        
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
	                       <table width="100%" cellspacing="1" cellpadding="2" border="1">
					        	<tr>
			                       <td class="row-odd" width="25%" ><div align="center"><h3><bean:message key="knowledgepro.attendance.classWiseAttendance.entry"/>&nbsp;&nbsp;&nbsp;
			                       	Class Name:<bean:write name="classWiseAttendanceStatusForm" property="className"/>&nbsp;&nbsp;
			                       (<bean:write name="classWiseAttendanceStatusForm" property="fromDate"/>&nbsp; to &nbsp;<bean:write name="classWiseAttendanceStatusForm" property="toDate"/>)</h3>
			                       </div></td>
			                    </tr>
			               </table>
			                 <table rules="all" border="2x" cellspacing="8" cellpadding="5" background="lightgreen">
								
								<tr>	
								<td class="row-odd" align="center" width="10%"></td>
								<logic:iterate id="periodList" name="PeriodList" >			                  
								  <td class="row-odd" align="center" width="10%">
								  		<h3><bean:write name="periodList"/></h3>
				                  </td>
				                </logic:iterate>
				       			</tr>
				        <logic:notEmpty name="ClassWiseAttendanceStatusDetails">  
				        <%
				          String date=null;
				        %> 
				          <logic:iterate name="ClassWiseAttendanceStatusDetails" id="attendanceDate" indexId="counter">
				                <tr>
				                <td class="row-even" width="10%"><div align="left">
				                &nbsp;&nbsp;<bean:write name="attendanceDate" property="key"/><br></br>
				                <% date="date_"+counter;%>
								<input type="hidden" id='<%=date%>' name="weekday" value='<bean:write name="attendanceDate" property="key"/>'/>
							<!--  	<script language="javascript">
              						 // Name of the days as Array
              						appDate=document.getElementById("date_<c:out value='${counter}'/>").value;
               						var dayNameArr = new Array ("Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday");
               						var dateArr = appDate.split("/"); // split the date into array using the date seperator
               						var day = eval(dateArr[0]);
               						var month = eval(dateArr[1]);
               						var year = eval(dateArr[2]);
               						var date=new Date(dateArr[1]+"/"+dateArr[0]+"/"+dateArr[2]);
              						// Calculate the total number of days, with taking care of leapyear 
              						var totalDays = day + (2*month) + parseInt(3*(month+1)/5) + year + parseInt(year/4) - parseInt(year/100) + parseInt(year/400) + 2;
              						// Mod of the total number of days with 7 gives us the day number
              						var dayNo = (totalDays%7);
              						// if the resultant mod of 7 is 0 then its Saturday so assign the dayNo to 7
              						if(dayNo == 0){
                   							dayNo = 7;
              						}
              						//document.write("&nbsp;&nbsp;"+dayNameArr[dayNo-1]);
              						document.write("&nbsp;&nbsp;"+dayNameArr[date.getDay()]);
								</script> -->
				                </div></td>
				                <logic:iterate id="periodMap" name="attendanceDate" property="value" >
				                <td class="row-even" width="10%">
				                 <logic:iterate id="list" name="periodMap" property="value">
				                <logic:equal value="true" property="isAttendanceEntered" name="list">
				                 <div align="left" style="background-color:#90EE90;overflow:hidden;width=150;height=80px">
				                	&nbsp;&nbsp;<bean:write name="list" property="subjectName"/><br/>
				                	&nbsp;&nbsp;<bean:write name="list" property="teachers"/><br></br>
				                	</div>
				                 </logic:equal>
				                    <logic:equal value="false" property="isAttendanceEntered" name="list">
				                 <div align="left" style="background-color:#FF6347;overflow:hidden;width=150;height=80px">
				                    &nbsp;&nbsp;<bean:write name="list" property="subjectName"/><br/>
				                	&nbsp;&nbsp;<bean:write name="list" property="teachers"/><br></br>
				                	</div>
				                	</logic:equal>
				                 </logic:iterate>
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
               
	            </logic:notEmpty> 
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
<script>
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("academicYear").value = year;
}
document.getElementById("academicYear").value=document.getElementById("year").value;

</script>