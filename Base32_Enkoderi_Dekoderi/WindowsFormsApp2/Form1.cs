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

            for (numri = 0; numri < krye.Length; numri++)
            {
                karakteri.Append(krye[numri]);

                if (karakteri.Length % 5 == 0)
                {
                    if ((karakteri.ToString() == "00000")&&(numri>(krye.Length-25)))
                    {
                        kryesorja.Append("=");
                    }
                    else
                    {
                        char[] array = (karakteri.ToString()).ToCharArray();

                        Array.Reverse(array);

                        int sum = 0;

                        for (int i = 0; i < array.Length; i++)
                        {
                            if (array[i] == '1')
                            {

                                if (i == 0)
                                {
                                    sum += 1;
                                }
                                else
                                {
                                    sum += (int)Math.Pow(2, i);
                                }
                            }

                        }
                        char[] digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".ToCharArray();
                        kryesorja.Append(digits[sum]);
                    }
                }
                if (karakteri.Length == 5)
                {
                    karakteri.Clear();
                }
            }


            txtEnkoduar.Text = kryesorja.ToString();

            vlera = kryesorja.ToString();
        }
        

        private void btnDekodo_Click(object sender, EventArgs e)
        {
            StringBuilder sb = new StringBuilder();
            StringBuilder karakteri = new StringBuilder();
            string konverto;

            foreach (char c in (vlera).ToCharArray())
            {
                sb.Append(Convert.ToChar(c));

            }
            char[] digits = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7' };
            konverto = sb.ToString();
            for (int i = 0; i < konverto.Length; i++)
            {
                for (int j = 0; j < digits.Length; j++)
                {
                    (konverto).TrimEnd('=');
                    if (digits[j] == sb[i])
                    {
                        karakteri.Append(Convert.ToString(j, 2).PadLeft(5, '0'));

                    }


                }

            }
            while (karakteri.Length % 8 != 0)
            {
                karakteri.Append(0);
            }
            string rsa = karakteri.ToString();

            StringBuilder builder = new StringBuilder();
            byte[] bArr = new byte[rsa.Length / 8];
            for (int i = 0; i < rsa.Length / 8; i++)
            {
                String part = rsa.Substring(i * 8, 8);
                bArr[i] += Convert.ToByte(part, 2);
            }
            System.Text.ASCIIEncoding encoding = new System.Text.ASCIIEncoding();


            txtDekoduar.Text = encoding.GetString(bArr);

        }
    }
}
