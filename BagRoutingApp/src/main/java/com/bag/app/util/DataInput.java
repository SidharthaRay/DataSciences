package com.bag.app.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bag.app.model.Bag;
import com.bag.app.model.Departure;
import com.bag.app.routing.ConveyorSystem;

public class DataInput {
    private static List<String> conveyorSystemList = new ArrayList<String>();
    private static List<String> departureList = new ArrayList<String>();
    private static List<String> bagList = new ArrayList<String>();

    static {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "src\\main\\resources\\input.txt"));
            String line = null;
            String indicator = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().contains("# Section")) {
                    if (line.contains("Conveyor System")) {
                        indicator = "Conveyor System";
                        continue;
                    } else if (line.contains("Departures")) {
                        indicator = "Departures";
                        continue;
                    } else if (line.contains("Bags")) {
                        indicator = "Bags";
                        continue;
                    }
                }
                if (indicator.equals("Conveyor System")) {
                    conveyorSystemList.add(line);
                } else if (indicator.equals("Departures")) {
                    departureList.add(line);
                } else if (indicator.equals("Bags")) {
                    bagList.add(line);
                }
            }
            // System.out.println(conveyorSystemList);
            // System.out.println(departureList);
            // System.out.println(bagList);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConveyorSystem[] dataInitConveyorSystem() {
        ConveyorSystem[] conveyorSystems = new ConveyorSystem[conveyorSystemList.size()];
        int conveyorSystemCount = 0;
        for (String str : conveyorSystemList) {
            ConveyorSystem conveyorSystem = new ConveyorSystem(str);
            conveyorSystems[conveyorSystemCount++] = conveyorSystem;
            // conveyorSystem.show();
        }
        return conveyorSystems;
    }

    public static Departure[] dataInitDeparture() {
        Departure[] departures = new Departure[departureList.size()];
        int departureCount = 0;
        for (String str : departureList) {
            Departure departure = new Departure(str);
            departures[departureCount++] = departure;
            // departure.show();
        }
        return departures;
    }

    public static Bag[] dataInitBag() {
        Bag[] bags = new Bag[bagList.size()];
        int bagCount = 0;
        for (String str : bagList) {
            Bag bag = new Bag(str);
            bags[bagCount++] = bag;
            // bag.show();
        }
        return bags;
    }
}
