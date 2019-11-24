package Objects.Enemies;

import Colision.Colision;
import Objects.CameraObject;
import Objects.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class MeleeEnemy extends Entity {
    private boolean sawPlayer = false;
    private Vector3 enemyRotation = new Vector3();
    private int cooldownAttack = 0;
    public MeleeEnemy(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, int health) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, health);
    }

    public MeleeEnemy(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
    }

    public void update(CameraObject cameraObject, ArrayList<Entity> map){
        if (!sawPlayer){

            boolean isWallBetween = true;

            if(calculateDistance(cameraObject.getCamera().position) < 3){
                ModelLoader loader = new ObjLoader();
                Model model = loader.loadModel(Gdx.files.internal("Models/Gost.obj"));
                setModelInstance(new ModelInstance(model));
                getModelInstance().transform.setToTranslation(getPosition());
               sawPlayer = true;
            }
        }
        if(cooldownAttack == 0){

            if (sawPlayer ){
                Vector3 cameraPosition = cameraObject.getCamera().position;
                float speed = 0.02f;
                Vector3 move = calculateWayToWalk(cameraPosition, speed);
                super.setPosition(new Vector3(getPosition().x + move.x, getPosition().y + move.y, getPosition().z + move.z));
                Quaternion quaternion = new Quaternion();
                float catetoOposto = move.x;
                if(move.x < 0){
                    catetoOposto = -move.x;
                }
                float sin = catetoOposto/speed;
                float degrees = (float) Math.asin(sin);
                degrees = (float) (degrees * 180/Math.PI);

                if(move.x > 0){
                    degrees = - degrees;
                }
                if(
                Colision.testQuadraticCollision(new Colision.Box(getPosition(), getMaxHitBoxPoints(), getMinHitBoxPoints(), null),
                                                 new Colision.Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), null))
                ){
                    move = new Vector3(-move.x, -move.y, -move.z);
                    cameraObject.setHealth(cameraObject.getHealth() - 8);
                    cooldownAttack = 60;
                }

                getModelInstance().transform.translate(move);

            }
        }else {
            cooldownAttack -= 1;
        }
    }

    public Vector3 calculateRotationToPoint(){
        return null;
    }

    public Vector3 calculateWayToWalk(Vector3 cameraPosition, float speed){


        float hypotenuse = calculateDistance(cameraPosition);
        float cosX = (cameraPosition.x - getPosition().x) / hypotenuse;
        float xDistance = speed * cosX;

        hypotenuse = calculateDistance(cameraPosition);
        float cosZ = (cameraPosition.z - getPosition().z) / hypotenuse;
        float zDistance = speed * cosZ;

        return new Vector3(xDistance, 0, zDistance);
    }

    public float calculateDistance(Vector3 cameraPosition){
        Vector3 enemyPosition = super.getPosition();
        float distanceX = enemyPosition.x - cameraPosition.x;
        float distanceZ = enemyPosition.z - cameraPosition.z;

        if(distanceX < 0){
            distanceX *= -1;
        }
        if(distanceZ < 0){
            distanceZ *= -1;
        }

        return (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceZ, 2));
    }

}
