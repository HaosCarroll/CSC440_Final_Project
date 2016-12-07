<div class="test_data-template">
    <h1><strong>!&iexcl;OMG&iexcl;!</strong></h1><br>
    <p id="data_status"></p><br>
</div>

<script>
    $(document).ready(function() {
        var url_string_to_clear_n_add_test_data = '/clearMongoDBAndAddTestData'
        $.ajax({
            type: "PUT",
            url: url_string_to_clear_n_add_test_data,
            success : function(e) {
                $("#data_status").text("");
                $("#data_status").append(e);
            },
            error : function(e) {
                console.log("Error! (addTestData.ftl)");
                $("#data_status").text(e);
            }
        });
    });
</script>