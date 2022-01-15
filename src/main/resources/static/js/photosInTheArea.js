function distance(tab, tablenght, lat, lng, curUserr, lg) {


    var phototabpath = [];
    var photopostid=[];
    var R = 6371; // Promień ziemi w km
    var photocounter=0;
    for (var i = 0; i < tablenght; i++) {
        var dLat = degtorad(lat-tab[i * 9 + 2]);
        var dLon = degtorad(lng-tab[i * 9 + 1]);
        var a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(degtorad(tab[i * 9 + 2])) * Math.cos(degtorad(lat)) *
            Math.sin(dLon/2) * Math.sin(dLon/2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Dystans w km
        //identyikator właściciela pinezki
        var idUser= tab[i * 9 + 7].replace(" ", '');
        //sprawdzenie, czy jest ustawiony profil publiczny czy prywatny
        var pp = tab[i * 9 + 8].replace(" ", '');
        if(lg===2)
        {
            if(d<5 && tab[i * 9 + 6]!==" -1" && (pp === "1" || idUser === curUserr))
            {
                phototabpath[photocounter]= tab[i * 9 + 6];
                photopostid[photocounter]= tab[i * 9 + 5];
                photocounter++;
            }
        }
        else  if(d<5 && tab[i * 9 + 6]!==" -1" && idUser === curUserr)
        {
            phototabpath[photocounter]= tab[i * 9 + 6];
            photopostid[photocounter]= tab[i * 9 + 5];
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


function degtorad(deg) {
    return deg * (Math.PI/180)
}