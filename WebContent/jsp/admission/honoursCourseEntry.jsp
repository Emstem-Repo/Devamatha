<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
                  inputObj.value="on";
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
                        inputObj.value="on";
                  }else{
                	  inputObj.value="off";	
                      }   
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }
}
function getdetails(){
	document.getElementById("method").value='getHonoursCourseStudents';
	document.honoursCourseEntryForm.submit();
}
function resetFields(){
	document.getElementById("courseId").value="";
	resetErrMsgs();
}
function submitDetails(){
	document.getElementById("method").value='saveCourseList';
	document.honoursCourseEntryForm.submit();
}
function cancelPage(){
	document.getElementById("courseId").value="";
	document.location.href="honoursCourseEntry.do?method=initHonourseCourse";
}
</script>

<html:form action="/honoursCourseEntry">
<html:hidden property="method" styleId="method" value="saveCourseList"/>
<html:hidden property="formName" value="honoursCourseEntryForm"/>
<html:hidden property="pageType" value="2"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; Honours Course &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> Students List</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red">
                       		<html:errors/>
                       		<bean:write name="honoursCourseEntryForm" property="errorMessage"/>
                       </FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 
                  <tr>
                   <td height="49" colspan="6" class="body" >
                   
                   
                   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                          <tr>
									<td align="right" height="25" width="24%" class="row-odd"><span class="Mandatory">*</span>Academic Year:</td>
									<td align="left" height="25" width="24%" class="row-even">
										<input type="hidden" id="yearId" value="<bean:write name="honoursCourseEntryForm" property="year"/>"/>
										<html:select property="year" styleId="year" styleClass="combo">
                       	   					<html:option value="">- Select -</html:option>
                       	   					<cms:renderYear></cms:renderYear>
                       			   		</html:select> 	
									</td>
									<td align="right" height="25" width="24%" class="row-odd">Course Applied:</td>
									<td align="left" height="25" class="row-even">
										<html:select property="courseId"  styleId="courseId" styleClass="comboMediumBig" >
										  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										  <logic:notEmpty name="honoursCourseEntryForm" property="courseMap" >
											  <html:optionsCollection name="honoursCourseEntryForm" property="courseMap" label="value" value="key" />
										  </logic:notEmpty>
								 	 	</html:select>
									</td>
						</tr>
                        <tr>
                          	<td colspan="4" align="center"> 
                          		<html:button property="" styleClass="formbutton" value="Search" onclick="getdetails()"></html:button>
		                      	<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()"></html:button>
                          	</td>
                        </tr>  
                       </table></td>
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
                 
                 
                 <tr>
                   <td height="49" colspan="6" class="body" >
                   
                   
                   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                          <tr>
									<td align="center" height="25" class="row-odd" width="8%">
									<input type="checkbox" id="checkAll" onclick="selectAll(this)"/>
									Select</td>
									<td align="center" height="25" class="row-odd" width="17%">Register Number</td>
									<td align="center" height="25" class="row-odd" width="25%">Name</td>
									<td align="center" height="25" class="row-odd" width="20%">Current Class</td>
									<td align="center" height="25" class="row-odd" width="30%">Course Applied</td>
						</tr>
                        <logic:notEmpty name="honoursCourseEntryForm" property="appliedDetails">
                        	<logic:iterate id="to" name="honoursCourseEntryForm" property="appliedDetails" indexId="count">
                        		<c:choose>
									<c:when test="${count%2 == 0}">
										<tr class="row-even">
									</c:when>
									<c:otherwise>
										<tr class="row-white">
									</c:otherwise>
								</c:choose>
								<td align="center">
									<input type="hidden" name="appliedDetails[<c:out value='${count}'/>].tempChecked" id="hidden[<c:out value='${count}'/>]" 
									value="<bean:write name='to' property='checked'/>"/>
									<input type="checkbox" name="appliedDetails[<c:out value='${count}'/>].checked" id="appliedDetails[<c:out value='${count}'/>]" onclick="unCheckSelectAll()"/>
									<script type="text/javascript">
										var hiddenvalue = document.getElementById("hidden[<c:out value='${count}'/>]").value;
										if(hiddenvalue=="on"){
											document.getElementById("appliedDetails[<c:out value='${count}'/>]").checked=true;
										}
									</script>
								</td>		
								<td align="center"><bean:write name="to" property="regNO"/></td>
								<td align="center"><bean:write name="to" property="studentName"/></td>
								<td align="center"><bean:write name="to" property="currentClass"/></td>
								<td align="center"><bean:write name="to" property="courseApplied"/></td>
                        	</logic:iterate>
                        </logic:notEmpty>   
                       </table></td>
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
                 <tr><td> 
                  <logic:notEmpty property="appliedDetails" name="honoursCourseEntryForm">
                  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                           <tr>
                            <td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Semester:</td>
                             <td height="25" class="row-even" align="left">
                           		<html:select name="honoursCourseEntryForm" property="semister" styleId="semister" styleClass="combo">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                            		<html:option value="1">1</html:option>
                            		<html:option value="2">2</html:option>
                            		<html:option value="3">3</html:option>
                            		<html:option value="4">4</html:option>
                            		<html:option value="5">5</html:option>
                            		<html:option value="6">6</html:option>
								</html:select> 
							</td>
                             <td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Selected For:</td>
                             <td height="25" class="row-even" align="left">
                            	<html:select property="honoursCourseId"  styleId="honoursCourseId" styleClass="comboMediumBig" >
								  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								  <logic:notEmpty name="honoursCourseEntryForm" property="honoursCourseMap" >
									  <html:optionsCollection name="honoursCourseEntryForm" property="honoursCourseMap" label="value" value="key" />
								  </logic:notEmpty>
						 	 	</html:select>
							
							</td>
                           </tr>
                       </table></td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </logic:notEmpty>
                 </td> </tr>
                 
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                     <logic:notEmpty property="appliedDetails" name="honoursCourseEntryForm">
			   				  <html:button property="" styleId="button" styleClass="formbutton" value="Submit" onclick="submitDetails()"> </html:button>
		                      <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelPage()"></html:button>
		                    </logic:notEmpty>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("yearId").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>