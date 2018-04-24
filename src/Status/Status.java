package Status;

import java.util.ArrayList;

public class Status {
    public static final int PYLON_EXISTS = 0;
    public static final int GATEWAY_EXISTS = 1;
    public static final int ASSIMILATOR_EXISTS = 2;
    public static final int CYBERNETICS_CORE_EXISTS = 3;
    public static final int ROBOTICS_FACILITY_EXISTS = 4;
    public static final int STARGATE_EXISTS = 5;
    public static final int FORGE_EXISTS = 6;
    public static final int TWILIGHT_COUNCIL_EXISTS = 7;
    public static final int ROBOTICS_BAY_EXISTS = 8;
    public static final int FLEET_BEACON_EXISTS = 9;
    public static final int TEMPLAR_ARCHIVES_EXISTS = 10;
    public static final int DARK_SHRINE_EXISTS = 11;


    public static ArrayList<Boolean> statues = new ArrayList<>();

    public Status() {
        statues.add(PYLON_EXISTS, false);
        statues.add(GATEWAY_EXISTS, false);
        statues.add(ASSIMILATOR_EXISTS, false);
        statues.add(CYBERNETICS_CORE_EXISTS, false);
        statues.add(ROBOTICS_FACILITY_EXISTS, false);
        statues.add(STARGATE_EXISTS, false);
        statues.add(FORGE_EXISTS, false);
        statues.add(TWILIGHT_COUNCIL_EXISTS, false);
        statues.add(ROBOTICS_BAY_EXISTS, false);
        statues.add(FLEET_BEACON_EXISTS, false);
        statues.add(TEMPLAR_ARCHIVES_EXISTS, false);
        statues.add(DARK_SHRINE_EXISTS, false);
    }
}
