public class Match {

    private ArrayList<Player> players;
    private Player turn;
    private GameField status;

    public GameField gameField;
    public Player[] playerName;

    public void initPlayers();
    public void initGameField();
    public void round(Player player);
}