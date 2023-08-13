package com.valdisdot.util.gui.json;

import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.parser.DefaultParsedView;
import com.valdisdot.util.ui.gui.parser.GUIParser;
import com.valdisdot.util.ui.gui.parser.json.JsonGUIParser;
import com.valdisdot.util.ui.gui.parser.mold.FrameMold;
import com.valdisdot.util.ui.gui.tool.FrameFactory;

import java.io.File;
import java.io.IOException;

public class JsonGUIParserExperiments {
    public static void main(String[] args) throws IOException {
        GUIParser guiParser = new JsonGUIParser(new File("D:\\users\\main\\documents\\Projects\\Utilities\\sketch\\gui.json"));
        for (FrameMold mold : guiParser.get()) {
            FrameFactory.playOnDesk(new DefaultParsedView(mold, ValuesParser::toJSONArray).get());
        }
    }
}
