 <style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> All ChocoUsers </h2>
    	<div class="chocoUserTable"> </div>
		<div class="paginationContainer "></div>
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getJsonChocoUserList',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".chocoUserTable").text("No Users Found");
    			}
    			else {
    				var tbl = new awesomeTableJs({
						data:json,
						tableWrapper:".chocoUserTable",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>
