public class Shot {
    private boolean notUsed;
    private int[] cost;
    private int damage;
    private int tags;
    private boolean peekingRequired;
    private int movesRequired;
    private int movesGranted;
    private boolean stepByStep;
    private boolean cardinalDirectionrequired;
    private int targetableEnemies;
    private int shockDisplacement;

    public void getInfo();
    public ArrayList<Player> approchable(Square from);
    public void use();

}