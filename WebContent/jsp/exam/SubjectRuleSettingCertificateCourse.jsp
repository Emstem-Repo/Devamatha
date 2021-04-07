<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Information Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

	function closeWindow(){
		document.location.href = "SubjectRuleCertificate.do?method=initSubjectRuleCertificate";
	}
	
    function searchdetails(){
		document.getElementById("method").value="searchPhdDetails";
		document.subjectRuleSettingsForm.submit();
	}
	
	function addCertificateDetails(){
	  document.getElementById("method").value="addCertificateDetails";
	  document.subjectRuleSettingsForm.submit();
	  }
		
	function selectAll(obj) {
			var start=0;
			var end=document.getElementById('countcheck').value;
		    var value = obj.checked;
		    var inputs = document.getElementsByTagName("input");
		    var inputObj;
		    var checkBoxselectedCount = 0;
		    for(var count1 = 0;count1<inputs.length;count1++) {
		          inputObj = inputs[count1];
		          var type = inputObj.getAttribute("type");
		            if (type == 'checkbox' && start<=end) {
		                  inputObj.checked = value;
		                  start++;
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
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/SubjectRuleCertificate" enctype="multipart/form-data">
	<html:hidden property="pageType" value="5" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="countcheck" styleId="countcheck"/>
	<html:hidden property="formName" value="subjectRuleSettingsForm" />
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.subject.certificate.course" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td valign="top">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.exam.subject.certificate.course"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="50" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
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
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
								<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.year" />:</div></td>
                                  <td align="left" height="25" class="row-even"><input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="subjectRuleSettingsForm" property="academicYear"/>" />
			                       <html:select property="academicYear"  styleId="academicYear" styleClass="combo">
			                       <html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
			                       <cms:renderAcademicYear></cms:renderAcademicYear>
			                        </html:select>
			                       </td>
							     <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.schemeType" />:</div>
									</td>
									<td class="row-even" ><html:select
										property="schemeType" styleId="schemeType"
										styleClass="combo" >
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:option value="1">Odd</html:option>
										<html:option value="2">Even</html:option>
										<html:option value="3">Both</html:option>
									</html:select>
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
							<tr>
					</tr>
						</table>
						</td>
					</tr>
				
						</table>
						</td>
					</tr>
					
			<tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				    <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				    <tr>
							<td align="center" colspan="6"> 
							<html:button property="" styleClass="formbutton" value="Search" onclick="searchdetails()"></html:button>&nbsp;&nbsp;	
							<html:button property="" styleClass="formbutton" value="Reset" onclick="closeWindow()"></html:button>	
              		        </td>
                           </tr>				    
                           <tr><td style="height: 10px" align="center" colspan="1"></td></tr> 
                </table></td>
              </tr>
          
            </table></td>
          </tr>
					
					
               
          <logic:notEmpty name="subjectRuleSettingsForm" property="subRulCerCourList">
           <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td width="10%" class="row-odd" align="center">SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/></td>
                    <td height="40%" class="row-odd" align="center"><bean:message key="knowledgepro.admin.certificate.course"/></td>
                    <td height="45%" class="row-odd" align="center" ><bean:message key="knowledgepro.attendance.teacherReport.subjectName"/></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <nested:iterate id="CME" name="subjectRuleSettingsForm" property="subRulCerCourList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="10%" height="25" class="row-even" align="center">
						<input type="hidden" name="subRulCerCourList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
						value="<nested:write name='CME' property='tempChecked'/>" />
														
						<input type="checkbox" name="subRulCerCourList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
						<script type="text/javascript">
						var certiId = document.getElementById("hidden_<c:out value='${count}'/>").value;
						if(certiId == "on") {
						document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
						</td>
                   		<td width="40%"  height="25"  class="row-even" align="left"><bean:write name="CME" property="cerCourseName"/></td>
                   		<td width="45%" height="25" class="row-even" align="left"><bean:write name="CME" property="subjectName"/></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="10%" height="25" class="row-white" align="center">
						<input type="hidden" name="subRulCerCourList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
						value="<nested:write name='CME' property='tempChecked'/>" />
														
						<input type="checkbox" name="subRulCerCourList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
						<script type="text/javascript">
						var certiId = document.getElementById("hidden_<c:out value='${count}'/>").value;
						if(certiId == "on") {
						document.getElementById("<c:out value='${count}'/>").checked = true;
						}		
						</script>
					</td>
               			<td width="40%" height="25" class="row-white" align="left"><bean:write name="CME" property="cerCourseName"/></td>
               			<td width="45%" height="25" class="row-white" align="left"><bean:write name="CME" property="subjectName"/></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </nested:iterate>
                
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				    <tr>
							<td align="center" colspan="6"> 
							<html:button property="" styleClass="formbutton" value="Add" onclick="addCertificateDetails()"></html:button>&nbsp;&nbsp;	
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>	
              		        </td>
                           </tr>
                
                </table></td>
              </tr>
          
            </table></td>
          </tr>  
	      </logic:notEmpty>
	             <tr>
				<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>		
				<tr>
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
			        </tr>
	</table>		 
	</html:form>
			</table>
			</body>
			<script type="text/javascript">
			
			var yearId = document.getElementById("tempyear").value;
			if (yearId != null && yearId.length != 0) {
				document.getElementById("academicYear").value = yearId;
			}
		</script>
	</html>
