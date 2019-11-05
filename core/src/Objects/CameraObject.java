package Objects;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


import Tools.Mathematics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntIntMap;

public class CameraObject{

    private Vector3 moveVector = new Vector3();
    private PerspectiveCamera camera;
    private Vector3 rotationVector = new Vector3();
    private Vector3 rotation = new Vector3();
    private float[] maxHitBoxPoint;
    private float[] minHitBoxPoint;
    private Vector3 position;


    public CameraObject(PerspectiveCamera camera) {
        super();
        this.camera = camera;
    }

    public CameraObject(PerspectiveCamera camera, float[] maxHitBoxPoints, float[] minHitBoxPoints, Vector3 position, Vector3 rotation){
        this.camera = camera;
        this.maxHitBoxPoint = maxHitBoxPoints;
        this.minHitBoxPoint = minHitBoxPoints;
        this.position = position;
        this.camera.position.y = position.y;
    }

    public void update(){
        if(!Gdx.input.isKeyPressed(Keys.J)){
            calculateCameraRotation(0, 0 , 0);

        }
        cameraInputs();
        camera.position.add(moveVector);

    }

    public void cameraInputs(){
        float velocity = 3;
        float side = 0;
        float forward = 0;
        float speed = 0.166f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
             forward = -0.016666f * velocity;
            //moveVector.set(camera.direction).nor().scl(0.016666f * velocity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            forward = 0.016666f * velocity;
            //moveVector.set(camera.direction).nor().scl(-0.016666f * velocity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            side = -0.016666f * velocity;
            //moveVector.set(camera.direction).crs(camera.up).nor().scl(-0.016666f * velocity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            side = 0.016666f * velocity;
            //moveVector.set(camera.direction).crs(camera.up).nor().scl(0.016666f * velocity);
        }
        float x = 0, z = 0;
        float cameraRotation = rotation.y;

        if(cameraRotation < 0){
            cameraRotation *= -1;
        }
        //System.out.println(camera.direction.z);
        double hypotenuse = Math.sqrt(Math.pow(camera.direction.z, 2) + Math.pow(camera.direction.x, 2));
        float sin = (float) (camera.direction.x / hypotenuse);
        float cos = (float) (camera.direction.z / hypotenuse);
        //System.out.println(hypotenuse);


        float frontalDistanceX = (float) (forward * -cos);
        float frontalDistanceZ = (float) (forward * -sin);

        x += frontalDistanceZ ;
        z += frontalDistanceX ;

        moveVector.set(x, -0.1f, z);
        position = new Vector3(position.x + moveVector.x, position.y + moveVector.y, position.z + moveVector.z);
    }

    public void calculateCameraRotation(int screenX, int screenY, int pointer){
        //float deltaX = -Gdx.input.getDeltaX() * 1f;
        //float deltaY = -Gdx.input.getDeltaY() * 1f;
        //System.out.println(deltaX);
        float deltaX = (640/2 - Gdx.input.getX()) * 0.5f;
        float deltaY = (480/2 - Gdx.input.getY()) * 0.5f;
        rotation.set(0 , rotation.y + deltaX, 0);
        camera.direction.rotate(camera.up, deltaX);
        rotationVector.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(rotationVector, deltaY);
        if(rotation.y < -360){
            rotation.y = rotation.y + 360;
        }
        if(rotation.y > 360){
            rotation.y = rotation.y - 360;
        }
        Gdx.input.setCursorPosition(640 / 2, 480 / 2);
    }

    public float getCameraRotation() {
        float camAngle = -(float)Math.atan2(camera.up.x, camera.up.y)* MathUtils.radiansToDegrees + 180;
        return camAngle;
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public void setCamera(PerspectiveCamera camera) {
        this.camera = camera;
    }

    public Vector3 getMoveVector() {
        return moveVector;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public float[] getMaxHitBoxPoints() {
        return maxHitBoxPoint;
    }

    public float[] getMinHitBoxPoints() {
        return minHitBoxPoint;
    }

    public Vector3 getPosition() {
        return position;
    }

}