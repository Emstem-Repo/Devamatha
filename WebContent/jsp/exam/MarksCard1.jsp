<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function submitData() {
	document.marksCardGenerateForm.submit();
}
function backPage() {
	document.location.href="marksCardGenerate.do?method=initMarksCard";
}

function backToFirstPage() {
	document.location.href="marksCardGenerate.do?method=initMarksCard";
}
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

</script>

<html:form action="/marksCardGenerate" method="post">
	<html:hidden property="method" styleId="method" value="generateMarksCardForExamType" />
	<html:hidden property="formName" value="marksCardGenerateForm" />
	<html:hidden property="pageType" value="1" />

<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; Marks Card &gt;&gt;</span></span></td></tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Marks Card</strong></div>
					</td>

					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="err"></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
						<td align="right" width="25%" class="row-odd" height="15px">Year:</td>
						<td width="25%" class="row-even" height="15px"><bean:write name="marksCardGenerateForm" property="year"/></td>
						<td align="right" width="25%" class="row-odd" height="15px">Class:</td>
						<td width="25%" class="row-even" height="15px"><bean:write name="marksCardGenerateForm" property="className"/></td>
                	</tr>
                	<tr>
                		<td class="row-odd" align="right" height="15px">Exam Type:</td>
                  		<td class="row-even" height="15px"><bean:write name="marksCardGenerateForm" property="examType"/></td>
                  		<td class="row-odd" align="right" height="15px">Exam Name:</td>
                   		<td class="row-even" height="15px"><bean:write name="marksCardGenerateForm" property="examNameId"/></td>
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
                  
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                       		<td height="25" align="center" class="row-odd">Select<br>
									<input type="checkbox" name="checkbox2" id="checkAll"  onclick="selectAll(this)"></td>
                        <td width="160" class="row-odd" align="center">
                        
                        		Exam Code
                        	
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        
                      </tr>
                      <nested:iterate id="student" property="studentList" name="marksCardGenerateForm" indexId="count">
                      
                      <c:if test="${count < marksCardGenerateForm.halfLength}">
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					 <td height="25" align="center" >
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                       
                          	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox(),unCheckSelectAll()"/>
                       <script type="text/javascript">
							var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                        <td width="193" align="center">
                         		<nested:write name="student" property="registerNo"/>
                        </td>
                        
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        
                      </tr>
                      </c:if>
                      </nested:iterate>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
              
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                        <td width="40" height="25" class="row-odd" align="center"></td>
                        <td width="160" class="row-odd" align="center">
                        		Exam Code
                        	
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        
                      </tr>
                      <c:set var="c" value="0"/>
                      <nested:iterate id="student" property="studentList" name="marksCardGenerateForm" indexId="count">
                      <c:set var="c" value="${c + 1}"/>
                      <c:if test="${count >= marksCardGenerateForm.halfLength}">
                      <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					 <td height="25" align="center">
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        
                           	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
                        
						<script type="text/javascript">
							var studentId1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                        <td width="193" align="center">
                           		<nested:write name="student" property="registerNo"/>
                        </td>
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        
                      </tr>
                      </c:if>
                      </nested:iterate>
                      <c:if test="${(c % 2) != 0}" >
                      <tr  class="row-white">
                        <td width="193"  >&nbsp;</td>
                        <td width="212" >&nbsp;</td>
                        <td height="25" align="center" >&nbsp;
                      </td>
                      </tr>
                      </c:if>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="35" align="center">
            <html:submit property="" styleClass="formbutton" value="Submit" ></html:submit>
            &nbsp;&nbsp;&nbsp;
            
            	<html:button property="" styleClass="formbutton" value="Cancel" onclick="backToFirstPage()"></html:button>
           
            </td>
			
          </tr>
        </table>
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
