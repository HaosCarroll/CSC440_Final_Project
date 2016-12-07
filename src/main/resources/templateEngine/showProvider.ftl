<style>
table th a {
 text-transform: capitalize;
}
</style>

<div class="starter-template">
	<h2> All Providers </h2>
	<div class="providerTable"> </div>
	<div class="paginationContainer "></div>
</div>	
 <script src="js/awesomeTable.js" type="text/javascript"></script>
 <script>
 	$( document ).ready(function() {
 		$.getJSON('/getJsonProviderList',function(json){
			if ( json.length == 0 ) {
    			$(".providerTable").text("No Providers Found");
			}
			else {
				var tbl = new awesomeTableJs({
					data:json,
					tableWrapper:".providerTable",
					paginationWrapper:".paginationContainer",
					buildPageSize: false,
					buildSearch: false,
				});
				tbl.createTable();	
			}
		});
	});
</script>