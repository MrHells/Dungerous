package Images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ImageObject {
    public Vector2 position;
    public Texture image;
    public boolean draw;

    public ImageObject(Vector2 position, Texture image, boolean draw){
        this.position = position;
        this.image = image;
        this.draw = draw;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public void changeDraw(){
        if(draw){
            draw = false;
        }else{
            draw = true;
        }
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }
}
