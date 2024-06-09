package com.yuanno.blockclover.data.entity;

public class EntityStatsBase implements IEntityStats {
    private MiscData miscData;
    private CombatData combatData;

    @Override
    public void setMiscData(MiscData miscData)
    {
        this.miscData = miscData;
    }
    @Override
    public MiscData getMiscData()
    {
        return this.miscData;
    }

    @Override
    public void setCombatData(CombatData combatData)
    {
        this.combatData = combatData;
    }

    @Override
    public CombatData getCombatData()
    {
        return this.combatData;
    }

}
