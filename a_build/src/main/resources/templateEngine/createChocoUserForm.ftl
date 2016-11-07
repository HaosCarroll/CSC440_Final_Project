<h2>Create ChocoUser</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">

    <div class="form-group">
      <label for="memberNumber">ID NUMBER</label>
      <input type="text" class="form-control" id="memberNumber" name="memberNumber" placeholder="Enter ID number for member.">
    </div>

    <div class="form-group">
      <label for="memberName">Name</label>
      <input type="text" class="form-control" id="memberName" name="memberName" placeholder="Enter name of member.">
    </div>

    <div class="form-group">
      <label for="memberStreetAddress">Street Address</label>
      <input type="text" class="form-control" id="memberStreetAddress" name="memberStreetAddress" placeholder="Enter street address for member.">
    </div>

    <div class="form-group">
      <label for="memberCity">City</label>
      <input type="text" class="form-control" id="memberCity" name="memberCity" placeholder="Enter City for member.">
    </div>

    <div class="form-group">
      <label for="memberState">STATE</label>
      <input type="text" class="form-control" id="memberState" name="memberState" placeholder="Enter State for member.">
    </div>

    <div class="form-group">
      <label for="memberZip">ZIP CODE</label>
      <input type="number" class="form-control" id="memberZip" name="memberZip">
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
  </form>


<!-- Simple JS Function to convert the data into JSON and Pass it as ajax Call --!>
<script>
$(function() {
    $('form').submit(function(e) {
        e.preventDefault();
        var this_ = $(this);
        var array = this_.serializeArray();
        var json = {};
    
        $.each(array, function() {
            json[this.name] = this.value || '';
        });
        json = JSON.stringify(json);
    
        // Ajax Call
        $.ajax({
            type: "POST",
            url: "createChocoUser",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("ChocoUser SuccesFully Added");
                this_.find('input,select').val('');
            },
            error : function(e) {
                console.log(e.responseText);
                $("#status").text(e.responseText);
            }
        });
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
});

</script>
