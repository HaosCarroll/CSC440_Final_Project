 <style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> All ChocoServiceProvideds </h2>
    	<div class="chocoServiceProvidedTable"> </div>
		<div class="paginationContainer "></div>
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getJsonChocoServiceProvidedList',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".chocoServiceProvidedTable").text("No Users Found");
    			}
    			else {
    				var tbl = new awesomeTableJs({
						data:json,
						tableWrapper:".chocoServiceProvidedTable",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>
