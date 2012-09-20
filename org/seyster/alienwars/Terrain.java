//The terrain class handles drawing of and collision with
//the buildings.

package org.seyster.alienwars;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Terrain 
{
	public static final int numBuildings = 6;

	private Image tImage;	//This will hold the image of the buildings
	private Graphics tContext;
	private ImageObserver tObs;

	private Image windows;	//These hold the bitmaps needed to draw the buildings
	private Image roof;

	private int[] buildings = new int[6];	//Hold the heights of the buildings
	private boolean[][] map = new boolean[350][300];	//The collision map

	//Constructor needs image to store image of terrain
	//and bitmaps for drawing the terrain
	Terrain(Image img, ImageObserver obs, Image imgWind, Image imgRoof)
	{
		tImage = img;
		tContext = tImage.getGraphics();

		tObs = obs;

		windows = imgWind;
		roof = imgRoof;

		//Initialize the collision map
		for (int i = 0 ; i < 350 ; i++)
			for (int j = 0 ; j < 300 ; j++)
				map[i][j] = false;

		createTerrain();
	}

	private void createTerrain()
	{
		//Randomly select building heights
		for (int p = 0 ; p <= 5 ; p++)
		{
				buildings[p] = (int)(Math.random() * 7 + 3);
		}

		//Draw the buildings
		tContext.setColor(Color.black);
		tContext.fillRect(0, 0, 350, 300);
	}

	//Draw the initial terrain
	public void draw()
	{
		for (int i = 0 ; i < numBuildings ; i++)
		{
			for (int j = 1 ; j <= buildings[i] ; j++)
			{
				tContext.drawImage(windows, (75 * i) - 37, 350 - (21 * j), tObs);
			}

			tContext.drawImage(roof, -37 + 75 * i, 350 - 21 * (buildings[i] + 1), tObs);

			//Set the collision map while we're at it
			fillCollMap((75 * i) - 37, 350 - (21 * (buildings[i] + 1)), 75, 500);
		}

		/*For testing
		tContext.setColor(Color.red);
		for (int i = 0 ; i < 350 ; i++)
			for (int j = 0 ; j < 300 ; j++)
				if (map[i][j])
					tContext.fillRect(i, j, 1, 1);*/
	}

	//Fill a section of the collision map
	private void fillCollMap(int x, int y, int width, int height)
	{
		//Prevent array out of bounds
		if (x < 0)
		{
			width += x;
			x = 0;
		}
		else if (x >= 350)
		{
			return;	//Don't do anything!
		}

		if (y < 0)
		{
			height += y;
			y = 0;
		}
		else if (y >= 300)
		{
			return;
		}

		if (x + width >= 350)
			width = 349 - x;
		
		if (y + height >= 300)
			height = 299 - y;

		for (int i = x ; i <= x + width ; i++)
			for (int j = y ; j <= y + height ; j++)
				map[i][j] = true;
	}

	private void clearCollCircle(int x, int y)
	{
		int radius = 8;
		int radiusSquared = 64;

		for (int i = -radius ; i <= radius ; i++)
			for (int j = -radius ; j <= radius ; j++)
				if (i * i + j * j <= radiusSquared && x + i >= 0 && x + i < 350 &&
					y + j >= 0 && y + j < 300)
				{
					map[x + i][y + j] = false;

					tContext.setColor(Color.black);
					tContext.fillRect(x + i, y + j, 1, 1);
				}
	}

	public int getHeight(int index)
	{
		if (index > 5 || index < 0)
			return 0;
		else
			return 350 - ((buildings[index] + 1) * 21);
	}

	public boolean collide(int x, int y)
	{
		/* Old collision detection code
		int index = (int)((x - 37) / 75) + 1;

		if (y >= getHeight(index))
			return true;
		else
			return false;	*/

		//Prevent array out of bounds
		if (x < 0 || x >= 350 || y < 0 || y >= 300)
			return true;

		if (map[x][y])
		{
			clearCollCircle(x, y);
			return true;
		}
		else
		{
			return false;
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(tImage, 0, 0, tObs);
	}
}