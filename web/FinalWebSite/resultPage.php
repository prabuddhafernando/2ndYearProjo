<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>

<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TestAppex</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css">
    <link href="js/bootstrap.js.css" rel="stylesheet" type="text/css">
    <link href="js/bootstrap.min.js.css" rel="stylesheet" type="text/css">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

</head>


<body>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

	<div class="container" id= "main">
    	
       <nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/.">
        <img style="max-width:200px; margin-top: -7px;"
             src="Logo.png">
      </a>
    </div><!-- end of navbar header-->

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="TestAppex.html">Home <span class="sr-only">(current)</span></a></li>
        <li><a href="AboutUs.html">About us</a></li>
        <li class="active"><a href="#">Current Traffic Condition</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Services <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-right" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>Search</button>
      </form>
     
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav> <br>
       
        
        <div class="row" id="bigCallout">
            <div class="col-12">   
                <div class="page-header">
                    <h1>Appex Traffic Visualization<small>You can get traffic data without errors.</small></h1>
                    
                    <p  class="lead"> In here you can consider main target of your website and your peoject. And your information and the other stuffs can contain here. Did you get my idea.</p>
                    
                    <a href="" class="btn  btn-large btn-primary">Current Traffic Condition</a>
                    <a href="" class="btn  btn-large btn-link">Current Traffic Condition</a>
                </div><!--end page header-->
            </div><!--end col-12-->
         </div><!-- end row-->
         
         <div class="row">
         	<form>
				Your Location:<br>
				<input type="text" name="Your Location"><br>
				Your Destination:<br>
				<input type="text" name="Your Destination">
				</form>
         </div><!-- end 2nd row-->
         
         <!-- add google map-->
         <div class="row" id="bigCallout">
         	<div class="col-12">
            	<div class="page-header">
                    <h1>Map Traffic Visualization<small>You can get current traffic condition.</small></h1>
                </div><!-- end page header-->
                
                <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d3961.7777193229895!2d79.901778!3d6.7968769999999985!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ae245416b7f34b5%3A0x7bd32721ab02560e!2sUniversity+of+Moratuwa!5e0!3m2!1sen!2slk!4v1431943019781" width="1200" height="450" frameborder="0" style="border:0"></iframe>
             
            </div><!--end col-12-->
         </div><!-- end bigCallout-->
        
        
    </div> <!-- end container -->

</body>


</html>