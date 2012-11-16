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
    $(function() {
        
        // Setup for dialog page
        $( "#dialogRequirement" ).dialog({
            autoOpen: false,
            width: 800,
            height: 350,
            buttons: [
                {
                    text: "Ok",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                }
            ]
        });

        // Makes button setup and binds the click event
        $("#btnRequirement").button();
        $("#btnRequirement").click(function(event) {
            $("#dialogRequirement").dialog("open");
            event.preventDefault();
        });

        
        // Generate tv schedule button
        $("#btnGenerate").button();
        $("#btnGenerate").click(function(event) {
            event.preventDefault();
            $.ajax({
                url: 'generate',
                success: function(data) {
					$('#content').empty().html(data);
                }
            });
        });
        
        
        // Process information and load result board
        $("#btnOptmize").button();
        $("#btnOptmize").click(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: 'optmize',
                data: { 
                    content : $('#content').html()
                },
                success: function(data) {
                    $('#content').empty().html(data);
                }
            });
        });
        
    });
    </script>
    <style>
    body{
        font: 62.5% "Trebuchet MS", sans-serif;
        margin: 50px;
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
	
	<br/>
	
	<div>
		<textarea id="content" name="content" style="width: 686px; height: 203px;">
		</textarea>
		
		<p style="display:inline; position:absolute;">
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
	</div>
	
	<br/>
	
	<!-- Button -->
    <button id="btnOptmize" title="Process the Schedule for grouping the entries">Process!</button>
	
	
	
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
