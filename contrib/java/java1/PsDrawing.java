//
//   PsDrawing.java : This file is part of pstoedit.
//   Java applet needed to run the java code generated by pstoedit -f java
//
// this applet is called from HTML via:
// <APPLET code="PsDrawing.class" width=700 height=800>
//	<PARAM name="psjavaclass" value="PSJava">
// </APPLET>
// replace PSJava by whatever you specified after the -f java option,
// e.g. -f java:anotherclassname
//  
//   Copyright (C) 1998 - 1999 Wolfgang Glunz, wglunz@geocities.com
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
//
import java.awt.*;

//
// this applet is called via:
// <PARAM name="psjavaclass" value="PSJava">
//
public class PsDrawing extends java.applet.Applet 
{
    public void init()
    {
        System.out.println("setting up pages ");
        setBackground( Color.white );
        try {
                String psjavaclassName  = getParameter("psjavaclass");
                Class psclass           = Class.forName(psjavaclassName);
                pspages                 = (PsPages)(psclass.newInstance());
                if (pspages.numberOfPages() > 1)  {
                        prevButton = new Button("previous page");
                        add(prevButton);
                        prevButton.disable();
                        nextButton = new Button("next page");
                        add(nextButton);
                }
        }
        catch(ClassNotFoundException e) { System.err.println(e); }
        catch(IllegalAccessException e) { System.err.println(e); }
        catch(InstantiationException e) { System.err.println(e); }
        System.out.println("setting up pages done ");


//      pspages                 = new PSJava();
    }
    public void paint( Graphics g )
    {
        setBackground( Color.white );
        System.out.println("paint started for page # "+pagenr);
        Rectangle r = bounds();
        pspages.showpage(r,pagenr,g);
        System.out.println("paint finished");
    }
    public boolean action(Event e, Object o)
    {
        if(e.target == nextButton) {
                pagenr++;
                if (pagenr == pspages.numberOfPages() ) {
                        if (nextButton != null) {
                                nextButton.disable();
                        }
                } else if (pagenr > 1) {
                        if (prevButton != null) {
                                prevButton.enable();
                        }
                }
                Rectangle r = bounds();
                pspages.showpage(r,pagenr,getGraphics());
                return true;
        } else if(e.target == prevButton) {
                pagenr--;
                if (pagenr == 1) {
                        if (prevButton != null) {
                                prevButton.disable();
                        }
                } else if (pagenr < pspages.numberOfPages() ) {
                        if (nextButton != null) {
                                nextButton.enable();
                        }
                }
                Rectangle r = bounds();
                pspages.showpage(r,pagenr,getGraphics());
                return true;
        } else return super.action(e,o);
    }
    private PsPages pspages = null;
    private int pagenr = 1;
    private Button nextButton;
    private Button prevButton;
}


