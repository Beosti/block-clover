package com.yuanno.blockclover.api.spells;

import java.util.HashMap;

public class SpellComponent {

    private HashMap<Integer, String> upgradeMap = new HashMap<>();

    public void addToUpgradeMap(int level, String upgrade)
    {
        this.upgradeMap.put(level, upgrade);
    }

    public HashMap<Integer, String> getUpgradeMap() {
        return upgradeMap;
    }
}
