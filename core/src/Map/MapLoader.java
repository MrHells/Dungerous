package Map;

import Objects.Entity;
import Objects.Wall;
import com.badlogic.gdx.math.Vector3;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public ArrayList<Entity> createMap(ArrayList<Entity> entities) throws FileNotFoundException {
        File txt = new File("map1.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<ArrayList<String>> map = new ArrayList<>() ;
        int counter = 0;
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
                    mapEntities.add(new Wall(new Vector3(i* 0.5f, 0 , j*0.5f), ));
                } else if(classCode.equals("M")){
                    //carrega um mago no array de entidades
                } else if(classCode.equals("C")){
                    //carrega um baú no arrayDe entidades
                } else if(classCode.equals("F")){
                    // carrega uma parede falsa(que quebra ou que pode ser atravessada)(deve ser entidade)
                } else if (classCode.equals("T")){
                    //carrega uma tocha no mapa( um modelo que tem uma luz na mesma posição)
                }
            }
        }

        return entities;
    }
}
