<div class="test_data-template">
    <h2>Remove All Data!</h2>
    <button type="submit" autofocus class="btn btn-default">REMOVE ALL DATA!</button>
    <p id="status"></p>
</div>

<script>
    $(document).ready(function() {
        $("button").on("click", function(e) {
            e.preventDefault();

            var url_string_for_adding_test_data = '/removeAllDataFromMongoDB'
            $.ajax({
                type: "PUT",
                url: url_string_for_adding_test_data,
                success : function(e) {
                    $("#status").text("");
                    $("#status").append(e);
                },
                error : function(e) {
                    console.log("Error! (addTestData.ftl)");
                    $("#status").text(e);
                }
            });
        });
    });
</script>
