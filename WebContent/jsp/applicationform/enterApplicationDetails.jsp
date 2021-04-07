<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
<meta charset="UTF-8">
<title>Knowledge Pro</title>

<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"
	type="text/javascript"></script>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css"
	rel="stylesheet" type="text/css" />

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>


<script
	src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script type="text/javascript"
	src="js/admission/UniqueIdRegistrationPage.js"></script>


</head>

<body>
<html:form action="/applicationFormRegistration" method="post">
	<html:hidden property="method" value="printApplicationForm" />
	<html:hidden property="formName" value="applicationRegistrationForm" />


	<div class="container">
	<fieldset><legend>
	<center>
	<h2><b>Bharata Mata College, Thrikkakara, Kochi-682021</b></h2>
	</center>
	</legend><br>

	<legend>
	<center>
	<h4><b>Print Application for Appointment as Assistant
	Professor</b></h4>
	</center>
	</legend><br>
	<div class="alert alert-danger" id="error_message">Fields marked
	with <span style="color: red;"><sup>*</sup></span> are mandatory.<br></br>
	<html:errors /></div>

	<div class="form-group"><label class="col-md-2 control-label">Date
	of Birth<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div id="datepicker" class="input-group date"><span
		class="input-group-addon"><i
		class="glyphicon glyphicon-calendar"></i></span> <html:text
		name="applicationRegistrationForm"
		styleClass="form-control datepicker" styleId="dateOfBirth"
		property="dateOfBirth"></html:text> <script type="text/javascript">
	$('.datepicker').datepicker( {
		format : "dd/mm/yyyy",
		size : 'large'
	});
</script>

</div>
	</div>




	<label class="col-md-2 control-label">Mobile number<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"><i
		class="glyphicon glyphicon-phone"></i></span> <html:text property="mobileNo"
		styleId="mobileNo" name="applicationRegistrationForm"
		styleClass="form-control" onkeypress="return isNumberKey(event)"
		maxlength="12"></html:text></div>
	</div>
	</div>
	<div class="form-group" style="line-height: 70px;">
	 <br/><p></p>
	</div>
	
	<div class="form-group">
         <div style="width: 1200px;" align="center">
         <html:submit styleClass="formbutton btn btn-primary" value="Submit"></html:submit></div>
	</div>
	
	</fieldset>
	</div>

</html:form>
</body>
</html:html>
