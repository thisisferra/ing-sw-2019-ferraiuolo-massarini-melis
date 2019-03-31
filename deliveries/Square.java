public abstract class Square {

    private int[] position;
    private ArrayList<Square> roomSquares;
    private ArrayList<Square> hammingSquare;
    private ArrayList<Square> axialSquares;
    private ArrayList<Square> doors;
    private boolean spawnPoint;
    private ArrayList<Player> playersOn;
    private String color;

    public Map map;


    public void restock(ArrayList<> deck);
}