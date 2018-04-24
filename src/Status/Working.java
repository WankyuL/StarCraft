package Status;

import java.util.ArrayList;

public class Working {
    public static final int NEXUS = 0;

    public static ArrayList<Boolean> working = new ArrayList<>();

    public Working() {
        working.add(NEXUS, false);
    }
}
