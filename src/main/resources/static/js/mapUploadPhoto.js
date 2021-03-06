
mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpeHhkIiwiYSI6ImNrdzk5MGRleDAwN3MycG13dzliNTVlZ20ifQ.8a4kA7C251FLFX3aTngyHA';
var userposition=[document.getElementById("lngUser").value, document.getElementById("latUser").value];
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center:userposition,
    zoom:8
});


var geocoder;

// Add the control to the map.
map.addControl(geocoder = new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        mapboxgl: mapboxgl,
        color: "#FFFF00"
    })
);

geocoder.on('result', function(e) {
    document.getElementById("lat").value = e.result.center[1];
    document.getElementById("lng").value = e.result.center[0];
});

var markerpom ;

map.on('contextmenu', function (e) {
    markerpom.remove();
    addMarker(e.lngLat,'contextmenu');
    //console.log(e.lngLat.lat);
    document.getElementById("lat").value = e.lngLat.lat;
    document.getElementById("lng").value = e.lngLat.lng;

});

map.on('load', function() {
    addMarker(userposition, 'load');
});

function addMarker(ltlng,event) {

    if(event === 'contextmenu'){
        userposition = ltlng;
    }
    markerpom = new mapboxgl.Marker({draggable: true,color:"#c8c6d0"})
        .setLngLat(userposition)
        .addTo(map)
        .on('dragend', onDragEnd);
}

function onDragEnd() {
    var lngLat = markerpom.getLngLat();
    document.getElementById("lat").value = lngLat.lat;
    document.getElementById("lng").value = lngLat.lng;
}

$('#signupForm').submit(function(event){
    event.preventDefault();
    $.ajax({
        success: function(data){
            alert(data);
            location.reload();

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Status: " + textStatus);
            alert("Error: " + errorThrown);
        }
    });
});

document.getElementById('geocoder').appendChild(geocoder.onAdd(map));