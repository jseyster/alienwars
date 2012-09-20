package org.seyster.alienwars;

import java.awt.*;

public class HUD 
{
	int side;
	int angle = 0;
	int power = 0;

	HUD (int n)
	{
		side = n;
	}

	public void incAngle()
	{
		if (angle < 90)
		{
			angle++;
		}
	}

	public void decAngle()
	{
		if (angle > 0)
		{
			angle--;
		}
	}

	public void incPower()
	{
		if (power < 500)
		{
			power++;
		}
	}

	public void decPower()
	{
		if (power > 0)
		{
			power--;
		}
	}

	public void reset()
	{
		angle = 0;
		power = 250;
	}

	public double getX()
	{
		double rAngle = (angle * Math.PI) / 180;
		return (int)((power / 25) * Math.cos(rAngle));
	}

	public double getY()
	{
		double rAngle = (angle * Math.PI) / 180;
		return (int)(0 - (power / 25) * Math.sin(rAngle));
	}

	public void draw(Graphics g)
	{
		int x, y, bar;
		double rAngle = (angle * Math.PI) / 180;
		String sAngle = new Integer(angle).toString();
		String sPower = new Integer(power).toString();
		FontMetrics HUDMetric = g.getFontMetrics();
		g.setColor(Color.green);
		if (side == 0)
		{
			g.drawLine(10, 10, 10, 60);
			g.drawLine(10, 60, 60, 60);
			g.drawArc(-40, 10, 100, 100, 0, 90);
			x = (int)(10 + Math.cos(rAngle) * 50);
			y = (int)(60 - Math.sin(rAngle) * 50);
			g.drawLine(10, 60, x, y);
			g.drawString(sAngle, 35 - HUDMetric.stringWidth(sAngle) / 2, 75);
			g.drawRect(10, 80, 50, 10);
			bar = power / 10;
			g.fillRect(10, 80, bar, 10);
			g.drawString(sPower, bar + 10 - HUDMetric.stringWidth(sPower) / 2, 105);
		}
		if (side == 1)
		{
			g.drawLine(340, 10, 340, 60);
			g.drawLine(340, 60, 290, 60);
			g.drawArc(290, 10, 100, 100, 90, 90);
			x = (int)(340 - Math.cos(rAngle) * 50);
			y = (int)(60 - Math.sin(rAngle) * 50);
			g.drawLine(340, 60, x, y);
			g.drawString(sAngle, 315 - HUDMetric.stringWidth(sAngle) / 2, 75);
			g.drawRect(290, 80, 50, 10);
			bar = power / 10;
			g.fillRect(290, 80, bar, 10);
			g.drawString(sPower, bar + 290 - HUDMetric.stringWidth(sPower) / 2, 105);
		}
	}
}

