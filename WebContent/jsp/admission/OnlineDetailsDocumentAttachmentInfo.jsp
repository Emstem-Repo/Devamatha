<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

 <script type="text/javascript">
		setStartDate();
		setInterval ( "checkSession()", 300000 );
		//raghu
		function downloadFile(documentId) {
			document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
		}
		
		function resetDocumentform(){   
			document.getElementById("editDocument").value = "";
			document.getElementById("recommentedBy").value = "";
			document.getElementById("recommentedPersonDesignation").value = "";
			document.getElementById("recommentedPersonMobile").value = "";
			
		}
		 /* function showIsMgquota(val) {
			if (val!="") {
				
		    if (val == true) {
		    	document.getElementById('ismgquotaYes').checked=true; 
		    	document.getElementById('isHide').style.display= 'block';
		    } 
		    else if(val == "fals") {
		    	document.getElementById('ismgquotaNo').checked=true;
		        document.getElementById('isHide').style.display= 'none';
		    }
			}
			else
			 document.getElementById('isHide').style.display= 'none';
		}  */
</script>

<style type="text/css">
	
		.tooltip{
   			display: inline;
    		position: relative;
		}
		
		.tooltip:hover:after{
    		background: #333;
    		background: rgba(0,0,0,.8);
    		border-radius: 5px;
    		bottom: 26px;
    		color: #fff;
    		content: attr(title);
    		left: 20%;
    		padding: 5px 15px;
    		position: absolute;
    		z-index: 98;
    		width: 220px;
		}
		
		.tooltip:hover:before{
    		border: solid;
    		border-color: #333 transparent;
    		border-width: 6px 6px 0 6px;
    		bottom: 20px;
    		content: "";
    		left: 50%;
    		position: absolute;
    		z-index: 99;
		}
		.refRed{
		color:	#FF0000;
		font-size: 16px;
		}
	</style>
 
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
			
 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>
<html:hidden property="applicantDetails.personalData.ismgquota" name="onlineApplicationForm" value="true"/>
<table width="80%" style="background-color: #F0F8FF" align="center">
	<tr><td height="10"></td></tr>
  	<tr>
		<td align="center">
			<div id="errorMessage" align="center">
				<FONT color="red"><html:errors /></FONT>
				<FONT color="green">
					<html:messages id="msg"	property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages>
				</FONT>
			</div>
			<div id="errorMessage1" style="font-size: 11px; color: red"></div>
			<div style="font-size: 16px; color: red"><span id="radiomsg"></span></div>
			
		</td>
	</tr>
	<tr><td height="10"></td></tr>
   	<tr><td align="center" class="subheading">Upload Document</td></tr>
	<tr>
		<td>
        	<table width="100%" border="0" cellpadding="4"  align="center" class="normal w">
				<nested:iterate name="onlineApplicationForm" property="applicantDetails.editDocuments" indexId="count" id="docList" type="com.kp.cms.to.admin.ApplnDocTO" >
					<nested:equal value="true" property="photo" name="docList">
          				<tr height="80">
							<td height="25" width="25%" class="row-even" align="center"><nested:write name="docList" property="printName" /></td>
							<td class="row-even" width="60%">
								<nested:equal value="true" property="photo" name="docList">
									<nested:file property="editDocument" styleId="editDocument"></nested:file>
								 	<a href="#" title="Upload Photo passport size" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
								</nested:equal>
							   	<nested:equal value="true" property="documentPresent" name="docList">
									<a href="javascript:downloadFile('<nested:write name="docList" property="id"/>')"><bean:message key="knowledgepro.view.image" /></a>
								</nested:equal>
							</td>
						    <nested:equal value="true" property="documentPresent" name="docList">
								<td width="30%">
									<img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>'  height="150px" width="150px" />
								</td>
	 						</nested:equal>	
	 						<nested:notEqual value="true" property="documentPresent" name="docList">
								<td width="30%"><img src="images/admission/images/passport.png" width="60" height="60"/></td>
							</nested:notEqual>
 						</tr>
					</nested:equal>
<!--					<nested:equal value="true" property="mngQuotaForm" name="docList">-->
<!--          				<tr height="80">-->
<!--							<td height="25" width="25%" class="row-even" align="center"><nested:write name="docList" property="printName" /></td>-->
<!--							<td class="row-even" width="60%">-->
<!--								<nested:file property="editDocument" styleId="editDocument"></nested:file>-->
<!--								<a href="#" title="Upload the Management Quota form you downloaded" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>-->
<!--							   	<nested:equal value="true" property="documentPresent" name="docList">-->
<!--									<a href="javascript:downloadFile('<nested:write name="docList" property="id"/>')"><bean:message key="knowledgepro.view.image" /></a>-->
<!--								</nested:equal>-->
<!--							</td>-->
<!--						    <nested:equal value="true" property="documentPresent" name="docList">-->
<!--								<td width="30%">	-->
<!--									<img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/MngQuotaFormServlet") %>'  height="150px" width="150px" />-->
<!--								</td>-->
<!--	 						</nested:equal>-->
<!-- 						</tr>-->
<!--					</nested:equal>-->
				</nested:iterate>
				
					<%-- <tr height="80">
							<td height="25" width="25%" class="row-even" align="center">Recommendation Application No:</td>
							<td class="row-even" width="60%"><html:text styleId="recommedationApplicationNo"
							property="recommedationApplicationNo"
							name="onlineApplicationForm" maxlength="80"
							styleClass="textboxmedium"></html:text> </td>
						    
 						</tr> --%>
				
				<tr>
					<td align="right"><span class="Mandatory">*</span>CAP Registration Number. :</td>
					<td>
						<nested:text property="applicantDetails.personalData.preferenceNoCAP" styleId="preferenceNoCAP" name="onlineApplicationForm" styleClass="textbox"></nested:text>
						<a href="#" title="Enter Your CAP Preference Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
					</td>
	          	</tr>
				<%-- <tr>
            		<td colspan="3" width="100%">
	            		<table>
	            			<tr>	   				
								<td colspan="2">Apply for Management Quota:</td>
				               	<td colspan="1">
				             		<nested:radio property="applicantDetails.personalData.ismgquota" styleId="ismgquotaYes" name="onlineApplicationForm" value="true" onclick="showIsMgquota(true)"></nested:radio>
								 	<label for="mngQuotatrue"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
									&nbsp;&nbsp;&nbsp;
							 		<nested:radio property="applicantDetails.personalData.ismgquota" styleId="ismgquotaNo" name="onlineApplicationForm" value="false" onclick="showIsMgquota('fals')" ></nested:radio>
									<label for="mngQuotafalse"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
							 		<a href="#" title="whether you seeking Admission under Management Quota then select Yes" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
								</td>
							</tr>
	            		</table>
            		</td> 
            		</tr>--%>
<!--	            	<tr>-->
<!--	            		<td class="refRed" width="100%">Recommendation Reference No:-->
<!--	            		<bean:write name="onlineApplicationForm" property="candidateRefNo" /></td>-->
<!--	            	</tr>-->
					<!-- <tr><td class="refRed">(For candidate submitting recommendation letter)</td></tr> -->
            
            	<%-- <tr>
            		<td colspan="3" width="100%" align="center">
            			<table width="100%" border="0" cellpadding="4"  align="center" id="isHide">
						<tr>
							<td height="30" colspan="3" width="100%" align="center"
								class="subheading">Recommentedation details</td>
						</tr>
				<tr>
					<td colspan="2" align="right"><span class="Mandatory">*</span>Recommented
					by Name:</td>
					<td colspan="2"><nested:text styleId="recommentedBy"
							property="applicantDetails.personalData.recommentedBy"
							name="onlineApplicationForm" maxlength="80"
							styleClass="textboxmedium"></nested:text> <a href="#"
							title="Specify the name of the person who recommented you."
							class="tooltip"><span title="Help"><img alt=""
							src="images/admission/images/Tooltip_QIcon.png" /></span></a></td>
					</tr>
					<tr>
						<td colspan="2" align="right"><span class="Mandatory">*</span>Designation
						of the recommented person:</td>
						<td colspan="2"><nested:text
							styleId="recommentedPersonDesignation"
							property="applicantDetails.personalData.recommentedPersonDesignation"
							name="onlineApplicationForm" maxlength="80"
							styleClass="textboxmedium"></nested:text> <a href="#"
							title="Specify the name of the person's designation who recommented you."
							class="tooltip"><span title="Help"><img alt=""
							src="images/admission/images/Tooltip_QIcon.png" /></span></a></td>
					</tr>
					<tr>
						<td colspan="2" align="right">Mobile
						number of the recommented person:</td>
						<td colspan="2"><nested:text
							styleId="recommentedPersonMobile"
							property="applicantDetails.personalData.recommentedPersonMobile"
							name="onlineApplicationForm" maxlength="12"
							styleClass="textboxmedium" onkeypress="return isNumberKey(event)"></nested:text>
						<a href="#"
							title="Specify the name of the person's mobile number who recommented you."
							class="tooltip"><span title="Help"><img alt=""
							src="images/admission/images/Tooltip_QIcon.png" /></span></a></td>
					</tr>
					<tr>
					<td colspan="2" align="left" style="padding-left: 50px;">
						Name, Designation and Mobile <br>Number of the Referee:</td>
					<td colspan="2">
					<nested:textarea rows="3" property="applicantDetails.personalData.recommendDeatails" style="width: 200px;height: 80px;" name="onlineApplicationForm"
							styleClass="textboxmedium"></nested:textarea> 
							</td>
					</tr>
				</table>
				</td>
				</tr> --%>
				<tr><td colspan="3" height="20"></td></tr>
		  		<tr>
            		<td colspan="3" width="100%" align="center">
            			<table width="100%" border="0" cellpadding="4"  align="center">
            				<tr height="30%"><td colspan="2" align="center"><b>Instructions for uploading photo</b></td></tr>
            				<tr height="30%">
            					<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
            					<td>Photograph should be of recent passport size format.</td>
            				</tr>
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Front view of full face and shoulder portion of candidate is to be seen clearly in the photograph.</td>
				            </tr>                       
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>The face of the candidate should be straight and at the centre.</td>
				            </tr>
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Photograph must be in colour with a light colour background, white is preferable.</td>
				            </tr>
				            <tr height="30%">
					            <td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Photo wearing caps and dark glasses will be rejected.</td>
				            </tr>
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Scanned image file should be in jpg  format only.</td>
				            </tr>
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Size of the image file should be between 40 kb and 100 kb.</td>
				            </tr>
				            <tr height="30%">
				            	<td valign="top"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>Candidate's name and date of photo taken should be printed at the bottom portion of the photograph with black letter and white background.</td>
				            </tr>
				            <tr height="30%">
				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>
				            	<td>If the face in the photograph is not clear, your application is liable to be rejected.</td>
				            </tr>
<!--				            <tr height="30%">-->
<!--				            	<td><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" /></td>-->
<!--				            	<td class="refRed">Do not upload pdf in Management Quota Form.</td>-->
<!--				            </tr>-->
			            </table>
		            </td>
			 	</tr>
        	</table>
        </td>
	</tr>
	<tr><td height="20"></td></tr>
  	<tr>
  		<td width="100%" align="center">
  	 		<html:button property="" onclick="submitAdmissionFormAttachment('submitAttachMentPageOnline')" styleClass="cntbtn" value="Save & Continue to Final Submission"></html:button>
  		</td>
  	</tr>
  	<tr>
  		<td width="100%" align="center">
  			<br/>
  			<html:button property="" value="Clear" styleClass="btn1" onclick="resetDocumentform();" /> 
			&nbsp;
			<html:button property="" onclick="cancel()" styleClass="cancelbtn" value="Logout"></html:button>
  		</td>
  	</tr>
	<tr><td height="40"></td></tr>
</table>
<!-- <script>
	console.log(document.getElementById("ismgquotaYes").checked);
	var isMgQta = "<c:out value='${onlineApplicationForm.mgquota}'/>";
	showIsMgquota(isMgQta);
</script> -->
<script language="JavaScript" src="js/admission/OnlineDetailsAppCreation.js"></script>