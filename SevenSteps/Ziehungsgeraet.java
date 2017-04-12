public class Ziehungsgeraet {
	protected Object[] pool;
	protected Object[] pool2;
	protected Object[] pool3;
	protected int aktuell;
	protected int entfernt;

	Ziehungsgeraet(Object...x)            //erzeugt ein neues Ziehungsgeraet
	{
		pool = x; 	
		entfernt = 0;
	}
	Ziehungsgeraet()
	{
		pool = null;
	}
	void insert(Object x)                         //Fügt ein Objekt ein in den Pool
	{
		pool2 = new Object[pool.length+1];
		for(int i = 0 ; i < pool.length;i++)
			pool2[i] = pool[i]; 
		pool2[pool2.length-1]=x;
		pool = pool2;
	}

	Object get()                  //Zieht ein Objekt aus dem Pool
	{   
		Object tmp;
		Object Ausgabe;
		if( pool.length >= (entfernt+1))
		{	
			aktuell = pool.length - entfernt; 
			int gezogen = (int)(Math.random() * aktuell);
			Ausgabe = pool[gezogen];

			tmp = pool[gezogen];                      //Gezogenen Wert mit dem Wert am Ende des Feldes
			pool[gezogen] = pool[pool.length - (entfernt + 1)];    //vertauschen
			pool[pool.length - entfernt - 1] = tmp;
			entfernt++;
			return Ausgabe;                           //gezogenen Wert Ausgeben
		}
		else
			return Fehler(0);
	}
	
	
	private String Fehler(int n)                     //falls keine Zahlen mehr im Pool
	{
		return "Fehler !! Pool ist Leer";
	}

	Object[] get(int m)                       //Zieht eine vorgegebene Anzahl von Objekten aus dem Pool
	{   
		Object[] pool3 = new Object[m];
		for(int i = 0 ; i < m ; i++)
			pool3[i] = get();
           
		return pool3;                //Rückgabe des Arrays mit den gezogenen Werten
	}
	void reset()
	{
		entfernt = 0;
	}
	
   










}
