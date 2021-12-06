function showPhoto() {
  var chosenFile = document.getElementById("filebutton");
  var divForImage = document.getElementById("photoprevievbox");

  chosenFile.addEventListener("change", function () {
    let photo = chosenFile.files[0];
    if (photo) {
      let fileReader = new FileReader();
      fileReader.readAsDataURL(photo);
      fileReader.addEventListener("load", function () {
        divForImage.innerHTML = '<img src="' + this.result + '" />';

        var tmpDiv = document.querySelector(".photoInfo");
        //todo pokazywać w MB i KB
        tmpDiv.innerHTML = `Rozmiar: ${(photo.size/1024/1024).toFixed(1)}MB`;
    });
    }

    getExif(photo);
  });
}


export function getExif(varPhoto){
    EXIF.getData(varPhoto, function(){
        console.log("pobieram dane");
        console.log(EXIF.getAllTags(this));


        var allMetaData = [EXIF.getTag(this, "PixelXDimension"),
                            EXIF.getTag(this, "PixelYDimension"),
                            EXIF.getTag(this, "ExposureTime"),
                            EXIF.getTag(this, "FNumber"),
                            EXIF.getTag(this, "FocalLength"),
                            EXIF.getTag(this, "ISOSpeedRatings"),
                            EXIF.getTag(this, "Model")]


        console.log(allMetaData);
        console.log(allMetaData[2].numerator + " / " + allMetaData[2].denominator + "s");
        
        let Fnumber = allMetaData[3].numerator/allMetaData[3].denominator;
        console.log(Fnumber);

        let FocalLenght = allMetaData[4].numerator/allMetaData[4].denominator;
        console.log(FocalLenght);

        var tmpDiv = document.querySelectorAll(".photoInfo");
        tmpDiv[1].innerHTML = `Rozmiar: ${allMetaData[0]}x${allMetaData[1]}px`;
        tmpDiv[2].innerHTML = `Ujęcie: ${allMetaData[2].numerator}/${allMetaData[2].denominator} f/${Fnumber} ${FocalLenght}mm`;
        tmpDiv[3].innerHTML = `ISO: ${allMetaData[5]}`;
        tmpDiv[4].innerHTML = `Urządzenie: ${allMetaData[6]}`;
    })
};