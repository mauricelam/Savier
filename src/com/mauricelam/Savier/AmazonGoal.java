package com.mauricelam.Savier;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:50 PM
 */
public class AmazonGoal extends Goal {

    private String imageUrl;
    private String amazonId;

    protected AmazonGoal() {
        // no-arg constructor for GSON
        super();
    }

    public AmazonGoal(String name, int target, String amazonId, String imageUrl, String url) {
        super(name, target, url);
        this.amazonId = amazonId;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImageURL() {
        return this.imageUrl;
    }

    public static AmazonGoal fromId(String amazonId) {
        // Query the amazon API here, and set the correct target

        // FIXME ---------------
        int target = 10000;
        String imageUrl = "http://www.blogcdn.com/www.joystiq.com/media/2011/10/amazon-logo.png";
        String url = "http://www.amazon.com/gp/product/0743264738/ref=s9_qpp_gw_p14_d99_i2?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=center-4&pf_rd_r=16SQ7XB1AD4VC2NG0ERF&pf_rd_t=101&pf_rd_p=1630083682&pf_rd_i=507846";
        // FIXME ---------------

        return new AmazonGoal("Test Amazon Goal", target, amazonId, imageUrl, url);
    }
}
