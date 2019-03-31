public class GameField {

    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedpowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosedMap;
    private ArrayList<Integer> killShotTrack;

    public Map map;
    public Weapon[] weapon;
    public Ammo[] ammo;

    public void shuffle(ArrayList<> deck);
}