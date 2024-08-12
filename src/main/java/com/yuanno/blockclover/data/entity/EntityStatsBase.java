package com.yuanno.blockclover.data.entity;

public class EntityStatsBase implements IEntityStats {
    private MiscData miscData;
    private CombatData combatData;
    private MagicData magicData;

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

    @Override
    public void setMagicData(MagicData magicData)
    {
        this.magicData = magicData;
    }
    @Override
    public MagicData getMagicData()
    {
        return this.magicData;
    }

}
