package ru.aleksandrbriker;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class App {
    public App() {
    }

    private boolean mustTake = false;


    private final int boardSize = 8;
    private final int shiftSmallA = 97;
    private final int shiftBigA = 65;
    private final int shiftDigit = 49;
    private Piece[][] field = new Piece[boardSize][boardSize];

    public Piece[][] getField() {
        return field;
    }

    /**
     * <p>Инициализирует поле</p>
     * <p>Создает на клетках поля объекты класса Piece<p>
     * @return ничего
     */
    public void initField() {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                field[i][j] = new Piece();
                if ((i + j) % 2 == 1) {
                    field[i][j].setWhiteCell(true); // белыми являются поля с разными четностями координат
                    // (если {a,b,c,...} заменить на {1,2,3,...})
                }
            }
        }
    }


    /**
     * <p>Заполняет поле шашками</p>
     * @return ничего
     */
    public void fillFieldWithPiece(String piece, boolean isWhite) {

        int column = (int) piece.charAt(0) - shiftSmallA; // transform column letter to a number
        int row = (int) piece.charAt(1) - shiftDigit;

        if (column < 0) {
            column = (int) piece.charAt(0) - shiftBigA;
            field[column][row].setIsQueen(true);
        }

        field[column][row].setIsWhite(isWhite);
        field[column][row].setExists(true);
        field[column][row].setColumn(column);
        field[column][row].setRow(row);
    }

    /**
     * <p>Проверяет есть ли сейчас под боем какая-то шашка</p>
     *
     * @param fromCol Вертикаль, на которой находится фигура (0=a, 7=h)
     * @param fromRow Горизонталь, на которой находится фигура (1...8)
     * @param toCol   Вертикаль клетки, на которую выполняется ход
     * @param toRow   Горизонталь клетки, на которую выполняется ход
     * @param whiteToMove булевая переменная отвечающая за то белых ли сейчас ход
     * @return true, если сейчас есть кого сбить, false если нет
     */
    public boolean analizeTakingOpportunities(boolean whiteToMove, int fromCol, int fromRow, int toCol,
                                                         int toRow) {
        mustTake = false;
        if (!field[fromCol][fromRow].getIsQueen()) {
            if (field[fromCol][fromRow].getExists() && field[fromCol][fromRow].getIsWhite() == whiteToMove) {
                if (fromCol + 2 < boardSize && fromRow + 2 < boardSize && field[fromCol + 1][fromRow + 1].getExists()
                        && field[fromCol + 1][fromRow + 1].getIsWhite() != whiteToMove
                        && !field[fromCol + 2][fromRow + 2].getExists()) {
                    field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol + 1,
                            fromRow + 1, fromCol + 2, fromRow + 2));
                    mustTake = true;
                }
                if (fromCol + 2 < boardSize && fromRow - 2 >= 0 && field[fromCol + 1][fromRow - 1].getExists()
                        && field[fromCol + 1][fromRow - 1].getIsWhite() != whiteToMove
                        && !field[fromCol + 2][fromRow - 2].getExists()) {
                    field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol + 1,
                            fromRow - 1, fromCol + 2, fromRow - 2));
                    mustTake = true;
                }

                if (fromCol - 2 >= 0 && fromRow - 2 >= 0 && field[fromCol - 1][fromRow - 1].getExists()
                        && field[fromCol - 1][fromRow - 1].getIsWhite() != whiteToMove
                        && !field[fromCol - 2][fromRow - 2].getExists()) {
                    field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol - 1,
                             fromRow - 1, fromCol - 2, fromRow - 2));
                    mustTake = true;
                }

                if (fromCol - 2 >= 0 && fromRow + 2 < boardSize && field[fromCol - 1][fromRow + 1].getExists()
                        && field[fromCol - 1][fromRow + 1].getIsWhite() != whiteToMove
                        && !field[fromCol - 2][fromRow + 2].getExists()) {
                    field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol - 1,
                            fromRow + 1, fromCol - 2, fromRow + 2));
                    mustTake = true;
                }
            }
        }  else if (field[fromCol][fromRow].getExists() && field[fromCol][fromRow].getIsWhite() == whiteToMove) {
            for (int k = 1; fromCol + k + 1 < boardSize && fromRow + k + 1 < boardSize; ++k) {
                if (fromCol + k == toCol && fromRow + k == toRow) { // 1.5.9
                    break;
                }
                if (field[fromCol + k][fromRow + k].getExists()
                        && field[fromCol + k][fromRow + k].getIsWhite() != whiteToMove) {
                    for (int l = 1; fromCol + k + l < boardSize && fromRow + k + l < boardSize; ++l) {

                        if (!field[fromCol + k + l][fromRow + k + l].getExists()) {

                            field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol + k, fromRow + k,
                                    fromCol + k + l, fromRow + k + l));
                            mustTake = true;
                        }
                    }
                }
            }
            for (int k = 1; fromCol + k + 1 < boardSize && fromRow - k - 1 >= 0; ++k) {
                if (fromCol + k == toCol && fromRow - k == toRow) { // 1.5.9
                    break;
                }
                if (field[fromCol + k][fromRow - k].getExists()
                        && field[fromCol + k][fromRow - k].getIsWhite() != whiteToMove) {
                    for (int l = 1; fromCol + k + l < boardSize && fromRow - k - l >= 0; ++l) {
                        if (!field[fromCol + k + l][fromRow - k - l].getExists()) {
                            field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol + k, fromRow - k,
                                    fromCol + k + l, fromRow - k - l));
                            mustTake = true;
                        }
                    }
                }
            }
            for (int k = 1; fromCol - k - 1 >= 0 && fromRow - k - 1 >= 0; ++k) {
                if (fromCol - k == toCol && fromRow - k == toRow) { // 1.5.9
                    break;
                }
                if (field[fromCol - k][fromRow - k].getExists()
                        && field[fromCol - k][fromRow - k].getIsWhite() != whiteToMove) {
                    for (int l = 1; fromCol - k - l >= 0 && fromRow - k - l >= 0; ++l) {
                        if (!field[fromCol - k - l][fromRow - k - l].getExists()) {
                            field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol - k, fromRow - k,
                                    fromCol - k - l, fromRow - k - l));
                            mustTake = true;
                        }
                    }
                }
            }
            for (int k = 1; fromCol - k - 1 >= 0 && fromRow + k + 1 < boardSize; ++k) {
                if (fromCol - k == toCol && fromRow + k == toRow) { // 1.5.9
                    break;
                }
                if (field[fromCol - k][fromRow + k].getExists()
                        && field[fromCol - k][fromRow + k].getIsWhite() != whiteToMove) {
                    for (int l = 1; fromCol - k - l >= 0 && fromRow + k + l < boardSize; ++l) {
                        if (!field[fromCol - k - l][fromRow + k + l].getExists()) {
                            field[fromCol][fromRow].getTakingOpp().add(new Coords(fromCol - k, fromRow + k,
                                    fromCol - k - l, fromRow + k + l));
                            mustTake = true;
                        }
                    }
                }
            }
        }
        return mustTake;
    }
    /**
     * <p>Делает ход</p>
     *
     * @param move ход записанный в строке
     * @param isWhite булевая переменная отвечающая за то белых ли сейчас ход
     * @return true, если было взятие, false если не было
     */
    public boolean makeMove(String move, boolean isWhite) throws MyException {
        boolean wasTaken = false;
        int fromColumn = (int) move.charAt(0) - shiftSmallA;
        if (fromColumn < 0) {
            fromColumn = (int) move.charAt(0) - shiftBigA;
        }
        int fromRow = (int) move.charAt(1) - shiftDigit;
        Coords from = new Coords(fromColumn, fromRow);
        Coords to = new Coords((int) move.charAt(3) - shiftSmallA, (int) move.charAt(4) - shiftDigit);
        if (to.getColumn() < 0) {
            to.setColumn((int) move.charAt(3) - shiftBigA);
        }
        if (move.charAt(2) == '-') {
            if (mustTake) {
                throw new MyException("invalid move");
            }

            if (field[to.getColumn()][to.getRow()].getExists()) {
                throw new MyException("busy cell");
            }
            if (field[to.getColumn()][to.getRow()].getWhiteCell()) {
                throw new MyException("white cell");
            }
            if (!field[from.getColumn()][from.getRow()].getExists()
                    || Math.abs(to.getColumn() - from.getColumn()) != 1
                    && !field[from.getColumn()][from.getRow()].getIsQueen()
                    || Math.abs(to.getRow() - from.getRow()) != 1
                    && !field[from.getColumn()][from.getRow()].getIsQueen()) {
                throw new MyException("general error 245");
            }

        } else if (move.charAt(2) == ':') {
            Piece pieceToBeTaken = field[(from.getColumn() + to.getColumn()) / 2][(from.getRow() + to.getRow()) / 2];
            if (field[from.getColumn()][from.getRow()].getIsQueen()) {
                for (var elem : field[from.getColumn()][from.getRow()].getTakingOpp()) {
                    if (elem.getRowToBeTaken() == to.getRow() && elem.getColumnToBeTaken() == to.getColumn()) {
                        pieceToBeTaken = field[elem.getColumn()][elem.getRow()];
                    }
                }
            }
            boolean exists = false;
            for (var elem : field[from.getColumn()][from.getRow()].getTakingOpp()) {
                if (elem.getRowToBeTaken() == to.getRow() && elem.getColumnToBeTaken() == to.getColumn()) {
                    exists = true;
                }
            }
            if (!exists) {
                if (field[to.getColumn()][to.getRow()].getExists()) {
                    throw new MyException("busy cell");
                } else {
                    throw new MyException("general error 264");
                }
            }
            if (pieceToBeTaken.getExists() && pieceToBeTaken.getIsWhite() != isWhite) {
                pieceToBeTaken.setExists(false);
                wasTaken = true;
            } else {
                throw new MyException("general error 271"); // нельзя побить т.к. там либо никого нет либо шашка
            }

        }
        field[to.getColumn()][to.getRow()].setExists(true);
        field[to.getColumn()][to.getRow()].setIsWhite(field[from.getColumn()][from.getRow()].getIsWhite());
        field[to.getColumn()][to.getRow()].setRow(to.getRow());
        field[to.getColumn()][to.getRow()].setColumn(to.getColumn());
        field[to.getColumn()][to.getRow()].setIsQueen(field[from.getColumn()][from.getRow()].getIsQueen());
        if (field[to.getColumn()][to.getRow()].getIsWhite() && to.getRow() == boardSize - 1
                || !field[to.getColumn()][to.getRow()].getIsWhite() && to.getRow() == 0) {
            field[to.getColumn()][to.getRow()].setIsQueen(true);
        }

        field[from.getColumn()][from.getRow()].setExists(false);
        field[from.getColumn()][from.getRow()].setIsQueen(false);

        if (wasTaken) { // если произошло взятие, проверяем, можно ли продолжить цепочку
            analizeTakingOpportunities(isWhite, to.getColumn(), to.getRow(), from.getColumn(), from.getRow());
            // надо анализировать только для шашки которая побила
            if (!mustTake && move.length() > 6) {
                throw new MyException("general error 290"); // бить уже не кого, но в записи хода еще происходит взятие
            }
            if (mustTake) {
                try {
                    makeMove(move.substring(3), isWhite); // may throw 'Index 3 out of bounds for length 2'
                    // so I have to catch Exception instead of MyException
                } catch (Exception e) {
                    String error = e.getMessage();
                    if (!"general error".equals(error)) {
                        error = "invalid move";
                    }

                    throw new MyException(error);
                }
            }
        }
        return wasTaken;
    }

    public ArrayList<ArrayList<String>> run() throws IOException, MyException {
        initField();

        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String white = reader.readLine();
        String black = reader.readLine();

        Pattern pattern = Pattern.compile(" ");
        String[] whiteSplited = pattern.split(white);
        String[] blackSplited = pattern.split(black);

        for (var piece : whiteSplited) {
            fillFieldWithPiece(piece, true);
        }
        for (var piece : blackSplited) {
            fillFieldWithPiece(piece, false);
        }

        String move;

        while ((move = reader.readLine()) != null) {
            if ("".equals(move)) {
                break;
            }
            mustTake = false;
            for (int i = 0; i < boardSize; ++i) {
                for (int j = 0; j < boardSize; ++j) {
                    mustTake = mustTake | analizeTakingOpportunities(true, i, j, -1, -1);
                }
            }
            // move consists of 2 strings sepatated by ' '
            // parse move to String[] with 2 elements using regex
            pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String[] moveSplited = pattern.split(move);

            makeMove(moveSplited[0], true);

            mustTake = false;
            for (int i = 0; i < boardSize; ++i) {
                for (int j = 0; j < boardSize; ++j) {
                    mustTake = mustTake | analizeTakingOpportunities(false, i, j, -1, -1);
                }
            }
            makeMove(moveSplited[1], false);
        }

        ArrayList<String> whitePieces = new ArrayList<>();
        ArrayList<String> blackPieces = new ArrayList<>();

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (field[i][j].getExists()) {
                    int currShift = field[i][j].getIsQueen() ? shiftBigA : shiftSmallA;
                    char c = (char) (i + currShift);
                    if (field[i][j].getIsWhite()) {
                        whitePieces.add("" + c + (j + 1) + " ");
                        // без "" выводятся числа - что-то типа 100 100 102 104 102 104 106 104 104
                    } else {
                        blackPieces.add("" + c + (j + 1) + " ");
                    }
                }
            }
        }
        Collections.sort(whitePieces);
        Collections.sort(blackPieces);

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        result.add(whitePieces);
        result.add(blackPieces);
        return result;

    }
}


