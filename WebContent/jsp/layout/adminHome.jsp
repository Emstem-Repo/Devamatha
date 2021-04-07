<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>

<script type="text/javascript" src="js/jquery.js">
	</script>
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
	<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<style type="text/css">
			#overlay {
     visibility: hidden;
     position: absolute;
     left: 0px;
     top: 0px;
     width:100%;
     height:100%;
     text-align:center;
     z-index: 1000;
}
	#overlay div {
     width:300px;
     margin: 100px auto;
     background-color: #fff;
     border:1px solid #000;
     padding:15px;
     text-align:center;
}
.ApprovedAudiEvents {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #434366;
	text-decoration: none;
    font-weight: bold;
}
 .panel-success>.panel-heading {
    color: #fbfbfb;
    background-color: #2196f3e3;
    border-color: #2196f3f7;
}
 .table-striped > tbody > tr:nth-child(2n+1) > td {
    background-color: #03a9f440;
}
			</style>

<script type="text/javascript">
	// 1.
	$(document).ready(function(){
		 
        $("#mobileNo").each(function() 
    	     {
            $(this).keyup(function(){
                calculate();
            });
        });
    });
 
    function calculate() {
        $("#mobileNo").each(function() {
            if(!isNaN(this.value) && this.value.length!=0) {
            	if(this.value.length > 10 || this.value.length < 10){
            		document.getElementById('button').style.visibility='hidden';
                	}else{
                		document.getElementById('button').style.visibility='visible';
                	}
            }
        });
    }
	</script>
	
	<script language="JavaScript">
	
 	 function submit(){
			var mobileNo=document.getElementById("mobileNo").value;
			document.location.href = "LoginAction.do?method=saveMobileNo&mobileNo=" +mobileNo;
 	 	 }
 	function openHtml() {
 		var url="StudentLoginAction.do?method=help";
 		win2 = window.open(url, "Help", 'left=1350,top=500,width=200,height=100,toolbar=0,resizable=0,scrollbars=0,addressbar=0'); 
 	}

 	function getLeaveApproverPage(){
 		document.location.href = "employeeOnlineLeave.do?method=initApproveLeave";
 	}

 	function invigilationDutyAllotment(){
 		var url = "LoginAction.do";
		var args = "method=getInvigilationDutyAllotment";
		requestOperationProgram(url,args,updateInvigilationDutyAllotmentDetails);
 	}

 	function updateInvigilationDutyAllotmentDetails(req) {
    	var responseObj = req.responseXML.documentElement;
    	var fields = responseObj.getElementsByTagName("fields");
    	var count=0;
    	var htm="<table width='100%'><tr height='30'></tr><tr><td width='100%'><table width='100%' style='border: 1px solid black;'	rules='all'>";
    	if(fields!=null){
    		for ( var i = 0; i < fields.length; i++) {
    			if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
    				if(fields[i]!=null){
    					if(count==0){
	    					 htm=htm+"<tr height='40px'><td width='25%' align='center' class='heading'>Exam Date</td><td class='heading' width='25%' align='center'>Session</td></tr>";
	    					 }
    					 htm=htm+"<tr height='30px'>";
    					 htm=htm+"<td width='88%' align='center' colspan='4'><table width='100%'  rules='all'>";
    					 var allotmentToDetails=fields[i].getElementsByTagName("allotmentToDetails");
    					 if(allotmentToDetails!=null){
    						 htm=htm+"<tr class='row-white' height='25px'> ";
    						 for ( var j = 0; j < allotmentToDetails.length; j++) {
    							 if(allotmentToDetails[j]!=null){
    							 var room=allotmentToDetails[j].getElementsByTagName("Room")[0].firstChild.nodeValue;
    							 var type=allotmentToDetails[j].getElementsByTagName("Type")[0].firstChild.nodeValue;
    							 var examDate=allotmentToDetails[j].getElementsByTagName("ExamDate")[0].firstChild.nodeValue;
    							 var session=allotmentToDetails[j].getElementsByTagName("Session")[0].firstChild.nodeValue;
    							 htm=htm+"<td align='center' class='ApprovedAudiEvents' width='25%'>"+examDate+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='25%'>"+session+ "</td>";
    							 }
    							 htm=htm+"</tr>";
    						 } 
    					 }
    					 htm=htm+"</table></td></tr>";
    				}
    			}
    			count++;
    		}
    	}
    	htm=htm+"</table></td></tr><tr height='30'></tr></table>";
    		approvedEventsDialog(htm);	
    }
 	  function approvedEventsDialog(htm) {
 		 document.getElementById('window').innerHTML = htm;
	    	    $("#window").dialog({
	    	        resizable: true,
	    	        modal: true,
	    	        height: 400,
	    	        title: "Invigilation Duty Allotment Details",
	    	        width: 400,
	    	        close: function() {
	    	    	$("#window").dialog("destroy");
	    	    	$("#window").hide();
	             },
	             buttons: {
	    	               Close : function() {
	            	                $("#window").dialog("close");
	            	                $("#window").hide();
	    	                     }
	             }
	    	    });
	    }
	    function syllabusEntry(batch,deptId){
	    	document.location.href = "syllabusEntry.do?method=initSyllabusEntryForTeacher&year="+batch+"&departmentId="+deptId+"&type=subjects";
		    }
	    function syllabusEntryForSecondLanguages(batch,deptId){
	    	document.location.href = "syllabusEntry.do?method=initSyllabusEntryForTeacher&year="+batch+"&departmentId="+deptId+"&type=languages";
		    }
 	</script>
</head>
<body>
 
<div class="form-group">
 <div class="col-sm-12">
 
 <div class="col-sm-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
   
           
              <div class="nav"><i class="glyphicon glyphicon-user"></i>&nbsp  <b><bean:message key="knowledgepro.studentlogin.profile"/></b></div>
              </div>
             
               <div class="panel-body" style="height: 215px;">
          
               <div class="form-group">
               	<div class="col-sm-4">
                  <!-- <div class="row" align="right"><div style="border-style:solid; border-width :1px;border-color:#3292BA;height: 186px; width: 144px;margin-left: 163px;">-->
	                 <div class="row" style="padding-top: 24px;">
	                  <%if(!CMSConstants.LINK_FOR_CJC){ %>
	                  	<img src='<%=session.getAttribute("EMP_IMAGE_HOME")%>'  height="186" width="144" />
	                   <%} else{%>
	                   	<img src='<%=request.getContextPath()+"/PhotoServlet"%>'  height="186" width="144" />
	                   	<%} %>
					</div>
                  </div>
                  </div>
                  </div>
                  <div class="table" style="height: 222px">
                  <table class="table table-bordered table-striped" width="100%" >
                  <tr>
	                   <td>			
                <span><b><bean:message key="knowledgepro.studentlogin.name"/></b></span>: 
                </td>
               <td>	
                <c:out value="${empname}"/><br />
               </td>	
               
              </tr>
              <tr>
                   <td>
					<span><b><bean:message key="knowledgepro.studentlogin.dob"/></b></span>: 
				 </td>
					 <td>
					<span><b><bean:write name="loginform" property="dateOfBirth"/></b> <br /></span>
					 </td>
					</tr>
					<tr>
				<td>
					<b><bean:message key="admissionFormForm.emailId"/></b> :
					</td>
				<td>
                     <span><b><bean:write name="loginform" property="contactMail"/></b><br /></span>
				</td>
					</tr>
					<tr>
				<td>
		                	<span><b><bean:message key="knowledgepro.studentlogin.mobile.no"/></b>:</span>
		                	</td>
					<td>
                            <bean:write name="loginform" property="mobileNo"/>
										 <div id="messageBox">
			                <div id="contents"></div></div>
			                </td>
			               </tr>
			                <tr>
					</tr>
					<tr>
					<td>
					<span><b><bean:message key="knowledgepro.studentlogin.departmentName"/></b></span>: 
					</td>
					<td>
					<span><b><bean:write name="loginform" property="departmentName"/></b> <br /></span>
					</td>
					</tr>
					</table>
					</div>
					</div>
              </div>
               
            
 
 <div class="col-sm-6" >
                                <div class="panel panel-success" style="height: 250px">
                                    <div class="panel-heading" style="padding: 19px;padding-left: 15px;">
                                     <i class="glyphicon glyphicon-hand-right"></i>  &nbsp <b><bean:message key="knowledgepro.studentlogin.newandevents" /></b>
             
          </div>
          
          <div class="form-group">
                 <div class="col-sm-4">
					<div id="test" class="navmenu1">
					<logic:equal value="true" property="linkForCJC" name="loginform">
					<marquee behavior="scroll" direction="up"
						scrollamount="1" width="200" 
						style="padding: top 10px;"
						onmouseover="this.setAttribute('scrollamount', 0, 0);"
					onmouseout="this.setAttribute('scrollamount', 1, 0);"
					class="navmenu1">
					<c:out	value='${loginform.description}' escapeXml='false' />
                    </marquee>
                     </logic:equal>
                   <marquee behavior="scroll" direction="up"
						scrollamount="1" width="200" 
						style="padding: top 10px;"
						onmouseover="this.setAttribute('scrollamount', 0, 0);"
					onmouseout="this.setAttribute('scrollamount', 1, 0);"
					class="navmenu1">
					<c:out	value='${loginform.description}' escapeXml='false' />
                    </marquee>					</div>
					</div></div>
             </div>
             </div>
           
             
          <div class="col-sm-6">
                                <div class="panel panel-success" style=" height: 250px;">
                                    <div class="panel-heading">
   
            <i class="glyphicon glyphicon-bell" ></i>
             &nbsp <b><bean:message key="knowledgepro.studentlogin.notifications"/></b></div>
           
            
         
             <div class="form-group">
                 <div class="col-sm-4">
             
					<logic:equal value="0" property="notifications" name="loginform">
				    	<logic:equal value="false" property="ciaEntrys" name="loginform">
				    	<logic:equal value="false" property="peerEvaluationLinkPresent" name="loginform">
				    	<logic:equal value="false" property="researchLinkPresent" name="loginform">
				    	
				    	<!-- <tr>
				    	 	<%if(!CMSConstants.LINK_FOR_CJC){ %>
				    		<td><img src="images/bullet_img.gif" width="14" height="9" /></td>
				    	 	<td><blink><a target="_blank" href="./downloadForms/ExamRegulations.pdf" class="navmenuRed">Exam Regulations</a></blink>
				    	 	<%} %></td>
				    	</tr>-->
				    	
				    	
				    	</logic:equal>
				    	</logic:equal>
				    	</logic:equal>
				    </logic:equal>
					
					<logic:greaterThan value="0" property="notifications" name="loginform">
						<img src="images/bullet_img.gif" width="14" height="9" />
			       	 	<blink><a href="#"	class="navmenuRed" onclick="getLeaveApproverPage(); ">
			       	 	<c:out value='${loginform.notifications}' escapeXml='false' /> - Leave Approvals Pending.
				    	</a> </blink><BR></BR>
				    </logic:greaterThan>
				    
				    	<logic:equal value="true" property="ciaEntrys" name="loginform">
						<img src="images/bullet_img.gif" width="13" height="9" />
						<logic:notEmpty name="loginform" property="displayLinkExamName">
				    	<blink><a href="javascript:void(0)" onclick="viewMarkEntryPage()"  class="navmenuRed"> Click Here For <bean:write name="loginform" property="displayLinkExamName"/>.</a></blink><br><br>
				    	</logic:notEmpty>
				    	<!--<logic:empty name="loginform" property="displayLinkExamName">
				    	<blink><a href="javascript:void(0)" onclick="viewMarkEntryPage()"  class="navmenuRed"> Internal Mark Entry.</a></blink><br><br>
				    	</logic:empty>
				    --></logic:equal>
 				   
				    <logic:equal value="true" property="peerEvaluationLinkPresent" name="loginform">
						<img src="images/bullet_img.gif" width="13" height="9" />
				    	<blink><a href="javascript:void(0)" onclick="peersEvaluationFeedback()" class="navmenuRed">Click here for Peer Evaluation </a></blink><br><br>
				    </logic:equal>
				    <logic:equal value="true" property="researchLinkPresent" name="loginform">
						<img src="images/bullet_img.gif" width="13" height="9" />
				    	<blink><a href="javascript:void(0)" onclick="ViewResaerchApprovalScreen()" class="navmenuRed">Approve Research and Publication</a></blink><br><br>
				    </logic:equal>
				    <logic:equal value="true" property="isAllotmentDetails" name="loginform">
						<img src="images/bullet_img.gif" width="13" height="9" />
				    	<blink><a href="javascript:void(0)" onclick="invigilationDutyAllotment()">Invigilation duty allotment</a></blink><br><br>
				    </logic:equal>
				    
				    <%--  <%if(!CMSConstants.LINK_FOR_CJC){ %>
                                         <!-- <img src="images/bullet_img.gif" width="13" height="9" />
                                        <blink><a href="./downloadForms/InvigilatorGuide-MBA.pdf" target="_blank" class="navmenuRed">Invigilator Guide</a></blink><br><br>-->
				   <img src="images/bullet_img.gif" width="13" height="9" />
                                        <blink><a href="http://christuniversity.in/uploadimages/Guidelines%20to%20Invigilators%20MBA%20MID%20SEM.pdf" target="_blank" class="navmenuRed">MID Sem MBA - Guidelines to Invigilators</a></blink><br><br>
				     <%} %>--%>
				</div>
				</div>
				</div>
				</div><!--
				 <div class="col-sm-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
              <i class="glyphicon glyphicon-calendar"></i>
               &nbsp   <b> <bean:message key="knowledgepro.studentlogin.dateandtime"/></b></div>
              <div class="panel-body">
               <div class="form-group">
               	<div class="col-sm-4">
                 <div class="col-sm-6" align="center"></div>
					</div>
					</div></div>
              </div>    
       </div>

				--></div>
				
			
	

</body>

<script language="JavaScript">
document.getElementById('button').style.visibility='hidden';
function viewMarkEntryPage(){
	<%--document.location.href = "internalMarksEntry.do?method=initMarksEntry";--%>
	document.location.href = "newCIAMarksEntry.do?method=initInternalMarksEntry";
}
function peersEvaluationFeedback(){
	document.location.href = "peersEvaluationFeedback.do?method=initPeersEvaluationFeedback";
}
function ViewResaerchApprovalScreen(){
	document.location.href = "empResPubPendApproval.do?method=initEmpResPendList";
}	
</script>