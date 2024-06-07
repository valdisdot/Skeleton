package com.valdisdot.skeleton.OLD.ui;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;

/*
The class is a simple UI util. It uses any InputStream and OutputStream for interacting with user.
WARNING: class uses while(true) and Thread.sleep() - use only with Threads like 'new Thread(new SimpleUI(...)).start()'
Code example: com.valdisdot.util.ui.UICodeExample
 */
public class SimpleUI implements Runnable {
    private String name;
    private String helloMessage;
    private String inputInvitationMessage;
    private Map<String, Callable<String>> commandMap; //command and its action-message
    private Callable<String> unknownCommandAction; //action-message for unknown command
    private String exitCommand;
    private String helpCommand;
    private Function<Exception, String> exceptionHandler;
    private InputStream inputStream; //input
    private Writer writer; //аoутпут

    public SimpleUI(
            String name, //cli entity name
            String helloMessage, //null -> no printing
            String inputInvitationMessage, //input command message, null -> no printing
            Map<String, Callable<String>> commandMap, //command and its action-message
            Callable<String> unknownCommandAction, //action-message for unknown command
            //you must define exit
            String exitCommand,
            Callable<String> onExitAction,
            String helpCommand, //help for listing all command
            Callable<String> onHelpAction,
            Function<Exception, String> exceptionHandler, // exception to string converter
            InputStream inputStream, //input
            OutputStream outputStream //output
    ) {
        this.name = Objects.requireNonNull(name);
        this.helloMessage = helloMessage;
        this.inputInvitationMessage = inputInvitationMessage;
        this.commandMap = new HashMap<>(Objects.requireNonNull(commandMap));
        this.unknownCommandAction = Objects.requireNonNull(unknownCommandAction);
        this.exitCommand = Objects.requireNonNull(exitCommand);
        this.commandMap.put(this.exitCommand, Objects.requireNonNull(onExitAction));
        this.helpCommand = Objects.requireNonNull(helpCommand);
        //onHelpAction may be null
        if (Objects.isNull(onHelpAction))
            onHelpAction = () -> this.commandMap.keySet().stream()
                    .sorted()
                    .collect(
                            StringBuilder::new,
                            ((stringBuilder, s) -> stringBuilder.append(s).append(" ")),
                            StringBuilder::append).
                    toString();
        this.commandMap.put(this.helpCommand, onHelpAction);
        this.exceptionHandler = Objects.requireNonNull(exceptionHandler);
        this.inputStream = Objects.requireNonNull(inputStream);
        this.writer = new OutputStreamWriter(Objects.requireNonNull(outputStream));
    }

    //for builder, more clean
    private SimpleUI() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String commandBuffer;
        write(helloMessage, writer);
        write(inputInvitationMessage, writer);

        while (true) {
            commandBuffer = readLine(reader);
            if (commandBuffer != null) {
                try {
                    write(commandMap.getOrDefault(commandBuffer, unknownCommandAction).call(), writer);
                } catch (Exception e) {
                    write(exceptionHandler.apply(e), writer);
                }

                if (commandBuffer.equals(exitCommand)) {
                    break;
                }
                write(inputInvitationMessage, writer);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String readLine(BufferedReader reader) {
        try {
            if (reader.ready()) {
                return reader.readLine();
            }
            return null;
        } catch (IOException e) {
            //I have no idea what did you do
            throw new RuntimeException(e);
        }
    }

    //you can overload it
    public void write(String s, Writer writer) {
        if (Objects.nonNull(s) && !s.isBlank())
            try {
                writer.write(
                        "[" +
                                name +
                                "]" +
                                "[" +
                                LocalDateTime.now() +
                                "]" +
                                ": " +
                                s +
                                "\n");
                writer.flush();
            } catch (IOException e) {
                //and don't want to
                throw new RuntimeException(e);
            }
    }

    public static class Builder {
        private SimpleUI ui = new SimpleUI();

        {
            ui.commandMap = new HashMap<>();
        }

        public Builder name(String name) {
            ui.name = Objects.requireNonNull(name, "Name of SimpleUI entity can not be null");
            if (name.isEmpty()) throw new IllegalArgumentException("Name of SimpleUI entity can not be empty");
            return this;
        }

        public Builder helloMessage(String helloMessage) {
            ui.helloMessage = helloMessage;
            return this;
        }

        public Builder inputInvitationMessage(String inputInvitationMessage) {
            ui.inputInvitationMessage = inputInvitationMessage;
            return this;
        }

        public Builder commandMap(Map<String, Callable<String>> commandMap) {
            ui.commandMap.putAll(Objects.requireNonNull(commandMap, "Command map of SimpleUI entity can not be null"));
            return this;
        }

        public Builder unknownCommandAction(Callable<String> unknownCommandAction) {
            ui.unknownCommandAction = Objects.requireNonNull(unknownCommandAction, "Action for unknown command of SimpleUI entity can not be null");
            return this;
        }

        public Builder exitCommand(String exitCommand, Callable<String> onExitAction) {
            ui.exitCommand = Objects.requireNonNull(exitCommand, "Exit command of SimpleUI entity can not be null");
            if (exitCommand.isBlank())
                throw new IllegalArgumentException("Exit command of SimpleUI entity can not be empty");
            ui.commandMap.put(exitCommand, Objects.requireNonNull(onExitAction, "Action for exit command of SimpleUI entity can not be null"));
            return this;
        }

        public Builder helpCommand(String helpCommand, Callable<String> onHelpAction) {
            ui.helpCommand = Objects.requireNonNull(helpCommand, "Help command of SimpleUI entity can not be null");
            if (helpCommand.isBlank())
                throw new IllegalArgumentException("Help command of SimpleUI entity can not be empty");
            if (Objects.isNull(onHelpAction)) {
                //this is why variable for working with lambda has to be final or effectively final.
                //variable may be changed with runtime
                Map<String, Callable<String>> map = ui.commandMap;
                onHelpAction = () -> map.keySet().stream() //with ui.commandMap doesn't work after calling Builder.build(), ui is null when lambda uses it
                        .sorted()
                        .collect(
                                StringBuilder::new,
                                ((stringBuilder, s) -> stringBuilder.append(s).append(" ")),
                                StringBuilder::append).
                        toString();
            }
            ui.commandMap.put(ui.helpCommand, onHelpAction);
            return this;
        }

        public Builder exceptionHandler(Function<Exception, String> exceptionHandler) {
            ui.exceptionHandler = Objects.requireNonNull(exceptionHandler, "Exception handler function of SimpleUI entity can not be null");
            return this;
        }

        public Builder inputStream(InputStream inputStream) {
            ui.inputStream = Objects.requireNonNull(inputStream, "Input stream of SimpleUI entity can not be null");
            return this;
        }

        public Builder outputStream(OutputStream outputStream) {
            ui.writer = new OutputStreamWriter(Objects.requireNonNull(outputStream, "Output stream of SimpleUI entity can not be null"));
            return this;
        }

        SimpleUI build() {
            //just once, honey
            try {
                return Objects.requireNonNull(ui);
            } finally {
                ui = null;
            }
        }
    }
}
