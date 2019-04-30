using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace WindowsFormsApp2
{
    public partial class Enkoderi_Dekoderi_Base32 : Form
    {
        public Enkoderi_Dekoderi_Base32()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }
        string vlera;
        private void btnEnkodo_Click(object sender, EventArgs e)
        {
           StringBuilder sb = new StringBuilder();

            foreach (char c in (txtMesazhi.Text).ToCharArray())
            {
                sb.Append(Convert.ToString(c, 2).PadLeft(8, '0'));
            }
            StringBuilder karakteri = new StringBuilder();
            StringBuilder kryesorja = new StringBuilder();
                       
            int numri = 0;

            while ((sb.Length % 5 != 0) || (sb.Length % 8 != 0))
            { sb.Append(0); }
            string krye = sb.ToString();
            

           txtEnkoduar.Text = kryesorja.ToString();

            vlera = kryesorja.ToString();
        }
        

        private void btnDekodo_Click(object sender, EventArgs e)
        {
            
          
            StringBuilder builder = new StringBuilder();
            byte[] bArr = new byte[rsa.Length / 8];
            for (int i = 0; i < rsa.Length/8; i ++)
            {
                String part = rsa.Substring(i * 8, 8);
                bArr[i] += Convert.ToByte(part, 2);
            }
            System.Text.ASCIIEncoding encoding = new System.Text.ASCIIEncoding();


            txtDekoduar.Text = encoding.GetString(bArr);

        }
    }
}
