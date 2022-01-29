<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
function searchAttendance() {

	var obj1= document.getElementById("classes").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	
	document.getElementById("method").value = "modifyAttendanceSearch";
	document.attendanceEntryForm.submit();
}

function editAttendance(id) {
	document.getElementById("attendanceId").value=id;
	document.getElementById("method").value ="modifyAttendance";
	document.attendanceEntryForm.submit();
}

function getClasses() {
	var isTeacherLogin = document.getElementById("isTeacherLogin").value;
	if(isTeacherLogin == 'true') {
		var attendancedate = document.getElementById("attendancedate").value;
		var userId = document.getElementById("userId").value;
		getClassMapForTeacherAndDate("classMap", userId, attendancedate, "classes", updateClasses);
	}
}

function updateClasses(req) {
	updateOptionsFromMapForMultiSelect(req,"classes");
}
</script>

<html:form action="/AttendanceEntry" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value="modifyAttendanceSearch"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="classSelectedIndex" styleId="classSelectedIndex"/>
<html:hidden property="attendanceId" styleId="attendanceId"/>
<html:hidden property="isTeacherLogin" styleId="isTeacherLogin"/>
<html:hidden property="userId" styleId="userId"/>
	<head>
	<style>

	
	</style>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap DatePicker</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script><!--
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    --><script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<style>
	img.tcalIcon {
    cursor: pointer;
    margin-left: 286px;
	margin-top: -40;
	}
	</style>


	</head>
	<!--<div class="pageheader">
                        <div class="media">
                            <div class="Container">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
                                    <li><a href="#">Attendance</a></li>
                                    <li> Modify Attendance</li>
                                </ul>
                                
                            </div>
                        </div> media 
                    </div> pageheader -->
					<!--<div class="contentpanel">
                        
                      -->
                      <div class="panel-body">
                        <div class="panel panel-primary">
                         <div class="panel-heading">
                         <h1 class="panel-title">Modify Attendance</h1>
                         
                         </div>
     
                         </div>
	
 <div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
       <div class="row">
    <div class="col-sm-12">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><b><bean:message key="knowledgepro.mandatoryfields"/></b></span></div>
               	 
               	    <div id="err" style="color:red;font-family:arial;font-size:15px;"></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	   
               <div class="col-md-12">
                 <div class="col-md-6">
	                  <div class="form-group">
				                 <span class='MandatoryMark' style="color:red">*</span><b><bean:message key="knowledgepro.attendanceentry.date"/>:</b>				                				                
				                    
				                  				                 
                   
                   <html:text  size="15" name="attendanceEntryForm" styleId="attendancedate" property="attendancedate" styleClass="form-control" onchange="getClasses()" style="width: 282px;"/>
                  
                  				                     
				           <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'attendanceEntryForm',
													// input name
													'controlname' :'attendancedate'
												});
											</script>
											
											</div>
                  </div>
												                     
				                     <!--
				                      <div class="col-sm-1"><script language="JavaScript">
											new tcal ({
												// form name
												'formname': 'attendanceEntryForm',
												// input name
												'controlname': 'attendancedate'
											});</script></div>
				            
				                 -->
				                 <div class="col-md-6">
                  <div class="form-group">
			                 		 <div id="classsdiv"><span class='MandatoryMark'style="color:red">*</span><b><bean:message key="knowledgepro.attendanceentry.class"/>:</b></div>
					              
					             
					                  <html:select name="attendanceEntryForm" styleId="classes" property="classes" size="5" multiple="multiple" styleClass="form-control">
					       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
					                  </html:select>
				                  </div>
				               </div>
				              
	                    
				                 <div class="col-sm-12 text-center">  
                       <div class="form-group">
	              	 		<button type="button" class="btn btn-success" value="Search" onclick="searchAttendance()">Search</button>
                   	 		<button type="button" class="btn btn-danger" value="Reset" onclick="resetFieldAndErrMsgs()">Reset</button>
                      </div>
               </div>
                <div class="col-sm-12"> 
                    <div class="form-group">
                  
                    <logic:notEmpty name="attendanceEntryForm" property="attendanceList">
					<div class="table-responsive">
					<table class="table table-bordered">
	                    <tr style="background-color: #618685; color:white;">
		                      <td> <div align="center"><b><bean:message key="knowledgepro.slno"/></b></div></td>
		                   <td> <div align="center"><b><bean:message key="knowledgepro.attendanceentry.subject"/></b></div></td>
		                   <td>     <div align="center"><b>Teachers</b></div></td>
		                   <td>     <div align="center"><b><bean:message key="knowledgepro.cancelattendance.periods"/></b></div></td>
		                     <td>   <div align="center"><b><bean:message key="knowledgepro.cancelattendance.batches"/></b></div></td>
		                      <td>   <div align="center"><b>Last Modified</b> </div></td>
		                    <td>  <div align="center"><b>Last Modified Date </b></div></td>
		                 <td> <div align="center"><b>Edit</b></div></td>
		               </tr>
	                       <c:set var="temp" value="0"/>
	                       <logic:iterate id="attendance" name="attendanceEntryForm" property="attendanceList" type="com.kp.cms.to.attendance.AttendanceTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           <tr>
				                      <td>  <div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td><div align="center"><bean:write property="subject" name="attendance"/></div></td>
				                     <td>    <div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div></td>
				                       <td> <div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td>
				                       <td>  <div align="center"><bean:write property="batch" name="attendance"/></div></td>
				                       <td>  <div align="center"><bean:write property="modifiedBy" name="attendance"/></div></td>
				                      <td> <div align="center"><bean:write property="lastModifiedDate" name="attendance"/></div></td>
				                     <td>  <div align="center"><i class="glyphicon glyphicon-edit" width="16" style="cursor:pointer" height="18" onclick="editAttendance('<bean:write name="attendance" property="id"/>')"></i></div></td>
				                  </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                   <tr>
			             			  <td><div align="center"><c:out value="${count + 1}"/></div></td>
			             			   <td> <div align="center"><bean:write property="subject" name="attendance"/></div></td>
				                       <td> <div align="center"><c:out value="${attendance.teachers}" escapeXml="false"/></div> </td>
				                        <td> <div align="center"><c:out value="${attendance.periods}" escapeXml="false"/></div></td> 
				                       <td> <div align="center"><bean:write property="batch" name="attendance"/></div> </td>
				                       <td>  <div align="center"><bean:write property="modifiedBy" name="attendance"/></div> </td>
				                       <td>  <div align="center"><bean:write property="lastModifiedDate" name="attendance"/></div> </td>
				                        <td> <div align="center"><i class="glyphicon glyphicon-edit" width="16" style="cursor:pointer" height="18" onclick="editAttendance('<bean:write name="attendance" property="id"/>')"></i></div></td>
	                          </tr>
	                    		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </logic:iterate>
	          </table>
	          </div>
                    </logic:notEmpty>   
                    </div>        
            </div>
            </div>
            </div>
     </div>
  </div>
</div>
</html:form>