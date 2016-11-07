<html>
   <head>
      <title>Spark Project</title>
      <link rel="stylesheet" href="css/bootstrap.min.css">
      <link rel="stylesheet" href="css/bootstrap-theme.min.css">
      <link rel="stylesheet" href="css/starter-template.css">
   </head>
   <body>
      <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
         <div class="container">
            <div class="navbar-header">
               <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
               <span class="sr-only">Toggle navigation</span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
               </button>
               <a class="navbar-brand" href="#"></a>
            </div>
            <div class="collapse navbar-collapse">
               <ul class="nav navbar-nav">
                  <li class="active"><a href="/">Home</a></li>

                  <li class="dropdown">
                     <a class="dropdown-toggle" data-toggle="dropdown" href="#">ChocoProvidable<span class="caret"></span></a>
                     <ul class="dropdown-menu">
                        <li><a href="createChocoProvidable">Create ChocoProvidable</a></li>
                        <li><a href="getAllChocoProvidables">Get All ChocoProvidables</a></li>
                        <li><a href="updateChocoProvidable">Update ChocoProvidable</a></li>
                        <li><a href="removeChocoProvidable">Remove ChocoProvidable</a></li>
                     </ul>
                  </li>


                  <li class="dropdown">
                     <a class="dropdown-toggle" data-toggle="dropdown" href="#">ChocoProvider<span class="caret"></span></a>
                     <ul class="dropdown-menu">
                        <li><a href="createChocoProvider">Create ChocoProvider</a></li>
                        <li><a href="getAllChocoProviders">Get All ChocoProviders</a></li>
                        <li><a href="updateChocoProvider">Update ChocoProvider</a></li>
                        <li><a href="removeChocoProvider">Remove ChocoProvider</a></li>
                     </ul>
                  </li>


                  <li class="dropdown">
                     <a class="dropdown-toggle" data-toggle="dropdown" href="#">ChocoServiceProvided<span class="caret"></span></a>
                     <ul class="dropdown-menu">
                        <li><a href="createChocoServiceProvided">Create ChocoServiceProvided</a></li>
                        <li><a href="getAllChocoServiceProvideds">Get All ChocoServiceProvideds</a></li>
                        <li><a href="updateChocoServiceProvided">Update ChocoServiceProvided</a></li>
                        <li><a href="removeChocoServiceProvided">Remove ChocoServiceProvided</a></li>
                     </ul>
                  </li>


                  <li class="dropdown">
                     <a class="dropdown-toggle" data-toggle="dropdown" href="#">ChocoUser<span class="caret"></span></a>
                     <ul class="dropdown-menu">
                        <li><a href="createChocoUser">Create ChocoUser</a></li>
                        <li><a href="getAllChocoUsers">Get All ChocoUsers</a></li>
                        <li><a href="updateChocoUser">Update ChocoUser</a></li>
                        <li><a href="removeChocoUser">Remove ChocoUser</a></li>
                     </ul>
                  </li>

               </ul>
            </div>
            <!--/.nav-collapse -->
         </div>
      </div>
      <script src="js/jquery.min.js"></script>
      <script src="js/bootstrap.min.js"></script>
      <div class="container">
         <#include "${templateName}">
      </div>
   </body>
</html>

