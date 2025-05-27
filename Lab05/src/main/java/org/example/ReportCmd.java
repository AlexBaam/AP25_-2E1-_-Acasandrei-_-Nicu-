package org.example;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.io.IOException;

/*
* init() -> initializeaza motorul Velocity
* .getTemplate ("nume_fisier") -> incarca un template
* StringWriter  -> stocheaza datele in memorie inainte de a le pune fectiv intr-un fisier.
                -> e de folos atunci cand vrei sa modifici datele inainte sa le scrii
                -> sau sa le trimit mai departe intr-un alt procest
                -> adica e un string
* template.merge(context, writer) -> astfel, Velocity va procesa sablonul si va scrie rezultatul in StringWriter
                                  -> adica legatura intre template si context
* writer.toString() -> ne ajuta sa avem rezultatul intr-un format HTML


*
*/


public class ReportCmd extends Commands {

    public ReportCmd(String[] args,Repository repo) {
        super(args, repo);
    }

    @Override
    protected void executeCommand() {
        try {
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "src/main/resources");
            velocityEngine.init();

            Template template = velocityEngine.getTemplate("report.vm");

            VelocityContext context = new VelocityContext();
            List<Image> images = repo.getImages();  // Problema aici

            context.put("reportTitle", "Raport Imagini");
            context.put("reportHeading", "Detalii despre Imagini");
            context.put("images", images);

            StringWriter writer = new StringWriter();

            template.merge(context, writer);

            FileWriter fileWriter = new FileWriter("reportHTML.html");
            fileWriter.write(writer.toString());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
