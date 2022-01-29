	$(function() {
		    $("#username").attr("placeholder", "User Name");
		});
	 $(function() {
		    $("#password").attr("placeholder", "Password");
		});
	 $(document).ready(function() {
		   $('#username').attr('autocomplete','off');
		    $('#password').attr('autocomplete','off');
		    $('#form').attr('autocomplete','off');
			//var urlImage="dist/images/photos/adminLogin.jpg?"+new Date().getTime();
		    var urlImage="images/AdminLogin/Devamatha-background.JPG?"+new Date().getTime();
			$('#wrapper').css("background-image", "url("+urlImage+")");
			$('#wrapper').css("background-repeat", "no-repeat");
			$('#wrapper').css("background-position", "center");
			$('#wrapper').css("background-attachment", "fixed");
			$('#wrapper').css("background-size", "cover");
			 $("#timer").hide();
			 $("#timer1").hide();
		 
			 $('#submit').click(function(e){
			 var username=  $('#username').val();
			 var password=  $('#password').val();
			 if(username=="" && password=="")
			 {
				 $('#errorMessage').slideDown().html("<span>Please enter username and password</span>");
				 return false;
			 }
			 else if(username=="")
			  {
			  $('#errorMessage').slideDown().html("<span>Please enter username</span>");
			  return false;
			  }
			 else if(password=="")
			  {
			  $('#errorMessage').slideDown().html('<span>Please enter password</span>');
			  return false;
			  }else if (username.indexOf('\'') >= 0 || username.indexOf('"') >= 0){
					 $('#errorMessage').slideDown().html('<span>Please enter valid username</span>');
					  return false;
			  }else if (password.indexOf('\'') >= 0 || password.indexOf('"') >= 0) {
					$('#errorMessage').slideDown().html('<span>Please enter valid password</span>');
					  return false;
			  }else{
				  e.preventDefault();
				  $.ajax({
				      type: "post",
				      url: "LoginAction.do?method=ajaxCallForlogin",
				      data: {userName:username,password:password},
				      success:function(data) {
					      if(data=='true'){
					    	  countdown();
					    	  $(".ui-dialog-titlebar-close").hide();
						   }else if(data=='notAllowMultiple'){
							   AlreadyLoggedIn();
							 }else if(data=='please Enter Valid'){
								 $('#errorMessage').slideDown().html("<span>Invalid username or password</span>");
								 }
						   else{
								document.location.href = "LoginAction.do?method=loginAction&homePage=yes";
							   }
				      }
				  });
				  }
			});
			 $(function(){
			        $("#webticker").webTicker();
			        
			});

				
			 
			});
		var mins = 15;
		//calculate the seconds (don't change this! unless time progresses at a different speed for you...)
		var secs = mins * 60;
		function AlreadyLoggedIn() {
				$("#timer1").dialog({
			        resizable: false,
			        modal: true,
			        height:"auto",
			        title: "Login Error",
			        width: "auto",
			        position: ['center',28],
			        close: function() {
			    	$("#timer1").dialog("destroy");
			    	$("#timer1").hide();
		      },
		      buttons: {
		            Close : function() {
			                $("#timer1").dialog("close");
			                $("#timer1").hide();
		                  }
		}
			    });
				}
		$(window).resize(function(){
		    $("#timer").dialog("option","position","top");
		});
		$(window).resize(function(){
		    $("#timer1").dialog("option","position","top");
		});
		function countdown() {
				$("#timer").dialog({
			        resizable: false,
			        modal: true,
			        height: "auto",
			        title: "Login Error",
			        width: "auto",
			        position: ['center',28]
			    });
				setTimeout('Decrement()',1000);
				AutoCloseDialogBox(950000);
		     return false;
				}
		function AutoCloseDialogBox(WaitSeconds) {
		  //Auto Close Dialog Box after few seconds
		  setTimeout(
		      function () {
		          $("#timer").dialog("close");
		      }, WaitSeconds);
		}
			
		function Decrement() {
		minutes = document.getElementById("minutes");
		seconds = document.getElementById("seconds");
		//if less than a minute remaining
		if (seconds < 59) {
		seconds.value = secs;
		} else {
		minutes.value = getminutes();
		seconds.value = getseconds();
		}
		if(minutes.value==0 && seconds.value<=1){
			$("#timer").dialog("close");
		}else{
			secs--;
			setTimeout('Decrement()',1000);
			}
		}
		function getminutes() {
		//minutes is seconds divided by 60, rounded down
		mins = Math.floor(secs / 60);
		return mins;
		}
		
		function getseconds() {
		//take mins remaining (as seconds) away from total seconds remaining
		return secs-Math.round(mins *60);
		}
		
		function resetFieldAndErrMsgs()
		{
			document.getElementById("username").value="";
			document.getElementById("password").value="";
			document.getElementById("msg").value="";
		}
		
		var browserName = navigator.appName;
		if (browserName == "Microsoft Internet Explorer") {
			alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
		}
		var serverDownMessage=$('#serverDownMessage').val();
		if(serverDownMessage!=null && serverDownMessage!=""){
			 $(".ZebraDialog_Title a.ZebraDialog_Close").hide();
			$.Zebra_Dialog('<strong>'+serverDownMessage+'</strong>',{
				'title':    'Alert',
				'buttons':  ['Hide'],
				'keyboard':false,
				'overlay_close':false,
				'show_close_button':false
			});
		}