



var buttons = document.querySelectorAll(".buttoncontainer button");
var panels = document.querySelectorAll(".tabpanel");
var mapa= document.getElementById("map");

function showPanel(panelIndex) {
    panels.forEach(function (node) {
       node.style.display= "none";
        mapa.style.display="none";
    });
    panels[panelIndex].style.display="block";
    mapa.style.display="block";
}

