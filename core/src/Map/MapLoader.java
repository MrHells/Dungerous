package Map;

import Objects.Enemies.MeleeEnemy;
import Objects.Enemies.RangedEnemy;
import Objects.Entity;
import Objects.Ground;
import Objects.Portal;
import Objects.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public ArrayList<Entity> createMap(int level) throws FileNotFoundException {
        File txt = new File("core\\assets\\Maps\\map"+level+".txt");
        Scanner scan = new Scanner(txt);
        ArrayList<ArrayList<String>> map = new ArrayList<>() ;
        ModelLoader loader = new ObjLoader();


        while(scan.hasNextLine()){
            String textLine = scan.nextLine();
            ArrayList<String> mapLine = new ArrayList<>();
            for (int i = 0; i < textLine.length(); i++){
                mapLine.add(Character.toString(textLine.charAt(i)));
            }
            map.add(mapLine);
        }
        ArrayList<Entity> mapEntities = new ArrayList<>();
        System.out.println(map);
        for (int i = 0; i < map.size(); i++){
            for (int j = 0; j < map.get(i).size(); j++){
                String classCode = map.get(i).get(j);
                if(classCode.equals("1")){
                    Model model = loader.loadModel(Gdx.files.internal("Models/Wall.obj"));
                    model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Wall_texture.png")));
                    mapEntities.add(new Wall(new ModelInstance(model), new Vector3(i , 0 , j)));
                } else if(classCode.equals("M")){
                    Model model = loader.loadModel(Gdx.files.internal("Models/Mage.obj"));
                    model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Mage_texture.png")));
                    mapEntities.add(new RangedEnemy(new ModelInstance(model), new Vector3(i, 0, j), new float[]{0.4f, 0, 0.4f}, new float[]{-0.4f, 0, -0.4f}, 5));
                } else if(classCode.equals("E")){
                    Model model = loader.loadModel(Gdx.files.internal("Models/Gost.obj"));
                    //model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Gost_texture.png")));
                    mapEntities.add(new MeleeEnemy(new ModelInstance(model), new Vector3(i, 0, j), new float[]{0.4f, 0, 0.4f}, new float[]{-0.4f, 0, -0.4f}, 5));
                } else if(classCode.equals("F")){
                    // carrega uma parede falsa(que quebra ou que pode ser atravessada)(deve ser entidade)
                } else if (classCode.equals("!")){
                    Model model = loader.loadModel(Gdx.files.internal("Models/Portal.obj"));
                    model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture("Textures/Portal_texture.png")));
                    mapEntities.add(new Portal(new ModelInstance(model), new Vector3(i, 0, j), new float[]{0.4f, 0, 0.4f}, new float[]{-0.4f, 0, -0.4f}, 5));
                }
            }
        }

        return mapEntities;
    }
}
