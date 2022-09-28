
// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {
    
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function(position) {
        
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        
        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">나의 현재 위치</div>'; // 인포윈도우에 표시될 내용입니다
        
        // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);
            
      });
    
} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
    
    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
        message = 'geolocation을 사용할수 없어요..'
        
    displayMarker(locPosition, message);
}

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨 
    }; 

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({  
        map: map, 
        position: locPosition
    }); 
    
    var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
    });
    
    // 인포윈도우를 마커위에 표시합니다 
    infowindow.open(map, marker);
    
    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);      
}    


function showLocation(event) {
    var latitude = event.coords.latitude 
    var longitude = event.coords.longitude
    document.querySelector("#latitude").textContent = latitude
    document.querySelector("#longitude").textContent = longitude 
  
    
    let apiKey = "184b4b64db6cd5ca2d07ddafd0b11d3b";
    let weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude 
                  + "&lon=" + longitude 
                  + "&appid=" + apiKey; 
  
      let options = { method: 'GET' }
    $.ajax(weatherUrl, options).then((response) => {
        console.log(response)
        let icon = (response.weather[0].icon);
        console.log(icon);
        let iconUrl = "http://openweathermap.org/img/wn/" + icon +"@2x.png";
        let img = document.querySelector("#wicon");
        img.src = iconUrl 
        document.querySelector("#tempr").textContent = "" + (response.main.temp - 273) + "ºC"  // 현재 온도
      })
  }
  
  function showError(event) {
    alert("위치 정보를 얻을 수 없습니다.")
  }
  
  window.addEventListener('load', () => { 
    if(window.navigator.geolocation) {
       window.navigator.geolocation.getCurrentPosition(showLocation,showError)
    }
  
  })


