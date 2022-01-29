<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
<script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript">
var totalSelectedPhotos=0;
$(document).ready(function(){
	$('#reasonDialog').hide();
	$('.imageShadow').tooltipster();
	$('.image-checkbox-container img').click(function(){
	    if(!$(this).prev('input[type="checkbox"]').prop('checked')){
	        $(this).prev('input[type="checkbox"]').prop('checked', true).attr('checked','checked');
	        this.style.border = '6px solid #0f8cd3';
	        this.style.margin = '0px';
	        totalSelectedPhotos=totalSelectedPhotos+1;
	    }else{
	        $(this).prev('input[type="checkbox"]').prop('checked', false).removeAttr('checked');
	        this.style.border = '0';
	        this.style.margin = '6px';
	        totalSelectedPhotos=totalSelectedPhotos-1;
	    }
	});
	$('#studentClassId').change(function () {
		document.location.href = "UploadPhotos.do?method=viewFinalYearStudentPhotos&studentClassId="+this.value;
	});
	$('#Close').click(function(){
		document.location.href = "LoginAction.do?method=loginAction";
	});
    $('#Approve').click(function (e) {
		if(totalSelectedPhotos==0){
			e.preventDefault();
			alertMessage('No Photos Selected to Approve.');
		}
	});
	  $('#Reject').click(function () {
		if(totalSelectedPhotos>0){
			document.getElementById("reason").value=null;
			 $("#reasonDialog").dialog({
	 	        resizable: false,
	 	        modal: true,
	 	        height: 200,
	 	        title: "Enter Reason",
	 	        width: 300,
	 	        close: function() {
	 	    	$("#reasonDialog").dialog("destroy");
	 	    	$("#reasonDialog").hide();
	          },
	          buttons: {
	        	  Cancel : function() {
	  			   document.getElementById("reason").value="";
	                $("#reasonDialog").dialog("close");
	                $("#reasonDialog").hide();
	             },
	        	  Submit : function() {
	            	var reason = document.getElementById("reason").value;
	            	if(reason.trim()!=''){
	                	if(reason.length>500){
	                		alertMessage('Reason length is too large.');
	                	}else{
		                	 if(totalSelectedPhotos>1){
		                		 $.confirm({
		     						'message'	: 'The Same Reason will be sent to all Selected Student Photos',
		     						'buttons'	: {
		     							'Ok'	: {
		     								'class'	: 'blue',
		     								'action': function(){
		     									$.confirm.hide();
		     									 document.getElementById("method").value="rejectFinalYearStudentPhotos";
		     									 document.getElementById("rejectedReason").value=reason;
		     			            			 document.studentUploadPhotoForm.submit();
		     			            			 $("#reasonDialog").dialog("close");
		     				                     $("#reasonDialog").hide();
		     								}
		     							},
		     		    	       'Cancel'	:  {
		     								'class'	: 'gray',
		     								'action': function(){
		     									$.confirm.hide();
		     								}
		     							}
		     						}
		     					});
		                	 }else if(totalSelectedPhotos==1){
		                		   document.getElementById("method").value="rejectFinalYearStudentPhotos";
		                		   document.getElementById("rejectedReason").value=reason;
			            		   document.studentUploadPhotoForm.submit();
			            		   $("#reasonDialog").dialog("close");
				                   $("#reasonDialog").hide();
		                	 }
	                    	}
	                	
	            	}else{
	            		 alertMessage('Please Enter Reason.');
	                	}
	            }
	          }
	 	    });
		}else{
			alertMessage('No Photos Selected to Reject.');
		}
	});
	$('#RejectedPhotos').click(function () {
		args =  "method=getRejectedStudentPhotos";
		var url = "UploadPhotos.do";
		requestOperationProgram(url, args, updateRejectedPhotos);
	});
	
});
function alertMessage(message) {
	 $.confirm({
			'message'	: message,
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
					}
				}
			}
		});
}
function updateRejectedPhotos(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	var isMsg = false;
	if (value != null) {
		for ( var I = 0; I < value.length; I++) {
			if (value[I].firstChild != null) {
				var temp = value[I].firstChild.nodeValue;
				alertMessage(temp);
				isMsg = true;
			}
		}
	}
	if (isMsg != true) {
		var items = responseObj.getElementsByTagName("rejectedList");
		var count=0;
		var htm="<table width='100%'><tr>";
		for ( var I = 0; I < items.length; I++) {
			if(items[I]!=null){
				if(count==4){
					htm=htm+"<tr>";
					count=0;
				}
				var regNo = items[I].getElementsByTagName("registerNo")[0].firstChild.nodeValue;
				var imageName= items[I].getElementsByTagName("imageName")[0].firstChild.nodeValue;
				var studentName= items[I].getElementsByTagName("studentName")[0].firstChild.nodeValue;
				var studentClass= items[I].getElementsByTagName("studentClass")[0].firstChild.nodeValue;
				var rejectedReason= items[I].getElementsByTagName("rejectedReason")[0].firstChild.nodeValue;
				htm=htm+"<td width='25%' height='25'>";
				var imagePath="images/FinalYearStudentPhoto/"+imageName;
				htm=htm+"<div align='center'><div><font size='2' style='font-weight: bolder;' class='heading'>" +regNo+"</font></div>";
				htm=htm+"<div><img src="+imagePath+" height='150' width='117' class='imageShadow' title='Name : "+studentName+"  Class : "+studentClass+"'/>";
                htm=htm+"</div><div><font size='2' style='font-weight: bolder;' class='heading'> Reason : " +rejectedReason+"</font></div></div></td>";
			}
			count++;
		}
		htm=htm+"</tr></table>";
		rejectedWindowDialog(htm);
	}
}
function rejectedWindowDialog(htm) {
	 document.getElementById('rejectedWindow').innerHTML = htm;
	    $("#rejectedWindow").dialog({
	        resizable: true,
	        modal: true,
	        height: 400,
	        title: "Rejected Photos",
	        width: 800,
	        close: function() {
	    	$("#rejectedWindow").dialog("destroy");
	    	$("#rejectedWindow").hide();
        },
        buttons: {
	               Close : function() {
       	                $("#rejectedWindow").dialog("close");
       	                $("#rejectedWindow").hide();
	                     }
        }
	    });
}
</script>
<style>
.image-checkbox-container input[type="checkbox"]{
    display: none;
}

.image-checkbox-container img{
    border: 0;
    margin: 4px;
}
.imageShadow {
    -webkit-box-shadow: 3px 3px 3px #7C7C7C;
    box-shadow: 3px 3px 3px 3px #7C7C7C;
    }
.tooltipster-default {
	border-radius: 10px; 
	border: 4px solid #fff;
	background:transparent url(images/grayArrow.jpg);
	color: #fff;
	box-shadow: 5px 5px 14px rgba(2,2,2,2.3);
}

 body { font-size: 62.5%; }
</style>
<html:form action="/UploadPhotos" method="post">
	<html:hidden property="formName" value="studentUploadPhotoForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="approveStudentPhotos" />
	<html:hidden property="rejectedReason" name="studentUploadPhotoForm" styleId="rejectedReason"/>
	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			Student Photo Display &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Student Photo Display</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
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
											<td width="40%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.exam.blockUnblock.class" />:</div>
											</td>
											<td width="40%" height="25" class="row-even">
											<div align="left">
											<html:select property="studentClassId" styleId="studentClassId"
												styleClass="combo" name="studentUploadPhotoForm">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="studentUploadPhotoForm"
													property="photoClassMap">
													<html:optionsCollection property="photoClassMap"
														name="studentUploadPhotoForm" label="value" value="key" />
												</logic:notEmpty>
											</html:select></div>
											</td>
											<td width="20%" height="25" class="row-even">
											<html:button property="" styleClass="formbutton"
												value="Rejected Photos" styleId="RejectedPhotos" ></html:button>	
											</td>
										</tr>
										 <logic:notEmpty property="uploadPhotoTOList" name="studentUploadPhotoForm">
										<tr height="10"></tr>
										<tr>
										<td><font color="#6a1074" size="3" style="font-weight: bolder;"><span>Click the Photos to Select</span></font> </td>
										</tr>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
                                 <logic:notEmpty property="uploadPhotoTOList" name="studentUploadPhotoForm">
                                 <tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									   <c:if test="${studentUploadPhotoForm.lengthOfStudentPhotos>1}">
                                            <tr>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">Old Photo</font></td>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">New Photo</font></td>
                                              <td width="10%"></td>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">Old Photo</font></td>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">New Photo</font></td>
                                               <td width="10%"></td>
                                            </tr>
                                           </c:if>
                                           <c:if test="${studentUploadPhotoForm.lengthOfStudentPhotos==1}">
                                            <tr>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">Old Photo</font></td>
                                              <td width="20%" height="25" align="center"><font color="#2f470a" size="4" style="font-weight: bolder;">New Photo</font></td>
                                               <td width="10%"></td>
                                            </tr> 
                                           </c:if>
									   <tr>
									   <%int count1=0; %>
                                       <nested:iterate id="upload" property="uploadPhotoTOList" name="studentUploadPhotoForm" indexId="count">
                                           <%if(count1==2) {%>
                                             <tr>
                                             <%count1=0; %>
                                           <%} %>
                                             <%count1++; %>
                                             <td width="20%" height="25">
											<div align="center">
											<div><font color="#052e45" size="2" style="font-weight: bolder;"><bean:write name='upload' property='regNo'/></font></div>
											
											<div>
                                            <img src="images/StudentPhotos/<bean:write name='upload' property='oldFileName'/>?<%=System.currentTimeMillis() %>" height="128" width="99" class="imageShadow"
                                             title="Name : <bean:write name='upload' property='studentName'/><br>Class : <bean:write name='upload' property='studentClass'/>" alt="Photo not available"/>
											</div>
											</div>
											</td>
											<td width="20%" height="25">
											<div align="center">
											<div><font color="#052e45" size="2" style="font-weight: bolder;"><bean:write name='upload' property='regNo'/></font></div>
											<div>
											<span class="image-checkbox-container">
											<input type="checkbox" name="uploadPhotoTOList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" />
                                            <img src="images/FinalYearStudentPhoto/<bean:write name='upload' property='fileName'/>" height="128" width="99" class="imageShadow"
                                             title="Name : <bean:write name='upload' property='studentName'/><br>Class : <bean:write name='upload' property='studentClass'/>" style="cursor:pointer"/>
											</span>
											</div>
											</div>
											</td>
											 <td width="10%"></td>
											</nested:iterate>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
                                 </logic:notEmpty>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="58%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton"
												value="Approve" styleId="Approve"></html:submit>
											<html:button property="" styleClass="formbutton"
												value="Reject" styleId="Reject" ></html:button>	
									</div>
									</td>
									<td width="2%"></td>
									<td width="40%"><html:button property=""
										styleClass="formbutton" value="Close" styleId="Close"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
			<td>
			<div id="reasonDialog">
					<div align="center">
						<textarea name="reason" style="width: 240px; padding: 3px;height: 90px;" id="reason" ></textarea>
					</div>
			</div>
			<div id='rejectedWindow' style='display: none;width:500px;height:500px;' ></div>
			</td>
		</tr>
	</table>
</html:form>
