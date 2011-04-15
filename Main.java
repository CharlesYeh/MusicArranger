   import javax.swing.JFrame;
   import javax.swing.JMenu;
   import javax.swing.JMenuBar;
   import javax.swing.JMenuItem;
   import java.awt.event.KeyEvent;
   import java.awt.event.ActionEvent;
   import java.awt.event.ActionListener;
   import gui.MainPanel;

/* 
 * Main handles delegations of tasks between 
 */
 
   public class Main extends JFrame{
      public Main(){
         super("Music Arranger");
         
         MainPanel mainPanel = new MainPanel();
         this.add(mainPanel);
      	
         this.setDefaultCloseOperation(EXIT_ON_CLOSE);
         this.pack();
         this.setVisible(true);
      
         addMenuBar();
      }
   
      public void addMenuBar(){
         JMenuBar menubar = new JMenuBar();
      
         JMenu file = new JMenu("File");
         file.setMnemonic(KeyEvent.VK_F);
      
         JMenuItem eMenuItem = new JMenuItem("Exit");
         eMenuItem.setMnemonic(KeyEvent.VK_C);
         eMenuItem.setToolTipText("Exit application");
         eMenuItem.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent event) {
                     System.exit(0);
                  }
               
               });
      
         file.add(eMenuItem);
      }
   
      public static void main(String[] args){
         new Main();
      }
   }