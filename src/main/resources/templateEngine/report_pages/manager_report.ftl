<style>
    table th a {
        text-transform: capitalize;
    }
</style>

<div class="report-template">

    <h2>${title}</h2>
    <div class="form-group">
        <label id="selector">Select Start Date for Report.</label>
        <select id="choices" name="managerReportStartDate"></select>
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
    <p id="status"></p>

    <div id="manager_report_heading"></div>

    <div class="managerWeeklyReportTable"></div>
    <div class="paginationContainer"></div>

</div>

<script src="js/awesomeTable.js" type="text/javascript"></script>

<script>
$(document).ready(function() {
        
    var date = ${date};
    var sel = $('#choices');
        
    $.each(date, function(key, val) {
        sel.append('<option value="' + val + '">' + val + '</option>');
    });
    
    $("button").on("click", function(e) {
        e.preventDefault();
        var date_arr = $("#choices").val();
        console.log("date_arr = " + date_arr);
        var url_string_for_report = '/managerReport/' + date_arr;

        var stringDateToParse = date_arr.replace(/-/g, '\/');
        var dateStart = new Date(stringDateToParse);
        var dateEnd = new Date(stringDateToParse);
        dateEnd.setDate(dateEnd.getDate() + 7);

        $('#manager_report_heading').text("");
        $('#manager_report_heading').append('<h4>' + dateStart.toDateString() + ' (9pm) - ' + dateEnd.toDateString() + ' (9pm)</h4>');

        $.getJSON(url_string_for_report, function(json) {
            
            if (json.length == 0) {
                //console.log("NO DATA for REPORT (user_report.ftl!)");
                $(".managerWeeklyReportTable").text("No Billables Found!");
            }
            else {
                // Clear table.
                $(".managerWeeklyReportTable").text("");
                // Make a new table.
                var tbl = new awesomeTableJs({
                    data: json,
                    tableWrapper: ".managerWeeklyReportTable",
                    paginationWrapper: ".paginationContainer",
                    buildPageSize: false,
                    buildSearch: false,
                });
                // Put the table on the page.
                tbl.createTable();
            }
        });
    });
});
</script>
