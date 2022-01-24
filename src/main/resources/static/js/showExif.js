

 function getExif(){

    varPhoto = document.getElementById('mainPhoto');
    varPhoto.onload = function() {
        console.log(`w:${this.width} + h:${this.height}`);

        var tmpDiv = document.querySelectorAll(".photoInfo");  

        let w=this.naturalWidth;
        let h=this.naturalHeight;

        tmpDiv[0].innerHTML = `Rozmiar: ${(photo.size/1024/1024).toFixed(2)}MB`;
        tmpDiv[1].innerHTML = `Wymiary:${w}x${h}px`;
    }

    EXIF.getData(varPhoto, function(){
        console.log("pobieram dane");
        console.log(EXIF.getAllTags(this));


        var allMetaData = [EXIF.getTag(this, "PixelXDimension"),
                            EXIF.getTag(this, "PixelYDimension"),
                            EXIF.getTag(this, "ExposureTime"),
                            EXIF.getTag(this, "FNumber"),
                            EXIF.getTag(this, "FocalLength"),
                            EXIF.getTag(this, "ISOSpeedRatings"),
                            EXIF.getTag(this, "Model")];


        let Fnumber = allMetaData[3].numerator/allMetaData[3].denominator;
        let FocalLenght = allMetaData[4].numerator/allMetaData[4].denominator;

        var tmpDiv = document.querySelectorAll(".photoInfo");
        tmpDiv[1].innerHTML = `Wymiary: ${allMetaData[0]}x${allMetaData[1]}px`;
        tmpDiv[2].innerHTML = `Ujęcie: ${allMetaData[2].numerator}/${allMetaData[2].denominator} f/${Fnumber} ${FocalLenght}mm`;
        tmpDiv[3].innerHTML = `ISO: ${allMetaData[5]}`;
        tmpDiv[4].innerHTML = `Urządzenie: ${allMetaData[6]}`;
    })
};