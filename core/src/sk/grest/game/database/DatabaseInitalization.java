package sk.grest.game.database;

public class DatabaseInitalization {

    private boolean isPlayerTable;
    private boolean isShipTable;
    private boolean isPlanetSystemTable;
    private boolean isPlanetTable;
    private boolean isResourcesTable;
    private boolean isDiscoveredSystemsTable;
    private boolean isShipFleetTable;
    private boolean isPlanetResourceTable;

    public DatabaseInitalization() {
        isPlayerTable = false;
        isShipTable = false;
        isPlanetSystemTable = false;
        isPlanetTable = false;
        isResourcesTable = false;
        isDiscoveredSystemsTable = false;
        isShipFleetTable = false;
        isPlanetResourceTable = false;
    }

    public boolean isPlayerTable() {
        return isPlayerTable;
    }
    public boolean isShipTable() {
        return isShipTable;
    }
    public boolean isPlanetSystemTable() {
        return isPlanetSystemTable;
    }
    public boolean isPlanetTable() {
        return isPlanetTable;
    }
    public boolean isResourcesTable() {
        return isResourcesTable;
    }
    public boolean isDiscoveredSystemsTable() {
        return isDiscoveredSystemsTable;
    }
    public boolean isShipFleetTable() {
        return isShipFleetTable;
    }
    public boolean isPlanetResourceTable() {
        return isPlanetResourceTable;
    }

    public void setPlayerTable() { isPlayerTable = true; }
    public void setShipTable() {
        isShipTable = true;
    }
    public void setPlanetSystemTable() {
        isPlanetSystemTable = true;
    }
    public void setPlanetTable() {
        isPlanetTable = true;
    }
    public void setResourcesTable() {
        isResourcesTable = true;
    }
    public void setDiscoveredSystemsTable() {
        isDiscoveredSystemsTable = true;
    }
    public void setShipFleetTable() {
        isShipFleetTable = true;
    }
    public void setPlanetResourceTable() {
        isPlanetResourceTable = true;
    }

    public boolean isDatabaseInitialized(){
        return isShipFleetTable && isDiscoveredSystemsTable && isPlanetTable && isPlanetResourceTable
                && isPlanetSystemTable && isShipTable && isResourcesTable && isPlayerTable;
    }
}
