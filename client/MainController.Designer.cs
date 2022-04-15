using System.ComponentModel;

namespace client;

partial class MainController
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
        this.allShowsList = new System.Windows.Forms.ListView();
        this.dateShowsList = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.dateTimePicker = new System.Windows.Forms.DateTimePicker();
        this.logoutButton = new System.Windows.Forms.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        // 
        // allShowsList
        // 
        this.allShowsList.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.allShowsList.HideSelection = false;
        this.allShowsList.Location = new System.Drawing.Point(12, 70);
        this.allShowsList.Name = "allShowsList";
        this.allShowsList.Size = new System.Drawing.Size(600, 562);
        this.allShowsList.TabIndex = 0;
        this.allShowsList.UseCompatibleStateImageBehavior = false;
        this.allShowsList.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.allShowsList_MouseDoubleClick);
        // 
        // dateShowsList
        // 
        this.dateShowsList.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.dateShowsList.FormattingEnabled = true;
        this.dateShowsList.ItemHeight = 18;
        this.dateShowsList.Location = new System.Drawing.Point(635, 124);
        this.dateShowsList.Name = "dateShowsList";
        this.dateShowsList.Size = new System.Drawing.Size(600, 508);
        this.dateShowsList.TabIndex = 1;
        this.dateShowsList.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.dateShowsList_MouseDoubleClick);
        // 
        // label1
        // 
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label1.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label1.Location = new System.Drawing.Point(12, 30);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(171, 23);
        this.label1.TabIndex = 2;
        this.label1.Text = "All shows:";
        // 
        // label2
        // 
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label2.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label2.Location = new System.Drawing.Point(635, 71);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(247, 23);
        this.label2.TabIndex = 3;
        this.label2.Text = "Choose shows\'s date:";
        // 
        // dateTimePicker
        // 
        this.dateTimePicker.CalendarFont = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.dateTimePicker.CalendarForeColor = System.Drawing.Color.Azure;
        this.dateTimePicker.CalendarMonthBackground = System.Drawing.Color.Azure;
        this.dateTimePicker.CalendarTitleForeColor = System.Drawing.Color.Azure;
        this.dateTimePicker.CalendarTrailingForeColor = System.Drawing.Color.Azure;
        this.dateTimePicker.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.dateTimePicker.Location = new System.Drawing.Point(888, 70);
        this.dateTimePicker.Name = "dateTimePicker";
        this.dateTimePicker.Size = new System.Drawing.Size(347, 24);
        this.dateTimePicker.TabIndex = 6;
        this.dateTimePicker.CloseUp += new System.EventHandler(this.dateTimePicker_CloseUp);
        // 
        // logoutButton
        // 
        this.logoutButton.BackColor = System.Drawing.Color.MidnightBlue;
        this.logoutButton.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.logoutButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.logoutButton.ForeColor = System.Drawing.Color.White;
        this.logoutButton.Location = new System.Drawing.Point(1129, 647);
        this.logoutButton.Name = "logoutButton";
        this.logoutButton.Size = new System.Drawing.Size(106, 41);
        this.logoutButton.TabIndex = 7;
        this.logoutButton.Text = "Logout";
        this.logoutButton.UseVisualStyleBackColor = false;
        this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
        // 
        // label3
        // 
        this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
        this.label3.ForeColor = System.Drawing.Color.MidnightBlue;
        this.label3.Location = new System.Drawing.Point(22, 657);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(374, 23);
        this.label3.TabIndex = 8;
        this.label3.Text = "Double-click on a show to buy a ticket!";
        // 
        // MainController
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1271, 762);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.logoutButton);
        this.Controls.Add(this.dateTimePicker);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.dateShowsList);
        this.Controls.Add(this.allShowsList);
        this.Name = "MainController";
        this.Text = "MainController";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainController_FormClosing);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Label label3;

    private System.Windows.Forms.Button logoutButton;

    private System.Windows.Forms.DateTimePicker dateTimePicker;

    private System.Windows.Forms.Label label2;

    private System.Windows.Forms.Label label1;

    private System.Windows.Forms.ListView allShowsList;
    private System.Windows.Forms.ListBox dateShowsList;

    #endregion
}