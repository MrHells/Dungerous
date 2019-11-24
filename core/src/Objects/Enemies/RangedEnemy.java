package Objects.Enemies;

import Colision.Colision;
import Objects.CameraObject;
import Objects.Entity;
import Objects.Shoot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import static com.mygdx.game.Main.entities;

import java.util.ArrayList;

public class RangedEnemy extends Entity {
    private boolean sawPlayer = false;
    private int shootCoolDown = 0;
    public RangedEnemy(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, int health) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, health);
    }

    public RangedEnemy(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
    }

    public void update(CameraObject cameraObject, ArrayList<Entity> map){
        float distanceBetweenCamera = calculateDistance(cameraObject.getCamera().position);
        if (!sawPlayer){
            boolean isWallBetween = true;
        //   System.out.println(calculateDistance(cameraObject.getCamera().position));
            if(distanceBetweenCamera < 6){
                sawPlayer = true;
            }
        }
        boolean canShot = false;
        if (sawPlayer){
            if(shootCoolDown == 0 ){
                canShot = true;
            }else{
                shootCoolDown -= 1;
            }
            if(distanceBetweenCamera < 10) {
                if(canShot){
                    Vector3 cameraPosition = cameraObject.getCamera().position;
                    Vector3 move = calculateWayToWalk(cameraPosition, 0.09f);
                    ModelLoader loader = new ObjLoader();
                    Model model = loader.loadModel(Gdx.files.internal("Models/FireBall.obj"));
                    model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Mage_texture.png")));
                    entities.add(new Shoot(new ModelInstance(model), new Vector3(getPosition().x - 0.142187f  , getPosition().y + 1f, getPosition().z - 0.023708f), new float[]{0.183434f, 3, 0.183434f}, new float[]{-0.183434f, 0, -0.183434f}, 20, move, 3));
                    shootCoolDown = 60;
                }

            }else{
                Vector3 cameraPosition = cameraObject.getCamera().position;
                Vector3 move = calculateWayToWalk(cameraPosition, 0.02f);
                setPosition(new Vector3(getPosition().x + move.x, getPosition().y + move.y, getPosition().z + move.z));
                getModelInstance().transform.setToTranslation(getPosition().x , getPosition().y, getPosition().z);
            }

        }
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
