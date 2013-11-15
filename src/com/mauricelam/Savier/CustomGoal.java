package com.mauricelam.Savier;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:58 PM
 */
public class CustomGoal extends Goal {

    private String url;
    private String imageUrl;

    public CustomGoal(int target, String url, String imageUrl) {
        super(target);
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImageUrl() {
        return this.imageUrl;
    }

    public CustomGoal fromUrl(String url, int target) {
        // FIXME ---------------
        String imageUrl = "http://www.veryicon.com/icon/png/System/Sticker%20System/Customize.png";
        // FIXME ---------------

        return new CustomGoal(target, url, imageUrl);
    }
}
