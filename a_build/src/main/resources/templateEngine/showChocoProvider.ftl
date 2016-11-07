 <style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> All ChocoProviders </h2>
    	<div class="chocoProviderTable"> </div>
		<div class="paginationContainer "></div>
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getJsonChocoProviderList',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".chocoProviderTable").text("No Users Found");
    			}
    			else {
    				var tbl = new awesomeTableJs({
						data:json,
						tableWrapper:".chocoProviderTable",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>
