package Colision;

import Objects.CameraObject;
import Objects.Entity;
import Objects.Ground;
import Tools.Mathematics;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Colision {
    public void testEntitiesCollision(ArrayList<Entity> entities) {
        ArrayList<Entity> toTest = entities;
        
        while(entities.size() != 1){
            Entity firstEntity = toTest.get(0);
            toTest.remove(firstEntity);
            testEntityCollisionWithAnotherEntities(firstEntity, toTest);
            
        }
    }

    private void testEntityCollisionWithAnotherEntities(Entity firstEntity, ArrayList<Entity> toTest) {
        for (int i = 0; i < toTest.size(); i++){
            toTest.get(i);
            Box firstEntityBox = new Box(firstEntity.getPosition(), firstEntity.getMaxHitBoxPoints(), firstEntity.getMinHitBoxPoints(), new Vector3(0, firstEntity.getRotation().y, 0));
            Box secondEntityBox = new Box(toTest.get(i).getPosition(), toTest.get(i).getMaxHitBoxPoints(), toTest.get(i).getMinHitBoxPoints(), toTest.get(i).getRotation());

            testQuadraticCollision(firstEntityBox, secondEntityBox);
        }
    }

    static class Box {
        float xPositionMax;
        float xPositionMin;
        float yPositionMax;
        float yPositionMin;
        float zPositionMax;
        float zPositionMin;
        Vector3 position;
        Vector3 rotation;

        Box(Vector3 position, float[] maxPoints, float[] minPoints, Vector3 rotation) {
            this.position = position;
            this.rotation = rotation;
            this.xPositionMin = minPoints[0];
            this.yPositionMin = minPoints[1];
            this.zPositionMin = minPoints[2];
            this.xPositionMax = maxPoints[0];
            this.yPositionMax = maxPoints[1];
            this.zPositionMax = maxPoints[2];
        }
    }

    private boolean testYCollisionBetweenBoxes(Box box1, Box box2) {
        return ((box2.position.y + box2.yPositionMin) < (box1.yPositionMax + box1.position.y) && (box2.position.y + box2.yPositionMin) > (box1.yPositionMin + box1.position.y));
    }
    public boolean testQuadraticCollision(Box firstBox, Box secondBox) {
        float secondBoxRotation = secondBox.rotation.y;

        float firstBoxRotation = firstBox.rotation.y;
//        btCollisionWorld collisionWorld;
//
//        btCollisionConfiguration collisionConfig = new btDefaultCollisionConfiguration();
//        btDispatcher dispatcher = new btCollisionDispatcher(collisionConfig);
//        btBroadphaseInterface broadphase = new btDbvtBroadphase();
//        collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
//
//        btCollisionObject firstBoxx, secondsBox;
//
//        btCollisionShape firstBoxSHape = new btBoxShape(new Vector3(firstBox.zPositionMin + firstBox.zPositionMax, z));
//        firstBoxx = new btCollisionObject();
        //firstBoxx.setCollisionShape(groundShape);
        //firstBoxx.setWorldTransform(ground.transform);


        //System.out.println(secondBox.zPositionMin);
        Mathematics mathematics = new Mathematics();


        if(
                (secondBox.xPositionMax + secondBox.position.x < firstBox.xPositionMax + firstBox.position.x &&
                secondBox.xPositionMax + secondBox.position.x > firstBox.xPositionMin + firstBox.position.x) ||
                (secondBox.xPositionMin + secondBox.position.x > firstBox.xPositionMin + firstBox.position.x &&
                secondBox.xPositionMin + secondBox.position.x < firstBox.xPositionMax + firstBox.position.x)
        ){
            if(
                    (secondBox.zPositionMax + secondBox.position.z < firstBox.zPositionMax + firstBox.position.z &&
                    secondBox.zPositionMax + secondBox.position.z > firstBox.zPositionMin + firstBox.position.z) ||
                    (secondBox.zPositionMin + secondBox.position.z > firstBox.zPositionMin + firstBox.position.z &&
                    secondBox.zPositionMin + secondBox.position.z < firstBox.zPositionMax + firstBox.position.z)
            ){
                return true;
            }

        }

//        if ((firstBox.zPositionMax + firstBox.position.z >= secondBox.zPositionMax && firstBox.zPositionMin + firstBox.position.z <= secondBox.zPositionMin && firstBox.zPositionMax >= secondBox.xPositionMax && firstBox.xPositionMin <= secondBox.xPositionMin) ||
//                (firstBox.zPositionMax + firstBox.position.z <= secondBox.zPositionMax && firstBox.zPositionMin + firstBox.position.z >= secondBox.zPositionMin && firstBox.zPositionMax <= secondBox.xPositionMax && firstBox.xPositionMin >= secondBox.xPositionMin)) {
//            System.out.println("WHAT A COLLISION");
//            return true;
//        }

        return false;

    }


    public boolean CollisionTestWithCamera(ArrayList<Entity> entities, CameraObject cameraObject) {
        Box cameraBox = new Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), new Vector3(0, 0, 0));
        for (Entity entity : entities) {
            Box entityBox = new Box(entity.getPosition(), entity.getMaxHitBoxPoints(), entity.getMinHitBoxPoints(), entity.getRotation());
            if (testQuadraticCollision(entityBox, cameraBox) ) {
                if(testYCollisionBetweenBoxes(cameraBox, entityBox) || testYCollisionBetweenBoxes(entityBox, cameraBox)){
                    if(entity.getClass() == Ground.class){
                        System.out.println("Colidiu GROUND");
                    } else{
                        System.out.println("Colidiu wall");
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
