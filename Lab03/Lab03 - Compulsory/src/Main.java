import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Airliner A1 = new Airliner("A1", "Alb", "150", 70, 120);
        Freighter F1 = new Freighter("F1", "Gri", "160", 400);
        Drone D1 = new Drone("D1", "Negru", "30", 100, 10);
        Freighter F2 = new Freighter("F2", "Gri", "160", 400);

        Aircraft[] cargoCarriers = new Aircraft[10];
        cargoCarriers[1] = F1;
        cargoCarriers[2] = D1;
        cargoCarriers[3] = F2;

        for(Aircraft carrier : cargoCarriers) {
            if(carrier != null) {
            System.out.println(carrier.toString());
            }

            if(carrier instanceof CargoCapable){
                CargoCapable cargoCapable = (CargoCapable) carrier;
                System.out.println("Cargo capable: " + cargoCapable.getCargoCapacity() + "kg");
            }
        }
    }
}

class Aircraft {
    private String name;
    private String model;
    private String tailNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", tailNumber='" + tailNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aircraft aircraft)) return false;
        return Objects.equals(getName(), aircraft.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}

class Airliner extends Aircraft implements PassagerCapable {
    private int wingSpan;
    private int passengerCapacity;

    public Airliner(String name, String model, String tailNumber,int wingSpan, int passengerCapacity) {
        this.wingSpan = wingSpan;
        this.passengerCapacity = passengerCapacity;
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
    }

    public int getWingSpan() {
        return wingSpan;
    }

    public void setWingSpan(int wingSpan) {
        this.wingSpan = wingSpan;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public int getPassagerCapacity() {
        return passengerCapacity;
    }
}

class Freighter extends Aircraft implements CargoCapable {
    private int payload;

    public Freighter(String name, String model, String tailNumber, int payload) {
        this.payload = payload;
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    @Override
    public int getCargoCapacity() {
        return payload;
    }
}

class Drone extends Aircraft implements CargoCapable {
    private int batteryLife;
    private int carryCapacity;

    public Drone(String name, String model, String tailNumber, int batteryLife, int carryCapacity) {
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
        this.batteryLife = batteryLife;
        this.carryCapacity = carryCapacity;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public int getCarryCapacity() {
        return carryCapacity;
    }

    public void setCarryCapacity(int carryCapacity) {
        this.carryCapacity = carryCapacity;
    }

    @Override
    public int getCargoCapacity() {
        return carryCapacity;
    }
}