package GameObject;

public class Buildings {
    public final String NAME;
    public final int MINERAL_COST;
    public final int GAS_COST;
    public final int BUILD_TIME;
    public final int SUPPLY_PROVIDED;
    public boolean working = false;
    public int timer = 0;
    public Units units;

    public Buildings(String NAME, int MINERAL_COST, int GAS_COST, int BUILD_TIME, int SUPPLY_PROVIDED) {
        this.NAME = NAME;
        this.MINERAL_COST = MINERAL_COST;
        this.GAS_COST = GAS_COST;
        this.BUILD_TIME = BUILD_TIME;
        this.SUPPLY_PROVIDED = SUPPLY_PROVIDED;
    }
}
