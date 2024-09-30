package com.valdisdot.skeleton.util;

import com.valdisdot.skeleton.core.ViewInstance;
import com.valdisdot.skeleton.core.ViewInstanceProvider;
import com.valdisdot.skeleton.gui.parser.provider.MoldProvider;
import com.valdisdot.skeleton.gui.parser.provider.json.JsonMoldProvider;
import com.valdisdot.skeleton.gui.parser.provider.json.JsonPlot;
import com.valdisdot.skeleton.gui.view.swing.JViewInstanceProvider;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * The util class, which allows the user to initialize the library, essential files and defaults.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class HelloWorld {
    public static void main(String[] args) throws IOException {
        boolean wasExecution = false;
        if (args.length != 0) {
            ArrayList<String> params = new ArrayList<>(Arrays.asList(args));
            if (params.contains("--doc")) {
                System.out.print("Writing the 'doc.txt' file. ");
                JsonPlot.writeDocReferenceTo(new File("doc.txt"));
                System.out.println("Done!");
                wasExecution = true;
                params.remove("--doc");
            }
            if (params.contains("--init")) {
                System.out.print("Writing the 'skeleton.json' file. ");
                JsonPlot.compileJsonStarterFileTo(new File("skeleton.json"));
                System.out.println("Done!");
                wasExecution = true;
                params.remove("--init");
            }
            if (params.contains("--example")) {
                System.out.print("Looking for the 'skeleton.json' file. ");
                File skeleton = new File("skeleton.json");
                if (skeleton.exists() && skeleton.isFile() && skeleton.length() > 0) {
                    System.out.printf("Found! (length: %s kBytes)%n", (float) skeleton.length() / 1024);
                } else {
                    System.out.println("Can't find the file.");
                    System.out.print("Writing the 'skeleton.json' file. ");
                    JsonPlot.compileJsonStarterFileTo(new File("skeleton.json"));
                    System.out.println("Done!");
                }
                System.out.print("Trying to parse the 'skeleton.json'. ");
                //library usage example
                MoldProvider moldProvider = new JsonMoldProvider(skeleton);
                ViewInstanceProvider<JPanel, JComponent, String> viewInstanceProvider = new JViewInstanceProvider(moldProvider);
                Optional<ViewInstance<JPanel, JComponent, String>> panelOptional = viewInstanceProvider.getInstance("some_panel_id");
                if (panelOptional.isEmpty()) {
                    System.out.println("Looks like you modified 'skeleton.json', can't parse it into the example.");
                } else { //build
                    ViewInstance<JPanel, JComponent, String> viewInstance = panelOptional.get();
                    JPanel panel = viewInstance.getView();
                    JFrame frame = new JFrame(viewInstanceProvider.getProperties().fetchAsString("example_application_name").orElse("Your app"));
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.add(panel);
                    frame.pack();
                    frame.setVisible(true);
                    viewInstance.getControlUnit("an_id_0").ifPresent(cu -> cu.addAction(() -> System.out.println("Skeleton example: you've clicked the button at " + LocalDateTime.now())));
                }
                System.out.println("Done!");
                wasExecution = true;
                params.remove("--example");
            }
            if (!params.isEmpty()) System.out.println("Unknown parameters: " + params);
        }
        if (!wasExecution)
            System.out.println("If your attempt is to learn more about the library or init the plot file, rerun Skeleton library's main class with parameters:\n--doc\t\tto create a 'doc.txt' file with API references\n--init\t\tto create a 'skeleton.json' file with the whole available fields and properties\n--example\tto show a plain panel with a view example");
    }
}
