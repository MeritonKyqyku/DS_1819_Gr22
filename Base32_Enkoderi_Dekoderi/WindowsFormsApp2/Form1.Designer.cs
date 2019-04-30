namespace WindowsFormsApp2
{
    partial class Enkoderi_Dekoderi_Base32
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.txtMesazhi = new System.Windows.Forms.TextBox();
            this.txtEnkoduar = new System.Windows.Forms.TextBox();
            this.txtDekoduar = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.btnEnkodo = new System.Windows.Forms.Button();
            this.btnDekodo = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // txtMesazhi
            // 
            this.txtMesazhi.Location = new System.Drawing.Point(35, 60);
            this.txtMesazhi.Name = "txtMesazhi";
            this.txtMesazhi.Size = new System.Drawing.Size(705, 20);
            this.txtMesazhi.TabIndex = 0;
            // 
            // txtEnkoduar
            // 
            this.txtEnkoduar.AccessibleDescription = "Teksti pas Enkodimit";
            this.txtEnkoduar.AccessibleName = "txtEnkoduar";
            this.txtEnkoduar.Location = new System.Drawing.Point(35, 205);
            this.txtEnkoduar.Name = "txtEnkoduar";
            this.txtEnkoduar.Size = new System.Drawing.Size(705, 20);
            this.txtEnkoduar.TabIndex = 1;
            // 
            // txtDekoduar
            // 
            this.txtDekoduar.AccessibleDescription = "Teksti pas Dekodimit";
            this.txtDekoduar.AccessibleName = "txtDekoduar";
            this.txtDekoduar.Location = new System.Drawing.Point(35, 374);
            this.txtDekoduar.Name = "txtDekoduar";
            this.txtDekoduar.Size = new System.Drawing.Size(705, 20);
            this.txtDekoduar.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(332, 29);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(46, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "Mesazhi";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(297, 343);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(116, 13);
            this.label3.TabIndex = 6;
            this.label3.Text = "Mesazhi pas Dekodimit";
            this.label3.Click += new System.EventHandler(this.label3_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(298, 177);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(115, 13);
            this.label4.TabIndex = 7;
            this.label4.Text = "Mesazhi pas Enkodimit";
            // 
            // btnEnkodo
            // 
            this.btnEnkodo.AccessibleDescription = "Enkodo Mesazhin";
            this.btnEnkodo.AccessibleName = "btnEnkodo";
            this.btnEnkodo.Location = new System.Drawing.Point(317, 112);
            this.btnEnkodo.Name = "btnEnkodo";
            this.btnEnkodo.Size = new System.Drawing.Size(75, 23);
            this.btnEnkodo.TabIndex = 8;
            this.btnEnkodo.Text = "Enkodo";
            this.btnEnkodo.UseVisualStyleBackColor = true;
            this.btnEnkodo.Click += new System.EventHandler(this.btnEnkodo_Click);
            // 
            // btnDekodo
            // 
            this.btnDekodo.AccessibleDescription = "Dekodo Mesazhin";
            this.btnDekodo.AccessibleName = "btnDekodo";
            this.btnDekodo.Location = new System.Drawing.Point(317, 286);
            this.btnDekodo.Name = "btnDekodo";
            this.btnDekodo.Size = new System.Drawing.Size(75, 23);
            this.btnDekodo.TabIndex = 9;
            this.btnDekodo.Text = "Dekodo";
            this.btnDekodo.UseVisualStyleBackColor = true;
            this.btnDekodo.Click += new System.EventHandler(this.btnDekodo_Click);
            // 
            // Enkoderi_Dekoderi_Base32
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoValidate = System.Windows.Forms.AutoValidate.EnableAllowFocusChange;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnDekodo);
            this.Controls.Add(this.btnEnkodo);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtDekoduar);
            this.Controls.Add(this.txtEnkoduar);
            this.Controls.Add(this.txtMesazhi);
            this.Name = "Enkoderi_Dekoderi_Base32";
            this.Text = "Enkoderi dhe Dekoderi Base32";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox txtMesazhi;
        private System.Windows.Forms.TextBox txtEnkoduar;
        private System.Windows.Forms.TextBox txtDekoduar;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnEnkodo;
        private System.Windows.Forms.Button btnDekodo;
    }
}

