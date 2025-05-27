package org.example;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.*;

public class Repository {
    private List<Image> images;

    private List<Image> jsonSavedImages = new ArrayList<>();
    private List<Image> jsonLoadedImages = new ArrayList<>();

    private List<Image> textSavedImages = new ArrayList<>();
    private List<Image> textLoadedImages = new ArrayList<>();

    private List<Image> binarySavedImages = new ArrayList<>();
    private List<Image> binaryLoadedImages = new ArrayList<>();

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

    public void save(String filename, String format) throws IOException, InvalidDataException {
        switch(format) {
            case "json": {
                boolean foundIt = false;
                String jsonPath = "data/bd.json";
                for(Image image : images) {
                    if(filename.equals(image.filename())) {
                        foundIt = true;
                        jsonSavedImages.add(image);
                        mapper.writeValue(new File (jsonPath), jsonSavedImages);
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
                break;
            }
            case "text": {
                String toDB = "";
                boolean foundIt = false;
                File textFile = new File("data/bd.txt");
                FileWriter writer = new FileWriter(textFile);

                for(Image image : images) {
                    if(filename.equals(image.filename())) {
                        foundIt = true;
                        textSavedImages.add(image);
                        images.remove(image);
                    }

                    if(foundIt){
                        for(Image i : textSavedImages) {
                            toDB = toDB +  i.filename() + "+" + i.tags() + "+" + i.path() + "+" + i.localDate() + "\n";
                        }
                        writer.write(toDB);
                        break;
                    }
                }
                if (!foundIt) {
                    throw new InvalidDataException("Image could not be found to be saved!");
                } else {
                    System.out.println("Image saved into the text file, but deleted from repository!");
                }
                writer.close();
                break;
            }
            case "binary": {
                boolean foundIt = false;
                File binFile = new File("data/bd.bin");
                FileOutputStream fos = new FileOutputStream(binFile);
                ObjectOutputStream out = new ObjectOutputStream(fos);

                for(Image image : images) {
                    if(filename.equals(image.filename())) {
                        foundIt = true;
                        binarySavedImages.add(image);
                        images.remove(image);
                    }

                    if(foundIt){
                        out.writeObject(binarySavedImages);
                        break;
                    }
                }
                if (!foundIt) {
                    throw new InvalidDataException("Image could not be found to be saved!");
                } else {
                    System.out.println("Image saved into the binary file, but deleted from repository!");
                }
                out.close();
                fos.close();
                break;
            }
            default: {
                System.out.println("Could not save the image!");
                break;
            }
        }
    }

    public void load(String fileName, String format) throws IOException {
        switch (format){
            case "json": {
                jsonLoadedImages = mapper.readValue(new File("data/bd.json"), new TypeReference<List<Image>>() {});
                boolean foundIt = false;

                for(Image image : jsonLoadedImages) {
                    if(image.filename().equals(fileName)){
                        images.add(image);
                        jsonLoadedImages.remove(image);
                        mapper.writeValue(new File ("data/bd.json"), jsonLoadedImages);
                        jsonLoadedImages.clear();
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
            case "text": {
                boolean foundIt = false;
                List<String> toLoad = new ArrayList<>();
                FileReader reader = new FileReader("data/bd.txt");
                BufferedReader br = new BufferedReader(reader);

                String line;
                while((line = br.readLine()) != null){
                    toLoad.add(line);
                }

                for(String s : toLoad){
                    String[] parts = s.split("\\+", 4);
                    if(parts[0].equals(fileName)){
                        foundIt = true;

                        LocalDate date = LocalDate.parse(parts[3]);

                        String[] tags = parts[1].split(",");
                        List<String> tagsList = new ArrayList<>();
                        for(String tag : tags){
                            tagsList.add(tag);
                        }

                        Image image = new Image(parts[0],date,tagsList,parts[2]);
                        textLoadedImages.add(image);
                        images.add(image);
                    }

                    if(foundIt){
                        break;
                    }
                }

                if(!foundIt){
                    System.out.println("Did not find any matching images in the text file!");
                } else {
                    for(Image image : textLoadedImages){
                        if(image.filename().equals(fileName)){
                            System.out.println("Name: " + image.filename()
                                    + "\nTags: " + image.tags()
                                    + "\nPath: " + image.path()
                                    + "\nDate: " + image.localDate() );
                        }
                    }
                    System.out.println("Image loaded into repository, but deleted from text file!");
                }
            }
            case "binary": {
                jsonLoadedImages = mapper.readValue(new File("data/bd.json"), new TypeReference<List<Image>>() {});
                boolean foundIt = false;

                for(Image image : jsonLoadedImages) {
                    if(image.filename().equals(fileName)){
                        images.add(image);
                        jsonLoadedImages.remove(image);
                        mapper.writeValue(new File ("data/bd.json"), jsonLoadedImages);
                        jsonLoadedImages.clear();
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
            default: {
                System.out.println("Could not load the image!");
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
