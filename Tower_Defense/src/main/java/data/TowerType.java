package data;

import org.newdawn.slick.opengl.Texture;
import static controllers.Graphic.*;

<<<<<<< HEAD
/**
 * Enum inaczej typ wyliczeniowy TowerType zawiera typy wież obronych i m.in. ich zasięg, szybkość wystrzału, koszt.
 * Wieża może się składać z wielu tekstur, dlatego została użyta tablica tekstur.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public enum TowerType {

    // wieża może się składać z wielu tekstur, dlatego została użyta tablica tekstur
    LightCannon(new Texture[] {FastLoad("SmallBase"), FastLoad("LightCannon")}, ProjectileType.ProjectileLightCannon,
            400, 2.0f, 0.0f,  40, ""),
    LightWarhead(new Texture[] {FastLoad("SmallBase"), FastLoad("LightWarhead")}, ProjectileType.ProjectileLightWarhead,
            550, 2.5f, 0.0f, 70, ""),
    TwinLightCannon(new Texture[] {FastLoad("MediumBase"), FastLoad("TwinLightCannon")}, ProjectileType.ProjectileLightCannon,
            400, 2.0f, 0.0f,  120, ""),
    MachineGun(new Texture[] {FastLoad("MediumBase"), FastLoad("MachineGun")}, ProjectileType.ProjectileMachineGun,
            650,0.05f, 0.0f, 300, "Target movement prediction"),
    Disruptor(new Texture[] {FastLoad("MediumBase"), FastLoad("Disruptor")}, ProjectileType.ProjectileDisruptor,
            650, 2.0f, 0.0f, 300, "Slows hitted enemy by 15%"),
    LightAntimatterMissileLauncher(new Texture[] {FastLoad("MediumBase"), FastLoad("LightAntimatterMissileLauncher")}, ProjectileType.ProjectileLightAntimatterMissile,
            1200, 5.0f, 0.0f, 500, "Guided missiles"),
    TwinDisruptors(new Texture[] {FastLoad("LargeBase"), FastLoad("TwinDisruptors")}, ProjectileType.ProjectileDisruptor,
            800, 2.0f, 0.0f, 500, "Slows hitted enemy by 15% x 2"),
    TwinMachineGun(new Texture[] {FastLoad("LargeBase"), FastLoad("TwinMachineGun")}, ProjectileType.ProjectileMachineGun,
            700,0.05f, 0.0f, 550, "Target movement prediction"),
    TwinCannon(new Texture[] {FastLoad("LargeBase"), FastLoad("TwinCannon")}, ProjectileType.ProjectileTwinCannon,
            900,3.0f, 0.8f,  550, "Target movement prediction"),
    HeavyCannon(new Texture[] {FastLoad("LargeBase"), FastLoad("HeavyCannon")}, ProjectileType.ProjectileHeavyCannon,
            1300,3.0f, 0.8f,  600, "Target movement prediction"),
    HeavyWarhead(new Texture[] {FastLoad("LargeBase"), FastLoad("HeavyWarhead")}, ProjectileType.ProjectileHeavyWarhead,
            1200,3.0f, 0.8f,  650, "Target movement prediction"),
    TwinHeavyWarhead(new Texture[] {FastLoad("LargeBase"), FastLoad("TwinHeavyWarhead")}, ProjectileType.ProjectileHeavyWarhead,
            1200,3.0f, 0.8f,  900, "Target movement prediction"),
    RocketLauncher(new Texture[] {FastLoad("LargeBase"), FastLoad("RocketLauncher"), FastLoad("RocketLauncherEmpty")}, ProjectileType.Rocket,
            1800, 3.5f, 1.5f, 950, "Guided rockets"),
    AntimatterMissileLauncher(new Texture[] {FastLoad("LargeBase"), FastLoad("AntimatterMissileLauncher")}, ProjectileType.ProjectileAntimatterMissile,
            1600, 4.0f, 0.0f, 1100, "Guided missiles"),
    HellBore(new Texture[] {FastLoad("LargeBase"), FastLoad("HellBore")}, ProjectileType.ProjectileHellBore,
            1100,0.7f, 0.8f,  1200, "Target movement prediction with 35% slow."),
    Pulson(new Texture[] {FastLoad("LargeBase"), FastLoad("Pulson")}, ProjectileType.ProjectilePulson,
            1500,0.6f, 0.8f,  1500, "Target movement prediction");

    Texture[] textures;
    public ProjectileType projectileType;
    String additionalDescription;
    int damage, range, cost, projectileSpeed;
    float firingSpeed, stopRotateOfTowerTime;

    TowerType(Texture[] textures, ProjectileType projectileType, int range, float firingSpeed, float stopRotateOfTowerTime, int cost, String additionalDescription){
        this.textures = textures;
        this.projectileType = projectileType;
        this.damage = projectileType.damage;
        this.projectileSpeed = (int) projectileType.speed;
        this.range = range;
        this.firingSpeed = firingSpeed;
        this.stopRotateOfTowerTime = stopRotateOfTowerTime;
        this.cost = cost;
        this.additionalDescription = additionalDescription;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range / 64;
    }

    public int getCost() {
        return cost;
    }

    public float getFiringSpeed() {
        return 60 / firingSpeed;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public int getSpeed() {
        return projectileSpeed;
    }
}
