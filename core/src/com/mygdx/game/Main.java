package com.mygdx.game;

import Map.MapLoader;
import Objects.CameraObject;
import Colision.Colision;
import Objects.Entity;
import Objects.Ground;
import Objects.Wall;
import Tools.Mathematics;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends ApplicationAdapter {

    public PerspectiveCamera camera;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public AssetManager assets;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public Environment environment;
    public boolean loading;
    public Entity entity;
    public ArrayList<ModelInstance> blocks = new ArrayList<ModelInstance>();
    public ArrayList<ModelInstance> invaders = new ArrayList<ModelInstance>();
    public CameraObject cameraObject;
    public Colision colision = new Colision();
    public ModelInstance modelInstance;
    public ArrayList<Entity> map = new ArrayList<>();

    public void create (){
        ModelLoader loader = new ObjLoader();

        Mathematics m = new Mathematics();
        m.rotatePointInMatrix(3, 2, 90);
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 15, 0);
        camera.lookAt(0f, camera.position.y, 0f);

        camera.near = 0.1f;
        camera.far = 1000;

        cameraObject = new CameraObject(camera, new float[]{0.2f, 0.1f, 0.2f}, new float[]{-0.2f, -1f, -0.2f}, camera.position, new Vector3(0, 0, 0));
        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new PointLight().set(Color.ORANGE, new Vector3(1, 1, 2), 3));
        environment.add();
        MapLoader mapLoader = new MapLoader();
        ArrayList<Entity> mapObjects = new ArrayList<>();
        try {
            mapObjects = mapLoader.createMap();
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

        Ground chao = new Ground(model, new Vector3(0, -0.7f, 0), new float[]{500f, 0.7f, 500f}, new float[]{-500f, 0f, -500f}, new Vector3(0, 0, 0));

        map.add(chao);
        //modelInstance = new ModelInstance(loader.loadModel(Gdx.files.internal("Models/collisionTest.obj")));
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
        //colision.CollisionTestWithCamera(entities, cameraObject);
        //colision.CollisionTestWithCamera(map, cameraObject);

        cameraObject.update(entities, map);
        cameraObject.getCamera().update();

//        modelInstance.transform.setToTranslation(cameraObject.getCamera().position);
//        modelInstance.transform.rotate(Vector3.X, cameraObject.getCameraRotation());
        modelBatch.begin(cameraObject.getCamera());
        //System.out.println(cameraObject.getCamera().position.x + " " + cameraObject.getCamera().position.z);
        //modelBatch.render(modelInstance, environment);

        for (int i = 0; i < map.size(); i++){
            map.get(i).update();
            modelBatch.render(map.get(i).getModelInstance(), environment);
        }
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).update();
            modelBatch.render(entities.get(i).getModelInstance(), environment);

        }
        modelBatch.end();
    }
}
