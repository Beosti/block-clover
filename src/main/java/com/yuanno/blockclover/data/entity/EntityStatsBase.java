package com.yuanno.blockclover.data.entity;

import com.yuanno.blockclover.data.util.MiscData;

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
