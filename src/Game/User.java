package Game;

import GameObject.Buildings;
import GameObject.Resources;
import GameObject.Units;
import Status.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    //Initial Condition
    public final int INIT_MINERAL = 8;
    public final int INIT_GAS = 2;
    public boolean gasAss = false;
    public int assNum = 0;

    //Initial Condition
    public double mineral = 50;
    public double gas = 0;
    public int maxSupply = 10;
    public int currentSupply = 6;

    //minerals per minute gained per 1st and 2nd probe on a mineral mine
    private final int MINERAL_PER_MIN = 41;
    //minerals per minute gained for the 3rd probe on a mineral mine
    private final int MINERAL_PER_MIN2 = 20;

    private final int GAS_PER_MIN = 38;

    private final double GAS_PER_SEC = (double) GAS_PER_MIN / 60;
    public final double MINERAL_PER_SEC = (double) MINERAL_PER_MIN / 60;
    private final double MINERAL_PER_SEC2 = (double) MINERAL_PER_MIN2 / 60;

    Resources mineralPatch = new Resources(1500, 0);
    Resources vespene = new Resources(0, 2500);

    Units probe;
    Units zealot;
    Units stalker;
    Units sentry;
    Units observer;
    Units immortal;
    Units phoenix;
    Units voidRay;
    Units oracle;
    Units warpPrism;
    Units colossus;
    Units tempest;
    Units highTemplar;
    Units darkTemplar;
    Units carrier;

    Buildings nexus;
    Buildings pylon;
    Buildings assimilator;
    Buildings gateway;
    Buildings cyberneticsCore;
    Buildings roboticsFacility;
    Buildings stargate;
    Buildings forge;
    Buildings twilightCouncil;
    Buildings templarArchives;
    Buildings darkShrine;
    Buildings roboticsBay;
    Buildings fleetBeacon;

    HashMap<Units, Integer> units = new HashMap<>();
    ArrayList<Buildings> buildings = new ArrayList<>();
    ArrayList<Resources> minerals = new ArrayList<>();
    ArrayList<Resources> vespenes = new ArrayList<>();

    public User() {
        probe = new Units("Probe", "Nexus", 50, 0 , 17, 0, 1);
        zealot = new Units("Zealot", "Gateway", 100, 0, 38, 28, 2);
        stalker = new Units("Stalker", "Gateway",  125, 50, 42, 32, 2);
        sentry = new Units("Sentry", "Gateway",  50, 100, 37, 32, 1);
        observer = new Units("Observer", "Robotics Facility",  25, 75, 40, 0, 1);
        immortal = new Units("Immortal", "Robotics Facility",  250, 100, 55, 0, 4);
        phoenix = new Units("Phoenix", "Stargate",  150, 100, 35, 0, 2);
        voidRay = new Units("Void Ray", "Stargate",  250, 150, 60, 0, 3);
        oracle = new Units("Oracle", "Stargate",  150, 150, 50, 0,3);
        warpPrism = new Units("Warp Prism", "Robotics Facility",  200, 0, 50, 0, 2);
        colossus = new Units("Colossus", "Robotics Facility",  300,200,75,0, 6);
        tempest = new Units("Tempest", "Stargate",  300, 200, 60, 0, 4);
        highTemplar = new Units("High Templar", "Gateway",  50, 150, 55, 45, 2);
        darkTemplar = new Units("Dark Templar", "Gateway",  125, 125, 55, 45, 2);
        carrier = new Units("Carrier", "Stargate",  350, 250, 120, 0, 6);

        nexus = new Buildings("Nexus", 400, 0, 100, 10);
        pylon = new Buildings("Pylon", 100, 0, 25, 8);
        assimilator = new Buildings("Assimilator", 75, 0, 30, 0);
        gateway = new Buildings("Gateway", 150, 0, 65, 0);
        cyberneticsCore = new Buildings("Cybernetics Core",150, 0, 50, 0);
        roboticsFacility = new Buildings("Robotics Facility",200, 100, 65, 0);
        stargate = new Buildings("Stargate", 150,150,60,0);
        forge = new Buildings("Forge", 150, 0, 45, 0);
        twilightCouncil = new Buildings("Twilight Council", 150, 100, 50, 0);
        templarArchives = new Buildings("Templar Archives", 150,200,50,0);
        darkShrine = new Buildings("Dark Shrine", 100, 250, 100, 0);
        roboticsBay = new Buildings("Robotics Bay", 200, 200, 65, 0);
        fleetBeacon = new Buildings("Fleet Beacon", 300,200, 60, 0);

        units.put(probe, 6);
        units.put(zealot, 0);
        units.put(stalker, 0);
        units.put(sentry, 0);
        units.put(observer,0);
        units.put(immortal, 0);
        units.put(phoenix, 0);
        units.put(voidRay, 0);
        units.put(oracle, 0);
        units.put(warpPrism, 0);
        units.put(colossus, 0);
        units.put(tempest, 0);
        units.put(highTemplar , 0);
        units.put(darkTemplar, 0);
        units.put(carrier, 0);

        for (int i = 0; i < INIT_MINERAL; i++) {
            minerals.add(i, mineralPatch);
        }
        buildings.add(0, nexus);
        vespenes.add(0, vespene);

    }

    public void addResources() {
        if (Status.statues.get(Status.ASSIMILATOR_EXISTS)) {
            int forMin = units.get(probe) - 3;

            for (int i = 0; i < 3; i++) {
                gas += GAS_PER_SEC * assNum;
            }
            if (units.get(probe) >= INIT_MINERAL * 3 + INIT_GAS * 3) {
                for (int i = 0; i < INIT_MINERAL * 3 + INIT_GAS * 3; i++) {
                    mineral += MINERAL_PER_SEC;
                }

                for (int j = INIT_MINERAL * 3 + INIT_GAS * 3; j < forMin; j++) {
                    mineral += MINERAL_PER_SEC2;
                }
            } else {
                for (int i = 0; i < forMin; i++) {
                    mineral += MINERAL_PER_SEC;
                }
            }

        } else {

            if (units.get(probe) >= INIT_MINERAL * 3) {
                for (int i = 0; i < INIT_MINERAL * 3; i++) {
                    mineral += MINERAL_PER_SEC;
                }

                for (int i = INIT_MINERAL * 3; i < units.get(probe); i++) {
                    mineral += MINERAL_PER_SEC2;
                }
            } else {
                for (int i = 0; i < units.get(probe); i++) {
                    mineral += MINERAL_PER_SEC;
                }
            }

        }
    }
}
