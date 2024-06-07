package com.valdisdot.skeleton.OLD.ui.gui;

import com.valdisdot.skeleton.OLD.ui.gui.component.FrameWithMenuBar;
import com.valdisdot.skeleton.OLD.ui.gui.component.FrameWithSidebar;
import com.valdisdot.skeleton.OLD.ui.gui.mold.ApplicationMold;
import com.valdisdot.skeleton.OLD.ui.gui.mold.FrameMold;
import com.valdisdot.skeleton.OLD.ui.gui.parser.DefaultMoldParser;
import com.valdisdot.skeleton.OLD.ui.gui.parser.MoldParser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/*
the class is user-end class for parsing GUI.
It accepts a ApplicationMold. We can use ready-to-use provider of ApplicationMold (ui.gui.parser.json.JsonApplicationMoldParser) or build it by hand.
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

    public GUI(ApplicationMold applicationMold, MoldParser<JPanel> moldParser) {
        //init storage
        controlMap = new HashMap<>();
        frames = new LinkedList<>();
        frameTitlePanelViewMap = new LinkedHashMap<>();
        //prepare application mold
        ApplicationMold.FramesGrouping framesGrouping = applicationMold.getBuildingPolicy();
        applicationTitle = applicationMold.getApplicationName();
        //parse raw
        for (FrameMold frameMold : applicationMold.getFrameMolds()) {
            moldParser.parse(frameMold);
            JPanel root = moldParser.get();
            controlMap.put(
                    frameMold.getName(),
                    new Control(moldParser.getDataCellGroups(), moldParser.getButtonsActionListenerConsumers()));
            frameTitlePanelViewMap.put(
                    frameMold.getTitle(),
                    root);
        }
        //parse user-end JFrames
        if (framesGrouping == ApplicationMold.FramesGrouping.MENU) {
            //create as a JMenu -> click on a JItem should change the current JPanel of the root JFrame
            frames.add(new FrameWithMenuBar(
                    applicationTitle,
                    applicationMold.getRootItemMenuName(),
                    new Color(applicationMold.getControlBackground()),
                    frameTitlePanelViewMap
            ));
        } else if (framesGrouping == ApplicationMold.FramesGrouping.JOINT_FRAME) {
            JPanel root = new JPanel(new MigLayout("insets 0,gap 0px 0px"));
            JFrame frame = new JFrame(applicationMold.getApplicationName());
            frameTitlePanelViewMap.forEach((title, panel) -> {
                root.add(panel, "top");
                root.setBackground(panel.getBackground());
            });
            frame.add(root);
            frame.repaint();
            frame.revalidate();
            frame.pack();
            frames.add(frame);
        } else if (framesGrouping == ApplicationMold.FramesGrouping.SIDE_BAR) {
            frames.add(new FrameWithSidebar(
                    applicationTitle,
                    new Color(applicationMold.getControlBackground()),
                    frameTitlePanelViewMap
            ));
        } else if(framesGrouping == ApplicationMold.FramesGrouping.PECULIAR_FRAME){
            frameTitlePanelViewMap.forEach((title, panel) -> {
                JFrame frame = new JFrame(title);
                frame.add(panel);
                frame.repaint();
                frame.revalidate();
                frame.pack();
                frames.add(frame);
            });
        } //else no parsing into JFrames
    }

    public GUI(ApplicationMold applicationMold) {
        this(applicationMold, new DefaultMoldParser());
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