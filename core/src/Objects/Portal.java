package Objects;

import Colision.Colision;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Portal extends Entity {
    public boolean isLevelPassed = false;
    public Portal(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, int health) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, health);
    }

    public void update(CameraObject cameraObject){
        Colision.Box entityBox = new Colision.Box(this.getPosition(), this.getMaxHitBoxPoints(), this.getMinHitBoxPoints(), this.getRotation());
        Colision.Box cameraBox = new Colision.Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), new Vector3(0, 0, 0));
        if(Colision.testQuadraticCollision(entityBox, cameraBox) || Colision.testQuadraticCollision(cameraBox, entityBox)){
            isLevelPassed = true;

        }

    }
}
