package com.valdisdot.util.ui;

import java.util.Map;

public class UICodeExample {
    public static void main(String[] args) {
        //experiment1();
        //experiment2();
    }

    static void experiment1() {
        SimpleUI cli = new SimpleUI(
                "UICodeExample CLI",
                null,
                null,
                Map.of(
                        "test1", () -> "HEY FROM test1",
                        "test2", () -> {
                            throw new Exception("EXCEPTION FROM test2");
                        }
                ),
                () -> "unknown command",
                "exit",
                () -> "closing ...",
                "help",
                null,
                Throwable::getMessage,
                System.in,
                System.out
        );
        new Thread(cli).start();
    }

    static void experiment2() {
        SimpleUI cli = SimpleUI.builder()
                .name("UICodeExample CLI")
                .helloMessage(null)
                .inputInvitationMessage(null)
                .commandMap(Map.of(
                        "test1", () -> "HEY FROM test1",
                        "test2", () -> {
                            throw new Exception("EXCEPTION FROM test2");
                        }
                ))
                .unknownCommandAction(() -> "unknown command")
                .exitCommand(
                        "exit",
                        () -> "closing ..."
                )
                .helpCommand(
                        "help",
                        null
                )
                .exceptionHandler(Throwable::getMessage)
                .inputStream(System.in)
                .outputStream(System.out)
                .build();
        new Thread(cli).start();
    }
}
