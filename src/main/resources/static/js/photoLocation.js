
mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpeHhkIiwiYSI6ImNrdzk5MGRleDAwN3MycG13dzliNTVlZ20ifQ.8a4kA7C251FLFX3aTngyHA';
var photoposition=[document.getElementById("lngPhoto").value, document.getElementById("latPhoto").value];
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: photoposition,
    zoom:8
});

var marker = new mapboxgl.Marker({
    color: "#3a97ff",
    draggable: false
}).setLngLat([photoposition[0], photoposition[1]
])
    .addTo(map);

map.dragRotate.disable();