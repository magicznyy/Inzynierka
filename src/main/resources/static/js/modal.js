window.onload = function () {
    var modalContainer = document.getElementById("showpicture");
    var imageFromBox = document.getElementById("image1");
    var imageModal = document.getElementById("img01");
    var divContainer = document.getElementById("postphoto");
    var span = document.getElementsByClassName("close")[0];

imageFromBox.addEventListener("click", event => {
    modalContainer.style.display = "block";
    imageModal.src = imageFromBox.src;
    imageModal.alt = imageFromBox.alt;
});

span.addEventListener("click", event => { 
    modalContainer.style.display = "none";
});
};



