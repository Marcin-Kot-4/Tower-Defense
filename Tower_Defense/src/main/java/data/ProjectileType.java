package data;

import org.newdawn.slick.opengl.Texture;
import static controllers.Graphic.*;

/**
 * Enum inaczej typ wyliczeniowy ProjectileType zawiera typy pocisków,
 * i wartość jakich zadają obrażeń, ich prędkość oraz zasięg.
 */
public enum ProjectileType {

    ProjectileLightCannon(FastLoad("ProjectileLightCannon"), 30, 550, 500),
    ProjectileLightWarhead(FastLoad("ProjectileLightWarhead"), 45, 650, 500),
    ProjectileDisruptor(FastLoad("ProjectileDisruptor"), 100, 250, 800),
    ProjectileMachineGun(FastLoad("ProjectileMachineGun"), 8, 350, 750),
    ProjectileLightAntimatterMissile(FastLoad("ProjectileLightAntimatterMissile"), 35, 500, 1300),
    ProjectileTwinCannon(FastLoad("ProjectileTwinCannon"), 150, 500, 900),
    ProjectileHeavyCannon(FastLoad("ProjectileHeavyCannon"), 450, 900, 1300),
    ProjectileHeavyWarhead(FastLoad("ProjectileHeavyWarhead"), 500, 700, 1200),
    Rocket(FastLoad("Rocket"), 700, 30, 2000),
    // 200 większy zasięg rakiety niż namierzania przez wyrzutnię
    // [zapas producenta po to aby rakiety częściej osiągały oddalający się cel lub cel zmieniony w trakcie lotu]
    ProjectileAntimatterMissile(FastLoad("ProjectileAntimatterMissile"), 100, 500, 1800),
    // 200 większy zasięg rakiety niż namierzania przez wyrzutnię
    // [zapas producenta po to aby rakiety częściej osiągały oddalający się cel lub cel zmieniony w trakcie lotu]
    ProjectileHellBore(FastLoad("ProjectileHellBore"), 600, 440, 1100),
    ProjectilePulson(FastLoad("ProjectilePulson"), 800, 800, 1500);

    Texture texture;
    int damage;
    float speed, range;

    ProjectileType(Texture texture, int damage, float speed, float range){
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRange() {
        return range;
    }
}
