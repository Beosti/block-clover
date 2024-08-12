package com.yuanno.blockclover.data.entity;

public interface IEntityStats {
    void setMiscData(MiscData miscData);
    MiscData getMiscData();

    void setCombatData(CombatData combatData);
    CombatData getCombatData();

    void setMagicData(MagicData magicData);
    MagicData getMagicData();
}
