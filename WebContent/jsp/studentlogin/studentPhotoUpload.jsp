<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#flUpload").change(function (){ 
		   var ext = $('#flUpload').val().split('.').pop().toLowerCase();
		   if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			   $.confirm({
					'message'	: 'Invalid Extention Image. Please Upload Correct Image. ',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});
		   }else{
			    var F = this.files;
			    if(F && F[0]) for(var i=0; i<F.length; i++) readImage( F[i] );
		     }       
	   });
	$('#Submit').click(function(e){
		e.preventDefault();
		var ext = $('#flUpload').val().split('.').pop().toLowerCase();
		if(ext!=''){
		   if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			   $.confirm({
					'message'	: 'Invalid Extention Image. Please Upload Correct Image. ',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});
		   }else{
			   if(parseInt(imageSize)>512000 && parseInt(width)>415 && parseInt(height)>533){
				   $('#errorMessage').slideDown().html("<span>Image Size is More than 500 kb.<br>Image Dimension shoulde be : 3.5cm(width) X 4.5cm (height).<br>Resolution should be: 300dpi.</span>");
			       return false;
			   }else if(parseInt(imageSize)>512000 && parseInt(width)<410 && parseInt(height)<528){
				   $('#errorMessage').slideDown().html("<span>Image Size is More than 500 kb.<br>Image Dimension shoulde be : 3.5cm(width) X 4.5cm (height).<br>Resolution should be: 300dpi.</span>");
			       return false;
			   }else if((parseInt(width)<410 && parseInt(height)<528) || (parseInt(width)>415 && parseInt(height)>533)){
				   $('#errorMessage').slideDown().html("<span>Image Dimension Should be: 3.5cm(width) X 4.5cm (height).<br>Image Resolution should be: 300dpi.</span>");
			       return false;
			   }else if(parseInt(imageSize)>512000){
				   $('#errorMessage').slideDown().html("<span>Image Size is More than 500 kb.</span>");
			       return false;
			   }else if(parseInt(width)>415 || parseInt(width)<410){
				   $('#errorMessage').slideDown().html("<span>Image Width Should be  3.5cm(width) and Resolution 300dpi.</span>");
			       return false;
			   }else if(parseInt(height)>533 || parseInt(height)<528){
				   $('#errorMessage').slideDown().html("<span>Image Height Should be 4.5cm (height) and Resolution 300dpi.</span>");
			       return false;
			   }else{
				   document.getElementById("method").value="uploadFinalYearStudentPhoto";
				   document.studentUploadPhotoForm.submit();
			   }
		     }
		}else{
			  $.confirm({
					'message'	: 'Please Upload Image. ',
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
	}); 
  });
	var width;
	var height;
	var imageSize;
	var fileType;
	var fileName;
	
function readImage(file) {
    var reader = new FileReader();
    var image  = new Image();
    reader.readAsDataURL(file);  
    reader.onload = function(_file) {
        image.src    = _file.target.result;
        image.onload = function() {
        	width = this.width,
           height = this.height,
           fileType = file.type,
           fileName = file.name,
           imageSize = ~~(file.size/1024);
        };
    };
}
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

</script>
<style>
.imageShadow {
    -webkit-box-shadow: 3px 3px 3px #7C7C7C;
    box-shadow: 3px 3px 3px 3px #7C7C7C;
    }
.approved {
color :#043b72;
font-size:15px;
font-weight: bolder;
}
.rejected {
color :#72043c;
font-size:15px;
font-weight: bolder;
}      
</style>
<html:form action="/UploadPhotos" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="studentUploadPhotoForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="uploadPhotoDetails" styleId="uploadPhotoDetails"/>
	<html:hidden property="rejectedReason" styleId="rejectedReason"/>
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.studentlogin" /> <span class="Bredcrumbs">&gt;&gt;
			Upload Photo &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Upload Photo</strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2"
								class="row-white">
								<tr>
									<td align="left">
									<div id="err"
										style="color: red; font-family: arial; font-size: 11px;"></div>
									<div id="errorMessage"
										style="color: red; font-family: arial; font-size: 13px;">
									<p><span id="err"><html:errors /></span></p>
									<FONT color="green"> <html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>

						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2"
								class="row-white">
							 <c:if test="${studentUploadPhotoForm.uploadPhotoDetails!='Approved'}">
							 <tr>
							 <td width="100%">
							 <div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr height="20"></tr>
								<tr><td width="20%"></td><td width="80%" align="left"><font size="3" color="#4e1014" style="font-weight: bolder;">INSTRUCTIONS</font></td></tr>
								<tr height="20"></tr>
								<tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">1. Dimension: 3.5cm(width) X 4.5cm (height).</font></td></tr>
								<tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">2. Resolution: 300dpi.</font></td></tr>
                                <tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">3. File size: Should be less than 500kb.</font></td></tr>
								<tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">4. Dress: Formal University / department dress code</font></td></tr>
								<tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">5. Background: WHITE background. </font></td></tr>
								<tr><td width="20%"></td><td width="100%" align="left"><font color="#1d5c8d" size="2" style="font-weight: bold;">6. Any queries on photographs, kindly post in support request.</font></td></tr>
								<tr height="20"></tr>
								</table>
					         </div>
							 </td>
							 </tr> 	
							 </c:if>
								<logic:notEmpty property="studentImageName" name="studentUploadPhotoForm">
								<tr>
								<td width="100%" align="center">
								<img src="images/FinalYearStudentPhoto/<bean:write name='studentUploadPhotoForm' property='studentImageName'/>" height="180" width="148" class="imageShadow"/>
								</td>
								</tr>
								</logic:notEmpty>
								<logic:empty property="studentImageName" name="studentUploadPhotoForm">
								<c:if test="${studentUploadPhotoForm.uploadPhotoDetails!='Approved'}">
								<tr>
								<td width="100%" align="center">
								<img src="images/FinalYearStudentPhoto/sample.jpg" height="180" width="148" class="imageShadow"/>
								</td>
								</tr>
								</c:if>
								</logic:empty>
								<tr>
								<td width="100%" align="center">
								<div id="reason"></div>
								</td>
								</tr>
								<tr>
									<td width="100%">
									<table width="100%" cellpadding="2" cellspacing="1">
										<tr class="row-white">
											<td height="25" class="studentrow-odd" width="50%">
											<div align="right"><span class="Mandatory">*</span>Upload Image:</div></td>
										<td width="50%" height="25" class="studentrow-even">
										<html:file property="studentPhoto" name="studentUploadPhotoForm" styleId="flUpload"/></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5"
								height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right">
							<html:submit property=""
								styleClass="btnbg" value="Submit"
								onclick="saveStduentCarDetails()" styleId="Submit"></html:submit> 
							</div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button
								property="" styleClass="btnbg" value="Close"
								onclick="goToHomePage()"></html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" height="29"
						width="9"></td>
					<td background="images/st_TcenterD.gif" width="100%"></td>
					<td><img src="images/st_Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var reason=document.getElementById("rejectedReason").value;
if(reason!=null && reason!=''){
	document.getElementById("reason").innerHTML=reason;
}else{
	document.getElementById("reason").innerHTML="";
}
var photoDetails=document.getElementById("uploadPhotoDetails").value;
if(photoDetails!="" && photoDetails!='Rejected'){
	document.getElementById("flUpload").disabled = true;
	document.getElementById("Submit").style.display = "none";
	$("#reason").addClass("approved");
}else{
	document.getElementById("flUpload").disabled = false;
	document.getElementById("Submit").style.display = "block";
	$("#reason").addClass("rejected");
}
</script>