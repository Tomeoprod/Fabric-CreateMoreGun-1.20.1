package net.tomeoprod.more_gun.util;

public class Image2d<Item, Vec2f> {
    private Item item;
    private Vec2f vec2f;

    public Image2d(Item item, Vec2f coords) {
        this.item = item;
        this.vec2f = coords;
    }

    public Item getItem() {
        return item;
    }

    public Vec2f getCoords() {
        return vec2f;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCoords(Vec2f coords) {
        this.vec2f = coords;
    }
}
