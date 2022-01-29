<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
$(document).keypress(function(e) {
    if(e.which == 13) {
        e.preventDefault();
        var regNo =  document.getElementById("regNo1").value;
        if(regNo!=null && regNo!=""){
        	 callingAjaxMethod(regNo);
            }else{
            	document.getElementById("errorMessage").innerHTML = null;
             	document.getElementById("notValid").innerHTML = null;
             	document.getElementById("name").innerHTML = null;
             	document.getElementById("clas").innerHTML = null;
             	document.getElementById("date").innerHTML =null;
             	document.getElementById("session").innerHTML = null;
             	document.getElementById("venue").innerHTML = null;
             	document.getElementById("examDate").value = null;
            	document.getElementById("regId").innerHTML="Register Number is Required";
                }
       
   	   
    }
});
function callingAjaxMethod(regNo){
	 document.getElementById("regId").innerHTML=null;
 	document.getElementById("errorMessage").innerHTML = null;
 	document.getElementById("notValid").innerHTML = null;
 	document.getElementById("name").innerHTML = null;
 	document.getElementById("clas").innerHTML = null;
 	document.getElementById("date").innerHTML =null;
 	document.getElementById("session").innerHTML = null;
 	document.getElementById("venue").innerHTML = null;
 	document.getElementById("examDate").value = null;
 		var url = "examHallEntry.do";
	    	var args = "method=getStudentDetails&regNo="+regNo;
	    	requestOperation(url, args, displayStudentDetails);
}
function updateStudentDetails(){
	document.getElementById("notValid").innerHTML=null;
	var examDate=document.getElementById("examDate").value;
	var examDay = examDate.split("/")[0];
	var examMonth = examDate.split("/")[1];
	var examYear = examDate.split("/")[2];
	var todayDate = new Date();
	var today = todayDate.getDate();
	var todayMonth = todayDate.getMonth()+1;
	var todayYear = todayDate.getFullYear();
	if(today<10){today='0'+today;}
	if(todayMonth<10){todayMonth='0'+todayMonth;}
	if(examDate==null || examDate==""){
		if(document.getElementById("regNo1").value!=null && document.getElementById("regNo1").value!=""){
			getExamDetails(document.getElementById("regNo1").value);
			}else{
				document.getElementById("regId").innerHTML="Register Number is Required";
				}
		}else  if((examDay== today)&&(examMonth== todayMonth)&& (examYear== todayYear)){
	    	document.getElementById("method").value = "updateExamDetails";
	    	document.examHallEntryForm.submit();
		}else{
			/*var retVal = confirm("Exam Date is "+ examDate+" Do you want to continue ");
			   if( retVal == true ){
					document.getElementById("method").value = "updateExamDetails";
					document.examHallEntryForm.submit();
			   }*/
			 $.confirm({
					'message'	: 'Exam Date is <font color=blue>'+ examDate+'</font> Do you want to continue?',
					'buttons'	: {
						'Yes'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
								document.getElementById("method").value = "updateExamDetails";
								document.examHallEntryForm.submit();
							}
						},
	 	       'No'	:  {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});
			}
}
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function resetMessages(){
	document.getElementById("regNo1").value= null;
	document.getElementById("checkId").checked = true;
	document.getElementById("regId").innerHTML= "<td></td>";
	document.getElementById("notValid").innerHTML = "<td></td>";
	document.getElementById("name").innerHTML = "<td></td>";
	document.getElementById("clas").innerHTML = "<td></td>";
	document.getElementById("date").innerHTML = "<td></td>";
	document.getElementById("session").innerHTML = "<td></td>";
	document.getElementById("venue").innerHTML = "<td></td>";
	document.getElementById("examDate").value = null;
	 resetFieldAndErrMsgs();
}

function getExamDetails(regNo){ 
		    	callingAjaxMethod(regNo);
		    	
}
function displayStudentDetails(req){
	var responseObj = req.responseXML.documentElement;
	
	var value = responseObj.getElementsByTagName("value");
	var isMsg = false;
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
		if (value[I].firstChild != null) {
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("notValid").innerHTML = temp;
			isMsg = true;
		}
		}
	}
	if(isMsg=true){
	var items = responseObj.getElementsByTagName("studentDetails");
	for ( var I = 0; I < items.length; I++) {
		if(items[I]!=null){
			var name = items[I].getElementsByTagName("name")[0].firstChild.nodeValue;
			var clas= items[I].getElementsByTagName("clas")[0].firstChild.nodeValue;
			var date = items[I].getElementsByTagName("date")[0].firstChild.nodeValue;
			var session = items[I].getElementsByTagName("session")[0].firstChild.nodeValue;
			var venue = items[I].getElementsByTagName("venue")[0].firstChild.nodeValue;
			document.getElementById("name").innerHTML = name;
			document.getElementById("clas").innerHTML = clas;
			document.getElementById("date").innerHTML = date;
			document.getElementById("session").innerHTML = session;
			document.getElementById("venue").innerHTML = venue;
			document.getElementById("examDate").value = date;
		}
	}
	}
}
function selectPresent(){
var sel=document.getElementById("checkId").value;
	if(sel!=null && sel=="on"){
		document.getElementById("checkId").checked=false;
		document.getElementById("checkId").value=null;
		}else{
			document.getElementById("checkId").checked=true;
			document.getElementById("checkId").value="on";
			}
}
</script>

<html:form action="/examHallEntry" method="post">
<html:hidden property="formName" value="examHallEntryForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method"  styleId="method" name="examHallEntryForm" value=""/>
<input type="hidden" name="date" id="examDate"></input>
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.sap" /> <span class="Bredcrumbs">&gt;&gt;
			Exam Hall Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Exam Hall Entry</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
			</tr>
			<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div id="notValid" style="color: red"><FONT color="red"></FONT></div>
					<div id="regId" style="color: red"><FONT color="red"></FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="20" valign="top" background="images/Tright_03_03.gif">
					</td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.exam.assignStudentsToRoom.displayOrder.regNo" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
											<html:text  property="regNo" styleId="regNo1" name="examHallEntryForm" onchange="getExamDetails(this.value)"></html:text>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.admin.name" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
										<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
											<div id="name1"><bean:write name="examHallEntryForm" property="name"/></div>
											</c:when>
											<c:otherwise>
											<div id="name" align="left">
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.attendance.activityattendence.class" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
										<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<div id="clas1"><bean:write name="examHallEntryForm" property="clas"/></div>
											</c:when>
											<c:otherwise>
											<div id="clas" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.sap.exam.date1" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
										<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<div id="date1"> <font style="font-size: 25px;color: blue"><bean:write name="examHallEntryForm" property="date"/></font> </div>
												<input type="hidden" name="date1" id="examDate1" value='<bean:write name="examHallEntryForm" property="date"/>'></input>
													<script type="text/javascript">
													document.getElementById("examDate").value = document.getElementById("examDate1").value;
													</script>
											</c:when>
											<c:otherwise>
											<div id="date" align="left" style="font-size: 25px;color: blue">
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.admin.session" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
										<c:choose>
											<c:when test="${admOperation != null && (admOperation == 'edit' || admOperation == 'add')}">
												<div id="session1"> <font style="font-size: 25px;color: blue"><bean:write name="examHallEntryForm" property="session"/></font> </div>
											</c:when>
											<c:otherwise>
											<div id="session" align="left" style="font-size: 25px;color: blue">
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.auditorium" />:</div>
									</td>
									<td width="25%" class="row-even" align="left">
										<c:choose>
											<c:when test="${admOperation != null &&  admOperation == 'add'}">
											<div id="venue1"> <font style="font-size: 25px;color: blue"><bean:write name="examHallEntryForm" property="venue"/></font> </div>	
											</c:when>
											<c:otherwise>
											<div id="venue" align="left" style="font-size: 25px;color: blue">
				  							</div>
											</c:otherwise>
										</c:choose>
									</td>
									
							</tr>
							<tr>
									<td width="25%" height="25">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.attendance.present" />:</div>
									</td>
									<td width="25%"  align="left">
									<input type="checkBox" id="checkId" name="present"  onclick="selectPresent()"/>
										<script type="text/javascript">
											var x=document.getElementById("checkId").value;
											if(x=="on"){
												 document.getElementById("checkId").checked = true;
												}else{
													document.getElementById("checkId").checked = false;
													}
										</script>
									</td>
									
							</tr>
								</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="24%" height="35" align="right">
									<html:button property="" styleClass="formbutton" value="Submit" onclick="updateStudentDetails()"></html:button>
							</td>
							<td width="1%" align="left">
									<html:button property="" styleClass="formbutton" onclick="resetMessages()">
										<bean:message key="knowledgepro.admin.reset"/>
									</html:button>
							</td>
							<td width="1%" align="left">
										<html:button property="" styleClass="formbutton" value="Cancel"	onclick="cancel()"></html:button>
							</td>
							<td width="24%" align="left"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
								