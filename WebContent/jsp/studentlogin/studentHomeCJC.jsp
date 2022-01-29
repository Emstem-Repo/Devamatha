<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript" src="js/clockh.js"></script>
<script type="text/javascript" src="js/clockp.js"></script>
	<script type="text/javascript">
	// 1.
	$(document).ready(function(){
		 
        $("#mobileNo").each(function() 
    	     {
            $(this).keyup(function(){
                calculate();
            });
        });
    });
 
    function calculate() {
        $("#mobileNo").each(function() {
            if(!isNaN(this.value) && this.value.length!=0) {
            	if(this.value.length > 10 || this.value.length < 10){
            		document.getElementById('button').style.visibility='hidden';
                	}else{
                		document.getElementById('button').style.visibility='visible';
                	}
            }
        });
    }
	</script>
<script language="JavaScript">
	
 	 function submit(){
			var mobileNo=document.getElementById("mobileNo").value;
			document.location.href = "StudentLoginAction.do?method=submitMobileNo&mobileNo=" +mobileNo;
 	 	 }
 	function openHtml() {
 		var url="StudentLoginAction.do?method=help";
 		win2 = window.open(url, "Help", "left=1350,top=550,width=200,height=100,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
 	}
 	</script>
<body>
<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td >&nbsp;</td>
    <td width="7">&nbsp;</td>
    <td>&nbsp;</td>
    <td width="7">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td valign="top"><!-- <table width="201" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.navigation"/></td>
                    <td width="13" class="tr">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="13" class="le">&nbsp;</td>
                    <td class=""><table border="0" cellspacing="0" cellpadding="0">
                       
                        <tr>
                         <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                          <td width="137" height="21" class="navmenu"><a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance"/></a></td>
                          </tr>
                          <tr>
                          <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                           <td width="137" height="21" class="navmenu"><a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseActivityAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance.activity.attendance"/> </a></td>
                        </tr>
                        <%-- 
                         <tr>
                          <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                          <td height="21" class="navmenu"><a href="studentreqstatus.do?method=initViewStudentDetails" class="navmenu"><bean:message key="knowledgepro.hostel.studentreqstatus"/></a></td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                          <td height="21" class="navmenu"><a href="HostelApplication.do?method=initHostelApplicationStudent" class="navmenu"><bean:message key="knowledgepro.hostel.app.student"/></a></td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                        --%>
                    </table></td>
                    <td class="ri">&nbsp;</td>
                  </tr>
                  <tr>
                    <td class="bl">&nbsp;</td>
                    <td class="bm">&nbsp;</td>
                    <td class="br">&nbsp;</td>
                  </tr>
              </table></td>

        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="30" valign="bottom"><p>&nbsp;</p>
          <p>&nbsp;</p>
          <p><img src="images/bullet_imge.gif" width="170" height="8" /></p></td>
        </tr>
      </table></td>
    <td width="7"><p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p></td>
    -->
</td>
	<td valign="top"><table width="248" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.newandevents"/></td>
              <td width="13" class="tr">&nbsp;</td>
            </tr>
            <tr>
              <td width="13" class="le">&nbsp;</td>
              <td><table width="246" border="0" cellspacing="0" cellpadding="3" height="180">
                  <tr><td class="navmenu1">
					<marquee behavior="scroll" direction="up"
						scrollamount="1" width="200" 
						style="padding: top 10px;"
						onmouseover="this.setAttribute('scrollamount', 0, 0);"
					onmouseout="this.setAttribute('scrollamount', 1, 0);"
					class="navmenu1">
					<c:out	value='${loginform.description}' escapeXml='false' />
                    </marquee>
					</td></tr>
              </table></td>
              <td class="ri">&nbsp;</td>
            </tr>
            <tr>
              <td class="bl">&nbsp;</td>
              <td class="bm">&nbsp;</td>
              <td class="br">&nbsp;</td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="23" align="center"><img src="images/dot_img.gif" width="247" height="1" /></td>
        </tr>
        <tr>
          <td><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.notifications"/></td>
              <td width="13" class="tr">&nbsp;</td>
            </tr>
            <tr>
              <td width="13" class="le">&nbsp;</td>
              <td><table height="149" width="100%" border="0" cellpadding="3" cellspacing="0">
                  <!-- <tr>
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                    <c:if test="${!linkForHolisticExam || linkForCjc==true}">
                     <td class="navmenu1"><bean:message key="knowledgepro.nonotifications"/> </td>
                     </c:if>
                    <td><c:if test="${linkForCjc==false}">
                    <c:if test="${linkForHolisticExam}">
						   <a href="onlineExamSuppApplication.do?method=initOnlineSuppExam" class="navmenu" style="text-decoration: blink;color: red">Holistic/Indian Constitution Repeat Exam Application</a>
				    </c:if>
				    </c:if>
				    </td>
                  </tr>-->
                  <tr>
                   <c:if test="${linkForCjc==false}">
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                     <td class="navmenu1"><blink><B><a href="StudentLoginAction.do?method=initSapRegistration" class="navmenu" style="text-decoration: blink;color: red">Christ University SAP-e Academy Course</a></B></blink></td>
                      </c:if>
                  </tr>
                   
                   <tr>
              <!--  <c:if test="${linkForCjc==false}">
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                  <td class="navmenu1"><blink>
						   <a href="studentCarPass.do?method=initStudentCarPass" class="navmenuRed">Click here for Car Pass</a>
				  	</blink></td> 
				  	
				    </c:if> -->
				    
				    <c:if test="${linkForCjc==false}">
                    	<td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                    	<td class="navmenu1"><a target="_blank" href="./downloadForms/Student Regulations & Sample Seating Chart.pdf" class="navmenuRed">Student Regulations & Sample Seating Chart</a></td>
                    </c:if>
				    
                  </tr>
                  <tr>
                  <c:if test="${linkForCjc==false}">
                    <c:if test="${finalYearStudent==true}">
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                    <td>
						   <a href="onlineExamSuppApplication.do?method=initConvocationRegistration" class="navmenu" style="text-decoration: blink;color: red">Convocation Registration</a>
				  	</td>
				    </c:if>
				    </c:if>
				    
                  </tr>
                  <tr>
                   <c:if test="${linkForCjc==false}">
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                     <td class="navmenu1"><a target="_blank" href="./downloadForms/ExamRegulations.pdf" class="navmenuRed">Exam Regulations</a></td>
                      </c:if>
                  </tr>
                  <tr>
                    <c:if test="${studentAchievement!=null}">
                    <td width="10" align="center"><img src="images/bullet_imgs.gif" width="8" height="6" /></td>
                    <td>
                    <font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: green;text-decoration: blink;">
						   <bean:write name="studentAchievement" scope="session"/></font>
				  	</td>
				    </c:if>
				    
                  </tr>
              </table></td>
              <td class="ri">&nbsp;</td>
            </tr>
            <tr>
              <td class="bl">&nbsp;</td>
              <td class="bm">&nbsp;</td>
              <td class="br">&nbsp;</td>
            </tr>
          </table></td>
        </tr>
      </table></td>
    <td width="15" align="center">&nbsp;</td>
    <td align="right" valign="top" ><table width="398" border="0" cellspacing="0" cellpadding="0">
        <!-- <tr>
          <td><table width="380" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.dateandtime"/></td>
              <td width="13" class="tr">&nbsp;</td>
            </tr>
            <tr>
              <td width="13" height="142" class="le">&nbsp;</td>
              <td height="150">
              <table width="350" border="0" align="center" cellpadding="0" cellspacing="0" class="calenderbg">
                  <tr>
                    <td width="200" height="143" align="center"><div id="clock_a" style="background:url(images/clock-face.gif) no-repeat; width:120px; background-position:top left; padding:9px; margin:8px auto auto auto;" ></div></td>
                    <td align="left">
                    <script type="text/javascript">
						var todaydate=new Date()
						var curmonth=todaydate.getMonth()+1 //get current month (1-12)
						var curyear=todaydate.getFullYear() //get current year
						
						document.write(buildCal(curmonth ,curyear, "main", "month", "daysofweek", "days", 0));
					</script>
					</td>
                  </tr>
              </table>
              </td>
              <td class="ri">&nbsp;</td>
            </tr>
            <tr>
              <td height="61" class="bl">&nbsp;</td>
              <td class="bm">&nbsp;</td>
              <td class="br">&nbsp;</td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="20" align="center"><img src="images/dot_imga.gif" width="397" height="1" /></td>
        </tr>-->
        <tr>
          <td valign="top"><table width="500" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.profile"/></td>
              <td width="13" class="tr">&nbsp;</td>
            </tr>
            <tr>
          
              <td width="13" class="le" valign="top">&nbsp;</td>
              <td valign="top"><table cellspacing="1" cellpadding="2" border="1" bordercolor="ffffff" height="410" width="100%">
                <tr>
                  <td width="151" class="studentrow-even" align="center" valign="top" colspan="2"><div style="border-style:solid; border-width :1px;border-color:#3292BA;height: 129px; width: 133px">
	                  <img src='<%=session.getAttribute("STUDENT_IMAGE")%>' width="133" height="129" align="top"/></div>
                  </td>
                  </tr>
                 <tr >
                    <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.studentlogin.name"/>:</td>
                    <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="studentName"/></td>
                   </tr>
                    <tr>
                    <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</td>
                    <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="className"/></td>
                 </tr>
                 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.fatherName"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="fatherName"/></td>
                 </tr>
                    <tr>  
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.motherName"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="motherName"/></td>
                 </tr>
				 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.studentinfo.nationality.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="nationality"/></td>
                </tr>
                    <tr>   
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.studentinfo.bloodgroup.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="bloodGroup"/></td>
                 </tr>
                 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.emailId"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="contactMail"/></td>
                  </tr>
                    <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.studentLogin.bankAcNo"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="bankAccNo"/></td>
                 </tr>     
				 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.phone.main.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="phNo1"/></td>
                   </tr>
                 <tr>
                   <td class="studentrow-odd" align="left"  width="35%"><bean:message key="admissionForm.studentinfo.currAddr.label"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="currentAddress1"/>,
                   													   <bean:write name="loginform" property="currentAddress2"/>,
                   													   <bean:write name="loginform" property="currentCity"/>,
                   													   <bean:write name="loginform" property="currentState"/>, 
                   													   <bean:write name="loginform" property="currentPincode"/>
				   </td>
				  </tr>
                  <tr>
                   <td class="studentrow-odd" align="left"  width="35%"><bean:message key="admissionForm.studentinfo.permAddr.label"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="permanentAddress1"/>,
                    												   <bean:write name="loginform" property="permanentAddress2"/>,
                    												   <bean:write name="loginform" property="permanentCity"/>, 
                   													   <bean:write name="loginform" property="permanentState"/>,
                                                                       <bean:write name="loginform" property="permanentPincode"/>
				   </td>
                 </tr> 
                 <tr>
                <td class="studentrow-odd" align="left"  width="35%"><bean:message key="knowledgepro.studentlogin.mobile.no"/>:</td>
                <td class="studentrow-even" align="left" width="65%"><html:text property="mobileNo" name="loginform" styleClass="studentrow-even" size="12" maxlength="10" styleId="mobileNo" /> 
					<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml()" title="Help"><div id="messageBox">
	                <div id="contents"></div></div>
	                <html:button property="" styleClass="btnbg" styleId="button" value="Update" onclick="submit()"></html:button></td>
                 
                 </tr>
                 
				
				
              
     <%--            <tr>
                  <td>&nbsp;</td>
                  <td align="center" class="navmenu1"><a href="#"><img src="images/req_img.gif" width="100" height="23" border="0" /></a></td>
                </tr>
     --%>
              </table></td>
              <td class="ri">&nbsp;</td>
            </tr>
            <tr>
              <td class="bl">&nbsp;</td>
              <td class="bm">&nbsp;</td>
              <td class="br">&nbsp;</td>
            </tr>
          </table>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top">&nbsp;</td>
    <td>&nbsp;</td>
    <td valign="top">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="right" valign="top" >&nbsp;</td>
  </tr>
</table>
</body>
<script language="JavaScript">
document.getElementById('button').style.visibility='hidden';
</script>