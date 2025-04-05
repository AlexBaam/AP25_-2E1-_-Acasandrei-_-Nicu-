package org.example;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
            System.out.println("Image added: " + image.filename() + " with tags: " + image.tags());
        } else {
            for(Image i : images) {
                if(!i.filename().equals(image.filename())) {
                    if(!i.path().equals(image.path())) {
                        this.images.add(image);
                        System.out.println("Image added: " + image.filename() + " with tags: " + image.tags());
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
                System.out.println("Image removed: " + image.filename());
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
                System.out.println("Image updated: " + image.filename() + " to " + newImage.filename());
                images.remove(image);
            }
        }
    }

    public void save(String filename) throws IOException, InvalidDataException {
        boolean foundIt = false;
        String jsonPath = "data/bd.json";
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
            throw new InvalidDataException("Image could not be found to be saved!");
        } else {
            System.out.println("Image saved into the JSON file, but deleted from repository!");
        }
    }

    public void load(String fileName) throws IOException {
        String jsonPath = "data/bd.json";
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

    public List<String> tags(String nume) {
        for(Image image:images)
        {
            if(image.filename().equals(nume)) {
                return image.tags();
            }
        }
        return null;
    }

    public List<String> tagsForIImage(int indexImage) {
        int index = 0;
        for(Image image:images)
        {
            if(index == indexImage) {
                return image.tags();
            }
            index++;
        }
        return null;
    }
}
