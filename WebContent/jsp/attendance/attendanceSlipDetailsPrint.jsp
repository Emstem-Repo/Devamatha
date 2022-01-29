<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.List"%><script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" href="css/styles.css">
	<!-- AttendanceSlipDetailsPrint.jsp-->
              <table width="100%"  height="25" border="0" cellpadding="0" cellspacing="0">
                <logic:notEmpty name="PeriodList">
                <tr>
			        <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
	                     <tr height="10">
	                       <table width="100%" style="border: 1px solid black; " rules="all">
					        	<tr height="5">
			                       <td  width="25%" align="center"><h3><bean:message key="knowledgepro.attendanceentry.attendanceslipdetails"/>&nbsp;&nbsp;&nbsp;
			                       	Class Name:<bean:write name="attendanceEntryForm" property="className"/>&nbsp;&nbsp;
			                       (<bean:write name="attendanceEntryForm" property="fromDate"/>&nbsp; to &nbsp;<bean:write name="attendanceEntryForm" property="toDate"/>)</h3>
			                       </td>
			                    </tr>
			               </table>
			               </tr>
			               <tr>
			                 <table width="100%" style="border: 1px solid black; " rules="all">
								<!-- AttedanceSlipDetails.Periods -->
								<tr height="5">	
								<td  align="left" width="15"></td>
								<logic:iterate id="periodList" name="PeriodList" >			                  
								  <td align="center" width="15%" class="rows-hedings">
								  		<bean:write name="periodList"/>
				                  </td>
				                </logic:iterate>
				       			</tr>
				        <logic:notEmpty name="SlipDetails">  
				        <%
				          String date=null;
				        %> 
				          <logic:iterate name="SlipDetails" id="attendanceDate" indexId="counter">
				                <tr height="10">
				                <td align="left" width="8%" class="rows-font">
				                &nbsp;&nbsp;<bean:write name="attendanceDate" property="key"/>
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
				                </td>
				                <logic:iterate id="periodMap" name="attendanceDate" property="value" >
				                <td  width="10%" class="rows-font">
				                 <logic:iterate id="list" name="periodMap" property="value">
				                	&nbsp;&nbsp;<bean:write name="list" property="subject"/> - 
				                	<bean:write name="list" property="teachers"/><br></br>
				                 </logic:iterate>
				                </td>
				                </logic:iterate>
				                </tr>
				         </logic:iterate>
				        </logic:notEmpty> 		 			       
	                       </table>
	                     </tr>
                   </table>
                 </tr>
               
               
                <logic:notEmpty name="attendanceEntryForm" property="subMap"> 
               <tr>
	                       <table width="100%" style="border: 1px solid black; " rules="all">
	                         <logic:iterate id="subjectMap" name="attendanceEntryForm" property="subMap" indexId="count">
	                         <c:if test="${count%3==0}">
	                         <tr>
	                         </c:if>
								<td  width="35%" class="rows-font">
									&nbsp;&nbsp;<bean:write name="subjectMap" property="key"/>=<bean:write name="subjectMap" property="value"/> 	
	                     	   </td>
	                     </logic:iterate>
	                     </table>
                 </tr>
	             </logic:notEmpty>
	             </logic:notEmpty>
               </table> 
                
<!-- List of Slip Details -->
<script type="text/javascript">
	window.print();
</script>
  
