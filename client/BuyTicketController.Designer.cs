using System.ComponentModel;

namespace client;

partial class BuyTicketController
{
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private IContainer components = null;

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
        this.ownerTextBox = new System.Windows.Forms.TextBox();
        this.numberTextBox = new System.Windows.Forms.TextBox();
        this.lebel = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.errorLabel = new System.Windows.Forms.Label();
        this.buyButton = new System.Windows.Forms.Button();
        this.SuspendLayout();
        // 
        // ownerTextBox
        // 
        this.ownerTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.ownerTextBox.Location = new System.Drawing.Point(58, 141);
        this.ownerTextBox.Margin = new System.Windows.Forms.Padding(4);
        this.ownerTextBox.Name = "ownerTextBox";
        this.ownerTextBox.Size = new System.Drawing.Size(206, 30);
        this.ownerTextBox.TabIndex = 0;
        // 
        // numberTextBox
        // 
        this.numberTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.numberTextBox.Location = new System.Drawing.Point(119, 219);
        this.numberTextBox.Margin = new System.Windows.Forms.Padding(4);
        this.numberTextBox.Name = "numberTextBox";
        this.numberTextBox.Size = new System.Drawing.Size(86, 30);
        this.numberTextBox.TabIndex = 0;
        // 
        // lebel
        // 
        this.lebel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.lebel.ForeColor = System.Drawing.Color.MidnightBlue;
        this.lebel.Location = new System.Drawing.Point(30, 108);
        this.lebel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
        this.lebel.Name = "lebel";
        this.lebel.Size = new System.Drawing.Size(234, 29);
        this.lebel.TabIndex = 1;
        this.lebel.Text = "Owner name:";
        // 
        // label1
        // 
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label1.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label1.Location = new System.Drawing.Point(36, 186);
        this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(182, 29);
        this.label1.TabIndex = 2;
        this.label1.Text = "Number of seats:";
        // 
        // label2
        // 
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label2.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label2.Location = new System.Drawing.Point(42, 37);
        this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(244, 48);
        this.label2.TabIndex = 3;
        this.label2.Text = "BUY A TICKET!";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        // 
        // errorLabel
        // 
        this.errorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.errorLabel.ForeColor = System.Drawing.Color.Firebrick;
        this.errorLabel.Location = new System.Drawing.Point(30, 253);
        this.errorLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
        this.errorLabel.Name = "errorLabel";
        this.errorLabel.Size = new System.Drawing.Size(277, 103);
        this.errorLabel.TabIndex = 4;
        this.errorLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        // 
        // buyButton
        // 
        this.buyButton.BackColor = System.Drawing.Color.MidnightBlue;
        this.buyButton.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.buyButton.ForeColor = System.Drawing.Color.White;
        this.buyButton.Location = new System.Drawing.Point(107, 360);
        this.buyButton.Margin = new System.Windows.Forms.Padding(4);
        this.buyButton.Name = "buyButton";
        this.buyButton.Size = new System.Drawing.Size(111, 52);
        this.buyButton.TabIndex = 5;
        this.buyButton.Text = "Buy!";
        this.buyButton.UseVisualStyleBackColor = false;
        this.buyButton.Click += new System.EventHandler(this.buyButton_Click);
        // 
        // BuyTicketController
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 20F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(322, 453);
        this.Controls.Add(this.buyButton);
        this.Controls.Add(this.errorLabel);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.lebel);
        this.Controls.Add(this.numberTextBox);
        this.Controls.Add(this.ownerTextBox);
        this.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.Margin = new System.Windows.Forms.Padding(4);
        this.Name = "BuyTicketController";
        this.Text = "BuyTicketController";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button buyButton;

    private System.Windows.Forms.Label errorLabel;

    private System.Windows.Forms.Label label2;

    private System.Windows.Forms.Label lebel;

    private System.Windows.Forms.Label label1;

    private System.Windows.Forms.TextBox ownerTextBox;
    private System.Windows.Forms.TextBox numberTextBox;

    #endregion
}