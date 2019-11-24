package Objects.Itens;


import Images.ImageObject;
import Objects.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Inventory {
    public Item[][] items = new Item[6][6];
    public ImageObject inventorySprite;
    public int selectedItemPosition = 0;

    public Inventory(){
        inventorySprite = new ImageObject(new Vector2(0, 0), new Texture("Images/Inventory.png"), false);
    }

    public boolean addItem(Entity itemToAdd){
        for (int i = 0; i < items.length; i++){
            for (int j = 0; j < items[i].length; j++) {
                Item item = (Item)items[i][j];
                if (item == null){
                    items[i][j] = ((Item) itemToAdd);
                    items[i][j].getImageObject().setPosition(new Vector2( (j  * 90) + 10 * (j + 1), 1080 - ((i+1) * 90 + 10 *(i + 1))));
                    return true;
                } else {
                    if(item.isSumable()){
                        item.setQuant( item.getQuant() + 1);
                        items[i][j] = item;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ImageObject getInventorySprite() {
        return inventorySprite;
    }

    public void setInventorySprite(ImageObject inventorySprite) {
        this.inventorySprite = inventorySprite;
    }

    public Entity[][] getItems() {
        return items;
    }

    public Item getSelectedItem() {
        return items[items.length-1][selectedItemPosition];
    }
}

