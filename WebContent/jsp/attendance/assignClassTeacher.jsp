
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
	function getClasses(year) {
		document.location.href = "AssignClassToTeacher.do?method=setClassEntry&academicYear="+year;
	}
	function addTeachers() {
		
		document.getElementById("method").value = "addTeachers";
		document.assignClassToTeacherForm.submit();
	}
	function editClassEntry(id) {
		document.location.href = "AssignClassToTeacher.do?method=editClassEntry&id="+id;
	}
	function deleteClassEntry(id) {
	
		document.location.href = "AssignClassToTeacher.do?method=deleteTeachers&id="+id;
		}
	function updateClassEntry() {

		document.getElementById("method").value = "updateClassEntry"; 
		
	}
	function resetClassEntry() {
		resetFieldAndErrMsgs();	
		document.getElementById("classesSelected").value= "";
		document.getElementById("teachers").value = "";
		
	}
		
</script>
<html:form action="/AssignClassToTeacher" method="post">
<c:choose>
	<c:when test="${classentry == 'edit'}">
		<html:hidden property="method" styleId="method" value="editClassEntry" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addTeachers" />
	</c:otherwise>
</c:choose>
	<html:hidden property="formName" value="assignClassToTeacherForm" />
	<html:hidden property="method" styleId="method"
				value="addTeachers" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			Assign Class To Teacher &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.attn.assignclassteacher.entry"/></td>
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
	                      <td height="25" class="row-odd" valign="top"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Year"/></div></td>
	                      <td class="row-even" align="left">
	                   	<input type="hidden" id="tempyear" name="tempyear" value='<bean:write name="assignClassToTeacherForm" property="academicYear"/>' />
							<html:select property="academicYear" styleClass="combo" styleId="academicYear" 
								onchange="getClasses(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderEmployeeYear></cms:renderEmployeeYear>
							</html:select></td>	
						<td class="row-odd" valign="top" ><div align="right">
	                      <span class="Mandatory">*</span>
	                      <bean:message key = "knowledgepro.attendance.class.col"/></div></td>
	                     <td class="row-even" align="left">
	                     
	                    <!--  Classes for the academic year  --> 
						<html:select name="assignClassToTeacherForm" 
							property="classesSelected" styleClass="combo"   
							styleId="classesSelected">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<html:optionsCollection name="assignClassToTeacherForm" 
									property="classMap" label="value" 
									value="key" styleClass="combo"/>
						</html:select>						
						 </td>				
	                    </tr>
	                    <tr>
	                    
						 
						 <td  height="25" class="row-odd" valign="top">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.attn.teacherclass.teacher.name" /></div>
							</td>
							<td  class="row-even" align="left" valign="top">
							  <!--  Load teachers -->           
	                       <html:select name="assignClassToTeacherForm" 
	                       styleId="teachers" property="teachers"  
	                       style="width:170px">
	                       <html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
	                       <html:optionsCollection name="assignClassToTeacherForm" 
	                       property="teachersMap" label="value" value="key"/>
                  		   </html:select> 
							</td>
						  <td height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.teacher.type"/> </div></td>
               			 <td class="row-even">
                			<html:select name="assignClassToTeacherForm" styleId="teacherType" property="teacherType"  
	                       style="width:170px"> 
	                       <html:option value=""><bean:message key="knowledgepro.select" />-</html:option>
	                      <html:option value="Class Teacher">Class Teacher</html:option>
	                       <html:option value="Co-ordinator">Co-ordinator</html:option>
	                        <html:option value="HOD">HOD</html:option>
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
				<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				<tr>
	            <td  height="35"><div align="center">
						 <c:choose>
            		<c:when test="${classentry == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateClassEntry()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addTeachers()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
				   <c:choose>
					<c:when test="${classentry == 'edit'}">
						<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetClassEntry()">
										<bean:message key="knowledgepro.cancel"/></html:button>
					</c:otherwise>
				</c:choose>
				</div></td>
			</tr>				
			<tr><td style="height: 10px" align="center" colspan="1"></td></tr>			
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
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.attn.teacherclass.teacher.name" /></div></td>
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.attendance.class.col" /></div> </td>
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.employee.teacher.type" /></div> </td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div> </td>
								</tr>
								<logic:notEmpty name="assignClassToTeacherForm" property="listTeacherClassEntry">
									<logic:iterate id="teacherClass" name="assignClassToTeacherForm"
										property="listTeacherClassEntry" indexId="count">
										
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<tr class="row-even">
										<td width="4%" height="25"> <div align="center"><c:out value="${count + 1}" /></div></td>
										<td align="center" width="14%" height="25"> <div align="center"><bean:write name="teacherClass" property="teacherName" /></div> </td>
										<td align="center" width="18%"><bean:write name="teacherClass" property="className" /></td> 
										<td align="center" width="18%"><bean:write name="teacherClass" property="teacherType" /></td>
										<td width="6%" height="25" class="row-even" ><div align="center"> <img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editClassEntry('<bean:write name="teacherClass" property="id"/>')"></div></td>
										<td width="7%" height="25"> <div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteClassEntry('<nested:write name="teacherClass" property="id"/>')" /></div> </td>
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
		document.getElementById("academicYear").value = yearId;
	}
</script>	