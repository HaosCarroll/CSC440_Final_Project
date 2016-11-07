<div class="starter-template">
	<h2> Remove ChocoUser </h2>
    	<p id="status"></p>
  	<div class="form-group">
      		<label for="id">Select ChocoUser.memberNumber to Remove</label>
      		<select id="chocoUsers" name="chocoUserMemberNumber"></select>
    	</div>
      <button type="submit" class="btn btn-default">Submit</button>
      <p id="status"></p>
</div>	

<script>
$( document ).ready(function() {
      var chocoUser = ${chocoUsers};
      var sel = $('#chocoUsers');
      $.each(chocoUser, function(key,val){
        sel.append('<option value="' + val + '">' + val + '</option>');   
      });
      $("button").on("click", function(e) {
      	e.preventDefault();
        var this_ = $(this);
        var arr = $("#chocoUsers").val();
        // Ajax Call
        $.ajax({
            type: "PUT",
            url: 'removeChocoUser/' + arr,
            success : function(e) {
                $("#chocoUsers option:selected").remove();
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
