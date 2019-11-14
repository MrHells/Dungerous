package Map;

import Objects.Entity;
import Objects.Ground;
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
    public ArrayList<Entity> createMap() throws FileNotFoundException {
        File txt = new File("core\\assets\\Maps\\map1.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<ArrayList<String>> map = new ArrayList<>() ;
        int counter = 0;
        ModelLoader loader = new ObjLoader();


        while(scan.hasNextLine()){
            String textLine = scan.nextLine();
            ArrayList<String> mapLine = new ArrayList<>();
            for (int i = 0; i < textLine.length(); i++){
                mapLine.add(Character.toString(textLine.charAt(i)));
            }
            map.add(mapLine);
            counter++;
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
                    mapEntities.add(new Ground(new ModelInstance(model), new Vector3(i, 0, j), new float[]{5, 0, 5}, new float[]{-5, 0, -5}));


                } else if(classCode.equals("C")){
                    //carrega um baú no arrayDe entidades
                } else if(classCode.equals("F")){
                    // carrega uma parede falsa(que quebra ou que pode ser atravessada)(deve ser entidade)
                } else if (classCode.equals("T")){
                    //carrega uma tocha no mapa( um modelo que tem uma luz na mesma posição)
                }
            }
        }

        return mapEntities;
    }
}
