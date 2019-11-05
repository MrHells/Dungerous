package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;

public class Wall extends Entity {
    public Wall(Vector3 position) {
        super(null, position, new float[]{0.5f, 0, 0.5f}, new float[]{-0.5f, 4, -0.5f});
        super.setModelInstance(getModelInstance());
    }
    public Wall(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
    }
    private ModelInstance getModelInstande(){
        ModelLoader loader = new ObjLoader();

        Model model = loader.loadModel(Gdx.files.internal("Models/Wall.obj"));
        return new ModelInstance(model);
    }
}
