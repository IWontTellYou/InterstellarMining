package sk.grest.game.database;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "interstellar_mining";

    public static final String PLAYER_ID = "player_id";

    public static final class AchievementTable {
        public static final String TABLE_NAME = "achievement";
        public static final String ID = "id";
    }

    public static final class PlayerAchievementTable {
        public static final String TABLE_NAME = "player_achievement";
        public static final String ID = "id";
        public static final String PLAYER_ID = DatabaseConstants.PLAYER_ID;
    }

    public static final class PlayerTable {
        public static final String TABLE_NAME = "player";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String LEVEL = "level";
        public static final String EXPERIENCE = "experience";
        public static final String MONEY = "money";
    }

    public static final class PlayerLoginHistoryTable {
        public static final String TABLE_NAME = "player_login_history";
        public static final String ID = "id";
        public static final String PLAYER_ID = DatabaseConstants.PLAYER_ID;
        public static final String LOGGED_IN = "logged_in";
        public static final String LOGGED_OUT = "logged_out";
    }

    public static final class PlanetTable {
        public static final String TABLE_NAME = "planet";
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String PLANET_SYSTEM_ID = "planet_system_id";
        public static final String SIZE = "size";
        public static final String INFO = "info";
        public static final String DISTANCE = "distance";
        public static final String HABITABLE = "habitable";
        public static final String UNLOCKED = "unlocked";
        public static final String ASSET_ID = "asset_id";
    }

    public static final class PlanetSystemTable {
        public static final String TABLE_NAME = "planet_system";
        public static final String ID = "id";
        public static final String STAR_NAME = "star_name";
        public static final String NAME = "name";
        public static final String UNLOCK_LVL = "unlock_lvl";
    }

    public static final class PlanetResourceTable {
        public static final String TABLE_NAME = "planet_resource";
        public static final String ID = "id";
        public static final String RESOURCE_ID = "resource_id";
        public static final String PLANET_ID = "planet_id";
        public static final String PERCENTAGE_MINED = "percentage_mined";
    }

    public static final class PlayerPlanetSystemTable {
        public static final String TABLE_NAME = "player_planet_system";
        public static final String ID = "id";
        public static final String PLANET_SYSTEM_ID = "planet_system_id";
        public static final String PLAYER_ID = DatabaseConstants.PLAYER_ID;
        public static final String UNLOCKED = "unlocked";
    }

    public static final class ResourceTable {
        public static final String TABLE_NAME = "resource";
        public static final String ID = "id";
        public static final String STATE = "state";
        public static final String RARITY = "rarity";
        public static final String NAME = "name";
        public static final String PRICE = "price";
    }

    public static final class ShipTable{
        public static final String TABLE_NAME = "ship";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MINING_SPEED = "mining_speed";
        public static final String TRAVEL_SPEED = "travel_speed";
        public static final String RESOURCE_CAPACITY = "resource_capacity";
        public static final String FUEL_CAPACITY = "fuel_capacity";
        public static final String FUEL_EFFICIENCY = "fuel_efficiency";
        public static final String PRICE = "price";
    }

    public static final class PlayerShipTable{
        public static final String TABLE_NAME = "player_ship";
        public static final String ID = "id";
        public static final String PLAYER_ID = DatabaseConstants.PLAYER_ID;
        public static final String SHIP_ID = "ship_id";
        public static final String RESOURCE_ID = "resource_id";
        public static final String UPGRADE_LEVEL = "upgrade_level";
        public static final String TASK_TIME = "task_time";
        public static final String DESTINATION_ID = "destination_id";
        public static final String STATE = "state";
        public static final String AMOUNT = "amount";
        public static final String TRAVEL_SPEED_LVL = "travel_speed_lvl";
        public static final String MINING_SPEED_LVL = "mining_speed_lvl";
        public static final String RESOURCE_CAPACITY_LVL = "resource_capacity_lvl";
        public static final String FUEL_CAPACITY_LVL = "fuel_capacity_lvl";
        public static final String FUEL_EFFICIENCY_LVL = "fuel_efficiency_lvl";
    }

    /*
    public static final class ResourceInShipTable{
        public static final String TABLE_NAME = "resource_in_ship";
        public static final String ID = "id";
        public static final String SHIP_ID = "ship_fleet_id";
        public static final String RESOURCE_ID = "resource_id";
        public static final String AMOUNT = "amount";
    }
    */

    public static final class PlayerResourceTable{
        public static final String TABLE_NAME = "player_resource";
        public static final String ID = "id";
        public static final String PLAYER_ID = DatabaseConstants.PLAYER_ID;
        public static final String RESOURCE_ID = "resource_id";
        public static final String AMOUNT = "amount";
    }

    public static final class ResearchTable{
        public static final String TABLE_NAME = "research";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String INFO = "info";
        public static final String EFFECT = "effect";
        public static final String UPGRADE_TIME = "upgrade_time";
        public static final String PRICE = "research_price";
    }

    public static final class ResearchRequirementTable{
        public static final String TABLE_NAME = "research_requirement";
        public static final String ID = "id";
        public static final String RESEARCH_ID = "research_id";
        public static final String RESEARCH_REQUIRED_ID = "research_required_id";
    }

    public static final class PlayerResearchTable{
        public static final String TABLE_NAME = "player_research";
        public static final String ID = "id";
        public static final String PLAYER_ID = "player_id";
        public static final String RESEARCH_ID = "research_id";
        public static final String LEVEL = "research_level";
        public static final String UNLOCKED = "unlocked";
    }
}
