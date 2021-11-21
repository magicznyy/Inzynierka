
function checkpositon(long,lati) {
    displayMap(long, lati)


}


function displayMap(center) {

    mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpeHhkIiwiYSI6ImNrdzk5MGRleDAwN3MycG13dzliNTVlZ20ifQ.8a4kA7C251FLFX3aTngyHA';
    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center: center

    });

}
