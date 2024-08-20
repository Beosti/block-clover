package com.yuanno.blockclover.init;

import java.util.ArrayList;

public class ModValues {

    // ATTRIBUTES
    public static final String FIRE = "Fire";
    public static final String WATER = "Water";
    public static final String EARTH = "Earth";
    public static final String WIND = "Wind";
    public static final String LIGHTNING = "Lightning";

    public static ArrayList<String> attributes = new ArrayList<String>();
    static {
        attributes.add(FIRE);
        attributes.add(WATER);
        attributes.add(EARTH);
        attributes.add(WIND);
        attributes.add(LIGHTNING);
    }

    // RACES
    public static final String HUMAN = "Human";
    public static final String ELF = "Elf";
    public static final String DWARF = "Dwarf";
    public static final String HYBRID = "Hybrid";

    public static ArrayList<String> races = new ArrayList<String>();
    static {
        races.add(HUMAN);
        races.add(ELF);
        races.add(DWARF);
        races.add(HYBRID);
    }
}
