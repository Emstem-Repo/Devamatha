<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<script type="text/javascript">
	//function getClasses(year) {
		//getClassesByYearInMuliSelect("classMap", year, "selectedClasses", updateClasses);
	//	getClassesByYear("classMap", year, "selectedClasses", updateClasses);		
	//}
    function addTeacherDepartment(){
    	document.getElementById("method").value = "addTeacherDepartment";
		document.teacherClassEntryForm.submit();
    }
	function getClasses(year) {
		document.location.href = "TeacherClassEntry.do?method=setClassEntry&academicYear="+year;
	}

	function deleteDepartmentEntry(id) {
		deleteConfirm = confirm("Are you sure want to delete Teacher Department?");
		if (deleteConfirm == true) {
			
			document.location.href = "TeacherDepartmentEntry.do?method=deleteTeacherDepartmentEntry&id="+ id;
		}
	}
	function editDepartmentEntry(id) {
		document.location.href = "TeacherDepartmentEntry.do?method=editTeacherDepartmentEntry&id="
			+ id;
	}
	function loadTeacherClass(id) {
		document.location.href = "TeacherClassEntry.do?method=loadTeacherClass&id=" + id;
	}
	function updateTeacherDepartment() {
		resetErrMsgs();
		document.getElementById("method").value = "updateTeacherDepartment";
		document.teacherClassEntryForm.submit();
	}
	
	function resetTeacherDepartmentEntry() {
		//document.getElementById("teacherClassName").value = "";
		var destination = document.getElementById("departmentid");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1].selected = false;
		}
		var destination1 = document.getElementById("teachers");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1].selected=false;
		}
		resetErrMsgs();
	}	
	
		
</script>
<html:form action="/TeacherDepartmentEntry" method="post">
	<c:choose>
		<c:when test="${teacherDepartmentOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateTeacherDepartment" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addTeacherDepartment" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="teacherDepartmentEntryForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			Teacher Class Entry &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.attn.teacherDepartment.entry"/></td>
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
	
							<td  height="25" class="row-odd" >
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.employee.department.col" /></div>
							</td>
							<td  class="row-even" align="left" valign="top"> 
							<html:select name="teacherDepartmentEntryForm" 
							property="department" styleClass="combo" style="width:250px"
							styleId="departmentid"> 
							
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<html:optionsCollection name="teacherDepartmentEntryForm" 
									property="departmentMap" label="value" style="width:210px" 
									value="key" styleClass="combo"/>
						</html:select>	</td>

	                       <td  height="25" class="row-odd" >
	                      <div align="right"><span class="Mandatory">*</span>
	                      <bean:message key = "knowledgepro.attn.teacherclass.teacher.name"/></div></td>
	                      <td class="row-even" align="left">
	                     <span class="star">
	                    <!--  Classes for the academic year  --> 
						<html:select name="teacherDepartmentEntryForm" 
	                       styleId="teachers" property="teacher"  
	                        styleClass="combo"  style="width:170px"><html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
	                       <html:optionsCollection name="teacherDepartmentEntryForm" 
	                       property="teachersMap" label="value" value="key" styleClass="combo"/>
	                       
                  		   </html:select></span></td>
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
				<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
				<tr>
	            <td width="45%" height="35"><div align="right">
				<c:choose>
					<c:when test="${teacherDepartmentOperation == 'edit'}">
						<html:submit  value="Update" styleClass="formbutton" onclick="updateTeacherDepartment()"></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit  value="Submit" styleClass="formbutton" onclick="addTeacherDepartment()"></html:submit>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="53%"><c:choose>
						<c:when test="${teacherDepartmentOperation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetTeacherDepartmentEntry()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>				
			<tr><td style="height: 10px" align="center" colspan="3"></td></tr>			
				</table>
			</td>
	          </tr>

	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd"> <div align="center"><bean:message key="knowledgepro.slno" /></div>	</td>
									<td align="center" height="25" class="row-odd"><bean:message key="knowledgepro.attn.teacherdepartment.departmentname" /></td>
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.attn.teacherdepartment.teachername" /></div></td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div> </td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div> </td>
								</tr>
								<logic:notEmpty name="teacherDepartmentEntryForm" property="teacherDepartmentTO">
									<logic:iterate id="teacherDepartment" name="teacherDepartmentEntryForm"
										property="teacherDepartmentTO" indexId="count">
										
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<tr class="row-even">
										<td width="10%" height="25"> <div align="center"><c:out value="${count + 1}" /></div></td>
										<td align="center" width="30%" height="25"><div align="center"><bean:write name="teacherDepartment" property="departmentName" /></div> </td>
										
										<td align="center" width="30%" height="25"> <div align="center"><bean:write name="teacherDepartment" property="teacherName" /></div> </td>
										
										<td width="15%" height="25">
										<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editDepartmentEntry('<nested:write name="teacherDepartment" property="id" />')" /> </div>

										</td>
										<td width="15%" height="25"> 
										<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteDepartmentEntry('<nested:write name="teacherDepartment" property="id" />')" /></div> 
										</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
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
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">   </td>   
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">    </td>  
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
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