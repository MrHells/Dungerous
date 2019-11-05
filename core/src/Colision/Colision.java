package Colision;

import Objects.CameraObject;
import Objects.Entity;
import Tools.Mathematics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

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
        if ((box2.position.y + box2.yPositionMin) < (box1.yPositionMax + box1.position.y) && (box2.position.y + box2.yPositionMin) > (box1.yPositionMin + box1.position.y)) {
            return true;
            //return ((box2.position.y + box2.yPositionMin) - (box1.yPositionMax + box1.position.y));
        }
        return false;
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


        System.out.println(secondBox.zPositionMin);
        firstBox.position = new Vector3(0, 0, 0);
        secondBox.zPositionMin += secondBox.position.z - firstBox.position.z;
        secondBox.zPositionMax += secondBox.position.z - firstBox.position.z;
        secondBox.xPositionMax += secondBox.position.x - firstBox.position.x;
        secondBox.xPositionMin += secondBox.position.x - firstBox.position.x;

        Mathematics mathematics = new Mathematics();


        System.out.println(TestLineIntersectionWithBox(firstBox, new float[]{secondBox.xPositionMin, secondBox.zPositionMin}, new float[]{secondBox.xPositionMin, secondBox.zPositionMax}) + " " +
                TestLineIntersectionWithBox(firstBox, new float[]{secondBox.xPositionMin, secondBox.zPositionMin}, new float[]{secondBox.xPositionMin, secondBox.zPositionMax}) + " " +
                TestLineIntersectionWithBox(firstBox, new float[]{secondBox.xPositionMax, secondBox.zPositionMax}, new float[]{secondBox.xPositionMin, secondBox.zPositionMax}) + " " +
                TestLineIntersectionWithBox(firstBox, new float[]{secondBox.xPositionMax, secondBox.zPositionMax}, new float[]{secondBox.xPositionMax, secondBox.zPositionMin}) +
                " AABB" + ((
                firstBox.zPositionMax + firstBox.position.z >= secondBox.zPositionMax + secondBox.position.z &&
                        firstBox.zPositionMin + firstBox.position.z <= secondBox.zPositionMin + secondBox.position.z &&
                        firstBox.xPositionMax + firstBox.position.x >= secondBox.xPositionMax + secondBox.position.x &&
                        firstBox.xPositionMin + firstBox.position.x <= secondBox.xPositionMin + secondBox.position.x) ||

                (
                        firstBox.zPositionMax + firstBox.position.z <= secondBox.zPositionMax + secondBox.position.z &&
                                firstBox.zPositionMin + firstBox.position.z >= secondBox.zPositionMin + secondBox.position.z &&
                                firstBox.xPositionMax + firstBox.position.x <= secondBox.xPositionMax + secondBox.position.x &&
                                firstBox.xPositionMin + firstBox.position.x >= secondBox.xPositionMin + secondBox.position.x)));

        System.out.println(TestLineIntersectionWithBox(firstBox, mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMin, secondBoxRotation - firstBoxRotation), mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation)));
        if (TestLineIntersectionWithBox(firstBox, mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMin, secondBoxRotation - firstBoxRotation), mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation)) ||
                TestLineIntersectionWithBox(firstBox, mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMin, secondBoxRotation - firstBoxRotation), mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation)) ||
                TestLineIntersectionWithBox(firstBox, mathematics.rotatePointInMatrix(secondBox.xPositionMax, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation), mathematics.rotatePointInMatrix(secondBox.xPositionMin, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation)) ||
                TestLineIntersectionWithBox(firstBox, mathematics.rotatePointInMatrix(secondBox.xPositionMax, secondBox.zPositionMax, secondBoxRotation - firstBoxRotation), mathematics.rotatePointInMatrix(secondBox.xPositionMax, secondBox.zPositionMin, secondBoxRotation - firstBoxRotation))
        ) {
            System.out.println("WHAT A DUE");
            return true;
        }
        if ((firstBox.zPositionMax + firstBox.position.z >= secondBox.zPositionMax && firstBox.zPositionMin + firstBox.position.z <= secondBox.zPositionMin && firstBox.zPositionMax >= secondBox.xPositionMax && firstBox.xPositionMin <= secondBox.xPositionMin) ||
                (firstBox.zPositionMax + firstBox.position.z <= secondBox.zPositionMax && firstBox.zPositionMin + firstBox.position.z >= secondBox.zPositionMin && firstBox.zPositionMax <= secondBox.xPositionMax && firstBox.xPositionMin >= secondBox.xPositionMin)) {
            System.out.println("WHAT A COLLISION");
            return true;
        }

        return false;
    }

    private boolean TestLineIntersectionWithBox(Box box, float[] point1Coordinates, float[] point2Coordinates) {
        if (TestLineIntersectionWithLine(new float[]{box.xPositionMin, box.zPositionMin}, new float[]{box.xPositionMin, box.zPositionMax}, point1Coordinates, point2Coordinates) ||
                TestLineIntersectionWithLine(new float[]{box.xPositionMin, box.zPositionMin}, new float[]{box.xPositionMax, box.zPositionMin}, point1Coordinates, point2Coordinates) ||
                TestLineIntersectionWithLine(new float[]{box.xPositionMax, box.zPositionMax}, new float[]{box.xPositionMin, box.zPositionMax}, point1Coordinates, point2Coordinates) ||
                TestLineIntersectionWithLine(new float[]{box.xPositionMax, box.zPositionMax}, new float[]{box.xPositionMax, box.zPositionMin}, point1Coordinates, point2Coordinates)
        ) {
            return true;
        }


        return false;

    }

    private boolean TestLineIntersectionWithLine(float[] firstLineCoordinates1, float[] firstLineCoordinates2, float[] secondLineCoordinates1, float[] secondLineCoordinates2) {
        float firstLineXmax, firstLineXmin, firstLineZmax, firstLineZmin;
        float secondLineXmax, secondLineXmin, secondLineZmax, secondLineZmin;


        if (firstLineCoordinates1[0] > firstLineCoordinates2[0]) {
            firstLineXmax = firstLineCoordinates1[0];
            firstLineXmin = firstLineCoordinates2[0];
        } else if (firstLineCoordinates1[0] < firstLineCoordinates2[0]) {
            firstLineXmax = firstLineCoordinates2[0];
            firstLineXmin = firstLineCoordinates1[0];
        } else {
            firstLineXmax = firstLineCoordinates1[0];
            firstLineXmin = firstLineXmax;
        }

        if (firstLineCoordinates1[1] > firstLineCoordinates2[1]) {
            firstLineZmax = firstLineCoordinates1[1];
            firstLineZmin = firstLineCoordinates2[1];
        } else if (firstLineCoordinates1[1] < firstLineCoordinates2[1]) {
            firstLineZmax = firstLineCoordinates2[1];
            firstLineZmin = firstLineCoordinates1[1];
        } else {
            firstLineZmax = firstLineCoordinates1[1];
            firstLineZmin = firstLineZmax;
        }

        if (secondLineCoordinates1[0] > secondLineCoordinates2[0]) {
            secondLineXmax = secondLineCoordinates1[0];
            secondLineXmin = secondLineCoordinates2[0];
        } else if (secondLineCoordinates1[0] < secondLineCoordinates2[0]) {
            secondLineXmax = secondLineCoordinates2[0];
            secondLineXmin = secondLineCoordinates1[0];
        } else {
            secondLineXmax = secondLineCoordinates1[0];
            secondLineXmin = secondLineXmax;
        }

        if (secondLineCoordinates1[1] > secondLineCoordinates2[1]) {
            secondLineZmax = secondLineCoordinates1[1];
            secondLineZmin = secondLineCoordinates2[1];
        } else if (secondLineCoordinates1[1] < secondLineCoordinates2[1]) {
            secondLineZmax = secondLineCoordinates2[1];
            secondLineZmin = secondLineCoordinates1[1];
        } else {
            secondLineZmax = secondLineCoordinates1[1];
            secondLineZmin = secondLineZmax;
        }
/*

        System.out.println((firstLineXmax >= secondLineXmax && firstLineXmax <= secondLineXmin) ||
                           (firstLineXmax <= secondLineXmax && firstLineXmin >= secondLineXmax) ||
                           (firstLineXmin <= secondLineXmax && firstLineXmin >= secondLineXmin) ||
                           (firstLineXmax >= secondLineXmin && firstLineXmin <= secondLineXmin) ||
                           (firstLineXmax >= secondLineXmax && firstLineXmin <= secondLineXmin) ||
                           (firstLineXmax <= secondLineXmax && firstLineXmin >= secondLineXmin));
*/
        if ((firstLineXmax >= secondLineXmax && firstLineXmax <= secondLineXmin) ||
                (firstLineXmax <= secondLineXmax && firstLineXmin >= secondLineXmax) ||
                (firstLineXmin <= secondLineXmax && firstLineXmin >= secondLineXmin) ||
                (firstLineXmax >= secondLineXmin && firstLineXmin <= secondLineXmin) ||
                (firstLineXmax >= secondLineXmax && firstLineXmin <= secondLineXmin) ||
                (firstLineXmax <= secondLineXmax && firstLineXmin >= secondLineXmin)) {
            if ((firstLineZmax >= secondLineZmax && firstLineZmax <= secondLineZmin) || (firstLineZmax <= secondLineZmax && firstLineZmin >= secondLineXmax) || (firstLineZmin <= secondLineZmax && firstLineZmin >= secondLineZmin) || (firstLineZmax >= secondLineZmin && firstLineXmin <= secondLineZmin) || (firstLineZmax >= secondLineZmax && firstLineZmin <= secondLineZmin) || (firstLineZmax <= secondLineZmax && firstLineZmin >= secondLineZmin)) {
                return true;
            }

        }

        return false;
    }

    public boolean CollisionTestWithCamera(ArrayList<Entity> entities, CameraObject cameraObject) {

        //ArrayList<Entity> toTest = entities;
        int counter = 0;
        for (Entity entity : entities) {
            Box cameraBox = new Box(cameraObject.getCamera().position, cameraObject.getMaxHitBoxPoints(), cameraObject.getMinHitBoxPoints(), new Vector3(0, cameraObject.getCameraRotation(), 0));
            Box entityBox = new Box(entity.getPosition(), entity.getMaxHitBoxPoints(), entity.getMinHitBoxPoints(), entity.getRotation());
            if (testQuadraticCollision(entityBox, cameraBox)) {
                if(testYCollisionBetweenBoxes(cameraBox, entityBox)){
                    cameraObject.getCamera().position.y += 0.1f;
                    System.out.println("Colidiu");
                    return true;
                }
            }
        }
        return false;
    }
}
