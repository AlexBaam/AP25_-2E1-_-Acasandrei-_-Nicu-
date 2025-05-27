package org.example;

import java.io.File;

public class AllAdd {
    private Repository repo;
    private Adder toAdd;

    public AllAdd(Repository repo) {
        this.repo = repo;
    }

    public void executeCommand() {
        File imagesDir = new File("images");
        File[] images = imagesDir.listFiles();
        checkFiles(images);
    }

    private void checkFiles(File[] images) {
        String[] imageData = new String[2];
        if(images != null) {
            for (File image : images) {
                if(image.isFile()) {
                    imageData[0] = image.getName();
                    String lowerCaseName = imageData[0].toLowerCase();
                    if(lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")) {
                        imageData[1] = image.getPath();
                        toAdd = new Adder(imageData, repo);
                        toAdd.executeCommand();
                    }
                } else if(image.isDirectory()) {
                    File[] subImages = image.listFiles();
                    checkFiles(subImages);
                }
            }
        } else {
            System.out.println("Empty image directory!");
        }
    }
}
