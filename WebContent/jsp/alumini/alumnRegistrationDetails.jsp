<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html lang="en" >

	<head>
	    <meta charset="UTF-8">
	    <title>Knowledge Pro</title>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
	    <link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>
	    <link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css'>
	    <link rel='stylesheet' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
	    
	    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
	    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
  	</head>

	<body>
		<div class="container">
			<html:form styleClass="well form-horizontal" action="/alumnRegistration" method="post">
      		
      			<html:hidden property="formName" value="aluminiRegistrationForm"/>
				<html:hidden property="method" styleId="method" value="submitAlumnRegistration"/>
				<html:hidden property="pageType" value="1"/>
				<html:hidden property="educationDetailsSize" styleId="educationDetailsSize"/>
		
        		<fieldset>

	          		<!-- Form Name -->
	          		<legend><center><h2><b>ALUMNI REGISTRATION FORM</b></h2></center></legend><br>
          
					<!-- Error message -->
          			<div class="alert alert-danger" role="alert" id="error_message">
          				Fields marked with <span style="color: red;"><sup>*</sup></span> are mandatory.<br></br>
            			<html:errors/>
          			</div>

          			<div class="form-group">
            			<label class="col-md-4 control-label">Full Name<span style="color: red;"><sup>*</sup></span></label>  
            			<div class="col-md-4 inputGroupContainer">
              				<div class="input-group">
                				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                				<html:text property="firstName" styleId="firstName" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
              				</div>
            			</div>
          			</div>

          			<div class="form-group">
            			<label class="col-md-4 control-label" >Mobile number<span style="color: red;"><sup>*</sup></span></label> 
            			<div class="col-md-4 inputGroupContainer">
              				<div class="input-group">
                				<span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                				<html:text property="mobileNo2" styleId="mobileNo2" name="aluminiRegistrationForm" styleClass="form-control" onkeypress="return isNumberKey(event)" maxlength="12"></html:text>
              				</div>
            			</div>
          			</div>
          
          			<div class="form-group">
            			<label class="col-md-4 control-label" >Email address</label> 
            			<div class="col-md-4 inputGroupContainer">
              				<div class="input-group">
                				<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                				<html:text property="emailId" styleId="emailId" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
              				</div>
            			</div>
          			</div>

	          		<div class="form-group"> 
	            		<label class="col-md-4 control-label">Current Status<span style="color: red;"><sup>*</sup></span></label>
	            		<div class="col-md-4 selectContainer">
	              			<div class="input-group">
	                			<span class="input-group-addon"><i class="glyphicon glyphicon-briefcase"></i></span>
	                			<html:select property="currentStatus" styleId="currentStatus" styleClass="form-control selectpicker" onchange="askWorkDetails(this.value)">
	                				<html:option value="">-Select-</html:option>
	                				<html:option value="Employee">Employee</html:option>
	                				<html:option value="Employer">Employer</html:option>
	                				<html:option value="Other">Other</html:option>
	                			</html:select>
	              			</div>
	            		</div>
	          		</div>
	          
		          	<div class="form-group" id="companyNameId" style="display: none;">
			            <label class="col-md-4 control-label" >Company name<span style="color: red;"><sup>*</sup></span></label> 
		            	<div class="col-md-4 inputGroupContainer">
		              		<div class="input-group">
			                	<span class="input-group-addon"><i class="glyphicon glyphicon-briefcase"></i></span>
			                	<html:text property="companyName" styleId="companyName" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
		              		</div>
		            	</div>
		          	</div>
	          
	          		<div class="form-group" id="designationId" style="display: none;">
	            		<label class="col-md-4 control-label" >Designation<span style="color: red;"><sup>*</sup></span></label> 
	            		<div class="col-md-4 inputGroupContainer">
	              			<div class="input-group">
	                			<span class="input-group-addon"><i class="glyphicon glyphicon-briefcase"></i></span>
	                			<html:text property="designation" styleId="designation" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
	              			</div>
	            		</div>
	          		</div>
	          
	          		<div class="form-group" id="otherJobDetailsId" style="display: none;">
	           	 		<label class="col-md-4 control-label" >Other details<span style="color: red;"><sup>*</sup></span></label> 
	            		<div class="col-md-4 inputGroupContainer">
	              			<div class="input-group">
	                			<span class="input-group-addon"><i class="glyphicon glyphicon-briefcase"></i></span>
	                			<html:text property="otherJobDetails" styleId="otherJobDetails" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
	              			</div>
	            		</div>
	          		</div>
	      
	          		<div class="form-group">
	            		<label class="col-md-4 control-label">Country<span style="color: red;"><sup>*</sup></span></label>  
	            		<div class="col-md-4 inputGroupContainer">
	              			<div class="input-group">
	                			<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
	                			<html:text property="countryName" styleId="countryName" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
	             	 		</div>
	            		</div>
	          		</div>
	          
	          		<div class="form-group">
	            		<label class="col-md-4 control-label">City<span style="color: red;"><sup>*</sup></span></label>  
	            		<div class="col-md-4 inputGroupContainer">
	              			<div class="input-group">
		                		<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
		                		<html:text property="city" styleId="city" name="aluminiRegistrationForm" styleClass="form-control"></html:text>
	              			</div>
	            		</div>
	          		</div>
	          		
	          		<div class="form-group">
	            		<label class="col-md-4 control-label">Highest Qualification<span style="color: red;"><sup>*</sup></span></label>  
	            		<div class="col-md-4 inputGroupContainer">
	              			<div class="input-group">
		                		<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
		                		<html:select property="highestQualification" styleId="highestQualification" styleClass="form-control selectpicker">
	                				<html:option value="">-Select-</html:option>
	                				<html:option value="Pre-Degree">Pre-Degree</html:option>
	                				<html:option value="Degree">Degree</html:option>
	                				<html:option value="PG">PG</html:option>
	                				<html:option value="MPhil">MPhil</html:option>
	                				<html:option value="PhD">PhD</html:option>
	                			</html:select>
	              			</div>
	            		</div>
	          		</div>
	          		
         			<div align="center" class="well well-sm">
         					
        					<nested:iterate id="education" name="aluminiRegistrationForm" property="educationDetails" indexId="count">
	          				<%
	          					String courseId = "courseId_" + count;
	          					String batchFrom = "batchFrom_" + count;
	          					String batchTo = "batchTo_" + count;
	          					String passOutYear = "passOutYear_" + count;
	          					String tempYearFromId = "tempYearFromId_" + count;
	          					String tempYearToId = "tempYearToId_" + count;
	          					String tempYearPassOutYearId = "tempYearPassOutYearId_" + count;
	          				%>
         			
		          			<div class="form-group">
		          				<label class="col-md-4 control-label">Course<span style="color: red;"><sup>*</sup></span></label>
		            			<div class="col-md-4 selectContainer">
		              				<div class="input-group">
		                				<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
		                				<nested:select property="courseId" styleId="<%=courseId %>" styleClass="form-control selectpicker">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<logic:notEmpty property="courseListStartApp" name="aluminiRegistrationForm">
												<html:optionsCollection property="courseListStartApp" name="aluminiRegistrationForm" label="name" value="id"/>
											</logic:notEmpty>
										</nested:select>	                				
		              				</div>
		            			</div>
		          			</div>

		          			<div class="form-group"> 
		            			<label class="col-md-4 control-label">Batch from<span style="color: red;"><sup>*</sup></span></label>
		            			<div class="col-md-4 selectContainer">
		              				<div class="input-group">
		                				<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
		                				<input type="hidden" id="<%=tempYearFromId %>" name="tempYearFrom" value='<nested:write property="batchFrom"/>'/>
		                				<nested:select property="batchFrom" styleId="<%=batchFrom %>" styleClass="form-control selectpicker">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderMinToMaxYearList></cms:renderMinToMaxYearList>
										</nested:select>
		              				</div>
		            			</div>
		          			</div>
			          
			          		<div class="form-group">
			           	 		<label class="col-md-4 control-label">Batch to<span style="color: red;"><sup>*</sup></span></label>
			            		<div class="col-md-4 selectContainer">
			              			<div class="input-group">
			                			<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
			                			<input type="hidden" id="<%=tempYearToId %>" name="tempYearTo" value='<nested:write property="batchTo"/>'/>
			                			<nested:select property="batchTo" styleId="<%=batchTo %>" styleClass="form-control selectpicker">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderMinToMaxYearList></cms:renderMinToMaxYearList>
										</nested:select>
			              			</div>
			            		</div>
			          		</div>
			          
			          		<div class="form-group"> 
			            		<label class="col-md-4 control-label">Pass out year<span style="color: red;"><sup>*</sup></span></label>
			            		<div class="col-md-4 selectContainer">
			              			<div class="input-group">
			                			<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
			                			<input type="hidden" id="<%=tempYearPassOutYearId %>" name="tempYearPassOutYear" value='<nested:write property="passOutYear"/>'/>
										<nested:select property="passOutYear" styleId="<%=passOutYear %>" styleClass="form-control selectpicker">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderMinToMaxYearList></cms:renderMinToMaxYearList>
										</nested:select>
			              			</div>
			            		</div>
			          		</div>
		          			
		          			<hr></hr>
		          			
          				</nested:iterate>
          				
          				<div class="form-group" >
		            		<label class="col-md-4 control-label"></label>
		            		<div class="col-md-4">
		            			<br>
		            			<html:button property="" styleClass="btn btn-info" onclick="addEdnDetails()">Add</html:button>
		            			<html:button property="" styleClass="btn btn-danger" onclick="removeEdnDetails()">Remove</html:button>
		            		</div>
		          		</div>
          			</div>
	
	          		<!-- Button -->
	          		<div class="form-group">
	            		<label class="col-md-4 control-label"></label>
	            		<div class="col-md-4">
	            			<br>
	              			&nbsp;&nbsp;&nbsp;&nbsp;
	              			&nbsp;&nbsp;&nbsp;&nbsp;
	              			&nbsp;&nbsp;&nbsp;&nbsp;
	              			&nbsp;&nbsp;&nbsp;&nbsp;
	              			<html:submit styleClass="btn btn-success">Register</html:submit>
	            		</div>
	          		</div>
	
				</fieldset>
			</html:form>
		</div><!-- /.container -->
	    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	    <script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
	    <script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
	    <script  src="js/alumni/index.js"></script>
	    <script type="text/javascript" src="js/admission/UniqueIdRegistrationPage.js"></script>
	    <script type="text/javascript" src="js/alumini/alumnRegistrationDetails.js"></script>
	</body>

</html>