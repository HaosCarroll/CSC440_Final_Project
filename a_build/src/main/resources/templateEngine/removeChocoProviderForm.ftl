<div class="starter-template">
	<h2> Remove ChocoProvider </h2>
    	<p id="status"></p>
  	<div class="form-group">
      		<label for="id">Select ChocoProvider.providerNumber to Remove</label>
      		<select id="chocoProviders" name="chocoProviderProviderNumber"></select>
    	</div>
      <button type="submit" class="btn btn-default">Submit</button>
      <p id="status"></p>
</div>	

<script>
$( document ).ready(function() {
      var chocoProvider = ${chocoProviders};
      var sel = $('#chocoProviders');
      $.each(chocoProvider, function(key,val){
        sel.append('<option value="' + val + '">' + val + '</option>');   
      });
      $("button").on("click", function(e) {
      	e.preventDefault();
        var this_ = $(this);
        var arr = $("#chocoProviders").val();
        // Ajax Call
        $.ajax({
            type: "PUT",
            url: 'removeChocoProvider/' + arr,
            success : function(e) {
                $("#chocoProviders option:selected").remove();
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
