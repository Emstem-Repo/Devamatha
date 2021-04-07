<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
		<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
</head>
<%@page import="java.util.List"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.GOTO"%><script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function getClasses(year) {
	getClassesByYear("classMap", year, "classId", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classId", "- Select -");
}

function uploadData(){
	document.getElementById("method").value="updateUploadData";
	document.UploadUniversityRegNoForm.submit();
	
}

function cancel(){
	resetFieldAndErrMsgs();
}

</script>

<html:form action="/UploadUniversityRegNo" method="POST" enctype="multipart/form-data">

<html:hidden property="formName" value="UploadUniversityRegNoForm" />
<html:hidden property="method" styleId="method" value="updateUploadData"/>
<html:hidden property="pageType" value="3"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;Upload University Register No &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Upload University Register No</strong></div></td>
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
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="UploadUniversityRegNoForm" property="academicYear"/>"/>
				                           <html:select property="academicYear" styleId="academicYear" name="UploadUniversityRegNoForm" styleClass="combo" onchange="getClasses(this.value)">
		                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
		                       			   </html:select>
				        			</td>
				                  <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select name="UploadUniversityRegNoForm" styleId="classId" property="classId">
					                  <html:option value="">Select</html:option>
					                 	    <c:if test="${classMap != null}">
					       		    		<html:optionsCollection property="classMap" label="value" value="key"/>
					       		    		</c:if>
					                  </html:select>
				                  </td>
				                  
				                  <td height="25" class="row-odd" width="25%">
												<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.file" />:</div></td>
											<td height="25" class="row-even"><label>
												<html:file  property="csvFile" styleId="csvFile" size="15" maxlength="30"/></label>
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
                
               </table> 
                
<!-- List of Slip Details -->
	          </td>
	          <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	         
  </tr>
  <tr>
  	 <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                     <tr>
                      <td width="45%"><div align="right">
                       <html:button property="" styleClass="formbutton" value="Submit"  onclick="uploadData()"></html:button>
                       </div></td>
                       <td width="10%"></td>
                       <td width="45%"><div align="left">
					    <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
					   </div></td>
                     </tr>
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>                     
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
var academicYear = document.getElementById("tempyear").value;
if(academicYear.length != 0) {
	document.getElementById("academicYear").value = academicYear;
}
</script>
