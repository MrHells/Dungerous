package Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;

public class Ground extends Entity{
    public Ground(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, 999999999);
        super.getModelInstance().materials.add( new Material(ColorAttribute.createDiffuse(Color.GREEN)));

    }

    public Ground(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
        super.getModelInstance().materials.add(new Material(ColorAttribute.createDiffuse(Color.GREEN)));
    }

    public void update(){
    }


}
