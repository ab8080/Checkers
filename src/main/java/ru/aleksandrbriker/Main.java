package ru.aleksandrbriker;

import java.io.IOException;

public final class Main {
    public static void main(String[] args) {
        App game = new App();
        try {
            var result = game.run();
            var whitePieces = result.get(0);
            var blackPieces = result.get(1);
            for (var elem : whitePieces) {
                System.out.print(elem);
            }
            System.out.println();
            for (var elem : blackPieces) {
                System.out.print(elem);
            }
        } catch (IOException | MyException e) {
            System.out.println(e.getMessage());
        }
    }
    private Main() {

    }
}
