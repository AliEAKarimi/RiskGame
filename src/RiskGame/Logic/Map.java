package RiskGame.Logic;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    private List<Continent> continents;
    private List<Land> lands;
    private List<Ocean> oceans;
    private List sortedMap;

    public Map() {
        createContinents();
    }

    private void createContinents() {

        //map
        sortedMap = new ArrayList();
        for(int i = 0 ; i < 42 ; i ++)
            sortedMap.add(null);

        //American continent
        List<Land> americanLands = new ArrayList<>();
        americanLands.add(new Land(1, "Canada"));americanLands.add( new Land(2, "United States"));
        americanLands.add(new Land(7,"Mexico"));americanLands.add(new Land(8, "Cuba"));
        americanLands.add(new Land(13, "Colombia"));americanLands.add(new Land(19, "Peru"));
        americanLands.add(new Land(25, "Brazil"));americanLands.add(new Land(31, "Chile"));americanLands.add(new Land(32, "Argentina"));
        Continent america = new Continent("America", 3, americanLands);

        //Europe Continent
        List<Land> europeLands = new ArrayList<>();
        europeLands.add(new Land(3, "Denmark"));europeLands.add(new Land(4, "Poland"));europeLands.add(new Land(9, "Belgium"));
        europeLands.add(new Land(10, "Germany"));europeLands.add(new Land(15, "France"));europeLands.add(new Land(16,"Italy"));
        Continent europe = new Continent("Europe", 4, europeLands);

        //Asia continent
        List<Land> asiaLands = new ArrayList<>();
        asiaLands.add(new Land(5, "China"));asiaLands.add(new Land(6, "North Korea"));
        asiaLands.add(new Land(11, "Turkey"));asiaLands.add(new Land(12, "Iran"));asiaLands.add(new Land(17, "Iraq"));
        asiaLands.add(new Land(18, "Pakistan"));asiaLands.add(new Land(23, "India"));asiaLands.add(new Land(24, "Japan"));
        Continent asia = new Continent("Asia", 4, asiaLands);

        //Africa continent
        List<Land> africaLands = new ArrayList<>();
        africaLands.add(new Land(22, "Egypt"));africaLands.add(new Land(27, "Mali"));africaLands.add(new Land(28, "Niger"));
        africaLands.add(new Land(33, "Sudan"));africaLands.add(new Land(34, "Kenya"));africaLands.add(new Land(39, "Zambia"));
        Continent africa = new Continent("Africa", 2, africaLands);

        //Ocean
        oceans = new ArrayList<>();
        oceans.add(new Ocean(14, "North Atlantic Ocean"));oceans.add(new Ocean(20, "North Atlantic Ocean"));
        oceans.add(new Ocean(21, "North Atlantic Ocean"));oceans.add(new Ocean(26, "North Atlantic Ocean"));
        oceans.add(new Ocean(29, "Indian Ocean"));oceans.add(new Ocean(30, "Indian Ocean"));
        oceans.add(new Ocean(35, "Indian Ocean"));oceans.add(new Ocean(36, "Indian Ocean"));
        oceans.add(new Ocean(37, "South Atlantic Ocean"));oceans.add(new Ocean(38, "South Atlantic Ocean"));
        oceans.add(new Ocean(40, "Indian Ocean"));oceans.add(new Ocean(41, "Indian Ocean"));
        oceans.add(new Ocean(42, "Indian Ocean"));


        continents = new ArrayList<>();
        continents.add(america);continents.add(europe);continents.add(asia);continents.add(africa);
        lands = new ArrayList<>();

        for (Continent continent : continents)
            for(Land land : continent.getLands()) {
                lands.add(land);
                sortedMap.set(land.getID() - 1, land);  //add lands with an arrange to the map
            }

        for(Ocean ocean : oceans) //add ocean with an arrange to the map
            sortedMap.set(ocean.getID() - 1, ocean);

        int[] landsType1 = {7, 8, 9, 10, 14, 15, 16, 21, 22, 26, 27, 31, 32, 33}; //the lands that has 4 abutting.
        int[] landsType2 = {1, 2, 3, 4};
        int[] landsType3 = {11, 17, 23};
        int[] landsType4 = {38};
        int[] landsType5 = {6, 12, 18, 24, 30};
        int[] landsType6 = {0, 5};

        for (int i = 0 ; i < 42 ; i++) {
            if(sortedMap.get(i) instanceof Land) {
                Land land = (Land) sortedMap.get(i);
                if (i == 0) {
                    land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    land.addAbuttingLand((Land) sortedMap.get(i + 6));
                } else if (i == 5) {
                    land.addAbuttingLand((Land) sortedMap.get(i - 1));
                    land.addAbuttingLand((Land) sortedMap.get(i + 6));
                } else if (Arrays.binarySearch(landsType1, i) >= 0) {
                    if (sortedMap.get(i + 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    if (sortedMap.get(i - 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 1));
                    if (sortedMap.get(i + 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 6));
                    if (sortedMap.get(i - 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 6));
                } else if (Arrays.binarySearch(landsType2, i) >= 0) {
                    if (sortedMap.get(i + 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    if (sortedMap.get(i - 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 1));
                    if (sortedMap.get(i + 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 6));
                } else if (Arrays.binarySearch(landsType3, i) >= 0) {
                    if (sortedMap.get(i - 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    if (sortedMap.get(i - 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 6));
                    if (sortedMap.get(i + 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 6));
                } else if (Arrays.binarySearch(landsType4, i) >= 0) {
                    if (sortedMap.get(i + 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    if (sortedMap.get(i - 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 1));
                    if (sortedMap.get(i - 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 6));
                } else if (Arrays.binarySearch(landsType5, i) >= 0) {
                    if (sortedMap.get(i + 1) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 1));
                    if (sortedMap.get(i - 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i - 6));
                    if (sortedMap.get(i + 6) instanceof Land) land.addAbuttingLand((Land) sortedMap.get(i + 6));
                }
            }
        }
    }


    public List<Continent> getContinents() {
        return continents;
    }

    public List<Ocean> getOceans() {
        return oceans;
    }
    public List getSortedMap() {
        return sortedMap;
    }
    public List<Land> getLands() {
        return lands;
    }

}
