package com.example.RejestracjaLogowanie.Controller;



import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.example.RejestracjaLogowanie.*;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class UploadPhotoController {
    public static String uploadDir = System.getProperty("user.dir") + "/uploads";

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private  PinRepository pinRepository;

    @Autowired
    ServletContext context;

    @GetMapping("/uploadPhoto")
    public String uploadPhoto(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("lat", user.getMapsCenterLatitude());
        model.addAttribute("lng", user.getMapsCenterLongitude());
        model.addAttribute("profilepic", user.getProfilePicPath());
        return "uploadPhoto" ;
    }

    @PostMapping("/upload")
    public String upload (Model model, @RequestParam("image") MultipartFile image, @RequestParam("description") String description, @RequestParam("tags") String tags, @RequestParam(value = "lat", required=false) Double lat, @RequestParam(value = "lng", required=false) Double lng, @RequestParam(value = "pindescription", required=false) String pindescription, @RequestParam(value = "color", required=false) String color) throws IOException {
        String userImageFolderPath = "src/main/resources/static/images/user";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());


        Photo photo = new Photo("ścieżka");
        photoRepository.save(photo);

        Post post = new Post(tags, description, new Date(), user, photo);
        postRepository.save(post);


        String photoExtension = image.getOriginalFilename();
        photoExtension = photoExtension.substring(photoExtension.length() - 3).toLowerCase();
        StringBuilder builder = new StringBuilder();

        if(Objects.equals(photoExtension,"jpg") || Objects.equals(photoExtension,"png") )
               builder.append(userImageFolderPath + user.getId().toString());
        else {
            try {
                throw new Exception("Wprowadzono nieprawidłowy typ pliku");
            } catch (Exception e) {
                e.printStackTrace();
                cleanDatabase(photo.getPhotoId(),post.getIdPost());
                return "uploadPhoto";
            }
        }


        String path = builder.toString();
        String filename = photo.getPhotoId() + "."+ photoExtension;


        builder.append("/" + filename);
        Path fileNameAndPath = Paths.get(builder.toString());
        try{
            image.transferTo(fileNameAndPath);
        }
        catch(Exception e){
            System.out.print("Problem z zapisem oryginalnego zdjęcia na dysk. Info="+e.toString());
            cleanDatabase(photo.getPhotoId(),post.getIdPost());
            e.printStackTrace();
        }


        //utworzenie miniatury zdjęcia
        try {
            makeMiniaturePhoto(path, fileNameAndPath, filename, photoExtension);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Błąd w tworzeniu miniatury. Info="+e.toString());
        }


        path = fileNameAndPath.toString();
        int index = path.lastIndexOf("images");
        path = path.substring(index-1);

        photo.setPath(path);
        photoRepository.save(photo);

        postRepository.save(post);
        user.addPost(post);

        if(!Objects.isNull(lng))
        {
            Pin pin = new Pin(lng, lat, pindescription, color, user, photo);
            pinRepository.save(pin);
        }


        return "uploadPhoto";
    }

    private void cleanDatabase(Long photoID,Long postID){
        photoRepository.deleteById(photoID);
        postRepository.deleteById(postID);
    }

    private void makeMiniaturePhoto(String path, Path fileNameAndPath, String filename, String photoExtension) throws IOException {
        File tempFile = new File(fileNameAndPath.toString());

        //utworzenie folderu z miniaturkami
        File directory = new File(path+"/resized/");
        if(!directory.exists())
            directory.mkdir();


        BufferedImage fileToResize = ImageIO.read(tempFile);


        //zmienne do kompresji
        BufferedImage resizedPicture;
        int compressionWidth;
        boolean needRotation=false;
        int height = fileToResize.getHeight();
        int width = fileToResize.getWidth();

        //sprawdzanie orientacji grafiki + sprawdzenie rozmiarów
        //zdjęcie jest zmniejszane jeśli dodano większe, dla mniejszych tylko kompresowane
        if(height>1000 && width>700 || height>700 && width>1000) {
            if (height >= width){
                compressionWidth = 1000;
                needRotation=true;
            }
            else
                compressionWidth = 1500;
        }else{
            compressionWidth = width;
        }

        //skalowanie
        resizedPicture = Scalr.resize(fileToResize, compressionWidth);

        //sprawdzanie czy zdjęcie nie posiada dodatkowego parametru obrotu
        Scalr.Rotation rotation = getOrientationExif(tempFile);

        //resizedPicture = Scalr.resize(fileToResize,Scalr.Method.QUALITY,compressionWidth);


        if(null!=rotation)
            resizedPicture = Scalr.rotate(resizedPicture, rotation);
        else if (needRotation)
            resizedPicture = Scalr.rotate(resizedPicture, Scalr.Rotation.CW_270);

        //utworzenie ścieżki do której ma zostać zapisana miniatura
        StringBuilder builder = new StringBuilder();
        builder.append(path).append("/resized/").append(filename);
        File destFile = new File(builder.toString());

        //zapisanie zdjęcia do pamięci
        try {
            ImageIO.write(resizedPicture, photoExtension, destFile);
        }catch (IOException e){
            System.out.printf("Problem z zapisem miniaturki na dysk");
            e.printStackTrace();


            Long id = Long.valueOf(filename.substring(0,filename.length()-4));
            cleanDatabase(id,id);
            new File(fileNameAndPath.toString()).delete();
            return;
        }
        //wyczyszczenie pamięci z niepotrzebnych plików
        fileToResize.flush();
        resizedPicture.flush();

    }


    private Scalr.Rotation getOrientationExif(File file){
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            ExifIFD0Directory dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            int orientation = dir.getInt(ExifIFD0Directory.TAG_ORIENTATION);

            switch (orientation) {
                case 6: // [Exif IFD0] Orientation - Right side, top (Rotate 90 CW)
                    return Scalr.Rotation.CW_90;
                case 3: // [Exif IFD0] Orientation - Bottom, right side (Rotate 180 CW)
                    return Scalr.Rotation.CW_180;
                case 8: // [Exif IFD0] Orientation - Left side, bottom (Rotate 270 CW)
                    return Scalr.Rotation.CW_270;
            }

        } catch (ImageProcessingException | MetadataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
