package Status;

import java.util.ArrayList;

public class Action {
    public final int WAIT = 0; //always true

    public final int MAKE_PROBE = 1;
    public final int MAKE_PYLON = 2;
    public final int MAKE_GATEWAY = 3;
    public final int MAKE_ZEALOT = 4;
    public final int MAKE_ASSIMILATOR = 5;
    public final int MAKE_STALKER = 6;
    public final int MAKE_SENTRY = 7;
    public final int MAKE_CYBERNETICS_CORE = 8;
    public final int MAKE_ROBOTICS_FACILITY = 9;
    public final int MAKE_OBSERVER = 10;
    public final int MAKE_IMMORTAL = 11;
    public final int MAKE_STARGATE = 12;
    public final int MAKE_PHOENIX = 13;
    public final int MAKE_VOID_RAY = 14;
    public final int MAKE_FORGE = 15;
    public final int MAKE_TWILIGHT_COUNCIL = 16;
    public final int MAKE_TEMPLAR_ARCHIVES = 17;
    public final int MAKE_DARK_SHRINE = 18;
    public final int MAKE_ROBOTICS_BAY = 19;
    public final int MAKE_FLEET_BEACON = 20;
    public final int MAKE_ORACLE = 21;
    public final int MAKE_WARP_PRISM = 22;
    public final int MAKE_COLOSSUS = 23;
    public final int MAKE_TEMPEST = 24;
    public final int MAKE_HIGH_TEMPLAR = 25;
    public final int MAKE_DARK_TEMPLAR = 26;
    public final int MAKE_CARRIER = 27;

    Status status = new Status();

    public ArrayList<Boolean> statuses = new ArrayList<>();

    public Action() {
        statuses.add(WAIT, true);
        statuses.add(MAKE_PROBE, false);
        statuses.add(MAKE_PYLON, false);
        statuses.add(MAKE_GATEWAY, false);
        statuses.add(MAKE_ZEALOT, false);
        statuses.add(MAKE_ASSIMILATOR, false);
        statuses.add(MAKE_STALKER, false);
        statuses.add(MAKE_SENTRY, false);
        statuses.add(MAKE_CYBERNETICS_CORE, false);
        statuses.add(MAKE_ROBOTICS_FACILITY, false);
        statuses.add(MAKE_OBSERVER, false);
        statuses.add(MAKE_IMMORTAL, false);
        statuses.add(MAKE_STARGATE, false);
        statuses.add(MAKE_PHOENIX, false);
        statuses.add(MAKE_VOID_RAY, false);
        statuses.add(MAKE_FORGE, false);
        statuses.add(MAKE_TWILIGHT_COUNCIL, false);
        statuses.add(MAKE_TEMPLAR_ARCHIVES, false);
        statuses.add(MAKE_DARK_SHRINE, false);
        statuses.add(MAKE_ROBOTICS_BAY, false);
        statuses.add(MAKE_FLEET_BEACON, false);
        statuses.add(MAKE_ORACLE, false);
        statuses.add(MAKE_WARP_PRISM, false);
        statuses.add(MAKE_COLOSSUS, false);
        statuses.add(MAKE_TEMPEST, false);
        statuses.add(MAKE_HIGH_TEMPLAR, false);
        statuses.add(MAKE_DARK_TEMPLAR, false);
        statuses.add(MAKE_CARRIER, false);
    }
}
