package Tools;

public class Mathematics {
    public float[] rotatePointInMatrix(float firstPoint, float secondPoint, float degrees){
        float degreesCos = (float) java.lang.Math.cos(degrees);
        float degreesSin = (float) java.lang.Math.sin(degrees);

        float[] newValues = new float[2];
        //System.out.println(degreesCos);

        newValues[0] = firstPoint * degreesCos + secondPoint * degreesSin;
        newValues[1] = firstPoint * -degreesSin + secondPoint * degreesCos;
        //System.out.println(newValues[0] + " " + newValues[1]);

        return newValues;
    }
}
