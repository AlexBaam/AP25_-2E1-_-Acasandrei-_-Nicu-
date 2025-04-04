package org.example;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.*;

public class Repository {
    private List<Image> images;
    private List<Image> savedImages = new ArrayList<>();
    private List<Image> loadedImages = new ArrayList<>();

    private ObjectMapper mapper = new ObjectMapper();

    public Repository() throws IOException {
        this.images = new ArrayList<Image>();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void add(Image image) {
        if(images.isEmpty()) {
            this.images.add(image);
        } else {
            for(Image i : images) {
                if(!i.filename().equals(image.filename())) {
                    if(!i.path().equals(image.path())) {
                        this.images.add(image);
                        break;
                    }
                } else {
                    System.out.println("Cannot add duplicates!");
                    break;
                }
            }
        }
    }

    public void remove(String fileName) {
        for(Image image : images) {
            if(image.filename().equals(fileName)) {
                images.remove(image);
                break;
            }
        }
    }

    public void update(String filename1, String filename2) {
        LocalDate date1 = LocalDate.now();
        for(Image image : images) {
            if(image.filename().equals(filename1)) {
                Image newImage = new Image(filename2, date1, image.tags() , image.path());
                images.add(newImage);
                images.remove(image);
            }
        }
    }

    public void save(String filename) throws IOException, InvalidDataException {
        boolean foundIt = false;
        String jsonPath = "C:\\Users\\AlexA\\Documents\\JAVA\\Lab5\\src\\main\\java\\org\\example\\bd.json";
        for(Image image : images) {
            if(filename.equals(image.filename())) {
                foundIt = true;
                savedImages.add(image);
                mapper.writeValue(new File (jsonPath), savedImages);
                images.remove(image);
            }

            if(foundIt){
                break;
            }
        }

        if (!foundIt) {
            throw new InvalidDataException("Imaginea nu a fost gasita pentru a fi salvata!");
        } else {
            System.out.println("Imaginea a fost salvata in fișierul JSON, dar ștearsa din depozit!");
        }
    }

    public void load(String fileName) throws IOException {
        String jsonPath = "C:\\Users\\AlexA\\Documents\\JAVA\\Lab5\\src\\main\\java\\org\\example\\bd.json";
        loadedImages = mapper.readValue(new File(jsonPath), new TypeReference<List<Image>>() {});
        boolean foundIt = false;

        for(Image image : loadedImages) {
            if(image.filename().equals(fileName)){
               images.add(image);
               loadedImages.remove(image);
               mapper.writeValue(new File (jsonPath), loadedImages);
               loadedImages.clear();
               foundIt = true;
            }

            if(foundIt){
                System.out.println("Name: " + image.filename()
                        + "\nPath: " + image.path()
                        + "\nDate: " + image.localDate() );

                System.out.println("Image loaded into repository, but deleted from JSON file!");
                break;
            }
        }
    }

    public void report() throws IOException {
        String[] argsReport = new String[0]; // sau poți trece null dacă preferi
        ReportCmd reportCmd = new ReportCmd(argsReport, this);
        reportCmd.executeCommand();
    }

    public void display(String fileName) throws IOException {
        for (Image image : images) {
            if (image.filename().equals(fileName)) {
                File file = new File(image.path());
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                } else {
                    System.out.println("Desktop is not supported");
                }
            }
        }
    }

    public List<Image> getImages() {
        return this.images;
    }
}
