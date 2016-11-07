<div class="starter-template">
	<h2> Remove ChocoServiceProvided </h2>
    	<p id="status"></p>
  	<div class="form-group">
      		<label for="id">Select ChocoServiceProvided.chocoServiceProvidedIdNumber to Remove</label>
      		<select id="chocoServiceProvideds" name="chocoServiceProvidedChocoServiceProvidedIdNumber"></select>
    	</div>
      <button type="submit" class="btn btn-default">Submit</button>
      <p id="status"></p>
</div>	

<script>
$( document ).ready(function() {
      var chocoServiceProvided = ${chocoServiceProvideds};
      var sel = $('#chocoServiceProvideds');
      $.each(chocoServiceProvided, function(key,val){
        sel.append('<option value="' + val + '">' + val + '</option>');   
      });
      $("button").on("click", function(e) {
      	e.preventDefault();
        var this_ = $(this);
        var arr = $("#chocoServiceProvideds").val();
        // Ajax Call
        $.ajax({
            type: "PUT",
            url: 'removeChocoServiceProvided/' + arr,
            success : function(e) {
                $("#chocoServiceProvideds option:selected").remove();
                $("#status").text(e);
            },
            error : function(e) {
                $("#status").text(e);
            }
        });
        return false;
    });
});
	
</script>
