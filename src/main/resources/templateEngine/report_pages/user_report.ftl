<style>
    table th a {
        text-transform: capitalize;
    }
</style>

<div class="user_report-template">

    <h2>${title}</h2>
    <div class="form-group">
        <label id="selector">Select User for Report.</label>
        <select id="choices" name="userEntityUserIdNumber"></select>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
    <p id="status"></p>

    <div id="user_billables_heading"></div>

    <div id="user_billables_report_heading"></div>

    <div class="userBillableTable"></div>
    <div class="paginationContainer"></div>

</div>

<script src="js/awesomeTable.js" type="text/javascript"></script>

<script>
$(document).ready(function() {
        
    var provider = ${users};
    var sel = $('#choices');
        
    $.each(provider, function(key, val) {
        sel.append('<option value="' + val + '">' + val + '</option>');
    });

    $("button").on("click", function(e) {
        e.preventDefault();

        var user_arr = $("#choices").val();
        var isnum = /^\d+$/.test(user_arr);

        if (isnum) {
            var url_string_for_address = '/userAddress/' + user_arr;
            $(document).ready(function() {
                $.get(url_string_for_address, function(element) {
                    if (element.length == 0) {
                        console.log("USER NOT FOUND (user_report.ftl!)");
                        $('#user_billables_heading').text("No Billables Found");
                    }
                    else {
                        // Clear Heading.
                        $('#user_billables_heading').text("");
                        // Put the provider aadress on the heading.
                        $('#user_billables_heading').append(element);
                    }
                });
            });


            $('#selector').text("");
            $('#selector').append("Choose week (starts 9pm date of : ");
            $('#choices').text("");

            var url_string_for_date_choices = '/userReport/' + user_arr;
            $.getJSON(url_string_for_date_choices, function(date) {
                //console.log("date:");
                //console.log(date);
                var sel = $('#choices');
                $.each(date, function(key, val) {
                    sel.append('<option value="' + val + '">' + val + '</option>');
                });
            });

            $("button").on("click", function(e) {
                e.preventDefault();
                var date_arr = $("#choices").val();
                //console.log("user_arr = " + user_arr);
                //console.log("date_arr = " + date_arr);
                var url_string_for_report = '/userReport/' + user_arr + '/' + date_arr;

                var stringDateToParse = date_arr.replace(/-/g, '\/');
                var dateStart = new Date(stringDateToParse);
                var dateEnd = new Date(stringDateToParse);
                dateEnd.setDate(dateEnd.getDate() + 7);

                $('#user_billables_report_heading').text("");
                $('#user_billables_report_heading').append('<h4>' + dateStart.toDateString() + ' (9pm) - ' + dateEnd.toDateString() + ' (9pm)</h4>');

                $.getJSON(url_string_for_report, function(json) {
                    
                    if (json.length == 0) {
                        //console.log("NO DATA for REPORT (user_report.ftl!)");
                        $(".userBillableTable").text("No Billables Found for provider : " + user_arr);
                    }
                    else {
                        // Clear table.
                        $(".userBillableTable").text("");
                        // Make a new table.
                        var tbl = new awesomeTableJs({
                            data: json,
                            tableWrapper: ".userBillableTable",
                            paginationWrapper: ".paginationContainer",
                            buildPageSize: false,
                            buildSearch: false,
                        });
                        // Put the table on the page.
                        tbl.createTable();
                    }
                });
            });
        }
    });
});
</script>
