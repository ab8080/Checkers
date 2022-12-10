package ru.aleksandrbriker;

public final class Coords {
    private int column = 0;
    private int row = 0;
    private int columnToBeTaken = -1;
    private int rowToBeTaken = -1;
    Coords(int column, int row) {
        this.column = column;
        this.row = row;
    }
    Coords(int column, int row, int columnToBeTaken, int rowToBeTaken) {
        this.column = column;
        this.row = row;
        this.columnToBeTaken = columnToBeTaken;
        this.rowToBeTaken = rowToBeTaken;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getColumnToBeTaken() {
        return columnToBeTaken;
    }
    public int getRowToBeTaken() {
        return rowToBeTaken;
    }
    // Coords() { }
}
