package airline.aircraft;

import java.util.HashMap;

import cargo.Cargo;

public abstract class CargoAircraft extends Aircraft implements Aircraft, Cargo{
    HashMap<Integer, Cargo> cargos;

    boolean isEmpty() {
        return cargos.isEmpty();
    }

    void printContents() {
        for (Cargo cargo : cargos.values()) {
            System.out.println(cargo.toString());
        }
    }
    
}
