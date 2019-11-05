package Objects;

import Colision.Colision;
import Tools.Mathematics;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;

//Extends ModelInstance (depois)
public class Entity{
    private ModelInstance modelInstance;
    private Vector3 motion = new Vector3(0, 0, 0);
    private Vector3 position = new Vector3();
    private float[] maxHitBoxPoints;
    private float[] minHitBoxPoints;
    private Vector3 rotation;

    public Entity(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints){
        this.position = position;
        this.modelInstance = modelInstance;
        this.modelInstance.transform.setToTranslation(position);
        this.rotation = new Vector3(0, 0, 0);
        this.minHitBoxPoints = minHitBoxPoints;
        this.maxHitBoxPoints = maxHitBoxPoints;
    }

    public Entity(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation){
        this.position = position;
        this.maxHitBoxPoints = maxHitBoxPoints;
        this.minHitBoxPoints = minHitBoxPoints;
        this.rotation = rotation;
        modelInstance = new ModelInstance(model);

        modelInstance.transform.setToTranslation(position);

        rotateModel();
    }

    public void update() {    }

    private void rotateModel(){
        modelInstance.transform.rotate(Vector3.Y, rotation.y);
        //System.out.println();
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    public Vector3 getMotion() {
        return motion;
    }

    public void setMotion(Vector3 motion) {
        this.motion = motion;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float[] getMaxHitBoxPoints() {
        return maxHitBoxPoints;
    }

    public float[] getMinHitBoxPoints() {
        return minHitBoxPoints;
    }

    public Vector3 getRotation() { return rotation; }

    public void setRotation(Vector3 rotation){
        this.rotation = rotation;
        rotateModel();
    }
}


