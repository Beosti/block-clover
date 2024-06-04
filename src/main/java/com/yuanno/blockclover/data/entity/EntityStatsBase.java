package com.yuanno.blockclover.data.entity;

public class EntityStatsBase implements IEntityStats {
    private MiscData miscData;

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
}
