package Game;

import GameObject.Buildings;
import GameObject.Resources;
import GameObject.Units;
import Status.Status;
import Status.Action;
import Status.Working;
import Status.Need;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.util.*;

public class Play {
    User user = new User();
    Need need = new Need();

    Status status = new Status();
    Action action = new Action();

    StringBuilder resultParts = new StringBuilder();
    ArrayList<String> resultString = new ArrayList<>();
    ArrayList<Integer> result = new ArrayList<>();
    HashMap<Units, Integer> goal = new HashMap<>();
    HashMap<Units, Integer> goalTest = new HashMap<>();

    static int probeNum = 6;
    int gatewayNum = 0;
    int roboticsNum = 0;
    int stargateNum = 0;
    int fromGateway = 0;
    int fromRobotics = 0;
    int fromStargate = 0;

    boolean alreadyMaking = false;

    ArrayList<Integer> buildingsTimer = new ArrayList<>();

    ArrayList<Buildings> buildingsEvent = new ArrayList<>();

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableProbe() {
        if (need.statuses.get(need.NEED_PROBE) && user.mineral >= user.probe.MINERAL_COST && (user.currentSupply < user.maxSupply) && (!user.buildings.get(0).working)) {
            action.statuses.set(action.MAKE_PROBE, true);
            if (user.units.get(user.probe) >= probeNum) {
                action.statuses.set(action.MAKE_PROBE, false);
            }
        } else {
            action.statuses.set(action.MAKE_PROBE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ablePylon() {
        if (need.statuses.get(need.NEED_PYLON) && user.mineral >= user.pylon.MINERAL_COST) {
            if (user.maxSupply - user.currentSupply <= 6) {
                action.statuses.set(action.MAKE_PYLON, true);
            } else {
                action.statuses.set(action.MAKE_PYLON, false);
            }

        } else {
            action.statuses.set(action.MAKE_PYLON, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableGateway() {
        if (need.statuses.get(need.NEED_GATEWAY) && user.mineral >= user.gateway.MINERAL_COST && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_GATEWAY, true);
        } else {
            action.statuses.set(action.MAKE_GATEWAY, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableZealot() {
        if (need.statuses.get(need.NEED_ZEALOT) && user.mineral >= user.zealot.MINERAL_COST && (user.currentSupply + user.zealot.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.GATEWAY_EXISTS) && findFree("Gateway")) {
            action.statuses.set(action.MAKE_ZEALOT, true);
        } else {
            action.statuses.set(action.MAKE_ZEALOT, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableStalker() {
        if (need.statuses.get(need.NEED_STALKER) && user.mineral >= user.stalker.MINERAL_COST && user.gas >= user.stalker.GAS_COST && (user.currentSupply + user.stalker.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.CYBERNETICS_CORE_EXISTS) && status.statues.get(status.GATEWAY_EXISTS) && findFree("Gateway")) {
            action.statuses.set(action.MAKE_STALKER, true);
        } else {
            action.statuses.set(action.MAKE_STALKER, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableCyberneticsCore() {
        if (need.statuses.get(need.NEED_CYBERNETICS_CORE) && user.mineral >= user.cyberneticsCore.MINERAL_COST && status.statues.get(status.GATEWAY_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_CYBERNETICS_CORE, true);
        } else {
            action.statuses.set(action.MAKE_CYBERNETICS_CORE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableRoboticsFacility() {
        if (need.statuses.get(need.NEED_ROBOTICS_FACILITY) && user.mineral >= user.roboticsFacility.MINERAL_COST && user.gas >= user.roboticsFacility.GAS_COST && status.statues.get(status.CYBERNETICS_CORE_EXISTS)) {
            action.statuses.set(action.MAKE_ROBOTICS_FACILITY, true);
        } else {
            action.statuses.set(action.MAKE_ROBOTICS_FACILITY, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableSentry() {
        if (need.statuses.get(need.NEED_SENTRY) && user.mineral >= user.sentry.MINERAL_COST && user.gas >= user.sentry.GAS_COST && (user.currentSupply + user.sentry.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.GATEWAY_EXISTS) && findFree("Gateway")) {
            action.statuses.set(action.MAKE_SENTRY, true);
        } else {
            action.statuses.set(action.MAKE_SENTRY, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableObserver() {
        if (need.statuses.get(need.NEED_OBSERVER) && user.mineral >= user.observer.MINERAL_COST && user.gas >= user.observer.GAS_COST && (user.currentSupply + user.observer.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.ROBOTICS_FACILITY_EXISTS) && findFree("Robotics Facility")) {
            action.statuses.set(action.MAKE_OBSERVER, true);
        } else {
            action.statuses.set(action.MAKE_OBSERVER, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableImmortal() {
        if (need.statuses.get(need.NEED_IMMORTAL) && user.mineral >= user.immortal.MINERAL_COST && user.gas >= user.immortal.GAS_COST && (user.currentSupply + user.immortal.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.ROBOTICS_FACILITY_EXISTS) && findFree("Robotics Facility")) {
            action.statuses.set(action.MAKE_IMMORTAL, true);
        } else {
            action.statuses.set(action.MAKE_IMMORTAL, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public synchronized void ableAssimilator() {
        if (need.statuses.get(need.NEED_ASSIMILATOR) && user.mineral >= user.assimilator.MINERAL_COST && user.assNum != user.INIT_GAS) {

            action.statuses.set(action.MAKE_ASSIMILATOR, true);
        } else {
            action.statuses.set(action.MAKE_ASSIMILATOR, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableStargate() {
        if (need.statuses.get(need.NEED_STARGATE) && user.mineral >= user.stargate.MINERAL_COST && user.gas >= user.stargate.GAS_COST && status.statues.get(status.CYBERNETICS_CORE_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_STARGATE, true);
        } else {
            action.statuses.set(action.MAKE_STARGATE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ablePhoenix() {
        if (need.statuses.get(need.NEED_PHOENIX) && user.mineral >= user.phoenix.MINERAL_COST && user.gas >= user.phoenix.GAS_COST && (user.currentSupply + user.phoenix.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.STARGATE_EXISTS) && findFree("Stargate")) {
            action.statuses.set(action.MAKE_PHOENIX, true);
        } else {
            action.statuses.set(action.MAKE_PHOENIX, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableVoidRay() {
        if (need.statuses.get(need.NEED_VOID_RAY) && user.mineral >= user.voidRay.MINERAL_COST && user.gas >= user.voidRay.GAS_COST && (user.currentSupply + user.voidRay.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.STARGATE_EXISTS) && findFree("Stargate")) {
            action.statuses.set(action.MAKE_VOID_RAY, true);
        } else {
            action.statuses.set(action.MAKE_VOID_RAY, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableForge() {
        if (need.statuses.get(need.NEED_FORGE) && user.mineral >= user.forge.MINERAL_COST && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_FORGE, true);
        } else {
            action.statuses.set(action.MAKE_FORGE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableTwilightCouncil() {
        if (need.statuses.get(need.NEED_TWILIGHT_COUNCIL) && user.mineral >= user.twilightCouncil.MINERAL_COST && user.gas >= user.twilightCouncil.GAS_COST && status.statues.get(status.CYBERNETICS_CORE_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_TWILIGHT_COUNCIL, true);
        } else {
            action.statuses.set(action.MAKE_TWILIGHT_COUNCIL, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableTemplarArchives() {
        if (need.statuses.get(need.NEED_TEMPLAR_ARCHIVES) && user.mineral >= user.templarArchives.MINERAL_COST && user.gas >= user.templarArchives.GAS_COST && status.statues.get(status.TWILIGHT_COUNCIL_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_TEMPLAR_ARCHIVES, true);
        } else {
            action.statuses.set(action.MAKE_TEMPLAR_ARCHIVES, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableDarkShrine() {
        if (need.statuses.get(need.NEED_DARK_SHRINE) && user.mineral >= user.darkShrine.MINERAL_COST && user.gas >= user.darkShrine.GAS_COST && status.statues.get(status.TWILIGHT_COUNCIL_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_DARK_SHRINE, true);
        } else {
            action.statuses.set(action.MAKE_DARK_SHRINE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableRoboticsBay() {
        if (need.statuses.get(need.NEED_ROBOTICS_BAY) && user.mineral >= user.roboticsBay.MINERAL_COST && user.gas >= user.roboticsBay.GAS_COST && status.statues.get(status.ROBOTICS_FACILITY_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_ROBOTICS_BAY, true);
        } else {
            action.statuses.set(action.MAKE_ROBOTICS_BAY, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableFleetBeacon() {
        if (need.statuses.get(need.NEED_FLEET_BEACON) && user.mineral >= user.fleetBeacon.MINERAL_COST && user.gas >= user.fleetBeacon.GAS_COST && status.statues.get(status.STARGATE_EXISTS) && status.statues.get(status.PYLON_EXISTS)) {
            action.statuses.set(action.MAKE_FLEET_BEACON, true);
        } else {
            action.statuses.set(action.MAKE_FLEET_BEACON, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableOracle() {
        if (need.statuses.get(need.NEED_ORACLE) && user.mineral >= user.oracle.MINERAL_COST && user.gas >= user.oracle.GAS_COST && (user.currentSupply + user.oracle.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.STARGATE_EXISTS) && findFree("Stargate")) {
            action.statuses.set(action.MAKE_ORACLE, true);
        } else {
            action.statuses.set(action.MAKE_ORACLE, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableWarpPrism() {
        if (need.statuses.get(need.NEED_WARP_PRISM) && user.mineral >= user.warpPrism.MINERAL_COST && (user.currentSupply + user.warpPrism.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.ROBOTICS_FACILITY_EXISTS) && findFree("Robotics Facility")) {
            action.statuses.set(action.MAKE_WARP_PRISM, true);
        } else {
            action.statuses.set(action.MAKE_WARP_PRISM, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableColossus() {
        if (need.statuses.get(need.NEED_COLOSSUS) && user.mineral >= user.colossus.MINERAL_COST && user.gas >= user.colossus.GAS_COST && (user.currentSupply + user.colossus.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.ROBOTICS_FACILITY_EXISTS) && status.statues.get(status.ROBOTICS_BAY_EXISTS) && findFree("Robotics Facility")) {
            action.statuses.set(action.MAKE_COLOSSUS, true);
        } else {
            action.statuses.set(action.MAKE_COLOSSUS, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableTempest() {
        if (need.statuses.get(need.NEED_TEMPEST) && user.mineral >= user.tempest.MINERAL_COST && user.gas >= user.tempest.GAS_COST && (user.currentSupply + user.tempest.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.FLEET_BEACON_EXISTS) && status.statues.get(status.STARGATE_EXISTS) && findFree("Stargate")) {
            action.statuses.set(action.MAKE_TEMPEST, true);
        } else {
            action.statuses.set(action.MAKE_TEMPEST, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableHighTemplar() {
        if (need.statuses.get(need.NEED_HIGH_TEMPLAR) && user.mineral >= user.highTemplar.MINERAL_COST && user.gas >= user.highTemplar.GAS_COST && (user.currentSupply + user.highTemplar.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.TEMPLAR_ARCHIVES_EXISTS) && status.statues.get(status.GATEWAY_EXISTS) && findFree("Gateway")) {
            action.statuses.set(action.MAKE_HIGH_TEMPLAR, true);
        } else {
            action.statuses.set(action.MAKE_HIGH_TEMPLAR, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableDarkTemplar() {
        if (need.statuses.get(need.NEED_DARK_TEMPLAR) && user.mineral >= user.darkTemplar.MINERAL_COST && user.gas >= user.darkTemplar.GAS_COST && (user.currentSupply + user.darkTemplar.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.DARK_SHRINE_EXISTS) && status.statues.get(status.GATEWAY_EXISTS) && findFree("Gateway")) {
            action.statuses.set(action.MAKE_DARK_TEMPLAR, true);
        } else {
            action.statuses.set(action.MAKE_DARK_TEMPLAR, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void ableCarrier() {
        if (need.statuses.get(need.NEED_CARRIER) && user.mineral >= user.carrier.MINERAL_COST && user.gas >= user.carrier.GAS_COST && (user.currentSupply + user.carrier.SUPPLY_NEEDED <= user.maxSupply) && status.statues.get(status.FLEET_BEACON_EXISTS) && status.statues.get(status.STARGATE_EXISTS) && findFree("Stargate")) {
            action.statuses.set(action.MAKE_CARRIER, true);
        } else {
            action.statuses.set(action.MAKE_CARRIER, false);
        }
    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void makeUnit(Units unit) {
        user.mineral -= unit.MINERAL_COST;
        setWorking(unit, unit.BUILT_FROM, unit.BUILD_TIME + 1);
        user.currentSupply += unit.SUPPLY_NEEDED;

    }

    /**
     * Check whether user can make units or buildings, or not
     */
    public void makeBuilding (Buildings buildings) {
        user.mineral -= buildings.MINERAL_COST;
        buildingsEvent.add(buildings);
        buildingsTimer.add(buildings.BUILD_TIME);
    }

    /**
     * Every second the programme will reduce the time for making units or buildings.
     * The build time for units is saved in the building object.
     * @param seconds   game time
     */
    public void reduceTimer(int seconds) {

        /*
          The build time for units is saved in the building object.
         */
        for (int i = 0; i < user.buildings.size(); i++) {
            //Check the buildings are working
            if (user.buildings.get(i).timer > 0) {
                user.buildings.get(i).timer--;
                if (user.buildings.get(i).timer == 0) {
                    //Add one unit
                    user.units.put(user.buildings.get(i).units, user.units.get(user.buildings.get(i).units) + 1);
                    printTime(user.buildings.get(i).units, seconds);
                    user.buildings.get(i).working = false;
                }
            }
        }

        for (int i = 0; i < buildingsTimer.size(); i++) {
            buildingsTimer.set(i, (buildingsTimer.get(i) - 1));
            if (buildingsTimer.get(i) == 0) {
                user.maxSupply += buildingsEvent.get(i).SUPPLY_PROVIDED;
                printTime(buildingsEvent.get(i), seconds);
                switch (buildingsEvent.get(i).NAME) {
                    case "Pylon":
                        user.buildings.add(new Buildings("Pylon", 100, 0, 25, 8));
                        status.statues.set(status.PYLON_EXISTS, true);
                        alreadyMaking = false;
                        break;
                    case "Gateway":
                        user.buildings.add(new Buildings("Gateway", 150, 0, 65, 0));
                        status.statues.set(status.GATEWAY_EXISTS, true);
                        break;
                    case "Assimilator":
                        user.buildings.add(new Buildings("Assimilator", 75, 0, 30, 0));
                        status.statues.set(status.ASSIMILATOR_EXISTS, true);
                        break;
                    case "Cybernetics Core":
                        user.buildings.add(new Buildings("Cybernetics Core",150, 0, 50, 0));
                        status.statues.set(status.CYBERNETICS_CORE_EXISTS, true);
                        break;
                    case "Robotics Facility":
                        user.buildings.add(new Buildings("Robotics Facility",200, 100, 65, 0));
                        status.statues.set(status.ROBOTICS_FACILITY_EXISTS, true);
                        break;
                    case "Stargate":
                        user.buildings.add(new Buildings("Stargate", 150,150,60,0));
                        status.statues.set(status.STARGATE_EXISTS, true);
                        break;
                    case "Forge":
                        user.buildings.add(new Buildings("Forge", 150, 0, 45, 0));
                        status.statues.set(status.FORGE_EXISTS, true);
                        break;
                    case "Twilight Council":
                        user.buildings.add(new Buildings("Twilight Council", 150, 100, 50, 0));
                        status.statues.set(status.TWILIGHT_COUNCIL_EXISTS, true);
                        break;
                    case "Templar Archives":
                        user.buildings.add(new Buildings("Templar Archives", 150,200,50,0));
                        status.statues.set(status.TEMPLAR_ARCHIVES_EXISTS, true);
                        break;
                    case "Dark Shrine":
                        user.buildings.add(new Buildings("Dark Shrine", 100, 250, 100, 0));
                        status.statues.set(status.DARK_SHRINE_EXISTS, true);
                        break;
                    case "Robotics Bay":
                        user.buildings.add(new Buildings("Robotics Bay", 200, 200, 65, 0));
                        status.statues.set(status.ROBOTICS_BAY_EXISTS, true);
                        break;
                    case "Fleet Beacon":
                        user.buildings.add(new Buildings("Fleet Beacon", 300,200, 60, 0));
                        status.statues.set(status.FLEET_BEACON_EXISTS, true);
                        break;
                }
                buildingsEvent.remove(i);
                buildingsTimer.remove(i);
            }
        }
    }

    /**
     * Choose random number based on action status
     * @return  the random number
     */
    public int random() {
        Random random = new Random();
        ArrayList<Integer> randomChoice = new ArrayList<>();
        for (int i = 0; i <action.statuses.size(); i++) {
            if (action.statuses.get(i)) {
                randomChoice.add(i);
            }
        }
        int index = random.nextInt(randomChoice.size());
        return randomChoice.get(index);
    }

    /**
     * Making units or buildings
     * @param action  action
     * @param seconds game time
     */
    public void doAction(int action, int seconds) {
        String temp = (seconds/60 + "\t:\t" + seconds % 60 + "\t");

        switch (action) {
            case 0:
                break;
            case 1:
                makeUnit(user.probe);
                resultParts.append(temp + " making Probe...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                break;
            case 2:
                if (alreadyMaking)
                    break;
                makeBuilding(user.pylon);
                resultParts.append(temp + " making Pylon...\n");
                alreadyMaking = true;
                break;
            case 3:
                makeBuilding(user.gateway);
                resultParts.append(temp + " making Gateway...\n");
                gatewayNum++;
                if (gatewayNum == fromGateway) // change to random value...
                    need.statuses.set(need.NEED_GATEWAY, false);
                break;
            case 4:
                makeUnit(user.zealot);
                resultParts.append(temp + " making Zealot...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.zealot);
                if (goalTest.get(user.zealot) == goal.get(user.zealot)) {
                    need.statuses.set(need.NEED_ZEALOT, false);
                }
                break;
            case 5:
                makeBuilding(user.assimilator);
                resultParts.append(temp + " making Assimilator...\n");
                user.assNum++;
                user.gasAss = true;
                break;
            case 6:
                makeUnit(user.stalker);
                resultParts.append(temp + " making Stalker...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.stalker);
                if (goalTest.get(user.stalker) == goal.get(user.stalker)) {
                    need.statuses.set(need.NEED_STALKER, false);
                }
                break;
            case 7:
                makeUnit(user.sentry);
                resultParts.append(temp + " making Sentry...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.sentry);
                if (goalTest.get(user.sentry) == goal.get(user.sentry)) {
                    need.statuses.set(need.NEED_SENTRY, false);
                }

                break;
            case 8:
                makeBuilding(user.cyberneticsCore);
                resultParts.append(temp + " making Cybernetics Core...\n");
                need.statuses.set(need.NEED_CYBERNETICS_CORE, false);
                break;
            case 9:
                makeBuilding(user.roboticsFacility);
                resultParts.append(temp + " making Robotics Facility...\n");
                roboticsNum++;
                if (roboticsNum == fromRobotics) // change to random value...\n
                    need.statuses.set(need.NEED_ROBOTICS_FACILITY, false);
                break;
            case 10:
                makeUnit(user.observer);
                resultParts.append(temp + " making Observer...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.observer);
                if (goalTest.get(user.observer) == goal.get(user.observer)) {
                    need.statuses.set(need.NEED_OBSERVER, false);
                }
                break;
            case 11:
                makeUnit(user.immortal);
                resultParts.append(temp + " making Immortal...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.immortal);
                if (goalTest.get(user.immortal) == goal.get(user.immortal)) {
                    need.statuses.set(need.NEED_IMMORTAL, false);
                }
                break;
            case 12:
                makeBuilding(user.stargate);
                resultParts.append(temp + " making Stargate...\n");
                stargateNum++;
                if (stargateNum == fromStargate) // change to random value...\n
                    need.statuses.set(need.NEED_STARGATE, false);
                break;
            case 13:
                makeUnit(user.phoenix);
                resultParts.append(temp + " making Phoenix...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.phoenix);
                if (goalTest.get(user.phoenix) == goal.get(user.phoenix)) {
                    need.statuses.set(need.NEED_PHOENIX, false);
                }
                break;
            case 14:
                makeUnit(user.voidRay);
                resultParts.append(temp + " making Void Ray...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.voidRay);
                if (goalTest.get(user.voidRay) == goal.get(user.voidRay)) {
                    need.statuses.set(need.NEED_VOID_RAY, false);
                }
                break;
            case 15:
                makeBuilding(user.forge);
                resultParts.append(temp + " making Forge...\n");
                break;
            case 16:
                makeBuilding(user.twilightCouncil);
                resultParts.append(temp + " making Twilight Council...\n");
                need.statuses.set(need.NEED_TWILIGHT_COUNCIL, false);
                break;
            case 17:
                makeBuilding(user.templarArchives);
                resultParts.append(temp + " making Templar Archives...\n");
                need.statuses.set(need.NEED_TEMPLAR_ARCHIVES, false);
                break;
            case 18:
                makeBuilding(user.darkShrine);
                resultParts.append(temp + " making Dark Shrine...\n");
                need.statuses.set(need.NEED_DARK_SHRINE, false);
                break;
            case 19:
                makeBuilding(user.roboticsBay);
                resultParts.append(temp + " making Robotics Bay...\n");
                need.statuses.set(need.NEED_ROBOTICS_BAY, false);
                break;
            case 20:
                makeBuilding(user.fleetBeacon);
                resultParts.append(temp + " making Fleet Beacon...\n");
                need.statuses.set(need.NEED_FLEET_BEACON, false);
                break;
            case 21:
                makeUnit(user.oracle);
                resultParts.append(temp + " making Oracle...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.oracle);
                if (goalTest.get(user.oracle) == goal.get(user.oracle)) {
                    need.statuses.set(need.NEED_ORACLE, false);
                }
                break;
            case 22:
                makeUnit(user.warpPrism);
                resultParts.append(temp + " making Warp Prism...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.warpPrism);
                if (goalTest.get(user.warpPrism) == goal.get(user.warpPrism)) {
                    need.statuses.set(need.NEED_WARP_PRISM, false);
                }
                break;
            case 23:
                makeUnit(user.colossus);
                resultParts.append(temp + " making Colossus...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.colossus);
                if (goalTest.get(user.colossus) == goal.get(user.colossus)) {
                    need.statuses.set(need.NEED_COLOSSUS, false);
                }
                break;
            case 24:
                makeUnit(user.tempest);
                resultParts.append(temp + " making Tempest...\n");
                goalTest(user.tempest);
                if (goalTest.get(user.tempest) == goal.get(user.tempest)) {
                    need.statuses.set(need.NEED_TEMPEST, false);
                }
                break;
            case 25:
                makeUnit(user.highTemplar);
                resultParts.append(temp + " making High Templar...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.highTemplar);
                if (goalTest.get(user.highTemplar) == goal.get(user.highTemplar)) {
                    need.statuses.set(need.NEED_HIGH_TEMPLAR, false);
                }
                break;
            case 26:
                makeUnit(user.darkTemplar);
                resultParts.append(temp + " making Dark Templar...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.darkTemplar);
                if (goalTest.get(user.darkTemplar) == goal.get(user.darkTemplar)) {
                    need.statuses.set(need.NEED_DARK_TEMPLAR, false);
                }
                break;
            case 27:
                makeUnit(user.carrier);
                resultParts.append(temp + " making Carrier...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                goalTest(user.carrier);
                if (goalTest.get(user.carrier) == goal.get(user.carrier)) {
                    need.statuses.set(need.NEED_CARRIER, false);
                }
                break;
        }
    }

    /**
     * Add a unit into goalTest hashmap
     * @param unit units
     */
    public void goalTest(Units unit) {
        if (goalTest.get(unit) == null) {
            goalTest.put(unit, 1);
        } else {
            goalTest.put(unit, goalTest.get(unit) + 1);
        }
    }

    /**
     * Setting a goal
     * @param target Units and number that user want to check
     */
    public void goal(HashMap<Units, Integer> target) {
        goal.putAll(target);
    }

    /**
     * Prints the end time for units
     * @param unit    unit
     * @param seconds game time
     */
    public void printTime(Units unit, int seconds) {
        String temp = (seconds/60 + "\t:\t" + seconds % 60 + "\t");

        switch (unit.NAME) {
            case "Probe":
                resultParts.append(temp + " Probe has been called...\n");
                break;
            case "Zealot":
                resultParts.append(temp + " Zealot has been called...\n");
                break;
            case "Stalker":
                resultParts.append(temp + " Stalker has been called...\n");
                break;
            case "Sentry":
                resultParts.append(temp + " Sentry has been called...\n");
                break;
            case "Observer":
                resultParts.append(temp + " Observer has been called...\n");
                break;
            case "Immortal":
                resultParts.append(temp + " Immortal has been called...\n");
                break;
            case "Phoenix":
                resultParts.append(temp + " Phoenix has been called...\n");
                break;
            case "Void Ray":
                resultParts.append(temp + " Void Ray has been called...\n");
                break;
            case "Oracle":
                resultParts.append(temp + " Oracle has been called...\n");
                break;
            case "Warp Prism":
                resultParts.append(temp + " Warp Prism has been called...\n");
                break;
            case "Colossus":
                resultParts.append(temp + " Colossus has been called...\n");
                break;
            case "Tempest":
                resultParts.append(temp + " Tempest has been called...\n");
                break;
            case "High Templar":
                resultParts.append(temp + " High Templar has been called...\n");
                break;
            case "Dark Templar":
                resultParts.append(temp + " Dark Templar has been called...\n");
                break;
            case "Carrier":
                resultParts.append(temp + " Carrier has been called...\n");
                break;
        }
    }

    /**
     * Prints the end time for buildings
     * @param building building
     * @param seconds  game time
     */
    public void printTime(Buildings building, int seconds) {
        String temp = (seconds/60 + "\t:\t" + seconds % 60 + "\t");
        switch (building.NAME) {
            case "Pylon":
                resultParts.append(temp + " Pylon has been built...\t" + user.currentSupply + " / " + user.maxSupply + "\n");
                break;
            case "Gateway":
                resultParts.append(temp + " Gateway has been built...\n");
                break;
            case "Assimilator":
                resultParts.append(temp + " Assimilator has been built...\n");
                break;
            case "Cybernetics Core":
                resultParts.append(temp + " Cybernetics Core has been built...\n");
                break;
            case "Robotics Facility":
                resultParts.append(temp + " Robotics Facility has been built...\n");
                break;
            case "Stargate":
                resultParts.append(temp + " Stargate has been built...\n");
                break;
            case "Forge":
                resultParts.append(temp + " Forge has been built...\n");
                break;
            case "Twilight Council":
                resultParts.append(temp + " Twilight Council has been built...\n");
                break;
            case "Templar Archives":
                resultParts.append(temp + " Templar Archives has been built...\n");
                break;
            case "Dark Shrine":
                resultParts.append(temp + " Dark Shrine has benn built...\n");
                break;
            case "Robotics Bay":
                resultParts.append(temp + " Robotics Bay has been built...\n");
                break;
            case "Fleet Beacon":
                resultParts.append(temp + " Fleet Beacon has been built...\n");
                break;
        }
    }

    /**
     * Add the number where the unit made from
     */
    public void unitsFrom() {
        for (Map.Entry<Units, Integer> goalUnit : goal.entrySet()) {
            switch (goalUnit.getKey().NAME) {
                case "Zealot":
                    fromGateway += goalUnit.getValue();
                    break;
                case "Stalker":
                    fromGateway += goalUnit.getValue();
                    break;
                case "Sentry":
                    fromGateway += goalUnit.getValue();
                    break;
                case "Observer":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromRobotics += goalUnit.getValue();
                    break;
                case "Immortal":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromRobotics += goalUnit.getValue();
                    break;
                case "Phoenix":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromStargate += goalUnit.getValue();
                    break;
                case "Void Ray":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromStargate += goalUnit.getValue();
                    break;
                case "Oracle":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromStargate += goalUnit.getValue();
                    break;
                case "Warp Prism":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromRobotics += goalUnit.getValue();
                    break;
                case "Colossus":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromRobotics += goalUnit.getValue();
                    break;
                case "Tempest":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromStargate += goalUnit.getValue();
                    break;
                case "High Templar":
                    fromGateway += goalUnit.getValue();
                    break;
                case "Dark Templar":
                    fromGateway += goalUnit.getValue();
                    break;
                case "Carrier":
                    if (fromGateway == 0) {
                        fromGateway++;
                    }
                    fromStargate += goalUnit.getValue();
                    break;
            }
        }
    }

    /**
     * Check the user hashmap is same as gaol hashmap
     * @return result
     */
    public boolean goalCheck() {
        boolean same = false;

        ArrayList<Boolean> list = new ArrayList<>();

        for (Map.Entry<Units, Integer> userUnit : user.units.entrySet()) {
            String unit = userUnit.getKey().NAME;
            int num = userUnit.getValue();
            for (Map.Entry<Units, Integer> goalUnit : goal.entrySet()) {
                String targetUnit = goalUnit.getKey().NAME;
                int targetNum = goalUnit.getValue();
                if (unit.equals(targetUnit) && num >= targetNum) {
                    list.add(true);
                }
            }
        }
//
        if (list.size() == goal.size()) {
            for (boolean ok : list) {
                if (!ok)
                    break;
            }
            same = true;
        }

        return same;
    }

    /**
     * After one simulation, the programme has to reset the statuses
     */
    public void refresh() {
        user = new User();
        status.statues.clear();
        status = new Status();
        need = new Need();
        buildingsEvent.clear();
        buildingsTimer.clear();
        gatewayNum = 0;
        roboticsNum = 0;
        stargateNum = 0;
        fromGateway = 0;
        fromRobotics = 0;
        fromStargate = 0;
        alreadyMaking = false;
    }

    /**
     * If building is making unit, building.working change to true
     * @param unit      unit
     * @param building  building
     * @param buildTime build time
     */
    public void setWorking(Units unit, String building, int buildTime) {
        for (int i = 0; i < user.buildings.size(); i++) {
            if (user.buildings.get(i).NAME.equals(building)) {
                if (!user.buildings.get(i).working) {
                    user.buildings.get(i).working = true;
                    user.buildings.get(i).timer = buildTime;
                    user.buildings.get(i).units = unit;
                    break;
                }
            }
        }
    }

    /**
     * Find the building which is free (Is not working)
     * @param building building
     * @return true if there is, otherwise false
     */
    public boolean findFree(String building) {
        for (int i = 0; i < user.buildings.size(); i++) {
            if (user.buildings.get(i).NAME.equals(building)) {
                if (!user.buildings.get(i).working) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * It forces programme to do not make useless building.
     */
    public void efficiency() {
        for (Units unit : goal.keySet()) {
            switch (unit.NAME) {
                case "Probe":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    break;
                case "Zealot":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ZEALOT , true);
                    break;
                case "Stalker":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STALKER , true);
                    break;
                case "Sentry":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_SENTRY , true);
                    break;
                case "Observer":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_ROBOTICS_FACILITY , true);
                    need.statuses.set(need.NEED_OBSERVER , true);
                    break;
                case "Immortal":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_ROBOTICS_FACILITY , true);
                    need.statuses.set(need.NEED_IMMORTAL , true);
                    break;
                case "Phoenix":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STARGATE , true);
                    need.statuses.set(need.NEED_PHOENIX , true);
                    break;
                case "Void Ray":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STARGATE , true);
                    need.statuses.set(need.NEED_VOID_RAY , true);
                    break;
                case "Oracle":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STARGATE , true);
                    need.statuses.set(need.NEED_ORACLE , true);
                    break;
                case "Warp Prism":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_ROBOTICS_FACILITY , true);
                    need.statuses.set(need.NEED_WARP_PRISM , true);
                    break;
                case "Colossus":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_ROBOTICS_FACILITY , true);
                    need.statuses.set(need.NEED_ROBOTICS_BAY , true);
                    need.statuses.set(need.NEED_COLOSSUS , true);
                    break;
                case "Tempest":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STARGATE , true);
                    need.statuses.set(need.NEED_FLEET_BEACON , true);
                    need.statuses.set(need.NEED_TEMPEST , true);
                    break;
                case "High Templar":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_TWILIGHT_COUNCIL , true);
                    need.statuses.set(need.NEED_TEMPLAR_ARCHIVES , true);
                    need.statuses.set(need.NEED_HIGH_TEMPLAR , true);
                    break;
                case "Dark Templar":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_TWILIGHT_COUNCIL , true);
                    need.statuses.set(need.NEED_DARK_SHRINE , true);
                    need.statuses.set(need.NEED_DARK_TEMPLAR , true);
                    break;
                case "Carrier":
                    need.statuses.set(need.NEED_PROBE, true);
                    need.statuses.set(need.NEED_PYLON, true);
                    need.statuses.set(need.NEED_GATEWAY , true);
                    need.statuses.set(need.NEED_ASSIMILATOR, true);
                    need.statuses.set(need.NEED_CYBERNETICS_CORE , true);
                    need.statuses.set(need.NEED_STARGATE , true);
                    need.statuses.set(need.NEED_FLEET_BEACON , true);
                    need.statuses.set(need.NEED_CARRIER , true);
                    break;
            }
        }
    }

    /**
     * This is just for recap
     */
    public void able() {
        ableProbe();
        ablePylon();
        ableGateway();
        ableZealot();
        ableAssimilator();
        ableStalker();
        ableSentry();
        ableCyberneticsCore();
        ableRoboticsFacility();
        ableObserver();
        ableImmortal();
        ableStargate();
        ablePhoenix();
        ableVoidRay();
        ableForge();
        ableTwilightCouncil();
        ableTemplarArchives();
        ableDarkShrine();
        ableRoboticsBay();
        ableFleetBeacon();
        ableOracle();
        ableWarpPrism();
        ableColossus();
        ableTempest();
        ableHighTemplar();
        ableDarkTemplar();
        ableCarrier();
    }

    /**
     * For the testing
     * @param args the user arguments
     * @return String array
     */
    public String[] setTest(String[] args) {
        switch(args[0]) {
            case "test1":
                args = new String[10];
                args[0] = "zealot";
                args[1] = "1";
                args[2] = "stalker";
                args[3] = "4";
                args[4] = "immortal";
                args[5] = "2";
                args[6] = "colossus";
                args[7] = "2";
                args[8] = "50";
                args[9] = "100";
                break;
            case "test2":
                args = new String[10];
                args[0] = "zealot";
                args[1] = "6";
                args[2] = "stalker";
                args[3] = "2";
                args[4] = "sentry";
                args[5] = "3";
                args[6] = "void_ray";
                args[7] = "4";
                args[8] = "50";
                args[9] = "100";
                break;
            case "test3":
                args = new String[12];
                args[0] = "zealot";
                args[1] = "2";
                args[2] = "stalker";
                args[3] = "2";
                args[4] = "sentry";
                args[5] = "1";
                args[6] = "colossus";
                args[7] = "2";
                args[8] = "phoenix";
                args[9] = "5";
                args[10] = "50";
                args[11] = "100";
                break;
            case "test4":
                args = new String[10];
                args[0] = "zealot";
                args[1] = "10";
                args[2] = "stalker";
                args[3] = "7";
                args[4] = "sentry";
                args[5] = "2";
                args[6] = "high_templar";
                args[7] = "3";
                args[8] = "50";
                args[9] = "100";
                break;
            case "test5":
                args = new String[16];
                args[0] = "zealot";
                args[1] = "8";
                args[2] = "stalker";
                args[3] = "10";
                args[4] = "sentry";
                args[5] = "2";
                args[6] = "immortal";
                args[7] = "1";
                args[8] = "observer";
                args[9] = "1";
                args[10] = "carrier";
                args[11] = "3";
                args[12] = "dark_templar";
                args[13] = "2";
                args[14] = "50";
                args[15] = "100";
                break;
        }
        return args;
    }


    public static void main(String[] args) {
        try {
            Play play = new Play();
            if (args.length == 1) {
                args = play.setTest(args);
            }

            int seconds = 0;
            int times = 0;

            HashMap<Units, Integer> test = new HashMap<>();

            int maxProbe = Integer.parseInt(args[args.length - 2]);
            int simulations = Integer.parseInt(args[args.length - 1]);

            //Simulation Start!!!
            while (probeNum < maxProbe) {
                while (times < simulations) {
                    for (int i = 0; i < args.length - 2; i++) {
                        if (i % 2 == 0) {
                            switch (args[i]) {
                                case "probe":
                                    test.put(play.user.probe, Integer.parseInt(args[i + 1]));
                                    break;
                                case "zealot":
                                    test.put(play.user.zealot, Integer.parseInt(args[i + 1]));
                                    break;
                                case "stalker":
                                    test.put(play.user.stalker, Integer.parseInt(args[i + 1]));
                                    break;
                                case "sentry":
                                    test.put(play.user.sentry, Integer.parseInt(args[i + 1]));
                                    break;
                                case "observer":
                                    test.put(play.user.observer, Integer.parseInt(args[i + 1]));
                                    break;
                                case "immortal":
                                    test.put(play.user.immortal, Integer.parseInt(args[i + 1]));
                                    break;
                                case "phoenix":
                                    test.put(play.user.phoenix, Integer.parseInt(args[i + 1]));
                                    break;
                                case "void_ray":
                                    test.put(play.user.voidRay, Integer.parseInt(args[i + 1]));
                                    break;
                                case "oracle":
                                    test.put(play.user.oracle, Integer.parseInt(args[i + 1]));
                                    break;
                                case "warp_prism":
                                    test.put(play.user.warpPrism, Integer.parseInt(args[i + 1]));
                                    break;
                                case "colossus":
                                    test.put(play.user.colossus, Integer.parseInt(args[i + 1]));
                                    break;
                                case "tempest":
                                    test.put(play.user.tempest, Integer.parseInt(args[i + 1]));
                                    break;
                                case "high_templar":
                                    test.put(play.user.highTemplar, Integer.parseInt(args[i + 1]));
                                    break;
                                case "dark_templar":
                                    test.put(play.user.darkTemplar, Integer.parseInt(args[i + 1]));
                                    break;
                                case "carrier":
                                    test.put(play.user.carrier, Integer.parseInt(args[i + 1]));
                                    break;
                                default:
                                    System.out.println("Wrong Units Name");
                                    System.exit(1);
                                    break;
                            }
                        }
                    }

                    play.goal(test);
                    play.unitsFrom();
                    play.efficiency();

                    while (!play.goalCheck()) {
                        play.user.addResources();
                        play.able();
                        play.doAction(play.random(), seconds);

                        play.reduceTimer(seconds);

                        seconds++;
                    }
                    System.out.println(times + 1 + " Simulation Done for " + probeNum + " of probes");
                    play.resultString.add(play.resultParts.toString());
                    play.resultParts.delete(0, play.resultParts.length());
                    play.result.add(seconds - 1);
                    test.clear();
                    play.goal.clear();

                    play.refresh();

                    seconds = 0;
                    times++;
                }
                times = 0;
                play.probeNum++;
            }
            System.out.println(play.result.indexOf(Collections.min(play.result)));
            System.out.println(play.resultString.get(play.result.indexOf(Collections.min(play.result))));
        } catch (NumberFormatException e) {
            System.out.println("Usage : <Unit_Name> <Number_Of_Units>... <Maximum number of probe> <Maximum number of simulations>");
        }
    }
}
