mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpeHhkIiwiYSI6ImNrdzk5MGRleDAwN3MycG13dzliNTVlZ20ifQ.8a4kA7C251FLFX3aTngyHA';
var userposition=[21.017532,52.237049];
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center:userposition,
    zoom:8
});

var user_position = [21.017532,52.237049];


var mapdata = '[[${pins}]]';
console.log(mapdata);
mapdata = mapdata.replaceAll("]]", '');
mapdata = mapdata.replaceAll("]", '');
mapdata = mapdata.replaceAll("[[", '');
mapdata = mapdata.replaceAll("[", '');
var help = mapdata.split(",");
console.log(help);
var photo;
if(help.length !== 1)
{
    var ile = help.length / 7;
    for (var i = 0; i < ile; i++) {
        if(help[i * 7 + 6]!==" -1")
            photo = "<img class='pinsimages' src='"+help[i * 7 + 6]+"'/>";
        else photo = "";
        console.log(photo);
        var marker = new mapboxgl.Marker({
            color: help[i * 7 + 4],
            draggable: false
        }).setLngLat([help[i * 7 + 1], help[i * 7 + 2]
        ])
            .setPopup(new mapboxgl.Popup().setHTML(help[i * 7 + 3] + photo))
            .addTo(map);
    }
}


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

/*skrit*/

var markerpom ;

map.on('contextmenu', function (e) {
    markerpom.remove();
    addMarker(e.lngLat,'contextmenu');
    //console.log(e.lngLat.lat);
    document.getElementById("lat").value = e.lngLat.lat;
    document.getElementById("lng").value = e.lngLat.lng;

});

map.on('load', function() {
    addMarker(user_position, 'load');
});

function addMarker(ltlng,event) {

    if(event === 'contextmenu'){
        user_position = ltlng;
    }
    markerpom = new mapboxgl.Marker({draggable: true,color:"#d02922"})
        .setLngLat(user_position)
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