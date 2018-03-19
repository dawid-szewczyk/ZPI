package com.pwr.zpi.core.memory.holons.ContextJar;

import com.pwr.zpi._EpisodicMemoryLayer.BaseProfile;
import com.pwr.zpi.util.Pair;
import com.pwr.zpi._CommonClasses.Trait;

import java.util.Set;

/**
 * Interface serving purpose of obtaining Distance Functions of every sort.
 * Created by Jarema on 5/24/2017.
 */
public abstract class DistanceFunction {

    public abstract double implementation(BaseProfile first, BaseProfile second);
    public abstract double composite(BaseProfile first,Pair<Set<Trait>,Set<Trait>> second);
}
