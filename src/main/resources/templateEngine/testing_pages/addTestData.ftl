<div class="test_data-template">
    <h2>Add Test Data.</h2>
    <button type="submit" autofocus class="btn btn-default">ADD TEST DATA!</button>
    <p id="status"></p>
</div>

<script>
    $(document).ready(function() {
        $("button").on("click", function(e) {
            e.preventDefault();

            var url_string_for_adding_test_data = '/autoAddDataToMongoDB'
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
