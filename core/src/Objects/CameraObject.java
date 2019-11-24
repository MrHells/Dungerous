package Objects;

import Colision.Colision;
import Objects.Itens.Inventory;
import Objects.Itens.Item;
import Tools.Mathematics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

import static com.mygdx.game.Main.entities;

public class CameraObject{

    private Vector3 moveVector = new Vector3();
    private PerspectiveCamera camera;
    private Vector3 rotationVector = new Vector3();
    private Vector3 rotation = new Vector3();
    private float[] maxHitBoxPoint;
    private float[] minHitBoxPoint;
    private int health = 20;
    private Inventory inventory;
    private Item handledItem = null;
    private Shoot shoot = null;
    public CameraObject(PerspectiveCamera camera, float[] maxHitBoxPoints, float[] minHitBoxPoints){
        this.camera = camera;
        this.maxHitBoxPoint = maxHitBoxPoints;
        this.minHitBoxPoint = minHitBoxPoints;
        inventory = new Inventory();
    }

    public void update(ArrayList<Entity> entities, ArrayList<Entity> map, ArrayList<Item> worldItems){
        cameraInputs(worldItems, map);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(inventory.getInventorySprite().draw){
                for (int i = 0; i < inventory.items.length;i++){
                    for (int j = 0; j < inventory.items[i].length;j++){
                        Item item = inventory.items[i][j];
                        if(item == null)
                            continue;
                        float mouseX = Gdx.input.getX();
                        float mouseY = 1080 - Gdx.input.getY();

                        if (item.getImageObject().position.x < mouseX && item.getImageObject().position.x + 90 > mouseX && item.getImageObject().position.y < mouseY && item.getImageObject().position.y + 90 > mouseY){
                            handledItem = item;
                            System.out.println("FOi");
                        }
                    }
                }
            }else{
                if(handledItem != null){
                    if(handledItem.isRanged()){
                        Vector3 move = calculateWayToWalk(1f);
                        ModelLoader loader = new ObjLoader();
                        Model model = loader.loadModel(Gdx.files.internal("Models/FireBall.obj"));
                        model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Mage_texture.png")));
                        shoot = new Shoot(new ModelInstance(model), camera.position, new float[]{0.183434f, 3, 0.183434f}, new float[]{-0.183434f, 0, -0.183434f}, handledItem.getDamage(), move, handledItem.getDamage());
                        handledItem.setHealth(handledItem.getHealth() - 1);

                    }
                }
                for (int i = 0; i < entities.size(); i++){
                    if(Colision.textLineIntersectionWithBox(new Colision.Box(entities.get(i).getPosition(), entities.get(i).getMaxHitBoxPoints(), entities.get(i).getMinHitBoxPoints(), null),
                            camera.position, new Vector3(camera.direction.x + camera.position.x, camera.direction.y + camera.position.y, camera.direction.z + camera.position.z)) ||
                            Colision.textLineIntersectionWithBox(new Colision.Box(entities.get(i).getPosition(), entities.get(i).getMaxHitBoxPoints(), entities.get(i).getMinHitBoxPoints(), null),
                                    camera.position, new Vector3(camera.direction.x / 2 + camera.position.x, camera.direction.y / 2 + camera.position.y, camera.direction.z / 2 + camera.position.z))){
                        if(handledItem == null){
                            entities.get(i).setHealth(entities.get(i).getHealth() - 1);
                        }else{
                            entities.get(i).setHealth(entities.get(i).getHealth() - handledItem.getDamage());
                        }
                    }
                }
            }
        }

        if(!inventory.getInventorySprite().draw){
            calculateCameraRotation(0, 0 , 0);
        }
    }

    public void cameraInputs(ArrayList<Item> worldItems, ArrayList<Entity> map){
        float velocity = 3;
        float side = 0;
        float forward = 0;
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){

            for (Item item : worldItems){
                if(Colision.textLineIntersectionWithBox(new Colision.Box(item.getPosition(),item.getMaxHitBoxPoints(), item.getMinHitBoxPoints(), null),
                        camera.position, new Vector3(camera.direction.x + camera.position.x, camera.direction.y + camera.position.y, camera.direction.z + camera.position.z)) ||
                        Colision.textLineIntersectionWithBox(new Colision.Box(item.getPosition(), item.getMaxHitBoxPoints(), item.getMinHitBoxPoints(), null),
                                camera.position, new Vector3(camera.direction.x / 2 + camera.position.x, camera.direction.y / 2 + camera.position.y, camera.direction.z / 2 + camera.position.z))){
                    inventory.addItem(item);
                    worldItems.remove(item);
                    break;
            }
        }

    }
        if (Gdx.input.isKeyJustPressed(Keys.I)) {
            inventory.getInventorySprite().changeDraw();
            Gdx.input.setCursorPosition(640/2, 480/2);
            Gdx.input.setCursorCatched(!inventory.getInventorySprite().draw);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
             forward = -0.016666f * velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            forward = 0.016666f * velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            side = 0.016666f * velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            side = -0.016666f * velocity;
        }
        float x = 0, z = 0;
        double hypotenuse = Math.sqrt(Math.pow(camera.direction.z, 2) + Math.pow(camera.direction.x, 2));
        float sin = (float) (camera.direction.x / hypotenuse);
        float cos = (float) (camera.direction.z / hypotenuse);

        float frontalDistanceX = forward * -cos;
        float frontalDistanceZ = forward * -sin;

        double sideHypotenuse = Math.sqrt(Math.pow(camera.direction.x, 2) + Math.pow(camera.direction.z, 2));
        float sideSin = (float) (camera.direction.x / sideHypotenuse);
        float sideCos = (float) (camera.direction.z / sideHypotenuse);

        float sideDistanceZ =  side * sideCos;
        float sideDistanceX =  side * -sideSin;

        Colision colision = new Colision();
        x += frontalDistanceZ + sideDistanceZ;
        camera.position.add(new Vector3(x, 0, 0));
        if(colision.CollisionTestWithCamera(map, this)){
            camera.position.add(new Vector3(-x, 0, 0));
        }
        z += frontalDistanceX + sideDistanceX;
        camera.position.add(new Vector3(0, 0, z));
        if(colision.CollisionTestWithCamera(map, this)){
            camera.position.add(new Vector3(0, 0, -z));
        }
        camera.position.add(new Vector3(0, -0.1f, 0));
        if (colision.CollisionTestWithCamera(map, this)){
            camera.position.add(new Vector3(0, 0.1f, 0));
        }
        moveVector.set(x, -0.1f, z);
    }

    public Vector3 calculateWayToWalk( float speed){
        float hypotenuse = calculateDistance();
        float cosX = camera.direction.x / hypotenuse;
        float xDistance = speed * cosX;

        hypotenuse = calculateDistance();
        float cosZ = camera.direction.z / hypotenuse;
        float zDistance = speed * cosZ;

        return new Vector3(xDistance, 0, zDistance);
    }

    public float calculateDistance(){
        float distanceX = camera.direction.x - camera.position.x;
        float distanceZ = camera.direction.z - camera.position.z;

        if(distanceX < 0){
            distanceX *= -1;
        }
        if(distanceZ < 0){
            distanceZ *= -1;
        }

        return (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceZ, 2));
    }

    public void calculateCameraRotation(int screenX, int screenY, int pointer){
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public void setShoot(Shoot shoot) {
        this.shoot = shoot;
    }
}