package Objects.Itens;

import Objects.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Item extends Entity {
    public Item(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints);
    }

    public Item(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
    }
}
