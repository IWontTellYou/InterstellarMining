package sk.grest.game.entities;

public class Research {

    private int id;
    private String name;
    private String info;
    private int cost;

    private int columnIndex;
    private int rowIndex;

    public Research(int id, String name, String info, int cost, int columnIndex, int rowIndex) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.cost = cost;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
}
