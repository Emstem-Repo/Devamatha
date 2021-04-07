<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap" %>

<script type="text/javascript">
var feeId;
function resetCoursesChilds() {
	resetOption("semister");
	resetOption("toSemester");   
}

function getSemistersByCourse(courseId) 
{
	var academicYear = document.getElementById("academicYear").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",academicYear,updateSemisters);
	resetOption("toSemester");     
}

function getSemisters(year) 
{
	var courseId = document.getElementById("course").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",year,updateSemisters);
	resetOption("toSemester");        
}

function updateSemisters(req){
	updateOptionsFromMap(req,"semister","- Select -");
}

function getToSemister(semisterId)
{
	var semister=document.getElementById(semisterId);
	document.getElementById("toSemester").options.length=0;
	var optn = document.createElement("OPTION");
	optn.text = "- Select -";
	optn.value = "";
	document.getElementById("toSemester").options.add(optn);
	for(var i=semister.value-0+1;i<semister.options.length;i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = i;
		optn.value = i;
		document.getElementById("toSemester").options.add(optn);
	}			    
}

function resetDataErrMsgs()	{
	resetFieldAndErrMsgs();
	resetOption("toSemester");
	resetOption("semister");
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("academicYear").value=year;
	}
}



</script>

<html:form action="/FeeAssignmentCopy">

<html:hidden property="method" styleId="method" value="copyFeeAssignmentCourse"/>
<html:hidden property="formName" value="feeAssignmentCopyForm"/>
<html:hidden property="pageType" value="2"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.assignment"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.assignment"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
		                         <tr >
		                           <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.course"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                           <html:select property="courseId"  styleId="course" styleClass="combo">
                           				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                          				<html:optionsCollection property="courseList"  name="feeAssignmentCopyForm" label="name" value="id"/>
                       			   </html:select>
		                           </td>
		                           <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.admittedYear"/>:</div></td>
		                           <td class="row-even" align="left">
		                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="feeAssignmentCopyForm" property="academicYear"/>"/>
		                           <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getSemisters(this.value)">
                       	   				 <html:option value="">- Select -</html:option>
                       	   				 <cms:renderYear></cms:renderYear>
                       			   </html:select>
		                           </td>
		                         </tr>
		                         <tr >
		                           <td width="24%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>To Course</div></td>
		                           <td width="24%" height="25" class="row-even" align="left"><label>
		                           <html:select property="toCourseId"  styleId="newcourse" styleClass="combo" onchange="resetCoursesChilds()">
                           				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                          				<html:optionsCollection property="courseList"  name="feeAssignmentCopyForm" label="name" value="id"/>
                       			   </html:select>
		                           </label></td>
		                           
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
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                     <tr>
                       <td width="45%"><div align="right">
                        	<html:submit styleClass="formbutton" value="Copy Fee" />
                           	 
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                       		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetDataErrMsgs()"></html:button>
                      </td>
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                     
                   </table>
                   </td>
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
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
getToSemister("semister"); 
var toSem=document.getElementById("tempToSemester").value;
if(toSem.length!=0)
{
	document.getElementById("toSemester").value=toSem;	
}
</script>