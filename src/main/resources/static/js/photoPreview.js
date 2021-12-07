function setId(id){

        var path = "http://localhost:8080/photoPreview/post/" + id;
        document.getElementById("photoPreview").src=path;
        document.getElementsByClassName("modal-title").value= "dupa";
        document.getElementById("exampleModalLabel").value= "dupa";

}
window.onload = function()
{
        document.getElementsByClassName("modal-title").value= "dupa";
        document.getElementById("exampleModalLabel").value= "dupa";
};

