package com.thebyteguru.graphics;

import java.awt.image.BufferedImage;

import com.thebyteguru.utils.ResourceLoader;

public class TextureAtlas {

	BufferedImage	image;
//получает сылку на папку где лежит картинка 
	public TextureAtlas(String imageName) {
		image = ResourceLoader.loadImage(imageName);
	}
//далее вырезает по заданым параметрамм 
	public BufferedImage cut(int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

}
