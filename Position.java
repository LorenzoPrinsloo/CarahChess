//package root;

public class Position {

    int column;
    int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

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

    public void incrementColumn() {
        this.column++;
    }

    public void incrementRow() {
        this.row++;
    }
}