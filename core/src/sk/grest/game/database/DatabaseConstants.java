package sk.grest.game.database;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "interstellar_mining";

    public static final class PlayerTable {
        public static final String TABLE_NAME = "player";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String LEVEL = "level";
        public static final String EXPERIENCE = "experience";
    }

    public static final class LoginHistoryTable {
        public static final String TABLE_NAME = "login_history";
        public static final String ID = "id";
        public static final String PLAYER_ID = "player_id";
        public static final String LOGGED_IN = "logged_in";
        public static final String LOGGED_OUT = "logged_out";
    }

    public static final class PlanetTable {
        public static final String TABLE_NAME = "planet";
        public static final String ID = "id";
        public static final String PLANET_SYSTEM_ID = "planet_system_id";
        public static final String UNLOCKED = "unlocked";
    }

    public static final class PlanetSystemTable {
        public static final String TABLE_NAME = "planet_system";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String UNLOCK_LVL = "unlock_lvl";
    }

    public static final class PlanetResourceTable {
        public static final String TABLE_NAME = "planet_resource";
        public static final String ID = "id";
        public static final String RESOURCE_ID = "resource_id";
        public static final String PLANET_ID = "planet_id";
        public static final String PERCENTAGE_MINED = "percentage_mined";
        public static final String AMOUNT = "amount";
    }

    public static final class DiscoveredSystemsTable {
        public static final String TABLE_NAME = "discovered_systems";
        public static final String ID = "id";
        public static final String DISCOVERED_SYSTEMS_TABLE = "planet_system_id";
        public static final String UNLOCKED = "unlocked";
    }

    public static final class ResourceTable {
        public static final String TABLE_NAME = "resource";
        public static final String ID = "id";
        public static final String STATE_ID = "state_id";
        public static final String RARITY_ID = "rarity_id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
    }

    public static final class StateTable {
        public static final String STATE_TABLE = "state";
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static final class Rarity {
        public static final String STATE_TABLE = "rarity";
        public static final String ID = "id";
        public static final String NAME = "name";
    }



    public static final String SHIP_TABLE = "ship";
    public static final String COLONY_TABLE = "colony";
    public static final String SHIP_FLEET_TABLE = "ship_fleet";
    public static final String RESOURCE_IN_SHIP_TABLE = "resource_in_ship";

    public static final String RESEARCH_TABLE = "ship";
    public static final String RESEARCH_TREE_TABLE = "ship";
    public static final String RESEARCH_REQUIREMENT = "research_requirement";
}
