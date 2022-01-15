


var button1 =  document.getElementById("buttonviewmap1");
var button2 =   document.getElementById("buttonviewmap2");
var mapa= document.getElementById("map");
var pins=document.getElementById('pinsy').value;

function showPanel(panelIndex) {

    console.log(panelIndex);
    if(panelIndex===0) {
        button1.style.background='#005E86';
        button2.style.background='#BFE0EE';

        button1.style.color='#BFE0EE';
        button2.style.color='#005E86';
        f(1, pins); /*lokalne pinezki*/
        mapa.style.display="block";
    }
    if(panelIndex===1) {
        button1.style.background='#BFE0EE';
        button2.style.background='#005E86';

        button2.style.color='#BFE0EE';
        button1.style.color='#005E86';

        f(2, pins); /*globalne pinezki*/

        mapa.style.display="block";
    }
}

f(1, pins); /*lokalne pinezki*/