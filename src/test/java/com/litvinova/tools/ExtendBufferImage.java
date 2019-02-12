package com.litvinova.tools;

import java.awt.image.BufferedImage;

public class ExtendBufferImage{

    private BufferedImage bufferedImage;

    private static final int EPS = 10;

    public ExtendBufferImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public boolean looksLike(ExtendBufferImage image) {

        if (bufferedImage.getWidth() == image.bufferedImage.getWidth() &&
                bufferedImage.getHeight() == image.bufferedImage.getHeight()) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    if (Math.abs(bufferedImage.getRGB(x, y) - image.bufferedImage.getRGB(x, y)) > EPS)
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
