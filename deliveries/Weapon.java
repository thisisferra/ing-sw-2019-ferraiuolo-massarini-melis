public abstract class Weapon {
    private String type;
    private boolean load;
    private int[] buyingCost;
    private int[] reloadCost;

    public abstract void reload(int[] cost);
}