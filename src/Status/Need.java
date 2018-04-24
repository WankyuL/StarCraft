package Status;

import java.util.ArrayList;

public class Need {
    public final int NEED_PROBE = 0;
    public final int NEED_PYLON = 1;
    public final int NEED_GATEWAY = 2;
    public final int NEED_ZEALOT = 3;
    public final int NEED_ASSIMILATOR = 4;
    public final int NEED_STALKER = 5;
    public final int NEED_SENTRY = 6;
    public final int NEED_CYBERNETICS_CORE = 7;
    public final int NEED_ROBOTICS_FACILITY = 8;
    public final int NEED_OBSERVER = 9;
    public final int NEED_IMMORTAL = 10;
    public final int NEED_STARGATE = 11;
    public final int NEED_PHOENIX = 12;
    public final int NEED_VOID_RAY = 13;
    public final int NEED_FORGE = 14;
    public final int NEED_TWILIGHT_COUNCIL = 15;
    public final int NEED_TEMPLAR_ARCHIVES = 16;
    public final int NEED_DARK_SHRINE = 17;
    public final int NEED_ROBOTICS_BAY = 18;
    public final int NEED_FLEET_BEACON = 19;
    public final int NEED_ORACLE = 20;
    public final int NEED_WARP_PRISM = 21;
    public final int NEED_COLOSSUS = 22;
    public final int NEED_TEMPEST = 23;
    public final int NEED_HIGH_TEMPLAR = 24;
    public final int NEED_DARK_TEMPLAR = 25;
    public final int NEED_CARRIER = 26;

    public ArrayList<Boolean> statuses = new ArrayList<>();

    public Need() {
        statuses.add(NEED_PROBE, false);
        statuses.add(NEED_PYLON, false);
        statuses.add(NEED_GATEWAY, false);
        statuses.add(NEED_ZEALOT, false);
        statuses.add(NEED_ASSIMILATOR, false);
        statuses.add(NEED_STALKER, false);
        statuses.add(NEED_SENTRY, false);
        statuses.add(NEED_CYBERNETICS_CORE, false);
        statuses.add(NEED_ROBOTICS_FACILITY, false);
        statuses.add(NEED_OBSERVER, false);
        statuses.add(NEED_IMMORTAL, false);
        statuses.add(NEED_STARGATE, false);
        statuses.add(NEED_PHOENIX, false);
        statuses.add(NEED_VOID_RAY, false);
        statuses.add(NEED_FORGE, false);
        statuses.add(NEED_TWILIGHT_COUNCIL, false);
        statuses.add(NEED_TEMPLAR_ARCHIVES, false);
        statuses.add(NEED_DARK_SHRINE, false);
        statuses.add(NEED_ROBOTICS_BAY, false);
        statuses.add(NEED_FLEET_BEACON, false);
        statuses.add(NEED_ORACLE, false);
        statuses.add(NEED_WARP_PRISM, false);
        statuses.add(NEED_COLOSSUS, false);
        statuses.add(NEED_TEMPEST, false);
        statuses.add(NEED_HIGH_TEMPLAR, false);
        statuses.add(NEED_DARK_TEMPLAR, false);
        statuses.add(NEED_CARRIER, false);
    }
}
