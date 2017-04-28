
public class Ziehungsgeraet_Test {
	public static void main(String [] args){
		Ziehungsgeraet lotto = new Ziehungsgeraet(1,2,3,4,5,6,7,8,9,10,
				11,12,13,14,15,16,17,18,19,20,
				21,22,23,24,25,26,27,28,29,30,
				31,32,33,34,35,36,37,38,39,40,
				41,42,43,44,45,46,47,48,49);
       
		Integer[] zahlen = new Integer[6];
		for(int i = 0; i <= 5 ; i++)
		{
			System.out.println("Bitte geben sie ihre " +(i+1) +" Glückszahl ein : ");
			zahlen[i] = 6;
		}
		int richtig3 = 0 ;int richtig4 = 0;int richtig5 = 0;int richtig6 = 0;//Glückszahlen
		int gesammt =0;

		for(int k=1 ; k<= 100000; k++)
		{
			Object[] y= lotto.get(6);   //Ziehung von 6 Zahlen

			for(int i = 0; i <= 5 ;i++)
			{
				for(int j = 0 ; j <= 5 ; j++)
				{  
					if(y[i].equals(zahlen[j]))  //vergleich ob glückszahl = zufallszahl
					{
						gesammt++;	               	  
					}
				}	 
				switch(gesammt)
				{
				case 3: richtig3++;
				break;
				case 4: richtig4++;        
				break;
				case 5: richtig5++;
				break;
				case 6: richtig6++;        
				}
			}
			lotto.reset();
			gesammt = 0;
		}
		System.out.println( "In 100000 Ziehungen :");   //Ausgabe
		System.out.println(richtig3 + " mal 3 Richtige");
		System.out.println(richtig4 + " mal 4 Richtige");
		System.out.println(richtig5 + " mal 5 Richtige");
		System.out.println(richtig6 + " mal 6 Richtige");
	}

}

