package ru.aleksandrbriker;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AppTest {
    @Test
    void analizeTakingOpportunitiesTest() {
        App game = new App();
        game.initField();
        game.fillFieldWithPiece("a1", true);
        game.fillFieldWithPiece("b2", false);


        Assertions.assertThat(true).isEqualTo
                (game.analizeTakingOpportunities(true, 0, 0, -1, -1));
        Assertions.assertThat(false).isEqualTo
                (game.analizeTakingOpportunities(false, 0, 0, -1, -1));
    }

    @Test
    void makeMoveThrowsCorrectlyTest() {
        App game = new App();
        game.initField();
        game.fillFieldWithPiece("D4", true);
        game.fillFieldWithPiece("c3", false);
        game.fillFieldWithPiece("a1", false);
        game.fillFieldWithPiece("e5", false);

        try {
            game.makeMove("D4-C5", true);
        } catch (MyException e) {
            Assertions.assertThat("invalid move").isEqualTo(e.getMessage());
        }

        try {
            game.makeMove("D4:A1", true);
        } catch (MyException e) {
            Assertions.assertThat("busy cell").isEqualTo(e.getMessage());
        }
    }

    @Test
    void fillFieldWithPieceTest() {
        App game = new App();
        game.initField();
        game.fillFieldWithPiece("D2", true);
        game.fillFieldWithPiece("c3", false);
        game.fillFieldWithPiece("g3", true);
        game.fillFieldWithPiece("g5", true);
        game.fillFieldWithPiece("b4", false);
        game.fillFieldWithPiece("a5", false);

        Assertions.assertThat(false).isEqualTo(game.getField()[4][0].getExists());
        Assertions.assertThat(true).isEqualTo(game.getField()[3][1].getIsQueen());
    }

    @Test
    void initFrildTest() {
        App game = new App();
        game.initField();
        Assertions.assertThat(false).isEqualTo(game.getField()[4][0].getExists());
        Assertions.assertThat(false).isEqualTo(game.getField()[4][0].getWhiteCell());
        Assertions.assertThat(true).isEqualTo(game.getField()[4][1].getWhiteCell());
    }

    @Test
    void runTest() throws MyException, IOException {
        App game = new App();
        try(PrintWriter writer = new PrintWriter("input.txt")) {
            writer.print("D2 f2 h4 a1\n" +
                    "a5 b4 c3\n" +
                    "h4-g5 c3:e1:h4:e7\n" +
                    "\n");
        } catch (Exception e) {
            System.out.println("unexpectes exception");
        }
        ArrayList<String> whiteExpected = new ArrayList<>();
        whiteExpected.add("a1 ");
        ArrayList<String> blackExpected = new ArrayList<>();
        blackExpected.add("E7 ");
        blackExpected.add("a5 ");
        blackExpected.add("b4 ");
        Assertions.assertThat(whiteExpected).isEqualTo(game.run().get(0));
        Assertions.assertThat(blackExpected).isEqualTo(game.run().get(1));
    }
}


