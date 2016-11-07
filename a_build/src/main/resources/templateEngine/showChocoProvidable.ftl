 <style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> All ChocoProvidables </h2>
    	<div class="chocoProvidableTable"> </div>
		<div class="paginationContainer "></div>
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getJsonChocoProvidableList',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".chocoProvidableTable").text("No Users Found");
    			}
    			else {
    				var tbl = new awesomeTableJs({
						data:json,
						tableWrapper:".chocoProvidableTable",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>
