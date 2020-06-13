using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace HeroesMapEditor
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Map map = new Map();
        public MainWindow()
        {
            InitializeComponent();
        }

        private void NewFile_Click(object sender, RoutedEventArgs e)
        {
            NewMap newMap = new NewMap();
            if (newMap.ShowDialog() == true)
            {
                map.floor = new bool[newMap.MapWidth,newMap.MapHeight];
                for (int i = 0; i < newMap.MapWidth; i++)
                {
                    Map.RowDefinitions.Add(new RowDefinition());
                }
                for (int i = 0; i < newMap.MapHeight; i++)
                {
                    Map.ColumnDefinitions.Add(new ColumnDefinition());
                }
                for (int i = 0; i < Map.RowDefinitions.Count; i++)
                {
                    for (int j = 0; j < Map.ColumnDefinitions.Count; j++)
                    {
                        Button button = new Button();
                        button.Background = Brushes.Black;
                        button.Click += Floor_Click;
                        Grid.SetColumn(button, j);
                        Grid.SetRow(button, i);
                    }
                }
            }
        }

        private void Floor_Click(object sender, RoutedEventArgs e)
        {
            Button but = (Button)sender;
            if (but.Background == Brushes.Black)
            {
                but.Background = Brushes.Green;
                map.floor[Grid.GetColumn(but), Grid.GetRow(but)] = true;
            }
            else
            {
                but.Background = Brushes.Black;
                map.floor[Grid.GetColumn(but), Grid.GetRow(but)] = false;
            }
        }
    }
}
