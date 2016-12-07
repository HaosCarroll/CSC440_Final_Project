<style>
    table th a {
        text-transform: capitalize;
    }
</style>

<div class="provider_report-template">

    <h2>${title}</h2>
    <div class="form-group">
        <label id="selector">Select Provider for Report.</label>
        <select id="choices" name="providerEntityProviderIdNumber"></select>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
    <p id="status"></p>

    <div id="provider_billables_heading"></div>

    <div id="provider_billables_report_heading"></div>

    <div class="providerBillableTable"></div>
    <div class="paginationContainer"></div>

    <div id="provider_billables_footer"></div>
</div>

<script src="js/awesomeTable.js" type="text/javascript"></script>

<script>
    $(document).ready(function() {
        var provider = ${providers};
        var sel = $('#choices');
        $.each(provider, function(key, val) {
            sel.append('<option value="' + val + '">' + val + '</option>');
        });

        $("button").on("click", function(e) {
            e.preventDefault();
            var prov_arr = $("#choices").val();

            var isnum = /^\d+$/.test(prov_arr);

            if (isnum) {
                var url_string_for_address = '/providerAddress/' + prov_arr;
                $(document).ready(function() {
                    $.get(url_string_for_address, function(element) {
                        if (element.length == 0) {
                            console.log("PROVIDER NOT FOUND (provider_report.ftl!)");
                            $('#provider_billables_heading').text("No Billables Found");
                        }
                        else {
                            // Clear Heading.
                            $('#provider_billables_heading').text("");
                            // Put the provider aadress on the heading.
                            $('#provider_billables_heading').append(element);
                        }
                    });
                });

                $('#selector').text("");
                $('#selector').append("Choose week (starts 9pm date of : ");
                $('#choices').text("");

                var url_string_for_date_choices = '/providerReport/' + prov_arr;
                $.getJSON(url_string_for_date_choices, function(date) {
                    var sel = $('#choices');
                    $.each(date, function(key, val) {
                        sel.append('<option value="' + val + '">' + val + '</option>');
                    });
                });
                $("button").on("click", function(e) {
                    e.preventDefault();
                    var date_arr = $("#choices").val();
                    var url_string_for_report = '/providerReport/' + prov_arr + '/' + date_arr

                    var stringDateToParse = date_arr.replace(/-/g, '\/');
                    var dateStart = new Date(stringDateToParse);
                    var dateEnd = new Date(stringDateToParse);
                    dateEnd.setDate(dateEnd.getDate()+7);
                    
                    $('#provider_billables_report_heading').text("");
                    $('#provider_billables_report_heading').append('<h4>' + dateStart.toDateString() + ' (9pm) - ' + dateEnd.toDateString() + ' (9pm)</h4>');

                    $.getJSON(url_string_for_report, function(json) {
                        if (json.length == 0) {
                            console.log("NO DATA for REPORT (provider_report.ftl!)");
                            $(".providerBillableTable").text("No Billables Found for provider : " + prov_arr);
                        }
                        else {

                            // Clear table.
                            $(".providerBillableTable").text("");
                            // Make a new table.
                            var tbl = new awesomeTableJs({
                                data: json,
                                tableWrapper: ".providerBillableTable",
                                paginationWrapper: ".paginationContainer",
                                buildPageSize: false,
                                buildSearch: false,
                            });
                            // Put the table on the page.
                            tbl.createTable();
                        }

                        // Put the tabulations on the page.
                        var url_string_for_tabulations = '/providerBillablesTabulations/' + prov_arr

                        $(document).ready(function() {
                            $.get(url_string_for_tabulations, function(element) {
                                if (element.length == 0) {
                                    console.log("PROVIDER NOT FOUND (provider_report.ftl!)");
                                    $('#provider_billables_footer').text("No Billables Found");
                                }
                                else {
                                    // Clear Footer.
                                    $('#provider_billables_footer').text("");
                                    $('#provider_billables_footer').append(element);
                                }
                            });
                        });
                    });
                });
            }
        });
    });
</script>