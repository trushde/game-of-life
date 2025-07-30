/******************************************************************************
 * Author: Tajrian Rushat 
 * Project: Sim134 
 * Simulation of the 'Game of Life'
 ******************************************************************************/

import java.awt.Color;

public class Sim134
{
    private static int x = 80, y = 80; // x-by-y grid of cells
    private int magnification;  // pixel-width of each cell
    private int[][] cells;      // cells to be randomly coloured
    private Picture pic;        // picture to be drawn on screen

    public Sim134()
    {   magnification = 10;
        cells = new int[x][y];
        pic = new Picture(x * magnification, y * magnification);

        System.out.println("Hello!");

    }
    
    // fill a cell with either black or white color
    private void drawCell(int i, int j, int value)
    {
        this.cells[i][j] = value;

        Color col;


        if (value == 1)
        {
            col = new Color(0,0,0);
        } 

        else 
        {
            col = new Color (255,255,255);
        }
        
        for (int offsetX = 0; offsetX < magnification; offsetX++)
        {
            for (int offsetY = 0; offsetY < magnification; offsetY++)
            {
                // set() colours an individual pixel
                pic.set((i*magnification)+offsetX,
                        (j*magnification)+offsetY, col);
            }
        }
    }

   
    public int getCell(int i, int j)                      { return this.cells[i][j]; }

    // Returns count of live cells in neighbours 
    public int countNeighbours(int i, int j) 
    {
        int count = 0;

        // above
        count += this.cells[(i - 1 + x) % x][j];

        // left
        count += this.cells[i][(j - 1 + x) % x];

        // up and left
        count += this.cells[(i - 1 + x) % x][(j - 1 + x) % x];

        // up and right
        count += this.cells[(i - 1 + x) % x][(j + 1) % x];
        
        // down
        count += this.cells[(i + 1) % x][j];
        
        // down and left
        count += this.cells[(i + 1) % x][(j - 1 + x) % x];
        
        // right
        count += this.cells[i][(j + 1) % x];
        
        // down and right
        count += this.cells[(i + 1) % x][(j + 1) % x];  

        return count;
    }


    
    // display (or update) the picture on screen
    public void show()
    {
        pic.show();     // without calling this the pic will not show
    }

    public static void main(String[] args) {
        int iterations = Integer.parseInt(args[0]);
        String position = args[1];

        Sim134 picDemo = new Sim134();
 
        // Random initialization
        if (position.equals("R")) 
        {
            for (int i = 0; i < x; i++) 
            {
                for (int j = 0; j < y; j++) 
                {
                    int value = 0;
                    if (Math.random() < 0.5) 
                    {
                        value = 1;
                    }

                    picDemo.drawCell(i, j, value);
                }
            }
            
        } 
        
        // Jam (osciallator pattern) initialization 
        else if (position.equals("J")) 
        {
            int[] jamX = {4,5,3,6,1,4,6,1,5,1,4,2,3}; //initial x coordinates on the left
            int[] jamY = {1,1,2,2,3,3,3,4,4,5,6,7,7}; //initial y coordinates on the left

            for (int count = 0; count < jamX.length; count++)
            {
                int i = jamX[count];
                int j = jamY[count];
                picDemo.drawCell(i, j, 1);
            }
        }


        // Dart (glider pattern) initialization
        else if (position.equals("D")) 
        {
            int[] dartX = {8,7,9,6,10,7,8,9,5,6,10,11,3,7,9,13,2,3,7,9,13,14,1,7,9,15,2,4,5,7,9,11,12,14}; //initial x coordinates on the left 
            int[] dartY = {1,2,2,3,3,4,4,4,6,6,6,6,7,7,7,7,8,8,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10}; //initial y coordinates on the left
            
            for (int count = 0; count < dartX.length; count++)
            {
                int i = dartX[count];
                int j = dartY[count];
                picDemo.drawCell(i, j, 1);
            }
        } 

        else          {System.out.println ("Must use R or J or D for the program to run. Example Command: java Sim134 400 D"); return; }

        for (int r = 0; r < iterations; r++) 
        {
            int[][] new_cells = new int[x][y];

            // Computes the new cells
            for (int i = 0; i < x; i++) 

            {
                for (int j = 0; j < y; j++)

                {
                    int neighbours = picDemo.countNeighbours(i, j);
                    
                    // Conditions for Conway's game of life
                    if (picDemo.getCell(i, j) == 1 && neighbours < 2) 
                    {
                        new_cells[i][j] = 0;
                    } 
                    
                    else if (picDemo.getCell(i, j) == 1 && (neighbours == 2 || neighbours == 3)) 
                    {
                        new_cells[i][j] = 1;
                    } 
                    
                    else if (picDemo.getCell(i, j) == 1 && neighbours < 2)
                    {
                        new_cells[i][j] = 0;
                    } 

                    else if (picDemo.getCell(i, j) == 0 && neighbours == 3) 
                    {
                        new_cells[i][j] = 1;
                    } 
                    else

                    {
                        new_cells[i][j] = 0;
                    }
                }
            }

            // Draws the new cells
            for (int i = 0; i < x; i++) 
            {
                for (int j = 0; j < y; j++) 
                {
                    picDemo.drawCell(i, j, new_cells[i][j]);
                }
            }

            // Causes a delay 
            try 
            {
                Thread.sleep(80);
            } catch (InterruptedException e) 
            
            {
                System.err.format("IOException: %s%n", e);
            }
            picDemo.show();
        }

    }
}
