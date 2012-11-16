<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="us">
<head>
    <meta charset="utf-8">
    <title>Everton Rosario - Tunein Program</title>
    <link href="css/redmond/jquery-ui-1.9.1.custom.css" rel="stylesheet">
    <script src="js/jquery-1.8.2.js"></script>
    <script src="js/jquery-ui-1.9.1.custom.js"></script>
    <script>
    
    function formatItem(item) {
        return item.shortName + ', ' + 
               item.daysOfWeek.join('/') + ', ' +
               item.startTime + ', ' +
               item.durationTime;
               
    }
    
    $(function() {
        
        // Setup for dialog requirements page
        $("#dialogRequirement").dialog({
            autoOpen: false,
            width: 800,
            height: 350,
            buttons: [
                {
                    text: "Ok",
                    click: function() {
                        $(this).dialog("close");
                    }
                }
            ]
        });
        
     // Setup for dialog Empty page
        $("#dialogEmptyField" ).dialog({
            autoOpen: false,
            width: 300,
            buttons: [
                {
                    text: "Ok",
                    click: function() {
                        $(this).dialog("close");
                    }
                }
            ]
        });

        // Makes button setup and binds the click event
        $("#btnRequirement").button({
            icons: {
                primary: "ui-icon-mail-closed"
            }
        });
        $("#btnRequirement").click(function(event) {
            $("#dialogRequirement").dialog("open");
            event.preventDefault();
        });

        
        // Generate tv schedule button
        $("#btnGenerate").button({
            icons: {
                primary: "ui-icon-shuffle"
            }
        });
        $("#btnGenerate").click(function(event) {
            event.preventDefault();
            $.ajax({
                url: 'generate',
                success: function(data) {
                    $("#content").val(data);
                }
            });
        });
        
        // Makes button setup and binds the click event
        $("#btnClear").button({
            icons: {
                primary: "ui-icon-trash"
            }
        });
        $("#btnClear").click(function(event) {
            $("#content").empty();
            event.preventDefault();
        });

        // Process information and load result board
        $("#btnOptmize").button({
            icons: {
                primary: "ui-icon-gear"
            }
        });
        $("#btnOptmize").click(function(event) {
            event.preventDefault();

            var content = $("#content").val();

            if (!content) { 
                $("#dialogEmptyField").dialog("open");
            
            } else {

                $.ajax({
	                
                    type: 'POST',
	                url: 'optmize',
	                data: {
	                    content : content
	                },
	                success: function(data) {
	                    data.forEach(function(item, index) {
	                        var line = formatItem(item);
	                        console.log(item);
	                        console.log(line);
	                    });
	                }
	            });
            }
        });
        
    });
    </script>
    <style>
    body{
        font: 70% "Trebuchet MS", sans-serif;
        margin: 65px;
    }
    </style>
</head>


<body>

	<h1>TV Schedule Optmization</h1>
	
	<div class="ui-widget">
	    <p>Created in November 16th, 2012. by Everton Rosario <a href="mailto:erosario@gmail.com">e-mail</a></p>
	</div>
	
	<!-- Button -->
	<button id="btnRequirement" title="Shows Project description">Description</button>
	
	<!-- Button -->
	<button id="btnGenerate" title="Generates a random fake week schedule">Generate</button>
	
    <!-- Button -->
    <button id="btnClear" title="Clear current input field">Clear</button>

	<br/>
	
	<div>
		<p style="position: absolute;left: 7px; text-align: right;">
			Example:<br/>
			<br/>
			Car Racing<br/>
			Monday<br/>
			6pm<br/>
			1hr<br/>
			<br/>
			Car Racing<br/>
            Tuesday<br/>
            6pm<br/>
            1hr<br/>
		</p>
		
		<textarea id="content" name="content" style="width: 100%; height: 203px; font: 100% 'Trebuchet MS', sans-serif;"></textarea>
	</div>
	
	
	<br/>
	
	<!-- Button -->
    <button id="btnOptmize" title="Process the Schedule entered, grouping the entries">Process!</button>

	<br/>
    <br/>
	
	<div style="width: 100%; height: 203px; border: solid 1px #AAA;">
        <div>Car Racing, S/T, 10pm, 1hr</div>
        <div>Car Racing, S/T, 10pm, 1hr</div>
        <div>Car Racing, S/T, 10pm, 1hr</div>
        <div>Car Racing, S/T, 10pm, 1hr</div>
        <div>Car Racing, S/T, 10pm, 1hr</div>
    </div>
	
	
    <!-- 
          Dialog for Project Description 
    -->
    <div id="dialogEmptyField" title="Empty TV Schedule">
        <p>
            Please enter TV Schedule in the presenting input, for further processing.
        </p> 
    </div>

	<!-- 
	      Dialog for Project Description 
	-->
	<div id="dialogRequirement" title="Project description">
	    <p>
			You are to implement a very simple web application that captures and consolidates weekly syndicated 
			TV schedules from structured user input. Your web form will accept an arbitrary number of rows with 
			the following fields:
			<ul>
				<li>Show Name</li>
				<li>Days of Week</li>
				<li>Time of Day</li>
				<li>Duration</li>
			</ul>
			The system should attempt to create the most efficient representation of the schedule possible. 
			That means if you see two entries like this:<br/>
			<br/>
			Car Racing<br/>
			Monday<br/>
			6pm<br/>
			1hr<br/>
			<br/>
			Car Racing<br/>
			Tuesday<br/>
			6pm<br/>
			1hr<br/>
			<br/>
			You would store only one representation - Car Racing, M/T, 6pm, 1hr (consolidating the days).  Similarly:<br/>
			<br/>
			Car Racing<br/>
			Monday<br/>
			6pm<br/>
			1hr<br/>
			<br/>
			Car Racing<br/>
			Monday<br/>
			7pm<br/>
			2hr<br/>
			<br/>
			Would be compacted to Car Racing, M, 6pm, 3hr (consolidating the contiguous times). Always prefer the 
			greatest consolidation option available. Bonus if your application detects collisions - when different 
			shows are defined over the same times and days.
		</p>
	</div>

</body>
</html>
