using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;

namespace Rasterization
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private static int pixel = 1;
        private static int midPointX = 0;
        private static int midPointY = 0;
        private static HashSet<ValueTuple<int, int>> points = new HashSet<ValueTuple<int, int>>();


        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            int w = 600;
            int h = 600;

            Font drawFont = new Font("Arial", 10);  
            SolidBrush drawBrush = new SolidBrush(Color.Black);
            int startX = -w / (pixel * 2) + midPointX;
            int endX = startX + w / pixel; 
            int padding = 30;
            int full = endX - startX;
            int pause = full / 10;
            int k = 0;
            for (int i = startX; i <= endX; i += pause / 2)
            {
                int newX = (i - startX) * pixel + padding;
                if (k % 2 == 0)
                    e.Graphics.DrawString(i.ToString(), drawFont, drawBrush, newX - 5, 0);

                e.Graphics.DrawLine(new Pen(Brushes.Black), new Point(newX,
                    padding), new Point(newX, h + padding));

                k++;
            }

            k = 0;
            int startY = -h / (pixel * 2) + midPointY;
            int endY = startY + h / pixel;
            full = endY - startY;
            pause = full / 10;

            for (int i = startY; i <= endY; i += pause / 2)
            {
                int newY = (i - startY) * pixel + padding;
                if (k % 2 == 0)
                    e.Graphics.DrawString(i.ToString(), drawFont, drawBrush, 0, newY - 5);

                
                e.Graphics.DrawLine(new Pen(Brushes.Black), new Point(padding,
                    newY), new Point(w + padding, newY));

                k++;
            }

            drawAllPoints(e.Graphics);
            if (startX <= 0 && endX >= 0)
            {
                int newX = -startX * pixel + padding;
                e.Graphics.DrawLine(new Pen(Brushes.Red), new Point(newX,
                        padding), new Point(newX, h + padding));
            }

            if (startY <= 0 && endY >= 0)
            {
                int newY = -startY * pixel + padding;
                e.Graphics.DrawLine(new Pen(Brushes.Red), new Point(padding,
                        newY), new Point(w + padding, newY));
            }
        }

        private void drawAllPoints(Graphics g)
        {
            int frameLeftX = midPointX - 300 / pixel;
            int frameRightX = midPointX + 300 / pixel;
            int frameLeftY = midPointY - 300 / pixel;
            int frameRightY = midPointY + 300 / pixel;

            int newP = 25 / pixel;

            foreach (ValueTuple<int, int> tuple in points)
            {
                if (tuple.Item1 >= frameLeftX && tuple.Item1 < frameRightX &&
                    tuple.Item2 >= frameLeftY && tuple.Item2 < frameRightY)
                {
                    int newX = (tuple.Item1 - frameLeftX) * pixel + 30;
                    int newY = (tuple.Item2 - frameLeftY) * pixel + 30;
                    g.FillRectangle(Brushes.Violet, newX, newY, pixel, pixel);
                }
            }
        }

        private void pictureBox1_MouseClick(object sender, MouseEventArgs e)
        {
            int frameLength = 300 / pixel;
            int frameHeight = 300 / pixel;
            int realX = (e.X - 30) / pixel - frameLength;
            int realY = (e.Y - 30) / pixel - frameHeight;


            if (e.Button == MouseButtons.Right)
            {
                if (pixel != 1)
                {
                    pixel /= 2;
                    midPointX += realX;
                    midPointY += realY;
                }
            }
            else
            {
                if (pixel < 25)
                {
                    pixel *= 2;
                    midPointX += realX;
                    midPointY += realY;
                }
            }

            if (pixel == 1)
            {
                midPointX = 0;
                midPointY = 0;
            }
            pictureBox1.Refresh();
        }

        private void button3_Click_1(object sender, EventArgs e)
        {
            double time = Algo.DDA(int.Parse(textBoxX1.Text), int.Parse(textBoxY1.Text),
            int.Parse(textBoxX2.Text), int.Parse(textBoxY2.Text), points);
            label6.Text = $"Время: {time} ms";
            pictureBox1.Refresh();
        }

        private void button4_Click_1(object sender, EventArgs e)
        {
            double time = Algo.BresenhamLine(int.Parse(textBoxX1.Text), int.Parse(textBoxY1.Text),
                int.Parse(textBoxX2.Text), int.Parse(textBoxY2.Text), points);
            label6.Text = $"Время: {time} tics";
            pictureBox1.Refresh();
        }

        private void button5_Click_1(object sender, EventArgs e)
        {

            double time = Algo.BresenhamCircle(int.Parse(textBoxX1.Text), int.Parse(textBoxY1.Text),
                int.Parse(textBoxR.Text), points);
            label6.Text = $"Время: {time} tics";
            pictureBox1.Refresh();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            double time = Algo.GetLine(int.Parse(textBoxX1.Text), int.Parse(textBoxY1.Text),
                int.Parse(textBoxX2.Text), int.Parse(textBoxY2.Text), points);
            label6.Text = $"Время: {time} ms";
            pictureBox1.Refresh();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            points.Clear();
            pictureBox1.Refresh();

        }
    }
}
