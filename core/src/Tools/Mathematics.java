package Tools;

public class Mathematics {
    public static float[] rotatePointInMatrix(float firstPoint, float secondPoint, float degrees){
        float degreesCos = (float) java.lang.Math.cos(degrees);
        float degreesSin = (float) java.lang.Math.sin(degrees);

        float[] newValues = new float[2];

        newValues[0] = firstPoint * degreesCos + secondPoint * degreesSin;
        newValues[1] = firstPoint * -degreesSin + secondPoint * degreesCos;

        return newValues;
    }
    public static float[] rotatePointInMatrix(float firstPoint, float secondPoint, float cos, float sin){


        float[] newValues = new float[2];

        newValues[0] = firstPoint * cos + secondPoint * sin;
        newValues[1] = firstPoint * -sin + secondPoint * cos;

        return newValues;
    }

}
