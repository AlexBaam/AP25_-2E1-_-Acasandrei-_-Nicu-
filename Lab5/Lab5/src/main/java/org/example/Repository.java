package org.example;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Image> images;

    public Repository() {
        this.images = new ArrayList<Image>();
    }

    public void add(Image image) {
        images.add(image);
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
}
