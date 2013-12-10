package com.mauricelam.Savier;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:58 PM
 */
public class CustomGoal extends Goal {

    private String imageUrl;

    protected CustomGoal() {
        // no-arg constructor for GSON
        super();
    }

    public CustomGoal(String name, int target, String url, String imageUrl) {
        super(name, target, url, "");
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImageURL() {
        return this.imageUrl;
    }

    public CustomGoal fromUrl(String url, int target) {
        // FIXME ---------------
        String imageUrl = "http://www.veryicon.com/icon/png/System/Sticker%20System/Customize.png";
        // FIXME ---------------

        return new CustomGoal("Test custom goal", target, url, imageUrl);
    }
}
