package airline.aircraft;

import java.util.HashMap;

import cargo.Cargo;

public abstract class CargoAircraft extends Aircraft implements Aircraft, Cargo{
    HashMap<Integer, Cargo> cargos;

    public boolean isEmpty() {
        return cargos.isEmpty();
    }

    public void printContents() {
        for (Cargo cargo : cargos.values()) {
            System.out.println(cargo.toString());
        }
    }

    public boolean isFull() {
        return false;
    }

    
}
