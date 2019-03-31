public class Player {

    private String clientName;
    private int[] position;        //È giusta la dichiarazione del vettore?
    private int score;
    private String color;
    private PlayerBoard playerBoard;
    private boolean firstPlayer;
    private boolean suspended;
    
    public PlayerBoard playerBoard;
    public Hand hand;
    public Match match;

    public void draw(ArrayList<> deck);

}
