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
	
	function reActivate() {
		document.location.href = "TeacherClassEntry.do?method=activateTeacherClassSubjectEntry";
	}
	function getClasses(year) {
		document.location.href = "TeacherClassEntry.do?method=setClassEntry&academicYear="+year;
	}

	function deleteClassEntry(id) {
		deleteConfirm = confirm("Are you sure want to delete subject name?");
		if (deleteConfirm == true) {
			var year=document.getElementById("year").value;
			document.location.href = "TeacherClassEntry.do?method=deleteTeacherClassEntry&id="+ id+"&year="+year;
		}
	}
	function editClassEntry(id) {
		document.location.href = "TeacherClassEntry.do?method=editTeacherClassEntry&id="
			+ id;
	}
	
	function updateClasses(req) {
		updateOptionsFromMap(req, "selectedClasses", "- Select -");
	}	
	//function updateClasses(req) {
	//	updateOptionsFromMapForMultiSelect(req, "selectedClasses");
	//}

	function loadTeacherClass(id) {
		document.location.href = "TeacherClassEntry.do?method=loadTeacherClass&id=" + id;
	}
	function updateTeacherClass() {

		document.getElementById("selectedClassesID").value=document.getElementById("selectedClasses").value;
		document.getElementById("method").value = "updateTeacherClass";
		document.teacherClassEntryForm.submit();
	}
	function addTeacherClass() {
		console.log(document.getElementById("teachers").value);
		document.getElementById("method").value = "addTeacherClass";
		document.getElementById("te").value = document.getElementById("teachers").value;
		document.teacherClassEntryForm.submit();
	}
	function resetTeacherClassEntry() {
		//document.getElementById("teacherClassName").value = "";
		var destination = document.getElementById("selectedClasses");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1].selected = false;
		}
		var destination1 = document.getElementById("subjectId");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1]=null;
		}
		resetErrMsgs();
	}	
	function checkNumber(field){
		if(isNaN(field.value)){
			field.value = "00";
		}
	}
	function getSubjectsPeriodsBatchForClass() {
		var classes =  document.getElementById("selectedClasses");
		var destination1 = document.getElementById("subjectId");
		for (x1=destination1.options.length-1; x1>0; x1--) {
			destination1.options[x1]=null;
		}
		/*
		var destination2 = document.getElementById("periods");
		for (x1=destination2.options.length-1; x1>=0; x1--) {
			destination2.options[x1]=null;
		}
		*/
		if(classes.selectedIndex != -1) {
			destination1.options[0]=new Option("- Loading -","");
//			destination2.options[0]=new Option("- Loading -","");
			var year = document.getElementById("year").value;
			var selectedClasses = new Array();
			var count = 0;
			for (var i=0; i<classes.options.length; i++) {
			    if (classes.options[i].selected) {
			    	selectedClasses[count] = classes.options[i].value;
			      count++;
			    }
			 }	
			var url = "TeacherClassEntry.do";
			var args = "method=getCommonSubjectsForClass&selectedClassesArray1="+selectedClasses+"&year"+year;
			requestOperationProgram(url,args,updateSubjcetBatchPeriod);
		} 
	}

	function updateSubjcetBatchPeriod(req) {
		
		var responseObj = req.responseXML.documentElement;
		var destination1 = document.getElementById("subjectId");
		destination1.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("subject");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination1.options[j+1] = new Option(label,value);
		 }
	}
	/*
		var destination2 = document.getElementById("periods");
		
		var items2 = responseObj.getElementsByTagName("period");
		if(items2.length == 0) {
			var destination5 = document.getElementById("periods");
			for (x1=destination5.options.length-1; x1>=0; x1--) {
				destination5.options[x1]=null;
			}
		}	
		for (var k = 0 ; k < items2.length ; k++) {
	        label = items2[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items2[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination2.options[k] = new Option(label,value);
		 }
		*/
		function cancelTeacherClassEntry(){
			document.location.href = "TeacherClassEntry.do?method=initTeacherClass";
		}
		
</script>
<html:form action="/TeacherClassEntry" method="post">
	<c:choose>
		<c:when test="${teacherClassOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateTeacherClass" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addTeacherClass" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="teacherClassEntryForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="selectedClasses" styleId="selectedClassesID"  name="teacherClassEntryForm" />
	<html:hidden property="teachers" styleId="te"  name="teacherClassEntryForm" />
		
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			Teacher Class Entry &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.attn.teacherclass.entry"/></td>
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
								key="knowledgepro.interview.Year" /></div>
							</td>
							<td  class="row-even" align="left" valign="top">
							<input type="hidden" id="tempyear" name="tempyear" value='<bean:write name="teacherClassEntryForm" property="year"/>' />
							<html:select property="year" styleClass="combo" styleId="year" 
								onchange="getClasses(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderAcademicYear></cms:renderAcademicYear>
							</html:select></td>

	                      <td height="25" class="row-odd" valign="top"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.class.col"/></div></td>
	                      <% boolean disable=false;%>
	                      <c:choose>
	                      <c:when test="${teacherClassOperation == 'edit'}">
							<% disable=true;%>
	                      </c:when>
	                      </c:choose>
	                      <td class="row-even" align="left">
	                     
	                    <!--  Classes for the academic year  --> 
						<html:select name="teacherClassEntryForm" 
							property="selectedClasses" size="5" style="width:150px" multiple="multiple"  disabled='<%=disable%>'
							styleId="selectedClasses" 
							onchange="getSubjectsPeriodsBatchForClass()">
								<html:optionsCollection name="teacherClassEntryForm" 
									property="classMap" label="value" 
									value="key" styleClass="combo"/>
						</html:select>						
						 </td>
						 
						 
	                    </tr>
	                    <tr >
	                    <!--  Subject goes here -->
	                      <td class="row-odd" valign="top" ><div align="right">
	                      <span class="Mandatory">*</span>
	                      <bean:message key = "knowledgepro.attn.teacherClass.subject.name"/></div></td>
	                      <td class="row-even" align="left" valign="top">

                    <html:select property="subjectId" styleId="subjectId" 
                    	styleClass="combo"  style="width:300px">
                      		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                      		
                      		<c:if test="${teacherClassEntryForm.subjectMap != null && teacherClassEntryForm.subjectMap != ''}">
													
														<html:optionsCollection  property="subjectMap" name="teacherClassEntryForm" label="value"
															value="key" />
													
												</c:if>
                    </html:select>
	                      </td>
	                      <td  height="25" class="row-odd" >
	                      <div align="right"><span class="Mandatory">*</span>
	                      <bean:message key = "knowledgepro.attn.teacherclass.teacher.name"/></div></td>
	                      <td class="row-even" align="left"  >
	                      <!--  Load teachers -->           
	                      <logic:equal value="131" name="teacherClassEntryForm" property="roleId">
	                      		 <html:select name="teacherClassEntryForm" disabled="true"
	                      		 styleId="teachers" property="teacher" style="width:300px">
	                     		  <html:optionsCollection name="teacherClassEntryForm" 
	                     		  property="teachersMap" label="value" value="key" />
	                       
                  		   </html:select> 
                  		   </logic:equal>
                  		   <logic:notEqual value="131" name="teacherClassEntryForm" property="roleId">
	                      	 <html:select name="teacherClassEntryForm" 
	                      	  styleId="teachers" property="teacher"   
	                     		 style="width:300px" >
	                      			 <html:optionsCollection name="teacherClassEntryForm" 
	                     			  property="teachersMap" label="value" value="key" />
	                       
                  		   	 </html:select> 
                  		   </logic:notEqual>
                  		   <script>
                  		   if ('<bean:write name="teacherClassEntryForm" property="roleId"/>'=='131') {
                  			 document.getElementById("teachers").value = '<bean:write name="teacherClassEntryForm" property="userId"/>';
                  			var sel = document.getElementById('teachers');
                  			var opt = sel.options[sel.selectedIndex];
							}
                  		   </script>
	                      </td>
	                     
	                      </tr>
	                      <tr>
	                      <td width="22%" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.attn.teacherClass.numeric.code"/></div>
									</td>
									<td width="16%" class="row-even"><span class="star">
									<html:text property="numericCode" styleId="numericCode" styleClass="TextBox"
										size="20" maxlength="30"/> </span></td>
									<td class="row-odd"></td>	
									<td class="row-even"></td>
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
					<c:when test="${teacherClassOperation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Update"
							onclick="updateTeacherClass()"></html:button>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Submit"
							onclick="addTeacherClass()"></html:button>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="53%"><c:choose>
						<c:when test="${teacherClassOperation == 'edit'}">
							<html:button property="" value="Reset" styleClass="formbutton" 
									onclick="cancelTeacherClassEntry()"></html:button>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetTeacherClassEntry()"></html:button>
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
									<td align="center" height="25" class="row-odd"><bean:message key="knowledgepro.fee.academicyear" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.attn.teacherClass.numeric.code" /></td>
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.attn.teacherclass.teacher.name" /></div></td>
									<td align="center" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.attendance.class.col" /></div> </td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.attn.teacherClass.subject.name" /></td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div> </td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div> </td>
								</tr>
								<logic:notEmpty name="teacherClassEntryForm" property="listTeacherClassEntry">
									<logic:iterate id="teacherClass" name="teacherClassEntryForm"
										property="listTeacherClassEntry" indexId="count">
										<bean:define id="year1" property="academicYear" name="teacherClass"
											type="java.lang.Integer"></bean:define>
										<% year1= year1.intValue(); %>
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
										<td align="center" width="10%" height="25"><div align="center"><bean:write name="teacherClass" property="academicYear" />-<%=year1+1 %></div> </td>
										<td align="center" width="6%"><bean:write name="teacherClass" property="numericCode"/> </td>
										<td align="center" width="14%" height="25"> <div align="center"><bean:write name="teacherClass" property="teacherName" /></div> </td>
										<td align="center" width="6%"><bean:write name="teacherClass" property="className" /></td> 
										<td align="center" width="18%"><bean:write name="teacherClass" property="subjectName" /></td>
										<td width="7%" height="25"> <div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editClassEntry('<nested:write name="teacherClass" property="id" />')" /></div></td>
										<td width="7%" height="25"> <div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteClassEntry('<nested:write name="teacherClass" property="id" />')" /></div> </td>
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