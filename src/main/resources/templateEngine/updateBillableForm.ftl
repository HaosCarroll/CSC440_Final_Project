<h2>Enter Details of Exisiting Billable to Update.</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">

    <div class="form-group">
      <label for="entityBillableIdNumber">Billable entity ID number</label>
      <input type="text" class="form-control" pattern="[0-9]{1,}" title="Must be digits." id="entityBillableIdNumber" name="entityBillableIdNumber" placeholder="Enter ID number Billable to modify.">
    </div>

    <div class="form-group">
      <label for="memberNumberService">Serviced Member ID</label>
      <input type="text" class="form-control" pattern="[0-9]{9}" title="Nine Digit Member ID." id="memberNumberService" name="memberNumberService" placeholder="Enter ID of serviced member.">
    </div>

    <div class="form-group">
      <label for="providerNumberServicing">Servicing Provider ID</label>
      <input type="text" class="form-control" pattern="[0-9]{9}"  title="Nine Digit Provider ID." id="providerNumberServicing" name="providerNumberServicing" placeholder="Enter ID of servicing provider.">
    </div>

    <div class="form-group" id="dateInput">
		<label for="dateServiced">Date Serviced</label><br>
        <input  type="text" class="form-control" id="dateServiced" name="dateServiced" data-provide="datepicker">
    </div>

    <div class="form-group" id="dateInput">
		<label for="dateServicedRecorded">Date Recorded</label><br>
        <input  type="text" class="form-control" id="dateServicedRecorded" name="dateServicedRecorded" data-provide="datepicker">
    </div>

    <div class="form-group">
      <label for="serviceCost">Cost</label>
      <input type="number" step="any" class="form-control currency" id="serviceCost" name="serviceCost" min="0.00" max="999.99" value="0.00" />
    </div>

    <div class="form-group">
      <label for="serviceComment">Comment</label>
      <input type="text" class="form-control" id="serviceComment" name="serviceComment" placeholder="Enter comment if any.">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>

<!-- Simple JS Function to convert the data into JSON and Pass it as ajax Call -->
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
            url: "updateBillable",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("Billable SuccesFully Updated");
                this_.find('input, select').val('');
            },
            error : function(e) {
                $("#status").text(e.responseText);
            }
        });
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
});

</script>
