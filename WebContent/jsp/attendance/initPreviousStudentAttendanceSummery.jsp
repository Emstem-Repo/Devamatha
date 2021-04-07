<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	
</script>
	<html:form action="/studentWiseAttendanceSummary" >
<c:choose>
  <c:when test="${linkForCjc}">
      <html:hidden property="method" styleId="method" value="getPreviousStudentWiseAttendanceSummaryCjc"/>
  </c:when>
   <c:otherwise>
      <html:hidden property="method" styleId="method" value="getPreviousStudentWiseSubjectSummaryChrist"/>
  </c:otherwise>
</c:choose>

<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="4"/>

	 <div class="pageheader">
                        <div class="media">
                            <div class="Container">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
                                    <li><a href="#">Student Login</a></li>
                                    <li>Previous class Attendance</li>
                                </ul>
                                
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->


<div class="panel-body">  
	<div class="panel panel-primary">
	<div class="panel-heading">
	<h1 class="panel-title">Previous class Attendance</h1>
	</div>
	  </div>
                          <div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
					<div class="form-group">
	                <div class="col-sm-4">
							
								
	                       				<div id="errorMessage">
	                       				<p>
										<span id="err"><html:errors/></span> 
										</p>
	                       					<FONT color="green">
											<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
											</html:messages>
						 				 </FONT>
						  			</div>
						  			</div>
						</div>
						
									<div class="form-group">
									   <div class="col-sm-12">
	                                 <div class="col-sm-4">
									<div id="dataRegularLabel">
										<span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.classname"/></div>
										</div>
									 <div class="col-sm-4">
									 <div id="dataRegular">
									 
										<html:select property="classesId" styleId="regularExamId" styleClass="btn btn-white dropdown-toggle">
											<html:option value="">Select</html:option>
												<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="classMap">
												<html:optionsCollection name="studentWiseAttendanceSummaryForm" property="classMap" label="value" value="key" />
										</logic:notEmpty>
										 </html:select>
										 
                    </div>
												</div>
							 	    </div>
							 	  </div>
								 
				 <div class="form-group">
							<div class="col-sm-10 text-center">
							
                              <html:submit property="" value="Submit" styleClass="btn-success" onclick="submitForAttendance()"></html:submit>
                             <html:button property="" value="Close" styleClass="btn-danger" onclick="cancelAction()"></html:button>
							</div>
					</div>
		</div>
	
		</div>
		
	
</html:form>
