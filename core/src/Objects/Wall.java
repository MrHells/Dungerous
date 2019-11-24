package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;

public class Wall extends Entity {
    public Wall(ModelInstance modelInstance, Vector3 position) {
        super(modelInstance, position, new float[]{0.51f, 4,0.51f}, new float[]{-0.6f, -2f, -0.6f}, 99999999);
        super.setModelInstance(getModelInstance());
    }
//    public Wall(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
//        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
//    }
}
