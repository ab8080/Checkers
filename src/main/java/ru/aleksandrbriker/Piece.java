
package ru.aleksandrbriker;

import java.util.ArrayList;
import java.util.List;

public final class Piece {
    private boolean exists = false;
    private boolean isWhite;
    private boolean isQueen = false;
    private boolean whiteCell = false;

    private int column;
    private int row;
    private List<Coords> takingOpp = new ArrayList<>();

    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public boolean getExists() {
        return exists;
    }
    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean getWhiteCell() {
        return whiteCell;
    }
    public void setWhiteCell(boolean whiteCell) {
        this.whiteCell = whiteCell;
    }
    public boolean getIsQueen() {
        return isQueen;
    }
    public void setIsQueen(boolean isQueen) {
        this.isQueen = isQueen;
    }

    public List<Coords> getTakingOpp() {
        return takingOpp;
    }
    public void setTakingOpp(List<Coords> takingOpp) {
        this.takingOpp = takingOpp;
    }
}



