function f(lg, pins)
{
    mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpeHhkIiwiYSI6ImNrdzk5MGRleDAwN3MycG13dzliNTVlZ20ifQ.8a4kA7C251FLFX3aTngyHA';
    var userposition=[document.getElementById("lngUser").value, document.getElementById("latUser").value];
    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center: userposition,
        zoom: 8
    });

    map.dragRotate.disable();

    var mapdata = pins;
    mapdata = mapdata.replaceAll("]]", '');
    mapdata = mapdata.replaceAll("]", '');
    mapdata = mapdata.replaceAll("[[", '');
    mapdata = mapdata.replaceAll("[", '');
    var help = mapdata.split(",");
    var photo;
    var idCurrUser=document.getElementById('idCurrUser').value;
    if (help.length !== 1) {
        var ile = help.length / 9;

        if(lg === 1)
        {
            var marker = [];
            for (var i = 0; i < ile; i++) {
                if (help[i * 9 + 6] !== " -1")
                    photo="<input type='image'  id='"+help[i * 9 + 5]+"' src='" + help[i * 9 + 6] + "' class='pinsimages' data-toggle='modal' data-target='#exampleModal' onclick='setId(this.id)'/>";
                else photo = "";
                console.log(help[i * 9 +7]);
                console.log(idCurrUser);
                if(help[i * 9 + 7].replace(" ", '') === idCurrUser)
                {
                    var pinitp= "<div class= 'podgladpinezki'>"+photo + help[i * 9 + 3]+"<form action='/deletepin' method='request'><input id='idPinezki' name='idPinezki' type='hidden' value='"+help[i * 9]+"'> <input type='image' class='iconBin' src='/icons/Maps/iconBinPin.svg' alt='Submit Form'/></form></div>";
                    marker[i] = new mapboxgl.Marker({
                        color: help[i * 9 + 4],
                        draggable: false
                    }).setLngLat([help[i * 9 + 1], help[i * 9 + 2]
                    ])
                        .setPopup(new mapboxgl.Popup()
                            .setHTML(pinitp)
                        )
                        .addTo(map);
                }
            }
        }

        if(lg === 2)
        {
            var marker = [];
            for (var ii = 0; ii < ile; ii++) {
                if (help[ii * 9 + 6] !== " -1")
                    photo="<input type='image'  id='"+help[ii * 9 + 5]+"' src='" + help[ii * 9 + 6] + "' class='pinsimages' data-toggle='modal' data-target='#exampleModal' onclick='setId(this.id)'/>";
                else photo = "";
                console.log(help[ii * 9 +7]);
                console.log(idCurrUser);
                if(help[ii * 9 + 7].replace(" ", '') === idCurrUser)
                    var pinitp= "<div class= 'podgladpinezki'>"+photo + help[ii * 9 + 3]+"<form action='/deletepin' method='request'><input id='idPinezki' name='idPinezki' type='hidden' value='"+help[ii * 9]+"'> <input type='image' class='iconBin' src='/icons/Maps/iconBinPin.svg' alt='Submit Form'/></form></div>";
                else pinitp="<div class= 'podgladpinezki'>"+photo + help[ii * 9 + 3]+"</div>";
                if(help[ii * 9 + 7].replace(" ", '') === idCurrUser || help[ii * 9 + 8] === " 1")
                {
                    marker[ii] = new mapboxgl.Marker({
                        color: help[ii * 9 + 4],
                        draggable: false
                    }).setLngLat([help[ii * 9 + 1], help[ii * 9 + 2]
                    ])
                        .setPopup(new mapboxgl.Popup()
                            .setHTML(pinitp)
                        )
                        .addTo(map);
                }
            }
        }


    }

    if(undefined !== marker && marker.length)
    {
        for( let y=0;y<marker.length;y++)
        {
            if(marker[y] !== undefined)
                marker[y].getElement().addEventListener('click', function () {

                    distance(help, ile, marker[y].getLngLat().lat, marker[y].getLngLat().lng);
                });
        }
    }




    var geocoder;

// Add the control to the map.
    map.addControl(geocoder = new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,
            mapboxgl: mapboxgl,
            color: "#50d6fe"
        })
    );

    geocoder.on('result', function (e) {
        document.getElementById("lat").value = e.result.center[1];
        document.getElementById("lng").value = e.result.center[0];
        distance(help, ile, e.result.center[1], e.result.center[0]);
        console.log(el.result._latlng);
    });

    var markerpom;

    map.on('contextmenu', function (e) {
        markerpom.remove();
        addMarker(e.lngLat, 'contextmenu');
        distance(help, ile, e.lngLat.lat, e.lngLat.lng);
        document.getElementById("lat").value = e.lngLat.lat;
        document.getElementById("lng").value = e.lngLat.lng;

    });

    map.on('load', function () {
        addMarker(userposition, 'load');
    });

    function addMarker(ltlng, event) {

        if (event === 'contextmenu') {
            userposition = ltlng;
        }
        markerpom = new mapboxgl.Marker({draggable: true, color: "#50d6fe"})
            .setLngLat(userposition)
            .addTo(map)
            .on('dragend', onDragEnd);
    }

    function onDragEnd() {
        var lngLat = markerpom.getLngLat();
        document.getElementById("lat").value = lngLat.lat;
        document.getElementById("lng").value = lngLat.lng;
        distance(help, ile, lngLat.lat, lngLat.lng);
    }

    $('#signupForm').submit(function (event) {
        event.preventDefault();
        $.ajax({
            success: function (data) {
                alert(data);
                location.reload();

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("Status: " + textStatus);
                alert("Error: " + errorThrown);
            }
        });
    });

    document.getElementById('geocoder').appendChild(geocoder.onAdd(map));

}