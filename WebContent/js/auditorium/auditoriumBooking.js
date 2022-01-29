$(document).ready(function() {
	   var $calendar = $('#calendar');
	   var approvedOrPendingEvents="";
	   var resetCheckBoxForNewEvents="";
	   $calendar.weekCalendar({
	      timeslotsPerHour : 4,
	      allowCalEventOverlap : true,
	      overlapEventsSeparate: true,
	      firstDayOfWeek : 1,
	      businessHours :{start: 0, end: 24, limitDisplay: true },
	      daysToShow : 7,
	      height : function($calendar) {
	         return $(window).height() - $("h1").outerHeight() - 1;
	      },
	      eventRender : function(calEvent, $event) {
	         if (calEvent.end.getTime() < new Date().getTime()) {
	            $event.css("backgroundColor", "#787878");
	            $event.find(".wc-time").css({
	               "backgroundColor" : "#474747",
	               "border" : "1px solid #474747"
	            });
	         }
	         else if(calEvent.approved==true)
	         {
	        	 $event.css("backgroundColor", "#009933");
	        	 $event.find(".wc-time").css({
		               "backgroundColor" : "#006633",
		               "border" : "1px solid #006633"
		            });
		            if(calEvent.adminRemarks!=null && calEvent.adminRemarks!="")
		            {
		            calEvent.title="<b>Modified And Approved</b> <br/> <b>Reason:</b><br/>"+calEvent.adminRemarks;
		            }
		            else{
		            	calEvent.title="<b>Approved</b>";
		            }
	         }
	         else if(calEvent.rejected==true)
	         {
	        	 $event.css("backgroundColor", "#A52A2A");
	        	 $event.find(".wc-time").css({
		               "backgroundColor" : "#800000",
		               "border" : "1px solid #800000"
		            });
	        	 if(calEvent.adminRemarks!=null && calEvent.adminRemarks!="")
		            {
		            calEvent.title="<b> Reason:</b><br/>"+calEvent.adminRemarks;
		            }
	         }
	      },
	      draggable : function(calEvent, $event) {
	         return calEvent.readOnly != true;
	      },
	      resizable : function(calEvent, $event) {
	         return calEvent.readOnly != true;
	      },
	      eventNew : function(calEvent, $event) {
	         var $dialogContent = $("#event_edit_container");
	         resetForm($dialogContent);
	         $dialogContent.find("select").val("");
	      
	         if(resetCheckBoxForNewEvents!=null && resetCheckBoxForNewEvents!=""){
	        	 for (var count = 0; count < resetCheckBoxForNewEvents.length; count++) {
		        	 $dialogContent.find("input[type='checkbox'][id='" + resetCheckBoxForNewEvents[count] + "']").attr("checked", false);
	         }}
	         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
	         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
	         var blockId = $dialogContent.find("select[name='blockId']");
	         var venueId = $dialogContent.find("select[name='venueId']");
	         var remarks = $dialogContent.find("textarea[name='remarks']");
	        /* if(calEvent.start.getTime()<= (new Date().getTime()+30 * 24 * 60 * 60 * 1000)){*/
	         if (calEvent.start.getTime() >= new Date().getTime()) {
	         $dialogContent.dialog({
	            modal: true,
	            title: "New Calendar Event",
	            close: function() {
	               $dialogContent.dialog("destroy");
	               $dialogContent.hide();
	               $('#calendar').weekCalendar("removeUnsavedEvents");
	            },
	            
	            buttons: {
	            	Cancel : function() {
	                  $dialogContent.dialog("close");
	               },
	               Save : function() {
                  var multipleCheckValues= $('.requirementsCheckBox:checked').map(function() {return this.id;}).get().join(',');
	                  calEvent.start = new Date(startField.val());
	                  calEvent.end = new Date(endField.val());
	                 // var userName = document.getElementById("userName").value;
	                  calEvent.remarks = remarks.val();
	                  var StartEndEventDate=$calendar.weekCalendar("formatDate", calEvent.start);
	                  var startTime=$calendar.weekCalendar("formatTime", calEvent.start);
	                  var endTime=$calendar.weekCalendar("formatTime", calEvent.end);
                    if((blockId.val()==null || blockId.val()=="") && (venueId.val()==null || venueId.val()==""))
                    {
                    	$.confirm({
							'message'	: 'Please Select <b>Block</b> And <b>Venue</b>',
							'buttons'	: {
								'Ok'	: {
									'class'	: 'blue',
									'action': function(){
										$.confirm.hide();
									}
								}
							}
						});
                    }
	                 if(blockId.val()!=null && blockId.val()!="")
	                 {
	                  if(venueId.val()!=null && venueId.val()!="")
	                  {   
	                	  calEvent.venueId = venueId.val();
	                      calEvent.blockId = blockId.val();
		                  saveCalendarEvents(calEvent.id,calEvent.remarks,blockId.val(),venueId.val(),endTime,startTime,StartEndEventDate,multipleCheckValues);
		                  $calendar.weekCalendar("removeUnsavedEvents");
		                  $calendar.weekCalendar("updateEvent", calEvent);
		                  $dialogContent.dialog("close");
	                  }
	                  else {
	                	  $.confirm({
								'message'	: 'Please Select <b>Venue</b>',
								'buttons'	: {
									'Ok'	: {
										'class'	: 'blue',
										'action': function(){
											$.confirm.hide();
										}
									}
								}
							});
	                  }
	                 }else{
	                	 $.confirm({
								'message'	: 'Please Select <b>Block</b>',
								'buttons'	: {
									'Ok'	: {
										'class'	: 'blue',
										'action': function(){
											$.confirm.hide();
										}
									}
								}
							});
	                 }
	               }
	               
	            }
	            
	         }).show();
	         }
	         else{
	        	 $calendar.weekCalendar("removeUnsavedEvents");
	        	 $.growlUI('Event Notification', 'You cannot book for an event before current time'); 
	         }
	        /* }
	         else{
	        	 $calendar.weekCalendar("removeUnsavedEvents");
	        	 $.growlUI('Event Notification', 'You can only book for an Event within one Month');
	         }*/
	        $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
	         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
	      },
	      eventDrop : function(calEvent, $event) {
	    	var StartEndEventDate=$calendar.weekCalendar("formatDate", calEvent.start);
	                  var startTime=$calendar.weekCalendar("formatTime", calEvent.start);
	                  var endTime=$calendar.weekCalendar("formatTime", calEvent.end);
	                  updateDropedAndResizeCalendarEventsByEventId(calEvent.id,endTime,startTime,StartEndEventDate);
	      },
	      eventResize : function(calEvent, $event) {
	    	  var StartEndEventDate=$calendar.weekCalendar("formatDate", calEvent.start);
              var startTime=$calendar.weekCalendar("formatTime", calEvent.start);
              var endTime=$calendar.weekCalendar("formatTime", calEvent.end);
              updateDropedAndResizeCalendarEventsByEventId(calEvent.id,endTime,startTime,StartEndEventDate);
	      },
	      eventClick : function(calEvent, $event) {
	         if (calEvent.readOnly) {
	            return;
	         }
	         else{
                 if(calEvent.venueMap!=null && calEvent.venueMap!="")
                 {
                	 var destination = document.getElementById("venueId");
		             var count5=0;
	             $.each(calEvent.venueMap, function( key, value ) {
	            	 count5++;
	            	 destination.options[count5] = new Option(value, key);
	            	 });
                 }
	         var $dialogContent = $("#event_edit_container");
	         resetForm($dialogContent);
	         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
	         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
	         var remarksField = $dialogContent.find("textarea[name='remarks']").val(calEvent.remarks);
	         var blockId="";
	         var venueId="";
	         if(calEvent.block!=null)
	         {
	       blockId = $dialogContent.find("select[name='blockId']").val(calEvent.block);
	         }else{
	        	blockId = $dialogContent.find("select[name='blockId']");
	         }
	         if(calEvent.venue!=null)
	         {
	         venueId = $dialogContent.find("select[name='venueId']").val(calEvent.venue);
	         }else{
	        	venueId = $dialogContent.find("select[name='venueId']");
	         }
	         var requirementList=calEvent.requireList;
	         if(calEvent.allRequirIdList!=null && calEvent.allRequirIdList!=""){
	        	 for (var count = 0; count < calEvent.allRequirIdList.length; count++) {
		        	 $dialogContent.find("input[type='checkbox'][id='" + calEvent.allRequirIdList[count] + "']").attr("checked", false);
	       }}
	          if(requirementList!=null && requirementList!="")
	         {
	         for (var count = 0; count < requirementList.length; count++) {
	        			 $dialogContent.find("input[type='checkbox'][id='" + requirementList[count] + "']").attr("checked", true);
	        	       }
	         }
	          if(calEvent.approved==true){
	        	  $('.requirementsCheckBox').attr("disabled", true);
	        	  $('#start').attr("disabled", true);
	        	  $('#end').attr("disabled", true);
	        	  $('#blockIdDisable').attr("disabled", true);
	        	  $('#venueId').attr("disabled", true);
	        	  $('#remarks').attr("disabled", true);
	        	   $dialogContent.dialog({
	   	            modal: true,
	   	            title: "Edit - " + calEvent.venueName,
	   	            close: function() {
	   	               $dialogContent.dialog("destroy");
	   	               $dialogContent.hide();
	   	               $('#calendar').weekCalendar("removeUnsavedEvents");
	   	            },
	   	            buttons: {
	   	             Close  : function() {
	                       $dialogContent.dialog("close");
	                     },
	   	            	"Cancel Event"  : function() {
	                    	 calEvent.id = calEvent.id;
	                    	 $.confirm({
									'message'	: 'Are you sure you want to cancel the booked <b>Venue</b>',
									'buttons'	: {
										'Ok'	: {
											'class'	: 'blue',
											'action': function(){
	                    		                   cancelEventByUserAndEventId(calEvent.id);
												$.confirm.hide();
											}
										},
	                    	        'Cancel'	:  {
											'class'	: 'gray',
											'action': function(){
												$.confirm.hide();
											}
										}
									}
								});
	   	                       $dialogContent.dialog("close");
	   	                     }
	   	            }
	   	         }).show();
	          }else{
	        	  $('.requirementsCheckBox').attr("disabled", false);
	        	  $('#start').attr("disabled", false);
	        	  $('#end').attr("disabled", false);
	        	  $('#blockIdDisable').attr("disabled", false);
	        	  $('#venueId').attr("disabled", false);
	        	  $('#remarks').attr("disabled", false);
	        	   $dialogContent.dialog({
	   	            modal: true,
	   	            title: "Edit - " + calEvent.venueName,
	   	            close: function() {
	   	               $dialogContent.dialog("destroy");
	   	               $dialogContent.hide();
	   	               $('#calendar').weekCalendar("removeUnsavedEvents");
	   	            },
	   	            buttons: {
	   	               Close : function() {
	   	                       $dialogContent.dialog("close");
	   	                     },
	   	              "Delete" : function() {
	   		                   deleteEventByEventId(calEvent.id);
	   	                       $calendar.weekCalendar("removeEvent", calEvent.id);
	   	                       $dialogContent.dialog("close");
	   	               },
	   	               Update : function() {
	   		            	  calEvent.id = calEvent.id;
	   		                  calEvent.start = new Date(startField.val());
	   		                  calEvent.end = new Date(endField.val());
	   		                  //calEvent.title = "booked by "+titleField.val();
	   		                  calEvent.remarks = remarksField.val();
	   		                  calEvent.blockId=blockId.val();
	   		                  calEvent.venueId= venueId.val();
	   		                  var multipleCheckValues= $('.requirementsCheckBox:checked').map(function() {return this.id;}).get().join(',');
	   		                  var StartEndEventDate=$calendar.weekCalendar("formatDate", calEvent.start);
	   		                  var startTime=$calendar.weekCalendar("formatTime", calEvent.start);
	   		                  var endTime=$calendar.weekCalendar("formatTime", calEvent.end);
	   		                  updateCalendarEventsByEventId(calEvent.id,calEvent.remarks,blockId.val(),venueId.val(),endTime,startTime,StartEndEventDate,multipleCheckValues);
	   			              $calendar.weekCalendar("updateEvent", calEvent);
	   		                  $dialogContent.dialog("close");
	   		               }
	   	            }
	   	         }).show();
	          }
	      
	         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
	         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
	         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
	         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
	         $(window).resize().resize(); //fixes a bug in modal overlay size ??
	      }
	      },
	      eventMouseover : function(calEvent, $event) {
	    	 /* if(calEvent.bookedUser==calEvent.currentUser)
	    	  {*/
	    	  var requirName=calEvent.requirementName;
	    	  var requir1=requirName.split(",");
	    	  var requir3="";
	    	  for(var i=0;i<requir1.length;i++)
	    	  {
	    		  requir3=requir3+requir1[i]+"<br/>";
	    	  }
	    	    Tipped.create($event, "<strong>"+calEvent.bookedBy+"<br/>Department :"+calEvent.department+"<br/>Block : "+calEvent.blockName+"<br/>Venue : "+calEvent.venueName+"</strong><br/><strong>Event Requirements</strong>"+requir3+"",  { skin: 'tiny' ,position:"bottom right",tipClass:"welcome"});
	    	 /* }*/
	      },
	      eventMouseout : function(calEvent, $event) {
	    	 /* if(calEvent.bookedUser==calEvent.currentUser)
	    	  {*/
	    	  var requirName=calEvent.requirementName;
	    	  var requir1=requirName.split(",");
	    	  var requir3="";
	    	  for(var i=0;i<requir1.length;i++)
	    	  {
	    		  requir3=requir3+requir1[i]+"<br/>";
	    	  }
	    	    Tipped.create($event, "<strong>"+calEvent.bookedBy+"<br/>Department :"+calEvent.department+"<br/>Block : "+calEvent.blockName+"<br/>Venue : "+calEvent.venueName+"</strong><br/><strong>Event Requirements</strong>"+requir3+"",  { skin: 'tiny' });
	    	 /* }*/
	      },
	      noEvents : function() {

	      },
	      data : function(start, end, callback) {
	         callback(getEventData());
	      }
	   });

	   function resetForm($dialogContent) {
	      $dialogContent.find("input").val("");
	      $dialogContent.find("textarea").val("");
	   }
    
      function makeJSonCall(handleData)
      {
	        $.ajax( {
		   	    type : "POST",
		   	    url : "auditoriumBooking.do?method=getCalendarEventsData",
		   	    data: "{}",
		   	    contentType: "application/json; charset=utf-8",
                dataType: "json", 
		   	    success : function(result) {
			   	handleData(result);
      },
      async:   false    	 
		   	     });
	       
      }
      
	   function getEventData() {
		  var arr2=new Array();
	        var jsonObj = new Array();
	        makeJSonCall(function(output){
	        	arr2.pop();
	        	jsonObj.pop();
	                    $.each(output, function(index, value) {
		                    var read=false;
		                    if(value.approved==true || value.rejected==true)
		                    {
			                    read=false;
		                    }
		                    if(value.bookedUser!=value.currentUser)
		                    {
			                    read=true;
		                    }
		                    resetCheckBoxForNewEvents=value.allRequirIdList;
	  jsonObj[index] = {
	                "id": value.id,
	                "start": new Date(value.year, value.month, value.day, value.startHours,value.startMinutes),
	                "end": new Date(value.year, value.month, value.day, value.endHours,value.endMinutes),
	                "block":value.blockId,
	                "venue":value.venueId,
	                "requireList":value.requireList,
	                "allRequirIdList":value.allRequirIdList,
	                "venueMap":value.venueMap,
	                "approved":value.approved,
	                "rejected":value.rejected,
	                "remarks":value.remarks,
	                "requirementName":value.requirementName,
	                "adminRemarks":value.adminRemarks,
	                "bookedUser":value.bookedUser,
	                "currentUser":value.currentUser,
	                "bookedBy":value.bookedBy,
	                "blockName":value.blockName,
	                "venueName":value.venueName,
	                "department":value.department,
	                readOnly:read
	                
	            };
	       arr2.push(jsonObj[index]);
	        });
	                });
	        var myObj  =    {
	                        events:arr2 
	            };
	            return myObj;
	        } 


	   /*
	    * Sets up the start and end time fields in the calendar event
	    * form for editing based on the calendar event being edited
	    */
	   function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

	      for (var i = 0; i < timeslotTimes.length; i++) {
	         var startTime = timeslotTimes[i].start;
	         var endTime = timeslotTimes[i].end;
	         var startSelected = "";
	         if (startTime.getTime() === calEvent.start.getTime()) {
	            startSelected = "selected=\"selected\"";
	         }
	         var endSelected = "";
	         if (endTime.getTime() === calEvent.end.getTime()) {
	            endSelected = "selected=\"selected\"";
	         }
	         $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
	         $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

	      }
	      $endTimeOptions = $endTimeField.find("option");
	      $startTimeField.trigger("change");
	   }

	   var $endTimeField = $("select[name='end']");
	   var $endTimeOptions = $endTimeField.find("option");

	   //reduces the end time options to be only after the start time options.
	   $("select[name='start']").change(function() {
	      var startTime = $(this).find(":selected").val();
	      var currentEndTime = $endTimeField.find("option:selected").val();
	      $endTimeField.html(
	            $endTimeOptions.filter(function() {
	               return startTime < $(this).val();
	            })
	            );

	      var endTimeSelected = false;
	      $endTimeField.find("option").each(function() {
	         if ($(this).val() === currentEndTime) {
	            $(this).attr("selected", "selected");
	            endTimeSelected = true;
	            return false;
	         }
	      });

	      if (!endTimeSelected) {
	         $endTimeField.find("option:eq(1)").attr("selected", "selected");
	      }

	   });
	   var $about = $("#about");

	   $("#about_button").click(function() {
	      $about.dialog({
	         title: "Booking Instructions",
	         width: 600,
	         height:500,
	         close: function() {
	            $about.dialog("destroy");
	            $about.hide();
	         },
	         buttons: {
	            close : function() {
	               $about.dialog("close");
	            }
	         }
	      }).show();
	   });
		$("#searchFrom").click(function() {
			var blockIdForSearch=$("#blockIDs").val();;
			var venueIdForSearch=$("#venueIdForBlock").val();
			if(blockIdForSearch=='' && venueIdForSearch==''){
				 $.confirm({
						'message'	: 'Please Select <b>Block</b> and <b>Venue</b>',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
			}else if(blockIdForSearch==''){
				 $.confirm({
						'message'	: 'Please Select <b>Block</b>',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
				}else if(venueIdForSearch==''){
					$.confirm({
						'message'	: 'Please Select <b>Venue</b>',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
					}else{
							var clickSearch="Search";
							hook=false;
							document.location.href = "auditoriumBooking.do?method=initNewCalendar&blockId="+blockIdForSearch+"&venueId="+venueIdForSearch+"&searchButton="+clickSearch;
						}
		});
		
		$("#pendingEvents").click(function() {
			approvedOrPendingEvents="PendingEvents";
			var url = "auditoriumBooking.do";
			var args = "method=getPendingApprovedEvents";
			requestOperationProgram(url, args, displayApprovedEventsAgenda);
		});
		
		$("#agendaType").click(function() {
			approvedOrPendingEvents="ApprovedEvents";
	    		var url = "auditoriumBooking.do";
	    		var args = "method=getApprovedEvents";
	    		requestOperationProgram(url, args, displayApprovedEventsAgenda);
	});	   
	    function displayApprovedEventsAgenda(req) {
	    	var responseObj = req.responseXML.documentElement;
	    	var fields = responseObj.getElementsByTagName("fields");
	    	var count=0;
	    	var htm="<table width='100%'><tr height='30'></tr><tr><td width='100%'><table width='100%' style='border: 1px solid black;'	rules='all'>";
	    	if(fields!=null){
	    		for ( var i = 0; i < fields.length; i++) {
	    			if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
	    				if(fields[i]!=null){
	    					if(count==0){
		    					 htm=htm+"<tr height='40px'><td width='12%' align='center' class='heading'>Date</td><td class='heading' width='12%' align='center'>Block</td><td class='heading' width='12%' align='center'>Venue</td><td class='heading' width='10%' align='center'>Start Time</td><td class='heading' width='10%' align='center'>End Time</td><td class='heading' width='12%' align='center'>Booked By</td><td class='heading' width='16%' align='center'>Department</td><td class='heading' width='12%' align='center'>Requirements</td></tr>";
		    					 } 
	    					htm=htm+"<tr height='30px'><td width='12%' align='center' class='heading'>";
	    					 var value = fields[i].getElementsByTagName("value")[0].firstChild.nodeValue;
	    					 htm=htm+value+ "</td><td width='88%' align='center' colspan='7'><table width='100%'  rules='all'>";
	    					 var audiBookingToDetails=fields[i].getElementsByTagName("audiBookingToDetails");
	    					 if(audiBookingToDetails!=null){
	    						 htm=htm+"<tr class='row-white' height='25px'> ";
	    						 for ( var j = 0; j < audiBookingToDetails.length; j++) {
	    							 if(audiBookingToDetails[j]!=null){
	    							 var block=audiBookingToDetails[j].getElementsByTagName("block")[0].firstChild.nodeValue;
	    							 var venue=audiBookingToDetails[j].getElementsByTagName("venue")[0].firstChild.nodeValue;
	    							 var startTime=audiBookingToDetails[j].getElementsByTagName("startTime")[0].firstChild.nodeValue;
	    							 var endTime=audiBookingToDetails[j].getElementsByTagName("endTime")[0].firstChild.nodeValue;
	    							 var bookedBy=audiBookingToDetails[j].getElementsByTagName("bookedBy")[0].firstChild.nodeValue;
	    							 var department=audiBookingToDetails[j].getElementsByTagName("department")[0].firstChild.nodeValue;
	    							 var requirements=audiBookingToDetails[j].getElementsByTagName("requirements")[0].firstChild.nodeValue;
	    							 var requir1=requirements.split(",");
	    					    	  var requir3="";
	    					    	  for(var m=0;m<requir1.length;m++)
	    					    	  {
	    					    		  requir3=requir3+requir1[m]+"<br/>";
	    					    	  }
	    							 htm=htm+"<td align='center' class='ApprovedAudiEvents' width='12%'>"+block+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='12%'>"+venue+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='10%'>"+startTime+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='10%'>"+endTime+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='12%'>"+bookedBy+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='16%'>"+department+ "</td>"+"<td align='center' class='ApprovedAudiEvents' width='12%'>"+requir3+ "</td>";
	    							 }
	    							 htm=htm+"</tr>";
	    						 } 
	    					 }
	    					 htm=htm+"</table></td></tr>";
	    				}
	    			}
	    			count++;
	    		}
	    	}
	    	htm=htm+"</table></td></tr><tr height='30'></tr></table>";
	    	if(approvedOrPendingEvents=='ApprovedEvents'){
	    		approvedEventsDialog(htm);	
	    	}else{
	    		pendingEventsDialog(htm);
	    	}
	    	
	    }
	    function approvedEventsDialog(htm) {
	    	 document.getElementById('window').innerHTML = htm;
	    	    $("#window").dialog({
	    	        resizable: true,
	    	        modal: true,
	    	        height: 600,
	    	        title: "Approved Events",
	    	        width: 1200,
	    	        close: function() {
	    	    	$("#window").dialog("destroy");
	    	    	$("#window").hide();
	             },
	             buttons: {
	    	               Close : function() {
	            	                $("#window").dialog("close");
	            	                $("#window").hide();
	    	                     }
	             }
	    	    });
	    }
	    function pendingEventsDialog(htm) {
	    	 document.getElementById('window').innerHTML = htm;
	    	    $("#window").dialog({
	    	        resizable: true,
	    	        modal: true,
	    	        height: 600,
	    	        title: "Pending for Approval",
	    	        width: 1200,
	    	        close: function() {
	    	    	$("#window").dialog("destroy");
	    	    	$("#window").hide();
	             },
	             buttons: {
	    	               Close : function() {
	            	                $("#window").dialog("close");
	            	                $("#window").hide();
	    	                     }
	             }
	    	    });
	    }
	    
	    $("#displayCalendarByDate").click(function() {
	    	var date1=$('#gotoDtae').val();
	    	if(date1!=null && date1!=''){
	    		$calendar.weekCalendar("goToDate");		
	    	}else{
	    		$.confirm({
					'message'	: 'Please Select <b>Date</b>',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});
	    	}
	    
	    });

	});

function getFacilityAvailable(){
    var blockId=$('#blockIdDisable').val(); 
    var venueId=$('#venueId').val();
	 $.ajax({
      type: "post",
      url: "auditoriumBooking.do?method=getFacilityAvailableByBlockAndVenue",
      data: {blockId:blockId,venueId:venueId},
      success:function(data) {
     	 //$.growlUI('Event Notification', data); 
     	 document.getElementById('window').innerHTML = data;
	    	    $("#window").dialog({
	    	        resizable: true,
	    	        position: [800,200],
	    	        modal: true,
	    	        height: 200,
	    	        title: "Facility Available",
	    	        width: 300,
	    	        close: function() {
	    	    	$("#window").dialog("destroy");
	    	    	$("#window").hide();
	             },
	             buttons: {
	    	               Close : function() {
	            	                $("#window").dialog("close");
	            	                $("#window").hide();
	    	                     }
	             }
	    	    });
       }
   });   
	
}
function getVenuesByBlock(blockId) {
	 var args = "method=getVenuesByBlock&blockId="+blockId;
		var url = "auditoriumBooking.do";
		requestOperationProgram(url, args, updateVenuesByBlock);
}
function updateVenuesByBlock(req) {
	updateOptionsFromMap(req,"venueIdForBlock","Select venues");
}
function getVenues(blockId) {
	 var args = "method=getVenuesByBlock&blockId="+blockId;
		var url = "auditoriumBooking.do";
		requestOperationProgram(url, args, updateVenues);
}
function updateVenues(req) {
	updateOptionsFromMap(req,"venueId","Select venues");
}
function saveCalendarEvents(id,remarks,blockId,venueId,endTime,startTime,StartEndEventDate,multipleCheckValues)
{
	var saved=0;
	var duplicate=0;
	        $.ajax( {
		   	    type : "POST",
		   	    url : "auditoriumBooking.do?method=checkDuplicateCalendarEvents",
		   	    data: "{}",
		   	    contentType: "application/json; charset=utf-8",
                dataType: "json",
                async:   false, 
		   	    success : function(result) {
		   	     $.each(result, function(index, value) {
                	 if(value.dateForDup != StartEndEventDate)
                	 {
                		 saved=1;
                	 }
                	 else{
                		 var startTimeForDup=value.startHours+"."+value.startMinutes;
                		 var endTimeForDup=value.endHours+"."+value.endMinutes;
                		 var startTimeSaving=settings(startTime);
                		 var endTimeSaving=settings(endTime);
                		 if(endTimeSaving<=startTimeForDup || startTimeSaving>=endTimeForDup && !(startTimeSaving>=startTimeForDup && endTimeSaving<=endTimeForDup))
                         {
                			 saved=1;
                         }else{
                        	 if(value.blockId==blockId && value.venueId==venueId){
                        		 duplicate=1;
                            	 saved=0;
                            	 return false; 
                        	 }
                            
                         } 
                	 }
                 });
                 if(saved==1)
                 {
                	 $.ajax({
                         type: "post",
                         url: "auditoriumBooking.do?method=saveCalendarEvents",
                         data: {eventId:id,date:StartEndEventDate, startTime:startTime,endTime:endTime,remarks:remarks,blockId:blockId,venueId:venueId,multipleCheckValues:multipleCheckValues},
                         success:function(data) {
                        	 //$.growlUI('Event Notification', data); 
                        	 $.confirm({
     							'message'	: '<b>'+data+'</b>',
     							'buttons'	: {
     								'Ok'	: {
     									'class'	: 'blue',
     									'action': function(){
     										$.confirm.hide();
     										document.location.href = "auditoriumBooking.do?method=initNewCalendar";
     									}
     								}
     							}
     						});
                          }
                      });   
                 }else if(duplicate==1){
                	 $.confirm({
							'message'	: '<b>Duplicate Event</b>, This Events already exist?',
							'buttons'	: {
								'Ok'	: {
									'class'	: 'blue',
									'action': function(){
										$.confirm.hide();
									}
								}
							}
						});
                         $calendar.weekCalendar("removeUnsavedEvents");
                         $dialogContent.dialog("close");
                 }
		   	    }
		   	     });
}

 function updateCalendarEventsByEventId(eventId,remarks,blockId,venueId,endTime,startTime,StartEndEventDate,multipleCheckValues) {
 $.ajax({
     type: "post",
     url: "auditoriumBooking.do?method=updateCalendarEventsByEventId",
     data: {eventId:eventId,date:StartEndEventDate, startTime:startTime,endTime:endTime,remarks:remarks,blockId:blockId,venueId:venueId,multipleCheckValues:multipleCheckValues,mode:"UserUpdate"},
     success:function(data) {
    	 $.growlUI('Event Notification', data); 
        /* $.confirm({
				'message'	: data,
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});

		 $.confirm.hide = function(){
				$('#confirmOverlay').fadeOut(function(){
					$(this).remove();
				});
			};*/
      }
  });
 }
 function deleteEventByEventId(eventId) {
	 $.ajax({
	     type: "post",
	     url: "auditoriumBooking.do?method=deleteEventByEventId",
	     data: {eventId:eventId,mode:"Deleted"},
	     success:function(data) {
	    	 $.growlUI('Event Notification', data); 
            /* $.confirm({
					'message'	: data,
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});

			 $.confirm.hide = function(){
					$('#confirmOverlay').fadeOut(function(){
						$(this).remove();
					});
				};*/
          }
	  });
}
 function updateDropedAndResizeCalendarEventsByEventId(eventId,endTime,startTime,StartEndEventDate) {
	 $.ajax({
	     type: "post",
	     url: "auditoriumBooking.do?method=updateCalendarEventsByEventId",
	     data: {eventId:eventId,date:StartEndEventDate, startTime:startTime,endTime:endTime,mode:"UserDropAndResize"},
	     success:function(data) {
	    	 $.growlUI('Event Notification', data); 
           /*  $.confirm({
					'message'	: data,
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});

			 $.confirm.hide = function(){
					$('#confirmOverlay').fadeOut(function(){
						$(this).remove();
					});
				};*/
          }
	  });
}
 function settings(time) {
 	var time1= time.split(' ');
 	var time2=time1[0].split(':');
		var hours;
		if( time2[0]>=1 && time2[0]<12 && time1[1]=='pm')
		{
			hours=parseInt(time2[0])+parseInt('12');
		}else{
			if(time2[0]==12 && time1[1]=='am')
			{
			hours=0;
			}else{
				hours=time2[0];
			}
		}
		hours=hours+"."+time2[1];
		return hours;
}
 function cancelEventByUserAndEventId(eventId) {
	 $.ajax({
	     type: "post",
	     url: "auditoriumBooking.do?method=deleteEventByEventId",
	     data: {eventId:eventId,mode:"CancelEventByUser"},
	     
	     success:function(data) {
        	  
	    	 $.growlUI('Event Notification', data);
	    	 //setTimeout(initCalender, 1000);
	    	 setTimeout(function(){initCalender();},500);
        	/*
	    	 $.confirm({
					'message'	: '<b>'+data+'</b>',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
								document.location.href = "auditoriumBooking.do?method=initNewCalendar";
							}
						}
					}
				});
        	 */
          }
	     
	  });
} 
 
 function initCalender(){
	 document.location.href = "auditoriumBooking.do?method=initNewCalendar";
	}
	
 