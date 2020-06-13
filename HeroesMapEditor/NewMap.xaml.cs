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
using System.Windows.Shapes;

namespace HeroesMapEditor
{
    /// <summary>
    /// Логика взаимодействия для NewMap.xaml
    /// </summary>
    public partial class NewMap : Window
    {
        public NewMap()
        {
            InitializeComponent();
        }

        public int MapWidth => Convert.ToInt32(Width.Text);
        public int MapHeight => Convert.ToInt32(Height.Text);

        private void OK_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}
