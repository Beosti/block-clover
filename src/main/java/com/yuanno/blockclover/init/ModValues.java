package com.yuanno.blockclover.init;

import java.util.ArrayList;

public class ModValues {

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
