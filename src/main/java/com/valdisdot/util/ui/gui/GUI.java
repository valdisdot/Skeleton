package com.valdisdot.util.ui.gui;

import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.component.Frame;
import com.valdisdot.util.ui.gui.parser.DefaultParsedView;
import com.valdisdot.util.ui.gui.parser.ParsedView;
import com.valdisdot.util.ui.gui.parser.Parser;
import com.valdisdot.util.ui.gui.mold.ApplicationMold;
import com.valdisdot.util.ui.gui.mold.FrameMold;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Function;

/*
the class is user-end class for parsing GUI.
It accepts a Parser. We can use ready-to-use implementation (com.valdisdot.util.ui.gui.parser.json.JsonGUIParser, see 'README.md' -> ui.gui.parser.json.JsonGUIParser).
Parser is a simple supplier of a ApplicationMold. We can create a new one implementation of the interface (parse from XML or manually by creating some FrameMoldBuilder). Or we can just pass through a function ApplicationMold::new.
ApplicationMold.FramesGrouping is an enum which provide logic of building a graphical interface.
    MENU -> will create a JFrame with JMenu, all frames will be placed as JPanels
    PECULIAR_FRAME -> will create a JFrame, all frames will be places in a JScrollPane
    JOINT_FRAME (default) -> will create a list of JFrames
*/
public class GUI {

    //frame 'name' and the Control of all elements in the frame
    private final Map<String, Control> controlMap;

    //frame 'title' and the JPanel with all elements in the frame (for cases to parse without ApplicationMold.FramesGrouping policy)
    private final LinkedHashMap<String, JPanel> frameTitlePanelViewMap;

    //parsed with ApplicationMold.FramesGrouping policy frames
    private final LinkedList<JFrame> frames;

    //for cases where frames.size() > 1
    private final String applicationTitle;

    public GUI(ApplicationMold applicationMold, Function<List<String>, String> listToStringFunction) {
        //init storage
        controlMap = new HashMap<>();
        frames = new LinkedList<>();
        frameTitlePanelViewMap = new LinkedHashMap<>();
        //prepare application mold
        ApplicationMold.FramesGrouping framesGrouping = applicationMold.getBuildingPolicy();
        applicationTitle = applicationMold.getApplicationName();
        //parse raw
        for (FrameMold frameMold : applicationMold.getFrameMolds()) {
            ParsedView<JPanel> parsedView = new DefaultParsedView(frameMold, listToStringFunction);
            JPanel root = parsedView.get();
            controlMap.put(
                    frameMold.getName(),
                    new Control(parsedView.getDataCellGroups(), parsedView.getButtonsActionListenerConsumers()));
            frameTitlePanelViewMap.put(
                    frameMold.getTitle(),
                    root);
        }
        //parse user-end JFrames
        if (framesGrouping == ApplicationMold.FramesGrouping.MENU) {
            //create as a JMenu -> click on a JItem should change the current JPanel of the root JFrame
            frames.add(new Frame(
                    applicationTitle,
                    applicationMold.getRootItemMenuName(),
                    new Color(applicationMold.getMenuBackground()),
                    frameTitlePanelViewMap
            ));
        } else if (framesGrouping == ApplicationMold.FramesGrouping.JOINT_FRAME) {
            JPanel root = new JPanel(new MigLayout("insets 0,gap 0px 0px,wrap"));
            JFrame frame = new JFrame(applicationMold.getApplicationName());
            frameTitlePanelViewMap.forEach((title, panel) -> {
                root.add(panel);
                root.setBackground(panel.getBackground());
            });
            frame.add(root);
            frame.repaint();
            frame.revalidate();
            frame.pack();
            frames.add(frame);
        } else {
            //should the closing of one frame (EXIT_ON_CLOSE or ??) close the other JFrames?
            frameTitlePanelViewMap.forEach((title, panel) -> {
                JFrame frame = new JFrame(title);
                frame.add(panel);
                frame.repaint();
                frame.revalidate();
                frame.pack();
                frames.add(frame);
            });
        }
    }

    public GUI(ApplicationMold applicationMold) {
        this(applicationMold, ValuesParser::toJSONArray);
    }

    public GUI(Parser parser, Function<List<String>, String> listToStringFunction) {
        this(parser.get(), listToStringFunction);
    }

    public GUI(Parser guiParser) {
        this(guiParser, ValuesParser::toJSONArray);
    }

    public Map<String, Control> getControlMap() {
        return controlMap;
    }

    public List<JFrame> getFrames() {
        return frames;
    }

    public JFrame getFirstFrame() {
        return frames.getFirst();
    }

    public LinkedHashMap<String, JPanel> getFrameTitlePanelViewMap() {
        return frameTitlePanelViewMap;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

}