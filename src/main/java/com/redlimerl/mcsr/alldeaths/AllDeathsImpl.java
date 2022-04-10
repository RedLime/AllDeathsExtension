package com.redlimerl.mcsr.alldeaths;

import com.google.common.collect.Lists;
import com.redlimerl.speedrunigt.api.SpeedRunIGTApi;
import com.redlimerl.speedrunigt.timer.category.RunCategory;

import java.util.Collection;

public class AllDeathsImpl implements SpeedRunIGTApi {

    @Override
    public Collection<RunCategory> registerCategories() {
        return Lists.newArrayList(AllDeaths.CATEGORY_ALL_DEATHS, AllDeaths.CATEGORY_HALF_DEATH);
    }
}
