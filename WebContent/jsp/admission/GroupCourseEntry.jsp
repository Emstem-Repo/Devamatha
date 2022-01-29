<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function deleteCourse(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "groupCourseEntry.do?method=deleteGroupCourse&id="+ id;
	}
}
function submitCourseGroup(){
	document.getElementById("method").value="addGroupCourse";
	document.groupCourseEntryForm.submit();
}

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
		

</SCRIPT>
<html:form action="/groupCourseEntry">	
	<html:hidden property="method" styleId="method" value="getCourses" />
	<html:hidden property="formName" styleId="formName" value="groupCourseEntryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			Group Course Entry
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Group Course Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.group.group.name"/> </div>
									</td>
									<td  class="row-even">
									<html:select property="groupId" styleClass="combo" styleId="groupId" name="groupCourseEntryForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="groupCourseEntryForm" property="groupToList">
										<html:optionsCollection property="groupToList" name="groupCourseEntryForm" label="name" value="id" />
										</logic:notEmpty>
									</html:select>
									</td>
									<td  height="25" class="row-odd">
									<div align="right"> <bean:message key="knowledgepro.petticash.programType"/> </div>
									</td>
									<td class="row-even">
									<html:select property="programTypeIds" styleId="programTypeIds"  styleClass="combo" multiple="multiple" style="width:200px;height:80px">
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="1" cellpadding="2">
					<logic:notEmpty name="groupCourseEntryForm" property="courseList">
					<tr class="row-odd">
					<td colspan="6">
					<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> Select All
					</td>
					</tr>
						<nested:iterate id="courseList" name="groupCourseEntryForm" property="courseList" indexId="count">
							<c:if test="${count%3 == 0}">
													<tr class="row-even">
												</c:if>
							<td width="3%">
												 <input type="hidden" name="courseList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='courseList' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="courseList[<c:out value='${count}'/>].checked1"
																	id="<c:out value='${count}'/>" onclick="unCheckSelectAll()"/>
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
												</td>
												<td><nested:write name="courseList" property="name"/> </td>					
						</nested:iterate>
					</logic:notEmpty>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td height="35">
							<div align="center">
							<logic:notEmpty name="groupCourseEntryForm" property="courseList">
									<html:button property="" styleClass="formbutton" value="submit"
										styleId="submitbutton" onclick="submitCourseGroup()">
									</html:button>
									</logic:notEmpty>
								</div>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="1" cellpadding="2">
					<logic:notEmpty name="groupCourseEntryForm" property="groupCourseToList">
					<tr class="row-odd">
									<td align="center"> SI.No</td>
									<td align="center"> Program Name</td>
									<td align="center"> Course Name</td>
									<td align="center"> Deleted</td>
								</tr>
						<nested:iterate id="to" name="groupCourseEntryForm" property="groupCourseToList" indexId="count">
								<tr class="row-even">
									<td align="center"> <c:out value="${count+1}"></c:out> </td>
									<td align="center"> <bean:write name="to" property="courseName"/></td>
									<td align="center"> <bean:write name="to" property="programName"/></td>
									<td align="center"> <img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteCourse('<bean:write name="to" property="id" />')" /> </td>
								</tr>
								
							</nested:iterate>
					</logic:notEmpty>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>