package com.mygdx.game;

import Images.ImageObject;
import Map.MapLoader;
import Objects.*;
import Colision.Colision;
import Objects.Enemies.MeleeEnemy;
import Objects.Enemies.RangedEnemy;
import Objects.Itens.Item;
import Tools.Mathematics;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import javax.sound.sampled.Port;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends ApplicationAdapter {

    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public static ArrayList<Entity> entities = new ArrayList<Entity>();
    public Environment environment;
    public Entity entity;
    public CameraObject cameraObject;
    public ArrayList<Entity> map = new ArrayList<>();
    public ArrayList<Item> worldItems = new ArrayList<>();
    public SpriteBatch spriteBatch;
    public int level = 1;
    public boolean isLevelPassed;
    public void create (){
        isLevelPassed = false;
        ModelLoader loader = new ObjLoader();

        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(3f, 2, 3);
        camera.lookAt(0f, camera.position.y, 0f);

        camera.near = 0.1f;
        camera.far = 1000;

        cameraObject = new CameraObject(camera, new float[]{0.2f, 0.1f, 0.2f}, new float[]{-0.2f, -1f, -0.2f});
        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new PointLight().set(Color.ORANGE, new Vector3(1, 1, 2), 3));
        environment.add();
        MapLoader mapLoader = new MapLoader();
        ArrayList<Entity> mapObjects = new ArrayList<>();
        try {
            mapObjects = mapLoader.createMap(level);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Entity mapObject : mapObjects){
            if(mapObject.getClass() == Wall.class){
                map.add(mapObject);
            }else{
                entities.add(mapObject);
            }
        }

        Model model = loader.loadModel(Gdx.files.internal("Models/Ground.obj"));
        model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Ground_texture.png")));
        Ground chao = new Ground(model, new Vector3(0, -0.7f, 0), new float[]{500f, 0.7f, 500f}, new float[]{-500f, 0f, -500f}, new Vector3(0, 0, 0));

        map.add(chao);

        model = loader.loadModel(Gdx.files.internal("Models/Ground.obj"));
        model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Ground_texture.png")));
        chao = new Ground(model, new Vector3(0, 3.3f, 0), new float[]{500f, 0.7f, 500f}, new float[]{-500f, 0f, -500f}, new Vector3(0, 0, 0));

        map.add(chao);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void dispose(){
        modelBatch.dispose();
    }

    public void resume(){
    }

    public void pause(){
    }

    public void input(){
    }

    public void render(){
        input();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        cameraObject.update(entities, map, worldItems);
        cameraObject.getCamera().update();

        modelBatch.begin(cameraObject.getCamera());

        for (int i = 0; i < map.size(); i++){
            map.get(i).update();
            modelBatch.render(map.get(i).getModelInstance(), environment);
        }
        ArrayList<Entity> entitiesToDelete = new ArrayList<>();
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i).getClass() == Portal.class){
                ((Portal)entities.get(i)).update(cameraObject);
                if(((Portal)entities.get(i)).isLevelPassed){
                    isLevelPassed = true;
                }
            }else{
                entities.get(i).update(cameraObject, map);
            }
            if(entities.get(i).getHealth() <= 0){
                entitiesToDelete.add(entities.get(i));
            }
            modelBatch.render(entities.get(i).getModelInstance(), environment);
        }
        if (cameraObject.getShoot() != null){
            cameraObject.getShoot().update(entities, map);
            if(cameraObject.getShoot().getHealth() <= 0){
                cameraObject.setShoot(null);
            }else{
                modelBatch.render(cameraObject.getShoot().getModelInstance(), environment);
            }
        }
        for(Entity entityToDelete : entitiesToDelete){
            entities.remove(entityToDelete);
            if(entityToDelete.getClass() == MeleeEnemy.class){
                ModelLoader loader = new ObjLoader();
                Model model =  loader.loadModel(Gdx.files.internal("Models/Adaga.obj"));
                model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Adaga_texture.png")));
                ImageObject imageObject = new ImageObject(new Vector2(0, 0), new Texture("Images/Adaga_image.png"), true);
                worldItems.add(new Item(new ModelInstance(model), entityToDelete.getPosition(), entityToDelete.getMaxHitBoxPoints(), entityToDelete.getMinHitBoxPoints(), imageObject, false, 10, false, 10));
            }else if(entityToDelete.getClass() == RangedEnemy.class){
                ModelLoader loader = new ObjLoader();
                Model model =  loader.loadModel(Gdx.files.internal("Models/Cajado.obj"));
                model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Cajado_texture.png")));
                ImageObject imageObject = new ImageObject(new Vector2(0, 0), new Texture("Images/Cajado_image.png"), true);
                worldItems.add(new Item(new ModelInstance(model), entityToDelete.getPosition(), entityToDelete.getMaxHitBoxPoints(), entityToDelete.getMinHitBoxPoints(), imageObject, true, 10, false, 2));
            }
        }
        for (Item item : worldItems){
            modelBatch.render(item.getModelInstance(), environment);
        }
        modelBatch.end();
        spriteBatch.begin();
        if(cameraObject.getInventory().inventorySprite.draw){
            spriteBatch.draw(cameraObject.getInventory().inventorySprite.image, cameraObject.getInventory().inventorySprite.position.x, cameraObject.getInventory().inventorySprite.position.y);
            for (Item[] items : cameraObject.getInventory().items){
                for (Item item : items){
                    if(item != null){
                        spriteBatch.draw(item.getImageObject().image, item.getImageObject().position.x, item.getImageObject().position.y);
                    }
                }
            }
        }

        spriteBatch.end();
        if(isLevelPassed){
            entities = new ArrayList<>();
            worldItems = new ArrayList<>();
            map = new ArrayList<>();

            dispose();
            create();
            level += 1;
        }
        if(cameraObject.getHealth() <= 0){
            entities = new ArrayList<>();
            worldItems = new ArrayList<>();
            map = new ArrayList<>();

            dispose();
            create();
        }
    }
}
