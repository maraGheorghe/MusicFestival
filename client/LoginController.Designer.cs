using System.ComponentModel;

namespace client;

partial class LoginController
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(LoginController));
        this.usernameField = new System.Windows.Forms.TextBox();
        this.passwordField = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.errorLabel = new System.Windows.Forms.Label();
        this.label = new System.Windows.Forms.Label();
        this.loginButton = new System.Windows.Forms.Button();
        this.SuspendLayout();
        // 
        // usernameField
        // 
        this.usernameField.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.usernameField.Location = new System.Drawing.Point(51, 128);
        this.usernameField.Name = "usernameField";
        this.usernameField.Size = new System.Drawing.Size(240, 30);
        this.usernameField.TabIndex = 0;
        // 
        // passwordField
        // 
        this.passwordField.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.passwordField.Location = new System.Drawing.Point(51, 232);
        this.passwordField.Name = "passwordField";
        this.passwordField.PasswordChar = '*';
        this.passwordField.Size = new System.Drawing.Size(240, 30);
        this.passwordField.TabIndex = 1;
        this.passwordField.UseSystemPasswordChar = true;
        // 
        // label1
        // 
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label1.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label1.Location = new System.Drawing.Point(29, 93);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(120, 23);
        this.label1.TabIndex = 2;
        this.label1.Text = "Username:";
        // 
        // label2
        // 
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label2.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label2.Location = new System.Drawing.Point(29, 192);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(112, 23);
        this.label2.TabIndex = 3;
        this.label2.Text = "Password:";
        // 
        // errorLabel
        // 
        this.errorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.errorLabel.ForeColor = System.Drawing.Color.Firebrick;
        this.errorLabel.Location = new System.Drawing.Point(29, 284);
        this.errorLabel.Name = "errorLabel";
        this.errorLabel.Size = new System.Drawing.Size(266, 100);
        this.errorLabel.TabIndex = 5;
        this.errorLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        // 
        // label
        // 
        this.label.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label.Location = new System.Drawing.Point(118, 29);
        this.label.Name = "label";
        this.label.Size = new System.Drawing.Size(127, 41);
        this.label.TabIndex = 6;
        this.label.Text = "LOGIN";
        // 
        // loginButton
        // 
        this.loginButton.BackColor = System.Drawing.Color.MidnightBlue;
        this.loginButton.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.loginButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.loginButton.ForeColor = System.Drawing.Color.White;
        this.loginButton.Location = new System.Drawing.Point(109, 387);
        this.loginButton.Name = "loginButton";
        this.loginButton.Size = new System.Drawing.Size(115, 35);
        this.loginButton.TabIndex = 7;
        this.loginButton.Text = "Login";
        this.loginButton.UseVisualStyleBackColor = false;
        this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
        // 
        // LoginController
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.BackColor = System.Drawing.SystemColors.Control;
        this.ClientSize = new System.Drawing.Size(340, 451);
        this.Controls.Add(this.loginButton);
        this.Controls.Add(this.label);
        this.Controls.Add(this.errorLabel);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.passwordField);
        this.Controls.Add(this.usernameField);
        this.Icon = ((System.Drawing.Icon) (resources.GetObject("$this.Icon")));
        this.Name = "LoginController";
        this.Text = "LoginController";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button loginButton;

    private System.Windows.Forms.Label errorLabel;

    private System.Windows.Forms.Label label;

    private System.Windows.Forms.Label label2;

    private System.Windows.Forms.Label label1;

    private System.Windows.Forms.TextBox usernameField;
    private System.Windows.Forms.TextBox passwordField;

    #endregion
}