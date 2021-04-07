<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
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
	function resetData() {
		resetFieldAndErrMsgs();
	}

	function getExamNameByYear(year) {
		getExamNameByExamTypeYearWise("examMap", "Both",year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function allotment(){
		document.getElementById("method").value="roomAllotment";
		document.examRoomAllotmentForm.submit();
	}
	function canclePage(){
		document.location.href="ExamRoomAllotment.do?method=initRoomAllotment";
	}
</script>

<html:form action="/ExamRoomAllotment">
<html:hidden property="method" styleId="method" value="getDetailsForAllotment"/>
<html:hidden property="formName" value="examRoomAllotmentForm"/>
<html:hidden property="pageType" value="1"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; Room Allotment&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Room Allotment</strong></div></td>
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
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td height="25" class="row-odd" width="24%">
									<div align="right">Program Type Wise Allotment: </div>
								</td>
								<td class="row-even" width="24%">
									<div align="left">
										<html:radio property="programType" value="Yes">Yes</html:radio>
										&nbsp;&nbsp;<html:radio property="programType" value="NO">No</html:radio>
									</div>
								</td>
								<td height="25" class="row-odd" width="24%">
									<div align="right"><span class="Mandatory">*</span> Cycle </div>
								</td>
								<td class="row-even" width="24%">
									<div align="left">
										<html:select property="cycleId" styleClass="combo" styleId="cycleId" name="examRoomAllotmentForm">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<logic:notEmpty name="examRoomAllotmentForm" property="cycleMap">
												<html:optionsCollection property="cycleMap" name="examRoomAllotmentForm" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</div>
								</td>
							</tr>
							<tr>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Allotment Type: </div>
								</td>
								<td class="row-even">
									<div align="left">
										<html:select property="allotmentType" styleId="allotmentType" name="examRoomAllotmentForm" styleClass="body" multiple="multiple" size="5" style="width:350px">
											<html:option value="Pool">Pool</html:option>
											<html:option value="Group">Group</html:option>
											<html:option value="Specialization">Specialization</html:option>
										</html:select>
									</div>
								</td>
								<td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Campus:</td>
								<td class="row-even">
									<div align="left">
										<html:select property="campus" styleId="campus" name="examRoomAllotmentForm" styleClass="combo">
											<html:option value="">	<bean:message key="knowledgepro.admin.select" /> </html:option>
											<logic:notEmpty name="examRoomAllotmentForm"	property="workLocationMap"> 
												<html:optionsCollection property="workLocationMap"	name="examRoomAllotmentForm" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</div>
								</td>
							</tr>
							<tr>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.fee.academicyear"/>: </div>
								</td>
								<td class="row-even">
									<input type="hidden" id="tempYear" value="<bean:write name='examRoomAllotmentForm' property='year'/>"/>
									<html:select property="year" name="examRoomAllotmentForm" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderYear></cms:renderYear>
	                     			</html:select>
								</td>
								<td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Exam:</td>
								<td class="row-even">
									<div align="left">
										<html:select name="examRoomAllotmentForm" property="examId" styleId="examName" styleClass="combo">
										<html:option value="">	<bean:message key="knowledgepro.admin.select" /> </html:option>
											<logic:notEmpty name="examRoomAllotmentForm"	property="examNameList"> 
												<html:optionsCollection property="examNameList"	name="examRoomAllotmentForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select>
									</div>
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
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
			   				  <html:submit property="" styleId="print" styleClass="formbutton" value="Submit"></html:submit>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" >
                   <logic:notEmpty property="allotmentList" name="examRoomAllotmentForm">
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
								<tr class="row-odd">
									<td height="25" width="5%" align="center">Sl.NO</td>
									<td height="25" width="5%" align="center">Select All <input type="checkbox" id="checkAll" onclick="selectAll(this)"/></td>
									<td  width="24%" align="center"> Course</td>
									<td  width="24%" align="center"> Scheme No</td>
								</tr>
								<nested:iterate id="to" property="allotmentList" name="examRoomAllotmentForm" indexId="count">
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
										<td width="4%" height="25">
											<div align="center">
												<nested:checkbox property="checked" onclick="unCheckSelectAll()"> </nested:checkbox>
											</div>
										</td>
										<td height="25" width="24%" align="center"><bean:write name="to" property="courseName" /> </td>
										<td  width="24%" align="center"><bean:write name="to" property="semNo" /> </td>
								
								</nested:iterate>
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
                   </logic:notEmpty>
                   </td>
                 </tr>
                 <logic:notEmpty property="allotmentList" name="examRoomAllotmentForm">
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
			   				  <html:submit property="" styleId="submit" styleClass="formbutton" value="Submit" onclick="allotment()"></html:submit>
		                      <html:button property="" styleClass="formbutton" value="Cancel" onclick="canclePage()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 </logic:notEmpty>
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
if(document.getElementById("tempYear") != null && document.getElementById("tempYear").value != null&& document.getElementById("tempYear").value !=""){
	document.getElementById("year").value=document.getElementById("tempYear").value;
}
</script>