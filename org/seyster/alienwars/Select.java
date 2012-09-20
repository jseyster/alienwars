package org.seyster.alienwars;

import java.awt.*;

public class Select 
{
	boolean Selected;
	int top;
	int left;
	int width;
	int height;
	String SString;
	Font fSelect = new Font("helvetica", Font.BOLD, 12);
	Font fnSelect = new Font("helvetica", 0, 12);

	public Select(int x1, int y1, int x2, int y2, String label)
	{
		left = x1;
		top = y1;
		width = x2;
		height = y2;
		Selected = false;
		SString = label;
	}

	public Select(int x1, int y1, int x2, int y2, boolean initial, String label)
	{
		left = x1;
		top = y1;
		width = x2;
		height = y2;
		Selected = initial;
		SString = label;
	}

	public boolean isSelected()
	{
		return Selected;
	}

	public void setState()
	{
		Selected = !Selected;
	}

	public void setState(boolean state)
	{
		Selected = state;
	}

	public boolean inRect(int x, int y)
	{
		if (x >= left && x <= left + width && y >= top && y <= top + height)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void Draw(Graphics g)
	{
		Font oldFont = g.getFont();
		int base;
		if (Selected)
		{
			g.setFont(fSelect);
			base = g.getFontMetrics(fSelect).getHeight();
		}
		else
		{
			g.setFont(fnSelect);
			base = g.getFontMetrics(fnSelect).getHeight();
		}
		g.drawString(SString, left, top + base);
		g.setFont(oldFont);
	}

}

