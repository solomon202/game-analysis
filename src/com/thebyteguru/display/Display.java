package com.thebyteguru.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

import com.thebyteguru.IO.Input;
   
public abstract class Display {
	// дисплей имеет 
	private static boolean			created	= false;
	//Это верхний контейнер, на котором строятся графические интерфейсы
	private static JFrame			window;
	//«чистый лист», на котором вы можете рисовать фигуры
	private static Canvas			content;
 //класс  основным инструментом для представления изображений в памяти работа с цветом 
	// После загрузки изображения в память с ним можно работать как с единым объектом
	private static BufferedImage	buffer;
	private static int[]			bufferData;
	private static Graphics			bufferGraphics;
	private static int				clearColor;
  //картинка ресуется за рания чтобы небыло мерцания 
	private static BufferStrategy	bufferStrategy;
	
	public static void create(int width, int height, String title, int _clearColor, int numBuffers) {

		if (created)
			return;
//создаем окно 
		window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content = new Canvas();

		Dimension size = new Dimension(width, height);
		content.setPreferredSize(size);

		window.setResizable(false);
		window.getContentPane().add(content);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
//рисуем картинку и в тавляем как контент 
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
		bufferGraphics = buffer.getGraphics();
		((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 	clearColor = _clearColor;

		content.createBufferStrategy(numBuffers);
		bufferStrategy = content.getBufferStrategy();

		created = true;

	}
//заливает цветом 
	public static void clear() {
		Arrays.fill(bufferData, clearColor);
	}
   //метод меняе на новую картинку кадра получая картинку 
	public static void swapBuffers() {
		Graphics g = bufferStrategy.getDrawGraphics();
		//Рисует другое изображение на текущем с возможностью масштабирования и позиционирования
		g.drawImage(buffer, 0, 0, null);
		bufferStrategy.show();
	}
  
   public static Graphics2D getGraphics() {
	return (Graphics2D) bufferGraphics;
	}

	public static void destroy() {

		if (!created)
			return;

		window.dispose();

	}

	public static void setTitle(String title) {

		window.setTitle(title);

	}

	public static void addInputListener(Input inputListener) {
		window.add(inputListener);
	}

}
