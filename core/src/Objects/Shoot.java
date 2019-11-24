package Objects;

import Colision.Colision;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Main;

import java.util.ArrayList;

public class Shoot extends Entity {
    private Vector3 move;
    private int damage;
    public Shoot(ModelInstance modelInstance, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, int health, Vector3 move, int damage) {
        super(modelInstance, position, maxHitBoxPoints, minHitBoxPoints, health);
        this.move = move;
        this.damage = damage;
    }

    public Shoot(Model model, Vector3 position, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 rotation) {
        super(model, position, maxHitBoxPoints, minHitBoxPoints, rotation);
    }
    public void update(ArrayList<Entity> entities, ArrayList<Entity> map){
        setPosition(new Vector3(getPosition().x + move.x, getPosition().y + move.y, getPosition().z + move.z));

        for (int i = 0; i < entities.size(); i++){
            Colision.Box entityBox = new Colision.Box(this.getPosition(), this.getMaxHitBoxPoints(), this.getMinHitBoxPoints(), null);
            Colision.Box cameraBox = new Colision.Box(entities.get(i).getPosition(), entities.get(i).getMaxHitBoxPoints(), entities.get(i).getMinHitBoxPoints(), null);
            getModelInstance().transform.setToTranslation(getPosition());

            if(Colision.testQuadraticCollision(entityBox, cameraBox) || Colision.testQuadraticCollision(cameraBox, entityBox)){
                entities.get(i).setHealth(entities.get(i).getHealth() - super.getHealth());
                setHealth(-10);

            }

        }

        for (Entity wall : map){
            Colision.Box shootBox = new Colision.Box(this.getPosition(), this.getMaxHitBoxPoints(), this.getMinHitBoxPoints(), this.getRotation());
            Colision.Box wallBox = new Colision.Box(wall.getPosition(), wall.getMaxHitBoxPoints(), wall.getMinHitBoxPoints(), new Vector3(0, 0, 0));
            if(wall.getClass() != Ground.class && Colision.testQuadraticCollision(wallBox, shootBox) || Colision.testQuadraticCollision(shootBox, wallBox)){
                setHealth(0);
            }
        }
    }

    public void update(CameraObject cameraObject, ArrayList<Entity> map){
        setPosition(new Vector3(getPosition().x + move.x, getPosition().y + move.y, getPosition().z + move.z));
        Colision.Box entityBox = new Colision.Box(this.getPosition(), this.getMaxHitBoxPoints(), this.getMinHitBoxPoints(), this.getRotation());
        Colision.Box cameraBox = new Colision.Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), new Vector3(0, 0, 0));
        getModelInstance().transform.setToTranslation(getPosition());

        if(Colision.testQuadraticCollision(entityBox, cameraBox) || Colision.testQuadraticCollision(cameraBox, entityBox)){
            cameraObject.setHealth(cameraObject.getHealth() - damage);
            setHealth(-10);
            move = new Vector3(0, 0, 0);
        }

        for (Entity wall : map){
            Colision.Box shootBox = new Colision.Box(this.getPosition(), this.getMaxHitBoxPoints(), this.getMinHitBoxPoints(), this.getRotation());
            Colision.Box wallBox = new Colision.Box(wall.getPosition(), wall.getMaxHitBoxPoints(), wall.getMinHitBoxPoints(), new Vector3(0, 0, 0));

            if(wall.getClass() != Ground.class && Colision.testQuadraticCollision(wallBox, shootBox) || Colision.testQuadraticCollision(shootBox, wallBox)){
                setHealth(0);
            }

        }
    }
}
