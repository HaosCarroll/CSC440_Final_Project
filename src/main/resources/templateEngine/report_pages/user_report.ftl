<style>
    table th a {
        text-transform: capitalize;
    }
</style>


<div class="user_report-template">

    <h2>${title}</h2>
    <p id="status"></p>
    <div class="form-group">
        <label for="id">Select User for Report.</label>
        <select id="users" name="userEntityUserIdNumber"></select>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
    <p id="status"></p>

    <h2 id="users_billables_heading"></h2>

    <div class="usersBillableTable"></div>
    <div class="paginationContainer"></div>

</div>

<script src="js/awesomeTable.js" type="text/javascript"></script>


<script>
$(document).ready(function() {
    var user = ${users};
    var sel = $('#users');
    $.each(user, function(key, val) {
        sel.append('<option value="' + val + '">' + val + '</option>');
    });
    $("button").on("click", function(e) {
        e.preventDefault();
        var this_ = $(this);
        var arr = $("#users").val();

        var url_string = '/userReport/' + arr
        $(document).ready(function() {
            $.getJSON(url_string, function(json) {
                if (json.length == 0) {
                    console.log("NO DATA!");
                    $(".usersBillableTable").text("No Billables Found");
                }
                else {

                    $("#users_billables_heading").text("USER : " + arr);
                    $(".usersBillableTable").text("");

                    var tbl = new awesomeTableJs({
                        data: json,
                        tableWrapper: ".usersBillableTable",
                        paginationWrapper: ".paginationContainer",
                        buildPageSize: false,
                        buildSearch: false,
                    });
                    tbl.createTable();
                }
            });
        });
        return false;
    });
});
</script>
 