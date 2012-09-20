package org.seyster.alienwars;

import java.applet.Applet;
import java.awt.*;

//
//  Alien Wars!
//
public class AlienWars extends Applet implements Runnable
{
	int CProc = 0;
	int CPlayer = 0;
	int GVictor = 0;
	int GP1Height;
	int GP2Height;
	int GXRadius = 17;
	int GYRadius = 23;
	int GXP1Hit;
	int GYP1Hit;
	int GXP2Hit;
	int GYP2Hit;
	int GWind;
	double blobX = 55;
	double blobY = 155;
	boolean GKeyUp = false;
	boolean GKeyDown = false;
	boolean GKeyLeft = false;
	boolean GKeyRight = false;
	Thread kicker;
	Image GBuffer;
	Graphics GContext;
	MediaTracker GMedia;
	Terrain GTerrain;
	Image TImage;
	int GLoaded = 0;
	//Nice Fonts!!!
	Font GStandard = new Font("helvetica", 0, 12);
	Font GBold = new Font("helvetica", Font.BOLD, 14);
	Font GHeading = new Font("helvetica", Font.BOLD, 24);
	//HUD's
	HUD GHUDLeft = new HUD(0);
	HUD GHUDRight = new HUD(1);
	//images
	Image GUFO;
	Image GUFO2;
	Image GAlien;
	Image GAlienRight;
	Image GBuilding;
	Image GRoof;

	public void init()
	{
		GBuffer = createImage(350, 300);
		GContext = GBuffer.getGraphics();
		TImage = createImage(350, 300);
		GMedia = new MediaTracker(this);
		GUFO = getImage(getCodeBase(), "UFO.gif");
		GMedia.addImage(GUFO, 0);
		GUFO2 = getImage(getCodeBase(), "UFO2.gif");
		GMedia.addImage(GUFO2, 1);
		GAlien = getImage(getCodeBase(), "alien.gif");
		GMedia.addImage(GAlien, 2);
		GAlienRight = getImage(getCodeBase(), "ralien.gif");
		GMedia.addImage(GAlienRight, 3);
		GBuilding = getImage(getCodeBase(), "building.gif");
		GMedia.addImage(GBuilding, 4);
		GRoof = getImage(getCodeBase(), "roof.gif");
		GMedia.addImage(GRoof, 5);

		GTerrain = new Terrain(TImage, this, GBuilding, GRoof);

		GP1Height = GTerrain.getHeight(1) - 35;
		GP2Height = GTerrain.getHeight(4) - 35;
		GXP1Hit = 57;
		GYP1Hit = GP1Height + 20;
		GXP2Hit = 312;
		GYP2Hit = GP2Height + 20;
		GHUDLeft.reset();
		GHUDRight.reset();
	}

	public void start()
	{
		if (kicker == null)
		{
			kicker = new Thread(this);
			kicker.start();
		}
	}

	public void stop()
	{
		kicker.stop();
		kicker = null;
	}

	public void run()
	{
		//Load Images
		CProc = 0;
		GLoaded = 0;
		for (int p = 0 ; p <= 5 ; p++)
		{
			try
			{
				GMedia.waitForID(p);
			}
			catch (InterruptedException e)
			{
				return;
			}
			GLoaded++;
			repaint();
		}

		//Menu
		CProc = 1;
		while(CProc == 1)
		{
			try
			{
				kicker.sleep(500);
			}
			catch(Exception e)
			{
				return;
			}
		}

		//Set up the terrain
		GTerrain.draw();

		while (GVictor == 0)
		{
			while(CProc == 2)
			{
				if (GKeyUp)
				{
					if (CPlayer == 0)
					{
						GHUDLeft.incAngle();
					}
					if (CPlayer == 1)
					{
						GHUDRight.incAngle();
					}
				}
				if (GKeyDown)
				{
					if (CPlayer == 0)
					{
						GHUDLeft.decAngle();
					}
					if (CPlayer == 1)
					{
						GHUDRight.decAngle();
					}
				}
				if (GKeyLeft)
				{
					if (CPlayer == 0)
					{
						GHUDLeft.decPower();
					}
					if (CPlayer == 1)
					{
						GHUDRight.decPower();
					}
				}
				if (GKeyRight)
				{
					if (CPlayer == 0)
					{
						GHUDLeft.incPower();
					}
					if (CPlayer == 1)
					{
						GHUDRight.incPower();
					}
				}

				repaint();
				try
				{
					kicker.sleep(10);
				}
				catch(Exception e)
				{
					return;
				}
			}

			//Time to fire!!!
			double x = 0;
			double y = 0;
			double n;
			if (CPlayer == 0)
			{
				x = GHUDLeft.getX();
				y = GHUDLeft.getY();
				blobX = 75;
				blobY = GP1Height + 15;
			}
			if (CPlayer == 1)
			{
				x = GHUDRight.getX();
				y = GHUDRight.getY();
				blobX = 290;
				blobY = GP2Height + 15;
			}
			n = (x + y) / 2;
			x /= 4;
			y /= 4;
			while(CProc == 3)
			{
				if (CPlayer == 0)
				{
					blobX += x;
				}
				else if (CPlayer == 1)
				{
					blobX -= x;
				}
				blobY += y;
				y += .06; //gravity
				if (GTerrain.collide((int)blobX, (int)blobY))
				{
					CProc = 2;
					if (CPlayer == 0)
					{
						CPlayer = 1;
					}
					else if (CPlayer == 1)
					{
						CPlayer = 0;
					}
					//Was it a hit!
					if (blobX >= GXP1Hit - GXRadius && blobX <= GXP1Hit + GXRadius)
					{
						if (blobY >= GYP1Hit - GYRadius && blobY <= GYP1Hit + GYRadius)
						{
							GVictor = 2;
							CProc = 4;
						}
					}
					else if (blobX >= GXP2Hit - GXRadius && blobX <= GXP2Hit + GXRadius)
					{
						if (blobY >= GYP2Hit - GYRadius && blobY <= GYP2Hit + GYRadius)
						{
							GVictor = 1;
							CProc = 4;
						}
					}
				}
				repaint();
				try
				{
					kicker.sleep(50);
				}
				catch(Exception e)
				{
					return;
				}
			}
		}

		//Game
		kicker.stop();
		kicker = null;
	}
	
	public void paint(Graphics g)
	{
		if (CProc == 0)
		{
			GContext.setFont(GBold);
			int textWidth = g.getFontMetrics(GBold).stringWidth("Loading Images...");
			GContext.drawImage(GUFO, 0, 0, this);
			GContext.setColor(new Color(255, 153, 51));
			GContext.drawString("Loading Images...", 175 - textWidth / 2, 150);
			GContext.drawRect(315, 60, 25, 200);
			int StatusHeight = GLoaded * (200 / 6);
			GContext.fillRect(315, 260 - StatusHeight, 25, StatusHeight);
			g.drawImage(GBuffer, 0, 0, this);
			GContext.setColor(Color.black);
			GContext.setFont(GStandard);
		}
		else if (CProc == 1)
		{
			GContext.drawImage(GUFO, 0, 0, this);
			GContext.drawImage(GUFO2, 40, 60, this);
			g.drawImage(GBuffer, 0, 0, this);
		}
		else if (CProc == 2)
		{
			GTerrain.paint(GContext);
			GHUDLeft.draw(GContext);
			GHUDRight.draw(GContext);
			GContext.drawImage(GAlien, 40, GP1Height, this);
			GContext.drawImage(GAlienRight, 295, GP2Height, this);
			g.drawImage(GBuffer, 0, 0, this);
		}
		else if (CProc == 3)
		{
			GTerrain.paint(GContext);
			GHUDLeft.draw(GContext);
			GHUDRight.draw(GContext);
			GContext.drawImage(GAlien, 40, GP1Height, this);
			GContext.drawImage(GAlienRight, 295, GP2Height, this);
			GContext.fillOval((int)blobX - 5, (int)blobY - 5, 10, 10);
			g.drawImage(GBuffer, 0, 0, this);
		}
		else if (CProc == 4)
		{
			int left;

			GTerrain.paint(GContext);
			GContext.drawImage(GAlien, 40, GP1Height, this);
			GContext.drawImage(GAlienRight, 295, GP2Height, this);
			GContext.setFont(GHeading);
			if (GVictor == 1)
			{
				left = 175 - GContext.getFontMetrics().stringWidth("Player One Wins!") / 2;
				GContext.drawString("Player One Wins!", left, 150);
			}
			if (GVictor == 2)
			{
				left = 175 - GContext.getFontMetrics().stringWidth("Player Two Wins!") / 2;
				GContext.drawString("Player Two Wins!", left, 150);
			}
			GContext.setFont(GStandard);

			left = 175 - GContext.getFontMetrics().stringWidth("Press space for new game") / 2;
			GContext.drawString("Press space for new game", left, 200);

			g.drawImage(GBuffer, 0, 0, this);
		}
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public boolean mouseUp(Event evt, int x, int y)
	{
		if (CProc == 1)
		{
			CProc = 2;
		}
		return true;
	}

	public boolean keyDown(Event evt, int key)
	{
		if (CProc == 2)
		{
			switch (key)
			{
				case Event.UP:
					GKeyUp = true;
				break;
				case Event.DOWN:
					GKeyDown = true;
				break;
				case Event.LEFT:
					GKeyLeft = true;
				break;
				case Event.RIGHT:
					GKeyRight = true;
				break;
			}
		}
		return true;
	}

	public boolean keyUp(Event evt, int key)
	{
		if (CProc == 2)
		{
			switch (key)
			{
				case Event.UP:
					GKeyUp = false;
				break;
				case Event.DOWN:
					GKeyDown = false;
				break;
				case Event.LEFT:
					GKeyLeft = false;
				break;
				case Event.RIGHT:
					GKeyRight = false;
				break;
				case (int)' ':
					CProc = 3;
				break;
			}
		}
		else if (CProc == 4 && key == (int)' ')
		{
			stop();
			CProc = 0;
			CPlayer = 0;
			GVictor = 0;
			init();
			start();
		}
		return true;
	}

}