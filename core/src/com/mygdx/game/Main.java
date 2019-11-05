package com.mygdx.game;

import Objects.CameraObject;
import Colision.Colision;
import Objects.Entity;
import Objects.Ground;
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

    public void create (){
        int sum = 0;
        for (int i = 100; i != 0; i--){
            sum += i;
        }
        System.out.println(sum);
        ModelLoader loader = new ObjLoader();

        Mathematics m = new Mathematics();
        m.rotatePointInMatrix(3, 2, 90);
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 10, 0);
        camera.lookAt(0f, camera.position.y, 0f);

        camera.near = 0.1f;
        camera.far = 1000;

        cameraObject = new CameraObject(camera, new float[]{0.3f, 0.1f, 0.3f}, new float[]{-0.3f, -1f, -0.3f}, camera.position, new Vector3(0, 0, 0));
        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0, 0, 0));
        environment.add(new PointLight().set(Color.YELLOW, cameraObject.getCamera().position, 4));
        environment.add();
/*
        for (float x = 1f; x < 2f; x++) {
            for (float z = -1f; z < 2f; z++){
                Model model = loader.loadModel(Gdx.files.internal("Models/BigEnemy.obj"));
                ModelInstance modelInstance = new ModelInstance(model);
                Entity ent = new Entity(model, new Vector3(x * 10, 0, z* 10), new float[]{1f, 2f, 1f} , new float[]{1f, 2f, 1f}, new Vector3(0, 45, 0));
                instances.add(ent);
            }
        }*/
        Model model = loader.loadModel(Gdx.files.internal("Models/Ground.obj"));
        Ground chao = new Ground(model, new Vector3(0, -0.7f, 0), new float[]{5f, 0f, 5f}, new float[]{-5f, 0.1f, -5f}, new Vector3(0, 0, 0));

        entities.add(chao);
        modelInstance = new ModelInstance(loader.loadModel(Gdx.files.internal("Models/collisionTest.obj")));
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
        colision.CollisionTestWithCamera(entities, cameraObject);
        cameraObject.update();
        cameraObject.getCamera().update();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        modelInstance.transform.setToTranslation(cameraObject.getCamera().position);
        modelInstance.transform.rotate(Vector3.X, cameraObject.getCameraRotation());
        modelBatch.begin(cameraObject.getCamera());
        System.out.println(cameraObject.getCamera().position.x + " " + cameraObject.getCamera().position.z);
        modelBatch.render(modelInstance, environment);

        for(int i = 0; i < entities.size(); i++){
            entities.get(i).update();
            modelBatch.render(entities.get(i).getModelInstance(), environment);
        }
        modelBatch.end();
    }
}
