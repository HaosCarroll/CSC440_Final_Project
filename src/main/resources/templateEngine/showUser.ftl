<style>
	table th a {
		text-transform: capitalize;
	}
</style>

<div class="starter-template">
	<h2> All Users </h2>
	<div class="userTable"> </div>
	<div class="paginationContainer "></div>
</div>
<script src="js/awesomeTable.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$.getJSON('/getJsonUserList', function(json) {
			if (json.length == 0) {
				$(".userTable").text("No Users Found");
			}
			else {
				var tbl = new awesomeTableJs({
					data: json,
					tableWrapper: ".userTable",
					paginationWrapper: ".paginationContainer",
					buildPageSize: false,
					buildSearch: false,
				});
				tbl.createTable();
			}
		});

	});
</script>