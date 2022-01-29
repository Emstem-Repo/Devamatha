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
    validateCheckBox();
}

function selectSome(obj) {
	var endNumber=document.getElementById("endNumber").value;
	if(endNumber == null || endNumber==""){

		var total=document.getElementById("startNumber").value;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    var id=0;
	
	    for(var count1 = 0;count1<inputs.length;count1++) {
	        inputObj = inputs[count1];
	        var type = inputObj.getAttribute("type");
	          if (type == 'checkbox') {
	                inputObj.checked = false;
	                inputObj.value="off";
	          }
	  	}
	    
	    for(var count1 = 0;count1<total;count1++) {
	          inputObj = inputs[id];
	          id++;
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = true;
	                  inputObj.value="on";
	                  
	            }else{
	            	count1--;
	                }
	    }

	}else{
		var object=document.getElementById("endNumber");
		selectMiddle(object);
	}
    validateCheckBox();
}

function selectMiddle(obj) {
	var start=document.getElementById("startNumber").value;
	start=start-1;
	var total=document.getElementById("endNumber").value;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    var id=0;

    for(var count1 = 0;count1<inputs.length;count1++) {
        inputObj = inputs[count1];
        var type = inputObj.getAttribute("type");
          if (type == 'checkbox') {
                inputObj.checked = false;
                inputObj.value="off";
          }
  	}
    
    for(var count1 = 0;count1<total;count1++) {
          inputObj = inputs[id];
          id++;
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
            	if(count1 >= start && count1 <= total){
                  inputObj.checked = true;
                  inputObj.value="on";
                }
                  
            }else{
            	count1--;
                }
    }
    if(total == null || total ==""){
    	var startObj=document.getElementById("startNumber");
    	selectSome(startObj);
        }
    validateCheckBox();
}


	function resetData() {
		resetFieldAndErrMsgs();
		document.getElementById("display").innerHTML ="";
	}
	
	function cancelPage() {
		document.getElementById("method").value="initPage";
		document.assignClassForStudentForm.submit();
	}
	
	function getStudentList(){
		document.getElementById("method").value="studentSearch";
		document.assignClassForStudentForm.submit();
	}
	
	function getCourses(programId) {
		resetOption("course");
		getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	}
	
	function updateCourses(req) {
		updateOptionsFromMap(req,"course","- Select -");
	}
</script>

<html:form action="/AssignClassForStudent">
<html:hidden property="method" styleId="method" value="assignClass"/>
<html:hidden property="formName" value="assignClassForStudentForm"/>
<html:hidden property="pageType" value="2"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; Students List&gt;&gt;</span></span></td>
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
                       			<td class="row-odd"><div align="right"><span class="Mandatory">*</span> Admission Year:</div></td>
	                             <td class="row-even" align="left">
	                             <html:select property="admissionYear" styleId="admissionYear" styleClass="combo">
			                     	 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			                     	 <cms:renderAcademicYear></cms:renderAcademicYear>
			                     </html:select>  
	                             </td>
	                             <td class="row-odd"><div align="right"><span class="Mandatory">*</span> Academic Year:</div></td>
	                             <td class="row-even" align="left">
	                             <html:select property="academicYear" styleId="academicYear" styleClass="combo">
			                     	 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			                     	 <cms:renderAcademicYear></cms:renderAcademicYear>
			                     </html:select>  
	                             </td>
                       		</tr>
                           <tr >
                             <td height="25" class="row-odd" ><div id = "reFrom" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program" /> </div></td>
                             <td height="25" class="row-even" align="left">
                           		 <label>
									<html:select property="programId" styleClass="comboLarge"
										styleId="programId" onchange="getCourses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="programMap" label="value"
															value="key" />
									</html:select>
								</label>
							</td>
									<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									
									<td height="25" class="row-even">
									<html:select property="courseId"
										styleId="course" styleClass="comboExtraLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
												<c:if test="${courseMap != null}">
													<html:optionsCollection name="courseMap" label="value"
														value="key" />
												</c:if>
									</html:select></td>
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
                   <tr height="10px"></tr>
                   <tr>
                   <td>
                   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       		<div id="display_Details">
                       		<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
										<div align="center">Select</div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div id="displayNo1">Sort Order
										<html:text property="languageNo" styleId="languageNo" size="3" onkeyup="validateLanguageNumber(this.value)"></html:text>
									</div>
									<div align="center">Second Language</div>
									</td>
									<td class="row-odd">
									<div id="displayNo2" align="center">Sort Order
										<html:text property="percentageNo" styleId="percentageNo" size="3" onkeyup="validatePercentage(this.value)"></html:text>
									</div>
									<div align="center">Percentage</div>
									</td>
									<td class="row-odd">
									<div id="displayNo3" align="center">Sort Order
										<html:text property="genderNo" styleId="genderNo" size="3" onkeyup="validateGender(this.value)"></html:text>
									</div>
									<div align="center">Gender</div>
									</td>
									<td class="row-odd">
									<div id="displayNo4" align="center">Sort Order
										<html:text property="nameNo" styleId="nameNo" size="3" onkeyup="validateName(this.value)"></html:text>
									</div>
									<div align="center">Name</div>
									</td>
									<td class="row-odd">
									<div id="displayNo5" align="center">Sort Order
										<html:text property="categoryNo" styleId="categoryNo" size="3" onkeyup="validateCategory(this.value)"></html:text>
									</div>
									<div align="center">Domicile Status</div>
									</td>
									<td class="row-odd">
									<div id="displayNo5" align="center">Sort Order
										<html:text property="sectionNo" styleId="sectionNo" size="3" onkeyup="validateSection(this.value)"></html:text>
									</div>
									<div align="center">Category</div>
									</td>
									<td class="row-odd">
									<div id="displayNo7" align="center">Sort Order
										<html:text property="classNo" styleId="classNo" size="3" onkeyup="validateClass(this.value)"></html:text>
									</div>
									<div align="center">Class</div>
									</td>
								</tr>
							</table>
                       </div>
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
                 <tr height="15px"></tr>
                 <tr height="10px">
                 <td height="20"><div align="center">
                 	
		                      <html:button property="" styleClass="formbutton" value="Submit" onclick="getStudentList()"></html:button>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
		          
                 		</div>
                 </td>
                 
                 </tr>
                 <tr height="20px">
                 	<td> <div id="display"></div>  <div id="display_message"></div> </td>
                 </tr>
                 <tr>
                 <td>
				<logic:notEmpty property="assingClassForStudentTOs" name="assignClassForStudentForm">
                   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       		<div id="display_Details">
                       		<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<html:text property="startNumber" styleId="startNumber" onkeyup="selectSome(this)" size="3"></html:text>&nbsp;&nbsp;
									<html:text property="endNumber" styleId="endNumber" onkeyup="selectMiddle(this)" size="3"></html:text>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center">Second Language</div>
									</td>
									<td class="row-odd">
									<div align="center"> Percentage</div>
									</td>
									<td class="row-odd">
									<div align="center"> Gender</div>
									</td>
									<td class="row-odd">
									<div align="center"> Appln No</div>
									</td>
									<td class="row-odd">
									<div align="center"> Name</div>
									</td>
									<td class="row-odd">
									<div align="center"> Domicile Status</div>
									</td>
									<td class="row-odd">
									<div align="center"> Category</div>
									</td>
									<td class="row-odd">
									<div align="center"> Class</div>
									</td>
								</tr>
									
									<nested:iterate id="to" property="assingClassForStudentTOs" name="assignClassForStudentForm" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td width="10%" height="25">
										<div align="center">
										<nested:checkbox property="checked" onclick="validateCheckBox()"> </nested:checkbox>
										</div>
										</td>
										<td align="center" width="12%" height="25"><nested:write
											 property="secondLanguage" /></td>
										<td align="center" width="8%" height="25"><nested:write
											 property="percentage" /></td>
										<td align="center" width="8%" height="25"><nested:write
											 property="gender" /></td>
										 <td align="center" width="8%" height="25"><nested:write
											 property="applNo" /></td>
										<td width="22%" height="25"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<nested:write
											 property="studentName" /></td>
										<td align="center" width="8%" height="25"><nested:write
											 property="category" /></td>
										<td align="center" width="10%" height="25"><nested:write
											 property="sectionName" /></td>
										<td align="center" width="8%" height="25"><nested:write
											 property="className" /></td>
									</nested:iterate>
							</table>
                       </div>
                       </td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>                 
                 </logic:notEmpty>
                 </td>
                 </tr>
                 <tr height="15px"></tr>
                 <tr><td> 
                  <logic:notEmpty property="assingClassForStudentTOs" name="assignClassForStudentForm">
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
                             <td height="25" class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.assignClasses.class" /></td>
                             <td height="25" class="row-even" align="left">
                            
                             
                           		 <label>
									<html:select styleId="classes" property="classes" size="5" style="width:200px" multiple="multiple">
										<c:if test="${classMap != null}">
										<html:optionsCollection name="classMap" label="value"
															value="key" />
															</c:if>
									</html:select>
								</label>
							
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
		                     <logic:notEmpty property="assingClassForStudentTOs" name="assignClassForStudentForm">
			   				  <html:submit property="" styleId="print" styleClass="formbutton" value="Submit"></html:submit>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
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
<script language="JavaScript" >


function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("display").innerHTML = "Number of selected students:"+checkBoxselectedCount;
}

function validateLanguageNumber(number){
	var percentage = document.getElementById("percentageNo").value;
	var gender = document.getElementById("genderNo").value;
	var name = document.getElementById("nameNo").value;
	var category = document.getElementById("categoryNo").value;
	var section = document.getElementById("sectionNo").value;
	var classNo = document.getElementById("classNo").value;
	
	if(number != null && number != ""){
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
	
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}


function validatePercentage(number){
	var language = document.getElementById("languageNo").value;
	var gender = document.getElementById("genderNo").value;
	var name = document.getElementById("nameNo").value;
	var category = document.getElementById("categoryNo").value;
	var section = document.getElementById("sectionNo").value;
	var classNo = document.getElementById("classNo").value;

	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
	
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}

function validateGender(number){
	var language = document.getElementById("languageNo").value;
	var percentage = document.getElementById("percentageNo").value;
	var name = document.getElementById("nameNo").value;
	var category = document.getElementById("categoryNo").value;
	var section = document.getElementById("sectionNo").value;
	var classNo = document.getElementById("classNo").value;

	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
	
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}


function validateName(number){
	var language = document.getElementById("languageNo").value;
	var percentage = document.getElementById("percentageNo").value;
	var gender = document.getElementById("genderNo").value;
	var category = document.getElementById("categoryNo").value;
	var section = document.getElementById("sectionNo").value;
	var classNo = document.getElementById("classNo").value;

	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}

function validateCategory(number){
	var language = document.getElementById("languageNo").value;
	var percentage = document.getElementById("percentageNo").value;
	var gender = document.getElementById("genderNo").value;
	var name = document.getElementById("nameNo").value;
	var section = document.getElementById("sectionNo").value;
	var classNo = document.getElementById("classNo").value;
	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}

function validateSection(number){
	var language = document.getElementById("languageNo").value;
	var percentage = document.getElementById("percentageNo").value;
	var gender = document.getElementById("genderNo").value;
	var name = document.getElementById("nameNo").value;
	var category = document.getElementById("categoryNo").value;
	var classNo = document.getElementById("classNo").value;

	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(classNo != null && classNo != ""){
			if(number == classNo){
				alert("You already selected this number");
			}
		}
	}
}

function validateClass(number){
	var language = document.getElementById("languageNo").value;
	var percentage = document.getElementById("percentageNo").value;
	var gender = document.getElementById("genderNo").value;
	var name = document.getElementById("nameNo").value;
	var category = document.getElementById("categoryNo").value;
	var section = document.getElementById("sectionNo").value;

	if(number != null && number != ""){
		if(language != null && language != ""){
			if(number == language){
				alert("You already selected this number");
			}
		}
	
		if(percentage != null && percentage != ""){
			if(number == percentage){
				alert("You already selected this number");
			}
		}
	
		if(gender != null && gender != ""){
			if(number == gender){
				alert("You already selected this number");
			}
		}
	
		if(name != null && name != ""){
			if(number == name){
				alert("You already selected this number");
			}
		}
		if(category != null && category != ""){
			if(number == category){
				alert("You already selected this number");
			}
		}
		if(section != null && section != ""){
			if(number == section){
				alert("You already selected this number");
			}
		}
	}
}
</script>