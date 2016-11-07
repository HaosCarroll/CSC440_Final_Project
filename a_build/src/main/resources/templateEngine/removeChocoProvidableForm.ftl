<div class="starter-template">
	<h2> Remove ChocoProvidable </h2>
    	<p id="status"></p>
  	<div class="form-group">
      		<label for="id">Select ChocoProvidable.providableServiceIdNum to Remove</label>
      		<select id="chocoProvidables" name="chocoProvidableProvidableServiceIdNum"></select>
    	</div>
      <button type="submit" class="btn btn-default">Submit</button>
      <p id="status"></p>
</div>	

<script>
$( document ).ready(function() {
      var chocoProvidable = ${chocoProvidables};
      var sel = $('#chocoProvidables');
      $.each(chocoProvidable, function(key,val){
        sel.append('<option value="' + val + '">' + val + '</option>');   
      });
      $("button").on("click", function(e) {
      	e.preventDefault();
        var this_ = $(this);
        var arr = $("#chocoProvidables").val();
        // Ajax Call
        $.ajax({
            type: "PUT",
            url: 'removeChocoProvidable/' + arr,
            success : function(e) {
                $("#chocoProvidables option:selected").remove();
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
