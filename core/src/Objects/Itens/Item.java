package Objects.Itens;

import Images.ImageObject;
import Objects.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Item extends Entity {
    private ImageObject imageObject;
    private boolean isRanged;
    private boolean isSumable;
    private int quant = 0;
    private int damage;
    public Item(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, ImageObject imageObject, boolean isRanged, int health, boolean isSumable, int damage) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, health);
        this.isRanged = isRanged;
        this.imageObject = imageObject;
        this.isSumable = isSumable;
        quant++;
        this.damage = damage;
    }

    public ImageObject getImageObject() {
        return imageObject;
    }

    public void setImageObject(ImageObject imageObject) {
        this.imageObject = imageObject;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public void setRanged(boolean ranged) {
        isRanged = ranged;
    }

    public boolean isSumable() {
        return isSumable;
    }

    public void setSumable(boolean sumable) {
        isSumable = sumable;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
