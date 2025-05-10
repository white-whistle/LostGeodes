package com.bajookie.lost_geodes.world.gen;

public class ModWorldGeneration{

    public static void generateModWorldGen(){
        ModOreGeneration.generateOres();
        ModVegetationGeneration.generateFlowers();
        ModTreeGeneration.generateTrees(); // empty!
    }


}
