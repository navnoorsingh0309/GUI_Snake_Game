import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
class JTextFieldLimit extends PlainDocument {
	  private int limit;
	  JTextFieldLimit(int limit) {
	    super();
	    this.limit = limit;
	  }

	  JTextFieldLimit(int limit, boolean upper) {
	    super();
	    this.limit = limit;
	  }

	  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	    if (str == null)
	      return;

	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
	}
class Audio
{	
	public Clip audioclip = null;
	public Audio()
	{}
	public Audio(String filename)
	{ 
		try {        
	    }
	    catch (Exception e) {
	        //whatevers
	    }
	}
	public void PlayAudio()
	{		
		audioclip.start();
	}
	public void StopAudio()
	{
		audioclip.stop();
		audioclip.loop(500);
	}
	public void SetLoop(int arg)
	{
		audioclip.loop(arg);
	}
	public void SetVolume(int volume)
	{
		if(volume<=47)
		{
		FloatControl gainControl = 
		    (FloatControl) audioclip.getControl(FloatControl.Type.MASTER_GAIN);
		if(volume==0)
		    gainControl.setValue(-80);
		else if(volume>0)
			gainControl.setValue((volume-80)+39);
		}
	}
}
public class Snaky
{
	static int food_x = 0, food_y = 0, DOT_SIZE = 20, snake_x = 50, snake_y = 50;
	static int Direction = 1; //1-Right 2-Left 3-Up 4-Down
	static int dots = 3, gameover=0, speed=120, scores=0, foodno=1, timeractivated = 0;
	static Dimension res;
	static boolean pause = false, started=false;
	static int speedchanged = 0;
	static JFrame frame = new JFrame(); //Main Frame
	static JPanel panel = new JPanel(); //Main Panel
	static Image ball, head, food, food2, food3, head2, head3, head4, body2, body3, worm, cur;
	static Image power, progr, covid, fullsundi;
	static URL sFire1, sFire2, Gobbieat, Gameover, Pause, PintoSundi;
	static Cursor wcursor; 
	//Position of Snake parts
	static int x[] = new int[1000];
	static int y[] = new int[1000];
	//Minimize Exit Buttons
	static JButton Mbtn = new JButton("_");
	static JButton Ebtn = new JButton("X");
	static int gameoveroption = 1;
	static int option_Y = 10;
	//Buttons
	static JButton resbtn = new JButton("Restart");
	static JButton exitbtn = new JButton("Quit");
	static int pause_y = 0;
	static JPanel gameoverpanel = new JPanel(),
	              startpane = new JPanel();
	static Color NormalBackground = null;
	static int IPanel_X = -690;
	static JLabel goverlabel = new JLabel();
	static JLabel pauselabel = new JLabel();
	static JLabel psundilabel = new JLabel();
	static int Des1_X, Des2_X;
	static int partsofmovement1 = 1, partsofmovement2 = 1;
	static int freewColor = 1;
	//Tooltip Elements
	static int mousetooltip_X = 0, mousetooltip_Y = 0, longmousestay = 0;
	static boolean starttooltip = false, mousein = false;
	static Timer mousestartstay = new Timer();
	static TimerTask mousestartstaytask = null;
	static JLabel startbtn = new JLabel("");	
	//Start Toolttip
	static int StartTip_Y = 0;
	static Timer Shinctooltimer = new Timer();
	static TimerTask Shinctooltask = null;
	//Movement Timer
	static TimerTask speedtt = new TimerTask()
	{
		public void run()
		{
			action();
		}
	};
	static Timer speedt;
	//Speed caption remove timer
	static Timer speedcrtimer = new Timer();
	static TimerTask speedcrtimertask = new TimerTask()
	{
		public void run()
		{
			speedchanged = 0;
			speedcrtimer.cancel();
		}
	};
	//Sounds
	public static Audio background = new Audio(),
    Food = new Audio(),
    Over = new Audio();
	public static URL readfood = null, backg = null, over=null;
	public static void GetResolution()
	{
		res = Toolkit.getDefaultToolkit().getScreenSize();
	}
	public static void LocateApple()
	{
		do
		{
			food_x = (int)(Math.random()*((res.width-(DOT_SIZE*2))/DOT_SIZE));
			food_x *= DOT_SIZE;
			food_y = (int)(Math.random()*((res.height-DOT_SIZE)/DOT_SIZE));
			food_y *= DOT_SIZE;
			int foodrand = 1 + (int)(Math.random() * ((10 - 1) + 1));
			if(foodrand>=1 && foodrand<=2)
				foodno=1;
			else if(foodrand>=3 && foodrand<=8)
				foodno=2;
			else if(foodrand>8)
				foodno=3;
		}
		while((food_x<DOT_SIZE*2 || food_x>res.width-DOT_SIZE) || (food_y<DOT_SIZE*2 || food_y>res.height-DOT_SIZE));
	}	
    public void loadImages()
	{
		try
	    {
	    	ball = ImageIO.read(getClass().getResource("/res/body.png"));
	    	body2 = ImageIO.read(getClass().getResource("/res/body2.png"));                
	    	body3 = ImageIO.read(getClass().getResource("/res/body3.png"));
	    	food = ImageIO.read(getClass().getResource("/res/Gobbi.png"));        
	    	food2 = ImageIO.read(getClass().getResource("/res/blue gobbi.png"));
	    	food3 = ImageIO.read(getClass().getResource("/res/red gobbi.png"));
	    	head = ImageIO.read(getClass().getResource("/res/head.png"));        
	    	head2 = ImageIO.read(getClass().getResource("/res/head2.png"));        
	    	head3 = ImageIO.read(getClass().getResource("/res/head3.png"));        
	    	head4 = ImageIO.read(getClass().getResource("/res/head4.png"));	    	
	    	worm = ImageIO.read(getClass().getResource("/res/sundi.png"));	    	
	    	cur = ImageIO.read(getClass().getResource("/res/cursor.png"));	    	
	    	cur = ImageIO.read(getClass().getResource("/res/cursor.png"));	    	
	    	power = ImageIO.read(getClass().getResource("/res/Power.png"));	    	
	    	progr = ImageIO.read(getClass().getResource("/res/SundiDes.png"));	    	
	    	covid = ImageIO.read(getClass().getResource("/res/COVID-19.png"));	    	
	    	fullsundi = ImageIO.read(getClass().getResource("/res/FullSundi.png"));	    	
	    	sFire1 = getClass().getResource("res/sundi fire left.gif");
	    	sFire2 = getClass().getResource("res/sundi fire right.gif");	    	
	    	Gameover = getClass().getResource("res/game-over.png");	    	
	    	Pause = getClass().getResource("res/pause3.png");	    	
	    	Gobbieat = getClass().getResource("res/gobbi-eater.gif");	    	
	    	PintoSundi = getClass().getResource("res/pinto-s3.png");	    	
	    	wcursor = Toolkit.getDefaultToolkit().createCustomCursor(
					cur,
					new Point(0,0),"custom cursor");
        }
        catch (IOException e)
        {
			e.printStackTrace();
		}
        try
        {
        	readfood = getClass().getResource("res/Food.wav");
        	backg = getClass().getResource("res/backg.wav");
        	over = getClass().getResource("res/over.wav");
        }
        catch(Exception ex)
        {}
    }
	JLabel obj1 = new JLabel();
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	public Snaky()
	{
		//RIGHT
	    obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
	    obj1.getActionMap().put("RIGHT", new KeyBSet(1));
	    //LEFT
	    obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
	    obj1.getActionMap().put("LEFT", new KeyBSet(2));
	    //UP
	    obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
	    obj1.getActionMap().put("UP", new KeyBSet(3));
	    //DOWN
	    obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
	    obj1.getActionMap().put("DOWN", new KeyBSet(4));    
	    //P
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "PAUSE");
	    obj1.getActionMap().put("PAUSE", new KeyBSet(5));
	    //Enter
	    obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
	    obj1.getActionMap().put("ENTER", new KeyBSet(6));
	    frame.add(obj1);
	    
	}
	//Check Apple
	public void CheckApple()
	{
		for(int i=1;i<=DOT_SIZE;i++)
		{
			if((x[0]+i>=food_x && x[0]+i<=food_x+30) && (y[0]+i>=food_y && y[0]+i<=food_y+30))
			{
				try
				{
					AudioInputStream ains = AudioSystem.getAudioInputStream(readfood);
					Food.audioclip = AudioSystem.getClip();
					Food.audioclip.open(ains);
					Food.PlayAudio();
				}
				catch(Exception ex)
				{}
				timeractivated = 0;
				if(foodno==2)
				{
					speedchanged=1;
					if(speed>20)
						speed-=10;

				}
				else if(foodno==3)
				{
					speedchanged=2;
					if(speed<=160)
						speed+=10;
				}				
				speedtt.cancel();
				speedt.cancel();
				speedtt = new TimerTask()
				{
					public void run()
					{
						action();
					}
				};
				speedt = new Timer();
				speedt.schedule(speedtt, speed, speed);
				dots++;
				scores++;
				LocateApple();
			}
		}
	}
	public static void initGame()
	{
		scores = 0;
		speed = 60;
		Direction = 1;
		gameover = 0;
		pause = false;
		namefield.setVisible(false);
		goverlabel.setVisible(false);
		//Hide Cursor
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		dots = 3;
		for(int i=0;i<dots;i++)
		{
			x[i] = (int)(res.width/28.8)-i*(DOT_SIZE-5);
			y[i] = res.height/18;
		}
		gameoverpanel.setVisible(false);
		LocateApple();		
		speedt = new Timer();
		speedtt = new TimerTask()
		{
			public void run()
			{
				action();
			}
		};
		speedt.schedule(speedtt, speed, speed);
	}
	public static void action()
	{
		if(gameover==0)
		{
			Move();
			new Snaky().CheckApple();
			CheckCollisions();
		}		
		panel.repaint();
	}
	public static void Move()
	{
		for(int i=dots;i>0;i--)
		{
			x[i] = x[(i-1)];
			y[i] = y[(i-1)];
		}
		if(Direction==1)
		    x[0] += DOT_SIZE-5;
		else if(Direction==2)
		    x[0] -= DOT_SIZE-5;
		else if(Direction==3)
		    y[0] -= DOT_SIZE-5;
		else if(Direction==4)
		    y[0] += DOT_SIZE-5;
	}
	//Setting KeyBinding Class
	public class KeyBSet extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		int kk = 0;
		KeyBSet(int keycode)
		{			
			kk = keycode;
		}
		public void actionPerformed(ActionEvent arg0)
		{
			if(kk == 1 && Direction!=2 && pause==false && gameover == 0 && started==true)
				Direction=1;
			else if(kk==2  && Direction!=1 && pause==false && gameover == 0 && started==true)
				Direction=2;
			else if(kk==3)
			{
				if(Direction!=4 && pause==false && gameover == 0)
					Direction=3;
				if(gameover==1)
				{
					if(gameoveroption!=1)
					{
						option_Y = 10;
						gameoverpanel.repaint();
						exitbtn.setBackground(resbtn.getBackground());
						resbtn.setBackground(Color.red);					
						gameoveroption=1;
					}
				}
			}
			else if(kk==4)
			{
				if(Direction!=3 && pause==false && gameover == 0 && started==true)
					Direction=4;
				if(gameover==1)
				{
					if(gameoveroption!=2)
					{
						option_Y = 105;
						gameoverpanel.repaint();
						resbtn.setBackground(exitbtn.getBackground());
						exitbtn.setBackground(Color.red);
						gameoveroption=2;
					}
				}
			}
			else if(kk==5 && gameover == 0 && started==true)
			{
				if(pause==false)
				{					
					//Show Mouse
					frame.getContentPane().setCursor(wcursor);
					Mbtn.setVisible(true);
					Ebtn.setVisible(true);
					pause = true;					
					speedt.cancel();
				}
				else
				{
					pauselabel.setVisible(false);
					//Hide Cursor
					// Transparent 16 x 16 pixel cursor image.
					BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
					// Create a new blank cursor.
					Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					    cursorImg, new Point(0, 0), "blank cursor");
					// Set the blank cursor to the JFrame.
					frame.getContentPane().setCursor(blankCursor);
					Mbtn.setVisible(false);
					Ebtn.setVisible(false);
					pause = false;
					speedtt = new TimerTask()
					{
						public void run()
						{
							action();
						}
					};
					speedt = new Timer();
					speedt.schedule(speedtt, speed, speed);
				}
				panel.repaint();
			}
			else if(kk==6)
			{
				if(gameover==1)
				{
					if(gameoverpanel.isVisible())
					{
						if(gameoveroption==1)
							initGame();
						else
							System.exit(0);
					}
					else
					{
						OkayBtnFunc();
					}
				}
				//Play on Enter
				if(started==false)
				{
					startpane.setVisible(false);
					frame.setContentPane(panel);
					started = true;
					initGame();
				}
			}
		}
	}
	
	//Check Collisions
	public static void CheckCollisions()
	{
		//With Walls
		if(x[0]<-2 || x[0]>res.width-DOT_SIZE || y[0]<-2 || y[0]>res.height-DOT_SIZE)
			gameover=1;		
		//With itself
		for(int i=0;i<=dots;i++)
		{
			if(x[0]==x[i] && y[0]==y[i] && i>4)
			{				
			    gameover = 1;
			}
		}
		if(gameover==1)
		{
			frame.getContentPane().setCursor(wcursor);
			panel.repaint();
			try
			{
				AudioInputStream ains = AudioSystem.getAudioInputStream(over);
				Over.audioclip = AudioSystem.getClip();
				Over.audioclip.open(ains);
				Over.audioclip.setMicrosecondPosition(0);
				Over.PlayAudio();
			}
			catch(Exception ex)
			{}
		}
	}
	//Get Mouse Position
	public static void GetMousePos()
	{
		//Get Mouse information
		PointerInfo info = MouseInfo.getPointerInfo();
		Point point = info.getLocation();
		int px = (int)point.getX();
		int py = (int)point.getY();
		mousetooltip_X = px;
		mousetooltip_Y = py;
	}
	//Start Button Tip
	public static void LoadStartButtonTimer()
	{
		//Check how long mouse stays on the button		
		mousestartstay = new Timer();
		mousestartstaytask = new TimerTask()
		{
			public void run()
			{
				longmousestay++;
				if(longmousestay==2)
				{
					starttooltip = true;
					startbtn.repaint();
					StartTip_Y=0;	
					//Height of tip increaser
					Shinctooltimer = new Timer();
					Shinctooltask = new TimerTask()
					{
						public void run()
						{
							if(StartTip_Y>=(int)(res.height/56.25))
								Shinctooltimer.cancel();
							StartTip_Y++;
							startbtn.repaint();
						}
					};
					Shinctooltimer.schedule(Shinctooltask, 10, 10);
					mousestartstay.cancel();
					GetMousePos();
				}
			}
		};
	}
	public static String ConvertToLanguage(String cline, int mode)
	{
		String line = "";
		for(int i=0;i<cline.length();i++)
		{
			if(mode==1)
				line += (char)(cline.charAt(i)-47);
			else if(mode==2)
				line += (char)(cline.charAt(i)+47);
		}
		return line;
	}
	//Score variables
	static String highscorername = "";
	static int highscore = 100;
	//High Score
	public static void LoadFile() throws IOException
	{
		File datafile = new File("C:/Pinto/data.posi");
		if(datafile.exists())
		{
			FileReader fr = new FileReader(datafile);
			BufferedReader br = new BufferedReader(fr);
			String cname, cscr, name, scr;
			cname = br.readLine();            
			if(cname!=null)
			{
				name = ConvertToLanguage(cname, 2);
				highscorername = name;
			}
			cscr = br.readLine();
			if(cscr!=null)
			{
				scr = ConvertToLanguage(cscr, 2);
				highscore = Integer.parseInt(scr);
			}
			br.close();
			fr.close();
		}
		else
		{
			File datafolder = new File("C:/Pinto");
			datafolder.mkdir();
			if(datafile.createNewFile())
			{
				FileWriter write = new FileWriter(datafile);
				write.write("");
				write.close();
			}
		}
	}
	public static void OkayBtnFunc()
	{
		//Writing File
		FileWriter writer;
		try
		{
			if(namefield.getText().length()>0 && namefield.getText().length()<10)
			{
				writer = new FileWriter(new File("C:/Pinto/data.posi"));				
				writer.write(ConvertToLanguage(namefield.getText(), 1)+"\n"+ConvertToLanguage(String.valueOf(scores), 1));
				writer.close();
				highscore = scores;
				goverlabel.setVisible(true);
				ok.setVisible(false);
				namefield.setVisible(false);
				gameoverpanel.setVisible(true);
				panel.repaint();
			}
			else
			{
				if(namefield.getText().length()<=0)
				{
					JOptionPane.showMessageDialog(null, "You can't leave name box empty.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(namefield.getText().length()>10)
				{
					JOptionPane.showMessageDialog(null, "Your name can't be grater than 10 letters.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	static JTextField namefield = new JTextField("");
	static JButton ok = new JButton("Okay");
	//Online Advertisement Variables
	static int BnbAdno = 1;
	static int OAdno = 1;
	static int FirstLine = 0, SecondLine = 1, ThirdLine = 2;
	static Image OImg;
	static String FirstL, SecondL, ThirdL;
	static int lineno = 0;
	//Opacity Timer Settings
	static Timer AdOpacityTimer = new Timer();
	static TimerTask AdOpacityTimerTask = null;
	static float AdOpacity = 0.1f;
	static int AdOpacityChanging = 1;
	public static void AdOpacityTimerSettings()
	{
		AdOpacityTimer = new Timer();
		AdOpacityTimerTask = new TimerTask()
		{
			public void run()
			{
				if(AdOpacity<1.0f)
				{
					AdOpacity+=0.1f;					
				}
				else
				{
					AdOpacity=0.1f;
					AdOpacityChanging = 0;
					AdOpacityTimer.cancel();
				}
			}
		};		
	}
	//Online Advertisement Functions
	public static  void OnlineAdvTimFunc(final int LineNo, String FLine, String SLine, String TLine, Image OnlineImg)
	{
		FirstL = FLine;
		SecondL = SLine;
		ThirdL = TLine;
		OImg = OnlineImg;
		if(OAdno<(LineNo+1)/2)
		{
			OAdno++;
			FirstLine+=3;
			SecondLine+=3;
			ThirdLine+=3;
		}
		else
		{
			OAdno=1;
			FirstLine = 0;
			SecondLine = 1;
			ThirdLine = 2;
		}
		lineno = 0;
		panel.repaint();
		startpane.repaint();
		AdOpacityTimerSettings();
		AdOpacityTimer.schedule(AdOpacityTimerTask, 50, 50);
		AdOpacityChanging = 1;
		try
		{
			Thread.sleep(10000);
			ReadWebData();
		}
		catch(Exception ex) {}
	}
	public static void ReadWebData()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					URL url = new URL("https://bitnbyteComputers.co.in/SoftwareAds/SundiAdv/Text.txt");
					BufferedReader read = new BufferedReader(
							new InputStreamReader(url.openStream()));
					int LNum = 0;
					String FLin="", SLin="", TLin="";
					String i;
					while ((i = read.readLine()) != null)
					{
						if(LNum==FirstLine)
							FLin = i;
						if(LNum==SecondLine)
							SLin = i;
						if(LNum==ThirdLine)
							TLin = i;
						LNum++;
					}
					read.close();
					URL url2 = new URL("https://bitnbytecomputers.co.in/SoftwareAds/SundiAdv/Ad"+OAdno+".png");
					Image OAdvImg = ImageIO.read(url2);					
					OnlineAdvTimFunc(LNum, FLin, SLin, TLin, OAdvImg);
				}
				catch(Exception ex) {}
			}
		}.start();
	}	
	//Main Function
	public static void main(String args[]) throws IOException
	{
		GetResolution();
		LoadFile();
		IPanel_X = -(int)(res.width/2.08);
		Des1_X=-(int)(res.width/2.05);
		Des2_X=-(int)(res.width/2.21);
		//Settings
		DOT_SIZE = res.width/72; snake_x = (int)(res.width/28.8); snake_y = res.height/18;
		new Snaky().loadImages();
		//Setting Up Frame
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);   //For Maximizing
		frame.setUndecorated(true);
		frame.setBackground(Color.black);
		//Set Taskbar icon
		frame.setIconImage(head2);
		frame.setVisible(true);
		frame.getContentPane().setCursor(wcursor);		
		//Back sound
		try
		{
			AudioInputStream ains = AudioSystem.getAudioInputStream(backg);			
			background.audioclip = AudioSystem.getClip();
			background.audioclip.open(ains);
			background.audioclip.setMicrosecondPosition(0);
			background.PlayAudio();
			background.SetLoop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex)
		{}
		//Covid-19 Image
		final ImageIcon covid19 = new ImageIcon(covid);
		covid19.setImage(covid19.getImage().getScaledInstance((int)(res.width/20.57), res.height/18, Image.SCALE_DEFAULT));
		//Start Panel		
		startpane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g.setColor(Color.red);				
				g.setFont(new Font("Arial", Font.BOLD, (int)(res.width/11.07)));
				//Instructions
				g.setFont(new Font("Arial", Font.BOLD, res.width/36));
				if(FirstL!=null)
				{
					if(AdOpacity<=1.0f && AdOpacityChanging==1)
					{
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AdOpacity));
					}					
					//Advertisement
					g.setColor(Color.white);
					g.drawRect((int)(res.width/1.44), (int)(res.height/3.6), (int)(res.width/3.6), (int)(res.height/1.65));
					//Phone Number
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/65)));
					FontMetrics fm = g.getFontMetrics();
					g.setColor(Color.white);
					g.drawString(ThirdL, (int)(res.width/1.44), (int)(res.height/3.2));
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/50.6)));
					g.setColor(Color.blue);					
					fm = g.getFontMetrics();
					g.drawString(FirstL, ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(FirstL)/2, (int)(res.height/2.7));
					String SLines[] = SecondL.split("&&");
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/57.6)));
					g.setColor(Color.yellow);
					for(int i=0;i<SLines.length;i++)
					{
						fm = g.getFontMetrics();
						g.drawString(SLines[i], ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(SLines[i])/2, (int)(res.height/2.6)+(i+1)*25);						
					}
					g.drawImage(OImg, (int)(res.width/1.35), (int)(res.height/1.52), (int)(res.width/5.76), (int)(res.height/4.5), null);
				}
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				//Freeware Label
				g.setFont(new Font("Arial", Font.BOLD, (int)(res.width/28.8)));
				if(freewColor==1)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("F", (int)(res.width/2.05), res.height/3);
				if(freewColor==2)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("r", (int)(res.width/1.97), res.height/3);
				if(freewColor==3)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("e", (int)(res.width/1.92), res.height/3);
				if(freewColor==4)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("e", (int)(res.width/1.84), res.height/3);
				if(freewColor==5)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("w", (int)(res.width/1.77), res.height/3);
				if(freewColor==6)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("a", (int)(res.width/1.69), res.height/3);
				if(freewColor==7)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("r", (int)(res.width/1.63), res.height/3);
				if(freewColor==8)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.red);
				g.drawString("e", (int)(res.width/1.6), res.height/3);
				//Underline Sundi
				g.drawImage(fullsundi, (int)(res.width/2.05), (int)(res.height/3.05), (int)(res.width/6.54), res.height/18, null);
				//COVID19
				g.setColor(Color.white);
				g.drawString("COVID-19:-", res.width/72, (int)(res.height/4.09));
				g.setColor(Color.yellow);
				g.drawString("#StayHome StaySafe   Play Game", (int)(res.width/4.8), (int)(res.height/4.09));
				//COVID19 image
				g.drawImage(covid19.getImage(), res.width/36, (int)(res.height/5.08), null);
				//High score Panel
				g.setFont(new Font("Arial", Font.BOLD, res.width/32));
				g.setColor(Color.white);
				g.drawRect((int)(res.width/2.21), (int)(res.height/2.57), (int)(res.width/4.8), (int)(res.height/6));
				g.drawString("High Score", (int)(res.width/2.09), (int)(res.height/2.307));
				//Email
				g.setFont(new Font("Arial", Font.BOLD, (int)(res.width/55.38)));
				g.drawString("Mail:-dicky.bal@gmail.com", (int)(res.width/2.28), (int)(res.height/1.63));
				//Bit n Byte Scores
				if(highscore<=100)
				{
					g.setFont(new Font("Arial", Font.BOLD, (int)(res.width/41.14)));
					g.setColor(Color.yellow);
					g.drawString("Bit n Byte:-", (int)(res.width/2.16), (int)(res.height/2.075));
					g.setFont(new Font("Arial", Font.BOLD, res.width/45));
					g.setColor(Color.green);
					g.drawString("100", (int)(res.width/1.66), (int)(res.height/2.075));
				}				
				else
				{
					g.setColor(Color.yellow);
					g.drawString(highscorername+":-", (int)(res.width/2.16), (int)(res.height/2.075));
					g.setColor(Color.green);
					g.setFont(new Font("Arial", Font.BOLD, res.width/45));
					g.drawString(String.valueOf(highscore), (int)(res.width/1.64), (int)(res.height/2.075));
				}
			}
		};
		ReadWebData();
		//Freeware Color change
		Timer freewtimer = new Timer();
		TimerTask freewtimertask = new TimerTask()
		{
			public void run()
			{
				if(freewColor==8)
					freewColor=0;
				freewColor++;
				startpane.repaint();
			}
		};
		freewtimer.schedule(freewtimertask, 200, 200);
		startpane.setBackground(Color.black);
		startpane.setLayout(null);
		startpane.setBounds(0, 0, res.width, res.height);		
		//Pinto Sundi Label
		ImageIcon PSundi = new ImageIcon(PintoSundi);
		PSundi.setImage(PSundi.getImage().getScaledInstance((int)(res.width/1.90), (int)(res.height/5.11), Image.SCALE_DEFAULT));
		psundilabel.setIcon(PSundi);
		psundilabel.setBounds((res.width/2)-(int)(res.width/3.78), 0, (int)(res.width/1.90), (int)(res.height/5.11));
		psundilabel.setVisible(true);
		startpane.add(psundilabel);
		frame.getContentPane().setCursor(wcursor);
		//Description like Powered By,Programmer, and Graphics;
		ImageIcon SundiPower = new ImageIcon(power);
		SundiPower.setImage(SundiPower.getImage().getScaledInstance((int)(res.width/5.76), (int)(res.height/7.2), Image.SCALE_DEFAULT));
		final JLabel powered = new JLabel(SundiPower);
		powered.setBounds(Des1_X, (int)(res.height/1.5), (int)(res.width/5.76), (int)(res.height/7.2));
		startpane.add(powered);
		final ImageIcon SundiDes = new ImageIcon(progr);
		SundiDes.setImage(SundiDes.getImage().getScaledInstance((int)(res.width/4.11), (int)(res.height/5.14), Image.SCALE_DEFAULT));
		final JLabel Descrip = new JLabel(SundiDes);
		Descrip.setBounds(Des2_X, (int)(res.height/1.24), (int)(res.width/4.11), (int)(res.height/5.14));
		startpane.add(Descrip);
		//Timer for Movements 1
		final Timer movementtimer = new Timer();
		TimerTask movementtimertask = new TimerTask()
		{
			public void run()
			{
				if(partsofmovement1==1)
				{
					if(Des1_X<(int)(res.width/2))
						Des1_X+=(int)(res.width/28.8);
					else
						partsofmovement1=2;
					powered.setLocation(Des1_X, (int)(res.height/1.5));
				}
				else if(partsofmovement1==2)
				{
					if(Des1_X>(int)(res.width/2.05))
						Des1_X-=res.width/72;
					else
						partsofmovement1=3;
					powered.setLocation(Des1_X, (int)(res.height/1.5));
				}
				else if(partsofmovement1==3)
				{
					if(Des1_X<(int)(res.width/1.9))
						Des1_X+=res.width/72;
					else
						partsofmovement1=4;
					powered.setLocation(Des1_X, (int)(res.height/1.5));
				}
				else if(partsofmovement1==4)
				{
					if(Des1_X>(int)(res.width/2.05))
						Des1_X-=res.width/72;
					else
						movementtimer.cancel();
					powered.setLocation(Des1_X, (int)(res.height/1.5));
				}
			}
		};
		movementtimer.schedule(movementtimertask, 1000, 50);
		//Timer for Movements 2
		final Timer movementtimer2 = new Timer();
		final TimerTask movementtimertask2 = new TimerTask()
		{
			public void run()
			{
				if(partsofmovement2==1)
				{
					if(Des2_X<(int)(res.width/2.1))
						Des2_X+=(int)(res.width/28.8);
					else
						partsofmovement2=2;
					Descrip.setLocation(Des2_X, (int)(res.height/1.24));
				}
				else if(partsofmovement2==2)
				{
					if(Des2_X>(int)(res.width/2.21))
						Des2_X-=res.width/72;
					else
						partsofmovement2=3;
					Descrip.setLocation(Des2_X, (int)(res.height/1.24));
				}
				else if(partsofmovement2==3)
				{
					if(Des2_X<(int)(res.width/2.1))
						Des2_X+=res.width/72;
					else
						partsofmovement2=4;
					Descrip.setLocation(Des2_X, (int)(res.height/1.24));
				}
				else if(partsofmovement2==4)
				{
					if(Des2_X>(int)(res.width/2.21))
						Des2_X-=res.width/72;
					else
						movementtimer2.cancel();
					Descrip.setLocation(Des2_X, (int)(res.height/1.24));
				}
			}
		};
		movementtimer2.schedule(movementtimertask2, 2000, 50);
		//Instruction Pane
		final JPanel Instruct = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				//BackgroundRect
				g.setColor(Color.black);
				g.fillRect(0, 0, (int)(res.width/2.05), (int)(res.height/1.28));
				//Instructions
				g.setFont(new Font("Arial", Font.BOLD, res.width/36));
				//Instrictions
				g.setColor(Color.yellow);
				g.drawString("Instructions of the game:-", res.width/36, res.height/30);
				g.setColor(Color.red);
				g.drawString("This is a simple game in which", 0, res.height/10);
				g.drawString("you have to eat cauliflowers by", 0, (int)(res.height/6.92));
				g.drawString("Moving Sundi with arrow keys.", 0, (int)(res.height/5.29));
				g.drawString("By eating cauliflower the length", 0, (int)(res.height/4.28));
				g.drawString("of Sundi(worm) will increase.", 0, (int)(res.height/3.6));
				g.drawString("Press 'P' for Pause/Resume.", 0, (int)(res.height/3.08));
				g.drawString("Different coloured cauliflowers", 0, (int)(res.height/2.5));
				g.drawString("in the game mean as follows:-", 0, (int)(res.height/2.25));
				//Meaning of Gobbis
				//Images
				g.drawImage(food, res.width/72, (int)(res.height/2.1), (int)(res.width/28.8), res.height/18, null);
				g.drawImage(food2, res.width/72, (int)(res.height/1.82), (int)(res.width/28.8), res.height/18, null);
				g.drawImage(food3, res.width/72, (int)(res.height/1.60), (int)(res.width/28.8), res.height/18, null);
				//Texts
				g.drawString("Normal", (int)(res.width/14.4), (int)(res.height/1.9));
				g.drawString("Speed Increaser", (int)(res.width/14.4), (int)(res.height/1.68));
				g.drawString("Speed Decreaser", (int)(res.width/14.4), (int)(res.height/1.5));
			}
		};
		Instruct.setBounds(IPanel_X, (int)(res.height/3.6), (int)(res.width/2.4), (int)(res.width/1.28));
		startpane.add(Instruct);
		final Timer Instructimer = new Timer();
		TimerTask InstructTask = new TimerTask()
		{
			public void run()
			{
				if(IPanel_X<0)
				{
					IPanel_X+=30;				
					Instruct.setLocation(IPanel_X, (int)(res.height/3.6));
				}
				else
				{
					Instructimer.cancel();
				}
			}
		};
		Instructimer.schedule(InstructTask, 50, 50);
		//Gif Labels
		ImageIcon sleftfire = new ImageIcon(sFire1);
		sleftfire.setImage(sleftfire.getImage().getScaledInstance((int)(res.width/4), (int)(res.height/5.5), Image.SCALE_DEFAULT));
		JLabel leftlabel = new JLabel(sleftfire);
		leftlabel.setBounds(0, 0, res.width/4, (int)(res.height/5.5));
		leftlabel.setForeground(Color.white);
		startpane.add(leftlabel);
		ImageIcon srightfire = new ImageIcon(sFire2);
		srightfire.setImage(srightfire.getImage().getScaledInstance((int)(res.width/4), (int)(res.height/5.5), Image.SCALE_DEFAULT));
		JLabel rightlabel = new JLabel(srightfire);
		rightlabel.setBounds((int)(res.width/1.33), 0,(int)(res.width/4), (int)(res.height/5.5));
		startpane.add(rightlabel);		
		//Start Button
		ImageIcon startbtnI = new ImageIcon(Gobbieat);
		startbtnI.setImage(startbtnI.getImage().getScaledInstance((int)(res.width/7.2), (int)(res.height/9.67), Image.SCALE_DEFAULT));
		startbtn = new JLabel(startbtnI)
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				//Tooltip
				if(starttooltip==true)
				{
					g.setColor(Color.black);
					g.drawRect(mousetooltip_X-(int)(res.width/1.21), mousetooltip_Y-(int)(res.height/1.16), (int)(res.width/14.4), StartTip_Y);
					g.setColor(Color.white);
					g.fillRect((mousetooltip_X-(int)(res.width/1.21))+1, (mousetooltip_Y-(int)(res.height/1.16))+1, (int)(res.width/14.4)-1, StartTip_Y-1);
					g.setColor(Color.black);
					g.setFont(new Font("Arial", Font.BOLD, res.width/120));
					g.drawString("Click here to play", (mousetooltip_X-(int)(res.width/1.21))+3, (mousetooltip_Y-(int)(res.height/1.16))+res.height/60);
				}
			}
		};
		startbtn.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				startpane.setVisible(false);
				frame.setContentPane(panel);
				started = true;
				initGame();
			}
			public void mouseEntered(MouseEvent arg0)
			{
				longmousestay=0;
				mousein = true;
				mousestartstay.schedule(mousestartstaytask, 1000, 1000);
			}
			public void mouseExited(MouseEvent arg0)
			{
				starttooltip = false;
				startbtn.repaint();
				mousein = false;
				mousestartstay.cancel();
				LoadStartButtonTimer();
			}
			public void mousePressed(MouseEvent arg0)
			{}
			public void mouseReleased(MouseEvent arg0)
			{}			
		});		
		startbtn.setBounds((int)(res.width/1.19), (int)(res.height/1.12), (int)(res.width/7.2), (int)(res.height/9.67));
		startbtn.setFont(new Font("Arial", Font.BOLD, res.height/30));
		startpane.add(startbtn);
		LoadStartButtonTimer();
		//__________________________________________________________________________
		frame.setContentPane(startpane);
		frame.getContentPane().setCursor(wcursor);
		panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				if(gameover==1)
				{
					speedt.cancel();
					g.setColor(Color.white);
					g.setFont(new Font("Arial", Font.BOLD, res.height/12));															
				}
				//Drawing Snake
				for(int i=0;i<dots;i++)
			    {
					if(i==0)
					{
						if(Direction==1)
							g.drawImage(head2, x[i], y[i]-5, DOT_SIZE+5, DOT_SIZE+5, null);
						else if(Direction==2)
							g.drawImage(head, x[i], y[i]-5, DOT_SIZE+5, DOT_SIZE+5, null);
						else if(Direction==3)
							g.drawImage(head4, x[i], y[i]-5, DOT_SIZE+5, DOT_SIZE+5, null);
						else if(Direction==4)
							g.drawImage(head3, x[i], y[i]-5, DOT_SIZE+5, DOT_SIZE+5, null);
					}
					else
					{
						if(Direction==2 || Direction==1)
							g.drawImage(ball, x[i], y[i], DOT_SIZE, DOT_SIZE, null);
						else if(Direction==3)
							g.drawImage(body2, x[i], y[i]-5, DOT_SIZE, DOT_SIZE, null);
						else if(Direction==4)
				    		g.drawImage(body2, x[i], y[i]-5, DOT_SIZE, DOT_SIZE, null);
					}
			    }
				//Draw Apple
				switch(foodno)
				{
					case 1:
						g.drawImage(food, food_x, food_y, res.width/48, res.height/30, null);
						break;
					case 2:
						g.drawImage(food2, food_x, food_y, res.width/48, res.height/30, null);
						break;
					case 3:
						g.drawImage(food3, food_x, food_y, res.width/48, res.height/30, null);
						break;
				}
				//Scores
				g.drawImage(food, res.width/288, res.height/180, res.width/72, res.height/45, null);
				g.setFont(new Font("Arial", Font.BOLD, res.height/45));
				g.setColor(Color.white);
				g.drawString(" : "+String.valueOf(scores), (int)(res.width/51.42), (int)(res.height/39.13));
				//if Speed change
				if(speedchanged==1)
				{
					g.setColor(Color.BLUE);
					g.drawString("Speed Increased", (int)(res.width/1.2), res.height/36);
					if(timeractivated == 0)
					{
						speedcrtimer.cancel();
						speedcrtimertask.cancel();						
						speedcrtimer = new Timer();
						speedcrtimertask = new TimerTask()
    					{
							public void run()
							{
								timeractivated = 0;
								speedchanged = 0;
								speedcrtimer.cancel();
							}
						};
						speedcrtimer.schedule(speedcrtimertask, 3000, 3000);
						timeractivated = 1;
					}
				}
				else if(speedchanged==2)
				{
					g.setColor(Color.RED);
					g.drawString("Speed Decreased", (int)(res.width/1.2), res.height/36);
					if(timeractivated == 0)
					{
						speedcrtimer.cancel();
						speedcrtimertask.cancel();						
						speedcrtimer = new Timer();
						speedcrtimertask = new TimerTask()
						{
							public void run()
							{
								timeractivated = 0;
								speedchanged = 0;
								speedcrtimer.cancel();
							}
						};
						speedcrtimer.schedule(speedcrtimertask, 3000, 3000);
						timeractivated = 1;
					}
				}
				//Paused Game
				if(pause==true)
				{
					g.setFont(new Font("Arial", Font.BOLD, res.height/9));
					g.setColor(Color.white);					
					pauselabel.setVisible(true);
					if(FirstL!=null)
					{
						if(AdOpacity<=1.0f && AdOpacityChanging==1)
						{
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AdOpacity));
						}
						//Advertisement
						//Phone Number
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/65)));
						FontMetrics fm = g.getFontMetrics();
						g.setColor(Color.white);
						g.drawString(ThirdL, (int)(res.width/1.44), (int)(res.height/3.2));
						g.setColor(Color.white);
						g.drawRect((int)(res.width/1.44), (int)(res.height/3.6), (int)(res.width/3.6), (int)(res.height/1.65));				
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/50.6)));
						g.setColor(Color.blue);
						fm = g.getFontMetrics();
						g.drawString(FirstL, ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(FirstL)/2, (int)(res.height/2.7));
						String SLines[] = SecondL.split("&&");
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/57.6)));
						g.setColor(Color.yellow);
						for(int i=0;i<SLines.length;i++)
						{
							fm = g.getFontMetrics();
							g.drawString(SLines[i], ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(SLines[i])/2, (int)(res.height/2.6)+(i+1)*25);						
						}
						g.drawImage(OImg, (int)(res.width/1.35), (int)(res.height/1.52), (int)(res.width/5.76), (int)(res.height/4.5), null);
					}
					panel.repaint();
				}
				//Entername box
				if(gameover==1)
				{
					if(FirstL!=null)
					{
						if(AdOpacity<=1.0f && AdOpacityChanging==1)
						{
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, AdOpacity));
						}
						//Advertisement
						//Phone Number
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/65)));
						FontMetrics fm = g.getFontMetrics();
						g.setColor(Color.white);
						g.drawString(ThirdL, (int)(res.width/1.44), (int)(res.height/3.2));
						g.setColor(Color.white);
						g.drawRect((int)(res.width/1.44), (int)(res.height/3.6), (int)(res.width/3.6), (int)(res.height/1.65));				
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/50.6)));
						g.setColor(Color.blue);
						fm = g.getFontMetrics();
						g.drawString(FirstL, ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(FirstL)/2, (int)(res.height/2.7));
						String SLines[] = SecondL.split("&&");
						g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int)(res.width/57.6)));
						g.setColor(Color.yellow);
						for(int i=0;i<SLines.length;i++)
						{
							fm = g.getFontMetrics();
							g.drawString(SLines[i], ((int)(res.width/1.44)+((int)(res.width/3.6)/2))-fm.stringWidth(SLines[i])/2, (int)(res.height/2.6)+(i+1)*25);						
						}
						g.drawImage(OImg, (int)(res.width/1.35), (int)(res.height/1.52), (int)(res.width/5.76), (int)(res.height/4.5), null);
					}
					panel.repaint();
				}
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				if(scores>highscore && gameover==1)
				{
					g.setFont(new Font("Arial", Font.BOLD, (int)(res.height/25)));
					g.setColor(Color.white);
					g.drawString("You scored highest", (int)(res.width/2.4), (int)(res.height/3.9));
					g.setColor(Color.yellow);
					g.drawString("Enter your name:-", (int)(res.width/3.4), (res.height/3));
					namefield.setVisible(true);	
					namefield.requestFocus();
					ok.setVisible(true);					
				}
				else
				{
					if(gameover==1)
					{
						gameoverpanel.setVisible(true);
						goverlabel.setVisible(true);
					}
				}
	    	}
		};
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		panel.setBackground(Color.black);
		panel.setLayout(null);
		//Enter name Text Box
		namefield = new JTextField()
		{
			public void setBorder(Border border)
			{}
		};		
		namefield.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent arg0)
			{
			}
			public void focusLost(FocusEvent arg0)
			{
				if(gameover==1 && namefield.isVisible())
					namefield.requestFocus();
			}			
		});
		namefield.setDocument(new JTextFieldLimit(8));
		namefield.setCaretColor(Color.WHITE);
		namefield.setForeground(Color.WHITE);
		namefield.setBackground(Color.black);		
		namefield.setFont(new Font("Arial", Font.BOLD, (int)(res.height/25)));
		namefield.setBounds((int)(res.width/1.8), (int)(res.height/3.4), (int)(res.width/7.2), res.height/18);
		namefield.setVisible(false);
		//Enter name Okay Box
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{				
				OkayBtnFunc();
			}
		});
		ok.setFont(new Font("Arial", Font.BOLD, 40));
		ok.setBounds((int)(res.width/1.4), (int)(res.height/3.4), (int)(res.width/7.2), (int)(res.height/20));		
		ok.setVisible(false);
		panel.add(ok);
		panel.add(namefield);
		//Minimize Button
		Mbtn = new JButton()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(5.0f));
				g2d.drawLine(res.width/96, res.height/90, (int)(res.width/57.6), res.height/45);
				g2d.drawLine((int)(res.width/57.6), res.height/45, (int)(res.width/41.14), res.height/90);
			}
		};
		Mbtn.setBounds((int)(res.width/1.075), 0, (int)(res.width/28.8), res.height/30);
		Mbtn.setFont(new Font("Arial", Font.BOLD, (int)(res.width/72)));
		Mbtn.setBackground(Color.RED);
		Mbtn.setVisible(false);
		Mbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				frame.setState(Frame.ICONIFIED);
			}
		});
		panel.add(Mbtn);
		//Exit Button
		Ebtn = new JButton()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke((float)(res.width/288)));
				g2d.drawLine(res.width/96, res.height/90, (int)(res.width/41.14), res.height/45);
				g2d.drawLine(res.width/96, res.height/45, (int)(res.width/41.14), res.height/90);
			}
		};
		Ebtn.setBounds((int)(res.width/1.035), 0, (int)(res.width/28.8), res.height/30);
		Ebtn.setFont(new Font("Arial", Font.BOLD, (int)(res.width/72)));
		Ebtn.setBackground(Color.RED);
		Ebtn.setVisible(false);
		Ebtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				System.exit(0);
			}
		});
		panel.add(Ebtn);
		frame.addWindowListener(new WindowListener()
		{
			public void windowActivated(WindowEvent arg0){}
			public void windowClosed(WindowEvent arg0){}
			public void windowClosing(WindowEvent arg0){System.exit(0);}
			public void windowDeactivated(WindowEvent arg0){}
			public void windowDeiconified(WindowEvent arg0){}
			public void windowIconified(WindowEvent arg0){}
			public void windowOpened(WindowEvent arg0){}			
		});
		//Restart Button
		resbtn.setToolTipText("Click here to restart");
		resbtn.setFont(new Font("arial", Font.BOLD, (int)(res.width/28.8)));
		resbtn.setBounds((int)(res.width/28.8), 0, (int)(res.width/4.235), res.height/15);
		resbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				initGame();
			}
		});
		resbtn.setBackground(Color.RED);
		resbtn.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{}
			public void mouseEntered(MouseEvent arg0)
			{
				option_Y = res.height/90;
				gameoveroption = 1; 
				gameoverpanel.repaint();
				exitbtn.setBackground(NormalBackground);
				resbtn.setBackground(Color.red);
			}
			public void mouseExited(MouseEvent arg0)
			{}
			public void mousePressed(MouseEvent arg0)
			{}
			public void mouseReleased(MouseEvent arg0)
			{}			
		});		
		gameoverpanel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(worm, 0, option_Y, (int)(res.width/28.8), res.height/30, null);				
			}
		};
		//Gameover Panel
		gameoverpanel.setBounds((int)(res.width/2.8), (res.height/2)+res.height/18, (int)(res.width/3.5), res.height/5);
		gameoverpanel.setBackground(Color.black);		
		gameoverpanel.setLayout(null);
		gameoverpanel.setVisible(false);
		//gameoverpanel.setVisible(false);		
		gameoverpanel.add(resbtn);
		//Exit Button
		exitbtn.setToolTipText("Click here to restart");
		exitbtn.setFont(new Font("arial", Font.BOLD, (int)(res.width/28.8)));
		exitbtn.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{}
			public void mouseEntered(MouseEvent arg0)
			{
				gameoveroption = 2;
				option_Y = (int)(res.height/8.57);
				gameoverpanel.repaint();
				resbtn.setBackground(NormalBackground);
				exitbtn.setBackground(Color.red);
			}
			public void mouseExited(MouseEvent arg0)
			{}
			public void mousePressed(MouseEvent arg0)
			{}
			public void mouseReleased(MouseEvent arg0)
			{}			
		});
		exitbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				System.exit(0);
			}
		});		
		exitbtn.setBounds((int)(res.width/28.8), (int)(res.height/10), (int)(res.width/4.235), res.height/15);
		gameoverpanel.add(exitbtn);
		panel.add(gameoverpanel);
		NormalBackground = exitbtn.getBackground();
		//Gameover Label
		ImageIcon PauseI = new ImageIcon(Pause);
		PauseI.setImage(PauseI.getImage().getScaledInstance((int)(res.width/2.19), (int)(res.height/6.33), Image.SCALE_DEFAULT));
		ImageIcon GOverI = new ImageIcon(Gameover);
		GOverI.setImage(GOverI.getImage().getScaledInstance((int)(res.width/2.19), (int)(res.height/6.33), Image.SCALE_DEFAULT));
		goverlabel = new JLabel();
		goverlabel.setIcon(GOverI);
		goverlabel.setBounds((int)(res.width/5), (int)(res.height/2.6), (int)(res.width/2.19), (int)(res.height/6.33));
		goverlabel.setVisible(false);
		panel.add(goverlabel);
		//Pause Label
		pauselabel.setIcon(PauseI);
		pauselabel.setBounds((int)(res.width/5), (int)(res.height/2.6), (int)(res.width/2.19), (int)(res.height/6.33));
		pauselabel.setVisible(false);
		panel.add(pauselabel);
		new Snaky();
	}
}
