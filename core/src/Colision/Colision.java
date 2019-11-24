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

    public static class Box {
        float xPositionMax;
        float xPositionMin;
        float yPositionMax;
        float yPositionMin;
        float zPositionMax;
        float zPositionMin;
        Vector3 position;
        Vector3 rotation;

        public Box(Vector3 position, float[] maxPoints, float[] minPoints, Vector3 rotation) {
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
    public static boolean testQuadraticCollision(Box firstBox, Box secondBox) {
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
        return false;

    }

    public boolean CollisionTestWithCamera(ArrayList<Entity> entities, CameraObject cameraObject) {
        Box cameraBox = new Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), new Vector3(0, 0, 0));
        for (Entity entity : entities) {
            Box entityBox = new Box(entity.getPosition(), entity.getMaxHitBoxPoints(), entity.getMinHitBoxPoints(), entity.getRotation());
            if (testQuadraticCollision(entityBox, cameraBox) ) {
                if(testYCollisionBetweenBoxes(cameraBox, entityBox) || testYCollisionBetweenBoxes(entityBox, cameraBox)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean textLineIntersectionWithBox(Box box, Vector3 firstPoint, Vector3 secondPoint){
        secondPoint = new Vector3(secondPoint.x - firstPoint.x, secondPoint.y - firstPoint.y, secondPoint.z - firstPoint.z);
        box.position = new Vector3(box.position.x - firstPoint.x, box.position.y - firstPoint.y, box.position.z - firstPoint.z);
        firstPoint = new Vector3(0, 0, 0);

        float catetoOposto = secondPoint.x;
        float catetoAdjacente = secondPoint.z;
        if(catetoOposto <= 0){
            catetoOposto *= -1;
        }
        if(catetoAdjacente <= 0){
            catetoAdjacente *= -1;
        }

        float hyp =(float) Math.sqrt(Math.pow(catetoOposto, 2) + Math.pow(catetoAdjacente, 2));
        float sin = catetoOposto/hyp;
        float cos = catetoAdjacente/hyp;

        float[] newSecondPoint = Mathematics.rotatePointInMatrix(secondPoint.x, secondPoint.z, cos, sin);
        secondPoint.x = newSecondPoint[0];
        secondPoint.z = newSecondPoint[1];

        float[] newBoxPoint = Mathematics.rotatePointInMatrix(box.position.x, box.position.z, cos, sin);
        box.position.x = newBoxPoint[0];
        box.position.z = newBoxPoint[1];
        if(secondPoint.x < box.position.x + box.xPositionMax && secondPoint.x > box.position.x + box.xPositionMin){
            newSecondPoint = Mathematics.rotatePointInMatrix(secondPoint.x, secondPoint.z, 1, 1);
            secondPoint.x = newSecondPoint[0];
            secondPoint.z = newSecondPoint[1];

            newBoxPoint = Mathematics.rotatePointInMatrix(box.position.x, box.position.z, 1, 1);
            box.position.x = newBoxPoint[0];
            box.position.z = newBoxPoint[1];
            if(secondPoint.z < box.position.z + box.zPositionMax && secondPoint.z > box.position.z + box.zPositionMin){
                return true;
            }

        }
        return false;
    }
}
