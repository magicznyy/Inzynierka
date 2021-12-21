function distance(tab, tablenght, lat, lng) {
 /* 1 - dlugosc 2- szerokosc*/

    var phototabpath = [];
    var photopostid=[];
    var R = 6371; // Radius of the earth in km
    var photocounter=0;
    for (var i = 0; i < tablenght; i++) {
        var dLat = deg2rad(lat-tab[i * 8 + 2]);  // deg2rad below
        var dLon = deg2rad(lng-tab[i * 8 + 1]);
        var a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(deg2rad(tab[i * 8 + 2])) * Math.cos(deg2rad(lat)) *
            Math.sin(dLon/2) * Math.sin(dLon/2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Distance in km

        if(d<5 && tab[i * 8 + 6]!==" -1")
        {
            phototabpath[photocounter]= tab[i * 8 + 6];
            photopostid[photocounter]= tab[i * 8 + 5];
            photocounter++;
        }
    }

    let rodzic= document.getElementById("PITA");
    if(photocounter===0)
    rodzic.innerHTML='<h1 class="Photosh1">Brak zdjęć w okolicy</h1>';
    else rodzic.innerHTML='<h1 class="Photosh1">Zdjęcia w okolicy:</h1>';
    rodzic.innerHTML+='<div id="PITABI"></div>';
    for(let k=0; k<photocounter; k++)
    {

        var crate_img= document.createElement("INPUT");
        /*crate_img.className="PhotosyArea";*/
        crate_img.className="PhotosyArea";
        crate_img.setAttribute("src", phototabpath[k]);
        crate_img.setAttribute("type", 'image');
        crate_img.setAttribute("id", photopostid[k]);
        crate_img.setAttribute("onclick", 'setId(this.id)');
        crate_img.setAttribute("data-toggle", 'modal');
        crate_img.setAttribute("data-target", '#exampleModal');
        document.getElementById("PITABI").appendChild(crate_img);
    }



}


function deg2rad(deg) {
    return deg * (Math.PI/180)
}