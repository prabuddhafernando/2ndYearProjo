
$(function(){
	
	console.log("Requesting Data");
	
	
	$.ajax({
		type: 'GET',
		url: '/localDatabase.php',
//		dataType: 'json',
		success: function(data){
			console.log('Data: '+data.replace(",]","]"));
			var response = jQuery.parseJSON(data.replace(",]","]"));
//			var response = $.parseJson(data);
			console.log('parsedData: '+response);
			
			
//			var marker = new google.maps.Marker();
	

			var myLatlng = new google.maps.LatLng(6.847355, 79.926288);
var mapOptions = {
  zoom: 12,
  center: myLatlng
}
var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

			$.each(response, function(index){
			
				console.log('latitude: '+response[index].latitude);
				console.log('longitude: '+response[index].longitude);
				console.log('speed: '+response[index].speed);
				
			//	$.each(response[index], function(values){
			//		console.log(response[index][values]);
			//	});
			if(response[index].speed <= 20){
				var myLatlng = new google.maps.LatLng(response[index].latitude, response[index].longitude);
				console.log(myLatlng);
				
				var marker = new google.maps.Marker({
				position: myLatlng,
				icon: 'high.png',
				title:"Hello World!"
				});
				
				marker.setMap(map);
			}else if(response[index].speed <= 45){
				var myLatlng = new google.maps.LatLng(response[index].latitude, response[index].longitude);
				console.log(myLatlng);
				
				var marker = new google.maps.Marker({
				position: myLatlng,
				icon: 'medium.png',
				title:"Hello World!"
				});
				
				marker.setMap(map);
			}else{
				var myLatlng = new google.maps.LatLng(response[index].latitude, response[index].longitude);
				console.log(myLatlng);
				
				var marker = new google.maps.Marker({
				position: myLatlng,
				icon: 'low.png',
				title:"Hello World!"
				});
				
				marker.setMap(map);
			}
				
				
				


// To add the marker to the map, call setMap();

				
				// To add the marker to the map, call setMap();
	//			marker.setMap(map);
				
			});
		},
		error: function(data){
			console.log('Error');
		}
	});
	
});

