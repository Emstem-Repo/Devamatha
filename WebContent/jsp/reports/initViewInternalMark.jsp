<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function getInternalMarks()
	{
		document.
		document.location.href = "ViewInternalMarks.do?method=getInternalMarks";
		ViewInternalMarksForm.submit();
	}
	function Cancel()
	{
		document.location.href = "LoginAction.do?method=loginAction";
	}

	function getClasses(year) {
			var userId = "<c:out value='${ViewInternalMarksForm.userId}'/>";
			getClassByYear(userId,year, updateClasses);
		}
	function updateClasses(req) {
		updateOptionsFromMap(req,"classId","- Select -");
	}

	function getSubjectByClass(classId) {
			var userId = "<c:out value='${ViewInternalMarksForm.userId}'/>";
			var year= document.getElementById("year").value;
			getSubjectByClassTeacherYear(userId,year,classId,updateSubject);
		}

		function updateSubject(req) {
			updateOptionsFromMap(req,"subjectId","- Select -");
		}

		function getClassByTeacherAndYear(year){
			//	var teachers =  document.getElementById("teachers").value;
			//destination1.options[0]=new Option("- Select -","");
			//destination3.options[0]=new Option("- Select -","");
			if(year!=null && year!='') {
				getClassesByTeacherAndYear("classMap", year, teachers,"classes", updateClasses);
			} 
		}
</script>

<html:form action="/ViewInternalMarks" method="post">	
<html:hidden property="method" styleId="method"	value="getInternalMarks" />
<html:hidden property="formName" value="ViewInternalMarksForm" />
<html:hidden property="id" styleId="id" />
<html:hidden property="userId" styleId="userId"/>
<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.view.internal.marks" />
			&gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.view.internal.marks"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	              <tr>
	                <td ><img src="images/01.gif" width="5" height="5" /></td>
	                <td width="914" background="images/02.gif"></td>
	                <td><img src="images/03.gif" width="5" height="5" /></td>
	              </tr>
	              <tr>
	                <td width="5"  background="images/left.gif"></td>
	                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                    <tr >
	
							<td  height="25" class="row-odd" valign="top">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.year" /></div>
							</td>
							<td  class="row-even" align="left" valign="top">
							<input type="hidden" id="tempyear" name="tempyear" value='<bean:write name="ViewInternalMarksForm" property="year"/>' />
							<html:select property="year" styleClass="combo" styleId="year" onchange="getClasses(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderAcademicYear></cms:renderAcademicYear>
							</html:select></td>

	                      <td height="25" class="row-odd" valign="top"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.class.col"/></div></td>
	                      <td class="row-even" align="left">
	                     
	                    <!--  Classes for the academic year  --> 
						<html:select name="ViewInternalMarksForm" property="classId" styleClass="comboMediumLarge" styleId="classId" onchange="getSubjectByClass(this.value)">
								<html:option value=""><bean:message key="knowledgepro.select" />-</html:option>
								<logic:notEmpty property="classMap" name="ViewInternalMarksForm">
								<html:optionsCollection name="ViewInternalMarksForm" property="classMap" label="value" value="key" styleClass="comboMediumLarge"/>
								</logic:notEmpty>
						</html:select>					
				 </td>
	       </tr>
	       <tr >
	           <!--  Subject goes here -->
	         <td class="row-odd" valign="top" ><div align="right">
	         <span class="Mandatory">*</span>
	         <bean:message key = "knowledgepro.attn.teacherClass.subject.name"/></div></td>
	         <td class="row-even" align="left" valign="top" colspan="2">
             <html:select property="subjectId" styleId="subjectId" name="ViewInternalMarksForm" styleClass="comboMediumLarge"  style="width:300px">
               <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
               <logic:notEmpty property="subjectMap" name="ViewInternalMarksForm">
					<html:optionsCollection  property="subjectMap" name="ViewInternalMarksForm" label="value" value="key" />
					</logic:notEmpty>
               </html:select>
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
	            </table></td>
	          </tr>
	          <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
	            <td width="45%" height="35"><div align="Center">
				
						<html:submit property="" styleClass="formbutton" value="Submit"	></html:submit>
						<html:button property="" styleClass="formbutton" value="Cancel"	onclick="Cancel()"></html:button>
					</div>
				</td>
			</tr>
				</table>
			</td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>	