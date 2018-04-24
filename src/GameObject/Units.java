package GameObject;

public class Units {
    public final String NAME;
    public final String BUILT_FROM;
    public final int MINERAL_COST;
    public final int GAS_COST;
    public final int BUILD_TIME;
    public final int BUILD_TIME_REDUCE;
    public final int SUPPLY_NEEDED;

    public Units(String NAME, String BUILT_FROM, int MINERAL_COST, int GAS_COST, int BUILD_TIME, int BUILD_TIME_REDUCE, int SUPPLY_NEEDED) {
        this.NAME = NAME;
        this.BUILT_FROM = BUILT_FROM;
        this.MINERAL_COST = MINERAL_COST;
        this.GAS_COST = GAS_COST;
        this.BUILD_TIME = BUILD_TIME;
        this.BUILD_TIME_REDUCE = BUILD_TIME_REDUCE;
        this.SUPPLY_NEEDED = SUPPLY_NEEDED;
    }
}
